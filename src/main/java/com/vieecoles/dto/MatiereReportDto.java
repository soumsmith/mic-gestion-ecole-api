package com.vieecoles.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereReportDto {
  private String matiereCode;
  private String matiereLibelle;
  private String categorieMatiere;
  private Double moyenne;
  private Integer rang;
  private Double coef;
  private Double moyCoef;
  private String appreciation;
  private String  categorie;
  private Integer  num_ordre;
  private String nom_prenom_professeur ;
  private String sexe_professeur ;
  private Double moyAn;
  private String rangAn;
  private Integer pec;
  private Integer bonus;
  private String parentMatiere ;
  private String isRanked;
}
