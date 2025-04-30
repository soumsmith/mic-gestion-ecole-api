package com.vieecoles.dto;

import java.util.List;

public class EffectifiConseilClasseDto {
    private String niveau ;
    private Long effeF;
    private Long effeG;
    private Long effEtabliF;
    private Long effEtabliG;

    public Long getEffEtabliF() {
        return effEtabliF;
    }

    public void setEffEtabliF(Long effEtabliF) {
        this.effEtabliF = effEtabliF;
    }

    public Long getEffEtabliG() {
        return effEtabliG;
    }

    public void setEffEtabliG(Long effEtabliG) {
        this.effEtabliG = effEtabliG;
    }

    public EffectifiConseilClasseDto() {
    }

    public EffectifiConseilClasseDto(List<EffectifiConseilClasseDto> resultatsElevesAffecteDtos) {
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Long getEffeF() {
        return effeF;
    }

    public void setEffeF(Long effeF) {
        this.effeF = effeF;
    }

    public Long getEffeG() {
        return effeG;
    }

    public void setEffeG(Long effeG) {
        this.effeG = effeG;
    }
}
