package com.vieecoles.services.souscription;

import com.vieecoles.dto.CreerCompteUtilsateurDto;
import com.vieecoles.dto.ecoleDto2;
import com.vieecoles.dto.sous_attent_ecoleDto;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.vieecoles.dto.souscriptionEcoleDto;
import com.vieecoles.dto.souscriptionValidationDto;
import com.vieecoles.entities.Zone;
import com.vieecoles.entities.fonction;
import com.vieecoles.entities.utilisateur;
import com.vieecoles.entities.operations.*;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class SousceecoleService implements PanacheRepositoryBase<sousc_atten_etabliss, Long> {
    @Inject
    EntityManager em;

  @Transactional
   public String   CreerSousCrietabliss(sous_attent_ecoleDto souscriecole) {
    sousc_atten_etabliss  mysouscripPersonn = new sousc_atten_etabliss() ;
    sousc_atten_etabliss  mysouscripPersonn1 = new sousc_atten_etabliss() ;

    mysouscripPersonn = getSouscripByEmail2(souscriecole.getSousc_atten_etabliss_email()) ;

    if (mysouscripPersonn!=null) {
        return  "EXISTE_DEJA !";
    } else {
        ville myVille = new ville()  ;
        Zone myZone = new Zone() ;
        sousc_atten_etabliss  myecolePersonn = new sousc_atten_etabliss() ;
        myVille= ville.findById(souscriecole.getVille_villeid());
        myZone = Zone.findById(souscriecole.getZone_zoneid());

        mysouscripPersonn1.setSousc_atten_etabliss_email(souscriecole.getSousc_atten_etabliss_email());
        mysouscripPersonn1.setSousc_atten_etabliss_indication(souscriecole.getSousc_atten_etabliss_indication());
        mysouscripPersonn1.setSousc_atten_etabliss_nom(souscriecole.getSousc_atten_etabliss_nom());
        mysouscripPersonn1.setSousc_atten_etabliss_tel(souscriecole.getSousc_atten_etabliss_tel());
        mysouscripPersonn1.setVille(myVille);
        mysouscripPersonn1.setZone(myZone);
        mysouscripPersonn1.setSousc_atten_etablisscode(souscriecole.getSousc_atten_etablisscode());
        mysouscripPersonn1.persist();
        return  "Demande créée avec succes !";
    }
     }

     public String CreerCompteUtlisateurEcole(sous_attent_ecoleDto souscriecole) {
        String messageRetour= null;
        messageRetour = CreerSousCrietabliss(souscriecole) ;

        if (messageRetour.equals("Demande créée avec succes !")) {
            sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
            mysouscripPersonn1= getSouscripByEmail(souscriecole.getSous_attent_personn_email()) ;
            Long idsouscripteur = mysouscripPersonn1.getSous_attent_personnid() ;
            CreerCompteUtilsateurDto creerCompte = new CreerCompteUtilsateurDto() ;
            creerCompte.setUtilisateur_mot_de_passe(souscriecole.getSous_attent_personn_password());
            creerCompte.setSous_attent_personn_sous_attent_personnid(idsouscripteur);
            creerCompte.setUtilisateu_email(souscriecole.getSous_attent_personn_email());
            messageRetour = creerCompteUtilisateur(creerCompte) ;

        } else if (messageRetour.equals("EXISTE_DEJA !")){
            messageRetour ="EXISTE_DEJA !" ;
        }

return  messageRetour ;

     }
     public  sous_attent_personn getSouscripByEmail(String email){
        try {
            return (sous_attent_personn) em.createQuery("select o from sous_attent_personn o where o.sous_attent_personn_email =:email")
                    .setParameter("email",email)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }
   
   
       } 

     

     @Transactional
     public String  creerCompteUtilisateur(CreerCompteUtilsateurDto creerCompte){

      utilisateur myNewInser = new utilisateur() ;
      myNewInser.setUtilisateu_email(creerCompte.getUtilisateu_email());
      myNewInser.setSous_attent_personn_sous_attent_personnid(creerCompte.getSous_attent_personn_sous_attent_personnid());
      myNewInser.setUtilisateur_mot_de_passe(creerCompte.getUtilisateur_mot_de_passe());
      myNewInser.persist();
      return  "Demande créée avec succes !";

     }




        public String creerSouscripEtablissement(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2 ,String email ,String password,List<sous_attent_ecoleDto> listsouscr){
         return    creerLesEcoles(fonctionId,nom,prenom,contact1,contact2,email,password,listsouscr) ;
        }



        public sous_attent_personn CreerPersonnelSouscriAndCompteUser(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2,String email,
        String password) {
            sous_attent_personn sous_attent_personn= new sous_attent_personn() ;
            //Creer souscripteur
            sous_attent_personn= creerPersonnel(fonctionId,nom,prenom,contact1,contact2,email) ;
            CreerCompteUtilsateurDto comptUserDto = new CreerCompteUtilsateurDto() ;
            comptUserDto.setSous_attent_personn_sous_attent_personnid(sous_attent_personn.getSous_attent_personnid());
            comptUserDto.setUtilisateu_email(email);
            comptUserDto.setUtilisateur_mot_de_passe(password);
               //Creer compte souscripteur 
            creerCompteUtilisateur(comptUserDto) ;
            return sous_attent_personn ;
        }



      @Transactional
  public sous_attent_personn creerPersonnel(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2,String email){
           sous_attent_personn sous_attent_personn= new sous_attent_personn() ;
              fonction myFon= fonction.findById(fonctionId) ;
              sous_attent_personn.setSous_attent_personn_nom(nom);
              sous_attent_personn.setSous_attent_personn_prenom(prenom);
              sous_attent_personn.setSous_attent_personn_contact(contact1);
              sous_attent_personn.setSous_attent_personn_contact2(contact2);
              sous_attent_personn.setSous_attent_personn_email(email);
              sous_attent_personn.setSous_attent_personn_statut("EN ATTENTE");
              sous_attent_personn.setFonction(myFon);
              sous_attent_personn.persist();
              System.out.println("Personnel "+sous_attent_personn.toString());
            return sous_attent_personn ;
  }

  @Transactional
  public String creerLesEcoles(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2 ,String email ,String password,List<sous_attent_ecoleDto> listsouscr ) {
    sous_attent_personn sous = new sous_attent_personn() ;
    sous = CreerPersonnelSouscriAndCompteUser(fonctionId,nom,prenom,contact1,contact2,email,password);

    System.out.println("sous+++ "+sous.toString());
    System.out.println("Longueur Tableau listsouscr+++ "+listsouscr.size());
      String messageRetour ="" ;
      List<String> matriculeNonCreer = new ArrayList<>();
      for(int i = 0 ; i < listsouscr.size() ; i++)
      {
          sousc_atten_etabliss  sousc_atten_etabliss1= new sousc_atten_etabliss() ;
          sousc_atten_etabliss1 = checkExistEtabliss(listsouscr.get(i).getSousc_atten_etablisscode()) ;
          sousc_atten_etabliss sousEtabli = new sousc_atten_etabliss() ;
          ville mville = new ville() ;
          mville = ville.findById(listsouscr.get(i).getVille_villeid()) ;
          Zone myZone = Zone.findById(listsouscr.get(i).getZone_zoneid()) ;

          if(sousc_atten_etabliss1 ==null ){
              sousEtabli.setSousc_atten_etabliss_email(listsouscr.get(i).getSousc_atten_etabliss_email());
              sousEtabli.setSous_attent_personn_sous_attent_personnid(sous.getSous_attent_personnid());
              sousEtabli.setVille(mville);
              sousEtabli.setZone(myZone);
              sousEtabli.setSousc_atten_etabliss_statut(Inscriptions.status.EN_ATTENTE);
              sousEtabli.setSousc_atten_etabliss_indication(listsouscr.get(i).getSousc_atten_etabliss_indication()) ;
              sousEtabli.setSousc_atten_etabliss_nom(listsouscr.get(i).getSousc_atten_etabliss_nom()) ;
              sousEtabli.setSousc_atten_etabliss_tel(listsouscr.get(i).getSousc_atten_etabliss_tel()) ;
              sousEtabli.setSousc_atten_etablisscode(listsouscr.get(i).getSousc_atten_etablisscode()) ;
              sousEtabli.setSousc_atten_etabliss_lien_autorisa(listsouscr.get(i).getSousc_atten_etabliss_lien_autorisa());
              sousEtabli.setNiveau_Enseignement_id(listsouscr.get(i).getNiveau_Enseignement_id());
              sousEtabli.persist();
              System.out.println("sousEtabli "+sousEtabli.toString());
          } else {
              matriculeNonCreer.add(listsouscr.get(i).getSousc_atten_etablisscode()) ;
          }
      }
      if(matriculeNonCreer.size()>0){
          String mess="Veuillez contacter l'administrateur.Les écoles avec les codes  suivants existent déjà:";
          messageRetour = String.join(", ", matriculeNonCreer);
          messageRetour= mess+" "+ messageRetour ;
      } else  {
          messageRetour ="DEMANDE D'INSCRIPTION EFFECTUEE AVEC SUCCES!" ;
      }




return  messageRetour ;

  }

  public  sousc_atten_etabliss checkExistEtabliss(String codeEtabliss) {
      sousc_atten_etabliss  sousc_atten_etabliss1= new sousc_atten_etabliss() ;
    try {
         sousc_atten_etabliss1= (sousc_atten_etabliss) em.createQuery("select o from sousc_atten_etabliss o  where o.sousc_atten_etablisscode =:codeEtabliss " )
                .setParameter("codeEtabliss", codeEtabliss).getSingleResult() ;
    }catch (Exception e){
        sousc_atten_etabliss1 = null ;
    }
    return sousc_atten_etabliss1 ;
  }



    public  List<sousc_atten_etabliss> getAllSouscriptionPersonnels(){
        try {
            return (List<sousc_atten_etabliss>) em.createQuery("select o from  sousc_atten_etabliss  o")

                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }

    public  List<ecoleDto2> getAllEcoleBySouscripFondateur(Long idFondat){
        TypedQuery<ecoleDto2> q = em.createQuery( "SELECT new com.vieecoles.dto.ecoleDto2(o.ecoleid,o.ecolecode,o.ecoleclibelle) from ecole o  where o.sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT=:idFondat",
        ecoleDto2.class);

List<ecoleDto2> listEcoleDto = q.setParameter("idFondat", idFondat).
                       getResultList();

return  listEcoleDto;
    }





    public void creerEtValiderEcole(souscriptionValidationDto mysouscription){
        validerSouscriptionEcole(mysouscription) ;
        creerEcoleBySouscrip(mysouscription.getIdsouscrip()) ;

    }

  @Transactional
    public void validerSouscriptionEcole(souscriptionValidationDto mysouscription){
        sousc_atten_etabliss  mysous= new sousc_atten_etabliss() ;
        mysous= (sousc_atten_etabliss) em.createQuery(" select e from sousc_atten_etabliss e   where e.idSOUS_ATTENT_ETABLISSEMENT =:souscripId "
                        ,sousc_atten_etabliss.class )
                .setParameter("souscripId",mysouscription.getIdsouscrip())
                .getSingleResult();

      Inscriptions.status status1= Inscriptions.status.valueOf(mysouscription.getStatuts());
        mysous.setSousc_atten_etabliss_statut(status1);
      mysous.setSousc_atten_etabliss_date_traitement(LocalDateTime.now());
      mysous.setSousc_atten_etabliss_motifRefus(mysouscription.getMessageRefus());
    }

    @Transactional
  public void creerEcoleBySouscrip(Long  idSouscrip ){ 
    ecole myEcole = new ecole();
    sousc_atten_etabliss  mysous= new sousc_atten_etabliss() ;
        mysous= (sousc_atten_etabliss) em.createQuery(" select e from sousc_atten_etabliss e   where e.idSOUS_ATTENT_ETABLISSEMENT =:souscripId "
                        ,sousc_atten_etabliss.class )
                .setParameter("souscripId",idSouscrip)
                .getSingleResult();
                System.out.print("mysous "+ mysous.toString());
                myEcole.setEcolecode(mysous.getSousc_atten_etablisscode());
                myEcole.setEcole_fondateur_contact(mysous.getSousc_atten_etabliss_fondateur());
                myEcole.setEcoleclibelle(mysous.getSousc_atten_etabliss_nom());
                //myEcole.setVille_villeid(mysous.getVille().getVilleid());
                myEcole.setEcole_telephone(mysous.getSousc_atten_etabliss_tel());
                myEcole.setZone_zoneid(mysous.getZone().getZoneid());
                myEcole.setSousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT(mysous.getIdSOUS_ATTENT_ETABLISSEMENT());
                myEcole.setNiveau_Enseignement_id(mysous.getNiveau_Enseignement_id());
                myEcole.persist();

                 }

    @Transactional
    public List<souscriptionEcoleDto> listTousLesSouscrEcole(Inscriptions.status status){
        List <souscriptionEcoleDto> minScription ;
        try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.souscriptionEcoleDto(o.idSOUS_ATTENT_ETABLISSEMENT ,o.sousc_atten_etablisscode ,o.sousc_atten_etabliss_nom,p.sous_attent_personn_nom ,p.sous_attent_personn_prenom,p.sous_attent_personn_contact,p.sous_attent_personn_contact2,o.sousc_atten_etabliss_indication,v.villeid,z.zoneid,f.fonctionid,v.villelibelle,z.zonelibelle,f.fonctionlibelle,o.sousc_atten_etabliss_statut,o.sousc_atten_etabliss_lien_autorisa,n.libelle)  from NiveauEnseignement n ,sousc_atten_etabliss o , sous_attent_personn  p join  o.ville v join o.zone z join p.fonction f " +
                            " where o.sous_attent_personn_sous_attent_personnid = p.sous_attent_personnid and o.Niveau_Enseignement_id = n.id and o.sousc_atten_etabliss_statut=: status ",souscriptionEcoleDto.class )
                    .setParameter("status",status)
                    .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }
    }

    @Transactional
    public List<souscriptionEcoleDto> listTousLesSouscrEcoleParFondateur(Long sousCripteur){
        List <souscriptionEcoleDto> minScription ;
        try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.souscriptionEcoleDto(o.idSOUS_ATTENT_ETABLISSEMENT ,o.sousc_atten_etablisscode ,o.sousc_atten_etabliss_nom,p.sous_attent_personn_nom ,p.sous_attent_personn_prenom,p.sous_attent_personn_contact,p.sous_attent_personn_contact2,o.sousc_atten_etabliss_indication,v.villeid,z.zoneid,f.fonctionid,v.villelibelle,z.zonelibelle,f.fonctionlibelle,o.sousc_atten_etabliss_statut,o.sousc_atten_etabliss_lien_autorisa ,n.libelle) from NiveauEnseignement n, sousc_atten_etabliss o , sous_attent_personn  p join  o.ville v join o.zone z join p.fonction f " +
                            " where o.sous_attent_personn_sous_attent_personnid = p.sous_attent_personnid  and o.sous_attent_personn_sous_attent_personnid=:sousCripteur and o.Niveau_Enseignement_id = n.id",souscriptionEcoleDto.class )
                    .setParameter("sousCripteur",sousCripteur)
                    .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }
    }




    public  sousc_atten_etabliss getSouscripByEmail2(String email){
     try {
         return (sousc_atten_etabliss) em.createQuery("select o from sousc_atten_etabliss o where o.sousc_atten_etabliss_email =:email1")
                 .setParameter("email1",email)
                 .getSingleResult();
     } catch (Exception e) {
         return  null;
     }


    }



    public  sousc_atten_etabliss getSouscPersonnelByID(Long identifiant){
        return   sousc_atten_etabliss.findById(identifiant) ;
    }


    @Transactional
    public sousc_atten_etabliss   modifierSousEcolel(sous_attent_ecoleDto souscriecole) {
        sousc_atten_etabliss  mysouscripPersonn1 = new sousc_atten_etabliss() ;
        mysouscripPersonn1= sousc_atten_etabliss.findById(souscriecole.getSousc_atten_etablissid()) ;


        ville myVille = new ville()  ;
        Zone myZone = new Zone() ;
        sousc_atten_etabliss  myecolePersonn = new sousc_atten_etabliss() ;

        myVille= ville.findById(souscriecole.getVille_villeid());
        myZone = Zone.findById(souscriecole.getZone_zoneid());

        mysouscripPersonn1.setSousc_atten_etabliss_email(souscriecole.getSousc_atten_etabliss_email());
        mysouscripPersonn1.setSousc_atten_etabliss_indication(souscriecole.getSousc_atten_etabliss_indication());
        mysouscripPersonn1.setSousc_atten_etabliss_nom(souscriecole.getSousc_atten_etabliss_nom());
        mysouscripPersonn1.setSousc_atten_etabliss_tel(souscriecole.getSousc_atten_etabliss_tel());
        mysouscripPersonn1.setVille(myVille);
        mysouscripPersonn1.setZone(myZone);
        return  mysouscripPersonn1 ;
    }

    @Transactional
    public void    deleteSousCriptionPersonnel(Long identifiant) {
        sous_attent_personn myPersonel = new sous_attent_personn() ;
        myPersonel = sous_attent_personn.findById(identifiant) ;

        try{
            myPersonel.delete();
        }catch (Exception e) {

        }

    }



    public  String getSouscrCode(String tenant) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        Long  maxrecor= (Long) em.createQuery("select max(o.sous_attent_personnid)  from sous_attent_personn o ")
               .getSingleResult();

        System.out.println("maxrecor "+maxrecor);

        String eleveCode= "GAIN"+yearInString+ String.valueOf(maxrecor) ;
        System.out.println("InscriptionCode "+eleveCode);

        return eleveCode ;
    }



}
