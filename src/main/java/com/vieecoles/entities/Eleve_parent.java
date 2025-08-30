package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;

@Entity
@Table(name = "Eleve_parent")
public class Eleve_parent extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

  private  Long   parentidf ;
     private  String   parentcode;
   private String  parentnom ;
    private String  parentprenom ;
    private String  parent_tel ;
    private String  parent_tel2 ;
      private String  parent_profession ;
    private String   parentemail;



 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_parentid")
    private type_parent typeParent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private com.vieecoles.entities.tenant tenant;

    @ManyToMany
    @JoinTable( name = "eleve_parent",
            joinColumns = @JoinColumn( name = "parentid" ),
            inverseJoinColumns = @JoinColumn( name = "eleveid" ) )
    private List<eleve> eleves = new ArrayList<>();*/

    /*public List<eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<eleve> eleves) {
        this.eleves = eleves;
    }

    public com.vieecoles.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.entities.tenant tenant) {
        this.tenant = tenant;
    }
*/
    public Long getParentid() {
        return parentidf;
    }

    public void setParentid(Long parentid) {
        this.parentidf = parentid;
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


  /*  public type_parent getTypeParent() {
        return typeParent;
    }

    public void setTypeParent(type_parent typeParent) {
        this.typeParent = typeParent;
    }*/
}
