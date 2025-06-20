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
@Table(name = "AD_METTRE_EN_CONTACT")
public class MettreEnContact extends PanacheEntityBase{

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	@Column(name = "idAnnceParen")
	Long idAnnceParen;
	@Column(name = "idAgentTraitement")
	Long idAgentTraitement;
	@Column(name = "date")
	LocalDate date;
}
