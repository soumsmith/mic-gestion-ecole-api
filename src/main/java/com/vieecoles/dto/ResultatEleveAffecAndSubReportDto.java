package com.vieecoles.dto;

public class ResultatEleveAffecAndSubReportDto {
    private String niveau ;
    private   ResultatsElevesAffecteDto resultatsElevesAffecteDto ;

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public ResultatsElevesAffecteDto getResultatsElevesAffecteDto() {
        return resultatsElevesAffecteDto;
    }

    public void setResultatsElevesAffecteDto(ResultatsElevesAffecteDto resultatsElevesAffecteDto) {
        this.resultatsElevesAffecteDto = resultatsElevesAffecteDto;
    }
}
