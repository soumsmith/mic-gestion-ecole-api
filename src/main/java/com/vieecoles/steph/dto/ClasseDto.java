package com.vieecoles.steph.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ClasseDto extends IdLongCodeLibelleDto {
	
	public ClasseDto(Long ecoleId, Long id, String libelle) {
		this.ecoleId = ecoleId;
		this.setId(id);
		this.setLibelle(libelle);
	}
	
	@Getter
	@Setter
	private Long ecoleId;
}
