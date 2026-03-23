package com.vieecoles.services.etats;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NiveauOrderDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Requêtes centralisées sur {@code Bulletin} pour listes de classes / ordre de niveau
 * (évite la duplication JPQL dans les processeurs Word).
 */
@ApplicationScoped
public class BulletinNiveauClasseQueryService {

    private static final String JPQL_CLASSES_ORDERED =
            "SELECT new com.vieecoles.dto.NiveauOrderDto(b.libelleClasse, b.ordreNiveau) "
                    + "FROM Bulletin b WHERE b.ecoleId = :idEcole AND b.libellePeriode = :periode "
                    + "AND b.anneeLibelle = :annee "
                    + "GROUP BY b.ordreNiveau, b.libelleClasse ORDER BY b.ordreNiveau, b.libelleClasse";

    /** Libellés de classe distincts pour un niveau (champ {@link NiveauDto#getNiveau()} = libellé classe). */
    private static final String JPQL_LIBELLES_CLASSE_PAR_NIVEAU =
            "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) FROM Bulletin b "
                    + "WHERE b.ecoleId = :idEcole AND b.libellePeriode = :periode AND b.anneeLibelle = :annee "
                    + "AND b.niveau = :niveau GROUP BY b.libelleClasse";

    /** Niveaux distincts présents sur les bulletins (champ {@link NiveauDto#getNiveau()} = niveau). */
    private static final String JPQL_NIVEAUX_DISTINCTS =
            "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) FROM Bulletin b "
                    + "WHERE b.ecoleId = :idEcole AND b.libellePeriode = :periode AND b.anneeLibelle = :annee "
                    + "GROUP BY b.niveau";

    @Inject
    EntityManager em;

    public List<NiveauOrderDto> listClassesOrderedByNiveau(Long idEcole, String libelleAnnee, String libellePeriode) {
        TypedQuery<NiveauOrderDto> q = em.createQuery(JPQL_CLASSES_ORDERED, NiveauOrderDto.class);
        q.setParameter("idEcole", idEcole);
        q.setParameter("annee", libelleAnnee);
        q.setParameter("periode", libellePeriode);
        return q.getResultList();
    }

    public List<NiveauDto> listLibellesClasseParNiveau(Long idEcole, String libelleAnnee, String libellePeriode,
            String niveau) {
        TypedQuery<NiveauDto> q = em.createQuery(JPQL_LIBELLES_CLASSE_PAR_NIVEAU, NiveauDto.class);
        q.setParameter("idEcole", idEcole);
        q.setParameter("annee", libelleAnnee);
        q.setParameter("periode", libellePeriode);
        q.setParameter("niveau", niveau);
        return q.getResultList();
    }

    public List<NiveauDto> listNiveauxDistincts(Long idEcole, String libelleAnnee, String libellePeriode) {
        TypedQuery<NiveauDto> q = em.createQuery(JPQL_NIVEAUX_DISTINCTS, NiveauDto.class);
        q.setParameter("idEcole", idEcole);
        q.setParameter("annee", libelleAnnee);
        q.setParameter("periode", libellePeriode);
        return q.getResultList();
    }
}
