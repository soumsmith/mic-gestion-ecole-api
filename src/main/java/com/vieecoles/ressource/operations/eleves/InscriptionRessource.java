package com.vieecoles.ressource.operations.eleves;

import com.vieecoles.dto.InscriptionAvaliderDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
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
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Inscription", description = "ressources Inscription")
@Path("/inscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InscriptionRessource {
    @Inject
    InscriptionService matService ;
    @Inject
    EntityManager em;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("allInscription/{idEcole}/{idAnnee}/{typeInscription}")
    public List<InscriptionAvaliderDto> inscriptionsAllInscrip(@PathParam("idEcole") Long idEcole,@PathParam("idAnnee") Long idAnnee,@PathParam("typeInscription") String typeInscription) {
        System.out.println("entree");
        Inscriptions.typeOperation typeOperation= Inscriptions.typeOperation.valueOf(typeInscription);
           return matService.listTousLesInscription(idEcole,idAnnee,typeOperation) ;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("statuts/{idEcole}/{idAnnee}/{myStatus}/{typeInscription}")
    public List<InscriptionAvaliderDto> inscriptionsByStatus(@PathParam("idEcole") Long idEcole,@PathParam("idAnnee") Long idAnnee,@PathParam("myStatus") String myStatus,@PathParam("typeInscription") String typeInscription) {
        System.out.println("entree");
        Inscriptions.typeOperation typeOperation= Inscriptions.typeOperation.valueOf(typeInscription);
        Inscriptions.status status= Inscriptions.status.valueOf(myStatus);
        return matService.listInscription(idEcole,idAnnee,status,typeOperation) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/ecole-eleve/{ecoleID}/{eleveID}")
     public List<Inscriptions> inscriptionsByEleveEcole(@PathParam("ecoleID") Long idEcole,@PathParam("eleveID") Long idEleve) {
        return matService.getListInscriptionByIdEcoleAndIdEleve(idEcole,idEleve);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/en-attente/{tenant}/{statut}/{processus}")
    public List<InscriptionAvaliderDto> inscriptionAvalider(@PathParam("tenant") String  tenant ,@PathParam("statut") Inscriptions.status  statut,
                                                            @PathParam("processus") Inscriptions.processus  processus) {
        return matService.findAllInscriptionAvaliderDto(tenant,statut,processus) ;
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/valider-inscription/{inscriptionId}")
    @Transactional
    public Response validerInscription(@PathParam("inscriptionId") Long   inscriptionId ) {
        matService.validerInscription(inscriptionId);
        return   Response.ok(String.format("Inscription  %s mis à jour",inscriptionId)).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON,MediaType.MULTIPART_FORM_DATA})
    @Path("/charger-photo/{inscriptionId}")
    @Transactional
    public Response chargerPhoto(@MultipartForm MultipartFormDataInput input,@PathParam("inscriptionId") Long   inscriptionId ) {
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
                matService.chargerPhoto(bytes,inscriptionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return   Response.ok(String.format("Inscription  %s mis à jour",inscriptionId)).build();
    }



    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateIns(InscriptionDto inscriptionDto) {
             matService.updateIns(inscriptionDto);
        return   Response.ok(String.format("Inscription  %s mis à jour",inscriptionDto.getInscriptionsid())).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/verifier-infos-a-jour/{idInscrip}")
    public String checkInfosAjour(@PathParam("idInscrip") Long  idInscrip) {
         return   matService.checkInfosAjour(idInscrip);
    }


    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/infos-complementaires")
    @Transactional
    public Response updateInfosComplementaire(InscriptionDto inscriptionDto) {
        matService.updateInfosComplementaire(inscriptionDto);
        return   Response.ok(String.format("Inscription  %s mis à jour",inscriptionDto.getInscriptionsid())).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/libelle-handicap/{InscriptionId}/{oldHandicap}/{newHandicap}")
    @Transactional
    public Response updateLibelleHandicapIns(@PathParam("InscriptionId")Long InscriptionId ,@PathParam("oldHandicap") Long oldHandicap ,@PathParam("newHandicap") Long newHandicap) {
        matService.updatelibelleHandicap_inscrip(InscriptionId,oldHandicap,newHandicap);
        return   Response.ok(String.format("Handicap   %s mis à jour",InscriptionId)).build();



    }





    @DELETE
    @Transactional
    public void delete(InscriptionDto inscriptionDto ) {
     matService.deleteInscriptions(inscriptionDto);
    }
    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


    @POST

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response CreerInscription(InscriptionDto inscriptionDto) {
       matService.createinscription(inscriptionDto);
return  Response.ok("Succes").build() ;

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

}
