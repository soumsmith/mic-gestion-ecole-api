package com.vieecoles.entities.operations;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

import com.vieecoles.entities.Zone;

import java.time.LocalDateTime;

@Entity(name = "sousc_atten_etabliss")
public class sousc_atten_etabliss extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idSOUS_ATTENT_ETABLISSEMENT ;
    private  String sousc_atten_etablisscode;
    private  String sousc_atten_etabliss_nom;
    private  String sousc_atten_etabliss_fondateur;
    private String  sousc_atten_etabliss_tel;
    private String sousc_atten_etabliss_email;
    private  String sousc_atten_etabliss_indication ;
    private  String sousc_atten_etabliss_lien_autorisa  ;
    private  String sousc_atten_etabliss_motifRefus ;
    private LocalDateTime sousc_atten_etabliss_dateCreation ;
    private  LocalDateTime sousc_atten_etabliss_date_traitement ;
    private  String sousc_atten_etabliss_lien_logo ;
    private Long Niveau_Enseignement_id ;
    private  int connecte ;

    @Enumerated(EnumType.STRING)
    private Inscriptions.status sousc_atten_etabliss_statut ;


   private  Long sous_attent_personn_sous_attent_personnid ;

    public int getConnecte() {
        return connecte;
    }

    public void setConnecte(int connecte) {
        this.connecte = connecte;
    }

    private Long  zone_zoneid ;
    private Long  commune_communeid ;

    @Override
    public String toString() {
        return "sousc_atten_etabliss{" +
                "idSOUS_ATTENT_ETABLISSEMENT=" + idSOUS_ATTENT_ETABLISSEMENT +
                ", sousc_atten_etablisscode='" + sousc_atten_etablisscode + '\'' +
                ", sousc_atten_etabliss_nom='" + sousc_atten_etabliss_nom + '\'' +
                ", sousc_atten_etabliss_fondateur='" + sousc_atten_etabliss_fondateur + '\'' +
                ", sousc_atten_etabliss_tel='" + sousc_atten_etabliss_tel + '\'' +
                ", sousc_atten_etabliss_email='" + sousc_atten_etabliss_email + '\'' +
                ", sousc_atten_etabliss_indication='" + sousc_atten_etabliss_indication + '\'' +
                ", sousc_atten_etabliss_lien_autorisa='" + sousc_atten_etabliss_lien_autorisa + '\'' +
                ", sousc_atten_etabliss_motifRefus='" + sousc_atten_etabliss_motifRefus + '\'' +
                ", sousc_atten_etabliss_dateCreation=" + sousc_atten_etabliss_dateCreation +
                ", sousc_atten_etabliss_date_traitement=" + sousc_atten_etabliss_date_traitement +
                ", Niveau_Enseignement_id=" + Niveau_Enseignement_id +
                ", sousc_atten_etabliss_statut=" + sousc_atten_etabliss_statut +
                ", sous_attent_personn_sous_attent_personnid=" + sous_attent_personn_sous_attent_personnid +
                ", zone_zoneid=" + zone_zoneid +
                '}';
    }

    public String getSousc_atten_etabliss_lien_logo() {
        return sousc_atten_etabliss_lien_logo;
    }

    public void setSousc_atten_etabliss_lien_logo(String sousc_atten_etabliss_lien_logo) {
        this.sousc_atten_etabliss_lien_logo = sousc_atten_etabliss_lien_logo;
    }

    public String getSousc_atten_etabliss_motifRefus() {
        return sousc_atten_etabliss_motifRefus;
    }

    public void setSousc_atten_etabliss_motifRefus(String sousc_atten_etabliss_motifRefus) {
        this.sousc_atten_etabliss_motifRefus = sousc_atten_etabliss_motifRefus;
    }

    public Long getCommune_communeid() {
        return commune_communeid;
    }

    public void setCommune_communeid(Long commune_communeid) {
        this.commune_communeid = commune_communeid;
    }

    public LocalDateTime getSousc_atten_etabliss_dateCreation() {
        return sousc_atten_etabliss_dateCreation;
    }

    public void setSousc_atten_etabliss_dateCreation(LocalDateTime sousc_atten_etabliss_dateCreation) {
        this.sousc_atten_etabliss_dateCreation = sousc_atten_etabliss_dateCreation;
    }

    public LocalDateTime getSousc_atten_etabliss_date_traitement() {
        return sousc_atten_etabliss_date_traitement;
    }

    public void setSousc_atten_etabliss_date_traitement(LocalDateTime sousc_atten_etabliss_date_traitement) {
        this.sousc_atten_etabliss_date_traitement = sousc_atten_etabliss_date_traitement;
    }

    public String getSousc_atten_etabliss_lien_autorisa() {
        return sousc_atten_etabliss_lien_autorisa;
    }

    public void setSousc_atten_etabliss_lien_autorisa(String sousc_atten_etabliss_lien_autorisa) {
        this.sousc_atten_etabliss_lien_autorisa = sousc_atten_etabliss_lien_autorisa;
    }


    public Inscriptions.status getSousc_atten_etabliss_statut() {
        return sousc_atten_etabliss_statut;
    }

    public void setSousc_atten_etabliss_statut(Inscriptions.status sousc_atten_etabliss_statut) {
        this.sousc_atten_etabliss_statut = sousc_atten_etabliss_statut;
    }

    public Long getIdSOUS_ATTENT_ETABLISSEMENT() {
        return idSOUS_ATTENT_ETABLISSEMENT;
    }

    public void setIdSOUS_ATTENT_ETABLISSEMENT(Long idSOUS_ATTENT_ETABLISSEMENT) {
        this.idSOUS_ATTENT_ETABLISSEMENT = idSOUS_ATTENT_ETABLISSEMENT;
    }

    public Long getSous_attent_personn_sous_attent_personnid() {
        return sous_attent_personn_sous_attent_personnid;
    }

    public void setSous_attent_personn_sous_attent_personnid(Long sous_attent_personn_sous_attent_personnid) {
        this.sous_attent_personn_sous_attent_personnid = sous_attent_personn_sous_attent_personnid;
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

    public String getSousc_atten_etabliss_fondateur() {
        return sousc_atten_etabliss_fondateur;
    }

    public void setSousc_atten_etabliss_fondateur(String sousc_atten_etabliss_fondateur) {
        this.sousc_atten_etabliss_fondateur = sousc_atten_etabliss_fondateur;
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

    public Long getZone_zoneid() {
        return zone_zoneid;
    }

    public void setZone_zoneid(Long zone_zoneid) {
        this.zone_zoneid = zone_zoneid;
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
