package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressionDto {
	private String id;
	private IdLongCodeLibelleDto niveau;
	private IdLongCodeLibelleDto annee;
	private IdLongCodeLibelleDto branche;
	private IdLongCodeLibelleDto matiere;
	private List<DetailProgressionDto> datas;
}