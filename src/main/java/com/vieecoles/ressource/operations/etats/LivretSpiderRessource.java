package com.vieecoles.ressource.operations.etats;


import com.vieecoles.InforPersonLivretScolaire;
import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.etats.LivretSpiderServices;
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

@Path("/imprimer-livret-list")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class LivretSpiderRessource {
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
    LivretSpiderServices bulletinSpider ;


    private static String UPLOAD_DIR = "/data/";

    @GET

    @Path("/spider-livret-infos/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}")
    @Produces(MediaType.APPLICATION_JSON)
    public void  getDtoInfosRapport(@PathParam("idEcole") Long idEcole , @PathParam("libellePeriode") String libellePeriode ,
                                                           @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse) throws Exception, JRException {
        InputStream myInpuStream ;

        if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


         bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;

    }

     @GET

    @Path("/spider-livret/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{distinct}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse,
                                                @PathParam("positionLogo") boolean positionLogo ,
                                                 @PathParam("filigranne") boolean filigranne, @PathParam("infoAmoirie") boolean infoAmoiri,
                                                 @PathParam("pivoter") boolean pivoter ,
                                                 @PathParam("distinct") boolean distinct ) throws Exception, JRException {
        InputStream myInpuStream ;

         myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/LivretScolaire/callLivretScolaire.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        //bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
         String infos= null ;
         String pdistinct= null ;
         String plogoPosi= null ;
         String psetBg= null ;
         if(distinct){
             pdistinct="1";
         } else{
             pdistinct="0";
         }

         if(infoAmoiri){
             infos="1";
         } else{
             infos="0";
         }
         if(positionLogo){
             plogoPosi="1";
         } else{
             plogoPosi="0";
         }
         if(filigranne){
             psetBg="1";
         } else{
             psetBg="0";
         }

        map.put("classe", libelleClasse);
        map.put("idEcole", idEcole);
        map.put("libelleAnnee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
         map.put("infosAmoirie", infos);
         map.put("distinctin", pdistinct);
         map.put("positionLogo", plogoPosi);
         map.put("setBg", psetBg);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();

        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider"+libelleClasse+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("/spider-livret-matricule/{idEcole}/{libellePeriode}/{libelleAnnee}/{libelleClasse}/{matricule}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{distinct}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("libelleClasse") String libelleClasse ,@PathParam("matricule") String matricule,
                                                 @PathParam("positionLogo") boolean positionLogo ,
                                                 @PathParam("filigranne") boolean filigranne,
                                                 @PathParam("infoAmoirie") boolean infoAmoiri,
                                                 @PathParam("pivoter") boolean pivoter ,
                                                 @PathParam("distinct") boolean distinct) throws Exception, JRException {
        InputStream myInpuStream ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/LivretScolaire/Livret_scolaireSpider.jrxml");

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


      // bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);


        Map<String, Object> map = new HashMap<>();
        String infos= null ;
        String pdistinct= null ;
        String plogoPosi= null ;
        String psetBg= null ;
        if(distinct){
            pdistinct="1";
        } else{
            pdistinct="0";
        }

        if(infoAmoiri){
            infos="1";
        } else{
            infos="0";
        }
        if(positionLogo){
            plogoPosi="1";
        } else{
            plogoPosi="0";
        }
        if(filigranne){
            psetBg="1";
        } else{
            psetBg="0";
        }
         map.put("idEcole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        map.put("matricule",matricule);
        map.put("infosAmoirie", infos);
        map.put("distinctin", pdistinct);
        map.put("positionLogo", plogoPosi);
        map.put("setBg", psetBg);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);

        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider-"+matricule+".pdf");
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
