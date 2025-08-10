package com.vieecoles.steph.dto.moyennes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EleveDto {
	
	public EleveDto(long id, String matricule, String nom, String prenom,String sexe, String urlPhoto, long classeId, String classeLibelle) {
		super();
		this.id = id;
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.urlPhoto = urlPhoto;
		this.classeId = classeId;
		this.classeLibelle = classeLibelle;
	}
	private long id;
	private String matricule;
	private String nom;
	private String prenom;
	private String sexe;
	private String urlPhoto;
	
	private long classeId;
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
