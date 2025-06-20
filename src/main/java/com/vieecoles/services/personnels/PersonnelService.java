package com.vieecoles.services.personnels;

import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.personnelDto;
import com.vieecoles.entities.*;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.entities.operations.personnel;

import com.vieecoles.projection.InfosConnexionSelect;
import com.vieecoles.projection.panier_personnelSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class PersonnelService implements PanacheRepositoryBase<personnel, Long> {
    @Inject
    EntityManager em;

@Transactional
   public String   CreerPersonnel(personnelDto personnelDto) {
    type_personnel mytype_personnel= new type_personnel() ;
    personnel_status mypersonnel_status = new personnel_status() ;
    personnel myPersonel = new personnel() ;
   fonction myFonction= new fonction() ;
    myFonction= fonction.findById(personnelDto.getIdentifiant_fonction()) ;
    mytype_personnel = type_personnel.findById(personnelDto.getIdentifiant_type_personnel()) ;

    mypersonnel_status= personnel_status.findById(personnelDto.getIdentifiant_personnelStatut()) ;

   // myPersonel.setPersonnel_status(mypersonnel_status);
    myPersonel.setFonction(myFonction);
   // myPersonel.setType_personnel(mytype_personnel);
    myPersonel.setPersonnel_lieunaissance(personnelDto.getPersonnel_lieunaissance());
    myPersonel.setPersonnelcode(personnelDto.getPersonnelcode());
    myPersonel.setPersonnelnom(personnelDto.getPersonnelnom());
    myPersonel.setPersonnelprenom(personnelDto.getPersonnelprenom());
    myPersonel.setPersonneldatenaissance(personnelDto.getPersonneldatenaissance());
    myPersonel.persist();
      return  myPersonel.getPersonnelcode() ;
          }


          public  List<personnel> getPersonnels(String tenant ){
              /*TypedQuery<personnel> q = (TypedQuery<personnel>) em.createQuery("select o from personnel  o join  o. p join o.tenant t join o.type_personnel join  o.fonction f " +
                      "where  t.tenantid=:tenant ");
              List<personnel> personnelSelect = q.setParameter("tenant",tenant).
                      getResultList();*/
              return null    ;
          }

    public  List<personnel> getPersonnels2(String LibelleFonctopn ,String tenant ){
        /*TypedQuery<personnel> q = (TypedQuery<personnel>) em.createQuery("select o from personnel  o join  o.personnel_status p join o.tenant t join o.type_personnel join  o.fonction f " +
                "where f.fonctionlibelle=:fonction and t.tenantid=:tenant ");
        List<personnel> personnelSelect = q.setParameter("fonction",LibelleFonctopn).setParameter("tenant",tenant).
                getResultList();*/
        return null ;
    }



    public  List<personnel> getAllPersonnels(){
    return personnel.listAll() ;
    }




    public  personnel getPersonnelsByID(Long identifiantPersonnel){
        return   personnel.findById(identifiantPersonnel) ;
    }


    @Transactional
    public personnel   modifierPersonnel(personnelDto personnelDto) {
        type_personnel mytype_personnel= new type_personnel() ;
        personnel_status mypersonnel_status = new personnel_status() ;
        personnel myPersonel = new personnel() ;
        mytype_personnel = type_personnel.findById(personnelDto.getIdentifiant_type_personnel()) ;
        mypersonnel_status= personnel_status.findById(personnelDto.getIdentifiant_personnelStatut()) ;
       fonction myFonction = fonction.findById(personnelDto.getIdentifiant_fonction());
        myPersonel = personnel.findById(personnelDto.getPersonnelid()) ;

        myPersonel.setFonction(myFonction);
        myPersonel.setPersonnel_lieunaissance(personnelDto.getPersonnel_lieunaissance());
        myPersonel.setPersonnelcode(personnelDto.getPersonnelcode());
        myPersonel.setPersonnelnom(personnelDto.getPersonnelnom());
        myPersonel.setPersonnelprenom(personnelDto.getPersonnelprenom());
        myPersonel.setPersonneldatenaissance(personnelDto.getPersonneldatenaissance());

        return  myPersonel ;
    }

    @Transactional
    public void    deletePersonnel(Long identifiantPersonnel) {

        personnel myPersonel = new personnel() ;
        myPersonel = personnel.findById(identifiantPersonnel) ;

        try{
            myPersonel.delete();
        }catch (Exception e) {

        }

    }

    public List<InfosConnexionSelect> getConnexionInfos(){
        List<InfosConnexionSelect>  mesPaniers = new ArrayList<>()  ;
        try {
            mesPaniers=  em.createQuery("SELECT new com.vieecoles.projection.InfosConnexionSelect(p.personnelnom ,p.personnelprenom, e.ecoleclibelle ,t.utilisateu_email ,t.utilisateur_mot_de_passe) from personnel p , ecole  e ,utilisateur_has_personnel u,utilisateur t" +
                            " where p.ecole.ecoleid =e.ecoleid and u.personnel_personnelid= p.personnelid and u.utilisateur.utilisateurid = t.utilisateurid ")
                    .getResultList() ;
        } catch (Exception e) {
            mesPaniers=  null;
        }

        return mesPaniers ;
    }


    public List<InfosConnexionSelect> getConnexionInfosByEcole(Long idEcole){
        List<InfosConnexionSelect>  mesPaniers = new ArrayList<>()  ;
        try {
            mesPaniers=  em.createQuery("SELECT new com.vieecoles.projection.InfosConnexionSelect(p.personnelnom ,p.personnelprenom, e.ecoleclibelle ,t.utilisateu_login ,t.utilisateur_mot_de_passe) from personnel p , ecole  e ,utilisateur_has_personnel u,utilisateur t" +
                            " where p.ecole.ecoleid =e.ecoleid and u.personnel_personnelid= p.personnelid and u.utilisateur.utilisateurid = t.utilisateurid and e.ecoleid=: idEcole ")
                    .setParameter("idEcole",idEcole)
                    .getResultList() ;
        } catch (Exception e) {
            mesPaniers=  null;
        }

        return mesPaniers ;
    }








    public  String getElevCode(String tenant) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        Long  maxrecor= (Long) em.createQuery("select max(o.eleveid)  from eleve o ")
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
       tenant mytenant = (tenant) em.createQuery("select o from tenant o  ")
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
         entity.setEleve_sexe(elev.getEleveSexe());
       entity.setParents(parentsList);
       entity.setEleve_matricule(elev.getElevematricule_national());
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
