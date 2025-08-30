package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/imprimer-rapport")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class IdentiteRessource {
    @Inject
    EntityManager em;
    @Inject
    IdentiteEtatService identiteEtatService ;





    private static String UPLOAD_DIR = "/data/";

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/identite/{idEcole}")
    public List<IdentiteEtatDto>  repartiParAnn(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<IdentiteEtatDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= identiteEtatService.getIdentiteDto(idEcole);
        return detailsBull ;

    }

    @GET
    @Path("/identite-ecole/{idEcole}/{type}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Identite.jrxml");



       List<IdentiteEtatDto> detailsBull = new ArrayList<>() ;

      detailsBull= identiteEtatService.getIdentiteDto(idEcole);;
       // System.out.println("detailsBull "+detailsBull);

        if(type.toUpperCase().equals("PDF")){
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
             //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
             Map<String, Object> map = new HashMap<>();
             JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
    //to pdf ;
      byte[] data =JasperExportManager.exportReportToPdf(report);
      HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=IdentiteEcole.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
    } else {
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
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=IdentiteEcole.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }




    }





}
