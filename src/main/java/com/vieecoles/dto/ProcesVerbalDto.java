package com.vieecoles.dto;

import java.util.Date;

public class ProcesVerbalDto {
    private String matiere;
    private String codeEval;
    private Date dateEval;
    private String  natureEvaluation;
    private String heureDebut ;
    private String heureFin ;
    private String temps ;
    private String periode ;
    private String professeur ;
    private String surveillant ;
    private Long effectif ;
    private Long present ;
    private Long absent ;

    private String matricule ;
    private String nomPrenoms;
    private String classe ;
    private Double noteObtenu;
    private String noteSur ;
    private String appreciation ;
    private String chapitreDeLecon ;
    private String observation ;
    private  Double moyenneEvaluation ;
    private  Double forteNote ;
    private Double faibleNote ;
    private Long nbreNoteInf_egal_8_50 ;
    private Long nbreNoteSup_8_50_inf_9_99 ;
    private Long nbreNoteSup10 ;
    private Long nbreNoteSup12 ;
    private Double pour8_50 ;
    private Double pour_8_50_inf_9_99 ;
    private Double pour_nbreNoteSup10 ;
    private Double pour_nbreNoteSup12 ;

    public String getCodeEval() {
        return codeEval;
    }

    public void setCodeEval(String codeEval) {
        this.codeEval = codeEval;
    }

    public Date getDateEval() {
        return dateEval;
    }

    public void setDateEval(Date dateEval) {
        this.dateEval = dateEval;
    }

    public ProcesVerbalDto(String matiere, String natureEvaluation, String heureDebut, String heureFin, String temps, String periode, String professeur, String surveillant, Long effectif, Long present, Long absent, String matricule, String nomPrenoms, String classe, Double noteObtenu, String noteSur, String appreciation, String chapitreDeLecon, String observation, Double moyenneEvaluation, Double forteNote, Double faibleNote, Long nbreNoteInf_egal_8_50, Long nbreNoteSup_8_50_inf_9_99, Long nbreNoteSup10, Long nbreNoteSup12,
                           Double pour8_50, Double pour_8_50_inf_9_99, Double pour_nbreNoteSup10, Double pour_nbreNoteSup12) {
        this.matiere = matiere;
        this.natureEvaluation = natureEvaluation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.temps = temps;
        this.periode = periode;
        this.professeur = professeur;
        this.surveillant = surveillant;
        this.effectif = effectif;
        this.present = present;
        this.absent = absent;
        this.matricule = matricule;
        this.nomPrenoms = nomPrenoms;
        this.classe = classe;
        this.noteObtenu = noteObtenu;
        this.noteSur = noteSur;
        this.appreciation = appreciation;
        this.chapitreDeLecon = chapitreDeLecon;
        this.observation = observation;
        this.moyenneEvaluation = moyenneEvaluation;
        this.forteNote = forteNote;
        this.faibleNote = faibleNote;
        this.nbreNoteInf_egal_8_50 = nbreNoteInf_egal_8_50;
        this.nbreNoteSup_8_50_inf_9_99 = nbreNoteSup_8_50_inf_9_99;
        this.nbreNoteSup10 = nbreNoteSup10;
        this.nbreNoteSup12 = nbreNoteSup12;
        this.pour8_50 = pour8_50;
        this.pour_8_50_inf_9_99 = pour_8_50_inf_9_99;
        this.pour_nbreNoteSup10 = pour_nbreNoteSup10;
        this.pour_nbreNoteSup12 = pour_nbreNoteSup12;
    }

    public Double getPour8_50() {
        return pour8_50;
    }

    public void setPour8_50(Double pour8_50) {
        this.pour8_50 = pour8_50;
    }

    public Double getPour_8_50_inf_9_99() {
        return pour_8_50_inf_9_99;
    }

    public void setPour_8_50_inf_9_99(Double pour_8_50_inf_9_99) {
        this.pour_8_50_inf_9_99 = pour_8_50_inf_9_99;
    }

    public Double getPour_nbreNoteSup10() {
        return pour_nbreNoteSup10;
    }

    public void setPour_nbreNoteSup10(Double pour_nbreNoteSup10) {
        this.pour_nbreNoteSup10 = pour_nbreNoteSup10;
    }

    public Double getPour_nbreNoteSup12() {
        return pour_nbreNoteSup12;
    }

    public void setPour_nbreNoteSup12(Double pour_nbreNoteSup12) {
        this.pour_nbreNoteSup12 = pour_nbreNoteSup12;
    }

    public ProcesVerbalDto() {
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNatureEvaluation() {
        return natureEvaluation;
    }

    public void setNatureEvaluation(String natureEvaluation) {
        this.natureEvaluation = natureEvaluation;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getProfesseur() {
        return professeur;
    }

    public void setProfesseur(String professeur) {
        this.professeur = professeur;
    }

    public String getSurveillant() {
        return surveillant;
    }

    public void setSurveillant(String surveillant) {
        this.surveillant = surveillant;
    }

    public Long getEffectif() {
        return effectif;
    }

    public void setEffectif(Long effectif) {
        this.effectif = effectif;
    }

    public Long getPresent() {
        return present;
    }

    public void setPresent(Long present) {
        this.present = present;
    }

    public Long getAbsent() {
        return absent;
    }

    public void setAbsent(Long absent) {
        this.absent = absent;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomPrenoms() {
        return nomPrenoms;
    }

    public void setNomPrenoms(String nomPrenoms) {
        this.nomPrenoms = nomPrenoms;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Double getNoteObtenu() {
        return noteObtenu;
    }

    public void setNoteObtenu(Double noteObtenu) {
        this.noteObtenu = noteObtenu;
    }

    public String getNoteSur() {
        return noteSur;
    }

    public void setNoteSur(String noteSur) {
        this.noteSur = noteSur;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public String getChapitreDeLecon() {
        return chapitreDeLecon;
    }

    public void setChapitreDeLecon(String chapitreDeLecon) {
        this.chapitreDeLecon = chapitreDeLecon;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getMoyenneEvaluation() {
        return moyenneEvaluation;
    }

    public void setMoyenneEvaluation(Double moyenneEvaluation) {
        this.moyenneEvaluation = moyenneEvaluation;
    }

    public Double getForteNote() {
        return forteNote;
    }

    public void setForteNote(Double forteNote) {
        this.forteNote = forteNote;
    }

    public Double getFaibleNote() {
        return faibleNote;
    }

    public void setFaibleNote(Double faibleNote) {
        this.faibleNote = faibleNote;
    }

    public Long getNbreNoteInf_egal_8_50() {
        return nbreNoteInf_egal_8_50;
    }

    public void setNbreNoteInf_egal_8_50(Long nbreNoteInf_egal_8_50) {
        this.nbreNoteInf_egal_8_50 = nbreNoteInf_egal_8_50;
    }

    public Long getNbreNoteSup_8_50_inf_9_99() {
        return nbreNoteSup_8_50_inf_9_99;
    }

    public void setNbreNoteSup_8_50_inf_9_99(Long nbreNoteSup_8_50_inf_9_99) {
        this.nbreNoteSup_8_50_inf_9_99 = nbreNoteSup_8_50_inf_9_99;
    }

    public Long getNbreNoteSup10() {
        return nbreNoteSup10;
    }

    public void setNbreNoteSup10(Long nbreNoteSup10) {
        this.nbreNoteSup10 = nbreNoteSup10;
    }

    public Long getNbreNoteSup12() {
        return nbreNoteSup12;
    }

    public void setNbreNoteSup12(Long nbreNoteSup12) {
        this.nbreNoteSup12 = nbreNoteSup12;
    }
}
