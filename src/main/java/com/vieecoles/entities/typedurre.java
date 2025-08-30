package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "typedurre")
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
