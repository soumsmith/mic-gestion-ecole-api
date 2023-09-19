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
public class PyramideEffectifBilanServices {
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

        for (int i=0; i< sizeClass;i++) {
            PyramideEffectDto m = new PyramideEffectDto() ;


                effectifClasseDto effectifClasseDtos  = new effectifClasseDto();
            RedoublantAffClasseDto redoublantAffClasseDtos = new RedoublantAffClasseDto();
            AffecteClasseDto affecteClasseDtos  = new AffecteClasseDto();

            m.setNiveau(niveauDtoList.get(i).getNiveau());
            System.out.println("mm "+m.getNiveau());


            nombreClasse = findNombreClasseBranche(niveauDtoList.get(i).getId(),idEcole) ;
            System.out.println("nombreClasse "+nombreClasse);
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

    List<NiveauClasseDto> getListClasse(Long idEcole , Long idAnneId) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole  and o.annee.id =:idAnneId   order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                                          // .setParameter("lim" ,lim)
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
