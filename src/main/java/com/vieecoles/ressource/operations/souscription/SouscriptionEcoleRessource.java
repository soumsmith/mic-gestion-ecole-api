package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.sousc_atten_etabliss;
import com.vieecoles.entities.r_directeur_des_etudes;
import com.vieecoles.entities.r_info_etablissement;
import com.vieecoles.entities.r_info_fondateur;
import com.vieecoles.services.personnels.PersonnelService;
import com.vieecoles.services.souscription.SousceecoleService;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Souscription-Ecole", description = "mes Souscriptions-Ecole")
@Path("/souscription-ecole")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SouscriptionEcoleRessource {
    @Inject
    SousceecoleService souscPersonnelService ;
    @Inject
    EntityManager em;

    @Inject
    PersonnelService personnelService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<sousc_atten_etabliss> getAllSouscription() {
        return souscPersonnelService.getAllSouscriptionPersonnels() ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("email/{emailEtablissement}")
    public sousc_atten_etabliss getSouscriptionByEmail(@PathParam("emailEtablissement") String emailEtablissement) {
        return souscPersonnelService.getSouscripByEmail2(emailEtablissement) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("demande-ecoles-fondateur/{idFondat}")
    public List<ecoleDto2> getEcoleFondateur(@PathParam("idFondat") Long idFondat) {
        return souscPersonnelService.getAllEcoleBySouscripFondateur(idFondat) ;
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("allSouscriptionEcoles/{status}")
    public List<etablissementDto> getAllSouscriptionEcoleS(@PathParam("status") String status) {
        Inscriptions.status status1= Inscriptions.status.valueOf(status);
        return souscPersonnelService.listTousLesSouscrEcole(status1) ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("allSouscriptionEcoles-fondateur/{idsouscrip}")
    public List<souscriptionEcoleDto> getAllSouscriptionEcoleSParFondateur(@PathParam("idsouscrip") Long idsouscrip) {
       // Inscriptions.status status1= Inscriptions.status.valueOf(status);
        return souscPersonnelService.listTousLesSouscrEcoleParFondateur(idsouscrip);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("souscri-etabliss-a-modifier-fondateur/{idsouscrip}")
    public List<etablissementAmodifierDto> getSouscripAmodifierParFondateur(@PathParam("idsouscrip") Long idsouscrip) {
        // Inscriptions.status status1= Inscriptions.status.valueOf(status);
        return souscPersonnelService.getListSouscEcoleParFondateur(idsouscrip) ;
    }



    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/valider-ecoles/")
        public Response validerSouscription(souscriptionValidationDto sousValid) {
        souscPersonnelService.creerEtValiderEcole(sousValid) ;
        //souscPersonnelService.validerSouscriptionEcole(sousValid);
        return   Response.ok(String.format("Inscription  %s mis à jour",sousValid.getStatuts())).build();
    }



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    @Path("creer-ecole-by-souscription/{idsouscrip}")
    public void souscriptionEtablissementCreer(@PathParam("idsouscrip")Long idsouscrip){
      souscPersonnelService.creerEcoleBySouscrip(idsouscrip) ;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("souscription-etablissement")
    public String souscriptionEtablissement(@QueryParam("fonctionId") Long fonctionId ,@QueryParam("nom")String nom,@QueryParam("prenom") String prenom,@QueryParam("contact1") String contact1 ,@QueryParam("contact2")String contact2 ,@QueryParam("email")String email ,@QueryParam("passWord")String passWord ,@QueryParam("login")String login ,List<sous_attent_ecoleDto> listsouscr ){
        System.out.println("Fondateur1 "+login);
     return   souscPersonnelService.creerSouscripEtablissement(fonctionId,nom,prenom ,contact1,contact2,email,passWord,login,listsouscr) ;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("ajouter/souscription-etablissement")
    public String ajoutersouscriptionEtablissement(@QueryParam("idSouscrip")Long idSouscrip ,List<sous_attent_ecoleDto> listsouscr ){
        return   souscPersonnelService.ajouterLesEcoles(idSouscrip,listsouscr) ;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("souscription-etablissement")
    public String updatesouscriptionEtablissement(@QueryParam("souscripId") Long souscripId ,@QueryParam("nom")String nom,@QueryParam("prenom") String prenom,@QueryParam("contact1") String contact1 ,@QueryParam("contact2")String contact2 ,@QueryParam("email")String email ,List<sous_attent_ecoleDto> listsouscr ){
        return   souscPersonnelService.ModifierSouscripEtablissement(souscripId,nom,prenom ,contact1,contact2,email ,listsouscr) ;
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON,MediaType.MULTIPART_FORM_DATA})
    @Path("/charger-photo-etablissement/{inscriptionId}")
    @Transactional
    public Response chargerPhoto(@MultipartForm MultipartFormDataInput input,  @PathParam("inscriptionId") String   inscriptionId ) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<String> fileNames = new ArrayList<>();

        List<InputPart> inputParts = uploadForm.get("file");
        System.out.println("inputParts size: " + inputParts.size());
        String fileName = null;
        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                fileNames.add(fileName);
                System.out.println("File Name: " + fileName);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                souscPersonnelService.chargerPhotoBulletinEcole(bytes,inscriptionId) ;
                //matService.chargerPhoto(bytes,inscriptionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return   Response.ok(String.format("Inscription  %s mis à jour",inscriptionId)).build();
    }
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON,MediaType.MULTIPART_FORM_DATA})
    @Path("/charger-filigrane-etablissement/{inscriptionId}")
    @Transactional
    public Response chargerFiligrane(@MultipartForm MultipartFormDataInput input, @PathParam("inscriptionId") String   inscriptionId ) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<String> fileNames = new ArrayList<>();

        List<InputPart> inputParts = uploadForm.get("file");
        System.out.println("inputParts size: " + inputParts.size());
        String fileName = null;
        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                fileNames.add(fileName);
                System.out.println("File Name: " + fileName);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                souscPersonnelService.chargerFiligraneEcole(bytes,inscriptionId) ;
                //matService.chargerPhoto(bytes,inscriptionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return   Response.ok(String.format("Inscription  %s mis à jour",inscriptionId)).build();
    }



    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }



    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String   CreerSouscription(sous_attent_ecoleDto mySouscrip) {
    return     souscPersonnelService.CreerSousCrietabliss(mySouscrip) ;

    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/rapport-infos-etablissement")
    @Transactional
    public String   rapportEtblissement(r_info_etablissement et) {
        return     souscPersonnelService.creationMiseAjourInfosEtablisse(et) ;

    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/rapport-infos-directeur_etude")
    @Transactional
    public String   rapportDirecteurEtude(r_directeur_des_etudes et) {
        return     souscPersonnelService.creationMiseAjourInfosDirecteurEtude(et);

    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/rapport-infos-fondateur")
    @Transactional
    public String   rapportFondateur(r_info_fondateur et) {
        return     souscPersonnelService.creationMiseAjourInfosFondatateur(et);

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public sousc_atten_etabliss UpdateSouscripEcole(sous_attent_ecoleDto mySouscrip) {
     return    souscPersonnelService.modifierSousEcolel(mySouscrip) ;
    }

    @DELETE
    @Path("/{identifiantSouscrip}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public void deletePersonnel(@PathParam("identifiantSouscrip") Long identifiantSouscrip) {
        souscPersonnelService.deleteSousCriptionPersonnel(identifiantSouscrip);
    }



}
