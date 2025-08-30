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

@Path("/imprimer-rapport-annuelle")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class spiderAnnuelleRessource {
    @Inject
    EntityManager em;
    @Inject
    IdentiteEtatService identiteEtatService ;
    @Inject
    resultatsAnnuelleServices resultatsServices ;

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
    MajorParClasseNiveauAnnuelleServices majorServices ;

    @Inject
    EleveNonAffecteParClasseAnnuelleServices eleveNonAffecteParClasseServices ;

    @Inject
    EleveAffecteParClasseAnnulleServices eleveAffecteParClasseServices ;

    @Inject
    resultatsRecapAnnuelleServices resultatsRecapServices ;
    @Inject
    resultatsNonAffecteAnnuelleServices resultatsNonAffecteServices ;
    @Inject
    resultatsRecapNonAffAnnuelleServices resultatsRecapNonAffServices ;
    @Inject
    resultatsRecapAffEtNonAffAnnuelleServices resultatsRecapAffEtNonAffServices ;




    private static String UPLOAD_DIR = "/data/";

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/convertDate")
    public String  convert() throws Exception, JRException {

        return "DFS" ;

    }




    @GET
    @Transactional
    @Path("/pouls-rapport/{idEcole}/{type}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type ,
                                                 @PathParam("libelleAnnee") String libelleAnnee,
                                                 @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book.jrxml");

        EmptyDto myIntro = new EmptyDto();
        myIntro.setIntro("INTRODUCTION");
        List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
       List<ResultatsElevesAffecteDto> resultatsElevesAffecteDto = new ArrayList<>() ;
       List<RecapDesResultatsElevesAffecteDto> recapDesResultatsElevesAffecteDto= new ArrayList<>();
       List<ResultatsElevesNonAffecteDto> resultatsElevesNonAffecteDto = new ArrayList<>();
       List<RecapDesResultatsElevesNonAffecteDto> recapDesResultatsElevesNonAffecteDto = new ArrayList<>();
       List<RecapResultatsElevesAffeEtNonAffDto> recapResultatsElevesAffeEtNonAffDto = new ArrayList<>();
       List<eleveAffecteParClasseDto> eleveAffecteParClasseDto = new ArrayList<>();
       List<eleveNonAffecteParClasseDto> eleveNonAffecteParClasseDto = new ArrayList<>();
       List<MajorParClasseNiveauDto> majorParClasseNiveauDto = new ArrayList<>();
       List<TransfertsDto>transfertsDto= new ArrayList<>() ;
       List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>() ;
       List<BoursierDto> boursierDto = new ArrayList<>() ;
       List<EffApprocheNiveauGenreDto> effApprocheNiveauGenreDto= new ArrayList<>()  ;
       EffApprocheNiveauGenreDto effApprocheNive = new EffApprocheNiveauGenreDto() ;


       List<EmptyDto> introLis= new ArrayList<>() ;
        introLis.add(myIntro) ;
         spiderDto detailsBull= new spiderDto() ;

         ecole myScole= new ecole() ;
        myScole= ecole.findById(idEcole) ;

        identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
        resultatsElevesAffecteDto= resultatsServices.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre) ;
        System.out.println("FIN ResultatsEleveAffecte ");
        recapDesResultatsElevesAffecteDto= resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("FIN RecapResultatsEleveAffecte ");
        resultatsElevesNonAffecteDto = resultatsNonAffecteServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("FIN resultatsNonAffecte ");
        recapDesResultatsElevesNonAffecteDto= resultatsRecapNonAffServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
        System.out.println("FIN resultatsRecapNonAff ");
        recapResultatsElevesAffeEtNonAffDto = resultatsRecapAffEtNonAffServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
        System.out.println("FIN resultatsRecapAffEtNonAff ");
        eleveAffecteParClasseDto= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
        System.out.println("FIN eleveAffecteParClasse ");
        eleveNonAffecteParClasseDto = eleveNonAffecteParClasseServices.eleveNonAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("FIN eleveNonAffecteParClasse ");
        majorParClasseNiveauDto = majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
        System.out.println("FIN major ");
        transfertsDto= transfertsServices.transferts(idEcole) ;
        System.out.println(" FIN transfert ");
        repartitionEleveParAnNaissDto= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("FIN repartitionElevParAnNaiss ");
        boursierDto = boursiersServices.boursier(idEcole ,libelleAnnee , libelleTrimetre);
        System.out.println("FIN Boursier ");
        effApprocheNive= approcheParNiveauParGenreServices.EffApprocheNiveauGenre(idEcole ,libelleAnnee , libelleTrimetre) ;
        System.out.println("FIN approcheParNiveauParGenre ");
        effApprocheNiveauGenreDto.add(effApprocheNive)   ;


        detailsBull.setIdentiteEtatDto(identiteEtatDto);
        detailsBull.setResultatsElevesAffecteDto(resultatsElevesAffecteDto);
        detailsBull.setIntro(introLis);
        detailsBull.setRecapDesResultatsElevesAffecteDto(recapDesResultatsElevesAffecteDto);
        detailsBull.setResultatsElevesNonAffecteDto(resultatsElevesNonAffecteDto);
        detailsBull.setRecapDesResultatsElevesNonAffecteDto(recapDesResultatsElevesNonAffecteDto);
        detailsBull.setRecapResultatsElevesAffeEtNonAffDto(recapResultatsElevesAffeEtNonAffDto);
        detailsBull.setEleveAffecteParClasseDto(eleveAffecteParClasseDto);
        detailsBull.setEleveNonAffecteParClasseDto(eleveNonAffecteParClasseDto);
        detailsBull.setMajorParClasseNiveauDto(majorParClasseNiveauDto);
        detailsBull.setTransfertsDto(transfertsDto);
        detailsBull.setRepartitionEleveParAnNaissDto(repartitionEleveParAnNaissDto);
        detailsBull.setBoursierDto(boursierDto);
        detailsBull.setEffApprocheNiveauGenreDto(effApprocheNiveauGenreDto);

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
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport-Pouls-Annuelle.pdf");
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
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport Pouls-Scolaire-Annuelle.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }




    }





}
