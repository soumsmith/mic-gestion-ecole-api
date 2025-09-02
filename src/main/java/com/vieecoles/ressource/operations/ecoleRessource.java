package com.vieecoles.ressource.operations;

import com.vieecoles.dto.ecoleDto;
import com.vieecoles.entities.Zone;
import com.vieecoles.entities.cycle;
import com.vieecoles.entities.groupe_ecole;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.quartier;
import com.vieecoles.services.operations.ecoleService;
import com.vieecoles.steph.entities.Ecole;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/ecole")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ecoleRessource {
    @Inject
    ecoleService matService ;
    @Inject
    EntityManager em;
/*     @GET
    @Path("groupe/{groupe}")
    public List<ecole> list(@PathParam("groupe") Long groupe) {
        return matService.findByIdTypegroupe_ecole(groupe);
    } */

    /* @GET
    @Path("zone/{zone}")
    public List<ecole> listbyzone(@PathParam("zone") Long zone) {
        return matService.findByIdTypezone(zone);
    }

    @GET
    @Path("quartier/{quartier}")
    public List<ecole> listbyquartier(@PathParam("quartier") Long quartier) {
        return matService.findByIdTypequartier(quartier);
    }
    @GET
    @Path("cycle/{cycle}")
    public List<ecole> listbycycle(@PathParam("cycle") Long cycle) {
        return matService.findAllecolebyCycle(cycle);
    } */

    @GET
        public List<ecole> list2() {
        return matService.findAllecole();
    }


    @GET
    @Path("/ecole-dto")
        public List<ecoleDto> list3() {
        return matService.findAllecoleDto();
    }

   /*  @GET
    @Path("cycle")
    public List<ecole> listCycle() {
        return matService.findAllecoleAndCycle();
    }
 */

    @GET
    @Path("/{id}")
    public ecole get(@PathParam("id") Long id) {
        return matService.findByIDecole(id);
    }

    @GET
    @Path("new/{id}")
    public Ecole get2(@PathParam("id") Long id) {
        return matService.findByIDecole2(id);
    }

    @POST
    @Path("/{creerEcole}")
    public Response CreerEcole(ecole myEcole) {
        myEcole.persist();
        return Response.created(URI.create("/ecole/" + myEcole)).build();
    }



    @POST
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
        Zone zone= em.getReference(Zone.class,ecole_zone);
        quartier quartier= em.getReference(quartier.class,ecole_quartier);
        cycle cycle= em.getReference(cycle.class,ecole_cycle);
        ArrayList<cycle> al1=new ArrayList<cycle>();
        al1.add(cycle);
        ecole obj = new ecole();

        obj.setEcoleclibelle(ecole_libelle);
        obj.setEcolecode(ecole_code);
        obj.setRecoiaffecteetat(recoiaffecteetat);
        obj.setEcolearreteecreation(arreteCreation);



        return matService.create(obj);
    }

/*     @PUT
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
        Zone zone= em.getReference(Zone.class,ecole_zone);
        quartier quartier= em.getReference(quartier.class,ecole_quartier);

        ecole obj = new ecole();
        obj.setEcoleid(ecole_id);
        obj.setEcoleclibelle(ecole_libelle);
        obj.setEcolecode(ecole_code);
        obj.setRecoiaffecteetat(recoiaffecteetat);
        obj.setEcolearreteecreation(arreteCreation);

    return matService.updateecole(obj);

    } */
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


}
