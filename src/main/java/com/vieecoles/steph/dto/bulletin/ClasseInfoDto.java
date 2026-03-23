package com.vieecoles.steph.dto.bulletin;
/** Infos légères sur la classe */
public class ClasseInfoDto {
    private Long id;
    private String code;
    private String libelle;
    private String brancheLibelle;
 
    public ClasseInfoDto() {}
    public ClasseInfoDto(Long id, String code, String libelle, String brancheLibelle) {
        this.id = id; this.code = code; this.libelle = libelle; this.brancheLibelle = brancheLibelle;
    }
    public Long getId()           { return id; }
    public String getCode()       { return code; }
    public String getLibelle()    { return libelle; }
    public String getBrancheLibelle() { return brancheLibelle; }
}