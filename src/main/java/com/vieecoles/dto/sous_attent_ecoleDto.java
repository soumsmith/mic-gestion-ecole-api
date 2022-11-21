package com.vieecoles.dto;


import java.time.LocalDate;


public class sous_attent_ecoleDto {

    private Long  sousc_atten_etablissid ;
   private  String sousc_atten_etablisscode;
    private  String sousc_atten_etabliss_nom;

    private  String sousc_atten_etabliss_Prenomfondateur;
    private String  sousc_atten_etabliss_tel;
    private String  sousc_atten_etabliss_email;
    private  String sousc_atten_etabliss_lien_autorisa  ;
    private  String sousc_atten_etabliss_indication ;
    private  Long  ville_villeid ;
    private  Long zone_zoneid  ;
    private Long fonctionid ;

    public Long getSousc_atten_etablissid() {
        return sousc_atten_etablissid;
    }

    public void setSousc_atten_etablissid(Long sousc_atten_etablissid) {
        this.sousc_atten_etablissid = sousc_atten_etablissid;
    }

    public String getSousc_atten_etabliss_Prenomfondateur() {
        return sousc_atten_etabliss_Prenomfondateur;
    }

    public void setSousc_atten_etabliss_Prenomfondateur(String sousc_atten_etabliss_Prenomfondateur) {
        this.sousc_atten_etabliss_Prenomfondateur = sousc_atten_etabliss_Prenomfondateur;
    }

    public String getSousc_atten_etabliss_lien_autorisa() {
        return sousc_atten_etabliss_lien_autorisa;
    }

    public void setSousc_atten_etabliss_lien_autorisa(String sousc_atten_etabliss_lien_autorisa) {
        this.sousc_atten_etabliss_lien_autorisa = sousc_atten_etabliss_lien_autorisa;
    }

    public Long getFonctionid() {
        return fonctionid;
    }

    public void setFonctionid(Long fonctionid) {
        this.fonctionid = fonctionid;
    }

    public String getSousc_atten_etablisscode() {
        return sousc_atten_etablisscode;
    }

    public void setSousc_atten_etablisscode(String sousc_atten_etablisscode) {
        this.sousc_atten_etablisscode = sousc_atten_etablisscode;
    }

    public String getSousc_atten_etabliss_nom() {
        return sousc_atten_etabliss_nom;
    }

    public void setSousc_atten_etabliss_nom(String sousc_atten_etabliss_nom) {
        this.sousc_atten_etabliss_nom = sousc_atten_etabliss_nom;
    }



    public String getSousc_atten_etabliss_tel() {
        return sousc_atten_etabliss_tel;
    }

    public void setSousc_atten_etabliss_tel(String sousc_atten_etabliss_tel) {
        this.sousc_atten_etabliss_tel = sousc_atten_etabliss_tel;
    }

    public String getSousc_atten_etabliss_email() {
        return sousc_atten_etabliss_email;
    }

    public void setSousc_atten_etabliss_email(String sousc_atten_etabliss_email) {
        this.sousc_atten_etabliss_email = sousc_atten_etabliss_email;
    }

    public String getSousc_atten_etabliss_indication() {
        return sousc_atten_etabliss_indication;
    }

    public void setSousc_atten_etabliss_indication(String sousc_atten_etabliss_indication) {
        this.sousc_atten_etabliss_indication = sousc_atten_etabliss_indication;
    }

    public Long getVille_villeid() {
        return ville_villeid;
    }

    public void setVille_villeid(Long ville_villeid) {
        this.ville_villeid = ville_villeid;
    }

    public Long getZone_zoneid() {
        return zone_zoneid;
    }

    public void setZone_zoneid(Long zone_zoneid) {
        this.zone_zoneid = zone_zoneid;
    }
}
