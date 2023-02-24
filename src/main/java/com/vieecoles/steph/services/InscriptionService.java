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

	public List<Inscription> getByBrancheAndAnneeAndStatut(long branche, long annee, String statut, Long ecole) {

		return Inscription.find("branche.id = ?1 and annee.id = ?2 and statut = ?3 and ecole.id=?4", branche, annee,
				statut, ecole).list();
	}

	public Inscription getByEleveAndAnnee(long eleve, long annee) {

		return Inscription.find("eleve.id = ?1 and annee.id = ?2", eleve, annee).singleResult();
	}

	public List<Inscription> getByBrancheAndAnneeAndStatutNotinClasse(long branche, long annee, String statut,
			long ecole) {

		List<Inscription> inscriptions = getByBrancheAndAnneeAndStatut(branche, annee, statut, ecole);
		List<ClasseEleve> classeEleves = classeEleveService.getByBrancheAndAnnee(branche, annee, ecole);
		List<Inscription> _inscriptions = new ArrayList<Inscription>();
		System.out.println("liste inscriptions " + (inscriptions != null ? inscriptions.size() : 0));
		System.out.println("liste acffecte dans une clase " + (classeEleves != null ? classeEleves.size() : 0));
		Boolean flat = false;
		for (Inscription ins : inscriptions) {
			System.out.println(ins);
			for (ClasseEleve ce : classeEleves) {
				System.out.println(ins.getId() +" == "+ ce.getInscription().getId());
				if (ins.getId() == ce.getInscription().getId()) {
					flat = true;
					break;
				}
			}
			if (flat)
				_inscriptions.add(ins);
			flat = false;
		}
		for (Inscription ins : _inscriptions) {
			inscriptions.remove(ins);
		}

		return inscriptions;
	}
}
