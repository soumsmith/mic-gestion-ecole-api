package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "niveau_etude_has_publication")
public class niveau_etude_has_publication extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id ;
    private Long  niveau_etude_niveau_etudeid ;
    private  Long  publication_id;

    public niveau_etude_has_publication() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNiveau_etude_niveau_etudeid() {
        return niveau_etude_niveau_etudeid;
    }

    public void setNiveau_etude_niveau_etudeid(Long niveau_etude_niveau_etudeid) {
        this.niveau_etude_niveau_etudeid = niveau_etude_niveau_etudeid;
    }

    public Long getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Long publication_id) {
        this.publication_id = publication_id;
    }
}
