package com.vieecoles.services.etats;

import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.MatriceMoyenneDto;
import com.vieecoles.dto.NiveauDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MatriceMoyenneServices {
    @Inject
    EntityManager em;

    public List<MatriceMoyenneDto>  DspspDto(Long idEcole, String libellePeriode , String libelleAnnee ,String Classe){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole and b.libelleClasse=: Classe and  b.ordreNiveau < 15 and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee order by b.nom asc, b.prenoms asc ,b.ordreNiveau"
               , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("periode", libellePeriode)
                                 .setParameter("libelleAnnee", libelleAnnee)
                                   .setParameter("Classe", Classe)
                                 .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<MatriceMoyenneDto> resultatsListElevesDto = new ArrayList<>();

        String matricule ,nom,prenoms,niveau,is_classFran = null,is_clasCompoFr = null,is_clasOrthoGram = null,is_clasExpreOral = null,is_clasphiloso,is_clasAng,
                is_clasMath,is_clasPhysiq,is_clasSVT,is_clasHg,is_clasLv2,is_clasEdhc,is_clasArplat,is_clasTic ,
                is_clasConduite,is_clasEps,is_class_periode ,nom_ecole,is_clasPericul,is_clasMemoris,is_clasFiq,is_clasAsSirah,is_clasAlQidah,is_clasAlAklaq ,pAppreciation;
        Double moyenTrim,moyenCompoFr = null,moyenFran = null,moyenOrthoGram = null,moyenExpreOral = null,moyenphiloso,moyenAng,moyenMath,moyenPhysiq,moyenSVT,
                moyenHg,moyenLv2,moyenEdhc,moyenArplat,moyenTic,moyenConduite,moyenEps,moyenPericul,moyenMemoris,moyenFiq,moyenAsSirah,moyenAlQidah,moyenAlAklaq,
                pCompoFr ,pFran,pOrthoGram ,pExpreOral ,philoso ,pAng ,pMath,pPhysiq,pSVT,pHg,pLv2,pEdhc,pArplat,pTic,pConduite,pEps,pPericul,pMemoris,
                pFiq,pAsSirah,pAlQidah,pAlAklaq ,pmoyenne
                ;
        Integer rang , ordre_niveau ;

        for (int i=0; i< LongTableau;i++) {
            MatriceMoyenneDto m = new MatriceMoyenneDto();
            pmoyenne = getMoyTotalAvgMatiere(libellePeriode ,libelleAnnee ,idEcole ,Classe);
            m.setLibelleAnnee(libelleAnnee);
            m.setLibelleClasse(Classe);
            m.setLibellePeriode(libellePeriode);
           // pAppreciation= getAppreciaTrimes(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode ,libelleAnnee) ;

            nom_ecole= getNomEcole(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode ,libelleAnnee) ;
            System.out.println("nom_ecole >>>>>>"+nom_ecole);
            moyenTrim = getMoyenTrimes(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode ,libelleAnnee) ;
            System.out.println("moyenTrim >>>>>>"+moyenTrim);
            rang = getRang(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode ,libelleAnnee) ;
            System.out.println("rang >>>>>>"+rang);
            ordre_niveau = getOrdreNiveau(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode ,libelleAnnee) ;
            System.out.println("ordre_niveau >>>>>>"+ordre_niveau);
            nom = getName(classeNiveauDtoList.get(i).getNiveau(),libellePeriode ,libelleAnnee) ;
            System.out.println("nom >>>>>>"+nom);
            prenoms= getSurName(classeNiveauDtoList.get(i).getNiveau(),libellePeriode ,libelleAnnee);
            System.out.println("prenoms >>>>>>"+prenoms);
            niveau = getNiveau(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode ,libelleAnnee) ;
            System.out.println("niveau >>>>>>"+niveau);
            matricule= classeNiveauDtoList.get(i).getNiveau();
            System.out.println("matricule >>>>>>"+matricule);
            is_class_periode= getIsClassePeriode(classeNiveauDtoList.get(i).getNiveau(),libellePeriode ,libelleAnnee) ;
            System.out.println("is_class_periode >>>>>>"+is_class_periode);

            Integer niveauOrdre= getNiveauId(matricule,libelleAnnee ,libellePeriode,idEcole);
            System.out.println("niveauOrdrew>>> "+niveauOrdre);
            if(niveauOrdre<=4) {
                is_clasCompoFr = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"02",libellePeriode ,libelleAnnee ) ;
                System.out.println("is_clasCompoFr >>>>>>"+is_clasCompoFr);
                moyenCompoFr = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"02",libellePeriode ,libelleAnnee ) ;
                pCompoFr = getMoyAvgMatiere("02",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
                m.setpCompoFr(pCompoFr);
                System.out.println("moyenCompoFr >>>>>>"+moyenCompoFr);

                is_clasOrthoGram = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"04",libellePeriode ,libelleAnnee) ;
                moyenOrthoGram = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"04",libellePeriode ,libelleAnnee) ;
                pOrthoGram = getMoyAvgMatiere("04",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
                m.setpOrthoGram(pOrthoGram);

                is_clasExpreOral = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"03",libellePeriode ,libelleAnnee) ;
                moyenExpreOral = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"03",libellePeriode ,libelleAnnee) ;
                pExpreOral = getMoyAvgMatiere("03",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
                m.setpExpreOral(pExpreOral);
            } else {
                System.out.println("niveauOrdrew Second cyle>>> "+niveauOrdre);
                is_classFran = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"01",libellePeriode ,libelleAnnee) ;
                System.out.println("is_classFran >>>>>>"+is_classFran);
                moyenFran = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"01",libellePeriode ,libelleAnnee) ;
                pFran = getMoyAvgMatiere("01",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
                m.setpFran(pFran);
                System.out.println("moyenFran >>>>>>"+moyenFran);

            }





            is_clasphiloso = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"26",libellePeriode ,libelleAnnee) ;
            moyenphiloso = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"26",libellePeriode ,libelleAnnee) ;
            philoso = getMoyAvgMatiere("26",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setPhiloso(philoso);

            is_clasAng = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"05",libellePeriode ,libelleAnnee) ;
            moyenAng = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"05",libellePeriode ,libelleAnnee) ;
            pAng = getMoyAvgMatiere("05",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpAng(pAng);

            is_clasMath = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"07",libellePeriode ,libelleAnnee) ;
            moyenMath = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"07",libellePeriode ,libelleAnnee) ;
            pMath = getMoyAvgMatiere("07",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpMath(pMath);

            is_clasPhysiq = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"08",libellePeriode ,libelleAnnee) ;
            moyenPhysiq = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"08",libellePeriode ,libelleAnnee) ;
            pPhysiq = getMoyAvgMatiere("08",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpPhysiq(pPhysiq);

            is_clasSVT = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"09",libellePeriode ,libelleAnnee) ;
            moyenSVT = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"09",libellePeriode ,libelleAnnee) ;
            pSVT = getMoyAvgMatiere("09",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpSVT(pSVT);


            is_clasHg = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"06",libellePeriode ,libelleAnnee) ;
            System.out.println("is_clasHg >>>>>>"+is_clasHg);
            moyenHg = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"06",libellePeriode ,libelleAnnee) ;
            pHg = getMoyAvgMatiere("06",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpHg(pHg);
            System.out.println("moyenHg >>>>>>"+moyenHg);

            is_clasLv2 = getIsClasseMatiereLV2(classeNiveauDtoList.get(i).getNiveau(),libellePeriode ,libelleAnnee) ;
            System.out.println("is_clasLv2 >>>>>>"+is_clasLv2);
            moyenLv2 = getMoyMatiereLV2(classeNiveauDtoList.get(i).getNiveau(),libellePeriode ,libelleAnnee) ;
            pLv2 = getMoyAvgLV2Matiere(libellePeriode ,libelleAnnee) ;
            m.setpLv2(pLv2);

            System.out.println("moyenLv2 >>>>>>"+moyenLv2);

            is_clasEdhc = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"11",libellePeriode ,libelleAnnee) ;
            moyenEdhc = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"11",libellePeriode ,libelleAnnee) ;
            pEdhc = getMoyAvgMatiere("11",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpEdhc(pEdhc);

            is_clasArplat = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"19",libellePeriode ,libelleAnnee) ;
            System.out.println("is_clasArplat >>>>>>"+is_clasArplat);
            moyenArplat = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"19",libellePeriode ,libelleAnnee) ;
            pArplat = getMoyAvgMatiere("19",libellePeriode ,libelleAnnee,idEcole ,Classe) ;
            m.setpArplat(pArplat);

            System.out.println("moyenArplat >>>>>>"+moyenArplat);
            is_clasTic = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"13",libellePeriode ,libelleAnnee) ;
            moyenTic = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"13",libellePeriode ,libelleAnnee) ;
            pTic = getMoyAvgMatiere("13",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpTic(pTic);

            is_clasConduite = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"12",libellePeriode ,libelleAnnee) ;
            moyenConduite = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"12",libellePeriode ,libelleAnnee) ;
            pConduite = getMoyAvgMatiere("12",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpConduite(pConduite);

            is_clasEps = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"10",libellePeriode ,libelleAnnee) ;
            moyenEps = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"10",libellePeriode ,libelleAnnee) ;
            pEps = getMoyAvgMatiere("10",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpEps(pEps);

            is_clasPericul = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"72",libellePeriode ,libelleAnnee) ;
            moyenPericul = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"72",libellePeriode ,libelleAnnee) ;
            pPericul = getMoyAvgMatiere("72",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpPericul(pPericul);

            is_clasMemoris = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"29",libellePeriode ,libelleAnnee) ;
            moyenMemoris = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"29",libellePeriode ,libelleAnnee) ;
            pMemoris = getMoyAvgMatiere("29",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpMemoris(pMemoris);

            is_clasFiq = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"30",libellePeriode ,libelleAnnee) ;
            moyenFiq = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"30",libellePeriode ,libelleAnnee) ;
            pFiq = getMoyAvgMatiere("30",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpFiq(pFiq);

            is_clasAsSirah = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"35",libellePeriode ,libelleAnnee) ;
            moyenAsSirah = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"35",libellePeriode ,libelleAnnee) ;
            pAsSirah = getMoyAvgMatiere("35",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpAsSirah(pAsSirah);

            is_clasAlQidah = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"37",libellePeriode ,libelleAnnee) ;
            moyenAlQidah = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"37",libellePeriode ,libelleAnnee) ;
            pAlQidah = getMoyAvgMatiere("37",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpAlQidah(pAlQidah);


            is_clasAlAklaq = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"38",libellePeriode ,libelleAnnee) ;
            moyenAlAklaq = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"38",libellePeriode ,libelleAnnee) ;
            pAlAklaq = getMoyAvgMatiere("38",libellePeriode ,libelleAnnee ,idEcole ,Classe) ;
            m.setpAlAklaq(pAlAklaq);

            m.setMoyenPericul(moyenPericul);
            m.setIs_clasPericul(is_clasPericul);

           m.setMoyenMemoris(moyenMemoris);
           m.setIs_clasMemoris(is_clasMemoris);

           m.setMoyenAlAklaq(moyenAlAklaq);
           m.setIs_clasAlAklaq(is_clasAlAklaq);

           m.setMoyenAlQidah(moyenAlQidah);
           m.setIs_clasAlQidah(is_clasAlQidah);

           m.setMoyenAsSirah(moyenAsSirah);
           m.setIs_clasAsSirah(is_clasAsSirah);

           m.setMoyenFiq(moyenFiq);
           m.setIs_clasFiq(is_clasFiq);
            m.setNiveau(niveau);
            m.setNom(nom);
            m.setPrenoms(prenoms);
            m.setOrdre_niveau(ordre_niveau);
            m.setRang(rang);
            m.setMoyenTrim(moyenTrim);

            m.setIs_clasCompoFr(is_clasCompoFr);
            m.setMoyenCompoFr(moyenCompoFr);

            m.setIs_clasExpreOral(is_clasExpreOral);
            m.setMoyenExpreOral(moyenExpreOral);

            m.setIs_clasOrthoGram(is_clasOrthoGram);
            m.setMoyenOrthoGram(moyenOrthoGram);

            m.setMoyenFran(moyenFran);
            m.setIs_classFran(is_classFran);

            m.setIs_clasphiloso(is_clasphiloso);
            m.setMoyenphiloso(moyenphiloso);

            m.setIs_clasAng(is_clasAng);
            m.setMoyenAng(moyenAng);

            m.setIs_clasMath(is_clasMath);
            m.setMoyenMath(moyenMath);

            m.setIs_clasPhysiq(is_clasPhysiq);
            m.setMoyenPhysiq(moyenPhysiq);

            m.setIs_clasSVT(is_clasSVT);
            m.setMoyenSVT(moyenSVT);

            m.setIs_clasHg(is_clasHg);
            m.setMoyenHg(moyenHg);

            m.setIs_clasLv2(is_clasLv2);
            m.setMoyenLv2(moyenLv2);

            m.setIs_clasEdhc(is_clasEdhc);
            m.setMoyenEdhc(moyenEdhc);

           m.setIs_clasArplat(is_clasArplat);
           m.setMoyenArplat(moyenArplat);

           m.setIs_clasTic(is_clasTic);
           m.setMoyenTic(moyenTic);

           m.setIs_clasConduite(is_clasConduite);
           m.setMoyenConduite(moyenConduite);

           m.setIs_clasEps(is_clasEps);
           m.setMoyenEps(moyenEps);

           m.setMatricule(matricule);
           m.setIs_class_periode(is_class_periode);
           m.setNom_ecole(nom_ecole);
           m.setPmoyenne(pmoyenne);
           resultatsListElevesDto.add(m);

        }

        return  resultatsListElevesDto ;
    }

    public  String  getAppreciaTrimes(String matricule,String libelleMatiere,String periode ,String libelleAnnee){
        try {
            String   appreci = (String) em.createQuery("select d.appreciation  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  appreci ;
        } catch (NoResultException e){
            return null;
        }

    }



    public  Double getMoyMatiere(String matricule,String libelleMatiere,String periode ,String libelleAnnee){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode =:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getBilanMoyMatiere(String libelleMatiere,String periode ,String libelleAnnee){
        try {
            Double   moyClasseF = (Double) em.createQuery("select avg(d.moyenne)   from DetailBulletin  d join d.bulletin b  where  d.matiereLibelle=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getMoyAvgMatiere(String libelleMatiere,String periode ,String libelleAnnee ,Long idEcole ,String classe){
        try {
            Double   moyClasseF = (Double) em.createQuery("select  Avg(d.moyenne)   from DetailBulletin  d join d.bulletin b  where  d.matiereCode=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and d.isRanked=:isclass and b.ecoleId=:idEcole and b.libelleClasse=: classe ")

                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("isclass", "O")
                    .setParameter("idEcole", idEcole)
                    .setParameter("classe", classe)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getMoyTotalAvgMatiere(String periode ,String libelleAnnee ,Long idEcole ,String classe ){
        try {
            Double   moyClasseF = (Double) em.createQuery("select  Avg(d.moyenne)   from DetailBulletin  d join d.bulletin b  where   b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and d.isRanked=:isclass and b.ecoleId=: idEcole and b.libelleClasse=: classe ")

                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("isclass", "O")
                    .setParameter("idEcole", idEcole)
                    .setParameter("classe", classe)

                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }


    public  Double getMoyAvgLV2Matiere(String periode ,String libelleAnnee){
        try {
            Double   moyClasseF = (Double) em.createQuery("select  Avg(d.moyenne)   from DetailBulletin  d join d.bulletin b  where (d.matiereLibelle=:libelleMatiere1 or d.matiereLibelle=:libelleMatiere2 )   and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and d.isRanked=:isclass ")

                    .setParameter("libelleMatiere1","ESPAGNOL")
                    .setParameter("libelleMatiere2","ALLEMAND")
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("isclass", "O")
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }




    public  Double getMoyMatiereLV2(String matricule ,String periode ,String libelleAnnee){
        String lv2 ;
        lv2= getMyLV2(matricule,periode ,libelleAnnee) ;
        Double   moyClasseF ;

        if( lv2!=null && lv2.equals("ESP") ) {
            try {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ESPAGNOL")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .getSingleResult();
            } catch (NoResultException e) {

                try {
                    moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee ")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","ALLEMAND")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .getSingleResult();

                } catch (NoResultException ex) {
                    moyClasseF =0D ;
                }




            }

        }
        else  if (lv2!=null && lv2.equals("ALL") ) {

            try {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ALLEMAND")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .getSingleResult();
            } catch (NoResultException e) {


                try {
                    moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","ESPAGNOL")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .getSingleResult();
                    System.out.println("moyClasseF ----"+moyClasseF);

                } catch (NoResultException ex) {
                    moyClasseF=0D ;
                }



            }


        }
        else
            moyClasseF=0D ;

        return  moyClasseF ;

    }


    public  String getMyLV2(String matricule, String periode ,String libelleAnnee){
System.out.println("Chercher lv2>>>>>>");
        try {
            String   lv2 = (String) em.createQuery("select b.lv2  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee ")
                    .setParameter("matricule",matricule)
                     .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            System.out.println("lv2 chercher est >>>>>>"+lv2);
            return  lv2 ;
        } catch (NoResultException e){
            System.out.println("Acun lv2 trouvé");
            return null ;
        }

    }

    public  String getIsClasseMatiereLV2(String matricule, String periode ,String libelleAnnee ){
        String lv2 ;
        lv2= getMyLV2(matricule,periode,libelleAnnee) ;
        String   isClasseMat = null;
        if( lv2!=null && lv2.equals("ESP") ) {
            try {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 )  " +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","21")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .getSingleResult();
            } catch (NoResultException e) {

                try {
                    isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 )  " +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","25")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .getSingleResult();
                } catch (NoResultException ex) {
                    isClasseMat= null ;
                }




            }


        } else  if ( lv2!=null && lv2.equals("ALL") ) {

            try {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 ) " +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","25")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .getSingleResult();

            } catch (NoResultException e) {

                try {
                    isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 ) " +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","21")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .getSingleResult();
                } catch (NoResultException ex) {
                    isClasseMat= null ;
                }





            }


        } else
            isClasseMat= null ;


        return  isClasseMat ;
    }


    public  String getIsClasseMatiere(String matricule,String libelleMatiere,String periode ,String libelleAnnee){

        try {
            String   isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode=:libelleMatiere " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  isClasseMat ;
        } catch (NoResultException e){
            return null ;
        }



    }

    public  String getIsClassePeriode(String matricule,String periode ,String libelleAnnee){
        try {
            String   isClassePeriode = (String) em.createQuery("select b.isClassed  from Bulletin b   where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  isClassePeriode ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getName(String matricule  ,String periode ,String libelleAnnee){
        try {
            String   nom = (String) em.createQuery("select b.nom  from Bulletin b   where b.matricule=:matricule and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  nom ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getSurName(String matricule ,String periode ,String libelleAnnee){
        try {
            String   Prenoms = (String) em.createQuery("select b.prenoms  from Bulletin b   where b.matricule=:matricule and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  Prenoms ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getNiveau(String matricule,String periode ,String libelleAnnee){
        try {
            String   niveau = (String) em.createQuery("select b.niveauLibelle  from Bulletin b  where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  niveau ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getNiveauId(String matricule, String annee,String periode,Long idEcole){
        try {
            Integer  num = (Integer) em.createQuery("select b.ordreNiveau from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  num ;
        } catch (NoResultException e){
            return 0;
        }
    }

    public  Integer getRang(String matricule,String periode ,String libelleAnnee){
        try {
            Integer   rang = (Integer) em.createQuery("select b.rang   from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  String  getNomEcole(String matricule,String periode ,String libelleAnnee ){
        try {
            String   nomEcole = (String) em.createQuery("select b.nomEcole  from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getOrdreNiveau(String matricule,String periode ,String libelleAnnee){
        try {
            Integer   rang = (Integer) em.createQuery("select b.ordreNiveau  from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  Double getMoyenTrimes(String matricule,String periode ,String libelleAnnee){
        try {
            Double   rang = (Double) em.createQuery("select b.moyGeneral   from Bulletin b  where b.matricule=:matricule and b.libellePeriode=:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0D ;
        }

    }









}
