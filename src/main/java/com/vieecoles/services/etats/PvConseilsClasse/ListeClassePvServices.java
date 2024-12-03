package com.vieecoles.services.etats.PvConseilsClasse;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.ProcesVerbalListeClasseDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class ListeClassePvServices {
    @Inject
    EntityManager em;

    public List<ProcesVerbalListeClasseDto>  getListClasse(Long idEcole , String libelleAnnee , String libelleTrimestre, String classe){

        Integer orderNiv ;



        List<eleveAffecteParClasseDto> resultatsListElevesDto = new ArrayList<>();


        List<eleveAffecteParClasseDto> resultatsListEleves= new ArrayList<>();

        resultatsListEleves = getListEleveNonAffectParClassDto(idEcole,classe,libelleAnnee , libelleTrimestre);
        int LongTableau=0 ;
        Long nbreFille;Long nbreGarcon;Long nbreTotal; Long nbreRedFille;Long nbreRedGarcon;Long nbreRedTotal;
        Long nbreAffFille;Long nbreAffGarcon;Long nbreAffTotal;Long nbreNonAffFille;Long nbreNonAffGarcon;
        Long nbreNonAffTotal;
        LongTableau=resultatsListEleves.size();
        List<ProcesVerbalListeClasseDto> listm=new ArrayList<>(LongTableau);
        for (int i=0; i< LongTableau;i++) {
            ProcesVerbalListeClasseDto m=new ProcesVerbalListeClasseDto();
          m.setClasse(resultatsListEleves.get(i).getAffecte());
          m.setProfesseurPrincipal(resultatsListEleves.get(i).getProfesseurPrincipal());
          m.setNom(resultatsListEleves.get(i).getNomEleve());
          m.setPrenoms(resultatsListEleves.get(i).getPrenomEleve());
          m.setMatricule(resultatsListEleves.get(i).getMatricule());
          m.setDateNaissance(resultatsListEleves.get(i).getAnneeNaissance());
          m.setRang(resultatsListEleves.get(i).getRang());
          m.setMoyenne(resultatsListEleves.get(i).getMoyeGeneral());
            nbreFille= geteffeClasseF(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreGarcon= geteffeClasseG(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreTotal=nbreFille+nbreGarcon;
            m.setNbreFille(nbreFille);
            m.setNbreGarcon(nbreGarcon);
            m.setNbreTotal(nbreTotal);
            nbreAffFille=geteffeAffecteF(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreAffGarcon=geteffeAffecteG(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreAffTotal=nbreAffFille+nbreAffGarcon;
            m.setNbreAffFille(nbreAffFille);
            m.setNbreAffGarcon(nbreAffGarcon);
            m.setNbreAffTotal(nbreAffTotal);
            nbreRedFille=geteffeRedoublantF(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreRedGarcon=getefferRedoublantG(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreRedTotal=nbreRedFille+nbreRedGarcon;
            m.setNbreRedFille(nbreRedFille);
            m.setNbreRedGarcon(nbreRedGarcon);
            m.setNbreRedTotal(nbreRedTotal);
            nbreNonAffFille =geteffeNonAffecteF(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreNonAffGarcon =geteffeNonAffecteG(idEcole, classe, libelleAnnee, libelleTrimestre);
            nbreNonAffTotal=nbreNonAffFille+nbreNonAffGarcon;
            m.setNbreNonAffFille(nbreNonAffFille);
            m.setNbreNonAffGarcon(nbreNonAffGarcon);
            m.setNbreNonAffTotal(nbreNonAffTotal);
            listm.add(m);
        }

        return listm;
    }



    public  Integer getOrderNiveau(String niveau){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct o.ordreNiveau from Bulletin o where  o.niveau=:niveau ")
                    .setParameter("niveau",niveau)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }


    public List<eleveAffecteParClasseDto> getListEleveNonAffectParClassDto(Long idEcole , String classe,String libelleAnnee , String libelleTrimestre){
        List<eleveAffecteParClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<eleveAffecteParClasseDto> q= em.createQuery("select new com.vieecoles.dto.eleveAffecteParClasseDto(o.libelleClasse,o.nomPrenomProfPrincipal,o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.nationalite,o.redoublant,o.affecte,o.numDecisionAffecte,o.moyGeneral,o.rang,o.appreciation,o.nomPrenomEducateur,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and o.libelleClasse=:classe  and o.libellePeriode=:periode and o.anneeLibelle=:annee order by o.nom ,o.prenoms asc ", eleveAffecteParClasseDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                 .setParameter("classe",classe)
                                    .setParameter("annee", libelleAnnee)
                                    .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }


    public  Long geteffeClasseF(Long idEcole ,String classe,String annee , String periode){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.anneeLibelle=:annee and o.libellePeriode=:periode and  o.libelleClasse=:classe  ")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }




    public  Long geteffeAffecteF(Long idEcole ,String classe,String annee , String periode){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.anneeLibelle=:annee and o.libellePeriode=:periode and o.libelleClasse=:classe  ")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte","AFFECTE")
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long geteffeRedoublantF(Long idEcole ,String classe,String annee , String periode){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.redoublant=:redoublant and o.anneeLibelle=:annee and o.libellePeriode=:periode and o.libelleClasse=:classe ")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("redoublant","OUI")
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }



    public  Long geteffeNonAffecteF(Long idEcole ,String classe ,String annee , String periode){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.anneeLibelle=:annee and o.libellePeriode=:periode and o.libelleClasse=:classe  ")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte","NON_AFFECTE")
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long geteffeClasseG(Long idEcole ,String classe ,String annee , String periode){

        Long  effeG ;
        try {
            effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole " +
                    " and o.libellePeriode=:periode and o.anneeLibelle =:annee and o.libelleClasse=:classe ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
            return  effeG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Long geteffeAffecteG(Long idEcole ,String classe ,String annee , String periode){
        Long  effeG ;
        try {
            effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte" +
                    " and o.libellePeriode=:periode and o.anneeLibelle =:annee and o.libelleClasse=:classe ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte","AFFECTE")
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
            return  effeG ;
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long getefferRedoublantG(Long idEcole ,String classe,String annee , String periode){
        Long  effeG ;
        try {
            effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.redoublant=:redoublant" +
                    " and o.libellePeriode=:periode and o.anneeLibelle =:annee and o.libelleClasse=:classe ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("redoublant","OUI")
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
            return  effeG ;
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long geteffeNonAffecteG(Long idEcole ,String classe,String annee , String periode){
        Long  effeG ;
        try {
            effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte" +
                    " and o.libellePeriode=:periode and o.anneeLibelle =:annee and o.libelleClasse=:classe ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("affecte","NON_AFFECTE")
                .setParameter("classe",classe)
                .setParameter("annee",annee)
                .setParameter("periode",periode)
                .getSingleResult();
            return  effeG ;
        } catch (NoResultException e){
            return 0L ;
        }
    }


}
