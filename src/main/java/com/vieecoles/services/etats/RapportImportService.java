package com.vieecoles.services.etats;

import com.vieecoles.dto.ImportMatieresClasseDto;
import com.vieecoles.dto.ImportNotesMatieresEcole;
import com.vieecoles.dto.ImportReportDto;
import com.vieecoles.dto.MatiereImportDto;
import com.vieecoles.dto.NotesAlAklaq;
import com.vieecoles.dto.NotesAlAquidah;
import com.vieecoles.dto.NotesAll;
import com.vieecoles.dto.NotesAnglais;
import com.vieecoles.dto.NotesArabe;
import com.vieecoles.dto.NotesArtVisuel;
import com.vieecoles.dto.NotesArtplas;
import com.vieecoles.dto.NotesAssirah;
import com.vieecoles.dto.NotesCompFr;
import com.vieecoles.dto.NotesConduite;
import com.vieecoles.dto.NotesEdhc;
import com.vieecoles.dto.NotesEmr;
import com.vieecoles.dto.NotesEps;
import com.vieecoles.dto.NotesEsp;
import com.vieecoles.dto.NotesExpresOral;
import com.vieecoles.dto.NotesFiq;
import com.vieecoles.dto.NotesFr;
import com.vieecoles.dto.NotesHg;
import com.vieecoles.dto.NotesInfor;
import com.vieecoles.dto.NotesMaths;
import com.vieecoles.dto.NotesMemo;
import com.vieecoles.dto.NotesOrthGram;
import com.vieecoles.dto.NotesPhiloso;
import com.vieecoles.dto.NotesPhysiques;
import com.vieecoles.dto.NotesSvt;
import com.vieecoles.dto.NotesTic;
import com.vieecoles.steph.dto.BulletinDto;
import com.vieecoles.steph.dto.DetailBulletinDto;
import com.vieecoles.steph.dto.ImportEvaluationDto;
import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.DetailBulletin;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.EvaluationLoader;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.services.EvaluationLoaderService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class RapportImportService {
  @Inject
  EntityManager em;
  @Inject
  EvaluationLoaderService evaluationLoaderService;
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
           bulletin1.setRangAn(importReportDto.get(i).getRangAn().toString());
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
          compFr.setRangAn(importReportDto.getRangAnCompFr().toString());
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
          MatiereImportDto infoExpreOral = new MatiereImportDto();
          infoExpreOral = getInfoMatiere(importReportDto.getEcoleId(),importReportDto.getBrancheId(),3L);
         if(infoExpreOral !=null) {


          expressOrl.setMoyenne(importReportDto.getMoyenneExpreOral());
          expressOrl.setRang(importReportDto.getRangExpreOral());
          expressOrl.setNomPrenomProfesseur(importReportDto.getNom_prenom_professeurExpreOral());
          expressOrl.setSexeProfesseur(importReportDto.getSexe_professeurExpreOral());
          expressOrl.setMoyAn(importReportDto.getMoyAnExpreOral());
          expressOrl.setRangAn(importReportDto.getRangAnExpreOral().toString());
          expressOrl.setIsRanked(importReportDto.getIsRankedExpreOral());
          expressOrl.setAppreciation(importReportDto.getAppreciationExpreOral());
          expressOrl.setAppreciationAn(importReportDto.getAppreciationAnExpreOral());
          expressOrl.setMatiereRealId(infoExpreOral.getMatiereId());
          expressOrl.setMatiereId(infoExpreOral.getMatiereId());
          expressOrl.setMatiereLibelle(infoExpreOral.getLibelle());
          expressOrl.setCategorieMatiere(infoExpreOral.getCategorie());
         // expressOrl.setCoef(Double.valueOf(infoExpreOral.getCoef()));

          try {
            double coef = Double.parseDouble(infoExpreOral.getCoef()) ;
            expressOrl.setCoef(coef);
            double moyCoef = importReportDto.getMoyenneCompFr() * coef;
            expressOrl.setMoyCoef(moyCoef);
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
          System.out.println("expressOrl 5");

          expressOrl.setCategorie(infoExpreOral.getCategorie());
          expressOrl.setNumOrdre(infoExpreOral.getNumOrdre());
          expressOrl.setPec(infoExpreOral.getPec());
          expressOrl.setParentMatiere(infoExpreOral.getLibelle());
          dlist.add(expressOrl);
         }
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

      private MatiereImportDto getInfoMatiere(Long ecoleId,Long brancheId,Long matiereId ){
        MatiereImportDto ecoleHasMatiere = new MatiereImportDto();
        try{
          ecoleHasMatiere= (MatiereImportDto) em.createQuery("select new com.vieecoles.dto.MatiereImportDto( m.id, m.matiere.libelle , m.matiere.id ,c.coef,m.categorie.libelle,m.numOrdre,m.pec) from ClasseMatiere c ," +
                  " EcoleHasMatiere  m where c.ecole.id=m.ecole.id and c.matiere.id=m.id and " +
                  " c.ecole.id=:ecoleId  and c.branche.id=:brancheId and m.matiere.id=:matiereId",MatiereImportDto.class )
              .setParameter("ecoleId", ecoleId)
              .setParameter("brancheId", brancheId)
              .setParameter("matiereId", matiereId)
              .getSingleResult(); ;

        } catch (NoResultException e) {
          e.printStackTrace();
          ecoleHasMatiere=null;
        }
        return ecoleHasMatiere;
      }
      public String importAndSaveNote(List<ImportMatieresClasseDto> importMatieresClasseDtos){
        String message = null;
  //List<ImportMatieresClasseDto> importMatieresClasseDtos= new ArrayList<>();
        try {
          if(!importMatieresClasseDtos.isEmpty()) {
            for (ImportMatieresClasseDto dto : importMatieresClasseDtos) {
              String uuid = null;

              uuid= importData(dto.getImportEvaluationDtos(),dto.getClasse(),dto.getAnnee());
              List<EvaluationLoader> evaLoaders=new ArrayList<>();
              evaLoaders= evaluationLoaderService.findByCode(uuid) ;

              if(!evaLoaders.isEmpty()){
                System.out.println("evaLoaders======="+evaLoaders);
               applyData(evaLoaders, dto.getMatiere(), dto.getPeriode(), dto.getAnnee(),
                 dto.getType(),dto.getNotesur(),  dto.getUser(),dto.getDate());
                message="Opération reussie";
              }
            }
          }

        } catch (Exception e){
          e.printStackTrace();
          message="Erreur d' application des données";
        }









  return message ;
      }
      @Transactional
      public  String importData(List<ImportEvaluationDto> importEvaluationDtos,Long classe,Long annee){
        String uuid = null;
        List<EvaluationLoader> evaluationLoaders = new ArrayList<EvaluationLoader>();
//    		Gson g =new Gson();
//    		System.out.println(g.toJson(importEvaluationDtos));
        for (ImportEvaluationDto dto : importEvaluationDtos) {
          EvaluationLoader load = new EvaluationLoader();
          load.setMatricule(dto.getMatricule());
          load.setNotes(dto.getNotes());
          evaluationLoaders.add(load);
        }

        uuid= evaluationLoaderService.handleLoading(evaluationLoaders, classe, annee);
      return uuid ;
      }
      @Transactional
      public String applyData(List<EvaluationLoader> evaLoaders,Long matiere,Long periode,Long annee,
                              Long type,String notesur,String user,String date){
  String message=null;

        try {
          evaluationLoaderService.appliquerChargement(evaLoaders, matiere, periode, annee, notesur, date, type, user);
          message="Opération reussie";
        } catch (Exception e) {
          e.printStackTrace();
          message="Erreur d' application des données";
        }

      return message ;
      }
public List<ImportMatieresClasseDto> processing(List<ImportNotesMatieresEcole> importNotes){
  LocalDateTime now = LocalDateTime.now();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
  String currentDate = now.format(formatter);

// Initialiser la date pour chaque élément de la liste
  importNotes.forEach(dto -> {
    dto.setDate(currentDate);
  });

  List<ImportMatieresClasseDto> importMatieresClasseDtoList = new ArrayList<>();


  for (ImportNotesMatieresEcole dto : importNotes) {


    if(dto.getMatiereMathId()==7L){
      List<ImportEvaluationDto> evalMaths= new ArrayList<>();
      ImportMatieresClasseDto noteMaths=new ImportMatieresClasseDto();

      noteMaths.setAnnee(dto.getAnnee());
      Classe classe= new Classe();

      classe= Classe.findById(dto.getClasse());
      Long idMatiereEcole=0L;
      if(classe!=null)
        idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),7L);
      if(idMatiereEcole!=null)
      {
        noteMaths.setMatiere(idMatiereEcole);
      }
      noteMaths.setClasse(dto.getClasse());
      noteMaths.setType(1L);
      noteMaths.setNotesur("20");
      noteMaths.setUser(dto.getUser());
      noteMaths.setPeriode(dto.getPeriode());
      noteMaths.setDate(dto.getDate());
      for (ImportNotesMatieresEcole dtoMath : importNotes) {
        ImportEvaluationDto evalMath = new ImportEvaluationDto();
        evalMath.setMatricule(dtoMath.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dtoMath.getNoteMath1()))
        {
          notes.add(dtoMath.getNoteMath1()) ;
        }
        if(isValidNumber(dtoMath.getNoteMath2()))
        {
          notes.add(dtoMath.getNoteMath2()) ;
        }
        if(isValidNumber(dtoMath.getNoteMath3()))
        {
          notes.add(dtoMath.getNoteMath3()) ;
        }
        if(isValidNumber(dtoMath.getNoteMath4()))
        {
          notes.add(dtoMath.getNoteMath4()) ;
        }
        if(isValidNumber(dtoMath.getNoteMath5()))
        {
          notes.add(dtoMath.getNoteMath5()) ;
        }
        evalMath.setNotes(notes);
        evalMaths.add(evalMath);

      }
      noteMaths.setImportEvaluationDtos(evalMaths);
      importMatieresClasseDtoList.add(noteMaths);


    }


  }





  return importMatieresClasseDtoList;

}

  public static boolean isValidNumber(String str) {
    // Vérifie si la chaîne est null ou vide
    if (str == null || str.trim().isEmpty()) {
      return false;
    }

    try {
      // Essaie de convertir en double (accepte les décimaux)
      Double.parseDouble(str.trim());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
  public  Long getIdMatiereEcole(Long idEcole ,Long matiere){
    try {
      Long   idMatiereEcole = (Long) em.createQuery("select o.id from EcoleHasMatiere o where o.matiere.id=:matiereId and o.ecole.id=:idEcole")
          .setParameter("matiereId",matiere)
          .setParameter("idEcole",idEcole)
          .getSingleResult();
      return  idMatiereEcole;
    } catch (NoResultException e){
      return 0L ;
    }

  }

  public List<NotesMaths> buldDtoMath(List<ImportNotesMatieresEcole> importNotes){
    List<NotesMaths> notesMathsList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

    if(dto.getMatiereMathId()==7L){
      NotesMaths notesMaths = new NotesMaths();
      Classe classe= new Classe();
      classe= Classe.findById(dto.getClasse());
      Long idMatiereEcole=0L;
      if(classe!=null)
        idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),7L);
      notesMaths.setMatiereMathId(idMatiereEcole);
      notesMaths.setMoyenMath1(dto.getNoteMath1());
      notesMaths.setMoyenMath2(dto.getNoteMath2());
      notesMaths.setMoyenMath3(dto.getNoteMath3());
      notesMaths.setMoyenMath4(dto.getNoteMath4());
      notesMaths.setMoyenMath5(dto.getNoteMath5());
      notesMaths.setMatricule(dto.getMatricule());
      notesMathsList.add(notesMaths);

    }
    }
    return notesMathsList;
  }

  public List<NotesEmr> buldDtoErm(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEmr> notesEmrList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereEmrId()==31L){
        NotesEmr notesEmr = new NotesEmr();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),31L);
        notesEmr.setMatiereEmrId(idMatiereEcole);
        notesEmr.setNoteEmr1(dto.getNoteEmr1());
        notesEmr.setNoteEmr2(dto.getNoteEmr2());
        notesEmr.setNoteEmr3(dto.getNoteEmr3());
        notesEmr.setNoteEmr4(dto.getNoteEmr4());
        notesEmr.setNoteEmr5(dto.getNoteEmr5());
        notesEmr.setMatricule(dto.getMatricule());
        notesEmrList.add(notesEmr);

      }
    }
    return notesEmrList;
  }

  public List<NotesAlAquidah> buldDtoAlaquidah(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAlAquidah> notesAlaquidahList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereAlquidahId()==37L){
        NotesAlAquidah notesAlAquidah = new NotesAlAquidah();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),37L);
        notesAlAquidah.setMatiereAlquidahId(idMatiereEcole);
        notesAlAquidah.setNoteAlquidah1(dto.getNoteAlquidah1());
        notesAlAquidah.setNoteAlquidah2(dto.getNoteAlquidah2());
        notesAlAquidah.setNoteAlquidah3(dto.getNoteAlquidah3());
        notesAlAquidah.setNoteAlquidah4(dto.getNoteAlquidah4());
        notesAlAquidah.setNoteAlquidah5(dto.getNoteAlquidah5());
        notesAlAquidah.setMatricule(dto.getMatricule());
        notesAlaquidahList.add(notesAlAquidah);

      }
    }
    return notesAlaquidahList;
  }



  public List<NotesAlAklaq> buldDtoAlAklaq(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAlAklaq> notesAlAklaqList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereAlAklaqId()==38L){
        NotesAlAklaq notesAlAklaq = new NotesAlAklaq();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),38L);
        notesAlAklaq.setMatiereAlAklaqId(idMatiereEcole);
        notesAlAklaq.setNoteAlAklaq1(dto.getNoteAlAklaq1());
        notesAlAklaq.setNoteAlAklaq2(dto.getNoteAlAklaq2());
        notesAlAklaq.setNoteAlAklaq3(dto.getNoteAlAklaq3());
        notesAlAklaq.setNoteAlAklaq4(dto.getNoteAlAklaq4());
        notesAlAklaq.setNoteAlAklaq5(dto.getNoteAlAklaq5());
        notesAlAklaq.setMatricule(dto.getMatricule());
        notesAlAklaqList.add(notesAlAklaq);

      }
    }
    return notesAlAklaqList;
  }

  public List<NotesArabe> buldDtoArabe(List<ImportNotesMatieresEcole> importNotes){
    List<NotesArabe> notesArabeList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereArabeId()==73L){
        NotesArabe notesArabe = new NotesArabe();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),73L);
        notesArabe.setMatiereArabeId(idMatiereEcole);
        notesArabe.setNoteArabe1(dto.getNoteArabe1());
        notesArabe.setNoteArabe2(dto.getNoteArabe2());
        notesArabe.setNoteArabe3(dto.getNoteArabe3());
        notesArabe.setNoteArabe4(dto.getNoteArabe4());
        notesArabe.setNoteArabe5(dto.getNoteArabe5());
        notesArabe.setMatricule(dto.getMatricule());
        notesArabeList.add(notesArabe);

      }
    }
    return notesArabeList;
  }
  public List<NotesMemo> buldDtoMemo(List<ImportNotesMatieresEcole> importNotes){
    List<NotesMemo> notesMemoList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereMemoId()==29L){
        NotesMemo notesMemo = new NotesMemo();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),29L);
        notesMemo.setMatiereMemoId(idMatiereEcole);
        notesMemo.setNoteMemo1(dto.getNoteMemo1());
        notesMemo.setNoteMemo2(dto.getNoteMemo2());
        notesMemo.setNoteMemo3(dto.getNoteMemo3());
        notesMemo.setNoteMemo4(dto.getNoteMemo4());
        notesMemo.setNoteMemo5(dto.getNoteMemo5());
        notesMemo.setMatricule(dto.getMatricule());
        notesMemoList.add(notesMemo);

      }
    }
    return notesMemoList;
  }

  public List<NotesFiq> buldDtoFiq(List<ImportNotesMatieresEcole> importNotes){
    List<NotesFiq> notesFiqList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereFiqId()==30L){
        NotesFiq notesFiq = new NotesFiq();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),30L);
        notesFiq.setMatiereFiqId(idMatiereEcole);
        notesFiq.setNoteFiq1(dto.getNoteFiq1());
        notesFiq.setNoteFiq2(dto.getNoteFiq2());
        notesFiq.setNoteFiq3(dto.getNoteFiq3());
        notesFiq.setNoteFiq4(dto.getNoteFiq4());
        notesFiq.setNoteFiq5(dto.getNoteFiq5());
        notesFiq.setMatricule(dto.getMatricule());
        notesFiqList.add(notesFiq);

      }
    }
    return notesFiqList;
  }
  public List<NotesAssirah> buldDtoAssirah(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAssirah> notesAssirahList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereAssirahId()==35L){
        NotesAssirah notesAssirah = new NotesAssirah();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),35L);
        notesAssirah.setMatiereAssirahId(idMatiereEcole);
        notesAssirah.setNoteAssirah1(dto.getNoteAssirah1());
        notesAssirah.setNoteAssirah2(dto.getNoteAssirah2());
        notesAssirah.setNoteAssirah3(dto.getNoteAssirah3());
        notesAssirah.setNoteAssirah4(dto.getNoteAssirah4());
        notesAssirah.setNoteAssirah5(dto.getNoteAssirah5());
        notesAssirah.setMatricule(dto.getMatricule());
        notesAssirahList.add(notesAssirah);

      }
    }
    return notesAssirahList;
  }
  public List<NotesArtVisuel> buldDtoArtVisuel(List<ImportNotesMatieresEcole> importNotes){
    List<NotesArtVisuel> notesArabeList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereArtVisuId()==36L){
        NotesArtVisuel notesArtVisuel = new NotesArtVisuel();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),36L);
        notesArtVisuel.setMatiereArtVisuId(idMatiereEcole);
        notesArtVisuel.setNoteArtVisu1(dto.getNoteArtVisu1());
        notesArtVisuel.setNoteArtVisu2(dto.getNoteArtVisu2());
        notesArtVisuel.setNoteArtVisu3(dto.getNoteArtVisu3());
        notesArtVisuel.setNoteArtVisu4(dto.getNoteArtVisu4());
        notesArtVisuel.setNoteArtVisu5(dto.getNoteArtVisu5());
        notesArtVisuel.setMatricule(dto.getMatricule());
        notesArabeList.add(notesArtVisuel);

      }
    }
    return notesArabeList;
  }

  public List<NotesFr> buldDtoFr(List<ImportNotesMatieresEcole> importNotes){
    List<NotesFr> notesFrList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereFranId()==1L){
        NotesFr notesFr = new NotesFr();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),1L);
        notesFr.setMatiereFranId(idMatiereEcole);
        notesFr.setNoteFran1(dto.getNoteFran1());
        notesFr.setNoteFran2(dto.getNoteFran2());
        notesFr.setNoteFran3(dto.getNoteFran3());
        notesFr.setNoteFran4(dto.getNoteFran4());
        notesFr.setNoteFran5(dto.getNoteFran5());
        notesFr.setMatricule(dto.getMatricule());
        notesFrList.add(notesFr);

      }
    }
    return notesFrList;
  }

  public List<NotesAll> buldDtoAll(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAll> notesAllList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereAllId()==25L){
        NotesAll notesAll = new NotesAll();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),25L);
        notesAll.setMatiereAllId(idMatiereEcole);
        notesAll.setNoteAll1(dto.getNoteAll1());
        notesAll.setNoteAll2(dto.getNoteAll2());
        notesAll.setNoteAll3(dto.getNoteAll3());
        notesAll.setNoteAll4(dto.getNoteAll4());
        notesAll.setNoteAll5(dto.getNoteAll5());
        notesAll.setMatricule(dto.getMatricule());
        notesAllList.add(notesAll);

      }

    }
    return notesAllList;
  }
  public List<NotesEsp> buldDtoEsp(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEsp> notesEspList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereEspId()==21L){
        NotesEsp notesEsp = new NotesEsp();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),21L);
        notesEsp.setMatiereEspId(idMatiereEcole);
        notesEsp.setNoteEsp1(dto.getNoteEsp1());
        notesEsp.setNoteEsp2(dto.getNoteEsp2());
        notesEsp.setNoteEsp3(dto.getNoteEsp3());
        notesEsp.setNoteEsp4(dto.getNoteEsp4());
        notesEsp.setNoteEsp5(dto.getNoteEsp5());
        notesEsp.setMatricule(dto.getMatricule());
        notesEspList.add(notesEsp);

      }

    }
    return notesEspList;
  }
  public List<NotesAnglais> buldDtoAng(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAnglais> notesAngList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereAngId()==5L){
        NotesAnglais notesAnglais = new NotesAnglais();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),5L);
        notesAnglais.setMatiereAngId(idMatiereEcole);
        notesAnglais.setNoteAng1(dto.getNoteAng1());
        notesAnglais.setNoteAng2(dto.getNoteAng2());
        notesAnglais.setNoteAng3(dto.getNoteAng3());
        notesAnglais.setNoteAng4(dto.getNoteAng4());
        notesAnglais.setNoteAng5(dto.getNoteAng5());
        notesAnglais.setMatricule(dto.getMatricule());
        notesAngList.add(notesAnglais);

      }

    }
    return notesAngList;
  }
  public List<NotesArtplas> buldDtoArt(List<ImportNotesMatieresEcole> importNotes){
    List<NotesArtplas> notesArtplaList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereArplatId()==36L){
        NotesArtplas notesArtplas = new NotesArtplas();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),36L);
        notesArtplas.setMatiereArplatId(idMatiereEcole);
        notesArtplas.setNoteArplat1(dto.getNoteArplat1());
        notesArtplas.setNoteArplat2(dto.getNoteArplat2());
        notesArtplas.setNoteArplat3(dto.getNoteArplat3());
        notesArtplas.setNoteArplat4(dto.getNoteArplat4());
        notesArtplas.setNoteArplat5(dto.getNoteArplat5());
        notesArtplas.setMatricule(dto.getMatricule());
        notesArtplaList.add(notesArtplas);

      }

    }
    return notesArtplaList;
  }
  public List<NotesCompFr> buldDtoCompoFr(List<ImportNotesMatieresEcole> importNotes){
    List<NotesCompFr> notesCompoFrList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereCompoFrId()==2L){
        NotesCompFr notesCompoFr = new NotesCompFr();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),2L);
        notesCompoFr.setMatiereCompoFrId(idMatiereEcole);
        notesCompoFr.setNoteCompoFr1(dto.getNoteCompoFr1());
        notesCompoFr.setNoteCompoFr2(dto.getNoteCompoFr2());
        notesCompoFr.setNoteCompoFr3(dto.getNoteCompoFr3());
        notesCompoFr.setNoteCompoFr4(dto.getNoteCompoFr4());
        notesCompoFr.setNoteCompoFr5(dto.getNoteCompoFr5());
        notesCompoFr.setMatricule(dto.getMatricule());
        notesCompoFrList.add(notesCompoFr);

      }

    }
    return notesCompoFrList;
  }
  public List<NotesConduite> buldDtoConduite(List<ImportNotesMatieresEcole> importNotes){
    List<NotesConduite> notesConduiteList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereConduiteId()==12L){
        NotesConduite notesconduite = new NotesConduite();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),12L);
        notesconduite.setMatiereConduiteId(idMatiereEcole);
        notesconduite.setNoteConduite1(dto.getNoteConduite1());
        notesconduite.setNoteConduite2(dto.getNoteConduite2());
        notesconduite.setNoteConduite3(dto.getNoteConduite3());
        notesconduite.setNoteConduite4(dto.getNoteConduite4());
        notesconduite.setNoteConduite5(dto.getNoteConduite5());
        notesconduite.setMatricule(dto.getMatricule());
        notesConduiteList.add(notesconduite);

      }

    }
    return notesConduiteList;
  }
  public List<NotesOrthGram> buldDtoOrthGram(List<ImportNotesMatieresEcole> importNotes){
    List<NotesOrthGram> notesOrthGramList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereOrthoGramId()==4L){
        NotesOrthGram notesOrthGram = new NotesOrthGram();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),4L);
        notesOrthGram.setMatiereOrthoGramId(idMatiereEcole);
        notesOrthGram.setNoteOrthoGram1(dto.getNoteOrthoGram1());
        notesOrthGram.setNoteOrthoGram2(dto.getNoteOrthoGram2());
        notesOrthGram.setNoteOrthoGram3(dto.getNoteOrthoGram3());
        notesOrthGram.setNoteOrthoGram4(dto.getNoteOrthoGram4());
        notesOrthGram.setNoteOrthoGram5(dto.getNoteOrthoGram5());
        notesOrthGram.setMatricule(dto.getMatricule());
        notesOrthGramList.add(notesOrthGram);

      }

    }
    return notesOrthGramList;
  }
  public List<NotesPhysiques> buldDtoPhysiques(List<ImportNotesMatieresEcole> importNotes){
    List<NotesPhysiques> notesPhysiquesList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatierePhysiqId()==8L){
        NotesPhysiques notesPhysiques = new NotesPhysiques();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),8L);
        notesPhysiques.setMatierePhysiqId(idMatiereEcole);
        notesPhysiques.setMoyenPhysiq1(dto.getNotePhysiq1());
        notesPhysiques.setMoyenPhysiq2(dto.getNotePhysiq2());
        notesPhysiques.setMoyenPhysiq3(dto.getNotePhysiq3());
        notesPhysiques.setMoyenPhysiq4(dto.getNotePhysiq4());
        notesPhysiques.setMoyenPhysiq5(dto.getNotePhysiq5());
        notesPhysiques.setMatricule(dto.getMatricule());
        notesPhysiquesList.add(notesPhysiques);

      }

    }
    return notesPhysiquesList;
  }
  public List<NotesEps> buldDtoEps(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEps> notesEpsList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereEpsId()==10L){
        NotesEps notesEps = new NotesEps();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),10L);
        notesEps.setMatiereEpsId(idMatiereEcole);
        notesEps.setNoteEps1(dto.getNoteEps1());
        notesEps.setNoteEps2(dto.getNoteEps2());
        notesEps.setNoteEps3(dto.getNoteEps3());
        notesEps.setNoteEps4(dto.getNoteEps4());
        notesEps.setNoteEps5(dto.getNoteEps5());
        notesEps.setMatricule(dto.getMatricule());
        notesEpsList.add(notesEps);

      }

    }
    return notesEpsList;
  }
  public List<NotesPhiloso> buldDtoPhilo(List<ImportNotesMatieresEcole> importNotes){
    List<NotesPhiloso> notesPhiloList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatierephilosoId()==26L){
        NotesPhiloso notesPhilo = new NotesPhiloso();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),26L);
        notesPhilo.setMatierephilosoId(idMatiereEcole);
        notesPhilo.setNotephiloso1(dto.getNotephiloso1());
        notesPhilo.setNotephiloso2(dto.getNotephiloso2());
        notesPhilo.setNotephiloso3(dto.getNotephiloso3());
        notesPhilo.setNotephiloso4(dto.getNotephiloso4());
        notesPhilo.setNotephiloso5(dto.getNotephiloso5());
        notesPhilo.setMatricule(dto.getMatricule());
        notesPhiloList.add(notesPhilo);

      }

    }
    return notesPhiloList;
  }
  public List<NotesSvt> buldDtoSvt(List<ImportNotesMatieresEcole> importNotes){
    List<NotesSvt> notesSvtList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereSVTId()==9L){
        NotesSvt notesSvt = new NotesSvt();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),9L);
        notesSvt.setMatiereSVTId(idMatiereEcole);
        notesSvt.setNoteSVT1(dto.getNoteSVT1());
        notesSvt.setNoteSVT2(dto.getNoteSVT2());
        notesSvt.setNoteSVT3(dto.getNoteSVT3());
        notesSvt.setNoteSVT4(dto.getNoteSVT4());
        notesSvt.setNoteSVT5(dto.getNoteSVT5());
        notesSvt.setMatricule(dto.getMatricule());
        notesSvtList.add(notesSvt);

      }

    }
    return notesSvtList;
  }
  public List<NotesTic> buldDtoTic(List<ImportNotesMatieresEcole> importNotes){
    List<NotesTic> notesTicList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereTicId()==27L){
        NotesTic notesTic = new NotesTic();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),27L);
        notesTic.setMatiereTicId(idMatiereEcole);
        notesTic.setNoteTic1(dto.getNoteTic1());
        notesTic.setNoteTic2(dto.getNoteTic2());
        notesTic.setNoteTic3(dto.getNoteTic3());
        notesTic.setNoteTic4(dto.getNoteTic4());
        notesTic.setNoteTic5(dto.getNoteTic5());
        notesTic.setMatricule(dto.getMatricule());
        notesTicList.add(notesTic);

      }

    }
    return notesTicList;
  }

  public List<NotesInfor> buldDtoInfor(List<ImportNotesMatieresEcole> importNotes){
    List<NotesInfor> notesTicList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereInforId()==13L){
        NotesInfor notesInfor = new NotesInfor();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),13L);
        notesInfor.setMatiereInforId(idMatiereEcole);
        notesInfor.setNoteInfor1(dto.getNoteInfor1());
        notesInfor.setNoteInfor2(dto.getNoteInfor2());
        notesInfor.setNoteInfor3(dto.getNoteInfor3());
        notesInfor.setNoteInfor4(dto.getNoteInfor4());
        notesInfor.setNoteInfor5(dto.getNoteInfor5());
        notesInfor.setMatricule(dto.getMatricule());
        notesTicList.add(notesInfor);

      }

    }
    return notesTicList;
  }
  public List<NotesExpresOral> buldDtoExpressOral(List<ImportNotesMatieresEcole> importNotes){
    List<NotesExpresOral> notesExpressOralList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereExpreOralId()==3L){
        NotesExpresOral notesExpresOral = new NotesExpresOral();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),3L);
        notesExpresOral.setMatiereExpreOralId(idMatiereEcole);
        notesExpresOral.setNoteExpreOral1(dto.getNoteExpreOral1());
        notesExpresOral.setNoteExpreOral2(dto.getNoteExpreOral2());
        notesExpresOral.setNoteExpreOral3(dto.getNoteExpreOral3());
        notesExpresOral.setNoteExpreOral4(dto.getNoteExpreOral4());
        notesExpresOral.setNoteExpreOral5(dto.getNoteExpreOral5());
        notesExpresOral.setMatricule(dto.getMatricule());
        notesExpressOralList.add(notesExpresOral);

      }

    }
    return notesExpressOralList;
  }

  public List<NotesHg> buldDtoHg(List<ImportNotesMatieresEcole> importNotes){
    List<NotesHg> notesHgList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereHgId()==6L){
        NotesHg notesHg = new NotesHg();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),6L);
        notesHg.setMatiereHgId(idMatiereEcole);
        notesHg.setNoteHg1(dto.getNoteHg1());
        notesHg.setNoteHg2(dto.getNoteHg2());
        notesHg.setNoteHg3(dto.getNoteHg3());
        notesHg.setNoteHg4(dto.getNoteHg4());
        notesHg.setNoteHg5(dto.getNoteHg5());
        notesHg.setMatricule(dto.getMatricule());
        notesHgList.add(notesHg);

      }

    }
    return notesHgList;
  }
  public List<NotesEdhc> buldDtoEdhc(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEdhc> notesEdhcList= new ArrayList<>();
    for (ImportNotesMatieresEcole dto : importNotes) {

      if(dto.getMatiereEdhcId()==11L){
        NotesEdhc notesEdhc = new NotesEdhc();
        Classe classe= new Classe();
        classe= Classe.findById(dto.getClasse());
        Long idMatiereEcole=0L;
        if(classe!=null)
          idMatiereEcole = getIdMatiereEcole(classe.getEcole().getId(),11L);
        notesEdhc.setMatiereEdhcId(idMatiereEcole);
        notesEdhc.setNoteEdhc1(dto.getNoteEdhc1());
        notesEdhc.setNoteEdhc2(dto.getNoteEdhc2());
        notesEdhc.setNoteEdhc3(dto.getNoteEdhc3());
        notesEdhc.setNoteEdhc4(dto.getNoteEdhc4());
        notesEdhc.setNoteEdhc5(dto.getNoteEdhc5());
        notesEdhc.setMatricule(dto.getMatricule());
        notesEdhcList.add(notesEdhc);
      }

    }
    return notesEdhcList;
  }
  public List<ImportMatieresClasseDto> processDto(List<ImportNotesMatieresEcole> importNotes) {
  // create Math Notes
    List<NotesMaths> notesMathsList= new ArrayList<>();
    List<ImportMatieresClasseDto> importMatieresClasseDtoList = new ArrayList<>();
    if(!importNotes.isEmpty()) {
      ImportMatieresClasseDto importMaths = new ImportMatieresClasseDto();
      ImportMatieresClasseDto importPhysiq = new ImportMatieresClasseDto();
      ImportMatieresClasseDto importAnglais = new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportAll= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportEsp= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportEps= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportArt= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportCompoFr= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportOrthGram= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportPhiloso= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportTic= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportSvt= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportConduite= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportEdhc= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportExpresOral= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportHg= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportFr= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportArabe= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportMemo= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportFiq= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportAssirah= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportArtVisu= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportEmr= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportAlquidah= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportAlAklaq= new ImportMatieresClasseDto();
      ImportMatieresClasseDto ImportInform= new ImportMatieresClasseDto();



      importMaths = processMath(importNotes);
      importPhysiq = processPhysiques(importNotes);
     importAnglais =processAnglais(importNotes);
      ImportAll = processAll(importNotes);
      ImportEsp= processEsp(importNotes);
      ImportEps=processEps(importNotes);
      ImportArt =processArtplas(importNotes);
      ImportCompoFr= processComposFr(importNotes);
      ImportOrthGram= processOrthoGram(importNotes);
      ImportPhiloso=processPhiloso(importNotes);
      ImportTic=processTic(importNotes);
      ImportSvt=processSvt(importNotes);
      ImportConduite=processConduite(importNotes) ;
      ImportEdhc = processEdhc(importNotes);
      ImportHg= processHg(importNotes);
      ImportExpresOral=processExpresOral(importNotes);
      ImportFr= processFr(importNotes);
      ImportArabe=processArabe(importNotes);
      ImportMemo=processMemo(importNotes);
      ImportFiq=processFiq(importNotes);
      ImportAssirah=processAssirah(importNotes);
      ImportArtVisu=processArtVisu(importNotes);
      ImportEmr=processEmr(importNotes);
      ImportAlquidah=processAlquidah(importNotes);
      ImportAlAklaq=processAlAklaq(importNotes);
      ImportInform= processInfor(importNotes);

      importMatieresClasseDtoList.add(importPhysiq);
      importMatieresClasseDtoList.add(importAnglais);
       importMatieresClasseDtoList.add(ImportAll);
      importMatieresClasseDtoList.add(ImportEsp);
      importMatieresClasseDtoList.add(ImportEps);
      importMatieresClasseDtoList.add(ImportArt);
      importMatieresClasseDtoList.add(ImportCompoFr);
      importMatieresClasseDtoList.add(ImportOrthGram);
      importMatieresClasseDtoList.add(ImportTic);
      importMatieresClasseDtoList.add(ImportSvt);
      importMatieresClasseDtoList.add(ImportPhiloso);
      importMatieresClasseDtoList.add(ImportConduite);
      importMatieresClasseDtoList.add(importMaths);
      importMatieresClasseDtoList.add(ImportEdhc);
      importMatieresClasseDtoList.add(ImportHg);
      importMatieresClasseDtoList.add(ImportExpresOral);
      importMatieresClasseDtoList.add(ImportFr);
      importMatieresClasseDtoList.add(ImportArabe);
      importMatieresClasseDtoList.add(ImportMemo);
      importMatieresClasseDtoList.add(ImportFiq);
      importMatieresClasseDtoList.add(ImportAssirah);
      importMatieresClasseDtoList.add(ImportArtVisu);
      importMatieresClasseDtoList.add(ImportEmr);
      importMatieresClasseDtoList.add(ImportAlquidah);
      importMatieresClasseDtoList.add(ImportAlAklaq);
      importMatieresClasseDtoList.add(ImportInform);


    }

    return importMatieresClasseDtoList ;
  }

  ImportMatieresClasseDto processMath(List<ImportNotesMatieresEcole> importNotes){
    List<NotesMaths> notesMathsList= new ArrayList<>();
    notesMathsList = buldDtoMath(importNotes);
    ImportMatieresClasseDto importMaths = new ImportMatieresClasseDto();
    if(!notesMathsList.isEmpty()) {

      importMaths.setClasse(importNotes.get(0).getClasse());
      importMaths.setMatiere(notesMathsList.get(0).getMatiereMathId());
      importMaths.setAnnee(importNotes.get(0).getAnnee());
      importMaths.setPeriode(importNotes.get(0).getPeriode());
      importMaths.setDate(importNotes.get(0).getDate());
      importMaths.setType(1L);
      importMaths.setNotesur("20");
      List<ImportEvaluationDto> evalMaths = new ArrayList<>();
      for (NotesMaths dto : notesMathsList) {
        ImportEvaluationDto evalMath = new ImportEvaluationDto();
        evalMath.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getMoyenMath1()))
        {
          notes.add(dto.getMoyenMath1()) ;
        }
        if(isValidNumber(dto.getMoyenMath2()))
        {
          notes.add(dto.getMoyenMath2()) ;
        }
        if(isValidNumber(dto.getMoyenMath3()))
        {
          notes.add(dto.getMoyenMath3()) ;
        }
        if(isValidNumber(dto.getMoyenMath4()))
        {
          notes.add(dto.getMoyenMath4()) ;
        }
        if(isValidNumber(dto.getMoyenMath5()))
        {
          notes.add(dto.getMoyenMath5()) ;
        }
        if(!notes.isEmpty()) {
          evalMath.setNotes(notes);
          evalMaths.add(evalMath);
        }

      }
      importMaths.setImportEvaluationDtos(evalMaths);


    }
    return importMaths;
  }
  ImportMatieresClasseDto processArabe(List<ImportNotesMatieresEcole> importNotes){
    List<NotesArabe> notesArabeList= new ArrayList<>();
    notesArabeList = buldDtoArabe(importNotes);
    ImportMatieresClasseDto importArabe = new ImportMatieresClasseDto();
    if(!notesArabeList.isEmpty()) {

      importArabe.setClasse(importNotes.get(0).getClasse());
      importArabe.setMatiere(notesArabeList.get(0).getMatiereArabeId());
      importArabe.setAnnee(importNotes.get(0).getAnnee());
      importArabe.setPeriode(importNotes.get(0).getPeriode());
      importArabe.setDate(importNotes.get(0).getDate());
      importArabe.setType(1L);
      importArabe.setNotesur("20");
      List<ImportEvaluationDto> evalArabes = new ArrayList<>();
      for (NotesArabe dto : notesArabeList) {
        ImportEvaluationDto evalArabe = new ImportEvaluationDto();
        evalArabe.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteArabe1()))
        {
          notes.add(dto.getNoteArabe1()) ;
        }
        if(isValidNumber(dto.getNoteArabe2()))
        {
          notes.add(dto.getNoteArabe2()) ;
        }
        if(isValidNumber(dto.getNoteArabe3()))
        {
          notes.add(dto.getNoteArabe3()) ;
        }
        if(isValidNumber(dto.getNoteArabe4()))
        {
          notes.add(dto.getNoteArabe4()) ;
        }
        if(isValidNumber(dto.getNoteArabe5()))
        {
          notes.add(dto.getNoteArabe5()) ;
        }
        if(!notes.isEmpty()) {
          evalArabe.setNotes(notes);
          evalArabes.add(evalArabe);
        }

      }
      importArabe.setImportEvaluationDtos(evalArabes);


    }
    return importArabe;
  }
  ImportMatieresClasseDto processMemo(List<ImportNotesMatieresEcole> importNotes){
    List<NotesMemo> notesMemoList= new ArrayList<>();
    notesMemoList = buldDtoMemo(importNotes);
    ImportMatieresClasseDto importMemo = new ImportMatieresClasseDto();
    if(!notesMemoList.isEmpty()) {

      importMemo.setClasse(importNotes.get(0).getClasse());
      importMemo.setMatiere(notesMemoList.get(0).getMatiereMemoId());
      importMemo.setAnnee(importNotes.get(0).getAnnee());
      importMemo.setPeriode(importNotes.get(0).getPeriode());
      importMemo.setDate(importNotes.get(0).getDate());
      importMemo.setType(1L);
      importMemo.setNotesur("20");
      List<ImportEvaluationDto> evalMemos = new ArrayList<>();
      for (NotesMemo dto : notesMemoList) {
        ImportEvaluationDto evalMemo = new ImportEvaluationDto();
        evalMemo.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteMemo1()))
        {
          notes.add(dto.getNoteMemo1()) ;
        }
        if(isValidNumber(dto.getNoteMemo2()))
        {
          notes.add(dto.getNoteMemo2()) ;
        }
        if(isValidNumber(dto.getNoteMemo3()))
        {
          notes.add(dto.getNoteMemo3()) ;
        }
        if(isValidNumber(dto.getNoteMemo4()))
        {
          notes.add(dto.getNoteMemo4()) ;
        }
        if(isValidNumber(dto.getNoteMemo5()))
        {
          notes.add(dto.getNoteMemo5()) ;
        }
        if(!notes.isEmpty()) {
          evalMemo.setNotes(notes);
          evalMemos.add(evalMemo);
        }

      }
      importMemo.setImportEvaluationDtos(evalMemos);


    }
    return importMemo;
  }

  ImportMatieresClasseDto processFiq(List<ImportNotesMatieresEcole> importNotes){
    List<NotesFiq> notesFiqList= new ArrayList<>();
    notesFiqList = buldDtoFiq(importNotes);
    ImportMatieresClasseDto importFiq = new ImportMatieresClasseDto();
    if(!notesFiqList.isEmpty()) {

      importFiq.setClasse(importNotes.get(0).getClasse());
      importFiq.setMatiere(notesFiqList.get(0).getMatiereFiqId());
      importFiq.setAnnee(importNotes.get(0).getAnnee());
      importFiq.setPeriode(importNotes.get(0).getPeriode());
      importFiq.setDate(importNotes.get(0).getDate());
      importFiq.setType(1L);
      importFiq.setNotesur("20");
      List<ImportEvaluationDto> evalFiqs = new ArrayList<>();
      for (NotesFiq dto : notesFiqList) {
        ImportEvaluationDto evalFiq = new ImportEvaluationDto();
        evalFiq.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteFiq1()))
        {
          notes.add(dto.getNoteFiq1()) ;
        }
        if(isValidNumber(dto.getNoteFiq2()))
        {
          notes.add(dto.getNoteFiq2()) ;
        }
        if(isValidNumber(dto.getNoteFiq3()))
        {
          notes.add(dto.getNoteFiq3()) ;
        }
        if(isValidNumber(dto.getNoteFiq4()))
        {
          notes.add(dto.getNoteFiq4()) ;
        }
        if(isValidNumber(dto.getNoteFiq5()))
        {
          notes.add(dto.getNoteFiq5()) ;
        }
        if(!notes.isEmpty()) {
          evalFiq.setNotes(notes);
          evalFiqs.add(evalFiq);
        }

      }
      importFiq.setImportEvaluationDtos(evalFiqs);


    }
    return importFiq;
  }
  ImportMatieresClasseDto processAssirah(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAssirah> notesAssirahList= new ArrayList<>();
    notesAssirahList = buldDtoAssirah(importNotes);
    ImportMatieresClasseDto importAssirah = new ImportMatieresClasseDto();
    if(!notesAssirahList.isEmpty()) {

      importAssirah.setClasse(importNotes.get(0).getClasse());
      importAssirah.setMatiere(notesAssirahList.get(0).getMatiereAssirahId());
      importAssirah.setAnnee(importNotes.get(0).getAnnee());
      importAssirah.setPeriode(importNotes.get(0).getPeriode());
      importAssirah.setDate(importNotes.get(0).getDate());
      importAssirah.setType(1L);
      importAssirah.setNotesur("20");
      List<ImportEvaluationDto> evalFiqs = new ArrayList<>();
      for (NotesAssirah dto : notesAssirahList) {
        ImportEvaluationDto evalFiq = new ImportEvaluationDto();
        evalFiq.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteAssirah1()))
        {
          notes.add(dto.getNoteAssirah1()) ;
        }
        if(isValidNumber(dto.getNoteAssirah2()))
        {
          notes.add(dto.getNoteAssirah2()) ;
        }
        if(isValidNumber(dto.getNoteAssirah3()))
        {
          notes.add(dto.getNoteAssirah3()) ;
        }
        if(isValidNumber(dto.getNoteAssirah4()))
        {
          notes.add(dto.getNoteAssirah4()) ;
        }
        if(isValidNumber(dto.getNoteAssirah5()))
        {
          notes.add(dto.getNoteAssirah5()) ;
        }
        if(!notes.isEmpty()) {
          evalFiq.setNotes(notes);
          evalFiqs.add(evalFiq);
        }

      }
      importAssirah.setImportEvaluationDtos(evalFiqs);


    }
    return importAssirah;
  }
  ImportMatieresClasseDto processArtVisu(List<ImportNotesMatieresEcole> importNotes){
    List<NotesArtVisuel> notesArvisuelList= new ArrayList<>();
    notesArvisuelList = buldDtoArtVisuel(importNotes);
    ImportMatieresClasseDto importArtVisuel = new ImportMatieresClasseDto();
    if(!notesArvisuelList.isEmpty()) {

      importArtVisuel.setClasse(importNotes.get(0).getClasse());
      importArtVisuel.setMatiere(notesArvisuelList.get(0).getMatiereArtVisuId());
      importArtVisuel.setAnnee(importNotes.get(0).getAnnee());
      importArtVisuel.setPeriode(importNotes.get(0).getPeriode());
      importArtVisuel.setDate(importNotes.get(0).getDate());
      importArtVisuel.setType(1L);
      importArtVisuel.setNotesur("20");
      List<ImportEvaluationDto> evalArtVisuels = new ArrayList<>();
      for (NotesArtVisuel dto : notesArvisuelList) {
        ImportEvaluationDto evalArtVisuel = new ImportEvaluationDto();
        evalArtVisuel.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteArtVisu1()))
        {
          notes.add(dto.getNoteArtVisu1()) ;
        }
        if(isValidNumber(dto.getNoteArtVisu2()))
        {
          notes.add(dto.getNoteArtVisu2()) ;
        }
        if(isValidNumber(dto.getNoteArtVisu3()))
        {
          notes.add(dto.getNoteArtVisu3()) ;
        }
        if(isValidNumber(dto.getNoteArtVisu4()))
        {
          notes.add(dto.getNoteArtVisu4()) ;
        }
        if(isValidNumber(dto.getNoteArtVisu5()))
        {
          notes.add(dto.getNoteArtVisu5()) ;
        }
        if(!notes.isEmpty()) {
          evalArtVisuel.setNotes(notes);
          evalArtVisuels.add(evalArtVisuel);
        }

      }
      importArtVisuel.setImportEvaluationDtos(evalArtVisuels);


    }
    return importArtVisuel;
  }

  ImportMatieresClasseDto processEmr(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEmr> notesErmList= new ArrayList<>();
    notesErmList = buldDtoErm(importNotes);
    ImportMatieresClasseDto importEmr = new ImportMatieresClasseDto();
    if(!notesErmList.isEmpty()) {

      importEmr.setClasse(importNotes.get(0).getClasse());
      importEmr.setMatiere(notesErmList.get(0).getMatiereEmrId());
      importEmr.setAnnee(importNotes.get(0).getAnnee());
      importEmr.setPeriode(importNotes.get(0).getPeriode());
      importEmr.setDate(importNotes.get(0).getDate());
      importEmr.setType(1L);
      importEmr.setNotesur("20");
      List<ImportEvaluationDto> evalEmrs = new ArrayList<>();
      for (NotesEmr dto : notesErmList) {
        ImportEvaluationDto evalErm = new ImportEvaluationDto();
        evalErm.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteEmr1()))
        {
          notes.add(dto.getNoteEmr1()) ;
        }
        if(isValidNumber(dto.getNoteEmr2()))
        {
          notes.add(dto.getNoteEmr2()) ;
        }
        if(isValidNumber(dto.getNoteEmr3()))
        {
          notes.add(dto.getNoteEmr3()) ;
        }
        if(isValidNumber(dto.getNoteEmr4()))
        {
          notes.add(dto.getNoteEmr4()) ;
        }
        if(isValidNumber(dto.getNoteEmr5()))
        {
          notes.add(dto.getNoteEmr5()) ;
        }
        if(!notes.isEmpty()) {
          evalErm.setNotes(notes);
          evalEmrs.add(evalErm);
        }

      }
      importEmr.setImportEvaluationDtos(evalEmrs);


    }
    return importEmr;
  }

  ImportMatieresClasseDto processAlquidah(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAlAquidah> notesAlAquidahList= new ArrayList<>();
    notesAlAquidahList = buldDtoAlaquidah(importNotes);
    ImportMatieresClasseDto importAlaquidah = new ImportMatieresClasseDto();
    if(!notesAlAquidahList.isEmpty()) {

      importAlaquidah.setClasse(importNotes.get(0).getClasse());
      importAlaquidah.setMatiere(notesAlAquidahList.get(0).getMatiereAlquidahId());
      importAlaquidah.setAnnee(importNotes.get(0).getAnnee());
      importAlaquidah.setPeriode(importNotes.get(0).getPeriode());
      importAlaquidah.setDate(importNotes.get(0).getDate());
      importAlaquidah.setType(1L);
      importAlaquidah.setNotesur("20");
      List<ImportEvaluationDto> evalAlaquidahs = new ArrayList<>();
      for (NotesAlAquidah dto : notesAlAquidahList) {
        ImportEvaluationDto evalAlaquidah = new ImportEvaluationDto();
        evalAlaquidah.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteAlquidah1()))
        {
          notes.add(dto.getNoteAlquidah1()) ;
        }
        if(isValidNumber(dto.getNoteAlquidah2()))
        {
          notes.add(dto.getNoteAlquidah2()) ;
        }
        if(isValidNumber(dto.getNoteAlquidah3()))
        {
          notes.add(dto.getNoteAlquidah3()) ;
        }
        if(isValidNumber(dto.getNoteAlquidah4()))
        {
          notes.add(dto.getNoteAlquidah4()) ;
        }
        if(isValidNumber(dto.getNoteAlquidah5()))
        {
          notes.add(dto.getNoteAlquidah5()) ;
        }
        if(!notes.isEmpty()) {
          evalAlaquidah.setNotes(notes);
          evalAlaquidahs.add(evalAlaquidah);
        }

      }
      importAlaquidah.setImportEvaluationDtos(evalAlaquidahs);


    }
    return importAlaquidah;
  }
  ImportMatieresClasseDto processAlAklaq(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAlAklaq> notesAlAlAklaqList= new ArrayList<>();
    notesAlAlAklaqList = buldDtoAlAklaq(importNotes);
    ImportMatieresClasseDto importAlAlAklaq = new ImportMatieresClasseDto();
    if(!notesAlAlAklaqList.isEmpty()) {

      importAlAlAklaq.setClasse(importNotes.get(0).getClasse());
      importAlAlAklaq.setMatiere(notesAlAlAklaqList.get(0).getMatiereAlAklaqId());
      importAlAlAklaq.setAnnee(importNotes.get(0).getAnnee());
      importAlAlAklaq.setPeriode(importNotes.get(0).getPeriode());
      importAlAlAklaq.setDate(importNotes.get(0).getDate());
      importAlAlAklaq.setType(1L);
      importAlAlAklaq.setNotesur("20");
      List<ImportEvaluationDto> evalAlAlAklaqs = new ArrayList<>();
      for (NotesAlAklaq dto : notesAlAlAklaqList) {
        ImportEvaluationDto evalAlAlAklaq = new ImportEvaluationDto();
        evalAlAlAklaq.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteAlAklaq1()))
        {
          notes.add(dto.getNoteAlAklaq1()) ;
        }
        if(isValidNumber(dto.getNoteAlAklaq2()))
        {
          notes.add(dto.getNoteAlAklaq2()) ;
        }
        if(isValidNumber(dto.getNoteAlAklaq3()))
        {
          notes.add(dto.getNoteAlAklaq3()) ;
        }
        if(isValidNumber(dto.getNoteAlAklaq4()))
        {
          notes.add(dto.getNoteAlAklaq4()) ;
        }
        if(isValidNumber(dto.getNoteAlAklaq5()))
        {
          notes.add(dto.getNoteAlAklaq5()) ;
        }
        if(!notes.isEmpty()) {
          evalAlAlAklaq.setNotes(notes);
          evalAlAlAklaqs.add(evalAlAlAklaq);
        }

      }
      importAlAlAklaq.setImportEvaluationDtos(evalAlAlAklaqs);


    }
    return importAlAlAklaq;
  }

  ImportMatieresClasseDto processFr(List<ImportNotesMatieresEcole> importNotes){
    List<NotesFr> notesFrList= new ArrayList<>();
    notesFrList = buldDtoFr(importNotes);
    ImportMatieresClasseDto importFr = new ImportMatieresClasseDto();
    if(!notesFrList.isEmpty()) {

      importFr.setClasse(importNotes.get(0).getClasse());
      importFr.setMatiere(notesFrList.get(0).getMatiereFranId());
      importFr.setAnnee(importNotes.get(0).getAnnee());
      importFr.setPeriode(importNotes.get(0).getPeriode());
      importFr.setDate(importNotes.get(0).getDate());
      importFr.setType(1L);
      importFr.setNotesur("20");
      List<ImportEvaluationDto> evalFrs = new ArrayList<>();
      for (NotesFr dto : notesFrList) {
        ImportEvaluationDto evalFr = new ImportEvaluationDto();
        evalFr.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteFran1()))
        {
          notes.add(dto.getNoteFran1()) ;
        }
        if(isValidNumber(dto.getNoteFran2()))
        {
          notes.add(dto.getNoteFran2()) ;
        }
        if(isValidNumber(dto.getNoteFran3()))
        {
          notes.add(dto.getNoteFran3()) ;
        }
        if(isValidNumber(dto.getNoteFran4()))
        {
          notes.add(dto.getNoteFran4()) ;
        }
        if(isValidNumber(dto.getNoteFran5()))
        {
          notes.add(dto.getNoteFran5()) ;
        }
        if(!notes.isEmpty()) {
          evalFr.setNotes(notes);
          evalFrs.add(evalFr);
        }

      }
      importFr.setImportEvaluationDtos(evalFrs);


    }
    return importFr;
  }

  ImportMatieresClasseDto processAnglais(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAnglais> notesAnglaisList= new ArrayList<>();
    notesAnglaisList = buldDtoAng(importNotes);
    ImportMatieresClasseDto importAnglais = new ImportMatieresClasseDto();
    if(!notesAnglaisList.isEmpty()) {

      importAnglais.setClasse(importNotes.get(0).getClasse());
      importAnglais.setMatiere(notesAnglaisList.get(0).getMatiereAngId());
      importAnglais.setAnnee(importNotes.get(0).getAnnee());
      importAnglais.setPeriode(importNotes.get(0).getPeriode());
      importAnglais.setDate(importNotes.get(0).getDate());
      importAnglais.setType(1L);
      importAnglais.setNotesur("20");
      List<ImportEvaluationDto> evalAnglais = new ArrayList<>();
      for (NotesAnglais dto : notesAnglaisList) {
        ImportEvaluationDto evalAng = new ImportEvaluationDto();
        evalAng.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteAng1()))
        {
          notes.add(dto.getNoteAng1()) ;
        }
        if(isValidNumber(dto.getNoteAng2()))
        {
          notes.add(dto.getNoteAng2()) ;
        }
        if(isValidNumber(dto.getNoteAng3()))
        {
          notes.add(dto.getNoteAng3()) ;
        }
        if(isValidNumber(dto.getNoteAng4()))
        {
          notes.add(dto.getNoteAng4()) ;
        }
        if(isValidNumber(dto.getNoteAng5()))
        {
          notes.add(dto.getNoteAng5()) ;
        }
        if(!notes.isEmpty()) {
          evalAng.setNotes(notes);
          evalAnglais.add(evalAng);
        }

      }
      importAnglais.setImportEvaluationDtos(evalAnglais);


    }
    return importAnglais;
  }
  ImportMatieresClasseDto processAll(List<ImportNotesMatieresEcole> importNotes){
    List<NotesAll> notesAllList= new ArrayList<>();
    notesAllList = buldDtoAll(importNotes);
    ImportMatieresClasseDto importAll = new ImportMatieresClasseDto();
    if(!notesAllList.isEmpty()) {

      importAll.setClasse(importNotes.get(0).getClasse());
      importAll.setMatiere(notesAllList.get(0).getMatiereAllId());
      importAll.setAnnee(importNotes.get(0).getAnnee());
      importAll.setPeriode(importNotes.get(0).getPeriode());
      importAll.setDate(importNotes.get(0).getDate());
      importAll.setType(1L);
      importAll.setNotesur("20");
      List<ImportEvaluationDto> evalAlllist = new ArrayList<>();
      for (NotesAll dto : notesAllList) {
        ImportEvaluationDto evalAll = new ImportEvaluationDto();
        evalAll.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteAll1()))
        {
          notes.add(dto.getNoteAll1()) ;
        }
        if(isValidNumber(dto.getNoteAll2()))
        {
          notes.add(dto.getNoteAll2()) ;
        }
        if(isValidNumber(dto.getNoteAll3()))
        {
          notes.add(dto.getNoteAll3()) ;
        }
        if(isValidNumber(dto.getNoteAll4()))
        {
          notes.add(dto.getNoteAll4()) ;
        }
        if(isValidNumber(dto.getNoteAll5()))
        {
          notes.add(dto.getNoteAll5()) ;
        }
        if(!notes.isEmpty()) {
          evalAll.setNotes(notes);
          evalAlllist.add(evalAll);
        }

      }
      importAll.setImportEvaluationDtos(evalAlllist);


    }
    return importAll;
  }
  ImportMatieresClasseDto processEps(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEps> notesEpsList= new ArrayList<>();
    notesEpsList = buldDtoEps(importNotes);
    ImportMatieresClasseDto importEps = new ImportMatieresClasseDto();
    if(!notesEpsList.isEmpty()) {

      importEps.setClasse(importNotes.get(0).getClasse());
      importEps.setMatiere(notesEpsList.get(0).getMatiereEpsId());
      importEps.setAnnee(importNotes.get(0).getAnnee());
      importEps.setPeriode(importNotes.get(0).getPeriode());
      importEps.setDate(importNotes.get(0).getDate());
      importEps.setType(1L);
      importEps.setNotesur("20");
      List<ImportEvaluationDto> evalEpslist = new ArrayList<>();
      for (NotesEps dto : notesEpsList) {
        ImportEvaluationDto evalEps = new ImportEvaluationDto();
        evalEps.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteEps1()))
        {
          notes.add(dto.getNoteEps1()) ;
        }
        if(isValidNumber(dto.getNoteEps2()))
        {
          notes.add(dto.getNoteEps2()) ;
        }
        if(isValidNumber(dto.getNoteEps3()))
        {
          notes.add(dto.getNoteEps3()) ;
        }
        if(isValidNumber(dto.getNoteEps4()))
        {
          notes.add(dto.getNoteEps4()) ;
        }
        if(isValidNumber(dto.getNoteEps5()))
        {
          notes.add(dto.getNoteEps5()) ;
        }
        if(!notes.isEmpty()) {
          evalEps.setNotes(notes);
          evalEpslist.add(evalEps);
        }

      }
      importEps.setImportEvaluationDtos(evalEpslist);


    }
    return importEps;
  }
  ImportMatieresClasseDto processArtplas(List<ImportNotesMatieresEcole> importNotes){
    List<NotesArtplas> notesArtplasList= new ArrayList<>();
    notesArtplasList = buldDtoArt(importNotes);
    ImportMatieresClasseDto importArt = new ImportMatieresClasseDto();
    if(!notesArtplasList.isEmpty()) {

      importArt.setClasse(importNotes.get(0).getClasse());
      importArt.setMatiere(notesArtplasList.get(0).getMatiereArplatId());
      importArt.setAnnee(importNotes.get(0).getAnnee());
      importArt.setPeriode(importNotes.get(0).getPeriode());
      importArt.setDate(importNotes.get(0).getDate());
      importArt.setType(1L);
      importArt.setNotesur("20");
      List<ImportEvaluationDto> evalArtlist = new ArrayList<>();
      for (NotesArtplas dto : notesArtplasList) {
        ImportEvaluationDto evalArt = new ImportEvaluationDto();
        evalArt.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteArplat1()))
        {
          notes.add(dto.getNoteArplat1()) ;
        }
        if(isValidNumber(dto.getNoteArplat2()))
        {
          notes.add(dto.getNoteArplat2()) ;
        }
        if(isValidNumber(dto.getNoteArplat3()))
        {
          notes.add(dto.getNoteArplat3()) ;
        }
        if(isValidNumber(dto.getNoteArplat4()))
        {
          notes.add(dto.getNoteArplat4()) ;
        }
        if(isValidNumber(dto.getNoteArplat5()))
        {
          notes.add(dto.getNoteArplat5()) ;
        }
        if(!notes.isEmpty()) {
          evalArt.setNotes(notes);
          evalArtlist.add(evalArt);
        }

      }
      importArt.setImportEvaluationDtos(evalArtlist);


    }
    return importArt;
  }
  ImportMatieresClasseDto processComposFr(List<ImportNotesMatieresEcole> importNotes){
    List<NotesCompFr> notesCompoFrList= new ArrayList<>();
    notesCompoFrList = buldDtoCompoFr(importNotes);
    ImportMatieresClasseDto importCompoFr = new ImportMatieresClasseDto();
    if(!notesCompoFrList.isEmpty()) {

      importCompoFr.setClasse(importNotes.get(0).getClasse());
      importCompoFr.setMatiere(notesCompoFrList.get(0).getMatiereCompoFrId());
      importCompoFr.setAnnee(importNotes.get(0).getAnnee());
      importCompoFr.setPeriode(importNotes.get(0).getPeriode());
      importCompoFr.setDate(importNotes.get(0).getDate());
      importCompoFr.setType(1L);
      importCompoFr.setNotesur("20");
      List<ImportEvaluationDto> evalCompolist = new ArrayList<>();
      for (NotesCompFr dto : notesCompoFrList) {
        ImportEvaluationDto evalCompoFr = new ImportEvaluationDto();
        evalCompoFr.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteCompoFr1()))
        {
          notes.add(dto.getNoteCompoFr1()) ;
        }
        if(isValidNumber(dto.getNoteCompoFr2()))
        {
          notes.add(dto.getNoteCompoFr2()) ;
        }
        if(isValidNumber(dto.getNoteCompoFr3()))
        {
          notes.add(dto.getNoteCompoFr3()) ;
        }
        if(isValidNumber(dto.getNoteCompoFr4()))
        {
          notes.add(dto.getNoteCompoFr4()) ;
        }
        if(isValidNumber(dto.getNoteCompoFr5()))
        {
          notes.add(dto.getNoteCompoFr5()) ;
        }
        if(!notes.isEmpty()) {
          evalCompoFr.setNotes(notes);
          evalCompolist.add(evalCompoFr);
        }

      }
      importCompoFr.setImportEvaluationDtos(evalCompolist);


    }
    return importCompoFr;
  }
  ImportMatieresClasseDto processOrthoGram(List<ImportNotesMatieresEcole> importNotes){
    List<NotesOrthGram> notesOrthGramList= new ArrayList<>();
    notesOrthGramList = buldDtoOrthGram(importNotes);
    ImportMatieresClasseDto importOrthoGram = new ImportMatieresClasseDto();
    if(!notesOrthGramList.isEmpty()) {

      importOrthoGram.setClasse(importNotes.get(0).getClasse());
      importOrthoGram.setMatiere(notesOrthGramList.get(0).getMatiereOrthoGramId());
      importOrthoGram.setAnnee(importNotes.get(0).getAnnee());
      importOrthoGram.setPeriode(importNotes.get(0).getPeriode());
      importOrthoGram.setDate(importNotes.get(0).getDate());
      importOrthoGram.setType(1L);
      importOrthoGram.setNotesur("20");
      List<ImportEvaluationDto> evalOrthlist = new ArrayList<>();
      for (NotesOrthGram dto : notesOrthGramList) {
        ImportEvaluationDto evalOrth = new ImportEvaluationDto();
        evalOrth.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteOrthoGram1()))
        {
          notes.add(dto.getNoteOrthoGram1()) ;
        }
        if(isValidNumber(dto.getNoteOrthoGram2()))
        {
          notes.add(dto.getNoteOrthoGram2()) ;
        }
        if(isValidNumber(dto.getNoteOrthoGram3()))
        {
          notes.add(dto.getNoteOrthoGram3()) ;
        }
        if(isValidNumber(dto.getNoteOrthoGram4()))
        {
          notes.add(dto.getNoteOrthoGram4()) ;
        }
        if(isValidNumber(dto.getNoteOrthoGram5()))
        {
          notes.add(dto.getNoteOrthoGram5()) ;
        }
        if(!notes.isEmpty()) {
          evalOrth.setNotes(notes);
          evalOrthlist.add(evalOrth);
        }

      }
      importOrthoGram.setImportEvaluationDtos(evalOrthlist);


    }
    return importOrthoGram;
  }
  ImportMatieresClasseDto processPhysiques(List<ImportNotesMatieresEcole> importNotes){
    List<NotesPhysiques> notesPhysiqList= new ArrayList<>();
    notesPhysiqList = buldDtoPhysiques(importNotes);
    ImportMatieresClasseDto importPhysiq = new ImportMatieresClasseDto();
    if(!notesPhysiqList.isEmpty()) {
      importPhysiq.setClasse(importNotes.get(0).getClasse());
      importPhysiq.setMatiere(notesPhysiqList.get(0).getMatierePhysiqId());
      importPhysiq.setAnnee(importNotes.get(0).getAnnee());
      importPhysiq.setPeriode(importNotes.get(0).getPeriode());
      importPhysiq.setDate(importNotes.get(0).getDate());
      importPhysiq.setType(1L);
      importPhysiq.setNotesur("20");
      List<ImportEvaluationDto> evalPhysiques = new ArrayList<>();
      for (NotesPhysiques dto : notesPhysiqList) {
        ImportEvaluationDto evalPhys = new ImportEvaluationDto();
        evalPhys.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getMoyenPhysiq1()))
        {
          notes.add(dto.getMoyenPhysiq1()) ;
        }
        if(isValidNumber(dto.getMoyenPhysiq2()))
        {
          notes.add(dto.getMoyenPhysiq2()) ;
        }
        if(isValidNumber(dto.getMoyenPhysiq3()))
        {
          notes.add(dto.getMoyenPhysiq3()) ;
        }
        if(isValidNumber(dto.getMoyenPhysiq4()))
        {
          notes.add(dto.getMoyenPhysiq4()) ;
        }
        if(isValidNumber(dto.getMoyenPhysiq5()))
        {
          notes.add(dto.getMoyenPhysiq5()) ;
        }
        if(!notes.isEmpty()) {
          evalPhys.setNotes(notes);
          evalPhysiques.add(evalPhys);
        }

      }
      importPhysiq.setImportEvaluationDtos(evalPhysiques);

    }
    return importPhysiq;
  }
  ImportMatieresClasseDto processEsp(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEsp> notesEspList= new ArrayList<>();
    notesEspList = buldDtoEsp(importNotes);
    ImportMatieresClasseDto importEsp = new ImportMatieresClasseDto();
    if(!notesEspList.isEmpty()) {
      importEsp.setClasse(importNotes.get(0).getClasse());
      importEsp.setMatiere(notesEspList.get(0).getMatiereEspId());
      importEsp.setAnnee(importNotes.get(0).getAnnee());
      importEsp.setPeriode(importNotes.get(0).getPeriode());
      importEsp.setDate(importNotes.get(0).getDate());
      importEsp.setType(1L);
      importEsp.setNotesur("20");
      List<ImportEvaluationDto> evalEsplist = new ArrayList<>();
      for (NotesEsp dto : notesEspList) {
        ImportEvaluationDto evalEsp = new ImportEvaluationDto();
        evalEsp.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteEsp1()))
        {
          notes.add(dto.getNoteEsp1()) ;
        }
        if(isValidNumber(dto.getNoteEsp2()))
        {
          notes.add(dto.getNoteEsp2()) ;
        }
        if(isValidNumber(dto.getNoteEsp3()))
        {
          notes.add(dto.getNoteEsp3()) ;
        }
        if(isValidNumber(dto.getNoteEsp4()))
        {
          notes.add(dto.getNoteEsp4()) ;
        }
        if(isValidNumber(dto.getNoteEsp5()))
        {
          notes.add(dto.getNoteEsp5()) ;
        }
        if(!notes.isEmpty()) {
          evalEsp.setNotes(notes);
          evalEsplist.add(evalEsp);
        }

      }
      importEsp.setImportEvaluationDtos(evalEsplist);

    }
    return importEsp;
  }
  ImportMatieresClasseDto processPhiloso(List<ImportNotesMatieresEcole> importNotes){
    List<NotesPhiloso> notesPhilosoList= new ArrayList<>();
    notesPhilosoList = buldDtoPhilo(importNotes);
    ImportMatieresClasseDto importPhiloso = new ImportMatieresClasseDto();
    if(!notesPhilosoList.isEmpty()) {
      importPhiloso.setClasse(importNotes.get(0).getClasse());
      importPhiloso.setMatiere(notesPhilosoList.get(0).getMatierephilosoId());
      importPhiloso.setAnnee(importNotes.get(0).getAnnee());
      importPhiloso.setPeriode(importNotes.get(0).getPeriode());
      importPhiloso.setDate(importNotes.get(0).getDate());
      importPhiloso.setType(1L);
      importPhiloso.setNotesur("20");
      List<ImportEvaluationDto> evalPhiolosolist = new ArrayList<>();
      for (NotesPhiloso dto : notesPhilosoList) {
        ImportEvaluationDto evalPhioloso = new ImportEvaluationDto();
        evalPhioloso.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNotephiloso1()))
        {
          notes.add(dto.getNotephiloso1()) ;
        }
        if(isValidNumber(dto.getNotephiloso2()))
        {
          notes.add(dto.getNotephiloso2()) ;
        }
        if(isValidNumber(dto.getNotephiloso3()))
        {
          notes.add(dto.getNotephiloso3()) ;
        }
        if(isValidNumber(dto.getNotephiloso4()))
        {
          notes.add(dto.getNotephiloso4()) ;
        }
        if(isValidNumber(dto.getNotephiloso5()))
        {
          notes.add(dto.getNotephiloso5()) ;
        }
        if(!notes.isEmpty()) {
          evalPhioloso.setNotes(notes);
          evalPhiolosolist.add(evalPhioloso);
        }

      }
      importPhiloso.setImportEvaluationDtos(evalPhiolosolist);

    }
    return importPhiloso;
  }
  ImportMatieresClasseDto processTic(List<ImportNotesMatieresEcole> importNotes){
    List<NotesTic> notesTicList= new ArrayList<>();
    notesTicList = buldDtoTic(importNotes);
    ImportMatieresClasseDto importTic = new ImportMatieresClasseDto();
    if(!notesTicList.isEmpty()) {
      importTic.setClasse(importNotes.get(0).getClasse());
      importTic.setMatiere(notesTicList.get(0).getMatiereTicId());
      importTic.setAnnee(importNotes.get(0).getAnnee());
      importTic.setPeriode(importNotes.get(0).getPeriode());
      importTic.setDate(importNotes.get(0).getDate());
      importTic.setType(1L);
      importTic.setNotesur("20");
      List<ImportEvaluationDto> evalTiclist = new ArrayList<>();
      for (NotesTic dto : notesTicList) {
        ImportEvaluationDto evalTic = new ImportEvaluationDto();
        evalTic.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteTic1()))
        {
          notes.add(dto.getNoteTic1()) ;
        }
        if(isValidNumber(dto.getNoteTic2()))
        {
          notes.add(dto.getNoteTic2()) ;
        }
        if(isValidNumber(dto.getNoteTic3()))
        {
          notes.add(dto.getNoteTic3()) ;
        }
        if(isValidNumber(dto.getNoteTic4()))
        {
          notes.add(dto.getNoteTic4()) ;
        }
        if(isValidNumber(dto.getNoteTic5()))
        {
          notes.add(dto.getNoteTic5()) ;
        }
        if(!notes.isEmpty()) {
          evalTic.setNotes(notes);
          evalTiclist.add(evalTic);
        }

      }
      importTic.setImportEvaluationDtos(evalTiclist);

    }
    return importTic;
  }

  ImportMatieresClasseDto processInfor(List<ImportNotesMatieresEcole> importNotes){
    List<NotesInfor> notesInforList= new ArrayList<>();
    notesInforList = buldDtoInfor(importNotes);
    ImportMatieresClasseDto importInfor = new ImportMatieresClasseDto();
    if(!notesInforList.isEmpty()) {
      importInfor.setClasse(importNotes.get(0).getClasse());
      importInfor.setMatiere(notesInforList.get(0).getMatiereInforId());
      importInfor.setAnnee(importNotes.get(0).getAnnee());
      importInfor.setPeriode(importNotes.get(0).getPeriode());
      importInfor.setDate(importNotes.get(0).getDate());
      importInfor.setType(1L);
      importInfor.setNotesur("20");
      List<ImportEvaluationDto> evalInforlist = new ArrayList<>();
      for (NotesInfor dto : notesInforList) {
        ImportEvaluationDto evalInfo = new ImportEvaluationDto();
        evalInfo.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteInfor1()))
        {
          notes.add(dto.getNoteInfor1()) ;
        }
        if(isValidNumber(dto.getNoteInfor2()))
        {
          notes.add(dto.getNoteInfor2()) ;
        }
        if(isValidNumber(dto.getNoteInfor3()))
        {
          notes.add(dto.getNoteInfor3()) ;
        }
        if(isValidNumber(dto.getNoteInfor4()))
        {
          notes.add(dto.getNoteInfor4()) ;
        }
        if(isValidNumber(dto.getNoteInfor5()))
        {
          notes.add(dto.getNoteInfor5()) ;
        }
        if(!notes.isEmpty()) {
          evalInfo.setNotes(notes);
          evalInforlist.add(evalInfo);
        }

      }
      importInfor.setImportEvaluationDtos(evalInforlist);

    }
    return importInfor;
  }
  ImportMatieresClasseDto processSvt(List<ImportNotesMatieresEcole> importNotes){
    List<NotesSvt> notesSvtList= new ArrayList<>();
    notesSvtList = buldDtoSvt(importNotes);
    ImportMatieresClasseDto importSvt = new ImportMatieresClasseDto();
    if(!notesSvtList.isEmpty()) {
      importSvt.setClasse(importNotes.get(0).getClasse());
      importSvt.setMatiere(notesSvtList.get(0).getMatiereSVTId());
      importSvt.setAnnee(importNotes.get(0).getAnnee());
      importSvt.setPeriode(importNotes.get(0).getPeriode());
      importSvt.setDate(importNotes.get(0).getDate());
      importSvt.setType(1L);
      importSvt.setNotesur("20");
      List<ImportEvaluationDto> evalSvtlist = new ArrayList<>();
      for (NotesSvt dto : notesSvtList) {
        ImportEvaluationDto evalSvt = new ImportEvaluationDto();
        evalSvt.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteSVT1()))
        {
          notes.add(dto.getNoteSVT1()) ;
        }
        if(isValidNumber(dto.getNoteSVT2()))
        {
          notes.add(dto.getNoteSVT2()) ;
        }
        if(isValidNumber(dto.getNoteSVT3()))
        {
          notes.add(dto.getNoteSVT3()) ;
        }
        if(isValidNumber(dto.getNoteSVT4()))
        {
          notes.add(dto.getNoteSVT4()) ;
        }
        if(isValidNumber(dto.getNoteSVT5()))
        {
          notes.add(dto.getNoteSVT5()) ;
        }
        if(!notes.isEmpty()) {
          evalSvt.setNotes(notes);
          evalSvtlist.add(evalSvt);
        }

      }
      importSvt.setImportEvaluationDtos(evalSvtlist);

    }
    return importSvt;
  }

  ImportMatieresClasseDto processConduite(List<ImportNotesMatieresEcole> importNotes){
    List<NotesConduite> notesConduiteList= new ArrayList<>();
    notesConduiteList = buldDtoConduite(importNotes);
    ImportMatieresClasseDto importConduite = new ImportMatieresClasseDto();
    if(!notesConduiteList.isEmpty()) {
      importConduite.setClasse(importNotes.get(0).getClasse());
      importConduite.setMatiere(notesConduiteList.get(0).getMatiereConduiteId());
      importConduite.setAnnee(importNotes.get(0).getAnnee());
      importConduite.setPeriode(importNotes.get(0).getPeriode());
      importConduite.setDate(importNotes.get(0).getDate());
      importConduite.setType(1L);
      importConduite.setNotesur("20");
      List<ImportEvaluationDto> evalConduitelist = new ArrayList<>();
      for (NotesConduite dto : notesConduiteList) {
        ImportEvaluationDto evalConduite = new ImportEvaluationDto();
        evalConduite.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteConduite1()))
        {
          notes.add(dto.getNoteConduite1()) ;
        }
        if(isValidNumber(dto.getNoteConduite2()))
        {
          notes.add(dto.getNoteConduite2()) ;
        }
        if(isValidNumber(dto.getNoteConduite3()))
        {
          notes.add(dto.getNoteConduite3()) ;
        }
        if(isValidNumber(dto.getNoteConduite4()))
        {
          notes.add(dto.getNoteConduite4()) ;
        }
        if(isValidNumber(dto.getNoteConduite5()))
        {
          notes.add(dto.getNoteConduite5()) ;
        }
        if(!notes.isEmpty()) {
          evalConduite.setNotes(notes);
          evalConduitelist.add(evalConduite);
        }

      }
      importConduite.setImportEvaluationDtos(evalConduitelist);

    }
    return importConduite;
  }

  ImportMatieresClasseDto processExpresOral(List<ImportNotesMatieresEcole> importNotes){
    List<NotesExpresOral> notesExoresOralList= new ArrayList<>();
    notesExoresOralList = buldDtoExpressOral(importNotes);
    ImportMatieresClasseDto importExpresOral = new ImportMatieresClasseDto();
    if(!notesExoresOralList.isEmpty()) {
      importExpresOral.setClasse(importNotes.get(0).getClasse());
      importExpresOral.setMatiere(notesExoresOralList.get(0).getMatiereExpreOralId());
      importExpresOral.setAnnee(importNotes.get(0).getAnnee());
      importExpresOral.setPeriode(importNotes.get(0).getPeriode());
      importExpresOral.setDate(importNotes.get(0).getDate());
      importExpresOral.setType(1L);
      importExpresOral.setNotesur("20");
      List<ImportEvaluationDto> evalExpresOrallist = new ArrayList<>();
      for (NotesExpresOral dto : notesExoresOralList) {
        ImportEvaluationDto evalExpresOral = new ImportEvaluationDto();
        evalExpresOral.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteExpreOral1()))
        {
          notes.add(dto.getNoteExpreOral1()) ;
        }
        if(isValidNumber(dto.getNoteExpreOral2()))
        {
          notes.add(dto.getNoteExpreOral2()) ;
        }
        if(isValidNumber(dto.getNoteExpreOral3()))
        {
          notes.add(dto.getNoteExpreOral3()) ;
        }
        if(isValidNumber(dto.getNoteExpreOral4()))
        {
          notes.add(dto.getNoteExpreOral4()) ;
        }
        if(isValidNumber(dto.getNoteExpreOral5()))
        {
          notes.add(dto.getNoteExpreOral5()) ;
        }
        if(!notes.isEmpty()) {
          evalExpresOral.setNotes(notes);
          evalExpresOrallist.add(evalExpresOral);
        }

      }
      importExpresOral.setImportEvaluationDtos(evalExpresOrallist);

    }
    return importExpresOral;
  }

  ImportMatieresClasseDto processHg(List<ImportNotesMatieresEcole> importNotes){
    List<NotesHg> notesHgList= new ArrayList<>();
    notesHgList = buldDtoHg(importNotes);
    ImportMatieresClasseDto importHg = new ImportMatieresClasseDto();
    if(!notesHgList.isEmpty()) {
      importHg.setClasse(importNotes.get(0).getClasse());
      importHg.setMatiere(notesHgList.get(0).getMatiereHgId());
      importHg.setAnnee(importNotes.get(0).getAnnee());
      importHg.setPeriode(importNotes.get(0).getPeriode());
      importHg.setDate(importNotes.get(0).getDate());
      importHg.setType(1L);
      importHg.setNotesur("20");
      List<ImportEvaluationDto> evalHglist = new ArrayList<>();
      for (NotesHg dto : notesHgList) {
        ImportEvaluationDto evalhg = new ImportEvaluationDto();
        evalhg.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteHg1()))
        {
          notes.add(dto.getNoteHg1()) ;
        }
        if(isValidNumber(dto.getNoteHg2()))
        {
          notes.add(dto.getNoteHg2()) ;
        }
        if(isValidNumber(dto.getNoteHg3()))
        {
          notes.add(dto.getNoteHg3()) ;
        }
        if(isValidNumber(dto.getNoteHg4()))
        {
          notes.add(dto.getNoteHg4()) ;
        }
        if(isValidNumber(dto.getNoteHg5()))
        {
          notes.add(dto.getNoteHg5()) ;
        }
        if(!notes.isEmpty()) {
          evalhg.setNotes(notes);
          evalHglist.add(evalhg);
        }

      }
      importHg.setImportEvaluationDtos(evalHglist);

    }
    return importHg;
  }
  ImportMatieresClasseDto processEdhc(List<ImportNotesMatieresEcole> importNotes){
    List<NotesEdhc> notesEdhcList= new ArrayList<>();
    notesEdhcList = buldDtoEdhc(importNotes);
    ImportMatieresClasseDto importEdhc = new ImportMatieresClasseDto();
    if(!notesEdhcList.isEmpty()) {
      importEdhc.setClasse(importNotes.get(0).getClasse());
      importEdhc.setMatiere(notesEdhcList.get(0).getMatiereEdhcId());
      importEdhc.setAnnee(importNotes.get(0).getAnnee());
      importEdhc.setPeriode(importNotes.get(0).getPeriode());
      importEdhc.setDate(importNotes.get(0).getDate());
      importEdhc.setType(1L);
      importEdhc.setNotesur("20");
      List<ImportEvaluationDto> evalEdhclist = new ArrayList<>();
      for (NotesEdhc dto : notesEdhcList) {
        ImportEvaluationDto evalEdhc = new ImportEvaluationDto();
        evalEdhc.setMatricule(dto.getMatricule());
        List<String> notes=new ArrayList<>();
        if(isValidNumber(dto.getNoteEdhc1()))
        {
          notes.add(dto.getNoteEdhc1()) ;
        }
        if(isValidNumber(dto.getNoteEdhc2()))
        {
          notes.add(dto.getNoteEdhc2()) ;
        }
        if(isValidNumber(dto.getNoteEdhc3()))
        {
          notes.add(dto.getNoteEdhc3()) ;
        }
        if(isValidNumber(dto.getNoteEdhc4()))
        {
          notes.add(dto.getNoteEdhc4()) ;
        }
        if(isValidNumber(dto.getNoteEdhc5()))
        {
          notes.add(dto.getNoteEdhc5()) ;
        }
        if(!notes.isEmpty()) {
          evalEdhc.setNotes(notes);
          evalEdhclist.add(evalEdhc);
        }

      }
      importEdhc.setImportEvaluationDtos(evalEdhclist);

    }
    return importEdhc;
  }
}


