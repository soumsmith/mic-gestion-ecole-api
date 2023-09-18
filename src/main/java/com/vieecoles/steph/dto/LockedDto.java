package com.vieecoles.steph.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockedDto {

	private Boolean isLocked = true;
	private Date dateLimite;
}
