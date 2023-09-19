package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.dto.matriceAnnuelleDto;
import com.vieecoles.dto.spiderCIODto;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.etats.MatriceAnnuelleServices;
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

@Path("/imprimer-matrice-Annuelle")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class MatriceAnnuelleRessource {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ecoleviedbv2";
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;
    Connection dbConnection = null;

    @Inject
    EntityManager em;
    @Inject
    BulletinRapportServices dpspServices ;

    @Inject
    MatriceAnnuelleServices matriceAnnuelleServices ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/imprimer-spider-annuelle-infos/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<matriceAnnuelleDto> getDtoRapportInfos(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/matriceAnnuelle.jrxml");

        List<matriceAnnuelleDto> detailsBull= new ArrayList<>() ;


     return    detailsBull= matriceAnnuelleServices.getInfosMatriceAnnuelleClasse(idEcole,libelleAnnee, periode ,anneeId,classe);

    }


    @GET
    @Transactional
    @Path("/imprimer-spider-annuelle-xls/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/matriceAnnuelle.jrxml");

        List<matriceAnnuelleDto> detailsBull= new ArrayList<>() ;


        detailsBull= matriceAnnuelleServices.getInfosMatriceAnnuelleClasse(idEcole,libelleAnnee, periode ,anneeId,classe);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        // map.put("title", type);
        // map.put("title", type);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Matrice Annuelle.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);



    }


    @GET
    @Transactional
    @Path("/imprimer-spider-annuelle/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportmat(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/matriceAnnuelle.jrxml");

        List<matriceAnnuelleDto> detailsBull= new ArrayList<>() ;


        detailsBull= matriceAnnuelleServices.getInfosMatriceAnnuelleClasse(idEcole,libelleAnnee, periode ,anneeId,classe);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        // map.put("title", type);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=matrice annuelle.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);


    }



}
