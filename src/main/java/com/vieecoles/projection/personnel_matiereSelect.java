package com.vieecoles.projection;

import javax.persistence.*;

public class personnel_matiereSelect  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   personnel_matiereid ;
    private String  libelleMatiere ;
    private String  nomPersonnel;
    private String  prenomPersonnel;

    public personnel_matiereSelect(Long personnel_matiereid, String libelleMatiere, String nomPersonnel, String prenomPersonnel) {
        this.personnel_matiereid = personnel_matiereid;
        this.libelleMatiere = libelleMatiere;
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
    }

    public Long getPersonnel_matiereid() {
        return personnel_matiereid;
    }

    public void setPersonnel_matiereid(Long personnel_matiereid) {
        this.personnel_matiereid = personnel_matiereid;
    }

    public String getLibelleMatiere() {
        return libelleMatiere;
    }

    public void setLibelleMatiere(String libelleMatiere) {
        this.libelleMatiere = libelleMatiere;
    }

    public String getNomPersonnel() {
        return nomPersonnel;
    }

    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }

    public String getPrenomPersonnel() {
        return prenomPersonnel;
    }

    public void setPrenomPersonnel(String prenomPersonnel) {
        this.prenomPersonnel = prenomPersonnel;
    }

    @Override
    public String toString() {
        return "personnel_matiereSelect{" +
                "personnel_matiereid=" + personnel_matiereid +
                ", libelleMatiere='" + libelleMatiere + '\'' +
                ", nomPersonnel='" + nomPersonnel + '\'' +
                ", prenomPersonnel='" + prenomPersonnel + '\'' +
                '}';
    }
}
