package com.vieecoles.services.souscription;

import com.vieecoles.dto.*;

import com.vieecoles.entities.*;
import com.vieecoles.entities.operations.*;

import com.vieecoles.services.connexion.connexionService;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.services.AnneeService;
import com.vieecoles.steph.services.EcoleHasMatiereService;
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
    @Inject
    AnneeService anneeService;
    @Inject
    EcoleHasMatiereService matiereEcoleService;
    @Inject
    connexionService myconnexionService ;
    @Inject
    SouscPersonnelService souscPersonnelService ;
    public String creationMiseAjourInfosFondatateur(r_info_fondateur fondateur){
        r_info_fondateur fondateur1 = new r_info_fondateur() ;
        fondateur1 = checkFondateur(fondateur.getFon_id_ecole()) ;
        String message= null ;
        if(fondateur1==null){
            fondateur.persist();
        } else {
            fondateur1.setFon_adresse(fondateur.getFon_adresse());
            fondateur1.setFon_cellulaire(fondateur.getFon_cellulaire());
            fondateur1.setFon_telephone(fondateur.getFon_telephone());
            fondateur1.setFon_email(fondateur.getFon_email());
            fondateur1.setFon_nomPrenoms(fondateur.getFon_nomPrenoms());
            fondateur1.setFon_code_etablissement(fondateur.getFon_code_etablissement());
            fondateur1.setFon_fonction(fondateur.getFon_fonction());
            fondateur1.setFon_id_ecole(fondateur.getFon_id_ecole());
        }
        message="INFORMATIONS ENREGISTREES" ;
        return message ;
    }

    public String creationMiseAjourInfosEtablisse(r_info_etablissement et){
        System.out.print("infos_etablisseDto "+et.toString());
        r_info_etablissement et1 = new r_info_etablissement() ;
        System.out.print("id_ecole "+et.getEtab_id_ecole());
        et1 = checkEtablissement(et.getEtab_id_ecole()) ;
        String message= null ;
        if(et1==null){
            et.persist();
        } else {
            et1.setEtab_adresse(et.getEtab_fax());
            et1.setEtab_fax(et.getEtab_fax());
            et1.setEtab_code_etablissement(et.getEtab_code_etablissement());
            et1.setEtab_denomination(et.getEtab_denomination());
            et1.setEtab_adresse(et.getEtab_adresse());
            et1.setEtab_id_ecole(et.getEtab_id_ecole());
            et1.setEtab_email(et.getEtab_email());
            et1.setEtab_situation_geographique(et.getEtab_situation_geographique());
            et1.setEtab_num_decision_ouverture(et.getEtab_num_decision_ouverture());
        }
        message="INFORMATIONS ENREGISTREES" ;
        return message ;
    }
    public String creationMiseAjourInfosDirecteurEtude(r_directeur_des_etudes et){
        r_directeur_des_etudes et1 = new r_directeur_des_etudes() ;
        et1 = checkDirecteurEtude(et.getDirect_id_ecole()) ;
        String message= null ;
        if(et1==null){
            et1.persist();
        } else {
           et1.setAutoriSatDirecteurEtude(et.getAutoriSatDirecteurEtude());
           et1.setEmailDirecteurEtude(et.getEmailDirecteurEtude());
           et1.setDirect_adresse(et.getDirect_adresse());
           et1.setDirect_cellulaire(et.getDirect_cellulaire());
           et1.setDirect_code_etablissement(et.getDirect_code_etablissement());
           et1.setDirect_email(et.getDirect_email());
           et1.setDirect_nom_prenom(et.getDirect_nom_prenom());
           et1.setDirect_telephone(et.getDirect_telephone());
           et1.setDirect_numero_autorisation_enseigner(et.getDirect_numero_autorisation_enseigner());
           et1.setDirect_id_ecole(et.getDirect_id_ecole());
        }
        message="INFORMATIONS ENREGISTREES" ;
        return message ;
    }



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
        mysouscripPersonn1.setCommune_communeid(souscriecole.getCommune_communeid());
        mysouscripPersonn1.setZone_zoneid(souscriecole.getZone_zoneid());
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

    public  r_info_fondateur checkFondateur(Long idEcole){
        try {
            return (r_info_fondateur) em.createQuery("select o from r_info_fondateur o where o.fon_id_ecole =:idEcole")
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }
    public r_info_etablissement checkEtablissement(Long idEcole){
        try {
            return (r_info_etablissement) em.createQuery("select o from r_info_etablissement o where o.etab_id_ecole =:idEcole")
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }

    public r_directeur_des_etudes checkDirecteurEtude(Long idEcole){
        try {
            return (r_directeur_des_etudes) em.createQuery("select o from r_directeur_des_etudes o where o.direct_id_ecole =:idEcole")
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }


     @Transactional
     public String  creerCompteUtilisateur(CreerCompteUtilsateurDto creerCompte){

      utilisateur myNewInser = new utilisateur() ;
      myNewInser.setUtilisateu_email(creerCompte.getUtilisateu_email());
      myNewInser.setUtilisateu_login(creerCompte.getUtilisateu_login());
      myNewInser.setSous_attent_personn_sous_attent_personnid(creerCompte.getSous_attent_personn_sous_attent_personnid());
      myNewInser.setUtilisateur_mot_de_passe(creerCompte.getUtilisateur_mot_de_passe());
      myNewInser.persist();
      return  "Demande créée avec succes !";

     }




        public String creerSouscripEtablissement(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2 ,String email ,String password,String login,List<sous_attent_ecoleDto> listsouscr){
           String messaRetour = null ;
           utilisateur myUtili= new utilisateur() ;
            myUtili = myconnexionService.verifiEmailUtilisateur(email);
            if(myUtili==null){
                System.out.println("Fondateur2 "+login);
                return    creerLesEcoles(fonctionId,nom,prenom,contact1,contact2,email,password,login,listsouscr) ;
            } else {
               return  messaRetour= "Cet utilisateur à déjà un compte";
            }

        }





    public String ModifierSouscripEtablissement(Long souscripId ,String nom, String prenom, String contact1 ,String contact2 ,String email ,List<sous_attent_ecoleDto> listsouscr){
        String messaRetour = null ;

        return    modifierLesEcoles(souscripId,nom,prenom,contact1,contact2,email,listsouscr) ;


    }
    public String chargerPhotoBulletinEcole(byte[] bytes,String codeEcole,String signataire){
      String message;
        sousc_atten_etabliss  sousEtabli= new sousc_atten_etabliss() ;
        ecole myEcole = new ecole() ;
        sousEtabli = checkExistEtabliss(codeEcole) ;
        myEcole = getEcoleIdBySouscrId(sousEtabli.getIdSOUS_ATTENT_ETABLISSEMENT()) ;
        sousEtabli.setLogoBlob(bytes);
        sousEtabli.setNom_signataire(signataire);
        myEcole.setLogoBlob(bytes);
        myEcole.setNom_signataire(signataire);
        message="Informations mises à jour avec succès!";
      return  message;
    }
    public String chargerFiligraneEcole(byte[] bytes,String codeEcole){
        String message;
        sousc_atten_etabliss  sousEtabli= new sousc_atten_etabliss() ;
        ecole myEcole = new ecole() ;
        sousEtabli = checkExistEtabliss(codeEcole) ;
        myEcole = getEcoleIdBySouscrId(sousEtabli.getIdSOUS_ATTENT_ETABLISSEMENT()) ;
        sousEtabli.setFiligramme(bytes);
        myEcole.setFiligramme(bytes);
        message="Photo chargée avec succès!";
        return  message;
    }
    @Transactional
    public String modifierLesEcoles(Long souscripId ,String nom, String prenom, String contact1 ,String contact2 ,String email ,List<sous_attent_ecoleDto> listsouscr ) {
        modifierInfosPersonnel(souscripId,nom,prenom,contact1,contact2,email);

        System.out.println("Longueur Tableau listsouscr+++ "+listsouscr.size());
        String messageRetour ="" ;
        List<String> matriculeNonCreer = new ArrayList<>();
        for(int i = 0 ; i < listsouscr.size() ; i++)
        {
            sousc_atten_etabliss  sousEtabli= new sousc_atten_etabliss() ;
            sousEtabli = checkExistEtabliss(listsouscr.get(i).getSousc_atten_etablisscode()) ;
            System.out.println("sousEtabli---- "+sousEtabli);


            sousEtabli.setSousc_atten_etabliss_email(listsouscr.get(i).getSousc_atten_etabliss_email());
            sousEtabli.setZone_zoneid(listsouscr.get(i).getZone_zoneid());
            sousEtabli.setCommune_communeid(listsouscr.get(i).getCommune_communeid());
            sousEtabli.setSousc_atten_etabliss_indication(listsouscr.get(i).getSousc_atten_etabliss_indication()) ;
            sousEtabli.setSousc_atten_etabliss_nom(listsouscr.get(i).getSousc_atten_etabliss_nom()) ;
            sousEtabli.setSousc_atten_etabliss_tel(listsouscr.get(i).getSousc_atten_etabliss_tel()) ;
            sousEtabli.setSousc_atten_etablisscode(listsouscr.get(i).getSousc_atten_etablisscode()) ;
            sousEtabli.setSousc_atten_etabliss_lien_autorisa(listsouscr.get(i).getSousc_atten_etabliss_lien_autorisa());
            sousEtabli.setSousc_atten_etabliss_lien_logo(listsouscr.get(i).getSousc_atten_etabliss_lien_logo());
            sousEtabli.setNiveau_Enseignement_id(listsouscr.get(i).getNiveau_Enseignement_id());
        }
        messageRetour ="DEMANDE D'INSCRIPTION MODIFIEE AVEC SUCCES!" ;

        return  messageRetour ;

    }

    @Transactional
    public Long modifierInfosPersonnel(Long souscripId ,String nom, String prenom, String contact1 ,String contact2,String email){
        sous_attent_personn sous_attent_personn= new sous_attent_personn() ;
        sous_attent_personn = souscPersonnelService.findPersonnelById(souscripId) ;
        sous_attent_personn.setSous_attent_personn_nom(nom);
        sous_attent_personn.setSous_attent_personn_prenom(prenom);
        sous_attent_personn.setSous_attent_personn_contact(contact1);
        sous_attent_personn.setSous_attent_personn_contact2(contact2);
        sous_attent_personn.setSous_attent_personn_email(email);

        System.out.println("Personnel "+sous_attent_personn.toString());
        return sous_attent_personn.getSous_attent_personnid() ;
    }






        public sous_attent_personn CreerPersonnelSouscriAndCompteUser(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2,String email,
        String password,String login) {
            sous_attent_personn sous_attent_personn= new sous_attent_personn() ;
            //Creer souscripteur
            sous_attent_personn= creerPersonnel(fonctionId,nom,prenom,contact1,contact2,email) ;
            CreerCompteUtilsateurDto comptUserDto = new CreerCompteUtilsateurDto() ;
            comptUserDto.setSous_attent_personn_sous_attent_personnid(sous_attent_personn.getSous_attent_personnid());
            comptUserDto.setUtilisateu_email(email);
            comptUserDto.setUtilisateu_login(login);
            comptUserDto.setUtilisateur_mot_de_passe(password);
               //Creer compte souscripteur
            creerCompteUtilisateur(comptUserDto) ;
            return sous_attent_personn ;
        }


    public List<etablissementAmodifierDto> getListSouscEcoleParFondateur(Long idSouscrip){
        TypedQuery<etablissementAmodifierDto> q = em.createQuery( "SELECT distinct new com.vieecoles.dto.etablissementAmodifierDto(o.idSOUS_ATTENT_ETABLISSEMENT, o.sousc_atten_etablisscode,o.sousc_atten_etabliss_nom,o.sousc_atten_etabliss_tel,o.sousc_atten_etabliss_email,o.sousc_atten_etabliss_indication,v,c,z,n,o.sousc_atten_etabliss_lien_autorisa,v.myDirection_regionale , v.myDirection_regionale.pays,o.sousc_atten_etabliss_lien_logo,o.sousc_atten_etabliss_statut) from sousc_atten_etabliss o left join zone z on o.zone_zoneid=z.zoneid ,commune  c ,ville v, NiveauEnseignement n where o.commune_communeid=c.communeid  and v.villeid=c.ville.villeid and o.Niveau_Enseignement_id= n.id and o.sous_attent_personn_sous_attent_personnid=:idFondat",
                etablissementAmodifierDto.class);

        List<etablissementAmodifierDto> listEcoleDto = q.setParameter("idFondat", idSouscrip).
                getResultList();
return listEcoleDto ;
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
    public String ajouterLesEcoles(Long souscripteurId,List<sous_attent_ecoleDto> listsouscr ) {
        /*sous_attent_personn sous = new sous_attent_personn() ;
        sous = CreerPersonnelSouscriAndCompteUser(fonctionId,nom,prenom,contact1,contact2,email,password);

        System.out.println("sous+++ "+sous.toString());
        System.out.println("Longueur Tableau listsouscr+++ "+listsouscr.size());*/
        String messageRetour ="" ;
        List<String> matriculeNonCreer = new ArrayList<>();
        for(int i = 0 ; i < listsouscr.size() ; i++)
        {
            sousc_atten_etabliss  sousc_atten_etabliss1= new sousc_atten_etabliss() ;
            sousc_atten_etabliss1 = checkExistEtabliss(listsouscr.get(i).getSousc_atten_etablisscode()) ;
            sousc_atten_etabliss sousEtabli = new sousc_atten_etabliss() ;

            if(sousc_atten_etabliss1 ==null ){
                sousEtabli.setSousc_atten_etabliss_email(listsouscr.get(i).getSousc_atten_etabliss_email());
                sousEtabli.setSous_attent_personn_sous_attent_personnid(souscripteurId);
                sousEtabli.setZone_zoneid(listsouscr.get(i).getZone_zoneid());
                sousEtabli.setCommune_communeid(listsouscr.get(i).getCommune_communeid());
                sousEtabli.setSousc_atten_etabliss_statut(Inscriptions.status.EN_ATTENTE);
                sousEtabli.setSousc_atten_etabliss_indication(listsouscr.get(i).getSousc_atten_etabliss_indication()) ;
                sousEtabli.setSousc_atten_etabliss_nom(listsouscr.get(i).getSousc_atten_etabliss_nom()) ;
                sousEtabli.setSousc_atten_etabliss_tel(listsouscr.get(i).getSousc_atten_etabliss_tel()) ;
                sousEtabli.setSousc_atten_etablisscode(listsouscr.get(i).getSousc_atten_etablisscode()) ;
                sousEtabli.setSousc_atten_etabliss_lien_autorisa(listsouscr.get(i).getSousc_atten_etabliss_lien_autorisa());
                sousEtabli.setSousc_atten_etabliss_lien_logo(listsouscr.get(i).getSousc_atten_etabliss_lien_logo());

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


  @Transactional
  public String creerLesEcoles(Long fonctionId ,String nom, String prenom, String contact1 ,String contact2 ,String email ,String password,String login ,List<sous_attent_ecoleDto> listsouscr ) {
    sous_attent_personn sous = new sous_attent_personn() ;
    sous = CreerPersonnelSouscriAndCompteUser(fonctionId,nom,prenom,contact1,contact2,email,password,login);

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
          //Zone myZone = Zone.findById(listsouscr.get(i).getZone_zoneid()) ;

          if(sousc_atten_etabliss1 ==null ){
              sousEtabli.setSousc_atten_etabliss_email(listsouscr.get(i).getSousc_atten_etabliss_email());
              sousEtabli.setSous_attent_personn_sous_attent_personnid(sous.getSous_attent_personnid());

              sousEtabli.setZone_zoneid(listsouscr.get(i).getZone_zoneid());
              sousEtabli.setCommune_communeid(listsouscr.get(i).getCommune_communeid());
              sousEtabli.setSousc_atten_etabliss_statut(Inscriptions.status.EN_ATTENTE);
              sousEtabli.setSousc_atten_etabliss_indication(listsouscr.get(i).getSousc_atten_etabliss_indication()) ;
              sousEtabli.setSousc_atten_etabliss_nom(listsouscr.get(i).getSousc_atten_etabliss_nom()) ;
              sousEtabli.setSousc_atten_etabliss_tel(listsouscr.get(i).getSousc_atten_etabliss_tel()) ;
              sousEtabli.setSousc_atten_etablisscode(listsouscr.get(i).getSousc_atten_etablisscode()) ;
               sousEtabli.setSousc_atten_etabliss_lien_autorisa(listsouscr.get(i).getSousc_atten_etabliss_lien_autorisa());
              sousEtabli.setSousc_atten_etabliss_lien_logo(listsouscr.get(i).getSousc_atten_etabliss_lien_logo());

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
@Transactional
    public  ecole getEcoleIdBySouscrId(Long  idCodeEtabli) {
        ecole  myEcole= new ecole() ;
        try {
            myEcole= (ecole) em.createQuery("select o from ecole o  where o.sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT=:idCodeEtabli " )
                    .setParameter("idCodeEtabli", idCodeEtabli).getSingleResult() ;
        }catch (Exception e){
            myEcole = null ;
        }
        return myEcole ;
    }



    public  ecole getInffosEcoleByID(Long  idEtabli) {
        ecole  myEcole= new ecole() ;
        try {
            myEcole= (ecole) em.createQuery("select o from ecole o  where o.ecoleid =:idEtabli " )
                    .setParameter("idEtabli", idEtabli).getSingleResult() ;
        }catch (Exception e){
            myEcole = null ;
        }
        return myEcole ;
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
        generateMatiereByEcole(mysouscription.getIdsouscrip()) ;
        iniAnneeForEcole(mysouscription.getIdsouscrip());

    }
    @Transactional
    public  void generateMatiereByEcole(Long idSouscrip){
        ecole  myecole = new ecole() ;
        myecole = (ecole) em.createQuery(" select e from ecole e   where e.sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT =:souscripId "
                        ,ecole.class )
                .setParameter("souscripId",idSouscrip)
                .getSingleResult();
        Ecole ecole = Ecole.findById(myecole.getEcoleid());
        matiereEcoleService.generateMatieres(ecole) ;

    }
    @Transactional
    public void iniAnneeForEcole(Long idSouscrip){
        ecole  myecole = new ecole() ;
        myecole = (ecole) em.createQuery(" select e from ecole e   where e.sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT =:souscripId "
                        ,ecole.class )
                .setParameter("souscripId",idSouscrip)
                .getSingleResult();
        Ecole ecole = Ecole.findById(myecole.getEcoleid());
        anneeService.initAnneeEcole(ecole.getId());

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
      mysous.setConnecte(0);
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
                myEcole.setZone_zoneid(mysous.getZone_zoneid());
                myEcole.setCommune_communeid(mysous.getCommune_communeid());
                myEcole.setSousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT(mysous.getIdSOUS_ATTENT_ETABLISSEMENT());
                myEcole.setNiveau_Enseignement_id(mysous.getNiveau_Enseignement_id());
                myEcole.persist();

                 }

    @Transactional
    public List<etablissementDto> listTousLesSouscrEcole(Inscriptions.status status){


        TypedQuery<etablissementDto> q = em.createQuery( "SELECT distinct new com.vieecoles.dto.etablissementDto(o.idSOUS_ATTENT_ETABLISSEMENT, o.sousc_atten_etablisscode,o.sousc_atten_etabliss_nom,o.sousc_atten_etabliss_tel,o.sousc_atten_etabliss_email,o.sousc_atten_etabliss_indication,v.villelibelle,c.communelibelle,z.zonelibelle,n.libelle,o.sousc_atten_etabliss_lien_autorisa,v.myDirection_regionale.libelle , v.myDirection_regionale.pays.payslibelle,o.sousc_atten_etabliss_lien_logo,p.sous_attent_personn_nom,p.sous_attent_personn_prenom) from sousc_atten_etabliss o left join zone z on o.zone_zoneid=z.zoneid ,commune  c ,ville v, NiveauEnseignement n,sous_attent_personn p where o.commune_communeid=c.communeid  and v.villeid=c.ville.villeid and o.Niveau_Enseignement_id= n.id and o.sous_attent_personn_sous_attent_personnid =p.sous_attent_personnid and o.sousc_atten_etabliss_statut=:status",
                etablissementDto.class);

        List<etablissementDto> listEcoleDto = q.setParameter("status", status).
                getResultList();

        return listEcoleDto ;


   /*     try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.souscriptionEcoleDto(o.idSOUS_ATTENT_ETABLISSEMENT ,o.sousc_atten_etablisscode ,o.sousc_atten_etabliss_nom,p.sous_attent_personn_nom ,p.sous_attent_personn_prenom,p.sous_attent_personn_contact,p.sous_attent_personn_contact2,o.sousc_atten_etabliss_indication,z.zoneid,f.fonctionid,z.zonelibelle,f.fonctionlibelle,o.sousc_atten_etabliss_lien_autorisa,n.libelle,c.id,c.communelibelle,o.sousc_atten_etabliss_statut)  from NiveauEnseignement n ,sousc_atten_etabliss o , sous_attent_personn  p ,commune  c join p.fonction f " +
                            " where o.sous_attent_personn_sous_attent_personnid = p.sous_attent_personnid and o.commune_communeid= c.communeid and o.Niveau_Enseignement_id = n.id and o.sousc_atten_etabliss_statut=: status ",souscriptionEcoleDto.class )
                    .setParameter("status",status)
                    .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }*/
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
        mysouscripPersonn1.setCommune_communeid(souscriecole.getCommune_communeid());
        mysouscripPersonn1.setZone_zoneid(souscriecole.getZone_zoneid());
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
