package com.vieecoles.dto;

public class MatiereMardDto {
    private String heureDebMard;
    private String heureFinMard;
    private String matiereMard;
    private String activiteMard ;

    public String getActiviteMard() {
        return activiteMard;
    }

    public void setActiviteMard(String activiteMard) {
        this.activiteMard = activiteMard;
    }

    public MatiereMardDto() {
    }

    public String getHeureDebMard() {
        return heureDebMard;
    }

    public void setHeureDebMard(String heureDebMard) {
        this.heureDebMard = heureDebMard;
    }

    public String getHeureFinMard() {
        return heureFinMard;
    }

    public void setHeureFinMard(String heureFinMard) {
        this.heureFinMard = heureFinMard;
    }

    public String getMatiereMard() {
        return matiereMard;
    }

    public void setMatiereMard(String matiereMard) {
        this.matiereMard = matiereMard;
    }

    @Override
    public String toString() {
        return "MatiereMardDto{" +
                "heureDebMard='" + heureDebMard + '\'' +
                ", heureFinMard='" + heureFinMard + '\'' +
                ", matiereMard='" + matiereMard + '\'' +
                '}';
    }
}
