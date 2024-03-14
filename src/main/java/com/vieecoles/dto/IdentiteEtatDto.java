package com.vieecoles.dto;

import java.util.List;

public class IdentiteEtatDto {
private String denominEtabli ;
private String numDecisionOuver ;
private String codeEtabli;
private String situationGeogra;
private String adressieEtablis;
private String telephonEtablisse;
private String faxEtabliss ;
private String emailEtablissem;
private String nomPrenomFondateur;

private String adresseFondateur;
private String telephoneFondateur ;
private String cellulaireFondateur ;
private String emailFondateur;
private String nomPrenomDireteurEtude;
private String prenomDirecteurEtude;
private String adresseDirecteurEtude;
private String telephoneDirecteuEtude;
private String cellulaireDirecteurEtude;
private String emailDirecteurEtude;
    private String autoriSatDirecteurEtude;

    public IdentiteEtatDto(List<IdentiteEtatDto> identiteDto) {
    }

    public String getEmailDirecteurEtude() {
        return emailDirecteurEtude;
    }

    public void setEmailDirecteurEtude(String emailDirecteurEtude) {
        this.emailDirecteurEtude = emailDirecteurEtude;
    }

    public String getAutoriSatDirecteurEtude() {
        return autoriSatDirecteurEtude;
    }

    public void setAutoriSatDirecteurEtude(String autoriSatDirecteurEtude) {
        this.autoriSatDirecteurEtude = autoriSatDirecteurEtude;
    }

    public IdentiteEtatDto() {
    }

    public IdentiteEtatDto(String denominEtabli,
                           String numDecisionOuver,
                           String codeEtabli,
                           String situationGeogra,
                           String adressieEtablis,
                           String telephonEtablisse,
                           String faxEtabliss,
                           String emailEtablissem,
                           String nomPrenomFondateur,

                           String adresseFondateur,
                           String telephoneFondateur,
                           String cellulaireFondateur,
                           String emailFondateur,
                           String nomPrenomDireteurEtude,

                           String adresseDirecteurEtude,
                           String telephoneDirecteuEtude,
                           String cellulaireDirecteurEtude,
                           String emailDirecteurEtude,
                           String autoriSatDirecteurEtude

                ) {
        this.denominEtabli = denominEtabli;
        this.numDecisionOuver = numDecisionOuver;
        this.codeEtabli = codeEtabli;
        this.situationGeogra = situationGeogra;
        this.adressieEtablis = adressieEtablis;
        this.telephonEtablisse = telephonEtablisse;
        this.faxEtabliss = faxEtabliss;
        this.emailEtablissem = emailEtablissem;
        this.nomPrenomFondateur = nomPrenomFondateur;
        this.adresseFondateur = adresseFondateur;
        this.telephoneFondateur = telephoneFondateur;
        this.cellulaireFondateur = cellulaireFondateur;
        this.emailFondateur = emailFondateur;
        this.nomPrenomDireteurEtude = nomPrenomDireteurEtude ;

        this.adresseDirecteurEtude = adresseDirecteurEtude;
        this.telephoneDirecteuEtude = telephoneDirecteuEtude;
        this.cellulaireDirecteurEtude = cellulaireDirecteurEtude;
        this.emailDirecteurEtude = emailDirecteurEtude ;
        this.autoriSatDirecteurEtude= autoriSatDirecteurEtude;
    }

    @Override
    public String toString() {
        return "IdentiteEtatDto{" +
                "denominEtabli='" + denominEtabli + '\'' +
                ", NumDecisionOuver='" + numDecisionOuver + '\'' +
                ", CodeEtabli='" + codeEtabli + '\'' +
                ", SituationGeogra='" + situationGeogra + '\'' +
                ", AdressieEtablis='" + adressieEtablis + '\'' +
                ", telephonEtablisse='" + telephonEtablisse + '\'' +
                ", faxEtabliss='" + faxEtabliss + '\'' +
                ", emailEtablissem='" + emailEtablissem + '\'' +
                ", nomPrenomFondateur='" + nomPrenomFondateur + '\'' +
                ", adresseFondateur='" + adresseFondateur + '\'' +
                ", telephoneFondateur='" + telephoneFondateur + '\'' +
                ", cellulaireFondateur='" + cellulaireFondateur + '\'' +
                ", emailFondateur='" + emailFondateur + '\'' +
                ", NomPrenomDireteurEtude='" + nomPrenomDireteurEtude + '\'' +
                ", prenomDirecteurEtude='" + prenomDirecteurEtude + '\'' +
                ", adresseDirecteurEtude='" + adresseDirecteurEtude + '\'' +
                ", telephoneDirecteuEtude='" + telephoneDirecteuEtude + '\'' +
                ", cellulaireDirecteurEtude='" + cellulaireDirecteurEtude + '\'' +
                ", emailDirecteurEtude='" + emailDirecteurEtude + '\'' +
                ", autoriSatDirecteurEtude='" + autoriSatDirecteurEtude + '\'' +
                '}';
    }

    public String getDenominEtabli() {
        return denominEtabli;
    }

    public void setDenominEtabli(String denominEtabli) {
        this.denominEtabli = denominEtabli;
    }

    public String getNumDecisionOuver() {
        return numDecisionOuver;
    }

    public void setNumDecisionOuver(String numDecisionOuver) {
        numDecisionOuver = numDecisionOuver;
    }

    public String getCodeEtabli() {
        return codeEtabli;
    }

    public void setCodeEtabli(String codeEtabli) {
        codeEtabli = codeEtabli;
    }

    public String getSituationGeogra() {
        return situationGeogra;
    }

    public void setSituationGeogra(String situationGeogra) {
        situationGeogra = situationGeogra;
    }

    public String getAdressieEtablis() {
        return adressieEtablis;
    }

    public void setAdressieEtablis(String adressieEtablis) {
        adressieEtablis = adressieEtablis;
    }

    public String getTelephonEtablisse() {
        return telephonEtablisse;
    }

    public void setTelephonEtablisse(String telephonEtablisse) {
        this.telephonEtablisse = telephonEtablisse;
    }
    public String getFaxEtabliss() {
        return faxEtabliss;
    }

    public void setFaxEtabliss(String faxEtabliss) {
        this.faxEtabliss = faxEtabliss;
    }

    public String getEmailEtablissem() {
        return emailEtablissem;
    }

    public void setEmailEtablissem(String emailEtablissem) {
        this.emailEtablissem = emailEtablissem;
    }


    public String getNomPrenomFondateur() {
        return nomPrenomFondateur;
    }

    public void setNomPrenomFondateur(String nomPrenomFondateur) {
        this.nomPrenomFondateur = nomPrenomFondateur;
    }

    public String getAdresseFondateur() {
        return adresseFondateur;
    }

    public void setAdresseFondateur(String adresseFondateur) {
        this.adresseFondateur = adresseFondateur;
    }

    public String getTelephoneFondateur() {
        return telephoneFondateur;
    }

    public void setTelephoneFondateur(String telephoneFondateur) {
        this.telephoneFondateur = telephoneFondateur;
    }

    public String getCellulaireFondateur() {
        return cellulaireFondateur;
    }

    public void setCellulaireFondateur(String cellulaireFondateur) {
        this.cellulaireFondateur = cellulaireFondateur;
    }

    public String getEmailFondateur() {
        return emailFondateur;
    }

    public void setEmailFondateur(String emailFondateur) {
        this.emailFondateur = emailFondateur;
    }

    public String getNomPrenomDireteurEtude() {
        return nomPrenomDireteurEtude;
    }

    public void setNomPrenomDireteurEtude(String nomPrenomDireteurEtude) {
        nomPrenomDireteurEtude = nomPrenomDireteurEtude;
    }

    public String getPrenomDirecteurEtude() {
        return prenomDirecteurEtude;
    }

    public void setPrenomDirecteurEtude(String prenomDirecteurEtude) {
        this.prenomDirecteurEtude = prenomDirecteurEtude;
    }

    public String getAdresseDirecteurEtude() {
        return adresseDirecteurEtude;
    }

    public void setAdresseDirecteurEtude(String adresseDirecteurEtude) {
        this.adresseDirecteurEtude = adresseDirecteurEtude;
    }

    public String getTelephoneDirecteuEtude() {
        return telephoneDirecteuEtude;
    }

    public void setTelephoneDirecteuEtude(String telephoneDirecteuEtude) {
        this.telephoneDirecteuEtude = telephoneDirecteuEtude;
    }

    public String getCellulaireDirecteurEtude() {
        return cellulaireDirecteurEtude;
    }

    public void setCellulaireDirecteurEtude(String cellulaireDirecteurEtude) {
        this.cellulaireDirecteurEtude = cellulaireDirecteurEtude;
    }
}
