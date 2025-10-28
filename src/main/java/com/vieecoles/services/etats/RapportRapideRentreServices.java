package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.Rapide_rapide;
import com.vieecoles.entities.operations.Rapide_rapide2;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.services.ClasseMatiereService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RapportRapideRentreServices {
    @Inject
    EntityManager em;
    @Inject
    ClasseMatiereService cmService;

    public  Rapport_RappidDto  RapportRentree(Long idEcole , Long  AnneeId ){
        int LongTableau;


        Rapport_RappidDto resultatsListElevesDto = new Rapport_RappidDto() ;

         Boolean perAdmi = null,conseilInter = null, conseilProfesseur = null,conseilProfesPrincip = null;
         Boolean conseilEnseigne = null, professClassExame = null, parentsEleve = null,perAdmichefClasse = null,unitePedagogi = null;
         Boolean chefClasse = null ;
        String ecoleclibelle = null, ecole_adresse = null, ecolecode = null,ecole_statut = null,ecole_telephone = null;

        List<ClasseParNiveauDto> classeParNiveauDto  = new ArrayList<>();
        Long nbreCla6 = 0L ,  nbreCla5 = 0L ,nbreCla4 = 0L, nbreCla3 = 0L, nbreCla2A = 0L,nbreCla2C = 0L;
         Long nbreCla1A = 0L, nbreCla1C = 0L, nbreCla1D = 0L,nbreClaTLA = 0L, nbreClaTLC = 0L, nbreClaTLD = 0L;

        List<AffecteParNiveauDto> affecteParNiveauDto = new ArrayList<>() ;
         Long attenAFF6 , attenAFF5 , attenAFF4 , attenAFF3 , attenAFF2A ;
         Long attenAFF2C , attenAFF1A , attenAFF1C , attenAFF1D , attenAFFTLA , attenAFFTLC ;
        Long attenAFFTLD , presenAFF6 = 0L, presenAFF5 = 0L, presenAFF4 = 0L, presenAFF3 = 0L, presenAFF2A = 0L;
         Long presenAFF2C = 0L , presenAFF1A = 0L, presenAFF1C = 0L, presenAFF1D = 0L, presenAFFTLA = 0L ,presenAFFTLC = 0L,presenAFFTLD = 0L ;


        List<NonAffecteParNiveau> nonAffecteParNiveau = new ArrayList<>() ;
        Long attenNAFF6 = 0L, attenNAFF5 = 0L , attenNAFF4 = 0L, attenNAFF3 = 0L , attenNAFF2A = 0L,attenNAFF2C = 0L, attenNAFF1A = 0L;
         Long attenNAFF1C = 0L,attenNAFF1D = 0L, attenNAFFTLA = 0L, attenNAFFTLC = 0L;
         Long attenNAFFTLD = 0L, presenNAFF6 = 0L, presenNAFF5 = 0L, presenNAFF4 =0L, presenNAFF3 = 0L,presenNAFF2A = 0L;
         Long presenNAFF2C = 0L, presenNAFF1A = 0L, presenNAFF1C = 0L,presenNAFF1D = 0L,presenNAFFTLA = null, presenNAFFTLC = 0L, presenNAFFTLD = 0L;
         Long stClasse = 0L ;
           // getClasse Par Niveau
        nbreCla6 = getNombreClasse("6EME",idEcole);
        System.out.println("Nombre classe en 6eme"+nbreCla6);
        nbreCla5 = getNombreClasse("5EME",idEcole);
        nbreCla4 = getNombreClasse("4EME",idEcole);
        nbreCla3 = getNombreClasse("3EME",idEcole);
        nbreCla2A = getNombreClasse("2ND A",idEcole);
        nbreCla2C = getNombreClasse("2ND C",idEcole);
        nbreCla1A = getNombreClasse("1 ERE A",idEcole);
        nbreCla1C = getNombreClasse("1 ERE C",idEcole);
        nbreCla1D = getNombreClasse("1 ERE D",idEcole);
        Long nbreClaTLA1 = getNombreClasse("TLE A1",idEcole);
        Long nbreClaTLA2 = getNombreClasse("TLE A2",idEcole);
        nbreClaTLA = nbreClaTLA1+nbreClaTLA2 ;
        nbreClaTLC = getNombreClasse("TLE C",idEcole);
        nbreClaTLD = getNombreClasse("TLE D",idEcole);

        ClasseParNiveauDto cl = new ClasseParNiveauDto() ;

        cl.setNbreCla6(nbreCla6);
        cl.setNbreCla5(nbreCla5);
        cl.setNbreCla4(nbreCla4);
        cl.setNbreCla3(nbreCla3);
        cl.setNbreCla2A(nbreCla2A);
        cl.setNbreCla2C(nbreCla2C);
        cl.setNbreCla1A(nbreCla1A);
        cl.setNbreCla1C(nbreCla1C);
        cl.setNbreCla1D(nbreCla1D);
        cl.setNbreClaTLA(nbreClaTLA);
        cl.setNbreClaTLC(nbreClaTLC);
        cl.setNbreClaTLD(nbreClaTLD);
        classeParNiveauDto.add(cl) ;
        String libAffect = String.valueOf(Inscriptions.statusEleve.AFFECTE);
        // Get Inscription attendus Affectee ;
        attenAFF6= getNombreInscripStatutAttendu(1L,idEcole,AnneeId ,libAffect);
        System.out.println("Attendu et Affecté en 6eme"+attenAFF6);
        attenAFF5= getNombreInscripStatutAttendu(2L,idEcole,AnneeId , libAffect);
        attenAFF4= getNombreInscripStatutAttendu(7L,idEcole,AnneeId , libAffect);
        attenAFF3= getNombreInscripStatutAttendu(8L,idEcole,AnneeId ,libAffect);
        attenAFF2A= getNombreInscripStatutAttendu(9L,idEcole,AnneeId , libAffect);
        attenAFF2C= getNombreInscripStatutAttendu(10L,idEcole,AnneeId , libAffect);
        attenAFF1A= getNombreInscripStatutAttendu(11L,idEcole,AnneeId , libAffect);
        attenAFF1C= getNombreInscripStatutAttendu(5L,idEcole,AnneeId , libAffect);
        attenAFF1D= getNombreInscripStatutAttendu(12L,idEcole,AnneeId , libAffect);
       Long attenAFFTLA1 = getNombreInscripStatutAttendu(13L,idEcole,AnneeId , libAffect) ;
        Long attenAFFTLA2 = getNombreInscripStatutAttendu(16L,idEcole,AnneeId , libAffect) ;
       attenAFFTLA = attenAFFTLA1+attenAFFTLA2 ;
        attenAFFTLC = getNombreInscripStatutAttendu(14L,idEcole,AnneeId , libAffect) ;
        attenAFFTLD = getNombreInscripStatutAttendu(15L,idEcole,AnneeId , libAffect) ;

        AffecteParNiveauDto aff = new AffecteParNiveauDto() ;
        aff.setAttenAFF6(attenAFF6);
        aff.setAttenAFF5(attenAFF5);
        aff.setAttenAFF4(attenAFF4);
        aff.setAttenAFF3(attenAFF3);
        aff.setAttenAFF2A(attenAFF2A);
        aff.setAttenAFF2C(attenAFF2C);
        aff.setAttenAFF1A(attenAFF1A);
        aff.setAttenAFF1C(attenAFF1C);
        aff.setAttenAFF1D(attenAFF1D);
        aff.setAttenAFFTLA(attenAFFTLA);
        aff.setAttenAFFTLC(attenAFFTLC);
        aff.setAttenAFFTLD(attenAFFTLD);

        Rapide_rapide r6 = new Rapide_rapide() ;
        Rapide_rapide r5 = new Rapide_rapide() ;
        Rapide_rapide r4 = new Rapide_rapide() ;
        Rapide_rapide r3 = new Rapide_rapide() ;
        Rapide_rapide r2ndA = new Rapide_rapide() ;
        Rapide_rapide r2ndC = new Rapide_rapide() ;
        Rapide_rapide r1A = new Rapide_rapide() ;
        Rapide_rapide r1C = new Rapide_rapide() ;
        Rapide_rapide r1D = new Rapide_rapide() ;
        Rapide_rapide rTA1 = new Rapide_rapide() ;
        Rapide_rapide rTA2 = new Rapide_rapide() ;
        Rapide_rapide rTC = new Rapide_rapide() ;
        Rapide_rapide rTD = new Rapide_rapide() ;

        //eleve present affecte

        r6 = getInfoEnquete("6EME",idEcole,AnneeId,libAffect );

        if(r6!=null){

            presenAFF6 = Long.valueOf(r6.getNombreAff());
            System.out.println("Nombre présent et  Affecte en 6eme"+presenAFF6);
        }


        r5 = getInfoEnquete("5EME",idEcole,AnneeId,libAffect );
        if(r5!=null)
            presenAFF5 = Long.valueOf(r5.getNombreAff());

        r4 = getInfoEnquete("4EME",idEcole,AnneeId, libAffect);
        if(r4!=null)
            presenAFF4 = Long.valueOf(r4.getNombreAff());

        r3 = getInfoEnquete("3EME",idEcole,AnneeId,libAffect);
        if(r3!=null)
            presenAFF3 = Long.valueOf(r3.getNombreAff());

        r2ndA = getInfoEnquete("2ND A",idEcole,AnneeId, libAffect);
        if(r2ndA!=null)
            presenAFF2A = Long.valueOf(r2ndA.getNombreAff());

        r2ndC = getInfoEnquete("2ND C",idEcole,AnneeId, libAffect);
        if(r2ndC!=null)
            presenAFF2C = Long.valueOf(r2ndC.getNombreAff());

        r1A = getInfoEnquete("1 ERE A",idEcole,AnneeId,libAffect);
        if(r1A!=null)
            presenAFF1A = Long.valueOf(r1A.getNombreAff());

        r1C = getInfoEnquete("1 ERE C",idEcole,AnneeId, libAffect);
        if(r1C!=null)
            presenAFF1C = Long.valueOf(r1C.getNombreAff());

        r1D = getInfoEnquete("1 ERE D",idEcole,AnneeId,libAffect);
        if(r1D!=null)
            presenAFF1D = Long.valueOf(r1D.getNombreAff());
        Long  presenAFFTLA1 = 0L,presenAFFTLA2 = 0L;

        rTA1 = getInfoEnquete("TLE A1",idEcole,AnneeId,libAffect);
          if(rTA1!=null)
           presenAFFTLA1 = Long.valueOf(rTA1.getNombreAff());

        rTA2 = getInfoEnquete("TLE A2",idEcole,AnneeId, libAffect);
        if(rTA2!=null)
            presenAFFTLA2 = Long.valueOf(rTA2.getNombreAff());

        presenAFFTLA= presenAFFTLA1+presenAFFTLA2 ;

        rTC = getInfoEnquete("TLE C",idEcole,AnneeId, libAffect);
        if(rTC!=null)
            presenAFFTLC = Long.valueOf(rTC.getNombreAff());

        rTD = getInfoEnquete("TLE D",idEcole,AnneeId, libAffect);
        if(rTD!=null)
            presenAFFTLD = Long.valueOf(rTD.getNombreAff());

        aff.setPresenAFF6(presenAFF6);
        aff.setPresenAFF5(presenAFF5);
        aff.setPresenAFF4(presenAFF4);
        aff.setPresenAFF3(presenAFF3);
        aff.setPresenAFF2A(presenAFF2A);
        aff.setPresenAFF2C(presenAFF2C);
        aff.setPresenAFF1A(presenAFF1A);
        aff.setPresenAFF1C(presenAFF1C);
        aff.setPresenAFF1D(presenAFF1D);
        aff.setPresenAFFTLA(presenAFFTLA);
        aff.setPresenAFFTLC(presenAFFTLC);
        aff.setPresenAFFTLD(presenAFFTLD);

        affecteParNiveauDto.add(aff);

        //getResultat Nonaffectes+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Get Inscription attendus Affectee ;
        String libNonAff = String.valueOf(Inscriptions.statusEleve.NON_AFFECTE);
        attenNAFF6= getNombreInscripStatutAttendu(1L,idEcole,AnneeId , libNonAff);
        System.out.println("Attendu et non Affectés en 6eme "+attenNAFF6);
        attenNAFF5= getNombreInscripStatutAttendu(2L,idEcole,AnneeId , libNonAff);
        attenNAFF4= getNombreInscripStatutAttendu(7L,idEcole,AnneeId , libNonAff);
        attenNAFF3= getNombreInscripStatutAttendu(8L,idEcole,AnneeId , libNonAff);
        attenNAFF2A= getNombreInscripStatutAttendu(9L,idEcole,AnneeId , libNonAff);
        attenNAFF2C= getNombreInscripStatutAttendu(10L,idEcole,AnneeId , libNonAff);
        attenNAFF1A= getNombreInscripStatutAttendu(11L,idEcole,AnneeId ,libNonAff);
        attenNAFF1C= getNombreInscripStatutAttendu(5L,idEcole,AnneeId , libNonAff);
        attenNAFF1D= getNombreInscripStatutAttendu(12L,idEcole,AnneeId , libNonAff);
        Long attenNAFFTLA1 = getNombreInscripStatutAttendu(13L,idEcole,AnneeId , libNonAff) ;
        Long attenNAFFTLA2 = getNombreInscripStatutAttendu(16L,idEcole,AnneeId , libNonAff) ;
        attenNAFFTLA = attenNAFFTLA1+attenNAFFTLA2 ;
        attenNAFFTLC = getNombreInscripStatutAttendu(14L,idEcole,AnneeId , libNonAff) ;
        attenNAFFTLD = getNombreInscripStatutAttendu(15L,idEcole,AnneeId ,libNonAff) ;




        NonAffecteParNiveau Naff = new NonAffecteParNiveau() ;
        Naff.setAttenNAFF6(attenNAFF6);
        Naff.setAttenNAFF5(attenNAFF5);
        Naff.setAttenNAFF4(attenNAFF4);
        Naff.setAttenNAFF3(attenNAFF3);
        Naff.setAttenNAFF2A(attenNAFF2A);
        Naff.setAttenNAFF2C(attenNAFF2C);
        Naff.setAttenNAFF1A(attenNAFF1A);
        Naff.setAttenNAFF1C(attenNAFF1C);
        Naff.setAttenNAFF1D(attenNAFF1D);
        Naff.setAttenNAFFTLA(attenNAFFTLA);
        Naff.setAttenNAFFTLC(attenNAFFTLC);
        Naff.setAttenNAFFTLD(attenNAFFTLD);

        Rapide_rapide nr6 = new Rapide_rapide() ;
        Rapide_rapide nr5 = new Rapide_rapide() ;
        Rapide_rapide nr4 = new Rapide_rapide() ;
        Rapide_rapide nr3 = new Rapide_rapide() ;
        Rapide_rapide nr2ndA = new Rapide_rapide() ;
        Rapide_rapide nr2ndC = new Rapide_rapide() ;
        Rapide_rapide nr1A = new Rapide_rapide() ;
        Rapide_rapide nr1C = new Rapide_rapide() ;
        Rapide_rapide nr1D = new Rapide_rapide() ;
        Rapide_rapide nrTA1 = new Rapide_rapide() ;
        Rapide_rapide nrTA2 = new Rapide_rapide() ;
        Rapide_rapide nrTC = new Rapide_rapide() ;
        Rapide_rapide nrTD = new Rapide_rapide() ;

        //eleve present Nonaffecte
        nr6 = getInfoEnquete("6EME",idEcole,AnneeId, libNonAff);
        if(nr6!=null) {
            presenNAFF6 = Long.valueOf(nr6.getNombreNAff());
            System.out.println("Present et non affectes en 6eme "+presenNAFF6);
        }


        nr5 = getInfoEnquete("5EME",idEcole,AnneeId,  libNonAff);
        if(nr5!=null)
            presenNAFF5 = Long.valueOf(nr5.getNombreNAff());

        nr4 = getInfoEnquete("4EME",idEcole,AnneeId, libNonAff);
        if(nr4!=null)
            presenNAFF4 = Long.valueOf(nr4.getNombreNAff());

        nr3 = getInfoEnquete("3EME",idEcole,AnneeId, libNonAff);
        if(nr3!=null)
            presenNAFF3 = Long.valueOf(nr3.getNombreNAff());

        nr2ndA = getInfoEnquete("2ND A",idEcole,AnneeId, libNonAff);
        if(nr2ndA!=null)
            presenNAFF2A = Long.valueOf(nr2ndA.getNombreNAff());

        nr2ndC = getInfoEnquete("2ND C",idEcole,AnneeId, libNonAff);
        if(nr2ndC!=null)
            presenNAFF2C = Long.valueOf(nr2ndC.getNombreNAff());

        nr1A = getInfoEnquete("1 ERE A",idEcole,AnneeId,libNonAff);
        if(nr1A!=null)
            presenNAFF1A = Long.valueOf(nr1A.getNombreNAff());

        nr1C = getInfoEnquete("1 ERE C",idEcole,AnneeId, libNonAff);
        if(nr1C!=null)
            presenNAFF1C = Long.valueOf(nr1C.getNombreNAff());

        nr1D = getInfoEnquete("1 ERE D",idEcole,AnneeId,libNonAff);
        if(nr1D!=null)
            presenNAFF1D = Long.valueOf(nr1D.getNombreNAff());
        Long  presenNAFFTLA1 = 0L,presenNAFFTLA2 = 0L;

        nrTA1 = getInfoEnquete("TLE A1",idEcole,AnneeId, libNonAff);
        if(nrTA1!=null)
            presenNAFFTLA1 = Long.valueOf(nrTA1.getNombreNAff());

        nrTA2 = getInfoEnquete("TLE A2",idEcole,AnneeId,libNonAff);
        if(nrTA2!=null)
            presenNAFFTLA2 = Long.valueOf(nrTA2.getNombreNAff());

        presenNAFFTLA= presenNAFFTLA1+presenNAFFTLA2 ;

        nrTC = getInfoEnquete("TLE C",idEcole,AnneeId, libNonAff);
        if(nrTC!=null)
            presenNAFFTLC = Long.valueOf(nrTC.getNombreNAff());

        nrTD = getInfoEnquete("TLE D",idEcole,AnneeId, libNonAff);
        if(nrTD!=null)
            presenNAFFTLD = Long.valueOf(nrTD.getNombreNAff());

        Naff.setPresenNAFF6(presenNAFF6);
        Naff.setPresenNAFF5(presenNAFF5);
        Naff.setPresenNAFF4(presenNAFF4);
        Naff.setPresenNAFF3(presenNAFF3);
        Naff.setPresenNAFF2A(presenNAFF2A);
        Naff.setPresenNAFF2C(presenNAFF2C);
        Naff.setPresenNAFF1A(presenNAFF1A);
        Naff.setPresenNAFF1C(presenNAFF1C);
        Naff.setPresenNAFF1D(presenNAFF1D);
        Naff.setPresenNAFFTLA(presenNAFFTLA);
        Naff.setPresenNAFFTLC(presenNAFFTLC);
        Naff.setPresenNAFFTLD(presenNAFFTLD);

        nonAffecteParNiveau.add(Naff);
        Rapide_rapide2 r2 = new Rapide_rapide2() ;
        r2 = getInfoEnquete2(idEcole,AnneeId) ;
        if(r2!=null) {
            System.out.println("Infos Suplementaire "+r2.toString());
            perAdmi = r2.getPerAdmi();
            conseilInter =r2.getConseilInter();
            conseilProfesseur = r2.getConseilProfesseur();
            conseilProfesPrincip = r2.getConseilProfesPrincip() ;
            conseilEnseigne = r2.getConseilEnseigne() ;
            parentsEleve= r2.getParentsEleve();
            perAdmichefClasse = r2.getPerAdmichefClasse();
            unitePedagogi = r2.getUnitePedagogi();
            chefClasse = r2.getChefClasse() ;
            professClassExame =r2.getProfessClassExame();
        }
        stClasse = getNombreTotalClasse(idEcole);
        resultatsListElevesDto.setStClasse(stClasse);
        resultatsListElevesDto.setChefClasse(chefClasse);
        resultatsListElevesDto.setProfessClassExame(professClassExame);
        resultatsListElevesDto.setAffecteParNiveauDto(affecteParNiveauDto);
        resultatsListElevesDto.setClasseParNiveauDto(classeParNiveauDto);
        resultatsListElevesDto.setNonAffecteParNiveau(nonAffecteParNiveau);
        resultatsListElevesDto.setPerAdmi(perAdmi);
        resultatsListElevesDto.setConseilInter(conseilInter);
        resultatsListElevesDto.setConseilProfesseur(conseilProfesseur);
        resultatsListElevesDto.setConseilProfesPrincip(conseilProfesPrincip);
        resultatsListElevesDto.setConseilEnseigne(conseilEnseigne);
        resultatsListElevesDto.setParentsEleve(parentsEleve);
        resultatsListElevesDto.setPerAdmichefClasse(perAdmichefClasse);
        resultatsListElevesDto.setUnitePedagogi(unitePedagogi);
        Double pourAFF6 =0d , pourAFF5 =0d , pourAFF4 =0d , pourAFF3 =0d,pourAFF2A =0d,pourAFF2C =0d ;
        Double pourAFF1A =0d,pourAFF1C =0d, pourAFF1D =0d,pourAFFTLA =0d,pourAFFTLC =0d,pourAFFTLD =0d;

        Double pourNAFF6 =0d,pourNAFF5 =0d, pourNAFF4 =0d, pourNAFF3 =0d,pourNAFF2A =0d, pourNAFF2C =0d, pourNAFF1A =0d ;
        Double pourNAFF1C =0d, pourNAFF1D =0d,pourNAFFTLA =0d, pourNAFFTLC =0d,pourNAFFTLD =0d;

        if(attenAFF6!=0L)
            pourAFF6 = (((double)presenAFF6*100d)/(double)attenAFF6);

        resultatsListElevesDto.setPourAFF6(pourAFF6);

        if(attenAFF5!=0L)
            pourAFF5 = (((double)presenAFF5*100d)/(double)attenAFF5);

        resultatsListElevesDto.setPourAFF5(pourAFF5);

        if(attenAFF4!=0L)
            pourAFF4 = (((double)presenAFF4*100d)/(double)attenAFF4);
        resultatsListElevesDto.setPourAFF4(pourAFF4);

        if(attenAFF3!=0L)
            pourAFF3 = (((double)presenAFF3*100d)/(double)attenAFF3);
        resultatsListElevesDto.setPourAFF3(pourAFF3);

        if(attenAFF2A!=0L)
            pourAFF2A = (((double)presenAFF2A*100d)/(double)attenAFF2A);
        resultatsListElevesDto.setPourAFF2A(pourAFF2A);

        if(attenAFF2C!=0L)
            pourAFF2C = (((double)presenAFF2C*100d)/(double)attenAFF2C);
        resultatsListElevesDto.setPourAFF2C(pourAFF2C);

        if(attenAFF1A!=0L)
            pourAFF1A = (((double)presenAFF1A*100d)/(double)attenAFF1A);
        resultatsListElevesDto.setPourAFF1A(pourAFF1A);

        if(attenAFF1C!=0L)
            pourAFF1C = (((double)presenAFF1C*100d)/(double)attenAFF1C);
        resultatsListElevesDto.setPourAFF1C(pourAFF1C);

        if(attenAFF1D!=0L)
            pourAFF1D = (((double)presenAFF1D*100d)/(double)attenAFF1D);
        resultatsListElevesDto.setPourAFF1D(pourAFF1D);

        if(attenAFFTLA!=0L)
            pourAFFTLA = (((double)presenAFFTLA*100d)/(double)attenAFFTLA);
        resultatsListElevesDto.setPourAFFTA(pourAFFTLA);

        if(attenAFFTLC!=0L)
            pourAFFTLC = (((double)presenAFFTLC*100d)/(double)attenAFFTLC);
        resultatsListElevesDto.setPourAFFTC(pourAFFTLC);

        if(attenAFFTLD!=0L)
            pourAFFTLD = (((double)presenAFFTLD*100d)/(double)attenAFFTLD);
        resultatsListElevesDto.setPourAFFTD(pourAFFTLD);
 //Pourcentage non affecté

        if(attenNAFF6!=0L)
            pourNAFF6 = (((double)presenNAFF6*100d)/(double)attenNAFF6);
        resultatsListElevesDto.setPourNAFF6(pourNAFF6);

        if(attenNAFF5!=0L)
            pourNAFF5 = (((double)presenNAFF5*100d)/(double)attenNAFF5);
        resultatsListElevesDto.setPourNAFF5(pourNAFF5);

        if(attenNAFF4!=0L)
            pourNAFF4 = (((double)presenNAFF4*100d)/(double)attenNAFF4);
        resultatsListElevesDto.setPourNAFF4(pourNAFF4);

        if(attenNAFF3!=0L)
            pourNAFF3 = (((double)presenNAFF3*100d)/(double)attenNAFF3);
        resultatsListElevesDto.setPourNAFF3(pourNAFF3);

        if(attenNAFF2A!=0L)
            pourNAFF2A = (((double)presenNAFF2A*100d)/(double)attenNAFF2A);
        resultatsListElevesDto.setPourNAFF2A(pourNAFF2A);

        if(attenNAFF2C!=0L)
            pourNAFF2C = (((double)presenNAFF2C*100d)/(double)attenNAFF2C);
        resultatsListElevesDto.setPourNAFF2C(pourNAFF2C);

        if(attenNAFF1A!=0L)
            pourNAFF1A = (((double)presenNAFF1A*100d)/(double)attenNAFF1A);
        resultatsListElevesDto.setPourNAFF1A(pourNAFF1A);

        if(attenNAFF1C!=0L)
            pourNAFF1C = (((double)presenNAFF1C*100d)/(double)attenNAFF1C);
        resultatsListElevesDto.setPourNAFF1C(pourNAFF1C);

        if(attenNAFF1D!=0L)
            pourNAFF1D = (((double)presenNAFF1D*100d)/(double)attenNAFF1D);
        resultatsListElevesDto.setPourNAFF1D(pourNAFF1D);

        if(attenNAFFTLA!=0L)
            pourNAFFTLA = (((double)presenNAFFTLA*100d)/(double)attenNAFFTLA);
        resultatsListElevesDto.setPourNAFFTA(pourNAFFTLA);

        if(attenNAFFTLC!=0L)
            pourNAFFTLC = (((double)presenNAFFTLC*100d)/(double)attenNAFFTLC);
        resultatsListElevesDto.setPourNAFFTC(pourNAFFTLC);

        if(attenNAFFTLD!=0L)
            pourNAFFTLD = (((double)presenNAFFTLD*100d)/(double)attenNAFFTLD);
        resultatsListElevesDto.setPourNAFFTD(pourNAFFTLD);

        Long stAttentAff =0L,stAttentNAff =0L, stpresentAff =0L, stpresentNAff =0L;
        Double pourAff =0d ,pourNAff =0d;

        String libAffectTotlaff = String.valueOf(Inscriptions.statusEleve.AFFECTE);

        stAttentAff= getTotalNombreInscripStatutAttendu(idEcole,AnneeId ,libAffectTotlaff);
        resultatsListElevesDto.setStAttentAff(stAttentAff);

        String libAffectTotlNaff = String.valueOf(Inscriptions.statusEleve.NON_AFFECTE);

        stAttentNAff= getTotalNombreInscripStatutAttendu(idEcole,AnneeId ,libAffectTotlNaff);

        resultatsListElevesDto.setStAttentNAff(stAttentNAff);
        stpresentAff = presenAFF6+ presenAFF5+presenAFF4+presenAFF3+ presenAFF2A + presenAFF2C +
                presenAFF1A + presenAFF1C+ presenAFF1D +presenAFFTLA+ presenAFFTLC+presenAFFTLD ;

        resultatsListElevesDto.setStpresentAff(stpresentAff);
        stpresentNAff = presenNAFF6+ presenNAFF5+presenNAFF4+presenNAFF3+ presenNAFF2A + presenNAFF2C +
                presenNAFF1A + presenNAFF1C+ presenNAFF1D +presenNAFFTLA+ presenNAFFTLC+presenNAFFTLD ;

        resultatsListElevesDto.setStpresentNAff(stpresentNAff);

        if(stAttentAff!=0L)
            pourAff = (((double)stpresentAff*100d)/(double)stAttentAff);
        resultatsListElevesDto.setPourAff(pourAff);

        if(stAttentNAff!=0L)
            pourNAff = (((double)stpresentNAff*100d)/(double)stAttentNAff);
        resultatsListElevesDto.setPourNAff(pourNAff);






        return  resultatsListElevesDto ;
    }

 Long getNombreClasse( String niveau,Long IdEcole  ){
        List<Classe> myClass= new ArrayList<>();
     myClass= Classe.find("branche.libelle = ?1 and ecole.id=?2 and visible=?3",niveau, IdEcole,1).list() ;
     try {
         return (long) myClass.size();
     }catch (NoResultException e) {
         return 0L;
     }

 }

    Long getNombreTotalClasse( Long IdEcole  ){
        List<Classe> myClass= new ArrayList<>();
        myClass= Classe.find("ecole.id=?1 and visible=?2", IdEcole,1).list() ;
        try {
            return (long) myClass.size();
        }catch (NoResultException e) {
            return 0L;
        }

    }

    private  Long getTotalNombreInscripStatutAttendu(Long IdEcole , Long ann , String statusEleve){
        Inscriptions.statusEleve statusEleve1 ;

        if(statusEleve.equals("AFFECTE")){
            statusEleve1 = Inscriptions.statusEleve.AFFECTE ;
        }  else  {
            statusEleve1 = Inscriptions.statusEleve.NON_AFFECTE ;
        }

        List<Inscriptions> inscriptionsList= new ArrayList<>();
        inscriptionsList = Inscriptions.find("select m from Inscriptions m where  m.ecole.ecoleid=?1 and m.annee_scolaire.annee_scolaireid=?2 and m.inscriptions_statut_eleve=?3 ", IdEcole,ann,statusEleve1).list() ;
        try {
            return (long) inscriptionsList.size();
        }catch (NoResultException e) {
            return 0L;
        }
    }

   private  Long getNombreInscripStatutAttendu(Long idBranche, Long IdEcole , Long idAnneId , String statusEleve){
        Inscriptions.statusEleve statusEleve1 ;

        if(statusEleve.equals("AFFECTE")){
            statusEleve1 = Inscriptions.statusEleve.AFFECTE ;
        }  else  {
            statusEleve1 = Inscriptions.statusEleve.NON_AFFECTE ;
        }

     try {
       TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(distinct v.eleveid) FROM Inscriptions i ,ecole e ,eleve v, Annee_Scolaire an , ClasseEleve h , Classe c" +
           " where i.ecole.ecoleid=e.ecoleid and i.eleve.eleveid=v.eleveid and i.annee_scolaire.annee_scolaireid=an.annee_scolaireid and i.inscriptionsid=h.inscription.id and h.classe.id=c.id and " +
           "i.ecole.ecoleid=:idEcole and i.annee_scolaire.annee_scolaireid=:idAnn and c.branche.id=:idBranche and i.inscriptions_statut_eleve=:statut");
       Long size = q.setParameter("idBranche" ,idBranche).
           setParameter("idAnn" ,idAnneId).
           setParameter("idEcole" ,IdEcole).
           setParameter("statut" ,statusEleve1).
           getSingleResult() ;

       return size;
     } catch (NoResultException e) {
       return 0L ;
     }
    }
    private Rapide_rapide getInfoEnquete(String niveau, Long IdEcole , Long ann , String statusEleve){
        Inscriptions.statusEleve statusEleve1 ;
        if(statusEleve.equals("AFFECTE")){
            statusEleve1 = Inscriptions.statusEleve.AFFECTE ;
        }  else  {
            statusEleve1 = Inscriptions.statusEleve.NON_AFFECTE ;
        }
        Rapide_rapide rapideRapide =new Rapide_rapide() ;
        rapideRapide = Rapide_rapide.find("select m from Rapide_rapide m where  m.branche.libelle = ?1 and m.ecole.ecoleid=?2 and m.annee_scolaire.annee_scolaireid=?3  ",niveau, IdEcole,ann).firstResult() ;
        return  rapideRapide ;
    }

    private Rapide_rapide2 getInfoEnquete2(Long IdEcole , Long ann ){
        Rapide_rapide2 rapideRapide2 =new Rapide_rapide2() ;
        rapideRapide2 = Rapide_rapide2.find("ecole.ecoleid=?1 and annee_scolaire.annee_scolaireid=?2", IdEcole,ann).firstResult() ;
        return  rapideRapide2 ;
    }










}
