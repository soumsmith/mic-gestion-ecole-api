package com.vieecoles.dto;

import java.util.List;

public class SpiderRapportRentreeDto {
    private List<RapportRentreeDto>  rapportRentreeDto ;
    private List<PyramideEffectDto> pyramideEffectDto1 ;
    private List<PyramideEffectDto> pyramideEffectDto2 ;
    private List<PyramideEffectDto> pyramideEffectDtoBilan ;
    private List<EtatNominatifEnseignatDto> etatNominatifEnseignatDto ;
    private List<ComparatifDto> comparatifDto ;
    List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto ;
    public List<ComparatifDto> getComparatifDto() {
        return comparatifDto;
    }

    public void setComparatifDto(List<ComparatifDto> comparatifDto) {
        this.comparatifDto = comparatifDto;
    }

    public List<EtatNominatifEnseignatDto> getEtatNominatifEnseignatDto() {
        return etatNominatifEnseignatDto;
    }

    public void setEtatNominatifEnseignatDto(List<EtatNominatifEnseignatDto> etatNominatifEnseignatDto) {
        this.etatNominatifEnseignatDto = etatNominatifEnseignatDto;
    }

    private List<EffectifElevLangueVivante2Dto> effectifElevLangueVivante2Dto ;

    public List<PyramideEffectDto> getPyramideEffectDto1() {
        return pyramideEffectDto1;
    }

    public void setPyramideEffectDto1(List<PyramideEffectDto> pyramideEffectDto1) {
        this.pyramideEffectDto1 = pyramideEffectDto1;
    }

    public List<RepartitionEleveParAnNaissDto> getRepartitionEleveParAnNaissDto() {
        return repartitionEleveParAnNaissDto;
    }

    public void setRepartitionEleveParAnNaissDto(List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto) {
        this.repartitionEleveParAnNaissDto = repartitionEleveParAnNaissDto;
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

    public List<RapportRentreeDto> getRapportRentreeDto() {
        return rapportRentreeDto;
    }

    public void setRapportRentreeDto(List<RapportRentreeDto> rapportRentreeDto) {
        this.rapportRentreeDto = rapportRentreeDto;
    }



    public List<EffectifElevLangueVivante2Dto> getEffectifElevLangueVivante2Dto() {
        return effectifElevLangueVivante2Dto;
    }

    public void setEffectifElevLangueVivante2Dto(List<EffectifElevLangueVivante2Dto> effectifElevLangueVivante2Dto) {
        this.effectifElevLangueVivante2Dto = effectifElevLangueVivante2Dto;
    }
}
