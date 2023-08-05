package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
import com.vieecoles.dto.spiderDspsDto;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.etats.DpspServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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
import java.sql.Connection;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.util.*;

@Path("/imprimer-bulletin-list")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class BulletinSpiderRessource {
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
    @Transactional
    @Path("/spider-bulletin-infos/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<InfosPersoBulletins> getDtoRapportInformation(@PathParam("idEcole") Long idEcole , @PathParam("libellePeriode") String libellePeriode ,
                                                   @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse) throws Exception, JRException {

   return      bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;



        }
    @GET

    @Path("/spider-bulletin/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;
        //connect() ;

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("classe", libelleClasse);
        map.put("idEcole", idEcole);
        map.put("libelleAnnee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("/infos-spider-bulletin/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<InfosPersoBulletins>  getInfosSpider(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse) throws Exception, JRException {


       return bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;
      }
    @GET
    @Path("/spider-bulletin-matricule/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}/{matricule}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse ,@PathParam("matricule") String matricule) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


       bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
       // compileReport.setProperty("REPORT_CONNECTION", "jdbc:mysql://127.0.0.1:3308/ecolevie_dbProd?user=root&password=root");
     //   compileReport.setProperty("REPORT_CONNECTION", String.valueOf(connection));


        Map<String, Object> map = new HashMap<>();
         map.put("idEcole", idEcole);
         map.put("classe", "");
        map.put("libelleAnnee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        map.put("matricule",matricule);
      JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);

        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider-matricule.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }





    @Transactional

    public  void connect(){
        try {/*  ww  w . j a  va2s  .co m*/

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);


        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
    }





}
