package com.vieecoles.projection;


import java.time.LocalDate;


public class InfosConnexionSelect {
private  String nom ;
private String prenom ;
private  String ecole ;
private String login ;
private String motPasse ;

    public InfosConnexionSelect() {
    }

    public InfosConnexionSelect(String nom, String prenom, String ecole, String login, String motPasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.ecole = ecole;
        this.login = login;
        this.motPasse = motPasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEcole() {
        return ecole;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }
}
