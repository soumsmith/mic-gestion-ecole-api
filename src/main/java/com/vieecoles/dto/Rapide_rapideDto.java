package com.vieecoles.dto;

import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;



public class Rapide_rapideDto  {


    private Long  idAnn ;

    private Long idEcole;

    private Long idBranche ;
    private Integer nombreNAff ;
    private Integer nombreAff ;


 public Rapide_rapideDto() {
 }

 public Long getIdAnn() {
  return idAnn;
 }

 public void setIdAnn(Long idAnn) {
  this.idAnn = idAnn;
 }

 public Long getIdEcole() {
  return idEcole;
 }

 public void setIdEcole(Long idEcole) {
  this.idEcole = idEcole;
 }

 public Long getIdBranche() {
  return idBranche;
 }

 public void setIdBranche(Long idBranche) {
  this.idBranche = idBranche;
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
