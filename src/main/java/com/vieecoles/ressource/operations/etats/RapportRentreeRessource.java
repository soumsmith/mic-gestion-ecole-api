package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.MatriceMoyenneDto;
import com.vieecoles.dto.RapportRentreeDto;
import com.vieecoles.dto.matriceDspsDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.MatriceMoyenneServices;
import com.vieecoles.services.etats.RapportRentreeServices;
import com.vieecoles.services.souscription.SousceecoleService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@Path("/rapport-rentree")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class RapportRentreeRessource {
    @Inject
    EntityManager em;
    @Inject
    MatriceMoyenneServices moyenneServices ;
    @Inject
    SousceecoleService sousceecoleService ;

    @Inject
    RapportRentreeServices rapportRentree ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/{idEcole}/{idAnnee}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole , @PathParam("idAnnee") Long idAnnee) throws Exception, JRException {

        parametre  mpara = new parametre();
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        mpara = parametre.findById(1L) ;

        byte[] imagebytes2 = myEcole.getLogoBlob() ;
        byte[] imagebytes3 = mpara.getImage() ;
        byte[] imagebytes4 = myEcole.getFiligramme() ;
        BufferedImage logo= null ,amoirie= null,bg= null;
        String libelleEcole , adresse, telephone , code,statut ;
        libelleEcole= myEcole.getEcoleclibelle() ;
        adresse= myEcole.getEcole_adresse() ;
        telephone= myEcole.getEcole_telephone() ;
        code = myEcole.getEcolecode() ;
        statut = myEcole.getEcole_statut() ;

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
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Rapport_de_rentree.jrxml");
        List<RapportRentreeDto> detailsBull= new ArrayList<>() ;

              detailsBull = rapportRentree.rapportRentree(idEcole,idAnnee);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();

        map.put("logo",logo);
        map.put("amoirie",amoirie);
        map.put("bg",bg);
        map.put("libelleEcole",libelleEcole);
        map.put("adresse",adresse);
        map.put("telephone",telephone);
        map.put("code",code);
        map.put("statut",statut);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(report));
        // File exportReportFile = new File("profils" + ".docx");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=BulletinBean.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);


    }


    @GET
    @Transactional
    @Path("/infos/{idEcole}/{idAnnee}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<RapportRentreeDto> getInfos(@PathParam("idEcole") Long idEcole , @PathParam("idAnnee") Long idAnnee){
   // return  rapportRentree.countProfByMatiereAndEcole(idEcole,idMatiere,idAnnee ,sexe ,niveau) ;
   return  rapportRentree.rapportRentree(idEcole,idAnnee);
    }



}
