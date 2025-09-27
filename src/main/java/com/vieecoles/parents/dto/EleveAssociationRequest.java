package com.vieecoles.parents.dto;

public class EleveAssociationRequest {
    private String parentTelephone;
    private String enfantMatricule;
    private String enfantNom;
    private String enfantPrenom;
    private Long enfantAnneeId;
    private Long enfantClasseId;
    private Long enfantEcoleId;
    private String relationType = "parent";

    // Constructeurs
    public EleveAssociationRequest() {}

    public EleveAssociationRequest(String parentTelephone, String enfantMatricule,
                                 String enfantNom, String enfantPrenom) {
        this.parentTelephone = parentTelephone;
        this.enfantMatricule = enfantMatricule;
        this.enfantNom = enfantNom;
        this.enfantPrenom = enfantPrenom;
    }

    // Getters et Setters
    public String getParentTelephone() { return parentTelephone; }
    public void setParentTelephone(String parentTelephone) { this.parentTelephone = parentTelephone; }

    public String getEnfantMatricule() { return enfantMatricule; }
    public void setEnfantMatricule(String enfantMatricule) { this.enfantMatricule = enfantMatricule; }

    public String getEnfantNom() { return enfantNom; }
    public void setEnfantNom(String enfantNom) { this.enfantNom = enfantNom; }

    public String getEnfantPrenom() { return enfantPrenom; }
    public void setEnfantPrenom(String enfantPrenom) { this.enfantPrenom = enfantPrenom; }

    public Long getEnfantAnneeId() { return enfantAnneeId; }
    public void setEnfantAnneeId(Long enfantAnneeId) { this.enfantAnneeId = enfantAnneeId; }

    public Long getEnfantClasseId() { return enfantClasseId; }
    public void setEnfantClasseId(Long enfantClasseId) { this.enfantClasseId = enfantClasseId; }


    public Long getEnfantEcoleId() { return enfantEcoleId; }
    public void setEnfantEcoleId(Long enfantEcoleId) { this.enfantEcoleId = enfantEcoleId; }

    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }
}
