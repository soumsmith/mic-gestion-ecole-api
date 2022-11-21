package com.vieecoles.dao.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class appel_numerique extends PanacheEntityBase {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Integer  appel_numeriqueid ;
  private String  appel_numeriquecode ;
 private LocalDate appel_numeriquedate ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_personnelid")
    private personnel niveau ;

    public appel_numerique() {
    }

    public Integer getAppel_numeriqueid() {
        return appel_numeriqueid;
    }

    public void setAppel_numeriqueid(Integer appel_numeriqueid) {
        this.appel_numeriqueid = appel_numeriqueid;
    }

    public String getAppel_numeriquecode() {
        return appel_numeriquecode;
    }

    public void setAppel_numeriquecode(String appel_numeriquecode) {
        this.appel_numeriquecode = appel_numeriquecode;
    }

    public LocalDate getAppel_numeriquedate() {
        return appel_numeriquedate;
    }

    public void setAppel_numeriquedate(LocalDate appel_numeriquedate) {
        this.appel_numeriquedate = appel_numeriquedate;
    }

    public personnel getNiveau() {
        return niveau;
    }

    public void setNiveau(personnel niveau) {
        this.niveau = niveau;
    }
}
