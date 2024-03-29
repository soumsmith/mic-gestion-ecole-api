package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class personnel_status extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  personnel_statusid ;
    private  String personnel_statuscode;
    private  String personnel_statulibelle;


    public Long getPersonnel_statusid() {
        return personnel_statusid;
    }

    public void setPersonnel_statusid(Long personnel_statusid) {
        this.personnel_statusid = personnel_statusid;
    }

    public String getPersonnel_statuscode() {
        return personnel_statuscode;
    }

    public void setPersonnel_statuscode(String personnel_statuscode) {
        this.personnel_statuscode = personnel_statuscode;
    }

    public String getPersonnel_statulibelle() {
        return personnel_statulibelle;
    }

    public void setPersonnel_statulibelle(String personnel_statulibelle) {
        this.personnel_statulibelle = personnel_statulibelle;
    }
}
