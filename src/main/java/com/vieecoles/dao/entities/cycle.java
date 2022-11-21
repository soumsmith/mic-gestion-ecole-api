package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class cycle extends PanacheEntityBase  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  cycleid ;
    private  String cyclecode;
    private  String cyclelibelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private tenant tenant;


    @ManyToMany
    @JoinTable( name = "ecole_cycle",
            joinColumns = @JoinColumn( name = "cycle_cycleid" ),
            inverseJoinColumns = @JoinColumn( name = "ecole_ecoleid" ) )
    private List<com.vieecoles.dao.entities.operations.ecole> ecole = new ArrayList<>();

    public com.vieecoles.dao.entities.tenant getTenant() {
        return tenant;
}

    public void setTenant(com.vieecoles.dao.entities.tenant tenant) {
        this.tenant = tenant;
    }

    public List<com.vieecoles.dao.entities.operations.ecole> getEcole() {
        return ecole;
    }

    public void setEcole(List<com.vieecoles.dao.entities.operations.ecole> ecole) {
        this.ecole = ecole;
    }

    public Long getCycleid() {
        return cycleid;
    }

    public void setCycleid(Long cycleid) {
        this.cycleid = cycleid;
    }

    public String getCyclecode() {
        return cyclecode;
    }

    public void setCyclecode(String cyclecode) {
        this.cyclecode = cyclecode;
    }

    public String getCyclelibelle() {
        return cyclelibelle;
    }

    public void setCyclelibelle(String cyclelibelle) {
        this.cyclelibelle = cyclelibelle;
    }
}
