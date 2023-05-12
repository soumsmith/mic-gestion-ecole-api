package com.vieecoles.ressource;

import com.vieecoles.dto.publicationDto;
import com.vieecoles.entities.Niveau;
import com.vieecoles.entities.postuler;
import com.vieecoles.projection.publicationCandidatSelectDto;
import com.vieecoles.projection.publicationSelectDto;
import com.vieecoles.services.niveauService;
import com.vieecoles.services.publications.postulerService;
import com.vieecoles.services.publications.publicationService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/publications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class publicationRessource {
    @Inject
    publicationService pubService ;
    @Inject
    postulerService postService ;


    @GET
    @Path("/{idEcole}")
    public List<publicationSelectDto> get(@PathParam("idEcole") Long idEcole) {
        return pubService.getListPublication(idEcole) ;
    }

    @GET
    @Path("rechercher")
    public List<publicationSelectDto> rechercherOffre(@QueryParam("ListNiveau") Long ListNiveau, @QueryParam("idEcole") Long idEcole ,
                                                      @QueryParam("typeOfre") Long typeOfre,
                                                      @QueryParam("typeFonction") Long typeFonction,
                                                      @QueryParam("periode") int periode) {
        return pubService.rechercherPublication(ListNiveau,idEcole,typeOfre,typeFonction,periode) ;
    }
    @GET
    @Path("candidat/{idCandidat}")
    public List<publicationSelectDto> getPublicationCandidat(@PathParam("idCandidat") Long idCandidat) {
        return pubService.getPublicationCandidat(idCandidat) ;
    }

    @GET
    @Path("candidat-postuler/ecole/{idEcole}/{periode}")
    public List<publicationCandidatSelectDto> getPublicationPostulerCandidat(@PathParam("idEcole") Long idEcole,@PathParam("periode") int periode) {
        return pubService.getListCandidatPostulerParEcole(idEcole,periode);
    }

    @POST
    @Transactional
    public void create(publicationDto pub) {
        System.out.print("Publication :"+pub.toString());
        pubService.creerUnePublicationFin(pub); ;
    }

    @POST
    @Transactional
    @Path("postuler/")
    public void Postuler(postuler post) {
        postService.creerPostuler(post); ;
    }

    @PUT
    @Transactional
    public void modifier(publicationDto pub) {
        pubService.modifierUnePublication(pub);
    }


    @PUT
    @Path("fermer/{idPublication}")
    @Transactional
    public void FermerOfrre(@PathParam("idPublication") Long idPublication) {
        pubService.fermerOfrre(idPublication);
    }



}
