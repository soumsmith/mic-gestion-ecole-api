package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.eleveAffecteParClasseDtoAvecTousTrimestres;
import com.vieecoles.dto.eleveNonAffecteParClasseDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class EleveNonAffecteParClassePoiServices {
    @Inject
    EntityManager em;

    public List<eleveAffecteParClasseDtoAvecTousTrimestres>  eleveNonAffecteParClasse(Long idEcole ,String libelleAnnee , String libelleTrimestre,String classe){
        int LongTableau;

        List<NiveauOrderDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauOrderDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauOrderDto(b.libelleClasse,b.ordreNiveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.libelleClasse=:classe " +
                "group by b.ordreNiveau, b.libelleClasse order by b.ordreNiveau, b.libelleClasse ", NiveauOrderDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                                  .setParameter("classe", classe)
                                  .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<eleveAffecteParClasseDtoAvecTousTrimestres> resultatsListElevesDto = new ArrayList<>();


        for (int i=0; i< LongTableau;i++) {
            List<eleveAffecteParClasseDtoAvecTousTrimestres> resultatsListEleves= new ArrayList<>();

            resultatsListEleves = getListEleveNonAffectParClassDto(idEcole,classeNiveauDtoList.get(i).getNiveau(),libelleAnnee , libelleTrimestre);

            resultatsListElevesDto.addAll(resultatsListEleves);

        }

        return  resultatsListElevesDto ;
    }







    public List<eleveAffecteParClasseDtoAvecTousTrimestres> getListEleveNonAffectParClassDto(Long idEcole , String classe, String libelleAnnee , String libelleTrimestre){

        try {
            // 1. Récupérer tous les bulletins pertinents en une SEULE requête
            TypedQuery<Object[]> qTousLesBulletins = em.createQuery(
                "SELECT o.matricule, o.nom, o.prenoms, o.sexe, o.dateNaissance, o.nationalite, " +
                    "o.redoublant, o.affecte, o.numDecisionAffecte, o.nomPrenomEducateur, o.ordreNiveau, " +
                    "o.libelleClasse, o.nomPrenomProfPrincipal, o.libellePeriode, o.moyGeneral, o.rang, o.appreciation " +
                    "FROM Bulletin o " +
                    "WHERE o.ecoleId = :idEcole AND o.libelleClasse = :classe " +
                    "AND o.affecte = :affecte AND o.anneeLibelle = :annee " +
                    "ORDER BY o.nom, o.prenoms, o.libellePeriode",
                Object[].class);

            List<Object[]> tousLesBulletins = qTousLesBulletins
                .setParameter("idEcole", idEcole)
                .setParameter("classe", classe)
                .setParameter("affecte", "NON_AFFECTE")
                .setParameter("annee", libelleAnnee)
                .getResultList();
            System.out.println("Nombre Bulletin "+tousLesBulletins.size());
            // 2. Organiser les données par élève
            Map<String, eleveAffecteParClasseDtoAvecTousTrimestres> elevesMap = new HashMap<>();

            for (Object[] bulletinData : tousLesBulletins) {
                String matricule = (String) bulletinData[0];
                String trimestre = (String) bulletinData[13]; // libellePeriode
                Double moyenneGenerale = (Double) bulletinData[14];
                Integer rang = (Integer) bulletinData[15];
                String appreciation = (String) bulletinData[16];
                String nomProfesseur= (String) bulletinData[12];

                if (!elevesMap.containsKey(matricule)) {
                    eleveAffecteParClasseDtoAvecTousTrimestres eleve = new eleveAffecteParClasseDtoAvecTousTrimestres();

                    // Remplir les informations de base de l'élève
                    eleve.setMatricule(matricule);
                    eleve.setNomEleve((String) bulletinData[1]);
                    eleve.setPrenomEleve((String) bulletinData[2]);
                    eleve.setSexe((String) bulletinData[3]);
                    eleve.setAnneeNaissance((String) bulletinData[4]);
                    eleve.setNationnalite((String) bulletinData[5]);
                    eleve.setRedoublan((String) bulletinData[6]);
                    eleve.setAffecte((String) bulletinData[7]);
                    eleve.setNumDecisionAffecte((String) bulletinData[8]);
                    eleve.setNomEducateur((String) bulletinData[9]);
                    eleve.setOrdre_niveau((Integer) bulletinData[10]);
                    eleve.setClasseLibelle((String) bulletinData[11]);
                    //eleve.setProfesseurPrincipal((String) bulletinData[12]);
                    //eleve.setRang(rang);
                   // eleve.setObservat(appreciation);


                    elevesMap.put(matricule, eleve);
                }

                // Ajouter les informations du trimestre
                eleveAffecteParClasseDtoAvecTousTrimestres eleve = elevesMap.get(matricule);

                if ("Premier Trimestre".equals(trimestre)) {
                    eleve.setMoyeGeneralTrim1(moyenneGenerale);

                } else if ("Deuxième Trimestre".equals(trimestre)) {
                    eleve.setMoyeGeneralTrim2(moyenneGenerale);

                } else if ("Troisième Trimestre".equals(trimestre)) {
                    eleve.setMoyeGeneralTrim3(moyenneGenerale);
                    eleve.setRang(rang);
                    eleve.setProfesseurPrincipal(nomProfesseur);
                    eleve.setObservat(appreciation);

                }
            }

            List<eleveAffecteParClasseDtoAvecTousTrimestres> resultatFinal = new ArrayList<>(elevesMap.values());

            // 4. Trier la liste finale par nom et prénom
            resultatFinal.sort(Comparator.comparing(eleveAffecteParClasseDtoAvecTousTrimestres::getNomEleve)
                .thenComparing(eleveAffecteParClasseDtoAvecTousTrimestres::getPrenomEleve));

            return resultatFinal;

        } catch (NoResultException e){
            return null ;
        }

    }


}
