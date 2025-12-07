package com.vieecoles.services.eleves;

import com.vieecoles.dto.InscriptionAvaliderDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.dto.ListeElevesClasse;
import com.vieecoles.entities.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class InscriptionService implements PanacheRepositoryBase<Inscriptions, Long> {
    @Inject
    EntityManager em;



 public  String getIncripCode(String tenant) {
     Calendar now = Calendar.getInstance();
     int year = now.get(Calendar.YEAR);
     String yearInString = String.valueOf(year);

   Long  maxrecor= (Long) em.createQuery("select max(o.inscriptionsid)  from Inscriptions o " )
                    .getSingleResult();

   System.out.println("maxrecor "+maxrecor);

     String InscriptionCode= "INS"+yearInString+ String.valueOf(maxrecor) ;
     System.out.println("InscriptionCode "+InscriptionCode);

 return InscriptionCode ;
 }



    public  List<InscriptionAvaliderDto> findAllInscriptionAvaliderDto(String idecole, Inscriptions.status Status ,Inscriptions.processus processus  ){

        TypedQuery<InscriptionAvaliderDto> q = em.createQuery( "SELECT new com.vieecoles.dto.InscriptionAvaliderDto(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type ,o.inscriptions_processus ,o.branche.id ,o.branche.libelle) from Inscriptions o join  o.eleve  q join o.ecole e where e.ecoleid=:idecole " +
                        " and  o.inscriptions_status=: Status  and o.inscriptions_processus=: processus",
                InscriptionAvaliderDto.class);

        List<InscriptionAvaliderDto> listInscriptionAvaliderDto = q.setParameter("idecole" ,idecole)
                .setParameter("Status" ,Status).setParameter("processus",processus).
        getResultList();

        return  listInscriptionAvaliderDto;
    }


   /* public  List<InscriptionAvaliderDto> findInscriptionValider(String tenant, Inscriptions.status Status   ){

        TypedQuery<InscriptionAvaliderDto> q = em.createQuery( "SELECT new com.vieecoles.dto.InscriptionAvaliderDto(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type,o.inscriptions_processus  ) from Inscriptions o join  o.eleve  q where q.tenant.tenantid=:tenant" +
                        " and o.inscriptions_status=: Status",
                InscriptionAvaliderDto.class);

        List<InscriptionAvaliderDto> listInscriptionAvaliderDto = q.
                setParameter("tenant" ,tenant)
                .setParameter("Status" ,Status)
                .getResultList();

        return  listInscriptionAvaliderDto;
    }*/





    public  String getPreIncripCode(String tenant) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        Long  maxrecor= (Long) em.createQuery("select Max(o.inscriptionsid) from Inscriptions o" )
                     .getSingleResult();
        String InscriptionCode= "PREINS"+yearInString+ String.valueOf(maxrecor) ;
        return InscriptionCode ;
    }

   @Transactional
   public String createinscription(InscriptionDto inscriptionDto) {
       Inscriptions inscr = new Inscriptions() ;
       List<Libellehandicap> libellehandicapList= new ArrayList<>() ;

       System.out.println("Mon objet1");

       for(int i = 0 ; i < inscriptionDto.getLibellehandicap().size() ; i++)
       {
           Libellehandicap libellehandicap = new Libellehandicap() ;
           libellehandicap= Libellehandicap.findById(inscriptionDto.getLibellehandicap().get(i)) ;
           libellehandicapList.add(libellehandicap) ;
       }


       System.out.println("Mon objet74444545545445//"+inscriptionDto.getIdentifiantEleve());

       System.out.println("Mon objet3");
       eleve  eleve1= eleve.findById(inscriptionDto.getIdentifiantEleve());
       System.out.println("Mon objet////");
       ecole ecole1= ecole.findById(inscriptionDto.getIdentifiantEcole()) ;
       System.out.println("Mon objet4");
       Annee_Scolaire annee_scolaire= Annee_Scolaire.findById(inscriptionDto.getIdentifiantAnnee_scolaire()) ;
       System.out.println("Mon objet5 ");
        Branche myBranche = Branche.findById(inscriptionDto.getIdentifiantBranche());

       System.out.println("Mon objet7"+annee_scolaire);

       System.out.println("Mon objet8"+annee_scolaire);
      inscr.setInscriptions_delete(false);
       System.out.println("Mon objet9"+annee_scolaire);
       inscr.setInscriptionsdate_creation(LocalDate.now());
       System.out.println("Mon objet19"+annee_scolaire);
      if (inscriptionDto.getInscriptions_type() == Inscriptions.typeOperation.INSCRIPTION) {
            inscr.setEcole(ecole1);
          inscr.setEleve(eleve1);

          inscr.setAnnee_scolaire(annee_scolaire);
          inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
          inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
          // inscr.setLibellehandicap(libellehandicapList);
          inscr.setInscriptions_processus(Inscriptions.processus.EN_COURS);
          inscr.setInscriptions_status(Inscriptions.status.EN_ATTENTE);
          inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
          inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
          inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
          inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
          inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
          inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
          inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
          inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());
          inscr.setBranche(myBranche);
          inscr.persist();

      } else {
          inscr.setEcole(ecole1);
          inscr.setEleve(eleve1);
           inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
          inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
        //  inscr.setLibellehandicap(libellehandicapList);
          inscr.setInscriptions_processus(Inscriptions.processus.EN_COURS);
          inscr.setInscriptions_status(Inscriptions.status.EN_ATTENTE);
          inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
          inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
          inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
          inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
          inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
          inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
          inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
          inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());
          inscr.setAnnee_scolaire(annee_scolaire);
          inscr.setBranche(myBranche);
          inscr.persist();
      }

       return "DEMANDE D'INSCRIPTION EFFECTUEE AVEC SUCCES!" ;

   }

    @Transactional
    public String updateinscriptionImporter(Long idInscrip ,InscriptionDto inscriptionDto) {
        Inscriptions inscr = new Inscriptions() ;
        inscr = Inscriptions.findById(idInscrip) ;
        List<Libellehandicap> libellehandicapList= new ArrayList<>() ;

        System.out.println("Mon objet1");



        System.out.println("Mon objet74444545545445//"+inscriptionDto.getIdentifiantEleve());

        System.out.println("Mon objet3");
        eleve  eleve1= eleve.findById(inscriptionDto.getIdentifiantEleve());
        System.out.println("Mon objet////");
        ecole ecole1= ecole.findById(inscriptionDto.getIdentifiantEcole()) ;
        System.out.println("Mon objet4");
        Annee_Scolaire annee_scolaire= Annee_Scolaire.findById(inscriptionDto.getIdentifiantAnnee_scolaire()) ;
        System.out.println("Mon objet5 ");
        Branche myBranche = Branche.findById(inscriptionDto.getIdentifiantBranche());

        if (inscriptionDto.getInscriptions_type() == Inscriptions.typeOperation.INSCRIPTION) {

            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());

            inscr.setInscriptions_boursier(inscriptionDto.getInscriptions_boursier());
            inscr.setInscriptions_redoublant(inscriptionDto.getInscriptions_redoublant());
            inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
            inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
            inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
            inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
            inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
            inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
            inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
            inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());

            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setBranche(myBranche);
            inscr.setIvoirien(inscriptionDto.getIvoirien());
            inscr.setEtranger_africain(inscriptionDto.getEtranger_africain());
            inscr.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());
            inscr.setCheminphoto(inscriptionDto.getCheminphoto());

        } else {
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
            inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
            inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
            inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
            inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
            inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
            inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
            inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
            inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());
            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setBranche(myBranche);
            inscr.setIvoirien(inscriptionDto.getIvoirien());
            inscr.setEtranger_africain(inscriptionDto.getEtranger_africain());
            inscr.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());
            inscr.setCheminphoto(inscriptionDto.getCheminphoto());

        }

        return "INSCRIPTION MODIFIEE AVEC SUCCES!" ;

    }


    @Transactional
    public String createinscriptionImporter(InscriptionDto inscriptionDto) {
        Inscriptions inscr = new Inscriptions() ;
        List<Libellehandicap> libellehandicapList= new ArrayList<>() ;

        System.out.println("Mon objet1");

        for(int i = 0 ; i < inscriptionDto.getLibellehandicap().size() ; i++)
        {
            Libellehandicap libellehandicap = new Libellehandicap() ;
            libellehandicap= Libellehandicap.findById(inscriptionDto.getLibellehandicap().get(i)) ;
            libellehandicapList.add(libellehandicap) ;
        }


        System.out.println("Mon objet74444545545445//"+inscriptionDto.getIdentifiantEleve());

        System.out.println("Mon objet3");
        eleve  eleve1= eleve.findById(inscriptionDto.getIdentifiantEleve());
        System.out.println("Mon objet////");
        ecole ecole1= ecole.findById(inscriptionDto.getIdentifiantEcole()) ;
        System.out.println("Mon objet4");
        Annee_Scolaire annee_scolaire= Annee_Scolaire.findById(inscriptionDto.getIdentifiantAnnee_scolaire()) ;
        System.out.println("Mon objet5 ");
        Branche myBranche = Branche.findById(inscriptionDto.getIdentifiantBranche());

        System.out.println("Mon objet7"+annee_scolaire);

        System.out.println("Mon objet8"+annee_scolaire);
        inscr.setInscriptions_delete(false);
        System.out.println("Mon objet9"+annee_scolaire);
        inscr.setInscriptionsdate_creation(LocalDate.now());
        System.out.println("Mon objet19"+annee_scolaire);
        if (inscriptionDto.getInscriptions_type() == Inscriptions.typeOperation.INSCRIPTION) {
            inscr.setEcole(ecole1);
            inscr.setEleve(eleve1);

            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
           // inscr.setLibellehandicap(libellehandicapList);
            inscr.setInscriptions_processus(Inscriptions.processus.EN_COURS);
            inscr.setInscriptions_status(Inscriptions.status.VALIDEE);
            inscr.setInscriptions_boursier(inscriptionDto.getInscriptions_boursier());
            inscr.setInscriptions_redoublant(inscriptionDto.getInscriptions_redoublant());
            inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
            inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
            inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
            inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
            inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
            inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
            inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
            inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());
            inscr.setBranche(myBranche);
            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setIvoirien(inscriptionDto.getIvoirien());
            inscr.setEtranger_africain(inscriptionDto.getEtranger_africain());
            inscr.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());
            inscr.persist();

        } else {
            inscr.setEcole(ecole1);
            inscr.setEleve(eleve1);
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
        //    inscr.setLibellehandicap(libellehandicapList);
            inscr.setInscriptions_processus(Inscriptions.processus.EN_COURS);
            inscr.setInscriptions_status(Inscriptions.status.VALIDEE);
            inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
            inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
            inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
            inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
            inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
            inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
            inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
            inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());

            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setBranche(myBranche);
            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setIvoirien(inscriptionDto.getIvoirien());
            inscr.setEtranger_africain(inscriptionDto.getEtranger_africain());
            inscr.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());
            inscr.persist();
        }

        return "DEMANDE D'INSCRIPTION EFFECTUEE AVEC SUCCES!" ;

    }

    @Transactional
    public Inscriptions createinscriptionVieEcole(InscriptionDto inscriptionDto) {
        Inscriptions inscr = new Inscriptions() ;
        List<Libellehandicap> libellehandicapList= new ArrayList<>() ;

        System.out.println("Mon objet1");

        for(int i = 0 ; i < inscriptionDto.getLibellehandicap().size() ; i++)
        {
            Libellehandicap libellehandicap = new Libellehandicap() ;
            libellehandicap= Libellehandicap.findById(inscriptionDto.getLibellehandicap().get(i)) ;
            libellehandicapList.add(libellehandicap) ;
        }


        System.out.println("Mon objet74444545545445//"+inscriptionDto.getIdentifiantEleve());

        System.out.println("Mon objet3");
        eleve  eleve1= eleve.findById(inscriptionDto.getIdentifiantEleve());
        System.out.println("Mon objet////");
        ecole ecole1= ecole.findById(inscriptionDto.getIdentifiantEcole()) ;
        System.out.println("Mon objet4");
        Annee_Scolaire annee_scolaire= Annee_Scolaire.findById(inscriptionDto.getIdentifiantAnnee_scolaire()) ;
        System.out.println("Mon objet5 ");
        Branche myBranche = Branche.findById(inscriptionDto.getIdentifiantBranche());

        System.out.println("Mon objet7"+annee_scolaire);

        System.out.println("Mon objet8"+annee_scolaire);
        inscr.setInscriptions_delete(false);
        System.out.println("Mon objet9"+annee_scolaire);
        inscr.setInscriptionsdate_creation(LocalDate.now());
        System.out.println("Mon objet19"+annee_scolaire);
        if (inscriptionDto.getInscriptions_type() == Inscriptions.typeOperation.INSCRIPTION) {
            inscr.setEcole(ecole1);
            inscr.setEleve(eleve1);

            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
            // inscr.setLibellehandicap(libellehandicapList);
            inscr.setInscriptions_processus(Inscriptions.processus.EN_COURS);
            inscr.setInscriptions_status(Inscriptions.status.VALIDEE);
            inscr.setInscriptions_boursier(inscriptionDto.getInscriptions_boursier());
            inscr.setInscriptions_redoublant(inscriptionDto.getInscriptions_redoublant());
            inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
            inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
            inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
            inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
            inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
            inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
            inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
            inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());
            inscr.setBranche(myBranche);
            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setIvoirien(inscriptionDto.getIvoirien());
            inscr.setEtranger_africain(inscriptionDto.getEtranger_africain());
            inscr.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());
            inscr.setCheminphoto(inscriptionDto.getCheminphoto());
            inscr.persist();

        } else {
            inscr.setEcole(ecole1);
            inscr.setEleve(eleve1);
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
            //    inscr.setLibellehandicap(libellehandicapList);
            inscr.setInscriptions_processus(Inscriptions.processus.EN_COURS);
            inscr.setInscriptions_status(Inscriptions.status.VALIDEE);
            inscr.setInscriptions_contact1(inscriptionDto.getInscriptions_contact1());
            inscr.setInscriptions_contact2(inscriptionDto.getInscriptions_contact2());
            inscr.setInscriptions_code_interne(inscriptionDto.getInscriptions_code_interne());
            inscr.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
            inscr.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
            inscr.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
            inscr.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
            inscr.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());

            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setBranche(myBranche);
            inscr.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
            inscr.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
            inscr.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
            inscr.setDecision_ant(inscriptionDto.getDecision_ant());
            inscr.setIvoirien(inscriptionDto.getIvoirien());
            inscr.setEtranger_africain(inscriptionDto.getEtranger_africain());
            inscr.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());
            inscr.persist();
        }

        return inscr;

    }


               public Inscriptions checkInscrit(Long idEcole , String matricule, Long idAnnee,Long idBranche){
                   try {
                       // Option A: Max par ID (si l'ID représente l'ordre chronologique)
                       Long maxId = (Long) em.createQuery(
                               "SELECT MAX(i.id) FROM Inscriptions i " +
                                   "WHERE i.eleve.eleveid = (SELECT e.eleveid FROM eleve e WHERE e.eleve_matricule = :matricule) " +
                                   "AND i.ecole.ecoleid = :idecole " +
                                   "AND i.annee_scolaire.annee_scolaireid = :idAnnee AND i.branche.id=:idBranche"
                           )
                           .setParameter("idecole", idEcole)
                           .setParameter("matricule", matricule)
                           .setParameter("idAnnee", idAnnee)
                           .setParameter("idBranche", idBranche)
                           .getSingleResult();

                       // Puis récupérer l'entité avec cet ID
                       if (maxId != null) {
                           return em.find(Inscriptions.class, maxId);
                       }
                       return null;

                   } catch (Exception e) {
                       e.printStackTrace();
                       return null;
                   }
              }

    public Inscriptions checkInscritOld(Long idEcole , String matricule, Long idAnnee){
        Inscriptions minScription = new Inscriptions() ;
        try {
            return    minScription = (Inscriptions) em.createQuery("select o from Inscriptions o join  o.ecole e  join  o.eleve l join o.annee_scolaire " +
                    " where o.ecole.ecoleid =:idecole and o.eleve.eleve_matricule =: matricule and o.annee_scolaire.annee_scolaireid =:idAnnee and o.branche.id=:branche " )
                .setParameter("idecole", idEcole)
                .setParameter("matricule",matricule)
                .setParameter("idAnnee",idAnnee)
                .setParameter("branche",1L)
                .getSingleResult();
        } catch (Exception e){
            return  null ;
        }
    }

    @Transactional
    public List<InscriptionAvaliderDto> listTousLesInscription(Long idEcole , Long idAnnee, Inscriptions.typeOperation typeOperation){
        List <InscriptionAvaliderDto> minScription ;
        try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.InscriptionAvaliderDto(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type ,o.inscriptions_processus, o.branche.id ,o.branche.libelle ,o.nom_prenoms_pere ,o.nom_prenoms_mere,o.nom_prenoms_tuteur,o.commune ,o.handicap, o.moteur,o.visuel ," +
                            " o.auditif ,o.reaffecte ,o.regularisation ,o.reintegration ,o.prise_en_charge ,o.origine_prise_en_charge ,o.profession_pere,o.boite_postal_pere," +
                            "o.tel1_pere ,o.tel2_pere , o.profession_mere ,o.boite_postal_mere,o.tel1_mere,o.tel2_mere ,o.profession_tuteur,o.boite_postal_tuteur,o.tel1_tuteur," +
                            " o.tel2_tuteur ,o.profession_pers_en_charge ,o.boite_postale_pers_en_charge ,o.tel1_pers_en_charge , o.tel2_pers_en_charge ,o.autre_handicap ,o.nom_prenom_pers_en_charge ,o.classe_arabe , o.inscriptions_ecole_origine ,o.transfert ,o.num_decision_affecte ,o.inscriptions_langue_vivante ,o.inscriptions_redoublant , o.inscriptions_boursier ,o.internes ,o.demi_pension ,o.externes,o.ivoirien ,o.etranger_africain ,o.etranger_non_africain ,o.cheminphoto ,q.eleve_nationalite ,q.elevelieu_naissance) from Inscriptions o join  o.ecole e  join  o.eleve q join o.annee_scolaire n " +
                            " where o.ecole.ecoleid =:idecole and o.annee_scolaire.annee_scolaireid =:idAnnee  and o.inscriptions_type=: typeInscrip ",InscriptionAvaliderDto.class )
                    .setParameter("idecole", idEcole)
                    .setParameter("typeInscrip", typeOperation)
                     .setParameter("idAnnee",idAnnee)
                    .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }
    }

    @Transactional
    public List<ListeElevesClasse> listDesElevesInscrits(Long idEcole , Long idAnnee){
        List <ListeElevesClasse> minScription ;
        try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.ListeElevesClasse(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type ,o.inscriptions_processus, o.branche.id ,c.classe.libelle,o.nom_prenoms_pere ,o.nom_prenoms_mere,o.nom_prenoms_tuteur,o.commune ,o.handicap, o.moteur,o.visuel ," +
                            " o.auditif ,o.reaffecte ,o.regularisation ,o.reintegration ,o.prise_en_charge ,o.origine_prise_en_charge ,o.profession_pere,o.boite_postal_pere," +
                            "o.tel1_pere ,o.tel2_pere , o.profession_mere ,o.boite_postal_mere,o.tel1_mere,o.tel2_mere ,o.profession_tuteur,o.boite_postal_tuteur,o.tel1_tuteur," +
                            " o.tel2_tuteur ,o.profession_pers_en_charge ,o.boite_postale_pers_en_charge ,o.tel1_pers_en_charge , o.tel2_pers_en_charge ,o.autre_handicap ,o.nom_prenom_pers_en_charge ,o.classe_arabe , o.inscriptions_ecole_origine ,o.transfert ,o.num_decision_affecte ,o.inscriptions_langue_vivante ,o.inscriptions_redoublant , o.inscriptions_boursier ,o.internes ,o.demi_pension ,o.externes,o.ivoirien ,o.etranger_africain ,o.etranger_non_africain ,o.cheminphoto,q.eleve_nationalite ,q.elevelieu_naissance,c.classe.id,c.classe.libelle) from Inscriptions o" +
                            " , ecole e  ,eleve q ,Annee_Scolaire n ,ClasseEleve c" +
                            " where o.ecole.ecoleid = e.ecoleid and o.eleve.eleveid = q.eleveid and o.annee_scolaire.annee_scolaireid=n.annee_scolaireid and o.inscriptionsid=c.inscription.id and  " +
                            " o.ecole.ecoleid =:idecole and o.annee_scolaire.annee_scolaireid =:idAnnee ",ListeElevesClasse.class )
                    .setParameter("idecole", idEcole)
                    .setParameter("idAnnee",idAnnee)
                    .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }
    }


  @Transactional
    public List<InscriptionAvaliderDto> listInscription(Long idEcole , Long idAnnee, Inscriptions.status status, Inscriptions.typeOperation typeOperation){
        List <InscriptionAvaliderDto> minScription ;
        try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.InscriptionAvaliderDto(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type ,o.inscriptions_processus, o.branche.id ,o.branche.libelle ,o.nom_prenoms_pere ,o.nom_prenoms_mere,o.nom_prenoms_tuteur,o.commune ,o.handicap, o.moteur,o.visuel ,"+
                            "o.auditif ,o.reaffecte ,o.regularisation ,o.reintegration ,o.prise_en_charge ,o.origine_prise_en_charge ,o.profession_pere,o.boite_postal_pere," +
                            "o.tel1_pere ,o.tel2_pere , o.profession_mere ,o.boite_postal_mere,o.tel1_mere,o.tel2_mere ,o.profession_tuteur,o.boite_postal_tuteur,o.tel1_tuteur,"+
                            " o.tel2_tuteur ,o.profession_pers_en_charge ,o.boite_postale_pers_en_charge ,o.tel1_pers_en_charge , o.tel2_pers_en_charge ,o.autre_handicap ,o.nom_prenom_pers_en_charge ,o.classe_arabe , o.inscriptions_ecole_origine ,o.transfert ,o.num_decision_affecte,o.inscriptions_langue_vivante ,o.inscriptions_redoublant ,o.inscriptions_boursier ,o.internes ,o.demi_pension ,o.externes,o.ivoirien ,o.etranger_africain ,o.etranger_non_africain,o.cheminphoto,q.eleve_nationalite ,q.elevelieu_naissance) from Inscriptions o join  o.ecole e  join  o.eleve q join o.annee_scolaire n " +
                                        " where o.ecole.ecoleid =:idecole and o.annee_scolaire.annee_scolaireid =:idAnnee  and o.inscriptions_status =:status and o.inscriptions_type=: typeInscrip ",InscriptionAvaliderDto.class )
                                .setParameter("idecole", idEcole)
                          .setParameter("typeInscrip", typeOperation)
                             .setParameter("status",status)
                                .setParameter("idAnnee",idAnnee)
                                .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }
    }




   @Transactional
   public  String verifInscription(InscriptionDto inscriptionDto,  Long idEcole , String matricule, Long idAnnee,Long idBranche){
       eleve myeleve = new eleve() ;
       Inscriptions minScription = new Inscriptions() ;
       String messageRetour = null;
       minScription = checkInscrit(idEcole,matricule,idAnnee,idBranche) ;
       System.out.println("minScriptionAAA** "+ minScription);
       if(minScription==null){
           messageRetour =  createinscription(inscriptionDto);
       } else {
           messageRetour = "Insription entaméé ou déjà terminé. Veuille effectuer une recherche avec le matricule de cet elève!" ;
       }
       System.out.println("messageRetour** "+ messageRetour);
       return messageRetour ;
   }

    @Transactional
    public  String verifInscriptionImporter(InscriptionDto inscriptionDto,  Long idEcole , String matricule, Long idAnnee){
        eleve myeleve = new eleve() ;
        Inscriptions minScription = new Inscriptions() ;
        String messageRetour = null;
        minScription = checkInscrit(idEcole,matricule,idAnnee,inscriptionDto.getIdentifiantBranche()) ;
        System.out.println("minScriptionBB** "+ minScription);
        if(minScription==null){
            messageRetour =  createinscriptionImporter(inscriptionDto);
        } else {
            messageRetour = "Insription entaméé ou déjà terminé. Veuille effectuer une recherche avec le matricule de cet elève!" ;
        }
        System.out.println("messageRetour** "+ messageRetour);
        return messageRetour ;
    }

    @Transactional
    public  Inscriptions verifInscriptionVieEcole(InscriptionDto inscriptionDto,  Long idEcole , String matricule, Long idAnnee,Long idBranche){
        eleve myeleve = new eleve() ;
        Inscriptions minScription = new Inscriptions() ;
        Inscriptions minScriptionCree;
        minScription = checkInscrit(idEcole,matricule,idAnnee ,idBranche) ;
        System.out.println("minScriptionBB** "+ minScription);
        if(minScription==null){
       return      minScriptionCree =  createinscriptionVieEcole(inscriptionDto);
        } else {
           return null;
        }
    }



    @Transactional
    public  String verifmodifierInscription(InscriptionDto inscriptionDto,  Long idEcole , String matricule, Long idAnnee,Long idBranche){
        eleve myeleve = new eleve() ;
        Inscriptions minScription = new Inscriptions() ;
        String messageRetour = null;
        minScription = checkInscrit(idEcole,matricule,idAnnee,idBranche) ;

        Long idInscrip= minScription.getInscriptionsid();

        if(minScription==null){
            messageRetour = "Elève pas encore inscrit!" ;

        } else {
            messageRetour =  updateinscriptionImporter(idInscrip,inscriptionDto);
        }
        System.out.println("messageRetour** "+ messageRetour);
        return messageRetour ;
    }





    @Transactional
    public  void updateEleveInscrit( InscriptionAvaliderDto inscr){
        Inscriptions myInscr =new Inscriptions() ;
        Branche myBranche = new Branche() ;
        eleve myEleve = new eleve() ;
        myEleve = eleve.findById(inscr.getIdEleveInscrit()) ;
        myEleve.setElevenom(inscr.getNomEleve());
        myEleve.setEleveprenom(inscr.getPrenomEleve());
        myEleve.setElevecellulaire(inscr.getContactEleve());
        myEleve.setElevedate_naissance(inscr.getDate_naissanceEleve());
        myInscr = Inscriptions.findById(inscr.getInscriptionsidEleve()) ;
        myBranche= Branche.findById(inscr.getBrancheid());
        myInscr.setBranche(myBranche);

    }





    public  List<Inscriptions> findByStatusINSCRIPTION(String myStatus, Long idEcole, String mclasse){

        return    em.createQuery("select o from Inscriptions o join fetch  o.ecole e where o.inscriptions_status =: status" +
                        " and e.ecoleid=: idEcole and o.inscriptions_classe_actuelle =: classe ")
                .setParameter("status", myStatus)
                .setParameter("classe",mclasse)
                .setParameter("idEcole",idEcole)
                .getResultList();
    }

    public  List<Inscriptions> getListInscriptionByIdEcoleAndIdEleve( Long idEcole, Long idEleve){

        return    em.createQuery("select o from Inscriptions o  join  o.ecole e  where o.ecole.ecoleid=:idEcole and o.eleve.eleveid=: idEleve" )
                   .setParameter("idEcole", idEcole)
                   .setParameter("idEleve", idEleve)
                   .getResultList();

    }
public  void updatelibelleHandicap_inscrip (Long InscriptionId , Long oldHandicap , Long newHandicap) {
    libelleHandicap_has_inscriptions ins= em.createQuery("select o from libelleHandicap_has_inscriptions  o where  o.inscriptions_inscriptionsid=: idCription and o.libelleHandicap_libelleHandicapid=: idlibell " ,libelleHandicap_has_inscriptions.class)
                .setParameter("idCription",InscriptionId)
                .setParameter("idlibell",oldHandicap)
                   .getSingleResult();
        System.out.println("LibelleAndicap "+ins);
        if (ins == null )
            throw new RuntimeException(" Aucun enregistrement trouvé");
        em.createQuery(" update libelleHandicap_has_inscriptions e set  e.libelleHandicap_libelleHandicapid=: idLibel where e.inscriptions_inscriptionsid=: idCription and e.libelleHandicap_libelleHandicapid=: idoldLibelle "
                )
                .setParameter("idLibel",newHandicap)
                .setParameter("idCription",InscriptionId)
                .setParameter("idoldLibelle",oldHandicap)
                .executeUpdate();

    }

    public void validerInscription(Long inscriptionID){
     Inscriptions myInsription= new Inscriptions() ;
        myInsription= (Inscriptions) em.createQuery(" select e from Inscriptions e   where e.inscriptionsid=:inscriptionId ",Inscriptions.class )
                .setParameter("inscriptionId",inscriptionID)
                .getSingleResult();
        myInsription.setInscriptions_delete(false);
       // myInsription.setInscriptions_processus(Inscriptions.processus.EN_COURS);
        myInsription.setInscriptions_status(Inscriptions.status.VALIDEE);
    }


    public void deletePhoto(Long inscriptionID){
        Inscriptions myInsription= new Inscriptions() ;
        myInsription= (Inscriptions) em.createQuery(" select e from Inscriptions e   where e.inscriptionsid=:inscriptionId ",Inscriptions.class )
                .setParameter("inscriptionId",inscriptionID)
                .getSingleResult();
        myInsription.setCheminphoto("");
        System.out.println("myInsription photo "+myInsription);
        eleve elev = new eleve() ;
        elev = eleve.findById(myInsription.getEleve().getEleveid()) ;
        elev.setCheminphoto("");

    }


    public void chargerPhoto(byte[] bytes,Long inscriptionID){


        Inscriptions myInsription= new Inscriptions() ;
        myInsription= (Inscriptions) em.createQuery(" select e from Inscriptions e   where e.inscriptionsid=:inscriptionId ",Inscriptions.class )
                .setParameter("inscriptionId",inscriptionID)
                .getSingleResult();
        myInsription.setPhoto_eleve(bytes);

    }

    public  void updateInfosComplementaire( InscriptionDto inscriptionDto){
        Inscriptions myIns= new Inscriptions() ;
        Branche myB = new Branche() ;
        myB = Branche.findById(inscriptionDto.getIdentifiantBranche()) ;
        myIns =Inscriptions.findById(inscriptionDto.getInscriptionsid()) ;

        eleve elev = new eleve() ;
        System.out.println("Identifiant elève "+inscriptionDto.getIdentifiantEleve());
        elev = eleve.findById(inscriptionDto.getIdEleveInscrit());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Date--Naissance "+inscriptionDto.getDate_naissanceEleve());
        LocalDate localDateExtre = LocalDate.parse(inscriptionDto.getDate_naissanceEleve(), formatter);

        LocalDate localDateNaiss = localDateExtre;
        elev.setElevedate_naissance(localDateNaiss);

        elev.setEleve_sexe(inscriptionDto.getSexeEleve());
        elev.setEleve_matricule(inscriptionDto.getMatriculeEleve());
        elev.setElevenom(inscriptionDto.getNomEleve());
        System.out.println("Nom  "+inscriptionDto.getNomEleve());
        System.out.println("PréNom  "+inscriptionDto.getPrenomEleve());
        elev.setEleveprenom(inscriptionDto.getPrenomEleve());
        elev.setEleve_nationalite(inscriptionDto.getNationalite());
        elev.setElevelieu_naissance(inscriptionDto.getLieu_naissance ());
        myIns.setBranche(myB);
        myIns.setInscriptions_classe_actuelle(myB.getLibelle());
        myIns.setInscriptions_ecole_origine(inscriptionDto.getInscriptions_ecole_origine());
        myIns.setInscriptions_boursier(inscriptionDto.getInscriptions_boursier());
        myIns.setInscriptions_redoublant(inscriptionDto.getInscriptions_redoublant());
        myIns.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
        System.out.println("inscriptionDto.getInscriptions_langue_vivante() "+inscriptionDto.getInscriptions_langue_vivante());
        myIns.setInscriptions_langue_vivante(inscriptionDto.getInscriptions_langue_vivante());
       // myIns.setInscriptions_classe_actuelle(inscriptionDto.getInscriptions_classe_actuelle());
        myIns.setInscriptions_classe_precedente(inscriptionDto.getInscriptions_classe_precedente());
        myIns.setInscriptions_derniereclasse_religieuse(inscriptionDto.getInscriptions_derniereclasse_religieuse());
        System.out.println ("origine Prise en charge>> "+inscriptionDto.getOrigine_prise_en_charge());
        myIns.setOrigine_prise_en_charge(inscriptionDto.getOrigine_prise_en_charge());
        myIns.setInscriptionsdate_modification(LocalDate.now());
        myIns.setTransfert(inscriptionDto.getTransfert());
        myIns.setNum_decision_affecte(inscriptionDto.getNum_decision_affecte());
        myIns.setNom_prenoms_pere(inscriptionDto.getNom_prenoms_pere());
        myIns.setNom_prenoms_mere(inscriptionDto.getNom_prenoms_mere());
        myIns.setNom_prenoms_tuteur(inscriptionDto.getNom_prenoms_tuteur());
        myIns.setCommune(inscriptionDto.getCommune());
        myIns.setHandicap(inscriptionDto.getHandicap());
        myIns.setMoteur(inscriptionDto.getMoteur());
        myIns.setVisuel(inscriptionDto.getVisuel());
        myIns.setAuditif(inscriptionDto.getAuditif());
        myIns.setReaffecte(inscriptionDto.getReaffecte());
        myIns.setRegularisation(inscriptionDto.getRegularisation());
        myIns.setReintegration(inscriptionDto.getReintegration());
        myIns.setPrise_en_charge(inscriptionDto.getPrise_en_charge());
        myIns.setProfession_pere(inscriptionDto.getProfession_pere());
        myIns.setBoite_postal_pere(inscriptionDto.getBoite_postal_pere());
        myIns.setTel1_pere(inscriptionDto.getTel1_pere());
        myIns.setTel2_pere(inscriptionDto.getTel2_pere());
        myIns.setProfession_mere(inscriptionDto.getProfession_mere());
        myIns.setBoite_postal_mere(inscriptionDto.getBoite_postal_mere());
        myIns.setTel1_mere(inscriptionDto.getTel1_mere());
        myIns.setTel2_mere(inscriptionDto.getTel2_mere());
        myIns.setProfession_tuteur(inscriptionDto.getProfession_tuteur());
        myIns.setBoite_postal_tuteur(inscriptionDto.getBoite_postal_tuteur());
        myIns.setTel1_tuteur(inscriptionDto.getTel1_tuteur());
        myIns.setTel2_tuteur(inscriptionDto.getTel2_tuteur());
        myIns.setProfession_pers_en_charge(inscriptionDto.getProfession_pers_en_charge());
        myIns.setBoite_postale_pers_en_charge(inscriptionDto.getBoite_postale_pers_en_charge());
        myIns.setTel1_pers_en_charge(inscriptionDto.getTel1_pers_en_charge());
        myIns.setTel2_pers_en_charge(inscriptionDto.getTel2_pers_en_charge());
        myIns.setAutre_handicap(inscriptionDto.getAutre_handicap());
        myIns.setNom_prenom_pers_en_charge(inscriptionDto.getNom_prenom_pers_en_charge());
        myIns.setClasse_arabe(inscriptionDto.getClasse_arabe());
        myIns.setExternes(inscriptionDto.getExternes());
        myIns.setInternes(inscriptionDto.getInternes());
        myIns.setDemi_pension(inscriptionDto.getDemi_pension());

        myIns.setIvoirien(inscriptionDto.getIvoirien());
        myIns.setEtranger_africain(inscriptionDto.getEtranger_africain());
        myIns.setEtranger_non_africain(inscriptionDto.getEtranger_non_africain());



    }

    public  String checkInfosAjour( Long idIns){
        String mess= null ;
        Inscriptions myIns= new Inscriptions() ;
        myIns =Inscriptions.findById(idIns) ;
        if((myIns.getInscriptions_boursier()==null )||( myIns.getInscriptions_boursier().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        } else if ((myIns.getInscriptions_redoublant()==null )||( myIns.getInscriptions_redoublant().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }
        else if ((myIns.getInscriptions_classe_actuelle()==null )||( myIns.getInscriptions_classe_actuelle().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }
        else if ((myIns.getInscriptions_classe_precedente()==null )||( myIns.getInscriptions_classe_precedente().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }
        else if ((myIns.getInscriptions_derniereclasse_religieuse()==null )||( myIns.getInscriptions_derniereclasse_religieuse().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }
        else if ((myIns.getTransfert()==null )||( myIns.getTransfert().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }
        else if ((myIns.getNum_decision_affecte()==null )||( myIns.getNum_decision_affecte().equals(""))) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }

        else if ((myIns.getPhoto_eleve()==null )) {
            mess= "INFORMATIONS INCOMPLETES" ;
        }
        else if ((myIns.getInscriptions_ecole_origine()==null || myIns.getInscriptions_ecole_origine().equals("") )) {
            mess= "INFORMATIONS INCOMPLETES" ;
        } else {
            mess= "VOS INFORMATIONS SONT A JOUR" ;
        }
       return mess ;

    }

   public  void updateIns(InscriptionDto inscriptionDto){

       if (inscriptionDto.getInscriptionsid() == null || inscriptionDto.getInscriptionsid()==0)
           throw new RuntimeException("L'identifiant  est requis");
              Inscriptions ins= em.createQuery("select o from Inscriptions  o where o.inscriptionsid=: inscr and o.eleve.eleveid=:idElev and o.ecole.ecoleid =: ecoleId" ,Inscriptions.class)
               .setParameter("idElev",inscriptionDto.getIdentifiantEleve())
               .setParameter("ecoleId",inscriptionDto.getIdentifiantEcole())
               .setParameter("inscr",inscriptionDto.getInscriptionsid())
               .getSingleResult();
   //System.out.println("Mon objet "+ins);
       if (ins == null )
           throw new RuntimeException(" Aucun enregistrement trouvé");
       System.out.println("Mon objet1 ");
       if (inscriptionDto.getIdentifiantEleve() == null || inscriptionDto.getIdentifiantEleve()==0)
       {
           throw new RuntimeException("L'identifiant de l'elève  est requis");
       }

       eleve eleve = com.vieecoles.entities.operations.eleve.findById(inscriptionDto.getIdentifiantEleve());
       if (eleve == null)
           throw new RuntimeException("Cet elève n'existe pas ");

       if (inscriptionDto.getIdentifiantEcole() == null || inscriptionDto.getIdentifiantEcole()==0)
           throw new RuntimeException("L'identifiant de l'ecole  est requis");
       ecole ecole = com.vieecoles.entities.operations.ecole.findById(inscriptionDto.getIdentifiantEcole());
       if (ecole == null)
           throw new RuntimeException("Cette ecole n'existe pas ");
       System.out.println("Mon objet3");
       ArrayList<Libellehandicap> libellehandicapList= new ArrayList<>() ;
       for(int i = 0 ; i < inscriptionDto.getLibellehandicap().size() ; i++)
       {

           Libellehandicap libellehandicap = new Libellehandicap() ;
           libellehandicap= Libellehandicap.findById(inscriptionDto.getLibellehandicap().get(i)) ;
           libellehandicapList.add(libellehandicap) ;
       }
       System.out.println("Mon objet4");
           eleve  eleve1= com.vieecoles.entities.operations.eleve.findById(inscriptionDto.getIdentifiantEleve());
           ecole ecole1= ecole.findById(inscriptionDto.getIdentifiantEcole()) ;
       Annee_Scolaire annee_scolaire= Annee_Scolaire.findById(inscriptionDto.getIdentifiantAnnee_scolaire()) ;
       System.out.println("Mon objet5");


       em.createQuery(" update Inscriptions e set e.ecole=:ecole1 , e.eleve=: eleve1 , e.inscriptions_classe_actuelle=: classeActuelle ," +
                               " e.inscriptionsdate_modification=: dateModif , " +
                               "e.inscriptions_ecole_origine=: ecoleOrigine, e.annee_scolaire=: anneeScolaire   " +
                               " ,e.inscriptions_derniereclasse_religieuse=: classeReligieuse , e.inscriptions_classe_precedente=: classePrecedente," +
                               "e.inscriptions_status=: inscrStatus" +
                               ",e.inscriptions_redoublant=: redoublant , e.inscriptions_type=:typeInscr" +
                               " ,e.inscriptions_boursier=:boursier , e.inscriptions_statut_eleve=:statutElev  where e.inscriptionsid=: inscr " +
                               "and e.eleve.eleveid=:idElev and e.ecole.ecoleid =: ecoleId "
                       )
               .setParameter("ecole1",ecole1)
               .setParameter("eleve1",eleve1)
                .setParameter("anneeScolaire",annee_scolaire)
               .setParameter("inscr",inscriptionDto.getInscriptionsid())
               .setParameter("idElev",inscriptionDto.getIdentifiantEleve())
               .setParameter("ecoleId",inscriptionDto.getIdentifiantEcole())
               .setParameter("classeActuelle",inscriptionDto.getInscriptions_classe_actuelle())
               .setParameter("statutElev",inscriptionDto.getInscriptions_statut_eleve())
               .setParameter("redoublant",inscriptionDto.getInscriptions_redoublant())
               .setParameter("typeInscr",inscriptionDto.getInscriptions_type())
               .setParameter("boursier",inscriptionDto.getInscriptions_classe_actuelle())


               .setParameter("dateModif",inscriptionDto.getInscriptionsdate_modification())

               .setParameter("ecoleOrigine",inscriptionDto.getInscriptions_ecole_origine())
               .setParameter("classeReligieuse",inscriptionDto.getInscriptions_derniereclasse_religieuse())
               .setParameter("classePrecedente",inscriptionDto.getInscriptions_classe_precedente())
               .setParameter("inscrStatus",inscriptionDto.getInscriptions_status())
               .setParameter("ecoleOrigine",inscriptionDto.getInscriptions_ecole_origine())
               .executeUpdate();
       System.out.println("Mon objet6");
     //  return  q;

       System.out.println("Modification "+ins);
   }




    public void   deleteInscriptions( InscriptionDto inscriptionDto  ){
        if (inscriptionDto.getInscriptionsid() == null || inscriptionDto.getInscriptionsid()==0)
            throw new RuntimeException("L'identifiant  est requis");
        Inscriptions ins= em.createQuery("select o from Inscriptions  o where o.inscriptionsid=: inscr and o.eleve.eleveid=:idElev and o.ecole.ecoleid =: ecoleId" ,Inscriptions.class)
                .setParameter("idElev",inscriptionDto.getIdentifiantEleve())
                .setParameter("ecoleId",inscriptionDto.getIdentifiantEcole())
                .setParameter("inscr",inscriptionDto.getInscriptionsid())
                .getSingleResult();
        System.out.println("Mon objet "+ins);
        if (ins == null )
            throw new RuntimeException(" Aucun enregistrement trouvé");

        if (inscriptionDto.getIdentifiantEleve() == null || inscriptionDto.getIdentifiantEleve()==0)
            throw new RuntimeException("L'identifiant de l'elève  est requis");
        eleve eleve = com.vieecoles.entities.operations.eleve.findById(inscriptionDto.getIdentifiantEleve());
        if (eleve == null)
            throw new RuntimeException("Cet elève n'existe pas ");

        if (inscriptionDto.getIdentifiantEcole() == null || inscriptionDto.getIdentifiantEcole()==0)
            throw new RuntimeException("L'identifiant de l'ecole  est requis");
        ecole ecole = com.vieecoles.entities.operations.ecole.findById(inscriptionDto.getIdentifiantEcole());
        if (ecole == null)
            throw new RuntimeException("Cette ecole n'existe pas ");

        ins.setInscriptionsdate_modification(LocalDate.now());
         ins.setInscriptions_delete(true);

    }

   public  List<Inscriptions> search(String Libelle){
       return   em.createQuery("select o from Inscriptions o where  o.inscriptionscode like CONCAT('%',:Libelle ,'%') ")
               .setParameter("Libelle",Libelle).getResultList();
   }

    public  long count(){
        return  Inscriptions.count();
    }

    public  eleve getElevByMatricule(String matricule){
        eleve elev = new eleve() ;
        elev= (eleve) em.createQuery(" select e from eleve e   where e.eleve_matricule=:matricule ",eleve.class )
                .setParameter("matricule",matricule)
                .getSingleResult();

        return  elev ;
    }


}
