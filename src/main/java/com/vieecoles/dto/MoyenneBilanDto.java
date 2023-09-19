package com.vieecoles.dto;

import java.util.List;

public class MoyenneBilanDto {
   private Long idEcole ;
   private List<matiereMoyenneBilanDto> matiereMoyenneBilanDto ;

    public Long getIdEcole() {
        return idEcole;
    }

    public void setIdEcole(Long idEcole) {
        this.idEcole = idEcole;
    }

    public List<com.vieecoles.dto.matiereMoyenneBilanDto> getMatiereMoyenneBilanDto() {
        return matiereMoyenneBilanDto;
    }

    public void setMatiereMoyenneBilanDto(List<com.vieecoles.dto.matiereMoyenneBilanDto> matiereMoyenneBilanDto) {
        this.matiereMoyenneBilanDto = matiereMoyenneBilanDto;
    }
}
