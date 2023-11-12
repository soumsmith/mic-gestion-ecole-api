package com.vieecoles.steph.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "LOGGER_AUDIT")
@Entity
public class LoggerAudit extends PanacheEntityBase{
	@Id
	private String id;
	private String message;
	private String user;
	private String module;
	@Column(name = "type_action")
	private String typeAction;
	@Column(name = "date_creation")
	private Date dateCreation;
}
