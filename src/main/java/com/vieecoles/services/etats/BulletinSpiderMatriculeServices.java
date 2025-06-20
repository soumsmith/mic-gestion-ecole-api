package com.vieecoles.services.etats;

import com.vieecoles.dto.BulletinSpiderDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Ecole;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BulletinSpiderMatriculeServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;
@Transactional
    public List<InfosPersoBulletins>  bulletinInfos(Long idEcole ,String libelleAnnee , String libelleTrimestre ,String matricule ,boolean logoPosi ,boolean filigranne){
        int LongTableau;
         AnneeScolaire mainAnnee = new AnneeScolaire() ;
         Ecole mynewEcole = new Ecole() ;
    mynewEcole = Ecole.findById(idEcole);
    mainAnnee =findMainAnneeByEcole(mynewEcole) ;
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
    TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
            " and b.matricule=:matricule order by b.nom , b.prenoms", NiveauDto.class);
    classeNiveauDtoList = q.setParameter("idEcole", idEcole)
            .setParameter("annee", libelleAnnee)
            .setParameter("periode", libelleTrimestre)
            .setParameter("matricule", matricule)
            .getResultList() ;


        LongTableau= classeNiveauDtoList.size();
        List<InfosPersoBulletins> resultatsListElevesDto = new ArrayList<>();

        Double TmoyFr,TcoefFr,TmoyCoefFr,TmoyCoefEMR, moy_1er_trim ,moy_2eme_trim,moy_3eme_trim ,TmoyCoefEMR1 ,TcoefEMR ,TmoyCoefReligio = null,TmoyCoefReligio1,
            TcoefReligion;;
       Integer rang_1er_trim ,rang_2eme_trim ,rang_3eme_trim ;
        int TrangEMR = 0, TrangFr = 0;
      String  codeEcole,is_class_1er_trim,is_class_2e_trim,is_class_3e_trim ;



        List<InfosPersoBulletins> mlist = new ArrayList<>();
        for (int i=0; i< LongTableau;i++) {

            BulletinSpiderDto m = new BulletinSpiderDto();
            String b ;
            InfosPersoBulletins l = new InfosPersoBulletins();
            Inscriptions myIns= new Inscriptions() ;
            ecole myEcole= new ecole() ;
            String idBulletin ;

            idBulletin = getIdBulletin(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;

            TcoefEMR= calculcoefEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;

            TcoefReligion = calculcoefReligion(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole);
            TcoefFr = calculcoefFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
             Double TrangEMR1 = 0d ;

            Double TrangFr1 = calculRangFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;
             TmoyCoefFr = calculMoycoefFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;

           TrangFr1 = calculRangFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;

            if(TrangFr1 !=null)
                TrangFr = TrangFr1.intValue() ;

           TmoyFr = calculTMoyFran(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole);

            TrangEMR1 =calculRangEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole) ;

            if(TrangEMR1 !=null)
                TrangEMR = TrangEMR1.intValue() ;
            TmoyCoefEMR1 = calculMoycoefEMR(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole);
            TmoyCoefReligio1 = calculMoycoefReligion(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,libelleTrimestre,idEcole);

            TmoyCoefEMR = (TcoefEMR ==0.0? 0.0: (TmoyCoefEMR1)/TcoefEMR);
            if(TcoefEMR==0.0 && TcoefReligion==0.0) {
                TmoyCoefReligio= 0.0 ;
            } else if (TcoefEMR==0.0&& TcoefReligion!=0.0) {
                TmoyCoefReligio = (TmoyCoefReligio1)/(TcoefReligion) ;
            } else if(TcoefEMR!=0.0 && TcoefReligion==0.0) {
                TmoyCoefReligio = TmoyCoefEMR ;
            } else if (TcoefEMR!=0.0 && TcoefReligion!=0.0) {
                TmoyCoefReligio = (((TmoyCoefReligio1)/(TcoefReligion))+TmoyCoefEMR) /2L;
            }


             moy_1er_trim = calculmoyenTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;

             moy_2eme_trim = calculmoyenTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;
            moy_3eme_trim = calculmoyenTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Troisième Trimestre",idEcole) ;

             rang_1er_trim = calculRangTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            rang_2eme_trim = calculRangTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;
           rang_3eme_trim = calculRangTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Troisième Trimestre",idEcole) ;

             is_class_1er_trim = calculIsClassTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Premier Trimestre",idEcole) ;
            is_class_2e_trim = calculIsClassTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Deuxième Trimestre",idEcole) ;

            is_class_3e_trim = calculIsClassTrimesPasse(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee,"Troisième Trimestre",idEcole) ;
             l= getIdBulletinFromInfosBull(idBulletin);

            if(l==null) {
                InfosPersoBulletins k = new InfosPersoBulletins();
                k.setTmoyFr(TcoefFr==null ||TcoefFr==0? null:TmoyCoefFr/TcoefFr);
                k.setTrangFr(TrangFr);
                k.setTrangEMR(TrangEMR);
                k.setTmoyCoefEMR(TmoyCoefEMR);
                myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
                myIns = inscriptionService.checkInscrit(idEcole,classeNiveauDtoList.get(i).getNiveau(),mainAnnee.getId());
                parametre mpara = new parametre();
                mpara = parametre.findById(1L) ;
                if(logoPosi){
                    k.setAmoirie(mpara.getImage() );
                    k.setLogo(myEcole.getLogoBlob());
                } else {
                    k.setAmoirie(myEcole.getLogoBlob());
                    k.setLogo(mpara.getImage());
                }

                k.setCodeEcole(myEcole.getEcolecode());
                if(filigranne)
                k.setBg(myEcole.getFiligramme());
                else
                    k.setBg(null);
                if(myIns!=null && myIns.getCheminphoto()!=null) {
                    k.setCheminphoto(myIns.getCheminphoto());
                }
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
                k.setTmoyCoefReligio(TmoyCoefReligio);
                k.setIdBulletin(idBulletin);
                k.persist();

                mlist.add(k);
            } else {

                l.setTmoyFr(TcoefFr==null||TcoefFr==0? null:TmoyCoefFr/TcoefFr);
                l.setTrangFr(TrangFr);
                l.setTrangEMR(TrangEMR);
                l.setTmoyCoefEMR(TmoyCoefEMR);
                myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
                myIns = inscriptionService.checkInscrit(idEcole,classeNiveauDtoList.get(i).getNiveau(),mainAnnee.getId());

                parametre mpara = new parametre();
                mpara = parametre.findById(1L) ;
                if(logoPosi){
                    l.setAmoirie(mpara.getImage() );
                    l.setLogo(myEcole.getLogoBlob());
                } else {
                    l.setAmoirie(myEcole.getLogoBlob());
                    l.setLogo(mpara.getImage());
                }
                if(filigranne)
                    l.setBg(myEcole.getFiligramme());
                else
                    l.setBg(null);

                l.setCodeEcole(myEcole.getEcolecode());
                if(myIns!=null && myIns.getCheminphoto()!=null) {
                    l.setCheminphoto(myIns.getCheminphoto());
                }

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
                l.setTmoyCoefReligio(TmoyCoefReligio);
                mlist.add(l);
            }

        }

        return  mlist ;
    }

    public  Double calculTMoyFran(String matricule, String annee,String periode,Long idEcole){
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        if(niveauOrdre<=4){
            try {
                Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .getSingleResult();
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        } else {
            try {
                Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle=:matiere")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
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

    public  InfosPersoBulletins getIdBulletinFromInfosBull(String idBulletin){
        try {
            InfosPersoBulletins  d = (InfosPersoBulletins) em.createQuery("select b from InfosPersoBulletins b where b.idBulletin=:idBulletin ")
                    .setParameter("idBulletin",idBulletin)
                    .getSingleResult();
            return  d;
        } catch (NoResultException e){
            return null ;
        }
    }


    public  Double calculcoefFran(String matricule, String annee,String periode,Long idEcole){
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        System.out.println("annee "+annee);
        System.out.println("matricule "+matricule);
        System.out.println("periode "+periode);
        System.out.println("idEcole "+idEcole);
        if(niveauOrdre<=4) {
            try {
                Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .getSingleResult();
                System.out.println("moyTfr>>>>> "+moyTfr);
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        } else {
            try {
                Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:matiere ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        }

    }

    public  Double calculcoefEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode in ('30','35','37','38','29','32') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            if(moyTfr==null) {
                return 0D ;
            } else
                return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Double calculcoefReligion(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode not in ('30','35','37','38','29','32') and d.categorie=:cat ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .setParameter("cat","05")
                    .getSingleResult();
            if(moyTfr==null) {
                return 0D ;
            } else
                return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }
    public  Double calculMoycoefReligion(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne)  from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode not in ('30','35','37','38','29','32') and d.categorie=:cate ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .setParameter("cate","05")
                    .getSingleResult();
            if(moyTfr==null) {
                return 0D ;
            } else
                return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }
    public  Double calculMoycoefFran(String matricule, String annee,String periode,Long idEcole){
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        System.out.println("niveauOrdre>>> "+niveauOrdre);
        if(niveauOrdre<=4){
            System.out.println("Premier Cycle>>> "+niveauOrdre);
            try {
                Double  moyTfr = (Double) em.createQuery("select SUM(d.coef*d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode in ('2','3','4') ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .getSingleResult();
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        } else {
            System.out.println("Second Cycle>>> "+niveauOrdre);
            try {
                Double  moyTfr = (Double) em.createQuery("select d.moyCoef from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle=:matiere ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                System.out.println("Niveau ordre moyCoef "+moyTfr);
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        }

    }

    public  Double calculMoycoefEMR(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.moyenne)  from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode in ('30','35','37','38','29','32') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            if(moyTfr==null) {
                return 0D ;
            } else
                return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }



    public  Double calculRangFran(String matricule, String annee,String periode,Long idEcole){
        Integer niveauOrdre= getNiveau(matricule,annee ,periode,idEcole);
        if(niveauOrdre<=4) {
            try {
                Double  moyTfr = (Double) em.createQuery("select AVG(d.rang) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode in ('2','3','4') ")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .getSingleResult();
                if(moyTfr==null) {
                    return 0D ;
                } else
                return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        } else {
            try {
                Double  moyTfr = (Double) em.createQuery("select AVG(d.rang) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle =:matiere")
                        .setParameter("matricule",matricule)
                        .setParameter("annee",annee)
                        .setParameter("periode",periode)
                        .setParameter("idEcole",idEcole)
                        .setParameter("matiere","FRANCAIS")
                        .getSingleResult();
                if(moyTfr==null) {
                    return 0D ;
                } else
                    return  moyTfr ;
            } catch (NoResultException e){
                return 0D ;
            }
        }

    }



    public  Double calculRangEMR(String matricule, String annee,String periode,Long idEcole){
        System.out.println("Debut Rang EMR ");
        try {
            Double  moyTfr = (Double) em.createQuery("select AVG(d.rang) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereCode in ('30','35','37','38','29')  ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            System.out.println("Rang EMR "+moyTfr);
            if(moyTfr==null)
            return  0D ;
            else
            return moyTfr ;
        } catch (NoResultException e){
            System.out.println("Moyenne EMR Error");
            return 0D ;
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
            if(moyTfr==null) {
                return 0D ;
            } else
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
            if(moyTfr==null) {
                return 0 ;
            } else
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





    public  Integer getNiveau(String matricule, String annee,String periode,Long idEcole){
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

    public AnneeScolaire findMainAnneeByEcole(Ecole ecole) {
        // find annee ouverte by ecole
        List<AnneeScolaire> anneeOuverte = getByEcoleAndStatut(ecole.getId(), Constants.OUVERT);
        if (anneeOuverte.size() >= 1)
            return findCentralAnneeReference(anneeOuverte.get(0));
        return new AnneeScolaire();
    }
    public List<AnneeScolaire> getByEcoleAndStatut(Long ecoleId, String statut) {

        List<AnneeScolaire> annees = new ArrayList<AnneeScolaire>();
        try {
            annees = AnneeScolaire.find("ecole.id = ?1 and statut = ?2", ecoleId, statut).list();
        } catch (RuntimeException e) {
            e.printStackTrace();

        }
        return annees;
    }

    public AnneeScolaire findCentralAnneeReference(AnneeScolaire ecoleAnneeOuvert) {
        AnneeScolaire centralAnnee = new AnneeScolaire();
        try {
            centralAnnee = AnneeScolaire
                    .find("anneeDebut =?1 and niveauEnseignement.id=?2 and periodicite.id =?3 and ecole is null",
                            ecoleAnneeOuvert.getAnneeDebut(), ecoleAnneeOuvert.getNiveauEnseignement().getId(),
                            ecoleAnneeOuvert.getPeriodicite().getId())
                    .singleResult();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return centralAnnee;
    }



}
