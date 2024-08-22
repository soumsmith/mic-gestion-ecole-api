package com.vieecoles.steph.dto;

import java.util.List;

import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.NiveauEnseignement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressionDto {
	private String id;
	private NiveauEnseignement niveau;
	private AnneeScolaire annee;
	private Branche branche;
	private Matiere matiere;
	private List<DetailProgressionDto> datas;
}