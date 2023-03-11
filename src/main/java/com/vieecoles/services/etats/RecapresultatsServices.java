package com.vieecoles.services.etats;

import com.vieecoles.dto.RecapResultatsElevesAffecteDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RecapresultatsServices {
    @Inject
    EntityManager em;

    public List<RecapResultatsElevesAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String affecte){

        List<RecapResultatsElevesAffecteDto> resultatsListElevesDto = new ArrayList<>();
        Integer totalBulletin, effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G, effectifClasse,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10F ,pourMoySup10G,pourMoyInf999F,pourMoyInf999G,pourMoyInf85G,pourMoyInf85F,moyClasseF,moyClasseG;
        List<String>niveauList ;
        List<String>classeList ;
        List<String>sexeList ;

        totalBulletin = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getSingleResult();


        niveauList= (List<String>) em.createQuery("select o.niveau from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getResultList() ;

        classeList= (List<String>) em.createQuery("select o.libelleClasse from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getResultList() ;

        sexeList= (List<String>) em.createQuery("select o.sexe from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getResultList() ;

        effectifClasse = (Integer) em.createQuery("select o.effectif from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                    .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getSingleResult();

        effeG= (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                  .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getSingleResult();

        effeF = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte group by  o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .getSingleResult();

        classF = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.is_classed=:isClass group by  o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("isClass","O")
                .getSingleResult();

        classG = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.is_classed=:isClass group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("isClass","O")
                .getSingleResult();

        nonclassF = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.is_classed=:isClass group by o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("isClass","N")
                .getSingleResult();

        nonclassG = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.is_classed=:isClass group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("isClass","N")
                .getSingleResult();

        nbreMoySup10F = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy  group by  o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("moy",10L)
                .getSingleResult();

        nbreMoySup10G = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy  group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("moy",10L)
                .getSingleResult();

        nbreMoyInf999F = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy and o.moyGeneral <:moy2 group by  o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("moy",8.5)
                .setParameter("moy2",9.99)
                .getSingleResult();

        nbreMoyInf999G = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy and o.moyGeneral <:moy2 group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("moy",8.5)
                .setParameter("moy2",9.99)
                .getSingleResult();

        nbreMoyInf85G = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral<:moy  group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("moy",8.5)
                .getSingleResult();

        nbreMoyInf85F = (Integer) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral<:moy  group by  o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("moy",8.5)
                .getSingleResult();

        moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.is_classed=:isClass group by  o.niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("isClass","O")
                .getSingleResult();

        moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.is_classed=:isClass group by  o.niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte",affecte)
                .setParameter("isClass","O")
                .getSingleResult();


        pourMoyInf85G= (double) ((nbreMoyInf85G/effectifClasse)*100);
        pourMoyInf85F= (double) ((nbreMoyInf85F/effectifClasse)*100);

        pourMoyInf999G=(double) ((nbreMoyInf999G/effectifClasse)*100);
        pourMoyInf999F=(double) ((nbreMoyInf999F/effectifClasse)*100);

        pourMoySup10F = (double) ((nbreMoySup10F/effectifClasse)*100);
        pourMoySup10G = (double) ((nbreMoySup10G/effectifClasse)*100);

        for (int i=0; i<totalBulletin;i++) {
            resultatsListElevesDto.get(i).setNiveau(niveauList.get(i));
            resultatsListElevesDto.get(i).setClasse(classeList.get(i));
            resultatsListElevesDto.get(i).setSexe(sexeList.get(i));
            resultatsListElevesDto.get(i).setEffeG(effeG);
            resultatsListElevesDto.get(i).setEffeF(effeF);
            resultatsListElevesDto.get(i).setClassG(classG);
            resultatsListElevesDto.get(i).setClassF(classF);
            resultatsListElevesDto.get(i).setNonclassF(nonclassF);
            resultatsListElevesDto.get(i).setNonclassG(nonclassG);

            resultatsListElevesDto.get(i).setNbreMoySup10F(nbreMoySup10F);
            resultatsListElevesDto.get(i).setNbreMoySup10G(nbreMoySup10G);

            resultatsListElevesDto.get(i).setPourMoySup10F(pourMoySup10F);
            resultatsListElevesDto.get(i).setPourMoySup10G(pourMoySup10G);

            resultatsListElevesDto.get(i).setNbreMoyInf999F(nbreMoyInf999F);
            resultatsListElevesDto.get(i).setNbreMoyInf999G(nbreMoyInf999G);

            resultatsListElevesDto.get(i).setPourMoyInf999F(pourMoyInf999F);
            resultatsListElevesDto.get(i).setPourMoyInf999G(pourMoyInf999G);

            resultatsListElevesDto.get(i).setNbreMoyInf85G(nbreMoyInf85G);
            resultatsListElevesDto.get(i).setNbreMoyInf85F(nbreMoyInf85F);

            resultatsListElevesDto.get(i).setPourMoyInf85G(pourMoyInf85G);
            resultatsListElevesDto.get(i).setPourMoyInf85F(pourMoyInf85F);

            resultatsListElevesDto.get(i).setMoyClasseG(moyClasseG);
            resultatsListElevesDto.get(i).setMoyClasseF(moyClasseF);

        }




        return  resultatsListElevesDto ;
    }

}
