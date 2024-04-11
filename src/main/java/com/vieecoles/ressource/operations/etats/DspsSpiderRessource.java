package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.services.etats.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@Path("/imprimer-rapport-dsps")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class DspsSpiderRessource {
    @Inject
    EntityManager em;
    @Inject
    DpspServices dpspServices ;
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/pouls-rapport-dsps/{idEcole}/{libellePeriode}/{libelleAnnee}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/rapport_dsps.jrxml");
        spiderDspsDto detailsBull= new spiderDspsDto() ;
        List<DspsDto>  dspsDto = new ArrayList<>() ;

       /* try {
            dspsDto = dpspServices.DspspDto(idEcole,libellePeriode,libelleAnnee) ;

        } catch (RuntimeException e){
            e.printStackTrace ();
        }*/



       // detailsBull.setDspsDto(dspsDto);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);

        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("idEcole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("periode", libellePeriode);
        // map.put("title", type);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport Pouls-Scolaire-dsps.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);



    }





}
