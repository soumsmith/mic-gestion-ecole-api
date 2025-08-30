package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_objet")
public class type_objet extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  type_objetid ;
    private  String type_objetcode;
    private  String type_objetlibelle;

    public Long getType_objetid() {
        return type_objetid;
    }

    public void setType_objetid(Long type_objetid) {
        this.type_objetid = type_objetid;
    }

    public String getType_objetcode() {
        return type_objetcode;
    }

    public void setType_objetcode(String type_objetcode) {
        this.type_objetcode = type_objetcode;
    }

    public String getType_objetlibelle() {
        return type_objetlibelle;
    }

    public void setType_objetlibelle(String type_objetlibelle) {
        this.type_objetlibelle = type_objetlibelle;
    }
}
