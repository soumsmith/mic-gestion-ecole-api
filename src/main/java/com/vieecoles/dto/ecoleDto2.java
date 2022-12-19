package com.vieecoles.dto;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ecoleDto {
    String codeEcole ;
    String libelleEcole;
    String arretecreation ;
    String zone ;
    String quartier ;
  String cycle ;
    String groupe_ecole ;

    public ecoleDto(String codeEcole, String libelleEcole, String arretecreation, String zone, String quartier, String groupe_ecole,String cycle) {
        this.codeEcole = codeEcole;
        this.libelleEcole = libelleEcole;
        this.arretecreation = arretecreation;
        this.zone = zone;
        this.quartier = quartier;
              this.groupe_ecole = groupe_ecole;
        this.cycle = cycle;
    }

    public ecoleDto() {
    }

    public String getGroupe_ecole() {
        return groupe_ecole;
    }

    public void setGroupe_ecole(String groupe_ecole) {
        this.groupe_ecole = groupe_ecole;
    }

    public String getCodeEcole() {
        return codeEcole;
    }

    public void setCodeEcole(String codeEcole) {
        this.codeEcole = codeEcole;
    }

    public String getLibelleEcole() {
        return libelleEcole;
    }

    public void setLibelleEcole(String libelleEcole) {
        this.libelleEcole = libelleEcole;
    }

    public String getArretecreation() {
        return arretecreation;
    }

    public void setArretecreation(String arretecreation) {
        this.arretecreation = arretecreation;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
}
