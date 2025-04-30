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
public class RecapitulatifStatistiqueBilan2Services {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<PyramideEffectDto> getPyramide(Long idEcole ,Long anneeId ){

        List<NiveauClasseDto> niveauDtoList = new ArrayList<>() ;

        niveauDtoList= getListClasse(idEcole,anneeId);


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
             boursiernbreG = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","B",idEcole) ;
            boursiernbreF = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","B",idEcole) ;
            boursierffTotal = boursiernbreG+boursiernbreF ;

            m.setBoursiernbreF(boursiernbreF);
            m.setBoursiernbreG(boursiernbreG);
            m.setBoursierffTotal(boursierffTotal);


            demiboursiernbreG = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","1/2B",idEcole) ;
            demiboursiernbreF = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","1/2B",idEcole) ;
            demiboursierffTotal = demiboursiernbreG+demiboursiernbreF ;

            m.setDemiboursiernbreF(demiboursiernbreF);
            m.setDemiboursiernbreG(demiboursiernbreG);
            m.setDemiboursierffTotal(demiboursierffTotal);


            nonboursiernbreG = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","",idEcole) ;
            nonboursiernbreF = getEffectEtatBoursierClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","",idEcole) ;
            nonboursierffTotal = nonboursiernbreG+nonboursiernbreF ;

            m.setNonboursiernbreF(nonboursiernbreF);
            m.setNonboursiernbreG(nonboursiernbreG);
            m.setNonboursierffTotal(nonboursierffTotal);

            naffnbreF = getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON_AFFECTE",idEcole) ;
            naffnbreG = getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","NON_AFFECTE",idEcole) ;
            nbrenAffTotal = naffnbreF+ naffnbreG ;

            m.setNaffnbreF(naffnbreF);
            m.setNaffnbreG(naffnbreG);
            m.setNbrenAffTotal(nbrenAffTotal);

            reaffenbreG  = getEffReacffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
            reaffenbreF = getEffReacffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
            reaffeTotal = reaffenbreG+reaffenbreF ;


            m.setReaffenbreF(reaffenbreF);
            m.setReaffenbreG(reaffenbreG);
            m.setReaffeTotal(reaffeTotal);

            internenbreG = getEffInterneClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
            internenbreF = getEffInterneClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
            interneTotal = internenbreF +internenbreG ;
            m.setInternenbreG(internenbreG);
            m.setInternenbreF(internenbreF);
            m.setInterneTotal(interneTotal);

            demiPensionbreG = getEffdemiPensionClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
            demiPensionnbreF= getEffdemiPensionClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
             demiPensionTotal= demiPensionbreG+demiPensionnbreF ;
             m.setDemiPensionbreG(demiPensionbreG);
             m.setDemiPensionnbreF(demiPensionnbreF);
             m.setDemiPensionTotal(demiPensionTotal);


            externesnbreG = getEffExternesClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
            externesnbreF= getEffExternesClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
            externesTotal= externesnbreG+externesnbreF ;
            m.setExternesnbreG(externesnbreG);
            m.setExternesnbreF(externesnbreF);
            m.setExternesTotal(externesTotal);




            ivoiriensnbreG =getEffIvoiriensClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
          ivoiriensnbreF = getEffIvoiriensClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
            ivoiriensTotal =  ivoiriensnbreG+ ivoiriensnbreF ;

            m.setIvoiriensnbreG(ivoiriensnbreG);
            m.setIvoiriensnbreF(ivoiriensnbreF);
            m.setIvoiriensTotal(ivoiriensTotal);


            etrangerAfricnbreG =getEffAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
            etrangerAfricnbreF = getEffAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
            etrangerAfricTotal =  etrangerAfricnbreG+etrangerAfricnbreF ;

            m.setEtrangerAfricnbreG(etrangerAfricnbreG);
            m.setEtrangerAfricnbreF(etrangerAfricnbreF);
            m.setEtrangerAfricTotal(etrangerAfricTotal);

            etrangerNonAfricnbreG =getEffNonAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
            etrangerNonAfricnbreF = getEffNonAfricainClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
            etrangerNonAfricTotal =  etrangerNonAfricnbreG+etrangerNonAfricnbreF ;

            m.setEtrangerNonAfricnbreG(etrangerNonAfricnbreG);
            m.setEtrangerNonAfricnbreF(etrangerNonAfricnbreF);
            m.setEtrangerNonAfricTotal(etrangerNonAfricTotal);



            tranfertnbreG = getEffTransfertClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",true,idEcole) ;
             tranfertnbreF =getEffTransfertClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",true,idEcole) ;
             tranfertTotal =  tranfertnbreG+ tranfertnbreF ;

             m.setTranfertnbreG(tranfertnbreG);
            m.setTranfertnbreF(tranfertnbreF);
            m.setTranfertTotal(tranfertTotal);

            nombreClasse = findNombreClasseBranche(niveauDtoList.get(i).getId(),idEcole) ;
            //System.out.println("nombreClasse "+nombreClasse);
            effectifClasseDtos.setClasse(niveauDtoList.get(i).getNiveau());
            effectifClasseDtos.setNbreG(getEffectParGenreClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN",idEcole));
            effectifClasseDtos.setNbreF(getEffectParGenreClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ",idEcole));
            effectifClasseDtos.setNbreTotal(effectifClasseDtos.getNbreF()+effectifClasseDtos.getNbreG());

            redoublantAffClasseDtos.setClasse(niveauDtoList.get(i).getNiveau());
            redoublantAffClasseDtos.setRednbreF(getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ" ,"OUI","AFFECTE",idEcole));
            redoublantAffClasseDtos.setRednbreG(getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN" ,"OUI","AFFECTE",idEcole));
            redoublantAffClasseDtos.setNbreTotal(redoublantAffClasseDtos.getRednbreF()+redoublantAffClasseDtos.getRednbreG());

            affecteClasseDtos.setClasse(niveauDtoList.get(i).getNiveau());
            affecteClasseDtos.setAffnbreF(getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","AFFECTE",idEcole));
            affecteClasseDtos.setAffnbreG(getAffecteClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","AFFECTE",idEcole));
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





    List<NiveauClasseDto> getListClasse(Long idEcole , Long idAnneId) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole    order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                        .getResultList() ;

    }

    List<NiveauClasseDto> getListClasseSecon(Long idEcole , Long idAnneId, Integer lim) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole   and o.branche.niveau.id >=:lim  order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                .setParameter("lim" ,lim).getResultList() ;

    }

    Long getEffectParGenreClasse(Long idAnneId ,Long idBranche ,String sexe , Long idEcole ) {
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                        " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe");
            Long size = q.setParameter("idBranche" ,idBranche).
                           setParameter("idAnn" ,idAnneId).
                          setParameter("sexe" ,sexe).
                        setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }


    Long getEffectRedoubAfflanClasse(Long idAnneId ,Long idBranche ,String sexe ,String redoubl ,String aff1 , Long idEcole) {
      //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.redoublant=:redoubl and i.afecte=:aff");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("redoubl" ,redoubl).
                    setParameter("aff" ,aff1).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffReacffecteClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean reaffecte , Long idEcole) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.reaffecte =:reaffecte ");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("reaffecte" ,reaffecte).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffInterneClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean interne , Long idEcole ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.internes=:interne");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("interne" ,interne).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffdemiPensionClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean demiPension , Long idEcole ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.demi_pension=:demiPension");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("demiPension" ,demiPension).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffExternesClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean externe , Long idEcole ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.externes=: externe");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("externe" ,externe).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffIvoiriensClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean ivoirien , Long idEcole  ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.ivoirien=:ivoirien");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("ivoirien" ,ivoirien).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffAfricainClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean africain , Long idEcole ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.etranger_africain=:africain");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("africain" ,africain).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getEffNonAfricainClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean nonafricain , Long idEcole) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.etranger_non_africain=:nonafricain");


            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("nonafricain" ,nonafricain).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }
    Long getEffTransfertClasse(Long idAnneId ,Long idBranche ,String sexe ,Boolean transfert ,Long idEcole ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.transfert=:transfert");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("transfert" ,transfert).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }


    Long getEffectEtatBoursierClasse(Long idAnneId ,Long idBranche ,String sexe ,String boursier ,Long idEcole   ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.boursier=:boursier");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("boursier" ,boursier).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

    Long getAffecteClasse(Long idAnneId ,Long idBranche ,String sexe ,String aff1  ,Long idEcole  ) {
        //Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.afecte=:aff");
            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("aff" ,aff1).
                    setParameter("idEcole" ,idEcole).
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
