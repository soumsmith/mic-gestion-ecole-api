package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.steph.entities.Branche;
import net.sf.jasperreports.engine.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/imprimer-trois-premiers")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class TroisPremiersSpiderRessource {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ecoleviedbv2";
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;
    Connection dbConnection = null;

    @Inject
    EntityManager em;
    @Inject
    BulletinRapportServices dpspServices ;

    @Inject
    BulletinSpiderServices bulletinSpider ;


    private static String UPLOAD_DIR = "/data/";

     @GET
    @Path("par-niveau/{idEcole}/{libellePeriode}/{libelleAnnee}/{branche_id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  troisPremierParNiveau(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("branche_id") Long branche_id) throws Exception, JRException {
         Branche myBranche = new Branche();
         myBranche = Branche.findById(branche_id);
         String niveau= null ;
         niveau= myBranche.getNiveau().getLibelle() ;

        InputStream myInpuStream ;
         myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_Trois_premiersNiveau.jrxml") ;


        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("id_ecole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("periode", libellePeriode);
         map.put("niveau", niveau);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste trois premiers par niveau"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("par-niveau-annuelle/{idEcole}/{libellePeriode}/{libelleAnnee}/{branche_id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  troisPremierParNiveauannulle(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                         @PathParam("libelleAnnee") String libelleAnnee , @PathParam("branche_id") Long branche_id) throws Exception, JRException {
        Branche myBranche = new Branche();
        myBranche = Branche.findById(branche_id);
        String niveau= null ;
        niveau= myBranche.getNiveau().getLibelle() ;

        InputStream myInpuStream ;
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_Trois_premiersNiveauAnnuels.jrxml") ;


        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("id_ecole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("periode", libellePeriode);
        map.put("niveau", niveau);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste trois premiers par niveau"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("par-classe-annuelle/{idEcole}/{libellePeriode}/{libelleAnnee}/{classeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  troisPremierParClasseannuelle(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee ,@PathParam("classeId") Long classeId ) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_Trois_premiersClasseAnnuels.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("id_ecole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("periode", libellePeriode);
        map.put("id_classe", classeId);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste trois premiers par Classe"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("par-classe/{idEcole}/{libellePeriode}/{libelleAnnee}/{classeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  troisPremierParClasse(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                         @PathParam("libelleAnnee") String libelleAnnee ,@PathParam("classeId") Long classeId ) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_Trois_premiersClasse.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("id_ecole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("periode", libellePeriode);
        map.put("id_classe", classeId);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste trois premiers par Classe"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }



}
