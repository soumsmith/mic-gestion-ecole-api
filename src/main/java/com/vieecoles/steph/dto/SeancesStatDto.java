package com.vieecoles.steph.dto;

import java.util.List;

import com.vieecoles.steph.enumerations.SearchLevelSeanceEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeancesStatDto {
	private String ecoleId;
	private String ecoleLibelle;
	private SearchLevelSeanceEnum searchLevel;
	private Long totalGeneral;
	private Long totalJour;
	private Long totalAppelJour;
	private Long totalAppelJourEffectue;
	private Long totalAppelGeneral;
	private Long totalAppelGeneralEffectue;
	private Long totalCTJour;
	private Long totalCTJourEffectue;
	private Long totalCTGeneral;
	private Long totalCTGeneralEffectue;
	private List<SeanceDto> listSeances;
}
