package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressionSeanceDto {
	private String seanceId;
	private SeanceDto seanceDto;
	private Integer duree;
	private Integer dureeTotale;
	private String heureDeb;
	private String heureFin;
	private String dateSeance;
	private String professeur;
	private List<String> detailProgressions;
	private List<DetailProgressionDto> fullDetailProgressions;
	private Integer position;
	private String observations;
	private String attachmentUrl; 
}
