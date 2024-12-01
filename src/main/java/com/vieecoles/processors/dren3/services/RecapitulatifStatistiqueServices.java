package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.AffecteClasseDto;
import com.vieecoles.dto.EffectifNiveauGenreNationaliteDto;
import com.vieecoles.dto.NiveauClasseDto;
import com.vieecoles.dto.PyramideEffectDto;
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

    public List<EffectifNiveauGenreNationaliteDto> getPyramide(Long idEcole ,Long anneeId ,Integer lim){

        List<NiveauClasseDto> niveauDtoList = new ArrayList<>() ;

         if(lim ==4) {
             niveauDtoList= getListClasse(idEcole,anneeId,lim);
         } else {
          //   niveauDtoList= getListClasseSecon(idEcole,anneeId,lim);
         }


       int sizeClass ;
        sizeClass = niveauDtoList.size() ;

      List<EffectifNiveauGenreNationaliteDto> resultatsListElevesDto = new ArrayList<>() ;

        List<effectifClasseDto> effectifClasseDtosList  = new ArrayList<>() ;
        List<RedoublantAffClasseDto> redoublantAffClasseDtosList = new ArrayList<>();
        List<AffecteClasseDto> affecteClasseDtosLis  = new ArrayList<>();

     String niveau ;Long nombreClasse ; Long nbreRedouIvoireG; Long nbreRedouIvoireF; Long nbrnbreRedouIvoireTotal;
         Long nbreRedouEtrangerG;Long nbreRedouEtrangerF; Long nbrnbreRedouEtrangerTotal;
         Long nbreNonRedouIvoireG; Long nbreNonRedouIvoireF;  Long nbreNonRedouIvoireTotal;Long nbreNonRedouEtrangerG;
         Long nbreNonRedouEtrangerF; Long nbreNonRedouEtrangerTotal;Long totalGeneralG;Long totalGeneralF;
         Long totalGeneralTotal;

        for (int i=0; i< sizeClass;i++) {
          EffectifNiveauGenreNationaliteDto effectifClasseDtos  = new EffectifNiveauGenreNationaliteDto();

            AffecteClasseDto affecteClasseDtos  = new AffecteClasseDto();

            nombreClasse = findNombreClasseBranche(niveauDtoList.get(i).getId(),idEcole) ;

          nbreRedouIvoireF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",true,idEcole);
          nbreRedouIvoireG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",true,idEcole);
          nbrnbreRedouIvoireTotal=nbreRedouIvoireF+nbreRedouIvoireG;

          nbreRedouEtrangerF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",false,idEcole);
          nbreRedouEtrangerG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",false,idEcole);
          nbrnbreRedouEtrangerTotal=nbreRedouIvoireF+nbreRedouIvoireG;


          nbreNonRedouIvoireF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","NON",true,idEcole);
          nbreNonRedouIvoireG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",true,idEcole);
          nbreNonRedouIvoireTotal=nbreNonRedouIvoireF+nbreNonRedouIvoireG;

          nbreNonRedouEtrangerF= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"FEMININ","OUI",false,idEcole);
          nbreNonRedouEtrangerG= getEffectRedoubAfflanClasse(anneeId,niveauDtoList.get(i).getId() ,"MASCULIN","OUI",false,idEcole);
          nbreNonRedouEtrangerTotal=nbreNonRedouEtrangerF+nbreNonRedouEtrangerG;





           // resultatsListElevesDto.add(m) ;

        }


        return  resultatsListElevesDto ;
    }





    List<NiveauClasseDto> getListClasse(Long idEcole , Long idAnneId, Integer lim) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole   and o.branche.niveau.id <=:lim  order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                                           .setParameter("lim" ,lim).getResultList() ;

    }






    Long getEffectRedoubAfflanClasse(Long idAnneId ,Long idBranche ,String sexe ,String redoubl ,boolean ivoir , Long idEcole) {
      //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
                    " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole and c.branche.id =:idBranche " +
                    "and i.annee.id =:idAnn and e.sexe=:sexe and i.redoublant=:redoubl and i.ivoirien=:ivoir");

            Long size = q.setParameter("idBranche" ,idBranche).
                    setParameter("idAnn" ,idAnneId).
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

  Long getEffectTotalRedoubAfflanClasse(Long idAnneId ,Long idBranche ,String sexe ,String redoubl ,boolean ivoir , Long idEcole) {

    try {
      TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(ic.id) FROM ClasseEleve ic , Inscription i  ,Eleve e ,Classe  c" +
          " where ic.classe.id = c.id and ic.inscription.id = i.id and i.eleve.id= e.id and i.ecole.id =: idEcole  " +
          "and i.annee.id =:idAnn and e.sexe=:sexe  and i.ivoirien=:ivoir");

      Long size = q.setParameter("idAnn" ,idAnneId).
          setParameter("sexe" ,sexe).
          setParameter("ivoir" ,ivoir).
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
