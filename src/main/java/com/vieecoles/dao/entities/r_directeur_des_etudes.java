package com.vieecoles.dao.entities;

import com.vieecoles.steph.entities.CategorieMatiere;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "r_directeur_des_etudes")
@Data
@EqualsAndHashCode(callSuper = false)
public class r_directeur_des_etudes extends PanacheEntityBase {
    @Id @GeneratedValue
  private Long  id ;
private String direct_adresse ;
private String  direct_telephone ;
private String  direct_cellulaire; 
private String direct_email ;
private String direct_numero_autorisation_enseigner ;
private String direct_nom_prenom ;
private String direct_code_etablissement;
}
