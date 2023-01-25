package com.vieecoles.entities;

import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "Classe_Matiere")
public class classeMatiere extends PanacheEntityBase{

	@Id
	private long id;
	private String coef;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matiereid")
	private matiere matiere ;



	@Transient
	@ManyToOne
	private com.vieecoles.entities.operations.classe classe;
	@ManyToOne
	@JoinColumn(name = "branche_id")
	private Branche branche;

}
