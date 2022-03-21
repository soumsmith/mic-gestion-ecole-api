package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EcoleCycle {

	@Id
	private long id;
	private Cycle cycle;
}
