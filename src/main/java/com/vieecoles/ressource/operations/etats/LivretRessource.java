package com.vieecoles.ressource.operations.etats;



import com.vieecoles.dto.MatriculeClasseDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
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
import com.vieecoles.steph.services.AnneeService;
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
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Path("/livret-scolaire")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class LivretRessource {
    @Inject
    AnneeService anneeService;
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;
    @Inject
    LivretScolaireServices livretScolaireServices ;
    @Inject
    LivretScolaireServices bulletinClasseServices ;

    private static String UPLOAD_DIR = "/data/";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ecoleviedbv2";
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;
    Connection dbConnection = null;





        @GET
    @Path("/livret-scolaire/{matricule}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public ResponseEntity<byte[]>  getdetailsLivret(@PathParam("matricule") String matricule,@PathParam("idEcole") Long idEcole,@PathParam("libelleAnnee") String libelleAnnee,
                                                      @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
            InputStream myInpuStream ;

            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/LivretScolaire/Livret_scolaireSpider.jrxml");

            spiderBulletinDto detailsBull= new spiderBulletinDto() ;
            List<parametreDto>  dspsDto = new ArrayList<>() ;


            // bulletinSpider.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,libelleClasse) ;


            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecoleviedbv2", USER, PASS);
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);


            Map<String, Object> map = new HashMap<>();
            map.put("idEcole", idEcole);
            map.put("annee", libelleAnnee);
            map.put("libellePeriode", libelleTrimetre);
            map.put("matricule",matricule);
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);

            byte[] data =JasperExportManager.exportReportToPdf(report);

            HttpHeaders headers= new HttpHeaders();
            // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider-"+matricule+".pdf");
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);


        }

    @GET
    @Path("/livret-scolaire-information/{matricule}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public ResponseEntity<byte[]>  getdetailsLivretInfos(@PathParam("matricule") String matricule,@PathParam("idEcole") Long idEcole,@PathParam("libelleAnnee") String libelleAnnee,
                                                    @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {
        System.out.println(" entree >>> "+1);
        InputStream myInpuStream ;
        Integer niveauOrdre= getNiveau(matricule,libelleAnnee ,libelleTrimetre,idEcole);
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/Livret_scolaire.jrxml");

        //  myInpuStream = this.getClass().getClassLoader().getResourceAsStream("spider/test.jrxml");
        List<LivretScolaireSelectDto>  detailsBull = new ArrayList<>() ;

        Inscriptions myIns= new Inscriptions() ;
        ecole myEcole= new ecole() ;
        parametre  mpara = new parametre();
        mpara = parametre.findById(1L) ;
        eleve myelev = new eleve() ;
        String DateNaiss = null;


        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        //System.out.println("myEcole "+myEcole.toString());
        myIns = inscriptionService.checkInscrit(idEcole,matricule,1L);
        // System.out.println("Inscription "+ myIns.toString());
        byte[] imagebytes = new byte[0],imagebytes2 = new byte[0],imagebytes3 = new byte[0] ,imagebytes4 = new byte[0] ;
        if(myIns.getPhoto_eleve()!=null)
            imagebytes = myIns.getPhoto_eleve() ;
        if(myEcole.getLogoBlob()!=null)
            imagebytes2 = myEcole.getLogoBlob() ;

        imagebytes3 = mpara.getImage() ;
        if(myEcole.getFiligramme()!=null)
            imagebytes4 = myEcole.getFiligramme() ;
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


       detailsBull = livretScolaireServices.livretScolaire(idEcole,libelleTrimetre,matricule,libelleAnnee);


        Double TmoyFr= calculTMoyFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Double TcoefFr=0D;
        Double  TmoyCoefFr=0D;

        TcoefFr = calculcoefFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        TmoyCoefFr = calculMoycoefFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Double TmoyCoefFrPermier= calculMoycoefFran(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Double TmoyCoefFrDeuxieme= calculMoycoefFran(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        Double TmoyFrAnn = null;
        String is_class_1er_trim = calculIsClassTrimesPasse(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        String is_class_2e_trim = calculIsClassTrimesPasse(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        String is_class_3e_trim = calculIsClassTrimesPasse(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;

           /* String is_class_mat_1er_trim = calculIsClassMatiere(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
            String is_class_mat_2e_trim = calculIsClassMatiere(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
            String is_class_mat_3e_trim = calculIsClassMatiere(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;*/
        if(is_class_1er_trim.equals("N")) {
            TmoyCoefFrPermier=0D ;
        }

        if(is_class_2e_trim.equals("N")) {
            TmoyCoefFrDeuxieme=0D ;

        }
        if(is_class_3e_trim.equals("N")||TmoyCoefFr==null) {
            TmoyCoefFr=0D ;
        }

        if(TcoefFr!=null)
            TmoyFrAnn= ( ( (TmoyCoefFrPermier != null) ? TmoyCoefFrPermier/TcoefFr : 0.0) + ((TmoyCoefFrDeuxieme != null) ? TmoyCoefFrDeuxieme/TcoefFr : 0.0)*2 + (TmoyCoefFr/TcoefFr)*2 )/5 ;
        Double TrangFr1 = calculRangFran(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Double TrangFrAnnuel1 = calculRangFranAnnuel(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Double TrangFrPremier1 = calculRangFran(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Double TrangFrDeuxieme1 = calculRangFran(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;

        Integer TrangEMR =calculRangEMR(matricule,libelleAnnee,libelleTrimetre,idEcole) ;
        Integer TrangEMRPremier =calculRangEMR(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Integer TrangEMRDeuxieme =calculRangEMR(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        Double TmoyCoefEMR = calculMoycoefEMR(matricule,libelleAnnee,libelleTrimetre,idEcole);
        Double TmoyCoefEMRPremier = calculMoycoefEMR(matricule,libelleAnnee,"Premier Trimestre",idEcole);
        Double TmoyCoefEMRDeuxieme = calculMoycoefEMR(matricule,libelleAnnee,"Deuxième Trimestre",idEcole);
        Double TcoefEMR  = calculcoefEMR(matricule,libelleAnnee,libelleTrimetre,idEcole);
        Double TmoyEMRANN ;

        TmoyEMRANN = ( TmoyCoefEMRPremier + (TmoyCoefEMRDeuxieme * 2) + (TmoyCoefEMR*2) )/5 ;


        Double moy_1er_trim = calculmoyenTrimesPasse(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Double moy_2eme_trim = calculmoyenTrimesPasse(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        Double moy_3eme_trim = calculmoyenTrimesPasse(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;

        Integer rang_1er_trim = calculRangTrimesPasse(matricule,libelleAnnee,"Premier Trimestre",idEcole) ;
        Integer rang_2eme_trim = calculRangTrimesPasse(matricule,libelleAnnee,"Deuxième Trimestre",idEcole) ;
        Integer rang_3eme_trim = calculRangTrimesPasse(matricule,libelleAnnee,"Troisième Trimestre",idEcole) ;





        int TrangFr = 0;
        int TrangFrPremier = 0;
        int TrangFrDeuxieme = 0;
        int TrangFrAnnuel = 0 ;
        if(TrangFrAnnuel1 !=null)
            TrangFrAnnuel = TrangFrAnnuel1.intValue() ;

        if(TrangFr1 !=null)
            TrangFr = TrangFr1.intValue() ;

        if(TrangFrPremier1 !=null)
            TrangFrPremier = TrangFrPremier1.intValue() ;

        if(TrangFrDeuxieme1 !=null)
            TrangFrDeuxieme = TrangFrDeuxieme1.intValue() ;

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("photo_eleve",photo_eleve);
        map.put("logo",logo);
        map.put("amoirie",amoirie);
        map.put("bg",bg);
        map.put("TmoyFr",TcoefFr == null?TmoyCoefFr/1:TmoyCoefFr/TcoefFr);
        map.put("TmoyFrPremier", TcoefFr == null? ((TmoyCoefFrPermier != null) ? TmoyCoefFrPermier : 0.0/1): ((TmoyCoefFrPermier != null) ? TmoyCoefFrPermier: 0.0)/TcoefFr);
        map.put("TmoyFrDeuxieme",TcoefFr == null?((TmoyCoefFrDeuxieme != null) ? TmoyCoefFrDeuxieme : 0.0)/1: ((TmoyCoefFrDeuxieme != null) ? TmoyCoefFrDeuxieme : 0.0)/TcoefFr);
        map.put("TcoefFr",TcoefFr);
        map.put("TmoyFrAnn",TmoyFrAnn);
        map.put("TrangFrAnnuel",TrangFrAnnuel);
        map.put("TmoyEMRANN",TmoyEMRANN);
        map.put("TmoyCoefFr",TmoyCoefFr);
        map.put("TrangFr",TrangFr);
        map.put("TrangFrPremier",TrangFrPremier);
        map.put("TrangFrDeuxieme",TrangFrDeuxieme);
        map.put("TrangEMRPremier",TrangEMRPremier);
        map.put("TrangEMRDeuxieme",TrangEMRDeuxieme);
        map.put("codeEcole",codeEcole);
        map.put("statut",statut);
        map.put("DateNaiss",DateNaiss);
        map.put("TmoyCoefEMR",TmoyCoefEMR);
        map.put("TmoyCoefEMRPremier",TmoyCoefEMRPremier);
        map.put("TmoyCoefEMRDeuxieme",TmoyCoefEMRDeuxieme);
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Livret-scolaire.pdf");

        return    ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);


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
         Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
            if(niveauOrdre<=4){
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
            } else {
                try {
                    Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle=:matiere")
                            .setParameter("matricule",matricule)
                            .setParameter("annee",annee)
                            .setParameter("periode",periode)
                            .setParameter("idEcole",idEcole)
                            .setParameter("matiere","FRANCAIS")
                            .getSingleResult();
                    return  moyTfr ;
                } catch (NoResultException e){
                    return 0D ;
                }
            }

     }

    public  Double calculcoefFran(String matricule, String annee,String periode,Long idEcole){
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        if(niveauOrdre<=4) {
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
        } else {
            try {
                Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:matiere ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
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
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        System.out.println("niveauOrdre>>> "+niveauOrdre);
        if(niveauOrdre<=4){
            System.out.println("Premier Cycle>>> "+niveauOrdre);
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
        } else {
            System.out.println("Second Cycle>>> "+niveauOrdre);
            try {
                Double  moyTfr = (Double) em.createQuery("select d.moyCoef from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle=:matiere ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                System.out.println("Niveau ordre moyCoef "+moyTfr);
                return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
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
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        if(niveauOrdre<=4) {
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
        } else {
            try {
                Double  moyTfr = (Double) em.createQuery("select AVG(d.rang) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:matiere")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        }

    }
    public  Double calculRangFranAnnuel(String matricule, String annee,String periode,Long idEcole){
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        if(niveauOrdre<=4){
            try {
                Double  moyTfr = (Double) em.createQuery("select AVG(d.rangAn) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .getSingleResult();
                return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        } else {
            try {
                Double  moyTfr = (Double) em.createQuery("select AVG(d.rangAn) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:matiere ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
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

    public  Integer getNiveau(String matricule, String annee,String periode,Long idEcole){
        try {
            Integer  num = (Integer) em.createQuery("select b.ordreNiveau from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  num ;
        } catch (NoResultException e){
            return 0;
        }
    }

    public  String calculIsClassMatiere(String matricule, String annee,String periode,Long idEcole){
        try {
            String  isclass = (String) em.createQuery("select d.isRanked from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
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
