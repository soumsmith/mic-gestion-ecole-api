package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Inscription;
import com.vieecoles.steph.entities.Inscription;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class InscriptionService implements PanacheRepositoryBase<Inscription, Integer> {

	@Inject
	ClasseEleveService classeEleveService;

	public List<Inscription> getByBrancheAndAnneeAndStatut(long branche, long annee, String statut) {

		return Inscription.find("branche.id = ?1 and annee.id = ?2 and statut = ?3", branche, annee, statut).list();
	}

	public Inscription getByEleveAndAnnee(long eleve, long annee) {

		return Inscription.find("eleve.id = ?1 and annee.id = ?2", eleve, annee).singleResult();
	}

	public List<Inscription> getByBrancheAndAnneeAndStatutNotinClasse(long branche, long annee, String statut) {

		List<Inscription> inscriptions = getByBrancheAndAnneeAndStatut(branche, annee, statut);
		List<ClasseEleve> classeEleves = classeEleveService.getByBrancheAndAnnee(branche, annee);
		List<Inscription> _inscriptions = new ArrayList<Inscription>();
		for (Inscription ins : inscriptions) {
			for (ClasseEleve ce : classeEleves) {
				if (ins.getId() == ce.getInscription().getId()) {
					_inscriptions.add(ins);
					break;
				}
			}
		}
		for (Inscription ins : _inscriptions) {
			inscriptions.remove(ins);
		}

		return inscriptions;
	}
}
