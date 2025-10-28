package com.vieecoles.ressource.operations.eleves;

import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.EleveMatriculeDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.dto.importEleveDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Inscription;
import com.vieecoles.steph.services.ClasseEleveService;
import java.util.ArrayList;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/eleve")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class eleveRessource {
    @Inject
    EleveService EleveService ;
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
  @Inject
  ClasseEleveService classeEleveService;

    @Inject


    @GET
  @Path("/eleveDto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EleveDto> list2() {
        return null ;
    }

  /*  @GET
    @Path("/eleve/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<eleve> list3(@PathParam("code") String code ) {
        return EleveService.listEleve(code);
    }*/


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("importer-eleve/{idEcole}/{idAnneeScolaire}/{typeOperation}/{idBranche}")
    public Response importerCreerEleve(List<importEleveDto> lisImpo , @PathParam("idEcole") Long idEcole, @PathParam("idAnneeScolaire") Long idAnneeScolaire,
                                     @PathParam("typeOperation") String typeOperation ,@PathParam("idBranche") Long idBranche ){
      // System.out.print("lisImpo "+lisImpo);
        try {
return  Response.ok().entity( EleveService.importerCreerEleve(lisImpo,idEcole,idAnneeScolaire,typeOperation,idBranche)).build() ;
        } catch (Exception e){
            return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
        }
    }
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("inscrire-eleves-vie-ecole/{idEcole}/{idAnneeScolaire}/{idBranche}/{idClasse}/{idNiveauEnseignement}")
  public Response importerCreerEleveVieEcole(List<importEleveDto> lisImpo , @PathParam("idEcole") String ecole,
                                             @PathParam("idAnneeScolaire") Long idAnneeScolaire,
                                     @PathParam("idBranche") Long idBranche ,@PathParam("idClasse") String idClasse ,
                                             @PathParam("idNiveauEnseignement") Long idNiveauEnseignement){
    // System.out.print("lisImpo "+lisImpo);

    Ecole ecole1 = Ecole.find("identifiantVieEcole =?1 and niveauEnseignement.id=?2",ecole,idNiveauEnseignement).firstResult();
    Classe classe = Classe.find("identifiantVieEcole =?1 and ecole.id=?2",idClasse,ecole1.getId()).firstResult();
    Long idClass= 0L;
    if(classe!=null){
      idClass= classe.getId();
    }

    try {
      List<Inscriptions> inscriptions = new ArrayList<>() ;
      List<Inscription> inscriptions2 = new ArrayList<>() ;
      if(ecole1==null){
        return  Response.ok().entity("Ecole introuvale sur pouls-scolaire").build();
      }
      inscriptions=  EleveService.inscrireEleveVieEcole(lisImpo,ecole,idAnneeScolaire,idBranche,idNiveauEnseignement);
      System.out.println("Longueur Tableau New"+inscriptions.size());
      if(inscriptions.size()>0){
        for (int i = 0; i < inscriptions.size(); i++){
          Inscription inscription = new Inscription();
          System.out.println("Identifiant à inscrire: "+inscriptions.get(i).getInscriptionsid());
          inscription = Inscription.findById(inscriptions.get(i).getInscriptionsid());
          inscriptions2.add(inscription);
        }
      }
      if(classe==null){
        return  Response.ok().entity("Classe introuvale sur pouls-scolaire").build();

      } else if(inscriptions.size()==0){
        return  Response.ok().entity("Aucune nouvelle inscription").build();
      }else {
    System.out.println("Affectation classe");
        return  Response.ok().entity( classeEleveService.handleCreate(idClass,inscriptions2)).build() ;
      }

    } catch (Exception e){
      return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
    }
  }




    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("importer-eleve/{idEcole}/{idAnneeScolaire}/{typeOperation}/{idBranche}")
    public Response importerMiseAjourEleve(List<importEleveDto> lisImpo , @PathParam("idEcole") Long idEcole, @PathParam("idAnneeScolaire") Long idAnneeScolaire,
                                         @PathParam("typeOperation") String typeOperation ,@PathParam("idBranche") Long idBranche ){
        // System.out.print("lisImpo "+lisImpo);

        try {
            return  Response.ok().entity(EleveService.importerMiseEleve(lisImpo,idEcole,idAnneeScolaire,typeOperation,idBranche)).build() ;
        } catch (Exception e){
            return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
        }

    }
  @PUT
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("modifier-eleves-vie-ecole/{ecole}/{idAnneeScolaire}/{typeOperation}/{idBranche}/{idNiveauEnseignement}")
  public Response modifierElevesVieEcole(List<importEleveDto> lisImpo , @PathParam("ecole") String ecole, @PathParam("idAnneeScolaire") Long idAnneeScolaire,
                                        @PathParam("idBranche") Long idBranche ,@PathParam("idNiveauEnseignement") Long idNiveauEnseignement){
    // System.out.print("lisImpo "+lisImpo);
    Ecole ecole1 = Ecole.find("identifiantVieEcole =?1 and niveauEnseignement.id=?2",ecole,idNiveauEnseignement).firstResult();
    if(ecole1==null){
      return  Response.ok().entity("Ecole introuvale sur pouls-scolaire").build();
    }
    try {
      return  Response.ok().entity(EleveService.modifierEleveVieEcole(lisImpo,ecole,idAnneeScolaire,idBranche,idNiveauEnseignement)).build() ;
    } catch (Exception e){
      return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
    }

  }
  @PUT
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("modifier-eleves-vie-ecole")
  public Response modifierElevesVieEcoleMatricule(List<EleveMatriculeDto> lisImpo ){
    try {
      return  Response.ok().entity(EleveService.modifierEleveMatricule(lisImpo)).build() ;
    } catch (Exception e){
      return  Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
    }

  }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)

    @Path("/{idEcole}/{idAnneeScolaire}/{statutEleve}/{typeOperation}/{idBranche}")

    public String  CreerNewEleve(EleveDto eleveDto ,@PathParam("idEcole") Long idEcole , @PathParam("idAnneeScolaire") Long idAnneeScolaire,@PathParam("statutEleve") String statutEleve ,
                                @PathParam("typeOperation") String typeOperation , @PathParam("idBranche") Long idBranche  ) {
       eleve elv =new eleve() ;
        elv=  EleveService.CreerUnEleve(eleveDto) ;

          System.out.print("eleve=="+elv);
        InscriptionDto inscriptionDto = new InscriptionDto() ;
        inscriptionDto.setIdentifiantEcole(idEcole);
        inscriptionDto.setIdentifiantAnnee_scolaire(idAnneeScolaire);
        inscriptionDto.setIdentifiantBranche(idBranche);
        Inscriptions.typeOperation myOperation= Inscriptions.typeOperation.valueOf(typeOperation);
        Inscriptions.statusEleve myStatutEleve = Inscriptions.statusEleve.valueOf(statutEleve);

        inscriptionDto.setInscriptions_statut_eleve(myStatutEleve);
        inscriptionDto.setIdentifiantEleve(elv.getEleveid());
        inscriptionDto.setInscriptions_type(myOperation);
        System.out.println("elevinscriptionDto =="+inscriptionDto.toString());

        System.out.println("idAnneeScolaire =="+idAnneeScolaire);

        System.out.println("matricule**** =="+elv.getEleve_matricule());
        return   inscriptionService.verifInscription(inscriptionDto,idEcole,elv.getEleve_matricule(),idAnneeScolaire,idBranche);

    }

    @PUT
    @Path("/{elevID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response UpdateEleve(EleveDto eleveDto ,@PathParam("elevID") Long elevID) {
        EleveService.updatEeleve(eleveDto,elevID) ;
        return  Response.ok("Succes").build() ;
    }

}
