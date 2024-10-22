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
public class ComparatifServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public ComparatifDto getComparatif(Long idEcole ,Long anneeId ){



       String niveau ;
         Long nombreClasse;

      ComparatifDto m= new ComparatifDto() ;

          Long nbrc6 ,nbrc5 , nbrc4, nbrc3, nbrc2nA , nbrc2nC , nbrc1A , nbrc1C ;
         Long nbrc1D , nbrcTA , nbrcTC , nbrcTD , eff6 ;
         Long eff5 , eff4 ,eff3 , eff2nA , eff2nC , eff1A , eff1C ;
        Long eff1D ,effTA , effTC , effTD ; Double effParClass6 , effParClass5 ;
        Double effParClass4 , effParClass3 , effParClass2nA , effParClass2nC ;
        Double effParClass1A , effParClass1C , effParClass1D , effParClassTA ;
        Double effParClassTC , effParClassTD ;

        Long n1brc6 = 0L,n1brc5  = 0L , n1brc4 = 0L, n1brc3 = 0L, n1brc2nA = 0L , n1brc2nC = 0L , n1brc1A = 0L, n1brc1C = 0L;
        Long n1brc1D = 0L, n1brcTA = 0L , n1brcTC = 0L , n1brcTD = 0L , e1ff6 = 0L ;
        Long e1ff5 = 0L, e1ff4 = 0L ,e1ff3 = 0L , e1ff2nA = 0L, e1ff2nC = 0L, e1ff1A = 0L , e1ff1C = 0L ;
        Long e1ff1D = 0L ,e1ffTA = 0L, e1ffTC = 0L , e1ffTD = 0L ; Double e1ffParClass6 = 0d, e1ffParClass5 = 0d;
        Double e1ffParClass4 = 0d , e1ffParClass3 = 0d , e1ffParClass2nA = 0d, e1ffParClass2nC = 0d;
        Double e1ffParClass1A = 0d, e1ffParClass1C = 0d, e1ffParClass1D = 0d, e1ffParClassTA= 0d ;
        Double e1ffParClassTC = 0d, e1ffParClassTD = 0d;

        String anneeCourante = getLibelleAnneScolaire(anneeId,idEcole) ;
        String anneePrecedente= null ;
System.out.println("anneeCourante "+anneeCourante);
        if(anneeCourante!=null || !anneeCourante.isEmpty()) {
            String[] parties = anneeCourante.split(" ");
            int anneeDebut = Integer.parseInt(parties[1]);  // "2022" -> 2022
            int anneeFin = Integer.parseInt(parties[3]);
            anneeDebut -= 1;
            anneeFin -= 1;
            anneePrecedente = "Année " + anneeDebut + " - " + anneeFin ;
        }
System.out.println("anneePrecedente :"+anneePrecedente);
        if(anneePrecedente!=null || !anneePrecedente.isEmpty()){
        Long idAnneePrec  =  getIdentifiantAnneScolairePrecedente(anneePrecedente,idEcole) ;
            n1brc6 = findNombreClasseBranche(1L,idEcole);
            n1brc5 = findNombreClasseBranche(2L,idEcole);
            n1brc4 = findNombreClasseBranche(7L,idEcole);
            n1brc3 = findNombreClasseBranche(8L,idEcole);
            n1brc2nA = findNombreClasseBranche(9L,idEcole);
            n1brc2nC = findNombreClasseBranche(10L,idEcole);
            n1brc1A = findNombreClasseBranche(11L,idEcole);
            n1brc1C = findNombreClasseBranche(5L,idEcole);
            n1brc1D = findNombreClasseBranche(12L,idEcole);
            n1brcTC = findNombreClasseBranche(14L,idEcole);
            n1brcTD = findNombreClasseBranche(15L,idEcole);
            Long n1brcTA1 ,n1brcTA2 ;
            n1brcTA1= findNombreClasseBranche(13L,idEcole);
            n1brcTA2= findNombreClasseBranche(16L,idEcole);
            n1brcTA = n1brcTA1+ n1brcTA2 ;
            //Effectif n-1
            e1ffParClass6 = getnCountNomBreClasse(1L,idAnneePrec,idEcole);
            e1ffParClass5 = getnCountNomBreClasse(2L,idAnneePrec,idEcole);
            e1ffParClass4 = getnCountNomBreClasse(7L,idAnneePrec,idEcole);
            e1ffParClass3 = getnCountNomBreClasse(8L,idAnneePrec,idEcole);
            e1ffParClass2nA = getnCountNomBreClasse(9L,idAnneePrec,idEcole);
            e1ffParClass2nA = ( e1ffParClass2nA==null ? 0D:e1ffParClass2nA );
            e1ffParClass2nC = getnCountNomBreClasse(10L,idAnneePrec,idEcole);
            e1ffParClass2nC = ( e1ffParClass2nC==null ? 0D:e1ffParClass2nC );
            e1ffParClass1A = getnCountNomBreClasse(11L,idAnneePrec,idEcole);
            e1ffParClass1A = ( e1ffParClass1A==null ? 0D:e1ffParClass1A );
            e1ffParClass1C = getnCountNomBreClasse(5L,idAnneePrec,idEcole);
            e1ffParClass1C = ( e1ffParClass1C==null ? 0D:e1ffParClass1C );
            e1ffParClass1D = getnCountNomBreClasse(12L,idAnneePrec,idEcole);
            e1ffParClass1D = ( e1ffParClass1D==null ? 0D:e1ffParClass1D );
            e1ffParClassTC = getnCountNomBreClasse(14L,idAnneePrec,idEcole);
            e1ffParClassTC = ( e1ffParClassTC==null ? 0D:e1ffParClassTC );
            e1ffParClassTD = getnCountNomBreClasse(15L,idAnneePrec,idEcole);
            e1ffParClassTD = ( e1ffParClassTD==null ? 0D:e1ffParClassTD );
            Double e1ffParClassTA1  ,e1ffParClassTA2 ;
            e1ffParClassTA1= getnCountNomBreClasse(13L,idAnneePrec,idEcole);
            e1ffParClassTA2= getnCountNomBreClasse(16L,idAnneePrec,idEcole);
            e1ffParClassTA = ( e1ffParClassTA1==null ? 0D:e1ffParClassTA1 )+ (e1ffParClassTA2==null ? 0D:e1ffParClassTA2 );

             // efectif
            e1ff6 = getEffectParClasse(1L,idAnneePrec,idEcole);
            e1ff5 = getEffectParClasse(2L,idAnneePrec,idEcole);
            e1ff4 = getEffectParClasse(7L,idAnneePrec,idEcole);
            e1ff3 = getEffectParClasse(8L,idAnneePrec,idEcole);
            e1ff2nA = getEffectParClasse(9L,idAnneePrec,idEcole);
            e1ff2nC = getEffectParClasse(10L,idAnneePrec,idEcole);
            e1ff1A = getEffectParClasse(11L,idAnneePrec,idEcole);
            e1ff1C = getEffectParClasse(5L,idAnneePrec,idEcole);
            e1ff1D = getEffectParClasse(12L,idAnneePrec,idEcole);
            e1ffTC = getEffectParClasse(14L,idAnneePrec,idEcole);
            e1ffTD = getEffectParClasse(15L,idAnneePrec,idEcole);
            Long e1ffTA1 ,e1ffTA2 ;
            e1ffTA1= getEffectParClasse(13L,idAnneePrec,idEcole);
            e1ffTA2= getEffectParClasse(16L,idAnneePrec,idEcole);
            e1ffTA = e1ffTA1+ e1ffTA2 ;


        }


        nbrc6 = findNombreClasseBranche(1L,idEcole);
        nbrc5 = findNombreClasseBranche(2L,idEcole);
        nbrc4 = findNombreClasseBranche(7L,idEcole);
        nbrc3 = findNombreClasseBranche(8L,idEcole);
        nbrc2nA = findNombreClasseBranche(9L,idEcole);
        nbrc2nC = findNombreClasseBranche(10L,idEcole);
        nbrc1A = findNombreClasseBranche(11L,idEcole);
        nbrc1C = findNombreClasseBranche(5L,idEcole);
        nbrc1D = findNombreClasseBranche(12L,idEcole);
        nbrcTC = findNombreClasseBranche(14L,idEcole);
        nbrcTD = findNombreClasseBranche(15L,idEcole);
        Long nbrcTA1 ,nbrcTA2 ;
        nbrcTA1= findNombreClasseBranche(13L,idEcole);
        nbrcTA2= findNombreClasseBranche(16L,idEcole);
        nbrcTA = nbrcTA1+ nbrcTA2 ;
        m.setNbrc6(nbrc6);
        m.setNbrc5(nbrc5);
        m.setNbrc4(nbrc4);
        m.setNbrc3(nbrc3);
        m.setNbrc2nA(nbrc2nA);
        m.setNbrc2nC(nbrc2nC);
        m.setNbrc1A(nbrc1A);
        m.setNbrc1C(nbrc1C);
        m.setNbrc1D(nbrc1D);
        m.setNbrcTC(nbrcTC);
        m.setNbrcTD(nbrcTD);
        m.setNbrcTA(nbrcTA);
        //n-1 mise à jour
        m.setN1brc6(n1brc6);
        m.setN1brc5(n1brc5);
        m.setN1brc4(n1brc4);
        m.setN1brc3(n1brc3);
        m.setN1brc2nA(n1brc2nA);
        m.setN1brc2nC(n1brc2nC);
        m.setN1brc1A(n1brc1A);
        m.setN1brc1C(n1brc1C);
        m.setN1brc1D(n1brc1D);
        m.setN1brcTC(n1brcTC);
        m.setN1brcTD(n1brcTD);
        m.setN1brcTA(n1brcTA);


        //Effectif prevus de la classe


        effParClass6 = getCountNomBreClasse(1L,anneeId,idEcole);
        System.out.println("effParClass6 "+effParClass6);
        effParClass5 = getCountNomBreClasse(2L,anneeId,idEcole);
        effParClass4 = getCountNomBreClasse(7L,anneeId,idEcole);
        effParClass3 = getCountNomBreClasse(8L,anneeId,idEcole);
        effParClass2nA = getCountNomBreClasse(9L,anneeId,idEcole);
        effParClass2nA = ( effParClass2nA==null ? 0D:effParClass2nA );
        effParClass2nC = getCountNomBreClasse(10L,anneeId,idEcole);
        effParClass2nC = ( effParClass2nC==null ? 0D:effParClass2nC );
        effParClass1A = getCountNomBreClasse(11L,anneeId,idEcole);
        effParClass1A = ( effParClass1A==null ? 0D:effParClass1A );
        effParClass1C = getCountNomBreClasse(5L,anneeId,idEcole);
        effParClass1C = ( effParClass1C==null ? 0D:effParClass1C );
        effParClass1D = getCountNomBreClasse(12L,anneeId,idEcole);
        effParClass1D = ( effParClass1D==null ? 0D:effParClass1D );
        effParClassTC = getCountNomBreClasse(14L,anneeId,idEcole);
        effParClassTC = ( effParClassTC==null ? 0D:effParClassTC );
        effParClassTD = getCountNomBreClasse(15L,anneeId,idEcole);
        effParClassTD = ( effParClassTD==null ? 0D:effParClassTD );
        Double effParClassTA1  ,effParClassTA2 ;
        effParClassTA1= getCountNomBreClasse(13L,anneeId,idEcole);
        effParClassTA2= getCountNomBreClasse(16L,anneeId,idEcole);
        effParClassTA = ( effParClassTA1==null ? 0D:effParClassTA1 )+ (effParClassTA2==null ? 0D:effParClassTA2 );


        m.setEffParClass6(effParClass6);
        m.setEffParClass5(effParClass5);
        m.setEffParClass4(effParClass4);
        m.setEffParClass3(effParClass3);
        m.setEffParClass2nA(effParClass2nA);
        m.setEffParClass2nC(effParClass2nC);
        m.setEffParClass1A(effParClass1A);
        m.setEffParClass1C(effParClass1C);
        m.setEffParClass1D(effParClass1D);
        m.setEffParClassTC(effParClassTC);
        m.setEffParClassTD(effParClassTD);
        m.setEffParClassTA(effParClassTA);

       // Mise à jour effectif n-1
        m.setE1ffParClass6(e1ffParClass6);
        m.setE1ffParClass5(e1ffParClass5);
        m.setE1ffParClass4(e1ffParClass4);
        m.setE1ffParClass3(e1ffParClass3);
        m.setE1ffParClass2nA(e1ffParClass2nA);
        m.setE1ffParClass2nC(e1ffParClass2nC);
        m.setE1ffParClass1A(e1ffParClass1A);
        m.setE1ffParClass1C(e1ffParClass1C);
        m.setE1ffParClass1D(e1ffParClass1D);
        m.setE1ffParClassTC(e1ffParClassTC);
        m.setE1ffParClassTD(e1ffParClassTD);
        m.setE1ffParClassTA(e1ffParClassTA);


        //Effectif prevus de la classe

        // get effectif par classe

        eff6 = getEffectParClasse(1L,anneeId,idEcole);
        eff5 = getEffectParClasse(2L,anneeId,idEcole);
        eff4 = getEffectParClasse(7L,anneeId,idEcole);
        eff3 = getEffectParClasse(8L,anneeId,idEcole);
        eff2nA = getEffectParClasse(9L,anneeId,idEcole);
        eff2nC = getEffectParClasse(10L,anneeId,idEcole);
        eff1A = getEffectParClasse(11L,anneeId,idEcole);
        eff1C = getEffectParClasse(5L,anneeId,idEcole);
        eff1D = getEffectParClasse(12L,anneeId,idEcole);
        effTC = getEffectParClasse(14L,anneeId,idEcole);
        effTD = getEffectParClasse(15L,anneeId,idEcole);
        Long effTA1 ,effTA2 ;
        effTA1= getEffectParClasse(13L,anneeId,idEcole);
        effTA2= getEffectParClasse(16L,anneeId,idEcole);
        effTA = effTA1+ effTA2 ;


        // get effectif par classe n-1

        System.out.println("eff6 "+eff6);

        m.setEff6(eff6);
        m.setEff5(eff5);
        m.setEff4(eff4);
        m.setEff3(eff3);
        m.setEff2nA(eff2nA);
        m.setEff2nC(eff2nC);
        m.setEff1A(eff1A);
        m.setEff1C(eff1C);
        m.setEff1D(eff1D);
        m.setEffTC(effTC);
        m.setEffTD(effTD);
        m.setEffTA(effTA);

        // Mise à jour n-1 effectif


        m.setE1ff6(e1ff6);
        m.setE1ff5(e1ff5);
        m.setE1ff4(e1ff4);
        m.setE1ff3(e1ff3);
        m.setE1ff2nA(e1ff2nA);
        m.setE1ff2nC(e1ff2nC);
        m.setE1ff1A(e1ff1A);
        m.setE1ff1C(e1ff1C);
        m.setE1ff1D(e1ff1D);
        m.setE1ffTC(e1ffTC);
        m.setE1ffTD(e1ffTD);
        m.setE1ffTA(e1ffTA);











        return  m ;
    }



       Double getCountNomBreClasse(Long idBranche,Long idAnneId ,Long idEcole) {
           try {
               TypedQuery<Double> q = (TypedQuery<Double>) em.createQuery( "SELECT avg(o.effectif)  from Classe o   where o.ecole.id =:idEcole   and o.branche.id=:idBranche ");
               Double size = q.setParameter("idEcole" ,idEcole).
                                 setParameter("idBranche" ,idBranche).
                              getSingleResult() ;

               return size;
           } catch (NoResultException e) {
               return 0D ;
           }
       }

    Double getnCountNomBreClasse(Long idBranche,Long idAnneId ,Long idEcole) {
        try {
            TypedQuery<Double> q = (TypedQuery<Double>) em.createQuery( "SELECT avg(o.effectif)  from Classe o   where o.ecole.id =:idEcole   and o.branche.id=:idBranche ");
            Double size = q.setParameter("idEcole" ,idEcole).
                    setParameter("idBranche" ,idBranche).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0D ;
        }
    }


    List<NiveauClasseDto> getListClasse(Long idEcole , Long idAnneId, Integer lim) {
        List<NiveauClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauClasseDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseDto(o.branche.libelle,o.branche.id) FROM Classe o  where o.ecole.id =:idEcole  and o.annee.id =:idAnneId and o.branche.niveau.id <=:lim  order by o.branche.id "
                , NiveauClasseDto.class);
        return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
                                           .setParameter("lim" ,lim)
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

    Long getEffectParClasse(Long idBranche,Long idAnneId ,Long idEcole ) {
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.id =: idBranche  and o.inscription.annee.id=:idAnn " +
                    " and o.classe.ecole.id=:idEcole ");
            Long size = q.setParameter("idBranche" ,idBranche).
                           setParameter("idAnn" ,idAnneId).
                         setParameter("idEcole" ,idEcole).
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


    public Long findNombreClasseBranche(long id, Long ecoleId) {
        //logger.info(String.format("find by Branche id :: %s", id));
        return Classe.find("branche.id = ?1 and ecole.id=?2",id, ecoleId).count() ;
    }

    public String  getLibelleAnneScolaire(long anneId, Long ecoleId) {
        try {
            TypedQuery<String> q = (TypedQuery<String>) em.createQuery( "select distinct b.anneeLibelle from Bulletin b where b.anneeId=:annee and b.ecoleId=:ecoleId");
            String size = q.setParameter("annee" ,anneId).
                    setParameter("ecoleId" ,ecoleId).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return null ;
        }
    }

    public Long  getIdentifiantAnneScolairePrecedente(String  annee, Long ecoleId) {
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "select distinct b.anneeId from Bulletin b where b.anneeLibelle=:annee and b.ecoleId=:ecoleId");
            Long anneeLibelle = q.setParameter("annee" ,annee).
                    setParameter("ecoleId" ,ecoleId).
                    getSingleResult() ;
            return anneeLibelle;
        } catch (NoResultException e) {
            return 0L;
        }
    }



}
