package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class type_evaluation extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  type_evaluationid ;
    private  String type_evaluationcode;
    private  String type_evaluationlibelle;


    public Long getType_evaluationid() {
        return type_evaluationid;
    }

    public void setType_evaluationid(Long type_evaluationid) {
        this.type_evaluationid = type_evaluationid;
    }

    public String getType_evaluationcode() {
        return type_evaluationcode;
    }

    public void setType_evaluationcode(String type_evaluationcode) {
        this.type_evaluationcode = type_evaluationcode;
    }

    public String getType_evaluationlibelle() {
        return type_evaluationlibelle;
    }

    public void setType_evaluationlibelle(String type_evaluationlibelle) {
        this.type_evaluationlibelle = type_evaluationlibelle;
    }
}
