package com.vieecoles.dao.entities;

import com.vieecoles.dao.entities.operations.quartier;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "utilisateur_has_personnel")
public class utilisateur_has_personnel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   utilisateur_has_personnelid ;
    private  Long   personnel_personnelid ;
    private LocalDate utilisateur_has_person_date_creation ;
    private LocalDate utilisateur_has_person_modif ;
    private  int utilisateur_has_person_active ;
    private LocalDate utilisateur_has_person_date_debut ;
    private  LocalDate utilisateur_has_person_date_fin ;
    private Long  ecole_ecoleid ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profil_profilid")
    private com.vieecoles.dao.entities.profil  profil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_utilisateurid")
    private com.vieecoles.dao.entities.utilisateur  utilisateur;

    public utilisateur_has_personnel() {
    }

    public Long getUtilisateur_has_personnelid() {
        return utilisateur_has_personnelid;
    }

    public void setUtilisateur_has_personnelid(Long utilisateur_has_personnelid) {
        this.utilisateur_has_personnelid = utilisateur_has_personnelid;
    }

    public Long getPersonnel_personnelid() {
        return personnel_personnelid;
    }

    public void setPersonnel_personnelid(Long personnel_personnelid) {
        this.personnel_personnelid = personnel_personnelid;
    }

    public Long getEcole_ecoleid() {
        return ecole_ecoleid;
    }

    public void setEcole_ecoleid(Long ecole_ecoleid) {
        this.ecole_ecoleid = ecole_ecoleid;
    }

    public LocalDate getUtilisateur_has_person_date_creation() {
        return utilisateur_has_person_date_creation;
    }

    public void setUtilisateur_has_person_date_creation(LocalDate utilisateur_has_person_date_creation) {
        this.utilisateur_has_person_date_creation = utilisateur_has_person_date_creation;
    }

    public LocalDate getUtilisateur_has_person_modif() {
        return utilisateur_has_person_modif;
    }

    public void setUtilisateur_has_person_modif(LocalDate utilisateur_has_person_modif) {
        this.utilisateur_has_person_modif = utilisateur_has_person_modif;
    }

    public int getUtilisateur_has_person_active() {
        return utilisateur_has_person_active;
    }

    public void setUtilisateur_has_person_active(int utilisateur_has_person_active) {
        this.utilisateur_has_person_active = utilisateur_has_person_active;
    }

    public com.vieecoles.dao.entities.profil getProfil() {
        return profil;
    }

    public void setProfil(com.vieecoles.dao.entities.profil profil) {
        this.profil = profil;
    }

    public com.vieecoles.dao.entities.utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(com.vieecoles.dao.entities.utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDate getUtilisateur_has_person_date_debut() {
        return utilisateur_has_person_date_debut;
    }

    public void setUtilisateur_has_person_date_debut(LocalDate utilisateur_has_person_date_debut) {
        this.utilisateur_has_person_date_debut = utilisateur_has_person_date_debut;
    }

    public LocalDate getUtilisateur_has_person_date_fin() {
        return utilisateur_has_person_date_fin;
    }

    public void setUtilisateur_has_person_date_fin(LocalDate utilisateur_has_person_date_fin) {
        this.utilisateur_has_person_date_fin = utilisateur_has_person_date_fin;
    }
}
