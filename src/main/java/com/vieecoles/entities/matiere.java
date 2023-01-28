package com.vieecoles.entities;

import com.vieecoles.steph.entities.CategorieMatiere;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "matiere")
@Data
@EqualsAndHashCode(callSuper = false)
public class matiere extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  matiereid ;
    private  String matierecode;
    private  String matierelibelle;
    private Long  matierecoefficien ;
    @Transient
    private Double moyenne;
    @Transient
    private String rang;
    @Transient
    private String  coef ;
    @Transient
    private String appreciation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_matiere_categorie_matiereid")
    private CategorieMatiere categorie_matiere ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private com.vieecoles.entities.tenant tenant ;

    public Long getMatiereid() {
        return matiereid;
    }

    public void setMatiereid(Long matiereid) {
        this.matiereid = matiereid;
    }

    public String getMatierecode() {
        return matierecode;
    }

    public void setMatierecode(String matierecode) {
        this.matierecode = matierecode;
    }

    public String getMatierelibelle() {
        return matierelibelle;
    }

    public void setMatierelibelle(String matierelibelle) {
        this.matierelibelle = matierelibelle;
    }

    public Long getMatierecoefficien() {
        return matierecoefficien;
    }

    public void setMatierecoefficien(Long matierecoefficien) {
        this.matierecoefficien = matierecoefficien;
    }

    public CategorieMatiere getCategorie_matiere() {
        return categorie_matiere;
    }

    public void setCategorie_matiere(CategorieMatiere categorie_matiere) {
        this.categorie_matiere = categorie_matiere;
    }

    public com.vieecoles.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.entities.tenant tenant) {
        this.tenant = tenant;
    }
}
