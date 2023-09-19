package com.vieecoles.dto;

import java.util.List;

public class SpiderMatriceClasseDto {
    private List<matiereMoyenneBilanDto>  matiereMoyenneBilanDto ;
    private List<matriceClasseDto> matriceClasseDto ;

    public List<com.vieecoles.dto.matiereMoyenneBilanDto> getMatiereMoyenneBilanDto() {
        return matiereMoyenneBilanDto;
    }

    public void setMatiereMoyenneBilanDto(List<com.vieecoles.dto.matiereMoyenneBilanDto> matiereMoyenneBilanDto) {
        this.matiereMoyenneBilanDto = matiereMoyenneBilanDto;
    }

    public List<com.vieecoles.dto.matriceClasseDto> getMatriceClasseDto() {
        return matriceClasseDto;
    }

    public void setMatriceClasseDto(List<com.vieecoles.dto.matriceClasseDto> matriceClasseDto) {
        this.matriceClasseDto = matriceClasseDto;
    }
}
