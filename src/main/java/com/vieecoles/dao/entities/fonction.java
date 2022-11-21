package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class fonction extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  fonctionid ;
  //  fonctionid
    private  String fonctioncode;
    private  String fonctionlibelle;

    public Long getFonctionid() {
        return fonctionid;
    }

    public void setFonctionid(Long fonctioneid) {
        this.fonctionid = fonctioneid;
    }

    public String getFonctioncode() {
        return fonctioncode;
    }

    public void setFonctioncode(String fonctioncode) {
        this.fonctioncode = fonctioncode;
    }

    public String getFonctionlibelle() {
        return fonctionlibelle;
    }

    public void setFonctionlibelle(String fonctionlibelle) {
        this.fonctionlibelle = fonctionlibelle;
    }
}
