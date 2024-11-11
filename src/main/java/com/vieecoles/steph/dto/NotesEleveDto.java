package com.vieecoles.steph.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotesEleveDto {
	
	private String matricule;
	private String nom;
	private String prenom;
	private List<MatiereNotesEleveDto> list = new ArrayList<>();

}
