package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

@Entity
@Table(name = "r_info_etablissement")
@Data
@EqualsAndHashCode(callSuper = false)
public class r_info_etablissement extends PanacheEntityBase {
    @Id @GeneratedValue
  private Long  id ;
private String etab_denomination ;
private String  etab_num_decision_ouverture ;
private String  etab_code_etablissement;
private String etab_situation_geographique ;
private String etab_adresse ;
private String etab_telephone ;
private String etab_fax;
private String etab_email;
private  Long etab_id_ecole ;
}
