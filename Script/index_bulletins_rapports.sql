-- Index recommandés (MySQL / MariaDB) pour accélérer les rapports basés sur la table Bulletins
-- et la requête agrégée : école + année + période + affectation + ordre de niveau.
--
-- À exécuter hors heures de pointe ; vérifier les index existants avant (SHOW INDEX FROM Bulletins).

-- Couvre : BulletinPoiResultatsAggregator + listes de classes (BulletinNiveauClasseQueryService)
CREATE INDEX idx_bulletins_rapport_ecole_annee_periode_affecte_ordre
    ON Bulletins (id_ecole, annee_libelle, libelle_periode, affecte, ordre_niveau);

-- Variante utile quand on filtre surtout par école / année / période (récap, listes)
CREATE INDEX idx_bulletins_rapport_ecole_annee_periode
    ON Bulletins (id_ecole, annee_libelle, libelle_periode);

-- Détail bulletins : jointures fréquentes bulletin_id + filtres métier (adapter aux noms réels de colonnes)
-- CREATE INDEX idx_detail_bulletins_bulletin_id ON Detail_bulletins (bulletin_id);
