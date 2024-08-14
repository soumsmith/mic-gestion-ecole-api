package com.vieecoles.steph.services;

import com.vieecoles.entities.Niveau;
import com.vieecoles.services.niveauService;
import com.vieecoles.steph.dto.IdCodeLibelleDto;
import com.vieecoles.steph.dto.ProgrammeBrancheDto;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.projections.GenericBasicProjectionLongId;
import com.vieecoles.steph.entities.NiveauEnseignement;
import com.vieecoles.steph.entities.Programme;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class BrancheService implements PanacheRepositoryBase<Branche, Long> {
	public List<Branche> getList() {
		return Branche.listAll();
	}

	public Branche findById(Long id) {
		return Branche.findById(id);
	}

	public List<Branche> findByNiveauEnseignementViaEcole(Long id) {
		Ecole ecole = Ecole.findById(id);
//		System.out.println(ecole);
		return Branche.find("niveauEnseignement.id =?1 order by libelle desc", ecole.getNiveauEnseignement().getId())
				.list();
	}

	public List<Branche> findByNiveauEnseignement(Long id) {

		return Branche.find("niveauEnseignement.id =?1", id).list();
	}

	public List<GenericBasicProjectionLongId> findByNiveauEnseignementProjection(Long id) {
		List<GenericBasicProjectionLongId> branches = new ArrayList<GenericBasicProjectionLongId>();
		try {
			branches = Branche.find("niveauEnseignement.id =?1", id).project(GenericBasicProjectionLongId.class).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return branches;
	}

	public Response create(Branche branche) {
		branche.persist();
		return Response.created(URI.create("/branche/" + branche.getId())).build();
	}

	public Branche update(long id, Branche branche) {
		Branche entity = Branche.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setId(branche.getId());
		entity.setLibelle(branche.getLibelle());
		return entity;
	}

	public void delete(long id) {
		Branche entity = Branche.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}

	public List<Branche> search(String libelle) {
		return Branche.find("libelle", libelle).list();
	}

	public long count() {
		return Branche.count();
	}

	public List<IdCodeLibelleDto> convertToDto(List<Branche> branches) {
		IdCodeLibelleDto dto;
		List<IdCodeLibelleDto> list = new ArrayList<IdCodeLibelleDto>();
		for (Branche br : branches) {
			dto = new IdCodeLibelleDto();
			dto.setId(String.valueOf(br.getId()));
			dto.setLibelle(br.getLibelle());
			list.add(dto);
		}
		return list;
	}

	public ProgrammeBrancheDto findByProgrammeAndNiveauEnseignement(String programmeId, String niveau) {
		Programme programme = Programme.findById(programmeId);
		NiveauEnseignement niveauEnseignement = NiveauEnseignement.findById(Long.parseLong(niveau));
		ProgrammeBrancheDto dto = new ProgrammeBrancheDto();
		List<Branche> branches = new ArrayList<Branche>();
		if (programme != null && niveauEnseignement!= null) {
			dto.setProgrammeId(programme.getId());
			dto.setProgrammeCodeLibelle(String.format("%s - %s", programme.getCode(), programme.getLibelle()));
			dto.setNiveauId(String.valueOf(niveauEnseignement.getId()));
			dto.setNiveauCodeLibelle(String.format("%s - %s",niveauEnseignement.getCode(), niveauEnseignement.getLibelle()));
			try {
				branches = Branche.find("programme.id = ?1 and niveauEnseignement.id = ?2 order by libelle desc", programme.getId(),niveauEnseignement.getId()).list();
			} catch (RuntimeException e) {
				e.printStackTrace();
				branches = new ArrayList<>();
				System.out.println(e.getMessage());
			}
			dto.setNbreBranche(branches.size());
			dto.setBranches(convertToDto(branches));
		}
		return dto;
	}
}
