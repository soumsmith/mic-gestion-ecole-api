package com.vieecoles.dto;

public class MatiereJeudDto {
    private String heureDebJeud;
    private String heureFinJeud;
    private String matiereJeud;
    private String activiteJeud ;

    public String getActiviteJeud() {
        return activiteJeud;
    }

    public void setActiviteJeud(String activiteJeud) {
        this.activiteJeud = activiteJeud;
    }

    public MatiereJeudDto() {
    }

    @Override
    public String toString() {
        return "MatiereJeudDto{" +
                "heureDebJeud='" + heureDebJeud + '\'' +
                ", heureFinJeud='" + heureFinJeud + '\'' +
                ", matiereJeud='" + matiereJeud + '\'' +
                '}';
    }

    public String getHeureDebJeud() {
        return heureDebJeud;
    }

    public void setHeureDebJeud(String heureDebJeud) {
        this.heureDebJeud = heureDebJeud;
    }

    public String getHeureFinJeud() {
        return heureFinJeud;
    }

    public void setHeureFinJeud(String heureFinJeud) {
        this.heureFinJeud = heureFinJeud;
    }

    public String getMatiereJeud() {
        return matiereJeud;
    }

    public void setMatiereJeud(String matiereJeud) {
        this.matiereJeud = matiereJeud;
    }
}
