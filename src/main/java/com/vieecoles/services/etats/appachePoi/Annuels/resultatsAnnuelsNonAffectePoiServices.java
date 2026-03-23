package com.vieecoles.services.etats.appachePoi.Annuels;

import com.vieecoles.dto.ResultatsElevesNonAffecteDto;
import com.vieecoles.services.etats.BulletinPoiResultatsAggregator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class resultatsAnnuelsNonAffectePoiServices {

    @Inject
    BulletinPoiResultatsAggregator bulletinPoiResultatsAggregator;

    public List<ResultatsElevesNonAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String libelleAnnee,
            String libelleTrimestre, int order) {
        return bulletinPoiResultatsAggregator.calculResultatsNonAffectes(idEcole, libelleAnnee, libelleTrimestre,
                order);
    }
}
