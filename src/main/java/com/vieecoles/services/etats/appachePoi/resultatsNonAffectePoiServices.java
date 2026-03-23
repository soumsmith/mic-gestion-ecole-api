package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.ResultatsElevesNonAffecteDto;
import com.vieecoles.services.etats.BulletinPoiResultatsAggregator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Statistiques résultats « non affectés » pour rapports POI (1 requête + agrégation mémoire).
 */
@ApplicationScoped
public class resultatsNonAffectePoiServices {

    @Inject
    BulletinPoiResultatsAggregator bulletinPoiResultatsAggregator;

    public List<ResultatsElevesNonAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String libelleAnnee,
            String libelleTrimestre, int order) {
        return bulletinPoiResultatsAggregator.calculResultatsNonAffectes(idEcole, libelleAnnee, libelleTrimestre,
                order);
    }
}
