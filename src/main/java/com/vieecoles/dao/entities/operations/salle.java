package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.Niveau;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

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
