package com.vieecoles.steph.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnneeInfoDto {

	private Long anneeId;
	private String anneeLibelle;
	private Long periodeId;
	private String periodeLibelle;
	private Date dateDebut;
	private Date dateFin;
	private Date dateLimite;
	private String severity;
}
