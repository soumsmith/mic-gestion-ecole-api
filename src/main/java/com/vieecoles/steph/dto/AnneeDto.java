package com.vieecoles.steph.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnneeDto { 
	private Long ecoleId;
	private String ecoleLibelle;
	private Long anneeOuverteCentraleId;
	private List<DetailAnneeDto> anneeEcoleList = new ArrayList<DetailAnneeDto>();

}


