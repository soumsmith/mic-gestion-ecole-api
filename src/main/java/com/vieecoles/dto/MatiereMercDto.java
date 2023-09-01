package com.vieecoles.dto;

public class MatiereMercDto {
    private String heureDebMerc;
    private String heureFinMerc;
    private String matiereMerc;
    private String activiteMerc ;

    public String getActiviteMerc() {
        return activiteMerc;
    }

    public void setActiviteMerc(String activiteMerc) {
        this.activiteMerc = activiteMerc;
    }

    public MatiereMercDto() {
    }

    @Override
    public String toString() {
        return "MatiereMercDto{" +
                "heureDebMerc='" + heureDebMerc + '\'' +
                ", heureFinMerc='" + heureFinMerc + '\'' +
                ", matiereMerc='" + matiereMerc + '\'' +
                '}';
    }

    public String getHeureDebMerc() {
        return heureDebMerc;
    }

    public void setHeureDebMerc(String heureDebMerc) {
        this.heureDebMerc = heureDebMerc;
    }

    public String getHeureFinMerc() {
        return heureFinMerc;
    }

    public void setHeureFinMerc(String heureFinMerc) {
        this.heureFinMerc = heureFinMerc;
    }

    public String getMatiereMerc() {
        return matiereMerc;
    }

    public void setMatiereMerc(String matiereMerc) {
        this.matiereMerc = matiereMerc;
    }
}
