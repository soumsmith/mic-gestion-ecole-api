package com.vieecoles.steph.services;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.MatiereDto;
import com.vieecoles.steph.entities.CategorieMatiere;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.NiveauEnseignement;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class MatiereService implements PanacheRepositoryBase<Matiere, Long>{
	//Logger logger = Logger.getLogger(Matiere.class.getName());

	public List<Matiere> getList() {
		try {
			return Matiere.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Matiere>() ;
		}
	}

	@Transactional
	public Response create(Matiere ev) {
		Gson gson = new Gson();
		//logger.info(gson.toJson(ev));

//		UUID uuid = UUID.randomUUID();
//		ev.setCode(uuid.toString());
//		ev.setDateCreation(new Date());

		ev.persist();
		return Response.ok("Matiere creee").build();
	}
	
	public List<Matiere> getByNiveauEnseignement(Long niveau){
		return find("niveauEnseignement.id = ?1", niveau).list();
	}
	
	public MatiereDto buildEntityToDto(Matiere matiere) {
		MatiereDto dto = new MatiereDto();
		
		dto.setId(matiere.getId());
		dto.setCode(matiere.getCode());
		dto.setLibelle(matiere.getLibelle());
		dto.setPec(matiere.getPec());
		dto.setNiveauEnseignement(matiere.getNiveauEnseignement());
		dto.setMoyenne(matiere.getMoyenne());
		dto.setRang(matiere.getRang());
		dto.setCoef(matiere.getCoef());
		dto.setAppreciation(matiere.getAppreciation());
		dto.setMatiereParent(matiere.getMatiereParent()!= null ? Matiere.findById(Long.parseLong(matiere.getMatiereParent())) : null);
		dto.setCategorie(matiere.getCategorie());
		
		return dto;
	}

	@Transactional
	public Matiere update(Matiere ev) {
		Matiere entity = Matiere.findById(ev.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setId(ev.getId());
		entity.setCode(ev.getCode());
//		entity.setDate(ev.getDate());
//		entity.setDateLimite(ev.getDateLimite());
//		entity.setDuree(ev.getDuree());
//		entity.setEtat(ev.getEtat());
		entity.setPec(ev.getPec());
		entity.setLibelle(ev.getLibelle());
		entity.setNiveauEnseignement(ev.getNiveauEnseignement());
		entity.setMatiereParent(ev.getMatiereParent());

		return entity;
	}

	public Matiere updateAndDisplay(Matiere evaluation) {
		Matiere ev;
		if (update(evaluation) != null) {
			ev = findById(evaluation.getId());
			return ev;
		}

		return null;

	}

	@Transactional
	public void delete(long id) {
		Matiere entity = Matiere.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}
}
