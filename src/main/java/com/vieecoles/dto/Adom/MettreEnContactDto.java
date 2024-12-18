package com.vieecoles.dto.Adom;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)

public class MettreEnContactDto {
	Long id;
	Long idAnnceParen;
	Long idAgentTraitement;
	LocalDate date;
}
