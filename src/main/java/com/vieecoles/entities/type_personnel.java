package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_personnel")
public class type_personnel extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  type_personnelid ;
    private  String type_personnelcode;
    private  String type_personnellibelle;

    public Long getType_personnelid() {
        return type_personnelid;
    }

    public void setType_personnelid(Long type_personnelid) {
        this.type_personnelid = type_personnelid;
    }

    public String getType_personnelcode() {
        return type_personnelcode;
    }

    public void setType_personnelcode(String type_personnelcode) {
        this.type_personnelcode = type_personnelcode;
    }

    public String getType_personnellibelle() {
        return type_personnellibelle;
    }

    public void setType_personnellibelle(String type_personnellibelle) {
        this.type_personnellibelle = type_personnellibelle;
    }


}
