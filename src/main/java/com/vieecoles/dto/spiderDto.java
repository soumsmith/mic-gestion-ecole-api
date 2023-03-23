package com.vieecoles.dto;

import java.util.List;

public class spiderDto {
    private List<IdentiteEtatDto> identiteEtatDto ;
    private List<ResultatsElevesAffecteDto> resultatsElevesAffecteDto ;
    private List<RecapDesResultatsElevesAffecteDto> recapDesResultatsElevesAffecteDto ;
    private List<ResultatsElevesNonAffecteDto> resultatsElevesNonAffecteDto ;
    private List<RecapDesResultatsElevesNonAffecteDto> recapDesResultatsElevesNonAffecteDto ;
   private List<RecapResultatsElevesAffeEtNonAffDto> recapResultatsElevesAffeEtNonAffDto ;
   private List<eleveAffecteParClasseDto> eleveAffecteParClasseDto ;
   private  List<eleveNonAffecteParClasseDto> eleveNonAffecteParClasseDto ;
   private  List<MajorParClasseNiveauDto> majorParClasseNiveauDto ;

    public List<RecapDesResultatsElevesAffecteDto> getRecapDesResultatsElevesAffecteDto() {
        return recapDesResultatsElevesAffecteDto;
    }

    public void setRecapDesResultatsElevesAffecteDto(List<RecapDesResultatsElevesAffecteDto> recapDesResultatsElevesAffecteDto) {
        this.recapDesResultatsElevesAffecteDto = recapDesResultatsElevesAffecteDto;
    }

    public List<ResultatsElevesNonAffecteDto> getResultatsElevesNonAffecteDto() {
        return resultatsElevesNonAffecteDto;
    }

    public void setResultatsElevesNonAffecteDto(List<ResultatsElevesNonAffecteDto> resultatsElevesNonAffecteDto) {
        this.resultatsElevesNonAffecteDto = resultatsElevesNonAffecteDto;
    }

    public List<RecapDesResultatsElevesNonAffecteDto> getRecapDesResultatsElevesNonAffecteDto() {
        return recapDesResultatsElevesNonAffecteDto;
    }

    public void setRecapDesResultatsElevesNonAffecteDto(List<RecapDesResultatsElevesNonAffecteDto> recapDesResultatsElevesNonAffecteDto) {
        this.recapDesResultatsElevesNonAffecteDto = recapDesResultatsElevesNonAffecteDto;
    }

    public List<RecapResultatsElevesAffeEtNonAffDto> getRecapResultatsElevesAffeEtNonAffDto() {
        return recapResultatsElevesAffeEtNonAffDto;
    }

    public void setRecapResultatsElevesAffeEtNonAffDto(List<RecapResultatsElevesAffeEtNonAffDto> recapResultatsElevesAffeEtNonAffDto) {
        this.recapResultatsElevesAffeEtNonAffDto = recapResultatsElevesAffeEtNonAffDto;
    }

    public List<com.vieecoles.dto.eleveAffecteParClasseDto> getEleveAffecteParClasseDto() {
        return eleveAffecteParClasseDto;
    }

    public void setEleveAffecteParClasseDto(List<com.vieecoles.dto.eleveAffecteParClasseDto> eleveAffecteParClasseDto) {
        this.eleveAffecteParClasseDto = eleveAffecteParClasseDto;
    }

    public List<com.vieecoles.dto.eleveNonAffecteParClasseDto> getEleveNonAffecteParClasseDto() {
        return eleveNonAffecteParClasseDto;
    }

    public void setEleveNonAffecteParClasseDto(List<com.vieecoles.dto.eleveNonAffecteParClasseDto> eleveNonAffecteParClasseDto) {
        this.eleveNonAffecteParClasseDto = eleveNonAffecteParClasseDto;
    }

    public List<MajorParClasseNiveauDto> getMajorParClasseNiveauDto() {
        return majorParClasseNiveauDto;
    }

    public void setMajorParClasseNiveauDto(List<MajorParClasseNiveauDto> majorParClasseNiveauDto) {
        this.majorParClasseNiveauDto = majorParClasseNiveauDto;
    }

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
