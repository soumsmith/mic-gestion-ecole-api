package com.vieecoles.steph.services;

import com.vieecoles.entities.operations.Inscriptions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Inscription;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class InscriptionService implements PanacheRepositoryBase<Inscription, Integer> {

	@Inject
	ClasseEleveService classeEleveService;

	@Inject
	EntityManager em;


	public List<Inscription> getByBrancheAndAnneeAndStatut(long branche, long annee, String statut, Long ecole) {

		return Inscription.find("branche.id = ?1 and annee.id = ?2 and statut = ?3 and ecole.id=?4", branche, annee,
				statut, ecole).list();
	}

	public Inscription getByEleveAndEcoleAndAnnee(long eleve, long ecole, long annee,long branche) {

		Inscription minScription = new Inscription() ;
		try {
			   minScription = (Inscription) em.createQuery("select Max(i) from Inscription i  where i.eleve.id=:eleve and i.annee.id=:annee and i.ecole.id=:ecole" +
							" and i.branche.id=:branche " )
					.setParameter("ecole", ecole)
					.setParameter("annee",annee)
					.setParameter("eleve",eleve)
					.setParameter("branche",branche)
					.getSingleResult();
		} catch (Exception e){
			e.printStackTrace();

		}
		return    minScription ;
	}

	// Nombre d'eleves dans une ecole
	public Long getNbreEleveByEcoleAndAnneeAndStatut(Long ecole, Long annee, String statut) {
		return Inscription.find("ecole.id = ?1 and annee.id = ?2 and statut = ?3", ecole, annee, statut).count();
	}

	// Nombre d'eleves dans une ecole en fonction du sexe
	public Long getNbreEleveByEcoleAndAnneeAndStatutAndSexe(Long ecole, Long annee, String statut, String sexe) {

		Long result = null;
		/*try {
			result = (Long) em.createQuery("select count(o.id) from Inscription o ,eleve e where o.eleve.id=e.id and o.ecole.id=:ecole and o.statut = :statut " +
							"and e.eleve_sexe = :sexe  and o.annee.id = :annee")
					.setParameter("sexe",sexe)
					.setParameter("ecole",ecole)
					.setParameter("statut",statut)
					.setParameter("annee",annee)
					.getSingleResult();
			return result ;
		} catch (NoResultException e){
			return 0L ;
		}*/





		return Inscription
				.find("ecole.id = ?1 and annee.id = ?2 and statut = ?3 and eleve.sexe = ?4", ecole, annee, statut, sexe)
				.count();
	}

	// Nombre d'eleves affecté par l Etat ou non dans une ecole
	public Long getNbreEleveAffecteByEcoleAndAnneeAndStatut(Long ecole, Long annee, String statut,
			String statutAffecte) {
		return Inscription.find("ecole.id = ?1 and annee.id = ?2 and statut = ?3 and afecte = ?4", ecole, annee, statut,
				statutAffecte).count();
	}

	// Nombre d'eleves affecté par l Etat ou non dans une ecole en fonction du sexe
	public Long getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(Long ecole, Long annee, String statut, String sexe,
			String statutAffecte) {
		return Inscription
				.find("ecole.id = ?1 and annee.id = ?2 and statut = ?3 and eleve.sexe = ?4 and afecte = ?5",
						ecole, annee, statut, sexe, statutAffecte)
				.count();
	}

	// nombre de nouveaux élèves dans l 'ecole
	public long countNewEleve(Long ecoleId, Long anneeId) {
        Query query = em.createNamedQuery("Inscription.getNewEleveCount");
        query.setParameter("ecoleId", ecoleId);
        query.setParameter("anneeId", anneeId);
        try{
//        	System.out.println(query.getSingleResult());
        	BigInteger count = (BigInteger) query.getSingleResult();
            return Long.parseLong(count.toString());
        }catch(NoResultException ex){
            ex.getMessage();
            return 0;
        }
    }
	// Nombre des anciens élèves dans l'ecole
	public long countOldEleve(Long ecoleId, Long anneeId) {
        Query query = em.createNamedQuery("Inscription.getOldEleveCount");
        query.setParameter("ecoleId", ecoleId);
        query.setParameter("anneeId", anneeId);
        try{
//        	System.out.println(query.getSingleResult());
        	BigInteger count = (BigInteger) query.getSingleResult();
            return Long.parseLong(count.toString());
        }catch(NoResultException ex){
            ex.getMessage();
            return 0;
        }
    }

	public long getNewEleveAffCountBySexe(Long ecoleId, Long anneeId, String sexe, String statut) {
        Query query = em.createNamedQuery("Inscription.getNewEleveCountBySexeAndAffecte");
        query.setParameter("ecoleId", ecoleId);
        query.setParameter("sexe", sexe);
        query.setParameter("statutAffecte", statut);
        query.setParameter("anneeId", anneeId);
        try{
//        	System.out.println(query.getSingleResult());
        	BigInteger count = (BigInteger) query.getSingleResult();
            return Long.parseLong(count.toString());
        }catch(NoResultException ex){
            ex.getMessage();
            return 0;
        }
    }


	public BigDecimal getAvgEffectifbyClasse(Long ecoleId, Long anneeId) {
        Query query = em.createNamedQuery("Inscription.getAvgEffectifbyClasse");
        query.setParameter("ecoleId", ecoleId);
        query.setParameter("anneeId", anneeId);
        try{
        	BigDecimal count = (BigDecimal) query.getSingleResult();
        	System.out.println("************  "+count);
            return count.round(new MathContext(2, RoundingMode.HALF_UP));
        }catch(NoResultException ex){
            ex.getMessage();
            return BigDecimal.ZERO;
        }
    }

	public long getOldEleveAffCountBySexe(Long ecoleId, Long anneeId, String sexe, String statut) {
        Query query = em.createNamedQuery("Inscription.getOldEleveCountBySexeAndAffecte");
        query.setParameter("ecoleId", ecoleId);
        query.setParameter("anneeId", anneeId);
        query.setParameter("sexe", sexe);
        query.setParameter("statutAffecte", statut);
        try{
//        	System.out.println(query.getSingleResult());
        	BigInteger count = (BigInteger) query.getSingleResult();
            return Long.parseLong(count.toString());
        }catch(NoResultException ex){
            ex.getMessage();
            return 0;
        }
    }



	public List<Inscription> getByBrancheAndAnneeAndStatutNotinClasse(long branche, long annee, String statut,
			long ecole) {

		List<Inscription> inscriptions = getByBrancheAndAnneeAndStatut(branche, annee, statut, ecole);
		List<ClasseEleve> classeEleves = classeEleveService.getByBrancheAndAnneeAndStatut(branche, annee, ecole, Constants.STATUT_ELEVE_RETIRE_CLASSE);
		List<Inscription> _inscriptions = new ArrayList<Inscription>();
//		System.out.println("liste inscriptions " + (inscriptions != null ? inscriptions.size() : 0));
//		System.out.println("liste acffecte dans une clase " + (classeEleves != null ? classeEleves.size() : 0));
		Boolean flat = false;
		for (Inscription ins : inscriptions) {
//			System.out.println(ins);
			for (ClasseEleve ce : classeEleves) {
//				System.out.println(ins.getId() + " == " + ce.getInscription().getId());
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
