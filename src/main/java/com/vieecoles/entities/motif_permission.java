package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "motif_permission")
public class motif_permission extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  motif_permissionid ;
    private  String motif_permissioncode;
    private  String motif_permissionlibelle;

    public Long getMotif_permissionid() {
        return motif_permissionid;
    }

    public void setMotif_permissionid(Long motif_permissionid) {
        this.motif_permissionid = motif_permissionid;
    }

    public String getMotif_permissioncode() {
        return motif_permissioncode;
    }

    public void setMotif_permissioncode(String motif_permissioncode) {
        this.motif_permissioncode = motif_permissioncode;
    }

    public String getMotif_permissionlibelle() {
        return motif_permissionlibelle;
    }

    public void setMotif_permissionlibelle(String motif_permissionlibelle) {
        this.motif_permissionlibelle = motif_permissionlibelle;
    }
}
