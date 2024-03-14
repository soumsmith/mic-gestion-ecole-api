package com.vieecoles.services.eleves;

import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.dto.importEleveDto;
import com.vieecoles.entities.Parent;
import com.vieecoles.entities.cycle;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

    public String importerMiseEleve(List<importEleveDto> lisImpo ,Long idEcole,Long idAnneeScolaire,String typeOperation ,Long idBranche){
        String messageRetour ="" ;
        List<String> matriculeNonCreer = new ArrayList<>();
        for (int i = 0; i < lisImpo.size(); i++){
            //String myStatut= lisImpo.get(i).getStatut() ;
            String NewmyStatut ;
            System.out.println("Statut0 "+lisImpo.get(i).getStatut());
            if(lisImpo.get(i).getStatut().equals("AFF")){
                NewmyStatut= String.valueOf(Inscriptions.statusEleve.AFFECTE);
            }else {
                NewmyStatut= String.valueOf(Inscriptions.statusEleve.NON_AFFECTE);
            }
            EleveDto eleveDto= new EleveDto() ;
            eleve elv =new eleve() ;
            InscriptionDto inscriptionDto = new InscriptionDto() ;
            Long  identifiantBranche ;


            Long NiveauEnseignement=  (Long) em.createQuery("select o.Niveau_Enseignement_id from ecole o  where o.ecoleid =:idEcole " )
                    .setParameter("idEcole", idEcole).getSingleResult() ;




            if(lisImpo.get(i).getMatricule()==null|| lisImpo.get(i).getMatricule().equals(""))
            {
                eleveDto.setElevematricule_national(lisImpo.get(i).getId_eleve());

            }

            else
                eleveDto.setElevematricule_national(lisImpo.get(i).getMatricule());

            String msexe ;
            if(lisImpo.get(i).getSexe().equals("F"))
                msexe = "FEMININ";
            else
                msexe = "MASCULIN";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            eleveDto.setEleveSexe(msexe);
            eleveDto.setElevenom(lisImpo.get(i).getNom());
            eleveDto.setEleve_nationalite(lisImpo.get(i).getNationalite());
            eleveDto.setEleveprenom(lisImpo.get(i).getPrenoms());
            eleveDto.setEleve_nationalite(lisImpo.get(i).getNationalite());
            eleveDto.setElevelieu_naissance(lisImpo.get(i).getLieun());
            eleveDto.setEleve_numero_extrait_naiss(lisImpo.get(i).getExtrait_numero());

            LocalDate localDateExtre = LocalDate.parse(lisImpo.get(i).getExtrait_date(), formatter);
            LocalDate localDateNaissExtre = localDateExtre;
            eleveDto.setElevedate_etabli_extrait_naiss(localDateNaissExtre);
            eleveDto.setElevelieu_etabliss_etrait_naissance(lisImpo.get(i).getExtrait_lieu());
            eleveDto.setEleveadresse(lisImpo.get(i).getAdresse());
            System.out.println("Debut creation Date format ");

            System.out.println("Fin creation Date format ");
            if(lisImpo.get(i).getDatenaissance()==null||lisImpo.get(i).getDatenaissance().equals("")){
                String date = "01/01/1900";
                LocalDate localDate = LocalDate.parse(date, formatter);
                LocalDate localDateNaiss = localDate;

                eleveDto.setElevedate_naissance(localDateNaiss);
            } else {
                String date = lisImpo.get(i).getDatenaissance();
                LocalDate localDate = LocalDate.parse(date, formatter);
                LocalDate localDateNaiss = localDate;

                eleveDto.setElevedate_naissance(localDateNaiss);
            }



            System.out.println("Debut creation eleve ");

            elv = ModifierEleve(eleveDto) ;



            if(elv != null){
                System.out.println("Matricule: "+ elv.getEleve_matricule());
                System.out.println("Fin creation eleve ");
                inscriptionDto.setIdentifiantEcole(idEcole);
                inscriptionDto.setIdentifiantAnnee_scolaire(idAnneeScolaire);
                inscriptionDto.setIdentifiantBranche(idBranche);
                Inscriptions.typeOperation myOperation= Inscriptions.typeOperation.valueOf(typeOperation);

                Inscriptions.statusEleve myStatutEleve = Inscriptions.statusEleve.valueOf(NewmyStatut);
                inscriptionDto.setInscriptions_langue_vivante(lisImpo.get(i).getLv2());
                inscriptionDto.setInscriptions_redoublant(lisImpo.get(i).getRedoublant());
                inscriptionDto.setInscriptions_contact1(lisImpo.get(i).getMobile());
                inscriptionDto.setInscriptions_contact2(lisImpo.get(i).getMobile2());
                inscriptionDto.setNom_prenoms_pere(lisImpo.get(i).getPere());
                inscriptionDto.setNom_prenoms_mere(lisImpo.get(i).getMere());
                inscriptionDto.setInscriptions_statut_eleve(myStatutEleve);
                inscriptionDto.setNom_prenoms_tuteur(lisImpo.get(i).getTuteur());
                inscriptionDto.setDecision_ant(lisImpo.get(i).getDecision_ant());
                inscriptionDto.setIdentifiantEleve(elv.getEleveid());
                inscriptionDto.setInscriptions_type(myOperation);
                System.out.println("regime+++ "+lisImpo.get(i).getRegime());
                inscriptionDto.setInscriptions_boursier(lisImpo.get(i).getRegime());
                inscriptionDto.setInscriptionsdate_modification(LocalDate.now());

                System.out.println("Debut  creation Insrip "+inscriptionDto.toString());
                messageRetour=  inscriptionService.verifmodifierInscription(inscriptionDto,idEcole,elv.getEleve_matricule(),idAnneeScolaire);

                if(!messageRetour.equals("INSCRIPTION MODIFIEE AVEC SUCCES!")){
                    matriculeNonCreer.add(lisImpo.get(i).getMatricule()) ;
                }
            } else  {
                matriculeNonCreer.add(lisImpo.get(i).getMatricule()) ;
            }

        }

        if(matriculeNonCreer.size()>0){
            String mess="Les matricules suivants n'existe pas!";
            messageRetour = String.join(", ", matriculeNonCreer);
            messageRetour= mess+" "+ messageRetour ;
        }

        return messageRetour ;
    }



   public String importerCreerEleve(List<importEleveDto> lisImpo ,Long idEcole,Long idAnneeScolaire,String typeOperation ,Long idBranche){
       long timestamp = System.currentTimeMillis() ;
       String matriculeGenere = "G_"+idEcole+timestamp ;

       System.out.println("matriculeGenere "+matriculeGenere);

       String messageRetour ="" ;
       List<String> matriculeNonCreer = new ArrayList<>();
       for (int i = 0; i < lisImpo.size(); i++){
           //String myStatut= lisImpo.get(i).getStatut() ;
           String NewmyStatut ;
System.out.println("Statut0 "+lisImpo.get(i).getStatut());
           if(lisImpo.get(i).getStatut().equals("AFF")){
               NewmyStatut= String.valueOf(Inscriptions.statusEleve.AFFECTE);
           }else {
               NewmyStatut= String.valueOf(Inscriptions.statusEleve.NON_AFFECTE);
           }
           EleveDto eleveDto= new EleveDto() ;
           eleve elv =new eleve() ;
           InscriptionDto inscriptionDto = new InscriptionDto() ;



           Long NiveauEnseignement=  (Long) em.createQuery("select o.Niveau_Enseignement_id from ecole o  where o.ecoleid =:idEcole " )
                   .setParameter("idEcole", idEcole).getSingleResult() ;





           if(lisImpo.get(i).getMatricule()==null || lisImpo.get(i).getMatricule().equals(""))
           {
               eleveDto.setElevematricule_national(matriculeGenere);

           }

           else
               eleveDto.setElevematricule_national(lisImpo.get(i).getMatricule());

           String msexe ;
           if(lisImpo.get(i).getSexe().equals("F"))
               msexe = "FEMININ";
           else
               msexe = "MASCULIN";
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
           eleveDto.setEleveSexe(msexe);
           System.out.println("msexe "+msexe);
           eleveDto.setElevenom(lisImpo.get(i).getNom());
           System.out.println("Nom "+lisImpo.get(i).getNom());
           eleveDto.setEleve_nationalite(lisImpo.get(i).getNationalite());
           System.out.println("Nationalite "+lisImpo.get(i).getNationalite());
           eleveDto.setEleveprenom(lisImpo.get(i).getPrenoms());
           System.out.println("prenom "+lisImpo.get(i).getPrenoms());
           eleveDto.setEleve_nationalite(lisImpo.get(i).getNationalite());
           System.out.println("nationalite "+lisImpo.get(i).getNationalite());
           eleveDto.setElevelieu_naissance(lisImpo.get(i).getLieun());
           System.out.println("Lieu Naiss "+lisImpo.get(i).getLieun());
           eleveDto.setEleve_numero_extrait_naiss(lisImpo.get(i).getExtrait_numero());
           System.out.println("numExtrai "+lisImpo.get(i).getExtrait_numero());
           System.out.println("DateExtra1*** "+lisImpo.get(i).getExtrait_date());
           if(lisImpo.get(i).getExtrait_date()!=null){
               LocalDate localDateExtre = LocalDate.parse(lisImpo.get(i).getExtrait_date(), formatter);
               System.out.println("DateExtra1 "+lisImpo.get(i).getExtrait_date());
               LocalDate localDateNaissExtre = localDateExtre;
               eleveDto.setElevedate_etabli_extrait_naiss(localDateNaissExtre);
               System.out.println("DateExtra "+localDateNaissExtre);
           }


           eleveDto.setElevelieu_etabliss_etrait_naissance(lisImpo.get(i).getExtrait_lieu());
           System.out.println("LieuExtrai "+lisImpo.get(i).getExtrait_lieu());
           eleveDto.setEleveadresse(lisImpo.get(i).getAdresse());
           System.out.println("Debut creation Date format ");

           System.out.println("Fin creation Date format ");
           if(lisImpo.get(i).getDatenaissance()==null||lisImpo.get(i).getDatenaissance().equals("")){
               String date = "01/01/1900";
               LocalDate localDate = LocalDate.parse(date, formatter);
               LocalDate localDateNaiss = localDate;

               eleveDto.setElevedate_naissance(localDateNaiss);
           } else {
               String date = lisImpo.get(i).getDatenaissance();
               LocalDate localDate = LocalDate.parse(date, formatter);
               LocalDate localDateNaiss = localDate;

               eleveDto.setElevedate_naissance(localDateNaiss);
           }



    ///Creer l'eleve
           System.out.println("Debut creation eleve ");
           elv= CreerUnEleve(eleveDto) ;
           System.out.println("Fin creation eleve ");
           inscriptionDto.setIdentifiantEcole(idEcole);
           inscriptionDto.setIdentifiantAnnee_scolaire(idAnneeScolaire);
           inscriptionDto.setIdentifiantBranche(idBranche);
           Inscriptions.typeOperation myOperation= Inscriptions.typeOperation.valueOf(typeOperation);

           Inscriptions.statusEleve myStatutEleve = Inscriptions.statusEleve.valueOf(NewmyStatut);
           inscriptionDto.setInscriptions_langue_vivante(lisImpo.get(i).getLv2());
           inscriptionDto.setInscriptions_redoublant(lisImpo.get(i).getRedoublant());
           inscriptionDto.setInscriptions_contact1(lisImpo.get(i).getMobile() );
           inscriptionDto.setInscriptions_contact2(lisImpo.get(i).getMobile2()) ;
           inscriptionDto.setNom_prenoms_pere(lisImpo.get(i).getPere());
           inscriptionDto.setNom_prenoms_mere(lisImpo.get(i).getMere());
           inscriptionDto.setInscriptions_statut_eleve(myStatutEleve);
           inscriptionDto.setNom_prenoms_tuteur(lisImpo.get(i).getTuteur());
           inscriptionDto.setDecision_ant(lisImpo.get(i).getDecision_ant());
           inscriptionDto.setIdentifiantEleve(elv.getEleveid());
           inscriptionDto.setInscriptions_type(myOperation);
           System.out.println("regime+++ "+lisImpo.get(i).getRegime());
           inscriptionDto.setInscriptions_boursier(lisImpo.get(i).getRegime());
           inscriptionDto.setInscriptionsdate_creation(LocalDate.now());


           System.out.println("Debut  creation Insrip "+inscriptionDto.toString());
           messageRetour =  inscriptionService.verifInscriptionImporter(inscriptionDto,idEcole,elv.getEleve_matricule(),idAnneeScolaire);

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
    public eleve   ModifierEleve(EleveDto eleveDto) {
        eleve myeleve = new eleve() ;
        eleve myElev = new eleve() ;
        try {
            myeleve= (eleve) em.createQuery("select o from eleve o  where o.eleve_matricule =:matricule " )
                    .setParameter("matricule", eleveDto.getElevematricule_national()).getSingleResult() ;
            System.out.println(eleveDto.getElevematricule_national());
            System.out.println("Ancien elève");
            myElev = eleve.findById(myeleve.getEleveid());
            myElev.setEleveprenom(eleveDto.getEleveprenom());
            myElev.setElevenom(eleveDto.getElevenom());
            myElev.setElevelieu_naissance(eleveDto.getElevelieu_naissance());
            myElev.setEleveadresse(eleveDto.getEleveadresse());
            myElev.setEleve_nationalite(eleveDto.getEleve_nationalite());
            myElev.setElevecellulaire(eleveDto.getElevecellulaire());
            myElev.setElevedate_naissance(eleveDto.getElevedate_naissance());
            myElev.setElevedate_etabli_extrait_naiss(eleveDto.getElevedate_etabli_extrait_naiss());
            myElev.setElevelieu_etabliss_etrait_naissance(eleveDto.getElevelieu_etabliss_etrait_naissance());
            myElev.setEleve_sexe(eleveDto.getEleveSexe());
            myElev.setEleveadresse(eleveDto.getEleveadresse());
            myElev.setEleve_matricule(eleveDto.getElevematricule_national());

        } catch (Exception e) {
            System.out.println("Nouvel elève");
            myElev= null;
        }

       // System.out.println("Moustt"+myElev.toString());
        return myElev ;
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
           myElev.setEleve_nationalite(eleveDto.getEleve_nationalite());
           myElev.setElevecellulaire(eleveDto.getElevecellulaire());
           myElev.setElevedate_naissance(eleveDto.getElevedate_naissance());
           myElev.setElevedate_etabli_extrait_naiss(eleveDto.getElevedate_etabli_extrait_naiss());
           myElev.setElevelieu_etabliss_etrait_naissance(eleveDto.getElevelieu_etabliss_etrait_naissance());
           myElev.setParents(parentsList);
           myElev.setEleve_sexe(eleveDto.getEleveSexe());
           myElev.setEleveadresse(eleveDto.getEleveadresse());
           myElev.setEleve_matricule(eleveDto.getElevematricule_national());
           myElev.persist();
       } else {
           myElev= myeleve ;
       }

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
