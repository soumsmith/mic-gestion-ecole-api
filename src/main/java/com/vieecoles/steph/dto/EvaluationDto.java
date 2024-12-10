package com.vieecoles.steph.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluationDto {
	public EvaluationDto(Long id, String code, String duree,String noteSur,Integer pec) {
		this.id = id;
		this.code = code;
		this.duree = duree;
		this.noteSur = noteSur;
		this.pec = pec;
	}
	private Long id;
	private String code;
	private Long numero;
	private String heure;
	private String duree;
	private String noteSur;
	private String etat;
	private String groupeEvaluationId;
	private String dateLimite;
	private IdLongCodeLibelleDto type;
	private IdLongCodeLibelleDto periode;
	private Integer pec;
	private IdLongCodeLibelleDto annee;
	private IdLongCodeLibelleDto classe;
	private IdLongCodeLibelleDto matiereEcole;
}
