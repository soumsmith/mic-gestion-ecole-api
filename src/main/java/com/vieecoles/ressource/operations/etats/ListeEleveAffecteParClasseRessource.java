package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.dto.eleveNonAffecteParClasseDto;
import com.vieecoles.services.etats.*;
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

@Path("/imprimer-rapport")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class ListeEleveAffecteParClasseRessource {
    @Inject
    EntityManager em;
    @Inject
    IdentiteEtatService identiteEtatService ;
    @Inject
    EleveAffecteParClasseServices resultatsServices ;

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
    @Path("/list-eleve-affecte-par-classe/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<eleveAffecteParClasseDto>  repartiParAnn(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,
                                                         @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<eleveAffecteParClasseDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre)  ;
        return detailsBull ;

    }

    @GET
    @Path("/imprimer-list-eleve-affecte-par-classe/{idEcole}/{type}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type,@PathParam("libelleAnnee") String libelleAnnee,
                                                 @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_nominative_des_eleves_et_resultats_scolaire.jrxml");



       List<eleveAffecteParClasseDto> detailsBull = new ArrayList<>() ;

        detailsBull= resultatsServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
        System.out.println("detailsBull "+detailsBull);

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
