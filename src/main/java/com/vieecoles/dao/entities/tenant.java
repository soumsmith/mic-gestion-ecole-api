package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class tenant extends PanacheEntityBase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  tenantid ;
    private  String tenantcode;
    private  String tenantlibelle;


    @Override
    public String toString() {
        return "tenant{" +
                "tenantid='" + tenantid + '\'' +
                ", tenantcode='" + tenantcode + '\'' +
                ", tenantlibelle='" + tenantlibelle + '\'' +
                '}';
    }

    public tenant() {
    }

    public String getTenantid() {
        return tenantid;
    }

    public void setTenantid(String tenantid) {
        this.tenantid = tenantid;
    }

    public String getTenantcode() {
        return tenantcode;
    }

    public void setTenantcode(String tenantcode) {
        this.tenantcode = tenantcode;
    }

    public String getTenantlibelle() {
        return tenantlibelle;
    }

    public void setTenantlibelle(String tenantlibelle) {
        this.tenantlibelle = tenantlibelle;
    }
}
