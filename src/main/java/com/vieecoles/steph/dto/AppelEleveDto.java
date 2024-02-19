package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppelEleveDto {
		private Long inscriptionClasseEleveId;
		private String nom;
		private String prenoms;
		private String matricule;
		private String presence;
		
		public AppelEleveDto() {
			
		}
	}