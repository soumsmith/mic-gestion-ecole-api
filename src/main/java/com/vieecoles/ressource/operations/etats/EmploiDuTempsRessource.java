package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.EmploiDuTemps;
import com.vieecoles.dto.RapportRentreeDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.etats.EmploiDuTempsServices;
import com.vieecoles.services.etats.MatriceMoyenneServices;
import com.vieecoles.services.etats.RapportRentreeServices;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import java.util.logging.Logger;

@Path("/emploi-du-temps")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class EmploiDuTempsRessource {
    @Inject
    EntityManager em;
    Logger logger = Logger.getLogger(PersonnelMatiereClasseService.class.getName());
    @Inject
    MatriceMoyenneServices moyenneServices ;
    @Inject
    SousceecoleService sousceecoleService ;

    @Inject
    EmploiDuTempsServices rapportRentree ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("imprimer/{idEcole}/{idAnnee}/{classeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole , @PathParam("idAnnee") Long idAnnee ,@PathParam("classeId") Long classeId) throws Exception, JRException {

        parametre  mpara = new parametre();
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        mpara = parametre.findById(1L) ;
        String professPrincipal = null, educateur =null, directeurEtudes=null ;
        String libelleClasse = null;
        PersonnelMatiereClasse perm = new PersonnelMatiereClasse() ;
        perm= getPersonnelByClasseAndAnneeAndFonction(classeId,idAnnee ,1) ;
        PersonnelMatiereClasse perm2 = new PersonnelMatiereClasse() ;

        perm2= getPersonnelByClasseAndAnneeAndFonction(classeId,idAnnee ,2) ;

        if(perm2!=null)
        educateur= perm2.getPersonnel().getNom() +" "+ perm2.getPersonnel().getPrenom() ;

        if(perm!=null){
            professPrincipal= perm.getPersonnel().getNom() +" "+ perm.getPersonnel().getPrenom() ;
            libelleClasse = perm.getClasse().getLibelle() ;
        }


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
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Emploi_du_temps.jrxml");
        EmploiDuTemps emp= new EmploiDuTemps() ;

        List<EmploiDuTemps> detailsBull= new ArrayList<>() ;

        emp = rapportRentree.EmploiDuTemps(idEcole ,idAnnee ,classeId) ;

        detailsBull.add(emp);
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
        map.put("professPrincipal",professPrincipal);
        map.put("educateur",educateur);
        map.put("directeurEtudes",directeurEtudes);
        map.put("libelleClasse",libelleClasse);


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        //to pdf ;
        byte[] data =JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Emploi-du-temps-de-classe.pdf");
        return    ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
    }


    @GET
    @Transactional
    @Path("/infos/{idEcole}/{idAnnee}/{classeId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public EmploiDuTemps getInfos(@PathParam("idEcole") Long idEcole , @PathParam("idAnnee") Long idAnnee ,@PathParam("classeId") Long classeId){
   // return  rapportRentree.countProfByMatiereAndEcole(idEcole,idMatiere,idAnnee ,sexe ,niveau) ;
   return  rapportRentree.EmploiDuTemps(idEcole ,idAnnee ,classeId) ;
    }

    public PersonnelMatiereClasse getPersonnelByClasseAndAnneeAndFonction(Long classe, Long annee, int fonction) {
        PersonnelMatiereClasse pmc = null;
        try {
            pmc = PersonnelMatiereClasse
                    .find("classe.id = ?1 and annee.id= ?2 and personnel.fonction.id =?3 and matiere is null", classe,
                            annee, fonction)
                    .singleResult();
        } catch (RuntimeException e) {
            if (e.getClass().getName().equals(NoResultException.class.getName())) {
                logger.info(String.format("Aucun personnel educateur ou professeur principal [fonction : %s] trouvé ",
                        fonction));
            } else {
                e.printStackTrace();
            }
            return pmc;
        }
        return pmc;
    }


}
