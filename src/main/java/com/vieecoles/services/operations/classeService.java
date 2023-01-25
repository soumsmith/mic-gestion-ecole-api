package com.vieecoles.services.operations;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.operations.classe;

import java.net.URI;
import java.util.List;


@ApplicationScoped
public class classeService implements PanacheRepositoryBase<classe, Long> {
    @Inject
    EntityManager em;

    public  List<classe> findAllclasse(String tenant){
        return  em.createQuery("select o from classe o " +
                        "where o.niveau.tenant_tenantid =:tenant")
                .setParameter("tenant",tenant)
                .getResultList() ;

    }
    public List <classe> ListeClasseParEcole(String tenantId ){
        TypedQuery<classe> q = (TypedQuery<classe>)
                em.createQuery("select o from classe o join o.niveau n  " +
                        " where  n.tenant_tenantid =:idtenant ");
        List<classe> personnelSelect = q.setParameter("idtenant",tenantId).
                getResultList();

        return personnelSelect ;
    }


    public  List<classe> findByIdTypeclasse(Long idniv){

        return    em.createQuery("select o from classe o join fetch o.niveau h where h.niveauid =:idniv")
                .setParameter("idniv",idniv)
                .getResultList();

    }

    public  classe  findByIDclasse(Long idpobj){
        return classe.find("classeid",idpobj).singleResult();
    }




   public Response create(classe obj) {

       obj.persist();
       return Response.created(URI.create("/classe/" + obj)).build();
   }

   public  int updateclasse(classe obj){



       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update classe e set e.classecode =:code , e.classelibelle  =:libelle," +
                       " e.niveau=:idniv where e.classeid  =: classeId")
               .setParameter("code",obj.getClassecode())
               .setParameter("libelle",obj.getClasselibelle())
               .setParameter("idniv",obj.getNiveau())
               .setParameter("classeId",obj.getClasseid()).executeUpdate();
       return  q;
   }

    public void  deletclasse(long classeID){
        classe obj1 = classe.findById(classeID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<classe> search(String Libelle){

       return   em.createQuery("select o from classe o where  o.classelibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();

   }

    public  long count(){
        return  classe.count();
    }


}
