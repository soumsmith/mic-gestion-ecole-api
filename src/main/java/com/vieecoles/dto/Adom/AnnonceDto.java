package com.vieecoles.dto.Adom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)

public class AnnonceDto {
	Long id;
	String code;
	String titre;
	String informationCours;
	String commentaireSurVous;
	String lieuCours;
	Integer tarif;
	String numero;
}
