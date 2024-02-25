package com.vieecoles.steph.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AnneePeriode;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.pojos.AnneePeriodePojo;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class AnneePeriodeService implements PanacheRepositoryBase<AnneePeriode, Long> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public List<AnneePeriode> getList() {
		return AnneePeriode.listAll();
	}

	public AnneePeriode findById(Long id) {
		return AnneePeriode.findById(id);
	}

	public List<AnneePeriode> listByAnneeAndEcole(Long anneeId, Long ecoleId) {
		List<AnneePeriode> anneesP = new ArrayList<>();
		try {
			anneesP = AnneePeriode.find("anneeScolaire.id=?1 and ecole.id=?2", anneeId, ecoleId).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return anneesP;
	}

	public AnneePeriode getByAnneeAndEcoleAndPeriode(Long anneeId, Long ecoleId, Long periodeId) {
		logger.info(String.format("annee : %s - ecole : %s - periode %s", anneeId,ecoleId, periodeId));
		System.out.println(String.format("annee : %s - ecole : %s - periode %s", anneeId,ecoleId, periodeId));
		AnneePeriode anneePeriode = null;
		try {
			anneePeriode = AnneePeriode
					.find("anneeScolaire.id=?1 and ecole.id=?2 and periode.id=?3", anneeId, ecoleId, periodeId)
					.singleResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return anneePeriode;
	}

// CENTRAL
	public List<AnneePeriode> listByAnneeAndNiveauEnseignementToCentral(Long anneeId, Long niveauEnseignement) {
		System.out.println(anneeId);
		System.out.println(niveauEnseignement);
		List<AnneePeriode> anPerList = new ArrayList<AnneePeriode>();
		try {
			Gson g = new Gson();
			System.out.println(g.toJson(findById(1L)));
			anPerList = AnneePeriode
					.find("anneeScolaire.id=?1 and anneeScolaire.niveauEnseignement.id=?2 and ecole is null", anneeId,
							niveauEnseignement)
					.list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return anPerList;
	}

	@Transactional
	public AnneePeriode create(AnneePeriode anneePeriode) {
		anneePeriode.setDateCreation(LocalDateTime.now());
		anneePeriode.setDateUpdate(LocalDateTime.now());
		anneePeriode.persist();
		return anneePeriode;
	}

	@Transactional
	public void manyCreate(List<AnneePeriode> anneesPeriodes) {
		try {
			for (AnneePeriode ap : anneesPeriodes) {
//				System.out.println(ap);
				create(ap);
			}
		} catch (RuntimeException r) {
			r.printStackTrace();
			logger.log(Level.SEVERE, "Erreur de persistance :" + r.getMessage());
			throw new RuntimeException(r.getMessage());
		}
	}

	public AnneePeriode update(AnneePeriode anneePeriode) {
		AnneePeriode ap = AnneePeriode.findById(anneePeriode.getId());
		if (ap == null) {
			throw new NotFoundException(String.format("Aucune Annee periode [id=%s] trouvé", anneePeriode.getId()));
		}

		ap.setAnneeScolaire(anneePeriode.getAnneeScolaire());
		ap.setDateDebut(anneePeriode.getDateDebut());
		ap.setDateFin(anneePeriode.getDateFin());
		ap.setDateLimite(anneePeriode.getDateLimite());
		ap.setEcole(anneePeriode.getEcole());
		ap.setNbreEval(anneePeriode.getNbreEval());
		ap.setNiveau(anneePeriode.getNiveau());
		ap.setPeriode(anneePeriode.getPeriode());
		ap.setUser(anneePeriode.getUser());
		ap.setDateUpdate(LocalDateTime.now());

		return ap;
	}

	AnneePeriode setDateFromPojo(AnneePeriodePojo aPojo, AnneePeriode ap) {
		Periode periode = new Periode();
		if (aPojo.getId() != null && aPojo.getId().split("_")[0].equalsIgnoreCase("deb")) {
			ap.setDateDebut(DateUtils.asLocalDateTime(aPojo.getValue()));
		} else if (aPojo.getId() != null && aPojo.getId().split("_")[0].equalsIgnoreCase("fin")) {
			ap.setDateFin(DateUtils.asLocalDateTime(aPojo.getValue()));
			ap.setDateLimite(DateUtils.asLocalDateTime(aPojo.getValue()));
		}
		if (aPojo.getId().split("_").length >= 2 && aPojo.getId().split("_")[1] != null) {
			if (ap.getPeriode() == null) {
				periode.setId(Long.parseLong(aPojo.getId().split("_")[1]));
				ap.setPeriode(periode);
			}
		} else
			throw new RuntimeException(String.format("Periode non trouvée field << %s >>", aPojo.getId()));
		return ap;
	}

	AnneePeriode setDatasFromPojoToPeriodeEcole(AnneePeriodePojo aPojo, AnneePeriode ap) {
		Periode periode = new Periode();
		if (aPojo.getId() != null && aPojo.getId().split("_")[0].equalsIgnoreCase("deb")) {
			ap.setDateDebut(DateUtils.asLocalDateTime(aPojo.getValue()));
		} else if (aPojo.getId() != null && aPojo.getId().split("_")[0].equalsIgnoreCase("fin")) {
			ap.setDateFin(DateUtils.asLocalDateTime(aPojo.getValue()));
		} else if (aPojo.getId() != null && aPojo.getId().split("_")[0].equalsIgnoreCase("limite")) {
			ap.setDateLimite(DateUtils.asLocalDateTime(aPojo.getValue()));
		} else if (aPojo.getId() != null && aPojo.getId().split("_")[0].equalsIgnoreCase("nbeval")) {
			ap.setNbreEval(aPojo.getNbEval());
		}

		if (aPojo.getId().split("_").length >= 2 && aPojo.getId().split("_")[1] != null) {
			if (ap.getPeriode() == null) {
				periode.setId(Long.parseLong(aPojo.getId().split("_")[1]));
				ap.setPeriode(periode);
			}
		} else
			throw new RuntimeException(String.format("Periode non trouvée field << %s >>", aPojo.getId()));
		return ap;
	}

	int getIndex(AnneePeriode anneePeriode, List<AnneePeriode> list) {
		int index = -1;

		for (AnneePeriode ap : list) {
			if (ap.getPeriode() != null && ap.getPeriode().getId() == anneePeriode.getPeriode().getId()) {
				index = list.indexOf(ap);
				break;
			}
		}

		return index;
	}

	@Transactional
	public void handleAnneeToPeriode(AnneeScolaire annee) {
		List<AnneePeriode> anneePeriodesBuilder = new ArrayList<AnneePeriode>();
		int index = -1;
		if (annee.getAnneePeriodes() != null) {
			for (AnneePeriodePojo ap : annee.getAnneePeriodes()) {
				AnneePeriode apObj = setDateFromPojo(ap, new AnneePeriode());
				index = getIndex(apObj, anneePeriodesBuilder);
				if (index == -1) {
					apObj.setAnneeScolaire(annee);
					apObj.setEcole(annee.getEcole());
					apObj.setNbreEval(annee.getNbreEval());
					apObj.setNiveau(annee.getNiveau());
					apObj.setUser(annee.getUser());
					anneePeriodesBuilder.add(apObj);
				} else {
					AnneePeriode apToMaj = anneePeriodesBuilder.get(index);
					anneePeriodesBuilder.set(index, setDateFromPojo(ap, apToMaj));
				}
			}

			// Suppression des enregistrements existant spécifiques
			List<AnneePeriode> anneesPeriodes = listByAnneeAndNiveauEnseignementToCentral(annee.getId(),
					annee.getNiveauEnseignement().getId());
			for (AnneePeriode anPer : anneesPeriodes) {
				logger.info(String.format("--> Suppression annee periode [id : %s]", anPer.getId()));
				anPer.delete();
			}

			// persist
			manyCreate(anneePeriodesBuilder);

//			Gson g = new Gson();
//			logger.info(g.toJson(anneePeriodesBuilder));

		} else
			logger.info("Aucune liste de périodes à traiter");
	}

	@Transactional
	public void handleUpdateAnneePeriodeToEcole(AnneeScolaire annee) {
		List<AnneePeriode> anneePeriodesBuilder = new ArrayList<AnneePeriode>();
		int index = -1;
		if (annee.getAnneePeriodes() != null) {
			for (AnneePeriodePojo ap : annee.getAnneePeriodes()) {
				AnneePeriode apObj = setDatasFromPojoToPeriodeEcole(ap, new AnneePeriode());
				index = getIndex(apObj, anneePeriodesBuilder);
				if (index == -1) {
					apObj.setAnneeScolaire(annee);
					apObj.setEcole(annee.getEcole());
					apObj.setNiveau(annee.getNiveau());
					apObj.setUser(annee.getUser());
					anneePeriodesBuilder.add(apObj);
				} else {
					AnneePeriode apToMaj = anneePeriodesBuilder.get(index);
					anneePeriodesBuilder.set(index, setDatasFromPojoToPeriodeEcole(ap, apToMaj));
				}
			}
			Gson g = new Gson();
			System.out.println(" ----- ----- -----");
			System.out.println(g.toJson(anneePeriodesBuilder));

			for (AnneePeriode apb : anneePeriodesBuilder) {
				try {
					AnneePeriode ap = AnneePeriode
							.find("anneeScolaire.id =?1 and ecole.id=?2 and periode.id =?3",
									apb.getAnneeScolaire().getId(), apb.getEcole().getId(), apb.getPeriode().getId())
							.singleResult();
					if (ap != null) {
						ap.setDateDebut(apb.getDateDebut());
						ap.setDateFin(apb.getDateFin());
						ap.setDateLimite(apb.getDateLimite());
						ap.setNbreEval(apb.getNbreEval());
						ap.setDateUpdate(LocalDateTime.now());
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw new RuntimeException("Erreur :" + e.getMessage());
				}
			}

		} else
			logger.info("Aucune liste de périodes à traiter");
	}

	@Transactional
	public void delete(Long id) {
		AnneePeriode annee = findById(id);
		if (annee == null)
			throw new NotFoundException();
		annee.delete();
	}

	// Pour CENTRAL
	@Transactional
	public void handleAnneeDeleteToCentral(AnneeScolaire anneeScolaire) {
		List<AnneePeriode> anneesPeriodes = listByAnneeAndNiveauEnseignementToCentral(anneeScolaire.getId(),
				anneeScolaire.getNiveauEnseignement().getId());

		if (anneesPeriodes != null)
			for (AnneePeriode an : anneesPeriodes)
				an.delete();
	}

	// Pour ECOLE
	@Transactional
	public void handleSharingToEcole(Long centralId, AnneeScolaire anneeScolaire) {
		List<AnneePeriode> anneesPeriodes = listByAnneeAndNiveauEnseignementToCentral(centralId,
				anneeScolaire.getNiveauEnseignement().getId());

		for (AnneePeriode ap : anneesPeriodes) {
			AnneePeriode apTemp = new AnneePeriode();
			apTemp.setAnneeScolaire(anneeScolaire);
			apTemp.setDateDebut(ap.getDateDebut());
			apTemp.setDateFin(ap.getDateFin());
			apTemp.setDateLimite(ap.getDateLimite());
			apTemp.setEcole(anneeScolaire.getEcole());
			apTemp.setNbreEval(ap.getNbreEval());
			apTemp.setNiveau(anneeScolaire.getNiveau());
			apTemp.setPeriode(ap.getPeriode());
			apTemp.setUser(ap.getUser());
			apTemp.setDateUpdate(LocalDateTime.now());
			create(apTemp);
		}
		System.out.println(String.format("----> %s annee periode crees", anneesPeriodes.size()));
	}

}
