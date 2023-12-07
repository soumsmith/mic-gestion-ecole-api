package com.vieecoles.dto;

import java.util.ArrayList;
import java.util.List;

public class spiderPartielDto {
    List<PyramideEffectDto> pyramideEffectDto1  ;
    List<PyramideEffectDto> pyramideEffectDto2  ;
    List<PyramideEffectDto> pyramideEffectDtoBilan  ;
    List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto ;

    public List<PyramideEffectDto> getPyramideEffectDto1() {
        return pyramideEffectDto1;
    }

    public void setPyramideEffectDto1(List<PyramideEffectDto> pyramideEffectDto1) {
        this.pyramideEffectDto1 = pyramideEffectDto1;
    }

    public List<PyramideEffectDto> getPyramideEffectDto2() {
        return pyramideEffectDto2;
    }

    public void setPyramideEffectDto2(List<PyramideEffectDto> pyramideEffectDto2) {
        this.pyramideEffectDto2 = pyramideEffectDto2;
    }

    public List<PyramideEffectDto> getPyramideEffectDtoBilan() {
        return pyramideEffectDtoBilan;
    }

    public void setPyramideEffectDtoBilan(List<PyramideEffectDto> pyramideEffectDtoBilan) {
        this.pyramideEffectDtoBilan = pyramideEffectDtoBilan;
    }

    public List<RepartitionEleveParAnNaissDto> getRepartitionEleveParAnNaissDto() {
        return repartitionEleveParAnNaissDto;
    }

    public void setRepartitionEleveParAnNaissDto(List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto) {
        this.repartitionEleveParAnNaissDto = repartitionEleveParAnNaissDto;
    }
}
