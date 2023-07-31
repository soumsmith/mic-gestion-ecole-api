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

    public RecapitulatifClassePedagoAffectNonAffect RecapClassePedagoAffecNonAffect(Long idEcole ,String libelleAnnee , String libelleTrimestre){
        int LongTableau;
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
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

        n6F= getNbrePedag(idEcole,"Sixième","FEMININ" ,libelleAnnee , libelleTrimestre) ;
        n6G= getNbrePedag(idEcole,"Sixième","MASCULIN" ,libelleAnnee , libelleTrimestre) ;
        n6AF= getNbreAffect(idEcole,"Sixième","AFFECTE" ,libelleAnnee , libelleTrimestre);
        n6NAF= getNbreAffect(idEcole,"Sixième","NON_AFFECTE" ,libelleAnnee , libelleTrimestre);
        n6NCLA= getNbreClasse(idEcole,"Sixième" ,libelleAnnee , libelleTrimestre);

        n5F= getNbrePedag(idEcole,"Cinquième","FEMININ" ,libelleAnnee , libelleTrimestre) ;
        n5G= getNbrePedag(idEcole,"Cinquième","MASCULIN" ,libelleAnnee , libelleTrimestre) ;
        n5AF= getNbreAffect(idEcole,"Cinquième","AFFECTE" ,libelleAnnee , libelleTrimestre);
        n5NAF= getNbreAffect(idEcole,"Cinquième","NON_AFFECTE" ,libelleAnnee , libelleTrimestre);
        n5NCLA= getNbreClasse(idEcole,"Cinquième" ,libelleAnnee , libelleTrimestre);

        n4espF= getNbrePedagLang(idEcole,"Quatrième","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        n4espG= getNbrePedagLang(idEcole,"Quatrième","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        n4espAF= getNbreAffectLang(idEcole,"Quatrième","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n4espNAF= getNbreAffectLang(idEcole,"Quatrième","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n4espNCLA = getNbreClasseLang(idEcole,"Quatrième","ESP" ,libelleAnnee , libelleTrimestre);

        n4allF= getNbrePedagLang(idEcole,"Quatrième","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        n4allG= getNbrePedagLang(idEcole,"Quatrième","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        n4allAF= getNbreAffectLang(idEcole,"Quatrième","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n4allNAF= getNbreAffectLang(idEcole,"Quatrième","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n4allNCLA = getNbreClasseLang(idEcole,"Quatrième","ALL" ,libelleAnnee , libelleTrimestre);


        n3espF= getNbrePedagLang(idEcole,"Troisième","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        n3espG= getNbrePedagLang(idEcole,"Troisième","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        n3espAF= getNbreAffectLang(idEcole,"Troisième","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n3espNAF= getNbreAffectLang(idEcole,"Troisième","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n3espNCLA = getNbreClasseLang(idEcole,"Troisième","ESP" ,libelleAnnee , libelleTrimestre);

        n3allF= getNbrePedagLang(idEcole,"Troisième","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        n3allG= getNbrePedagLang(idEcole,"Troisième","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        n3allAF= getNbreAffectLang(idEcole,"Troisième","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n3allNAF= getNbreAffectLang(idEcole,"Troisième","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n3allNCLA = getNbreClasseLang(idEcole,"Troisième","ALL" ,libelleAnnee , libelleTrimestre);

        n2ndAespF= getNbrePedagLang(idEcole,"Seconde A","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndAespG= getNbrePedagLang(idEcole,"Seconde A","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndAespAF= getNbreAffectLang(idEcole,"Seconde A","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndAespNAF= getNbreAffectLang(idEcole,"Seconde A","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndAespNCLA = getNbreClasseLang(idEcole,"Seconde A","ESP" ,libelleAnnee , libelleTrimestre);

        n2ndAallF= getNbrePedagLang(idEcole,"Seconde A","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndAallG= getNbrePedagLang(idEcole,"Seconde A","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndAallAF= getNbreAffectLang(idEcole,"Seconde A","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndAallNAF= getNbreAffectLang(idEcole,"Seconde A","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndAallNCLA = getNbreClasseLang(idEcole,"Seconde A","ALL" ,libelleAnnee , libelleTrimestre);

        n2ndCespF= getNbrePedagLang(idEcole,"Seconde C","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndCespG= getNbrePedagLang(idEcole,"Seconde C","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndCespAF= getNbreAffectLang(idEcole,"Seconde C","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndCespNAF= getNbreAffectLang(idEcole,"Seconde C","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n2ndCespNCLA = getNbreClasseLang(idEcole,"Seconde C","ESP" ,libelleAnnee , libelleTrimestre);

        n2ndCallF= getNbrePedagLang(idEcole,"Seconde C","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndCallG= getNbrePedagLang(idEcole,"Seconde C","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndCallAF= getNbreAffectLang(idEcole,"Seconde C","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndCallNAF= getNbreAffectLang(idEcole,"Seconde C","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n2ndCallNCLA = getNbreClasseLang(idEcole,"Seconde C","ALL" ,libelleAnnee , libelleTrimestre);

        n1ereAespF= getNbrePedagLang(idEcole,"Première A","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereAespG= getNbrePedagLang(idEcole,"Première A","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereAespAF= getNbreAffectLang(idEcole,"Première A","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereAespNAF= getNbreAffectLang(idEcole,"Première A","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereAespNCLA = getNbreClasseLang(idEcole,"Première A","ESP" ,libelleAnnee , libelleTrimestre);

        n1ereAallF= getNbrePedagLang(idEcole,"Première A","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        n1ereAallG= getNbrePedagLang(idEcole,"Première A","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        n1ereAallAF= getNbreAffectLang(idEcole,"Première A","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n1ereAallNAF= getNbreAffectLang(idEcole,"Première A","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        n1ereAallNCLA = getNbreClasseLang(idEcole,"Première A","ALL" ,libelleAnnee , libelleTrimestre);

        n1ereCespF= getNbrePedagLang(idEcole,"Première C","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereCespG= getNbrePedagLang(idEcole,"Première C","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereCespAF= getNbreAffectLang(idEcole,"Première C","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereCespNAF= getNbreAffectLang(idEcole,"Première C","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        n1ereCespNCLA = getNbreClasseLang(idEcole,"Première C","ESP"  ,libelleAnnee , libelleTrimestre);

        n1ereCallF= getNbrePedagLang(idEcole,"Première C","FEMININ","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereCallG= getNbrePedagLang(idEcole,"Première C","MASCULIN","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereCallAF= getNbreAffectLang(idEcole,"Première C","AFFECTE","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereCallNAF= getNbreAffectLang(idEcole,"Première C","NON_AFFECTE","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereCallNCLA = getNbreClasseLang(idEcole,"Première C","ALL"  ,libelleAnnee , libelleTrimestre);

        n1ereDespF= getNbrePedagLang(idEcole,"Première D","FEMININ","ESP"  ,libelleAnnee , libelleTrimestre);
        n1ereDespG= getNbrePedagLang(idEcole,"Première D","MASCULIN","ESP"  ,libelleAnnee , libelleTrimestre);
        n1ereDespAF= getNbreAffectLang(idEcole,"Première D","AFFECTE","ESP"  ,libelleAnnee , libelleTrimestre);
        n1ereDespNAF= getNbreAffectLang(idEcole,"Première D","NON_AFFECTE","ESP"  ,libelleAnnee , libelleTrimestre);
        n1ereDespNCLA = getNbreClasseLang(idEcole,"Première D","ESP"  ,libelleAnnee , libelleTrimestre);

        n1ereDallF= getNbrePedagLang(idEcole,"Première D","FEMININ","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereDallG= getNbrePedagLang(idEcole,"Première D","MASCULIN","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereDallAF= getNbreAffectLang(idEcole,"Première D","AFFECTE","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereDallNAF= getNbreAffectLang(idEcole,"Première D","NON_AFFECTE","ALL"  ,libelleAnnee , libelleTrimestre);
        n1ereDallNCLA = getNbreClasseLang(idEcole,"Première D","ALL"  ,libelleAnnee , libelleTrimestre);

        nTleAespF= getNbrePedagLang(idEcole,"Terminale A","FEMININ","ESP"  ,libelleAnnee , libelleTrimestre);
        nTleAespG= getNbrePedagLang(idEcole,"Terminale A","MASCULIN","ESP"  ,libelleAnnee , libelleTrimestre);
        nTleAespAF= getNbreAffectLang(idEcole,"Terminale A","AFFECTE","ESP"  ,libelleAnnee , libelleTrimestre);
        nTleAespNAF= getNbreAffectLang(idEcole,"Terminale A","NON_AFFECTE","ESP"  ,libelleAnnee , libelleTrimestre);
        nTleAespNCLA = getNbreClasseLang(idEcole,"Terminale A","ESP"  ,libelleAnnee , libelleTrimestre);

        nTleAallF= getNbrePedagLang(idEcole,"Terminale A","FEMININ","ALL"  ,libelleAnnee , libelleTrimestre);
        nTleAallG= getNbrePedagLang(idEcole,"Terminale A","MASCULIN","ALL"  ,libelleAnnee , libelleTrimestre);
        nTleAallAF= getNbreAffectLang(idEcole,"Terminale A","AFFECTE","ALL"  ,libelleAnnee , libelleTrimestre);
        nTleAallNAF= getNbreAffectLang(idEcole,"Terminale A","NON_AFFECTE","ALL"  ,libelleAnnee , libelleTrimestre);
        nTleAallNCLA = getNbreClasseLang(idEcole,"Terminale A","ALL"  ,libelleAnnee , libelleTrimestre);

        nTleCespF= getNbrePedagLang(idEcole,"Terminale C","FEMININ","ESP"  ,libelleAnnee , libelleTrimestre);
        nTleCespG= getNbrePedagLang(idEcole,"Terminale C","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        nTleCespAF= getNbreAffectLang(idEcole,"Terminale C","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        nTleCespNAF= getNbreAffectLang(idEcole,"Terminale C","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        nTleCespNCLA = getNbreClasseLang(idEcole,"Terminale C","ESP" ,libelleAnnee , libelleTrimestre);

        nTleCallF = getNbrePedagLang(idEcole,"Terminale C","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        nTleCallG = getNbrePedagLang(idEcole,"Terminale C","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        nTleCallAF = getNbreAffectLang(idEcole,"Terminale C","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        nTleCallNAF = getNbreAffectLang(idEcole,"Terminale C","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        nTleCallNCLA = getNbreClasseLang(idEcole,"Terminale C","ALL" ,libelleAnnee , libelleTrimestre);

        nTleDespF = getNbrePedagLang(idEcole,"Terminale D","FEMININ","ESP" ,libelleAnnee , libelleTrimestre);
        nTleDespG= getNbrePedagLang(idEcole,"Terminale D","MASCULIN","ESP" ,libelleAnnee , libelleTrimestre);
        nTleDespAF= getNbreAffectLang(idEcole,"Terminale D","AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        nTleDespNAF= getNbreAffectLang(idEcole,"Terminale D","NON_AFFECTE","ESP" ,libelleAnnee , libelleTrimestre);
        nTleDespNCLA = getNbreClasseLang(idEcole,"Terminale D","ESP" ,libelleAnnee , libelleTrimestre);

        nTleDallF = getNbrePedagLang(idEcole,"Terminale D","FEMININ","ALL" ,libelleAnnee , libelleTrimestre);
        nTleDallG= getNbrePedagLang(idEcole,"Terminale D","MASCULIN","ALL" ,libelleAnnee , libelleTrimestre);
        nTleDallAF= getNbreAffectLang(idEcole,"Terminale D","AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        nTleDallNAF= getNbreAffectLang(idEcole,"Terminale D","NON_AFFECTE","ALL" ,libelleAnnee , libelleTrimestre);
        nTleDallNCLA = getNbreClasseLang(idEcole,"Terminale D","ALL" ,libelleAnnee , libelleTrimestre);

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


    public Long getNbrePedag(Long idEcole , String niveau,String sexe ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.libellePeriode=:periode and o.anneeLibelle=:annee   group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe",sexe)
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



    public Long getNbrePedagLang(Long idEcole , String niveau,String sexe,String lv2 ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and  o.lv2=:lv2 and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe",sexe)
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("lv2",lv2)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }


    public Long getNbreAffect(Long idEcole , String niveau, String affecte ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("affecte",affecte)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getNbreAffectLang(Long idEcole , String niveau, String affecte,String lv2 ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte and  o.lv2=:lv2 and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("affecte",affecte)
                    .setParameter("lv2",lv2)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



    public Long getNbreClasse(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getNbreClasseLang(Long idEcole , String niveau ,String lv2 ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.ecoleId=:idEcole and o.lv2=:lv2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("lv2",lv2)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }






}
