package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

@Entity
@Table(name = "niveau")
@Data
@EqualsAndHashCode(callSuper=false)
public class Niveau extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name = "niveauid")
    private Integer  id ;
    @Column(name = "niveaucode")
    private  String code;
    @Column(name = "niveaulibelle")
    private  String libelle;
    private Integer ordre;

}
