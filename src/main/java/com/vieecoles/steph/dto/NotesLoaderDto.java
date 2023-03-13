package com.vieecoles.steph.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import com.vieecoles.steph.entities.EvaluationLoader;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotesLoaderDto{
	
	private long id;
	private Double note;
	private Integer numOrdre;

}
