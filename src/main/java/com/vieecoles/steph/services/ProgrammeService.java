package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.vieecoles.steph.dto.IdCodeLibelleDto;
import com.vieecoles.steph.dto.ProgrammeDto;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Programme;
import com.vieecoles.steph.entities.ProgrammeEcole;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProgrammeService implements PanacheRepositoryBase<Programme, String> {
	
	@Inject
	ProgrammeEcoleService programmeEcoleService;
	
	public List<Programme> getList() {
		return Programme.listAll();
	}

	public Programme getById(String id) {
		return Programme.findById(id);
	}
	
	public ProgrammeDto listByEcole(Long ecoleId) {
		ProgrammeDto dto = new ProgrammeDto();
		Ecole ecole = Ecole.findById(ecoleId);
		List<IdCodeLibelleDto> list = new ArrayList<IdCodeLibelleDto>();
		IdCodeLibelleDto niveauEnseignement = new IdCodeLibelleDto();
		IdCodeLibelleDto programme;
		List<ProgrammeEcole> listProgrammeEcole = programmeEcoleService.getByEcole(ecoleId);
		dto.setEcole(ecole.getLibelle());
		niveauEnseignement.setId(String.valueOf(ecole.getNiveauEnseignement().getId()));
		niveauEnseignement.setLibelle(ecole.getNiveauEnseignement().getLibelle());
		dto.setNiveauEnseignement(niveauEnseignement);
		if(listProgrammeEcole!=null) {
			dto.setNbreProgramme(listProgrammeEcole.size());
			for(ProgrammeEcole pe: listProgrammeEcole) {
				programme = new IdCodeLibelleDto();
				programme.setId(pe.getProgramme().getId());
				programme.setCode(pe.getProgramme().getCode());
				programme.setLibelle(pe.getProgramme().getLibelle());
				list.add(programme);
			}
			dto.setProgrammes(list);
		}
		return dto;
	}
}
