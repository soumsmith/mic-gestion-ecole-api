package com.vieecoles.dto;

public class InscriptionRequest {
    private String auditActeur;
    private String auditSession;
    private String idEleve;
    private String idTypeAffectation;
    private String idAnciennete;
    private String idScolarite;
    private String idClasse;
    private String idScolarite2;
    private String idClasse2;

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

    public String getIdScolarite2() {
        return idScolarite2;
    }

    public void setIdScolarite2(String idScolarite2) {
        this.idScolarite2 = idScolarite2;
    }

    public String getIdClasse2() {
        return idClasse2;
    }

    public void setIdClasse2(String idClasse2) {
        this.idClasse2 = idClasse2;
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
