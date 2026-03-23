package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.BulletinPoiResultatsAggregator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Statistiques résultats « affectés » pour rapports POI. Les calculs passent par
 * {@link BulletinPoiResultatsAggregator} (1 requête + agrégation mémoire par ordre de niveau).
 */
@ApplicationScoped
public class resultatsPoiServices {

    @Inject
    BulletinPoiResultatsAggregator bulletinPoiResultatsAggregator;

    public List<ResultatsElevesAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String libelleAnnee,
            String libelleTrimestre, int order) {
        return bulletinPoiResultatsAggregator.calculResultatsAffectes(idEcole, libelleAnnee, libelleTrimestre, order);
    }
}
