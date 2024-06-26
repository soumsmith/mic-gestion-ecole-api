package com.vieecoles.services.etats;

import com.vieecoles.InforPersonLivretScolaire;
import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.BulletinSpiderDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LivretSpiderServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;
@Transactional
    public List<InforPersonLivretScolaire>  bulletinInfos(Long idEcole , String libelleAnnee , String libelleTrimestre , String libelleClasse){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                " and b.libelleClasse=:libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("libelleClasse", libelleClasse)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<InfosPersoBulletins> resultatsListElevesDto = new ArrayList<>();

        Double TmoyFr,TcoefFr,TmoyCoefFr,TmoyCoefEMR = 0d, moy_1er_trim ,moy_2eme_trim,moy_3eme_trim ,
                TmoyFrPremier,TmoyFrDeuxieme,TmoyFrAnn = null,TmoyEMRANN,TmoyCoefFrPermier,TmoyCoefFrDeuxieme,
                TmoyCoefEMRPremier, TmoyCoefEMRDeuxieme ,TrangFrAnnuel1,TrangFrPremier1,TrangFrDeuxieme1;

       Integer rang_1er_trim ,rang_2eme_trim ,rang_3eme_trim ;
        int TrangEMR , TrangFr = 0,TrangFrAnnuel = 0,TrangFrPremier = 0,TrangFrDeuxieme = 0,TrangEMRPremier = 0,TrangEMRDeuxieme = 0;
      String  codeEcole,is_class_1er_trim,is_class_2e_trim,is_class_3e_trim,DateNaiss= null ,matricule ;



        List<InforPersonLivretScolaire> mlist = new ArrayList<>();
        for (int i=0; i< LongTableau;i++) {

            BulletinSpiderDto m = new BulletinSpiderDto();
            String b ;
            InforPersonLivretScolaire l = new InforPersonLivretScolaire();
            Inscriptions myIns= new Inscriptions() ;
            ecole myEcole= new ecole() ;
            String idBulletin ;

            idBulletin = getIdBulletin(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
            matricule = classeNiveauDtoList.get(i).getNiveau() ;
            System.out.println("idBulletin "+idBulletin);
            TcoefFr = calculcoefFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
            System.out.println("TcoefFr "+TcoefFr);
            TmoyCoefFrPermier= calculMoycoefFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            TmoyCoefFrDeuxieme= calculMoycoefFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;

           TmoyCoefEMRPremier = calculMoycoefEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole);
            TmoyCoefEMRDeuxieme = calculMoycoefEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole);

            TmoyEMRANN = ( TmoyCoefEMRPremier + (TmoyCoefEMRDeuxieme * 2) + (TmoyCoefEMR*2) )/5 ;
            TrangFrAnnuel1 = calculRangFranAnnuel(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
            TrangFrPremier1 = calculRangFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
           TrangFrDeuxieme1 = calculRangFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;
            if(TrangFrAnnuel1 !=null)
                TrangFrAnnuel = TrangFrAnnuel1.intValue() ;
            if(TrangFrPremier1 !=null)
                TrangFrPremier = TrangFrPremier1.intValue() ;

            if(TrangFrDeuxieme1 !=null)
                TrangFrDeuxieme = TrangFrDeuxieme1.intValue() ;

            TmoyFrPremier= (TcoefFr == null? TmoyCoefFrPermier/1: TmoyCoefFrPermier/TcoefFr );
            TmoyFrDeuxieme  =(TcoefFr == null?TmoyCoefFrDeuxieme/1: TmoyCoefFrDeuxieme/TcoefFr) ;

            TrangEMRPremier =calculRangEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            TrangEMRDeuxieme =calculRangEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;

            Double TrangFr1 = calculRangFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
             TmoyCoefFr = calculMoycoefFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
            System.out.println("TmoyCoefFr "+TmoyCoefFr);
            //System.out.println("Moyene en Francais: "+TmoyCoefFr/4);
           TrangFr1 = calculRangFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
            System.out.println("TrangFr1 "+TrangFr1);
            if(TrangFr1 !=null)
                TrangFr = TrangFr1.intValue() ;

           TmoyFr = calculTMoyFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole);
            TrangEMR =calculRangEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
            System.out.println("TrangEMR "+TrangEMR);
            TmoyCoefEMR = calculMoycoefEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole);
            System.out.println("TmoyCoefEMR "+TmoyCoefEMR);

             moy_1er_trim = calculmoyenTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            System.out.println("moy_1er_trim "+moy_1er_trim);
             moy_2eme_trim = calculmoyenTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;
            moy_3eme_trim = calculmoyenTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Troisième Trimestre",idEcole) ;

             rang_1er_trim = calculRangTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            rang_2eme_trim = calculRangTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;
           rang_3eme_trim = calculRangTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Troisième Trimestre",idEcole) ;

             is_class_1er_trim = calculIsClassTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            System.out.println("is_class_1er_trim "+is_class_1er_trim);
             is_class_2e_trim = calculIsClassTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;

            is_class_3e_trim = calculIsClassTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Troisième Trimestre",idEcole) ;
            System.out.println("is_class_3e_trim "+is_class_3e_trim);
            if(is_class_1er_trim.equals("N")) {
                TmoyCoefFrPermier=0D ;
            }

            if(is_class_2e_trim.equals("N")) {
                TmoyCoefFrDeuxieme=0D ;

            }
            if(is_class_3e_trim.equals("N")||TmoyCoefFr==null) {
                TmoyCoefFr=0D ;
            }

            if(TcoefFr!=null)
                TmoyFrAnn= ( (TmoyCoefFrPermier/TcoefFr) + (TmoyCoefFrDeuxieme/TcoefFr)*2 + (TmoyCoefFr/TcoefFr)*2 )/5 ;



           l= getIdBulletinFromInfosBull(idBulletin);

            if(l==null) {
                InforPersonLivretScolaire k = new InforPersonLivretScolaire();
                k.setTmoyFr(TmoyCoefFr==null? null:TmoyCoefFr/TcoefFr);
                k.setTrangFr(TrangFr);
                k.setTrangEMR(TrangEMR);
                k.setTmoyCoefEMR(TmoyCoefEMR);
                myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
                //System.out.println("myEcole "+myEcole.toString());
                myIns = inscriptionService.checkInscrit(idEcole,classeNiveauDtoList.get(i).getNiveau(),1L);
                parametre mpara = new parametre();
                mpara = parametre.findById(1L) ;
                k.setAmoirie(mpara.getImage() );
                k.setCodeEcole(myEcole.getEcolecode());
                k.setBg(myEcole.getFiligramme());
                k.setLogo(myEcole.getLogoBlob());
                k.setPhoto_eleve(myIns.getPhoto_eleve());
                k.setTcoefFr(TcoefFr);
                k.setTmoyCoefFr(TmoyCoefFr);
                k.setIs_class_1er_trim(is_class_1er_trim);
                k.setIs_class_2e_trim(is_class_2e_trim);
                k.setIs_class_3e_trim(is_class_3e_trim);
                k.setRang_1er_trim(rang_1er_trim);
                k.setRang_2eme_trim(rang_2eme_trim);
                k.setRang_3eme_trim(rang_3eme_trim);
                k.setMoy_1er_trim(moy_1er_trim);
                k.setMoy_2eme_trim(moy_2eme_trim);
                k.setMoy_3eme_trim(moy_3eme_trim);
                k.setIdBulletin(idBulletin);
                k.setTmoyFrPremier(TmoyFrPremier);
                k.setTmoyFrDeuxieme(TmoyFrDeuxieme);
                k.setTmoyFrAnn(TmoyFrAnn);
                k.setTmoyEMRANN(TmoyEMRANN);
                k.setTmoyCoefEMRPremier(TmoyCoefEMRPremier);
                k.setTmoyCoefEMRDeuxieme(TmoyCoefEMRDeuxieme);
                k.setTrangFrAnnuel(TrangFrAnnuel);
                k.setTrangFrPremier(TrangFrPremier);
                k.setTrangFrDeuxieme(TrangFrDeuxieme);
                k.setTrangEMRPremier(TrangEMRPremier);
                k.setTrangEMRDeuxieme(TrangEMRDeuxieme);
                k.setDateNaiss(DateNaiss);
                k.setMatriculeEleve(matricule);
                k.persist();
                mlist.add(k);
            } else {
               // System.out.println(l.toString());
                l.setTmoyFr(TmoyCoefFr==null? null:TmoyCoefFr/TcoefFr);
                l.setTrangFr(TrangFr);
                l.setTrangEMR(TrangEMR);
                l.setTmoyCoefEMR(TmoyCoefEMR);
                myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
                //System.out.println("myEcole "+myEcole.toString());
                myIns = inscriptionService.checkInscrit(idEcole,classeNiveauDtoList.get(i).getNiveau(),1L);
                parametre mpara = new parametre();
                mpara = parametre.findById(1L) ;
                l.setAmoirie(mpara.getImage() );
                l.setCodeEcole(myEcole.getEcolecode());
                l.setBg(myEcole.getFiligramme());
                l.setLogo(myEcole.getLogoBlob());
                l.setPhoto_eleve(myIns.getPhoto_eleve());
                l.setTcoefFr(TcoefFr);
                l.setTmoyCoefFr(TmoyCoefFr);
                l.setIs_class_1er_trim(is_class_1er_trim);
                l.setIs_class_2e_trim(is_class_2e_trim);
                l.setIs_class_3e_trim(is_class_3e_trim);
                l.setRang_1er_trim(rang_1er_trim);
                l.setRang_2eme_trim(rang_2eme_trim);
                l.setRang_3eme_trim(rang_3eme_trim);
                l.setMoy_1er_trim(moy_1er_trim);
                l.setMoy_2eme_trim(moy_2eme_trim);
                l.setMoy_3eme_trim(moy_3eme_trim);
                l.setIdBulletin(idBulletin);
                l.setTmoyFrPremier(TmoyFrPremier);
                l.setTmoyFrDeuxieme(TmoyFrDeuxieme);
                l.setTmoyFrAnn(TmoyFrAnn);
                l.setTmoyEMRANN(TmoyEMRANN);
                l.setTmoyCoefEMRPremier(TmoyCoefEMRPremier);
                l.setTmoyCoefEMRDeuxieme(TmoyCoefEMRDeuxieme);
                l.setTrangFrAnnuel(TrangFrAnnuel);
                l.setTrangFrPremier(TrangFrPremier);
                l.setTrangFrDeuxieme(TrangFrDeuxieme);
                l.setTrangEMRPremier(TrangEMRPremier);
                l.setTrangEMRDeuxieme(TrangEMRDeuxieme);
                l.setDateNaiss(DateNaiss);
                l.setMatriculeEleve(matricule);

                mlist.add(l);
            }

        }

        return  mlist ;
    }

    public  Double calculTMoyFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }


    public  String getIdBulletin(String matricule, String annee,String periode,Long idEcole){
        try {
            String  id = (String) em.createQuery("select b.id from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  id ;
        } catch (NoResultException e){
            return null ;
        }
    }

    public  InforPersonLivretScolaire getIdBulletinFromInfosBull(String idBulletin){
        try {
            InforPersonLivretScolaire  d = (InforPersonLivretScolaire) em.createQuery("select b from InforPersonLivretScolaire b where b.idBulletin=:idBulletin ")
                    .setParameter("idBulletin",idBulletin)
                    .getSingleResult();
            return  d;
        } catch (NoResultException e){
            return null ;
        }
    }


    public  Double calculcoefFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculcoefEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('FIQ','AS-SIRAH','AL-AQIDAH','AL-AKHLÂQ','MEMORISATION') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculMoycoefFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef*d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculMoycoefEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select d.moyenne from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:libelle ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .setParameter("libelle","EMR")
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculRangFranAnnuel(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select AVG(d.rangAn) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculRangFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select AVG(d.rang) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }



    public  Integer calculRangEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Integer  moyTfr = (Integer) em.createQuery("select d.rang from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:libelle ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .setParameter("libelle","EMR")
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0 ;
        }
    }



    public  Double calculmoyenTrimesPasse(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select b.moyGeneral from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Integer calculRangTrimesPasse(String matricule, String annee,String periode,Long idEcole){
        try {
            Integer  moyTfr = (Integer) em.createQuery("select b.rang from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0;
        }
    }




    public  String calculIsClassTrimesPasse(String matricule, String annee,String periode,Long idEcole){
        try {
            String  isclass = (String) em.createQuery("select b.isClassed from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  isclass ;
        } catch (NoResultException e){
            return null;
        }
    }



    public List<BoursierDto> getBoursierParNiveauBoursier(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        List<BoursierDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<BoursierDto> q= em.createQuery("select new com.vieecoles.dto.BoursierDto(o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.lieuNaissance,o.niveau,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and  o.niveau=:niveau and o.boursier=:boursier and o.libellePeriode=:periode and o.anneeLibelle=:annee", BoursierDto.class);
            classeNiveauDtoList = q.setParameter("boursier","B")

                                  .setParameter("idEcole",idEcole)
                                  .setParameter("niveau",niveau)
                                    .setParameter("annee", libelleAnnee)
                                    .setParameter("periode", libelleTrimestre)
                                  .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public List<BoursierDto> getBoursierParNiveauDemiBoursier(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        List<BoursierDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<BoursierDto> q= em.createQuery("select new com.vieecoles.dto.BoursierDto(o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.lieuNaissance,o.niveau ,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and  o.niveau=:niveau and o.boursier=:boursier and  o.libellePeriode=:periode and o.anneeLibelle=:annee", BoursierDto.class);
            classeNiveauDtoList = q.setParameter("boursier","1/2B")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }


}
