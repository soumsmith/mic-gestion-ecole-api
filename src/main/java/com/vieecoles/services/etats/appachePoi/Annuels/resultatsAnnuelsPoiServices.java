package com.vieecoles.services.etats.appachePoi.Annuels;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.BulletinPoiResultatsAggregator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Résultats annuels affectés : même agrégation optimisée que le trimestre (données toujours dans {@code Bulletins}
 * filtrées par période / année libellée).
 */
@ApplicationScoped
public class resultatsAnnuelsPoiServices {

    @Inject
    BulletinPoiResultatsAggregator bulletinPoiResultatsAggregator;

    public List<ResultatsElevesAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String libelleAnnee,
            String libelleTrimestre, int order) {
        return bulletinPoiResultatsAggregator.calculResultatsAffectes(idEcole, libelleAnnee, libelleTrimestre, order);
    }
}
