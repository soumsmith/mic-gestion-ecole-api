package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
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
import java.util.*;

@Path("/imprimer-rapport")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class ResultatsScolaireParNiveauRessource {
    @Inject
    EntityManager em;
    @Inject
    IdentiteEtatService identiteEtatService ;
    @Inject
    resultatsServices resultatsServices ;

    @Inject
    RepartitionElevParAnNaissServices repartitionElevParAnNaissServices ;

    @Inject
    RecapClassePedaAffNonAffecServices recapClassePedaAffNonAffecServices ;

    @Inject
    ApprocheParNiveauParGenreServices approcheParNiveauParGenreServices ;

    @Inject
    BoursiersServices boursiersServices ;

    @Inject
    TransfertsServices transfertsServices ;
    @Inject
    MajorParClasseNiveauServices majorServices ;

    @Inject
    EleveNonAffecteParClasseServices eleveNonAffecteParClasseServices ;

    @Inject
    EleveAffecteParClasseServices eleveAffecteParClasseServices ;

    private static String UPLOAD_DIR = "/data/";

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list-eleve-affecte-par-classe-par-niveau/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<ResultatsElevesAffecteDto>  repartiParAnn(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,@PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<ResultatsElevesAffecteDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre)  ;
        return detailsBull ;

    }

    @GET
    @Path("/imprimer-list-eleve-affecte-par-classe-par-niveau/{idEcole}/{type}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type ,@PathParam("libelleAnnee") String libelleAnnee,@PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Resultats_scolaire.jrxml");

        URL res = getClass().getClassLoader().getResource("etats/spider/Resultats_scolaire.jrxml");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        System.out.println("absolutePath "+absolutePath);

       List<ResultatsElevesAffecteDto> detailsBull = new ArrayList<>() ;

        detailsBull= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
        //System.out.println("detailsBull "+detailsBull);

        if(type.toUpperCase().equals("PDF")){
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
             //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
             Map<String, Object> map = new HashMap<>();
             JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
    //to pdf ;
      byte[] data =JasperExportManager.exportReportToPdf(report);
      HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=eleveAffecteParNiveau.pdf");
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
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=eleveAffecteParNiveau.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }




    }





}
