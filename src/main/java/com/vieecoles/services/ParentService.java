package com.vieecoles.services;

import com.vieecoles.dto.ParentDto;
import com.vieecoles.dao.entities.Parent;
import com.vieecoles.dao.entities.tenant;
import com.vieecoles.dao.entities.type_parent;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class ParentService implements PanacheRepositoryBase<Parent, Long> {
    @Inject
    EntityManager em;
   public List<Parent> getListparent(){
       return  Parent.listAll();
   }
   public  Parent findById(Long cycleId){
       return Parent.findById(cycleId);
   }

   public void CreerUnParent(ParentDto parentDto) {
      // System.out.println("TestSoum "+eleveDto.getIdTenant());
       tenant mytenant = (tenant) em.createQuery("select o from tenant o where  o.tenantid=:tenant")
                       .setParameter("tenant",parentDto.getIdentifiantTenant())
                               .getSingleResult() ;

       type_parent mytypeParent= type_parent.findById(parentDto.getIdentifiantTypeParent());

       System.out.println("Test00");
       Parent parent= new Parent() ;

       parent.setParent_tel(parentDto.getParent_tel());
       parent.setParent_profession(parentDto.getParent_profession());
       parent.setParent_tel2(parentDto.getParent_tel2());
       parent.setParentcode(parentDto.getParentcode());
       parent.setParentemail(parentDto.getParentemail());
       parent.setParentnom(parentDto.getParentnom());
       parent.setTypeParent(mytypeParent);
        parent.persist();
        System.out.println("Test2");
          }



   public  Parent updatParent(Long parentId, ParentDto parentDto){

       Parent parent = Parent.findById(parentId);

       if(parent == null) {
           throw new NotFoundException();
       }
       type_parent mytypeParent= type_parent.findById(parentDto.getIdentifiantTypeParent());
       parent.setParentprenom(parentDto.getParentprenom());
       parent.setTypeParent(mytypeParent);
       parent.setParent_tel2(parentDto.getParent_tel2());
       parent.setParent_tel(parentDto.getParent_tel());
       parent.setParentnom(parentDto.getParentnom());
       parent.setParentemail(parentDto.getParentemail());
       parent.setParentcode(parentDto.getParentcode());
       parent.setParent_profession(parentDto.getParent_profession());

        return  parent;
   }

    public void  deleteParent(long parentId){
        Parent parent = Parent.findById(parentId);
        if(parent == null) {
            throw new NotFoundException();
        }
        parent.delete();
    }



    public  long count(){
        return  Parent.count();
    }


}
