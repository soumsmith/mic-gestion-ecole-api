package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;

import com.vieecoles.entities.operations.Inscriptions;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libellehandicap")
public class Libellehandicap extends PanacheEntityBase {
    @Id @GeneratedValue
  private  Long  libelleHandicapid ;
   private String   libelleHandicode ;
  private  String   libelleHandicapLibelle ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private tenant tenant;

    @ManyToMany
    @JoinTable( name = "libellehandicap_inscriptions",
            joinColumns = @JoinColumn( name = "libelleHandicap_libelleHandicapid" ),
            inverseJoinColumns = @JoinColumn( name = "inscriptions_inscriptionsid" ) )
    private List<Inscriptions> inscriptions = new ArrayList<>();

    public com.vieecoles.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.entities.tenant tenant) {
        this.tenant = tenant;
    }

    public List<Inscriptions> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscriptions> inscriptions) {
        this.inscriptions = inscriptions;
    }

    @Override
    public String toString() {
        return "Libellehandicap{" +
                "libelleHandicapid=" + libelleHandicapid +
                ", libelleHandicode='" + libelleHandicode + '\'' +
                ", libelleHandicapLibelle='" + libelleHandicapLibelle + '\'' +
                '}';
    }

    public Long getLibelleHandicapid() {
        return libelleHandicapid;
    }

    public void setLibelleHandicapid(Long libelleHandicapid) {
        this.libelleHandicapid = libelleHandicapid;
    }

    public String getLibelleHandicode() {
        return libelleHandicode;
    }

    public void setLibelleHandicode(String libelleHandicode) {
        this.libelleHandicode = libelleHandicode;
    }

    public String getLibelleHandicapLibelle() {
        return libelleHandicapLibelle;
    }

    public void setLibelleHandicapLibelle(String libelleHandicapLibelle) {
        this.libelleHandicapLibelle = libelleHandicapLibelle;
    }
}
