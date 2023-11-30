package com.vieecoles.dto;

import java.util.List;

public class MoyenParProfDto implements Comparable<MoyenParProfDto> {
    String matricule ;
    String nomPrenom;
    String sexe;
    String moyenne ;
    String rang ;


    List<NoteDto> noteDtoList ;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(String moyenne) {
        this.moyenne = moyenne;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public List<NoteDto> getNoteDtoList() {
        return noteDtoList;
    }

    public void setNoteDtoList(List<NoteDto> noteDtoList) {
        this.noteDtoList = noteDtoList;
    }

    @Override
    public int compareTo(MoyenParProfDto o) {
        int result = this.nomPrenom.compareTo(o.nomPrenom);
        return result;
    }
}
