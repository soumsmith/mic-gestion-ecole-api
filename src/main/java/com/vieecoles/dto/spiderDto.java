package com.vieecoles.dto;

import java.util.List;

public class spiderDto {
    private List<IdentiteEtatDto> identiteEtatDto ;
    private List<ResultatsElevesAffecteDto> resultatsElevesAffecteDto ;

    public List<IdentiteEtatDto> getIdentiteEtatDto() {
        return identiteEtatDto;
    }

    public List<ResultatsElevesAffecteDto> getResultatsElevesAffecteDto() {
        return resultatsElevesAffecteDto;
    }
    public List<EmptyDto> intro ;

    public void setResultatsElevesAffecteDto(List<ResultatsElevesAffecteDto> resultatsElevesAffecteDto) {
        this.resultatsElevesAffecteDto = resultatsElevesAffecteDto;
    }

    public void setIdentiteEtatDto(List<IdentiteEtatDto> identiteEtatDto) {
        this.identiteEtatDto = identiteEtatDto;
    }

    public List<EmptyDto> getIntro() {
        return intro;
    }

    public void setIntro(List<EmptyDto> intro) {
        this.intro = intro;
    }
}
