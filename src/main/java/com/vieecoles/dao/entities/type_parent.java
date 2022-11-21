package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class type_parent extends PanacheEntityBase {
    @Id @GeneratedValue
  private  Long  type_parentid ;
  private  String  type_parentcode ;
 private  String   type_parentlibelle ;


    public type_parent() {
    }

    public Long getType_parentid() {
        return type_parentid;
    }

    public void setType_parentid(Long type_parentid) {
        this.type_parentid = type_parentid;
    }

    public String getType_parentcode() {
        return type_parentcode;
    }

    public void setType_parentcode(String type_parentcode) {
        this.type_parentcode = type_parentcode;
    }

    public String getType_parentlibelle() {
        return type_parentlibelle;
    }

    public void setType_parentlibelle(String type_parentlibelle) {
        this.type_parentlibelle = type_parentlibelle;
    }
}
