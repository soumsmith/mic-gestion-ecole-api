package com.vieecoles.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ProgrammationEvaluation {
	@Id
	private long id;
	private String code;
	private LocalDateTime date;
	private Float coefficient;
	private int status;
}
