package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "Direction_regionale")
public class Direction_regionale extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  id ;
    private  String code;
    private  String libelle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pays_paysid")
    private com.vieecoles.entities.pays pays;

    


    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return String return the libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @param libelle the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * @return Long return the pays_paysid
     */
    

    /**
     * @return com.vieecoles.entities.pays return the pays
     */
    public com.vieecoles.entities.pays getPays() {
        return pays;
    }

    /**
     * @param pays the pays to set
     */
    public void setPays(com.vieecoles.entities.pays pays) {
        this.pays = pays;
    }

}
