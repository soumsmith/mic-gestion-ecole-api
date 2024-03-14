package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationsProfStatDto {
	private Long profUserHasPersonnelId;
	private Long profPersonnelId;
	private String nomPrenoms;
	private int max;
	private List<DetailsEvaluationProfDto> details;
}
