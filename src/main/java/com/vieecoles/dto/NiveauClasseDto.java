package com.vieecoles.dto;

public class NiveauClasseDto {
    String niveau;
    Long id ;

    public NiveauClasseDto(String niveau, Long id) {
        this.niveau = niveau;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "NiveauClasseDto{" +
                "niveau='" + niveau + '\'' +
                ", id=" + id +
                '}';
    }
}
