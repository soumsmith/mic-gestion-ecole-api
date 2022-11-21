package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "parent")
public class Parent extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

  private  Long   parentid ;
     private  String   parentcode;
   private String  parentnom ;
    private String  parentprenom ;
    private String  parent_tel ;
    private String  parent_tel2 ;
      private String  parent_profession ;
    private String   parentemail;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_parent_type_parentid")
    private type_parent typeParent;


    public Parent() {
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


    public type_parent getTypeParent() {
        return typeParent;
    }

    public void setTypeParent(type_parent typeParent) {
        this.typeParent = typeParent;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "parentid=" + parentid +
                ", parentcode='" + parentcode + '\'' +
                ", parentnom='" + parentnom + '\'' +
                ", parentprenom='" + parentprenom + '\'' +
                ", parent_tel='" + parent_tel + '\'' +
                ", parent_tel2='" + parent_tel2 + '\'' +
                ", parent_profession='" + parent_profession + '\'' +
                ", parentemail='" + parentemail + '\'' +
                ", typeParent=" + typeParent +
                      '}';
    }
}
