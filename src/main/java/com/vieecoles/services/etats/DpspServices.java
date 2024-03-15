package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.NiveauDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DpspServices {
    @Inject
    EntityManager em;
    List<DspsDto> resultatsListElevesDto = new ArrayList<>();
    public List<DspsDto>  DspspDto(Long idEcole,String libellePeriode ,String libelleAnnee){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole  and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee   order by b.ordreNiveau ,b.nom asc, b.prenoms asc "
               , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("periode", libellePeriode)
                                 .setParameter("libelleAnnee", libelleAnnee)
                                 .getResultList() ;

        LongTableau= classeNiveauDtoList.size();


        String matricule ,nom,prenoms,niveau,is_clasCompoFr,is_clasOrthoGram,is_clasExpreOral,is_clasphiloso,is_clasAng,
                is_clasMath,is_clasPhysiq,is_clasSVT,is_clasHg,is_clasLv2,is_clasEdhc,is_clasArplat,is_clasTic ,
                is_clasConduite,is_clasEps,is_class_periode ,nom_ecole ,is_class_fr;
        Double moyenTrim,moyenCompoFr,moyenOrthoGram,moyenExpreOral,moyenphiloso,moyenAng,moyenMath,moyenPhysiq,moyenSVT,
                moyenHg,moyenLv2,moyenEdhc,moyenArplat,moyenTic,moyenConduite,moyenEps,moyen_fr;
        Integer rang , ordre_niveau ;

       System.out.println ("parallel started");
        long t = System.currentTimeMillis ();
        classeNiveauDtoList.stream ().parallel ().forEach (eleve-> getInfosDsps(eleve ,libellePeriode,libelleAnnee,idEcole));
        System.out.println ("parallel Duree =" + (System.currentTimeMillis () - t) / 1000l);

        /*System.out.println ("sequential started");
        long t = System.currentTimeMillis ();
        for (int k = 0; k < classeNiveauDtoList.size (); k++) {
            getInfosDsps(classeNiveauDtoList.get (k) ,libellePeriode,libelleAnnee,idEcole);
        }
        System.out.println ("sequential Duree =" + (System.currentTimeMillis () - t) / 1000l) ;*/





      /*     ExecutorService executorService = Executors.newFixedThreadPool (15);
        List<Void> resultList = new ArrayList<> ();
        Boolean retour;
        long   startTime =0l;
        try {
          startTime = System.currentTimeMillis ();
// Demmarage des processus
            List<Future<Void>> operationFutures = executorService.invokeAll (

                    classeNiveauDtoList.stream ()
                            .map (eleve -> (Callable<Void>) () -> {
                                getInfosDsps(eleve ,libellePeriode,libelleAnnee,idEcole) ;
                                //resultatsListElevesDto.add(resultat);
                                return null;
                            })
                            .collect (Collectors.toList ()) // Corrected here
            );

        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            // Arrêter l'ExecutorService

            long endTime = System.currentTimeMillis ();
            long executionTime = endTime - startTime;




            try {

                executorService.shutdown ();
              executorService.awaitTermination (30l, TimeUnit.MINUTES);
                System.out.println ("Processu terminés>> ");
                System.out.println ("Temps d'exécution total : " + executionTime / 1000l + " Secondes");
            } catch (InterruptedException e) {
                throw new RuntimeException (e);
            }

        }*/



        return  resultatsListElevesDto ;
    }
    @Transactional
public void getInfosDsps(NiveauDto eleve, String libellePeriode ,String libelleAnnee,Long idEcole){
        System.out.println ("Niveau >> "+eleve.getNiveau ());
    String matricule ,nom,prenoms,niveau,is_clasCompoFr,is_clasOrthoGram,is_clasExpreOral,is_clasphiloso,is_clasAng,
            is_clasMath,is_clasPhysiq,is_clasSVT,is_clasHg,is_clasLv2,is_clasEdhc,is_clasArplat,is_clasTic ,
            is_clasConduite,is_clasEps,is_class_periode ,nom_ecole ,is_class_fr;
    Double moyenTrim,moyenCompoFr,moyenOrthoGram,moyenExpreOral,moyenphiloso,moyenAng,moyenMath,moyenPhysiq,moyenSVT,
            moyenHg,moyenLv2,moyenEdhc,moyenArplat,moyenTic,moyenConduite,moyenEps,moyen_fr;
    Integer rang , ordre_niveau ;
    DspsDto m = new DspsDto();

    nom_ecole= getNomEcole(eleve.getNiveau() ,libellePeriode ,libelleAnnee,idEcole) ;

    moyenTrim = getMoyenTrimes(eleve.getNiveau() ,libellePeriode ,libelleAnnee ,idEcole) ;

    rang = getRang(eleve.getNiveau() ,libellePeriode ,libelleAnnee ,idEcole) ;

    ordre_niveau = getOrdreNiveau(eleve.getNiveau() ,libellePeriode ,libelleAnnee ,idEcole) ;

    nom = getName(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;

    prenoms= getSurName(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole);

    niveau = getNiveau(eleve.getNiveau() ,libellePeriode ,libelleAnnee ,idEcole) ;

    matricule= eleve.getNiveau();

    is_class_periode= getIsClassePeriode(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;



    is_class_fr = getIsClasseMatiere(eleve.getNiveau(),1L,libellePeriode ,libelleAnnee ,idEcole) ;

    moyen_fr = getMoyMatiere(eleve.getNiveau(),1L,libellePeriode ,libelleAnnee ,idEcole) ;



    is_clasCompoFr = getIsClasseMatiere(eleve.getNiveau(),2L,libellePeriode ,libelleAnnee ,idEcole) ;

    moyenCompoFr = getMoyMatiere(eleve.getNiveau(),2L,libellePeriode ,libelleAnnee ,idEcole) ;


    is_clasOrthoGram = getIsClasseMatiere(eleve.getNiveau(),4L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenOrthoGram = getMoyMatiere(eleve.getNiveau(),4L,libellePeriode ,libelleAnnee ,idEcole) ;

    is_clasExpreOral = getIsClasseMatiere(eleve.getNiveau(),3L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenExpreOral = getMoyMatiere(eleve.getNiveau(),3L,libellePeriode ,libelleAnnee ,idEcole) ;

    is_clasphiloso = getIsClasseMatiere(eleve.getNiveau(),26L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenphiloso = getMoyMatiere(eleve.getNiveau(),26L,libellePeriode ,libelleAnnee ,idEcole) ;

    is_clasAng = getIsClasseMatiere(eleve.getNiveau(),5L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenAng = getMoyMatiere(eleve.getNiveau(),5L,libellePeriode ,libelleAnnee ,idEcole) ;

    is_clasMath = getIsClasseMatiere(eleve.getNiveau(),7L,libellePeriode ,libelleAnnee ,idEcole) ;
        moyenMath = getMoyMatiere(eleve.getNiveau(),7L,libellePeriode ,libelleAnnee ,idEcole) ;

    is_clasPhysiq = getIsClasseMatiere(eleve.getNiveau(),8L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenPhysiq = getMoyMatiere(eleve.getNiveau(),8L,libellePeriode ,libelleAnnee ,idEcole ) ;

    is_clasSVT = getIsClasseMatiere(eleve.getNiveau(),9L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenSVT = getMoyMatiere(eleve.getNiveau(),9L,libellePeriode ,libelleAnnee ,idEcole) ;


    is_clasHg = getIsClasseMatiere(eleve.getNiveau(),6L,libellePeriode ,libelleAnnee ,idEcole) ;

    moyenHg = getMoyMatiere(eleve.getNiveau(),6L,libellePeriode ,libelleAnnee ,idEcole) ;


    is_clasLv2 = getIsClasseMatiereLV2(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;

    moyenLv2 = getMoyMatiereLV2(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;


    is_clasEdhc = getIsClasseMatiere(eleve.getNiveau(),11L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenEdhc = getMoyMatiere(eleve.getNiveau(),11L,libellePeriode ,libelleAnnee ,idEcole) ;

            /*is_clasArplat = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"19",libellePeriode ,libelleAnnee) ;
            System.out.println("is_clasArplat >>>>>>"+is_clasArplat);
            moyenArplat = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"19",libellePeriode ,libelleAnnee) ;
            System.out.println("moyenArplat >>>>>>"+moyenArplat);*/


    is_clasArplat = getIsClasseMatiereArtPlas(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;

    moyenArplat = getMoyMatiereArtPlas(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;




            /*is_clasTic = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"13",libellePeriode ,libelleAnnee) ;
            moyenTic = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"13",libellePeriode ,libelleAnnee) ;
*/
    is_clasTic = getIsClasseMatieretIC(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;
    moyenTic = getMoyMatiereTIC(eleve.getNiveau(),libellePeriode ,libelleAnnee ,idEcole) ;



    is_clasConduite = getIsClasseMatiere(eleve.getNiveau(),12L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenConduite = getMoyMatiere(eleve.getNiveau(),12L,libellePeriode ,libelleAnnee ,idEcole) ;

    is_clasEps = getIsClasseMatiere(eleve.getNiveau(),10L,libellePeriode ,libelleAnnee ,idEcole) ;
    moyenEps = getMoyMatiere(eleve.getNiveau(),10L,libellePeriode ,libelleAnnee ,idEcole) ;

    m.setNiveau(niveau);
    m.setNom(nom);
    m.setPrenoms(prenoms);
    m.setOrdre_niveau(ordre_niveau);
    m.setRang(rang);
    m.setMoyenTrim(moyenTrim);

    m.setIs_class_fr(is_class_fr);
    m.setMoyen_fr(moyen_fr);

    m.setIs_clasCompoFr(is_clasCompoFr);
    m.setMoyenCompoFr(moyenCompoFr);

    m.setIs_clasExpreOral(is_clasExpreOral);
    m.setMoyenExpreOral(moyenExpreOral);

    m.setIs_clasOrthoGram(is_clasOrthoGram);
    m.setMoyenOrthoGram(moyenOrthoGram);

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
    resultatsListElevesDto.add(m);
}

    public  Double getMoyMatiere(String matricule,Long libelleMatiere,String periode ,String libelleAnnee ,Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereId=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getMoyMatiereArtPlas(String matricule,String periode ,String libelleAnnee,Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode in ('19','36')  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getMoyMatiereTIC(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode IN ('13','27')  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }



    public  Double getMoyMatiereLV2(String matricule ,String periode ,String libelleAnnee,Long idEcole){
        String lv2 ;
        lv2= getMyLV2(matricule,periode ,libelleAnnee,idEcole) ;
        Double   moyClasseF ;

        if( lv2!=null && lv2.equals("ESP") ) {
            try {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereCode=:libelleMatiere1" +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","21")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .setParameter("idEcole", idEcole)
                        .getSingleResult();
            } catch (NoResultException e) {

                try {
                    moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereCode=:libelleMatiere1" +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","25")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .setParameter("idEcole", idEcole)
                            .getSingleResult();

                } catch (NoResultException ex) {
                    moyClasseF =0D ;
                }




            }

        }
        else  if (lv2!=null && lv2.equals("ALL") ) {

            try {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereCode=:libelleMatiere1" +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","25")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .setParameter("idEcole", idEcole)
                        .getSingleResult();
            } catch (NoResultException e) {


                try {
                    moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereCode=:libelleMatiere1" +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole ")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","21")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .setParameter("idEcole", idEcole)
                            .getSingleResult();


                } catch (NoResultException ex) {
                    moyClasseF=0D ;
                }



            }


        }
        else
            moyClasseF=0D ;

        return  moyClasseF ;

    }


    public  String getMyLV2(String matricule, String periode ,String libelleAnnee ,Long idEcole){

        try {
            String   lv2 = (String) em.createQuery("select b.lv2  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                     .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();

            return  lv2 ;
        } catch (NoResultException e){

            return null ;
        }

    }

    public  String getIsClasseMatiereLV2(String matricule, String periode ,String libelleAnnee ,Long idEcole){
        String lv2 ;
        lv2= getMyLV2(matricule,periode,libelleAnnee,idEcole) ;
        String   isClasseMat = null;
        if( lv2!=null && lv2.equals("ESP") ) {
            try {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 )  " +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee  and b.ecoleId=:idEcole")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","21")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .setParameter("idEcole", idEcole)
                        .getSingleResult();
            } catch (NoResultException e) {

                try {
                    isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 )  " +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee  and b.ecoleId=:idEcole")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","25")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .setParameter("idEcole", idEcole)
                            .getSingleResult();
                } catch (NoResultException ex) {
                    isClasseMat= null ;
                }




            }


        } else  if ( lv2!=null && lv2.equals("ALL") ) {

            try {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 ) " +
                                " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","25")
                        .setParameter("periode",periode)
                        .setParameter("libelleAnnee", libelleAnnee)
                        .setParameter("idEcole", idEcole)
                        .getSingleResult();

            } catch (NoResultException e) {

                try {
                    isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereCode=:libelleMatiere1 ) " +
                                    " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                            .setParameter("matricule",matricule)
                            .setParameter("libelleMatiere1","21")
                            .setParameter("periode",periode)
                            .setParameter("libelleAnnee", libelleAnnee)
                            .setParameter("idEcole", idEcole)
                            .getSingleResult();
                } catch (NoResultException ex) {
                    isClasseMat= null ;
                }





            }


        } else
            isClasseMat= null ;


        return  isClasseMat ;
    }


    public  String getIsClasseMatiere(String matricule,long libelleMatiere,String periode ,String libelleAnnee ,Long idEcole){
        try {

            String   isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereId=:libelleMatiere " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  isClasseMat ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getIsClasseMatiereArtPlas(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            String   isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode in ('19','36') " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  isClasseMat ;
        } catch (NoResultException e){
            return null ;
        }

    }



    public  String getIsClasseMatieretIC(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            String   isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode IN ('13','27') " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  isClasseMat ;
        } catch (NoResultException e){
            return null ;
        }

    }



    public  String getIsClassePeriode(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            String   isClassePeriode = (String) em.createQuery("select b.isClassed  from Bulletin b   where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  isClassePeriode ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getName(String matricule  ,String periode ,String libelleAnnee ,Long idEcole){
        try {
            String   nom = (String) em.createQuery("select b.nom  from Bulletin b   where b.matricule=:matricule and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  nom ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getSurName(String matricule ,String periode ,String libelleAnnee ,Long idEcole){
        try {
            String   Prenoms = (String) em.createQuery("select b.prenoms  from Bulletin b   where b.matricule=:matricule and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  Prenoms ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getNiveau(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            String   niveau = (String) em.createQuery("select b.niveauLibelle  from Bulletin b  where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  niveau ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getRang(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            Integer   rang = (Integer) em.createQuery("select b.rang   from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0 ;
        }

    }
    public  String  getNomEcole(String matricule,String periode ,String libelleAnnee ,Long idEcole){

        try {
            String   nomEcole = (String) em.createQuery("select b.nomEcole  from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getOrdreNiveau(String matricule,String periode ,String libelleAnnee,Long idEcole){
        try {
            Integer   rang = (Integer) em.createQuery("select b.ordreNiveau  from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  Double getMoyenTrimes(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            Double   rang = (Double) em.createQuery("select b.moyGeneral   from Bulletin b  where b.matricule=:matricule and b.libellePeriode=:periode " +
                            " and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0D ;
        }

    }







}
