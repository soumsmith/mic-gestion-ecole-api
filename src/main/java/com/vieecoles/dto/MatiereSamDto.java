package com.vieecoles.dto;

public class MatiereSamDto {
    private String heureDebSam;
    private String heureFinSam;
    private String matiereSam;
    private String activiteSam ;

    public String getActiviteSam() {
        return activiteSam;
    }

    public void setActiviteSam(String activiteSam) {
        this.activiteSam = activiteSam;
    }

    public MatiereSamDto() {
    }

    @Override
    public String toString() {
        return "MatiereSamDto{" +
                "heureDebSam='" + heureDebSam + '\'' +
                ", heureFinSam='" + heureFinSam + '\'' +
                ", matiereSam='" + matiereSam + '\'' +
                '}';
    }

    public String getHeureDebSam() {
        return heureDebSam;
    }

    public void setHeureDebSam(String heureDebSam) {
        this.heureDebSam = heureDebSam;
    }

    public String getHeureFinSam() {
        return heureFinSam;
    }

    public void setHeureFinSam(String heureFinSam) {
        this.heureFinSam = heureFinSam;
    }

    public String getMatiereSam() {
        return matiereSam;
    }

    public void setMatiereSam(String matiereSam) {
        this.matiereSam = matiereSam;
    }
}
