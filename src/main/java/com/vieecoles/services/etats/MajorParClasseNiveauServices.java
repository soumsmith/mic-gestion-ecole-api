package com.vieecoles.services.etats;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.TransfertsDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MajorParClasseNiveauServices {
    @Inject
    EntityManager em;

    public List<MajorParClasseNiveauDto> MajorParNiveauClasse(Long idEcole){

        List<ClasseNiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<ClasseNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.ClasseNiveauDto(b.libelleClasse ,b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.affecte=:affecte " +
                "group by b.libelleClasse ,b.niveau ", ClasseNiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                          .setParameter("affecte", "AFFECTE")
                           . getResultList() ;

  System.out.println("classeNiveauDtoList "+classeNiveauDtoList.toString());
        System.out.println("Longueur Tableau" +classeNiveauDtoList.size());
      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10F ,pourMoySup10G,pourMoyInf999F,pourMoyInf999G,pourMoyInf85G,pourMoyInf85F,moyClasseF,moyClasseG;
       Integer effectifClasse ;
        List<MajorParClasseNiveauDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
        List<MajorParClasseNiveauDto> majorExeco = new ArrayList<>();
        for (int i=0; i< LongTableau;i++) {
            MajorParClasseNiveauDto resultatsListEleves= new MajorParClasseNiveauDto();
            Double moyMajor = null;
            System.out.println(" Debut calcul Moyenne Major " + moyMajor);
            moyMajor = getMajorDto(idEcole,classeNiveauDtoList.get(i).getNiveau(),classeNiveauDtoList.get(i).getClasse()) ;
            System.out.println("Moyenne Major  " + moyMajor);
            System.out.println("Niveau   " + classeNiveauDtoList.get(i).getNiveau());
            System.out.println("Classe   " + classeNiveauDtoList.get(i).getClasse());
            majorExeco = getListMajorParClasseNiveau(idEcole,classeNiveauDtoList.get(i).getNiveau(),classeNiveauDtoList.get(i).getClasse(),moyMajor) ;

            if(majorExeco.size() >1){
                System.out.println("Mojor et execo   " + majorExeco.toString());
                for (int k=0 ;k< majorExeco.size(); k++){
                    resultatsListEleves = majorExeco.get(k);
                    resultatsListElevesDto.add(resultatsListEleves) ;
                }
            }else {
                resultatsListEleves = majorExeco.get(0);
                resultatsListElevesDto.add(resultatsListEleves) ;
            }

           // resultatsListEleves = getListMajorParClasseNiveau(idEcole,classeNiveauDtoList.get(i).getNiveau(),classeNiveauDtoList.get(i).getClasse(),moyMajor) ;


           // resultatsListElevesDto.add(resultatsListEleves) ;
        }

        return  resultatsListElevesDto ;
    }


    public List<MajorParClasseNiveauDto>  getListMajorParClasseNiveau(Long idEcole , String niveau,String classe,Double moy){
        List<MajorParClasseNiveauDto> classeNiveauDtoList = new ArrayList<>();
        try {
            TypedQuery  q= em.createQuery("select new com.vieecoles.dto.MajorParClasseNiveauDto(o.niveau,o.libelleClasse,o.matricule,o.nom,o.prenoms,SUBSTRING(o.dateNaissance,1,4) ,o.sexe,o.appreciation,o.redoublant,o.moyGeneral,o.lv2,o.ordreNiveau) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau and o.libelleClasse=:libelleClasse and o.moyGeneral=:moy ", MajorParClasseNiveauDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("libelleClasse",classe)
                    .setParameter("moy",moy)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public Double getMajorDto(Long idEcole , String niveau,String classe){
        Double classeNiveauDtoList ;
        try {
            TypedQuery<Double> q= (TypedQuery<Double>) em.createQuery("select MAX(o.moyGeneral) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau and o.libelleClasse=:libelleClasse");
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("libelleClasse",classe)
                    .getSingleResult() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return 0D ;
        }

    }



}
