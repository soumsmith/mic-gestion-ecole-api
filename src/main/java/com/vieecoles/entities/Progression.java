package com.vieecoles.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Progression {

	private long personnelId
	private String personnelCode;
	private LocalDateTime personnelHeureDebut;
	private LocalDateTime personnelHeureFin;
	private String personnelComments;
	private Personnel personnel; 
}
