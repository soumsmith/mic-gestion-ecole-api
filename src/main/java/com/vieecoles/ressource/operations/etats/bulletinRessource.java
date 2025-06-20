package com.vieecoles.ressource.operations.etats;



import com.vieecoles.dto.MatriculeClasseDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.entities.parametre;
import com.vieecoles.entities.profil;
import com.vieecoles.projection.BulletinSelectDto;
import com.vieecoles.projection.LivretScolaireSelectDto;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.etats.BulletinClasseServices;
import com.vieecoles.services.etats.LivretScolaireServices;
import com.vieecoles.services.profilService;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Bulletin;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import jakarta.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.hibernate.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    @Inject
    BulletinClasseServices bulletinClasseServices ;

    @Inject
    LivretScolaireServices livretScolaireServices ;

    private static String UPLOAD_DIR = "/data/";
    @GET
    @Path("/list-matricule-par-classe/{idEcole}/{classe}/{periode}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public List<NiveauDto>  getMatriculeByClasse(@PathParam("idEcole") Long idEcole,@PathParam("classe") String classe ,@PathParam("periode") String periode)  {
      List<NiveauDto> matricule= new ArrayList<>() ;
        matricule=  getMatriculeParClasse(idEcole,classe,periode) ;
        return matricule;
    }

    @GET
    @Path("/livret-scolaire/{matricule}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LivretScolaireSelectDto> livretScolaire(@PathParam("matricule") String matricule,
                                                        @PathParam("idEcole") Long idEcole,
                                                        @PathParam("libelleAnnee") String libelleAnnee,
                                                        @PathParam("libelleTrimetre") String libelleTrimetre)  {
        return  livretScolaireServices.livretScolaire(idEcole,libelleTrimetre,matricule,libelleAnnee);

    }

    @PUT
    @Path("/mise-a-jours-heures-absences/{matricule}/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{heureAbsenJusti}/{heureAbsenNonJusti}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Bulletin miseAjourBulletin(@PathParam("matricule") String matricule, @PathParam("idEcole") Long idEcole, @PathParam("libelleAnnee") String libelleAnnee,
                                       @PathParam("libelleTrimetre") String libelleTrimetre, @PathParam("heureAbsenJusti") String heureAbsenJusti , @PathParam("heureAbsenNonJusti") String heureAbsenNonJusti)  {
        return  bulletinClasseServices.miseAjoursHeureAbsence(matricule,libelleAnnee,libelleTrimetre,idEcole,heureAbsenJusti,heureAbsenNonJusti);
    }

    @GET
    @Path("/bulletin-genere-par-classe/{classe}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<Bulletin> listeBulletinGenereParClasse(@PathParam("classe") String classe, @PathParam("idEcole") Long idEcole, @PathParam("libelleAnnee") String libelleAnnee,
                                      @PathParam("libelleTrimetre") String libelleTrimetre)  {
        return  bulletinClasseServices.listeBulletinGenereParClasse(classe,libelleAnnee,libelleTrimetre,idEcole);
    }



        @GET
    @Path("/details-bulletin/{type}/{matricule}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public ResponseEntity<byte[]>  getdetailsBulletin(@PathParam("type") String type,@PathParam("matricule") String matricule,@PathParam("idEcole") Long idEcole,@PathParam("libelleAnnee") String libelleAnnee,
                                                      @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {

        InputStream myInpuStream ;
        if(libelleTrimetre.equals("Troisième Trimestre"))
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinTroisiemeTrimetre.jrxml");
        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinNobel.jrxml");

        //  myInpuStream = this.getClass().getClassLoader().getResourceAsStream("spider/test.jrxml");
        List<BulletinSelectDto>  detailsBull = new ArrayList<>() ;

        Inscriptions myIns= new Inscriptions() ;
        ecole myEcole= new ecole() ;
        parametre  mpara = new parametre();
        mpara = parametre.findById(1L) ;
        eleve myelev = new eleve() ;
        String DateNaiss = null;


        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        //System.out.println("myEcole "+myEcole.toString());
        myIns = inscriptionService.checkInscrit(idEcole,matricule,1L);
        System.out.println("Inscription "+ myIns.toString());
            byte[] imagebytes = new byte[0];
            if(myIns!=null){
                 imagebytes = myIns.getPhoto_eleve() ;
            }

        byte[] imagebytes2 = myEcole.getLogoBlob() ;
        byte[] imagebytes3 = mpara.getImage() ;
        byte[] imagebytes4 = myEcole.getFiligramme() ;
        BufferedImage photo_eleve = null,logo= null ,amoirie= null,bg= null;
        String codeEcole = myEcole.getEcolecode() ;
        String statut = myEcole.getEcole_statut() ;
        myelev= eleve.findById(myIns.getEleve().getEleveid()) ;

        LocalDate date ;
        date = myelev.getElevedate_naissance();


        if(date!=null){
            DateNaiss= String.valueOf(date.getDayOfMonth())+'/'+String.valueOf(date.getMonthValue())+'/'+String.valueOf(date.getYear()) ;

        }

        if(imagebytes!=null){
          photo_eleve= ImageIO.read(new ByteArrayInputStream(imagebytes));
        }
        if(imagebytes2!=null){
            logo= ImageIO.read(new ByteArrayInputStream(imagebytes2));
        }

        if(imagebytes3!=null){
            amoirie = ImageIO.read(new ByteArrayInputStream(imagebytes3));
        }

        if(imagebytes4!=null){
            bg = ImageIO.read(new ByteArrayInputStream(imagebytes4)) ;
        }


        System.out.println("myEcoleImage "+imagebytes2.toString());
        TypedQuery<BulletinSelectDto> q = em.createQuery( "SELECT new com.vieecoles.projection.BulletinSelectDto(b.ecoleId,b.nomEcole,b.statutEcole,b.urlLogo,b.adresseEcole,b.telEcole,b.anneeLibelle, b.libellePeriode,b.matricule,b.nom, b.prenoms, b.sexe,b.dateNaissance,b.lieuNaissance,b.nationalite,b.redoublant,b.boursier,b.affecte,b.libelleClasse,b.effectif,b.totalCoef,b.totalMoyCoef,b.nomPrenomProfPrincipal,b.heuresAbsJustifiees,b.heuresAbsNonJustifiees,b.moyGeneral,b.moyMax,b.moyMin,b.moyAvg,b.moyAn,b.rangAn,b.appreciation,b.dateCreation,b.codeQr,b.statut,d.matiereLibelle,d.moyenne,d.rang,d.coef ,d.moyCoef,d.appreciation,d.categorie,d.num_ordre,CAST(b.rang as string ) ,d.nom_prenom_professeur,d.categorieMatiere,b.nomSignataire,CAST(d.bonus as string ),cast(d.pec as string),d.parentMatiere,d.isRanked,b.isClassed,cast(b.effectifNonClasse as integer ) ) from DetailBulletin  d join d.bulletin b where b.matricule=:matricule " +
                "and b.ecoleId=:idEcole and b.anneeLibelle=:libelleAnnee and b.libellePeriode=:libelleTrimetre order by d.num_ordre ASC  ", BulletinSelectDto.class);
        detailsBull = q.setParameter("matricule", matricule)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("libelleTrimetre", libelleTrimetre)
                .setParameter("idEcole", idEcole)
                . getResultList() ;
        Double TmoyFr= calculTMoyFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;


        Double TcoefFr = calculcoefFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Double  TmoyCoefFr = calculMoycoefFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        //System.out.println("Moyene en Francais: "+TmoyCoefFr/4);
        Double TrangFr1 = calculRangFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;


        Integer TrangEMR =calculRangEMR(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Double TmoyCoefEMR = calculMoycoefEMR(matricule,libelleAnnee,libelleTrimetre,idEcole);
        Double TcoefEMR  = calculcoefEMR(matricule,libelleAnnee,libelleTrimetre,idEcole);


        Double moy_1er_trim = calculmoyenTrimesPasse(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Double moy_2eme_trim = calculmoyenTrimesPasse(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        Double moy_3eme_trim = calculmoyenTrimesPasse(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;

        Integer rang_1er_trim = calculRangTrimesPasse(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Integer rang_2eme_trim = calculRangTrimesPasse(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        Integer rang_3eme_trim = calculRangTrimesPasse(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;

        String is_class_1er_trim = calculIsClassTrimesPasse(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        String is_class_2e_trim = calculIsClassTrimesPasse(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        String is_class_3e_trim = calculIsClassTrimesPasse(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;

        int TrangFr = 0;


         if(TrangFr1 !=null)
         TrangFr = TrangFr1.intValue() ;


        if(type.toUpperCase().equals("PDF")){


            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
            Map<String, Object> map = new HashMap<>();
            map.put("photo_eleve",photo_eleve);
            map.put("logo",logo);
            map.put("amoirie",amoirie);
            map.put("bg",bg);
            map.put("TmoyFr",TmoyCoefFr/TcoefFr);
            map.put("TcoefFr",TcoefFr);
            map.put("TmoyCoefFr",TmoyCoefFr);
            map.put("TrangFr",TrangFr);
            map.put("codeEcole",codeEcole);
            map.put("statut",statut);
            map.put("DateNaiss",DateNaiss);
            map.put("TmoyCoefEMR",TmoyCoefEMR);
            map.put("TrangEMR",TrangEMR);
            map.put("moy_1er_trim",moy_1er_trim);
            map.put("moy_2eme_trim",moy_2eme_trim);
            map.put("moy_3eme_trim",moy_3eme_trim);

            map.put("rang_1er_trim",rang_1er_trim);
            map.put("rang_2eme_trim",rang_2eme_trim);
            map.put("rang_3eme_trim",rang_3eme_trim);

            map.put("is_class_1er_trim",is_class_1er_trim);
            map.put("is_class_2e_trim",is_class_2e_trim);
            map.put("is_class_3e_trim",is_class_3e_trim);


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



    List<NiveauDto> getMatriculeParClasse(Long idEcole ,String libelleClasse,String periode){
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole  and b.libelleClasse=:classe and b.libellePeriode=:periode "
                , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("classe", libelleClasse)
                .setParameter("periode", periode)
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

     public  Double calculTMoyFran(String matricule, String annee,String periode,Long idEcole){
         try {
             Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                     .setParameter("matricule",matricule)
                     .setParameter("annee",annee)
                     .setParameter("periode",periode)
                     .setParameter("idEcole",idEcole)
                      .getSingleResult();
             return  moyTfr ;
         } catch (NoResultException e){
             return 0D ;
         }
     }

    public  Double calculcoefFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculcoefEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('FIQ','AS-SIRAH','AL-AQIDAH','AL-AKHLÂQ','MEMORISATION') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }


    public  Double calculMoycoefFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef*d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculMoycoefEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select d.moyenne from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:libelle ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .setParameter("libelle","EMR")
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }



    public  Double calculRangFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select AVG(d.rang) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }



    public  Integer calculRangEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Integer  moyTfr = (Integer) em.createQuery("select d.rang from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:libelle ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .setParameter("libelle","EMR")
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0 ;
        }
    }



    public  Double calculmoyenTrimesPasse(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select b.moyGeneral from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Integer calculRangTrimesPasse(String matricule, String annee,String periode,Long idEcole){
        try {
            Integer  moyTfr = (Integer) em.createQuery("select b.rang from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0;
        }
    }




    public  String calculIsClassTrimesPasse(String matricule, String annee,String periode,Long idEcole){
        try {
            String  isclass = (String) em.createQuery("select b.isClassed from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  isclass ;
        } catch (NoResultException e){
            return null;
        }
    }



}
