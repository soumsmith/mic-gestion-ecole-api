package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.ProcesVerbalDto;
import com.vieecoles.dto.ProcesVerbalListeClasseDto;
import com.vieecoles.dto.ProcesVerbalMatiereSpecifiqueDto;
import com.vieecoles.dto.ProcesVerbalStatistiqueClasseDto;
import com.vieecoles.dto.ProcesVerbalStatistiqueDisciplineDto;
import com.vieecoles.dto.PyramideEffectDto;
import com.vieecoles.dto.RapportRentreeDto;
import com.vieecoles.dto.SpiderPvConseilDto;
import com.vieecoles.dto.SpiderRapportRentreeDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.ProcesVerbalServices;
import com.vieecoles.services.etats.PvConseilsClasse.ListeClassePvServices;
import com.vieecoles.services.etats.PvConseilsClasse.MajorClassePvServices;
import com.vieecoles.services.etats.PvConseilsClasse.MatiereParDisciplinePvServices;
import com.vieecoles.services.etats.PvConseilsClasse.MatiereSpecifiquePvServices;
import com.vieecoles.services.etats.PvConseilsClasse.StatistiqueClassePvServices;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.services.EvaluationService;
import javax.persistence.TypedQuery;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
import java.util.*;

@Path("/imprimer-proces-verbal")

public class ProcesVerbalRessource {
    @Inject
    EntityManager em;
    @Inject
    ProcesVerbalServices procesVerbalServices ;
    @Inject
    EvaluationService evaluationService;
    @Inject
    SousceecoleService sousceecoleService ;
    private static String UPLOAD_DIR = "/data/";
    @Inject
    ListeClassePvServices listeClassePvServices;
    @Inject
    MajorClassePvServices majorClassePvServices;
    @Inject
    MatiereParDisciplinePvServices matiereParDisciplinePvServices;
    @Inject
    MatiereSpecifiquePvServices matiereSpecifiquePvServices;
    @Inject
    StatistiqueClassePvServices statistiqueClassePvServices;


    @GET
    @Transactional
    @Path("/infos/{idClasse}/{code}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<ProcesVerbalDto>  getInfosProcesVerbal(@PathParam("idClasse") Long idClasse , @PathParam("code") String code ) throws Exception, JRException {

        return  procesVerbalServices.getProcesVerEval(idClasse ,code);
    }

    @GET
    @Transactional
    @Path("/infos-by-matricule/{idClasse}/{code}/{matricule}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ProcesVerbalDto  getInfosProcesVerbal(@PathParam("idClasse") Long idClasse , @PathParam("code") String code, @PathParam("matricule") String matricule  ) throws Exception, JRException {

        return  procesVerbalServices.getProcesVerEvalByMatricule(idClasse ,code ,matricule);
    }

    @GET
    @Transactional
    @Path("/imprimer-proces-verbal/{idClasse}/{code}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getInfosProcesVerbalImprim(@PathParam("idClasse") Long idClasse , @PathParam("code") String code )  throws Exception, JRException {
        Ecole myEcole= new Ecole() ;
        parametre mpara = new parametre();
        mpara = parametre.findById(1L) ;

        Classe cl= new Classe() ;
        cl= Classe.findById(idClasse);
        myEcole = cl.getEcole() ;
        ecole myEcole2= new ecole() ;
        myEcole2 =sousceecoleService.getInffosEcoleByID(myEcole.getId());

        byte[] imagebytes = new byte[0],imagebytes2 = new byte[0],imagebytes3 = new byte[0] ,imagebytes4 = new byte[0] ;

        if(myEcole2.getLogoBlob()!=null)
            imagebytes2 = myEcole2.getLogoBlob() ;

        imagebytes3 = mpara.getImage() ;
        if(myEcole2.getFiligramme()!=null)
            imagebytes4 = myEcole2.getFiligramme() ;
        BufferedImage photo_eleve = null,logo= null ,amoirie= null,bg= null;
        String codeEcole = myEcole2.getEcolecode() ;
        String statut = myEcole2.getEcole_statut() ;
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
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Proces_verbal_evaluation.jrxml");
        List<ProcesVerbalDto> detailsBull= new ArrayList<>() ;
        detailsBull= procesVerbalServices.getProcesVerEval(idClasse ,code);
        //dspsDto = dpspServices.DspspDto(idEcole,libellePeriode,libelleAnnee) ;
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull)  ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("ecole",myEcole2.getEcoleclibelle());
        map.put("codeEcole",codeEcole);
        map.put("statut",statut);
        map.put("logo",logo);
        map.put("amoirie",amoirie);
        map.put("bg",bg);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=proces-verbal-evaluation.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

    @GET
    @Transactional
    @Path("/imprimer-proces-verbalv2/{idEcole}/{libelleAnnee}/{libelleTrimestre}/{idClasse}/{libelleClasse}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getInfosProcesVerbalImprimV2(@PathParam("idEcole") Long idEcole ,
                                                                @PathParam("libelleAnnee") String libelleAnnee ,
                                                                @PathParam("libelleTrimestre") String libelleTrimestre,
                                                                @PathParam("idClasse") Long idClasse,@PathParam("libelleClasse") String libelleClasse )  throws Exception, JRException {
        Ecole myEcole= new Ecole() ;
        parametre mpara = new parametre();
        mpara = parametre.findById(1L) ;

        ecole myEcole2= new ecole() ;
      myEcole2 =ecole.findById(idEcole) ;

        if(myEcole2.getEcolecode()!=null)
        System.out.println("Libelle Ecole "+myEcole2.getEcoleclibelle());
        byte[] imagebytes = new byte[0],imagebytes2 = new byte[0],imagebytes3 = new byte[0] ,imagebytes4 = new byte[0] ;

         if(myEcole2.getLogoBlob()!=null)
            imagebytes2 = myEcole2.getLogoBlob() ;

        imagebytes3 = mpara.getImage() ;
       if(myEcole2.getFiligramme()!=null)
            imagebytes4 = myEcole2.getFiligramme() ;
        BufferedImage photo_eleve = null,logo= null ,amoirie= null,bg= null;
        String codeEcole = myEcole2.getEcolecode() ;
        String statut = myEcole2.getEcole_statut() ;
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

        SpiderPvConseilDto detailsBull= new SpiderPvConseilDto() ;
        List<ProcesVerbalListeClasseDto> procesVerbalListeClasseDto = new ArrayList<>() ;
        List<ProcesVerbalStatistiqueDisciplineDto> procesVerbalStatistiqueDisciplineDto = new ArrayList<>() ;
        List<ProcesVerbalMatiereSpecifiqueDto> procesVerbalMatiereSpecifiqueDto = new ArrayList<>();
        List<ProcesVerbalListeClasseDto> listMajors = new ArrayList<>() ;
        List<ProcesVerbalStatistiqueClasseDto>procesVerbalStatistiqueClasseDto= new ArrayList<>() ;
        System.out.println("Entree Majors");
        listMajors = majorClassePvServices.MajorParNiveauClasse(idEcole,libelleAnnee,libelleTrimestre,libelleClasse) ;
        System.out.println("listMajors ok");
        procesVerbalListeClasseDto=listeClassePvServices.getListClasse(idEcole  ,libelleAnnee,libelleTrimestre,libelleClasse);
        System.out.println("procesVerbalListeClasseDto ok");
        procesVerbalStatistiqueDisciplineDto=matiereParDisciplinePvServices.getDiscpline(idEcole  ,libelleAnnee,libelleTrimestre,libelleClasse);
        System.out.println("procesVerbalStatistiqueDisciplineDto ok");
        ProcesVerbalMatiereSpecifiqueDto procesVerbalMatiereSpecifiqueDto1= new ProcesVerbalMatiereSpecifiqueDto();

        procesVerbalMatiereSpecifiqueDto1=matiereSpecifiquePvServices.getMatiereSpecifique(idEcole  ,libelleTrimestre,libelleAnnee,libelleClasse);
        System.out.println("ProcesVerbalMatiereSpecifiqueDto ok");
        ProcesVerbalStatistiqueClasseDto procesVerbalStatistiqueClasseDto1= new ProcesVerbalStatistiqueClasseDto();
        procesVerbalStatistiqueClasseDto1= statistiqueClassePvServices.getStatistiqueClasse(idEcole,libelleAnnee,libelleTrimestre,libelleClasse);
        procesVerbalStatistiqueClasseDto.add(procesVerbalStatistiqueClasseDto1);
        System.out.println("Statistique classe ok");
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PvConseil/Spider_PV_Conseil_classe.jrxml");
        System.out.println("Rapport chargé ok");
        procesVerbalMatiereSpecifiqueDto.add(procesVerbalMatiereSpecifiqueDto1);
        detailsBull.setProcesVerbalListeClasseDto(procesVerbalListeClasseDto);
        detailsBull.setListeMajors(listMajors);
        detailsBull.setProcesVerbalMatiereSpecifiqueDto(procesVerbalMatiereSpecifiqueDto);
        detailsBull.setProcesVerbalStatistiqueDisciplineDto(procesVerbalStatistiqueDisciplineDto);
        detailsBull.setProcesVerbalStatistiqueClasseDto(procesVerbalStatistiqueClasseDto);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);

        Map<String, Object> map = new HashMap<>();
        map.put("ecoleclibelle",myEcole2.getEcoleclibelle());
       map.put("ecolecode",codeEcole);
        map.put("ecole_statut",statut);
        map.put("logo",logo);
        map.put("ecole_adresse",myEcole2.getEcole_adresse());
        map.put("amoirie",amoirie);
        map.put("classe",libelleClasse);

         /*map.put("ecoleclibelle","");
        map.put("ecolecode","codeEcole");
        map.put("ecole_statut","statut");
        map.put("logo",null);
        map.put("ecole_adresse","amoirie");
        map.put("amoirie",null);
        map.put("classe",libelleClasse);*/

        System.out.println("Rapport chargé ok2");


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        System.out.println("Rapport chargé ok3");

        //to pdf ;
        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=pv-conseil-classe.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }

}
