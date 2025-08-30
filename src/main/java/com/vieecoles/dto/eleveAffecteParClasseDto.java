package com.vieecoles.dto;

import java.util.List;

public class eleveAffecteParClasseDto {
   private  String classeLibelle;
   private  String professeurPrincipal ;
   private  String matricule ;
   private  String nomEleve ;
   private  String prenomEleve ;
   private  String sexe;
   private  String anneeNaissance ;
   private  String nationnalite ;
   private  String redoublan ;
   private  String affecte ;
   private  String numDecisionAffecte ;
   private  Double moyeGeneral ;
   private  Integer rang ;
   private  String observat ;
   private  String nomEducateur ;
   private   Integer ordre_niveau ;
   private  Double moyeGeneralTrim1 ;
   private  Double moyeGeneralTrim2 ;
   private  Double moyeGeneralTrim3;

   public Double getMoyeGeneralTrim1() {
      return moyeGeneralTrim1;
   }

   public void setMoyeGeneralTrim1(Double moyeGeneralTrim1) {
      this.moyeGeneralTrim1 = moyeGeneralTrim1;
   }

   public Double getMoyeGeneralTrim2() {
      return moyeGeneralTrim2;
   }

   public void setMoyeGeneralTrim2(Double moyeGeneralTrim2) {
      this.moyeGeneralTrim2 = moyeGeneralTrim2;
   }

   public Double getMoyeGeneralTrim3() {
      return moyeGeneralTrim3;
   }

   public void setMoyeGeneralTrim3(Double moyeGeneralTrim3) {
      this.moyeGeneralTrim3 = moyeGeneralTrim3;
   }

   public eleveAffecteParClasseDto(List<eleveAffecteParClasseDto> eleveAffecteParClasseDtos) {
    }

    public Integer getOrdre_niveau() {
      return ordre_niveau;
   }

   public void setOrdre_niveau(Integer ordre_niveau) {
      this.ordre_niveau = ordre_niveau;
   }

   public eleveAffecteParClasseDto(String classeLibelle, String professeurPrincipal,
                                   String matricule,
                                   String nomEleve, String prenomEleve, String sexe,
                                   String anneeNaissance, String nationnalite, String redoublan,
                                   String affecte, String numDecisionAffecte, Double moyeGeneral,
                                   Integer rang, String observat, String nomEducateur,
                                   Integer ordre_niveau) {
      this.classeLibelle = classeLibelle;
      this.professeurPrincipal = professeurPrincipal;
      this.matricule = matricule;
      this.nomEleve = nomEleve;
      this.prenomEleve = prenomEleve;
      this.sexe = sexe;
      this.anneeNaissance = anneeNaissance;
      this.nationnalite = nationnalite;
      this.redoublan = redoublan;
      this.affecte = affecte;
      this.numDecisionAffecte = numDecisionAffecte;
      this.moyeGeneral = moyeGeneral;
      this.rang = rang;
      this.observat = observat;
      this.nomEducateur = nomEducateur;
      this.ordre_niveau = ordre_niveau;
   }

   public eleveAffecteParClasseDto() {
   }

   public String getClasseLibelle() {
      return classeLibelle;
   }

   public void setClasseLibelle(String classeLibelle) {
      this.classeLibelle = classeLibelle;
   }

   public String getProfesseurPrincipal() {
      return professeurPrincipal;
   }

   public void setProfesseurPrincipal(String professeurPrincipal) {
      this.professeurPrincipal = professeurPrincipal;
   }

   public String getMatricule() {
      return matricule;
   }

   public void setMatricule(String matricule) {
      this.matricule = matricule;
   }

   public String getNomEleve() {
      return nomEleve;
   }

   public void setNomEleve(String nomEleve) {
      this.nomEleve = nomEleve;
   }

   public String getPrenomEleve() {
      return prenomEleve;
   }

   public void setPrenomEleve(String prenomEleve) {
      this.prenomEleve = prenomEleve;
   }

   public String getSexe() {
      return sexe;
   }

   public void setSexe(String sexe) {
      this.sexe = sexe;
   }

   public String getAnneeNaissance() {
      return anneeNaissance;
   }

   public void setAnneeNaissance(String anneeNaissance) {
      this.anneeNaissance = anneeNaissance;
   }

   public String getNationnalite() {
      return nationnalite;
   }

   public void setNationnalite(String nationnalite) {
      this.nationnalite = nationnalite;
   }

   public String getRedoublan() {
      return redoublan;
   }

   public void setRedoublan(String redoublan) {
      this.redoublan = redoublan;
   }

   public String getAffecte() {
      return affecte;
   }

   public void setAffecte(String affecte) {
      this.affecte = affecte;
   }

   public String getNumDecisionAffecte() {
      return numDecisionAffecte;
   }

   public void setNumDecisionAffecte(String numDecisionAffecte) {
      this.numDecisionAffecte = numDecisionAffecte;
   }

   public Double getMoyeGeneral() {
      return moyeGeneral;
   }

   public void setMoyeGeneral(Double moyeGeneral) {
      this.moyeGeneral = moyeGeneral;
   }

   public Integer getRang() {
      return rang;
   }

   public void setRang(Integer rang) {
      this.rang = rang;
   }

   public String getObservat() {
      return observat;
   }

   public void setObservat(String observat) {
      this.observat = observat;
   }

   public String getNomEducateur() {
      return nomEducateur;
   }

   public void setNomEducateur(String nomEducateur) {
      this.nomEducateur = nomEducateur;
   }
}
