package com.vieecoles.entities.adom;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "AD_ANNONCE")
public class Annonce extends PanacheEntityBase{

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	@Column(name = "code")
	String code;
	@Column(name = "titre")
	String titre;
	@Column(name = "information_cours")
	String informationCours;
	@Column(name = "commentaire_sur_vous")
	String commentaireSurVous;
	@Column(name = "lieu_cours")
	String lieuCours;
	@Column(name = "tarif")
	Integer tarif;
	@Column(name = "numero_tel")
	String numero;
}
