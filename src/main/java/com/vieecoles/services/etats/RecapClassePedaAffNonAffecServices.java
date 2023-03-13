package com.vieecoles.services.etats;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapitulatifClassePedagoAffectNonAffect;
import com.vieecoles.dto.ResultatsElevesAffecteDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RecapClassePedaAffNonAffecServices {
    @Inject
    EntityManager em;

    public RecapitulatifClassePedagoAffectNonAffect RecapClassePedagoAffecNonAffect(Long idEcole){
        int LongTableau;
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole  " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                              . getResultList() ;

        LongTableau = classeNiveauDtoList.size();





        Long   n6F,n6G,n6AF,n6NAF, n6NCLA, n5F, n5G, n5AF,
                n5NAF, n5NCLA,n4espF,n4espG, n4espAF, n4espNAF,n4espNCLA,
                n4allF, n4allG, n4allAF,n4allNAF,n4allNCLA, n3espF,
                n3espG, n3espAF,n3espNAF,n3espNCLA,n3allF, n3allG,
                n3allAF,n3allNAF, n3allNCLA,
                n2ndAespF,n2ndAespG, n2ndAespAF,n2ndAespNAF,n2ndAespNCLA,
                n2ndAallF, n2ndAallG,n2ndAallAF,n2ndAallNAF, n2ndAallNCLA,

                n2ndCespF,n2ndCespG, n2ndCespAF,n2ndCespNAF,n2ndCespNCLA,
                n2ndCallF, n2ndCallG,n2ndCallAF,n2ndCallNAF, n2ndCallNCLA,
                n1ereAespF,n1ereAespG, n1ereAespAF,n1ereAespNAF,n1ereAespNCLA,
                n1ereAallF, n1ereAallG,n1ereAallAF,n1ereAallNAF, n1ereAallNCLA,

                n1ereCespF,n1ereCespG, n1ereCespAF,n1ereCespNAF,n1ereCespNCLA,
                n1ereCallF, n1ereCallG,n1ereCallAF,n1ereCallNAF, n1ereCallNCLA,

                n1ereDespF,n1ereDespG, n1ereDespAF,n1ereDespNAF,n1ereDespNCLA,
                n1ereDallF, n1ereDallG,n1ereDallAF,n1ereDallNAF, n1ereDallNCLA,

                nTleAespF,nTleAespG, nTleAespAF,nTleAespNAF,nTleAespNCLA,
                nTleAallF, nTleAallG,nTleAallAF,nTleAallNAF, nTleAallNCLA,

                nTleCespF,nTleCespG, nTleCespAF,nTleCespNAF,nTleCespNCLA,
                nTleCallF, nTleCallG,nTleCallAF,nTleCallNAF, nTleCallNCLA,

                nTleDespF,nTleDespG, nTleDespAF,nTleDespNAF,nTleDespNCLA,
                nTleDallF, nTleDallG,nTleDallAF,nTleDallNAF, nTleDallNCLA;
        String niveau ;

       Integer effectifClasse ;
        List<RecapitulatifClassePedagoAffectNonAffect> resultatsListElevesDto = new ArrayList<>(LongTableau);
        System.out.println("resultatsListElevesDto Size "+ resultatsListElevesDto.size());

        RecapitulatifClassePedagoAffectNonAffect resultatsListEleves= new RecapitulatifClassePedagoAffectNonAffect();

        n6F= getNbrePedag(idEcole,"Sixième","FEMININ") ;
        n6G= getNbrePedag(idEcole,"Sixième","MASCULIN") ;
        n6AF= getNbreAffect(idEcole,"Sixième","AFFECTE");
        n6NAF= getNbreAffect(idEcole,"Sixième","NON_AFFECTE");
        n6NCLA= getNbreClasse(idEcole,"Sixième");

        n5F= getNbrePedag(idEcole,"Cinquième","FEMININ") ;
        n5G= getNbrePedag(idEcole,"Cinquième","MASCULIN") ;
        n5AF= getNbreAffect(idEcole,"Cinquième","AFFECTE");
        n5NAF= getNbreAffect(idEcole,"Cinquième","NON_AFFECTE");
        n5NCLA= getNbreClasse(idEcole,"Cinquième");

        n4espF= getNbrePedagLang(idEcole,"Quatrième","FEMININ","ESP");
        n4espG= getNbrePedagLang(idEcole,"Quatrième","MASCULIN","ESP");
        n4espAF= getNbreAffectLang(idEcole,"Quatrième","AFFECTE","ESP");
        n4espNAF= getNbreAffectLang(idEcole,"Quatrième","NON_AFFECTE","ESP");
        n4espNCLA = getNbreClasseLang(idEcole,"Quatrième","ESP");

        n4allF= getNbrePedagLang(idEcole,"Quatrième","FEMININ","ALL");
        n4allG= getNbrePedagLang(idEcole,"Quatrième","MASCULIN","ALL");
        n4allAF= getNbreAffectLang(idEcole,"Quatrième","AFFECTE","ALL");
        n4allNAF= getNbreAffectLang(idEcole,"Quatrième","NON_AFFECTE","ALL");
        n4allNCLA = getNbreClasseLang(idEcole,"Quatrième","ALL");


        n3espF= getNbrePedagLang(idEcole,"Troisième","FEMININ","ESP");
        n3espG= getNbrePedagLang(idEcole,"Troisième","MASCULIN","ESP");
        n3espAF= getNbreAffectLang(idEcole,"Troisième","AFFECTE","ESP");
        n3espNAF= getNbreAffectLang(idEcole,"Troisième","NON_AFFECTE","ESP");
        n3espNCLA = getNbreClasseLang(idEcole,"Troisième","ESP");

        n3allF= getNbrePedagLang(idEcole,"Troisième","FEMININ","ALL");
        n3allG= getNbrePedagLang(idEcole,"Troisième","MASCULIN","ALL");
        n3allAF= getNbreAffectLang(idEcole,"Troisième","AFFECTE","ALL");
        n3allNAF= getNbreAffectLang(idEcole,"Troisième","NON_AFFECTE","ALL");
        n3allNCLA = getNbreClasseLang(idEcole,"Troisième","ALL");

        n2ndAespF= getNbrePedagLang(idEcole,"Seconde A","FEMININ","ESP");
        n2ndAespG= getNbrePedagLang(idEcole,"Seconde A","MASCULIN","ESP");
        n2ndAespAF= getNbreAffectLang(idEcole,"Seconde A","AFFECTE","ESP");
        n2ndAespNAF= getNbreAffectLang(idEcole,"Seconde A","NON_AFFECTE","ESP");
        n2ndAespNCLA = getNbreClasseLang(idEcole,"Seconde A","ESP");

        n2ndAallF= getNbrePedagLang(idEcole,"Seconde A","FEMININ","ALL");
        n2ndAallG= getNbrePedagLang(idEcole,"Seconde A","MASCULIN","ALL");
        n2ndAallAF= getNbreAffectLang(idEcole,"Seconde A","AFFECTE","ALL");
        n2ndAallNAF= getNbreAffectLang(idEcole,"Seconde A","NON_AFFECTE","ALL");
        n2ndAallNCLA = getNbreClasseLang(idEcole,"Seconde A","ALL");

        n2ndCespF= getNbrePedagLang(idEcole,"Seconde C","FEMININ","ESP");
        n2ndCespG= getNbrePedagLang(idEcole,"Seconde C","MASCULIN","ESP");
        n2ndCespAF= getNbreAffectLang(idEcole,"Seconde C","AFFECTE","ESP");
        n2ndCespNAF= getNbreAffectLang(idEcole,"Seconde C","NON_AFFECTE","ESP");
        n2ndCespNCLA = getNbreClasseLang(idEcole,"Seconde C","ESP");

        n2ndCallF= getNbrePedagLang(idEcole,"Seconde C","FEMININ","ALL");
        n2ndCallG= getNbrePedagLang(idEcole,"Seconde C","MASCULIN","ALL");
        n2ndCallAF= getNbreAffectLang(idEcole,"Seconde C","AFFECTE","ALL");
        n2ndCallNAF= getNbreAffectLang(idEcole,"Seconde C","NON_AFFECTE","ALL");
        n2ndCallNCLA = getNbreClasseLang(idEcole,"Seconde C","ALL");

        n1ereAespF= getNbrePedagLang(idEcole,"Première A","FEMININ","ESP");
        n1ereAespG= getNbrePedagLang(idEcole,"Première A","MASCULIN","ESP");
        n1ereAespAF= getNbreAffectLang(idEcole,"Première A","AFFECTE","ESP");
        n1ereAespNAF= getNbreAffectLang(idEcole,"Première A","NON_AFFECTE","ESP");
        n1ereAespNCLA = getNbreClasseLang(idEcole,"Première A","ESP");

        n1ereAallF= getNbrePedagLang(idEcole,"Première A","FEMININ","ALL");
        n1ereAallG= getNbrePedagLang(idEcole,"Première A","MASCULIN","ALL");
        n1ereAallAF= getNbreAffectLang(idEcole,"Première A","AFFECTE","ALL");
        n1ereAallNAF= getNbreAffectLang(idEcole,"Première A","NON_AFFECTE","ALL");
        n1ereAallNCLA = getNbreClasseLang(idEcole,"Première A","ALL");

        n1ereCespF= getNbrePedagLang(idEcole,"Première C","FEMININ","ESP");
        n1ereCespG= getNbrePedagLang(idEcole,"Première C","MASCULIN","ESP");
        n1ereCespAF= getNbreAffectLang(idEcole,"Première C","AFFECTE","ESP");
        n1ereCespNAF= getNbreAffectLang(idEcole,"Première C","NON_AFFECTE","ESP");
        n1ereCespNCLA = getNbreClasseLang(idEcole,"Première C","ESP");

        n1ereCallF= getNbrePedagLang(idEcole,"Première C","FEMININ","ALL");
        n1ereCallG= getNbrePedagLang(idEcole,"Première C","MASCULIN","ALL");
        n1ereCallAF= getNbreAffectLang(idEcole,"Première C","AFFECTE","ALL");
        n1ereCallNAF= getNbreAffectLang(idEcole,"Première C","NON_AFFECTE","ALL");
        n1ereCallNCLA = getNbreClasseLang(idEcole,"Première C","ALL");

        n1ereDespF= getNbrePedagLang(idEcole,"Première D","FEMININ","ESP");
        n1ereDespG= getNbrePedagLang(idEcole,"Première D","MASCULIN","ESP");
        n1ereDespAF= getNbreAffectLang(idEcole,"Première D","AFFECTE","ESP");
        n1ereDespNAF= getNbreAffectLang(idEcole,"Première D","NON_AFFECTE","ESP");
        n1ereDespNCLA = getNbreClasseLang(idEcole,"Première D","ESP");

        n1ereDallF= getNbrePedagLang(idEcole,"Première D","FEMININ","ALL");
        n1ereDallG= getNbrePedagLang(idEcole,"Première D","MASCULIN","ALL");
        n1ereDallAF= getNbreAffectLang(idEcole,"Première D","AFFECTE","ALL");
        n1ereDallNAF= getNbreAffectLang(idEcole,"Première D","NON_AFFECTE","ALL");
        n1ereDallNCLA = getNbreClasseLang(idEcole,"Première D","ALL");

        nTleAespF= getNbrePedagLang(idEcole,"Terminale A","FEMININ","ESP");
        nTleAespG= getNbrePedagLang(idEcole,"Terminale A","MASCULIN","ESP");
        nTleAespAF= getNbreAffectLang(idEcole,"Terminale A","AFFECTE","ESP");
        nTleAespNAF= getNbreAffectLang(idEcole,"Terminale A","NON_AFFECTE","ESP");
        nTleAespNCLA = getNbreClasseLang(idEcole,"Terminale A","ESP");

        nTleAallF= getNbrePedagLang(idEcole,"Terminale A","FEMININ","ALL");
        nTleAallG= getNbrePedagLang(idEcole,"Terminale A","MASCULIN","ALL");
        nTleAallAF= getNbreAffectLang(idEcole,"Terminale A","AFFECTE","ALL");
        nTleAallNAF= getNbreAffectLang(idEcole,"Terminale A","NON_AFFECTE","ALL");
        nTleAallNCLA = getNbreClasseLang(idEcole,"Terminale A","ALL");

        nTleCespF= getNbrePedagLang(idEcole,"Terminale C","FEMININ","ESP");
        nTleCespG= getNbrePedagLang(idEcole,"Terminale C","MASCULIN","ESP");
        nTleCespAF= getNbreAffectLang(idEcole,"Terminale C","AFFECTE","ESP");
        nTleCespNAF= getNbreAffectLang(idEcole,"Terminale C","NON_AFFECTE","ESP");
        nTleCespNCLA = getNbreClasseLang(idEcole,"Terminale C","ESP");

        nTleCallF = getNbrePedagLang(idEcole,"Terminale C","FEMININ","ALL");
        nTleCallG = getNbrePedagLang(idEcole,"Terminale C","MASCULIN","ALL");
        nTleCallAF = getNbreAffectLang(idEcole,"Terminale C","AFFECTE","ALL");
        nTleCallNAF = getNbreAffectLang(idEcole,"Terminale C","NON_AFFECTE","ALL");
        nTleCallNCLA = getNbreClasseLang(idEcole,"Terminale C","ALL");

        nTleDespF = getNbrePedagLang(idEcole,"Terminale D","FEMININ","ESP");
        nTleDespG= getNbrePedagLang(idEcole,"Terminale D","MASCULIN","ESP");
        nTleDespAF= getNbreAffectLang(idEcole,"Terminale D","AFFECTE","ESP");
        nTleDespNAF= getNbreAffectLang(idEcole,"Terminale D","NON_AFFECTE","ESP");
        nTleDespNCLA = getNbreClasseLang(idEcole,"Terminale D","ESP");

        nTleDallF = getNbrePedagLang(idEcole,"Terminale D","FEMININ","ALL");
        nTleDallG= getNbrePedagLang(idEcole,"Terminale D","MASCULIN","ALL");
        nTleDallAF= getNbreAffectLang(idEcole,"Terminale D","AFFECTE","ALL");
        nTleDallNAF= getNbreAffectLang(idEcole,"Terminale D","NON_AFFECTE","ALL");
        nTleDallNCLA = getNbreClasseLang(idEcole,"Terminale D","ALL");

        resultatsListEleves.setnTleDallF(nTleDallF);
        resultatsListEleves.setnTleDallG(nTleDallG);
        resultatsListEleves.setnTleDallAF(nTleDallAF);
        resultatsListEleves.setnTleDallNAF(nTleDallNAF);
        resultatsListEleves.setnTleDallNCLA(nTleDallNCLA);


        resultatsListEleves.setnTleDespF(nTleDespF);
        resultatsListEleves.setnTleDespG(nTleDespG);
        resultatsListEleves.setnTleDespAF(nTleDespAF);
        resultatsListEleves.setnTleDespNAF(nTleDespNAF);
        resultatsListEleves.setnTleDespNCLA(nTleDespNCLA);

        resultatsListEleves.setnTleCallF(nTleCallF);
        resultatsListEleves.setnTleCallG(nTleCallG);
        resultatsListEleves.setnTleCallAF(nTleCallAF);
        resultatsListEleves.setnTleCallNAF(nTleCallNAF);
        resultatsListEleves.setnTleCallNCLA(nTleCallNCLA);

        resultatsListEleves.setnTleCespF(nTleCespF);
        resultatsListEleves.setnTleCespG(nTleCespG);
        resultatsListEleves.setnTleCespAF(nTleCespAF);
        resultatsListEleves.setnTleCespNAF(nTleCespNAF);
        resultatsListEleves.setnTleCespNCLA(nTleCespNCLA);

        resultatsListEleves.setnTleAallF(nTleAallF);
        resultatsListEleves.setnTleAallG(nTleAallG);
        resultatsListEleves.setnTleAallAF(nTleAallAF);
        resultatsListEleves.setnTleAallNAF(nTleAallNAF);
        resultatsListEleves.setnTleAallNCLA(nTleAallNCLA);



        resultatsListEleves.setnTleAespF(nTleAespF);
        resultatsListEleves.setnTleAespG(nTleAespG);
        resultatsListEleves.setnTleAespAF(nTleAespAF);
        resultatsListEleves.setnTleAespNAF(nTleAespNAF);
        resultatsListEleves.setnTleAespNCLA(nTleAespNCLA);



        resultatsListEleves.setN1ereDallF(n1ereDallF);
        resultatsListEleves.setN1ereDallG(n1ereDallG);
        resultatsListEleves.setN1ereDallAF(n1ereDallAF);
        resultatsListEleves.setN1ereDallNAF(n1ereDallNAF);
        resultatsListEleves.setN1ereDallNCLA(n1ereDallNCLA);


        resultatsListEleves.setN1ereDespF(n1ereDespF);
        resultatsListEleves.setN1ereDespG(n1ereDespG);
        resultatsListEleves.setN1ereDespAF(n1ereDespAF);
        resultatsListEleves.setN1ereDespNAF(n1ereDespNAF);
        resultatsListEleves.setN1ereDespNCLA(n1ereDespNCLA);



        resultatsListEleves.setN1ereCallF(n1ereCallF);
        resultatsListEleves.setN1ereCallG(n1ereCallG);
        resultatsListEleves.setN1ereCallAF(n1ereCallAF);
        resultatsListEleves.setN1ereCallNAF(n1ereCallNAF);
        resultatsListEleves.setN1ereCallNCLA(n1ereCallNCLA);

        resultatsListEleves.setN1ereCespF(n1ereCespF);
        resultatsListEleves.setN1ereCespG(n1ereCespG);
        resultatsListEleves.setN1ereCespAF(n1ereCespAF);
        resultatsListEleves.setN1ereCespNAF(n1ereCespNAF);
        resultatsListEleves.setN1ereCespNCLA(n1ereCespNCLA);

        resultatsListEleves.setN1ereAallF(n1ereAallF);
        resultatsListEleves.setN1ereAallG(n1ereAallG);
        resultatsListEleves.setN1ereAallAF(n1ereAallAF);
        resultatsListEleves.setN1ereAallNAF(n1ereAallNAF);
        resultatsListEleves.setN1ereAallNCLA(n1ereAallNCLA);


        resultatsListEleves.setN1ereAespF(n1ereAespF);
        resultatsListEleves.setN1ereAespG(n1ereAespG);
        resultatsListEleves.setN1ereAespAF(n1ereAespAF);
        resultatsListEleves.setN1ereAespNAF(n1ereAespNAF);
        resultatsListEleves.setN1ereAespNCLA(n1ereAespNCLA);

        resultatsListEleves.setN2ndCespF(n2ndCespF);
        resultatsListEleves.setN2ndCespG(n2ndCespG);
        resultatsListEleves.setN2ndCespAF(n2ndCespAF);
        resultatsListEleves.setN2ndCespNAF(n2ndCespNAF);
        resultatsListEleves.setN2ndCespNCLA(n2ndCespNCLA);

        resultatsListEleves.setN2ndCallF(n2ndCallF);
        resultatsListEleves.setN2ndCallG(n2ndCallG);
        resultatsListEleves.setN2ndCallAF(n2ndCallAF);
        resultatsListEleves.setN2ndCallNAF(n2ndCallNAF);
        resultatsListEleves.setN2ndCallNCLA(n2ndCallNCLA);


        resultatsListEleves.setN2ndAespF(n2ndAespF);
        resultatsListEleves.setN2ndAespG(n2ndAespG);
        resultatsListEleves.setN2ndAespAF(n2ndAespAF);
        resultatsListEleves.setN2ndAespNAF(n2ndAespNAF);
        resultatsListEleves.setN2ndAespNCLA(n2ndAespNCLA);

        resultatsListEleves.setN2ndAallF(n2ndAallF);
        resultatsListEleves.setN2ndAallG(n2ndAallG);
        resultatsListEleves.setN2ndAallAF(n2ndAallAF);
        resultatsListEleves.setN2ndAallNAF(n2ndAallNAF);
        resultatsListEleves.setN2ndAallNCLA(n2ndAallNCLA);






        resultatsListEleves.setN6AF(n6AF);
        resultatsListEleves.setN6G(n6G);
        resultatsListEleves.setN6F(n6F);
        resultatsListEleves.setN6NAF(n6NAF);
        resultatsListEleves.setN6NCLA(n6NCLA);

        resultatsListEleves.setN5AF(n5AF);
        resultatsListEleves.setN5G(n5G);
        resultatsListEleves.setN5F(n5F);
        resultatsListEleves.setN5NAF(n5NAF);
        resultatsListEleves.setN5NCLA(n5NCLA);

        resultatsListEleves.setN4allAF(n4allAF);
        resultatsListEleves.setN4allF(n4allF);
        resultatsListEleves.setN4allG(n4allG);
        resultatsListEleves.setN4allNAF(n4allNAF);
        resultatsListEleves.setN4allNCLA(n4allNCLA);

        resultatsListEleves.setN3allAF(n3allAF);
        resultatsListEleves.setN3allF(n3allF);
        resultatsListEleves.setN3allG(n3allG);
        resultatsListEleves.setN3allNAF(n3allNAF);
        resultatsListEleves.setN3allNCLA(n3allNCLA);

        resultatsListEleves.setN4espAF(n4espAF);
        resultatsListEleves.setN4espF(n4espF);
        resultatsListEleves.setN4espG(n4espG);
        resultatsListEleves.setN4espNAF(n4espNAF);
        resultatsListEleves.setN4espNCLA(n4espNCLA);

        resultatsListEleves.setN3espAF(n3espAF);
        resultatsListEleves.setN3espF(n3espF);
        resultatsListEleves.setN3espG(n3espG);
        resultatsListEleves.setN3espNAF(n3espNAF);
        resultatsListEleves.setN3espNCLA(n3espNCLA);

        return  resultatsListEleves ;
    }


    public Long getNbrePedag(Long idEcole , String niveau,String sexe){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe",sexe)
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



    public Long getNbrePedagLang(Long idEcole , String niveau,String sexe,String lv2){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and  o.lv2=:lv2 group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe",sexe)
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("lv2",lv2)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }


    public Long getNbreAffect(Long idEcole , String niveau, String affecte){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte  group by  o.niveau having o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("affecte",affecte)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getNbreAffectLang(Long idEcole , String niveau, String affecte,String lv2){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte and  o.lv2=:lv2 group by  o.niveau having o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("affecte",affecte)
                    .setParameter("lv2",lv2)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



    public Long getNbreClasse(Long idEcole , String niveau){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getNbreClasseLang(Long idEcole , String niveau ,String lv2){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.lv2=:lv2  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("lv2",lv2)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }






}
