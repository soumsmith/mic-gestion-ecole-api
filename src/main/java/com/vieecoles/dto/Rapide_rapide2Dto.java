package com.vieecoles.dto;

import com.vieecoles.entities.Annee_Scolaire;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;



public class Rapide_rapide2Dto {
   private  Long   id ;
    private Long idAnneeScolaire ;
    private Long idEcole ;

    private  Boolean perAdmi ;

    private  Boolean conseilInter ;
    private  Boolean conseilProfesseur ;
    private  Boolean conseilProfesPrincip ;
    private  Boolean conseilEnseigne ;
    private  Boolean professClassExame ;
    private  Boolean unitePedagogi ;
    private  Boolean parentsEleve ;
    private  Boolean perAdmichefClasse ;
    private Boolean chefClasse ;

 public Rapide_rapide2Dto() {
 }

 @Override
 public String toString() {
  return "Rapide_rapide2Dto{" +
          "id=" + id +
          ", idAnneeScolaire=" + idAnneeScolaire +
          ", idEcole=" + idEcole +
          ", perAdmi=" + perAdmi +
          ", conseilInter=" + conseilInter +
          ", conseilProfesseur=" + conseilProfesseur +
          ", conseilProfesPrincip=" + conseilProfesPrincip +
          ", conseilEnseigne=" + conseilEnseigne +
          ", professClassExame=" + professClassExame +
          ", unitePedagogi=" + unitePedagogi +
          ", parentsEleve=" + parentsEleve +
          ", perAdmichefClasse=" + perAdmichefClasse +
          ", chefClasse=" + chefClasse +
          '}';
 }

 public Boolean getChefClasse() {
  return chefClasse;
 }

 public void setChefClasse(Boolean chefClasse) {
  this.chefClasse = chefClasse;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Long getIdAnneeScolaire() {
  return idAnneeScolaire;
 }

 public void setIdAnneeScolaire(Long idAnneeScolaire) {
  this.idAnneeScolaire = idAnneeScolaire;
 }

 public Long getIdEcole() {
  return idEcole;
 }

 public void setIdEcole(Long idEcole) {
  this.idEcole = idEcole;
 }

 public Boolean getPerAdmi() {
  return perAdmi;
 }

 public void setPerAdmi(Boolean perAdmi) {
  this.perAdmi = perAdmi;
 }

 public Boolean getConseilInter() {
  return conseilInter;
 }

 public void setConseilInter(Boolean conseilInter) {
  this.conseilInter = conseilInter;
 }

 public Boolean getConseilProfesseur() {
  return conseilProfesseur;
 }

 public void setConseilProfesseur(Boolean conseilProfesseur) {
  this.conseilProfesseur = conseilProfesseur;
 }

 public Boolean getConseilProfesPrincip() {
  return conseilProfesPrincip;
 }

 public void setConseilProfesPrincip(Boolean conseilProfesPrincip) {
  this.conseilProfesPrincip = conseilProfesPrincip;
 }

 public Boolean getConseilEnseigne() {
  return conseilEnseigne;
 }

 public void setConseilEnseigne(Boolean conseilEnseigne) {
  this.conseilEnseigne = conseilEnseigne;
 }

 public Boolean getProfessClassExame() {
  return professClassExame;
 }

 public void setProfessClassExame(Boolean professClassExame) {
  this.professClassExame = professClassExame;
 }

 public Boolean getUnitePedagogi() {
  return unitePedagogi;
 }

 public void setUnitePedagogi(Boolean unitePedagogi) {
  this.unitePedagogi = unitePedagogi;
 }

 public Boolean getParentsEleve() {
  return parentsEleve;
 }

 public void setParentsEleve(Boolean parentsEleve) {
  this.parentsEleve = parentsEleve;
 }

 public Boolean getPerAdmichefClasse() {
  return perAdmichefClasse;
 }

 public void setPerAdmichefClasse(Boolean perAdmichefClasse) {
  this.perAdmichefClasse = perAdmichefClasse;
 }
}
