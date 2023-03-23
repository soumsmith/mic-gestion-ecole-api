package com.vieecoles.ressource.operations.etats;



import com.vieecoles.dto.NiveauDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.entities.profil;
import com.vieecoles.projection.BulletinSelectDto;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.profilService;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import com.vieecoles.services.souscription.SousceecoleService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/imprimer-bulletin")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class bulletinRessource {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    private static String UPLOAD_DIR = "/data/";


    @GET
    @Path("/details-bulletin/{type}/{matricule}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public ResponseEntity<byte[]>  getdetailsBulletin(@PathParam("type") String type,@PathParam("matricule") String matricule,@PathParam("idEcole") Long idEcole,@PathParam("libelleAnnee") String libelleAnnee,
                                                      @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {



        InputStream myInpuStream ;
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");
        //  myInpuStream = this.getClass().getClassLoader().getResourceAsStream("spider/test.jrxml");
        List<BulletinSelectDto>  detailsBull = new ArrayList<>() ;

        Inscriptions myIns= new Inscriptions() ;
        ecole myEcole= new ecole() ;
        parametre  mpara = new parametre();
        mpara = parametre.findById(1L) ;



        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        //System.out.println("myEcole "+myEcole.toString());
         myIns = inscriptionService.checkInscrit(idEcole,matricule,1L);
       // System.out.println("Inscription "+ myIns.toString());
        byte[] imagebytes = myIns.getPhoto_eleve() ;
        byte[] imagebytes2 = myEcole.getLogoBlob() ;
        byte[] imagebytes3 = mpara.getImage() ;
        BufferedImage photo_eleve= ImageIO.read(new ByteArrayInputStream(imagebytes));
      BufferedImage logo= ImageIO.read(new ByteArrayInputStream(imagebytes2));
        BufferedImage amoirie = ImageIO.read(new ByteArrayInputStream(imagebytes3));

        System.out.println("myEcoleImage "+imagebytes2.toString());
        TypedQuery<BulletinSelectDto> q = em.createQuery( "SELECT new com.vieecoles.projection.BulletinSelectDto(b.ecoleId,b.nomEcole,b.statutEcole,b.urlLogo,b.adresseEcole,b.telEcole,b.anneeLibelle, b.libellePeriode,b.matricule,b.nom, b.prenoms, b.sexe,b.dateNaissance,b.lieuNaissance,b.nationalite,b.redoublant,b.boursier,b.affecte,b.libelleClasse,b.effectif,b.totalCoef,b.totalMoyCoef,b.nomPrenomProfPrincipal,b.heuresAbsJustifiees,b.heuresAbsNonJustifiees,b.moyGeneral,b.moyMax,b.moyMin,b.moyAvg,b.moyAn,b.rangAn,b.appreciation,b.dateCreation,b.codeQr,b.statut,d.matiereLibelle,d.moyenne,d.rang,d.coef ,d.moyCoef,d.appreciation,d.categorie,d.num_ordre,b.rang,d.nom_prenom_professeur,d.categorieMatiere) from DetailBulletin  d join d.bulletin b where b.matricule=:matricule " +
                "and b.ecoleId=:idEcole and b.anneeLibelle=:libelleAnnee and b.libellePeriode=:libelleTrimetre order by d.num_ordre ASC  ", BulletinSelectDto.class);
        detailsBull = q.setParameter("matricule", matricule)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("libelleTrimetre", libelleTrimetre)
                .setParameter("idEcole", idEcole)
                . getResultList() ;


        if(type.toUpperCase().equals("PDF")){


            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
            Map<String, Object> map = new HashMap<>();
            map.put("photo_eleve",photo_eleve);
            map.put("logo",logo);
            map.put("amoirie",amoirie);

            JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

            //to pdf ;
            byte[] data =JasperExportManager.exportReportToPdf(report);
            HttpHeaders headers= new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=BulletinBean.pdf");

            return    ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
        } else {
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
            Map<String, Object> map = new HashMap<>();
            // map.put("title", type);
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            // File exportReportFile = new File("profils" + ".docx");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] data = baos.toByteArray() ;
            HttpHeaders headers= new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=BulletinBean.docx");
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
        }


    }



    List<NiveauDto> getMatriculeParClasse(Long idEcole ,String libelleClasse){
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole  and b.libelleClasse=:classe "
               , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                              .setParameter("classe", libelleClasse)
                .getResultList() ;
return  classeNiveauDtoList ;
    }

    @Transactional
    Long getIdEleveByMatricule(String matricule){
        try {
            Long   moyClasseF = (Long) em.createQuery("select o.eleveid from eleve o where  o.eleve_matricule=:matricule ")
                    .setParameter("matricule",matricule)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    @Transactional
    Inscriptions getIdInscription(Long idEleve){
        Inscriptions minScription = new Inscriptions() ;
        try {
            System.out.println("+++++ENNN");
            return    minScription = (Inscriptions) em.createQuery("select o from Inscriptions o join  o.ecole e  join  o.eleve l join o.annee_scolaire where o.eleve.eleveid =:idEleve ")
                    .setParameter("idEleve",idEleve)
                    .getSingleResult();

        } catch (Exception  e){
            System.out.println("IdInscripeXE-- ");
            return null ;
        }

    }

    Inscriptions getIdForPhoto(String matricule){
       Long idEleve= getIdEleveByMatricule(matricule);
       System.out.println("idEleve "+idEleve);

        Inscriptions IdInscrip = getIdInscription(idEleve);

        System.out.println("IdInscrip "+IdInscrip);
       return  IdInscrip ;
    }




}
