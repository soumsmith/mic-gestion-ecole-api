package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.profilDto;
import com.vieecoles.entities.profil;
import com.vieecoles.services.profilService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/imprimer-etat")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class etatsRessources {
    @Inject
    EntityManager em;
   

  
    @GET
    @Path("/imprimer-list/{type}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getEtatListProfil(@PathParam("type") String type) throws Exception, JRException {
        List<profilDto>  profilList = new ArrayList<>() ;
        try {
            profilList= (List<profilDto>) em.createQuery("select o from profil  o  where  o.profil_libelle not  in ('Fondateur','Admin')")
                   // .setParameter("names",names)
                    .getResultList();
        } catch (Exception e) {
            profilList = null;
        }
System.out.print("profilList "+profilList);
        if(type.toUpperCase().equals("PDF")){
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(profilList) ;
            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/etats/profils.jrxml"));
            Map<String, Object> map = new HashMap<>();
            map.put("title",type);
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
    //to pdf ;
      byte[] data =JasperExportManager.exportReportToPdf(report);
      HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=profils.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
    } else {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(profilList) ;
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/etats/profils.jrxml"));
        Map<String, Object> map = new HashMap<>();
        map.put("title", type);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRDocxExporter exporter = new JRDocxExporter();    
        exporter.setExporterInput(new SimpleExporterInput(report)); 
       // File exportReportFile = new File("profils" + ".docx"); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();    
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));    
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=profils.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }
       
    
      
      
    }



    

}
