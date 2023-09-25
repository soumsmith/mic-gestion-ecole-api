package com.vieecoles.dto;


import java.time.LocalDate;


public class personnelConnexionDto {

    private Long  personnelid ;

    private  String personnelnom;
    private  String personnelprenom;
    private String profil ;

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public personnelConnexionDto(Long personnelid, String personnelnom, String personnelprenom , String profil) {
        this.personnelid = personnelid;
        this.personnelnom = personnelnom;
        this.personnelprenom = personnelprenom;
        this.profil =profil ;
    }

    public personnelConnexionDto() {
    }

    @Override
    public String toString() {
        return "personnelConnexionDto{" +
                "personnelid=" + personnelid +

                ", personnelnom='" + personnelnom + '\'' +
                ", personnelprenom='" + personnelprenom + '\'' +

                '}';
    }

    public Long getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(Long personnelid) {
        this.personnelid = personnelid;
    }



    public String getPersonnelnom() {
        return personnelnom;
    }

    public void setPersonnelnom(String personnelnom) {
        this.personnelnom = personnelnom;
    }

    public String getPersonnelprenom() {
        return personnelprenom;
    }

    public void setPersonnelprenom(String personnelprenom) {
        this.personnelprenom = personnelprenom;
    }


}
