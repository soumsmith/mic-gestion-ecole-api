package com.vieecoles.steph.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.vieecoles.steph.dto.MatiereDto;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.projections.GenericBasicProjectionLongId;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MatiereService implements PanacheRepositoryBase<Matiere, Long> {
	// Logger logger = Logger.getLogger(Matiere.class.getName());

	@Inject
	EcoleService ecoleService;
	@Inject
	EcoleHasMatiereService ecoleHasMatiereService;

	Logger logger = Logger.getLogger(MatiereService.class.getName());
//	Gson gson = new Gson();

	public List<Matiere> getList() {
		try {
			return Matiere.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Matiere>();
		}
	}

	public Matiere getById(Long id) {
		try {
			return Matiere.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new Matiere();
		}
	}

	@Transactional
	public void create(Matiere matiere) {
//		Gson g = new Gson();
//		System.out.println(g.toJson(matiere));
		matiere.persist();
		matiere.setDateCreation(LocalDateTime.now());
		matiere.setDateUpdate(LocalDateTime.now());
		logger.info("--> Création de matière "+matiere.getLibelle()+" [ok]");
	}

	/*
	 * Cette methode permet de creer une matiere en centrale et dans chacune des
	 * ecoles existantes
	 */
	@Transactional
	public Matiere createMatiereInEcole(Matiere matiere) {
		try {
//			System.out.println("--------------------------------------");
//			System.out.println(gson.toJson(matiere));
			create(matiere);
			ecoleHasMatiereService.createMatiereToEcoles(matiere);
			return matiere;
		} catch (RuntimeException r) {
			r.printStackTrace();
			throw new RuntimeException(new String("Une Erreur s'est produite: " + r.getMessage()));
		}
	}

	public List<Matiere> getByNiveauEnseignement(Long niveau) {
		return find("niveauEnseignement.id = ?1", niveau).list();
	}

	public List<GenericBasicProjectionLongId> getByNiveauEnseignementProjection(Long niveau) {
		List<GenericBasicProjectionLongId> list = new ArrayList<>();
		try {
			list = find("niveauEnseignement.id = ?1 order by libelle", niveau).project(GenericBasicProjectionLongId.class).list();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public MatiereDto buildEntityToDto(Matiere matiere) {
		MatiereDto dto = new MatiereDto();

		dto.setId(matiere.getId());
		dto.setCode(matiere.getCode());
		dto.setCodeVieEcole(matiere.getCodeVieEcole());
		dto.setLibelle(matiere.getLibelle());
		dto.setPec(matiere.getPec());
		dto.setNiveauEnseignement(matiere.getNiveauEnseignement());
		dto.setMoyenne(matiere.getMoyenne());
		dto.setRang(matiere.getRang());
		dto.setCoef(matiere.getCoef());
		dto.setAppreciation(matiere.getAppreciation());
		dto.setNumOrdre(matiere.getNumOrdre());
		dto.setBonus(matiere.getBonus());
		dto.setMatiereParent(
				matiere.getMatiereParent() != null ? Matiere.findById(Long.parseLong(matiere.getMatiereParent()))
						: null);
		dto.setCategorie(matiere.getCategorie());

		return dto;
	}

	public Matiere buildDtoToEntity(MatiereDto matiereDto) {

//		System.out.println(gson.toJson(matiereDto));
		Matiere matiere = new Matiere();

		matiere.setId(matiereDto.getId());
		matiere.setCode(matiereDto.getCode());
		matiere.setLibelle(matiereDto.getLibelle());
		matiere.setPec(matiereDto.getPec());
		matiere.setNiveauEnseignement(matiereDto.getNiveauEnseignement());
		matiere.setMoyenne(matiereDto.getMoyenne());
		matiere.setRang(matiereDto.getRang());
		matiere.setCoef(matiereDto.getCoef());
		matiere.setAppreciation(matiereDto.getAppreciation());
		matiere.setCategorie(matiereDto.getCategorie());
		matiere.setNumOrdre(matiereDto.getNumOrdre());
		matiere.setBonus(matiereDto.getBonus());
		matiere.setCodeVieEcole(matiereDto.getCodeVieEcole());

		if (matiereDto.getMatiereParent().getId() != 0)
			matiere.setMatiereParent(matiereDto.getMatiereParent().getId().toString());
		else
			matiere.setMatiereParent(null);

		return matiere;
	}

	@Transactional
	public Matiere update(Matiere matiere) {

		Matiere entity = Matiere.findById(matiere.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setId(matiere.getId());
		entity.setCode(matiere.getCode());
//		entity.setDate(ev.getDate());
//		entity.setDateLimite(ev.getDateLimite());
//		entity.setDuree(ev.getDuree());
//		entity.setEtat(ev.getEtat());
		entity.setPec(matiere.getPec());
		entity.setLibelle(matiere.getLibelle());
		entity.setNiveauEnseignement(matiere.getNiveauEnseignement());
		entity.setMatiereParent(matiere.getMatiereParent());
		entity.setCategorie(matiere.getCategorie());
		entity.setNumOrdre(matiere.getNumOrdre());
		entity.setBonus(matiere.getBonus());
		entity.setDateUpdate(LocalDateTime.now());
		entity.setCodeVieEcole(matiere.getCodeVieEcole());

		return entity;
	}

	public Matiere updateAndDisplay(Matiere matiere) {
		Matiere ev;
		try {
			if (update(matiere) != null) {
				ev = findById(matiere.getId());
				return ev;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
