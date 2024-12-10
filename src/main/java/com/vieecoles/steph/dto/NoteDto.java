package com.vieecoles.steph.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vieecoles.steph.dto.moyennes.PersonneDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteDto {
	private long id;
	private EvaluationDto evaluation;
	private Double note;
	private Integer pec;
	private String appreciation ;
	private IdLongCodeLibelleDto classeEleve;
	private String statut;
	private PersonneDto personnel;
}
