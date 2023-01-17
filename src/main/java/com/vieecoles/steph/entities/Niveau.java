package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "niveau")
@Data
public class Niveau extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name = "niveauid")
    private Integer  id ;
    @Column(name = "niveaucode")
    private  String code;
    @Column(name = "niveaulibelle")
    private  String libelle;

}
