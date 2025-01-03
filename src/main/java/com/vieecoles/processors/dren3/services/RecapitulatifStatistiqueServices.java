package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.AffecteClasseDto;
import com.vieecoles.dto.EffectifNiveauGenreNationaliteDto;
import com.vieecoles.dto.NiveauClasseDto;
import com.vieecoles.dto.NiveauClasseIdDto;
import com.vieecoles.dto.RedoublantAffClasseDto;
import com.vieecoles.dto.effectifClasseDto;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class RecapitulatifStatistiqueServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<EffectifNiveauGenreNationaliteDto> getEffectifNiveauGenre(Long idEcole ,Long anneeId ,String annee, String periode){



      List<NiveauClasseIdDto> niveauDtoList = new ArrayList<>() ;

        niveauDtoList= getListClasse(idEcole,anneeId);



      int LongTableau =niveauDtoList.size() ;


       int sizeClass ;
        sizeClass = niveauDtoList.size() ;

      List<EffectifNiveauGenreNationaliteDto> resultatsListElevesDto = new ArrayList<>() ;

        List<effectifClasseDto> effectifClasseDtosList  = new ArrayList<>() ;
        List<RedoublantAffClasseDto> redoublantAffClasseDtosList = new ArrayList<>();
        List<AffecteClasseDto> affecteClasseDtosLis  = new ArrayList<>();

     String niveau ;Long nombreClasse ; Long nbreRedouIvoireG; Long nbreRedouIvoireF; Long nbrnbreRedouIvoireTotal;
         Long nbreRedouEtrangerG;Long nbreRedouEtrangerF; Long nbrnbreRedouEtrangerTotal;
         Long nbreNonRedouIvoireG; Long nbreNonRedouIvoireF;  Long nbreNonRedouIvoireTotal;Long nbreNonRedouEtrangerG;
         Long nbreNonRedouEtrangerF; Long nbreNonRedouEtrangerTotal;Long totalNiveauG;Long totalNiveauF;
         Long totalNiveauTotal;Long nombreCycleClasse;
         Long nbreRedouIvoireCycleG; Long nbreRedouIvoireCycleF; Long nbrnbreRedouIvoireCycleTotal;
          Long nbreRedouEtrangerCycleG; Long nbreRedouEtrangerCycleF; Long nbrnbreRedouEtrangerCycleTotal; Long nbreNonRedouIvoireCycleG;
          Long nbreNonRedouIvoireCycleF; Long nbreNonRedouIvoireCycleTotal; Long nbreNonRedouEtrangerCycleG; Long nbreNonRedouEtrangerCycleF;
           Long nbreNonRedouEtrangerCycleTotal; Long totalCycleG;Long totalCycleF; Long totalCycleTotal;

        for (int i=0; i< sizeClass;i++) {
          EffectifNiveauGenreNationaliteDto m  = new EffectifNiveauGenreNationaliteDto();

            AffecteClasseDto affecteClasseDtos  = new AffecteClasseDto();

            nombreClasse = findNombreClasseBranche(niveauDtoList.get(i).getId(),idEcole) ;
            m.setNombreClasse(nombreClasse);
          nombreCycleClasse = findNombreClasseBrancheCycle(idEcole,annee,periode) ;
          m.setNombreCycleClasse(nombreCycleClasse);
          m.setNiveau(niveauDtoList.get(i).getNiveau());
          nbreRedouIvoireF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",true,idEcole);
          m.setNbreRedouIvoireF(nbreRedouIvoireF);
          nbreRedouIvoireG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",true,idEcole);
          m.setNbreRedouIvoireG(nbreRedouIvoireG);
          nbrnbreRedouIvoireTotal=nbreRedouIvoireF+nbreRedouIvoireG;
          m.setNbrnbreRedouIvoireTotal(nbrnbreRedouIvoireTotal);


          nbreRedouIvoireCycleF= getEffectRedoubAfflanClasseCycle(anneeId ,"FEMININ","OUI",true,idEcole);
          m.setNbreRedouIvoireCycleF(nbreRedouIvoireCycleF);
          nbreRedouIvoireCycleG= getEffectRedoubAfflanClasseCycle(anneeId ,"MASCULIN","OUI",true,idEcole);
          m.setNbreRedouIvoireCycleG(nbreRedouIvoireCycleG);
          nbrnbreRedouIvoireCycleTotal=nbreRedouIvoireCycleF+nbreRedouIvoireCycleG;
          m.setNbrnbreRedouIvoireCycleTotal(nbrnbreRedouIvoireCycleTotal);



          nbreRedouEtrangerF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",false,idEcole);
          m.setNbreRedouEtrangerF(nbreRedouEtrangerF);
          nbreRedouEtrangerG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",false,idEcole);
          m.setNbreRedouEtrangerG(nbreRedouEtrangerG);
          nbrnbreRedouEtrangerTotal=nbreRedouIvoireF+nbreRedouIvoireG;
          m.setNbrnbreRedouEtrangerTotal(nbrnbreRedouEtrangerTotal);

          nbreRedouEtrangerCycleF= getEffectRedoubAfflanClasseCycle(anneeId,"FEMININ","OUI",false,idEcole);
          m.setNbreRedouEtrangerCycleF(nbreRedouEtrangerCycleF);
          nbreRedouEtrangerCycleG= getEffectRedoubAfflanClasseCycle(anneeId,"MASCULIN","OUI",false,idEcole);
          m.setNbreRedouEtrangerCycleG(nbreRedouEtrangerCycleG);
          nbrnbreRedouEtrangerCycleTotal=nbreRedouIvoireCycleF+nbreRedouIvoireCycleG;
          m.setNbrnbreRedouEtrangerCycleTotal(nbrnbreRedouEtrangerCycleTotal);



          nbreNonRedouIvoireF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON",true,idEcole);
          m.setNbreNonRedouIvoireF(nbreNonRedouIvoireF);
          nbreNonRedouIvoireG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","NON",true,idEcole);
          m.setNbreNonRedouIvoireG(nbreNonRedouIvoireG);
          nbreNonRedouIvoireTotal=nbreNonRedouIvoireF+nbreNonRedouIvoireG;
          m.setNbreNonRedouIvoireTotal(nbreNonRedouIvoireTotal);

          nbreNonRedouIvoireCycleF= getEffectRedoubAfflanClasseCycle(anneeId,"FEMININ","NON",true,idEcole);
          m.setNbreNonRedouIvoireCycleF(nbreNonRedouIvoireCycleF);
          nbreNonRedouIvoireCycleG= getEffectRedoubAfflanClasseCycle(anneeId ,"MASCULIN","NON",true,idEcole);
          m.setNbreNonRedouIvoireCycleG(nbreNonRedouIvoireCycleG);
          nbreNonRedouIvoireCycleTotal=nbreNonRedouIvoireCycleF+nbreNonRedouIvoireCycleG;
          m.setNbreNonRedouIvoireCycleTotal(nbreNonRedouIvoireCycleTotal);


          nbreNonRedouEtrangerF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON",false,idEcole);
          m.setNbreNonRedouEtrangerF(nbreNonRedouEtrangerF);
          nbreNonRedouEtrangerG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","NON",false,idEcole);
          m.setNbreNonRedouEtrangerG(nbreNonRedouEtrangerG);
          nbreNonRedouEtrangerTotal=nbreNonRedouEtrangerF+nbreNonRedouEtrangerG;
          m.setNbreNonbreRedouEtrangerTotal(nbreNonRedouEtrangerTotal);

          nbreNonRedouEtrangerCycleF= getEffectRedoubAfflanClasseCycle(anneeId,"FEMININ","NON",false,idEcole);
          m.setNbreNonRedouEtrangerCycleF(nbreNonRedouEtrangerCycleF);
          nbreNonRedouEtrangerCycleG= getEffectRedoubAfflanClasseCycle(anneeId,"MASCULIN","NON",false,idEcole);
          m.setNbreNonRedouEtrangerCycleG(nbreNonRedouEtrangerCycleG);
          nbreNonRedouEtrangerCycleTotal=nbreNonRedouEtrangerCycleF+nbreNonRedouEtrangerCycleG;
          m.setNbreNonRedouEtrangerCycleTotal(nbreNonRedouEtrangerCycleTotal);

          totalNiveauF = getEffectTotalRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId(),"FEMININ",idEcole);
          m.setTotalNiveauF(totalNiveauF);
          totalNiveauG = getEffectTotalRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId(),"MASCULIN",idEcole);
          m.setTotalNiveauG(totalNiveauG);
          totalNiveauTotal=totalNiveauF+totalNiveauG;
          m.setTotaNiveauTotal(totalNiveauTotal);

          totalCycleF = getEffectTotalRedoubAfflanClasseCycle(anneeId,"FEMININ",idEcole);
           m.setTotalCycleF(totalCycleF);
          totalCycleG = getEffectTotalRedoubAfflanClasseCycle(anneeId,"MASCULIN",idEcole);
          m.setTotalCycleG(totalCycleG);
          totalCycleTotal=totalCycleF+totalCycleG;
          m.setTotalCycleTotal(totalCycleTotal);










          resultatsListElevesDto.add(m) ;

        }


        return  resultatsListElevesDto ;
    }





    List<NiveauClasseIdDto> getListClasse(Long idEcole , Long idAnneId) {
        List<NiveauClasseIdDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseIdDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseIdDto(b.niveau,b.ordreNiveau) FROM Bulletin b where  b.ecoleId =:idEcole and b.anneeId=:annee   order by b.ordreNiveau "
                , NiveauClasseIdDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                                          . setParameter("annee" ,idAnneId)
                                      .getResultList() ;

    }






    Long getEffectRedoubAfflanClasse(Long idAnneId ,Integer idBranche ,String sexe ,String redoubl ,boolean ivoir , Long idEcole) {
      //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId and o.redoublant=:redoubl" +
                " and o.ivoirien=:ivoir  and o.ordreNiveau=:idBranche");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnneId" ,idAnneId).
                    setParameter("sexe" ,sexe).
                    setParameter("redoubl" ,redoubl).
                    setParameter("ivoir" ,ivoir).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }

  Long getEffectRedoubAfflanClasseCycle(Long idAnneId ,String sexe ,String redoubl ,boolean ivoir , Long idEcole) {
    //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId and o.redoublant=:redoubl " +
          " and o.ivoirien=:ivoir ");

      Long size = q.setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("redoubl" ,redoubl).
          setParameter("ivoir" ,ivoir).
          setParameter("idEcole" ,idEcole).
          getSingleResult() ;

      return size;
    } catch (NoResultException e) {
      return 0L ;
    }
  }

  Long getEffectTotalRedoubAfflanClasse(Long idAnneId ,Integer idBranche ,String sexe  , Long idEcole) {

    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId " +
         "  and  o.ordreNiveau=:idBranche");

      Long size = q.setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("idEcole" ,idEcole).
          setParameter("idBranche" ,idBranche).
          getSingleResult() ;

      return size;
    } catch (NoResultException e) {
      return 0L ;
    }
  }

  Long getEffectTotalRedoubAfflanClasseCycle(Long idAnneId  ,String sexe , Long idEcole) {

    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( " select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId "
         );

      Long size = q.setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
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

  public Long findNombreClasseBrancheCycle( Long ecoleId,String libelleAnnee , String libelleTrimestre) {
    //logger.info(String.format("find by Branche id :: %s", id));
    Long nbreClasse ;
    try {
      return  nbreClasse = (Long) em.createQuery("select count(distinct o.classeId) from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:libelleTrimestre and o.anneeLibelle=:libelleAnnee  ")
          .setParameter("idEcole",ecoleId)
          .setParameter("libelleAnnee", libelleAnnee)
          .setParameter("libelleTrimestre", libelleTrimestre)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }
}
