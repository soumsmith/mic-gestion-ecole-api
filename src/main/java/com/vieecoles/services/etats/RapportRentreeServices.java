package com.vieecoles.services.etats;

import com.vieecoles.dto.MatriceMoyenneDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RapportRentreeDto;
import com.vieecoles.steph.entities.ClasseMatiere;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RapportRentreeServices {
    @Inject
    EntityManager em;

    public List<RapportRentreeDto>  rapportRentree(Long idEcole ){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;


       // System.out.println("Ecole ::: " + ecoleId);
       List<ClasseMatiere>  classeMatiereList = new ArrayList<>() ;
        classeMatiereList = ClasseMatiere.find("select distinct m.matiere.libelle from ClasseMatiere m where m.matiere.ecole.id = ?1", idEcole).list();

        //System.out.println("classeMatiere "+classeMatiereList.get(0));

        //classeNiveauDtoList = classeMatiereList ;

        LongTableau= classeMatiereList.size() ;
        List<RapportRentreeDto> resultatsListElevesDto = new ArrayList<>();

        String libelleMatiere = null;
        Long nbreBac2G,nbreBac2F ,nbreCMG,nbreCMF,nbreCPLG,nbreCPLF ,nbreMaitriseG,nbreMaitriseF,nbreCAPESG ,nbreCAPESF;
        classeNiveauDtoList.toString() ;
        System.out.println("classeMatiere "+classeMatiereList.get(1));
        for (int i=0; i< LongTableau;i++) {
            RapportRentreeDto m = new RapportRentreeDto();

           libelleMatiere= String.valueOf(classeMatiereList.get(i));
           System.out.print("libelleMatiere "+libelleMatiere);
            nbreBac2G = getNombre(libelleMatiere ,"MASCULIN","BAC + 2");
            nbreBac2F = getNombre(libelleMatiere ,"FEMININ","BAC + 2");

            nbreCMF =  getNombre(libelleMatiere ,"FEMININ","CAP/CM");
            nbreCMG =  getNombre(libelleMatiere ,"MASCULIN","CAP/CM");

            nbreCPLF =  getNombre(libelleMatiere ,"FEMININ","CAP/CPL");
            nbreCPLG =  getNombre(libelleMatiere ,"MASCULIN","CAP/CPL");

            nbreMaitriseF =  getNombre(libelleMatiere ,"FEMININ","LIC/MAITRISE");
            nbreMaitriseG =  getNombre(libelleMatiere ,"MASCULIN","LIC/MAITRISE");

            nbreCAPESF =  getNombre(libelleMatiere ,"FEMININ","CAPES");
            nbreCAPESG =  getNombre(libelleMatiere ,"MASCULIN","CAPES");

            m.setNbreBac2F(nbreBac2F);
            m.setNbreBac2G(nbreBac2G);
            m.setNbreCAPESF(nbreCAPESF);
            m.setNbreCAPESG(nbreCAPESG);
            m.setNbreCMF(nbreCMF);
            m.setNbreCMG(nbreCMG);
            m.setNbreCPLF(nbreCPLF);
            m.setNbreCPLG(nbreCPLG);
            m.setNbreMaitriseG(nbreMaitriseG);
            m.setNbreMaitriseF(nbreMaitriseF);


           resultatsListElevesDto.add(m);

        }

        return  resultatsListElevesDto ;
    }

    public  Long  getNombre(String libelleMatiere,String sexe ,String diplome){
        try {
            Long   appreci = (Long) em.createQuery("select count (p.id) from  personnel p ,niveau_etude n , Personnel_matiere_classe   m where p.niveau_etude.id=n.niveau_etudeid and p.personnelid= m.personnel.personnelid and m.matiere.matierelibelle =:matiere and " +
                            " n.niveau_etude_libelle=:diplome and p.personnel_sexe=:sexe")
                    .setParameter("matiere",libelleMatiere)
                    .setParameter("sexe",sexe)
                    .setParameter("diplome",diplome)
                    .getSingleResult();
            return  appreci ;
        } catch (NoResultException e){
            return 0L;
        }

    }
















}
