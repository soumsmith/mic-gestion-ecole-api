package com.vieecoles.dto;

public class NoteDto {
    String noteEval ;
    String infosEval;
    String matricule ;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNoteEval() {
        return noteEval;
    }

    public void setNoteEval(String noteEval) {
        this.noteEval = noteEval;
    }

    public String getInfosEval() {
        return infosEval;
    }

    public void setInfosEval(String infosEval) {
        this.infosEval = infosEval;
    }
}
