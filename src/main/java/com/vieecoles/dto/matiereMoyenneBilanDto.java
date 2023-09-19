package com.vieecoles.dto;

public class matiereMoyenneBilanDto {
    private String libelleMatiereBilan ;
    private Double moyMatiereBilan ;
    private String ecoleId ;

    public String getEcoleId() {
        return ecoleId;
    }

    public void setEcoleId(String ecoleId) {
        this.ecoleId = ecoleId;
    }

    public matiereMoyenneBilanDto() {
    }

    public String getLibelleMatiereBilan() {
        return libelleMatiereBilan;
    }

    public void setLibelleMatiereBilan(String libelleMatiereBilan) {
        this.libelleMatiereBilan = libelleMatiereBilan;
    }

    public Double getMoyMatiereBilan() {
        return moyMatiereBilan;
    }

    public void setMoyMatiereBilan(Double moyMatiereBilan) {
        this.moyMatiereBilan = moyMatiereBilan;
    }
}
