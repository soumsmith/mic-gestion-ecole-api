package com.vieecoles.dto;

public class ParentDto {
    private  Long   parentid ;
    private  String   parentcode;
    private String  parentnom ;
    private String  parentprenom ;
    private String  parent_tel ;
    private String  parent_tel2 ;
    private String  parent_profession ;
    private String   parentemail;
    private Long  identifiantTypeParent ;
    private  String identifiantTenant ;

    public ParentDto() {
    }

    public Long getIdentifiantTypeParent() {
        return identifiantTypeParent;
    }

    public void setIdentifiantTypeParent(Long identifiantTypeParent) {
        this.identifiantTypeParent = identifiantTypeParent;
    }

    public String getIdentifiantTenant() {
        return identifiantTenant;
    }

    public void setIdentifiantTenant(String identifiantTenant) {
        this.identifiantTenant = identifiantTenant;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getParentnom() {
        return parentnom;
    }

    public void setParentnom(String parentnom) {
        this.parentnom = parentnom;
    }

    public String getParentprenom() {
        return parentprenom;
    }

    public void setParentprenom(String parentprenom) {
        this.parentprenom = parentprenom;
    }

    public String getParent_tel() {
        return parent_tel;
    }

    public void setParent_tel(String parent_tel) {
        this.parent_tel = parent_tel;
    }

    public String getParent_tel2() {
        return parent_tel2;
    }

    public void setParent_tel2(String parent_tel2) {
        this.parent_tel2 = parent_tel2;
    }

    public String getParent_profession() {
        return parent_profession;
    }

    public void setParent_profession(String parent_profession) {
        this.parent_profession = parent_profession;
    }

    public String getParentemail() {
        return parentemail;
    }

    public void setParentemail(String parentemail) {
        this.parentemail = parentemail;
    }
}
