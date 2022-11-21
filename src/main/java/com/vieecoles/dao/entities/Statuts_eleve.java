package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "statuts_eleve")
public class Statuts_eleve extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private  String    statuts_eleveid ;
  private  String   statuts_elevecode ;
  private  String   statuts_elevelibelle ;
    private  String tenant_tenantid ;

    public String getTenant_tenantid() {
        return tenant_tenantid;
    }

    public void setTenant_tenantid(String tenant_tenantid) {
        this.tenant_tenantid = tenant_tenantid;
    }

    public String getStatuts_eleveid() {
        return statuts_eleveid;
    }

    public void setStatuts_eleveid(String statuts_eleveid) {
        this.statuts_eleveid = statuts_eleveid;
    }

    public String getStatuts_elevecode() {
        return statuts_elevecode;
    }

    public void setStatuts_elevecode(String statuts_elevecode) {
        this.statuts_elevecode = statuts_elevecode;
    }

    public String getStatuts_elevelibelle() {
        return statuts_elevelibelle;
    }

    public void setStatuts_elevelibelle(String statuts_elevelibelle) {
        this.statuts_elevelibelle = statuts_elevelibelle;
    }
}
