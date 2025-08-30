package com.vieecoles.projection;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class publicationSelectDto extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id ;
    private  String code;
    private  String libelle;
    private LocalDate date_creation;
    private  LocalDate date_modification;
    private String libelle_ecole ;
    private String  libelle_offre ;
    private String libelle_niveau ;
    private Long identifiantPubNiv ;
    private  Long  niveauId ;
    private String experience ;
    private String lieu ;
    private  LocalDate date_limite ;
    private  Long fonction_fonctionid ;
    private  Long type_offre_id ;
    private String libelle_fonction ;
    private  String statut ;

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLibelle_fonction() {
        return libelle_fonction;
    }

    public void setLibelle_fonction(String libelle_fonction) {
        this.libelle_fonction = libelle_fonction;
    }

    public Long getType_offre_id() {
        return type_offre_id;
    }

    public void setType_offre_id(Long type_offre_id) {
        this.type_offre_id = type_offre_id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDate_limite() {
        return date_limite;
    }

    public void setDate_limite(LocalDate date_limite) {
        this.date_limite = date_limite;
    }

    public Long getFonction_fonctionid() {
        return fonction_fonctionid;
    }

    public void setFonction_fonctionid(Long fonction_fonctionid) {
        this.fonction_fonctionid = fonction_fonctionid;
    }

    public String getLibelle_ecole() {
        return libelle_ecole;
    }

    public String getLibelle_niveau() {
        return libelle_niveau;
    }

    public void setLibelle_niveau(String libelle_niveau) {
        this.libelle_niveau = libelle_niveau;
    }

    public Long getIdentifiantPubNiv() {
        return identifiantPubNiv;
    }

    public void setIdentifiantPubNiv(Long identifiantPubNiv) {
        this.identifiantPubNiv = identifiantPubNiv;
    }

    public Long getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(Long niveauId) {
        this.niveauId = niveauId;
    }

    public void setLibelle_ecole(String libelle_ecole) {
        this.libelle_ecole = libelle_ecole;
    }

    public String getLibelle_offre() {
        return libelle_offre;
    }

    public void setLibelle_offre(String libelle_offre) {
        this.libelle_offre = libelle_offre;
    }

    public publicationSelectDto(Long id, String code,
                                String libelle,
                                LocalDate date_creation,
                                LocalDate date_modification,
                                String libelle_ecole,
                                String libelle_offre,
                                String libelle_niveau,
                                Long niveauId ,
                                Long identifiantPubNiv,
                                String experience,
                                String lieu ,
                                LocalDate date_limite ,
                                Long fonction_fonctionid,
                                String libelle_fonction,
                                Long type_offre_id,String statut) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.libelle_ecole = libelle_ecole;
        this.libelle_offre = libelle_offre;
        this.libelle_niveau= libelle_niveau;
        this.niveauId= niveauId ;
        this.identifiantPubNiv = identifiantPubNiv ;
        this.experience = experience ;
        this.lieu = lieu ;
        this.date_limite= date_limite ;
        this.fonction_fonctionid= fonction_fonctionid ;
        this.libelle_fonction = libelle_fonction ;
        this.type_offre_id = type_offre_id ;
        this.statut = statut ;

    }

    public publicationSelectDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public LocalDate getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(LocalDate date_modification) {
        this.date_modification = date_modification;
    }

}
