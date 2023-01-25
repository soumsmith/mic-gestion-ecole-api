package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class motif_sanction extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  motif_sanctionid ;
    private  String motif_sanctioncode;
    private  String motif_sanctionlibelle;

    public Long getMotif_sanctionid() {
        return motif_sanctionid;
    }

    public void setMotif_sanctionid(Long motif_sanctionid) {
        this.motif_sanctionid = motif_sanctionid;
    }

    public String getMotif_sanctioncode() {
        return motif_sanctioncode;
    }

    public void setMotif_sanctioncode(String motif_sanctioncode) {
        this.motif_sanctioncode = motif_sanctioncode;
    }

    public String getMotif_sanctionlibelle() {
        return motif_sanctionlibelle;
    }

    public void setMotif_sanctionlibelle(String motif_sanctionlibelle) {
        this.motif_sanctionlibelle = motif_sanctionlibelle;
    }
}
