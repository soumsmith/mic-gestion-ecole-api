package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "r_info_fondateur")
@Data
@EqualsAndHashCode(callSuper = false)
public class r_info_fondateur extends PanacheEntityBase {
    @Id @GeneratedValue
  private Long  id ;
private String fon_nomPrenoms ;
private String  fon_fonction ;
private String  fon_adresse; 
private String fon_telephone ;
private String fon_cellulaire ;
private String fon_email ;
private String fon_code_etablissement;

}
