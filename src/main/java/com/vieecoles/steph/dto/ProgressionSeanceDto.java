package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressionSeanceDto {
	private String seanceId;
	private Integer duree;
	private Integer dureeTotale;
	private List<String> detailProgressions;
	private Integer position;
	private String observations;
	private String attachmentUrl; 
}
