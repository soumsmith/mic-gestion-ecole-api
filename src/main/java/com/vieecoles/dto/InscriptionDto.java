package com.vieecoles.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.vieecoles.entities.operations.Inscriptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDto {
    private  String nomEleve;
    private String prenomEleve ;
    private  Long   inscriptionsid ;
    private String  inscriptionscode ;
    private String decision_ant ;
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
    private  Boolean transfert ;
    private String num_decision_affecte ;
    private String nationalite ;
    private String lieu_naissance ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.statusEleve inscriptions_statut_eleve ;

    public String getDate_naissanceEleve() {
        return date_naissanceEleve;
    }

    public void setDate_naissanceEleve(String date_naissanceEleve) {
        this.date_naissanceEleve = date_naissanceEleve;
    }

    private String date_naissanceEleve ;

    @Enumerated(EnumType.STRING)
    private Inscriptions.typeOperation inscriptions_type ;
    private  int   inscriptions_delete ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.status inscriptions_status ;

    public String getDecision_ant() {
        return decision_ant;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public void setDecision_ant(String decision_ant) {
        this.decision_ant = decision_ant;
    }

    private LocalDate inscriptionsdate_creation ;
    private LocalDate inscriptionsdate_modification ;
    private  Long identifiantEleve ;
    private  Long identifiantEcole ;
    private Long idEleveInscrit ;

    private  Long identifiantAnnee_scolaire ;
    private Long identifiantBranche ;
    private  String nom_prenoms_pere ;
    private  String nom_prenoms_mere ;
    private String nom_prenoms_tuteur ;
    private String commune ;
    private Boolean handicap ;
    private  Boolean moteur ;
    private Boolean visuel ;
    private  Boolean auditif ;
    private  Boolean reaffecte ;
    private  Boolean regularisation ;
    private  Boolean reintegration ;
    private  Boolean prise_en_charge ;
    private  String origine_prise_en_charge ;
    private  String profession_pere ;
    private String boite_postal_pere ;
    private String tel1_pere ;
    private String tel2_pere ;
    private String profession_mere ;
    private String boite_postal_mere ;
    private String tel1_mere ;
    private String tel2_mere ;
    private String profession_tuteur ;
    private String boite_postal_tuteur ;
    private String tel1_tuteur ;
    private String tel2_tuteur ;
    private String profession_pers_en_charge ;
    private String boite_postale_pers_en_charge ;
    private String tel1_pers_en_charge ;
    private String tel2_pers_en_charge ;
    private String autre_handicap ;
    private String nom_prenom_pers_en_charge ;
    private String classe_arabe ;
    private Boolean internes;
    private Boolean demi_pension;
    private Boolean externes;
    private Boolean ivoirien;
    private Boolean etranger_africain;
    private Boolean etranger_non_africain;
    private  String matriculeEleve ;
    private  String sexeEleve ;

    public String getSexeEleve() {
        return sexeEleve;
    }

    public void setSexeEleve(String sexeEleve) {
        this.sexeEleve = sexeEleve;
    }

    public Long getIdEleveInscrit() {
        return idEleveInscrit;
    }

    public void setIdEleveInscrit(Long idEleveInscrit) {
        this.idEleveInscrit = idEleveInscrit;
    }



    public String getNomEleve() {
        return nomEleve;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
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

    public Boolean getInternes() {
        return internes;
    }

    public void setInternes(Boolean internes) {
        this.internes = internes;
    }

    public Boolean getDemi_pension() {
        return demi_pension;
    }

    public void setDemi_pension(Boolean demi_pension) {
        this.demi_pension = demi_pension;
    }

    public Boolean getExternes() {
        return externes;
    }

    public void setExternes(Boolean externes) {
        this.externes = externes;
    }

    public Boolean getIvoirien() {
        return ivoirien;
    }

    public void setIvoirien(Boolean ivoirien) {
        this.ivoirien = ivoirien;
    }

    public Boolean getEtranger_africain() {
        return etranger_africain;
    }

    public void setEtranger_africain(Boolean etranger_africain) {
        this.etranger_africain = etranger_africain;
    }

    public Boolean getEtranger_non_africain() {
        return etranger_non_africain;
    }

    public void setEtranger_non_africain(Boolean etranger_non_africain) {
        this.etranger_non_africain = etranger_non_africain;
    }

    public String getNom_prenoms_pere() {
        return nom_prenoms_pere;
    }

    public void setNom_prenoms_pere(String nom_prenoms_pere) {
        this.nom_prenoms_pere = nom_prenoms_pere;
    }

    public String getNom_prenoms_mere() {
        return nom_prenoms_mere;
    }

    public void setNom_prenoms_mere(String nom_prenoms_mere) {
        this.nom_prenoms_mere = nom_prenoms_mere;
    }

    public String getNom_prenoms_tuteur() {
        return nom_prenoms_tuteur;
    }

    public void setNom_prenoms_tuteur(String nom_prenoms_tuteur) {
        this.nom_prenoms_tuteur = nom_prenoms_tuteur;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public Boolean getHandicap() {
        return handicap;
    }

    public void setHandicap(Boolean handicap) {
        this.handicap = handicap;
    }

    public Boolean getMoteur() {
        return moteur;
    }

    public void setMoteur(Boolean moteur) {
        this.moteur = moteur;
    }

    public Boolean getVisuel() {
        return visuel;
    }

    public void setVisuel(Boolean visuel) {
        this.visuel = visuel;
    }

    public Boolean getAuditif() {
        return auditif;
    }

    public void setAuditif(Boolean auditif) {
        this.auditif = auditif;
    }

    public Boolean getReaffecte() {
        return reaffecte;
    }

    public void setReaffecte(Boolean reaffecte) {
        this.reaffecte = reaffecte;
    }

    public Boolean getRegularisation() {
        return regularisation;
    }

    public void setRegularisation(Boolean regularisation) {
        this.regularisation = regularisation;
    }

    public Boolean getReintegration() {
        return reintegration;
    }

    public void setReintegration(Boolean reintegration) {
        this.reintegration = reintegration;
    }

    public Boolean getPrise_en_charge() {
        return prise_en_charge;
    }

    public void setPrise_en_charge(Boolean prise_en_charge) {
        this.prise_en_charge = prise_en_charge;
    }

    public String getOrigine_prise_en_charge() {
        return origine_prise_en_charge;
    }

    public void setOrigine_prise_en_charge(String origine_prise_en_charge) {
        this.origine_prise_en_charge = origine_prise_en_charge;
    }

    public String getProfession_pere() {
        return profession_pere;
    }

    public void setProfession_pere(String profession_pere) {
        this.profession_pere = profession_pere;
    }

    public String getBoite_postal_pere() {
        return boite_postal_pere;
    }

    public void setBoite_postal_pere(String boite_postal_pere) {
        this.boite_postal_pere = boite_postal_pere;
    }

    public String getTel1_pere() {
        return tel1_pere;
    }

    public void setTel1_pere(String tel1_pere) {
        this.tel1_pere = tel1_pere;
    }

    public String getTel2_pere() {
        return tel2_pere;
    }

    public void setTel2_pere(String tel2_pere) {
        this.tel2_pere = tel2_pere;
    }

    public String getProfession_mere() {
        return profession_mere;
    }

    public void setProfession_mere(String profession_mere) {
        this.profession_mere = profession_mere;
    }

    public String getBoite_postal_mere() {
        return boite_postal_mere;
    }

    public void setBoite_postal_mere(String boite_postal_mere) {
        this.boite_postal_mere = boite_postal_mere;
    }

    public String getTel1_mere() {
        return tel1_mere;
    }

    public void setTel1_mere(String tel1_mere) {
        this.tel1_mere = tel1_mere;
    }

    public String getTel2_mere() {
        return tel2_mere;
    }

    public void setTel2_mere(String tel2_mere) {
        this.tel2_mere = tel2_mere;
    }

    public String getProfession_tuteur() {
        return profession_tuteur;
    }

    public void setProfession_tuteur(String profession_tuteur) {
        this.profession_tuteur = profession_tuteur;
    }

    public String getBoite_postal_tuteur() {
        return boite_postal_tuteur;
    }

    public void setBoite_postal_tuteur(String boite_postal_tuteur) {
        this.boite_postal_tuteur = boite_postal_tuteur;
    }

    public String getTel1_tuteur() {
        return tel1_tuteur;
    }

    public void setTel1_tuteur(String tel1_tuteur) {
        this.tel1_tuteur = tel1_tuteur;
    }

    public String getTel2_tuteur() {
        return tel2_tuteur;
    }

    public void setTel2_tuteur(String tel2_tuteur) {
        this.tel2_tuteur = tel2_tuteur;
    }

    public String getProfession_pers_en_charge() {
        return profession_pers_en_charge;
    }

    public void setProfession_pers_en_charge(String profession_pers_en_charge) {
        this.profession_pers_en_charge = profession_pers_en_charge;
    }

    public String getBoite_postale_pers_en_charge() {
        return boite_postale_pers_en_charge;
    }

    public void setBoite_postale_pers_en_charge(String boite_postale_pers_en_charge) {
        this.boite_postale_pers_en_charge = boite_postale_pers_en_charge;
    }

    public String getTel1_pers_en_charge() {
        return tel1_pers_en_charge;
    }

    public void setTel1_pers_en_charge(String tel1_pers_en_charge) {
        this.tel1_pers_en_charge = tel1_pers_en_charge;
    }

    public String getTel2_pers_en_charge() {
        return tel2_pers_en_charge;
    }

    public void setTel2_pers_en_charge(String tel2_pers_en_charge) {
        this.tel2_pers_en_charge = tel2_pers_en_charge;
    }

    public String getAutre_handicap() {
        return autre_handicap;
    }

    public void setAutre_handicap(String autre_handicap) {
        this.autre_handicap = autre_handicap;
    }

    public String getNom_prenom_pers_en_charge() {
        return nom_prenom_pers_en_charge;
    }

    public void setNom_prenom_pers_en_charge(String nom_prenom_pers_en_charge) {
        this.nom_prenom_pers_en_charge = nom_prenom_pers_en_charge;
    }

    public String getClasse_arabe() {
        return classe_arabe;
    }

    public void setClasse_arabe(String classe_arabe) {
        this.classe_arabe = classe_arabe;
    }

    public Boolean getTransfert() {
        return transfert;
    }

    public void setTransfert(Boolean transfert) {
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
