package com.vieecoles.services.etats;

import com.vieecoles.dto.NiveauClasseIdDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Requêtes {@code Bulletin} factorisées pour effectifs langues vivantes et statistiques DFA / niveau-sexe (Dren4).
 */
@ApplicationScoped
public class BulletinEffectifQueryService {

    private static final String JPQL_COUNT_SEXE_LV2_ORDRE =
            "SELECT COUNT(o.id) FROM Bulletin o WHERE o.sexe = :sexe AND o.ecoleId = :idEcole "
                    + "AND o.libellePeriode = :periode AND o.anneeLibelle = :annee "
                    + "AND o.lv2 = :lv2 AND o.ordreNiveau = :niveau";

    private static final String JPQL_COUNT_AFFECTE_LV2_ORDRE =
            "SELECT COUNT(o.id) FROM Bulletin o WHERE o.affecte = :affecte AND o.ecoleId = :idEcole "
                    + "AND o.libellePeriode = :periode AND o.anneeLibelle = :annee "
                    + "AND o.lv2 = :lv2 AND o.ordreNiveau = :niveau";

    private static final String JPQL_COUNT_DISTINCT_CLASSE_LV2_ORDRE =
            "SELECT COUNT(DISTINCT o.classeId) FROM Bulletin o WHERE o.ecoleId = :idEcole "
                    + "AND o.libellePeriode = :periode AND o.anneeLibelle = :annee "
                    + "AND o.lv2 = :lv2 AND o.ordreNiveau = :niveau";

    private static final String JPQL_COUNT_ORDRE_LV2 =
            "SELECT COUNT(o.id) FROM Bulletin o WHERE o.ecoleId = :idEcole "
                    + "AND o.ordreNiveau = :niveauId AND o.lv2 = :langueid";

    private static final String JPQL_LIST_NIVEAU_ORDRE_BY_ANNEE_ID =
            "SELECT DISTINCT new com.vieecoles.dto.NiveauClasseIdDto(b.niveau, b.ordreNiveau) FROM Bulletin b "
                    + "WHERE b.ecoleId = :idEcole AND b.anneeId = :annee ORDER BY b.ordreNiveau";

    private static final String JPQL_COUNT_CLASSES_BRANCHE =
            "SELECT COUNT(DISTINCT c.classeId) FROM Bulletin c WHERE c.ordreNiveau = :ordreId "
                    + "AND c.ecoleId = :ecoleId AND c.anneeLibelle = :anneeLibelle";

    /** Agrégation admis / redoublants / exclus / non classés par niveau (rapports DFA). */
    private static final String JPQL_DFA_AGGREGATION = """
            SELECT b.ordreNiveau, b.niveau,
            SUM(CASE \
            WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') \
            AND b.isClassed = 'O' \
            AND ( \
            NOT ( \
            (b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            OR (b.ordreNiveau = 4) \
            OR (b.ordreNiveau > 9 AND b.redoublant = 'OUI') \
            OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn >= 8.5) \
            OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn >= 8.5) \
            OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn < 8.5) \
            OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn < 8.5) \
            ) \
            ) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') \
            AND b.isClassed = 'O' \
            AND ( \
            NOT ( \
            (b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            OR (b.ordreNiveau = 4) \
            OR (b.ordreNiveau > 9 AND b.redoublant = 'OUI') \
            OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn >= 8.5) \
            OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn >= 8.5) \
            OR (b.ordreNiveau > 9 AND b.redoublant = 'NON' AND b.moyAn < 8.5) \
            OR (b.ordreNiveau > 9 AND b.redoublant IS NULL AND b.moyAn < 8.5) \
            ) \
            ) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') \
            AND b.isClassed = 'O' \
            AND ( \
            (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            ) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') \
            AND b.isClassed = 'O' \
            AND ( \
            (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn >= 8.5 AND b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            ) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') \
            AND b.isClassed = 'O' \
            AND ( \
            (b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            ) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') \
            AND b.isClassed = 'O' \
            AND ( \
            (b.moyAn < 10 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'OUI') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant = 'NON') \
            OR (b.moyAn < 8.5 AND (b.ordreNiveau != 4 AND b.ordreNiveau < 10) AND b.redoublant IS NULL) \
            ) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') \
            AND (b.isClassed = 'N' OR b.isClassed IS NULL) \
            THEN 1 ELSE 0 END), \
            SUM(CASE \
            WHEN (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') \
            AND (b.isClassed = 'N' OR b.isClassed IS NULL) \
            THEN 1 ELSE 0 END) \
            FROM Bulletin b \
            WHERE b.ecoleId = :idEcole \
            AND b.libellePeriode = :periode \
            AND b.anneeLibelle = :annee \
            GROUP BY b.ordreNiveau, b.niveau \
            ORDER BY b.ordreNiveau""";

    private static final String JPQL_COUNT_NON_CLASSE_G =
            "SELECT COUNT(b.id) FROM Bulletin b WHERE b.ecoleId = :idEcole "
                    + "AND b.libellePeriode = :periode AND b.anneeLibelle = :annee "
                    + "AND b.ordreNiveau = :ordreNiveau "
                    + "AND (b.sexe = 'M' OR b.sexe = 'MASCULIN' OR b.sexe = 'Garçon') "
                    + "AND (b.isClassed = 'N' OR b.isClassed IS NULL)";

    private static final String JPQL_COUNT_NON_CLASSE_F =
            "SELECT COUNT(b.id) FROM Bulletin b WHERE b.ecoleId = :idEcole "
                    + "AND b.libellePeriode = :periode AND b.anneeLibelle = :annee "
                    + "AND b.ordreNiveau = :ordreNiveau "
                    + "AND (b.sexe = 'F' OR b.sexe = 'FEMININ' OR b.sexe = 'Fille') "
                    + "AND (b.isClassed = 'N' OR b.isClassed IS NULL)";

    private static final String JPQL_COUNT_NON_CLASSE_CLASSE =
            "SELECT COUNT(o.id) FROM Bulletin o WHERE o.ecoleId = :idEcole "
                    + "AND o.isClassed = :isClass AND o.libellePeriode = :periode AND o.anneeLibelle = :annee "
                    + "AND o.libelleClasse = :classe AND o.niveau = :niveau";

    private static final String JPQL_DEBUG_BULLETIN_NIVEAU =
            "SELECT b.matricule, b.nom, b.prenoms, b.sexe, b.moyAn, b.redoublant, b.ordreNiveau, b.isClassed "
                    + "FROM Bulletin b WHERE b.ecoleId = :idEcole AND b.libellePeriode = :periode "
                    + "AND b.anneeLibelle = :annee AND b.ordreNiveau = :ordreNiveau ORDER BY b.nom, b.prenoms";

    @Inject
    EntityManager em;

    // --- Langue vivante (effectifs LV2 par ordre de niveau) ---

    public long countBySexeLv2Ordre(Long idEcole, int ordreNiveau, String libelleAnnee, String libelleTrimestre,
            String sexe, String lv2) {
        try {
            return em.createQuery(JPQL_COUNT_SEXE_LV2_ORDRE, Long.class)
                    .setParameter("sexe", sexe)
                    .setParameter("idEcole", idEcole)
                    .setParameter("niveau", ordreNiveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("lv2", lv2)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public long countByAffecteLv2Ordre(Long idEcole, int ordreNiveau, String libelleAnnee, String libelleTrimestre,
            String affecte, String lv2) {
        try {
            return em.createQuery(JPQL_COUNT_AFFECTE_LV2_ORDRE, Long.class)
                    .setParameter("affecte", affecte)
                    .setParameter("idEcole", idEcole)
                    .setParameter("niveau", ordreNiveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("lv2", lv2)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public long countDistinctClasseByLv2Ordre(Long idEcole, int ordreNiveau, String libelleAnnee,
            String libelleTrimestre, String lv2) {
        try {
            return em.createQuery(JPQL_COUNT_DISTINCT_CLASSE_LV2_ORDRE, Long.class)
                    .setParameter("idEcole", idEcole)
                    .setParameter("niveau", ordreNiveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("lv2", lv2)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public long countByEcoleOrdreNiveauAndLv2(Long idEcole, Integer ordreNiveau, String langueLv2) {
        try {
            return em.createQuery(JPQL_COUNT_ORDRE_LV2, Long.class)
                    .setParameter("idEcole", idEcole)
                    .setParameter("niveauId", ordreNiveau)
                    .setParameter("langueid", langueLv2)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    // --- Statistiques DFA / niveau-sexe ---

    public List<Object[]> fetchDfaAggregationRows(Long idEcole, String libelleTrimestre, String libelleAnnee) {
        TypedQuery<Object[]> query = em.createQuery(JPQL_DFA_AGGREGATION, Object[].class);
        query.setParameter("idEcole", idEcole);
        query.setParameter("periode", libelleTrimestre);
        query.setParameter("annee", libelleAnnee);
        return query.getResultList();
    }

    public long countNonClasseGarconsByOrdre(Long idEcole, String libelleTrimestre, String libelleAnnee,
            Integer ordreNiveau) {
        try {
            Long c = em.createQuery(JPQL_COUNT_NON_CLASSE_G, Long.class)
                    .setParameter("idEcole", idEcole)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("ordreNiveau", ordreNiveau)
                    .getSingleResult();
            return c != null ? c : 0L;
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public long countNonClasseFillesByOrdre(Long idEcole, String libelleTrimestre, String libelleAnnee,
            Integer ordreNiveau) {
        try {
            Long c = em.createQuery(JPQL_COUNT_NON_CLASSE_F, Long.class)
                    .setParameter("idEcole", idEcole)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("ordreNiveau", ordreNiveau)
                    .getSingleResult();
            return c != null ? c : 0L;
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public long countNonClasseByClasseNiveau(Long idEcole, String classe, String niveau, String annee, String periode) {
        try {
            Long nonclassF = em.createQuery(JPQL_COUNT_NON_CLASSE_CLASSE, Long.class)
                    .setParameter("idEcole", idEcole)
                    .setParameter("isClass", "N")
                    .setParameter("classe", classe)
                    .setParameter("niveau", niveau)
                    .setParameter("annee", annee)
                    .setParameter("periode", periode)
                    .getSingleResult();
            return nonclassF != null ? nonclassF : 0L;
        } catch (NoResultException e) {
            return 0L;
        }
    }

    public List<NiveauClasseIdDto> listDistinctNiveauOrdreByEcoleAnneeId(Long idEcole, Long idAnneeId) {
        TypedQuery<NiveauClasseIdDto> q = em.createQuery(JPQL_LIST_NIVEAU_ORDRE_BY_ANNEE_ID, NiveauClasseIdDto.class);
        q.setParameter("idEcole", idEcole);
        q.setParameter("annee", idAnneeId);
        return q.getResultList();
    }

    public long countDistinctClassesByOrdreEcoleAnnee(Integer ordreId, Long ecoleId, String anneeLibelle) {
        return em.createQuery(JPQL_COUNT_CLASSES_BRANCHE, Long.class)
                .setParameter("ordreId", ordreId)
                .setParameter("ecoleId", ecoleId)
                .setParameter("anneeLibelle", anneeLibelle)
                .getSingleResult();
    }

    public List<Object[]> debugBulletinRowsForNiveau(Long idEcole, String libelleTrimestre, String libelleAnnee,
            Integer ordreNiveau) {
        TypedQuery<Object[]> query = em.createQuery(JPQL_DEBUG_BULLETIN_NIVEAU, Object[].class);
        query.setParameter("idEcole", idEcole);
        query.setParameter("periode", libelleTrimestre);
        query.setParameter("annee", libelleAnnee);
        query.setParameter("ordreNiveau", ordreNiveau);
        return query.getResultList();
    }
}
