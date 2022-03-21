package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class details_infos_eleves extends PanacheEntityBase {
 @Id @GeneratedValue
  private  Integer  details_infos_elevesid;
  private  String  details_infos_elevescode ;
    private  String details_infos_eleveslibelle ;
    private  String details_infos_elevesvaleur ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_eleveid")
    private eleve eleve ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_information_categorie_informationid")
    private categorie_information categorie_information ;

    public details_infos_eleves() {
    }

    public Integer getDetails_infos_elevesid() {
        return details_infos_elevesid;
    }

    public void setDetails_infos_elevesid(Integer details_infos_elevesid) {
        this.details_infos_elevesid = details_infos_elevesid;
    }

    public String getDetails_infos_elevescode() {
        return details_infos_elevescode;
    }

    public void setDetails_infos_elevescode(String details_infos_elevescode) {
        this.details_infos_elevescode = details_infos_elevescode;
    }

    public String getDetails_infos_eleveslibelle() {
        return details_infos_eleveslibelle;
    }

    public void setDetails_infos_eleveslibelle(String details_infos_eleveslibelle) {
        this.details_infos_eleveslibelle = details_infos_eleveslibelle;
    }

    public String getDetails_infos_elevesvaleur() {
        return details_infos_elevesvaleur;
    }

    public void setDetails_infos_elevesvaleur(String details_infos_elevesvaleur) {
        this.details_infos_elevesvaleur = details_infos_elevesvaleur;
    }

    public com.vieecoles.entities.operations.eleve getEleve() {
        return eleve;
    }

    public void setEleve(com.vieecoles.entities.operations.eleve eleve) {
        this.eleve = eleve;
    }

    public com.vieecoles.entities.operations.categorie_information getCategorie_information() {
        return categorie_information;
    }

    public void setCategorie_information(com.vieecoles.entities.operations.categorie_information categorie_information) {
        this.categorie_information = categorie_information;
    }
}
