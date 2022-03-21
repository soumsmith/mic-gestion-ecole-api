package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class salle extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  salleid ;
    private  String sallecode;
    private  String sallelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_tage_niveau_etageid")
    private com.vieecoles.entities.niveau niveau ;

    public salle() {
    }


}
