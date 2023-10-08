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

        nbrc6 = findNombreClasseBranche(1L,idEcole);
        System.out.println("nbrc6 "+nbrc6);
        nbrc5 = findNombreClasseBranche(2L,idEcole);
        System.out.println("nbrc5 "+nbrc5);
        nbrc4 = findNombreClasseBranche(7L,idEcole);
        System.out.println("nbrc4 "+nbrc4);
        nbrc3 = findNombreClasseBranche(8L,idEcole);
        System.out.println("nbrc3 "+nbrc3);
        nbrc2nA = findNombreClasseBranche(9L,idEcole);
        System.out.println("nbrc2nA "+nbrc2nA);
        nbrc2nC = findNombreClasseBranche(10L,idEcole);
        System.out.println("nbrc2nC "+nbrc2nC);
        nbrc1A = findNombreClasseBranche(11L,idEcole);
        System.out.println("nbrc1A "+nbrc1A);
        nbrc1C = findNombreClasseBranche(5L,idEcole);
        System.out.println("nbrc1C "+nbrc1C);
        nbrc1D = findNombreClasseBranche(12L,idEcole);
        System.out.println("nbrc1D "+nbrc1D);
        nbrcTC = findNombreClasseBranche(14L,idEcole);
        System.out.println("nbrcTC "+nbrcTC);
        nbrcTD = findNombreClasseBranche(15L,idEcole);
        System.out.println("nbrcTD "+nbrcTD);
        Long nbrcTA1 ,nbrcTA2 ;
        nbrcTA1= findNombreClasseBranche(13L,idEcole);
        System.out.println("nbrcTA1 "+nbrcTA1);
        nbrcTA2= findNombreClasseBranche(16L,idEcole);
        System.out.println("nbrcTA2 "+nbrcTA2);
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
        //Effectif prevus de la classe


        effParClass6 = getCountNomBreClasse(1L,anneeId,idEcole);
        System.out.println("effParClass6 "+effParClass6);
        effParClass5 = getCountNomBreClasse(2L,anneeId,idEcole);
        effParClass4 = getCountNomBreClasse(7L,anneeId,idEcole);
        effParClass3 = getCountNomBreClasse(8L,anneeId,idEcole);
        effParClass2nA = getCountNomBreClasse(9L,anneeId,idEcole);
        effParClass2nC = getCountNomBreClasse(10L,anneeId,idEcole);
        effParClass1A = getCountNomBreClasse(11L,anneeId,idEcole);
        effParClass1C = getCountNomBreClasse(5L,anneeId,idEcole);
        effParClass1D = getCountNomBreClasse(12L,anneeId,idEcole);
        effParClassTC = getCountNomBreClasse(14L,anneeId,idEcole);
        effParClassTD = getCountNomBreClasse(15L,anneeId,idEcole);
        Double effParClassTA1  ,effParClassTA2 ;
        effParClassTA1= getCountNomBreClasse(13L,anneeId,idEcole);
        effParClassTA2= getCountNomBreClasse(16L,anneeId,idEcole);
        effParClassTA = ( effParClassTA1==null ? 0D:effParClassTA1 )+ (effParClassTA2==null ? 0D:effParClassTA2 );
        System.out.println("effParClass6 "+effParClass6);
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
}
