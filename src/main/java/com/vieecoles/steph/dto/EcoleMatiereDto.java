package com.vieecoles.steph.dto;

import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Matiere;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EcoleMatiereDto {
	private Long id;
	private Matiere matiere;
	private Ecole ecole;
	private String alias;
	private Integer pec;
	private Integer bonus;
	
}
