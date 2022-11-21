package com.vieecoles.ressource.operations.eleves;

import com.vieecoles.dto.EleveDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Tag(name = "Eleve classe", description = "Eleve-Classe-Ressource")
@Path("/eleve-classe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class eleveClasseRessource {

    @Inject
    EntityManager em;
    @Inject
 //   Eleve_ClasseService eleve_classeService ;



    @GET
     @Path("/eleveDto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EleveDto> list2() {
        return null ;
    }

    /*@GET
    @Path("eleve-inscrit-classe/{identifiantAnnee}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EleveInscritClasseDto> listEleveInscritDto(@PathParam("identifiantAnnee") Long  identifiantAnnee) {
        return eleve_classeService.findEleveClasseInscrit2(identifiantAnnee) ;
    }

    @GET
    @Path("/count/{tenant}/{classe}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BigInteger getCount(@PathParam("tenant") String tenant , @PathParam("classe") Long classe) {
        return eleve_classeService.CountEleveParClasse(tenant,classe) ;
    }




    @POST
    @Path("/{ideleve}/{idClasse}/{anneeScoliare}/{inscriptionId}/{tenant}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response  attribuerClasseEleve(@PathParam("ideleve") Long ideleve ,@PathParam("idClasse") Long  idClasse
    ,@PathParam("anneeScoliare") Long  anneeScoliare ,@PathParam("inscriptionId") Long  inscriptionId ,@PathParam("tenant") String  tenant  ) {
            eleve_classeService.attribuerClasseEleve(ideleve,idClasse,anneeScoliare,inscriptionId,tenant) ;
        return  Response.ok("Crée avec succes").build() ;
      //  return  null ;
    }

    @PUT
    @Path("/{idEleveClasse}/{ideleve}/{idClasse}/{anneeScoliare}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response  modifierClasseEleve(@PathParam("idEleveClasse") Long idEleveClasse,@PathParam("ideleve") Long ideleve ,@PathParam("idClasse") Long idClasse ,@PathParam("anneeScoliare") Long  anneeScoliare
                                             ) {
         eleve_classeService.modifierClasseEleve(idEleveClasse,ideleve,idClasse ,anneeScoliare) ;
        return  Response.ok("Succes").build() ;
    }*/




}
