package com.vieecoles.steph.dto.moyennes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EleveDto {
	
	public EleveDto(String id, String matricule, String nom, String prenom, String classeId, String classeLibelle) {
		super();
		this.id = id;
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.classeId = classeId;
		this.classeLibelle = classeLibelle;
	}
	private String id;
	private String matricule;
	private String nom;
	private String prenom;
	
	private String classeId;
	private String classeLibelle;
	
	private Double moyenne;
	private Integer rang;
	private String appreciation;
	private Double coef;
	private Double moyenneCoef;
	private Double moyenneAnnuelle;
	private Integer rangAnnuel;
	private String appreciationAnnuelle;
	
}
