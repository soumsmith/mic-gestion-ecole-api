package com.vieecoles.steph.dto.bulletin;
/** Infos légères sur l'école */
public class EcoleInfoDto {
    private Long id;
    private String code;
    private String libelle;
 
    public EcoleInfoDto() {}
    public EcoleInfoDto(Long id, String code, String libelle) {
        this.id = id; this.code = code; this.libelle = libelle;
    }
    public Long getId()           { return id; }
    public String getCode()       { return code; }
    public String getLibelle()    { return libelle; }
}