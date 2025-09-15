package com.vieecoles.parents.dto;

public class ParentConfigurationRequest {
    private String parentTelephone;
    private Long ecoleId;
    private Long anneeId;
    private Long classeId;


    // Constructeurs
    public ParentConfigurationRequest() {}

    public ParentConfigurationRequest(String parentTelephone, Long ecoleId,
                                   Long anneeId, Long classeId, Long periodeId) {
        this.parentTelephone = parentTelephone;
        this.ecoleId = ecoleId;
        this.anneeId = anneeId;
        this.classeId = classeId;
    }

    // Getters et Setters
    public String getParentTelephone() { return parentTelephone; }
    public void setParentTelephone(String parentTelephone) { this.parentTelephone = parentTelephone; }

    public Long getEcoleId() { return ecoleId; }
    public void setEcoleId(Long ecoleId) { this.ecoleId = ecoleId; }

    public Long getAnneeId() { return anneeId; }
    public void setAnneeId(Long anneeId) { this.anneeId = anneeId; }

    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }

}
