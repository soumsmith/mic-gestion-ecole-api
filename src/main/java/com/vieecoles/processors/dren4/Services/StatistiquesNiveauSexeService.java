package com.vieecoles.processors.dren4.Services;

import com.vieecoles.dto.NiveauClasseIdDto;
import com.vieecoles.dto.StatistiquesNiveauSexeDto;
import com.vieecoles.steph.entities.Classe;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
public class StatistiquesNiveauSexeService {

  @Inject
  EntityManager em;

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


    System.out.println("Identifiant idEcole "+idEcole);
    System.out.println("Identifiant libelleAnnee "+libelleAnnee);
    System.out.println("Identifiant libelleTrimestre "+libelleTrimestre);
    Long idAnnee=getIdAnnee(idEcole,libelleAnnee,libelleTrimestre);
    System.out.println("Identifiant idAnnee "+idAnnee);
    List<NiveauClasseIdDto> niveauDtoList = new ArrayList<>() ;
    System.out.println("Debut Longueur Tableau "+niveauDtoList.size());
    niveauDtoList= getListClasse(idEcole,idAnnee);
System.out.println("Longueur Tableau "+niveauDtoList.size());

    int longTableau =niveauDtoList.size();
    List<StatistiquesNiveauSexeDto> statistiquesNiveauSexeDtosList = new ArrayList<>() ;
    for (int i=0; i< longTableau;i++) {
      StatistiquesNiveauSexeDto statistiquesNiveauSexeDtos = new StatistiquesNiveauSexeDto();
      int ordre=niveauDtoList.get(i).getId();
      System.out.println("ordre: " + ordre);
      System.out.println("idEcole: " + idEcole);
      try {
        nombreClasse = findNombreClasseBranche(ordre,idEcole) ;
        if (nombreClasse==null)
          nombreClasse=0L;
      } catch (RuntimeException re) {
        if (nombreClasse==null)
          nombreClasse=0L;
        re.printStackTrace();
      }



      System.out.println("nombreClasse: " + nombreClasse);

      try {
        // Vérification que l'EntityManager est bien injecté
        if (em == null) {
          throw new RuntimeException("EntityManager is null - injection failed");
        }

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
      statistiquesNiveauSexeDtos.setLibelleNiveau(String.valueOf(ordre));
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

    // Vérification que l'EntityManager est bien injecté
    if (em == null) {
      throw new RuntimeException("EntityManager is null - injection failed");
    }

    // Requête mise à jour avec les non classés
    String jpql = "SELECT " +
        "b.ordreNiveau, " +
        "b.niveau, " +

        // Admis Garçon
        "SUM(CASE " +
        "WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') " +
        "AND b.isClassed = 'O' " +
        "AND ( " +
        // Cas par défaut : Admis en classe supérieure
        "NOT ( " +
        "(b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        "OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        "OR (b.ordreNiveau = 4) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant = 'OUI') " +
        "OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn >= 8.5) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn >= 8.5) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn < 8.5) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn < 8.5) " +
        ") " +
        ") " +
        "THEN 1 ELSE 0 END) as admisGarcon, " +

        // Admis Fille
        "SUM(CASE " +
        "WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') " +
        "AND b.isClassed = 'O' " +
        "AND ( " +
        "NOT ( " +
        "(b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        "OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        "OR (b.ordreNiveau = 4) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant = 'OUI') " +
        "OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn >= 8.5) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn >= 8.5) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn < 8.5) " +
        "OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn < 8.5) " +
        ") " +
        ") " +
        "THEN 1 ELSE 0 END) as admisFille, " +

        // Redoublant Garçon
        "SUM(CASE " +
        "WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') " +
        "AND b.isClassed = 'O' " +
        "AND ( " +
        "(b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        ") " +
        "THEN 1 ELSE 0 END) as redoublantGarcon, " +

        // Redoublant Fille
        "SUM(CASE " +
        "WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') " +
        "AND b.isClassed = 'O' " +
        "AND ( " +
        "(b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        ") " +
        "THEN 1 ELSE 0 END) as redoublantFille, " +

        // Exclus Garçon
        "SUM(CASE " +
        "WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') " +
        "AND b.isClassed = 'O' " +
        "AND ( " +
        "(b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        ") " +
        "THEN 1 ELSE 0 END) as exclusGarcon, " +

        // Exclus Fille
        "SUM(CASE " +
        "WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') " +
        "AND b.isClassed = 'O' " +
        "AND ( " +
        "(b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') " +
        "OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) " +
        ") " +
        "THEN 1 ELSE 0 END) as exclusFille, " +

        // NOUVEAU: Non classé Garçon
        "SUM(CASE " +
        "WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') " +
        "AND (b.isClassed = 'N' OR b.isClassed IS NULL) " +
        "THEN 1 ELSE 0 END) as nonClasseGarcon, " +

        // NOUVEAU: Non classé Fille
        "SUM(CASE " +
        "WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') " +
        "AND (b.isClassed = 'N' OR b.isClassed IS NULL) " +
        "THEN 1 ELSE 0 END) as nonClasseFille " +

        "FROM Bulletin b " +
        "WHERE b.ecoleId = :idEcole " +
        "AND b.libellePeriode = :periode " +
        "AND b.anneeLibelle = :annee " +
        "GROUP BY b.ordreNiveau, b.niveau " +
        "ORDER BY b.ordreNiveau";

    try {
      TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
      List<Object[]> results = query.setParameter("idEcole", idEcole)
          .setParameter("periode", libelleTrimestre)
          .setParameter("annee", libelleAnnee)
          .getResultList();

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
    try {
      String jpql = "SELECT COUNT(b.id) FROM Bulletin b " +
          "WHERE b.ecoleId = :idEcole " +
          "AND b.libellePeriode = :periode " +
          "AND b.anneeLibelle = :annee " +
          "AND b.ordreNiveau = :ordreNiveau " +
          "AND (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') " +
          "AND (b.isClassed = 'N' OR b.isClassed IS NULL)";

      Long count = (Long) em.createQuery(jpql)
          .setParameter("idEcole", idEcole)
          .setParameter("periode", libelleTrimestre)
          .setParameter("annee", libelleAnnee)
          .setParameter("ordreNiveau", ordreNiveau)
          .getSingleResult();

      return count != null ? count : 0L;
    } catch (NoResultException e) {
      return 0L;
    }
  }

  public Long compterNonClasseFillesNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    try {
      String jpql = "SELECT COUNT(b.id) FROM Bulletin b " +
          "WHERE b.ecoleId = :idEcole " +
          "AND b.libellePeriode = :periode " +
          "AND b.anneeLibelle = :annee " +
          "AND b.ordreNiveau = :ordreNiveau " +
          "AND (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') " +
          "AND (b.isClassed = 'N' OR b.isClassed IS NULL)";

      Long count = (Long) em.createQuery(jpql)
          .setParameter("idEcole", idEcole)
          .setParameter("periode", libelleTrimestre)
          .setParameter("annee", libelleAnnee)
          .setParameter("ordreNiveau", ordreNiveau)
          .getSingleResult();

      return count != null ? count : 0L;
    } catch (NoResultException e) {
      return 0L;
    }
  }

  // Fonction basée sur votre exemple
  public Long getNonClasseF(Long idEcole, String classe, String niveau, String annee, String periode) {
    try {
      Long nonclassF = (Long) em.createQuery("SELECT COUNT(o.id) FROM Bulletin o " +
              "WHERE o.ecoleId = :idEcole " +
              "AND o.isClassed = :isClass " +
              "AND o.libellePeriode = :periode " +
              "AND o.anneeLibelle = :annee " +
              "AND o.libelleClasse = :classe " +
              "AND o.niveau = :niveau")
          .setParameter("idEcole", idEcole)
          .setParameter("isClass", "N")
          .setParameter("classe", classe)
          .setParameter("niveau", niveau)
          .setParameter("annee", annee)
          .setParameter("periode", periode)
          .getSingleResult();
      return nonclassF;
    } catch (NoResultException e) {
      return 0L;
    }
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
    List<NiveauClasseIdDto> classeNiveauDtoList = new ArrayList<>() ;
    TypedQuery<NiveauClasseIdDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseIdDto(b.niveau,b.ordreNiveau) FROM Bulletin b where  b.ecoleId =:idEcole and b.anneeId=:annee  order by b.ordreNiveau "
        , NiveauClasseIdDto.class);
    return      classeNiveauDtoList = q.setParameter("idEcole" ,idEcole)
        . setParameter("annee" ,idAnneId)
        .getResultList() ;

  }
  @Transactional
  public  Long getIdAnnee(Long idEcole ,String libelleAnnee,String periode){
    try {
      Long   anneeID = (Long) em.createQuery("select distinct b.anneeId  from Bulletin b  where   b.anneeLibelle=:libelleAnnee " +
              " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
          .setParameter("periode",periode)
          .setParameter("libelleAnnee", libelleAnnee)
          .setParameter("idEcole", idEcole)
          .getSingleResult();
      return  anneeID ;
    } catch (NoResultException e){
      return null ;
    }

  }

  /**
   * Méthode de debug pour vérifier les données (mise à jour avec non classés)
   */
  @Transactional
  public void debugBulletinData(Long idEcole, String libelleTrimestre, String libelleAnnee, Integer ordreNiveau) {
    String jpql = "SELECT b.matricule, b.nom, b.prenoms, b.sexe, b.moyAn, b.redoublant, b.ordreNiveau, b.isClassed " +
        "FROM Bulletin b " +
        "WHERE b.ecoleId = :idEcole " +
        "AND b.libellePeriode = :periode " +
        "AND b.anneeLibelle = :annee " +
        "AND b.ordreNiveau = :ordreNiveau " +
        "ORDER BY b.nom, b.prenoms";

    TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
    List<Object[]> results = query.setParameter("idEcole", idEcole)
        .setParameter("periode", libelleTrimestre)
        .setParameter("annee", libelleAnnee)
        .setParameter("ordreNiveau", ordreNiveau)
        .getResultList();

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
  public Long findNombreClasseBranche(long id, Long ecoleId) {
    String jpql = "SELECT COUNT(c.id) FROM Classe c WHERE c.branche.id = :brancheId AND c.ecole.id = :ecoleId";

    TypedQuery<Long> query = em.createQuery(jpql, Long.class);
    query.setParameter("brancheId", id);
    query.setParameter("ecoleId", ecoleId);

    return query.getSingleResult();
  }


}
