package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class cycle extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  cycleid ;
    private  String cyclecode;
    private  String cyclelibelle;

    public Long getCycleid() {
        return cycleid;
    }

    public void setCycleid(Long cycleid) {
        this.cycleid = cycleid;
    }

    public String getCyclecode() {
        return cyclecode;
    }

    public void setCyclecode(String cyclecode) {
        this.cyclecode = cyclecode;
    }

    public String getCyclelibelle() {
        return cyclelibelle;
    }

    public void setCyclelibelle(String cyclelibelle) {
        this.cyclelibelle = cyclelibelle;
    }
}