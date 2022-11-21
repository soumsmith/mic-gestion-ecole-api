package com.vieecoles.dto;

import com.vieecoles.dao.entities.Branche;
import com.vieecoles.dao.entities.operations.Inscriptions;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@ApplicationScoped
public class InscriptionAvaliderDto {
    Long idEleveInscrit;
     Long   inscriptionsidEleve ;

    String NomEleve ;
    String prenomEleve;
    String matriculeEleve ;
    LocalDate Date_naissanceEleve ;
    String sexeEleve ;
    String contactEleve ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.statusEleve inscriptions_statut_eleve ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.status inscriptions_status ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.typeOperation inscriptions_type ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.processus inscriptions_processus ;
    private Long brancheid ;
    private  String brancheLibelle ;

    public InscriptionAvaliderDto() {
    }

    public InscriptionAvaliderDto(Long idEleveInscrit, Long   inscriptionsidEleve,String nomEleve, String prenomEleve, String matriculeEleve,
                                  LocalDate date_naissanceEleve,
                                  String sexeEleve, String contactEleve,
                                  Inscriptions.statusEleve inscriptions_statut_eleve,
                                  Inscriptions.status inscriptions_status,
                                  Inscriptions.typeOperation inscriptions_type,Inscriptions.processus inscriptions_processus ,Long brancheid ,String brancheLibelle) {
        this.idEleveInscrit = idEleveInscrit ;
        this.inscriptionsidEleve= inscriptionsidEleve ;
        NomEleve = nomEleve;
        this.prenomEleve = prenomEleve;
        this.matriculeEleve = matriculeEleve;
        Date_naissanceEleve = date_naissanceEleve;
        this.sexeEleve = sexeEleve;
        this.contactEleve = contactEleve;
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
        this.inscriptions_status = inscriptions_status;
        this.inscriptions_type = inscriptions_type;
        this.inscriptions_processus= inscriptions_processus ;
        this.brancheid = brancheid ;
        this.brancheLibelle =brancheLibelle ;

    }

    public Inscriptions.processus getInscriptions_processus() {
        return inscriptions_processus;
    }

    public void setInscriptions_processus(Inscriptions.processus inscriptions_processus) {
        this.inscriptions_processus = inscriptions_processus;
    }

    public Long getBrancheid() {
        return brancheid;
    }

    public void setBrancheid(Long brancheid) {
        this.brancheid = brancheid;
    }

    public String getBrancheLibelle() {
        return brancheLibelle;
    }

    public void setBrancheLibelle(String brancheLibelle) {
        this.brancheLibelle = brancheLibelle;
    }

    public Long getIdEleveInscrit() {
        return idEleveInscrit;
    }

    public void setIdEleveInscrit(Long idEleveInscrit) {
        this.idEleveInscrit = idEleveInscrit;
    }

    public String getNomEleve() {
        return NomEleve;
    }


    public void setNomEleve(String nomEleve) {
        NomEleve = nomEleve;
    }

    public String getPrenomEleve() {
        return prenomEleve;
    }

    public void setPrenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
    }

    public String getMatriculeEleve() {
        return matriculeEleve;
    }

    public void setMatriculeEleve(String matriculeEleve) {
        this.matriculeEleve = matriculeEleve;
    }

    public LocalDate getDate_naissanceEleve() {
        return Date_naissanceEleve;
    }

    public void setDate_naissanceEleve(LocalDate date_naissanceEleve) {
        Date_naissanceEleve = date_naissanceEleve;
    }

    public String getSexeEleve() {
        return sexeEleve;
    }

    public void setSexeEleve(String sexeEleve) {
        this.sexeEleve = sexeEleve;
    }

    public String getContactEleve() {
        return contactEleve;
    }

    public void setContactEleve(String contactEleve) {
        this.contactEleve = contactEleve;
    }

    public Inscriptions.statusEleve getInscriptions_statut_eleve() {
        return inscriptions_statut_eleve;
    }

    public void setInscriptions_statut_eleve(Inscriptions.statusEleve inscriptions_statut_eleve) {
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
    }

    public Inscriptions.status getInscriptions_status() {
        return inscriptions_status;
    }

    public void setInscriptions_status(Inscriptions.status inscriptions_status) {
        this.inscriptions_status = inscriptions_status;
    }

    public Inscriptions.typeOperation getInscriptions_type() {
        return inscriptions_type;
    }

    public void setInscriptions_type(Inscriptions.typeOperation inscriptions_type) {
        this.inscriptions_type = inscriptions_type;
    }

    public Long getInscriptionsidEleve() {
        return inscriptionsidEleve;
    }

    public void setInscriptionsidEleve(Long inscriptionsidEleve) {
        this.inscriptionsidEleve = inscriptionsidEleve;
    }
}
