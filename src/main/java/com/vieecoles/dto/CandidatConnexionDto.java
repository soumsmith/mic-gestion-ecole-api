package com.vieecoles.dto;


public class CandidatConnexionDto {

    private Long  candidatid ;
    private  String candidat_nom;
    private  String candidat_prenom;
    private  String candidat_email;
    private String connect ;
   private  String libelleFonction ;
    public CandidatConnexionDto(Long candidatid, String candidat_nom, String candidat_prenom, String candidat_email ,String connect,
                                String libelleFonction ) {
        this.candidatid = candidatid;
        this.candidat_nom = candidat_nom;
        this.candidat_prenom = candidat_prenom;
        this.candidat_email = candidat_email;
        this.connect = connect ;
        this.libelleFonction=libelleFonction;
    }

    public String getLibelleFonction() {
        return libelleFonction;
    }

    public void setLibelleFonction(String libelleFonction) {
        this.libelleFonction = libelleFonction;
    }

    public CandidatConnexionDto() {
    }

    @Override
    public String toString() {
        return "CandidatConnexionDto{" +
                "candidatid=" + candidatid +
                ", candidat_nom='" + candidat_nom + '\'' +
                ", candidat_prenom='" + candidat_prenom + '\'' +
                ", candidat_email='" + candidat_email + '\'' +
                ", connect='" + connect + '\'' +
                '}';
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public Long getCandidatid() {
        return candidatid;
    }

    public void setCandidatid(Long candidatid) {
        this.candidatid = candidatid;
    }

    public String getCandidat_nom() {
        return candidat_nom;
    }

    public void setCandidat_nom(String candidat_nom) {
        this.candidat_nom = candidat_nom;
    }

    public String getCandidat_prenom() {
        return candidat_prenom;
    }

    public void setCandidat_prenom(String candidat_prenom) {
        this.candidat_prenom = candidat_prenom;
    }

    public String getCandidat_email() {
        return candidat_email;
    }

    public void setCandidat_email(String candidat_email) {
        this.candidat_email = candidat_email;
    }
}
