package com.vieecoles.steph.dto;

import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Personnel;

import lombok.Data;

@Data
public class ProfEducDto {

	private Classe classe;
	private Personnel prof;
	private Personnel educateur;
}
