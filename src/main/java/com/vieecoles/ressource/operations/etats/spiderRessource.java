package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.projection.BulletinSelectDto;
import com.vieecoles.services.etats.*;
import com.vieecoles.steph.entities.Ecole;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    resultatsServicesAnnuels resultatsServicesAnnuels ;

    @Inject
    EtatNominatifEnseignantServices etatNominatifEnseignantServices ;

    @Inject
    RepartitionElevParAnNaissServices repartitionElevParAnNaissServices ;

    @Inject
    RecapClassePedaAffNonAffecServices recapClassePedaAffNonAffecServices ;

    @Inject
    ApprocheParNiveauParGenreServices approcheParNiveauParGenreServices ;

    @Inject
    BoursiersServices boursiersServices ;
    @Inject
    EffectifParLangueServices effectifParLangueServices ;
    @Inject
    TransfertsServices transfertsServices ;
    @Inject
    MajorParClasseNiveauServices majorServices ;
    @Inject
    MajorParClasseNiveauServicesAnnuels majorServicesAnnuels ;

    @Inject
    EleveNonAffecteParClasseServices eleveNonAffecteParClasseServices ;

    @Inject
    EleveAffecteParClasseServices eleveAffecteParClasseServices ;

    @Inject
    resultatsRecapServices resultatsRecapServices ;
    @Inject
    resultatsRecapServicesAnnuels resultatsRecapServicesAnnuels ;
    @Inject
    resultatsNonAffecteServices resultatsNonAffecteServices ;
    @Inject
    resultatsNonAffecteServicesAnnuels resultatsNonAffecteServicesAnnuels ;
    @Inject
    resultatsRecapNonAffServices resultatsRecapNonAffServices ;
    @Inject
    resultatsRecapNonAffServicesAnnuels resultatsRecapNonAffServicesAnnuels ;
    @Inject
    resultatsRecapAffEtNonAffServices resultatsRecapAffEtNonAffServices ;
    @Inject
    resultatsRecapAffEtNonAffServicesAnnuels resultatsRecapAffEtNonAffServicesAnnuels ;




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
    @Path("/recape-resultats-affecte-par-niveau/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<RecapDesResultatsElevesAffecteDto>  recap(@PathParam("idEcole") Long idEcole ,
                                                          @PathParam("libelleAnnee") String libelleAnnee,
                                                          @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<RecapDesResultatsElevesAffecteDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }


    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Eleve-affecte-par-classe/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<eleveAffecteParClasseDto>  eleveAffecteParClasse(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,
                                                                 @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<eleveAffecteParClasseDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Eleve-non-affecte-par-classe/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<eleveNonAffecteParClasseDto>  eleveNonAffecteParClasse(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,
                                                                       @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<eleveNonAffecteParClasseDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= eleveNonAffecteParClasseServices.eleveNonAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Major/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<MajorParClasseNiveauDto>  majorParClasseNiveau(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,
                                                               @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<MajorParClasseNiveauDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
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
    @Path("/resultat_eleve_affecte_par_classe_par_niveau/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<ResultatsElevesAffecteDto>  detailBulletinInfos(@PathParam("idEcole") Long idEcole ,
                                                                @PathParam("libelleAnnee") String libelleAnnee,
                                                                @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<ResultatsElevesAffecteDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsServices.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/boursiers-demi-boursiers/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<BoursierDto>  boursier(@PathParam("idEcole") Long idEcole  ,
                                       @PathParam("libelleAnnee") String libelleAnnee,
                                       @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<BoursierDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= boursiersServices.boursier(idEcole ,libelleAnnee,libelleTrimetre);
        return detailsBull ;

    }


    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/EffApprocheNiveauGenre/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public EffApprocheNiveauGenreDto effApprocheNiveauGenreDto(@PathParam("idEcole") Long idEcole ,
                                                               @PathParam("libelleAnnee") String libelleAnnee,
                                                               @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        EffApprocheNiveauGenreDto detailsBull = new EffApprocheNiveauGenreDto()  ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= approcheParNiveauParGenreServices.EffApprocheNiveauGenre(idEcole ,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recapitulatif-par-classe-pedagogique-Affcete-non-affecte/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public RecapitulatifClassePedagoAffectNonAffect recapAffecNonAffecte(@PathParam("idEcole") Long idEcole ,
                                                                         @PathParam("libelleAnnee") String libelleAnnee,
                                                                         @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        RecapitulatifClassePedagoAffectNonAffect detailsBull = new RecapitulatifClassePedagoAffectNonAffect()  ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= recapClassePedaAffNonAffecServices.RecapClassePedagoAffecNonAffect(idEcole ,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }


    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/repartition-par-annee-naissance/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    public List<RepartitionEleveParAnNaissDto>  repartiParAnn(@PathParam("idEcole") Long idEcole ,
                                                              @PathParam("libelleAnnee") String libelleAnnee,
                                                              @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        List<RepartitionEleveParAnNaissDto>  detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre) ;
        return detailsBull ;

    }



    @GET
    //@Transactional
    @Path("/pouls-rapport/{idEcole}/{type}/{libelleAnnee}/{libelleTrimetre}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("type") String type ,
                                                 @PathParam("libelleAnnee") String libelleAnnee,
                                                 @PathParam("libelleTrimetre") String libelleTrimetre,@PathParam("anneeId") Long anneeId) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/


        EmptyDto myIntro = new EmptyDto();
        myIntro.setIntro("INTRODUCTION");
         List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
        List<ResultatsElevesAffecteDto> resultatsElevesAffecteDto = new ArrayList<>() ;
        List<ResultatsElevesAffecteDto> resultatsElevesAffecteAnnuelsDto = new ArrayList<>() ;
         List<RecapDesResultatsElevesAffecteDto> recapDesResultatsElevesAffecteDto= new ArrayList<>();
        List<RecapDesResultatsElevesAffecteDto> recapDesResultatsElevesAffecteAnnuelsDto= new ArrayList<>();
          List<ResultatsElevesNonAffecteDto> resultatsElevesNonAffecteDto = new ArrayList<>();
        List<ResultatsElevesNonAffecteDto> resultatsElevesNonAffecteAnnuelsDto = new ArrayList<>();
          List<RecapDesResultatsElevesNonAffecteDto> recapDesResultatsElevesNonAffecteDto = new ArrayList<>();
        List<RecapDesResultatsElevesNonAffecteDto> recapDesResultatsElevesNonAffecteAnnuelsDto = new ArrayList<>();
          List<RecapResultatsElevesAffeEtNonAffDto> recapResultatsElevesAffeEtNonAffDto = new ArrayList<>();
        List<RecapResultatsElevesAffeEtNonAffDto> recapResultatsElevesAffeEtNonAffAnnuelsDto = new ArrayList<>();
          List<eleveAffecteParClasseDto> eleveAffecteParClasseDto = new ArrayList<>();
          List<eleveNonAffecteParClasseDto> eleveNonAffecteParClasseDto = new ArrayList<>();
          List<MajorParClasseNiveauDto> majorParClasseNiveauDto = new ArrayList<>();
        List<MajorParClasseNiveauDto> majorParClasseNiveauAnnuelsDto = new ArrayList<>();
          List<TransfertsDto>transfertsDto= new ArrayList<>() ;
          List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>() ;
          List<BoursierDto> boursierDto = new ArrayList<>() ;
          List<EffApprocheNiveauGenreDto> effApprocheNiveauGenreDto= new ArrayList<>()  ;
        EffApprocheNiveauGenreDto effApprocheNive = new EffApprocheNiveauGenreDto() ;
        EffectifElevLangueVivante2Dto m = new EffectifElevLangueVivante2Dto() ;
          List<EffectifElevLangueVivante2Dto> effectifElevLangueVivante2Dto = new ArrayList<>() ;

           List<EtatNominatifEnseignatDto> etatNominatifEnseignatDto = new ArrayList<>() ;


       List<EmptyDto> introLis= new ArrayList<>() ;
        introLis.add(myIntro) ;
         spiderDto detailsBull= new spiderDto() ;
         spiderAnnuelsDto detailsAnnuels= new spiderAnnuelsDto() ;
         ecole myScole= new ecole() ;
        myScole= ecole.findById(idEcole) ;

        if(libelleTrimetre.equals("Troisième Trimestre")||libelleTrimetre.equals("Deuxième Semestre")) {
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Drena3/Spider_BookAnnuels.jrxml");
            identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
            resultatsElevesAffecteDto= resultatsServices.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre) ;
            resultatsElevesAffecteAnnuelsDto= resultatsServicesAnnuels.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre) ;
            System.out.println("FIN ResultatsEleveAffecte ");
            recapDesResultatsElevesAffecteDto= resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
            recapDesResultatsElevesAffecteAnnuelsDto= resultatsRecapServicesAnnuels.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("FIN RecapResultatsEleveAffecte ");
            resultatsElevesNonAffecteDto = resultatsNonAffecteServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
            resultatsElevesNonAffecteAnnuelsDto = resultatsNonAffecteServicesAnnuels.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("FIN resultatsNonAffecte ");
            recapDesResultatsElevesNonAffecteDto= resultatsRecapNonAffServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
            recapDesResultatsElevesNonAffecteAnnuelsDto= resultatsRecapNonAffServicesAnnuels.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
            System.out.println("FIN resultatsRecapNonAff ");
            recapResultatsElevesAffeEtNonAffDto = resultatsRecapAffEtNonAffServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
            recapResultatsElevesAffeEtNonAffAnnuelsDto = resultatsRecapAffEtNonAffServicesAnnuels.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;
            System.out.println("FIN resultatsRecapAffEtNonAff ");
            eleveAffecteParClasseDto= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
            System.out.println("FIN eleveAffecteParClasse ");
            eleveNonAffecteParClasseDto = eleveNonAffecteParClasseServices.eleveNonAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("FIN eleveNonAffecteParClasse ");
            majorParClasseNiveauDto = majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimetre) ;
            majorParClasseNiveauAnnuelsDto = majorServicesAnnuels.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimetre) ;

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
            m = effectifParLangueServices.getLangueVivante(idEcole,anneeId);
            etatNominatifEnseignatDto = etatNominatifEnseignantServices.infosEtatNominEnseignant(idEcole ,anneeId) ;
            effectifElevLangueVivante2Dto.add(m);

            detailsAnnuels.setIdentiteEtatDto(identiteEtatDto);
            detailsAnnuels.setResultatsElevesAffecteDto(resultatsElevesAffecteDto);
            detailsAnnuels.setResultatsElevesAffecteAnnuelsDto(resultatsElevesAffecteAnnuelsDto);

            detailsAnnuels.setIntro(introLis);
            detailsAnnuels.setRecapDesResultatsElevesAffecteDto(recapDesResultatsElevesAffecteDto);
            detailsAnnuels.setRecapDesResultatsElevesAffecteAnnuelsDto(recapDesResultatsElevesAffecteAnnuelsDto);

            detailsAnnuels.setResultatsElevesNonAffecteDto(resultatsElevesNonAffecteDto);
            detailsAnnuels.setResultatsElevesNonAffecteAnnuelsDto(resultatsElevesNonAffecteAnnuelsDto);

            detailsAnnuels.setRecapDesResultatsElevesNonAffecteDto(recapDesResultatsElevesNonAffecteDto);
            detailsAnnuels.setRecapDesResultatsElevesNonAffecteAnnuelsDto(recapDesResultatsElevesNonAffecteAnnuelsDto);

            detailsAnnuels.setRecapResultatsElevesAffeEtNonAffDto(recapResultatsElevesAffeEtNonAffDto);
            detailsAnnuels.setRecapResultatsElevesAffeEtNonAffAnnuelsDto(recapResultatsElevesAffeEtNonAffAnnuelsDto);


            detailsAnnuels.setEleveAffecteParClasseDto(eleveAffecteParClasseDto);
            detailsAnnuels.setEleveNonAffecteParClasseDto(eleveNonAffecteParClasseDto);

            detailsAnnuels.setMajorParClasseNiveauDto(majorParClasseNiveauDto);
            detailsAnnuels.setMajorParClasseNiveauAnnuelsDto(majorParClasseNiveauAnnuelsDto);

            detailsAnnuels.setTransfertsDto(transfertsDto);
            detailsAnnuels.setRepartitionEleveParAnNaissDto(repartitionEleveParAnNaissDto);
            detailsAnnuels.setBoursierDto(boursierDto);
            detailsAnnuels.setEffApprocheNiveauGenreDto(effApprocheNiveauGenreDto);
            detailsAnnuels.setEffectifElevLangueVivante2Dto(effectifElevLangueVivante2Dto);
            detailsAnnuels.setEtatNominatifEnseignatDto(etatNominatifEnseignatDto);
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
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsAnnuels)) ;
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


        } else {
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book.jrxml");
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
            m = effectifParLangueServices.getLangueVivante(idEcole,anneeId);
            etatNominatifEnseignatDto = etatNominatifEnseignantServices.infosEtatNominEnseignant(idEcole ,anneeId) ;
            effectifElevLangueVivante2Dto.add(m);


            // Le reste de votre code après l'exécution parallèle
            detailsBull.setIdentiteEtatDto(identiteEtatDto);
            detailsBull.setResultatsElevesAffecteDto(resultatsElevesAffecteDto);

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
            detailsBull.setEffectifElevLangueVivante2Dto(effectifElevLangueVivante2Dto);
            detailsBull.setEtatNominatifEnseignatDto(etatNominatifEnseignatDto);
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


@Transactional
    public  void callExecutor(Long idEcole ,  String libelleAnnee, String libelleTrimetre ,Long anneeId)
    {
        EmptyDto myIntro = new EmptyDto();
        myIntro.setIntro("INTRODUCTION");
        final List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
        final  List<ResultatsElevesAffecteDto> resultatsElevesAffecteDto = new ArrayList<>() ;
        final  List<RecapDesResultatsElevesAffecteDto> recapDesResultatsElevesAffecteDto= new ArrayList<>();
        final  List<ResultatsElevesNonAffecteDto> resultatsElevesNonAffecteDto = new ArrayList<>();
        final  List<RecapDesResultatsElevesNonAffecteDto> recapDesResultatsElevesNonAffecteDto = new ArrayList<>();
        final  List<RecapResultatsElevesAffeEtNonAffDto> recapResultatsElevesAffeEtNonAffDto = new ArrayList<>();
        final  List<eleveAffecteParClasseDto> eleveAffecteParClasseDto = new ArrayList<>();
        final  List<eleveNonAffecteParClasseDto> eleveNonAffecteParClasseDto = new ArrayList<>();
        final  List<MajorParClasseNiveauDto> majorParClasseNiveauDto = new ArrayList<>();
        final  List<TransfertsDto>transfertsDto= new ArrayList<>() ;
        final  List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>() ;
        final  List<BoursierDto> boursierDto = new ArrayList<>() ;
        final  List<EffApprocheNiveauGenreDto> effApprocheNiveauGenreDto= new ArrayList<>()  ;

        final  List<EffectifElevLangueVivante2Dto> effectifElevLangueVivante2Dto = new ArrayList<>() ;

        final   List<EtatNominatifEnseignatDto> etatNominatifEnseignatDto = new ArrayList<>() ;

        ExecutorService executorService = Executors.newFixedThreadPool(10); // Nombre de threads à utiliser
        List<Future<?>> futures = new ArrayList<>();
        try {

            futures.add ( executorService.submit(() -> {
                identiteEtatDto.add(new IdentiteEtatDto(identiteEtatService.getIdentiteDto(idEcole)));
                // identiteEtatDto = identiteEtatService.getIdentiteDto(idEcole);
                System.out.println("FIN IdentiteEtatDto");
            }));

            futures.add(
                    executorService.submit(() -> {
                        resultatsElevesAffecteDto.add(new ResultatsElevesAffecteDto(resultatsServices.CalculResultatsEleveAffecte(idEcole, libelleAnnee, libelleTrimetre))) ;

                        System.out.println("FIN ResultatsEleveAffecte");
                    })) ;
            futures.add(executorService.submit(() -> {
                recapDesResultatsElevesAffecteDto.add(new RecapDesResultatsElevesAffecteDto(resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre))) ;
                System.out.println("FIN RecapResultatsEleveAffecte ");

            })) ;
            futures.add ( executorService.submit(() -> {
                resultatsElevesNonAffecteDto.add (new ResultatsElevesNonAffecteDto(resultatsNonAffecteServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre))) ;
                System.out.println("FIN resultatsNonAffecte ");
            }));
            futures.add (executorService.submit(() -> {
                recapDesResultatsElevesNonAffecteDto.add(new RecapDesResultatsElevesNonAffecteDto(resultatsRecapNonAffServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre)))  ;
                System.out.println("FIN resultatsRecapNonAff ");
            }));
            futures.add ( executorService.submit(() -> {
                recapResultatsElevesAffeEtNonAffDto.add(new RecapResultatsElevesAffeEtNonAffDto(resultatsRecapAffEtNonAffServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre)))  ;
                System.out.println("FIN resultatsRecapAffEtNonAff ");
            }));
            futures.add ( executorService.submit(() -> {
                eleveAffecteParClasseDto.add (new eleveAffecteParClasseDto(eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre)));
                System.out.println("FIN eleveAffecteParClasse ");
            })) ;
            futures.add ( executorService.submit(() -> {
                eleveNonAffecteParClasseDto.add (new eleveNonAffecteParClasseDto(eleveNonAffecteParClasseServices.eleveNonAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre)));
                System.out.println("FIN eleveNonAffecteParClasse ");
            }));
            futures.add(
                    executorService.submit(() -> {
                        majorParClasseNiveauDto.add(new MajorParClasseNiveauDto(majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimetre))) ;
                        System.out.println("FIN major ");
                    })
            );
            futures.add ( executorService.submit(() -> {

                transfertsDto.add (new TransfertsDto(transfertsServices.transferts(idEcole)));
                System.out.println(" FIN transfert ");
            }));

            futures.add (
                    executorService.submit(() -> {
                        repartitionEleveParAnNaissDto.add (new RepartitionEleveParAnNaissDto(repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre))) ;
                        System.out.println("FIN repartitionElevParAnNaiss ");
                    })
            ) ;
            futures.add (executorService.submit(() -> {
                boursierDto.add (new BoursierDto(boursiersServices.boursier(idEcole ,libelleAnnee , libelleTrimetre)));
                System.out.println("FIN Boursier ");

            })) ;
            futures.add (executorService.submit(() -> {
                EffApprocheNiveauGenreDto effApprocheNive = new EffApprocheNiveauGenreDto() ;
                effApprocheNive= approcheParNiveauParGenreServices.EffApprocheNiveauGenre(idEcole ,libelleAnnee , libelleTrimetre) ;
                System.out.println("FIN approcheParNiveauParGenre ");
                effApprocheNiveauGenreDto.add(effApprocheNive)   ;

            }));
            futures.add (executorService.submit(() -> {
                EffectifElevLangueVivante2Dto m = new EffectifElevLangueVivante2Dto() ;
                m = effectifParLangueServices.getLangueVivante(idEcole,anneeId);
                etatNominatifEnseignatDto.add (new EtatNominatifEnseignatDto(etatNominatifEnseignantServices.infosEtatNominEnseignant(idEcole ,anneeId))) ;
                effectifElevLangueVivante2Dto.add(m);

            }));
        } finally {
            executorService.shutdown();
        }

        // Attendre la fin de toutes les tâches après le shutdown
        for (Future<?> future : futures) {
            try {
                future.get(); // Attend la fin de l'exécution de chaque tâche
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
