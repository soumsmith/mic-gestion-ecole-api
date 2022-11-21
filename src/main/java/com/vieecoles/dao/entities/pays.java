package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class pays extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  paysid ;
    private  String payscode;
    private  String payslibelle;


    public Long getPaysid() {
        return paysid;
    }

    public void setPaysid(Long paysid) {
        this.paysid = paysid;
    }

    public String getPayscode() {
        return payscode;
    }

    public void setPayscode(String payscode) {
        this.payscode = payscode;
    }

    public String getPayslibelle() {
        return payslibelle;
    }

    public void setPayslibelle(String payslibelle) {
        this.payslibelle = payslibelle;
    }
}
