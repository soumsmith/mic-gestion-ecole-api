package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;



import java.util.ArrayList;
import java.util.List;

@Entity
public class ecole extends PanacheEntityBase {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  ecoleid ;
    private  String ecolecode;
    private  String ecoleclibelle;
    private  String ecolearreteecreation;
    private  boolean recoiaffecteetat;
     private Long zone_zoneid ;
    private  String ecole_indication ;
    private  String ecole_fondateur_nom_prenom ;
    private  String ecole_fondateur_contact ;
    private Long quartier_quartierid ;
    private Long groupe_ecole_groupe_ecoleid;
    private  String ecole_libelle_groupe ;
    private  String  ecole_adresse ;
    private  String  ecole_telephone ;
    private  String  ecole_statut ;

    //private Long ville_villeid ;
    private Long sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT;
    private Long Niveau_Enseignement_id ;
    private Long commune_communeid ;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] logoBlob ;
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] filigramme ;
    private String nom_signataire ;
     private  String numero_decision_ouver ;

    public String getNumero_decision_ouver() {
        return numero_decision_ouver;
    }

    public void setNumero_decision_ouver(String numero_decision_ouver) {
        this.numero_decision_ouver = numero_decision_ouver;
    }

    /**
     * @return Long return the ecoleid
     */
    public Long getEcoleid() {
        return ecoleid;
    }

    /**
     * @param ecoleid the ecoleid to set
     */
    public void setEcoleid(Long ecoleid) {
        this.ecoleid = ecoleid;
    }

    public byte[] getFiligramme() {
        return filigramme;
    }

    public String getNom_signataire() {
        return nom_signataire;
    }

    public void setNom_signataire(String nom_signataire) {
        this.nom_signataire = nom_signataire;
    }

    public void setFiligramme(byte[] filigramme) {
        this.filigramme = filigramme;
    }

    /**
     * @return String return the ecolecode
     */
    public String getEcolecode() {
        return ecolecode;
    }

    public Long getCommune_communeid() {
        return commune_communeid;
    }

    public void setCommune_communeid(Long commune_communeid) {
        this.commune_communeid = commune_communeid;
    }

    /**
     * @param ecolecode the ecolecode to set
     */
    public void setEcolecode(String ecolecode) {
        this.ecolecode = ecolecode;
    }

    /**
     * @return String return the ecoleclibelle
     */
    public String getEcoleclibelle() {
        return ecoleclibelle;
    }

    /**
     * @param ecoleclibelle the ecoleclibelle to set
     */
    public void setEcoleclibelle(String ecoleclibelle) {
        this.ecoleclibelle = ecoleclibelle;
    }

    /**
     * @return String return the ecolearreteecreation
     */
    public String getEcolearreteecreation() {
        return ecolearreteecreation;
    }

    /**
     * @param ecolearreteecreation the ecolearreteecreation to set
     */
    public void setEcolearreteecreation(String ecolearreteecreation) {
        this.ecolearreteecreation = ecolearreteecreation;
    }

    /**
     * @return boolean return the recoiaffecteetat
     */
    public boolean isRecoiaffecteetat() {
        return recoiaffecteetat;
    }

    /**
     * @param recoiaffecteetat the recoiaffecteetat to set
     */
    public void setRecoiaffecteetat(boolean recoiaffecteetat) {
        this.recoiaffecteetat = recoiaffecteetat;
    }

    /**
     * @return Long return the zone_zoneid
     */
    public Long getZone_zoneid() {
        return zone_zoneid;
    }

    /**
     * @param zone_zoneid the zone_zoneid to set
     */
    public void setZone_zoneid(Long zone_zoneid) {
        this.zone_zoneid = zone_zoneid;
    }

    /**
     * @return String return the ecole_indication
     */
    public String getEcole_indication() {
        return ecole_indication;
    }

    /**
     * @param ecole_indication the ecole_indication to set
     */
    public void setEcole_indication(String ecole_indication) {
        this.ecole_indication = ecole_indication;
    }

    @Override
    public String toString() {
        return "ecole{" +
                "ecoleid=" + ecoleid +
                ", ecolecode='" + ecolecode + '\'' +
                ", ecoleclibelle='" + ecoleclibelle + '\'' +
                ", ecolearreteecreation='" + ecolearreteecreation + '\'' +
                ", recoiaffecteetat=" + recoiaffecteetat +
                ", zone_zoneid=" + zone_zoneid +
                ", ecole_indication='" + ecole_indication + '\'' +
                ", ecole_fondateur_nom_prenom='" + ecole_fondateur_nom_prenom + '\'' +
                ", ecole_fondateur_contact='" + ecole_fondateur_contact + '\'' +
                ", quartier_quartierid=" + quartier_quartierid +
                ", groupe_ecole_groupe_ecoleid=" + groupe_ecole_groupe_ecoleid +
                ", ecole_libelle_groupe='" + ecole_libelle_groupe + '\'' +
                ", ecole_adresse='" + ecole_adresse + '\'' +
                ", ecole_telephone='" + ecole_telephone + '\'' +
                ", ecole_statut='" + ecole_statut + '\'' +
                ", sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT=" + sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT +
                ", Niveau_Enseignement_id=" + Niveau_Enseignement_id +
                ", commune_communeid=" + commune_communeid +
                '}';
    }

    /**
     * @return String return the ecole_fondateur_nom_prenom
     */

    public String getEcole_fondateur_nom_prenom() {
        return ecole_fondateur_nom_prenom;
    }

    /**
     * @param ecole_fondateur_nom_prenom the ecole_fondateur_nom_prenom to set
     */
    public void setEcole_fondateur_nom_prenom(String ecole_fondateur_nom_prenom) {
        this.ecole_fondateur_nom_prenom = ecole_fondateur_nom_prenom;
    }

    /**
     * @return String return the ecole_fondateur_contact
     */
    public String getEcole_fondateur_contact() {
        return ecole_fondateur_contact;
    }

    /**
     * @param ecole_fondateur_contact the ecole_fondateur_contact to set
     */
    public void setEcole_fondateur_contact(String ecole_fondateur_contact) {
        this.ecole_fondateur_contact = ecole_fondateur_contact;
    }

    public byte[] getLogoBlob() {
        return logoBlob;
    }

    public void setLogoBlob(byte[] logoBlob) {
        this.logoBlob = logoBlob;
    }

    /**
     * @return Long return the quartier_quartierid
     */
    public Long getQuartier_quartierid() {
        return quartier_quartierid;
    }

    /**
     * @param quartier_quartierid the quartier_quartierid to set
     */
    public void setQuartier_quartierid(Long quartier_quartierid) {
        this.quartier_quartierid = quartier_quartierid;
    }

    /**
     * @return Long return the groupe_ecole_groupe_ecoleid
     */
    public Long getGroupe_ecole_groupe_ecoleid() {
        return groupe_ecole_groupe_ecoleid;
    }

    /**
     * @param groupe_ecole_groupe_ecoleid the groupe_ecole_groupe_ecoleid to set
     */
    public void setGroupe_ecole_groupe_ecoleid(Long groupe_ecole_groupe_ecoleid) {
        this.groupe_ecole_groupe_ecoleid = groupe_ecole_groupe_ecoleid;
    }

    /**
     * @return String return the ecole_libelle_groupe
     */
    public String getEcole_libelle_groupe() {
        return ecole_libelle_groupe;
    }

    /**
     * @param ecole_libelle_groupe the ecole_libelle_groupe to set
     */
    public void setEcole_libelle_groupe(String ecole_libelle_groupe) {
        this.ecole_libelle_groupe = ecole_libelle_groupe;
    }

    /**
     * @return String return the ecole_adresse
     */
    public String getEcole_adresse() {
        return ecole_adresse;
    }

    /**
     * @param ecole_adresse the ecole_adresse to set
     */
    public void setEcole_adresse(String ecole_adresse) {
        this.ecole_adresse = ecole_adresse;
    }

    /**
     * @return String return the ecole_telephone
     */
    public String getEcole_telephone() {
        return ecole_telephone;
    }

    /**
     * @param ecole_telephone the ecole_telephone to set
     */
    public void setEcole_telephone(String ecole_telephone) {
        this.ecole_telephone = ecole_telephone;
    }

    /**
     * @return String return the ecole_statut
     */
    public String getEcole_statut() {
        return ecole_statut;
    }

    /**
     * @param ecole_statut the ecole_statut to set
     */
    public void setEcole_statut(String ecole_statut) {
        this.ecole_statut = ecole_statut;
    }

    /**
     * @return Long return the ville_villeid
     */



    /**
     * @return Long return the sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT
     */
    public Long getSousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT() {
        return sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT;
    }

    /**
     * @param sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT the sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT to set
     */
    public void setSousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT(Long sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT) {
        this.sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT = sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT;
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
