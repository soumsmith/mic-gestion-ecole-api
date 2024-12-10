package com.vieecoles.dto;

public class NiveauClasseIdDto {
    String niveau;
    Integer id ;

    public NiveauClasseIdDto(String niveau, Integer id) {
        this.niveau = niveau;
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
