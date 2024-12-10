package com.vieecoles.steph.dto.moyennes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
	private String id;
	private Double value;
	private Integer pec;
}
