package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

import com.vieecoles.entities.Niveau;

@Entity
public class salle extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  salleid ;
    private  String sallecode;
    private  String sallelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_tage_niveau_etageid")
    private Niveau niveau ;

    public salle() {
    }


}
