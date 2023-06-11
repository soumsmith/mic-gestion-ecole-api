package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.NiveauDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DpspServices {
    @Inject
    EntityManager em;

    public List<DspsDto>  DspspDto(Long idEcole,String libellePeriode){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole and b.ordreNiveau = 4 and b.libellePeriode=:periode  order by b.nom asc, b.prenoms asc ,b.ordreNiveau"
               , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("periode", libellePeriode)
                                 .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<DspsDto> resultatsListElevesDto = new ArrayList<>();

        String matricule ,nom,prenoms,niveau,is_clasCompoFr,is_clasOrthoGram,is_clasExpreOral,is_clasphiloso,is_clasAng,
                is_clasMath,is_clasPhysiq,is_clasSVT,is_clasHg,is_clasLv2,is_clasEdhc,is_clasArplat,is_clasTic ,
                is_clasConduite,is_clasEps,is_class_periode ,nom_ecole;
        Double moyenTrim,moyenCompoFr,moyenOrthoGram,moyenExpreOral,moyenphiloso,moyenAng,moyenMath,moyenPhysiq,moyenSVT,
                moyenHg,moyenLv2,moyenEdhc,moyenArplat,moyenTic,moyenConduite,moyenEps;
        Integer rang , ordre_niveau ;

        for (int i=0; i< LongTableau;i++) {
            DspsDto m = new DspsDto();

            nom_ecole= getNomEcole(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode) ;
            System.out.println("nom_ecole >>>>>>"+nom_ecole);
            moyenTrim = getMoyenTrimes(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode) ;
            System.out.println("moyenTrim >>>>>>"+moyenTrim);
            rang = getRang(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode) ;
            System.out.println("rang >>>>>>"+rang);
            ordre_niveau = getOrdreNiveau(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode) ;
            System.out.println("ordre_niveau >>>>>>"+ordre_niveau);
            nom = getName(classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("nom >>>>>>"+nom);
            prenoms= getSurName(classeNiveauDtoList.get(i).getNiveau(),libellePeriode);
            System.out.println("prenoms >>>>>>"+prenoms);
            niveau = getNiveau(classeNiveauDtoList.get(i).getNiveau() ,libellePeriode) ;
            System.out.println("niveau >>>>>>"+niveau);
            matricule= classeNiveauDtoList.get(i).getNiveau();
            System.out.println("matricule >>>>>>"+matricule);
            is_class_periode= getIsClassePeriode(classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("is_class_periode >>>>>>"+is_class_periode);

            is_clasCompoFr = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"COMPOSITION FRANCAISE",libellePeriode) ;
            System.out.println("is_clasCompoFr >>>>>>"+is_clasCompoFr);
            moyenCompoFr = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"COMPOSITION FRANCAISE",libellePeriode) ;
            System.out.println("moyenCompoFr >>>>>>"+moyenCompoFr);

            is_clasOrthoGram = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"ORTHOGRAPHE ET GRAMMAIRE",libellePeriode) ;
            moyenOrthoGram = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"ORTHOGRAPHE ET GRAMMAIRE",libellePeriode) ;

            is_clasExpreOral = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"EXPRESSION ORALE",libellePeriode) ;
            moyenExpreOral = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"EXPRESSION ORALE",libellePeriode) ;

            is_clasphiloso = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"PHILOSOPHIE",libellePeriode) ;
            moyenphiloso = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"PHILOSOPHIE",libellePeriode) ;

            is_clasAng = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"ANGLAIS",libellePeriode) ;
            moyenAng = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"ANGLAIS",libellePeriode) ;

            is_clasMath = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"MATHEMATIQUES",libellePeriode) ;
            moyenMath = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"MATHEMATIQUES",libellePeriode) ;

            is_clasPhysiq = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"PHYSIQUE-CHIMIE",libellePeriode) ;
            moyenPhysiq = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"PHYSIQUE-CHIMIE",libellePeriode) ;

            is_clasSVT = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"SVT",libellePeriode) ;
            moyenSVT = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"SVT",libellePeriode) ;


            is_clasHg = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"HISTOIRE-GEOGAPHIE",libellePeriode) ;
            System.out.println("is_clasHg >>>>>>"+is_clasHg);
            moyenHg = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"HISTOIRE-GEOGAPHIE",libellePeriode) ;
            System.out.println("moyenHg >>>>>>"+moyenHg);

            is_clasLv2 = getIsClasseMatiereLV2(classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("is_clasLv2 >>>>>>"+is_clasLv2);
            moyenLv2 = getMoyMatiereLV2(classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("moyenLv2 >>>>>>"+moyenLv2);

            is_clasEdhc = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"EDHC",libellePeriode) ;
            moyenEdhc = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"EDHC",libellePeriode) ;

            is_clasArplat = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"ARTS PLASTIQUES",libellePeriode) ;
            System.out.println("is_clasArplat >>>>>>"+is_clasArplat);
            moyenArplat = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"ARTS PLASTIQUES",libellePeriode) ;
            System.out.println("moyenArplat >>>>>>"+moyenArplat);
            is_clasTic = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"INFORMATIQUE",libellePeriode) ;
            moyenTic = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"INFORMATIQUE",libellePeriode) ;

            is_clasConduite = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"CONDUITE",libellePeriode) ;
            moyenConduite = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"CONDUITE",libellePeriode) ;

            is_clasEps = getIsClasseMatiere(classeNiveauDtoList.get(i).getNiveau(),"EPS",libellePeriode) ;
            moyenEps = getMoyMatiere(classeNiveauDtoList.get(i).getNiveau(),"EPS",libellePeriode) ;

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

        return  resultatsListElevesDto ;
    }


    public  Double getMoyMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }




    public  Double getMoyMatiereLV2(String matricule ,String periode ){
        String lv2 ;
        lv2= getMyLV2(matricule,periode) ;
        Double   moyClasseF ;

        if( lv2!=null && lv2.equals("ESP") ) {
            try {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ESPAGNOL")
                        .setParameter("periode",periode)
                        .getSingleResult();
            } catch (NoResultException e) {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ALLEMAND")
                        .setParameter("periode",periode)
                        .getSingleResult();

            }

        }
        else  if (lv2!=null && lv2.equals("ALL") ) {

            try {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ALLEMAND")
                        .setParameter("periode",periode)
                        .getSingleResult();
            } catch (NoResultException e) {
                moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere1" +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ESPAGNOL")
                        .setParameter("periode",periode)
                        .getSingleResult();
            }


        }
        else
            moyClasseF=0D ;

        return  moyClasseF ;

    }


    public  String getMyLV2(String matricule, String periode ){
System.out.println("Chercher lv2>>>>>>");
        try {
            String   lv2 = (String) em.createQuery("select b.lv2  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                     .setParameter("periode",periode)
                    .getSingleResult();
            System.out.println("lv2 chercher est >>>>>>"+lv2);
            return  lv2 ;
        } catch (NoResultException e){
            System.out.println("Acun lv2 trouvé");
            return null ;
        }

    }

    public  String getIsClasseMatiereLV2(String matricule, String periode ){
        String lv2 ;
        lv2= getMyLV2(matricule,periode) ;
        String   isClasseMat = null;
        if( lv2!=null && lv2.equals("ESP") ) {
            try {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereLibelle=:libelleMatiere1 )  " +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ESPAGNOL")
                        .setParameter("periode",periode)
                        .getSingleResult();
            } catch (NoResultException e) {

                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereLibelle=:libelleMatiere1 )  " +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ALLEMAND")
                        .setParameter("periode",periode)
                        .getSingleResult();

            }


        } else  if ( lv2!=null && lv2.equals("ALL") ) {

            try {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereLibelle=:libelleMatiere1 ) " +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ALLEMAND")
                        .setParameter("periode",periode)
                        .getSingleResult();

            } catch (NoResultException e) {
                isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and (d.matiereLibelle=:libelleMatiere1 ) " +
                                " and b.libellePeriode=:periode ")
                        .setParameter("matricule",matricule)
                        .setParameter("libelleMatiere1","ESPAGNOL")
                        .setParameter("periode",periode)
                        .getSingleResult();

            }


        } else
            isClasseMat= null ;


        return  isClasseMat ;
    }


    public  String getIsClasseMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   isClasseMat = (String) em.createQuery("select d.isRanked  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  isClasseMat ;
        } catch (NoResultException e){
            return null ;
        }

    }
    public  String getIsClassePeriode(String matricule,String periode ){
        try {
            String   isClassePeriode = (String) em.createQuery("select b.isClassed  from Bulletin b   where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  isClassePeriode ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getName(String matricule  ,String periode){
        try {
            String   nom = (String) em.createQuery("select b.nom  from Bulletin b   where b.matricule=:matricule and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nom ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getSurName(String matricule ,String periode){
        try {
            String   Prenoms = (String) em.createQuery("select b.prenoms  from Bulletin b   where b.matricule=:matricule and b.libellePeriode=:periode")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  Prenoms ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getNiveau(String matricule,String periode ){
        try {
            String   niveau = (String) em.createQuery("select b.niveauLibelle  from Bulletin b  where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  niveau ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getRang(String matricule,String periode ){
        try {
            Integer   rang = (Integer) em.createQuery("select b.rang   from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0 ;
        }

    }
    public  String  getNomEcole(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.nomEcole  from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getOrdreNiveau(String matricule,String periode ){
        try {
            Integer   rang = (Integer) em.createQuery("select b.ordreNiveau  from Bulletin b  where b.matricule=:matricule and b.libellePeriode =:periode " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  Double getMoyenTrimes(String matricule,String periode ){
        try {
            Double   rang = (Double) em.createQuery("select b.moyGeneral   from Bulletin b  where b.matricule=:matricule and b.libellePeriode=:periode " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return 0D ;
        }

    }







}
