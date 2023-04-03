package com.vieecoles.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.vieecoles.entities.operations.Inscriptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDto {
    private  Long   inscriptionsid ;
    private String  inscriptionscode ;
    private String inscriptions_ecole_origine ;
    private  String   inscriptions_classe_precedente ;
    private  String    inscriptions_derniereclasse_religieuse ;
    private  String   inscriptions_classe_actuelle ;
    private  String   inscriptions_redoublant ;
    private  String   inscriptions_boursier ;
    private  String  inscriptions_code_interne ;
    private  String  inscriptions_langue_vivante ;
    private  String inscriptions_contact1 ;
    private  String inscriptions_contact2 ;
    private  String transfert ;
    private String num_decision_affecte ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.statusEleve inscriptions_statut_eleve ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.typeOperation inscriptions_type ;
    private  int   inscriptions_delete ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.status inscriptions_status ;


    private LocalDate inscriptionsdate_creation ;
    private LocalDate inscriptionsdate_modification ;
    private  Long identifiantEleve ;
    private  Long identifiantEcole ;
    private  Long identifiantAnnee_scolaire ;
    private Long identifiantBranche ;

    public String getTransfert() {
        return transfert;
    }

    public void setTransfert(String transfert) {
        this.transfert = transfert;
    }

    private List<Long> libellehandicap = new ArrayList<>();

    public String getNum_decision_affecte() {
        return num_decision_affecte;
    }

    public void setNum_decision_affecte(String num_decision_affecte) {
        this.num_decision_affecte = num_decision_affecte;
    }

    public InscriptionDto() {
    }

    public String getInscriptions_redoublant() {
        return inscriptions_redoublant;
    }

    public void setInscriptions_redoublant(String inscriptions_redoublant) {
        this.inscriptions_redoublant = inscriptions_redoublant;
    }

    public String getInscriptions_boursier() {
        return inscriptions_boursier;
    }

    public void setInscriptions_boursier(String inscriptions_boursier) {
        this.inscriptions_boursier = inscriptions_boursier;
    }

    public String getInscriptions_contact1() {
        return inscriptions_contact1;
    }

    public void setInscriptions_contact1(String inscriptions_contact1) {
        this.inscriptions_contact1 = inscriptions_contact1;
    }

    public String getInscriptions_contact2() {
        return inscriptions_contact2;
    }

    public void setInscriptions_contact2(String inscriptions_contact2) {
        this.inscriptions_contact2 = inscriptions_contact2;
    }

    public String getInscriptions_code_interne() {
        return inscriptions_code_interne;
    }

    public void setInscriptions_code_interne(String inscriptions_code_interne) {
        this.inscriptions_code_interne = inscriptions_code_interne;
    }

    public String getInscriptions_langue_vivante() {
        return inscriptions_langue_vivante;
    }

    public void setInscriptions_langue_vivante(String inscriptions_langue_vivante) {
        this.inscriptions_langue_vivante = inscriptions_langue_vivante;
    }

    public Long getIdentifiantBranche() {
        return identifiantBranche;
    }

    public void setIdentifiantBranche(Long identifiantBranche) {
        this.identifiantBranche = identifiantBranche;
    }

    public Inscriptions.statusEleve getInscriptions_statut_eleve() {
        return inscriptions_statut_eleve;
    }

    public void setInscriptions_statut_eleve(Inscriptions.statusEleve inscriptions_statut_eleve) {
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
    }

    public Inscriptions.typeOperation getInscriptions_type() {
        return inscriptions_type;
    }

    public void setInscriptions_type(Inscriptions.typeOperation inscriptions_type) {
        this.inscriptions_type = inscriptions_type;
    }



    public List<Long> getLibellehandicap() {
        return libellehandicap;
    }

    public Long getIdentifiantAnnee_scolaire() {
        return identifiantAnnee_scolaire;
    }

    public void setIdentifiantAnnee_scolaire(Long identifiantAnnee_scolaire) {
        this.identifiantAnnee_scolaire = identifiantAnnee_scolaire;
    }

    public void setLibellehandicap(List<Long> libellehandicap) {
        this.libellehandicap = libellehandicap;
    }

    public Long getInscriptionsid() {
        return inscriptionsid;
    }

    public void setInscriptionsid(Long inscriptionsid) {
        this.inscriptionsid = inscriptionsid;
    }

    public String getInscriptionscode() {
        return inscriptionscode;
    }

    public void setInscriptionscode(String inscriptionscode) {
        this.inscriptionscode = inscriptionscode;
    }

    public String getInscriptions_ecole_origine() {
        return inscriptions_ecole_origine;
    }

    public void setInscriptions_ecole_origine(String inscriptions_ecole_origine) {
        this.inscriptions_ecole_origine = inscriptions_ecole_origine;
    }

    public String getInscriptions_classe_precedente() {
        return inscriptions_classe_precedente;
    }

    public void setInscriptions_classe_precedente(String inscriptions_classe_precedente) {
        this.inscriptions_classe_precedente = inscriptions_classe_precedente;
    }

    public String getInscriptions_derniereclasse_religieuse() {
        return inscriptions_derniereclasse_religieuse;
    }

    public void setInscriptions_derniereclasse_religieuse(String inscriptions_derniereclasse_religieuse) {
        this.inscriptions_derniereclasse_religieuse = inscriptions_derniereclasse_religieuse;
    }

    public String getInscriptions_classe_actuelle() {
        return inscriptions_classe_actuelle;
    }

    public void setInscriptions_classe_actuelle(String inscriptions_classe_actuelle) {
        this.inscriptions_classe_actuelle = inscriptions_classe_actuelle;
    }

    public int getInscriptions_delete() {
        return inscriptions_delete;
    }

    public void setInscriptions_delete(int inscriptions_delete) {
        this.inscriptions_delete = inscriptions_delete;
    }

    public Inscriptions.status getInscriptions_status() {
        return inscriptions_status;
    }

    public void setInscriptions_status(Inscriptions.status inscriptions_status) {
        this.inscriptions_status = inscriptions_status;
    }

    public LocalDate getInscriptionsdate_creation() {
        return inscriptionsdate_creation;
    }

    public void setInscriptionsdate_creation(LocalDate inscriptionsdate_creation) {
        this.inscriptionsdate_creation = inscriptionsdate_creation;
    }

    public LocalDate getInscriptionsdate_modification() {
        return inscriptionsdate_modification;
    }

    public void setInscriptionsdate_modification(LocalDate inscriptionsdate_modification) {
        this.inscriptionsdate_modification = inscriptionsdate_modification;
    }

    public Long getIdentifiantEleve() {
        return identifiantEleve;
    }

    public void setIdentifiantEleve(Long identifiantEleve) {
        this.identifiantEleve = identifiantEleve;
    }

    public Long getIdentifiantEcole() {
        return identifiantEcole;
    }

    public void setIdentifiantEcole(Long identifiantEcole) {
        this.identifiantEcole = identifiantEcole;
    }

    @Override
    public String toString() {
        return "InscriptionDto{" +
                "inscriptionsid=" + inscriptionsid +
                ", inscriptionscode='" + inscriptionscode + '\'' +
                ", inscriptions_ecole_origine='" + inscriptions_ecole_origine + '\'' +
                ", inscriptions_classe_precedente='" + inscriptions_classe_precedente + '\'' +
                ", inscriptions_derniereclasse_religieuse='" + inscriptions_derniereclasse_religieuse + '\'' +
                ", inscriptions_classe_actuelle='" + inscriptions_classe_actuelle + '\'' +
                ", inscriptions_redoublant='" + inscriptions_redoublant + '\'' +
                ", inscriptions_boursier='" + inscriptions_boursier + '\'' +
                ", inscriptions_statut_eleve=" + inscriptions_statut_eleve +
                ", inscriptions_type=" + inscriptions_type +
                ", inscriptions_delete=" + inscriptions_delete +
                ", inscriptions_status=" + inscriptions_status +
                ", inscriptionsdate_creation=" + inscriptionsdate_creation +
                ", inscriptionsdate_modification=" + inscriptionsdate_modification +
                ", identifiantEleve=" + identifiantEleve +
                ", identifiantEcole=" + identifiantEcole +
                ", identifiantAnnee_scolaire=" + identifiantAnnee_scolaire +
                ", libellehandicap=" + libellehandicap +
                '}';
    }
}
