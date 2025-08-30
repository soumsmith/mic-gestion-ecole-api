package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;

@Entity
@Table(name = "objet")
public class objet extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  objetid ;
    private  String objetcode;
    private  String objetlibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_objet_type_objetid")
    private type_objet type_objet;

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

    public com.vieecoles.entities.type_objet getType_objet() {
        return type_objet;
    }

    public void setType_objet(com.vieecoles.entities.type_objet type_objet) {
        this.type_objet = type_objet;
    }
}
