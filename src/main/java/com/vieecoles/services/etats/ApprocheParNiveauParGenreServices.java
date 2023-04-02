package com.vieecoles.services.etats;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.EffApprocheNiveauGenreDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapitulatifClassePedagoAffectNonAffect;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ApprocheParNiveauParGenreServices {
    @Inject
    EntityManager em;

    public EffApprocheNiveauGenreDto EffApprocheNiveauGenre(Long idEcole){
        int LongTableau;
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole  " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
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

        n6 = getBase(idEcole,"Sixième") ;
        n6RF= getNbreParGenreParNiveau(idEcole,"Sixième","FEMININ","RED");
        n6NRF= getNbreParGenreParNiveau(idEcole,"Sixième","FEMININ","NRED");
        n6RG= getNbreParGenreParNiveau(idEcole,"Sixième","MASCULIN","RED");
        n6NRG= getNbreParGenreParNiveau(idEcole,"Sixième","MASCULIN","NRED");

        n5 = getBase(idEcole,"Cinquième") ;
        n5RF= getNbreParGenreParNiveau(idEcole,"Cinquième","FEMININ","RED");
        n5NRF= getNbreParGenreParNiveau(idEcole,"Cinquième","FEMININ","NRED");
        n5RG= getNbreParGenreParNiveau(idEcole,"Cinquième","MASCULIN","RED");
        n5NRG= getNbreParGenreParNiveau(idEcole,"Cinquième","MASCULIN","NRED");


        n4 = getBase(idEcole,"Quatrième") ;
        n4RF= getNbreParGenreParNiveau(idEcole,"Quatrième","FEMININ","RED");
        n4NRF= getNbreParGenreParNiveau(idEcole,"Quatrième","FEMININ","NRED");
        n4RG= getNbreParGenreParNiveau(idEcole,"Quatrième","MASCULIN","RED");
        n4NRG= getNbreParGenreParNiveau(idEcole,"Quatrième","MASCULIN","NRED");

        n3 = getBase(idEcole,"Troisième") ;
        n3RF= getNbreParGenreParNiveau(idEcole,"Troisième","FEMININ","RED");
        n3NRF= getNbreParGenreParNiveau(idEcole,"Troisième","FEMININ","NRED");
        n3RG= getNbreParGenreParNiveau(idEcole,"Troisième","MASCULIN","RED");
        n3NRG= getNbreParGenreParNiveau(idEcole,"Troisième","MASCULIN","NRED");

        n2ndA = getBase(idEcole,"Seconde A") ;
        n2ndARF= getNbreParGenreParNiveau(idEcole,"Seconde A","FEMININ","RED");
        n2ndANRF= getNbreParGenreParNiveau(idEcole,"Seconde A","FEMININ","NRED");
        n2ndARG= getNbreParGenreParNiveau(idEcole,"Seconde A","MASCULIN","RED");
        n2ndANRG= getNbreParGenreParNiveau(idEcole,"Seconde A","MASCULIN","NRED");

        n2ndC = getBase(idEcole,"Seconde C") ;
        n2ndCRF= getNbreParGenreParNiveau(idEcole,"Seconde C","FEMININ","RED");
        n2ndCNRF= getNbreParGenreParNiveau(idEcole,"Seconde C","FEMININ","NRED");
        n2ndCRG= getNbreParGenreParNiveau(idEcole,"Seconde C","MASCULIN","RED");
        n2ndCNRG= getNbreParGenreParNiveau(idEcole,"Seconde C","MASCULIN","NRED");

        n1ereA = getBase(idEcole,"Première A") ;
        n1ereARF= getNbreParGenreParNiveau(idEcole,"Première A","FEMININ","RED");
        n1ereANRF= getNbreParGenreParNiveau(idEcole,"Première A","FEMININ","NRED");
        n1ereARG= getNbreParGenreParNiveau(idEcole,"Première A","MASCULIN","RED");
        n1ereANRG= getNbreParGenreParNiveau(idEcole,"Première A","MASCULIN","NRED");

        n1ereC = getBase(idEcole,"Première C") ;
        n1ereCRF= getNbreParGenreParNiveau(idEcole,"Première C","FEMININ","RED");
        n1ereCNRF= getNbreParGenreParNiveau(idEcole,"Première C","FEMININ","NRED");
        n1ereCRG= getNbreParGenreParNiveau(idEcole,"Première C","MASCULIN","RED");
        n1ereCNRG= getNbreParGenreParNiveau(idEcole,"Première C","MASCULIN","NRED");

        n1ereD = getBase(idEcole,"Première D") ;
        n1ereDRF= getNbreParGenreParNiveau(idEcole,"Première D","FEMININ","RED");
        n1ereDNRF= getNbreParGenreParNiveau(idEcole,"Première D","FEMININ","NRED");
        n1ereDRG= getNbreParGenreParNiveau(idEcole,"Première D","MASCULIN","RED");
        n1ereDNRG= getNbreParGenreParNiveau(idEcole,"Première D","MASCULIN","NRED");

        nTleA1 = getBase(idEcole,"Terminale A1") ;
        nTleA1RF= getNbreParGenreParNiveau(idEcole,"Terminale A1","FEMININ","RED");
        nTleA1NRF= getNbreParGenreParNiveau(idEcole,"Terminale A1","FEMININ","NRED");
        nTleA1RG= getNbreParGenreParNiveau(idEcole,"Terminale A1","MASCULIN","RED");
        nTleA1NRG= getNbreParGenreParNiveau(idEcole,"Terminale A1","MASCULIN","NRED");

        nTleA2 = getBase(idEcole,"Terminale A2") ;
        nTleA2RF= getNbreParGenreParNiveau(idEcole,"Terminale A2","FEMININ","RED");
        nTleA2NRF= getNbreParGenreParNiveau(idEcole,"Terminale A2","FEMININ","NRED");
        nTleA2RG= getNbreParGenreParNiveau(idEcole,"Terminale A2","MASCULIN","RED");
        nTleA2NRG= getNbreParGenreParNiveau(idEcole,"Terminale A2","MASCULIN","NRED");

        nTleC = getBase(idEcole,"Terminale C") ;
        nTleCRF= getNbreParGenreParNiveau(idEcole,"Terminale C","FEMININ","RED");
        nTleCNRF= getNbreParGenreParNiveau(idEcole,"Terminale C","FEMININ","NRED");
        nTleCRG= getNbreParGenreParNiveau(idEcole,"Terminale C","MASCULIN","RED");
        nTleCNRG= getNbreParGenreParNiveau(idEcole,"Terminale C","MASCULIN","NRED");

        nTleD = getBase(idEcole,"Terminale D") ;
        nTleDRF= getNbreParGenreParNiveau(idEcole,"Terminale D","FEMININ","RED");
        nTleDNRF= getNbreParGenreParNiveau(idEcole,"Terminale D","FEMININ","NRED");
        nTleDRG= getNbreParGenreParNiveau(idEcole,"Terminale D","MASCULIN","RED");
        nTleDNRG= getNbreParGenreParNiveau(idEcole,"Terminale D","MASCULIN","NRED");

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

    public Long getBase(Long idEcole , String niveau){
        Long classG;

        List<ClasseNiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        try {

            TypedQuery<ClasseNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.ClasseNiveauDto(b.libelleClasse ,b.niveau) from Bulletin b  where b.ecoleId =:idEcole and  b.niveau=:niveau " +
                    "group by b.libelleClasse ,b.niveau ", ClasseNiveauDto.class);
            classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                    .setParameter("niveau",niveau)
                    . getResultList() ;
            classG= (long) classeNiveauDtoList.size();
        }   catch (NoResultException e){
            classG=0L;
        }
        return  classG ;

    }

    public Long getNbreParGenreParNiveau(Long idEcole , String niveau,String sexe,String redoublant){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.redoublant=:redoublant and  o.niveau=:niveau group by  o.niveau ")
                    .setParameter("sexe",sexe)
                    .setParameter("redoublant",redoublant)
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }









}
