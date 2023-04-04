package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ecole_has_matiere")
@Data
@EqualsAndHashCode(callSuper = false)
public class EcoleHasMatiere extends PanacheEntityBase{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private Long  id ;
	@ManyToOne
	@JoinColumn(name = "ecole_ecoleid")
    private  Ecole ecole;
	@ManyToOne
	@JoinColumn(name = "matiere_matiereid")
    private  Matiere matiere;
	@Column(name = "alias_matiere_libelle")
    private  String aliasLibelle;
	
	private Integer pec;
	@Column(name = "num_ordre_affichage")
	private Integer bonus;
	private Integer numOrdre;
    @Transient
    private Double moyenne;
    @Transient
    private String rang;
    @Transient
    private String  coef ;
    @Transient
    private String appreciation;
//    @ManyToOne
//    @JoinColumn(name = "matiereparent_id")
//    @Column(name = "matiereparent_id")
//    private String matiereParent;

}
