package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
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
import java.util.*;
import java.util.stream.Collectors;

@Path("/imprimer-rapport-cio")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class CIOSpiderRessource {
    @Inject
    EntityManager em;
    @Inject
    resultatsRecapAffEtNonAffServices resultatsServices ;
    @Inject
    MoyenneParDisciplineService moyenneParDisciplineService ;
    @Inject
    resultatsRecapAffEtNonAffServicesAnnuels resultatsServicesAnnuels ;
    @Inject
    MoyenneParDisciplineServiceAnnuels moyenneParDisciplineServiceAnnuels ;


    private static String UPLOAD_DIR = "/data/";

    @GET
    @Transactional
    @Path("/pouls-rapport-cio/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{annuel}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee")
    String libelleAnnee,@PathParam("libelleTrimetre") String libelleTrimetre,@PathParam("annuel") boolean annuel) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        if((annuel)&&((libelleTrimetre.equals("Troisième Trimestre"))||(libelleTrimetre.equals("Deuxième Semestre")))){
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_AnnuelsCio.jrxml");
            spiderCIODto detailsBull= new spiderCIODto() ;
            List<RecapResultatsElevesAffeEtNonAffDto>  dspsDto = new ArrayList<>() ;
            try {
                dspsDto= resultatsServicesAnnuels.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;

                List<MoenneParDisciplineDto>  moyenParDiscipline = new ArrayList<>() ;
                moyenParDiscipline= moyenneParDisciplineServiceAnnuels.getMoyenneParDiscipline (idEcole ,libelleAnnee,libelleTrimetre) ;
                detailsBull.setDspsDto(dspsDto);
                detailsBull.setMoyenneParDisc(moyenParDiscipline);

            } catch (RuntimeException e) {
                e.printStackTrace();
            }



            System.out.println("detailsBull "+ detailsBull);

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
            Map<String, Object> map = new HashMap<>();
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
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport Pouls-Scolaire-cio.xls");
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

        }else{
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_Cio.jrxml");
            spiderCIODto detailsBull= new spiderCIODto() ;
            List<RecapResultatsElevesAffeEtNonAffDto>  dspsDto = new ArrayList<>() ;
            dspsDto= resultatsServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre) ;

            List<MoenneParDisciplineDto>  moyenParDiscipline = new ArrayList<>() ;
            moyenParDiscipline= moyenneParDisciplineService.getMoyenneParDiscipline (idEcole ,libelleAnnee,libelleTrimetre) ;

            detailsBull.setDspsDto(dspsDto);
            detailsBull.setMoyenneParDisc(moyenParDiscipline);

            System.out.println("detailsBull "+ detailsBull);

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
            Map<String, Object> map = new HashMap<>();
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
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport Pouls-Scolaire-cio.xls");
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);


        }







    }





}
