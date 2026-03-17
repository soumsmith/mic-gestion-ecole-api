package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.eclipse.microprofile.config.inject.ConfigProperty;
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
import java.sql.Connection;
import java.sql.DriverManager;
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
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;


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
            System.out.println("libelleTrimetreCCCC "+ libelleTrimetre);
           
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
            System.out.println("libelleTrimetreBBBBBBB "+ libelleTrimetre);
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_Cio.jrxml");
            /* spiderCIODto detailsBull= new spiderCIODto() ;
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

            JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource); */
            Connection connection = DriverManager.getConnection("jdbc:mysql://db:3306/ecoleviedbv2", USER, PASS);
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", "root", "root");

            Locale.setDefault(Locale.US);
            Locale franceLoc = new Locale("en", "US");

            Map<String, Object> map = new HashMap<>();
            map.put("idEcole", idEcole);
            map.put("libelleAnnee", libelleAnnee);
            map.put("libelleTrimestre", libelleTrimetre);
            map.put(JRParameter.REPORT_CONNECTION, connection);

            // Compilation des sous-rapports depuis .jrxml pour éviter NoClassDefFoundError (wrong name) avec les .jasper précompilés
            try (InputStream subRapportCio2 = getClass().getClassLoader().getResourceAsStream("etats/spider/RapportCio2.jrxml");
                 InputStream subRapportDiscipline = getClass().getClassLoader().getResourceAsStream("etats/spider/Rapport_cioParDiscipline.jrxml")) {
                if (subRapportCio2 == null || subRapportDiscipline == null) {
                    throw new IllegalStateException("Sous-rapports JRXML introuvables: RapportCio2.jrxml et/ou Rapport_cioParDiscipline.jrxml");
                }
                map.put("RapportCio2Report", JasperCompileManager.compileReport(subRapportCio2));
                map.put("Rapport_cioParDisciplineReport", JasperCompileManager.compileReport(subRapportDiscipline));
            }

            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);



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
