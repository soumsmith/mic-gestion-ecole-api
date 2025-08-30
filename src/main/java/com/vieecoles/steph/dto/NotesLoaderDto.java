package com.vieecoles.steph.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotesLoaderDto{

	private long id;
	private Double note;
	private Integer numOrdre;

}
