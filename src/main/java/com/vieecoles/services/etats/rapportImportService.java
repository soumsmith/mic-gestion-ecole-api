package com.vieecoles.services.etats;

import com.vieecoles.steph.dto.BulletinDto;
import com.vieecoles.steph.dto.DetailBulletinDto;
import com.vieecoles.steph.entities.Bulletin;
import java.util.List;

public class rapportImportService {











  Bulletin creerBulletinseleve(BulletinDto bulletinDto){
    Bulletin bulletin = new Bulletin();
    bulletin= getBulletinEleve(bulletinDto.getMatricule(),bulletinDto.getEcoleId(),bulletinDto.getLibellePeriode(),bulletinDto.getAnneeLibelle());
    if(bulletin!=null && bulletin.getMatricule().equals(bulletinDto.getMatricule())){
      bulletin.setEcoleId(bulletinDto.getEcoleId());
      bulletin.setNom(bulletinDto.getNom());
      bulletin.setStatutEcole(bulletinDto.getNomEcole());
      bulletin.setUrlLogo(bulletinDto.getUrlPhotoEleve());
      bulletin.setUrlPhoto(bulletinDto.getUrlPhotoEleve());
      bulletin.setAdresseEcole(bulletinDto.getAdresseEcole());
      bulletin.setTelEcole(bulletinDto.getTelEcole());
      bulletin.setAnneeId(bulletinDto.getAnneeId());
      bulletin.setAnneeLibelle(bulletinDto.getAnneeLibelle());
      bulletin.setPeriodeId(bulletinDto.getPeriodeId());
      bulletin.setLibellePeriode(bulletinDto.getLibellePeriode());
      bulletin.setMatricule(bulletinDto.getMatricule());
      bulletin.setStatut(bulletinDto.getStatut());
      bulletin.setNom(bulletinDto.getNom());
      bulletin.setPrenoms(bulletinDto.getPrenoms());
      bulletin.setSexe(bulletinDto.getSexe());
      bulletin.setDateNaissance(bulletinDto.getDateNaissance());
      bulletin.setLieuNaissance(bulletinDto.getLieuNaissance());
      bulletin.setNationalite(bulletinDto.getNationalite());
      bulletin.setRedoublant(bulletinDto.getRedoublant());
      bulletin.setBoursier(bulletinDto.getBoursier());
      bulletin.setAffecte(bulletinDto.getAffecte());
      bulletin.setClasseId(bulletinDto.getClasseId());
      bulletin.setLibelleClasse(bulletinDto.getLibelleClasse());
      bulletin.setEffectif(bulletinDto.getEffectif());
      bulletin.setTotalCoef(bulletinDto.getTotalCoef());
      bulletin.setTotalMoyCoef(bulletinDto.getTotalMoyCoef());
      bulletin.setCodeProfPrincipal(bulletinDto.getCodeProfPrincipal());
      bulletin.setNomPrenomProfPrincipal(bulletinDto.getNomPrenomProfPrincipal());
      bulletin.setCodeEducateur(bulletinDto.getCodeEducateur());
      bulletin.setNomPrenomEducateur(bulletinDto.getNomPrenomEducateur());
      bulletin.setHeuresAbsJustifiees(bulletinDto.getHeuresAbsJustifiees());
      bulletin.setHeuresAbsNonJustifiees(bulletinDto.getHeuresAbsNonJustifiees());
      bulletin.setMoyGeneral(bulletinDto.getMoyGeneral());
      bulletin.setMoyMax(bulletinDto.getMoyMax());
      bulletin.setMoyMin(bulletinDto.getMoyMin());
      bulletin.setMoyAvg(bulletinDto.getMoyAvg());
      bulletin.setMoyAn(bulletinDto.getMoyAn());
      bulletin.setRangAn(bulletinDto.getRangAn());
      bulletin.setApprAn(bulletinDto.getApprAn());
      bulletin.setAppreciation(bulletinDto.getAppreciation());
      bulletin.setDateCreation(bulletinDto.getDateCreation());
      bulletin.setDateUpdate(bulletinDto.getDateUpdate());
      bulletin.setNumDecisionAffecte(bulletinDto.getNumDecisionAffecte());
      bulletin.setLv2(bulletinDto.getLv2());
      bulletin.setNature(bulletinDto.getNature());
      bulletin.setIsClassed(bulletinDto.getIsClassed());
      bulletin.setCodeQr(bulletinDto.getCodeQr());
      bulletin.setStatut(bulletinDto.getStatut());
      bulletin.setRang(bulletinDto.getRang());
      bulletin.setNiveau(bulletinDto.getNiveau());
      bulletin.setOrdreNiveau(bulletinDto.getOrdreNiveau());
      bulletin.setEcoleOrigine(bulletinDto.getEcoleOrigine());
      bulletin.setNomSignataire(bulletinDto.getNomSignataire());
      bulletin.setPhoto_eleve(bulletinDto.getPhoto_eleve());
      bulletin.setNiveauEnseignementId(bulletinDto.getNiveauEnseignementId());
      bulletin.setTransfert(bulletinDto.getTransfert());
      bulletin.setIvoirien(bulletinDto.getIvoirien());

    } else {
      bulletin.setEcoleId(bulletinDto.getEcoleId());
      bulletin.setNom(bulletinDto.getNom());
      bulletin.setStatutEcole(bulletinDto.getNomEcole());
      bulletin.setUrlLogo(bulletinDto.getUrlPhotoEleve());
      bulletin.setUrlPhoto(bulletinDto.getUrlPhotoEleve());
      bulletin.setAdresseEcole(bulletinDto.getAdresseEcole());
      bulletin.setTelEcole(bulletinDto.getTelEcole());
      bulletin.setAnneeId(bulletinDto.getAnneeId());
      bulletin.setAnneeLibelle(bulletinDto.getAnneeLibelle());
      bulletin.setPeriodeId(bulletinDto.getPeriodeId());
      bulletin.setLibellePeriode(bulletinDto.getLibellePeriode());
      bulletin.setMatricule(bulletinDto.getMatricule());
      bulletin.setStatut(bulletinDto.getStatut());
      bulletin.setNom(bulletinDto.getNom());
      bulletin.setPrenoms(bulletinDto.getPrenoms());
      bulletin.setSexe(bulletinDto.getSexe());
      bulletin.setDateNaissance(bulletinDto.getDateNaissance());
      bulletin.setLieuNaissance(bulletinDto.getLieuNaissance());
      bulletin.setNationalite(bulletinDto.getNationalite());
      bulletin.setRedoublant(bulletinDto.getRedoublant());
      bulletin.setBoursier(bulletinDto.getBoursier());
      bulletin.setAffecte(bulletinDto.getAffecte());
      bulletin.setClasseId(bulletinDto.getClasseId());
      bulletin.setLibelleClasse(bulletinDto.getLibelleClasse());
      bulletin.setEffectif(bulletinDto.getEffectif());
      bulletin.setTotalCoef(bulletinDto.getTotalCoef());
      bulletin.setTotalMoyCoef(bulletinDto.getTotalMoyCoef());
      bulletin.setCodeProfPrincipal(bulletinDto.getCodeProfPrincipal());
      bulletin.setNomPrenomProfPrincipal(bulletinDto.getNomPrenomProfPrincipal());
      bulletin.setCodeEducateur(bulletinDto.getCodeEducateur());
      bulletin.setNomPrenomEducateur(bulletinDto.getNomPrenomEducateur());
      bulletin.setHeuresAbsJustifiees(bulletinDto.getHeuresAbsJustifiees());
      bulletin.setHeuresAbsNonJustifiees(bulletinDto.getHeuresAbsNonJustifiees());
      bulletin.setMoyGeneral(bulletinDto.getMoyGeneral());
      bulletin.setMoyMax(bulletinDto.getMoyMax());
      bulletin.setMoyMin(bulletinDto.getMoyMin());
      bulletin.setMoyAvg(bulletinDto.getMoyAvg());
      bulletin.setMoyAn(bulletinDto.getMoyAn());
      bulletin.setRangAn(bulletinDto.getRangAn());
      bulletin.setApprAn(bulletinDto.getApprAn());
      bulletin.setAppreciation(bulletinDto.getAppreciation());
      bulletin.setDateCreation(bulletinDto.getDateCreation());
      bulletin.setDateUpdate(bulletinDto.getDateUpdate());
      bulletin.setNumDecisionAffecte(bulletinDto.getNumDecisionAffecte());
      bulletin.setLv2(bulletinDto.getLv2());
      bulletin.setNature(bulletinDto.getNature());
      bulletin.setIsClassed(bulletinDto.getIsClassed());
      bulletin.setCodeQr(bulletinDto.getCodeQr());
      bulletin.setStatut(bulletinDto.getStatut());
      bulletin.setRang(bulletinDto.getRang());
      bulletin.setNiveau(bulletinDto.getNiveau());
      bulletin.setOrdreNiveau(bulletinDto.getOrdreNiveau());
      bulletin.setEcoleOrigine(bulletinDto.getEcoleOrigine());
      bulletin.setNomSignataire(bulletinDto.getNomSignataire());
      bulletin.setPhoto_eleve(bulletinDto.getPhoto_eleve());
      bulletin.setNiveauEnseignementId(bulletinDto.getNiveauEnseignementId());
      bulletin.setTransfert(bulletinDto.getTransfert());
      bulletin.setIvoirien(bulletinDto.getIvoirien());
      bulletin.persist();
    }
    return bulletin;

  }


  String creerDetailsBulletins(List<BulletinDto>b) {

    if(b.size()>0) {
      for (int i = 0; i < b.size(); i++) {
        Bulletin bulletin = new Bulletin();
        bulletin= creerBulletinseleve(b.get(i));
      }
    }
return  null ;
  }


  Bulletin getBulletinEleve(String matricule ,Long idEcole, String periode , String annee){
    return null;
  }
}
