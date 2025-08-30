package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import net.sf.jasperreports.engine.*;
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
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/imprimer-major-list")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class MajorSpiderRessource {
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

    @Path("/{idEcole}/{libellePeriode}/{libelleAnnee}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee ) throws Exception, JRException {
        InputStream myInpuStream ;

         myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Liste_des_majors_photos.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://db:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("id_ecole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("periode", libellePeriode);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Liste des Majors"+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }






}
