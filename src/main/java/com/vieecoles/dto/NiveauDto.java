package com.vieecoles.dto;

public class NiveauDto {
    String niveau;

    public NiveauDto(String niveau) {
        this.niveau = niveau;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "NiveauDto{" +
                "niveau='" + niveau + '\'' +
                '}';
    }
}
