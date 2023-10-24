package com.vieecoles.services.etats;

import com.vieecoles.dto.ClasseMatiereDto;
import com.vieecoles.dto.MatriceMoyenneDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RapportRentreeDto;
import com.vieecoles.entities.fonction;
import com.vieecoles.entities.matiere;
import com.vieecoles.entities.niveau_etude;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;

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

    public List<RapportRentreeDto>  rapportRentree(Long idEcole ,Long  AnneeId){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;


       // System.out.println("Ecole ::: " + ecoleId);
       List<ClasseMatiere>  classeMatiereList = new ArrayList<>() ;
        classeMatiereList = ClasseMatiere.find("select distinct m.matiere.id from ClasseMatiere m where m.matiere.ecole.id = ?1", idEcole).list();

        LongTableau= classeMatiereList.size() ;
        List<RapportRentreeDto> resultatsListElevesDto = new ArrayList<>();

        long idMatiere = 0;
        Long nbreBac2G,nbreBac2F ,nbreCMG,nbreCMF,nbreCPLG,nbreCPLF ,nbreMaitriseG,nbreMaitriseF,nbreCAPESG ,nbreCAPESF;
       // classeNiveauDtoList.toString() ;
        String libelleMatiere = null;

        for (int i=0; i< LongTableau;i++) {
            RapportRentreeDto m = new RapportRentreeDto();
           String id = String.valueOf(classeMatiereList.get(i));
            idMatiere = Long.parseLong(id);

            System.out.print("idMatiere "+idMatiere);
         libelleMatiere= getLibelleMatiere(idMatiere) ;

           //System.out.print("libelleMatiere "+idMatiere);

            niveau_etude n = new niveau_etude() ;
           Integer nivBac2 = getNiveauEtudeLibelle("BAC + 2");
           System.out.println("nivBac2 "+ nivBac2);
            Integer nivCM = getNiveauEtudeLibelle("CAP/CM");
            Integer nivCPL = getNiveauEtudeLibelle("CAP/CPL");
            Integer nivMaitrise = getNiveauEtudeLibelle("LIC/MAITRISE");
            Integer nivCapes = getNiveauEtudeLibelle("CAPES");

           nbreBac2G = countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"MASCULIN",nivBac2);
            System.out.println("nbreBac2G "+ nbreBac2G);
           nbreBac2F = countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"FEMININ",nivBac2);

            nbreCMF =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"FEMININ",nivCM);
            nbreCMG =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"MASCULIN",nivCM);

            nbreCPLF =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"FEMININ",nivCPL);
            nbreCPLG =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"MASCULIN",nivCPL);

            nbreMaitriseF =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"FEMININ",nivMaitrise);
            nbreMaitriseG =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"MASCULIN",nivMaitrise);

            nbreCAPESF =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"FEMININ",nivCapes);
            nbreCAPESG =  countProfByMatiereAndEcole(idEcole ,idMatiere ,AnneeId,"MASCULIN",nivCapes);

            m.setLibelleMatiere(libelleMatiere);
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



    public Long countProfByMatiereAndEcole(Long ecoleId, Long matiereId, Long anneeId,String sexe ,Integer niveauEtude) {
        try {
            return PersonnelMatiereClasse.find("select distinct p.personnel from PersonnelMatiereClasse p where p.classe.ecole.id = ?1 and p.matiere.id =?2 and p.annee.id=?3 and p.personnel.sexe =?4 and p.personnel.niveauEtude =?5 and (p.statut is null or p.statut <> 'DELETED') ",
                            ecoleId, matiereId,anneeId,sexe , niveauEtude)
                    .count();
        }catch(RuntimeException r) {
            r.printStackTrace();
            return 0L;
        }
    }


    public   Integer  getNiveauEtudeLibelle(String libelle ){
        try {
            TypedQuery<niveau_etude> q = (TypedQuery<niveau_etude>) em.createQuery( "SELECT  o from niveau_etude o where o.niveau_etude_libelle =:libelle ");
            niveau_etude niv = q.setParameter("libelle" ,libelle).getSingleResult() ;

            return Math.toIntExact(niv.getNiveau_etudeid());
        } catch (NoResultException e) {
            return 0 ;
        }

    }

    public   String  getLibelleMatiere(Long id ){
        try {
            TypedQuery<String> q = (TypedQuery<String>) em.createQuery( "SELECT  o.libelle from EcoleHasMatiere o where o.id=:id");
            String libelle = q.setParameter("id" ,id).getSingleResult() ;

            return libelle;
        } catch (NoResultException e) {
            return null ;
        }

    }













}
