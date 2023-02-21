package com.vieecoles.steph.services;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.steph.dto.EcoleMatiereDto;
import com.vieecoles.steph.dto.MatiereDto;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Matiere;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class EcoleHasMatiereService implements PanacheRepositoryBase<EcoleHasMatiere,Long> {
	@Inject
	EcoleService ecoleService;
	Logger logger = Logger.getLogger(EcoleHasMatiereService.class.getName());
	
	
	public List<EcoleHasMatiere> getList() {
		try {
			return EcoleHasMatiere.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<EcoleHasMatiere>() ;
		}
	}
	
	public List<EcoleHasMatiere> getListByEcole(Long ecole) {
		try {
			return EcoleHasMatiere.find("ecole.id = ?1", ecole).list();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<EcoleHasMatiere>() ;
		}
	}

	@Transactional
	public Response create(EcoleHasMatiere ev) {
//		Gson gson = new Gson();
		//logger.info(gson.toJson(ev));

//		UUID uuid = UUID.randomUUID();
//		ev.setCode(uuid.toString());
//		ev.setDateCreation(new Date());

		ev.persist();
		return Response.ok("Matiere Ecole creee").build();
	}
	
	/**
	 * Cette methode permet de creer les matieres dans chacune des ecoles existantes
	 * @param matiere
	 */
	@Transactional
	public void createMatiereToEcoles(Matiere matiere) {
		List<Ecole> ecoles = ecoleService.getByNiveauEnseignement(matiere.getNiveauEnseignement().getId());
		for(Ecole ecole: ecoles) {
			logger.info("-> Création de matiere pour l'ecole : "+ecole.getLibelle());
			EcoleHasMatiere matiereEcole = new EcoleHasMatiere();
			matiereEcole.setAliasLibelle(matiere.getLibelle());
			matiereEcole.setEcole(ecole);
			matiereEcole.setMatiere(matiere);
			matiereEcole.setPec(matiere.getPec());
			
			// SetMatiere parent pour plus tard lorsque l'algo sera bien précis
			
			create(matiereEcole);
		}
	}
	
	
	public List<EcoleHasMatiere> getByNiveauEnseignement(Long niveau){
		return find("matiere.niveauEnseignement.id = ?1", niveau).list();
	}
	/*
	 * A JUGER UTILITE
	 */
	public EcoleMatiereDto buildEntityToDto(EcoleHasMatiere ecoleMatiere) {
		EcoleMatiereDto dto = new EcoleMatiereDto();
		
		dto.setId(ecoleMatiere.getId());
		dto.setPec(ecoleMatiere.getPec());
		dto.setMatiere(ecoleMatiere.getMatiere());
		dto.setEcole(ecoleMatiere.getEcole());
		dto.setAlias(ecoleMatiere.getAliasLibelle());
//		dto.setRang(ecoleMatiere.getRang());
//		dto.setCoef(ecoleMatiere.getCoef());
//		dto.setAppreciation(ecoleMatiere.getAppreciation());
//		dto.setMatiereParent(matiere.getMatiereParent()!= null ? EcoleHasMatiere.findById(Long.parseLong(matiere.getMatiereParent())) : null);
		
		return dto;
	}

	@Transactional
	public EcoleHasMatiere update(EcoleHasMatiere ev) {
		EcoleHasMatiere entity = EcoleHasMatiere.findById(ev.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setAliasLibelle(ev.getAliasLibelle());
//		entity.setDate(ev.getDate());
//		entity.setDateLimite(ev.getDateLimite());
//		entity.setDuree(ev.getDuree());
//		entity.setEtat(ev.getEtat());
		entity.setPec(ev.getPec());

		return entity;
	}

	public EcoleHasMatiere updateAndDisplay(EcoleHasMatiere evaluation) {
		EcoleHasMatiere ev;
		if (update(evaluation) != null) {
			ev = findById(evaluation.getId());
			return ev;
		}

		return null;

	}

	@Transactional
	public void delete(long id) {
		EcoleHasMatiere entity = EcoleHasMatiere.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}
}
