package com.vieecoles.processors.dren4.Services;

import com.vieecoles.dto.NiveauClasseIdDto;
import com.vieecoles.dto.StatistiquesNiveauSexeDto;
import com.vieecoles.services.etats.BulletinAnneeLookupService;
import com.vieecoles.services.etats.BulletinEffectifQueryService;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class StatistiquesNiveauSexeService {

  @Inject
  BulletinEffectifQueryService bulletinEffectifQueryService;
  @Inject
  BulletinAnneeLookupService bulletinAnneeLookupService;

  @Transactional
  public List<StatistiquesNiveauSexeDto> getStatistiqueDfa(Long idEcole, String libelleAnnee, String libelleTrimestre) {

    Long admisGarcon = 0L;
    Long admisFille = 0L;
    Long totalAdmis = 0L;
    Long redoublantGarcon = 0L;
    Long redoublantFille = 0L;
    Long totalRedoublants = 0L;
    Long exclusGarcon = 0L;
    Long exclusFille = 0L;
    Long totalExclus = 0L;
    Long nonClasseGarcon = 0L;
    Long nonClasseFille = 0L;
    Long totalNonClasse = 0L;
    Long totalGarcon = 0L;
    Long totalFille = 0L;
    Long totalEleves = 0L;
    Long nombreClasse=0L;


    Long idAnnee = getIdAnnee(idEcole, libelleAnnee, libelleTrimestre);
    List<NiveauClasseIdDto> niveauDtoList = new ArrayList<>(getListClasse(idEcole, idAnnee));

    int longTableau =niveauDtoList.size();
    List<StatistiquesNiveauSexeDto> statistiquesNiveauSexeDtosList = new ArrayList<>() ;
    for (int i=0; i< longTableau;i++) {
      StatistiquesNiveauSexeDto statistiquesNiveauSexeDtos = new StatistiquesNiveauSexeDto();
      Integer ordre=niveauDtoList.get(i).getId();
      try {
        nombreClasse = findNombreClasseBranche(ordre,idEcole ,libelleAnnee) ;
        if (nombreClasse==null)
          nombreClasse=0L;
      } catch (RuntimeException re) {
        if (nombreClasse==null)
          nombreClasse=0L;
        re.printStackTrace();
      }



      try {
        admisGarcon = compterAdmisGarconsNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);


        admisFille = compterAdmisFillesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);


        totalAdmis = admisGarcon + admisFille;

        redoublantGarcon = compterRedoublantGarconsNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);
        redoublantFille = compterRedoublantFillesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);
        totalRedoublants = redoublantGarcon + redoublantFille;

        exclusGarcon = compterExclusGarconsNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);
        exclusFille = compterExclusFillesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);
        totalExclus = exclusGarcon + exclusFille;

        // NOUVEAU: Calcul des non classés
        nonClasseGarcon = compterNonClasseGarconsNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);
        nonClasseFille = compterNonClasseFillesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordre);
        totalNonClasse = nonClasseGarcon + nonClasseFille;



        totalGarcon = admisGarcon + redoublantGarcon + exclusGarcon + nonClasseGarcon;
        totalFille = admisFille + redoublantFille + exclusFille + nonClasseFille;
        totalEleves = totalGarcon + totalFille;

      } catch (Exception e) {
        System.err.println("Erreur lors du calcul des statistiques: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Erreur lors du calcul des statistiques", e);
      }

      // Remplissage du DTO
      statistiquesNiveauSexeDtos.setOrdreNiveau(ordre);
      statistiquesNiveauSexeDtos.setLibelleNiveau(niveauDtoList.get(i).getNiveau());
      statistiquesNiveauSexeDtos.setAdmisFille(admisFille);
      statistiquesNiveauSexeDtos.setAdmisGarcon(admisGarcon);
      statistiquesNiveauSexeDtos.setTotalAdmis(totalAdmis);
      statistiquesNiveauSexeDtos.setExclusGarcon(exclusGarcon);
      statistiquesNiveauSexeDtos.setExclusFille(exclusFille);
      statistiquesNiveauSexeDtos.setTotalExclus(totalExclus);
      statistiquesNiveauSexeDtos.setRedoublantGarcon(redoublantGarcon);
      statistiquesNiveauSexeDtos.setRedoublantFille(redoublantFille);
      statistiquesNiveauSexeDtos.setTotalRedoublants(totalRedoublants);

      // NOUVEAU: Ajout des non classés
      statistiquesNiveauSexeDtos.setNonClasseGarcon(nonClasseGarcon);
      statistiquesNiveauSexeDtos.setNonClasseFille(nonClasseFille);
      statistiquesNiveauSexeDtos.setTotalNonClasse(totalNonClasse);
      //Nobre classe :
      statistiquesNiveauSexeDtos.setNombreClasses(nombreClasse);


      statistiquesNiveauSexeDtos.setTotalGarcon(totalGarcon);
      statistiquesNiveauSexeDtos.setTotalFille(totalFille);
      statistiquesNiveauSexeDtos.setTotalEleves(totalEleves);

      statistiquesNiveauSexeDtosList.add(statistiquesNiveauSexeDtos);
    }






    return statistiquesNiveauSexeDtosList;
  }

  @Transactional
  public List<StatistiquesNiveauSexeDto> obtenirStatistiquesParNiveauEtSexe(Long idEcole, String libelleTrimestre, String libelleAnnee) {

    try {
      List<Object[]> results = bulletinEffectifQueryService.fetchDfaAggregationRows(idEcole, libelleTrimestre, libelleAnnee);

      List<StatistiquesNiveauSexeDto> statistiques = new ArrayList<>();

      for (Object[] row : results) {
        Integer ordreNiveau = (Integer) row[0];
        String libelleNiveau = (String) row[1];
        Long admisGarcon = (Long) row[2];
        Long admisFille = (Long) row[3];
        Long redoublantGarcon = (Long) row[4];
        Long redoublantFille = (Long) row[5];
        Long exclusGarcon = (Long) row[6];
        Long exclusFille = (Long) row[7];
        Long nonClasseGarcon = (Long) row[8];
        Long nonClasseFille = (Long) row[9];

        StatistiquesNiveauSexeDto stats = new StatistiquesNiveauSexeDto(
            ordreNiveau, libelleNiveau,
            admisGarcon, admisFille,
            redoublantGarcon, redoublantFille,
            exclusGarcon, exclusFille,
            nonClasseGarcon, nonClasseFille
        );



        statistiques.add(stats);
      }

      return statistiques;

    } catch (Exception e) {
      System.err.println("Erreur lors de l'exécution de la requête: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Erreur lors de l'exécution de la requête", e);
    }
  }

  // Méthodes spécifiques pour compter les non classés
  public Long compterNonClasseGarconsNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    return bulletinEffectifQueryService.countNonClasseGarconsByOrdre(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
  }

  public Long compterNonClasseFillesNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    return bulletinEffectifQueryService.countNonClasseFillesByOrdre(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
  }

  // Fonction basée sur votre exemple
  public Long getNonClasseF(Long idEcole, String classe, String niveau, String annee, String periode) {
    return bulletinEffectifQueryService.countNonClasseByClasseNiveau(idEcole, classe, niveau, annee, periode);
  }

  // Méthodes rapides pour des statistiques spécifiques (mises à jour)
  public Long compterAdmisFillesNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getAdmisFille() : 0L;
  }

  public Long compterAdmisGarconsNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);

    return stats != null ? stats.getAdmisGarcon() : 0L;
  }

  public Long compterRedoublantFillesNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getRedoublantFille() : 0L;
  }

  public Long compterRedoublantGarconsNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getRedoublantGarcon() : 0L;
  }

  public Long compterExclusFillesNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getExclusFille() : 0L;
  }

  public Long compterExclusGarconsNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getExclusGarcon() : 0L;
  }

  // NOUVELLES méthodes pour les non classés
  public Long compterNonClasseFillesNiveauSimple(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getNonClasseFille() : 0L;
  }

  public Long compterNonClasseGarconsNiveauSimple(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    StatistiquesNiveauSexeDto stats = obtenirStatistiquesNiveau(idEcole, libelleTrimestre, libelleAnnee, ordreNiveau);
    return stats != null ? stats.getNonClasseGarcon() : 0L;
  }

  /**
   * Obtient les statistiques pour un niveau spécifique
   */
  public StatistiquesNiveauSexeDto obtenirStatistiquesNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {

    List<StatistiquesNiveauSexeDto> stats = obtenirStatistiquesParNiveauEtSexe(idEcole, libelleTrimestre, libelleAnnee);

    return stats.stream()
        .filter(s -> s.getOrdreNiveau() != null && s.getOrdreNiveau().equals(ordreNiveau))
        .findFirst()
        .orElse(new StatistiquesNiveauSexeDto(ordreNiveau, "Niveau " + ordreNiveau));
  }
  @Transactional
  List<NiveauClasseIdDto> getListClasse(Long idEcole , Long idAnneId) {
    if (idAnneId == null) {
      return new ArrayList<>();
    }
    return bulletinEffectifQueryService.listDistinctNiveauOrdreByEcoleAnneeId(idEcole, idAnneId);
  }
  @Transactional
  public  Long getIdAnnee(Long idEcole ,String libelleAnnee,String periode){
    return bulletinAnneeLookupService.findAnneeId(idEcole, libelleAnnee, periode);
  }

  /**
   * Méthode de debug pour vérifier les données (mise à jour avec non classés)
   */
  @Transactional
  public void debugBulletinData(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    List<Object[]> results = bulletinEffectifQueryService.debugBulletinRowsForNiveau(idEcole, libelleTrimestre,
        libelleAnnee, ordreNiveau);

    System.out.println("=== DEBUG - Données niveau " + ordreNiveau + " ===");
    for (Object[] row : results) {
      String matricule = (String) row[0];
      String nom = (String) row[1];
      String prenoms = (String) row[2];
      String sexe = (String) row[3];
      Double moyAn = (Double) row[4];
      String redoublant = (String) row[5];
      Integer niveau = (Integer) row[6];
      String isClassed = (String) row[7];

      System.out.println(String.format("%s %s %s - Sexe: %s, Moy: %.2f, Redoublant: %s, Classé: %s",
          matricule, nom, prenoms, sexe, moyAn != null ? moyAn : 0.0, redoublant, isClassed));
    }
    System.out.println("=== FIN DEBUG ===");
  }
  @Transactional
  public Long findNombreClasseBranche(Integer id, Long ecoleId, String anneeLibelle) {
    return bulletinEffectifQueryService.countDistinctClassesByOrdreEcoleAnnee(id, ecoleId, anneeLibelle);
  }


}
