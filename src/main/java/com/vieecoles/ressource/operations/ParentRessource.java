package com.vieecoles.ressource.operations;

import com.vieecoles.dto.ParentDto;
import com.vieecoles.entities.Parent;
import com.vieecoles.services.ParentService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/parent")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParentRessource {
    @Inject
    ParentService parentService;
    @Inject
    EntityManager em;


    @GET

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Parent> list2() {
        return parentService.getListparent();
    }

    @GET
    @Path("/{parentid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Parent list3(@PathParam("parentid") Long parentid){
        return parentService.findById(parentid);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response CreerNewParent(ParentDto parentDto) {
        parentService.CreerUnParent(parentDto);
         return  Response.ok("Succes").build() ;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response UpdateParent(Long parentid ,ParentDto parentDto) {
        parentService.updatParent(parentid ,parentDto) ;
        return  Response.ok("Succes").build() ;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response DeleteParent(Long parentid ) {
     parentService.deleteParent(parentid);
        return  Response.ok("Succes").build() ;
    }


   /* @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response create(  @FormParam("ecole_code") String ecole_code,
                             @FormParam("ecole_libelle") String ecole_libelle ,
                             @FormParam("recoiaffecteetat") boolean recoiaffecteetat,
                             @FormParam("arreteCreation") String arreteCreation,
                             @FormParam("ecole_zone") Long ecole_zone ,
                             @FormParam("ecole_quartier") Long ecole_quartier ,
                             @FormParam("ecole_groupe") Long ecole_groupe ,
                             @FormParam("ecole_cycle") Long ecole_cycle ) {

        groupe_ecole groupe_ecole= em.getReference(groupe_ecole.class,ecole_groupe);
        zone zone= em.getReference(zone.class,ecole_zone);
        quartier quartier= em.getReference(quartier.class,ecole_quartier);
        cycle cycle= em.getReference(cycle.class,ecole_cycle);
        ArrayList<cycle> al1=new ArrayList<cycle>();
        al1.add(cycle);
        ecole obj = new ecole();

        obj.setEcoleclibelle(ecole_libelle);
        obj.setEcolecode(ecole_code);
        obj.setRecoiaffecteetat(recoiaffecteetat);
        obj.setEcolearreteecreation(arreteCreation);
        obj.setGroupe_ecole(groupe_ecole);
        obj.setQuartier(quartier);
        obj.setCycles(al1);
        obj.setZone(zone);


        return matService.create(obj);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public int update(
            @FormParam("ecole_code") String ecole_code,
            @FormParam("ecole_id") Long ecole_id,
            @FormParam("ecole_libelle") String ecole_libelle ,
            @FormParam("recoiaffecteetat") boolean recoiaffecteetat,
            @FormParam("arreteCreation") String arreteCreation,
            @FormParam("ecole_zone") Long ecole_zone ,
            @FormParam("ecole_quartier") Long ecole_quartier ,
            @FormParam("ecole_groupe") Long ecole_groupe
    ) {
        groupe_ecole groupe_ecole= em.getReference(groupe_ecole.class,ecole_groupe);
        zone zone= em.getReference(zone.class,ecole_zone);
        quartier quartier= em.getReference(quartier.class,ecole_quartier);

        ecole obj = new ecole();
        obj.setEcoleid(ecole_id);
        obj.setEcoleclibelle(ecole_libelle);
        obj.setEcolecode(ecole_code);
        obj.setRecoiaffecteetat(recoiaffecteetat);
        obj.setEcolearreteecreation(arreteCreation);
        obj.setGroupe_ecole(groupe_ecole);
        obj.setQuartier(quartier);
        obj.setZone(zone);
    return matService.updateecole(obj);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletclasse(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<ecole> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }
*/

}
