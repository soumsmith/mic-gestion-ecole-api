package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportEvaluationDto {

	private String matricule;
	private List<String> notes;
}
