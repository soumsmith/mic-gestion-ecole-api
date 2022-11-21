package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class typedurre extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  typedurreid ;
    private  String typedurrecode;
    private  String typedurrelibelle;

    public Long getTypedurreid() {
        return typedurreid;
    }

    public void setTypedurreid(Long typedurreid) {
        this.typedurreid = typedurreid;
    }

    public String getTypedurrecode() {
        return typedurrecode;
    }

    public void setTypedurrecode(String typedurrecode) {
        this.typedurrecode = typedurrecode;
    }

    public String getTypedurrelibelle() {
        return typedurrelibelle;
    }

    public void setTypedurrelibelle(String typedurrelibelle) {
        this.typedurrelibelle = typedurrelibelle;
    }
}
