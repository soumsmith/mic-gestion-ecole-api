package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.PyramideEffectDto;
import com.vieecoles.services.etats.PyramideEffectifBilanServices;
import com.vieecoles.services.etats.PyramideEffectifServices;
import com.vieecoles.services.etats.RecapitulatifStatistiqueBilanServices;
import com.vieecoles.services.etats.RecapitulatifStatistiqueServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/recap-statistique-filiere")

public class RecapitulatifStatisFiliereRessource {
    @Inject
    EntityManager em;
    @Inject
    RecapitulatifStatistiqueServices recapitulatifStatistiqueServices ;
    @Inject
    RecapitulatifStatistiqueBilanServices recapitulatifStatistiqueBilanServices ;




    private static String UPLOAD_DIR = "/data/";





    @GET
    @Transactional
    @Path("/infos/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PyramideEffectDto>  getDtoRapport2(@PathParam("idEcole") Long idEcole  , @PathParam("anneeId") Long anneeId ) throws Exception, JRException {

        return  recapitulatifStatistiqueServices.getPyramide(idEcole,anneeId,5);
    }






    @GET
    @Transactional
    @Path("/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole , @PathParam("anneeId") Long anneeId ) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        List<PyramideEffectDto> detailsBull= new ArrayList<>() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Recapitulatif_statique_filiere_classe1erCycle.jrxml");

        detailsBull = recapitulatifStatistiqueServices.getPyramide(idEcole,anneeId,4) ;

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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Recapitulatif_statique_filiere_classe1erCycle.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Transactional
    @Path("second-cyle/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportSecon(@PathParam("idEcole") Long idEcole , @PathParam("anneeId") Long anneeId ) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        List<PyramideEffectDto> detailsBull= new ArrayList<>() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Recapitulatif_statique_filiere_classe1erCycle2.jrxml");

        detailsBull = recapitulatifStatistiqueServices.getPyramide(idEcole,anneeId,5) ;

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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Recapitulatif_statique_filiere_classe1erCycle.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }


    @GET
    @Transactional
    @Path("Bilan/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportBilan(@PathParam("idEcole") Long idEcole , @PathParam("anneeId") Long anneeId ) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        List<PyramideEffectDto> detailsBull= new ArrayList<>() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Pyramide_et_effectifs_eleveBilan.jrxml");

        detailsBull = recapitulatifStatistiqueBilanServices.getPyramide(idEcole,anneeId) ;

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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Recapitulatif_statique_filiere_classe1erCycle.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

}
