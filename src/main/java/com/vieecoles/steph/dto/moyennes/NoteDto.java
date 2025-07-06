package com.vieecoles.steph.dto.moyennes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
	public NoteDto(long id, double note) {
		this.id = id;
		this.note = note;
	}
	private long id;
	private long evaluationId;
	private long matiereEcoleId;
	private String matiereEcoleLibelle;
	private double note;
	private long classeEleveId ;
}
