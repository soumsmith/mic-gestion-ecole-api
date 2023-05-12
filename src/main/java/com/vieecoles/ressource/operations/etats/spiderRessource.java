package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.projection.BulletinSelectDto;
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
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("/imprimer-rapport")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class spiderRessource {
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

    @Inject
    resultatsRecapServices resultatsRecapServices ;
    @Inject
    resultatsNonAffecteServices resultatsNonAffecteServices ;
    @Inject
    resultatsRecapNonAffServices resultatsRecapNonAffServices ;
    @Inject
    resultatsRecapAffEtNonAffServices resultatsRecapAffEtNonAffServices ;




    private static String UPLOAD_DIR = "/data/";

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/convertDate")
    public String  convert() throws Exception, JRException {

        return "DFS" ;

    }



    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recape-resultats-affecte-par-niveau/{idEcole}")
    public List<RecapDesResultatsElevesAffecteDto>  recap(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<RecapDesResultatsElevesAffecteDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole) ;
        return detailsBull ;

    }


    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Eleve-affecte-par-classe/{idEcole}")
    public List<eleveAffecteParClasseDto>  eleveAffecteParClasse(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<eleveAffecteParClasseDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Eleve-non-affecte-par-classe/{idEcole}")
    public List<eleveNonAffecteParClasseDto>  eleveNonAffecteParClasse(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<eleveNonAffecteParClasseDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= eleveNonAffecteParClasseServices.eleveNonAffecteParClasse(idEcole) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Major/{idEcole}")
    public List<MajorParClasseNiveauDto>  majorParClasseNiveau(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<MajorParClasseNiveauDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= majorServices.MajorParNiveauClasse(idEcole) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Transfert/{idEcole}")
    public List<TransfertsDto>  transferts(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<TransfertsDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= transfertsServices.transferts(idEcole) ;
        return detailsBull ;

    }
    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/resultat_eleve_affecte_par_classe_par_niveau/{idEcole}")
    public List<ResultatsElevesAffecteDto>  detailBulletinInfos(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<ResultatsElevesAffecteDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsServices.CalculResultatsEleveAffecte(idEcole) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/boursiers-demi-boursiers/{idEcole}")
    public List<BoursierDto>  boursier(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<BoursierDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= boursiersServices.boursier(idEcole);
        return detailsBull ;

    }


    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/EffApprocheNiveauGenre/{idEcole}")
    public EffApprocheNiveauGenreDto effApprocheNiveauGenreDto(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        EffApprocheNiveauGenreDto detailsBull = new EffApprocheNiveauGenreDto()  ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= approcheParNiveauParGenreServices.EffApprocheNiveauGenre(idEcole) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recapitulatif-par-classe-pedagogique-Affcete-non-affecte/{idEcole}")
    public RecapitulatifClassePedagoAffectNonAffect recapAffecNonAffecte(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        RecapitulatifClassePedagoAffectNonAffect detailsBull = new RecapitulatifClassePedagoAffectNonAffect()  ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= recapClassePedaAffNonAffecServices.RecapClassePedagoAffecNonAffect(idEcole) ;
        return detailsBull ;

    }


    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/repartition-par-annee-naissance/{idEcole}")
    public List<RepartitionEleveParAnNaissDto>  repartiParAnn(@PathParam("idEcole") Long idEcole) throws Exception, JRException {
        List<RepartitionEleveParAnNaissDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole) ;
        return detailsBull ;

    }



    @GET
    @Transactional
    @Path("/pouls-rapport/{idEcole}/{type}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type) throws Exception, JRException {
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


        identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
        resultatsElevesAffecteDto= resultatsServices.CalculResultatsEleveAffecte(idEcole) ;
        System.out.println("FIN ResultatsEleveAffecte ");
        recapDesResultatsElevesAffecteDto= resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole);
        System.out.println("FIN RecapResultatsEleveAffecte ");
        resultatsElevesNonAffecteDto = resultatsNonAffecteServices.CalculResultatsEleveAffecte(idEcole);
        System.out.println("FIN resultatsNonAffecte ");
        recapDesResultatsElevesNonAffecteDto= resultatsRecapNonAffServices.RecapCalculResultatsEleveAffecte(idEcole) ;
        System.out.println("FIN resultatsRecapNonAff ");
        recapResultatsElevesAffeEtNonAffDto = resultatsRecapAffEtNonAffServices.RecapCalculResultatsEleveAffecte(idEcole) ;
        System.out.println("FIN resultatsRecapAffEtNonAff ");
        eleveAffecteParClasseDto= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole) ;
        System.out.println("FIN eleveAffecteParClasse ");
        eleveNonAffecteParClasseDto = eleveNonAffecteParClasseServices.eleveNonAffecteParClasse(idEcole);
        System.out.println("FIN eleveNonAffecteParClasse ");
        majorParClasseNiveauDto = majorServices.MajorParNiveauClasse(idEcole) ;
        System.out.println("FIN major ");
        transfertsDto= transfertsServices.transferts(idEcole) ;
        System.out.println(" FIN transfert ");
        repartitionEleveParAnNaissDto= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole);
        System.out.println("FIN repartitionElevParAnNaiss ");
        boursierDto = boursiersServices.boursier(idEcole);
        System.out.println("FIN Boursier ");
        effApprocheNive= approcheParNiveauParGenreServices.EffApprocheNiveauGenre(idEcole) ;
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
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=RapportPouls.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }




    }





}
