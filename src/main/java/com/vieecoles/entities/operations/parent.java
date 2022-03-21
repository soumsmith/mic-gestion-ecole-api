package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class parent extends PanacheEntityBase {
 @Id @GeneratedValue
 private  Integer   parentid ;
  private String  parentcode ;
    private String parentnom ;
    private String parentprenom ;
    private String  parent_tel ;
    private String  parent_tel2 ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_parent_type_parentid")
    private com.vieecoles.entities.type_parent type_parent;





    public parent() {
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
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

    public com.vieecoles.entities.type_parent getType_parent() {
        return type_parent;
    }

    public void setType_parent(com.vieecoles.entities.type_parent type_parent) {
        this.type_parent = type_parent;
    }
}
