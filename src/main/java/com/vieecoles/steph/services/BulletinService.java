package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import com.google.gson.Gson;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.steph.dto.BulletinDto;
import com.vieecoles.steph.dto.DetailBulletinDto;
import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.entities.AbsenceEleve;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.ClasseEleveMatiere;
import com.vieecoles.steph.entities.ClasseElevePeriode;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailBulletin;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Inscription;
import com.vieecoles.steph.entities.MoyenneAdjustment;
import com.vieecoles.steph.entities.NoteBulletin;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.pojos.CalculMoyenneExceptPojo;
import com.vieecoles.steph.projections.BulletinIdProjection;
import com.vieecoles.steph.projections.DetailBulletinIdProjection;
import com.vieecoles.steph.util.CommonUtils;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class BulletinService implements PanacheRepositoryBase<Bulletin, String> {

	@Inject
	NoteService noteService;

	@Inject
	InscriptionService inscriptionService;

	@Inject
	ClasseEleveService classeEleveService;

	@Inject
	PersonnelMatiereClasseService personnelMatiereClasseService;

	@Inject
	ClasseElevePeriodeService classeElevePeriodeService;

	@Inject
	ClasseEleveMatiereService classeEleveMatiereService;

	@Inject
	AbsenceService absenceService;

	@Inject
	MoyenneAdjustmentService adjustmentService;

	@Inject
	DetailBulletinService detailBulletinService;

	@Inject
	EcoleHasMatiereService ecoleHasMatiereService;

	Logger logger = Logger.getLogger(BulletinService.class.getName());
	Gson g = new Gson();

	@Transactional
	public void save(Bulletin bulletin) {
		try {
			UUID uuid = UUID.randomUUID();
			bulletin.setId(uuid.toString());
			bulletin.setDateCreation(new Date());
			bulletin.persist();
//			System.out.println("Bulletin id ::: " + bulletin.getId());
			logger.info("Bulletin persisté !!! ");
		} catch (RuntimeException e) {
			logger.warning("Bulletin non persisté !!! ");
			e.printStackTrace();
		}

//		Gson g = new Gson();
//		System.out.println(g.toJson(findById(bulletin.getId())));
	}

	@Transactional
	public void updateBulletinStatut(Long ecoleId, Long anneeId, String statut) {
		List<Bulletin> bulletinsToUpdate = new ArrayList<Bulletin>();
		try {
			bulletinsToUpdate = Bulletin.find("ecoleId =?1 and anneeId = ?2", ecoleId, anneeId).list();
			for (Bulletin bul : bulletinsToUpdate) {
//				Bulletin b = Bulletin.findById(bul.getId());
				bul.setStatut(statut);
			}
			logger.info(String.format("%s bulletin(s) archivés", bulletinsToUpdate.size()));
		} catch (RuntimeException r) {
			if (NoResultException.class.getName().equals(r.getClass().getName()))
				logger.info("Aucun blletin à mettre à jour trouvé");
			else {
				r.printStackTrace();
				throw new RuntimeException("Erreur ::: " + r.getMessage());
			}
		}
	}

	/*
	 * Obténir la liste des bulletins d'un élève pour une année
	 */
	public List<Bulletin> getBulletinsEleveByAnnee(Long anneeId, String matricule, Long classeId) {

		List<Bulletin> myBulletins = new ArrayList<Bulletin>();

		try {
			myBulletins = Bulletin
					.find("anneeId = ?1 and matricule = ?2 and classeId = ?3 ", anneeId, matricule, classeId).list();
			logger.info(myBulletins.size() + " bulletin(s) trouvé(s) l'élève de matricule " + matricule);
		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucun bulletin trouvé pour l'année pour l'élève de matricule " + matricule);
			else
				ex.printStackTrace();
		}

		return myBulletins;
	}

	/*
	 * Obténir les infos du bulletin d'un élève pour une période dans une année
	 */
	public BulletinDto getBulletinsEleveByAnneeAndPeriode(Long anneeId, String matricule, Long classeId,
			Long periodeId) {

		Bulletin myBulletin = new Bulletin();
		List<DetailBulletin> details = new ArrayList<DetailBulletin>();
		BulletinDto dto = new BulletinDto();
		List<DetailBulletinDto> detailsDto = new ArrayList<DetailBulletinDto>();

		try {
			myBulletin = Bulletin.find("anneeId = ?1 and matricule = ?2 and classeId = ?3 and periodeId = ?4", anneeId,
					matricule, classeId, periodeId).singleResult();
			try {

				details = DetailBulletin.find("bulletin.id = ?1 order by num_ordre", myBulletin.getId()).list();
				for (DetailBulletin det : details) {
					DetailBulletinDto detailDto = new DetailBulletinDto();
					detailDto.setMatiereId(det.getMatiereRealId());
					detailDto.setMatiereLibelle(det.getMatiereLibelle());
					detailDto.setMoyenne(det.getMoyenne());
					detailDto.setCoef(det.getCoef());
					detailDto.setMoyCoef(det.getMoyCoef());
					detailDto.setAppreciation(det.getAppreciation());
					detailDto.setCategorie(det.getCategorie());
					detailDto.setCategorieMatiere(det.getCategorieMatiere());
					detailDto.setRang(det.getRang());
					MoyenneAdjustment moyenneAdjustment = adjustmentService.getByAnneePeriodeMatriculeAndMatiere(
							anneeId, periodeId, matricule, detailDto.getMatiereId());
					if (moyenneAdjustment.getId() != null) {
						detailDto.setAdjustMoyenne(moyenneAdjustment.getMoyenne());
						if (moyenneAdjustment.getStatut() != null
								&& moyenneAdjustment.getStatut().equals(Constants.VALID)) {
							detailDto.setIsChecked(true);
							detailDto.setStatut(moyenneAdjustment.getStatut());
						} else {
							detailDto.setStatut(Constants.INVALID);
							detailDto.setIsChecked(false);
						}
					}
					detailsDto.add(detailDto);
				}

			} catch (RuntimeException ex) {
				if (ex.getClass().getName().equals(NoResultException.class.getName()))
					logger.info("Aucun Detail Bulletin trouvé pour l'année pour l'élève de matricule " + matricule);
				else
					ex.printStackTrace();
			}

//			populateMoyennesFrAndReligion(myBulletin.getId());

			dto.setAppreciation(myBulletin.getAppreciation());
			dto.setMoyGeneral(myBulletin.getMoyGeneral());
			dto.setMatricule(myBulletin.getMatricule());
			dto.setNom(myBulletin.getNom());
			dto.setPrenoms(myBulletin.getPrenoms());
			dto.setClasseId(myBulletin.getClasseId());
			dto.setLibelleClasse(myBulletin.getLibelleClasse());
			dto.setRang(myBulletin.getRang());
			dto.setPeriodeId(periodeId);
			dto.setLibellePeriode(myBulletin.getLibellePeriode());
			dto.setDetails(detailsDto);

		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucun bulletin trouvé pour l'année pour l'élève de matricule " + matricule);
			else
				ex.printStackTrace();
		}

		return dto;
	}

	@Transactional
	public void handleAdjustmentMoyenne(BulletinDto bulletinDto) {
		List<MoyenneAdjustment> list = new ArrayList<MoyenneAdjustment>();
//		Gson g = new Gson();
//		System.out.println(g.toJson(bulletinDto));
		if (bulletinDto != null && bulletinDto.getDetails() != null) {
			for (DetailBulletinDto dtb : bulletinDto.getDetails()) {
				MoyenneAdjustment adj = new MoyenneAdjustment();
				adj.setAnnee(bulletinDto.getAnneeId());
				adj.setMatricule(bulletinDto.getMatricule());
				adj.setPeriode(bulletinDto.getPeriodeId());
				adj.setMoyenne(dtb.getAdjustMoyenne());
				adj.setClasse(bulletinDto.getClasseId());
				adj.setStatut(dtb.getStatut());
				adj.setMatiere(dtb.getMatiereId());
				list.add(adj);
			}
		}
		try {
			adjustmentService.handleSave(list);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Opération non effectuée");
		}

	}

	public void update(Bulletin bulletin) {
		Bulletin b = Bulletin.findById(bulletin.getId());
		if (b == null) {
			throw new RuntimeException("Bulletin a mettre a jour non trouve");
		}

		b.setDateUpdate(new Date());
		b.setAppreciation(bulletin.getAppreciation());
		b.setMoyGeneral(bulletin.getMoyGeneral());
		b.setAdresseEcole(bulletin.getAdresseEcole());
		b.setAffecte(bulletin.getAffecte());

//		b.setAnneeId(null);
		b.setAnneeLibelle(bulletin.getAnneeLibelle());
		b.setBoursier(bulletin.getBoursier());
		b.setClasseId(bulletin.getClasseId());
		b.setEcoleId(bulletin.getEcoleId());
		// b.setCodePeriode(me.getPeriode().getCode());
		b.setCodeProfPrincipal(bulletin.getCodeProfPrincipal());
		b.setCodeEducateur(bulletin.getCodeEducateur());
		b.setCodeQr(bulletin.getCodeQr());
		b.setDateNaissance(bulletin.getDateNaissance());
		b.setEffectif(bulletin.getEffectif());
		b.setHeuresAbsJustifiees(bulletin.getHeuresAbsJustifiees());
		b.setHeuresAbsNonJustifiees(bulletin.getHeuresAbsNonJustifiees());
		b.setLibelleClasse(bulletin.getLibelleClasse());
		b.setLieuNaissance(bulletin.getLieuNaissance());
		b.setMatricule(bulletin.getMatricule());
		b.setMoyAn(bulletin.getMoyAn());
		b.setMoyAvg(bulletin.getMoyAvg());
		b.setMoyGeneral(bulletin.getMoyGeneral());
		b.setMoyMax(bulletin.getMoyMax());
		b.setMoyMin(bulletin.getMoyMin());
		b.setNationalite(bulletin.getNationalite());
		b.setNom(bulletin.getNom());
		b.setNomEcole(bulletin.getNomEcole());
		b.setNomPrenomProfPrincipal(bulletin.getNomPrenomProfPrincipal());
		b.setNomPrenomEducateur(bulletin.getNomPrenomEducateur());
		b.setPrenoms(bulletin.getPrenoms());
		b.setRangAn(bulletin.getRangAn());
		b.setRedoublant(bulletin.getRedoublant());
		b.setSexe(bulletin.getSexe());
		b.setStatutEcole(bulletin.getStatutEcole());
		b.setTelEcole(bulletin.getTelEcole());
		b.setTotalCoef(bulletin.getTotalCoef());
		b.setTotalMoyCoef(bulletin.getTotalMoyCoef());
		b.setLv2(bulletin.getLv2());
		b.setNumDecisionAffecte(bulletin.getNumDecisionAffecte());
		b.setIsClassed(bulletin.getIsClassed());
		b.setNature(bulletin.getNature());
		b.setNiveau(bulletin.getNiveau());
		b.setNiveauLibelle(bulletin.getNiveauLibelle());
		b.setOrdreNiveau(bulletin.getOrdreNiveau());
		b.setEcoleOrigine(bulletin.getEcoleOrigine());
		b.setNomSignataire(bulletin.getNomSignataire());
		b.setTransfert(bulletin.getTransfert());
		b.setIvoirien(bulletin.getIvoirien());
		b.setRang(bulletin.getRang());
		b.setHeuresAbsJustifiees(bulletin.getHeuresAbsJustifiees());
		b.setHeuresAbsNonJustifiees(bulletin.getHeuresAbsNonJustifiees());

		b.setTypeEvaluation(bulletin.getTypeEvaluation());
		b.setTypeEvaluationLibelle(bulletin.getTypeEvaluationLibelle());
		b.setNumeroEvaluation(bulletin.getNumeroEvaluation());
		b.setNumeroIEPP(bulletin.getNumeroIEPP());
		b.setMoyEvaluationInterne(bulletin.getMoyEvaluationInterne());
		b.setMoyEvaluationIEPP(bulletin.getMoyEvaluationIEPP());
		b.setMoyEvaluationPassage(bulletin.getMoyEvaluationPassage());

		b.setMoyFr(bulletin.getMoyFr());
		b.setCoefFr(bulletin.getCoefFr());
		b.setMoyCoefFr(bulletin.getMoyCoefFr());
		b.setAppreciationFr(bulletin.getAppreciationFr());
		b.setMoyReli(bulletin.getMoyReli());
		b.setAppreciationReli(bulletin.getAppreciationReli());

	}

	@Transactional
	public void removeAllBulletinsByClasseProcess(String classe, String annee, String periode) {
		long startTime = System.nanoTime();
		List<Bulletin> bulletins = new ArrayList<Bulletin>();
		List<BulletinIdProjection> bulletinsProject = new ArrayList<>();
		try {

			PanacheQuery<BulletinIdProjection> bullProjectIds = Bulletin
					.find("classeId = ?1 and anneeId= ?2 and periodeId = ?3 and statut =?4", Long.parseLong(classe),
							Long.parseLong(annee), Long.parseLong(periode), Constants.MODIFIABLE)
					.project(BulletinIdProjection.class);
			bulletinsProject = bullProjectIds.list();
			bulletins = Bulletin.find("classeId = ?1 and anneeId= ?2 and periodeId = ?3 and statut =?4",
					Long.parseLong(classe), Long.parseLong(annee), Long.parseLong(periode), Constants.MODIFIABLE)
					.list();
			for (BulletinIdProjection bulletin : bulletinsProject) {
				List<DetailBulletinIdProjection> details = DetailBulletin.find("bulletin.id = ?1", bulletin.getId())
						.project(DetailBulletinIdProjection.class).list();
				for (DetailBulletinIdProjection detail : details) {
					NoteBulletin.delete("detailBulletin.id = ?1", detail.getId());
				}
				DetailBulletin.delete("bulletin.id = ?1", bulletin.getId());
				InfosPersoBulletins.delete("idBulletin", bulletin.getId());

				Bulletin.delete("id", bulletin.getId());
				logger.info((details != null ? details.size() : 0) + " details de bulletins supprimés");
			}
			logger.info((bulletins != null ? bulletins.size() : 0) + " bulletins supprimés");
			System.out.println((bulletins != null ? bulletins.size() : 0) + " bulletins supprimés");

		} catch (RuntimeException e) {
			logger.warning("IN THE CATCH OF REMOVER");
			throw new RuntimeException(
					String.format("Erreur dans le process de suppression des bulletins [%s]", e.getMessage()));
		}
		long endTime = System.nanoTime();
		long durationInSeconds = (endTime - startTime) / 1000000000;
		System.out.println("Temps d'exécution suppression " + durationInSeconds + " secondes");
	}

	@Transactional
	public void removeAllBulletinsByClasseProcess_V2(String classe, String annee, String periode) {
		long startTime = System.nanoTime();
		try {
			// 1. Récupération des IDs des bulletins à supprimer
			List<String> bulletinIds = Bulletin
					.find("classeId = ?1 and anneeId = ?2 and periodeId = ?3 and statut = ?4", Long.parseLong(classe),
							Long.parseLong(annee), Long.parseLong(periode), Constants.MODIFIABLE)
					.project(BulletinIdProjection.class).list().stream().map(BulletinIdProjection::getId)
					.collect(Collectors.toList());

			if (bulletinIds.isEmpty()) {
				logger.info("Aucun bulletin à supprimer");
				return;
			}

			// 2. Récupération des IDs des détails correspondants
			List<String> detailIds = DetailBulletin.find("bulletin.id in ?1", bulletinIds)
					.project(DetailBulletinIdProjection.class).list().stream().map(DetailBulletinIdProjection::getId)
					.collect(Collectors.toList());

			// 3. Suppression en lots
			int batchSize = 1000;
			for (int i = 0; i < detailIds.size(); i += batchSize) {
				List<String> batch = detailIds.subList(i, Math.min(i + batchSize, detailIds.size()));
				NoteBulletin.delete("detailBulletin.id in ?1", batch);
			}

			for (int i = 0; i < bulletinIds.size(); i += batchSize) {
				List<String> batch = bulletinIds.subList(i, Math.min(i + batchSize, bulletinIds.size()));

				DetailBulletin.delete("bulletin.id in ?1", batch);
				InfosPersoBulletins.delete("idBulletin in ?1", batch);
				Bulletin.delete("id in ?1", batch);
			}

			logger.info(detailIds.size() + " details de bulletins supprimés");
			logger.info(bulletinIds.size() + " bulletins supprimés");

		} catch (RuntimeException e) {
			logger.warning("IN THE CATCH OF REMOVER");
			throw new RuntimeException(
					String.format("Erreur dans le process de suppression des bulletins [%s]", e.getMessage()));
		}
		long endTime = System.nanoTime();
		long durationInSeconds = (endTime - startTime) / 1000000000;
		System.out.println("Temps d'exécution suppression " + durationInSeconds + " secondes");
	}

	@Transactional
	public int handleSave(String classe, String annee, String periode) {
		try {
			logger.info(String.format("classe %s  annee %s  periode  %s", classe, annee, periode));
			List<MoyenneEleveDto> moyenneParEleve = new ArrayList<MoyenneEleveDto>();
			try {
				long startTime = System.nanoTime();
				moyenneParEleve = noteService.moyennesAndNotesHandle(classe, annee, periode);
				long endTime = System.nanoTime();
				long durationInSeconds = (endTime - startTime) / 1000000000;

				System.out.println("Temps d'exécution Calcul des moyennes: " + durationInSeconds + " secondes");
			} catch (RuntimeException r) {
				r.printStackTrace();
				throw r;
			}
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			Bulletin bulletin;
//			List<NoteBulletin> notesBulletin;
			NoteBulletin noteBulletin;
			List<Double> moyGenElevesList = new ArrayList<Double>();
			List<String> bulletinIdList = new ArrayList<String>();
			Integer countNonClasses = 0;

//			List<Message> messages = new ArrayList<Message>();

			logger.info(String.format("Nombre d'élèves concerné %s ", moyenneParEleve.size()));

			// Nous allons avant toute opération vider les enregistrements des bulletins,
			// des détails bulletins et des notes bulletins.
			// Pour éviter que si une matiere n existe plus pour une classe, il ne puisse
			// pas avoir des enregistrement caduque dans la base de données.
			removeAllBulletinsByClasseProcess_V2(classe, annee, periode);

			Map<Long, Inscription> infosInscriptionsByClasseMap = new HashMap<Long, Inscription>();

			// Obtention du professeur principal
			PersonnelMatiereClasse pp = personnelMatiereClasseService.findProfPrinc(Long.parseLong(annee),
					Long.parseLong(classe));

			// Liste des professeurs des matières enseignées dans une classe
			List<PersonnelMatiereClasse> professeurs = personnelMatiereClasseService
					.findProfesseursByClasse(Long.parseLong(annee), Long.parseLong(classe));

			// Obtention de l'éducateur
			PersonnelMatiereClasse educ = personnelMatiereClasseService.findEducateurClasse(Long.parseLong(annee),
					Long.parseLong(classe));

			// Existence liste d'éleves d'une classe classés ou pas regroupé par id élève
			Map<Long, ClasseElevePeriode> cepMap = classeElevePeriodeService
					.listByClasseAndAnneeAndPeriode(Long.parseLong(classe), Long.parseLong(annee),
							Long.parseLong(periode))
					.stream().collect(Collectors.toMap(c -> c.getEleve().getId(), c -> c));

//			Map<Long, AbsenceEleve> absenceEleveMap = absenceService.getListByClasseAndAnneeAndPeriode(Long.parseLong(annee),
//					Long.parseLong(periode)).stream().collect(Collectors.toMap(a -> a.getEleve().getId(), a -> a));

			List<ClasseEleveMatiere> cemList = classeEleveMatiereService.findByClasseAnneeAndPeriode(
					Long.parseLong(classe), Long.parseLong(annee), Long.parseLong(periode));

			for (MoyenneEleveDto me : moyenneParEleve) {
				long startTime = System.nanoTime();
//			 logger.info(g.toJson(me));
				logger.info(String.format("%s %s  %s  %s %s", me.getClasse().getEcole().getId(),
						me.getClasse().getLibelle(), me.getMoyenne(), me.getAppreciation(), me.getEleve().getId()));

				logger.info(String.format(" getByEleveAndEcoleAndAnnee  %s  %s %s %s", me.getEleve().getId(),
						me.getClasse().getEcole().getId(), Long.parseLong(annee), me.getClasse().getBranche().getId()));

				// Obtenir et classer les inscriptions par eleve
				if (infosInscriptionsByClasseMap.size() <= 0) {
					logger.info("Recupération des inscriptions par eleve et rangement des données en Map");
					infosInscriptionsByClasseMap = inscriptionService
							.getByClasseAndEcoleAndAnnee(me.getClasse().getId(), me.getClasse().getEcole().getId(),
									Long.parseLong(annee), me.getClasse().getBranche().getId())
							.stream().collect(Collectors.toMap(ins -> ins.getEleve().getId(), ins -> ins));
				}

				// Collecter toutes les moyennes des élèves pour déterminer la moyenne max, min
				// et avg
				if (!me.getIsClassed().equals(Constants.NON)) {
					moyGenElevesList.add(me.getMoyenne());
				} else {
					countNonClasses++;
				}

//				System.out.println("Niveau ens ::: " + me.getClasse().getEcole().getNiveauEnseignement().getId());

				bulletin = new Bulletin();
				bulletin = convert(me);
				bulletin.setAnneeId(Long.parseLong(annee));
				AnneeScolaire anDb = AnneeScolaire.findById(Long.parseLong(annee));
				Periode periodeDb = Periode.findById(Long.parseLong(periode));
				bulletin.setAnneeLibelle(anDb.getLibelle());
				bulletin.setPeriodeId(periodeDb.getId());
				bulletin.setLibellePeriode(periodeDb.getLibelle());

				try {
					bulletin.setNiveauEnseignementId(me.getClasse().getEcole().getNiveauEnseignement() != null
							? me.getClasse().getEcole().getNiveauEnseignement().getId()
							: null);
				} catch (RuntimeException r) {
					r.printStackTrace();
				}

				if (!pp.equals(new PersonnelMatiereClasse())) {
					bulletin.setCodeProfPrincipal(pp.getPersonnel().getCode());
					bulletin.setNomPrenomProfPrincipal(
							pp.getPersonnel().getNom() + " " + pp.getPersonnel().getPrenom());
				}
				if (!educ.equals(new PersonnelMatiereClasse())) {
					bulletin.setCodeEducateur(educ.getPersonnel().getCode());
					bulletin.setNomPrenomEducateur(
							educ.getPersonnel().getNom() + " " + educ.getPersonnel().getPrenom());
				}
				// marquer que l eleve est classé ou non
				ClasseElevePeriode cep = cepMap.getOrDefault(me.getEleve().getId(), null);
//						classeElevePeriodeService.findByClasseAndEleveAndAnneeAndPeriode(
//						Long.parseLong(classe), me.getEleve().getId(), Long.parseLong(annee), Long.parseLong(periode));
				if (cep == null)
					bulletin.setIsClassed(Constants.OUI);
				else
					bulletin.setIsClassed(cep.getIsClassed());

				// Mise à jour des heures d'absence

				AbsenceEleve absenceEleve = absenceService.getByAnneeAndEleveAndPeriode(Long.parseLong(annee),
						me.getEleve().getId(), Long.parseLong(periode));

				if (absenceEleve != null) {
					bulletin.setHeuresAbsJustifiees(
							absenceEleve.getAbsJustifiee() != null ? absenceEleve.getAbsJustifiee().toString() : "0");
					bulletin.setHeuresAbsNonJustifiees(
							absenceEleve.getAbsNonJustifiee() != null ? absenceEleve.getAbsNonJustifiee().toString()
									: "0");
				} else {
					bulletin.setHeuresAbsJustifiees("0");
					bulletin.setHeuresAbsNonJustifiees("0");
				}

				Inscription infosInscriptionsEleve = new Inscription();
				infosInscriptionsEleve = infosInscriptionsByClasseMap.getOrDefault(me.getEleve().getId(), null);
//							inscriptionService.getByEleveAndEcoleAndAnnee(me.getEleve().getId(),
//							me.getClasse().getEcole().getId(), Long.parseLong(annee),
//							me.getClasse().getBranche().getId());

				if (infosInscriptionsEleve != null) {
					System.out.println("infosInscriptionsEleve>>>> " + infosInscriptionsEleve.getId());
					bulletin.setAffecte(infosInscriptionsEleve.getAfecte());
					bulletin.setRedoublant(infosInscriptionsEleve.getRedoublant());
					bulletin.setBoursier(infosInscriptionsEleve.getBoursier());
					bulletin.setLv2(infosInscriptionsEleve.getLv2());
					bulletin.setNumDecisionAffecte(infosInscriptionsEleve.getNumDecisionAffecte());
					bulletin.setEcoleOrigine(infosInscriptionsEleve.getEcoleOrigine());
					bulletin.setTransfert(infosInscriptionsEleve.getTransfert());
					bulletin.setUrlPhoto(infosInscriptionsEleve.getUrlPhoto());
					bulletin.setIvoirien(infosInscriptionsEleve.getIvoirien());
				}

				logger.info("Création bulletin ...");
				save(bulletin);
				bulletinIdList.add(bulletin.getId());

				Double moyCoef = (double) 0;
				for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
					logger.info(
							String.format("bulletin id %s - matiere %s", bulletin.getId(), entry.getKey().getCode()));
					DetailBulletin flag = null;

					logger.info("--> Création de detail bulletin ");
					flag = new DetailBulletin();
					UUID idDetail = UUID.randomUUID();
					moyCoef = entry.getKey().getMoyenne() * Double.parseDouble(entry.getKey().getCoef());
					flag.setId(idDetail.toString());
					// Champ à renseigner avec le code
//						System.out.println("matiere id ---> "+entry.getKey().getId()+" "+(entry.getKey().getMatiere() == null ? "Matière source Nulle" : "Matiere ok"));
					flag.setMatiereCode(entry.getKey().getMatiere().getId().toString());
					flag.setMatiereId(entry.getKey().getMatiere().getId());
					// attribut de l'id de la matiere
					flag.setMatiereRealId(entry.getKey().getId());
					flag.setTestLourdNote(entry.getKey().getTestLourdNote());
					flag.setTestLourdNoteSur(entry.getKey().getTestLourdNoteSur());
					flag.setMatiereLibelle(entry.getKey().getLibelle());
					flag.setMoyenne(CommonUtils.roundDouble(entry.getKey().getMoyenne(), 2));
					flag.setMoyCoef(CommonUtils.roundDouble(moyCoef, 2));
					flag.setCoef(Double.valueOf(entry.getKey().getCoef()));
					flag.setAppreciation(entry.getKey().getAppreciation());
					flag.setRang(Integer.valueOf(entry.getKey().getRang()));
					flag.setNum_ordre(entry.getKey().getNumOrdre());
					flag.setCategorieMatiere(entry.getKey().getCategorie().getLibelle());
					flag.setCategorie(entry.getKey().getCategorie().getCode());
					flag.setBulletin(bulletin);
					flag.setBonus(entry.getKey().getBonus());
					flag.setPec(entry.getKey().getPec());
					flag.setParentMatiere(entry.getKey().getParentMatiereLibelle());
					flag.setMoyAn(entry.getKey().getMoyenneAnnuelle());
					flag.setRangAn(entry.getKey().getRangAnnuel());

					flag.setMoyenneIntermediaire(entry.getKey().getMoyenneIntermediaire());

					if (entry.getKey().getMoyenneAnnuelle() != null)
						flag.setAppreciationAn(
								CommonUtils.appreciation(Double.valueOf(entry.getKey().getMoyenneAnnuelle())));

					flag.setIsAdjustment(entry.getKey().getIsAdjustment());
					flag.setDateCreation(new Date());

					// marquer que l eleve est classé dans une matiere ou non
//					ClasseEleveMatiere cem = classeEleveMatiereService.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(
//							Long.parseLong(classe), entry.getKey().getId(), me.getEleve().getId(),
//							Long.parseLong(annee), Long.parseLong(periode));

					Optional<ClasseEleveMatiere> cemOption = cemList.stream()
							.filter(c -> c.getEleve().getId() == me.getEleve().getId()
									&& c.getMatiere().getId() == entry.getKey().getId())
							.findFirst();

					// Inscrire si oui ou non l'élève est classé dans la matiere
					if (cemOption.isPresent()) {
						flag.setIsRanked(cemOption.get().getIsClassed());
					} else {
						flag.setIsRanked(Constants.OUI);
					}

					logger.info("--> Categorie" + entry.getKey().getCategorie().getLibelle());

					Optional<PersonnelMatiereClasse> persOpt = professeurs.stream()
							.filter(p -> p.getMatiere() != null && p.getMatiere().getId() == entry.getKey().getId())
							.findFirst();

					// Ajout de l'enseignant de la matiere
//					PersonnelMatiereClasse pers = personnelMatiereClasseService.findProfesseurByMatiereAndClasse(
//							Long.parseLong(annee), Long.parseLong(classe), entry.getKey().getId());
					if (persOpt.isPresent() && persOpt.get().getPersonnel() != null) {
						flag.setNom_prenom_professeur(
								persOpt.get().getPersonnel().getNom() + " " + persOpt.get().getPersonnel().getPrenom());
						flag.setSexeProfesseur(persOpt.get().getPersonnel().getSexe());
					} else {
						flag.setNom_prenom_professeur("N/A");
					}

					flag.persist();
//					System.out.println("Detail -> " + flag.getId());
					UUID idNoteBul;
					for (Notes note : entry.getValue()) {
						if (note.getEvaluation().getPec() == 1) {
							logger.info(String.format("--> Creation notes %s/%s", note.getNote(),
									note.getEvaluation().getNoteSur()));
							noteBulletin = new NoteBulletin();
							idNoteBul = UUID.randomUUID();
							noteBulletin.setId(idNoteBul.toString());
							noteBulletin.setNote(note.getNote());
							noteBulletin.setNoteSur(note.getEvaluation().getNoteSur());
							noteBulletin.setDetailBulletin(flag);
							noteBulletin.persist();
						}
					}
				}
				long endTime = System.nanoTime();
				long durationInSeconds = (endTime - startTime) / 1000000000;

				System.out.println("Temps d'exécution pour l'élève ->" + me.getEleve().getMatricule() + " "
						+ durationInSeconds + " secondes");
			}

			Double maxMoy;
			Double minMoy;
			Double sumMoy;

			try {
				maxMoy = Collections.max(moyGenElevesList);
				minMoy = Collections.min(moyGenElevesList);

				sumMoy = moyGenElevesList.stream().mapToDouble(a -> a).sum();
			} catch (NoSuchElementException e) {
				e.printStackTrace();
				throw new RuntimeException("Aucune donnée trouvée");
			}
			int effectif = classeEleveService.getCountByClasseAnnee(Long.parseLong(classe), Long.parseLong(annee));
			Double avgMoy = sumMoy / (moyGenElevesList.size() != 0 ? moyGenElevesList.size() : 1);

			logger.info(String.format("Max= %s , Min = %s, Sum = %s, Avg = %s, effectif %s", maxMoy, minMoy, sumMoy,
					avgMoy, effectif));

			// Mise à jour des bulletins
			bulletin = new Bulletin();
			for (String id : bulletinIdList) {
				bulletin = Bulletin.findById(id);
				bulletin.setMoyMax(CommonUtils.roundDouble(maxMoy, 2));
				bulletin.setMoyMin(CommonUtils.roundDouble(minMoy, 2));
				bulletin.setMoyAvg(CommonUtils.roundDouble(avgMoy, 2));
				bulletin.setEffectif(effectif);
				bulletin.setEffectifNonClasse(String.valueOf(countNonClasses));
			}

			logger.info("*********** FIN DU CALCUL DES MOYENNES");
			return moyenneParEleve != null ? moyenneParEleve.size() : 0;
		} catch (Exception r) {
			r.printStackTrace();
			return 0;
		}
	}

	public CalculMoyenneExceptPojo populateMoyennesFrAndReligion(String bulletinId) {
		class MoyenneCoef {
			public Double moyenne;
			public Double coef;
		}
		CalculMoyenneExceptPojo moyResultObj = new CalculMoyenneExceptPojo();
		Bulletin bulletin = Bulletin.findById(bulletinId);
		if (bulletin != null) {

			List<DetailBulletin> details = detailBulletinService.getByBulletin(bulletin.getId());
			List<MoyenneCoef> moyFrList = new ArrayList<>();
			List<MoyenneCoef> moyReligionList = new ArrayList<>();
			for (DetailBulletin detail : details) {
				if (detail.getMatiereRealId() != null) {
					EcoleHasMatiere matiereEcole = ecoleHasMatiereService.findById(detail.getMatiereRealId());
					if (matiereEcole != null && matiereEcole.getMatiereParent() != null
							&& matiereEcole.getNiveauEnseignement().getCode()
									.equals(Constants.CODE_NIVEAU_ENS_SECONDAIRE)
							&& matiereEcole.getMatiereParent().getMatiere().getMatiereParent()
									.equals(Constants.ID_MATIERE_FRANCAIS_CENTRAL)) {
						MoyenneCoef moyCoef = new MoyenneCoef();
						moyCoef.moyenne = detail.getMoyenne();
						moyCoef.coef = detail.getCoef();
						moyFrList.add(moyCoef);
					}

				} else if (detail.getMatiereCode() != null) {
					EcoleHasMatiere matiereEcoleParent = ecoleHasMatiereService.getByEcoleAndCode(bulletin.getEcoleId(),
							detail.getMatiereCode());
					if (matiereEcoleParent != null
							&& matiereEcoleParent.getNiveauEnseignement().getCode()
									.equals(Constants.CODE_NIVEAU_ENS_SECONDAIRE)
							&& matiereEcoleParent.getMatiere().getMatiereParent() != null && matiereEcoleParent
									.getMatiere().getMatiereParent().equals(Constants.ID_MATIERE_FRANCAIS_CENTRAL)) {
						MoyenneCoef moyCoef = new MoyenneCoef();
						moyCoef.moyenne = detail.getMoyenne();
						moyCoef.coef = detail.getCoef();
						moyFrList.add(moyCoef);
					}
				}
				if (detail.getCategorie() != null && detail.getCategorie().equals(Constants.CODE_CATEGORIE_RELIGION)) {
					MoyenneCoef moyCoef = new MoyenneCoef();
					moyCoef.moyenne = detail.getMoyenne();
					moyCoef.coef = detail.getCoef();
					moyReligionList.add(moyCoef);
				}
			}
			if (moyFrList.size() > 0) {
				Double sumMoy = moyFrList.stream().mapToDouble(o -> o.moyenne * o.coef).reduce(0, (a, b) -> a + b);
				Double sumCoef = moyFrList.stream().mapToDouble(o -> o.coef).reduce(0, (a, b) -> a + b);
				Double moyFr = sumMoy / sumCoef;

				moyResultObj = new CalculMoyenneExceptPojo(Double.valueOf(CommonUtils.roundDouble(moyFr, 2)),
						Double.valueOf(sumCoef), Double.valueOf(CommonUtils.roundDouble(sumMoy, 2)),
						CommonUtils.appreciation(moyFr), null);
			}
			if (moyReligionList.size() > 0) {
				Double sumMoy = moyReligionList.stream().mapToDouble(o -> o.moyenne * o.coef).reduce(0,
						(a, b) -> a + b);
				Double sumCoef = moyReligionList.stream().mapToDouble(o -> o.coef).reduce(0, (a, b) -> a + b);
				Double moyFr = sumMoy / sumCoef;
				moyResultObj.setMoyExcpReligion(Double.valueOf(CommonUtils.roundDouble(moyFr, 2)));
			}
		}
//		Gson gson = new Gson();
//		System.out.println(gson.toJson(moyResultObj));
		return moyResultObj;
	}

	Bulletin convert(MoyenneEleveDto me) {

		Bulletin bul = new Bulletin();

		bul.setAdresseEcole(me.getClasse().getEcole().getAdresse());
		bul.setTelEcole(me.getClasse().getEcole().getTel());
		bul.setStatutEcole(me.getClasse().getEcole().getStatut());

		bul.setAffecte(null);
//		bul.setAnneeId(null);
//		bul.setAnneeLibelle(null);
		bul.setAppreciation(me.getAppreciation());
		bul.setBoursier(null);
		bul.setClasseId(me.getClasse().getId());
		bul.setEcoleId(me.getClasse().getEcole().getId());
		// bul.setCodePeriode(me.getPeriode().getCode());
		bul.setCodeProfPrincipal(null);
		bul.setCodeQr(null);
		bul.setDateNaissance(me.getEleve().getDateNaissance());
		bul.setEffectif(null);
		bul.setHeuresAbsJustifiees(null);
		bul.setHeuresAbsNonJustifiees(null);
		bul.setLibelleClasse(me.getClasse().getLibelle());
		// bul.setLibellePeriode(me.getPeriode().getLibelle());
		bul.setLieuNaissance(me.getEleve().getLieuNaissance());
		bul.setMatricule(me.getEleve().getMatricule());
		bul.setRang(me.getRang() != null ? Integer.parseInt(me.getRang()) : null);
		bul.setMoyAn(me.getMoyenneAnnuelle());
		bul.setRangAn(me.getRangAnnuel());
		bul.setApprAn(me.getApprAnnuelle());
//		bul.setMoyGeneral(me.getMoyenne().toString());
		bul.setMoyGeneral(me.getMoyenne());
//		bul.setMoyMax(null);
//		bul.setMoyMin(null);
		bul.setNationalite(me.getEleve().getNationalite());
		bul.setNom(me.getEleve().getNom());
		bul.setNomEcole(me.getClasse().getEcole().getLibelle());
//		bul.setNomPrenomProfPrincipal(null);
		bul.setPrenoms(me.getEleve().getPrenom());
		bul.setSexe(me.getEleve().getSexe());
		bul.setStatut(Constants.MODIFIABLE);
		bul.setStatutEcole(me.getClasse().getEcole().getStatut());
		bul.setTelEcole(me.getClasse().getEcole().getTel());
//		bul.setTotalCoef(null);
//		bul.setTotalMoyCoef(null);
//		bul.setUrlLogo(null);
		bul.setNiveau(me.getClasse().getBranche().getNiveau().getLibelle());
		bul.setNiveauLibelle(me.getClasse().getBranche().getNiveau().getLibelle());
		bul.setOrdreNiveau(me.getClasse().getBranche().getNiveau().getOrdre());
//		bul.setLv2(null);
//		bul.setNumDecisionAffecte(null);
//		bul.setNature(null);
//		bul.setNomPrenomEducateur(null);
//		bul.setCodeEducateur(null);
		bul.setNomSignataire(me.getClasse().getEcole().getNomSignataire());

		bul.setTypeEvaluation(me.getTypeEvaluation());
		bul.setTypeEvaluationLibelle(me.getTypeEvationLibelle());
		bul.setNumeroEvaluation(me.getNumeroEvaluation() != null ? Integer.parseInt(me.getNumeroEvaluation()) : 0);
		bul.setNumeroIEPP(me.getNumeroIEPP() != null ? Integer.parseInt(me.getNumeroIEPP()) : 0);
		bul.setMoyEvaluationInterne(me.getMoyenneInterne());
		bul.setMoyEvaluationIEPP(me.getMoyenneIEPP());
		bul.setMoyEvaluationPassage(me.getMoyennePassage());

		bul.setMoyFr(me.getMoyFr());
		bul.setMoyFrIntermediaire(me.getMoyFrIntermediaire());
		bul.setCoefFr(me.getCoefFr());
		bul.setMoyCoefFr(me.getMoyCoefFr());
		bul.setAppreciationFr(me.getAppreciationFr());
		bul.setMoyReli(me.getMoyReli());
		bul.setAppreciationReli(me.getAppreciationReli());
		bul.setRangFr(me.getRangFr());
		bul.setMoyFrAn(me.getMoyAnFr());
		bul.setRangFrAn(me.getRangAnFr());
		bul.setAppreciationFrAn(me.getAppreciationAnFr());

		return bul;
	}

	/*******************************************************************************
	 *                                                                             *
	 *             OPTIMISATION DU PROCESSUS D INSERTION DES BULLETINS                     *
	 *                                                                             *
	 *******************************************************************************/
	
	@Transactional
	public int handleSave_v2(String classe, String annee, String periode) {
	    long startTime = System.nanoTime();
	    
	    try {
	        logger.info(String.format("Début traitement - classe %s, annee %s, periode %s", classe, annee, periode));
	        
	        // 1. VALIDATION DES PARAMÈTRES
	        if (!isValidParameters(classe, annee, periode)) {
	            return 0;
	        }
	        
	        // 2. PRÉ-CHARGEMENT MASSIF DE TOUTES LES DONNÉES
	        BulletinProcessingContext context = preloadAllData(classe, annee, periode);
	        if (context.moyenneParEleve.isEmpty()) {
	            logger.warning("Aucun élève trouvé pour le traitement");
	            return 0;
	        }
	        
	        logger.info(String.format("Nombre d'élèves à traiter: %s", context.moyenneParEleve.size()));
	        
	        // 3. NETTOYAGE PRÉALABLE
	        removeAllBulletinsByClasseProcess_V2(classe, annee, periode);
	        
	        // 4. TRAITEMENT EN BATCH OPTIMISÉ
	        List<String> bulletinIdList = new ArrayList<>(context.moyenneParEleve.size());
	        List<Double> moyGenElevesList = new ArrayList<>(context.moyenneParEleve.size());
	        AtomicInteger countNonClasses = new AtomicInteger(0);
	        
	        // Traitement principal avec optimisations
	        processBulletinsOptimized(context, bulletinIdList, moyGenElevesList, countNonClasses);
	        
	        // 5. CALCUL FINAL ET MISE À JOUR EN BATCH
	        updateBulletinsWithStatistics(bulletinIdList, moyGenElevesList, countNonClasses.get(), 
	                Long.parseLong(classe), Long.parseLong(annee));
	        
	        long endTime = System.nanoTime();
	        long totalSeconds = (endTime - startTime) / 1_000_000_000;
	        logger.info(String.format("Traitement terminé en %d secondes pour %d élèves", 
	                totalSeconds, context.moyenneParEleve.size()));
	        
	        return context.moyenneParEleve.size();
	        
	    } catch (Exception e) {
	        logger.severe("Erreur lors du traitement: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("Échec du traitement des bulletins", e);
	    }
	}

	// ===================== CLASSES INTERNES POUR L'OPTIMISATION =====================

	/**
	 * Contexte contenant toutes les données pré-chargées
	 */
	private static class BulletinProcessingContext {
	    List<MoyenneEleveDto> moyenneParEleve;
	    Map<Long, Inscription> inscriptionsMap;
	    Map<Long, ClasseElevePeriode> cepMap;
	    Map<Long, AbsenceEleve> absenceMap;
	    List<ClasseEleveMatiere> cemList;
	    Map<Long, ClasseEleveMatiere> cemByEleveAndMatiere;
	    List<PersonnelMatiereClasse> professeurs;
	    Map<Long, PersonnelMatiereClasse> professeursMap;
	    PersonnelMatiereClasse profPrincipal;
	    PersonnelMatiereClasse educateur;
	    AnneeScolaire anneeScolaire;
	    Periode periodeEntity;
	    Long classeId;
	    Long anneeId;
	    Long periodeId;
	}

	// ===================== MÉTHODES D'OPTIMISATION =====================

	private boolean isValidParameters(String classe, String annee, String periode) {
	    try {
	        Long.parseLong(classe);
	        Long.parseLong(annee);
	        Long.parseLong(periode);
	        return true;
	    } catch (NumberFormatException e) {
	        logger.severe("Paramètres invalides: " + e.getMessage());
	        return false;
	    }
	}

	/**
	 * PRÉ-CHARGEMENT MASSIF ET OPTIMISÉ DE TOUTES LES DONNÉES
	 */
	private BulletinProcessingContext preloadAllData(String classe, String annee, String periode) {
	    BulletinProcessingContext context = new BulletinProcessingContext();
	    
	    context.classeId = Long.parseLong(classe);
	    context.anneeId = Long.parseLong(annee);
	    context.periodeId = Long.parseLong(periode);
	    
	    try {
	        // Calcul des moyennes (opération la plus coûteuse)
	        long startMoyennes = System.nanoTime();
	        context.moyenneParEleve = noteService.moyennesAndNotesHandle(classe, annee, periode);
	        long endMoyennes = System.nanoTime();
	        logger.info(String.format("Calcul moyennes: %d secondes", 
	                (endMoyennes - startMoyennes) / 1_000_000_000));
	        
	        if (context.moyenneParEleve.isEmpty()) {
	            return context;
	        }
	        
	        // PRÉ-CHARGEMENT SÉQUENTIEL SÉCURISÉ DES DONNÉES
	        // Le parallélisme avec DB peut causer des problèmes de connexions, deadlocks, et transactions
	        
	        // 1. Données d'entités simples (peu coûteuses)
	        logger.info("Chargement des entités de base...");
	        context.anneeScolaire = AnneeScolaire.findById(context.anneeId);
	        context.periodeEntity = Periode.findById(context.periodeId);
	        
	        // 2. Données de la classe (référence commune)
	        logger.info("Chargement des données de classe...");
	        MoyenneEleveDto firstEleve = context.moyenneParEleve.get(0);
	        
	        // 3. Données élèves par ordre de priorité/dépendance
	        logger.info("Chargement des inscriptions...");
	        List<Inscription> inscriptions = inscriptionService
	                .getByClasseAndEcoleAndAnnee(firstEleve.getClasse().getId(), 
	                        firstEleve.getClasse().getEcole().getId(), context.anneeId, 
	                        firstEleve.getClasse().getBranche().getId());
	        context.inscriptionsMap = inscriptions.stream()
	                .collect(Collectors.toMap(ins -> ins.getEleve().getId(), ins -> ins));
	        
	        logger.info("Chargement des périodes élèves...");
	        List<ClasseElevePeriode> cepList = classeElevePeriodeService
	                .listByClasseAndAnneeAndPeriode(context.classeId, context.anneeId, context.periodeId);
	        context.cepMap = cepList.stream()
	                .collect(Collectors.toMap(c -> c.getEleve().getId(), c -> c));
	        
	        logger.info("Chargement des absences...");
	        List<AbsenceEleve> absences = absenceService
	                .getListByAnneeAndPeriode(context.anneeId, context.periodeId);
	        context.absenceMap = absences.stream()
	                .collect(Collectors.toMap(a -> a.getEleve().getId(), a -> a));
	        
	        // 4. Données matières et professeurs
	        logger.info("Chargement des matières élèves...");
	        context.cemList = classeEleveMatiereService.findByClasseAnneeAndPeriode(
	                context.classeId, context.anneeId, context.periodeId);
	        
	        // Index optimisé : clé composite (eleveId + matiereId)
	        context.cemByEleveAndMatiere = context.cemList.stream()
	                .collect(Collectors.toMap(
	                        cem -> cem.getEleve().getId() * 1000000L + cem.getMatiere().getId(),
	                        cem -> cem
	                ));
	        
	        logger.info("Chargement des professeurs...");
	        context.professeurs = personnelMatiereClasseService.findProfesseursByClasse(context.anneeId, context.classeId);
	        context.professeursMap = context.professeurs.stream()
	                .filter(p -> p.getMatiere() != null)
	                .collect(Collectors.toMap(p -> p.getMatiere().getId(), p -> p));
	        
	        context.profPrincipal = personnelMatiereClasseService.findProfPrinc(context.anneeId, context.classeId);
	        context.educateur = personnelMatiereClasseService.findEducateurClasse(context.anneeId, context.classeId);
	        
	        logger.info("Pré-chargement terminé avec succès");
	        
	    } catch (Exception e) {
	        logger.severe("Erreur lors du pré-chargement: " + e.getMessage());
	        throw new RuntimeException("Échec du pré-chargement", e);
	    }
	    
	    return context;
	}

	/**
	 * TRAITEMENT OPTIMISÉ DES BULLETINS
	 */
	private void processBulletinsOptimized(BulletinProcessingContext context, List<String> bulletinIdList,
	                                     List<Double> moyGenElevesList, AtomicInteger countNonClasses) {
	    
	    // Préparation des batches pour insertion en masse
	    List<Bulletin> bulletinsBatch = new ArrayList<>(context.moyenneParEleve.size());
	    List<DetailBulletin> detailsBatch = new ArrayList<>();
	    List<NoteBulletin> notesBatch = new ArrayList<>();
	    
	    for (MoyenneEleveDto me : context.moyenneParEleve) {
	        try {
	            // Création du bulletin principal
	            Bulletin bulletin = createBulletinOptimized(me, context);
	            // Recuperation des ids des bulletins
	            bulletinIdList.add(bulletin.getId());
	            bulletinsBatch.add(bulletin);
	            
	            // Collecte des moyennes
	            if (!Constants.NON.equals(me.getIsClassed())) {
	                moyGenElevesList.add(me.getMoyenne());
	            } else {
	                countNonClasses.incrementAndGet();
	            }
	            
	            // Traitement des matières et création des détails
	            processMatieresForBulletin(me, bulletin, context, detailsBatch, notesBatch);
	            
	        } catch (Exception e) {
	            logger.warning("Erreur traitement élève " + me.getEleve().getMatricule() + ": " + e.getMessage());
	        }
	    }
	    
	    // INSERTION EN MASSE
	    saveBatchData(bulletinsBatch, detailsBatch, notesBatch);
	}

	private Bulletin createBulletinOptimized(MoyenneEleveDto me, BulletinProcessingContext context) {
	    Bulletin bulletin = convert(me);
	    UUID uuid = UUID.randomUUID();
		bulletin.setId(uuid.toString());
	    bulletin.setAnneeId(context.anneeId);
	    bulletin.setAnneeLibelle(context.anneeScolaire.getLibelle());
	    bulletin.setPeriodeId(context.periodeEntity.getId());
	    bulletin.setLibellePeriode(context.periodeEntity.getLibelle());
	    
	    // Niveau d'enseignement
	    try {
	        bulletin.setNiveauEnseignementId(me.getClasse().getEcole().getNiveauEnseignement() != null
	                ? me.getClasse().getEcole().getNiveauEnseignement().getId() : null);
	    } catch (Exception e) {
	        logger.warning("Erreur niveau enseignement: " + e.getMessage());
	    }
	    
	    // Personnel avec cache
	    if (context.profPrincipal != null && context.profPrincipal.getPersonnel() != null) {
	        bulletin.setCodeProfPrincipal(context.profPrincipal.getPersonnel().getCode());
	        bulletin.setNomPrenomProfPrincipal(context.profPrincipal.getPersonnel().getNom() + " " + 
	                context.profPrincipal.getPersonnel().getPrenom());
	    }
	    
	    if (context.educateur != null && context.educateur.getPersonnel() != null) {
	        bulletin.setCodeEducateur(context.educateur.getPersonnel().getCode());
	        bulletin.setNomPrenomEducateur(context.educateur.getPersonnel().getNom() + " " + 
	                context.educateur.getPersonnel().getPrenom());
	    }
	    
	    // Classification avec cache
	    ClasseElevePeriode cep = context.cepMap.get(me.getEleve().getId());
	    bulletin.setIsClassed(cep != null ? cep.getIsClassed() : Constants.OUI);
	    
	    // Absences avec cache
	    AbsenceEleve absence = context.absenceMap.get(me.getEleve().getId());
	    if (absence != null) {
	        bulletin.setHeuresAbsJustifiees(absence.getAbsJustifiee() != null ? absence.getAbsJustifiee().toString() : "0");
	        bulletin.setHeuresAbsNonJustifiees(absence.getAbsNonJustifiee() != null ? absence.getAbsNonJustifiee().toString() : "0");
	    } else {
	        bulletin.setHeuresAbsJustifiees("0");
	        bulletin.setHeuresAbsNonJustifiees("0");
	    }
	    
	    // Inscriptions avec cache
	    Inscription inscription = context.inscriptionsMap.get(me.getEleve().getId());
	    if (inscription != null) {
	        bulletin.setAffecte(inscription.getAfecte());
	        bulletin.setRedoublant(inscription.getRedoublant());
	        bulletin.setBoursier(inscription.getBoursier());
	        bulletin.setLv2(inscription.getLv2());
	        bulletin.setNumDecisionAffecte(inscription.getNumDecisionAffecte());
	        bulletin.setEcoleOrigine(inscription.getEcoleOrigine());
	        bulletin.setTransfert(inscription.getTransfert());
	        bulletin.setUrlPhoto(inscription.getUrlPhoto());
	        bulletin.setIvoirien(inscription.getIvoirien());
	    }
	    
	    return bulletin;
	}

	private void processMatieresForBulletin(MoyenneEleveDto me, Bulletin bulletin, BulletinProcessingContext context,
	                                      List<DetailBulletin> detailsBatch, List<NoteBulletin> notesBatch) {
	    
	    for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
	        EcoleHasMatiere matiere = entry.getKey();
	        
	        // Création du détail bulletin
	        DetailBulletin detail = new DetailBulletin();
	        detail.setId(UUID.randomUUID().toString());
	        
	        // Données de base
	        Double moyCoef = matiere.getMoyenne() * Double.parseDouble(matiere.getCoef());
	        detail.setMatiereCode(matiere.getMatiere().getId().toString());
	        detail.setMatiereId(matiere.getMatiere().getId());
	        detail.setMatiereRealId(matiere.getId());
	        detail.setTestLourdNote(matiere.getTestLourdNote());
	        detail.setTestLourdNoteSur(matiere.getTestLourdNoteSur());
	        detail.setMatiereLibelle(matiere.getLibelle());
	        detail.setMoyenne(CommonUtils.roundDouble(matiere.getMoyenne(), 2));
	        detail.setMoyCoef(CommonUtils.roundDouble(moyCoef, 2));
	        detail.setCoef(Double.valueOf(matiere.getCoef()));
	        detail.setAppreciation(matiere.getAppreciation());
	        detail.setRang(Integer.valueOf(matiere.getRang()));
	        detail.setNum_ordre(matiere.getNumOrdre());
	        detail.setCategorieMatiere(matiere.getCategorie().getLibelle());
	        detail.setCategorie(matiere.getCategorie().getCode());
	        detail.setBulletin(bulletin);
	        detail.setBonus(matiere.getBonus());
	        detail.setPec(matiere.getPec());
	        detail.setParentMatiere(matiere.getParentMatiereLibelle());
	        detail.setMoyAn(matiere.getMoyenneAnnuelle());
	        detail.setRangAn(matiere.getRangAnnuel());
	        detail.setMoyenneIntermediaire(matiere.getMoyenneIntermediaire());
	        detail.setIsAdjustment(matiere.getIsAdjustment());
	        detail.setDateCreation(new Date());
	        
	        if (matiere.getMoyenneAnnuelle() != null) {
	            detail.setAppreciationAn(CommonUtils.appreciation(Double.valueOf(matiere.getMoyenneAnnuelle())));
	        }
	        
	        // Classification avec index optimisé
	        Long compositeKey = me.getEleve().getId() * 1000000L + matiere.getId();
	        ClasseEleveMatiere cem = context.cemByEleveAndMatiere.get(compositeKey);
	        detail.setIsRanked(cem != null ? cem.getIsClassed() : Constants.OUI);
	        
	        // Professeur avec cache
	        PersonnelMatiereClasse professeur = context.professeursMap.get(matiere.getId());
	        if (professeur != null && professeur.getPersonnel() != null) {
	            detail.setNom_prenom_professeur(professeur.getPersonnel().getNom() + " " + 
	                    professeur.getPersonnel().getPrenom());
	            detail.setSexeProfesseur(professeur.getPersonnel().getSexe());
	        } else {
	            detail.setNom_prenom_professeur("N/A");
	        }
	        
	        detailsBatch.add(detail);
	        
	        // Traitement des notes
	        for (Notes note : entry.getValue()) {
	            if (note.getEvaluation().getPec() == 1) {
	                NoteBulletin noteBulletin = new NoteBulletin();
	                noteBulletin.setId(UUID.randomUUID().toString());
	                noteBulletin.setNote(note.getNote());
	                noteBulletin.setNoteSur(note.getEvaluation().getNoteSur());
	                noteBulletin.setDetailBulletin(detail);
	                notesBatch.add(noteBulletin);
	            }
	        }
	    }
	}

	/**
	 * SAUVEGARDE SÉQUENTIELLE SÉCURISÉE AVEC GESTION TRANSACTIONNELLE
	 * Évite les problèmes de concurrence et de gestion des connexions DB
	 */
	private void saveBatchData(List<Bulletin> bulletins, List<DetailBulletin> details, List<NoteBulletin> notes) {
	    logger.info("Début de la sauvegarde séquentielle...");
	    
	    try {
	        // Configuration pour optimiser les performances sans parallélisme
	        int batchSize = 20; // Taille réduite pour éviter la surcharge mémoire/transaction
	        
	        // 1. BULLETINS - Sauvegarde par petits lots séquentiels
	        logger.info("Sauvegarde des bulletins...");
	        for (int i = 0; i < bulletins.size(); i += batchSize) {
	            List<Bulletin> batch = bulletins.subList(i, Math.min(i + batchSize, bulletins.size()));
	            
	            // Traitement séquentiel avec gestion d'erreur par lot
	            for (Bulletin bulletin : batch) {
	                try {
	                    //bulletin.persist();
	        			bulletin.setDateCreation(new Date());
	        			bulletin.persist();
	                } catch (Exception e) {
	                    logger.severe("Erreur sauvegarde bulletin " + bulletin.getId() + ": " + e.getMessage());
	                    throw new RuntimeException("Échec sauvegarde bulletin", e);
	                }
	            }
	            
	            // Flush périodique pour libérer la mémoire et valider les transactions
	            if ((i + batchSize) % 100 == 0) {
	                logger.info(String.format("Bulletins sauvegardés: %d/%d", i + batchSize, bulletins.size()));
	                // Force la validation et libère les ressources
	                getEntityManager().flush();
	                getEntityManager().clear();
	            }
	        }
	        
	        // 2. DÉTAILS BULLETINS - Après validation complète des bulletins
	        logger.info("Sauvegarde des détails bulletins...");
	        for (int i = 0; i < details.size(); i += batchSize) {
	            List<DetailBulletin> batch = details.subList(i, Math.min(i + batchSize, details.size()));
	            
	            for (DetailBulletin detail : batch) {
	                try {
	                    detail.persist();
	                } catch (Exception e) {
	                    logger.severe("Erreur sauvegarde détail " + detail.getId() + ": " + e.getMessage());
	                    throw new RuntimeException("Échec sauvegarde détail bulletin", e);
	                }
	            }
	            
	            if ((i + batchSize) % 100 == 0) {
	                logger.info(String.format("Détails sauvegardés: %d/%d", i + batchSize, details.size()));
	                getEntityManager().flush();
	                getEntityManager().clear();
	            }
	        }
	        
	        // 3. NOTES BULLETINS - En dernier après validation des détails
	        logger.info("Sauvegarde des notes bulletins...");
	        for (int i = 0; i < notes.size(); i += batchSize) {
	            List<NoteBulletin> batch = notes.subList(i, Math.min(i + batchSize, notes.size()));
	            
	            for (NoteBulletin note : batch) {
	                try {
	                    note.persist();
	                } catch (Exception e) {
	                    logger.severe("Erreur sauvegarde note " + note.getId() + ": " + e.getMessage());
	                    throw new RuntimeException("Échec sauvegarde note bulletin", e);
	                }
	            }
	            
	            if ((i + batchSize) % 100 == 0) {
	                logger.info(String.format("Notes sauvegardées: %d/%d", i + batchSize, notes.size()));
	                getEntityManager().flush();
	                getEntityManager().clear();
	            }
	        }
	        
	        // Validation finale
	        getEntityManager().flush();
	        
	        logger.info(String.format("Sauvegarde complète réussie: %d bulletins, %d détails, %d notes", 
	                bulletins.size(), details.size(), notes.size()));
	                
	    } catch (Exception e) {
	        logger.severe("Erreur critique lors de la sauvegarde: " + e.getMessage());
	        // En cas d'erreur, on laisse la transaction se rollback naturellement
	        throw new RuntimeException("Échec critique de la sauvegarde", e);
	    }
	}
	
	int nombreElementsTraités(int finIntervalle, int tailleTotale) {
		int jauge = finIntervalle - tailleTotale;
		if(jauge <= 0) {
			return tailleTotale;
		}
		return finIntervalle;
	}

	/**
	 * MISE À JOUR DES STATISTIQUES SÉCURISÉE
	 * Traitement par petits lots pour éviter les timeouts et problèmes de mémoire
	 */
	private void updateBulletinsWithStatistics(List<String> bulletinIds, List<Double> moyennes, 
	                                         int countNonClasses, Long classeId, Long anneeId) {
	    if (moyennes.isEmpty()) {
	        throw new RuntimeException("Aucune donnée trouvée pour les statistiques");
	    }
	    
	    // Calcul des statistiques
	    double maxMoy = Collections.max(moyennes);
	    double minMoy = Collections.min(moyennes);
	    double sumMoy = moyennes.stream().mapToDouble(Double::doubleValue).sum();
	    double avgMoy = sumMoy / moyennes.size();
	    int effectif = classeEleveService.getCountByClasseAnnee(classeId, anneeId);
	    
	    logger.info(String.format("Statistiques: Max=%.2f, Min=%.2f, Avg=%.2f, Effectif=%d, Non classés=%d", 
	            maxMoy, minMoy, avgMoy, effectif, countNonClasses));
	    
	    // Mise à jour séquentielle par petits lots
	    try {
	        int batchSize = 10; // Taille encore plus petite pour les mises à jour
	        int processedCount = 0;
	        
	        for (int i = 0; i < bulletinIds.size(); i += batchSize) {
	            List<String> batch = bulletinIds.subList(i, Math.min(i + batchSize, bulletinIds.size()));
	            
	            for (String id : batch) {
	                try {
	                    Bulletin bulletin = Bulletin.findById(id);
	                    if (bulletin != null) {
	                        bulletin.setMoyMax(CommonUtils.roundDouble(maxMoy, 2));
	                        bulletin.setMoyMin(CommonUtils.roundDouble(minMoy, 2));
	                        bulletin.setMoyAvg(CommonUtils.roundDouble(avgMoy, 2));
	                        bulletin.setEffectif(effectif);
	                        bulletin.setEffectifNonClasse(String.valueOf(countNonClasses));
	                        processedCount++;
	                    } else {
	                        logger.warning("Bulletin non trouvé pour mise à jour: " + id);
	                    }
	                } catch (Exception e) {
	                    logger.severe("Erreur mise à jour bulletin " + id + ": " + e.getMessage());
	                    throw new RuntimeException("Échec mise à jour bulletin " + id, e);
	                }
	            }
	            
	            // Flush périodique
	            if ((i + batchSize) % 50 == 0) {
	                getEntityManager().flush();
	                logger.info(String.format("Bulletins mis à jour: %d/%d", processedCount, bulletinIds.size()));
	            }
	        }
	        
	        // Validation finale
	        getEntityManager().flush();
	        logger.info(String.format("Mise à jour des statistiques terminée: %d bulletins traités", processedCount));
	        
	    } catch (Exception e) {
	        logger.severe("Erreur lors de la mise à jour des statistiques: " + e.getMessage());
	        throw new RuntimeException("Échec de mise à jour des statistiques", e);
	    }
	}

}
