package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.ClasseNiveauOrderDto;
import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.MajorParNiveauDto;
import com.vieecoles.dto.NiveauDto2;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class MajorParNiveauPoiServices {
    @Inject
    EntityManager em;

    public List<MajorParNiveauDto> MajorParNiveauClasse(Long idEcole , String libelleAnnee , String libelleTrimestre){

        List<NiveauDto2> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto2> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto2(b.niveau ,b.ordreNiveau) from Bulletin b  where b.ecoleId =:idEcole  and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.ordreNiveau, b.niveau order by b.ordreNiveau, b.niveau", NiveauDto2.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                            .setParameter("annee", libelleAnnee)
                            .setParameter("periode", libelleTrimestre)
                           . getResultList() ;

      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10F ,pourMoySup10G,pourMoyInf999F,pourMoyInf999G,pourMoyInf85G,pourMoyInf85F,moyClasseF,moyClasseG;
       Integer effectifClasse ;
        List<MajorParNiveauDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
        List<MajorParNiveauDto> majorExeco = new ArrayList<>();
        for (int i=0; i< LongTableau;i++) {
            MajorParNiveauDto  resultatsListEleves= new MajorParNiveauDto();
            Double moyMajor = null;

            moyMajor = getMajorDto(idEcole,classeNiveauDtoList.get(i).getNiveau(),libelleAnnee , libelleTrimestre) ;

            majorExeco = getListMajorParClasseNiveau(idEcole,classeNiveauDtoList.get(i).getNiveau(),moyMajor,libelleAnnee , libelleTrimestre) ;

            if(majorExeco.size() >1){

                for (int k=0 ;k< majorExeco.size(); k++){
                    resultatsListEleves = majorExeco.get(k);
                    resultatsListElevesDto.add(resultatsListEleves) ;
                }
            }else {
                resultatsListEleves = majorExeco.get(0);
                resultatsListElevesDto.add(resultatsListEleves) ;
            }


        }

        return  resultatsListElevesDto ;
    }


    public List<MajorParNiveauDto>  getListMajorParClasseNiveau(Long idEcole , String niveau,Double moy ,String libelleAnnee , String libelleTrimestre ){
        List<MajorParNiveauDto> classeNiveauDtoList = new ArrayList<>();
        try {
            TypedQuery  q= em.createQuery("select new com.vieecoles.dto.MajorParNiveauDto(o.niveau,o.libelleClasse,o.matricule,o.nom,o.prenoms,SUBSTRING(o.dateNaissance,1,4) ,o.sexe,o.appreciation,o.redoublant,o.moyGeneral,o.lv2,o.ordreNiveau,o.nationalite) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau  and o.moyGeneral=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee ", MajorParNiveauDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("moy",moy)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public Double getMajorDto(Long idEcole , String niveau,String libelleAnnee , String libelleTrimestre){
        Double classeNiveauDtoList ;
        try {
            TypedQuery<Double> q= (TypedQuery<Double>) em.createQuery("select MAX(o.moyGeneral) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau  and o.libellePeriode=:periode and o.anneeLibelle=:annee");
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return 0D ;
        }

    }



}
