package com.vieecoles.processors.dren4.Services;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.BulletinPoiResultatsAggregator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Délègue à {@link BulletinPoiResultatsAggregator} (1 requête + agrégation mémoire)
 * au lieu des dizaines de requêtes JPQL par classe.
 */
@ApplicationScoped
public class resultatsPoiServices {

    @Inject
    BulletinPoiResultatsAggregator bulletinPoiResultatsAggregator;

    public List<ResultatsElevesAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String libelleAnnee,
            String libelleTrimestre) {
        return bulletinPoiResultatsAggregator.calculResultatsAffectesTousNiveaux(idEcole, libelleAnnee,
                libelleTrimestre);
    }
}
