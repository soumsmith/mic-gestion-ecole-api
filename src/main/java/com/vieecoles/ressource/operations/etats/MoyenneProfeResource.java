package com.vieecoles.ressource.operations.etats;

import com.vieecoles.dto.EtatNominatifEnseignatDto;
import com.vieecoles.dto.MoyenParProfDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.MoyenneParProfServices;
import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.services.NoteService;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/moyenneProf")
public class MoyenneProfeResource {

    @Inject
    MoyenneParProfServices moyenneParProfServices ;
    @Inject
    PersonnelMatiereClasseService persMatClasService;


    @GET
    @Path("/infos/{classe}/{matiere}/{annee}/{periode}")
    @Operation(description = "Obtenir les notes des eleves d une classe par periode ", summary = "")

    public List<MoyenParProfDto> getNotesByClasseAndMatiereAndPeriode(@PathParam("classe") String classe, @PathParam("matiere") String matiere, @PathParam("annee") String annee, @PathParam("periode") String periode) {
    	return moyenneParProfServices.moyenneParProf(classe,matiere, annee, periode);
    }

    @GET
    @Transactional
    @Path("/{classe}/{matiere}/{annee}/{periode}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]> getDtoRapport(@PathParam("classe") String classe, @PathParam("matiere") String matiere, @PathParam("annee") String annee, @PathParam("periode") String periode) throws Exception, JRException {
      String adresse, telephone ,code, statut,libelleEcole,matiereLibelle ,professeurs ;
        BufferedImage logo= null ,bg= null, amoirie= null;
        EcoleHasMatiere ecoleHasMatiere= new EcoleHasMatiere() ;
        ecoleHasMatiere = EcoleHasMatiere.findById(Long.valueOf(matiere));
        matiereLibelle = ecoleHasMatiere.getLibelle() ;
        PersonnelMatiereClasse personnel = persMatClasService.findProfesseurByMatiereAndClasse(Long.valueOf(annee),Long.valueOf(classe) ,Long.valueOf(matiere) );
        professeurs = personnel.getPersonnel().getNom()+" "+personnel.getPersonnel().getPrenom() ;

       // professeurs = ecoleHasMatiere.getMatiere().
        Classe myClasse= new Classe() ;
        myClasse = Classe.findById(Long.valueOf(classe)) ;

        parametre mpara = new parametre();
        mpara = parametre.findById(1L) ;
        Ecole myEcolie = myClasse.getEcole() ;
        ecole monEcole = new ecole() ;
        monEcole = ecole.findById(myEcolie.getId());
        adresse = monEcole.getEcole_adresse() ;
        telephone = monEcole.getEcole_telephone();
        statut =monEcole.getEcole_statut();
        code = monEcole.getEcolecode() ;
        libelleEcole =monEcole.getEcoleclibelle();
        InputStream myInpuStream ;
        byte[] imagebytes2 = monEcole.getLogoBlob() ;
        byte[] imagebytes3 = mpara.getImage() ;
        byte[] imagebytes4 = monEcole.getFiligramme() ;


        if(imagebytes2!=null){
            logo= ImageIO.read(new ByteArrayInputStream(imagebytes2));
        }

        if(imagebytes3!=null){
            amoirie = ImageIO.read(new ByteArrayInputStream(imagebytes3));
        }

        if(imagebytes4!=null){
            bg = ImageIO.read(new ByteArrayInputStream(imagebytes4)) ;
        }

        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/

        List<MoyenParProfDto> detailsBull= new ArrayList<>() ;

        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/MoyenneParProf.jrxml");

        detailsBull = moyenneParProfServices.moyenneParProf(classe,matiere, annee, periode);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        map.put("adresse",adresse);
        map.put("logo",logo);
        map.put("telephone",telephone);
        map.put("code",code);
        map.put("statut",statut);
        map.put("libelleEcole",libelleEcole);
        map.put("matiereLibelle",matiereLibelle);
        map.put("professeurs",professeurs);
        map.put("classe",myClasse.getLibelle());
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=MoyenneParProfesseur.pdf");

        return    ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
    }


}
