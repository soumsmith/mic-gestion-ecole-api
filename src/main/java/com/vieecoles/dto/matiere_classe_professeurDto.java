package com.vieecoles.dto;

public class matiere_classe_professeurDto {
    private  Long   matiereId ;
    private  Long   classeId ;
    private  Long   PersonnelId ;
    private Long    anneeScolaireId;
    private  String tenanid ;


    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public Long getPersonnelId() {
        return PersonnelId;
    }

    public void setPersonnelId(Long personnelId) {
        PersonnelId = personnelId;
    }

    public Long getAnneeScolaireId() {
        return anneeScolaireId;
    }

    public void setAnneeScolaireId(Long anneeScolaireId) {
        this.anneeScolaireId = anneeScolaireId;
    }

    public String getTenanid() {
        return tenanid;
    }

    public void setTenanid(String tenanid) {
        this.tenanid = tenanid;
    }

    @Override
    public String toString() {
        return "matiere_classe_professeurDto{" +
                "matiereId=" + matiereId +
                ", classeId=" + classeId +
                ", PersonnelId=" + PersonnelId +
                ", anneeScolaireId=" + anneeScolaireId +
                ", tenanid='" + tenanid + '\'' +
                '}';
    }
}
