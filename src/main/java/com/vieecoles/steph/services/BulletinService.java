package com.vieecoles.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.vieecoles.dto.MoyenneEleveDto;
import com.vieecoles.entities.Bulletin;
import com.vieecoles.entities.Constants;
import com.vieecoles.entities.DetailBulletin;
import com.vieecoles.entities.Inscription;
import com.vieecoles.entities.Matiere;
import com.vieecoles.entities.NoteBulletin;
import com.vieecoles.entities.Notes;
import com.vieecoles.util.CommonUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class BulletinService implements PanacheRepositoryBase<Bulletin, Long> {

	@Inject
	NoteService noteService;
	
	@Inject
	InscriptionService inscriptionService;

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
		//b.setCodePeriode(me.getPeriode().getCode());
		b.setCodeProfPrincipal(bulletin.getCodeProfPrincipal());
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
		b.setPrenoms(bulletin.getPrenoms());
		b.setRangAn(bulletin.getRangAn());
		b.setRedoublant(bulletin.getRedoublant());
		b.setSexe(bulletin.getSexe());
		b.setStatutEcole(bulletin.getStatutEcole());
		b.setTelEcole(bulletin.getTelEcole());
		b.setTotalCoef(bulletin.getTotalCoef());
		b.setTotalMoyCoef(bulletin.getTotalMoyCoef());
	}
	@Transactional
	public int handleSave(String classe, String annee, String periode) {
		logger.info(String.format("classe %s  annee %s  periode  %s", classe, annee, periode));
		List<MoyenneEleveDto> moyenneParEleve = noteService.moyennesAndNotesHandle(classe, annee, periode);
		Bulletin bulletin;
		List<NoteBulletin> notesBulletin;
		NoteBulletin noteBulletin;
		
		//logger.info(g.toJson(moyenneParEleve));

		for (MoyenneEleveDto me : moyenneParEleve) {
			logger.info(
					String.format("%s  %s  %s ", me.getClasse().getLibelle(), me.getMoyenne(), me.getAppreciation()));
			
			Inscription infosInscriptionsEleve = inscriptionService.getByEleveAndAnnee(me.getEleve().getId(),Long.parseLong(annee));
			
			bulletin = new Bulletin();
			bulletin = convert(me);
				// Paramètres de la variable de session
			bulletin.setAnneeId(1L);
			bulletin.setAnneeLibelle("2021 - 2022");
			
			// A la fin d'une annee scolaire
//			bulletin.setMoyAn(null);
//			bulletin.setRangAn(null);

			//Mettre en place les fonctions permettant d obtenir les infos à set			
//			bulletin.setMoyAvg(null);
//			bulletin.setMoyMax(null);
//			bulletin.setMoyMin(null);
//			bulletin.setMoyGeneral(null);
			
			if (infosInscriptionsEleve != null) {
				bulletin.setAffecte(infosInscriptionsEleve.getAfecte());
				bulletin.setRedoublant(infosInscriptionsEleve.getRedoublant());
				bulletin.setBoursier(infosInscriptionsEleve.getBoursier());
			}
			
			
			
			Bulletin bltDb = Bulletin.find("matricule = :matricule and classeId = :classe and periodeId= :periode and anneeId= :annee", Parameters.with("matricule", bulletin.getMatricule()).and("classe", Long.parseLong(classe)).and("periode", Long.parseLong(periode) ).and("annee", Long.parseLong(annee))).singleResult();
//			logger.info("bulletin find ::: %s "+ g.toJson(bltDb));
			if (bltDb!= null && bltDb.getId() != null) {
				bulletin.setId(bltDb.getId());
				update(bulletin);
			}else
				save(bulletin);
			
			
			Double moyCoef = (double) 0;
			for (Map.Entry<Matiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				logger.info(String.format("bulletin id %s - matiere %s",bulletin.getId(),entry.getKey().getCode()));
				DetailBulletin flag = null;
				try {
				flag = DetailBulletin.find("bulletin.id = :bulletinId and matiereCode =: matiereCode ", Parameters.with("bulletinId", bulletin.getId()).and("matiereCode", entry.getKey().getCode())).singleResult();
				} catch(NoResultException ex) {
					logger.info("Aucun detail de bulletin trouvé");
				}
				if(flag != null) {
					logger.info("--> Modification de detail bulletin");
					flag.setMatiereLibelle(entry.getKey().getLibelle());
					flag.setMoyenne(String.valueOf(CommonUtils.roundDouble(entry.getKey().getMoyenne(),2)));
					flag.setMoyCoef(String.valueOf(CommonUtils.roundDouble(moyCoef, 2) ));
					flag.setAppreciation(entry.getKey().getAppreciation());
					flag.setCoef(entry.getKey().getCoef());
					flag.setRang(entry.getKey().getRang());
				} else {
					logger.info("--> Création de detail bulletin");
					flag = new DetailBulletin();
					UUID idDetail = UUID.randomUUID();
					moyCoef = entry.getKey().getMoyenne()*Double.parseDouble(entry.getKey().getCoef());
					flag.setId(idDetail.toString());
					flag.setMatiereCode(entry.getKey().getCode());
					flag.setMatiereLibelle(entry.getKey().getLibelle());
					flag.setMoyenne(String.valueOf(CommonUtils.roundDouble(entry.getKey().getMoyenne(),2)));
					flag.setMoyCoef(String.valueOf(CommonUtils.roundDouble(moyCoef, 2) ));
					flag.setCoef(entry.getKey().getCoef());
					flag.setAppreciation(entry.getKey().getAppreciation());
					flag.setRang(entry.getKey().getRang());
					flag.setBulletin(bulletin);
					flag.persist();
				}
				notesBulletin = NoteBulletin.find("detailBulletin.id = : detail", Parameters.with("detail", flag.getId())).list();
				
				if(notesBulletin!=null) {
					logger.info("--> Suppresion notes trouvees");
					for(NoteBulletin noteBul : notesBulletin) {
						noteBul.delete();
					}
				}
				UUID idNoteBul;
				for(Notes note : entry.getValue()) {
					logger.info("--> Creation notes");
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
		return moyenneParEleve != null? moyenneParEleve.size() : 0;

	}

	Bulletin convert(MoyenneEleveDto me) {

		Bulletin bul = new Bulletin();

		bul.setAdresseEcole(me.getClasse().getEcole().getAdresse());
		bul.setAffecte(null);
//		bul.setAnneeId(null);
//		bul.setAnneeLibelle(null);
		bul.setAppreciation(me.getAppreciation());
		bul.setBoursier(null);
		bul.setClasseId(me.getClasse().getId());
		bul.setEcoleId(me.getClasse().getEcole().getId());
		//bul.setCodePeriode(me.getPeriode().getCode());
		bul.setCodeProfPrincipal(null);
		bul.setCodeQr(null);
		bul.setDateNaissance(me.getEleve().getDateNaissance());
		bul.setEffectif(null);
		bul.setHeuresAbsJustifiees(null);
		bul.setHeuresAbsNonJustifiees(null);
		bul.setLibelleClasse(me.getClasse().getLibelle());
		//bul.setLibellePeriode(me.getPeriode().getLibelle());
		bul.setLieuNaissance(me.getEleve().getLieuNaissance());
		bul.setMatricule(me.getEleve().getMatricule());
		bul.setMoyAn(null);
		bul.setRangAn(null);
		bul.setMoyAvg(null);
		bul.setMoyGeneral(me.getMoyenne().toString());
		bul.setMoyMax(null);
		bul.setMoyMin(null);
		bul.setNationalite(me.getEleve().getNationalite());
		bul.setNom(me.getEleve().getNom());
		bul.setNomEcole(me.getClasse().getEcole().getLibelle());
		bul.setNomPrenomProfPrincipal(null);
		bul.setPrenoms(me.getEleve().getPrenom());
		bul.setSexe(me.getEleve().getSexe());
		bul.setStatut(Constants.MODIFIABLE);
		bul.setStatutEcole(me.getClasse().getEcole().getStatut());
		bul.setTelEcole(me.getClasse().getEcole().getTel());
		bul.setTotalCoef(null);
		bul.setTotalMoyCoef(null);
		bul.setUrlLogo(null);

		return bul;
	}
}
