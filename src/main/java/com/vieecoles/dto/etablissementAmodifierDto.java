package com.vieecoles.dto;


import com.vieecoles.entities.Direction_regionale;
import com.vieecoles.entities.Zone;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ville;
import com.vieecoles.entities.operations.commune;
import com.vieecoles.entities.pays;
import com.vieecoles.steph.entities.NiveauEnseignement;

public class etablissementAmodifierDto {

    private  Long sousc_atten_etablissid;
   private  String sousc_atten_etablisscode;
    private  String sousc_atten_etabliss_nom;
    private String  sousc_atten_etabliss_tel;
    private String  sousc_atten_etabliss_email;
    private  String sousc_atten_etabliss_indication ;
    private ville ville_ville ;
    private  commune commune_commune ;
    private Zone zone_zone ;
    private NiveauEnseignement Niveau_Enseignement_obj;
    private  String sousc_atten_etabliss_lien_autorisa ;
    private Direction_regionale myDirection_regionale ;
    private String sousc_atten_etabliss_lien_logo ;
    private Inscriptions.status sousc_atten_etabliss_status ;
    private pays pays ;
    private String adresse;
    private String signataire ;
    private String num_decision ;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSignataire() {
        return signataire;
    }

    public void setSignataire(String signataire) {
        this.signataire = signataire;
    }

    public String getNum_decision() {
        return num_decision;
    }

    public void setNum_decision(String num_decision) {
        this.num_decision = num_decision;
    }

    public Long getSousc_atten_etablissid() {
        return sousc_atten_etablissid;
    }

    public void setSousc_atten_etablissid(Long sousc_atten_etablissid) {
        this.sousc_atten_etablissid = sousc_atten_etablissid;
    }

    public etablissementAmodifierDto() {
    }

    public String getSousc_atten_etabliss_lien_logo() {
        return sousc_atten_etabliss_lien_logo;
    }

    public Inscriptions.status getSousc_atten_etabliss_status() {
        return sousc_atten_etabliss_status;
    }

    public void setSousc_atten_etabliss_status(Inscriptions.status sousc_atten_etabliss_status) {
        this.sousc_atten_etabliss_status = sousc_atten_etabliss_status;
    }

    public void setSousc_atten_etabliss_lien_logo(String sousc_atten_etabliss_lien_logo) {
        this.sousc_atten_etabliss_lien_logo = sousc_atten_etabliss_lien_logo;
    }

    public etablissementAmodifierDto(Long sousc_atten_etablissid,
                                     String sousc_atten_etablisscode,
                                     String sousc_atten_etabliss_nom,
                                     String sousc_atten_etabliss_tel,
                                     String sousc_atten_etabliss_email,
                                     String sousc_atten_etabliss_indication,
                                     com.vieecoles.entities.operations.ville ville,
                                     com.vieecoles.entities.operations.commune commune,
                                     Zone zone, NiveauEnseignement niveauEnseignement,
                                     String nomFichier, Direction_regionale myDirection_regionale,
                                     pays pays,String sousc_atten_etabliss_lien_logo ,
                                     Inscriptions.status sousc_atten_etabliss_status,
                                     String adresse,String signataire , String num_decision) {
        this.sousc_atten_etablissid = sousc_atten_etablissid ;
        this.sousc_atten_etablisscode = sousc_atten_etablisscode;
        this.sousc_atten_etabliss_nom = sousc_atten_etabliss_nom;
        this.sousc_atten_etabliss_tel = sousc_atten_etabliss_tel;
        this.sousc_atten_etabliss_email = sousc_atten_etabliss_email;
        this.sousc_atten_etabliss_indication = sousc_atten_etabliss_indication;
        this.ville_ville = ville;
        this.commune_commune = commune;
        this.zone_zone = zone;
        this.Niveau_Enseignement_obj = niveauEnseignement;
        this.sousc_atten_etabliss_lien_autorisa = nomFichier;
        this.myDirection_regionale = myDirection_regionale ;
        this.pays= pays;
        this.sousc_atten_etabliss_lien_logo =  sousc_atten_etabliss_lien_logo;
        this.sousc_atten_etabliss_status = sousc_atten_etabliss_status ;
        this.adresse = adresse;
        this.signataire =signataire;
        this.num_decision = num_decision ;
    }

    public String getSousc_atten_etablisscode() {
        return sousc_atten_etablisscode;
    }

    public com.vieecoles.entities.pays getPays() {
        return pays;
    }

    public void setPays(com.vieecoles.entities.pays pays) {
        this.pays = pays;
    }

    public Direction_regionale getMyDirection_regionale() {
        return myDirection_regionale;
    }

    public void setMyDirection_regionale(Direction_regionale myDirection_regionale) {
        this.myDirection_regionale = myDirection_regionale;
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

    public ville getVille_ville() {
        return ville_ville;
    }

    public void setVille_ville(ville ville_ville) {
        this.ville_ville = ville_ville;
    }

    public commune getCommune_commune() {
        return commune_commune;
    }

    public void setCommune_commune(commune commune_commune) {
        this.commune_commune = commune_commune;
    }

    public Zone getZone_zone() {
        return zone_zone;
    }

    public void setZone_zone(Zone zone_zone) {
        this.zone_zone = zone_zone;
    }

    public NiveauEnseignement getNiveau_Enseignement_obj() {
        return Niveau_Enseignement_obj;
    }

    public void setNiveau_Enseignement_obj(NiveauEnseignement niveau_Enseignement_obj) {
        Niveau_Enseignement_obj = niveau_Enseignement_obj;
    }

    public String getSousc_atten_etabliss_lien_autorisa() {
        return sousc_atten_etabliss_lien_autorisa;
    }

    public void setSousc_atten_etabliss_lien_autorisa(String sousc_atten_etabliss_lien_autorisa) {
        this.sousc_atten_etabliss_lien_autorisa = sousc_atten_etabliss_lien_autorisa;
    }
}
