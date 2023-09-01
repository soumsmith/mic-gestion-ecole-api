package com.vieecoles.dto;

public class ClasseMatiereDto {
    private  Long id ;
    private String libelle ;

    public ClasseMatiereDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "ClasseMatiereDto{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
