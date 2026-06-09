package com.vieecoles.steph.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodeMoyenneDto {
private Long periodeId;
private String periodeLibelle;
private Double moyenne;
}
