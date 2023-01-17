package com.vieecoles.dto;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ecoleDto2 {
    Long ecoleid ;
    String ecolecode ;
    String ecoleclibelle;

    public ecoleDto2() {
    }

    public ecoleDto2(Long ecoleid, String ecolecode, String ecoleclibelle) {
        this.ecoleid = ecoleid;
        this.ecolecode = ecolecode;
        this.ecoleclibelle = ecoleclibelle;
    }

    public Long getEcoleid() {
        return ecoleid;
    }

    public void setEcoleid(Long ecoleid) {
        this.ecoleid = ecoleid;
    }

    public String getEcolecode() {
        return ecolecode;
    }

    public void setEcolecode(String ecolecode) {
        this.ecolecode = ecolecode;
    }

    public String getEcoleclibelle() {
        return ecoleclibelle;
    }

    public void setEcoleclibelle(String ecoleclibelle) {
        this.ecoleclibelle = ecoleclibelle;
    }
}
