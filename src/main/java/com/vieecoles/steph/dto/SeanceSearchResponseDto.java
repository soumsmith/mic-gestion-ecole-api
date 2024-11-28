package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeanceSearchResponseDto {
	private Long total;
	private Integer page;
	private Integer rows;

	private List<SeanceDto> list;
}
