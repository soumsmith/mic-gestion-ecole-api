package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.ClasseElevePeriode;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailBulletin;
import com.vieecoles.steph.entities.Inscription;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.NoteBulletin;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.util.CommonUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class BulletinService implements PanacheRepositoryBase<Bulletin, Long> {

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

	Logger logger = Logger.getLogger(BulletinService.class.getName());
	Gson g = new Gson();

	public void save(Bulletin bulletin) {
		UUID uuid = UUID.randomUUID();
		bulletin.setId(uuid.toString());
		bulletin.setDateCreation(new Date());
		bulletin.persist();
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
	}

	@Transactional
	public int handleSave(String classe, String annee, String periode) {
		logger.info(String.format("classe %s  annee %s  periode  %s", classe, annee, periode));
		List<MoyenneEleveDto> moyenneParEleve = noteService.moyennesAndNotesHandle(classe, annee, periode);
		Bulletin bulletin;
		List<NoteBulletin> notesBulletin;
		NoteBulletin noteBulletin;
		List<Double> moyGenElevesList = new ArrayList<Double>();
		List<String> bulletinIdList = new ArrayList<String>();


		for (MoyenneEleveDto me : moyenneParEleve) {
//			 logger.info(g.toJson(me));
			logger.info(
					String.format("%s %s  %s  %s ",me.getClasse().getEcole().getId(), me.getClasse().getLibelle(), me.getMoyenne(), me.getAppreciation()));

			Inscription infosInscriptionsEleve = inscriptionService.getByEleveAndEcoleAndAnnee(me.getEleve().getId(),me.getClasse().getEcole().getId(),
					Long.parseLong(annee));
			// Collecter toutes les moyennes des élèves pour déterminer la moyenne max, min
			// et avg
			moyGenElevesList.add(me.getMoyenne());

			bulletin = new Bulletin();
			bulletin = convert(me);
			// Paramètres de la variable de session
			bulletin.setAnneeId(Long.parseLong(annee));
			AnneeScolaire anDb = AnneeScolaire.findById(Long.parseLong(annee));
			Periode periodeDb = Periode.findById(Long.parseLong(periode));
			bulletin.setAnneeLibelle(anDb.getLibelle());
			bulletin.setPeriodeId(periodeDb.getId());
			bulletin.setLibellePeriode(periodeDb.getLibelle());
			// Ajout du professeur principal
			PersonnelMatiereClasse pp = personnelMatiereClasseService.getPersonnelByClasseAndAnneeAndFonction(Long.parseLong(annee), Long.parseLong(classe), 1);
			if(pp!=null) {
				bulletin.setCodeProfPrincipal(pp.getPersonnel().getCode());
				bulletin.setNomPrenomProfPrincipal(pp.getPersonnel().getNom()+" "+pp.getPersonnel().getPrenom());
			}
			// Ajout de l'éducateur
			PersonnelMatiereClasse educ = personnelMatiereClasseService.getPersonnelByClasseAndAnneeAndFonction(Long.parseLong(annee), Long.parseLong(classe), 2);
			if(educ!=null) {
				bulletin.setCodeEducateur(educ.getPersonnel().getCode());
				bulletin.setNomPrenomEducateur(educ.getPersonnel().getNom()+" "+educ.getPersonnel().getPrenom());
			}
			// marquer que l eleve est classé ou non
			ClasseElevePeriode cep = classeElevePeriodeService.findByClasseAndEleveAndAnneeAndPeriode(Long.parseLong(classe), me.getEleve().getId(), Long.parseLong(annee), Long.parseLong(periode));			
			if(cep == null)
				bulletin.setIsClassed(Constants.OUI);
			else 
				bulletin.setIsClassed(cep.getIsClassed());
			
			
			// A la fin d'une annee scolaire
//			bulletin.setMoyAn(null);
//			bulletin.setRangAn(null);

			if (infosInscriptionsEleve != null) {
				bulletin.setAffecte(infosInscriptionsEleve.getAfecte());
				bulletin.setRedoublant(infosInscriptionsEleve.getRedoublant());
				bulletin.setBoursier(infosInscriptionsEleve.getBoursier());
				bulletin.setLv2(infosInscriptionsEleve.getLv2());
				bulletin.setNumDecisionAffecte(infosInscriptionsEleve.getNumDecisionAffecte());
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
			}
//			logger.info("bulletin find ::: %s "+ g.toJson(bltDb));
			if (bltDb != null && bltDb.getId() != null) {
				bulletin.setId(bltDb.getId());
				bulletinIdList.add(bltDb.getId());
				logger.info("Mise à jour bulletin id [ "+bltDb.getId()+"] ...");
				update(bulletin);
			} else {
				logger.info("Création bulletin ...");
				save(bulletin);
				bulletinIdList.add(bulletin.getId());
			}

			Double moyCoef = (double) 0;
			for (Map.Entry<Matiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				logger.info(String.format("bulletin id %s - matiere %s", bulletin.getId(), entry.getKey().getCode()));
				DetailBulletin flag = null;
				try {
					flag = DetailBulletin
							.find("bulletin.id = :bulletinId and matiereCode =: matiereCode ", Parameters
									.with("bulletinId", bulletin.getId()).and("matiereCode", entry.getKey().getCode()))
							.singleResult();
				} catch (NoResultException ex) {
					logger.info("Aucun detail de bulletin trouvé");
				}
				if (flag != null) {
					logger.info("--> Modification de detail bulletin");
					flag.setMatiereLibelle(entry.getKey().getLibelle());
					flag.setMoyenne(CommonUtils.roundDouble(entry.getKey().getMoyenne(), 2));
					flag.setMoyCoef(CommonUtils.roundDouble(moyCoef, 2));
					flag.setAppreciation(entry.getKey().getAppreciation());
					flag.setCoef(Double.valueOf(entry.getKey().getCoef()));
					flag.setRang(Integer.valueOf(entry.getKey().getRang()));
					flag.setCategorieMatiere(entry.getKey().getCategorie().getLibelle());
					flag.setCategorie(entry.getKey().getCategorie().getCode());
					logger.info(g.toJson(entry.getKey()));
					flag.setNum_ordre(entry.getKey().getNumOrdre());
					
					// Ajout de l'enseignant de la matiere
					PersonnelMatiereClasse pers = personnelMatiereClasseService.findProfesseurByMatiereAndClasse(Long.parseLong(annee), Long.parseLong(classe), entry.getKey().getId());
					if(pers!=null)
						flag.setNom_prenom_professeur(pers.getPersonnel().getNom()+" "+pers.getPersonnel().getPrenom());
					
						
					
				} else {
					logger.info("--> Création de detail bulletin");
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
					logger.info("--> Categorie"+entry.getKey().getCategorie().getLibelle());
					// Ajout de l'enseignant de la matiere
					PersonnelMatiereClasse pers = personnelMatiereClasseService.findProfesseurByMatiereAndClasse(Long.parseLong(annee), Long.parseLong(classe), entry.getKey().getId());
					if(pers!=null)
						flag.setNom_prenom_professeur(pers.getPersonnel().getNom()+" "+pers.getPersonnel().getPrenom());
					
					flag.persist();
				}
				notesBulletin = NoteBulletin
						.find("detailBulletin.id = : detail", Parameters.with("detail", flag.getId())).list();

				if (notesBulletin != null) {
					logger.info("--> Suppresion notes trouvees");
					for (NoteBulletin noteBul : notesBulletin) {
						noteBul.delete();
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
			throw new RuntimeException("Aucune donnée trouvée");
		}
		int effectif = classeEleveService.getCountByClasseAnnee(Long.parseLong(classe), Long.parseLong(annee));

		Double avgMoy = sumMoy / (moyGenElevesList.size() != 0 ? moyGenElevesList.size() : 1);

		logger.info(String.format("Max= %s , Min = %s, Sum = %s, Avg = %s, effectif %s", maxMoy, minMoy, sumMoy, avgMoy,
				effectif));

		// Mise à jour des bulletins
		bulletin = new Bulletin();
		for (String id : bulletinIdList) {
			bulletin = Bulletin.findById(id);
			bulletin.setMoyMax(CommonUtils.roundDouble(maxMoy, 2));
			bulletin.setMoyMin(CommonUtils.roundDouble(minMoy, 2));
			bulletin.setMoyAvg(CommonUtils.roundDouble(avgMoy, 2));
			bulletin.setEffectif(effectif);
//			logger.info(String.format("m a j du bulletin %s [effectif : % . smoy max : %s . moy min : %s . moy avg : %s", id,
//					effectif, bulletin.getMoyMax().toString(), bulletin.getMoyMin(), bulletin.getMoyAvg()));

		}
		return moyenneParEleve != null ? moyenneParEleve.size() : 0;
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
//		bul.setMoyAn(null);
//		bul.setRangAn(null);
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
//		bul.setLv2(null);
//		bul.setNumDecisionAffecte(null);
//		bul.setNature(null);
//		bul.setNomPrenomEducateur(null);
//		bul.setCodeEducateur(null);

		return bul;
	}
}
