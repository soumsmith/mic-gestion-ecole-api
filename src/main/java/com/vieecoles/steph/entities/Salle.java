package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "salle")
@Getter
@Setter
public class Salle extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salleid")
	private long id;
	@Column(name = "sallecode")
	private String code;
	@Column(name = "sallelibelle")
	private String libelle;
	@ManyToOne
	@JoinColumn(name = "ecole_id")
	private Ecole ecole;

//	@ManyToOne
//	@JoinColumn(name = "niveau_tage_niveau_etageid")
//	private Etage etage;
	@Column(name = "niveau_tage_niveau_etageid")
	private Integer niveauEtage;
}
