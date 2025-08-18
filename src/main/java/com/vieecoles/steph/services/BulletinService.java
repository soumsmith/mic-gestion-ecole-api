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
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

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

	/**
	 * NOUVEAU PROCESSUS D INSERTION DES BULLETINS
	 */

}
