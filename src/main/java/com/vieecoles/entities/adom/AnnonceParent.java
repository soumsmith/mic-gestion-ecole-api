package com.vieecoles.entities.adom;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDate;
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
@Table(name = "AD_ANNONCE_PARENT")
public class AnnonceParent extends PanacheEntityBase{

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	@Column(name = "annonceId")
	Long annonceId;
	@Column(name = "parentId")
	Long parentId;
	@Column(name = "date_creation")
	LocalDate dateCreation;
	@Column(name = "date_modification")
	LocalDate dateModification;
}
