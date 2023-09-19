package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RecapitulatifStatistiqueServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<PyramideEffectDto> getPyramide(Long idEcole ,Long anneeId ,Integer lim){

        List<NiveauClasseDto> niveauDtoList = new ArrayList<>() ;

         if(lim ==4) {
             niveauDtoList= getListClasse(idEcole,anneeId,lim);
         } else {
             niveauDtoList= getListClasseSecon(idEcole,anneeId,lim);
         }


       int sizeClass ;
        sizeClass = niveauDtoList.size() ;
        //System.out.println("niveauDtoList "+niveauDtoList.toString());
       String niveau ;
         Long nombreClasse;

      List<PyramideEffectDto> resultatsListElevesDto = new ArrayList<>() ;

        List<effectifClasseDto> effectifClasseDtosList  = new ArrayList<>() ;
        List<RedoublantAffClasseDto> redoublantAffClasseDtosList = new ArrayList<>();
        List<AffecteClasseDto> affecteClasseDtosLis  = new ArrayList<>();

         Long naffnbreG , naffnbreF , nbrenAffTotal ,boursiernbreG ;
         Long boursiernbreF , boursierffTotal , nonboursiernbreG,nonboursiernbreF ;
         Long nonboursierffTotal , demiboursiernbreG , demiboursiernbreF , demiboursierffTotal ;
          Long reaffenbreG  , reaffenbreF , reaffeTotal ,tranfertnbreG ,tranfertnbreF , tranfertTotal ;
          Long affectReaffectnbreG , affectReaffectnbreF , affectReaffectTotal , internenbreG , internenbreF ;
           Long interneTotal , demiPensionbreG , demiPensionnbreF , demiPensionTotal ,  externesnbreG ;
           Long externesnbreF , externesTotal , ivoiriensnbreG ;
           Long ivoiriensnbreF , ivoiriensTotal , etrangerAfricnbreG , etrangerAfricnbreF , etrangerAfricTotal ;
          Long etrangerNonAfricnbreG , etrangerNonAfricnbreF , etrangerNonAfricTotal ;

        for (int i=0; i< sizeClass;i++) {
            PyramideEffectDto m = new PyramideEffectDto() ;


                effectifClasseDto effectifClasseDtos  = new effectifClasseDto();
            RedoublantAffClasseDto redoublantAffClasseDtos = new RedoublantAffClasseDto();
            AffecteClasseDto affecteClasseDtos  = new AffecteClasseDto();

            m.setNiveau(niveauDtoList.get(i).getNiveau());
            System.out.println("mm "+m.getNiveau());
             boursiernbreG = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","B") ;
            boursiernbreF = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","B") ;
            boursierffTotal = boursiernbreG+boursiernbreF ;

            m.setBoursiernbreF(boursiernbreF);
            m.setBoursiernbreG(boursiernbreG);
            m.setBoursierffTotal(boursierffTotal);


            demiboursiernbreG = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","1/2B") ;
            demiboursiernbreF = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","1/2B") ;
            demiboursierffTotal = demiboursiernbreG+demiboursiernbreF ;

            m.setDemiboursiernbreF(demiboursiernbreF);
            m.setDemiboursiernbreG(demiboursiernbreG);
            m.setDemiboursierffTotal(demiboursierffTotal);


            nonboursiernbreG = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","") ;
            nonboursiernbreF = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","") ;
            nonboursierffTotal = nonboursiernbreG+nonboursiernbreF ;

            m.setNonboursiernbreF(nonboursiernbreF);
            m.setNonboursiernbreG(nonboursiernbreG);
            m.setNonboursierffTotal(nonboursierffTotal);

            naffnbreF = getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON_AFFECTE") ;
            naffnbreG = getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","NON_AFFECTE") ;
            nbrenAffTotal = naffnbreF+ naffnbreG ;

            m.setNaffnbreF(naffnbreF);
            m.setNaffnbreG(naffnbreG);
            m.setNbrenAffTotal(nbrenAffTotal);

            reaffenbreG  = getEffReacffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
            reaffenbreF = getEffReacffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
            reaffeTotal = reaffenbreG+reaffenbreF ;


            m.setReaffenbreF(reaffenbreF);
            m.setReaffenbreG(reaffenbreG);
            m.setReaffeTotal(reaffeTotal);

            internenbreG = getEffInterneClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
            internenbreF = getEffInterneClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
            interneTotal = internenbreF +internenbreG ;
            m.setInternenbreG(internenbreG);
            m.setInternenbreF(internenbreF);
            m.setInterneTotal(interneTotal);

            demiPensionbreG = getEffdemiPensionClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
            demiPensionnbreF= getEffdemiPensionClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
             demiPensionTotal= demiPensionbreG+demiPensionnbreF ;
             m.setDemiPensionbreG(demiPensionbreG);
             m.setDemiPensionnbreF(demiPensionnbreF);
             m.setDemiPensionTotal(demiPensionTotal);


            externesnbreG = getEffExternesClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
            externesnbreF= getEffExternesClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
            externesTotal= externesnbreG+externesnbreF ;
            m.setExternesnbreG(externesnbreG);
            m.setExternesnbreF(externesnbreF);
            m.setExternesTotal(externesTotal);




            ivoiriensnbreG =getEffIvoiriensClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
          ivoiriensnbreF = getEffIvoiriensClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
            ivoiriensTotal =  ivoiriensnbreG+ ivoiriensnbreF ;

            m.setIvoiriensnbreG(ivoiriensnbreG);
            m.setIvoiriensnbreF(ivoiriensnbreF);
            m.setIvoiriensTotal(ivoiriensTotal);


            etrangerAfricnbreG =getEffAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
            etrangerAfricnbreF = getEffAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
            etrangerAfricTotal =  etrangerAfricnbreG+etrangerAfricnbreF ;

            m.setEtrangerAfricnbreG(etrangerAfricnbreG);
            m.setEtrangerAfricnbreF(etrangerAfricnbreF);
            m.setEtrangerAfricTotal(etrangerAfricTotal);

            etrangerNonAfricnbreG =getEffNonAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true) ;
            etrangerNonAfricnbreF = getEffNonAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true) ;
            etrangerNonAfricTotal =  etrangerNonAfricnbreG+etrangerNonAfricnbreF ;

            m.setEtrangerNonAfricnbreG(etrangerNonAfricnbreG);
            m.setEtrangerNonAfricnbreF(etrangerNonAfricnbreF);
            m.setEtrangerNonAfricTotal(etrangerNonAfricTotal);



            tranfertnbreG = getEffTransfertClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","1") ;
             tranfertnbreF =getEffTransfertClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","1") ;
             tranfertTotal =  tranfertnbreG+ tranfertnbreF ;

             m.setTranfertnbreG(tranfertnbreG);
            m.setTranfertnbreF(tranfertnbreF);
            m.setTranfertTotal(tranfertTotal);

            nombreClasse = findNombreClasseBranche(niveauDtoList.get(i).getId(),idEcole) ;
            //System.out.println("nombreClasse "+nombreClasse);
            effectifClasseDtos.setClasse(niveauDtoList.get(i).getNiveau());
            effectifClasseDtos.setNbreG(getEffectParGenreClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN"));
            effectifClasseDtos.setNbreF(getEffectParGenreClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ"));
            effectifClasseDtos.setNbreTotal(effectifClasseDtos.getNbreF()+effectifClasseDtos.getNbreG());

            redoublantAffClasseDtos.setClasse(niveauDtoList.get(i).getNiveau());
            redoublantAffClasseDtos.setRednbreF(getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ" ,"OUI","AFFECTE"));
            redoublantAffClasseDtos.setRednbreG(getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN" ,"OUI","AFFECTE"));
            redoublantAffClasseDtos.setNbreTotal(redoublantAffClasseDtos.getRednbreF()+redoublantAffClasseDtos.getRednbreG());

            affecteClasseDtos.setClasse(niveauDtoList.get(i).getNiveau());
            affecteClasseDtos.setAffnbreF(getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","AFFECTE"));
            affecteClasseDtos.setAffnbreG(getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","AFFECTE"));
            affecteClasseDtos.setNbreTotal(affecteClasseDtos.getAffnbreF()+affecteClasseDtos.getAffnbreG());

             effectifClasseDtosList.add(effectifClasseDtos) ;
             affecteClasseDtosLis.add(affecteClasseDtos) ;
             redoublantAffClasseDtosList.add(redoublantAffClasseDtos) ;

            affectReaffectnbreG = affecteClasseDtos.getAffnbreG()+reaffenbreG ;
            affectReaffectnbreF = affecteClasseDtos.getAffnbreF()+reaffenbreF ;
            affectReaffectTotal = affectReaffectnbreG + affectReaffectnbreF ;
            m.setAffectReaffectnbreG(affectReaffectnbreG);
            m.setAffectReaffectnbreF(affectReaffectnbreF);
            m.setAffectReaffectTotal(affectReaffectTotal);



            m.setAffnbreF(affecteClasseDtos.getAffnbreF());
            m.setAffnbreG(affecteClasseDtos.getAffnbreG());
            m.setNbreAffTotal(affecteClasseDtos.getAffnbreF()+affecteClasseDtos.getAffnbreG());
            m.setNbreG(effectifClasseDtos.getNbreG());
            m.setNbreF(effectifClasseDtos.getNbreF());
            m.setNbreeffTotal(effectifClasseDtos.getNbreG()+effectifClasseDtos.getNbreF());
            m.setRednbreF(redoublantAffClasseDtos.getRednbreF());
            m.setRednbreG(redoublantAffClasseDtos.getRednbreG());
            m.setNbreRedTotal(redoublantAffClasseDtos.getRednbreF()+redoublantAffClasseDtos.getRednbreG());
            m.setNombreClasse(nombreClasse);

            resultatsListElevesDto.add(m) ;

        }


        return  resultatsListElevesDto ;
    }



       Long getCountNomBreClasse(Long idEcole , Long idAnneId ,Long classeId) {
           try {
               TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from Classe o   where o.ecole.id =:idEcole  and o.annee.id =:idAnneId and o.id");
               Long size = q.setParameter("idEcole" ,idEcole).
                                 setParameter("idAnneId" ,idAnneId).
                              getSingleResult() ;

               return size;
           } catch (NoResultException e) {
               return 0L ;
           }
       }

    List<NiveauClasseDto> getListClasse(Long idEcole , Long idAnneId, Integer lim) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole  and o.annee.id =:idAnneId and o.branche.niveau.id <=:lim  order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                                           .setParameter("lim" ,lim)
                .setParameter("idAnneId" ,idAnneId).getResultList() ;

    }

    List<NiveauClasseDto> getListClasseSecon(Long idEcole , Long idAnneId, Integer lim) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole  and o.annee.id =:idAnneId and o.branche.niveau.id >=:lim  order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                .setParameter("lim" ,lim)
                .setParameter("idAnneId" ,idAnneId).getResultList() ;

    }

    Long getEffectParGenreClasse(Long idAnneId ,Long idBranche ,String sexe ) {
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn ");
            Long size = q.setParameter("idBranche" ,idBranche).
                           setParameter("idAnn" ,idAnneId).
                          setParameter("sexe" ,sexe).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }


    Long getEffectRedoubAfflanClasse(Long idAnneId ,Long idBranche ,String sexe ,String redoubl ,String aff1 ) {
      //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.redoublant =: redoubl and o.inscription.afecte =: aff ");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("redoubl" ,redoubl).
                    setParameter("aff" ,aff1).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffReacffecteClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean reaffecte ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.reaffecte =: reaffecte");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("reaffecte" ,reaffecte).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffInterneClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean interne ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.internes =: interne");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("interne" ,interne).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffdemiPensionClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean demiPension ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.demi_pension =: demiPension");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("demiPension" ,demiPension).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffExternesClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean externe ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.externes =: externe");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("externe" ,externe).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffIvoiriensClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean ivoirien ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.ivoirien =: ivoirien");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("ivoirien" ,ivoirien).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffAfricainClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean africain ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.etranger_africain =: africain");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("africain" ,africain).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffNonAfricainClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean nonafricain ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.etranger_non_africain =: nonafricain");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("nonafricain" ,nonafricain).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }
    Long getEffTransfertClasse(Long idAnneId ,Long idBranche ,String sexe ,String transfert ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.transfert =: transfert");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("transfert" ,transfert).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }


    Long getEffectEtatBoursierClasse(Long idAnneId ,Long idBranche ,String sexe ,String boursier  ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    " and o.inscription.boursier =: boursier ");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("boursier" ,boursier).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getAffecteClasse(Long idAnneId ,Long idBranche ,String sexe ,String aff1 ) {
        //Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche and o.inscription.eleve.sexe =:sexe and o.inscription.annee.id=:idAnn " +
                    "  and o.inscription.afecte =: aff ");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("aff" ,aff1).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }


    public  List<Branche> findByNiveauEnseignementViaEcole(Long id){
        Ecole ecole = Ecole.findById(id);
        System.out.println(ecole);
        return Branche.find("niveauEnseignement.id =?1 order by libelle desc", ecole.getNiveauEnseignement().getId()).list();
    }
    public Long findNombreClasseBranche(long id, Long ecoleId) {
        //logger.info(String.format("find by Branche id :: %s", id));
        return Classe.find("branche.id = ?1 and ecole.id=?2",id, ecoleId).count() ;
    }
}
