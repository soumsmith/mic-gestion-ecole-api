package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vieecoles.entities.operations.commune;

@Entity(name = "zone")
public class Zone extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  zoneid ;
    private  String zonecode;
    private  String zonelibelle;
    private Long commune_communeid ;

    public Long getZoneid() {
        return zoneid;
    }

    public void setZoneid(Long zoneid) {
        this.zoneid = zoneid;
    }

    public String getZonecode() {
        return zonecode;
    }

    public void setZonecode(String zonecode) {
        this.zonecode = zonecode;
    }

    public String getZonelibelle() {
        return zonelibelle;
    }

    public void setZonelibelle(String zonelibelle) {
        this.zonelibelle = zonelibelle;
    }


    /**
     * @return commune return the mCommune
     */
   


    /**
     * @return Long return the commune_communeid
     */
    public Long getCommune_communeid() {
        return commune_communeid;
    }

    /**
     * @param commune_communeid the commune_communeid to set
     */
    public void setCommune_communeid(Long commune_communeid) {
        this.commune_communeid = commune_communeid;
    }

}
