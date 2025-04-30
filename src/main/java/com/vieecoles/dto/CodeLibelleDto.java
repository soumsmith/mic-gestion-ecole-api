package com.vieecoles.dto;

public class CodeLibelleDto {
  String libelle;
  String code;
  public CodeLibelleDto(String libelle, String code) {
    this.libelle = libelle;
    this.code = code;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
