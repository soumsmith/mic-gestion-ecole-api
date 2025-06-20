package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "domaine")
public class domaine extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  domaineid ;
    private  String domainecode;
    private  String domainelibelle;


    public Long getDomaineid() {
        return domaineid;
    }

    public void setDomaineid(Long domaineid) {
        this.domaineid = domaineid;
    }

    public String getDomainecode() {
        return domainecode;
    }

    public void setDomainecode(String domainecode) {
        this.domainecode = domainecode;
    }

    public String getDomainelibelle() {
        return domainelibelle;
    }

    public void setDomainelibelle(String domainelibelle) {
        this.domainelibelle = domainelibelle;
    }
}
