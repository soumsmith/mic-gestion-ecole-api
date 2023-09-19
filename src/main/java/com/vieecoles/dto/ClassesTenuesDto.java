package com.vieecoles.dto;

public class ClassesTenuesDto {
    private String libelleClasse;
    private Long  idPerson ;
    private Long  idAnn ;

    public ClassesTenuesDto(String libelleClasse, Long idPerson, Long idAnn) {
        this.libelleClasse = libelleClasse;
        this.idPerson = idPerson;
        this.idAnn = idAnn;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdAnn() {
        return idAnn;
    }

    public void setIdAnn(Long idAnn) {
        this.idAnn = idAnn;
    }

    public String getLibelleClasse() {
        return libelleClasse;
    }

    public void setLibelleClasse(String libelleClasse) {
        this.libelleClasse = libelleClasse;
    }

    @Override
    public String toString() {
        return "ClassesTenuesDto{" +
                "libelleClasse='" + libelleClasse + '\'' +
                '}';
    }
}
