package com.vieecoles.dto;

public class objetDto {
    private Long  objetid ;
    private  String objetcode;
    private  String objetlibelle;
    private String  typeobjetLibelle;


    public objetDto() {
    }

    public Long getObjetid() {
        return objetid;
    }

    public void setObjetid(Long objetid) {
        this.objetid = objetid;
    }

    public String getObjetcode() {
        return objetcode;
    }

    public void setObjetcode(String objetcode) {
        this.objetcode = objetcode;
    }

    public String getObjetlibelle() {
        return objetlibelle;
    }

    public void setObjetlibelle(String objetlibelle) {
        this.objetlibelle = objetlibelle;
    }

    public String getTypeobjetLibelle() {
        return typeobjetLibelle;
    }

    public void setTypeobjetLibelle(String typeobjetLibelle) {
        this.typeobjetLibelle = typeobjetLibelle;
    }
}
