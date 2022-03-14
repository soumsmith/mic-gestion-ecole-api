package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
public class TypeParent extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  id ;
    private  String code;
    private  String libelle;

}