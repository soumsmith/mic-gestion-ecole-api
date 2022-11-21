package com.vieecoles.services.eleves;

import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.entities.Libellehandicap;
import com.vieecoles.entities.Parent;
import com.vieecoles.entities.cycle;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.entities.tenant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class EleveService implements PanacheRepositoryBase<eleve, Long> {
    @Inject
    EntityManager em;

    @Inject
    InscriptionService inscriptionService ;
   public List<eleve> getListcycle(){
       return  eleve.listAll();
   }
   public  eleve findById(Long cycleId){
       return eleve.findById(cycleId);
   }
@Transactional
   public eleve   CreerUnEleve(EleveDto eleveDto) {

      System.out.println("TestSoum "+eleveDto.getIdTenant());
   tenant mytenant = (tenant) em.createQuery("select o from tenant o where   o.tenantid=:tenant ")
                       .setParameter("tenant",eleveDto.getIdTenant())
                               .getSingleResult() ;
     System.out.println("tenant "+mytenant.toString());

       List<Parent> parentsList= new ArrayList<>() ;
      // System.out.println( "Longueur"+ eleveDto.getParentList().size());
       for(int i = 0 ; i < eleveDto.getParentList().size() ; i++)
       {
           Parent myParent;
           myParent= Parent.findById(eleveDto.getParentList().get(i)) ;
           parentsList.add(myParent) ;
       }

       eleve myElev = new eleve() ;

       myElev.setEleveprenom(eleveDto.getEleveprenom());
       myElev.setElevenom(eleveDto.getElevenom());
       myElev.setElevecode(getElevCode(eleveDto.getIdTenant()));
       myElev.setElevelieu_naissance(eleveDto.getElevelieu_naissance());
       myElev.setEleveadresse(eleveDto.getEleveadresse());
       myElev.setElevecellulaire(eleveDto.getElevecellulaire());
       myElev.setElevedate_naissance(eleveDto.getElevedate_naissance());
       myElev.setElevedate_etabli_extrait_naiss(eleveDto.getElevedate_etabli_extrait_naiss());
       myElev.setElevelieu_etabliss_etrait_naissance(eleveDto.getElevelieu_etabliss_etrait_naissance());
        myElev.setTenant(mytenant);
       myElev.setParents(parentsList);
       myElev.setEleveSexe(eleveDto.getEleveSexe());
       myElev.setElevematricule_national(eleveDto.getElevematricule_national());
       myElev.persist();

      /// public void run() {

       System.out.println("Moustt"+myElev.toString());
    return myElev ;

          }


    public  String getElevCode(String tenant) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        Long  maxrecor= (Long) em.createQuery("select max(o.eleveid)  from eleve o  where o.tenant.tenantid =: tenant")
                .setParameter("tenant", tenant)
                .getSingleResult();

        System.out.println("maxrecor "+maxrecor);

        String eleveCode= "GAIN"+yearInString+ String.valueOf(maxrecor) ;
        System.out.println("InscriptionCode "+eleveCode);

        return eleveCode ;
    }









    public  List<eleve> listEleve(){

        return    em.createQuery("select o from eleve o join o.parents p")

                .getResultList();

    }

  





   public  eleve updatEeleve(EleveDto elev,Long EleveID){
       eleve entity = eleve.findById(EleveID);
       if(entity == null) {
           throw new NotFoundException();
       }
       tenant mytenant = (tenant) em.createQuery("select o from tenant o where   o.tenantid=:tenant ")
               .setParameter("tenant",elev.getIdTenant())
               .getSingleResult() ;

       List<Parent> parentsList= new ArrayList<>() ;
       // System.out.println( "Longueur"+ eleveDto.getParentList().size());
       for(int i = 0 ; i < elev.getParentList().size() ; i++)
       {
           Parent myParent;
           myParent= Parent.findById(elev.getParentList().get(i)) ;
           parentsList.add(myParent) ;

       }

       entity.setEleve_mail(elev.getEleve_mail());
       entity.setEleveadresse(elev.getEleveadresse());
       entity.setElevecode(elev.getElevecode());
       entity.setEleve_numero_extrait_naiss(elev.getEleve_numero_extrait_naiss());
       entity.setElevedate_etabli_extrait_naiss(elev.getElevedate_etabli_extrait_naiss());
       entity.setElevelieu_etabliss_etrait_naissance(elev.getElevelieu_etabliss_etrait_naissance());
       entity.setElevecellulaire(elev.getElevecellulaire());
       entity.setElevedate_naissance(elev.getElevedate_naissance());
       entity.setElevelieu_naissance(elev.getElevelieu_naissance());
       entity.setElevenom(elev.getElevenom());
       entity.setTenant(mytenant);
       entity.setEleveSexe(elev.getEleveSexe());
       entity.setParents(parentsList);
       entity.setElevematricule_national(elev.getElevematricule_national());
       entity.setEleveprenom(elev.getEleveprenom());
        return  entity;
   }

    public void  deleteeleve(long zonId){
        eleve entity = eleve.findById(zonId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<eleve> search(String Libelle){
       return   em.createQuery("select o from objet o where  o.objetlibelle like CONCAT('%',:Libelle ,'%') ")
               .setParameter("Libelle",Libelle).getResultList();
   }

    public  long count(){
        return  cycle.count();
    }


}
