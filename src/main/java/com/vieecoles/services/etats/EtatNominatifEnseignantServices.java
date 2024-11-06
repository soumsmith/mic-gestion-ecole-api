package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.projection.EnseignantSelectDto;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.services.ClasseMatiereService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EtatNominatifEnseignantServices {
    @Inject
    EntityManager em;
    @Inject
    ClasseMatiereService cmService;

    public  List<EtatNominatifEnseignatDto>  infosEtatNominEnseignant(Long idEcole , Long  AnneeId ){
        int LongTableau;

       String nom ,prenoms , dateNaiss , diplome ,statutVacPer , contact ;



        List<EnseignantSelectDto> listPers = new ArrayList<>() ;
        List<EtatNominatifEnseignatDto> listEtatNomina = new ArrayList<>() ;
        listPers= getInfosEnseignant(idEcole,AnneeId) ;

        for (int k=0; k< listPers.size();k++){
            EtatNominatifEnseignatDto m = new EtatNominatifEnseignatDto() ;
            List<ClassesTenuesDto> classesTenuesDto = new ArrayList<>() ;
           // System.out.println("classesTenuesDto***** "+listPers.get(k).getId());
            classesTenuesDto = getClasseProf(listPers.get(k).getId(),AnneeId) ;
           // System.out.println("classesTenuesDto "+ classesTenuesDto.toString());
            m.setNom(listPers.get(k).getNom());
            m.setPrenoms(listPers.get(k).getPrenoms());
            m.setContact(listPers.get(k).getContact());
            m.setStatutVacPer(listPers.get(k).getStatutVacPer());
            m.setDiplome(listPers.get(k).getDiplome());
            m.setClassesTenuesDto(classesTenuesDto);
            String dateNaissPer = null;
            // Create a DateTimeFormatter object.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if(listPers.get(k).getDateNaiss()!=null) {

                dateNaissPer = listPers.get(k).getDateNaiss().format(formatter);

            }
            m.setDateNaiss(dateNaissPer);
            listEtatNomina.add(m) ;
        }


        return  listEtatNomina ;
    }








    public List<EnseignantSelectDto>  getInfosEnseignant(Long idEcole ,Long AnnId){

        List<EnseignantSelectDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<EnseignantSelectDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.projection.EnseignantSelectDto(p.personnelid , p.personnelnom , p.personnelprenom , p.personneldatenaissance\n" +
                        " , n.niveau_etude_libelle  , p.personnel_contact , ps.personnel_statulibelle) FROM Personnel_matiere_classe  pm ,classe c , ecole e   , niveau_etude n ,Annee_Scolaire an ,personnel p " +
                        "left JOIN  personnel_status ps on p.personnel_status_personnel_statusid = ps.personnel_statusid where pm.classe.classeid = c.classeid and c.ecole_ecoleid = e.ecoleid  and pm.personnel.personnelid = p.personnelid \n" +
                        " and p.niveau_etude.niveau_etudeid = n.niveau_etudeid and pm.annee_scolaire.annee_scolaireid = an.annee_scolaireid and e.ecoleid =:idEcole and an.annee_scolaireid=:idAnn"
                , EnseignantSelectDto.class);
     return    classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                     .setParameter("idAnn", AnnId)
                .getResultList() ;
    }

    public  List<ClassesTenuesDto> getClasseProf(Long idPersonn , Long idAnn) {

        List<ClassesTenuesDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<ClassesTenuesDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.ClassesTenuesDto(c.classelibelle,pm.personnel.personnelid ,an.annee_scolaireid ) FROM Personnel_matiere_classe pm , classe c , ecole e  ,personnel p , niveau_etude n ,Annee_Scolaire an " +
                        "where pm.classe.classeid = c.classeid and c.ecole_ecoleid = e.ecoleid  and pm.personnel.personnelid = p.personnelid" +
                        " and p.niveau_etude.niveau_etudeid  = n.niveau_etudeid and pm.annee_scolaire.annee_scolaireid= an.annee_scolaireid and " +
                        " p.personnelid =:idPersonn and an.annee_scolaireid =:idAnn "
                , ClassesTenuesDto.class);
   return      classeNiveauDtoList = q.setParameter("idPersonn", idPersonn)
                .setParameter("idAnn", idAnn)
                .getResultList() ;


    }

}
