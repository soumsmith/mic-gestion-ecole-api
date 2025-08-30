package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.dto.spiderCIODto;
import com.vieecoles.dto.spiderLivretDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.entities.parametre;
import com.vieecoles.projection.LivretScolaireSelectDto;
import com.vieecoles.projection.LivretScolaireSpiderSelectDto;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.etats.LivretScolaireServices;
import com.vieecoles.services.etats.LivretScolaireServices2;
import com.vieecoles.services.souscription.SousceecoleService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Path("/livret-scolaire")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class LivretRessource2 {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;
    @Inject
    LivretScolaireServices2 livretScolaireServices ;
    @Inject
    LivretScolaireServices bulletinClasseServices ;

    private static String UPLOAD_DIR = "/data/";






        @GET
    @Path("/livret-scolaire2/{classe}/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public ResponseEntity<byte[]>  getdetailsLivret(@PathParam("classe") String classe,@PathParam("idEcole") Long idEcole,@PathParam("libelleAnnee") String libelleAnnee,
                                                      @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception, JRException {

            InputStream myInpuStream ;
            /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Livret_scolaireSpider.jrxml");

            List<LivretScolaireSpiderSelectDto>  detailsBull = new ArrayList<>() ;

            detailsBull= livretScolaireServices.livretScolaire(idEcole ,libelleTrimetre ,classe, libelleAnnee) ;

            System.out.println("dspsDto "+detailsBull.toString());

            spiderLivretDto dataSource = new spiderLivretDto(detailsBull);

           // JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
            Map<String, Object> map = new HashMap<>();
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, dataSource);

            //to pdf ;
            byte[] data =JasperExportManager.exportReportToPdf(report);
            HttpHeaders headers= new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Livret-scolaire.pdf");

            return    ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);

        }



}
