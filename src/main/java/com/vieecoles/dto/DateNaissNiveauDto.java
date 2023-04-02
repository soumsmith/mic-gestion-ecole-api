package com.vieecoles.dto;

public class DateNaissNiveauDto {
    private String dateNaiss ;


    public DateNaissNiveauDto(String dateNaiss) {
        this.dateNaiss = dateNaiss;

    }

    @Override
    public String toString() {
        return "DateNaissNiveauDto{" +
                "dateNaiss='" + dateNaiss + '\'' +
                '}';
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }


}
