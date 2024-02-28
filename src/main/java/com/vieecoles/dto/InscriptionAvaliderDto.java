package com.vieecoles.dto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

import com.vieecoles.entities.operations.Inscriptions;

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
    String cheminphoto ;

    public String getCheminphoto() {
        return cheminphoto;
    }

    public void setCheminphoto(String cheminphoto) {
        this.cheminphoto = cheminphoto;
    }

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
    private  String nom_prenoms_pere ;
    private  String nom_prenoms_mere ;
    private String nom_prenoms_tuteur ;
    private String commune ;
    private Boolean transfert ;
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
    private String ecole_origine  ;
    private  String num_decision_affectation ;
    private String  inscriptions_langue_vivante ;
    private  String inscriptions_redoublant ;
    private String  inscriptions_boursier ;
    private  Boolean internes ;
    private  Boolean demi_pension ;
    private  Boolean externes ;
    private  Boolean ivoirien ;
    private  Boolean etranger_africain ;
    private  Boolean etranger_non_africain ;
    private String nationalite ;
    private  String lieu_naissance ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] photo_eleve ;

    public byte[] getPhoto_eleve() {
        return photo_eleve;
    }

    public void setPhoto_eleve(byte[] photo_eleve) {
        this.photo_eleve = photo_eleve;
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

    public String getEcole_origine() {
        return ecole_origine;
    }

    public String getNum_decision_affectation() {
        return num_decision_affectation;
    }

    public void setNum_decision_affectation(String num_decision_affectation) {
        this.num_decision_affectation = num_decision_affectation;
    }

    public void setEcole_origine(String ecole_origine) {
        this.ecole_origine = ecole_origine;
    }

    public String getNom_prenoms_pere() {
        return nom_prenoms_pere;
    }

    public Boolean getTransfert() {
        return transfert;
    }

    public void setTransfert(Boolean transfert) {
        this.transfert = transfert;
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

    public String getInscriptions_langue_vivante() {
        return inscriptions_langue_vivante;
    }

    public void setInscriptions_langue_vivante(String inscriptions_langue_vivante) {
        this.inscriptions_langue_vivante = inscriptions_langue_vivante;
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

    public void setClasse_arabe(String classe_arabe) {
        this.classe_arabe = classe_arabe;
    }

    public InscriptionAvaliderDto() {
    }

    public InscriptionAvaliderDto(Long idEleveInscrit,
                                  Long   inscriptionsidEleve,
                                  String nomEleve,
                                  String prenomEleve,
                                  String matriculeEleve,
                                  LocalDate date_naissanceEleve,
                                  String sexeEleve,
                                  String contactEleve,
                                  Inscriptions.statusEleve inscriptions_statut_eleve,
                                  Inscriptions.status inscriptions_status,
                                  Inscriptions.typeOperation inscriptions_type,
                                  Inscriptions.processus inscriptions_processus ,
                                  Long brancheid
                                 ,String brancheLibelle,
                                  String  nom_prenoms_pere ,
                                  String  nom_prenoms_mere,
                                  String  nom_prenoms_tuteur,
                                  String  commune,
                                  Boolean  handicap,
                                  Boolean  moteur,
                                  Boolean  visuel,
                                  Boolean  auditif,
                                  Boolean  reaffecte,
                                  Boolean  regularisation,
                                  Boolean  reintegration,
                                  Boolean  prise_en_charge,
                                  String  origine_prise_en_charge,
                                  String  profession_pere,
                                  String  boite_postal_pere,
                                  String  tel1_pere,
                                  String  tel2_pere,
                                  String  profession_mere,
                                  String  boite_postal_mere,
                                  String  tel1_mere,
                                  String  tel2_mere,
                                  String  profession_tuteur,
                                  String  boite_postal_tuteur,
                                  String  tel1_tuteur,
                                  String  tel2_tuteur,
                                  String  profession_pers_en_charge,
                                  String  boite_postale_pers_en_charge,
                                  String  tel1_pers_en_charge,
                                  String  tel2_pers_en_chargex,
                                  String  autre_handicap,
                                  String  nom_prenom_pers_en_charge,
                                  String  classe_arabe ,
                                  String ecole_origine ,
                                  Boolean transfert ,
                                  String num_decision_affectation ,
                                  String inscriptions_langue_vivante ,
                                  String inscriptions_redoublant ,
                                  String inscriptions_boursier ,
                                  Boolean internes ,
                                  Boolean demi_pension ,
                                  Boolean externes ,
                                  Boolean ivoirien ,
                                  Boolean etranger_africain ,
                                  Boolean etranger_non_africain ,
                                  String cheminphoto,
                                  String nationalite ,
                                  String lieuNaissance


    ) {
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
        this.nom_prenoms_pere=nom_prenoms_pere ;
        this.nom_prenoms_mere=nom_prenoms_mere ;
        this.nom_prenoms_tuteur= nom_prenoms_tuteur ;
        this.commune =commune ;
        this.handicap= handicap ;
        this.moteur = moteur ;
        this.visuel =visuel ;
        this.auditif =auditif ;
        this.reaffecte =reaffecte ;
        this.regularisation =regularisation ;
        this.reintegration =reintegration ;
        this.prise_en_charge=prise_en_charge ;
        this.profession_pere=profession_pere ;
        this.origine_prise_en_charge= origine_prise_en_charge ;
        this.boite_postal_pere = boite_postal_pere ;
        this.tel1_pere =tel1_pere ;
        this.tel2_pere =tel2_pere ;
        this.profession_mere =profession_mere ;
        this.boite_postal_mere =boite_postal_mere ;
        this.tel1_mere = tel1_mere ;
        this.tel2_mere = tel2_mere ;
        this.profession_tuteur =profession_tuteur ;
        this.boite_postal_tuteur = boite_postal_tuteur ;
        this.tel1_tuteur =tel1_tuteur ;
        this.tel2_tuteur =tel2_tuteur ;
        this.profession_pers_en_charge= profession_pers_en_charge ;
        this.boite_postale_pers_en_charge = boite_postale_pers_en_charge ;
        this.tel1_pers_en_charge = tel1_pers_en_charge ;
        this.tel2_pers_en_charge = tel2_pers_en_chargex ;
        this.autre_handicap = autre_handicap ;
        this.nom_prenom_pers_en_charge =nom_prenom_pers_en_charge ;
        this.classe_arabe = classe_arabe ;
        this.ecole_origine = ecole_origine ;
        this.transfert = transfert ;
        this.num_decision_affectation = num_decision_affectation ;
        this.inscriptions_langue_vivante = inscriptions_langue_vivante ;
        this.inscriptions_redoublant = inscriptions_redoublant ;
        this.inscriptions_boursier = inscriptions_boursier ;
        this.internes = internes ;
        this.demi_pension = demi_pension ;
        this.externes = externes ;
        this.ivoirien = ivoirien ;
        this.etranger_africain = etranger_africain ;
        this.etranger_non_africain = etranger_non_africain ;
        this.cheminphoto = cheminphoto ;
        this.nationalite =nationalite ;
        this.lieu_naissance = lieuNaissance ;

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

    @Override
    public String toString() {
        return "InscriptionAvaliderDto{" +
                "idEleveInscrit=" + idEleveInscrit +
                ", inscriptionsidEleve=" + inscriptionsidEleve +
                ", NomEleve='" + NomEleve + '\'' +
                ", prenomEleve='" + prenomEleve + '\'' +
                ", matriculeEleve='" + matriculeEleve + '\'' +
                ", Date_naissanceEleve=" + Date_naissanceEleve +
                ", sexeEleve='" + sexeEleve + '\'' +
                ", contactEleve='" + contactEleve + '\'' +
                ", inscriptions_statut_eleve=" + inscriptions_statut_eleve +
                ", inscriptions_status=" + inscriptions_status +
                ", inscriptions_type=" + inscriptions_type +
                ", inscriptions_processus=" + inscriptions_processus +
                ", brancheid=" + brancheid +
                ", brancheLibelle='" + brancheLibelle + '\'' +
                ", nom_prenoms_pere='" + nom_prenoms_pere + '\'' +
                ", nom_prenoms_mere='" + nom_prenoms_mere + '\'' +
                ", nom_prenoms_tuteur='" + nom_prenoms_tuteur + '\'' +
                ", commune='" + commune + '\'' +
                ", transfert=" + transfert +
                ", handicap=" + handicap +
                ", moteur=" + moteur +
                ", visuel=" + visuel +
                ", auditif=" + auditif +
                ", reaffecte=" + reaffecte +
                ", regularisation=" + regularisation +
                ", reintegration=" + reintegration +
                ", prise_en_charge=" + prise_en_charge +
                ", origine_prise_en_charge='" + origine_prise_en_charge + '\'' +
                ", profession_pere='" + profession_pere + '\'' +
                ", boite_postal_pere='" + boite_postal_pere + '\'' +
                ", tel1_pere='" + tel1_pere + '\'' +
                ", tel2_pere='" + tel2_pere + '\'' +
                ", profession_mere='" + profession_mere + '\'' +
                ", boite_postal_mere='" + boite_postal_mere + '\'' +
                ", tel1_mere='" + tel1_mere + '\'' +
                ", tel2_mere='" + tel2_mere + '\'' +
                ", profession_tuteur='" + profession_tuteur + '\'' +
                ", boite_postal_tuteur='" + boite_postal_tuteur + '\'' +
                ", tel1_tuteur='" + tel1_tuteur + '\'' +
                ", tel2_tuteur='" + tel2_tuteur + '\'' +
                ", profession_pers_en_charge='" + profession_pers_en_charge + '\'' +
                ", boite_postale_pers_en_charge='" + boite_postale_pers_en_charge + '\'' +
                ", tel1_pers_en_charge='" + tel1_pers_en_charge + '\'' +
                ", tel2_pers_en_charge='" + tel2_pers_en_charge + '\'' +
                ", autre_handicap='" + autre_handicap + '\'' +
                ", nom_prenom_pers_en_charge='" + nom_prenom_pers_en_charge + '\'' +
                ", classe_arabe='" + classe_arabe + '\'' +
                ", ecole_origine='" + ecole_origine + '\'' +
                ", num_decision_affectation='" + num_decision_affectation + '\'' +
                ", inscriptions_langue_vivante='" + inscriptions_langue_vivante + '\'' +
                ", inscriptions_redoublant='" + inscriptions_redoublant + '\'' +
                ", inscriptions_boursier='" + inscriptions_boursier + '\'' +
                '}';
    }

    public Long getInscriptionsidEleve() {
        return inscriptionsidEleve;
    }

    public void setInscriptionsidEleve(Long inscriptionsidEleve) {
        this.inscriptionsidEleve = inscriptionsidEleve;
    }
}
