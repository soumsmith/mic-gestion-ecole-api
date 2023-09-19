package com.vieecoles.steph.services;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.AnneeInfoDto;
import com.vieecoles.steph.entities.AnneePeriode;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.pojos.AnneePeriodePojo;
import com.vieecoles.steph.pojos.SorterAnneePeriodePojo;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
public class AnneeService implements PanacheRepositoryBase<AnneeScolaire, Long> {

	@Inject
	AnneePeriodeService anneePeriodeService;

	@Inject
	EcoleService ecoleService;
	@Inject
	BulletinService bulletinService;
	
	@Inject
	PeriodeService periodeService;

	public List<AnneeScolaire> getList() {

		List<AnneeScolaire> annees = AnneeScolaire.listAll();
		try {
			if (annees != null && annees.size() > 0)
				for (AnneeScolaire ans : annees)
					populateEntity(ans);
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return annees;
	}

	public List<AnneeScolaire> getListByCentral() {

		List<AnneeScolaire> annees = AnneeScolaire.find("ecole is null").list();
		try {
			if (annees != null && annees.size() > 0)
				for (AnneeScolaire ans : annees)
					populateEntity(ans);
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return annees;
	}

	public List<AnneeScolaire> getListByEcole(Long ecoleId) {

		List<AnneeScolaire> annees = AnneeScolaire.find("ecole.id = ?1 and niveau =?2", ecoleId, Constants.ECOLE)
				.list();
		try {
			if (annees != null && annees.size() > 0)
				for (AnneeScolaire ans : annees)
					populateEntityEcole(ans);
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return annees;
	}

	public List<AnneeScolaire> getListOpenOrCloseByEcole(Long ecoleId) {
		Gson g = new Gson();
		List<AnneeScolaire> annees = new ArrayList<AnneeScolaire>();
		List<AnneeScolaire> anneesCentrales = new ArrayList<AnneeScolaire>();
		try {
			annees = AnneeScolaire
					.find("ecole.id = ?1 and niveau =?2 and statut <> ?3", ecoleId, Constants.ECOLE, Constants.DIFFUSE)
					.list();
			System.out.println(g.toJson(annees));

			if (annees != null && annees.size() > 0)
				for (AnneeScolaire ans : annees) {
					AnneeScolaire anneeCentrale = new AnneeScolaire();
					Boolean flat = true;
					anneeCentrale = findCentralAnneeReference(ans);
					if (anneesCentrales.size() > 0)
						for (AnneeScolaire annee : anneesCentrales) {
							if (annee.getId() == anneeCentrale.getId()) {
								flat = false;
								break;
							}
						}
					if (flat)
						anneesCentrales.add(populateEntity(anneeCentrale));
				}
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return anneesCentrales;
	}

	public AnneeScolaire getById(Long id) {
		AnneeScolaire annee = AnneeScolaire.findById(id);
		if (annee != null)
			return populateEntity(annee);
		else
			return annee;
	}

	AnneeScolaire populateEntity(AnneeScolaire anneeScolaire) {
		try {
			List<AnneePeriode> apList = anneePeriodeService.listByAnneeAndNiveauEnseignementToCentral(
					anneeScolaire.getId(), anneeScolaire.getNiveauEnseignement().getId());
//			System.out.println(apList);
			List<AnneePeriodePojo> anneePojoList = new ArrayList<AnneePeriodePojo>();
			for (AnneePeriode ap : apList) {
				AnneePeriodePojo aPojoDeb = new AnneePeriodePojo();
				AnneePeriodePojo aPojoFin = new AnneePeriodePojo();
				aPojoDeb.setId("deb_" + ap.getPeriode().getId());
				aPojoDeb.setValue(DateUtils.asDate(ap.getDateDebut()));
				anneePojoList.add(aPojoDeb);

				aPojoFin.setId("fin_" + ap.getPeriode().getId());
				aPojoFin.setValue(DateUtils.asDate(ap.getDateFin()));
				anneePojoList.add(aPojoFin);
			}
//		System.out.println(String.format("Liste de %s annee-periode-pojos", anneePojoList.size()));
			anneeScolaire.setAnneePeriodes(anneePojoList);
			anneeScolaire.setAnneeFin(anneeScolaire.getAnneeDebut() + 1);
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return anneeScolaire;
	}

	AnneeScolaire populateEntityEcole(AnneeScolaire anneeScolaire) {
		List<AnneePeriode> apList = anneePeriodeService.listByAnneeAndEcole(anneeScolaire.getId(),
				anneeScolaire.getEcole().getId());
		List<AnneePeriodePojo> anneePojoList = new ArrayList<AnneePeriodePojo>();
//		List<AnneePeriodePojo> anneePojoList = new ArrayList<AnneePeriodePojo>();
		for (AnneePeriode ap : apList) {
			AnneePeriodePojo aPojoDeb = new AnneePeriodePojo();
			AnneePeriodePojo aPojoFin = new AnneePeriodePojo();
			AnneePeriodePojo aPojoLim = new AnneePeriodePojo();
			AnneePeriodePojo aPojoNbEval = new AnneePeriodePojo();
			aPojoDeb.setId("deb_" + ap.getPeriode().getId());
			aPojoDeb.setValue(DateUtils.asDate(ap.getDateDebut()));
			anneePojoList.add(aPojoDeb);

			aPojoFin.setId("fin_" + ap.getPeriode().getId());
			aPojoFin.setValue(DateUtils.asDate(ap.getDateFin()));
			anneePojoList.add(aPojoFin);

			aPojoLim.setId("limite_" + ap.getPeriode().getId());
			aPojoLim.setValue(DateUtils.asDate(ap.getDateLimite()));
			anneePojoList.add(aPojoLim);

			aPojoNbEval.setId("nbeval_" + ap.getPeriode().getId());
			aPojoNbEval.setNbEval(ap.getNbreEval());
			anneePojoList.add(aPojoNbEval);
		}
//		System.out.println(String.format("Liste de %s annee-periode-pojos", anneePojoList.size()));
		anneeScolaire.setAnneePeriodes(anneePojoList);
		anneeScolaire.setAnneeFin(anneeScolaire.getAnneeDebut() + 1);
		return anneeScolaire;
	}

	public List<AnneeScolaire> getByStatut(String statut) {

		List<AnneeScolaire> annees = new ArrayList<AnneeScolaire>();
		try {
			annees = AnneeScolaire.find("statut = ?1 ", statut).list();
		} catch (RuntimeException e) {
			e.printStackTrace();

		}
		return annees;
	}

	public AnneeScolaire getByEcoleAndAnneeDebut(Long ecole, Integer anneeDebut) {
		AnneeScolaire anneeScolaire = new AnneeScolaire();
		try {
			anneeScolaire = AnneeScolaire.find("ecole.id=?1 and anneeDebut =?2", ecole, anneeDebut).singleResult();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return anneeScolaire;
	}

	public List<AnneeScolaire> getByEcoleAndStatut(Long ecoleId, String statut) {

		List<AnneeScolaire> annees = new ArrayList<AnneeScolaire>();
		try {
			annees = AnneeScolaire.find("ecole.id = ?1 and statut = ?2", ecoleId, statut).list();
		} catch (RuntimeException e) {
			e.printStackTrace();

		}
		return annees;
	}

	@Transactional
	public AnneeScolaire create(AnneeScolaire annee) {
		annee.setDateCreation(new Date());
		annee.setDateUpdate(new Date());
		annee.persist();
		return annee;
	}

	// vérifier si la contrainte avoir un element d un niveau d'enseignement,
	// periodicite et d'année est UNIQUE
	public Boolean isUniqueContraintValid(Long niveauEnseignement, Integer periodicite, Integer annee) {
		long nbre = AnneeScolaire
				.find("niveauEnseignement.id =?1 and periodicite.id =?2 and annee=?3 and ecole is null",
						niveauEnseignement, periodicite, annee)
				.count();
		System.out.println("isUniqueContraintValid " + nbre);
		return nbre == 0;
	}

	@Transactional
	public AnneeScolaire handleOpenAnnee(AnneeScolaire annee) {

		// save annee
		annee.setLibelle(annee.getCustomLibelle());
		annee.setStatut(Constants.INITIALISE);
		if (annee.getId() != 0)
			update(annee.getId(), annee);
		else {
			if (!isUniqueContraintValid(annee.getNiveauEnseignement().getId(), annee.getPeriodicite().getId(),
					annee.getAnneeDebut()))
				throw new RuntimeException("Enregistrement existant déjà!!!");
			create(annee);
		}

		// save annee periode
		anneePeriodeService.handleAnneeToPeriode(annee);
		return AnneeScolaire.findById(annee.getId());
	}

	@Transactional
	public AnneeScolaire handleOpenAnneeToEcole(AnneeScolaire annee) {

		// Update delai
		if (annee.getDelaiNotes() != null && annee.getDelaiNotes() != 0) {
			AnneeScolaire an = AnneeScolaire.findById(annee.getId());
			if (annee.getDelaiNotes() != an.getDelaiNotes())
				an.setDelaiNotes(annee.getDelaiNotes());
		}
		// Update annee periode
		anneePeriodeService.handleUpdateAnneePeriodeToEcole(annee);
		return AnneeScolaire.findById(annee.getId());
	}

	@Transactional
	public AnneeScolaire update(long id, AnneeScolaire annee) {
		AnneeScolaire entity = AnneeScolaire.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setLibelle(annee.getCustomLibelle());
		entity.setAnneeDebut(annee.getAnneeDebut());
		entity.setDateUpdate(new Date());
		entity.setNbreEval(annee.getNbreEval());
		entity.setNiveauEnseignement(annee.getNiveauEnseignement());
		entity.setPeriodicite(annee.getPeriodicite());
		entity.setStatut(annee.getStatut());
		entity.setEcole(annee.getEcole());
		entity.setUser(annee.getUser());
		return entity;
	}

	@Transactional
	public void delete(long id) {
		AnneeScolaire entity = AnneeScolaire.findById(id);
		if (entity == null) {
			throw new NotFoundException(String.format("[id : %s] non trouvé", id));
		}
		entity.delete();
	}

	@Transactional
	public AnneeScolaire openAnnee(AnneeScolaire annee) {
		annee.setStatut(Constants.OUVERT);
		update(annee.getId(), annee);
		return annee;
	}

	public AnneeScolaire findMainAnneeByEcole(Ecole ecole) {
		// find annee ouverte by ecole
		List<AnneeScolaire> anneeOuverte = getByEcoleAndStatut(ecole.getId(), Constants.OUVERT);
		if (anneeOuverte.size() >= 1)
			return findCentralAnneeReference(anneeOuverte.get(0));
		return new AnneeScolaire();
	}

	public AnneeScolaire findCentralAnneeReference(AnneeScolaire ecoleAnneeOuvert) {
		AnneeScolaire centralAnnee = new AnneeScolaire();
		try {
			centralAnnee = AnneeScolaire
					.find("anneeDebut =?1 and niveauEnseignement.id=?2 and periodicite.id =?3 and ecole is null",
							ecoleAnneeOuvert.getAnneeDebut(), ecoleAnneeOuvert.getNiveauEnseignement().getId(),
							ecoleAnneeOuvert.getPeriodicite().getId())
					.singleResult();

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return centralAnnee;
	}

	@Transactional
	public AnneeScolaire clotureAnnee(AnneeScolaire annee) {
		// Archivage des bulletins
		AnneeScolaire anneeCentrale = findCentralAnneeReference(annee);
		bulletinService.updateBulletinStatut(annee.getEcole().getId(), anneeCentrale.getId(), Constants.ARCHIVE);

		annee.setStatut(Constants.CLOTURE);
		update(annee.getId(), annee);
		return annee;
	}

	@Transactional
	public void initAnneeEcole(Long ecoleId) {
		Ecole ecole = ecoleService.getById(ecoleId);
		if (ecole != null) {
			List<AnneeScolaire> annees = getCentralByStatutAndNiveauEnseignement(Constants.DIFFUSE,
					ecole.getNiveauEnseignement().getId());
			for (AnneeScolaire an : annees) {
				AnneeScolaire anneeTemp = new AnneeScolaire();
				anneeTemp.setLibelle(an.getCustomLibelle());
				anneeTemp.setAnneeDebut(an.getAnneeDebut());
				anneeTemp.setNbreEval(an.getNbreEval());
				anneeTemp.setNiveauEnseignement(an.getNiveauEnseignement());
				anneeTemp.setPeriodicite(an.getPeriodicite());
				anneeTemp.setStatut(Constants.DIFFUSE);
				anneeTemp.setEcole(ecole);
				anneeTemp.setUser(an.getUser());
				anneeTemp.setNiveau(Constants.ECOLE);
				create(anneeTemp);
				System.out.println(String.format("Init----> %s - %s", anneeTemp.getId(), anneeTemp.getLibelle()));
				anneePeriodeService.handleSharingToEcole(an.getId(), anneeTemp);
			}
		}
	}

	public List<AnneeScolaire> getCentralByStatutAndNiveauEnseignement(String statutId, Long niveauId) {
		List<AnneeScolaire> list = new ArrayList<AnneeScolaire>();
		try {
			list = AnneeScolaire.find("statut =?1 and niveauEnseignement.id = ?2 and ecole is null", statutId, niveauId)
					.list();
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return list;
	}

	@Transactional
	public AnneeScolaire sharing(AnneeScolaire annee) {
		List<Ecole> ecoles = ecoleService.getByNiveauEnseignement(annee.getNiveauEnseignement().getId());
		System.out.println("annee ids " + annee.getId());

		annee.setStatut(Constants.DIFFUSE);
		update(annee.getId(), annee);

		if (ecoles != null && ecoles.size() > 0)
			for (Ecole ecole : ecoles) {
				System.out.println(String.format("==> %s", ecole.getLibelle()));
				AnneeScolaire anneeTemp = new AnneeScolaire();
				anneeTemp.setLibelle(annee.getCustomLibelle());
				anneeTemp.setAnneeDebut(annee.getAnneeDebut());
				anneeTemp.setNbreEval(annee.getNbreEval());
				anneeTemp.setNiveauEnseignement(annee.getNiveauEnseignement());
				anneeTemp.setPeriodicite(annee.getPeriodicite());
				anneeTemp.setStatut(Constants.DIFFUSE);
				anneeTemp.setEcole(ecole);
				anneeTemp.setUser(annee.getUser());
				anneeTemp.setNiveau(Constants.ECOLE);
				anneeTemp.setDelaiNotes(Constants.DEFAULT_DELAI_SAISIE_NOTES);
				create(anneeTemp);
				System.out.println(String.format("----> %s - %s", anneeTemp.getId(), anneeTemp.getLibelle()));
				anneePeriodeService.handleSharingToEcole(annee.getId(), anneeTemp);
			}
		else
			throw new RuntimeException("Aucune école détectée - veuillez procéder à leur création");
		System.out.println("Diffusion effectuee pour " + ecoles.size() + " ecoles");
		return annee;
	}

	@Transactional
	public void handleDelete(long id) {
		AnneeScolaire annee = getById(id);
		// Suppression des années periode correspondantes
		anneePeriodeService.handleAnneeDeleteToCentral(annee);
		// Suppression de l'année
		delete(annee);
	}

	public List<AnneeScolaire> search(String libelle) {
		return AnneeScolaire.find("libelle", libelle).list();
	}

	public long count() {
		return AnneeScolaire.count();
	}

	Integer getIndex(Long id, List<SorterAnneePeriodePojo> list) {
		int index = -1;
		int cmpte = 0;
		for (SorterAnneePeriodePojo elmt : list) {
			if (elmt.getId() == id) {
				index = cmpte;
				break;
			}
			cmpte++;

		}
		return index;
	}

	public List<SorterAnneePeriodePojo> buildSorter(List<AnneePeriodePojo> anneePeriodePojos) {
		List<SorterAnneePeriodePojo> list = new ArrayList<SorterAnneePeriodePojo>();
		for (AnneePeriodePojo ap : anneePeriodePojos) {
			String[] pattern = ap.getId().split("_");
			if (pattern != null && pattern.length > 1) {
				Long id = Long.parseLong(pattern[1]);
				Integer index = getIndex(id, list);
				if (index == -1) {
					SorterAnneePeriodePojo sap = new SorterAnneePeriodePojo();
					sap.setId(id);
					if (pattern[0].equals("deb"))
						sap.setPeriodeDebut(ap.getValue());
					else if (pattern[0].equals("fin"))
						sap.setPeriodeFin(DateUtils.getLastTimeFromDate(ap.getValue()));
					list.add(sap);

				}else {
					if (pattern[0].equals("deb"))
						list.get(index).setPeriodeDebut(ap.getValue());
					else if (pattern[0].equals("fin"))
						list.get(index).setPeriodeFin(DateUtils.getLastTimeFromDate(ap.getValue()));
					
				}
			}

		}
		return list;
	}

	public AnneeInfoDto getAnneeInfo(Long ecoleId) {
		Date today = new Date();
		Gson g = new Gson();
		AnneeInfoDto infos = new AnneeInfoDto();
		List<AnneeScolaire> anneeOuverteList = getByEcoleAndStatut(ecoleId, Constants.OUVERT);
		AnneeScolaire anneeOuverte = new AnneeScolaire();
		if (anneeOuverteList.size() > 0) {
			anneeOuverte = populateEntityEcole(anneeOuverteList.get(0));
			List<SorterAnneePeriodePojo> sorterList = buildSorter(anneeOuverte.getAnneePeriodes());
			infos.setAnneeId(anneeOuverte.getId());
			infos.setAnneeLibelle(anneeOuverte.getCustomLibelle());
//			System.out.println(anneeOuverte.getCustomLibelle());
			for(SorterAnneePeriodePojo sap : sorterList) {
				infos.setSeverity(Constants.SEVERITY_SUCCESS);
				if(today.after(sap.getPeriodeDebut()) && today.before(sap.getPeriodeFin())) {
					Periode per = Periode.findById(sap.getId());
					infos.setDateDebut(sap.getPeriodeDebut());
					infos.setDateFin(sap.getPeriodeFin());
					infos.setPeriodeId(sap.getId());
					infos.setPeriodeLibelle(per.getLibelle());
					break;
				}
			}
			
		}else {
			infos.setAnneeLibelle("Aucune année ouverte");
			infos.setSeverity(Constants.SEVERITY_DANGER);
		}

		return infos;
	}
}
