package com.vieecoles.services.etats;

import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.projection.BulletinSelectDto;
import com.vieecoles.projection.LivretScolaireSelectDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class LivretScolaireServices {
    @Inject
    EntityManager em;

    public List<LivretScolaireSelectDto>  livretScolaire(Long idEcole,String libellePeriode ,String monMatricule, String anneeLibelle){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(d.matiereLibelle) from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and b.libellePeriode=:libellePeriode" +
                        " and b.ecoleId=:idEcole and b.anneeLibelle=:anneeLibelle "
               , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("matricule", monMatricule)
                                .setParameter("libellePeriode", libellePeriode)
                                  .setParameter("idEcole", idEcole)
                                   .setParameter("anneeLibelle", anneeLibelle)
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

        List<LivretScolaireSelectDto> lm= new ArrayList<>() ;

        for (int i=0; i< LongTableau;i++) {
            LivretScolaireSelectDto m = new LivretScolaireSelectDto();
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
            System.out.println("rang "+rang);
            moyenne = getMoyenTroisiMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Troisième Trimestre") ;
            System.out.println("moyenne "+moyenne);
            rangPremier = getRangMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Premier Trimestre") ;
            System.out.println("rangPremier "+rangPremier);
            moyennePremier = getMoyenTroisiMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Premier Trimestre") ;
            System.out.println("moyennePremier "+moyennePremier);
            rangDeuxieme = getRangMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Deuxième Trimestre") ;
            System.out.println("rangDeuxieme "+rangDeuxieme);
            moyenneDeuxieme = getMoyenTroisiMatiere(monMatricule ,classeNiveauDtoList.get(i).getNiveau(),"Deuxième Trimestre") ;
            System.out.println("moyenneDeuxieme "+moyenneDeuxieme);
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







}
