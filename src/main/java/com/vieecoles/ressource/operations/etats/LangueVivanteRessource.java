package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.EffectifElevLangueVivante2Dto;
import com.vieecoles.services.etats.EffectifParLangueServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@Path("/imprimer-langue-vivante")

public class LangueVivanteRessource {
    @Inject
    EntityManager em;
    @Inject
    EffectifParLangueServices effectifParLangueServices ;




    @GET
    @Transactional
    @Path("/infos/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public EffectifElevLangueVivante2Dto  getDtoRapport2(@PathParam("idEcole") Long idEcole ,  @PathParam("anneeId") Long anneeId) throws Exception, JRException {

        return  effectifParLangueServices.getLangueVivante(idEcole ,anneeId) ;
    }



    @GET

    @Transactional
    @Path("/imprimer-Bilan/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport6(@PathParam("idEcole") Long idEcole , @PathParam("anneeId") Long anneeId) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/effectifParLanguevivante.jrxml");
        List<EffectifElevLangueVivante2Dto> detailsBull= new ArrayList<>() ;

        EffectifElevLangueVivante2Dto m = new EffectifElevLangueVivante2Dto() ;


        m= effectifParLangueServices.getLangueVivante(idEcole ,anneeId) ;
        detailsBull.add(m);

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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=effectifParLanguevivante.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }


}
