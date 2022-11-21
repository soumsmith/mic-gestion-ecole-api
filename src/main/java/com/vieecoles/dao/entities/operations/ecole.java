package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.cycle;
import com.vieecoles.dao.entities.groupe_ecole;
import com.vieecoles.dao.entities.Zone;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ecole extends PanacheEntityBase {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  ecoleid ;
    private  String ecolecode;
    private  String ecoleclibelle;
    private  String ecolearreteecreation;
    private  boolean recoiaffecteetat;

   // @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupe_ecole_groupe_ecoleid")
    private groupe_ecole groupe_ecole ;

   // @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quartier_quartierid")
    private quartier quartier ;

   // @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_zoneid")
    private Zone zone ;

   // @JsonbTransient
    @ManyToMany
    @JoinTable( name = "ecole_cycle",
            joinColumns = @JoinColumn( name = "ecole_ecoleid" ),
            inverseJoinColumns = @JoinColumn( name = "cycle_cycleid" ) )
    private List<cycle> cycles = new ArrayList<>();



    public ecole() {
    }


    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<cycle> getCycles() {
        return cycles;
    }

    public void setCycles(List<cycle> cycles) {
        this.cycles = cycles;
    }

    public Long getEcoleid() {
        return ecoleid;
    }

    public void setEcoleid(Long ecoleid) {
        this.ecoleid = ecoleid;
    }

    public String getEcolecode() {
        return ecolecode;
    }

    public void setEcolecode(String ecolecode) {
        this.ecolecode = ecolecode;
    }

    public String getEcoleclibelle() {
        return ecoleclibelle;
    }

    public void setEcoleclibelle(String ecoleclibelle) {
        this.ecoleclibelle = ecoleclibelle;
    }

    public String getEcolearreteecreation() {
        return ecolearreteecreation;
    }

    public void setEcolearreteecreation(String ecolearreteecreation) {
        this.ecolearreteecreation = ecolearreteecreation;
    }

    public boolean isRecoiaffecteetat() {
        return recoiaffecteetat;
    }

    public void setRecoiaffecteetat(boolean recoiaffecteetat) {
        this.recoiaffecteetat = recoiaffecteetat;
    }

    public com.vieecoles.dao.entities.groupe_ecole getGroupe_ecole() {
        return groupe_ecole;
    }

    public void setGroupe_ecole(com.vieecoles.dao.entities.groupe_ecole groupe_ecole) {
        this.groupe_ecole = groupe_ecole;
    }

    public com.vieecoles.dao.entities.operations.quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(com.vieecoles.dao.entities.operations.quartier quartier) {
        this.quartier = quartier;
    }


}
