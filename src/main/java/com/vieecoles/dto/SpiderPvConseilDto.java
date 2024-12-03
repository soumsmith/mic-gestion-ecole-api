package com.vieecoles.dto;

import java.util.List;

public class SpiderPvConseilDto {
    private List<ProcesVerbalListeClasseDto>  procesVerbalListeClasseDto ;
    private List<ProcesVerbalListeClasseDto>  listeMajors ;
    private List<ProcesVerbalStatistiqueDisciplineDto> procesVerbalStatistiqueDisciplineDto ;
    private List<ProcesVerbalMatiereSpecifiqueDto> procesVerbalMatiereSpecifiqueDto ;
    private List<ProcesVerbalStatistiqueClasseDto>procesVerbalStatistiqueClasseDto;

    public List<ProcesVerbalStatistiqueClasseDto> getProcesVerbalStatistiqueClasseDto() {
        return procesVerbalStatistiqueClasseDto;
    }

    public void setProcesVerbalStatistiqueClasseDto(
        List<ProcesVerbalStatistiqueClasseDto> procesVerbalStatistiqueClasseDto) {
        this.procesVerbalStatistiqueClasseDto = procesVerbalStatistiqueClasseDto;
    }

    public List<ProcesVerbalListeClasseDto> getListeMajors() {
        return listeMajors;
    }

    public void setListeMajors(List<ProcesVerbalListeClasseDto> listeMajors) {
        this.listeMajors = listeMajors;
    }

    public void setProcesVerbalMatiereSpecifiqueDto(
        List<ProcesVerbalMatiereSpecifiqueDto> procesVerbalMatiereSpecifiqueDto) {
        this.procesVerbalMatiereSpecifiqueDto = procesVerbalMatiereSpecifiqueDto;
    }

    public List<ProcesVerbalListeClasseDto> getProcesVerbalListeClasseDto() {
        return procesVerbalListeClasseDto;
    }

    public void setProcesVerbalListeClasseDto(
        List<ProcesVerbalListeClasseDto> procesVerbalListeClasseDto) {
        this.procesVerbalListeClasseDto = procesVerbalListeClasseDto;
    }

    public List<ProcesVerbalStatistiqueDisciplineDto> getProcesVerbalStatistiqueDisciplineDto() {
        return procesVerbalStatistiqueDisciplineDto;
    }

    public void setProcesVerbalStatistiqueDisciplineDto(
        List<ProcesVerbalStatistiqueDisciplineDto> procesVerbalStatistiqueDisciplineDto) {
        this.procesVerbalStatistiqueDisciplineDto = procesVerbalStatistiqueDisciplineDto;
    }

    public List<ProcesVerbalMatiereSpecifiqueDto> getProcesVerbalMatiereSpecifiqueDto() {
        return procesVerbalMatiereSpecifiqueDto;
    }
}
