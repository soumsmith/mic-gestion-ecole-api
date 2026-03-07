package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteEvaluationDto {
	private String note;
	private String noteSur;
	private String evaluationNumber;
	private String evaluationType;

	public String getNoteFormatted() {
		return String.format("%s/%s", note, noteSur);
	}
}
