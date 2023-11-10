package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.ProcesVerbalDto;
import com.vieecoles.dto.SpiderMatriceClasseDto;
import com.vieecoles.dto.matiereMoyenneBilanDto;
import com.vieecoles.dto.matriceClasseDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.MatriceClasseBilanServices;
import com.vieecoles.services.etats.MatriceClasseServices;
import com.vieecoles.services.etats.ProcesVerbalServices;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.services.EvaluationService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
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



}
