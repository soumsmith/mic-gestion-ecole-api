package com.vieecoles.dto;

public class ProcesVerbalStatistiqueDisciplineDto {
  String classe;
  String matiere;
  Integer effectif;
  Long nbreMoyenSup10;
  Long nbreMoyenInf10;
  Long nbreMoyenInf8_5;
  Double moyenMatiere;
  Double pourMoyenSup10;
  Double pourMoyenInf10;
  Double pourMoyenInf8_5;

  public String getClasse() {
    return classe;
  }

  public void setClasse(String classe) {
    this.classe = classe;
  }

  public String getMatiere() {
    return matiere;
  }

  public void setMatiere(String matiere) {
    this.matiere = matiere;
  }

  public Integer getEffectif() {
    return effectif;
  }

  public void setEffectif(Integer effectif) {
    this.effectif = effectif;
  }

  public Long getNbreMoyenSup10() {
    return nbreMoyenSup10;
  }

  public void setNbreMoyenSup10(Long nbreMoyenSup10) {
    this.nbreMoyenSup10 = nbreMoyenSup10;
  }

  public Long getNbreMoyenInf10() {
    return nbreMoyenInf10;
  }

  public void setNbreMoyenInf10(Long nbreMoyenInf10) {
    this.nbreMoyenInf10 = nbreMoyenInf10;
  }

  public Long getNbreMoyenInf8_5() {
    return nbreMoyenInf8_5;
  }

  public void setNbreMoyenInf8_5(Long nbreMoyenInf8_5) {
    this.nbreMoyenInf8_5 = nbreMoyenInf8_5;
  }

  public Double getMoyenMatiere() {
    return moyenMatiere;
  }

  public void setMoyenMatiere(Double moyenMatiere) {
    this.moyenMatiere = moyenMatiere;
  }

  public Double getPourMoyenSup10() {
    return pourMoyenSup10;
  }

  public void setPourMoyenSup10(Double pourMoyenSup10) {
    this.pourMoyenSup10 = pourMoyenSup10;
  }

  public Double getPourMoyenInf10() {
    return pourMoyenInf10;
  }

  public void setPourMoyenInf10(Double pourMoyenInf10) {
    this.pourMoyenInf10 = pourMoyenInf10;
  }

  public Double getPourMoyenInf8_5() {
    return pourMoyenInf8_5;
  }

  public void setPourMoyenInf8_5(Double pourMoyenInf8_5) {
    this.pourMoyenInf8_5 = pourMoyenInf8_5;
  }
}
