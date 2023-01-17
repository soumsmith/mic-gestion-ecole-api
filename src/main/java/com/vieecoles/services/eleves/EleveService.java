package com.vieecoles.services.eleves;

import com.vieecoles.dao.entities.operations.Inscriptions;
import com.vieecoles.dto.EleveDto;
import com.vieecoles.dao.entities.Parent;
import com.vieecoles.dao.entities.cycle;
import com.vieecoles.dao.entities.operations.eleve;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.dto.importEleveDto;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.format.DateTimeFormatter;
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

   public String importerCreerEleve(List<importEleveDto> lisImpo ,Long idEcole,Long idAnneeScolaire,String typeOperation){
       String messageRetour ="" ;
       List<String> matriculeNonCreer = new ArrayList<>();
       for (int i = 0; i < lisImpo.size(); i++){

           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");



           EleveDto eleveDto= new EleveDto() ;
           eleve elv =new eleve() ;
           InscriptionDto inscriptionDto = new InscriptionDto() ;
           Long  identifiantBranche ;
           Branche myBranch = new Branche() ;
           myBranch= (Branche) em.createQuery("select o from Branche o  where o.libelle =:branche " )
                   .setParameter("branche", lisImpo.get(i).getLibelleBranche()).getSingleResult() ;
           System.out.println("identifiantBranche "+myBranch.getId());

           eleveDto.setElevematricule_national(lisImpo.get(i).getMatricule());
           eleveDto.setEleveSexe(lisImpo.get(i).getSexe());
           eleveDto.setElevenom(lisImpo.get(i).getNom());
           eleveDto.setEleveprenom(lisImpo.get(i).getPrenoms());
         /*  String dateNaissance = lisImpo.get(i).getDate_naissance();
           LocalDate localDateNaiss = LocalDate.parse(dateNaissance, formatter);
           eleveDto.setElevedate_naissance(localDateNaiss);*/
           eleveDto.setElevelieu_naissance(lisImpo.get(i).getLieu_naissance());

    ///Creer l'eleve
           elv= CreerUnEleve(eleveDto) ;

           inscriptionDto.setIdentifiantEcole(idEcole);
           inscriptionDto.setIdentifiantAnnee_scolaire(idAnneeScolaire);
           inscriptionDto.setIdentifiantBranche(myBranch.getId());
           Inscriptions.typeOperation myOperation= Inscriptions.typeOperation.valueOf(typeOperation);
           Inscriptions.statusEleve myStatutEleve = Inscriptions.statusEleve.valueOf(lisImpo.get(i).getStatut());
           inscriptionDto.setInscriptions_langue_vivante(lisImpo.get(i).getLv2());
           inscriptionDto.setInscriptions_statut_eleve(myStatutEleve);
           inscriptionDto.setIdentifiantEleve(elv.getEleveid());
           inscriptionDto.setInscriptions_type(myOperation);
           messageRetour=  inscriptionService.verifInscriptionImporter(inscriptionDto,idEcole,elv.getEleve_matricule(),idAnneeScolaire);

           if(!messageRetour.equals("DEMANDE D'INSCRIPTION EFFECTUEE AVEC SUCCES!")){
               matriculeNonCreer.add(lisImpo.get(i).getMatricule()) ;
           }

       }

       if(matriculeNonCreer.size()>0){
           String mess="Les matricules suivants avaient probablement commencé leurs inscriptions";
           messageRetour = String.join(", ", matriculeNonCreer);
           messageRetour= mess+" "+ messageRetour ;
       }

   return messageRetour ;
   }
@Transactional
   public eleve   CreerUnEleve(EleveDto eleveDto) {
    eleve myeleve = new eleve() ;
    try {

        myeleve= (eleve) em.createQuery("select o from eleve o  where o.eleve_matricule =:matricule " )
                .setParameter("matricule", eleveDto.getElevematricule_national()).getSingleResult() ;
        System.out.println(eleveDto.getElevematricule_national());
        System.out.println("Ancien elève");
    } catch (Exception e) {
        System.out.println("Nouvel elève");
        myeleve= null ;
    }




       List<Parent> parentsList= new ArrayList<>() ;
      // System.out.println( "Longueur"+ eleveDto.getParentList().size());
       for(int i = 0 ; i < eleveDto.getParentList().size() ; i++)
       {
           Parent myParent;
           myParent= Parent.findById(eleveDto.getParentList().get(i)) ;
           parentsList.add(myParent) ;
       }
       eleve myElev = new eleve() ;

       if(myeleve==null ) {
           myElev.setEleveprenom(eleveDto.getEleveprenom());
           myElev.setElevenom(eleveDto.getElevenom());
           myElev.setElevelieu_naissance(eleveDto.getElevelieu_naissance());
           myElev.setEleveadresse(eleveDto.getEleveadresse());
           myElev.setElevecellulaire(eleveDto.getElevecellulaire());
           myElev.setElevedate_naissance(eleveDto.getElevedate_naissance());
           myElev.setElevedate_etabli_extrait_naiss(eleveDto.getElevedate_etabli_extrait_naiss());
           myElev.setElevelieu_etabliss_etrait_naissance(eleveDto.getElevelieu_etabliss_etrait_naissance());
           myElev.setParents(parentsList);
           myElev.setEleve_sexe(eleveDto.getEleveSexe());
           myElev.setEleve_matricule(eleveDto.getElevematricule_national());
           myElev.persist();
       } else {
           myElev= myeleve ;
       }




      /// public void run() {

       System.out.println("Moustt"+myElev.toString());
    return myElev ;

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









 /*   public  List<eleve> listEleve(String code){
tenant mytenant= tenant.findById(code) ;
        return    em.createQuery("select o from eleve o join fetch Parent where o.tenant.tenantid =:tenant")
                .setParameter("tenant",code)
                .getResultList();

    }*/


   public  eleve updatEeleve(EleveDto elev,Long EleveID){
       eleve entity = eleve.findById(EleveID);
       if(entity == null) {
           throw new NotFoundException();
       }


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
