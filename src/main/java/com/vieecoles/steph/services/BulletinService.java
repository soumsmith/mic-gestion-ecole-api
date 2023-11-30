package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.vieecoles.entities.InfosPersoBulletins;
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
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.entities.NoteBulletin;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.util.CommonUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

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

	Logger logger = Logger.getLogger(BulletinService.class.getName());
	Gson g = new Gson();

	public void save(Bulletin bulletin) {
		try {
			UUID uuid = UUID.randomUUID();
			bulletin.setId(uuid.toString());
			bulletin.setDateCreation(new Date());
			bulletin.persist();
			logger.info("Bulletin persisté !!! ");
		} catch (RuntimeException e) {
			logger.warning("Bulletin non persisté !!! ");
			e.printStackTrace();
		}

//		Gson g = new Gson();
//		System.out.println(g.toJson(findById(bulletin.getId())));
	}

	public void updateBulletinStatut(Long ecoleId, Long anneeId, String statut) {
		List<Bulletin> bulletinsToUpdate = new ArrayList<Bulletin>();
		try {
			bulletinsToUpdate = Bulletin.find("ecoleId =?1 and anneeId = ?2", ecoleId, anneeId).list();
			for (Bulletin bul : bulletinsToUpdate) {
				Bulletin b = Bulletin.findById(bul.getId());
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

	}

	@Transactional
	public void removeAllBulletinsByClasseProcess(String classe, String annee, String periode) {

		List<Bulletin> bulletins = new ArrayList<Bulletin>();
		Long bulletinsArchives = 0L;
		try {

			bulletins = Bulletin.find("classeId = ?1 and anneeId= ?2 and periodeId = ?3 and statut =?4",
					Long.parseLong(classe), Long.parseLong(annee), Long.parseLong(periode), Constants.MODIFIABLE)
					.list();
			for (Bulletin bulletin : bulletins) {
				List<DetailBulletin> details = DetailBulletin.find("bulletin.id = ?1", bulletin.getId()).list();
				for (DetailBulletin detail : details) {
					List<NoteBulletin> notesBulletin = NoteBulletin.find("detailBulletin.id", detail.getId()).list();
					for (NoteBulletin note : notesBulletin) {
						NoteBulletin.delete("id", note.getId());
					}
					DetailBulletin.delete("id", detail.getId());
					logger.info((notesBulletin != null ? notesBulletin.size() : 0) + " Notes de bulletins supprimées");
				}
				try {
					List<InfosPersoBulletins> infoBul = InfosPersoBulletins.find("idBulletin", bulletin.getId()).list();
					for (InfosPersoBulletins info : infoBul) {
						InfosPersoBulletins.deleteById(info.getId());
					}
					logger.info(String.format("%s informations personnelles de bulletin supprimées", infoBul.size()));
				} catch (RuntimeException e) {
					if (e.getClass().equals(NoResultException.class))
						logger.info(bulletin.getId() + " inexistant dans InfosPersoBulletins");
					else {
						e.printStackTrace();
					}
				}
				Bulletin.delete("id", bulletin.getId());
				logger.info((details != null ? details.size() : 0) + " details de bulletins supprimés");
			}
			logger.info((bulletins != null ? bulletins.size() : 0) + " bulletins supprimés");

		} catch (RuntimeException e) {
			logger.warning("IN THE CATCH OF REMOVER");
			if (e.getClass() == NoResultException.class) {
				logger.info("Aucun Bulletin à supprimer trouvé");
				bulletinsArchives = Bulletin.find("classeId = ?1 and anneeId= ?2 and periodeId = ?3 and statut =?4",
						Long.parseLong(classe), Long.parseLong(annee), Long.parseLong(periode), Constants.ARCHIVE)
						.count();
				if (bulletinsArchives != 0L) {
					throw new RuntimeException("Les bulletins sont archivés, aucune modification possible!");
				}
			} else
				throw new RuntimeException(
						String.format("Erreur dans le process de suppression des bulletins [%s]", e.getMessage()));
		}
//		throw new RuntimeException("COUCOU !!!!!");

	}

	@Transactional
	public int handleSave(String classe, String annee, String periode) {
		try {
			logger.info(String.format("classe %s  annee %s  periode  %s", classe, annee, periode));
			List<MoyenneEleveDto> moyenneParEleve = new ArrayList<MoyenneEleveDto>();
			try {
				moyenneParEleve = noteService.moyennesAndNotesHandle(classe, annee, periode);
			} catch (RuntimeException r) {
				r.printStackTrace();
				throw r;
			}
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			Bulletin bulletin;
			List<NoteBulletin> notesBulletin;
			NoteBulletin noteBulletin;
			List<Double> moyGenElevesList = new ArrayList<Double>();
			List<String> bulletinIdList = new ArrayList<String>();
			Integer countNonClasses = 0;

			List<Message> messages = new ArrayList<Message>();

			logger.info(String.format("Nombre d'élèves concerné %s ", moyenneParEleve.size()));

			// Nous allons avant toute opération vider les enregistrements des bulletins,
			// des détails bulletins et des notes bulletins.
			// Pour éviter que si une matiere n existe plus pour une classe, il ne puisse
			// pas avoir des enregistrement caduque dans la base de données.
			removeAllBulletinsByClasseProcess(classe, annee, periode);
			for (MoyenneEleveDto me : moyenneParEleve) {
//			 logger.info(g.toJson(me));
				logger.info(String.format("%s %s  %s  %s ", me.getClasse().getEcole().getId(),
						me.getClasse().getLibelle(), me.getMoyenne(), me.getAppreciation()));

				Inscription infosInscriptionsEleve = inscriptionService.getByEleveAndEcoleAndAnnee(
						me.getEleve().getId(), me.getClasse().getEcole().getId(), Long.parseLong(annee));
				// Collecter toutes les moyennes des élèves pour déterminer la moyenne max, min
				// et avg
				if (!me.getIsClassed().equals(Constants.NON))
					moyGenElevesList.add(me.getMoyenne());
				else
					countNonClasses++;

				bulletin = new Bulletin();
				bulletin = convert(me);
				bulletin.setAnneeId(Long.parseLong(annee));
				AnneeScolaire anDb = AnneeScolaire.findById(Long.parseLong(annee));
				Periode periodeDb = Periode.findById(Long.parseLong(periode));
				bulletin.setAnneeLibelle(anDb.getLibelle());
				bulletin.setPeriodeId(periodeDb.getId());
				bulletin.setLibellePeriode(periodeDb.getLibelle());
//			System.out.println("rang ->"+me.getRang());
//			bulletin.setRang(Integer.parseInt(me.getRang()));
				// Ajout du professeur principal
				PersonnelMatiereClasse pp = personnelMatiereClasseService.findProfPrinc(Long.parseLong(annee),
						Long.parseLong(classe));
				if (!pp.equals(new PersonnelMatiereClasse())) {
					bulletin.setCodeProfPrincipal(pp.getPersonnel().getCode());
					bulletin.setNomPrenomProfPrincipal(
							pp.getPersonnel().getNom() + " " + pp.getPersonnel().getPrenom());
				}
				// Ajout de l'éducateur
				PersonnelMatiereClasse educ = personnelMatiereClasseService.findEducateurClasse(Long.parseLong(annee),
						Long.parseLong(classe));
				if (!educ.equals(new PersonnelMatiereClasse())) {
					bulletin.setCodeEducateur(educ.getPersonnel().getCode());
					bulletin.setNomPrenomEducateur(
							educ.getPersonnel().getNom() + " " + educ.getPersonnel().getPrenom());
				}
				// marquer que l eleve est classé ou non
				ClasseElevePeriode cep = classeElevePeriodeService.findByClasseAndEleveAndAnneeAndPeriode(
						Long.parseLong(classe), me.getEleve().getId(), Long.parseLong(annee), Long.parseLong(periode));
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

				if (infosInscriptionsEleve != null) {
//				System.out.println("affecté ::: " + infosInscriptionsEleve.getAfecte());
					bulletin.setAffecte(infosInscriptionsEleve.getAfecte());
					bulletin.setRedoublant(infosInscriptionsEleve.getRedoublant());
					bulletin.setBoursier(infosInscriptionsEleve.getBoursier());
					bulletin.setLv2(infosInscriptionsEleve.getLv2());
					bulletin.setNumDecisionAffecte(infosInscriptionsEleve.getNumDecisionAffecte());
					bulletin.setEcoleOrigine(infosInscriptionsEleve.getEcoleOrigine());
					bulletin.setTransfert(infosInscriptionsEleve.getTransfert());
				}

				Bulletin bltDb = null;
				try {

					bltDb = Bulletin.find(
							"matricule = :matricule and classeId = :classe and periodeId= :periode and anneeId= :annee",
							Parameters.with("matricule", bulletin.getMatricule()).and("classe", Long.parseLong(classe))
									.and("periode", Long.parseLong(periode)).and("annee", Long.parseLong(annee)))
							.singleResult();
				} catch (NoResultException e) {
					logger.info("Aucun bulletin trouvé dans la bd");
				}catch (RuntimeException e) {
					e.printStackTrace();
				}
//			logger.info("bulletin find ::: %s "+ g.toJson(bltDb));
				if (bltDb != null && bltDb.getId() != null) {
					if (bltDb.getStatut() != null && bltDb.getStatut().equals(Constants.MODIFIABLE)) {
						// Condition qui ne devrait plus être vériffiée - A supprimer pour la suite
						bulletin.setId(bltDb.getId());
						bulletinIdList.add(bltDb.getId());
						logger.info("Mise à jour bulletin id [ " + bltDb.getId() + "] ...");
						update(bulletin);
					} else if (bltDb.getStatut() != null && bltDb.getStatut().equals(Constants.ARCHIVE)) {
						logger.info(String.format("Bulletin archivé [%s]", bltDb.getId()));
					}

				} else {
					logger.info("Création bulletin ...");
					save(bulletin);
					bulletinIdList.add(bulletin.getId());
				}

				Double moyCoef = (double) 0;
				for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
					logger.info(
							String.format("bulletin id %s - matiere %s", bulletin.getId(), entry.getKey().getCode()));
					DetailBulletin flag = null;
					try {
						flag = DetailBulletin.find("bulletin.id = :bulletinId and matiereCode =: matiereCode ",
								Parameters.with("bulletinId", bulletin.getId()).and("matiereCode",
										entry.getKey().getCode()))
								.singleResult();
					} catch (NoResultException ex) {
						logger.info("Aucun detail de bulletin trouvé pour la matiere " + entry.getKey().getLibelle());
					} catch (RuntimeException e) {
						e.printStackTrace();
					}

					// marquer que l eleve est classé dans une matiere ou non
					ClasseEleveMatiere cem = classeEleveMatiereService.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(
							Long.parseLong(classe), entry.getKey().getId(), me.getEleve().getId(),
							Long.parseLong(annee), Long.parseLong(periode));

					if (flag != null) {
						logger.info("--> Modification de detail bulletin");
						flag.setMatiereLibelle(entry.getKey().getLibelle());
						flag.setMoyenne(CommonUtils.roundDouble(entry.getKey().getMoyenne(), 2));
						moyCoef = entry.getKey().getMoyenne() * Double.parseDouble(entry.getKey().getCoef());
						flag.setMoyCoef(CommonUtils.roundDouble(moyCoef, 2));
						flag.setAppreciation(entry.getKey().getAppreciation());
						flag.setCoef(Double.valueOf(entry.getKey().getCoef()));
						flag.setParentMatiere(entry.getKey().getParentMatiereLibelle());
						flag.setMoyAn(entry.getKey().getMoyenneAnnuelle());
						flag.setRangAn(entry.getKey().getRangAnnuel());
						flag.setDateCreation(new Date());

						// Inscrire si oui ou non l'élève est classé dans la matiere
						if (cem != null)
							flag.setIsRanked(cem.getIsClassed());
						else
							flag.setIsRanked(Constants.OUI);

						try {
							flag.setRang(Integer.valueOf(entry.getKey().getRang()));
						} catch (RuntimeException ex) {
							ex.printStackTrace();
							throw new RuntimeException(String.format(
									"Veuillez définir un coefficient pour la matiere [ %s ] de la branche [ %s] ",
									entry.getKey().getLibelle(), me.getClasse().getBranche().getLibelle()));
						}
						flag.setCategorieMatiere(entry.getKey().getCategorie().getLibelle());
						flag.setCategorie(entry.getKey().getCategorie().getCode());
						flag.setBonus(entry.getKey().getBonus());
						flag.setPec(entry.getKey().getPec());
//					logger.info(g.toJson(entry.getKey()));
						flag.setNum_ordre(entry.getKey().getNumOrdre());

						// Ajout de l'enseignant de la matiere
						PersonnelMatiereClasse pers = personnelMatiereClasseService.findProfesseurByMatiereAndClasse(
								Long.parseLong(annee), Long.parseLong(classe), entry.getKey().getId());
						if (pers != null)
							flag.setNom_prenom_professeur(
									pers.getPersonnel().getNom() + " " + pers.getPersonnel().getPrenom());

					} else {
						logger.info("--> Création de detail bulletin ");
						flag = new DetailBulletin();
						UUID idDetail = UUID.randomUUID();
						moyCoef = entry.getKey().getMoyenne() * Double.parseDouble(entry.getKey().getCoef());
						flag.setId(idDetail.toString());
						flag.setMatiereCode(entry.getKey().getCode());
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
						flag.setDateCreation(new Date());
						// Inscrire si oui ou non l'élève est classé dans la matiere
						if (cem != null)
							flag.setIsRanked(cem.getIsClassed());
						else
							flag.setIsRanked(Constants.OUI);

						logger.info("--> Categorie" + entry.getKey().getCategorie().getLibelle());
						// Ajout de l'enseignant de la matiere
						PersonnelMatiereClasse pers = personnelMatiereClasseService.findProfesseurByMatiereAndClasse(
								Long.parseLong(annee), Long.parseLong(classe), entry.getKey().getId());
						if (pers != null)
							flag.setNom_prenom_professeur(
									pers.getPersonnel().getNom() + " " + pers.getPersonnel().getPrenom());

						flag.persist();
					}
					try {
						notesBulletin = NoteBulletin
								.find("detailBulletin.id = : detail", Parameters.with("detail", flag.getId())).list();
//					System.out.println("NOTE BULLETIN "+notesBulletin.size());
					} catch (RuntimeException e) {
						notesBulletin = new ArrayList<NoteBulletin>();
//					System.out.println("NOTE BULLETIN NULL" );
						e.printStackTrace();
						// TODO: handle exception
					}
					if (notesBulletin.size() > 0) {
						logger.info("--> Suppresion notes trouvees");
						try {
							for (NoteBulletin noteBul : notesBulletin) {
								logger.info("id --->" + noteBul.getId());
								noteBul.delete();
							}
						} catch (RuntimeException ex) {
							logger.warning("Erreur lors de la suppression des notes");
							ex.printStackTrace();
						}
					}
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
		bul.setRang(Integer.parseInt(me.getRang()));
		bul.setMoyAn(me.getMoyenneAnnuelle());
		bul.setRangAn(me.getRangAnnuel());
//		bul.setMoyAvg(null);
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

		return bul;
	}
}
