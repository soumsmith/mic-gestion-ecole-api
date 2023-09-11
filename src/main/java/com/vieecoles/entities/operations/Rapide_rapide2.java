package com.vieecoles.entities.operations;

import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity

public class Rapide_rapide2 extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anne_id")
    private Annee_Scolaire annee_scolaire ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecole_id")
    private ecole ecole;

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

 public Rapide_rapide2() {
 }

 public Boolean getChefClasse() {
  return chefClasse;
 }

 public void setChefClasse(Boolean chefClasse) {
  this.chefClasse = chefClasse;
 }

 @Override
 public String toString() {
  return "Rapide_rapide2{" +
          "id=" + id +
          ", annee_scolaire=" + annee_scolaire +
          ", ecole=" + ecole +
          ", perAdmi=" + perAdmi +
          ", conseilInter=" + conseilInter +
          ", conseilProfesseur=" + conseilProfesseur +
          ", conseilProfesPrincip=" + conseilProfesPrincip +
          ", conseilEnseigne=" + conseilEnseigne +
          ", professClassExame=" + professClassExame +
          ", unitePedagogi=" + unitePedagogi +
          ", parentsEleve=" + parentsEleve +
          ", perAdmichefClasse=" + perAdmichefClasse +
          '}';
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Annee_Scolaire getAnnee_scolaire() {
  return annee_scolaire;
 }

 public void setAnnee_scolaire(Annee_Scolaire annee_scolaire) {
  this.annee_scolaire = annee_scolaire;
 }

 public com.vieecoles.entities.operations.ecole getEcole() {
  return ecole;
 }

 public void setEcole(com.vieecoles.entities.operations.ecole ecole) {
  this.ecole = ecole;
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
