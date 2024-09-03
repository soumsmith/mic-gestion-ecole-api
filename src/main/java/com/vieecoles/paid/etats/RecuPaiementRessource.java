package com.vieecoles.paid.etats;


import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.souscription.SousceecoleService;
import net.sf.jasperreports.engine.*;
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

@Path("/imprimer-etats")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class RecuPaiementRessource {

    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;

     @GET
    @Path("/recu-paiement/{modele}/{numeroPaiement}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("numeroPaiement") String numeroPaiement ,
                                                 @PathParam("modele") String modele) throws Exception, JRException {

         if (modele == null || modele.isEmpty()) {
             modele = "1"; // Valeur par défaut
         }


         InputStream myInpuStream ;

         myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/Comptabilite/RECU_PAIEMENT.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poulspaid", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
         Map<String, Object> map = new HashMap<>();

         map.put("NUMERO_PAIEMENT", numeroPaiement);
         JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
         byte[] data =JasperExportManager.exportReportToPdf(report);

         HttpHeaders headers= new HttpHeaders();
         // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
         headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=reçu-de-paiement "+numeroPaiement+".pdf");
         return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

    @GET
    @Path("/liste-classe-arabe/{identifiant_classe}/{identifiant_annee}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getlisteClsse(@PathParam("identifiant_classe") long identifiant_classe,@PathParam("identifiant_annee") long identifiant_annee ) throws Exception, JRException {




        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/Comptabilite/LISTE_DE_CLASSE.jrxml");


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        Map<String, Object> map = new HashMap<>();

        map.put("CLASSE_IDENTIFANT", identifiant_classe);
        map.put("IDENTIFIANT_ANNEE", identifiant_annee);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=LISTE DE CLASSE "+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }




}
