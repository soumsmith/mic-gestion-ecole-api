package com.vieecoles.dto;

public class InscriptionRequest {
    private String auditActeur;
    private String auditSession;
    private String idEleve;
    private String idTypeAffectation;
    private String idAnciennete;
    private String idScolarite;
    private String idClasse;

    public String getAuditActeur() {
        return auditActeur;
    }

    public void setAuditActeur(String auditActeur) {
        this.auditActeur = auditActeur;
    }

    public String getAuditSession() {
        return auditSession;
    }

    public void setAuditSession(String auditSession) {
        this.auditSession = auditSession;
    }

    public String getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(String idEleve) {
        this.idEleve = idEleve;
    }

    public String getIdTypeAffectation() {
        return idTypeAffectation;
    }

    public void setIdTypeAffectation(String idTypeAffectation) {
        this.idTypeAffectation = idTypeAffectation;
    }

    public String getIdAnciennete() {
        return idAnciennete;
    }

    public void setIdAnciennete(String idAnciennete) {
        this.idAnciennete = idAnciennete;
    }

    public String getIdScolarite() {
        return idScolarite;
    }

    public void setIdScolarite(String idScolarite) {
        this.idScolarite = idScolarite;
    }

    public String getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(String idClasse) {
        this.idClasse = idClasse;
    }

    @Override
    public String toString() {
        return "InscriptionRequest{" +
                "auditActeur='" + auditActeur + '\'' +
                ", auditSession='" + auditSession + '\'' +
                ", idEleve='" + idEleve + '\'' +
                ", idTypeAffectation='" + idTypeAffectation + '\'' +
                ", idAnciennete='" + idAnciennete + '\'' +
                ", idScolarite='" + idScolarite + '\'' +
                ", idClasse='" + idClasse + '\'' +
                '}';
    }
    // Getters et setters
}
