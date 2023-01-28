package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class commune extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  communeid ;
    private  String communecode;
    private  String communelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ville_villeid")
    private ville ville;

    public commune() {
    }

    public Long getCommuneid() {
        return communeid;
    }

    public void setCommuneid(Long communeid) {
        this.communeid = communeid;
    }

    public String getCommunecode() {
        return communecode;
    }

    public void setCommunecode(String communecode) {
        this.communecode = communecode;
    }

    public String getCommunelibelle() {
        return communelibelle;
    }

    public void setCommunelibelle(String communelibelle) {
        this.communelibelle = communelibelle;
    }

    public com.vieecoles.entities.operations.ville getVille() {
        return ville;
    }

    public void setVille(com.vieecoles.entities.operations.ville ville) {
        this.ville = ville;
    }
}
