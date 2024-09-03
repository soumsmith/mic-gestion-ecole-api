package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailProgressionDto {
	private String id;
	private IdLongCodeLibelleDto periode;
	private Integer mois;
	private Integer semaine;
	private Integer numLecon;
	private String titre;
	private Integer heure;
	private String progressionId;
	private Integer ordre;
}
