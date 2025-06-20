package com.vieecoles.services.etats;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.EffApprocheNiveauGenreDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapitulatifClassePedagoAffectNonAffect;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ApprocheParNiveauParGenreServices {
    @Inject
    EntityManager em;

    public EffApprocheNiveauGenreDto EffApprocheNiveauGenre(Long idEcole ,String libelleAnnee , String libelleTrimestre){
        int LongTableau;
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee  " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                              . getResultList() ;

        LongTableau = classeNiveauDtoList.size();

        Long    n6,n6RF, n6NRF ,n6RG, n6NRG,
                n5,n5RF, n5NRF ,n5RG, n5NRG,
                n4,n4RF, n4NRF ,n4RG, n4NRG,
                n3,n3RF, n3NRF ,n3RG, n3NRG,

                n2ndA,n2ndARF, n2ndANRF ,n2ndARG, n2ndANRG,
                n2ndC,n2ndCRF, n2ndCNRF ,n2ndCRG, n2ndCNRG,

                n1ereA,n1ereARF, n1ereANRF ,n1ereARG, n1ereANRG,
                n1ereD,n1ereDRF, n1ereDNRF ,n1ereDRG, n1ereDNRG,
                n1ereC,n1ereCRF, n1ereCNRF ,n1ereCRG, n1ereCNRG,

                nTleA1,nTleA1RF, nTleA1NRF ,nTleA1RG, nTleA1NRG,
                nTleA2,nTleA2RF, nTleA2NRF ,nTleA2RG, nTleA2NRG,
                nTleD,nTleDRF, nTleDNRF ,nTleDRG, nTleDNRG,
                nTleC,nTleCRF, nTleCNRF ,nTleCRG, nTleCNRG;

        String niveau ;



        EffApprocheNiveauGenreDto resultatsListEleves= new EffApprocheNiveauGenreDto();

        n6 = getBase(idEcole,"Sixième" ,libelleAnnee , libelleTrimestre) ;
        n6RF= getNbreParGenreParNiveau(idEcole,"Sixième","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n6NRF= getNbreParGenreParNiveau(idEcole,"Sixième","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n6RG= getNbreParGenreParNiveau(idEcole,"Sixième","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n6NRG= getNbreParGenreParNiveau(idEcole,"Sixième","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n5 = getBase(idEcole,"Cinquième" ,libelleAnnee , libelleTrimestre) ;
        n5RF= getNbreParGenreParNiveau(idEcole,"Cinquième","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n5NRF= getNbreParGenreParNiveau(idEcole,"Cinquième","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n5RG= getNbreParGenreParNiveau(idEcole,"Cinquième","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n5NRG= getNbreParGenreParNiveau(idEcole,"Cinquième","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);


        n4 = getBase(idEcole,"Quatrième" ,libelleAnnee , libelleTrimestre) ;
        n4RF= getNbreParGenreParNiveau(idEcole,"Quatrième","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n4NRF= getNbreParGenreParNiveau(idEcole,"Quatrième","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n4RG= getNbreParGenreParNiveau(idEcole,"Quatrième","MASCULIN","OUI",libelleAnnee , libelleTrimestre);
        n4NRG= getNbreParGenreParNiveau(idEcole,"Quatrième","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n3 = getBase(idEcole,"Troisième" ,libelleAnnee , libelleTrimestre) ;
        n3RF= getNbreParGenreParNiveau(idEcole,"Troisième","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n3NRF= getNbreParGenreParNiveau(idEcole,"Troisième","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n3RG= getNbreParGenreParNiveau(idEcole,"Troisième","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n3NRG= getNbreParGenreParNiveau(idEcole,"Troisième","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n2ndA = getBase(idEcole,"Seconde A" ,libelleAnnee , libelleTrimestre) ;
        n2ndARF= getNbreParGenreParNiveau(idEcole,"Seconde A","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n2ndANRF= getNbreParGenreParNiveau(idEcole,"Seconde A","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n2ndARG= getNbreParGenreParNiveau(idEcole,"Seconde A","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n2ndANRG= getNbreParGenreParNiveau(idEcole,"Seconde A","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n2ndC = getBase(idEcole,"Seconde C" ,libelleAnnee , libelleTrimestre) ;
        n2ndCRF= getNbreParGenreParNiveau(idEcole,"Seconde C","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n2ndCNRF= getNbreParGenreParNiveau(idEcole,"Seconde C","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n2ndCRG= getNbreParGenreParNiveau(idEcole,"Seconde C","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n2ndCNRG= getNbreParGenreParNiveau(idEcole,"Seconde C","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n1ereA = getBase(idEcole,"Première A" ,libelleAnnee , libelleTrimestre) ;
        n1ereARF= getNbreParGenreParNiveau(idEcole,"Première A","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n1ereANRF= getNbreParGenreParNiveau(idEcole,"Première A","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n1ereARG= getNbreParGenreParNiveau(idEcole,"Première A","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n1ereANRG= getNbreParGenreParNiveau(idEcole,"Première A","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n1ereC = getBase(idEcole,"Première C" ,libelleAnnee , libelleTrimestre) ;
        n1ereCRF= getNbreParGenreParNiveau(idEcole,"Première C","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n1ereCNRF= getNbreParGenreParNiveau(idEcole,"Première C","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n1ereCRG= getNbreParGenreParNiveau(idEcole,"Première C","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n1ereCNRG= getNbreParGenreParNiveau(idEcole,"Première C","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        n1ereD = getBase(idEcole,"Première D" ,libelleAnnee , libelleTrimestre) ;
        n1ereDRF= getNbreParGenreParNiveau(idEcole,"Première D","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        n1ereDNRF= getNbreParGenreParNiveau(idEcole,"Première D","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        n1ereDRG= getNbreParGenreParNiveau(idEcole,"Première D","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        n1ereDNRG= getNbreParGenreParNiveau(idEcole,"Première D","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        nTleA1 = getBase(idEcole,"Terminale A1" ,libelleAnnee , libelleTrimestre) ;
        nTleA1RF= getNbreParGenreParNiveau(idEcole,"Terminale A1","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        nTleA1NRF= getNbreParGenreParNiveau(idEcole,"Terminale A1","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        nTleA1RG= getNbreParGenreParNiveau(idEcole,"Terminale A1","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        nTleA1NRG= getNbreParGenreParNiveau(idEcole,"Terminale A1","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        nTleA2 = getBase(idEcole,"Terminale A2" ,libelleAnnee , libelleTrimestre) ;
        nTleA2RF= getNbreParGenreParNiveau(idEcole,"Terminale A2","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        nTleA2NRF= getNbreParGenreParNiveau(idEcole,"Terminale A2","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        nTleA2RG= getNbreParGenreParNiveau(idEcole,"Terminale A2","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        nTleA2NRG= getNbreParGenreParNiveau(idEcole,"Terminale A2","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        nTleC = getBase(idEcole,"Terminale C" ,libelleAnnee , libelleTrimestre) ;
        nTleCRF= getNbreParGenreParNiveau(idEcole,"Terminale C","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        nTleCNRF= getNbreParGenreParNiveau(idEcole,"Terminale C","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        nTleCRG= getNbreParGenreParNiveau(idEcole,"Terminale C","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        nTleCNRG= getNbreParGenreParNiveau(idEcole,"Terminale C","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        nTleD = getBase(idEcole,"Terminale D" ,libelleAnnee , libelleTrimestre) ;
        nTleDRF= getNbreParGenreParNiveau(idEcole,"Terminale D","FEMININ","OUI" ,libelleAnnee , libelleTrimestre);
        nTleDNRF= getNbreParGenreParNiveau(idEcole,"Terminale D","FEMININ","NON" ,libelleAnnee , libelleTrimestre);
        nTleDRG= getNbreParGenreParNiveau(idEcole,"Terminale D","MASCULIN","OUI" ,libelleAnnee , libelleTrimestre);
        nTleDNRG= getNbreParGenreParNiveau(idEcole,"Terminale D","MASCULIN","NON" ,libelleAnnee , libelleTrimestre);

        resultatsListEleves.setnTleD(nTleD);
        resultatsListEleves.setnTleDRF(nTleDRF);
        resultatsListEleves.setnTleDNRF(nTleDNRF);
        resultatsListEleves.setnTleDRG(nTleDRG);
        resultatsListEleves.setnTleDNRG(nTleDNRG);


        resultatsListEleves.setnTleC(nTleC);
        resultatsListEleves.setnTleCRF(nTleCRF);
        resultatsListEleves.setnTleCNRF(nTleCNRF);
        resultatsListEleves.setnTleCRG(nTleCRG);
        resultatsListEleves.setnTlCNRG(nTleCNRG);




        resultatsListEleves.setnTleA2(nTleA2);
        resultatsListEleves.setnTleA2RF(nTleA2RF);
        resultatsListEleves.setnTleA2NRF(nTleA2NRF);
        resultatsListEleves.setnTleA2RG(nTleA2RG);
        resultatsListEleves.setnTleA2NRG(nTleA2NRG);

        resultatsListEleves.setnTleA1(nTleA1);
        resultatsListEleves.setnTleA1RF(nTleA1RF);
        resultatsListEleves.setnTleA1NRF(nTleA1NRF);
        resultatsListEleves.setnTleA1RG(nTleA1RG);
        resultatsListEleves.setnTleA1NRG(nTleA1NRG);


        resultatsListEleves.setN1ereD(n1ereD);
        resultatsListEleves.setN1ereDRF(n1ereDRF);
        resultatsListEleves.setN1ereDNRF(n1ereDNRF);
        resultatsListEleves.setN1ereDRG(n1ereDRG);
        resultatsListEleves.setN1ereDNRG(n1ereDNRG);

        resultatsListEleves.setN1ereC(n1ereC);
        resultatsListEleves.setN1ereCRF(n1ereCRF);
        resultatsListEleves.setN1ereCNRF(n1ereCNRF);
        resultatsListEleves.setN1ereCRG(n1ereCRG);
        resultatsListEleves.setN1ereCNRG(n1ereCNRG);

        resultatsListEleves.setN1ereA(n1ereA);
        resultatsListEleves.setN1ereARF(n1ereARF);
        resultatsListEleves.setN1ereANRF(n1ereANRF);
        resultatsListEleves.setN1ereARG(n1ereARG);
        resultatsListEleves.setN1ereANRG(n1ereANRG);

        resultatsListEleves.setN2ndC(n2ndC);
        resultatsListEleves.setN2ndCRF(n2ndCRF);
        resultatsListEleves.setN2ndCNRF(n2ndCNRF);
        resultatsListEleves.setN2ndCRG(n2ndCRG);
        resultatsListEleves.setN2ndCNRG(n2ndCNRG);

        resultatsListEleves.setN2ndA(n2ndA);
        resultatsListEleves.setN2ndARF(n2ndARF);
        resultatsListEleves.setN2ndANRF(n2ndANRF);
        resultatsListEleves.setN2ndARG(n2ndARG);
        resultatsListEleves.setN2ndANRG(n2ndANRG);

        resultatsListEleves.setN3(n3);
        resultatsListEleves.setN3RF(n3RF);
        resultatsListEleves.setN3NRF(n3NRF);
        resultatsListEleves.setN3RG(n3RG);
        resultatsListEleves.setN3NRG(n3NRG);


        resultatsListEleves.setN4(n4);
        resultatsListEleves.setN4RF(n4RF);
        resultatsListEleves.setN4NRF(n4NRF);
        resultatsListEleves.setN4RG(n4RG);
        resultatsListEleves.setN4NRG(n4NRG);

        resultatsListEleves.setN5(n5);
        resultatsListEleves.setN5RF(n5RF);
        resultatsListEleves.setN5NRF(n5NRF);
        resultatsListEleves.setN5RG(n5RG);
        resultatsListEleves.setN5NRG(n5NRG);

        resultatsListEleves.setN6(n6);
        resultatsListEleves.setN6RF(n6RF);
        resultatsListEleves.setN6NRF(n6NRF);
        resultatsListEleves.setN6RG(n6RG);
        resultatsListEleves.setN6NRG(n6NRG);






        return  resultatsListEleves ;
    }

    public Long getBase(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        Long classG;

        List<ClasseNiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        try {

            TypedQuery<ClasseNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.ClasseNiveauDto(b.libelleClasse ,b.niveau) from Bulletin b  where b.ecoleId =:idEcole and  b.niveau=:niveau  and b.libellePeriode=:periode and b.anneeLibelle=:annee" +
                    " group by b.libelleClasse ,b.niveau ", ClasseNiveauDto.class);
            classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    . getResultList() ;
            classG= (long) classeNiveauDtoList.size();
        }   catch (NoResultException e){
            classG=0L;
        }
        return  classG ;

    }

    public Long getNbreParGenreParNiveau(Long idEcole , String niveau,String sexe,String redoublant ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.redoublant=:redoublant and  o.niveau=:niveau and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau ")
                    .setParameter("sexe",sexe)
                    .setParameter("redoublant",redoublant)
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









}
