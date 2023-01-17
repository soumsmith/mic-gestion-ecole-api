package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.*;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class personnel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  personnelid ;
   private  String personnelcode;
    private  String personnelnom;
    private  String personnelprenom;
    private LocalDate personneldatenaissance;
    private  String personnel_lieunaissance;

    private  String personnel_emprunte ;
    private  String  personnel_contact ;
   private  Long personnel_status_personnel_statusid;
   private  Long type_personnel_type_personnelid ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecole_ecoleid")
    private com.vieecoles.dao.entities.operations.ecole ecole ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_etude_niveau_etudeid")
    private com.vieecoles.dao.entities.niveau_etude niveau_etude ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domaine_formation_domaine_formationid")
    private com.vieecoles.dao.entities.domaine_formation domaine_formation_domaine_formationid ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_attent_personn_sous_attent_personnid")
    private com.vieecoles.dao.entities.operations.sous_attent_personn sous_attent_personn ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fonction_fonctionid")
    private com.vieecoles.dao.entities.fonction fonction;


    @Transient
    @Enumerated(EnumType.STRING)
    private Civilite civilite;
    public com.vieecoles.dao.entities.fonction getFonction() {
        return fonction;
    }

    public void setFonction(com.vieecoles.dao.entities.fonction fonction) {
        this.fonction = fonction;
    }

    public com.vieecoles.dao.entities.niveau_etude getNiveau_etude() {
        return niveau_etude;
    }

    public void setNiveau_etude(com.vieecoles.dao.entities.niveau_etude niveau_etude) {
        this.niveau_etude = niveau_etude;
    }

    public domaine_formation getDomaine_formation_domaine_formationid() {
        return domaine_formation_domaine_formationid;
    }

    public void setDomaine_formation_domaine_formationid(domaine_formation domaine_formation_domaine_formationid) {
        this.domaine_formation_domaine_formationid = domaine_formation_domaine_formationid;
    }

    public Long getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(Long personnelid) {
        this.personnelid = personnelid;
    }

    public String getPersonnelcode() {
        return personnelcode;
    }

    public void setPersonnelcode(String personnelcode) {
        this.personnelcode = personnelcode;
    }

    public String getPersonnelnom() {
        return personnelnom;
    }

    public Long getPersonnel_status_personnel_statusid() {
        return personnel_status_personnel_statusid;
    }

    public void setPersonnel_status_personnel_statusid(Long personnel_status_personnel_statusid) {
        this.personnel_status_personnel_statusid = personnel_status_personnel_statusid;
    }

    public Long getType_personnel_type_personnelid() {
        return type_personnel_type_personnelid;
    }

    public void setType_personnel_type_personnelid(Long type_personnel_type_personnelid) {
        this.type_personnel_type_personnelid = type_personnel_type_personnelid;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public void setPersonnelnom(String personnelnom) {
        this.personnelnom = personnelnom;
    }

    public String getPersonnelprenom() {
        return personnelprenom;
    }

    public void setPersonnelprenom(String personnelprenom) {
        this.personnelprenom = personnelprenom;
    }

    public LocalDate getPersonneldatenaissance() {
        return personneldatenaissance;
    }

    public void setPersonneldatenaissance(LocalDate personneldatenaissance) {
        this.personneldatenaissance = personneldatenaissance;
    }

    public String getPersonnel_lieunaissance() {
        return personnel_lieunaissance;
    }

    public void setPersonnel_lieunaissance(String personnel_lieunaissance) {
        this.personnel_lieunaissance = personnel_lieunaissance;
    }

    public com.vieecoles.dao.entities.operations.ecole getEcole() {
        return ecole;
    }

    public void setEcole(com.vieecoles.dao.entities.operations.ecole ecole) {
        this.ecole = ecole;
    }

    public com.vieecoles.dao.entities.operations.sous_attent_personn getSous_attent_personn() {
        return sous_attent_personn;
    }

    public void setSous_attent_personn(com.vieecoles.dao.entities.operations.sous_attent_personn sous_attent_personn) {
        this.sous_attent_personn = sous_attent_personn;
    }

    public String getPersonnel_emprunte() {
        return personnel_emprunte;
    }

    public void setPersonnel_emprunte(String personnel_emprunte) {
        this.personnel_emprunte = personnel_emprunte;
    }

    public String getPersonnel_contact() {
        return personnel_contact;
    }

    public void setPersonnel_contact(String personnel_contact) {
        this.personnel_contact = personnel_contact;
    }
}
