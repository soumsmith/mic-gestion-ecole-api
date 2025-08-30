package com.vieecoles.entities.operations;

import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Rapide_rapide")
public class Rapide_rapide extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anne_id")
    private Annee_Scolaire annee_scolaire ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecole_id")
    private ecole ecole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branche_id")
    private Branche branche;
    private Integer nombreNAff ;
    private Integer nombreAff ;


 public Rapide_rapide() {
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

 public Branche getBranche() {
  return branche;
 }

 public void setBranche(Branche branche) {
  this.branche = branche;
 }

 public Integer getNombreNAff() {
  return nombreNAff;
 }

 public void setNombreNAff(Integer nombreNAff) {
  this.nombreNAff = nombreNAff;
 }

 public Integer getNombreAff() {
  return nombreAff;
 }

 public void setNombreAff(Integer nombreAff) {
  this.nombreAff = nombreAff;
 }


}
