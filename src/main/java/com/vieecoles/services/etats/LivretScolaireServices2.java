package com.vieecoles.services.etats;

import com.vieecoles.InforPersonLivretScolaire;
import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.parametre;
import com.vieecoles.projection.LivretScolaireSelectDto;
import com.vieecoles.projection.LivretScolaireSpiderSelectDto;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LivretScolaireServices2 {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<LivretScolaireSpiderSelectDto>  livretScolaire(Long idEcole,String libellePeriode ,String libelleClasse, String anneeLibelle){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                " and b.libelleClasse=:libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", anneeLibelle)
                .setParameter("periode", libellePeriode)
                .setParameter("libelleClasse", libelleClasse)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        System.out.println("List matiere "+classeNiveauDtoList.toString());
        List<LivretScolaireSelectDto> resultatsListElevesDto = new ArrayList<>();

             String nom_ecole;
                 String adresse_ecole;
                String tel_ecole; String annee_libelle; String libelle_periode;
                 String nom; String prenoms; String sexe;
                String date_naissance; String lieu_naissance; String nationalite;
                String redoublant; String boursier; String affecte; String libelle_classe;
                Integer effectif_classe; String nom_prof_princ; String heures_abs_just;
                String heures_abs_non_just; Double moy_general;
                Double moy_max; Double moy_min; Double moy_avg;

                String appreciation_conseil;
                String statut; String libelle_matiere; Double moyenne;
                Integer rang; Double moy_annuelle; String rang_annuelle;
                String appreciation; int num_ordre; String rangBulletin;
                String nom_prenom_professeur; String libelle_categorie;
                String signataire; String bonus; String parent_matiere;
                String is_classed_mat; String is_classed_periode;
                Integer effectif_non_classe ;
                Double moyennePremier;
                Integer rangPremier;
                  Double moyenneDeuxieme;
                 Integer rangDeuxieme;
                 Double moy_anMat;
                 Integer rang_anMat ;

        Double TmoyFr,TcoefFr,TmoyCoefFr,TmoyCoefEMR = 0d, moy_1er_trim ,moy_2eme_trim,moy_3eme_trim ,
                TmoyFrPremier,TmoyFrDeuxieme,TmoyFrAnn = null,TmoyEMRANN,TmoyCoefFrPermier,TmoyCoefFrDeuxieme,
                TmoyCoefEMRPremier, TmoyCoefEMRDeuxieme ,TrangFrAnnuel1,TrangFrPremier1,TrangFrDeuxieme1;

        Integer rang_1er_trim ,rang_2eme_trim ,rang_3eme_trim ;
        int TrangEMR , TrangFr = 0,TrangFrAnnuel = 0,TrangFrPremier = 0,TrangFrDeuxieme = 0,TrangEMRPremier = 0,TrangEMRDeuxieme = 0;
        String  codeEcole,is_class_1er_trim,is_class_2e_trim,is_class_3e_trim,DateNaiss= null ,matricule ;

        List<LivretScolaireSpiderSelectDto> lm= new ArrayList<>() ;

        for (int i=0; i< LongTableau;i++) {
            Inscriptions myIns= new Inscriptions() ;
            ecole myEcole= new ecole() ;
         String   monMatricule= classeNiveauDtoList.get(i).getNiveau() ;
            LivretScolaireSpiderSelectDto m = new LivretScolaireSpiderSelectDto();
            libelle_matiere = classeNiveauDtoList.get(i).getNiveau() ;
            moy_anMat= getMoyAnMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau() ,libellePeriode);
            System.out.println("moy_anMat "+moy_anMat);
            rang_anMat= getRangAnMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau() ,libellePeriode) ;
            System.out.println("rang_anMat "+rang_anMat);
            m.setMoy_anMat(moy_anMat);
            m.setRang_anMat(rang_anMat);

            nom_ecole= getNomEcole(monMatricule ,libellePeriode) ;
            System.out.println(">>>"+nom_ecole);
            m.setNom_ecole(nom_ecole);
            nom_prof_princ = getNomProfePrin(monMatricule ,libellePeriode) ;
            System.out.println(">>>"+nom_prof_princ);
            m.setNom_prof_princ(nom_prof_princ);
             adresse_ecole = getAdresseEcole(monMatricule ,libellePeriode) ;
            System.out.println(">>>"+adresse_ecole);
             tel_ecole =getTelEcole(monMatricule ,libellePeriode);
            System.out.println(">>>"+tel_ecole);
             m.setTel_ecole(tel_ecole);
             annee_libelle =getLibelleAnneeEcole(monMatricule ,libellePeriode);
            System.out.println(">>>"+annee_libelle);
             libelle_periode= getLibellePeriode(monMatricule ,libellePeriode);
            System.out.println(">>>"+libelle_periode);
             m.setLibelle_periode(libelle_periode);
             nom= getNom(monMatricule ,libellePeriode);
            System.out.println(">>>"+nom);
             m.setNom(nom);
             prenoms = getPrenom(monMatricule ,libellePeriode);
            System.out.println(">>>"+prenoms);
             m.setPrenoms(prenoms);
             sexe= getSexe(monMatricule ,libellePeriode);
            System.out.println(">>>"+sexe);
             date_naissance= getDateNaiss(monMatricule ,libellePeriode);
            System.out.println(">>>"+date_naissance);
             m.setDate_naissance(date_naissance);
             lieu_naissance = getLieuNaiss(monMatricule ,libellePeriode);
            System.out.println(">>>"+lieu_naissance);
             m.setLieu_naissance(lieu_naissance);
             nationalite = getNational(monMatricule ,libellePeriode);
            System.out.println(">>>"+nationalite);
             redoublant = getRedoublant(monMatricule ,libellePeriode);
            System.out.println(">>>"+redoublant);
             m.setRedoublant(redoublant);
             boursier = getBoursier(monMatricule ,libellePeriode);
            System.out.println(">>>"+boursier);
             affecte = getAffecte(monMatricule ,libellePeriode);
            System.out.println(">>>"+affecte);
             libelle_classe = getLibelleClasse(monMatricule ,libellePeriode);
            System.out.println(">>>"+libelle_classe);
             effectif_classe = geteffectifClasse(monMatricule ,libellePeriode);
            System.out.println("effectif classe>>>"+effectif_classe);
             nom_prenom_professeur= getNomProfePrin(monMatricule ,libellePeriode);
            System.out.println("nom_prenom_professeur>>"+nom_prenom_professeur);
             m.setNom_prenom_professeur(nom_prenom_professeur);

             heures_abs_just = getHeureJusti(monMatricule ,libellePeriode);
            System.out.println("heures_abs_just>>"+heures_abs_just);
             heures_abs_non_just = getHeureNonJusti(monMatricule ,libellePeriode);
            System.out.println("heures_abs_non_just>>"+heures_abs_non_just);
             m.setHeures_abs_non_just(heures_abs_non_just);
             moy_general = getMoyGeneRal(monMatricule ,libellePeriode);
            System.out.println("moy_general>>"+ moy_general );
             m.setMoy_general(moy_general);
             moy_annuelle =getmoyAn(monMatricule ,libellePeriode);

            System.out.println("moy_annuelle>>"+ moy_annuelle );

             rang_annuelle= getRangAN(monMatricule ,libellePeriode) ;
            System.out.println("rang_annuelle>>"+ rang_annuelle );

             m.setRang_annuelle(rang_annuelle);
             moy_min =getMoyMin(monMatricule ,libellePeriode) ;
            System.out.println("moy_min>>"+ moy_min );
             moy_max = getMoyMax(monMatricule ,libellePeriode);
            System.out.println("moy_max>>"+ moy_max );
             moy_avg= getMoyAvg(monMatricule ,libellePeriode);
            System.out.println("moy_avg>>"+ moy_avg );
             appreciation_conseil= getAppreciation(monMatricule ,libellePeriode) ;
            System.out.println("appreciation_conseil>>"+ appreciation_conseil );
             num_ordre= getNumOrdreMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("num_ordre>>"+ num_ordre );
             m.setNum_ordre(num_ordre);
             statut= getStatutEcole(monMatricule ,libellePeriode) ;
            System.out.println("statut>>"+ statut );
            m.setStatut(statut);
            rangBulletin = getRang(monMatricule ,libellePeriode) ;
            System.out.println("rangBulletin>>"+ rangBulletin );
             appreciation = getAppreciationMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;

            System.out.println("appreciation>>"+ appreciation );

             nom_prenom_professeur = getNomProfesseurMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("nom_prenom_professeur>>"+ nom_prenom_professeur );
             m.setNom_prenom_professeur(nom_prenom_professeur);
             libelle_categorie = getCategorieMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode);
            System.out.println("libelle_categorie>>"+ nom_prenom_professeur );
             signataire= getSignataire(monMatricule ,libellePeriode) ;
            System.out.println("signataire>>"+ signataire );
             m.setSignataire(signataire);
             bonus = getBonusMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            System.out.println("bonus>>"+ bonus );
             parent_matiere= getParentMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
             m.setParent_matiere(parent_matiere);
             is_classed_mat = getIsClasseMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),libellePeriode) ;
            is_classed_periode = getIsClassePeriode(monMatricule ,libellePeriode) ;
            effectif_non_classe = geteffectiNonClasse(monMatricule ,libellePeriode) ;

            System.out.println("effectif_non_classe>>"+ effectif_non_classe );

            rang = getRangMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Troisième Trimestre") ;
            moyenne = getMoyenTroisiMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Troisième Trimestre") ;

            rangPremier = getRangMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Premier Trimestre") ;
            moyennePremier = getMoyenTroisiMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Premier Trimestre") ;

            rangDeuxieme = getRangMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Deuxième Trimestre") ;
            moyenneDeuxieme = getMoyenTroisiMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Deuxième Trimestre") ;

            //***********//
            TcoefFr = calculcoefFran(monMatricule,anneeLibelle,libellePeriode,idEcole) ;
            System.out.println("TcoefFr "+TcoefFr);
            TmoyCoefFrPermier= calculMoycoefFran(monMatricule,anneeLibelle,"Premier Trimestre",idEcole) ;
            TmoyCoefFrDeuxieme= calculMoycoefFran(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole) ;

            TmoyCoefEMRPremier = calculMoycoefEMR(monMatricule,anneeLibelle,"Premier Trimestre",idEcole);
            TmoyCoefEMRDeuxieme = calculMoycoefEMR(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole);

            TmoyEMRANN = ( TmoyCoefEMRPremier + (TmoyCoefEMRDeuxieme * 2) + (TmoyCoefEMR*2) )/5 ;
            TrangFrAnnuel1 = calculRangFranAnnuel(monMatricule,anneeLibelle,libellePeriode,idEcole) ;
            TrangFrPremier1 = calculRangFran(monMatricule,anneeLibelle,"Premier Trimestre",idEcole) ;
            TrangFrDeuxieme1 = calculRangFran(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole) ;
            if(TrangFrAnnuel1 !=null)
                TrangFrAnnuel = TrangFrAnnuel1.intValue() ;
            if(TrangFrPremier1 !=null)
                TrangFrPremier = TrangFrPremier1.intValue() ;

            if(TrangFrDeuxieme1 !=null)
                TrangFrDeuxieme = TrangFrDeuxieme1.intValue() ;

            TmoyFrPremier= (TcoefFr == null? TmoyCoefFrPermier/1: TmoyCoefFrPermier/TcoefFr );
            TmoyFrDeuxieme  =(TcoefFr == null?TmoyCoefFrDeuxieme/1: TmoyCoefFrDeuxieme/TcoefFr) ;

            TrangEMRPremier =calculRangEMR(monMatricule,anneeLibelle,"Premier Trimestre",idEcole) ;
            TrangEMRDeuxieme =calculRangEMR(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole) ;

            Double TrangFr1 = calculRangFran(monMatricule,anneeLibelle,libellePeriode,idEcole) ;
            TmoyCoefFr = calculMoycoefFran(monMatricule,anneeLibelle,libellePeriode,idEcole) ;
            System.out.println("TmoyCoefFr "+TmoyCoefFr);
            //System.out.println("Moyene en Francais: "+TmoyCoefFr/4);
            TrangFr1 = calculRangFran(monMatricule,anneeLibelle,libellePeriode,idEcole) ;
            System.out.println("TrangFr1 "+TrangFr1);
            if(TrangFr1 !=null)
                TrangFr = TrangFr1.intValue() ;

           // TmoyFr = calculTMoyFran(monMatricule,anneeLibelle,libellePeriode,idEcole);
            TrangEMR =calculRangEMR(monMatricule,anneeLibelle,libellePeriode,idEcole) ;
            System.out.println("TrangEMR "+TrangEMR);
            TmoyCoefEMR = calculMoycoefEMR(monMatricule,anneeLibelle,libellePeriode,idEcole);
            System.out.println("TmoyCoefEMR "+TmoyCoefEMR);

            moy_1er_trim = calculmoyenTrimesPasse(monMatricule,anneeLibelle,"Premier Trimestre",idEcole) ;
            System.out.println("moy_1er_trim "+moy_1er_trim);
            moy_2eme_trim = calculmoyenTrimesPasse(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole) ;
            moy_3eme_trim = calculmoyenTrimesPasse(monMatricule,anneeLibelle,"Troisième Trimestre",idEcole) ;

            rang_1er_trim = calculRangTrimesPasse(monMatricule,anneeLibelle,"Premier Trimestre",idEcole) ;
            rang_2eme_trim = calculRangTrimesPasse(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole) ;
            rang_3eme_trim = calculRangTrimesPasse(monMatricule,anneeLibelle,"Troisième Trimestre",idEcole) ;

            is_class_1er_trim = calculIsClassTrimesPasse(monMatricule,anneeLibelle,"Premier Trimestre",idEcole) ;
            System.out.println("is_class_1er_trim "+is_class_1er_trim);
            is_class_2e_trim = calculIsClassTrimesPasse(monMatricule,anneeLibelle,"Deuxième Trimestre",idEcole) ;

            is_class_3e_trim = calculIsClassTrimesPasse(monMatricule,anneeLibelle,"Troisième Trimestre",idEcole) ;
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

            //*********//


            m.setAppreciation(appreciation);
            m.setAffecte(affecte);
            m.setId_ecole(idEcole);
            m.setBonus(bonus);
            m.setBoursier(boursier);
            m.setAnnee_libelle(annee_libelle);
            m.setSexe(sexe);
            m.setAppreciation_conseil(appreciation_conseil);
            m.setNationalite(nationalite);
            m.setEffectif_classe(effectif_classe);
            m.setHeures_abs_just(heures_abs_just);
            m.setIs_classed_mat(is_classed_mat);
            m.setLibelle_categorie(libelle_categorie);
            m.setMatricule(monMatricule);
            m.setLibelle_matiere(libelle_matiere);
            m.setIs_classed_periode(is_classed_periode);
            m.setLibelle_classe(libelle_classe);
            m.setAdresse_ecole(adresse_ecole);
            m.setMoy_annuelle(moy_annuelle);
            m.setEffectif_non_classe(effectif_non_classe);
            m.setMoy_avg(moy_avg);
            m.setMoy_max(moy_max);
            m.setMoy_min(moy_min);
            m.setRangBulletin(rangBulletin);

            m.setMoyenne(moyenne);
            m.setRang(rang);

            m.setMoyenneDeuxieme(moyenneDeuxieme);
            m.setRangDeuxieme(rangDeuxieme);

            m.setRangPremier(rangPremier);
            m.setMoyennePremier(moyennePremier);
            //***************************//
             if(TmoyCoefFr!=null && TcoefFr !=null )
                 TmoyFr=  TmoyCoefFr/TcoefFr  ;
                         else
                 TmoyFr=null ;

                         //  TmoyFr= (TmoyCoefFr==null&&TcoefFr==null? null:TmoyCoefFr/TcoefFr);
            m.setTmoyfr(TmoyFr);
            m.setTrangFr(TrangFr);
            m.setTrangEMR(TrangEMR);
            m.setTmoyCoefEMR(TmoyCoefEMR);
            myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
            //System.out.println("myEcole "+myEcole.toString());
            myIns = inscriptionService.checkInscrit(idEcole,classeNiveauDtoList.get(i).getNiveau(),1L);
            parametre mpara = new parametre();
            mpara = parametre.findById(1L) ;
            m.setAmoirie(mpara.getImage() );
            m.setCodeEcole(myEcole.getEcolecode());
            m.setBg(myEcole.getFiligramme());
            m.setLogo(myEcole.getLogoBlob());
            m.setPhoto_eleve(myIns.getPhoto_eleve());
            m.setTcoefFr(TcoefFr);
            m.setTmoyCoefFr(TmoyCoefFr);
            m.setIs_class_1er_trim(is_class_1er_trim);
            m.setIs_class_2e_trim(is_class_2e_trim);
            m.setIs_class_3e_trim(is_class_3e_trim);
            m.setRang_1er_trim(rang_1er_trim);
            m.setRang_2eme_trim(rang_2eme_trim);
            m.setRang_3eme_trim(rang_3eme_trim);
            m.setMoy_1er_trim(moy_1er_trim);
            m.setMoy_2eme_trim(moy_2eme_trim);
            m.setMoy_3eme_trim(moy_3eme_trim);

            m.setTmoyFrPremier(TmoyFrPremier);
            m.setTmoyFrDeuxieme(TmoyFrDeuxieme);
            m.setTmoyFrAnn(TmoyFrAnn);
            m.setTmoyEMRANN(TmoyEMRANN);
            m.setTmoyCoefEMRPremier(TmoyCoefEMRPremier);
            m.setTmoyCoefEMRDeuxieme(TmoyCoefEMRDeuxieme);
            m.setTrangFrAnnuel(TrangFrAnnuel);
            m.setTrangFrPremier(TrangFrPremier);
            m.setTrangFrDeuxieme(TrangFrDeuxieme);
            m.setTrangEMRPremier(TrangEMRPremier);
            m.setTrangEMRDeuxieme(TrangEMRDeuxieme);
            m.setDateNaiss(DateNaiss);

            lm.add(m) ;


        }

        return  lm;
    }

    public  Double getMoyenTroisiMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Double   rangMat = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  rangMat ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  String getPecMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   rangMat = (String) em.createQuery("select d.pec  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  rangMat ;
        } catch (NoResultException e){
            return null ;
        }

    }
    public  Integer  geteffectiNonClasse(String matricule,String periode ){
        try {
            Integer   nomEcole = (Integer) em.createQuery("select cast(b.effectifNonClasse as integer )  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  String getParentMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   rangMat = (String) em.createQuery("select d.parentMatiere  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  rangMat ;
        } catch (NoResultException e){
            return null ;
        }

    }
    public  String  getSignataire(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.nomSignataire  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }
    public  String  getRedoublant(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.redoublant  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getAppreciation(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.appreciation  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Double getMoyMax(String matricule,String periode ){
        try {
            Double   m = (Double) em.createQuery("select b.moyMax  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getMoyMin(String matricule,String periode ){
        try {
            Double   m = (Double) em.createQuery("select b.moyMin  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getMoyAvg(String matricule,String periode ){
        try {
            Double   m = (Double) em.createQuery("select b.moyAvg  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getmoyAn(String matricule,String periode ){
        try {
            Double   m = (Double) em.createQuery("select b.moyAn  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  String getRangAN(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.rangAn  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }


    public  Double getMoyGeneRal(String matricule,String periode ){
        try {
            Double   m = (Double) em.createQuery("select b.moyGeneral  from Bulletin b  where b.matricule=:matricule " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Integer  geteffectifClasse(String matricule,String periode ){
        System.out.println("matricule "+matricule);
        System.out.println("periode "+periode);
        try {
            Integer   m = (Integer) em.createQuery("select b.effectif  from Bulletin b where b.matricule=:matricule and b.libellePeriode=:periode")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getBonusMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   rangMat = (String) em.createQuery("select cast(d.bonus as string )  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  rangMat ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getNomProfePrin(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.nomPrenomProfPrincipal  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getHeureJusti(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.heuresAbsJustifiees  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getHeureNonJusti(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.heuresAbsNonJustifiees  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getTelEcole(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.telEcole  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getLibelleAnneeEcole(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.anneeLibelle  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nomEcole ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getLibellePeriode(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.libellePeriode  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getNom(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.nom  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getPrenom(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.prenoms  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }
    public  String  getSexe(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.sexe  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getDateNaiss(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.dateNaissance  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getLieuNaiss(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.lieuNaissance  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getNational(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.nationalite  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getBoursier(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.boursier  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getAffecte(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.affecte  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getLibelleClasse(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.libelleClasse  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getAdresseEcole(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.adresseEcole  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String  getStatutEcole(String matricule,String periode ){
        try {
            String   m = (String) em.createQuery("select b.statutEcole  from Bulletin b where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return null ;
        }

    }


    public  Double getMoyAnMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Double   m = (Double) em.createQuery("select d.moyAn  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Integer getRangAnMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Integer   m = (Integer) em.createQuery("select cast(d.rangAn as integer )   from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  m ;
        } catch (NoResultException e){
            return 0 ;
        }

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

    public  Integer getRangMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Integer   rangMat = (Integer) em.createQuery("select d.rang  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  rangMat ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  Integer getCoefMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Integer   rangMat = (Integer) em.createQuery("select d.coef  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  rangMat ;
        } catch (NoResultException e){
            return 0 ;
        }

    }
    public  String getAppreciationMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   appreciation = (String) em.createQuery("select d.appreciation  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  appreciation ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  Integer getNumOrdreMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            Integer   numOrdre = (Integer) em.createQuery("select d.num_ordre  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  numOrdre ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  String getNomProfesseurMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   numOrdre = (String) em.createQuery("select d.nom_prenom_professeur  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  numOrdre ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public  String getCategorieMatiere(String matricule,String libelleMatiere,String periode ){
        try {
            String   categMat = (String) em.createQuery("select d.categorieMatiere  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)

                    .getSingleResult();
            return  categMat ;
        } catch (NoResultException e){
            return null ;
        }
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

    public  String getRang(String matricule,String periode ){
        try {
            String   rang = (String) em.createQuery("select cast(b.rang as string )   from Bulletin b  where b.matricule=:matricule" +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  rang ;
        } catch (NoResultException e){
            return null ;
        }

    }
    public  String  getNomEcole(String matricule,String periode ){
        try {
            String   nomEcole = (String) em.createQuery("select b.nomEcole  from Bulletin b  where b.matricule=:matricule" +
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

//*********************//
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

    public InforPersonLivretScolaire getIdBulletinFromInfosBull(String idBulletin){
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



    public List<BoursierDto> getBoursierParNiveauBoursier(Long idEcole , String niveau , String libelleAnnee , String libelleTrimestre){
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

    //******************//





}
