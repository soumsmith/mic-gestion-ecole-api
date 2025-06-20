package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;

@Entity
@Table(name = "quartier")
public class quartier extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  quartierid ;
    private  String quartiercode;
    private  String quartierlibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_communeid")
    private commune commune;

    public quartier() {
    }

    public Long getQuartierid() {
        return quartierid;
    }

    public void setQuartierid(Long quartierid) {
        this.quartierid = quartierid;
    }

    public String getQuartiercode() {
        return quartiercode;
    }

    public void setQuartiercode(String quartiercode) {
        this.quartiercode = quartiercode;
    }

    public String getQuartierlibelle() {
        return quartierlibelle;
    }

    public void setQuartierlibelle(String quartierlibelle) {
        this.quartierlibelle = quartierlibelle;
    }

    public com.vieecoles.entities.operations.commune getCommune() {
        return commune;
    }

    public void setCommune(com.vieecoles.entities.operations.commune commune) {
        this.commune = commune;
    }
}
