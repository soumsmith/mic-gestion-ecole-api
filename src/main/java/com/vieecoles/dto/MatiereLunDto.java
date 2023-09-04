package com.vieecoles.dto;

public class MatiereLunDto {
    private String heureDebLun;
    private String heureFinLun;
    private String matiereLun;
    private String activiteLun;

    public String getActiviteLun() {
        return activiteLun;
    }

    public void setActiviteLun(String activiteLun) {
        this.activiteLun = activiteLun;
    }

    public MatiereLunDto() {
    }

    public String getHeureDebLun() {
        return heureDebLun;
    }

    public void setHeureDebLun(String heureDebLun) {
        this.heureDebLun = heureDebLun;
    }

    public String getHeureFinLun() {
        return heureFinLun;
    }

    public void setHeureFinLun(String heureFinLun) {
        this.heureFinLun = heureFinLun;
    }

    public String getMatiereLun() {
        return matiereLun;
    }

    public void setMatiereLun(String matiereLun) {
        this.matiereLun = matiereLun;
    }

    @Override
    public String toString() {
        return "MatiereLunDto{" +
                "heureDebLun='" + heureDebLun + '\'' +
                ", heureFinLun='" + heureFinLun + '\'' +
                ", matiereLun='" + matiereLun + '\'' +
                '}';
    }
}
