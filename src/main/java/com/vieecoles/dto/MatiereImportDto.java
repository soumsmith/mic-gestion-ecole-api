package com.vieecoles.dto;

public class MatiereImportDto {
  Long id ;
  String libelle;
  Long matiereId;
  String coef;
  String categorie;
  Integer numOrdre;
  Integer pec ;

  public Integer getNumOrdre() {
    return numOrdre;
  }

  public void setNumOrdre(Integer numOrdre) {
    this.numOrdre = numOrdre;
  }

  public Integer getPec() {
    return pec;
  }

  public void setPec(Integer pec) {
    this.pec = pec;
  }

  public MatiereImportDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public Long getMatiereId() {
    return matiereId;
  }

  public void setMatiereId(Long matiereId) {
    this.matiereId = matiereId;
  }

  public String getCoef() {
    return coef;
  }

  public void setCoef(String coef) {
    this.coef = coef;
  }

  public String getCategorie() {
    return categorie;
  }

  public void setCategorie(String categorie) {
    this.categorie = categorie;
  }

  public MatiereImportDto(Long id, String libelle, Long matiereId, String coef, String categorie,Integer numOrdre,Integer pec) {
    this.id = id;
    this.libelle = libelle;
    this.matiereId = matiereId;
    this.coef = coef;
    this.categorie = categorie;
    this.numOrdre = numOrdre;
    this.pec = pec;
  }
}
