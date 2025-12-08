SELECT
    `b`.`id_ecole` AS `id_ecole`,
    `b`.`libelle_periode` AS `libelle_periode`,
    `b`.`annee_libelle` AS `annee_libelle`,
    `b`.`nom_ecole` AS `nom_ecole`,
    `b`.`nom` AS `nom`,
    `b`.`prenoms` AS `prenoms`,
    `b`.`matricule` AS `matricule`,
    `b`.`niveau` AS `niveau`,
    FORMAT(`b`.`moy_general`, 2, 'fr_FR') AS `moyenTrim`,
    `b`.`rang` AS `rang`,
    `b`.`ordre_niveau` AS `ordre_niveau`,
    `b`.`is_classed` AS `is_class_periode`,
    MAX(
        (
            CASE WHEN(`d`.`id_matiere` = 1) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
        END
    )
) AS `moyen_fr`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 1) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_class_fr`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 2) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenCompoFr`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 2) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasCompoFr`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 4) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenOrthoGram`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 4) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasOrthoGram`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 3) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenExpreOral`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 3) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasExpreOral`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 26) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenphiloso`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 26) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasphiloso`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 5) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenAng`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 5) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasAng`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 7) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenMath`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 7) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasMath`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 8) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenPhysiq`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 8) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasPhysiq`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 9) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenSVT`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 9) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasSVT`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 6) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenHg`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 6) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasHg`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 11) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenEdhc`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 11) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasEdhc`,
MAX(
    (
        CASE WHEN(
            (`d`.`id_matiere` = 19) OR(`d`.`id_matiere` = 36)
        ) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenArplat`,
MAX(
    (
        CASE WHEN(
            (`d`.`id_matiere` = 19) OR(`d`.`id_matiere` = 36)
        ) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasArplat`,
MAX(
    (
        CASE WHEN(
            (`d`.`id_matiere` = 25) OR(`d`.`id_matiere` = 21)
        ) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenLv2`,
MAX(
    (
        CASE WHEN(
            (`d`.`id_matiere` = 25) OR(`d`.`id_matiere` = 21)
        ) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasLv2`,
MAX(
    (
        CASE WHEN(
            (`d`.`id_matiere` = 13) OR(`d`.`id_matiere` = 27)
        ) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenTic`,
MAX(
    (
        CASE WHEN(
            (`d`.`id_matiere` = 13) OR(`d`.`id_matiere` = 27)
        ) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasTic`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 12) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenConduite`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 12) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasConduite`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 10) THEN FORMAT(`d`.`moyenne`, 2, 'fr_FR') ELSE NULL
    END
)
) AS `moyenEps`,
MAX(
    (
        CASE WHEN(`d`.`id_matiere` = 10) THEN `d`.`is_classed` ELSE NULL
    END
)
) AS `is_clasEps`
FROM
    (
        `ecoleviedbv2`.`Bulletins` `b`
    JOIN `ecoleviedbv2`.`Detail_bulletins` `d`
    )
WHERE
    (`b`.`id` = `d`.`bulletin_id`)
GROUP BY
    `b`.`id_ecole`,
    `b`.`libelle_periode`,
    `b`.`annee_libelle`,
    `b`.`nom_ecole`,
    `b`.`nom`,
    `b`.`prenoms`,
    `b`.`matricule`,
    `b`.`niveau`,
    `b`.`moy_general`,
    `b`.`rang`,
    `b`.`ordre_niveau`,
    `b`.`is_classed`
ORDER BY
    `b`.`ordre_niveau`