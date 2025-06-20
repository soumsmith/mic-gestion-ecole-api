package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class utilisateur extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private  Long   utilisateurid ;
  private  String  utilisateur_mot_de_passe ;
 private  String   utilisateu_email ;
    private  String   utilisateu_login ;
 private  Long sous_attent_personn_sous_attent_personnid ;

    public String getUtilisateu_login() {
        return utilisateu_login;
    }

    public void setUtilisateu_login(String utilisateu_login) {
        this.utilisateu_login = utilisateu_login;
    }

    public utilisateur() {
    }

    public Long getSous_attent_personn_sous_attent_personnid() {
        return sous_attent_personn_sous_attent_personnid;
    }

    public void setSous_attent_personn_sous_attent_personnid(Long sous_attent_personn_sous_attent_personnid) {
        this.sous_attent_personn_sous_attent_personnid = sous_attent_personn_sous_attent_personnid;
    }

    public Long getUtilisateurid() {
        return utilisateurid;
    }

    public void setUtilisateurid(Long utilisateurid) {
        this.utilisateurid = utilisateurid;
    }

    public String getUtilisateur_mot_de_passe() {
        return utilisateur_mot_de_passe;
    }

    public void setUtilisateur_mot_de_passe(String utilisateur_mot_de_passe) {
        this.utilisateur_mot_de_passe = utilisateur_mot_de_passe;
    }

    public String getUtilisateu_email() {
        return utilisateu_email;
    }

    public void setUtilisateu_email(String utilisateu_email) {
        this.utilisateu_email = utilisateu_email;
    }

    @Override
    public String toString() {
        return "utilisateur{" +
                "utilisateurid=" + utilisateurid +
                ", utilisateur_mot_de_passe='" + utilisateur_mot_de_passe + '\'' +
                ", utilisateu_email='" + utilisateu_email + '\'' +
                ", sous_attent_personn_sous_attent_personnid=" + sous_attent_personn_sous_attent_personnid +
                '}';
    }


}
