package com.vieecoles.services.operations;

import com.vieecoles.dto.ecoleDto;
import com.vieecoles.dto.ecoleDto2;
import com.vieecoles.entities.operations.ecole;

import com.vieecoles.steph.entities.Ecole;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@ApplicationScoped
public class ecoleService implements PanacheRepositoryBase<ecole, Long> {
    @Inject
    EntityManager em;
    @Inject ecoleDto ecoleDto;
    public  List<ecole> findAllecole(){


         return   em.createQuery("select distinct o from ecole o   join o.cycles c join c.ecole ecole where  c.cycleid=1").getResultList();
    }

    public  List<ecoleDto> findAllecoleDto(){

        TypedQuery<ecoleDto> q = em.createQuery( "SELECT new com.vieecoles.dto.ecoleDto(o.ecolecode,o.ecoleclibelle,o.ecolearreteecreation,z.zonelibelle,q.quartierlibelle,g.groupe_ecolelibelle ,c.cyclelibelle) from ecole o join  o.quartier q join   o.zone z join  o.groupe_ecole g join o.cycles c join c.ecole ecole",
                ecoleDto.class);

        List<ecoleDto> listEcoleDto = q.getResultList();

        return  listEcoleDto;
    }

    public  List<ecoleDto2> findAllecoleDto2(){

 TypedQuery<ecoleDto2> q = em.createQuery( "SELECT new com.vieecoles.dto.ecoleDto2(o.ecoleid,o.ecolecode,o.ecoleclibelle) from ecole o where o.visible='1'",
                ecoleDto2.class);

 /*TypedQuery<ecoleDto2> q = em.createQuery( "SELECT new com.vieecoles.dto.ecoleDto2(o.ecoleid,o.ecolecode,o.ecoleclibelle) from ecole o ",
          ecoleDto2.class);
*/
        List<ecoleDto2> listEcoleDto = q.getResultList();

        return  listEcoleDto;
    }


/*
    public  List<ecole> findAllecoleAndCycle(){
        return  em.createQuery("select o from ecole o join fetch o.quartier join fetch o.zone join fetch o.groupe_ecole join fetch o.cycles ").getResultList() ;
    }

    public  List<ecole> findAllecolebyCycle(Long id_cyle){
        return  em.createQuery("select o from ecole o join fetch o.quartier join fetch o.zone join fetch o.groupe_ecole join fetch o.cycles c where c.cycleid=:id_cyle ")
                .setParameter("id_cyle",id_cyle)
                .getResultList() ;

    }



    public  List<ecole> findByIdTypegroupe_ecole(Long idniv){

        return    em.createQuery("select o from ecole o join fetch o.groupe_ecole h where h.groupe_ecoleeid =:idniv")
                .setParameter("idniv",idniv)
                .getResultList();

    }


    public  List<ecole> findByIdTypezone(Long idniv){

        return    em.createQuery("select o from ecole o join fetch o.zone h where h.zoneid =:idniv")
                .setParameter("idniv",idniv)
                .getResultList();

    }

    public  List<ecole> findByIdTypequartier(Long idniv){

        return    em.createQuery("select o from ecole o join fetch o.quartier h where h.quartierid =:idniv")
                .setParameter("idniv",idniv)
                .getResultList();

    } */



    public  ecole  findByIDecole(Long idpobj){
        return ecole.find("ecoleid",idpobj).singleResult();
    }

  public Ecole findByIDecole2(Long idpobj){
    return Ecole.findById(idpobj);
  }


   public Response create(ecole obj) {

       obj.persist();
       return Response.created(URI.create("/ecole/" + obj)).build();
   }

  /*  public  int updateecole(ecole obj){

       if(obj == null) {
           throw new NotFoundException();
       }
       int q = em.createQuery("update ecole e set  e.quartier=:quartier, e.zone=:zone, e.groupe_ecole=:groupe_ecole, e.ecolecode =:code , e.ecoleclibelle  =:libelle," +
                       " e.ecolearreteecreation=: arreteCreation, e.recoiaffecteetat =:recoiaffecteetat  where e.ecoleid  =: ecoleid")

               .setParameter("code",obj.getEcolecode())
               .setParameter("libelle",obj.getEcoleclibelle())
               .setParameter("arreteCreation",obj.getEcolearreteecreation())
               .setParameter("quartier",obj.getQuartier())
               .setParameter("zone",obj.getZone())
               .setParameter("groupe_ecole",obj.getGroupe_ecole())
               .setParameter("recoiaffecteetat",obj.isRecoiaffecteetat()).executeUpdate();
       return  q;
   }
 */
    public void  deletclasse(long ecoleID){
        ecole obj1 = ecole.findById(ecoleID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<ecole> search(String Libelle){

       return   em.createQuery("select o from ecole o where  o.ecoleclibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();

   }

    public  long count(){
        return  ecole.count();
    }


}
