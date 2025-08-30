package com.vieecoles.dto;

public class StatistiquesNiveauSexeDto {
  private Integer ordreNiveau;
  private String libelleNiveau;

  // Statistiques Admis
  private Long admisGarcon = 0L;
  private Long admisFille = 0L;
  private Long totalAdmis = 0L;

  // Statistiques Redoublants
  private Long redoublantGarcon = 0L;
  private Long redoublantFille = 0L;
  private Long totalRedoublants = 0L;

  // Statistiques Exclus
  private Long exclusGarcon = 0L;
  private Long exclusFille = 0L;
  private Long totalExclus = 0L;

  // Statistiques Non Classés
  private Long nonClasseGarcon = 0L;
  private Long nonClasseFille = 0L;
  private Long totalNonClasse = 0L;

  // Totaux par sexe
  private Long totalGarcon = 0L;
  private Long totalFille = 0L;
  private Long totalEleves = 0L;

  // NOUVEAUX CHAMPS: Pourcentages généraux par catégorie
  private Double pourcentageAdmis = 0.0;
  private Double pourcentageRedoublants = 0.0;
  private Double pourcentageExclus = 0.0;
  private Double pourcentageNonClasse = 0.0;

  // NOUVEAUX CHAMPS: Pourcentages par sexe - Admis
  private Double pourcentageAdmisGarcon = 0.0;
  private Double pourcentageAdmisFille = 0.0;

  // NOUVEAUX CHAMPS: Pourcentages par sexe - Redoublants
  private Double pourcentageRedoublantGarcon = 0.0;
  private Double pourcentageRedoublantFille = 0.0;

  // NOUVEAUX CHAMPS: Pourcentages par sexe - Exclus
  private Double pourcentageExclusGarcon = 0.0;
  private Double pourcentageExclusFille = 0.0;

  // NOUVEAUX CHAMPS: Pourcentages par sexe - Non classés
  private Double pourcentageNonClasseGarcon = 0.0;
  private Double pourcentageNonClasseFille = 0.0;

  // NOUVEAUX CHAMPS: Pourcentages totaux par sexe
  private Double pourcentageTotalGarcon = 0.0;
  private Double pourcentageTotalFille = 0.0;
  private Long nombreClasses;

  public Long getNombreClasses() {
    return nombreClasses;
  }

  public void setNombreClasses(Long nombreClasses) {
    this.nombreClasses = nombreClasses;
  }

  // Constructeurs
  public StatistiquesNiveauSexeDto() {}

  // Constructeur complet avec non classés
  public StatistiquesNiveauSexeDto(Integer ordreNiveau, String libelleNiveau,
                                   Long admisGarcon, Long admisFille,
                                   Long redoublantGarcon, Long redoublantFille,
                                   Long exclusGarcon, Long exclusFille,
                                   Long nonClasseGarcon, Long nonClasseFille) {
    this.ordreNiveau = ordreNiveau;
    this.libelleNiveau = libelleNiveau;
    this.admisGarcon = admisGarcon != null ? admisGarcon : 0L;
    this.admisFille = admisFille != null ? admisFille : 0L;
    this.redoublantGarcon = redoublantGarcon != null ? redoublantGarcon : 0L;
    this.redoublantFille = redoublantFille != null ? redoublantFille : 0L;
    this.exclusGarcon = exclusGarcon != null ? exclusGarcon : 0L;
    this.exclusFille = exclusFille != null ? exclusFille : 0L;
    this.nonClasseGarcon = nonClasseGarcon != null ? nonClasseGarcon : 0L;
    this.nonClasseFille = nonClasseFille != null ? nonClasseFille : 0L;


    // Calcul automatique des totaux et pourcentages
    calculerTotauxEtPourcentages();
  }

  // Constructeur ancien (pour compatibilité)
  public StatistiquesNiveauSexeDto(Integer ordreNiveau, String libelleNiveau,
                                   Long admisGarcon, Long admisFille,
                                   Long redoublantGarcon, Long redoublantFille,
                                   Long exclusGarcon, Long exclusFille) {
    this(ordreNiveau, libelleNiveau, admisGarcon, admisFille,
        redoublantGarcon, redoublantFille, exclusGarcon, exclusFille, 0L, 0L);
  }

  // Constructeur simple pour les cas par défaut
  public StatistiquesNiveauSexeDto(Integer ordreNiveau, String libelleNiveau) {
    this(ordreNiveau, libelleNiveau, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
  }

  @Override
  public String toString() {
    return "StatistiquesNiveauSexeDto{" +
        "ordreNiveau=" + ordreNiveau +
        ", libelleNiveau='" + libelleNiveau + '\'' +
        ", admisGarcon=" + admisGarcon +
        ", admisFille=" + admisFille +
        ", totalAdmis=" + totalAdmis +
        ", redoublantGarcon=" + redoublantGarcon +
        ", redoublantFille=" + redoublantFille +
        ", totalRedoublants=" + totalRedoublants +
        ", exclusGarcon=" + exclusGarcon +
        ", exclusFille=" + exclusFille +
        ", totalExclus=" + totalExclus +
        ", nonClasseGarcon=" + nonClasseGarcon +
        ", nonClasseFille=" + nonClasseFille +
        ", totalNonClasse=" + totalNonClasse +
        ", totalGarcon=" + totalGarcon +
        ", totalFille=" + totalFille +
        ", totalEleves=" + totalEleves +
        ", pourcentageAdmis=" + pourcentageAdmis +
        ", pourcentageRedoublants=" + pourcentageRedoublants +
        ", pourcentageExclus=" + pourcentageExclus +
        ", pourcentageNonClasse=" + pourcentageNonClasse +
        ", pourcentageAdmisGarcon=" + pourcentageAdmisGarcon +
        ", pourcentageAdmisFille=" + pourcentageAdmisFille +
        ", pourcentageRedoublantGarcon=" + pourcentageRedoublantGarcon +
        ", pourcentageRedoublantFille=" + pourcentageRedoublantFille +
        ", pourcentageExclusGarcon=" + pourcentageExclusGarcon +
        ", pourcentageExclusFille=" + pourcentageExclusFille +
        ", pourcentageNonClasseGarcon=" + pourcentageNonClasseGarcon +
        ", pourcentageNonClasseFille=" + pourcentageNonClasseFille +
        ", pourcentageTotalGarcon=" + pourcentageTotalGarcon +
        ", pourcentageTotalFille=" + pourcentageTotalFille +
        ", nombreClasses=" + nombreClasses +
        '}';
  }

  // Méthode pour calculer les totaux et pourcentages
  public void calculerTotauxEtPourcentages() {
    // Calcul des totaux
    this.totalAdmis = this.admisGarcon + this.admisFille;
    this.totalRedoublants = this.redoublantGarcon + this.redoublantFille;
    this.totalExclus = this.exclusGarcon + this.exclusFille;
    this.totalNonClasse = this.nonClasseGarcon + this.nonClasseFille;
    this.totalGarcon = this.admisGarcon + this.redoublantGarcon + this.exclusGarcon + this.nonClasseGarcon;
    this.totalFille = this.admisFille + this.redoublantFille + this.exclusFille + this.nonClasseFille;
    this.totalEleves = this.totalGarcon + this.totalFille;

    // Calcul des pourcentages (éviter division par zéro)
    if (this.totalEleves > 0) {
      // Pourcentages généraux par catégorie
      this.pourcentageAdmis = (this.totalAdmis * 100.0) / this.totalEleves;
      this.pourcentageRedoublants = (this.totalRedoublants * 100.0) / this.totalEleves;
      this.pourcentageExclus = (this.totalExclus * 100.0) / this.totalEleves;
      this.pourcentageNonClasse = (this.totalNonClasse * 100.0) / this.totalEleves;

      // Pourcentages par sexe - Admis
      this.pourcentageAdmisGarcon = (this.admisGarcon * 100.0) / this.totalEleves;
      this.pourcentageAdmisFille = (this.admisFille * 100.0) / this.totalEleves;

      // Pourcentages par sexe - Redoublants
      this.pourcentageRedoublantGarcon = (this.redoublantGarcon * 100.0) / this.totalEleves;
      this.pourcentageRedoublantFille = (this.redoublantFille * 100.0) / this.totalEleves;

      // Pourcentages par sexe - Exclus
      this.pourcentageExclusGarcon = (this.exclusGarcon * 100.0) / this.totalEleves;
      this.pourcentageExclusFille = (this.exclusFille * 100.0) / this.totalEleves;

      // Pourcentages par sexe - Non classés
      this.pourcentageNonClasseGarcon = (this.nonClasseGarcon * 100.0) / this.totalEleves;
      this.pourcentageNonClasseFille = (this.nonClasseFille * 100.0) / this.totalEleves;

      // Pourcentages totaux par sexe
      this.pourcentageTotalGarcon = (this.totalGarcon * 100.0) / this.totalEleves;
      this.pourcentageTotalFille = (this.totalFille * 100.0) / this.totalEleves;
    } else {
      // Si pas d'élèves, tous les pourcentages sont à 0
      resetPourcentages();
    }

    // Arrondir les pourcentages à 2 décimales
    arrondirPourcentages();
  }

  private void resetPourcentages() {
    this.pourcentageAdmis = 0.0;
    this.pourcentageRedoublants = 0.0;
    this.pourcentageExclus = 0.0;
    this.pourcentageNonClasse = 0.0;
    this.pourcentageAdmisGarcon = 0.0;
    this.pourcentageAdmisFille = 0.0;
    this.pourcentageRedoublantGarcon = 0.0;
    this.pourcentageRedoublantFille = 0.0;
    this.pourcentageExclusGarcon = 0.0;
    this.pourcentageExclusFille = 0.0;
    this.pourcentageNonClasseGarcon = 0.0;
    this.pourcentageNonClasseFille = 0.0;
    this.pourcentageTotalGarcon = 0.0;
    this.pourcentageTotalFille = 0.0;
  }

  private void arrondirPourcentages() {
    this.pourcentageAdmis = Math.round(this.pourcentageAdmis * 100.0) / 100.0;
    this.pourcentageRedoublants = Math.round(this.pourcentageRedoublants * 100.0) / 100.0;
    this.pourcentageExclus = Math.round(this.pourcentageExclus * 100.0) / 100.0;
    this.pourcentageNonClasse = Math.round(this.pourcentageNonClasse * 100.0) / 100.0;

    this.pourcentageAdmisGarcon = Math.round(this.pourcentageAdmisGarcon * 100.0) / 100.0;
    this.pourcentageAdmisFille = Math.round(this.pourcentageAdmisFille * 100.0) / 100.0;
    this.pourcentageRedoublantGarcon = Math.round(this.pourcentageRedoublantGarcon * 100.0) / 100.0;
    this.pourcentageRedoublantFille = Math.round(this.pourcentageRedoublantFille * 100.0) / 100.0;
    this.pourcentageExclusGarcon = Math.round(this.pourcentageExclusGarcon * 100.0) / 100.0;
    this.pourcentageExclusFille = Math.round(this.pourcentageExclusFille * 100.0) / 100.0;
    this.pourcentageNonClasseGarcon = Math.round(this.pourcentageNonClasseGarcon * 100.0) / 100.0;
    this.pourcentageNonClasseFille = Math.round(this.pourcentageNonClasseFille * 100.0) / 100.0;
    this.pourcentageTotalGarcon = Math.round(this.pourcentageTotalGarcon * 100.0) / 100.0;
    this.pourcentageTotalFille = Math.round(this.pourcentageTotalFille * 100.0) / 100.0;
  }

  // Méthode utilitaire pour recalculer si nécessaire
  public void recalculerPourcentages() {
    calculerTotauxEtPourcentages();
  }

  // Getters et Setters existants
  public Integer getOrdreNiveau() { return ordreNiveau; }
  public void setOrdreNiveau(Integer ordreNiveau) { this.ordreNiveau = ordreNiveau; }

  public String getLibelleNiveau() { return libelleNiveau; }
  public void setLibelleNiveau(String libelleNiveau) { this.libelleNiveau = libelleNiveau; }

  public Long getAdmisGarcon() { return admisGarcon; }
  public void setAdmisGarcon(Long admisGarcon) {
    this.admisGarcon = admisGarcon;
    calculerTotauxEtPourcentages();
  }

  public Long getAdmisFille() { return admisFille; }
  public void setAdmisFille(Long admisFille) {
    this.admisFille = admisFille;
    calculerTotauxEtPourcentages();
  }

  public Long getTotalAdmis() { return totalAdmis; }
  public void setTotalAdmis(Long totalAdmis) { this.totalAdmis = totalAdmis; }

  public Long getRedoublantGarcon() { return redoublantGarcon; }
  public void setRedoublantGarcon(Long redoublantGarcon) {
    this.redoublantGarcon = redoublantGarcon;
    calculerTotauxEtPourcentages();
  }

  public Long getRedoublantFille() { return redoublantFille; }
  public void setRedoublantFille(Long redoublantFille) {
    this.redoublantFille = redoublantFille;
    calculerTotauxEtPourcentages();
  }

  public Long getTotalRedoublants() { return totalRedoublants; }
  public void setTotalRedoublants(Long totalRedoublants) { this.totalRedoublants = totalRedoublants; }

  public Long getExclusGarcon() { return exclusGarcon; }
  public void setExclusGarcon(Long exclusGarcon) {
    this.exclusGarcon = exclusGarcon;
    calculerTotauxEtPourcentages();
  }

  public Long getExclusFille() { return exclusFille; }
  public void setExclusFille(Long exclusFille) {
    this.exclusFille = exclusFille;
    calculerTotauxEtPourcentages();
  }

  public Long getTotalExclus() { return totalExclus; }
  public void setTotalExclus(Long totalExclus) { this.totalExclus = totalExclus; }

  public Long getNonClasseGarcon() { return nonClasseGarcon; }
  public void setNonClasseGarcon(Long nonClasseGarcon) {
    this.nonClasseGarcon = nonClasseGarcon;
    calculerTotauxEtPourcentages();
  }

  public Long getNonClasseFille() { return nonClasseFille; }
  public void setNonClasseFille(Long nonClasseFille) {
    this.nonClasseFille = nonClasseFille;
    calculerTotauxEtPourcentages();
  }

  public Long getTotalNonClasse() { return totalNonClasse; }
  public void setTotalNonClasse(Long totalNonClasse) { this.totalNonClasse = totalNonClasse; }

  public Long getTotalGarcon() { return totalGarcon; }
  public void setTotalGarcon(Long totalGarcon) { this.totalGarcon = totalGarcon; }

  public Long getTotalFille() { return totalFille; }
  public void setTotalFille(Long totalFille) { this.totalFille = totalFille; }

  public Long getTotalEleves() { return totalEleves; }
  public void setTotalEleves(Long totalEleves) { this.totalEleves = totalEleves; }

  // NOUVEAUX GETTERS ET SETTERS POUR LES POURCENTAGES

  // Pourcentages généraux par catégorie
  public Double getPourcentageAdmis() { return pourcentageAdmis; }
  public void setPourcentageAdmis(Double pourcentageAdmis) { this.pourcentageAdmis = pourcentageAdmis; }

  public Double getPourcentageRedoublants() { return pourcentageRedoublants; }
  public void setPourcentageRedoublants(Double pourcentageRedoublants) { this.pourcentageRedoublants = pourcentageRedoublants; }

  public Double getPourcentageExclus() { return pourcentageExclus; }
  public void setPourcentageExclus(Double pourcentageExclus) { this.pourcentageExclus = pourcentageExclus; }

  public Double getPourcentageNonClasse() { return pourcentageNonClasse; }
  public void setPourcentageNonClasse(Double pourcentageNonClasse) { this.pourcentageNonClasse = pourcentageNonClasse; }

  // Pourcentages par sexe - Admis
  public Double getPourcentageAdmisGarcon() { return pourcentageAdmisGarcon; }
  public void setPourcentageAdmisGarcon(Double pourcentageAdmisGarcon) { this.pourcentageAdmisGarcon = pourcentageAdmisGarcon; }

  public Double getPourcentageAdmisFille() { return pourcentageAdmisFille; }
  public void setPourcentageAdmisFille(Double pourcentageAdmisFille) { this.pourcentageAdmisFille = pourcentageAdmisFille; }

  // Pourcentages par sexe - Redoublants
  public Double getPourcentageRedoublantGarcon() { return pourcentageRedoublantGarcon; }
  public void setPourcentageRedoublantGarcon(Double pourcentageRedoublantGarcon) { this.pourcentageRedoublantGarcon = pourcentageRedoublantGarcon; }

  public Double getPourcentageRedoublantFille() { return pourcentageRedoublantFille; }
  public void setPourcentageRedoublantFille(Double pourcentageRedoublantFille) { this.pourcentageRedoublantFille = pourcentageRedoublantFille; }

  // Pourcentages par sexe - Exclus
  public Double getPourcentageExclusGarcon() { return pourcentageExclusGarcon; }
  public void setPourcentageExclusGarcon(Double pourcentageExclusGarcon) { this.pourcentageExclusGarcon = pourcentageExclusGarcon; }

  public Double getPourcentageExclusFille() { return pourcentageExclusFille; }
  public void setPourcentageExclusFille(Double pourcentageExclusFille) { this.pourcentageExclusFille = pourcentageExclusFille; }

  // Pourcentages par sexe - Non classés
  public Double getPourcentageNonClasseGarcon() { return pourcentageNonClasseGarcon; }
  public void setPourcentageNonClasseGarcon(Double pourcentageNonClasseGarcon) { this.pourcentageNonClasseGarcon = pourcentageNonClasseGarcon; }

  public Double getPourcentageNonClasseFille() { return pourcentageNonClasseFille; }
  public void setPourcentageNonClasseFille(Double pourcentageNonClasseFille) { this.pourcentageNonClasseFille = pourcentageNonClasseFille; }

  // Pourcentages totaux par sexe
  public Double getPourcentageTotalGarcon() { return pourcentageTotalGarcon; }
  public void setPourcentageTotalGarcon(Double pourcentageTotalGarcon) { this.pourcentageTotalGarcon = pourcentageTotalGarcon; }

  public Double getPourcentageTotalFille() { return pourcentageTotalFille; }
  public void setPourcentageTotalFille(Double pourcentageTotalFille) { this.pourcentageTotalFille = pourcentageTotalFille; }
}
