package com.vieecoles.dto;

public class DateNaissNiveauDto {
    private String dateNaiss ;
    private String niveau ;

    public DateNaissNiveauDto(String dateNaiss, String niveau) {
        this.dateNaiss = dateNaiss;
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "DateNaissNiveauDto{" +
                "dateNaiss='" + dateNaiss + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
