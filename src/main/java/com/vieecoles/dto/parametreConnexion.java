package com.vieecoles.dto;

public class parametreConnexion {
    String login ;
    String motDepass ;

    public parametreConnexion() {
    }

    public parametreConnexion(String login, String motDepass) {
        this.login = login;
        this.motDepass = motDepass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDepass() {
        return motDepass;
    }

    public void setMotDepass(String motDepass) {
        this.motDepass = motDepass;
    }
}
