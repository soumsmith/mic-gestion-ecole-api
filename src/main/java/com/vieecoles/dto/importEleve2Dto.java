package com.vieecoles.dto;

public class importEleveDto {
    private  String elevematricule_national ;
    private  String  elevenom;
    private  String eleveprenom ;
    private  String inscriptions_statut_eleve;
    private  Long identifiantEcole;
    private  String libelleBranche ;
    private  Long identifiantBranche;
    private  String eleveSexe;


    public importEleveDto() {
    }

    public String getElevematricule_national() {
        return elevematricule_national;
    }

    public void setElevematricule_national(String elevematricule_national) {
        this.elevematricule_national = elevematricule_national;
    }

    public String getElevenom() {
        return elevenom;
    }

    public void setElevenom(String elevenom) {
        this.elevenom = elevenom;
    }

    public String getEleveprenom() {
        return eleveprenom;
    }

    public void setEleveprenom(String eleveprenom) {
        this.eleveprenom = eleveprenom;
    }

    public String getInscriptions_statut_eleve() {
        return inscriptions_statut_eleve;
    }

    public void setInscriptions_statut_eleve(String inscriptions_statut_eleve) {
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
    }

    public String getLibelleBranche() {
        return libelleBranche;
    }

    public void setLibelleBranche(String libelleBranche) {
        this.libelleBranche = libelleBranche;
    }

    public Long getIdentifiantEcole() {
        return identifiantEcole;
    }

    public void setIdentifiantEcole(Long identifiantEcole) {
        this.identifiantEcole = identifiantEcole;
    }

    public Long getIdentifiantBranche() {
        return identifiantBranche;
    }

    public void setIdentifiantBranche(Long identifiantBranche) {
        this.identifiantBranche = identifiantBranche;
    }

    public String getEleveSexe() {
        return eleveSexe;
    }

    public void setEleveSexe(String eleveSexe) {
        this.eleveSexe = eleveSexe;
    }
}
