package com.vieecoles.steph.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereNotesEleveDto {
	private Long matiereId;
	private String matiereLibelle;
	private List<String> notes = new ArrayList<>();
}