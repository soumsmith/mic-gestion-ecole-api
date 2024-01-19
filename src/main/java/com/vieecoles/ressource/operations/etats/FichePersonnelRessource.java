package com.vieecoles.ressource.operations.etats;


import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.souscription.SousceecoleService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@Path("/imprimer-perspnnel")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class FichePersonnelRessource {
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
    SousceecoleService sousceecoleService ;
    @Inject
    EntityManager em;
    @Inject
    BulletinRapportServices dpspServices ;

    @Inject
    BulletinSpiderServices bulletinSpider ;


    private static String UPLOAD_DIR = "/data/";

     @GET
    @Path("/personnel-enseignant/{idAnnee}/{IdEcole}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idAnnee") Long idAnnee , @PathParam("IdEcole") Long IdEcole ) throws Exception, JRException {
        InputStream myInpuStream ;

         myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste du personnel enseignant.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
         Map<String, Object> map = new HashMap<>();

         map.put("idAnnee", idAnnee);
         map.put("IdEcole", IdEcole);
         JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
         byte[] data =JasperExportManager.exportReportToPdf(report);

         HttpHeaders headers= new HttpHeaders();
         // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
         headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=personnel-enseignant"+".pdf");
         return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

    @GET
    @Path("/personnel-enseignant-xls/{idAnnee}/{IdEcole}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport4(@PathParam("idAnnee") Long idAnnee , @PathParam("IdEcole") Long IdEcole ) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste du personnel enseignant.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        Map<String, Object> map = new HashMap<>();

        map.put("idAnnee", idAnnee);
        map.put("IdEcole", IdEcole);
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=personnel-enseignant.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET

    @Path("/personnel-administratif/{IdEcole}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport2(@PathParam("IdEcole") Long IdEcole ) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_du_personnel_administratif.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("IdEcole", IdEcole);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=personnel-administratif"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

    @GET
    @Path("/personnel-administratif-xls/{IdEcole}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport3(@PathParam("IdEcole") Long IdEcole) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_du_personnel_administratif.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);


        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("IdEcole", IdEcole);
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=personnel-administratif.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

    @GET
    @Path("/eleve-par-classe/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport5(@QueryParam("IdEcole") Long IdEcole , @QueryParam("anneeId") Long anneeId
    , @QueryParam("classe") String classe , @QueryParam("branche") Long branche, @QueryParam("redoublant") String redoublant,
                                                  @QueryParam("genre") String genre, @QueryParam("langueVivante"
    ) String langueVivante , @QueryParam("affecte") String affecte ,@QueryParam("boursier") String boursier ,@QueryParam("photo") Boolean photo) throws Exception, JRException {
        InputStream myInpuStream ;

        if(photo){
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_eleve_par_classe_photos.jrxml");
        } else {
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_eleve_par_classe.jrxml");
        }


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("IdEcole", IdEcole);
        map.put("idAnnee", anneeId);
        map.put("classe", classe);
        map.put("branche", branche);
        map.put("redoublant", redoublant);
        map.put("genre", genre);
        map.put("langueVivante", langueVivante);
        map.put("affecte", affecte);
        map.put("boursier", boursier);


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste des élèves par classe"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }
    @GET
    @Path("/eleve-par-classe-xls")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport6(@QueryParam("IdEcole") Long IdEcole ,@QueryParam("anneeId") Long anneeId
            ,@QueryParam("classe") String classe ,@QueryParam("branche") Long branche,@QueryParam("redoublant") String redoublant,
                                                  @QueryParam("genre") String genre,@QueryParam("langueVivante") String langueVivante,
                                                  @QueryParam("affecte") String affecte,@QueryParam("boursier") String boursier,@QueryParam("photo") Boolean photo) throws Exception, JRException {
        InputStream myInpuStream ;

        if(photo){
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_eleve_par_classe_photos.jrxml");
        } else {
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_eleve_par_classe.jrxml");
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);


        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("IdEcole", IdEcole);
        map.put("idAnnee", anneeId);
        map.put("classe", classe);
        map.put("branche", branche);
        map.put("redoublant", redoublant);
        map.put("genre", genre);
        map.put("langueVivante", langueVivante);
        map.put("affecte", affecte);
        map.put("boursier", boursier);
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste des élèves par classe.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

    @GET
    @Path("/certificat-de-travail/{IdEcole}/{idSouscrip}/{signataire}/{fonction}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport7(@PathParam("IdEcole") Long IdEcole ,@PathParam("idSouscrip") Long idSouscrip
            ,@PathParam("signataire") String signataire ,@PathParam("fonction") String fonction) throws Exception, JRException {


        parametre mpara = new parametre();
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(IdEcole);
        mpara = parametre.findById(1L) ;

        byte[] imagebytes2 = myEcole.getLogoBlob() ;
        byte[] imagebytes3 = mpara.getImage() ;
        byte[] imagebytes4 = myEcole.getFiligramme() ;
        BufferedImage photo_eleve = null ;
        BufferedImage logo= null ,amoirie= null,bg= null;
        String libelleEcole , adresse, telephone , code,statut ;
        libelleEcole= myEcole.getEcoleclibelle() ;
        adresse= myEcole.getEcole_adresse() ;
        telephone= myEcole.getEcole_telephone() ;
        code = myEcole.getEcolecode() ;
        statut = myEcole.getEcole_statut() ;

        if(imagebytes2!=null){
            logo= ImageIO.read(new ByteArrayInputStream(imagebytes2));
        }

        if(imagebytes3!=null){
            amoirie = ImageIO.read(new ByteArrayInputStream(imagebytes3));
        }

        if(imagebytes4!=null){
            bg = ImageIO.read(new ByteArrayInputStream(imagebytes4)) ;
        }

        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/CertificatDeTravailN.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);


        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("logo",logo);
        map.put("amoirie",amoirie);
        map.put("bg",bg);
        map.put("libelleEcole",libelleEcole);
        map.put("adresse",adresse);
        map.put("telephone",telephone);
        map.put("code",code);
        map.put("statut",statut);
        map.put("idEcole", IdEcole);
        map.put("idSouscrip", idSouscrip);
        map.put("signataire", signataire);
        map.put("fonction", fonction);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=certificat-de-travail.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }


    @GET
    @Path("/eleve-par-moyenne/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportParMoyenne(@QueryParam("IdEcole") Long IdEcole , @QueryParam("anneeId") Long anneeId
            , @QueryParam("classe") String classe , @QueryParam("branche") Long branche, @QueryParam("moyenne") Double moyenne,
                                                  @QueryParam("genre") String genre,@QueryParam("affecte") String affecte
                                                           ) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_eleve_des_admis.jrxml");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("IdEcole", IdEcole);
        map.put("idAnnee", anneeId);
        map.put("classe", classe);
        map.put("branche", branche);
        map.put("moyenne", moyenne);
        map.put("genre", genre);
        map.put("affecte", affecte);


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste_eleve_des_admis"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }


    @GET
    @Path("/eleve-par-moyenne-xls")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportMoyenne6(@QueryParam("IdEcole") Long IdEcole ,@QueryParam("anneeId") Long anneeId
            ,@QueryParam("classe") String classe ,@QueryParam("branche") Long branche,@QueryParam("moyenne") Double moyenne,
                                                  @QueryParam("genre") String genre,@QueryParam("langueVivante") String langueVivante,
                                                         @QueryParam("affecte") String affecte) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_eleve_des_admis.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);


        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("IdEcole", IdEcole);
        map.put("idAnnee", anneeId);
        map.put("classe", classe);
        map.put("branche", branche);
        map.put("moyenne", moyenne);
        map.put("genre", genre);
        map.put("affecte", affecte);
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste_eleve_des_admis.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }



}
