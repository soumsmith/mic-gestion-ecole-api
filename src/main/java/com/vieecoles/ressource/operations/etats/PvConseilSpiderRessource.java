package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.MatriceClasseBilanServices;
import com.vieecoles.services.etats.MatriceClasseServices;
import com.vieecoles.services.etats.PvConseilDeclasseServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import java.io.InputStream;
import java.util.*;

@Path("/imprimer-rapport-pv-conseil-classe")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class PvConseilSpiderRessource {
    @Inject
    EntityManager em;
    @Inject
    PvConseilDeclasseServices resultatsServices ;
    @Inject
    MatriceClasseServices matriceClasseServices ;

    @Inject
    MatriceClasseBilanServices matriceBilanClasseServices ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/pouls-Conseil-classe/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{classe}/{anneeId}/{idclasse}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,@PathParam("libelleTrimetre") String libelleTrimetre ,@PathParam("classe") String classe ,@PathParam("anneeId") Long anneeId,@PathParam("idclasse") Long idclasse ) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_PV_Conseil_classe.jrxml");

        spiderPvDto detailsBull= new spiderPvDto() ;
        List<ConseilClasseDto>  dspsDto = new ArrayList<>() ;

        List<matriceClasseDto> detailsBull1= new ArrayList<>() ;
        List<matiereMoyenneBilanDto> detailsBull2= new ArrayList<>() ;
        detailsBull2=  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,libelleTrimetre ,anneeId, idclasse) ;

        detailsBull1=   matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,libelleTrimetre ,anneeId, idclasse) ;
        dspsDto= resultatsServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,classe) ;


        detailsBull.setMatiereMoyenneBilanDto(detailsBull2);
        detailsBull.setMatriceClasseDto(detailsBull1);
        detailsBull.setDspsDto(dspsDto);

        System.out.println("detailsBull "+ detailsBull);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        // map.put("title", type);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=PvConseil.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);



    }

    @GET
    @Transactional
    @Path("/infos/pouls-rapport-pv/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public  List<ConseilClasseDto>  getDtoRapport2(@PathParam("idEcole") Long idEcole , @PathParam("libelleAnnee") String libelleAnnee, @PathParam("libelleTrimetre") String libelleTrimetre , @PathParam("classe") String classe) throws Exception, JRException {
        return  resultatsServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,classe) ;
        //return  null ;
    }







}
