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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class RecapitulatifStatistiqueServices1 {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<EffectifNiveauGenreNationaliteDto> getEffectifNiveauGenre(Long idEcole ,Long anneeId ,String libelleAnnee, String periode ){



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

            nombreClasse = findNombreClasseBranche(idEcole,libelleAnnee,periode,niveauDtoList.get(i).getId()) ;
            m.setNombreClasse(nombreClasse);
            m.setNiveau(niveauDtoList.get(i).getNiveau());
          nombreCycleClasse = findNombreClasseBrancheCycle(idEcole,libelleAnnee,periode) ;
          m.setNombreCycleClasse(nombreCycleClasse);

          nbreRedouIvoireF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",true,idEcole,periode);
          m.setNbreRedouIvoireF(nbreRedouIvoireF);
          nbreRedouIvoireG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",true,idEcole,periode);
          m.setNbreRedouIvoireG(nbreRedouIvoireG);
          nbrnbreRedouIvoireTotal=nbreRedouIvoireF+nbreRedouIvoireG;
          m.setNbrnbreRedouIvoireTotal(nbrnbreRedouIvoireTotal);


          nbreRedouIvoireCycleF= getEffectRedoubAfflanClasseCycle(anneeId ,"FEMININ","OUI",true,idEcole,periode);
          m.setNbreRedouIvoireCycleF(nbreRedouIvoireCycleF);
          nbreRedouIvoireCycleG= getEffectRedoubAfflanClasseCycle(anneeId ,"MASCULIN","OUI",true,idEcole,periode);
          m.setNbreRedouIvoireCycleG(nbreRedouIvoireCycleG);
          nbrnbreRedouIvoireCycleTotal=nbreRedouIvoireCycleF+nbreRedouIvoireCycleG;
          m.setNbrnbreRedouIvoireCycleTotal(nbrnbreRedouIvoireCycleTotal);



          nbreRedouEtrangerF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",false,idEcole,periode);
          m.setNbreRedouEtrangerF(nbreRedouEtrangerF);
          nbreRedouEtrangerG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",false,idEcole,periode);
          m.setNbreRedouEtrangerG(nbreRedouEtrangerG);
          nbrnbreRedouEtrangerTotal=nbreRedouIvoireF+nbreRedouIvoireG;
          m.setNbrnbreRedouEtrangerTotal(nbrnbreRedouEtrangerTotal);

          nbreRedouEtrangerCycleF= getEffectRedoubAfflanClasseCycle(anneeId,"FEMININ","OUI",false,idEcole,periode);
          m.setNbreRedouEtrangerCycleF(nbreRedouEtrangerCycleF);
          nbreRedouEtrangerCycleG= getEffectRedoubAfflanClasseCycle(anneeId,"MASCULIN","OUI",false,idEcole,periode);
          m.setNbreRedouEtrangerCycleG(nbreRedouEtrangerCycleG);
          nbrnbreRedouEtrangerCycleTotal=nbreRedouIvoireCycleF+nbreRedouIvoireCycleG;
          m.setNbrnbreRedouEtrangerCycleTotal(nbrnbreRedouEtrangerCycleTotal);



          nbreNonRedouIvoireF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON",true,idEcole,periode);
          m.setNbreNonRedouIvoireF(nbreNonRedouIvoireF);
          nbreNonRedouIvoireG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","NON",true,idEcole,periode);
          m.setNbreNonRedouIvoireG(nbreNonRedouIvoireG);
          nbreNonRedouIvoireTotal=nbreNonRedouIvoireF+nbreNonRedouIvoireG;
          m.setNbreNonRedouIvoireTotal(nbreNonRedouIvoireTotal);

          nbreNonRedouIvoireCycleF= getEffectRedoubAfflanClasseCycle(anneeId,"FEMININ","NON",true,idEcole,periode);
          m.setNbreNonRedouIvoireCycleF(nbreNonRedouIvoireCycleF);
          nbreNonRedouIvoireCycleG= getEffectRedoubAfflanClasseCycle(anneeId ,"MASCULIN","NON",true,idEcole,periode);
          m.setNbreNonRedouIvoireCycleG(nbreNonRedouIvoireCycleG);
          nbreNonRedouIvoireCycleTotal=nbreNonRedouIvoireCycleF+nbreNonRedouIvoireCycleG;
          m.setNbreNonRedouIvoireCycleTotal(nbreNonRedouIvoireCycleTotal);


          nbreNonRedouEtrangerF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON",false,idEcole,periode);
          m.setNbreNonRedouEtrangerF(nbreNonRedouEtrangerF);
          nbreNonRedouEtrangerG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","NON",false,idEcole,periode);
          m.setNbreNonRedouEtrangerG(nbreNonRedouEtrangerG);
          nbreNonRedouEtrangerTotal=nbreNonRedouEtrangerF+nbreNonRedouEtrangerG;
          m.setNbreNonbreRedouEtrangerTotal(nbreNonRedouEtrangerTotal);

          nbreNonRedouEtrangerCycleF= getEffectRedoubAfflanClasseCycle(anneeId,"FEMININ","NON",false,idEcole,periode);
          m.setNbreNonRedouEtrangerCycleF(nbreNonRedouEtrangerCycleF);
          nbreNonRedouEtrangerCycleG= getEffectRedoubAfflanClasseCycle(anneeId,"MASCULIN","NON",false,idEcole,periode);
          m.setNbreNonRedouEtrangerCycleG(nbreNonRedouEtrangerCycleG);
          nbreNonRedouEtrangerCycleTotal=nbreNonRedouEtrangerCycleF+nbreNonRedouEtrangerCycleG;
          m.setNbreNonRedouEtrangerCycleTotal(nbreNonRedouEtrangerCycleTotal);

          totalNiveauF = getEffectTotalRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId(),"FEMININ",idEcole,periode);
          m.setTotalNiveauF(totalNiveauF);
          totalNiveauG = getEffectTotalRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId(),"MASCULIN",idEcole,periode);
          m.setTotalNiveauG(totalNiveauG);
          totalNiveauTotal=totalNiveauF+totalNiveauG;
          m.setTotaNiveauTotal(totalNiveauTotal);

          totalCycleF = getEffectTotalRedoubAfflanClasseCycle(anneeId,"FEMININ",idEcole,periode);
           m.setTotalCycleF(totalCycleF);
          totalCycleG = getEffectTotalRedoubAfflanClasseCycle(anneeId,"MASCULIN",idEcole,periode);
          m.setTotalCycleG(totalCycleG);
          totalCycleTotal=totalCycleF+totalCycleG;
          m.setTotalCycleTotal(totalCycleTotal);



          resultatsListElevesDto.add(m) ;

        }


        return  resultatsListElevesDto ;
    }





  List<NiveauClasseIdDto> getListClasse(Long idEcole , Long idAnneId) {
    List<NiveauClasseIdDto> classeNiveauDtoList = new ArrayList<>() ;
    TypedQuery<NiveauClasseIdDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseIdDto(b.niveau,b.ordreNiveau) FROM Bulletin b where  b.ecoleId =:idEcole and b.anneeId=:annee and b.ordreNiveau<5  order by b.ordreNiveau "
        , NiveauClasseIdDto.class);
    return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
        . setParameter("annee" ,idAnneId)
        .getResultList() ;

  }






  Long getEffectRedoubAfflanClasse(Long idAnneId ,Integer idBranche ,String sexe ,String redoubl ,boolean ivoir , Long idEcole,String periode) {
    //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId and o.redoublant=:redoubl and o.ordreNiveau<5 " +
          " and o.ivoirien=:ivoir and  o.ordreNiveau=:idBranche and o.libellePeriode=:periode");

      Long size = q.setParameter("idBranche" ,idBranche).
          setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("redoubl" ,redoubl).
          setParameter("ivoir" ,ivoir).
          setParameter("idEcole" ,idEcole).
          setParameter("periode" ,periode).
          getSingleResult() ;

      return size;
    } catch (NoResultException e) {
      return 0L ;
    }
  }

  Long getEffectRedoubAfflanClasseCycle(Long idAnneId ,String sexe ,String redoubl ,boolean ivoir , Long idEcole,String periode) {
    //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId and o.redoublant=:redoubl and o.ordreNiveau<5 " +
          " and o.ivoirien=:ivoir and o.libellePeriode=:periode  ");

      Long size = q.setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("redoubl" ,redoubl).
          setParameter("ivoir" ,ivoir).
          setParameter("idEcole" ,idEcole).
          setParameter("periode" ,periode).
          getSingleResult() ;

      return size;
    } catch (NoResultException e) {
      return 0L ;
    }
  }

  Long getEffectTotalRedoubAfflanClasse(Long idAnneId ,Integer idBranche ,String sexe  , Long idEcole,String periode) {

    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId and o.ordreNiveau<5 " +
          "   and   o.ordreNiveau=:idBranche and o.libellePeriode=:periode ");

      Long size = q.setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("idEcole" ,idEcole).
          setParameter("idBranche" ,idBranche).
          setParameter("periode" ,periode).
          getSingleResult() ;

      return size;
    } catch (NoResultException e) {
      return 0L ;
    }
  }

  Long getEffectTotalRedoubAfflanClasseCycle(Long idAnneId  ,String sexe , Long idEcole,String periode) {

    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( " select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.anneeId=:idAnneId and o.ordreNiveau<5 and o.libellePeriode=:periode"
      );

      Long size = q.setParameter("idAnneId" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("idEcole" ,idEcole).
          setParameter("periode" ,periode).
          getSingleResult() ;

      return size;
    } catch (NoResultException e) {
      return 0L ;
    }
  }





  public  Long findNombreClasseBranche(Long idEcole  ,String libelleAnnee , String libelleTrimestre,int orderNiveau){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select count(distinct o.classeId) from Bulletin o where o.ordreNiveau=:orderNiveau and  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  ")
          .setParameter("orderNiveau",orderNiveau)
          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }

  public Long findNombreClasseBrancheCycle( Long ecoleId,String libelleAnnee , String libelleTrimestre) {
    //logger.info(String.format("find by Branche id :: %s", id));
    Long nbreClasse ;
    try {
      return  nbreClasse = (Long) em.createQuery("select count(distinct o.classeId) from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:libelleTrimestre and o.anneeLibelle=:libelleAnnee and o.ordreNiveau<5  ")
          .setParameter("idEcole",ecoleId)
          .setParameter("libelleAnnee", libelleAnnee)
          .setParameter("libelleTrimestre", libelleTrimestre)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }
}
