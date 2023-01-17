package com.vieecoles.dao.entities.operations;


import com.vieecoles.dao.entities.Zone;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Inscriptions.status sousc_atten_etabliss_statut ;


private  Long sous_attent_personn_sous_attent_personnid ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ville_villeid")
    private  ville ville ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_zoneid")
    private Zone zone ;

    @Override
    public String toString() {
        return "sousc_atten_etabliss{" +
                "sousc_atten_etablissid=" + idSOUS_ATTENT_ETABLISSEMENT +
                ", sousc_atten_etablisscode='" + sousc_atten_etablisscode + '\'' +
                ", sousc_atten_etabliss_nom='" + sousc_atten_etabliss_nom + '\'' +
                ", sousc_atten_etabliss_fondateur='" + sousc_atten_etabliss_fondateur + '\'' +
                ", sousc_atten_etabliss_tel='" + sousc_atten_etabliss_tel + '\'' +
                ", sousc_atten_etabliss_email='" + sousc_atten_etabliss_email + '\'' +
                ", sousc_atten_etabliss_indication='" + sousc_atten_etabliss_indication + '\'' +
                ", ville=" + ville +
                ", zone=" + zone +
                '}';
    }

    public String getSousc_atten_etabliss_motifRefus() {
        return sousc_atten_etabliss_motifRefus;
    }

    public void setSousc_atten_etabliss_motifRefus(String sousc_atten_etabliss_motifRefus) {
        this.sousc_atten_etabliss_motifRefus = sousc_atten_etabliss_motifRefus;
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

    public com.vieecoles.dao.entities.operations.ville getVille() {
        return ville;
    }

    public void setVille(com.vieecoles.dao.entities.operations.ville ville) {
        this.ville = ville;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
