package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
