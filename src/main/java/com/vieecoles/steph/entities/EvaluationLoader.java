package com.vieecoles.steph.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.vieecoles.steph.dto.NotesLoaderDto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "Evaluation_Loader")
public class EvaluationLoader extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String code;
	@Transient
	private List<String> notes;
//	@OneToMany(targetEntity = NotesLoader.class, mappedBy = "evaluationLoader" )
	@Transient
	private List<NotesLoaderDto> noteloaders = new ArrayList<NotesLoaderDto>();
	private String matricule;
	private String user;
	private String applicable;
	private String observation;
	@Column(name = "nom_prenom")
	private String nomPrenom;
	@Column(name = "classe_id")
	private Long classeId;
	@Column(name = "classe_code")
	private String classeCode;
	@Column(name = "classe_libelle")
	private String classeLibelle;
	@Column(name = "date_creation")
	private String dateCreation;
	@Column(name = "date_update")
	private String dateUpdate;
	private String statut;
}
