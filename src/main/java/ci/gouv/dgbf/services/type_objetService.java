package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.niveau;
import ci.gouv.dgbf.entities.type_objet;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class type_objetService implements PanacheRepositoryBase<type_objet, Long> {

   public List<type_objet> getListtype_objet(){
       return  type_objet.listAll();
   }
   public  type_objet findById(Long type_objetId){
       return type_objet.findById(type_objetId);
   }

   public Response createtype_objet(type_objet type_obj) {
       type_obj.persist();
       return Response.created(URI.create("/type_objet/" + type_obj.getType_objetcode())).build();
   }

   public  type_objet updatetype_obj(long type_objId, type_objet typ_obj){
       type_objet entity = type_objet.findById(type_objId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setType_objetcode(typ_obj.getType_objetcode());
       entity.setType_objetlibelle(typ_obj.getType_objetlibelle());
       return  entity;
   }

    public void  deletetype_objet(long type_objIdId){
        type_objet entity = type_objet.findById(type_objIdId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<type_objet> search(String type_objetLibelle){
       return  type_objet.find("type_objetlibelle",type_objetLibelle).list() ;
   }

    public  long count(){
        return  type_objet.count();
    }


}
