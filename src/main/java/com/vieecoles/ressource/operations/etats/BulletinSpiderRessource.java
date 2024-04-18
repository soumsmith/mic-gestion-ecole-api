package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
import com.vieecoles.dto.spiderDspsDto;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.entities.parametre;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderMatriculeServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.etats.DpspServices;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Classe;
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
import javax.persistence.NoResultException;
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
    BulletinSpiderMatriculeServices bulletinSpiderMatriculeServices ;

    @Inject
    EntityManager em;
    @Inject
    BulletinRapportServices dpspServices ;

    @Inject
    BulletinSpiderServices bulletinSpider ;
    @Inject
    SousceecoleService sousceecoleService ;


    private static String UPLOAD_DIR = "/data/";

     @GET
    @Path("/spider-bulletin/{idEcole}/{libellePeriode}/{libelleAnnee}/{idClasse}/{compress}/{niveauEnseign}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{modelePoincarre}/{distinct}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("idClasse") Long libelleClasse ,@PathParam("compress") Boolean compress ,
                                                 @PathParam("niveauEnseign") Long niveauEnseign ,@PathParam("positionLogo") boolean positionLogo ,@PathParam("filigranne") boolean filigranne, @PathParam("infoAmoirie") boolean infoAmoiri,
                                                 @PathParam("pivoter") boolean pivoter ,
                                                 @PathParam("modelePoincarre") boolean modelePoincarre 
                                                 ,
                                                 @PathParam("distinct") boolean distinct) throws Exception, JRException {
        InputStream myInpuStream = null;
         Classe classe= new Classe() ;
         classe = Classe.findById(libelleClasse) ;

        if(!compress) {
            if(niveauEnseign==2) { 
                if(!pivoter) {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel90.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel.jrxml");
                System.out.println("Bulletin appeler>>>>>>") ;
                }
               

            } else if (niveauEnseign==4||niveauEnseign==1) {
                if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelPrimaire.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelPrimaire.jrxml");
                }
               

            }
            else if (niveauEnseign==5||niveauEnseign==6) {
                if(!pivoter) {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelTechnique.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelTechnique.jrxml");
                }
               

            }

            else if (niveauEnseign==3) {  
                if(modelePoincarre){
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTSPointCarre.jrxml");

                } else { 
                    if(!pivoter){
                        if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTS.jrxml");
                    } else{
                        if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTS.jrxml");
                    }

                }
                
               
            }

        } else {
            if(niveauEnseign==2) { 
                if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                        
                    }
                    
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress90.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress.jrxml");
                }
                

            } else if (niveauEnseign==4||niveauEnseign==1) {
                if(!pivoter) {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelPrimaire.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelPrimaire.jrxml");
                }
                

            }
            else if (niveauEnseign==5||niveauEnseign==6) { 
                if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanTechnique.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                    myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanTechnique.jrxml");
                }
                

            }
            else if (niveauEnseign==3) { 
                if(modelePoincarre){
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTSPointCarre.jrxml");

                } else {
                    if(!pivoter){
                        if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTS.jrxml");
                    } else {
                        if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTS.jrxml");
                    }
                }
                
                

            }

        }

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;

        System.out.println("entree bulletin>>> ");
       /*  try {
            bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse,positionLogo ,filigranne) ;
        } catch (RuntimeException e) {
            e.printStackTrace ();
        } */

        // deleteEmr(libellePeriode,libelleAnnee,idEcole);
        //connect() ;

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
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        map.put("classe", classe.getLibelle());
        map.put("idEcole", idEcole);
        map.put("libelleAnnee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        map.put("infosAmoirie", infos);
        map.put("distinctin", pdistinct);
        map.put("codeEcole", myEcole.getEcolecode());
        map.put("positionLogo", plogoPosi);
        map.put("setBg", psetBg);


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider"+libelleClasse+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("/spider-bulletin-matricule/{idEcole}/{libellePeriode}/{libelleAnnee}/{idClasse}/{matricule}/{compress}/{niveauEnseign}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{modelePoincarre}/{distinct}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("idClasse") Long libelleClasse ,@PathParam("matricule") String matricule,@PathParam("compress") Boolean compress,
                                                 @PathParam("niveauEnseign") Long niveauEnseign,@PathParam("positionLogo") boolean positionLogo ,@PathParam("filigranne") boolean filigranne ,
                                                 @PathParam("infoAmoirie") boolean infoAmoiri,
                                                 @PathParam("pivoter") boolean pivoter ,
                                                 @PathParam("modelePoincarre") boolean modelePoincarre  
                                                 ,
                                                 @PathParam("distinct") boolean distinct ) throws Exception, JRException {
        InputStream myInpuStream = null;
if (!compress){
    if(niveauEnseign ==2) { 
        if(!pivoter){
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpider90.jrxml");
        } else {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpider.jrxml");
        }
        

    } else if (niveauEnseign ==4||niveauEnseign ==1) { 
        if(!pivoter){
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PrimaireSpider.jrxml");
        } else {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PrimaireSpider.jrxml");
        }
        

    }
    else if (niveauEnseign==5||niveauEnseign==6) { 
        if(!pivoter){
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderTechnique.jrxml");
        } else {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderTechnique.jrxml");
        }
       

    }

    else if (niveauEnseign==3) {  
        if(modelePoincarre){
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderBTSPointCareeSup.jrxml");
        } else {
            if(!pivoter){
                if(libellePeriode.equals("Troisième Trimestre"))
                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderBTS.jrxml");
    
            } else {
                if(libellePeriode.equals("Troisième Trimestre"))
                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderBTS.jrxml");
            }

        }
        
       
    }
} else {
    if(niveauEnseign==2) { 
        if(!pivoter){
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtan90.jrxml");

        } else {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtan.jrxml");
        }
        

    } else if (niveauEnseign==4||niveauEnseign==1) { 
        if(!pivoter){
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PrimaireSpider.jrxml");
        } else {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PrimaireSpider.jrxml");
        }
      

    }
    else if (niveauEnseign==5||niveauEnseign==6) { 
        if(!pivoter) {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanTechnique.jrxml");
        } else{
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanTechnique.jrxml");
        }
       

    }
    else if (niveauEnseign==3) { 
        if(modelePoincarre) {
            if(libellePeriode.equals("Troisième Trimestre"))
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanBTSPointCarre.jrxml");

        } else{
            if(!pivoter){
                if(libellePeriode.equals("Troisième Trimestre"))
                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanBTS.jrxml");
            } else {
                if(libellePeriode.equals("Troisième Trimestre"))
                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderBulletin.jrxml");
            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanBTS.jrxml");
            }
        }
       
       

    }
}


        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;


        bulletinSpiderMatriculeServices.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,matricule,positionLogo,filigranne) ;

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        String infos= null ;

        String pdistinct= null ;
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

        Map<String, Object> map = new HashMap<>();
         map.put("idEcole", idEcole);
        map.put("annee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        map.put("matricule",matricule);
        map.put("infosAmoirie", infos);
        map.put("distinctin", pdistinct);
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
@Transactional
    public  void deleteEmr (String periode, String annee ,Long ecoleId){
        try {
            em.createQuery("delete from DetailBulletin d where  d.matiereCode in ('31') and d.bulletin.id in (select b.id from  Bulletin b where  b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ecoleId=:ecoleId  )  ")

                    .setParameter("periode",periode)
                    .setParameter("annee",annee)
                    .setParameter("ecoleId",ecoleId)
                    .executeUpdate() ;
        } catch (NoResultException e){

        }
    }



}
