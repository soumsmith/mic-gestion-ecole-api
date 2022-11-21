package com.vieecoles.dao.entities.operations;


import com.vieecoles.dao.entities.Zone;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

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

    public Long getIdSOUS_ATTENT_ETABLISSEMENT() {
        return idSOUS_ATTENT_ETABLISSEMENT;
    }

    public void setIdSOUS_ATTENT_ETABLISSEMENT(Long idSOUS_ATTENT_ETABLISSEMENT) {
        this.idSOUS_ATTENT_ETABLISSEMENT = idSOUS_ATTENT_ETABLISSEMENT;
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
