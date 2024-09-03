package com.vieecoles.steph.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdLongCodeLibelleDto {
	public IdLongCodeLibelleDto(Long id) {
		this.id = id;
	}
	private Long id;
	private String code;
	private String libelle;
}
