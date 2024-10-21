package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailProgressionDto {
	private String id;
	private IdLongCodeLibelleDto periode;
	private String dateDeb;
	private String dateFin;
	private Integer numLecon;
	private String titre;
	private Integer heure;
	private String progressionId;
	private Integer nbreSeance;
	private Integer ordre;
}
