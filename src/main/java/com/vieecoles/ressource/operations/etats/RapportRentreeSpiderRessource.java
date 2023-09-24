package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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

@Path("/imprimer-rapport-rentree-spider")

public class RapportRentreeSpiderRessource {
    @Inject
    EntityManager em;
    @Inject
    EtatNominatifEnseignantServices etatNominatifEnseignantServices ;
    @Inject
    RecapitulatifStatistiqueServices recapitulatifStatistiqueServices ;
    @Inject
    RapportRentreeServices rapportRentree ;
    @Inject
    EffectifParLangueServices effectifParLangueServices ;

    @Inject
    RecapitulatifStatistiqueServices recapitulatif ;
    @Inject
    RecapitulatifStatistiqueBilan2Services recapitulatiBilan ;

    @Inject
    ComparatifServices comparatifServices ;




    @GET
    @Transactional
    @Path("/infos/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<PyramideEffectDto>  getDtoRapport2(@PathParam("idEcole") Long idEcole , @PathParam("anneeId") Long anneeId) throws Exception, JRException {

        return  recapitulatifStatistiqueServices.getPyramide(idEcole,anneeId,4);
    }



    @GET

    @Transactional
    @Path("/{idEcole}/{anneeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport6(@PathParam("idEcole") Long idEcole , @PathParam("anneeId") Long anneeId) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/RapportDerentree/Spider_RapportRentre.jrxml");


        SpiderRapportRentreeDto detailsBull= new SpiderRapportRentreeDto() ;
        List<RapportRentreeDto> rapportRentreeDto = new ArrayList<>() ;
        List<PyramideEffectDto> pyramideEffectDto1 = new ArrayList<>() ;
        List<PyramideEffectDto> pyramideEffectDto2 = new ArrayList<>() ;
        List<PyramideEffectDto> pyramideEffectDtoBilan = new ArrayList<>() ;
        List<EtatNominatifEnseignatDto> etatNominatifEnseignatDto = new ArrayList<>() ;
        List<EffectifElevLangueVivante2Dto> effectifElevLangueVivante2Dto = new ArrayList<>() ;
            List<ComparatifDto> comparatifDto = new ArrayList<>() ;

        EffectifElevLangueVivante2Dto m = new EffectifElevLangueVivante2Dto() ;

        rapportRentreeDto=   rapportRentree.rapportRentree(idEcole,anneeId);
        System.out.println("Profil Enseignant ok");
        pyramideEffectDto1 = recapitulatif.getPyramide(idEcole,anneeId,4) ;
        System.out.println("Pyramide 1er cycle ok");
        pyramideEffectDto2 = recapitulatif.getPyramide(idEcole,anneeId,5) ;
        System.out.println("Pyramide 2nd cycle ok");
        pyramideEffectDtoBilan = recapitulatiBilan.getPyramide(idEcole,anneeId);
        System.out.println("Pyramide Bilan ok");
        etatNominatifEnseignatDto= etatNominatifEnseignantServices.infosEtatNominEnseignant(idEcole ,anneeId) ;
        System.out.println("Etat nominatif  ok");
        m = effectifParLangueServices.getLangueVivante(idEcole,anneeId);
        System.out.println("Langue vivante  ok");
        ComparatifDto  n= new ComparatifDto() ;
        n = comparatifServices.getComparatif(idEcole ,anneeId) ;
        System.out.println("Comparatif   ok");
        comparatifDto.add(n);

        effectifElevLangueVivante2Dto.add(m);

        detailsBull.setPyramideEffectDto1(pyramideEffectDto1);
        detailsBull.setPyramideEffectDto2(pyramideEffectDto2);
        detailsBull.setPyramideEffectDtoBilan(pyramideEffectDtoBilan);
        detailsBull.setRapportRentreeDto(rapportRentreeDto);
        detailsBull.setEffectifElevLangueVivante2Dto(effectifElevLangueVivante2Dto);
        detailsBull.setEtatNominatifEnseignatDto(etatNominatifEnseignatDto);
        detailsBull.setComparatifDto(comparatifDto);


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
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport de rentree.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }


}
