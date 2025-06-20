package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "libelleHandicap_has_inscriptions")
public class libelleHandicap_has_inscriptions extends PanacheEntityBase {
    @Id @GeneratedValue
  private  Long  libelleHandicap_libelleHandicapid ;
  private  Long  inscriptions_inscriptionsid ;

    public Long getLibelleHandicap_libelleHandicapid() {
        return libelleHandicap_libelleHandicapid;
}

    public void setLibelleHandicap_libelleHandicapid(Long libelleHandicap_libelleHandicapid) {
        this.libelleHandicap_libelleHandicapid = libelleHandicap_libelleHandicapid;
    }

    public Long getInscriptions_inscriptionsid() {
        return inscriptions_inscriptionsid;
    }

    public void setInscriptions_inscriptionsid(Long inscriptions_inscriptionsid) {
        this.inscriptions_inscriptionsid = inscriptions_inscriptionsid;
    }
}
