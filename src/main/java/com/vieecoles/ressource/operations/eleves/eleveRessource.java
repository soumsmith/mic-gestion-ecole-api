package com.vieecoles.ressource.operations.eleves;

import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.dao.entities.operations.Inscriptions;
import com.vieecoles.dao.entities.operations.eleve;
import com.vieecoles.dto.importEleveDto;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.eleves.InscriptionService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("importer-eleve/{idEcole}/{idAnneeScolaire}/{typeOperation}")
    public String importerCreerEleve(List<importEleveDto> lisImpo , @PathParam("idEcole") Long idEcole, @PathParam("idAnneeScolaire") Long idAnneeScolaire,@PathParam("typeOperation") String typeOperation ){
        return  EleveService.importerCreerEleve(lisImpo,idEcole,idAnneeScolaire,typeOperation);
    }

    @POST
    @Path("/{idEcole}/{idAnneeScolaire}/{statutEleve}/{typeOperation}/{idBranche}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

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
        return   inscriptionService.verifInscription(inscriptionDto,idEcole,elv.getEleve_matricule(),idAnneeScolaire);

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
