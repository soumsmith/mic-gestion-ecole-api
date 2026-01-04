package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.MajorParClasseNiveauDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class MajorParClasseNiveauPoiServices {
    @Inject
    EntityManager em;

    public List<MajorParClasseNiveauDto> MajorParNiveauClasse(Long idEcole ,String libelleAnnee , String libelleTrimestre){

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole  and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.libelleClasse order by b.libelleClasse", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                            .setParameter("annee", libelleAnnee)
                            .setParameter("periode", libelleTrimestre)
                           . getResultList() ;


      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10F ,pourMoySup10G,pourMoyInf999F,pourMoyInf999G,pourMoyInf85G,pourMoyInf85F,moyClasseF,moyClasseG;
       Integer effectifClasse ;
        List<MajorParClasseNiveauDto> resultatsListElevesDto = new ArrayList<>(LongTableau);

      resultatsListElevesDto= getAllMajorsEcole(idEcole,libelleAnnee,libelleTrimestre);

        return  resultatsListElevesDto ;
    }





  public List<MajorParClasseNiveauDto> getAllMajorsEcole(Long idEcole, String libelleAnnee, String libelleTrimestre) {
    List<MajorParClasseNiveauDto> tousLesMajors = new ArrayList<>();

    try {
      // Une seule requête pour récupérer les premiers de chaque classe
      Query q = em.createNativeQuery(
          "SELECT niveau, libelle_classe, matricule, nom, prenoms, " +
            "date_naissance, sexe, affecte, moy_general, lv2, ordre_niveau " +
            "FROM ( " +
            "  SELECT *, ROW_NUMBER() OVER (PARTITION BY libelle_classe ORDER BY moy_general DESC) as rn " +
            "  FROM Bulletins " +
            "  WHERE id_ecole = ?1 AND libelle_periode = ?2 AND annee_libelle = ?3 " +
            ") ranked " +
            "WHERE rn = 1 " +
            "ORDER BY libelle_classe, moy_general DESC");

      q.setParameter(1, idEcole);
      q.setParameter(2, libelleTrimestre);
      q.setParameter(3, libelleAnnee);

      List<Object[]> results = q.getResultList();

      for (Object[] result : results) {
        MajorParClasseNiveauDto dto = new MajorParClasseNiveauDto();
        dto.setNiveau((String) result[0]);
        dto.setClasseLibelle((String) result[1]);
        dto.setMatricule((String) result[2]);
        dto.setNom((String) result[3]);
        dto.setPrenom((String) result[4]);
        dto.setAnneeNaiss((String) result[5]);
        dto.setSexe((String) result[6]);
        dto.setAffecte((String) result[7]);
        dto.setMoyGeneral(convertToDouble(result[8]));
        dto.setLv2((String) result[9]);
        dto.setOrdre_niveau((Integer) result[10]);

        tousLesMajors.add(dto);
      }

      return tousLesMajors;

    } catch (Exception e) {
      System.err.println("Erreur lors de la récupération de tous les majors: " + e.getMessage());
      e.printStackTrace();
      return tousLesMajors;
    }
  }
  private Double convertToDouble(Object value) {
    if (value == null) {
      return 0.0;
    }
    if (value instanceof BigDecimal) {
      return ((BigDecimal) value).doubleValue();
    } else if (value instanceof Double) {
      return (Double) value;
    } else if (value instanceof Number) {
      return ((Number) value).doubleValue();
    } else {
      try {
        return Double.valueOf(value.toString());
      } catch (NumberFormatException e) {
        return 0.0;
      }
    }
  }

}
