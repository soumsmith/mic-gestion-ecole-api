SELECT
    MAX(`b`.`id_ecole`) AS `id_ecole`,
    MAX(`b`.`nom_ecole`) AS `nom_ecole`,
    MAX(`b`.`statut_ecole`) AS `statut_ecole`,
    MAX(`b`.`url_logo`) AS `url_logo`,
    MAX(`b`.`adresse_ecole`) AS `adresse_ecole`,
    MAX(`b`.`tel_ecole`) AS `tel_ecole`,
    MAX(`b`.`annee_libelle`) AS `annee_libelle`,
    MAX(`b`.`matricule`) AS `matricule`,
    MAX(`b`.`nom`) AS `nom`,
    MAX(`b`.`prenoms`) AS `prenoms`,
    MAX(`b`.`sexe`) AS `sexe`,
    MAX(`b`.`date_naissance`) AS `date_naissance`,
    MAX(`b`.`lieu_naissance`) AS `lieu_naissance`,
    MAX(`b`.`nationalite`) AS `nationalite`,
    MAX(`b`.`redoublant`) AS `redoublant`,
    MAX(`b`.`boursier`) AS `boursier`,
    MAX(`b`.`affecte`) AS `affecte`,
    MAX(`b`.`libelle_classe`) AS `libelle_classe`,
    MAX(`b`.`effectif_classe`) AS `effectif_classe`,
    MAX(`b`.`total_coef`) AS `total_coef`,
    MAX(`b`.`total_moy_coef`) AS `total_moy_coef`,
    MAX(`b`.`nom_prof_princ`) AS `nom_prof_princ`,
    MAX(`b`.`heures_abs_just`) AS `heures_abs_just`,
    MAX(`b`.`heures_abs_non_just`) AS `heures_abs_non_just`,
    MAX(`b`.`moy_general`) AS `moy_general`,
    MAX(`b`.`moy_max`) AS `moy_max`,
    MAX(`b`.`moy_min`) AS `moy_min`,
    MAX(`b`.`moy_avg`) AS `moy_avg`,
    MAX(`b`.`moy_annuelle`) AS `moy_annuelle`,
    MAX(`b`.`rang_annuelle`) AS `rang_annuelle`,
    MAX(`b`.`appreciation_conseil`) AS `appreciation_conseil`,
    MAX(`b`.`date_creation`) AS `date_creation`,
    MAX(`b`.`code_qr`) AS `code_qr`,
    MAX(`b`.`statut`) AS `statut`,
    MAX(`b`.`is_classed`) AS `is_classed_periode`,
    MAX(`b`.`effectif_non_classe`) AS `effectif_non_classe`,
    `d`.`libelle_matiere` AS `libelle_matiere`,
    CAST(
        MAX(`b`.`rang`) AS CHAR(10) CHARSET utf8mb4
    ) AS `rangBulletin`,
    MAX(`b`.`nom_signataire`) AS `signataire`,
    MAX(
        CASE WHEN `b`.`libelle_periode` = 'Troisième Trimestre' OR `b`.`libelle_periode` = 'Premier Semestre' THEN `d`.`moyenne` ELSE NULL
    END
) AS `moyenne`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Troisième Trimestre' OR `b`.`libelle_periode` = 'Premier Semestre' THEN `d`.`rang` ELSE NULL
END
) AS `rang`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Troisième Trimestre' OR `b`.`libelle_periode` = 'Premier Semestre' THEN `d`.`is_classed` ELSE NULL
END
) AS `is_classed_mat`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Premier Trimestre' OR `b`.`libelle_periode` = 'Premier Semestre' THEN `d`.`moyenne` ELSE NULL
END
) AS `moyennePremier`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Premier Trimestre' OR `b`.`libelle_periode` = 'Premier Semestre' THEN `d`.`rang` ELSE NULL
END
) AS `rangPremier`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Premier Trimestre' OR `b`.`libelle_periode` = 'Premier Semestre' THEN `d`.`is_classed` ELSE NULL
END
) AS `is_classed_matPremier`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Deuxième Trimestre' OR `b`.`libelle_periode` = 'Deuxième Semestre' THEN `d`.`moyenne` ELSE NULL
END
) AS `moyenneDeuxieme`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Deuxième Trimestre' OR `b`.`libelle_periode` = 'Deuxième Semestre' THEN `d`.`rang` ELSE NULL
END
) AS `rangDeuxieme`,
MAX(
    CASE WHEN `b`.`libelle_periode` = 'Deuxième Trimestre' OR `b`.`libelle_periode` = 'Deuxième Semestre' THEN `d`.`is_classed` ELSE NULL
END
) AS `is_classed_matDeuxieme`,
MAX(`d`.`coef`) AS `coef`,
MAX(`d`.`moy_coef`) AS `moy_coef`,
MAX(`d`.`appreciation`) AS `appreciation`,
MAX(`d`.`num_ordre`) AS `num_ordre`,
MAX(`d`.`nom_prenom_professeur`) AS `nom_prenom_professeur`,
MAX(`d`.`categorie`) AS `libelle_categorie`,
MAX(`d`.`categorie`) AS `categorie`,
MAX(`d`.`moy_An`) AS `moy_anMat`,
MAX(`d`.`rang_An`) AS `rang_anMat`,
CAST(
    MAX(`d`.`bonus`) AS CHAR(10) CHARSET utf8mb4
) AS `bonus`,
CAST(
    MAX(`d`.`pec`) AS CHAR(10) CHARSET utf8mb4
) AS `pec`
FROM
    (
        `ecoleviedbv2`.`bulletins` `b`
    JOIN `ecoleviedbv2`.`detail_bulletins` `d`
    ON
        (`b`.`id` = `d`.`bulletin_id`)
    )
WHERE
    `b`.`matricule` = '20513560T' AND `b`.`annee_libelle` = 'Année 2022 - 2023'
GROUP BY
    `b`.`nom`,
    `b`.`prenoms`,
    `b`.`matricule`,
    `d`.`libelle_matiere`
ORDER BY
    `b`.`nom`,
    `b`.`prenoms`,
    `b`.`matricule`