package com.vieecoles.services.etats;

import com.vieecoles.dto.ImportReportDto;
import com.vieecoles.steph.dto.BulletinDto;
import com.vieecoles.steph.dto.DetailBulletinDto;
import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.DetailBulletin;
import com.vieecoles.steph.entities.Matiere;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
@ApplicationScoped
public class RapportImportService {
  @Inject
  EntityManager em;
@Transactional
  public Bulletin creerBulletinseleve(BulletinDto bulletinDto){
    Bulletin bulletin = new Bulletin();
  Bulletin bulletinresul = new Bulletin();
    System.out.println("Entree recherche elève");
    bulletin= getBulletinEleve(bulletinDto.getMatricule(),bulletinDto.getEcoleId(),bulletinDto.getLibellePeriode(),bulletinDto.getAnneeLibelle());

    if(bulletin!=null && bulletin.getMatricule().equals(bulletinDto.getMatricule())){
      System.out.println("Eleve trouvé "+bulletin.getMatricule());
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
      bulletin.setNiveauLibelle(bulletinDto.getNiveauLibelle());
      bulletinresul=bulletin;

    } else {
      System.out.println("Bulletin non trouvé");
      System.out.println("bulletinDto.getEcoleId() A "+bulletinDto.getEcoleId());
      Bulletin bulletin1 = new Bulletin();
      UUID uuid = UUID.randomUUID();
      bulletin1.setId(uuid.toString());
      bulletin1.setEcoleId(bulletinDto.getEcoleId());

      bulletin1.setNom(bulletinDto.getNom());
      bulletin1.setStatutEcole(bulletinDto.getStatut());
      bulletin1.setNomEcole(bulletinDto.getNomEcole());
      System.out.println("bulletinDto.getEcoleId() A A"+bulletinDto.getEcoleId());

      bulletin1.setUrlLogo(bulletinDto.getUrlLogo());
      bulletin1.setUrlPhoto(bulletinDto.getUrlPhotoEleve());
      bulletin1.setAdresseEcole(bulletinDto.getAdresseEcole());
      bulletin1.setTelEcole(bulletinDto.getTelEcole());
      bulletin1.setAnneeId(bulletinDto.getAnneeId());
      bulletin1.setAnneeLibelle(bulletinDto.getAnneeLibelle());
      bulletin1.setPeriodeId(bulletinDto.getPeriodeId());
      bulletin1.setLibellePeriode(bulletinDto.getLibellePeriode());
      bulletin1.setMatricule(bulletinDto.getMatricule());
      bulletin1.setStatut(bulletinDto.getStatut());
      bulletin1.setNom(bulletinDto.getNom());
      bulletin1.setPrenoms(bulletinDto.getPrenoms());
      bulletin1.setSexe(bulletinDto.getSexe());
      bulletin1.setDateNaissance(bulletinDto.getDateNaissance());
      bulletin1.setLieuNaissance(bulletinDto.getLieuNaissance());
      bulletin1.setNationalite(bulletinDto.getNationalite());
      bulletin1.setRedoublant(bulletinDto.getRedoublant());
      bulletin1.setBoursier(bulletinDto.getBoursier());
      bulletin1.setAffecte(bulletinDto.getAffecte());
      bulletin1.setClasseId(bulletinDto.getClasseId());
      bulletin1.setLibelleClasse(bulletinDto.getLibelleClasse());
      bulletin1.setEffectif(bulletinDto.getEffectif());
      bulletin1.setTotalCoef(bulletinDto.getTotalCoef());
      bulletin1.setTotalMoyCoef(bulletinDto.getTotalMoyCoef());
      bulletin1.setCodeProfPrincipal(bulletinDto.getCodeProfPrincipal());
      bulletin1.setNomPrenomProfPrincipal(bulletinDto.getNomPrenomProfPrincipal());
      bulletin1.setCodeEducateur(bulletinDto.getCodeEducateur());
      bulletin1.setNomPrenomEducateur(bulletinDto.getNomPrenomEducateur());
      bulletin1.setHeuresAbsJustifiees(bulletinDto.getHeuresAbsJustifiees());
      bulletin1.setHeuresAbsNonJustifiees(bulletinDto.getHeuresAbsNonJustifiees());
      bulletin1.setMoyGeneral(bulletinDto.getMoyGeneral());
      bulletin1.setMoyMax(bulletinDto.getMoyMax());
      bulletin1.setMoyMin(bulletinDto.getMoyMin());
      bulletin1.setMoyAvg(bulletinDto.getMoyAvg());
      bulletin1.setMoyAn(bulletinDto.getMoyAn());
      bulletin1.setRangAn(bulletinDto.getRangAn());
      bulletin1.setApprAn(bulletinDto.getApprAn());
      bulletin1.setAppreciation(bulletinDto.getAppreciation());

      Date currentDate = new Date();
      bulletin1.setDateCreation(currentDate);
      System.out.println("bulletinDto.getEcoleId() B "+bulletinDto.getEcoleId());
      bulletin1.setDateUpdate(bulletinDto.getDateUpdate());
      bulletin1.setNumDecisionAffecte(bulletinDto.getNumDecisionAffecte());
      bulletin1.setLv2(bulletinDto.getLv2());
      bulletin1.setNature(bulletinDto.getNature());
      bulletin1.setIsClassed(bulletinDto.getIsClassed());
      bulletin1.setCodeQr(bulletinDto.getCodeQr());
      bulletin1.setStatut(bulletinDto.getStatut());
      bulletin1.setRang(bulletinDto.getRang());
      bulletin1.setNiveau(bulletinDto.getNiveau());
      bulletin1.setOrdreNiveau(bulletinDto.getOrdreNiveau());
      bulletin1.setEcoleOrigine(bulletinDto.getEcoleOrigine());
      bulletin1.setNomSignataire(bulletinDto.getNomSignataire());
      bulletin1.setPhoto_eleve(bulletinDto.getPhoto_eleve());
      bulletin1.setNiveauEnseignementId(bulletinDto.getNiveauEnseignementId());
      bulletin1.setTransfert(bulletinDto.getTransfert());
      bulletin1.setIvoirien(bulletinDto.getIvoirien());
      bulletin1.setNiveauLibelle(bulletinDto.getNiveauLibelle());
      System.out.println("bulletinDto.getEcoleId() C "+bulletinDto.getEcoleId());
      bulletin1.persist();
      bulletinresul=bulletin1;
      System.out.println("Bulletin  créé");
    }
    return bulletinresul;

  }

  @Transactional
 public String creerDetailsBulletins(List<ImportReportDto> importReportDto) {
    List<BulletinDto>b = new ArrayList<>() ;
    b= processBulletinDto(importReportDto);
    String messageRetour="";
    if(!b.isEmpty()) {
      System.out.println("Longueur Bulletin "+b.size());
      for (int i = 0; i < b.size(); i++) {
        Bulletin bulletin = new Bulletin();
        bulletin= creerBulletinseleve(b.get(i));
        if(bulletin!=null) {
          List<DetailBulletin> checkDetailBulletinExis= new ArrayList<>() ;
          checkDetailBulletinExis= getcheckDetailBulletin(bulletin.getId()) ;

          if(!checkDetailBulletinExis.isEmpty()){
            for (int k = 0; k < checkDetailBulletinExis.size(); k++) {
              DetailBulletin de = new DetailBulletin();
              de= DetailBulletin.findById(checkDetailBulletinExis.get(k).getId());
              de.delete();
            }

            for (int j = 0; j < b.get(i).getDetails().size(); j++) {
              DetailBulletinDto d = new DetailBulletinDto();
              d= b.get(i).getDetails().get(j);
              DetailBulletin detailBulletin = new DetailBulletin();
              UUID uuid = UUID.randomUUID();
              detailBulletin.setId(uuid.toString());
              detailBulletin.setAppreciation(d.getAppreciation());
              detailBulletin.setMatiereId(d.getMatiereId());
              detailBulletin.setMatiereRealId(d.getMatiereRealId());
              detailBulletin.setMatiereLibelle(d.getMatiereLibelle());
              detailBulletin.setCategorieMatiere(d.getCategorieMatiere());
              detailBulletin.setMoyenne(d.getMoyenne());
              detailBulletin.setRang(d.getRang());
              detailBulletin.setCoef(d.getCoef());
              detailBulletin.setMoyCoef(d.getMoyCoef());
              detailBulletin.setCategorie(d.getCategorie());
              detailBulletin.setNum_ordre(d.getNumOrdre());
              detailBulletin.setNom_prenom_professeur(d.getNomPrenomProfesseur());
              detailBulletin.setIsAdjustment(d.getIsAdjustment());
              detailBulletin.setMoyAn(d.getMoyAn());
              detailBulletin.setRangAn(d.getRangAn());
              detailBulletin.setAppreciationAn(d.getAppreciationAn());
              detailBulletin.setPec(d.getPec());
              detailBulletin.setBonus(d.getBonus());
              detailBulletin.setParentMatiere(d.getParentMatiere());
              detailBulletin.setTestLourdNoteSur(d.getTestLourdNoteSur());
              detailBulletin.setTestLourdNote(d.getTestLourdNote());
              detailBulletin.setMoyenneIntermediaire(d.getMoyenneIntermediaire());
              detailBulletin.setIsRanked(d.getIsRanked());
              detailBulletin.setBulletin(bulletin);
              Date currentDate = new Date();
              detailBulletin.setDateCreation(currentDate);
              detailBulletin.setSexeProfesseur(d.getSexeProfesseur());
              detailBulletin.persist();
              messageRetour="les bulletins modifier avec succès!";
            }
          } else {
            for (int j = 0; j < b.get(i).getDetails().size(); j++) {
              DetailBulletinDto d = new DetailBulletinDto();
              d= b.get(i).getDetails().get(j);
              DetailBulletin detailBulletin = new DetailBulletin();
              UUID uuid = UUID.randomUUID();
              detailBulletin.setId(uuid.toString());
              detailBulletin.setAppreciation(d.getAppreciation());
              detailBulletin.setMatiereId(d.getMatiereId());
              detailBulletin.setMatiereRealId(d.getMatiereRealId());
              detailBulletin.setMatiereLibelle(d.getMatiereLibelle());
              detailBulletin.setCategorieMatiere(d.getCategorieMatiere());
              detailBulletin.setMoyenne(d.getMoyenne());
              detailBulletin.setRang(d.getRang());
              detailBulletin.setCoef(d.getCoef());
              detailBulletin.setMoyCoef(d.getMoyCoef());
              detailBulletin.setCategorie(d.getCategorie());
              detailBulletin.setNum_ordre(d.getNumOrdre());
              detailBulletin.setNom_prenom_professeur(d.getNomPrenomProfesseur());
              detailBulletin.setIsAdjustment(d.getIsAdjustment());
              detailBulletin.setMoyAn(d.getMoyAn());
              detailBulletin.setRangAn(d.getRangAn());
              detailBulletin.setAppreciationAn(d.getAppreciationAn());
              detailBulletin.setPec(d.getPec());
              detailBulletin.setBonus(d.getBonus());
              detailBulletin.setParentMatiere(d.getParentMatiere());
              detailBulletin.setTestLourdNoteSur(d.getTestLourdNoteSur());
              detailBulletin.setTestLourdNote(d.getTestLourdNote());
              detailBulletin.setMoyenneIntermediaire(d.getMoyenneIntermediaire());
              detailBulletin.setIsRanked(d.getIsRanked());
              detailBulletin.setBulletin(bulletin);
              Date currentDate = new Date();
              detailBulletin.setDateCreation(currentDate);
              detailBulletin.setSexeProfesseur(d.getSexeProfesseur());
              detailBulletin.persist();
              messageRetour="les bulletins créés avec succès!";
            }
          }
        }


      }
    }
return messageRetour ;
  }


  Bulletin getBulletinEleve(String matricule ,Long idEcole, String periode , String annee){
Bulletin bulletin = new Bulletin();
try {
  bulletin= (Bulletin) em.createQuery("select o from Bulletin o  where o.matricule =:matricule  and o.anneeLibelle=:annee and " +
          " o.libellePeriode=:periode and o.ecoleId=:idEcole" )
      .setParameter("matricule", matricule)
      .setParameter("idEcole", idEcole)
      .setParameter("periode", periode)
      .setParameter("annee", annee)
      .getSingleResult() ;

} catch (NoResultException e) {
  //e.printStackTrace();
  bulletin=null;
}
    return bulletin;
  }


  List<DetailBulletin>  getcheckDetailBulletin(String idBulletin){
    List<DetailBulletin> detailbulletin = new ArrayList<>();
    try{
      detailbulletin= (List<DetailBulletin>) em.createQuery("select o from DetailBulletin o  where o.bulletin.id=:idBulletin " )
          .setParameter("idBulletin", idBulletin)
          .getResultList() ;

    } catch (NoResultException e) {
      e.printStackTrace();
      detailbulletin=null;
    }
    return detailbulletin;
  }

   public List<BulletinDto> processBulletinDto(List<ImportReportDto> importReportDto){

       List<BulletinDto> bulletin1List = new ArrayList<>();
     System.out.println("importReportDto Entree1");
       if(importReportDto!=null){
         System.out.println("importReportDto Entree2");
         for (int i = 0; i < importReportDto.size(); i++) {
          BulletinDto bulletin1= new BulletinDto();
           ImportReportDto importReportDto1 = new ImportReportDto();
           importReportDto1=importReportDto.get(i) ;
           List<DetailBulletinDto> detailBulletinList= new ArrayList<>();
           detailBulletinList =setDetailBulletinDto(importReportDto1) ;
           System.out.println("detailBulletinList ="+detailBulletinList);
           bulletin1.setEcoleId(importReportDto.get(i).getEcoleId());

           bulletin1.setNom(importReportDto.get(i).getNom());
           bulletin1.setStatut(importReportDto.get(i).getStatut());
           bulletin1.setNomEcole(importReportDto.get(i).getNomEcole());
           bulletin1.setUrlLogo(importReportDto.get(i).getUrlLogo());
           bulletin1.setAdresseEcole(importReportDto.get(i).getAdresseEcole());
           bulletin1.setTelEcole(importReportDto.get(i).getTelEcole());
           bulletin1.setAnneeId(importReportDto.get(i).getAnneeId());
           bulletin1.setAnneeLibelle(importReportDto.get(i).getAnneeLibelle());
           bulletin1.setPeriodeId(importReportDto.get(i).getPeriodeId());
           bulletin1.setLibellePeriode(importReportDto.get(i).getLibellePeriode());
           bulletin1.setMatricule(importReportDto.get(i).getMatricule());
           bulletin1.setStatut(importReportDto.get(i).getStatut());
           bulletin1.setNom(importReportDto.get(i).getNom());
           bulletin1.setPrenoms(importReportDto.get(i).getPrenoms());
           bulletin1.setSexe(importReportDto.get(i).getSexe());
           bulletin1.setDateNaissance(importReportDto.get(i).getDateNaissance());
           bulletin1.setLieuNaissance(importReportDto.get(i).getLieuNaissance());
           bulletin1.setNationalite(importReportDto.get(i).getNationalite());
           bulletin1.setRedoublant(importReportDto.get(i).getRedoublant());
           bulletin1.setBoursier(importReportDto.get(i).getBoursier());
           bulletin1.setAffecte(importReportDto.get(i).getAffecte());
           bulletin1.setClasseId(importReportDto.get(i).getClasseId());
           bulletin1.setLibelleClasse(importReportDto.get(i).getLibelleClasse());
           bulletin1.setEffectif(importReportDto.get(i).getEffectif());
           bulletin1.setTotalCoef(importReportDto.get(i).getTotalCoef());
           bulletin1.setTotalMoyCoef(importReportDto.get(i).getTotalMoyCoef());
           bulletin1.setCodeProfPrincipal(importReportDto.get(i).getCodeProfPrincipal());
           bulletin1.setNomPrenomProfPrincipal(importReportDto.get(i).getNomPrenomProfPrincipal());
           bulletin1.setCodeEducateur(importReportDto.get(i).getCodeEducateur());
           bulletin1.setNomPrenomEducateur(importReportDto.get(i).getNomPrenomEducateur());
           bulletin1.setHeuresAbsJustifiees(importReportDto.get(i).getHeuresAbsJustifiees());
           bulletin1.setHeuresAbsNonJustifiees(importReportDto.get(i).getHeuresAbsNonJustifiees());
           bulletin1.setMoyGeneral(importReportDto.get(i).getMoyGeneral());
           bulletin1.setMoyMax(importReportDto.get(i).getMoyMax());
           bulletin1.setMoyMin(importReportDto.get(i).getMoyMin());
           bulletin1.setMoyAvg(importReportDto.get(i).getMoyAvg());
           bulletin1.setMoyAn(importReportDto.get(i).getMoyAn());
           bulletin1.setRangAn(importReportDto.get(i).getRangAn());
           bulletin1.setApprAn(importReportDto.get(i).getApprAn());
           bulletin1.setAppreciation(importReportDto.get(i).getAppreciation());

           Date currentDate = new Date();
           bulletin1.setDateCreation(currentDate);
           bulletin1.setDateUpdate(importReportDto.get(i).getDateUpdate());
           bulletin1.setNumDecisionAffecte(importReportDto.get(i).getNumDecisionAffecte());
           bulletin1.setLv2(importReportDto.get(i).getLv2());
           bulletin1.setNature(importReportDto.get(i).getNature());
           bulletin1.setIsClassed(importReportDto.get(i).getIsClassed());
           bulletin1.setCodeQr(importReportDto.get(i).getCodeQr());
           bulletin1.setStatut(importReportDto.get(i).getStatut());
           bulletin1.setRang(importReportDto.get(i).getRang());
           bulletin1.setNiveau(importReportDto.get(i).getNiveau());
           bulletin1.setOrdreNiveau(importReportDto.get(i).getOrdreNiveau());
           bulletin1.setEcoleOrigine(importReportDto.get(i).getEcoleOrigine());
           bulletin1.setNomSignataire(importReportDto.get(i).getNomSignataire());
           bulletin1.setNiveauEnseignementId(importReportDto.get(i).getNiveauEnseignementId());
           bulletin1.setTransfert(importReportDto.get(i).getTransfert());
           bulletin1.setIvoirien(importReportDto.get(i).getIvoirien());
           bulletin1.setNiveauLibelle(importReportDto.get(i).getNiveauLibelle());
           bulletin1.setDetails(detailBulletinList);
           bulletin1List.add(bulletin1);

         }

       }


  return bulletin1List ;
   }

      List<DetailBulletinDto> setDetailBulletinDto(ImportReportDto importReportDto) {
        List<DetailBulletinDto> dlist = new ArrayList<>();
        System.out.println("importReportDto Entree3");
        if(importReportDto!=null){
          System.out.println("importReportDto Entree4");
          DetailBulletinDto compFr= new DetailBulletinDto();
          //CompositionFrancaise
          System.out.println("importReportDto Entree5");
          Matiere infoCompFr = new Matiere();
          infoCompFr=Matiere.findById(2L);
          compFr.setMoyenne(importReportDto.getMoyenneCompFr());
          compFr.setRang(importReportDto.getRangCompFr());
          compFr.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurCompFr());
          compFr.setSexeProfesseur(importReportDto.getSexe_professeurCompFr());
          compFr.setMoyAn(importReportDto.getMoyAnCompFr());
          compFr.setRangAn(importReportDto.getRangAnCompFr());
          compFr.setIsRanked(importReportDto.getIsRankedCompFr());
          System.out.println("importReportDto Entree6");
          compFr.setAppreciation(importReportDto.getAppreciationCompFr());
          compFr.setAppreciationAn(importReportDto.getAppreciationAnCompFr());
          System.out.println("importReportDto Entree7");
          compFr.setMatiereRealId(2L);
          compFr.setMatiereId(2L);
          System.out.println("importReportDto Entree8");
          compFr.setMatiereLibelle(infoCompFr.getLibelle());
          System.out.println("importReportDto Entree9");
          compFr.setCategorieMatiere(infoCompFr.getCategorie().getLibelle());
          System.out.println("infoCompFr 3");

          try {
            double coef = 3d;
            compFr.setCoef(coef);
            double moyCoef = importReportDto.getMoyenneCompFr() * coef;
            compFr.setMoyCoef(moyCoef);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }

          System.out.println("infoCompFr 5");
          compFr.setCategorie(infoCompFr.getCategorie().getLibelle());
          compFr.setNumOrdre(infoCompFr.getNumOrdre());
          compFr.setPec(infoCompFr.getPec());
          compFr.setParentMatiere(infoCompFr.getMatiereParent());
          dlist.add(compFr);
          ////ExpressOrl
          DetailBulletinDto expressOrl= new DetailBulletinDto();
          Matiere infoExpreOral = new Matiere();
          infoExpreOral =Matiere.findById(3L);
          expressOrl.setMoyenne(importReportDto.getMoyenneExpreOral());
          expressOrl.setRang(importReportDto.getRangExpreOral());
          expressOrl.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurExpreOral());
          expressOrl.setSexeProfesseur(importReportDto.getSexe_professeurExpreOral());
          expressOrl.setMoyAn(importReportDto.getMoyAnExpreOral());
          expressOrl.setRangAn(importReportDto.getRangAnExpreOral());
          expressOrl.setIsRanked(importReportDto.getIsRankedExpreOral());
          expressOrl.setAppreciation(importReportDto.getAppreciationExpreOral());
          expressOrl.setAppreciationAn(importReportDto.getAppreciationAnExpreOral());
          expressOrl.setMatiereRealId(3L);
          expressOrl.setMatiereId(3L);
          expressOrl.setMatiereLibelle(infoExpreOral.getLibelle());
          expressOrl.setCategorieMatiere(infoExpreOral.getCategorie().getLibelle());
         // expressOrl.setCoef(Double.valueOf(infoExpreOral.getCoef()));

          try {
            double coef = 2d;
            expressOrl.setCoef(coef);
            double moyCoef = importReportDto.getMoyenneCompFr() * coef;
            expressOrl.setMoyCoef(moyCoef);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
          System.out.println("expressOrl 5");

          expressOrl.setCategorie(infoExpreOral.getCategorie().getLibelle());
          expressOrl.setNumOrdre(infoExpreOral.getNumOrdre());
          expressOrl.setPec(infoExpreOral.getPec());
          expressOrl.setParentMatiere(infoExpreOral.getMatiereParent());
          dlist.add(expressOrl);

          /*//philoso
          DetailBulletinDto philoso= new DetailBulletinDto();
          Matiere infophiloso = new Matiere();
          infophiloso =Matiere.findById(26L);
          philoso.setMoyenne(importReportDto.getMoyennephiloso());
          philoso.setRang(importReportDto.getRangphiloso());
          philoso.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurphiloso());
          philoso.setSexeProfesseur(importReportDto.getSexe_professeurphiloso());
          philoso.setMoyAn(importReportDto.getMoyAnphiloso());
          philoso.setRangAn(importReportDto.getRangAnphiloso());
          philoso.setIsRanked(importReportDto.getIsRankedphiloso());
          philoso.setAppreciation(importReportDto.getAppreciationphiloso());
          philoso.setAppreciationAn(importReportDto.getAppreciationAnphiloso());
          philoso.setMatiereRealId(26L);
          philoso.setMatiereId(26L);
          philoso.setMatiereLibelle(infophiloso.getLibelle());
          philoso.setCategorieMatiere(infophiloso.getCategorie().getLibelle());
          philoso.setCoef(Double.valueOf(infophiloso.getCoef()));
          philoso.setMoyCoef(importReportDto.getMoyennephiloso()!=null? importReportDto.getMoyennephiloso()* Double.valueOf(infophiloso.getCoef()):0d);
          philoso.setCategorie(infophiloso.getCategorie().getLibelle());
          philoso.setNumOrdre(infophiloso.getNumOrdre());
          philoso.setPec(infophiloso.getPec());
          philoso.setParentMatiere(infophiloso.getMatiereParent());
          dlist.add(philoso);
          //Anglais
          DetailBulletinDto anglais= new DetailBulletinDto();
          Matiere infoAnglais = new Matiere();
          infoAnglais =Matiere.findById(5L);
          anglais.setMoyenne(importReportDto.getMoyenneAnglais());
          anglais.setRang(importReportDto.getRangAnglais());
          anglais.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurAnglais());
          anglais.setSexeProfesseur(importReportDto.getSexe_professeurAnglais());
          anglais.setMoyAn(importReportDto.getMoyAnAnglais());
          anglais.setRangAn(importReportDto.getRangAnAnglais());
          anglais.setIsRanked(importReportDto.getIsRankedAnglais());
          anglais.setAppreciation(importReportDto.getAppreciationAnglais());
          anglais.setAppreciationAn(importReportDto.getAppreciationAnAnglais());
          anglais.setMatiereRealId(5L);
          anglais.setMatiereId(5L);
          anglais.setMatiereLibelle(infoAnglais.getLibelle());
          anglais.setCategorieMatiere(infoAnglais.getCategorie().getLibelle());
          anglais.setCoef(Double.valueOf(infoAnglais.getCoef()));
          anglais.setMoyCoef(importReportDto.getMoyenneAnglais()!=null? importReportDto.getMoyenneAnglais()* Double.valueOf(infoAnglais.getCoef()):0d);
          anglais.setCategorie(infoAnglais.getCategorie().getLibelle());
          anglais.setNumOrdre(infoAnglais.getNumOrdre());
          anglais.setPec(infoAnglais.getPec());
          anglais.setParentMatiere(infoAnglais.getMatiereParent());
          dlist.add(anglais);
          //Math
          DetailBulletinDto math= new DetailBulletinDto();
          Matiere infoMath = new Matiere();
          infoMath =Matiere.findById(7L);
          math.setMoyenne(importReportDto.getMoyenneMath());
          math.setRang(importReportDto.getRangMath());
          math.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurMath());
          math.setSexeProfesseur(importReportDto.getSexe_professeurMath());
          math.setMoyAn(importReportDto.getMoyAnMath());
          math.setRangAn(importReportDto.getRangAnMath());
          math.setIsRanked(importReportDto.getIsRankedMath());
          math.setAppreciation(importReportDto.getAppreciationMath());
          math.setAppreciationAn(importReportDto.getAppreciationAnMath());
          math.setMatiereRealId(7L);
          math.setMatiereId(7L);
          math.setMatiereLibelle(infoMath.getLibelle());
          math.setCategorieMatiere(infoMath.getCategorie().getLibelle());
          math.setCoef(Double.valueOf(infoMath.getCoef()));
          math.setMoyCoef(importReportDto.getMoyenneMath()!=null? importReportDto.getMoyenneMath()* Double.valueOf(infoMath.getCoef()):0d);
          math.setCategorie(infoMath.getCategorie().getLibelle());
          math.setNumOrdre(infoMath.getNumOrdre());
          math.setPec(infoMath.getPec());
          math.setParentMatiere(infoMath.getMatiereParent());
          dlist.add(math);

          DetailBulletinDto physiq= new DetailBulletinDto();
          Matiere infoPhysiq = new Matiere();
          infoPhysiq =Matiere.findById(8L);
          physiq.setMoyenne(importReportDto.getMoyennePhysiq());
          physiq.setRang(importReportDto.getRangPhysiq());
          physiq.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurPhysiq());
          physiq.setSexeProfesseur(importReportDto.getSexe_professeurPhysiq());
          physiq.setMoyAn(importReportDto.getMoyAnPhysiq());
          physiq.setRangAn(importReportDto.getRangAnPhysiq());
          physiq.setIsRanked(importReportDto.getIsRankedPhysiq());
          physiq.setAppreciation(importReportDto.getAppreciationPhysiq());
          physiq.setAppreciationAn(importReportDto.getAppreciationAnPhysiq());
          physiq.setMatiereRealId(8L);
          physiq.setMatiereId(8L);
          physiq.setMatiereLibelle(infoPhysiq.getLibelle());
          physiq.setCategorieMatiere(infoPhysiq.getCategorie().getLibelle());
          physiq.setCoef(Double.valueOf(infoPhysiq.getCoef()));
          physiq.setMoyCoef(importReportDto.getMoyennePhysiq()!=null? importReportDto.getMoyennePhysiq()* Double.valueOf(infoPhysiq.getCoef()):0d);
          physiq.setCategorie(infoPhysiq.getCategorie().getLibelle());
          physiq.setNumOrdre(infoPhysiq.getNumOrdre());
          physiq.setPec(infoPhysiq.getPec());
          physiq.setParentMatiere(infoPhysiq.getMatiereParent());
          dlist.add(physiq);
          // SVT
          DetailBulletinDto svt = new DetailBulletinDto();
          Matiere infoSvt = new Matiere();
          infoSvt =Matiere.findById(9L);
          svt.setMoyenne(importReportDto.getMoyenneSVT());
          svt.setRang(importReportDto.getRangSVT());
          svt.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurSVT());
          svt.setSexeProfesseur(importReportDto.getSexe_professeurSVT());
          svt.setMoyAn(importReportDto.getMoyAnSVT());
          svt.setRangAn(importReportDto.getRangAnSVT());
          svt.setIsRanked(importReportDto.getIsRankedSVT());
          svt.setAppreciation(importReportDto.getAppreciationSVT());
          svt.setAppreciationAn(importReportDto.getAppreciationAnSVT());
          svt.setMatiereRealId(9L);
          svt.setMatiereId(9L);
          svt.setMatiereLibelle(infoSvt.getLibelle());
          svt.setCategorieMatiere(infoSvt.getCategorie().getLibelle());
          svt.setCoef(Double.valueOf(infoSvt.getCoef()));
          svt.setMoyCoef(importReportDto.getMoyenneSVT()!=null? importReportDto.getMoyenneSVT()* Double.valueOf(infoSvt.getCoef()):0d);
          svt.setCategorie(infoSvt.getCategorie().getLibelle());
          svt.setNumOrdre(infoSvt.getNumOrdre());
          svt.setPec(infoSvt.getPec());
          svt.setParentMatiere(infoSvt.getMatiereParent());
          dlist.add(svt);

          // HG
          DetailBulletinDto hg = new DetailBulletinDto();
          Matiere infohg = new Matiere();
          infohg =Matiere.findById(6L);
          hg.setMoyenne(importReportDto.getMoyenneHg());
          hg.setRang(importReportDto.getRangHg());
          hg.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurHg());
          hg.setSexeProfesseur(importReportDto.getSexe_professeurHg());
          hg.setMoyAn(importReportDto.getMoyAnHg());
          hg.setRangAn(importReportDto.getRangAnHg());
          hg.setIsRanked(importReportDto.getIsRankedHg());
          hg.setAppreciation(importReportDto.getAppreciationHg());
          hg.setAppreciationAn(importReportDto.getAppreciationAnHg());
          hg.setMatiereRealId(6L);
          hg.setMatiereId(6L);
          hg.setMatiereLibelle(infohg.getLibelle());
          hg.setCategorieMatiere(infohg.getCategorie().getLibelle());
          hg.setCoef(Double.valueOf(infohg.getCoef()));
          hg.setMoyCoef(importReportDto.getMoyenneHg()!=null? importReportDto.getMoyenneHg()* Double.valueOf(infohg.getCoef()):0d);
          hg.setCategorie(infohg.getCategorie().getLibelle());
          hg.setNumOrdre(infohg.getNumOrdre());
          hg.setPec(infohg.getPec());
          hg.setParentMatiere(infohg.getMatiereParent());
          dlist.add(hg);

          // Edhc
          DetailBulletinDto edhc = new DetailBulletinDto();
          Matiere infoEdhc = new Matiere();
          infoEdhc =Matiere.findById(11L);
          edhc.setMoyenne(importReportDto.getMoyenneEDHC());
          edhc.setRang(importReportDto.getRangEDHC());
          edhc.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurEDHC());
          edhc.setSexeProfesseur(importReportDto.getSexe_professeurEDHC());
          edhc.setMoyAn(importReportDto.getMoyAnEDHC());
          edhc.setRangAn(importReportDto.getRangAnEDHC());
          edhc.setIsRanked(importReportDto.getIsRankedEDHC());
          edhc.setAppreciation(importReportDto.getAppreciationEDHC());
          edhc.setAppreciationAn(importReportDto.getAppreciationAnEDHC());
          edhc.setMatiereRealId(11L);
          edhc.setMatiereId(11L);
          edhc.setMatiereLibelle(infoEdhc.getLibelle());
          edhc.setCategorieMatiere(infoEdhc.getCategorie().getLibelle());
          edhc.setCoef(Double.valueOf(infoEdhc.getCoef()));
          edhc.setMoyCoef(importReportDto.getMoyenneSVT()!=null? importReportDto.getMoyenneEDHC()* Double.valueOf(infoEdhc.getCoef()):0d);
          edhc.setCategorie(infoEdhc.getCategorie().getLibelle());
          edhc.setNumOrdre(infoEdhc.getNumOrdre());
          edhc.setPec(infoEdhc.getPec());
          edhc.setParentMatiere(infoEdhc.getMatiereParent());
          dlist.add(edhc);

          // Conduite
          DetailBulletinDto conduite = new DetailBulletinDto();
          Matiere infoConduite = new Matiere();
          infoConduite =Matiere.findById(12L);
          conduite.setMoyenne(importReportDto.getMoyenneConduite());
          conduite.setRang(importReportDto.getRangSVT());
          conduite.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurConduite());
          conduite.setSexeProfesseur(importReportDto.getSexe_professeurConduite());
          conduite.setMoyAn(importReportDto.getMoyAnConduite());
          conduite.setRangAn(importReportDto.getRangAnConduite());
          conduite.setIsRanked(importReportDto.getIsRankedConduite());
          conduite.setAppreciation(importReportDto.getAppreciationConduite());
          conduite.setAppreciationAn(importReportDto.getAppreciationAnConduite());
          conduite.setMatiereRealId(12L);
          conduite.setMatiereId(12L);
          conduite.setMatiereLibelle(infoConduite.getLibelle());
          conduite.setCategorieMatiere(infoConduite.getCategorie().getLibelle());
          conduite.setCoef(Double.valueOf(infoConduite.getCoef()));
          conduite.setMoyCoef(importReportDto.getMoyenneConduite()!=null? importReportDto.getMoyenneConduite()* Double.valueOf(infoConduite.getCoef()):0d);
          conduite.setCategorie(infoConduite.getCategorie().getLibelle());
          conduite.setNumOrdre(infoConduite.getNumOrdre());
          conduite.setPec(infoConduite.getPec());
          conduite.setParentMatiere(infoConduite.getMatiereParent());
          dlist.add(conduite);

          // Eps
          DetailBulletinDto eps = new DetailBulletinDto();
          Matiere infoEps = new Matiere();
          infoEps =Matiere.findById(10L);
          eps.setMoyenne(importReportDto.getMoyenneEps());
          eps.setRang(importReportDto.getRangEps());
          eps.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurEps());
          eps.setSexeProfesseur(importReportDto.getSexe_professeurEps());
          eps.setMoyAn(importReportDto.getMoyAnEps());
          eps.setRangAn(importReportDto.getRangAnEps());
          eps.setIsRanked(importReportDto.getIsRankedEps());
          eps.setAppreciation(importReportDto.getAppreciationEps());
          eps.setAppreciationAn(importReportDto.getAppreciationAnEps());
          eps.setMatiereRealId(10L);
          eps.setMatiereId(10L);
          eps.setMatiereLibelle(infoEps.getLibelle());
          eps.setCategorieMatiere(infoEps.getCategorie().getLibelle());
          eps.setCoef(Double.valueOf(infoEps.getCoef()));
          eps.setMoyCoef(importReportDto.getMoyenneEps()!=null? importReportDto.getMoyenneEps()* Double.valueOf(infoEps.getCoef()):0d);
          eps.setCategorie(infoEps.getCategorie().getLibelle());
          eps.setNumOrdre(infoEps.getNumOrdre());
          eps.setPec(infoEps.getPec());
          eps.setParentMatiere(infoEps.getMatiereParent());
          dlist.add(eps);

          // Artplastique
          DetailBulletinDto artplastique = new DetailBulletinDto();
          Matiere infoArtplastique = new Matiere();
          infoArtplastique =Matiere.findById(19L);
          artplastique.setMoyenne(importReportDto.getMoyenneArplat());
          artplastique.setRang(importReportDto.getRangArplat());
          artplastique.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurArplat());
          artplastique.setSexeProfesseur(importReportDto.getSexe_professeurArplat());
          artplastique.setMoyAn(importReportDto.getMoyAnArplat());
          artplastique.setRangAn(importReportDto.getRangAnArplat());
          artplastique.setIsRanked(importReportDto.getIsRankedArplat());
          artplastique.setAppreciation(importReportDto.getAppreciationArplat());
          artplastique.setAppreciationAn(importReportDto.getAppreciationAnArplat());
          artplastique.setMatiereRealId(19L);
          artplastique.setMatiereId(19L);
          artplastique.setMatiereLibelle(infoArtplastique.getLibelle());
          artplastique.setCategorieMatiere(infoArtplastique.getCategorie().getLibelle());
          artplastique.setCoef(Double.valueOf(infoArtplastique.getCoef()));
          artplastique.setMoyCoef(importReportDto.getMoyenneArplat()!=null? importReportDto.getMoyenneArplat()* Double.valueOf(infoArtplastique.getCoef()):0d);
          artplastique.setCategorie(infoArtplastique.getCategorie().getLibelle());
          artplastique.setNumOrdre(infoArtplastique.getNumOrdre());
          artplastique.setPec(infoArtplastique.getPec());
          artplastique.setParentMatiere(infoArtplastique.getMatiereParent());
          dlist.add(artplastique);

          // Lv2
          DetailBulletinDto lv2 = new DetailBulletinDto();
          Matiere infoLv2 = new Matiere();

          if(importReportDto.getLv2().equalsIgnoreCase("ALL")){
            infoLv2 =Matiere.findById(25L);
            lv2.setMatiereRealId(25L);
            lv2.setMatiereId(25L);
          }else {
            infoLv2 =Matiere.findById(21L);
            lv2.setMatiereRealId(21L);
            lv2.setMatiereId(21L);
          }
          lv2.setMoyenne(importReportDto.getMoyenneLv2());
          lv2.setRang(importReportDto.getRangLv2());
          lv2.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurLv2());
          lv2.setSexeProfesseur(importReportDto.getSexe_professeurLv2());
          lv2.setMoyAn(importReportDto.getMoyAnLv2());
          lv2.setRangAn(importReportDto.getRangAnLv2());
          lv2.setIsRanked(importReportDto.getIsRankedLv2());
          lv2.setAppreciation(importReportDto.getAppreciationLv2());
          lv2.setAppreciationAn(importReportDto.getAppreciationAnLv2());

          lv2.setMatiereLibelle(infoLv2.getLibelle());
          lv2.setCategorieMatiere(infoLv2.getCategorie().getLibelle());
          lv2.setCoef(Double.valueOf(infoLv2.getCoef()));
          lv2.setMoyCoef(importReportDto.getMoyenneLv2()!=null? importReportDto.getMoyenneLv2()* Double.valueOf(infoLv2.getCoef()):0d);
          lv2.setCategorie(infoLv2.getCategorie().getLibelle());
          lv2.setNumOrdre(infoLv2.getNumOrdre());
          lv2.setPec(infoLv2.getPec());
          lv2.setParentMatiere(infoLv2.getMatiereParent());
          dlist.add(lv2);

          // Tic
          DetailBulletinDto tic = new DetailBulletinDto();
          Matiere infoTic = new Matiere();
          infoTic =Matiere.findById(13L);
          tic.setMoyenne(importReportDto.getMoyenneTic());
          tic.setRang(importReportDto.getRangTic());
          tic.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurTic());
          tic.setSexeProfesseur(importReportDto.getSexe_professeurTic());
          tic.setMoyAn(importReportDto.getMoyAnTic());
          tic.setRangAn(importReportDto.getRangAnTic());
          tic.setIsRanked(importReportDto.getIsRankedTic());
          tic.setAppreciation(importReportDto.getAppreciationTic());
          tic.setAppreciationAn(importReportDto.getAppreciationAnTic());
          tic.setMatiereRealId(13L);
          tic.setMatiereId(13L);
          tic.setMatiereLibelle(infoTic.getLibelle());
          tic.setCategorieMatiere(infoTic.getCategorie().getLibelle());
          tic.setCoef(Double.valueOf(infoTic.getCoef()));
          tic.setMoyCoef(importReportDto.getMoyenneTic()!=null? importReportDto.getMoyenneTic()* Double.valueOf(infoTic.getCoef()):0d);
          tic.setCategorie(infoTic.getCategorie().getLibelle());
          tic.setNumOrdre(infoTic.getNumOrdre());
          tic.setPec(infoTic.getPec());
          tic.setParentMatiere(infoTic.getMatiereParent());
          dlist.add(tic);*/






        }
        return dlist ;
      }
}
