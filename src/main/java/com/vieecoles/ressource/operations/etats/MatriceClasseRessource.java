package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.DpspServices;
import com.vieecoles.services.etats.MatriceClasseBilanServices;
import com.vieecoles.services.etats.MatriceClasseServices;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
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
import java.util.*;

@Path("/imprimer-matrice-classe")

public class MatriceClasseRessource {
    @Inject
    EntityManager em;
    @Inject
    MatriceClasseServices matriceClasseServices ;

    @Inject
    MatriceClasseBilanServices matriceBilanClasseServices ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/imprimer-spider-xls/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportxls(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        SpiderMatriceClasseDto detailsBull= new SpiderMatriceClasseDto() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_matriceClasse.jrxml");
        List<matriceClasseDto> detailsBull1= new ArrayList<>() ;
        List<matiereMoyenneBilanDto> detailsBull2= new ArrayList<>() ;
        detailsBull2=  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;

        detailsBull1=   matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;

        detailsBull.setMatriceClasseDto(detailsBull1);
        detailsBull.setMatiereMoyenneBilanDto(detailsBull2);
        System.out.println("detailsBull2 "+detailsBull2);


        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        // map.put("title", type);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Matrice de classe.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }


    @GET
    @Transactional
    @Path("/infos/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<matriceClasseDto>  getDtoRapport2(@PathParam("idEcole") Long idEcole , @PathParam("libelleAnnee") String libelleAnnee ,

                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {

        return  matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;
    }

    @GET
    @Transactional
    @Path("/Bilan-infos/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<matiereMoyenneBilanDto>  getDtoRapport3(@PathParam("idEcole") Long idEcole , @PathParam("libelleAnnee") String libelleAnnee ,
                                                        @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId , @PathParam("classe") String classe) throws Exception, JRException {
        return  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;
    }

    @GET
    @Transactional
    @Path("/imprimer-Bilan/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport4(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BilanMatrice.jrxml");
        List<matiereMoyenneBilanDto> detailsBull= new ArrayList<>() ;


        detailsBull=   matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;
        //dspsDto = dpspServices.DspspDto(idEcole,libellePeriode,libelleAnnee) ;

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        // map.put("title", type);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Matrice de classe.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);

    }


    @GET
    @Transactional
    @Path("/imprimer-spider/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") String classe) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        SpiderMatriceClasseDto detailsBull= new SpiderMatriceClasseDto() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_matriceClasse.jrxml");
        List<matriceClasseDto> detailsBull1= new ArrayList<>() ;
        List<matiereMoyenneBilanDto> detailsBull2= new ArrayList<>() ;
        detailsBull2=  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;

        detailsBull1=   matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;

        detailsBull.setMatriceClasseDto(detailsBull1);
        detailsBull.setMatiereMoyenneBilanDto(detailsBull2);
        System.out.println("detailsBull2 "+detailsBull2);


        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=matrice trimestrielle.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Transactional
    @Path("/matieres-ecole/{idEcole}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<ClasseMatiere>  getDtoRapport(@PathParam("idEcole") Long idEcole , @PathParam("classe") Long classe ) throws Exception, JRException {
       Classe m= Classe.findById(classe) ;
       System.out.println("Classe "+m.getLibelle());
        return   ClasseMatiere.find("select distinct m from ClasseMatiere m  where m.matiere.ecole.id = ?1 and m.branche.id = ?2 ", idEcole,m.getBranche().getId()).list();

        // System.out.println("classeMatiereList "+classeMatiereList.toString());
    }

}
