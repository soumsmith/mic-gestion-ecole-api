package com.vieecoles.steph.dto.moyennes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteDto {
	public NoteDto(long id, double note) {
		this.id = id;
		this.note = note;
	}
	
	public NoteDto(Long id, Long evaluationId, Long matiereEcoleId, String matiereEcoleLibelle, Double note, String noteSur,
			Long classeEleveId, Boolean isTestLourd) {
		super();
		this.id = id;
		this.evaluationId = evaluationId;
		this.matiereEcoleId = matiereEcoleId;
		this.matiereEcoleLibelle = matiereEcoleLibelle;
		this.note = note;
		this.noteSur = Double.valueOf(noteSur);
		this.classeEleveId = classeEleveId;
		this.isTestLourd = isTestLourd;
	}

	private Long id;
	private Long evaluationId;
	private Long matiereEcoleId;
	private String matiereEcoleLibelle;
	private Double note;
	private Double noteSur;
	private Long classeEleveId;
	private Boolean isTestLourd;
}
