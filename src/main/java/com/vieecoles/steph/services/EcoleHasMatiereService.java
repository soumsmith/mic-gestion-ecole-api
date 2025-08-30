package com.vieecoles.steph.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.google.gson.Gson;
import com.vieecoles.services.matiereService;
import com.vieecoles.steph.dto.EcoleMatiereDto;
import com.vieecoles.steph.dto.MatiereDto;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Matiere;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class EcoleHasMatiereService implements PanacheRepositoryBase<EcoleHasMatiere, Long> {
	@Inject
	EcoleService ecoleService;
	@Inject
	ClasseMatiereService classeMatiereService;
	@Inject
	EcoleHasMatiereService ecoleHasMatiereService;
	@Inject
	MatiereService matiereService;

	Logger logger = Logger.getLogger(EcoleHasMatiereService.class.getName());

	public List<EcoleHasMatiere> getList() {
		try {
			return EcoleHasMatiere.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<EcoleHasMatiere>();
		}
	}

	public List<EcoleHasMatiere> getListByEcole(Long ecole) {
		try {
			return EcoleHasMatiere.find("ecole.id = ?1", ecole).list();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<EcoleHasMatiere>();
		}
	}
	
	public EcoleHasMatiere getByEcoleAndCode(Long ecole, String code) {
		try {
			return EcoleHasMatiere.find("ecole.id = ?1 and code = ?2",ecole, code).firstResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public EcoleHasMatiere getByEcoleAndCodeParent(Long ecole, String code) {
		try {
			return EcoleHasMatiere.find("ecole.id = ?1 and matiereParent.code = ?2",ecole, code).firstResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public Response create(EcoleHasMatiere ecoleHasMatiere) {
//		Gson gson = new Gson();
		// logger.info(gson.toJson(ev));

//		UUID uuid = UUID.randomUUID();
//		ev.setCode(uuid.toString());
		ecoleHasMatiere.setDateCreation(LocalDateTime.now());
		ecoleHasMatiere.setDateUpdate(LocalDateTime.now());

		ecoleHasMatiere.persist();
		return Response.ok("Matiere Ecole creee").build();
	}

	/**
	 * Cette methode permet de creer les matieres dans chacune des ecoles existantes
	 * 
	 * @param matiere
	 */
	@Transactional
	public void createMatiereToEcoles(Matiere matiere) {
		List<Ecole> ecoles = ecoleService.getByNiveauEnseignement(matiere.getNiveauEnseignement().getId());

		for (Ecole ecole : ecoles) {
			logger.info("-> Création de matiere pour l'ecole : " + ecole.getLibelle());
			EcoleHasMatiere matiereEcole = new EcoleHasMatiere();
			EcoleHasMatiere matiereParent = null;
			ClasseMatiere classeMatiere = new ClasseMatiere();

			matiereEcole = buildMatiereToEcoleMatiere(matiere);
			matiereEcole.setEcole(ecole);
			if (matiere.getMatiereParent() != null) {
				try {
					// Retrouver le parent par rapport à la matière source de l'école
//					logger.info("Matiere parent id : "+matiere.getMatiereParent()+ "ecole id : "+ ecole.getId());
					matiereParent = EcoleHasMatiere.find("matiere.id = ?1 and ecole.id=?2",
							Long.parseLong(matiere.getMatiereParent()), ecole.getId()).singleResult();
				} catch (RuntimeException e) {
					matiereParent = null;
					logger.warning(
							"Something wrong in the panache find request, to get matiere parent by code matiere source");
//					e.printStackTrace();
				}
			}
			matiereEcole.setMatiereParent(matiereParent);
			matiereEcole.setParentMatiereLibelle(
					matiereParent == null ? matiereEcole.getLibelle() : matiereParent.getLibelle());
			create(matiereEcole);

			// Pour chaque matiere créee il faut obligatoirement créer le coeficient avec
			// "1" comme valeur par defaut
			// classeMatiere.setBranche(null);
			classeMatiere.setCoef(Constants.DEFAULT_COEFFICIENT);
			classeMatiere.setEcole(ecole);
			classeMatiere.setMatiere(matiereEcole);
			classeMatiereService.handleCreateMatiereToBranche(classeMatiere);
		}
	}

	/*
	 * Process de création de matiere pour une ecole
	 */

	@Transactional
	public void createMatiereToOneEcole(Matiere matiere, Ecole ecole) {

		logger.info("-> Création de matiere pour l'ecole : " + ecole.getLibelle());
		EcoleHasMatiere matiereEcole = new EcoleHasMatiere();
		EcoleHasMatiere matiereParent = null;
		ClasseMatiere classeMatiere = new ClasseMatiere();

		matiereEcole = buildMatiereToEcoleMatiere(matiere);
		matiereEcole.setEcole(ecole);
		if (matiere.getMatiereParent() != null) {
			try {
				// Retrouver le parent par rapport à la matière source de l'école
//					logger.info("Matiere parent id : "+matiere.getMatiereParent()+ "ecole id : "+ ecole.getId());
				matiereParent = EcoleHasMatiere.find("matiere.id = ?1 and ecole.id=?2",
						Long.parseLong(matiere.getMatiereParent()), ecole.getId()).singleResult();
			} catch (RuntimeException e) {
				matiereParent = null;
				logger.warning(
						"Something wrong in the panache find request, to get matiere parent by code matiere source");
//					e.printStackTrace();
			}
		}
		matiereEcole.setMatiereParent(matiereParent);
		matiereEcole.setParentMatiereLibelle(
				matiereParent == null ? matiereEcole.getLibelle() : matiereParent.getLibelle());
		create(matiereEcole);

		// Pour chaque matiere créee il faut obligatoirement créer le coeficient avec
		// "1" comme valeur par defaut
		// classeMatiere.setBranche(null);
		classeMatiere.setCoef(Constants.DEFAULT_COEFFICIENT);
		classeMatiere.setEcole(ecole);
		classeMatiere.setMatiere(matiereEcole);
		classeMatiereService.handleCreateMatiereToBranche(classeMatiere);
	}

	@Transactional
	public String generateMatieres(Ecole ecole) {
		int cmpt = 0;
		try {
			logger.info("-> Création de matiere pour l'ecole : " + ecole.getLibelle());
			List<Matiere> matieres = matiereService.getByNiveauEnseignement(ecole.getNiveauEnseignement().getId());
			for (Matiere matiere : matieres) {
				Long ecoleHasMatiereIsExist = EcoleHasMatiere
						.find("ecole.id =?1 and matiere.id =?2 ", ecole.getId(), matiere.getId()).count();
				EcoleHasMatiere matiereEcole = new EcoleHasMatiere();
				EcoleHasMatiere matiereParent = null;
				ClasseMatiere classeMatiere = new ClasseMatiere();
				if (ecoleHasMatiereIsExist == 0) {
					matiereEcole = buildMatiereToEcoleMatiere(matiere);
					matiereEcole.setEcole(ecole);
					if (matiere.getMatiereParent() != null) {
						try {
							// Retrouver le parent par rapport à la matière source de l'école
//					logger.info("Matiere parent id : "+matiere.getMatiereParent()+ "ecole id : "+ ecole.getId());
							matiereParent = EcoleHasMatiere.find("matiere.id = ?1 and ecole.id=?2",
									Long.parseLong(matiere.getMatiereParent()), ecole.getId()).singleResult();
						} catch (RuntimeException e) {
							matiereParent = null;
							logger.warning(
									"Something wrong in the panache find request, to get matiere parent by code matiere source");
//					e.printStackTrace();
						}
					}
					matiereEcole.setMatiereParent(matiereParent);
					matiereEcole.setParentMatiereLibelle(
							matiereParent == null ? matiereEcole.getLibelle() : matiereParent.getLibelle());
					create(matiereEcole);
					cmpt++;
					// Pour chaque matiere créee il faut obligatoirement créer le coeficient avec
					// "1" comme valeur par defaut
					// classeMatiere.setBranche(null);
					classeMatiere.setCoef(Constants.DEFAULT_COEFFICIENT);
					classeMatiere.setEcole(ecole);
					classeMatiere.setMatiere(matiereEcole);
					classeMatiereService.handleCreateMatiereToBranche(classeMatiere);
				} else {
					logger.info(String.format("La matiere %s existe déjà", matiere.getLibelle()));
				}
			}
			return String.format("%s matiere(s) crée(s)", cmpt);
		} catch (RuntimeException r) {
			return r.getMessage();
		}
	}

	public List<EcoleHasMatiere> getByNiveauEnseignement(Long ecole, Long niveau) {
		return find("matiere.niveauEnseignement.id = ?1 and ecole.id=?2", niveau, ecole).list();
	}

	public EcoleHasMatiere getEMRByEcole(Long ecoleId) {
		try {
			EcoleHasMatiere matiere = EcoleHasMatiere
					.find("ecole.id =?1 and isEMR = ?2 ", ecoleId, Constants.OUI).firstResult();
			return matiere;
		} catch (RuntimeException e) {
			return null;
		}
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
		dto.setCode(ecoleMatiere.getCode());
		dto.setLibelle(ecoleMatiere.getLibelle());
		dto.setBonus(ecoleMatiere.getBonus());
		dto.setCategorie(ecoleMatiere.getCategorie());
		dto.setMatiereParent(ecoleMatiere.getMatiereParent());
		dto.setNumOrdre(ecoleMatiere.getNumOrdre());

		// Maj la matiere parentlibelle

		return dto;
	}

	public EcoleHasMatiere buildMatiereToEcoleMatiere(Matiere matiere) {
		EcoleHasMatiere matiereEcole = new EcoleHasMatiere();

		matiereEcole.setLibelle(matiere.getLibelle());
		matiereEcole.setMatiere(matiere);
		matiereEcole.setPec(matiere.getPec());
		matiereEcole.setNumOrdre(matiere.getNumOrdre());
		matiereEcole.setBonus(matiere.getBonus());
//		matiereEcole.setMatiereParent(matiereParent);
		matiereEcole.setCode(matiere.getCode());
		matiereEcole.setCategorie(matiere.getCategorie());
		matiereEcole.setNiveauEnseignement(matiere.getNiveauEnseignement());
//		matiereEcole.setParentMatiereLibelle(matiereParent != null ? matiereParent.getLibelle() : null);

		return matiereEcole;
	}

	@Transactional
	public EcoleHasMatiere update(EcoleHasMatiere ecoleHasMatiere) {
		EcoleHasMatiere entity = EcoleHasMatiere.findById(ecoleHasMatiere.getId());
		System.out.println("-----");
		System.out.println(ecoleHasMatiere.getMatiereParent() == null);
		System.out.println(ecoleHasMatiere.getMatiereParent().getId());

		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setLibelle(ecoleHasMatiere.getLibelle());
//		
		entity.setCode(ecoleHasMatiere.getCode());
		entity.setLibelle(ecoleHasMatiere.getLibelle());
		entity.setNumOrdre(ecoleHasMatiere.getNumOrdre());
		entity.setMatiereParent(
				(ecoleHasMatiere.getMatiereParent() != null && ecoleHasMatiere.getMatiereParent().getId() != null)
						? ecoleHasMatiere.getMatiereParent()
						: null);
		entity.setCategorie(ecoleHasMatiere.getCategorie());
		entity.setPec(ecoleHasMatiere.getPec());
		entity.setBonus(ecoleHasMatiere.getBonus());
		entity.setDateUpdate(LocalDateTime.now());

		return entity;
	}

	public EcoleHasMatiere updateAndDisplay(EcoleHasMatiere ecoleHasMatiere) {
		EcoleHasMatiere em;
		try {
			if (update(ecoleHasMatiere) != null) {
				em = findById(ecoleHasMatiere.getId());
				return em;
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
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
