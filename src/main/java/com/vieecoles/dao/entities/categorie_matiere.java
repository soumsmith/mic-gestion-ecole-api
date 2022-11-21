package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class categorie_matiere extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  categorie_matiereid ;
    private  String categorie_matierecode;
    private  String categorie_matierelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private com.vieecoles.dao.entities.tenant tenant ;


    public Long getCategorie_matiereid() {
        return categorie_matiereid;
    }

    public void setCategorie_matiereid(Long categorie_matiereid) {
        this.categorie_matiereid = categorie_matiereid;
    }

    public String getCategorie_matierecode() {
        return categorie_matierecode;
    }

    public void setCategorie_matierecode(String categorie_matierecode) {
        this.categorie_matierecode = categorie_matierecode;
    }

    public String getCategorie_matierelibelle() {
        return categorie_matierelibelle;
    }

    public void setCategorie_matierelibelle(String categorie_matierelibelle) {
        this.categorie_matierelibelle = categorie_matierelibelle;
    }

    public com.vieecoles.dao.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.dao.entities.tenant tenant) {
        this.tenant = tenant;
    }
}
