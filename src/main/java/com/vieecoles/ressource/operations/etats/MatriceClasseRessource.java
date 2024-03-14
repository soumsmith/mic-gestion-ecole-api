package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.MatriceClasseBilanServices;
import com.vieecoles.services.etats.MatriceClasseServices;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Matiere;
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
import javax.persistence.NoResultException;
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
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") Long classe) throws Exception, JRException {
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

                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") Long classe) throws Exception, JRException {

        return  matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;
        //return null;
    }

    @GET
    @Transactional
    @Path("/Bilan-infos/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<matiereMoyenneBilanDto>  getDtoRapport3(@PathParam("idEcole") Long idEcole , @PathParam("libelleAnnee") String libelleAnnee ,
                                                        @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId , @PathParam("classe") Long classe) throws Exception, JRException {
        return  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;
    }

    @GET
    @Transactional
    @Path("/imprimer-Bilan/{idEcole}/{libelleAnnee}/{periode}/{anneeId}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport4(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee ,
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") Long classe) throws Exception, JRException {
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
                                                 @PathParam("periode") String periode , @PathParam("anneeId") Long anneeId ,@PathParam("classe") Long classe) throws Exception, JRException {

        Long nombreSupegal10F = 0L, nombreInf8_5F= 0L ,nombreSup8_5F=0L ,nombreSupegal10G=0L , nombreInf8_5G=0L , nombreSup8_5G=0L ;
        Double pourSupegal10F = 0d, pourInf8_5F = 0d, pourSup8_5F = 0d, pourSupegal10G = 0d , pourInf8_5G = 0d, pourSup8_5G = 0d ;
        Long clasFille =0L ,clasgarcon =0L ;


        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        SpiderMatriceClasseDto detailsBull= new SpiderMatriceClasseDto() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_matriceClasse.jrxml");
        List<matriceClasseDto> detailsBull1= new ArrayList<>() ;
        List<matiereMoyenneBilanDto> detailsBull2= new ArrayList<>() ;
        try {




            detailsBull2=  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;

            detailsBull1=   matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,periode ,anneeId, classe) ;

            clasFille = getclassF(idEcole ,classe,libelleAnnee,periode) ;
            clasgarcon = getclassG(idEcole ,classe,libelleAnnee,periode) ;
            nombreSupegal10F = getnbreMoySupEgal10F(idEcole,classe,libelleAnnee,periode);
            nombreSupegal10G = getnbreMoySupEgal10G(idEcole,classe,libelleAnnee,periode) ;
            nombreInf8_5F = getnbreMoyInf8_5F(idEcole,classe,libelleAnnee,periode) ;
            nombreInf8_5G =getnbreMoyInf8_5G(idEcole,classe,libelleAnnee,periode) ;

            nombreSup8_5F =getnbreMoyInf999F(idEcole,classe,libelleAnnee,periode) ;
            nombreSup8_5G =getnbreMoyInf999G(idEcole,classe,libelleAnnee,periode) ;

            if(clasFille !=0)
                pourSupegal10F = (double) ((nombreSupegal10F*100d)/clasFille);
            if(clasgarcon !=0)
                pourSupegal10G = (double) ((nombreSupegal10G*100d)/clasgarcon);

            if(clasgarcon !=0)
                pourInf8_5G = (double) ((nombreInf8_5G*100d)/clasgarcon);

            if(clasFille !=0)
                pourInf8_5F = (double) ((nombreInf8_5F*100d)/clasFille);

            if(clasgarcon !=0)
                pourSup8_5G = (double) ((nombreSup8_5G*100d)/clasgarcon);

            if(clasFille !=0)
                pourSup8_5F = (double) ((nombreSup8_5F*100d)/clasFille);


        } catch (RuntimeException e){
            e.printStackTrace ();
        }


        detailsBull.setMatriceClasseDto(detailsBull1);
        detailsBull.setMatiereMoyenneBilanDto(detailsBull2);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
         map.put("nombreSupegal10F", nombreSupegal10F);
        map.put("nombreSupegal10G", nombreSupegal10G);
        map.put("nombreInf8_5F", nombreInf8_5F);
        map.put("nombreInf8_5G", nombreInf8_5G);
        map.put("nombreInf8_5F", nombreInf8_5F);
        map.put("nombreInf8_5G", nombreInf8_5G);
        map.put("pourSupegal10F", pourSupegal10F);
        map.put("pourSupegal10G", pourSupegal10G);
        map.put("pourInf8_5G", pourInf8_5G);
        map.put("pourInf8_5F", pourInf8_5F);
        map.put("pourSup8_5G", pourSup8_5G);
        map.put("pourSup8_5F", pourSup8_5F);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        //*********************************
        /*JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=matrice trimestrielle.docx");*/
        //*********************************

        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=matrice trimestrielle.pdf");


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
    @GET
    @Transactional
    @Path("/matieres-ecole-web/{idEcole}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<Matiere>  getDtoRapportweb(@PathParam("idEcole") Long idEcole , @PathParam("classe") Long classe ) throws Exception, JRException {
        Classe m= Classe.findById(classe) ;
        System.out.println("Classe "+m.getLibelle());
        List<ClasseMatiere> classeMatiereList ;


        classeMatiereList= ClasseMatiere.find("select distinct m from ClasseMatiere m  where m.matiere.ecole.id = ?1 and m.branche.id = ?2 ", idEcole,m.getBranche().getId()).list();
        List<Matiere> matieres = new ArrayList<>(classeMatiereList.size());
        for (int i=0; i< classeMatiereList.size();i++) {
            Matiere mat= new Matiere() ;
            mat.setId(classeMatiereList.get(i).getMatiere().getId());
            mat.setLibelle(classeMatiereList.get(i).getMatiere().getLibelle());
            matieres.add(mat);
        }
        return matieres ;
        // System.out.println("classeMatiereList "+classeMatiereList.toString());
    }
    public  Long getclassF(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre ){
        Long classF;
        try {
            classF = (Long) em.createQuery("select count(o.id) from Bulletin o  where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.classeId=:classeId "
                    )
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();

            return  classF ;
        } catch (NoResultException e){
            return 0L ;
        }


    }
    public Long getclassG(Long idEcole , Long classeId  ,String libelleAnnee , String libelleTrimestre ){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o  where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed =:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.classeId=:classeId "
                    )
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoySupEgal10G(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre ){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            "and o.classeId=:classeId")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoySupEgal10F(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            " and o.classeId=:classeId")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf8_5G(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre ){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral <:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            " and o.classeId=:classeId")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",8.5)
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf8_5F(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre ){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral <:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            " and o.classeId=:classeId")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",8.5)
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf999G(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre ){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o  where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.classeId=:classeId")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf999F(Long idEcole , Long classeId ,String libelleAnnee , String libelleTrimestre ){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o  where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.classeId=:classeId ")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("classeId",classeId)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
}
