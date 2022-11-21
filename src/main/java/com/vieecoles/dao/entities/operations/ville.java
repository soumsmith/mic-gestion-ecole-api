package com.vieecoles.dao.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class ville extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  villeid ;
    private  String villecode;
    private  String villelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pays_paysid")
    private com.vieecoles.dao.entities.pays pays;

    public ville() {
    }

    public Long getVilleid() {
        return villeid;
    }

    public void setVilleid(Long villeid) {
        this.villeid = villeid;
    }

    public String getVillecode() {
        return villecode;
    }

    public void setVillecode(String villecode) {
        this.villecode = villecode;
    }

    public String getVillelibelle() {
        return villelibelle;
    }

    public void setVillelibelle(String villelibelle) {
        this.villelibelle = villelibelle;
    }

    public com.vieecoles.dao.entities.pays getPays() {
        return pays;
    }

    public void setPays(com.vieecoles.dao.entities.pays pays) {
        this.pays = pays;
    }
}
