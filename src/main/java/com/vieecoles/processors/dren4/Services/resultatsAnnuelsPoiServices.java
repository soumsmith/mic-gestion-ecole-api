package com.vieecoles.processors.dren4.Services;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.BulletinPoiResultatsAggregator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Annuel Dren4 : même agrégation que le trimestre, avec {@code moyAn} projetée sur la projection légère.
 */
@ApplicationScoped
public class resultatsAnnuelsPoiServices {

    @Inject
    BulletinPoiResultatsAggregator bulletinPoiResultatsAggregator;

    public List<ResultatsElevesAffecteDto> CalculResultatsEleveAffecte(Long idEcole, String libelleAnnee,
            String libelleTrimestre) {
        return bulletinPoiResultatsAggregator.calculResultatsAffectesTousNiveauxAnnuel(idEcole, libelleAnnee,
                libelleTrimestre);
    }
}
