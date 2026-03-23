package com.vieecoles.services.etats;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

/**
 * Recherche optimisée de l'identifiant d'année scolaire via la table {@code Bulletin}.
 * Évite DISTINCT sur tout le jeu de résultats : un seul enregistrement suffit (même {@code anneeId}).
 */
@ApplicationScoped
public class BulletinAnneeLookupService {

    @Inject
    EntityManager em;

    /**
     * @return {@code anneeId} pour l'école / année libellée / période, ou {@code null} si aucun bulletin.
     */
    public Long findAnneeId(Long idEcole, String libelleAnnee, String periode) {
        try {
            return em.createQuery(
                    "select b.anneeId from Bulletin b where b.anneeLibelle = :libelleAnnee "
                            + "and b.libellePeriode = :periode and b.ecoleId = :idEcole",
                    Long.class)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("periode", periode)
                    .setParameter("idEcole", idEcole)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
