package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.MatriceMoyenneDto;
import com.vieecoles.dto.matriceDspsDto;
import com.vieecoles.dto.spiderDspsDto;
import com.vieecoles.services.etats.DpspServices;
import com.vieecoles.services.etats.MatriceMoyenneServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
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

@Path("/imprimer-matrice")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class MatriceMoyenneRessource {
    @Inject
    EntityManager em;
    @Inject
    MatriceMoyenneServices moyenneServices ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/trimestrielle/{idEcole}/{libellePeriode}/{libelleAnnee}/{classe}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee ,@PathParam("classe") String classe) throws Exception, JRException {

        Double pCompoFr ,pOrthoGram,pExpreOral,philoso , pAng, pMath,pPhysiq ,pSVT , pHg , pLv2 , pEdhc ,
                pArplat , pTic ,pConduite ,pEps ,pPericul ,pMemoris ,pFiq , pAsSirah ,pAlQidah ,pAlAklaq;
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_matrice.jrxml");
        matriceDspsDto detailsBull= new matriceDspsDto() ;
        List<MatriceMoyenneDto>  dspsDto = new ArrayList<>() ;

        dspsDto = moyenneServices.DspspDto(idEcole,libellePeriode,libelleAnnee, classe) ;

        detailsBull.setDspsDto(dspsDto);


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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Matrice-trimestrielle.xls");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);



    }





}
