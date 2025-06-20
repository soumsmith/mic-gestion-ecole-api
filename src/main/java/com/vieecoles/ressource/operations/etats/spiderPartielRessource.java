package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.ecole;
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
import java.io.InputStream;
import java.util.*;

@Path("/imprimer-rapport")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class spiderPartielRessource {
    @Inject
    EntityManager em;
    @Inject
    IdentiteEtatService identiteEtatService ;
    @Inject
    RecapitulatifStatistiqueBilan2Services recapitulatiBilan ;


    @Inject
    RepartitionElevParAnNaissServices repartitionElevParAnNaissServices ;

    @Inject
    RecapitulatifStatistiqueServices recapitulatif ;




    private static String UPLOAD_DIR = "/data/";







    @GET
    @Transactional
    @Path("/pouls-partiel-rapport/{idEcole}/{type}/{libelleAnnee}/{libelleTrimetre}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type ,
                                                 @PathParam("libelleAnnee") String libelleAnnee,
                                                 @PathParam("libelleTrimetre") String libelleTrimetre ,@PathParam("anneeId") Long anneeId) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/RapportDerentree/Spider_Partiel.jrxml");

        List<PyramideEffectDto> pyramideEffectDto1 = new ArrayList<>() ;
        List<PyramideEffectDto> pyramideEffectDto2 = new ArrayList<>() ;
        List<PyramideEffectDto> pyramideEffectDtoBilan = new ArrayList<>() ;


       List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>() ;
       List<BoursierDto> boursierDto = new ArrayList<>() ;
       List<EffApprocheNiveauGenreDto> effApprocheNiveauGenreDto= new ArrayList<>()  ;
       EffApprocheNiveauGenreDto effApprocheNive = new EffApprocheNiveauGenreDto() ;

        spiderPartielDto detailsBull= new spiderPartielDto() ;

         ecole myScole= new ecole() ;
        myScole= ecole.findById(idEcole) ;


        repartitionEleveParAnNaissDto= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre);

        pyramideEffectDto1 = recapitulatif.getPyramide(idEcole,anneeId,4) ;
        System.out.println("Pyramide 1er cycle ok");
        pyramideEffectDto2 = recapitulatif.getPyramide(idEcole,anneeId,5) ;
        System.out.println("Pyramide 2nd cycle ok");
        pyramideEffectDtoBilan = recapitulatiBilan.getPyramide(idEcole,anneeId);

        detailsBull.setPyramideEffectDto1(pyramideEffectDto1);
        detailsBull.setPyramideEffectDto2(pyramideEffectDto2);
        detailsBull.setPyramideEffectDtoBilan(pyramideEffectDtoBilan);

        detailsBull.setRepartitionEleveParAnNaissDto(repartitionEleveParAnNaissDto);

      // System.out.print("soummm"+resultatsElevesAffecteDto.toString());
        if(type.toUpperCase().equals("PDF")){
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
             //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
             Map<String, Object> map = new HashMap<>();
             JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
    //to pdf ;
      byte[] data =JasperExportManager.exportReportToPdf(report);
      HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=RapportPouls.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
    } else {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
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
     // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport Pouls-Scolaire.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }




    }





}
