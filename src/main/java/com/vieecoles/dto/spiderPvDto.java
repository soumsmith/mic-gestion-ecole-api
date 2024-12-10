package com.vieecoles.dto;

import java.util.List;

public class spiderPvDto {
    private List<ConseilClasseDto> dspsDto ;
    private List<matriceClasseDto> matriceClasseDto ;
    private List<matiereMoyenneBilanDto> matiereMoyenneBilanDto ;

    public List<matriceClasseDto> getMatriceClasseDto() {
        return matriceClasseDto;
    }

    public void setMatriceClasseDto(
        List<matriceClasseDto> matriceClasseDto) {
        this.matriceClasseDto = matriceClasseDto;
    }

    public List<com.vieecoles.dto.matiereMoyenneBilanDto> getMatiereMoyenneBilanDto() {
        return matiereMoyenneBilanDto;
    }

    public void setMatiereMoyenneBilanDto(List<com.vieecoles.dto.matiereMoyenneBilanDto> matiereMoyenneBilanDto) {
        this.matiereMoyenneBilanDto = matiereMoyenneBilanDto;
    }

    public List<ConseilClasseDto> getDspsDto() {
        return dspsDto;
    }

    public void setDspsDto(List<ConseilClasseDto> dspsDto) {
        this.dspsDto = dspsDto;
    }
}
