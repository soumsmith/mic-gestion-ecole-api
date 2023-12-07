package com.vieecoles.services.etats;

import com.vieecoles.dto.BulletinDto;
import com.vieecoles.dto.DetailBulletinDto;
import com.vieecoles.entities.BulletinImport;
import com.vieecoles.entities.DetailBulletinImport;
import com.vieecoles.entities.operations.eleve;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class ImporterBulletinServices {

      @Inject
      EntityManager em;

      public  void  importerEtCreerDetail( List<BulletinDto> bList){
            for (int i = 0; i < bList.size(); i++){
                  BulletinImport anImport = new BulletinImport() ;
                  anImport = getBulletinImport(bList.get(i).getMatricule(),bList.get(i).getAnneeId(), bList.get(i).getLibellePeriode(),bList.get(i).getLibelleClasse());

                  if(anImport==null){
                        BulletinImport b= new BulletinImport() ;
                        b.setEcoleId(bList.get(i).getEcoleId());
                        b.setNomEcole(bList.get(i).getNomEcole());
                        b.setUrlLogo(bList.get(i).getUrlLogo());
                        b.setAdresseEcole(bList.get(i).getAdresseEcole());
                        b.setTelEcole(bList.get(i).getTelEcole());
                        b.setAnneeId(bList.get(i).getAnneeId());
                        b.setAnneeLibelle(bList.get(i).getAnneeLibelle());
                        b.setPeriodeId(bList.get(i).getPeriodeId());
                        b.setLibellePeriode(bList.get(i).getLibellePeriode());
                        b.setMatricule(bList.get(i).getMatricule());
                        b.setNom(bList.get(i).getNom());
                        b.setPrenoms(bList.get(i).getPrenoms());
                        b.setSexe(bList.get(i).getSexe());
                        b.setDateNaissance(bList.get(i).getDateNaissance());
                        b.setLieuNaissance(bList.get(i).getLieuNaissance());
                        b.setNationalite(bList.get(i).getNationalite());
                        b.setRedoublant(bList.get(i).getRedoublant());
                        b.setBoursier(bList.get(i).getBoursier());
                        b.setAffecte(bList.get(i).getAffecte());
                        b.setClasseId(bList.get(i).getClasseId());
                        b.setLibelleClasse(bList.get(i).getLibelleClasse());
                        b.setEffectif(bList.get(i).getEffectif());
                        b.setTotalCoef(bList.get(i).getTotalCoef());
                        b.setTotalMoyCoef(bList.get(i).getTotalMoyCoef());
                        b.setNomPrenomProfPrincipal(bList.get(i).getNomPrenomProfPrincipal());
                        b.setNomPrenomEducateur(bList.get(i).getNomPrenomEducateur());
                        b.setHeuresAbsJustifiees(bList.get(i).getHeuresAbsJustifiees());
                        b.setHeuresAbsNonJustifiees(bList.get(i).getHeuresAbsNonJustifiees());
                        b.setMoyGeneral(bList.get(i).getMoyGeneral());
                        b.setMoyMax(bList.get(i).getMoyMax());
                        b.setMoyMin(bList.get(i).getMoyMin());
                        b.setMoyAvg(bList.get(i).getMoyAvg());
                        b.setMoyAn(bList.get(i).getMoyAn());
                        b.setRangAn(bList.get(i).getRangAn());
                        b.setAppreciation(bList.get(i).getAppreciation());
                        b.setDateCreation(bList.get(i).getDateCreation());
                        b.setDateUpdate(bList.get(i).getDateUpdate());
                        b.setNumDecisionAffecte(bList.get(i).getNumDecisionAffecte());
                        b.setLv2(bList.get(i).getLv2());
                        b.setIsClassed(bList.get(i).getIsClassed());
                        b.setRang(bList.get(i).getRang());
                        b.setNiveau(bList.get(i).getNiveau());
                        b.setOrdreNiveau(bList.get(i).getOrdreNiveau());
                        b.setEcoleOrigine(bList.get(i).getEcoleOrigine());
                        b.setNomSignataire(bList.get(i).getNomSignataire());
                        b.setTransfert(bList.get(i).getTransfert());
                        b.setPhoto_eleve(bList.get(i).getPhoto_eleve());
                        b.setNiveauLibelle(bList.get(i).getNiveauLibelle());
                        b.setEffectifNonClasse(bList.get(i).getEffectifNonClasse());
                        b.setTypeEvaluation(bList.get(i).getTypeEvaluation());
                        b.setTypeEvaluationLibelle(bList.get(i).getTypeEvaluationLibelle());
                        b.setNumeroEvaluation(bList.get(i).getNumeroEvaluation());
                        b.setNumeroIEPP(bList.get(i).getNumeroIEPP());
                        b.setMoyEvaluationIEPP(bList.get(i).getMoyEvaluationIEPP());
                        b.setMoyEvaluationInterne(bList.get(i).getMoyEvaluationInterne());
                        b.setMoyEvaluationPassage(bList.get(i).getMoyEvaluationPassage());
                       BulletinImport bulletinImport = new BulletinImport() ;
                      bulletinImport =  importerCreerBulletin(b);
                        //Insert detail Bulletin
                      DetailBulletinImport d = new DetailBulletinImport() ;
                      d.setMatiereCode(bList.get(i).getMatiereCode());
                      d.setCategorieMatiere(bList.get(i).getCategorieMatiere());
                      d.setMoyenne(bList.get(i).getMoyenne());
                      d.setRang(bList.get(i).getRangMatiere());
                      d.setCoef(bList.get(i).getCoef());
                      d.setMoyCoef(bList.get(i).getMoyCoef());
                      d.setAppreciation(bList.get(i).getAppreciationMatiere());
                      d.setCategorie(bList.get(i).getCategorie());
                      d.setNum_ordre(bList.get(i).getNum_ordre());
                      d.setNom_prenom_professeur(bList.get(i).getNom_prenom_professeur());
                      d.setMoyAn(bList.get(i).getMoyAnMatiere());
                      d.setRangAn(bList.get(i).getRangAnMatiere());
                      d.setPec(bList.get(i).getPec());
                      d.setBonus(bList.get(i).getBonus());
                      d.setParentMatiere(bList.get(i).getParentMatiere());
                      d.setIsRanked(bList.get(i).getIsRanked());
                      d.setBulletin(bulletinImport);
                      d.setDateCreation(bList.get(i).getDateCreation());
                      creerDetailsBulletins(d);
                  } else {
                      BulletinDto b= new BulletinDto() ;
                      b.setEcoleId(bList.get(i).getEcoleId());
                      b.setNomEcole(bList.get(i).getNomEcole());
                      b.setUrlLogo(bList.get(i).getUrlLogo());
                      b.setAdresseEcole(bList.get(i).getAdresseEcole());
                      b.setTelEcole(bList.get(i).getTelEcole());
                      b.setAnneeId(bList.get(i).getAnneeId());
                      b.setAnneeLibelle(bList.get(i).getAnneeLibelle());
                      b.setPeriodeId(bList.get(i).getPeriodeId());
                      b.setLibellePeriode(bList.get(i).getLibellePeriode());
                      b.setMatricule(bList.get(i).getMatricule());
                      b.setNom(bList.get(i).getNom());
                      b.setPrenoms(bList.get(i).getPrenoms());
                      b.setSexe(bList.get(i).getSexe());
                      b.setDateNaissance(bList.get(i).getDateNaissance());
                      b.setLieuNaissance(bList.get(i).getLieuNaissance());
                      b.setNationalite(bList.get(i).getNationalite());
                      b.setRedoublant(bList.get(i).getRedoublant());
                      b.setBoursier(bList.get(i).getBoursier());
                      b.setAffecte(bList.get(i).getAffecte());
                      b.setClasseId(bList.get(i).getClasseId());
                      b.setLibelleClasse(bList.get(i).getLibelleClasse());
                      b.setEffectif(bList.get(i).getEffectif());
                      b.setTotalCoef(bList.get(i).getTotalCoef());
                      b.setTotalMoyCoef(bList.get(i).getTotalMoyCoef());
                      b.setNomPrenomProfPrincipal(bList.get(i).getNomPrenomProfPrincipal());
                      b.setNomPrenomEducateur(bList.get(i).getNomPrenomEducateur());
                      b.setHeuresAbsJustifiees(bList.get(i).getHeuresAbsJustifiees());
                      b.setHeuresAbsNonJustifiees(bList.get(i).getHeuresAbsNonJustifiees());
                      b.setMoyGeneral(bList.get(i).getMoyGeneral());
                      b.setMoyMax(bList.get(i).getMoyMax());
                      b.setMoyMin(bList.get(i).getMoyMin());
                      b.setMoyAvg(bList.get(i).getMoyAvg());
                      b.setMoyAn(bList.get(i).getMoyAn());
                      b.setRangAn(bList.get(i).getRangAn());
                      b.setAppreciation(bList.get(i).getAppreciation());
                      b.setDateCreation(bList.get(i).getDateCreation());
                      b.setDateUpdate(bList.get(i).getDateUpdate());
                      b.setNumDecisionAffecte(bList.get(i).getNumDecisionAffecte());
                      b.setLv2(bList.get(i).getLv2());
                      b.setIsClassed(bList.get(i).getIsClassed());
                      b.setRang(bList.get(i).getRang());
                      b.setNiveau(bList.get(i).getNiveau());
                      b.setOrdreNiveau(bList.get(i).getOrdreNiveau());
                      b.setEcoleOrigine(bList.get(i).getEcoleOrigine());
                      b.setNomSignataire(bList.get(i).getNomSignataire());
                      b.setTransfert(bList.get(i).getTransfert());
                      b.setPhoto_eleve(bList.get(i).getPhoto_eleve());
                      b.setNiveauLibelle(bList.get(i).getNiveauLibelle());
                      b.setEffectifNonClasse(bList.get(i).getEffectifNonClasse());
                      b.setTypeEvaluation(bList.get(i).getTypeEvaluation());
                      b.setTypeEvaluationLibelle(bList.get(i).getTypeEvaluationLibelle());
                      b.setNumeroEvaluation(bList.get(i).getNumeroEvaluation());
                      b.setNumeroIEPP(bList.get(i).getNumeroIEPP());
                      b.setMoyEvaluationIEPP(bList.get(i).getMoyEvaluationIEPP());
                      b.setMoyEvaluationInterne(bList.get(i).getMoyEvaluationInterne());
                      b.setMoyEvaluationPassage(bList.get(i).getMoyEvaluationPassage());
                      importerModifierBulletin(b);
                      //Insert detail Bulletin

                      DetailBulletinDto d = new DetailBulletinDto() ;
                      d.setMatiereCode(bList.get(i).getMatiereCode());
                      d.setCategorieMatiere(bList.get(i).getCategorieMatiere());
                      d.setMoyenne(bList.get(i).getMoyenne());
                      d.setRang(bList.get(i).getRangMatiere());
                      d.setCoef(bList.get(i).getCoef());
                      d.setMoyCoef(bList.get(i).getMoyCoef());
                      d.setAppreciation(bList.get(i).getAppreciationMatiere());
                      d.setCategorie(bList.get(i).getCategorie());
                      d.setNum_ordre(bList.get(i).getNum_ordre());
                      d.setNom_prenom_professeur(bList.get(i).getNom_prenom_professeur());
                      d.setMoyAn(bList.get(i).getMoyAnMatiere());
                      d.setRangAn(bList.get(i).getRangAnMatiere());
                      d.setPec(bList.get(i).getPec());
                      d.setBonus(bList.get(i).getBonus());
                      d.setParentMatiere(bList.get(i).getParentMatiere());
                      d.setIsRanked(bList.get(i).getIsRanked());
                     // d.setBulletin(bulletinImport);
                      d.setDateCreation(bList.get(i).getDateCreation());
                      modifierDetailsBulletins(d);
                  }

            }


      }

      @Transactional
      public  BulletinImport importerCreerBulletin(BulletinImport b){
                        b.persist();
                        return b ;
      }

      @Transactional
      public  void importerModifierBulletin(BulletinDto bList){

                        BulletinImport b = new BulletinImport() ;
                        b= BulletinImport.findById(bList.getId());
                        b.setEcoleId(bList.getEcoleId());
                        b.setNomEcole(bList.getNomEcole());
                        b.setUrlLogo(bList.getUrlLogo());
                        b.setAdresseEcole(bList.getAdresseEcole());
                        b.setTelEcole(bList.getTelEcole());
                        b.setAnneeId(bList.getAnneeId());
                        b.setAnneeLibelle(bList.getAnneeLibelle());
                        b.setPeriodeId(bList.getPeriodeId());
                        b.setLibellePeriode(bList.getLibellePeriode());
                        //   b.setMatricule(bList.get(i).getMatricule());
                        b.setNom(bList.getNom());
                        b.setPrenoms(bList.getPrenoms());
                        b.setSexe(bList.getSexe());
                        b.setDateNaissance(bList.getDateNaissance());
                        b.setLieuNaissance(bList.getLieuNaissance());
                        b.setNationalite(bList.getNationalite());
                        b.setRedoublant(bList.getRedoublant());
                        b.setBoursier(bList.getBoursier());
                        b.setAffecte(bList.getAffecte());
                        b.setClasseId(bList.getClasseId());
                        b.setLibelleClasse(bList.getLibelleClasse());
                        b.setEffectif(bList.getEffectif());
                        b.setTotalCoef(bList.getTotalCoef());
                        b.setTotalMoyCoef(bList.getTotalMoyCoef());
                        b.setNomPrenomProfPrincipal(bList.getNomPrenomProfPrincipal());
                        b.setNomPrenomEducateur(bList.getNomPrenomEducateur());
                        b.setHeuresAbsJustifiees(bList.getHeuresAbsJustifiees());
                        b.setHeuresAbsNonJustifiees(bList.getHeuresAbsNonJustifiees());
                        b.setMoyGeneral(bList.getMoyGeneral());
                        b.setMoyMax(bList.getMoyMax());
                        b.setMoyMin(bList.getMoyMin());
                        b.setMoyAvg(bList.getMoyAvg());
                        b.setMoyAn(bList.getMoyAn());
                        b.setRangAn(bList.getRangAn());
                        b.setAppreciation(bList.getAppreciation());
                        b.setDateCreation(bList.getDateCreation());
                        b.setDateUpdate(bList.getDateUpdate());
                        b.setNumDecisionAffecte(bList.getNumDecisionAffecte());
                        b.setLv2(bList.getLv2());
                        b.setIsClassed(bList.getIsClassed());
                        b.setRang(bList.getRang());
                        b.setNiveau(bList.getNiveau());
                        b.setOrdreNiveau(bList.getOrdreNiveau());
                        b.setEcoleOrigine(bList.getEcoleOrigine());
                        b.setNomSignataire(bList.getNomSignataire());
                        b.setTransfert(bList.getTransfert());
                        b.setPhoto_eleve(bList.getPhoto_eleve());
                        b.setNiveauLibelle(bList.getNiveauLibelle());
                        b.setEffectifNonClasse(bList.getEffectifNonClasse());
                        b.setTypeEvaluation(bList.getTypeEvaluation());
                        b.setTypeEvaluationLibelle(bList.getTypeEvaluationLibelle());
                        b.setNumeroEvaluation(bList.getNumeroEvaluation());
                        b.setNumeroIEPP(bList.getNumeroIEPP());
                        b.setMoyEvaluationIEPP(bList.getMoyEvaluationIEPP());
                        b.setMoyEvaluationInterne(bList.getMoyEvaluationInterne());
                        b.setMoyEvaluationPassage(bList.getMoyEvaluationPassage());
                        // b.persist();




      }
      public void creerDetailsBulletins(DetailBulletinImport Detail){

            BulletinImport b = new BulletinImport() ;
            b= BulletinImport.findById(Detail.getBulletin()) ;

                  DetailBulletinImport d = new DetailBulletinImport() ;
                  d.setMatiereCode(Detail.getMatiereCode());
                  d.setMatiereLibelle(Detail.getMatiereLibelle());
                  d.setCategorieMatiere(Detail.getCategorieMatiere());
                  d.setMoyenne(Detail.getMoyenne());
                  d.setRang(Detail.getRang());
                  d.setCoef(Detail.getCoef());
                  d.setMoyCoef(Detail.getMoyCoef());
                  d.setAppreciation(Detail.getAppreciation());
                  d.setCategorie(Detail.getCategorie());
                  d.setNum_ordre(Detail.getNum_ordre());
                  d.setNom_prenom_professeur(Detail.getNom_prenom_professeur());
                  d.setMoyAn(Detail.getMoyAn());
                  d.setRangAn(Detail.getRangAn());
                  d.setPec(Detail.getPec());
                  d.setBonus(Detail.getBonus());
                  d.setParentMatiere(Detail.getParentMatiere());
                  d.setIsRanked(Detail.getIsRanked());
                  d.setBulletin(b);
                  d.setDateCreation(Detail.getDateCreation());
                  d.persist();

      }

      public void modifierDetailsBulletins(DetailBulletinDto Detail){
            BulletinImport b = new BulletinImport() ;
            b= BulletinImport.findById(Detail.getIdbulletin()) ;

            DetailBulletinImport d = new DetailBulletinImport() ;
            d.setMatiereCode(Detail.getMatiereCode());
            d.setMatiereLibelle(Detail.getMatiereLibelle());
            d.setCategorieMatiere(Detail.getCategorieMatiere());
            d.setMoyenne(Detail.getMoyenne());
            d.setRang(Detail.getRang());
            d.setCoef(Detail.getCoef());
            d.setMoyCoef(Detail.getMoyCoef());
            d.setAppreciation(Detail.getAppreciation());
            d.setCategorie(Detail.getCategorie());
            d.setNum_ordre(Detail.getNum_ordre());
            d.setNom_prenom_professeur(Detail.getNom_prenom_professeur());
            d.setMoyAn(Detail.getMoyAn());
            d.setRangAn(Detail.getRangAn());
            d.setPec(Detail.getPec());
            d.setBonus(Detail.getBonus());
            d.setParentMatiere(Detail.getParentMatiere());
            d.setIsRanked(Detail.getIsRanked());
            //d.setBulletin(b);
            d.setDateCreation(Detail.getDateCreation());


      }
      public BulletinImport getBulletinImport(String matricule,Long idAnnee ,String periode ,String classe){
            BulletinImport b = new BulletinImport() ;
            try {
                  b= (BulletinImport) em.createQuery("select o from BulletinImport o  where o.matricule =:matricule " +
                                  " and o.anneeId=:idAnnee and o.libellePeriode =:periode and o.libelleClasse=:classe" )
                          .setParameter("matricule", matricule).
                          setParameter("idAnnee", idAnnee).
                          setParameter("periode", periode).
                          setParameter("classe", classe).
                          getSingleResult() ;
            } catch (Exception e) {
                  System.out.println("Nouvel elève");
                  b= null ;
            }
            return b;
      }
}
