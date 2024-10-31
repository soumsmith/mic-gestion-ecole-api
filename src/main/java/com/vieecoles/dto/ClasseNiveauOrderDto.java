package com.vieecoles.dto;

public class ClasseNiveauOrderDto {
    private String classe ;
    private String Niveau;
    private Integer orderNiveau ;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public Integer getOrderNiveau() {
        return orderNiveau;
    }

    public void setOrderNiveau(Integer orderNiveau) {
        this.orderNiveau = orderNiveau;
    }

    public ClasseNiveauOrderDto() {
    }

    public ClasseNiveauOrderDto(String classe, String niveau, Integer orderNiveau) {
        this.classe = classe;
        Niveau = niveau;
        this.orderNiveau = orderNiveau;
    }

    @Override
    public String toString() {
        return "classeNiveauDto{" +
                "classe='" + classe + '\'' +
                ", Niveau='" + Niveau + '\'' +
                '}';
    }
}
