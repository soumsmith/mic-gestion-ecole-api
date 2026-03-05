package com.vieecoles.dto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ecoleDto3 {
    Long ecoleid ;
    String ecolecode ;
    String ecoleclibelle;
    String paramecole;

    public ecoleDto3() {
    }

    public ecoleDto3(Long ecoleid, String ecolecode, String ecoleclibelle, String paramecole) {
        this.ecoleid = ecoleid;
        this.ecolecode = ecolecode;
        this.ecoleclibelle = ecoleclibelle;
        this.paramecole = paramecole;
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

    public String getParamecole() {
        return paramecole;
    }

    public void setParamecole(String paramecole) {
        this.paramecole = paramecole;
    } 
}
