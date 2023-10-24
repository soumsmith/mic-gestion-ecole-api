package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.niveau_etude;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.services.ClasseMatiereService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmploiDuTempsServices {
    @Inject
    EntityManager em;
    @Inject
    ClasseMatiereService cmService;

    public  EmploiDuTemps  EmploiDuTemps(Long idEcole , Long  AnneeId ,Long classeid){
        int LongTableau;


      EmploiDuTemps resultatsListElevesDto = new EmploiDuTemps() ;

        List<MatiereLunDto> matiereLunDtos  = new ArrayList<>(); List<MatiereMardDto > matiereMardDtos = new ArrayList<>() ;
        List<MatiereMercDto> matiereMercDtos = new ArrayList<>() ; List<MatiereJeudDto> matiereJeudDtos = new ArrayList<>() ;
        List<MatiereVendDto> matiereVendDtos = new ArrayList<>() ; List<MatiereSamDto> matiereSamDtos = new ArrayList<>() ;
        List<MatProf> matProfs = new ArrayList<>() ;








            List<Activite> act = new ArrayList<>() ;
            act = getListByClasseAndJour(classeid,1) ;

            for (int i=0; i< act.size();i++){
                MatiereLunDto lunDto = new MatiereLunDto() ;
                lunDto.setHeureDebLun(act.get(i).getHeureDeb());
                lunDto.setHeureFinLun(act.get(i).getHeureFin());
                lunDto.setMatiereLun(act.get(i).getMatiere().getMatiere().getLibelle());
                lunDto.setActiviteLun(act.get(i).getTypeActivite().getLibelle());
                matiereLunDtos.add(lunDto) ;
             }
            act = getListByClasseAndJour(classeid,2) ;
            for (int i=0; i< act.size();i++){
                MatiereMardDto mardDto = new MatiereMardDto() ;
                mardDto.setHeureDebMard(act.get(i).getHeureDeb());
                mardDto.setHeureFinMard(act.get(i).getHeureFin());
                mardDto.setMatiereMard(act.get(i).getMatiere().getMatiere().getLibelle());
                mardDto.setActiviteMard(act.get(i).getTypeActivite().getLibelle());
                matiereMardDtos.add(mardDto) ;
             }

        act = getListByClasseAndJour(classeid,3) ;
        for (int i=0; i< act.size();i++){
            MatiereMercDto mercDto = new MatiereMercDto() ;
            mercDto.setHeureDebMerc(act.get(i).getHeureDeb());
            mercDto.setHeureFinMerc(act.get(i).getHeureFin());
            mercDto.setMatiereMerc(act.get(i).getMatiere().getMatiere().getLibelle());
            mercDto.setActiviteMerc(act.get(i).getTypeActivite().getLibelle());
            matiereMercDtos.add(mercDto) ;
        }
        act = getListByClasseAndJour(classeid,4) ;
        for (int i=0; i< act.size();i++){
            MatiereJeudDto jeudDto = new MatiereJeudDto() ;
            jeudDto.setHeureDebJeud(act.get(i).getHeureDeb());
            jeudDto.setHeureFinJeud(act.get(i).getHeureFin());
            jeudDto.setMatiereJeud(act.get(i).getMatiere().getMatiere().getLibelle());
            jeudDto.setActiviteJeud(act.get(i).getTypeActivite().getLibelle());
            matiereJeudDtos.add(jeudDto) ;
        }
        act = getListByClasseAndJour(classeid,5) ;
        for (int i=0; i< act.size();i++){
            MatiereVendDto vendDto = new MatiereVendDto() ;
            vendDto.setHeureDebVend(act.get(i).getHeureDeb());
            vendDto.setHeureFinVend(act.get(i).getHeureFin());
            vendDto.setMatiereVend(act.get(i).getMatiere().getMatiere().getLibelle());
            vendDto.setActiviteVend(act.get(i).getTypeActivite().getLibelle());
            matiereVendDtos.add(vendDto) ;
        }

        act = getListByClasseAndJour(classeid,6) ;
        for (int i=0; i< act.size();i++){
            MatiereSamDto samDto = new MatiereSamDto() ;;
            samDto.setHeureDebSam(act.get(i).getHeureDeb());
            samDto.setHeureFinSam(act.get(i).getHeureFin());
            samDto.setMatiereSam(act.get(i).getMatiere().getMatiere().getLibelle());
            samDto.setActiviteSam(act.get(i).getTypeActivite().getLibelle());
            matiereSamDtos.add(samDto) ;
        }
        List<PersonnelMatiereClasse> listPers = new ArrayList<>() ;
        listPers= findListByClasse(AnneeId,classeid) ;

        for (int k=0; k< listPers.size();k++){
            MatProf p = new MatProf() ;
            p.setMatiere(listPers.get(k).getMatiere().getLibelle());
            p.setNomProfess(listPers.get(k).getPersonnel().getNom()+" "+listPers.get(k).getPersonnel().getPrenom());
            matProfs.add(p) ;

        }

        resultatsListElevesDto.setMatiereLunDtos(matiereLunDtos);
        resultatsListElevesDto.setMatiereMardDtos(matiereMardDtos);
        resultatsListElevesDto.setMatiereMercDtos(matiereMercDtos);
        resultatsListElevesDto.setMatiereJeudDtos(matiereJeudDtos);
        resultatsListElevesDto.setMatiereVendDtos(matiereVendDtos);
        resultatsListElevesDto.setMatiereSamDtos(matiereSamDtos);
        resultatsListElevesDto.setMatProfs(matProfs);







        return  resultatsListElevesDto ;
    }





    public List<Activite> getListByClasseAndJour(long classeId, int jourId) {
        //logger.info(String.format("Annee %s - classe %s - jour %s", anneeId, classeId, jourId));
        return Activite.find("classe.id = ?1 and jour.id = ?2 and statut = ?3", classeId, jourId, Constants.ACTIF)
                .list();
    }


    public List<PersonnelMatiereClasse> findListByClasse(long annee, long classe) {
        List<PersonnelMatiereClasse> list = new ArrayList<PersonnelMatiereClasse>();
        try {
            list = PersonnelMatiereClasse.find("annee.id = ?1 and classe.id =?2 and matiere is not null and (statut is null or statut <> 'DELETED') ", annee, classe)
                    .list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return addCoefficientMatiere(list);
    }

    List<PersonnelMatiereClasse> addCoefficientMatiere(List<PersonnelMatiereClasse> listPersonnels) {
        for(PersonnelMatiereClasse ps : listPersonnels) {
            ClasseMatiere cm = cmService.getByMatiereAndBranche(ps.getMatiere().getId(), ps.getClasse().getBranche().getId(), ps.getClasse().getEcole().getId());
            if(cm != null) {
                ps.getMatiere().setCoef(cm.getCoef());
            }
        }
        return listPersonnels;
    }






}
