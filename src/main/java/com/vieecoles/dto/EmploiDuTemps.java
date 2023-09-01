package com.vieecoles.dto;

import java.util.List;

public class EmploiDuTemps {
    private List<MatiereLunDto> matiereLunDtos;
    private List<MatiereMardDto> matiereMardDtos ;
    private  List<MatiereMercDto> matiereMercDtos ;
    private List<MatiereJeudDto> matiereJeudDtos ;
    private List<MatiereVendDto> matiereVendDtos ;
    private  List<MatiereSamDto> matiereSamDtos ;
    private List<MatProf> matProfs ;

    public List<MatProf> getMatProfs() {
        return matProfs;
    }

    public void setMatProfs(List<MatProf> matProfs) {
        this.matProfs = matProfs;
    }

    public EmploiDuTemps() {
    }

    public List<MatiereLunDto> getMatiereLunDtos() {
        return matiereLunDtos;
    }

    public void setMatiereLunDtos(List<MatiereLunDto> matiereLunDtos) {
        this.matiereLunDtos = matiereLunDtos;
    }

    public List<MatiereMardDto> getMatiereMardDtos() {
        return matiereMardDtos;
    }

    public void setMatiereMardDtos(List<MatiereMardDto> matiereMardDtos) {
        this.matiereMardDtos = matiereMardDtos;
    }

    public List<MatiereMercDto> getMatiereMercDtos() {
        return matiereMercDtos;
    }

    public void setMatiereMercDtos(List<MatiereMercDto> matiereMercDtos) {
        this.matiereMercDtos = matiereMercDtos;
    }

    public List<MatiereJeudDto> getMatiereJeudDtos() {
        return matiereJeudDtos;
    }

    public void setMatiereJeudDtos(List<MatiereJeudDto> matiereJeudDtos) {
        this.matiereJeudDtos = matiereJeudDtos;
    }

    public List<MatiereVendDto> getMatiereVendDtos() {
        return matiereVendDtos;
    }

    public void setMatiereVendDtos(List<MatiereVendDto> matiereVendDtos) {
        this.matiereVendDtos = matiereVendDtos;
    }

    public List<MatiereSamDto> getMatiereSamDtos() {
        return matiereSamDtos;
    }

    public void setMatiereSamDtos(List<MatiereSamDto> matiereSamDtos) {
        this.matiereSamDtos = matiereSamDtos;
    }
}
