package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Bibliotheque extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  bibliothequeId ;
    private String bibliothequeCode;
    private String bibliothequeLibelle;
    private Ecole ecole;

}