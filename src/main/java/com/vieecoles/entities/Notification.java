package com.vieecoles.entities;

import java.sql.Time;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Notification {

	private long id;
	private String code;
	private String message;
	private String titre;
	private LocalDateTime date;
	private Time heure;
	private Parent parent;
}
