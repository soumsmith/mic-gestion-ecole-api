package com.vieecoles.dto;

public class MatiereVendDto {
    private String heureDebVend;
    private String heureFinVend;
    private String matiereVend;
    private String activiteVend ;

    public String getActiviteVend() {
        return activiteVend;
    }

    public void setActiviteVend(String activiteVend) {
        this.activiteVend = activiteVend;
    }

    public MatiereVendDto() {
    }

    @Override
    public String toString() {
        return "MatiereVendDto{" +
                "heureDebVend='" + heureDebVend + '\'' +
                ", heureFinVend='" + heureFinVend + '\'' +
                ", matiereVend='" + matiereVend + '\'' +
                '}';
    }

    public String getHeureDebVend() {
        return heureDebVend;
    }

    public void setHeureDebVend(String heureDebVend) {
        this.heureDebVend = heureDebVend;
    }

    public String getHeureFinVend() {
        return heureFinVend;
    }

    public void setHeureFinVend(String heureFinVend) {
        this.heureFinVend = heureFinVend;
    }

    public String getMatiereVend() {
        return matiereVend;
    }

    public void setMatiereVend(String matiereVend) {
        this.matiereVend = matiereVend;
    }
}
