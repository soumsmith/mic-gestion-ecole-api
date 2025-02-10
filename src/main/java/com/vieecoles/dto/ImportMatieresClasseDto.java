package com.vieecoles.dto;

import com.vieecoles.steph.dto.ImportEvaluationDto;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportMatieresClasseDto {
	List<ImportEvaluationDto> importEvaluationDtos ;
	Long matiere;
	Long periode;
	Long annee;
	Long type;
	String notesur;
	String user;
	String date;
	Long classe;
}
