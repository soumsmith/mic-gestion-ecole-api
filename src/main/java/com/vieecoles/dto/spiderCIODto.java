package com.vieecoles.dto;

import java.util.ArrayList;
import java.util.List;

public class spiderCIODto {
    private List<RecapResultatsElevesAffeEtNonAffDto> dspsDto ;

   private  List<MoenneParDisciplineDto>  moyenneParDisc = new ArrayList<> () ;

    public List<MoenneParDisciplineDto> getMoyenneParDisc() {
        return moyenneParDisc;
    }

    public void setMoyenneParDisc(List<MoenneParDisciplineDto> moyenneParDisc) {
        this.moyenneParDisc = moyenneParDisc;
    }

    public List<RecapResultatsElevesAffeEtNonAffDto> getDspsDto() {
        return dspsDto;
    }

    public void setDspsDto(List<RecapResultatsElevesAffeEtNonAffDto> dspsDto) {
        this.dspsDto = dspsDto;
    }
}
