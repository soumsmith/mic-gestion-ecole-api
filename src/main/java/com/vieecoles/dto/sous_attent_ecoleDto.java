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
    private  Long commune_communeid  ;
    private Long fonctionid ;
    private String sous_attent_personn_login ;
    private String sous_attent_personn_password ;
    private  String sous_attent_personn_email;
    private Long Niveau_Enseignement_id ;
    private String  niveauEnseignement ;
    private String  libelleCommune ;
    private  String sousc_atten_etabliss_lien_logo ;




    public sous_attent_ecoleDto() {
    }

    public String getSousc_atten_etabliss_lien_logo() {
        return sousc_atten_etabliss_lien_logo;
    }

    public void setSousc_atten_etabliss_lien_logo(String sousc_atten_etabliss_lien_logo) {
        this.sousc_atten_etabliss_lien_logo = sousc_atten_etabliss_lien_logo;
    }

    public sous_attent_ecoleDto(String sousc_atten_etablisscode,
                                String sousc_atten_etabliss_nom,
                                String sousc_atten_etabliss_tel,
                                String sousc_atten_etabliss_email,
                                String sousc_atten_etabliss_lien_autorisa,
                                String sousc_atten_etabliss_indication,

                                Long zone_zoneid,
                                Long niveau_Enseignement_id,
                                Long communeid ,
                                String niveauEnseignement,
                                String libelleCommune
                                ) {
        this.sousc_atten_etablisscode = sousc_atten_etablisscode;
        this.sousc_atten_etabliss_nom = sousc_atten_etabliss_nom;
        this.sousc_atten_etabliss_tel = sousc_atten_etabliss_tel;
        this.sousc_atten_etabliss_email = sousc_atten_etabliss_email;
        this.sousc_atten_etabliss_lien_autorisa = sousc_atten_etabliss_lien_autorisa;
        this.sousc_atten_etabliss_indication = sousc_atten_etabliss_indication;
        this.zone_zoneid = zone_zoneid;
        this.Niveau_Enseignement_id = niveau_Enseignement_id;
        this.commune_communeid= communeid ;
        this.niveauEnseignement =niveauEnseignement ;
        this.libelleCommune=libelleCommune ;

    }

    public String getNiveauEnseignement() {
        return niveauEnseignement;
    }

    public void setNiveauEnseignement(String niveauEnseignement) {
        this.niveauEnseignement = niveauEnseignement;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public Long getCommune_communeid() {
        return commune_communeid;
    }

    public void setCommune_communeid(Long commune_communeid) {
        this.commune_communeid = commune_communeid;
    }

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

    /**
     * @return String return the sous_attent_personn_login
     */
    public String getSous_attent_personn_login() {
        return sous_attent_personn_login;
    }

    /**
     * @param sous_attent_personn_login the sous_attent_personn_login to set
     */
    public void setSous_attent_personn_login(String sous_attent_personn_login) {
        this.sous_attent_personn_login = sous_attent_personn_login;
    }

    /**
     * @return String return the sous_attent_personn_password
     */
    public String getSous_attent_personn_password() {
        return sous_attent_personn_password;
    }

    /**
     * @param sous_attent_personn_password the sous_attent_personn_password to set
     */
    public void setSous_attent_personn_password(String sous_attent_personn_password) {
        this.sous_attent_personn_password = sous_attent_personn_password;
    }

    /**
     * @return String return the sous_attent_personn_email
     */
    public String getSous_attent_personn_email() {
        return sous_attent_personn_email;
    }

    /**
     * @param sous_attent_personn_email the sous_attent_personn_email to set
     */
    public void setSous_attent_personn_email(String sous_attent_personn_email) {
        this.sous_attent_personn_email = sous_attent_personn_email;
    }


    /**
     * @return Long return the Niveau_Enseignement_id
     */
    public Long getNiveau_Enseignement_id() {
        return Niveau_Enseignement_id;
    }

    /**
     * @param Niveau_Enseignement_id the Niveau_Enseignement_id to set
     */
    public void setNiveau_Enseignement_id(Long Niveau_Enseignement_id) {
        this.Niveau_Enseignement_id = Niveau_Enseignement_id;
    }

}
