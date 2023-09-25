package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.EmploiDuTemps;
import com.vieecoles.dto.certificatScolariteDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.etats.CertificatScolariteServices;
import com.vieecoles.services.etats.EmploiDuTempsServices;
import com.vieecoles.services.etats.MatriceMoyenneServices;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/certificat-scolarite")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class CertificatScolariteRessource {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    Logger logger = Logger.getLogger(PersonnelMatiereClasseService.class.getName());
    @Inject
    MatriceMoyenneServices moyenneServices ;
    @Inject
    SousceecoleService sousceecoleService ;
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;

    @Inject
    CertificatScolariteServices certificatScolariteServices ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("imprimer/{idEcole}/{matricule}/{AnneeId}/{signataire}/{fonction}/{periode}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole , @PathParam("matricule") String matricule ,@PathParam("AnneeId") Long AnneeId
            , @PathParam("signataire") String signataire , @PathParam("fonction") String fonction
            , @PathParam("periode") String periode) throws Exception, JRException {

        Inscriptions myIns= new Inscriptions() ;
        myIns = inscriptionService.checkInscrit(idEcole,matricule,AnneeId);
        byte[] imagebytes = new byte[0];
        if(myIns!=null){
            imagebytes = myIns.getPhoto_eleve() ;
        }
        parametre  mpara = new parametre();
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
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
        if(imagebytes!=null){
            photo_eleve= ImageIO.read(new ByteArrayInputStream(imagebytes));
        }
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/CertificatScolarite.jrxml");
        certificatScolariteDto emp= new certificatScolariteDto() ;

        List<certificatScolariteDto> detailsBull= new ArrayList<>() ;

        emp = certificatScolariteServices.getInfoCertificat(idEcole ,matricule ,AnneeId ,periode) ;

        detailsBull.add(emp);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("logo",logo);
        map.put("amoirie",amoirie);
        map.put("bg",bg);
        map.put("libelleEcole",libelleEcole);
        map.put("adresse",adresse);
        map.put("telephone",telephone);
        map.put("code",code);
        map.put("statut",statut);
        map.put("idEcole",idEcole);
        map.put("matricule",matricule);
        map.put("AnneeId",AnneeId);
        map.put("photo_eleve",photo_eleve);
        map.put("signataire",signataire);
        map.put("fonction",fonction);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Certificat de scolarite.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }


    @GET
    @Transactional
    @Path("/infos/{idEcole}/{matricule}/{AnneeId}/{periode}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public certificatScolariteDto  getInfos(@PathParam("idEcole") Long idEcole , @PathParam("matricule") String matricule ,@PathParam("AnneeId") Long AnneeId
            , @PathParam("periode") String periode){
   // return  rapportRentree.countProfByMatiereAndEcole(idEcole,idMatiere,idAnnee ,sexe ,niveau) ;
   return  certificatScolariteServices.getInfoCertificat(idEcole ,matricule ,AnneeId ,periode) ;
    }

    @GET
    @Path("/certificat-de-frequentation/{matricule}/{ecoleId}/{annee}/{signataire}/{fonction}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport7(@PathParam("matricule") String matricule ,@PathParam("ecoleId") Long ecoleId ,@PathParam("annee") String annee
            ,@PathParam("signataire") String signataire ,@PathParam("fonction") String fonction) throws Exception, JRException {


        parametre mpara = new parametre();
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(ecoleId);
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

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/CertificatFrequentation.jrxml");


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
        System.out.println("ecoleId "+ecoleId);
        map.put("ecoleId", ecoleId);
        map.put("annee", annee);
        System.out.println("annee "+annee);
        map.put("signataire", signataire);
        map.put("fonction", fonction);
        map.put("matricule", matricule);
        System.out.println("matricule "+matricule);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=certificat-de-frequentation.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

}
