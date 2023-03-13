package com.vieecoles.services.eleves;

import com.vieecoles.dto.InscriptionAvaliderDto;
import com.vieecoles.dto.InscriptionDto;
import com.vieecoles.entities.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
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
      inscr.setInscriptions_delete(0);
       System.out.println("Mon objet9"+annee_scolaire);
       inscr.setInscriptionsdate_creation(LocalDate.now());
       System.out.println("Mon objet19"+annee_scolaire);
      if (inscriptionDto.getInscriptions_type() == Inscriptions.typeOperation.INSCRIPTION) {
            inscr.setEcole(ecole1);
          inscr.setEleve(eleve1);

          inscr.setAnnee_scolaire(annee_scolaire);
          inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
          inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
           inscr.setLibellehandicap(libellehandicapList);
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
          inscr.setLibellehandicap(libellehandicapList);
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
        inscr.setInscriptions_delete(0);
        System.out.println("Mon objet9"+annee_scolaire);
        inscr.setInscriptionsdate_creation(LocalDate.now());
        System.out.println("Mon objet19"+annee_scolaire);
        if (inscriptionDto.getInscriptions_type() == Inscriptions.typeOperation.INSCRIPTION) {
            inscr.setEcole(ecole1);
            inscr.setEleve(eleve1);

            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
            inscr.setLibellehandicap(libellehandicapList);
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
            inscr.setBranche(myBranche);
            inscr.persist();

        } else {
            inscr.setEcole(ecole1);
            inscr.setEleve(eleve1);
            inscr.setInscriptions_type(inscriptionDto.getInscriptions_type());
            inscr.setInscriptions_statut_eleve(inscriptionDto.getInscriptions_statut_eleve());
            inscr.setLibellehandicap(libellehandicapList);
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
            inscr.setAnnee_scolaire(annee_scolaire);
            inscr.setBranche(myBranche);
            inscr.persist();
        }

        return "DEMANDE D'INSCRIPTION EFFECTUEE AVEC SUCCES!" ;

    }




                    public Inscriptions checkInscrit(Long idEcole , String matricule, Long idAnnee){
                                   Inscriptions minScription = new Inscriptions() ;
                               try {
                                return    minScription = (Inscriptions) em.createQuery("select o from Inscriptions o join  o.ecole e  join  o.eleve l join o.annee_scolaire " +
                                                " where o.ecole.ecoleid =:idecole and o.eleve.eleve_matricule =: matricule and o.annee_scolaire.annee_scolaireid =:idAnnee " )
                                        .setParameter("idecole", idEcole)
                                        .setParameter("matricule",matricule)
                                        .setParameter("idAnnee",idAnnee)
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
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.InscriptionAvaliderDto(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type ,o.inscriptions_processus, o.branche.id ,o.branche.libelle) from Inscriptions o join  o.ecole e  join  o.eleve q join o.annee_scolaire n " +
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
    public List<InscriptionAvaliderDto> listInscription(Long idEcole , Long idAnnee, Inscriptions.status status, Inscriptions.typeOperation typeOperation){
        List <InscriptionAvaliderDto> minScription ;
        try {
            System.out.println("entree1");
            return  minScription = em.createQuery("SELECT new com.vieecoles.dto.InscriptionAvaliderDto(q.eleveid,o.inscriptionsid,q.elevenom,q.eleveprenom,q.eleve_matricule,q.elevedate_naissance,q.eleve_sexe,q.elevecellulaire,o.inscriptions_statut_eleve,o.inscriptions_status,o.inscriptions_type ,o.inscriptions_processus, o.branche.id ,o.branche.libelle) from Inscriptions o join  o.ecole e  join  o.eleve q join o.annee_scolaire n " +
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
   public  String verifInscription(InscriptionDto inscriptionDto,  Long idEcole , String matricule, Long idAnnee){
       eleve myeleve = new eleve() ;
       Inscriptions minScription = new Inscriptions() ;
       String messageRetour = null;
       minScription = checkInscrit(idEcole,matricule,idAnnee) ;
       System.out.println("minScription** "+ minScription);
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
        minScription = checkInscrit(idEcole,matricule,idAnnee) ;
        System.out.println("minScription** "+ minScription);
        if(minScription==null){
            messageRetour =  createinscriptionImporter(inscriptionDto);
        } else {
            messageRetour = "Insription entaméé ou déjà terminé. Veuille effectuer une recherche avec le matricule de cet elève!" ;
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
        myInsription.setInscriptions_delete(0);
       // myInsription.setInscriptions_processus(Inscriptions.processus.EN_COURS);
        myInsription.setInscriptions_status(Inscriptions.status.VALIDEE);
    }

    public void chargerPhoto(byte[] bytes,Long inscriptionID){


        Inscriptions myInsription= new Inscriptions() ;
        myInsription= (Inscriptions) em.createQuery(" select e from Inscriptions e   where e.inscriptionsid=:inscriptionId ",Inscriptions.class )
                .setParameter("inscriptionId",inscriptionID)
                .getSingleResult();
        myInsription.setPhoto_eleve(bytes);

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
         ins.setInscriptions_delete(1);

    }

   public  List<Inscriptions> search(String Libelle){
       return   em.createQuery("select o from Inscriptions o where  o.inscriptionscode like CONCAT('%',:Libelle ,'%') ")
               .setParameter("Libelle",Libelle).getResultList();
   }

    public  long count(){
        return  Inscriptions.count();
    }


}
