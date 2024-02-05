package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MatriceClasseBilanServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<matiereMoyenneBilanDto> getInfosBilanMatriceClasse(Long idEcole , String libelleAnnee , String periode ,Long anneeId ,Long classe){

        Branche br = new Branche() ;
        Classe classe1= new Classe() ;

        classe1= Classe.findById(classe);

        br= getLibelleMBranche(classe1.getLibelle(),idEcole) ;
          String myBranch = null ;
        myBranch = String.valueOf(Classe.find("select distinct m.branche.libelle from Classe m where m.libelle = ?1 and m.ecole.id = ?2",classe1.getLibelle() ,idEcole).firstResult());

        List<NiveauDto> matriculeList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b " +
                " where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.classeId =:classe " , NiveauDto.class);
        matriculeList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", periode)
                .setParameter("classe", classe)
                .getResultList() ;

        int sizeMatricule  = matriculeList.size() ;

        List<NiveauDto3>  classeMatiereList = new ArrayList<>() ;

        TypedQuery<NiveauDto3> Q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauDto3(d.matiereId ,d.num_ordre) from Bulletin b, DetailBulletin d " +
                " where b.id= d.bulletin.id and b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.classeId =:classe order by d.num_ordre" , NiveauDto3.class);
        classeMatiereList = Q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", periode)
                .setParameter("classe", classe)
                .getResultList() ;





     int    sizeMatiereList = classeMatiereList.size() ;

            Long idEleve = null;
         String matricule , nom = null, prenoms = null;
           int rang ;
           Double moyenTrimes ;
        String appreciation  ;
         List<matiereMoyenneDto>  matiereMoyenneDto = new ArrayList<>() ;
          List<matriceClasseDto> resultatsListElevesDto = new ArrayList<>() ;

          List<MoyenneBilanDto> rapportMatriceClasseDtoList = new ArrayList<>() ;

        List<matiereMoyenneBilanDto> matiereMoyenneBilanDtoList = new ArrayList<>() ;





     for (int j=0; j< sizeMatiereList;j++) {
            matiereMoyenneBilanDto l = new matiereMoyenneBilanDto() ;

            long idMatiere = 0;

            String libelleMatiere ;
            String id = String.valueOf(classeMatiereList.get(j).getIdMatiere());
           // idMatiere = Long.parseLong(id);

         idMatiere = classeMatiereList.get(j).getIdMatiere();


         Matiere myMatiere = new Matiere();
         myMatiere = Matiere.findById(idMatiere);
         libelleMatiere = getCodeLIbelleById(idMatiere ,idEcole);
         Integer    numOrdreClasse  = getNiveauOrdreClasse(classe,periode,libelleAnnee) ;


         if(libelleMatiere.equals("FR") && numOrdreClasse<=2){

             Double moyMat = null;
             Double moyFr = calculMoycoefFran(classe,libelleAnnee ,periode,idEcole ) ;
             if(moyFr!=null && moyFr!=0D)
              moyMat= moyFr/(3d*sizeMatricule) ;
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setEcoleId("1");
             l.setMoyMatiereBilan(moyMat);
             matiereMoyenneBilanDtoList.add(l) ;
         } else if(libelleMatiere.equals("FR") && numOrdreClasse>2&& numOrdreClasse<5)   {

             Double moyFr = calculMoycoefFran(classe,libelleAnnee ,periode,idEcole ) ;
             Double moyMat = null;
             if(moyFr!=null && moyFr!=0D)
              moyMat= moyFr/(4d*sizeMatricule) ;
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setEcoleId("1");
             l.setMoyMatiereBilan(moyMat);
             matiereMoyenneBilanDtoList.add(l) ;
         }
         else if (libelleMatiere.equals("FR") && (numOrdreClasse>=5)) {

             Double moyMat=  getBilanMoyMatiere(id ,periode ,libelleAnnee ,classe ,idEcole);
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setMoyMatiereBilan(moyMat);
             l.setEcoleId("1");
             matiereMoyenneBilanDtoList.add(l) ;
         }
         else   {

             Matiere mat = new Matiere() ;
             mat= Matiere.findById(idMatiere);

             Double moyMat=  getBilanMoyMatiere(id ,periode ,libelleAnnee ,classe ,idEcole);
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setMoyMatiereBilan(moyMat);
             l.setEcoleId("1");
             matiereMoyenneBilanDtoList.add(l) ;
         }
       /*  else if (!libelleMatiere.equals("FR"))   {
             System.out.println("SSSSSSS3");
             Matiere mat = new Matiere() ;
             mat= Matiere.findById(idMatiere);

             System.out.println("libelleMatiere "+libelleMatiere);
             Double moyMat=  getBilanMoyMatiere(id ,periode ,libelleAnnee ,classe ,idEcole);
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setMoyMatiereBilan(moyMat);
             l.setEcoleId("1");
             matiereMoyenneBilanDtoList.add(l) ;
         }*/



            }


        return  matiereMoyenneBilanDtoList ;
    }




    public  Integer getNiveauOrdreClasse(Long classe ,String periode ,String libelleAnnee){
        try {
            Integer   moyClasseF = (Integer) em.createQuery("select distinct b.ordreNiveau  from Bulletin b  where b.classeId =:classe and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("classe",classe)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public String getCodeLIbelleById(Long idMatier ,Long idEcole){
        String libelle= null;
        Ecole ecole = new Ecole() ;
        ecole = Ecole.findById(idEcole) ;

        Long niveEnsei ;
        niveEnsei = ecole.getNiveauEnseignement().getId();

        if(idMatier==1L && niveEnsei==2L ) {
            libelle="FR";
        } else if (idMatier==2L && niveEnsei==2L ) {
            libelle="CF";
        }else if (idMatier==3L && niveEnsei==2L) {
            libelle="Ex O";
        } else if (idMatier==4L && niveEnsei==2L) {
            libelle="OG";
        } else if (idMatier==5L && niveEnsei==2L) {
            libelle="ANG";
        } else if (idMatier==6L && niveEnsei==2L) {
            libelle="HG";
        } else if (idMatier==7L && niveEnsei==2L) {
            libelle="Mathes";
        } else if (idMatier==8L && niveEnsei==2L) {
            libelle="PHYS";
        }else if (idMatier==9L && niveEnsei==2L) {
            libelle="SVT";
        } else if (idMatier==10L && niveEnsei==2L) {
            libelle="EPS";
        }
        else if (idMatier==11L && niveEnsei==2L) {
            libelle="EDHC";
        }

        else if (idMatier==12L && niveEnsei==2L) {
            libelle="COND";
        }

        else if (idMatier==13L && niveEnsei==2L) {
            libelle="INFO";
        }
        else if (idMatier==14L && niveEnsei==2L) {
            libelle="ENTREP";
        }
        else if (idMatier==19L && niveEnsei==2L) {
            libelle="ART-PLA";
        }
        else if (idMatier==21L && niveEnsei==2L) {
            libelle="ESP";
        }
        else if (idMatier==25L && niveEnsei==2L) {
            libelle="ALL";
        }
        else if (idMatier==26L && niveEnsei==2L) {
            libelle="PHILO";
        }
        else if (idMatier==27L && niveEnsei==2L) {
            libelle="TICE";
        }
        else if (idMatier==36L && niveEnsei==2L) {
            libelle="ART-VIS";
        }
        else if (idMatier==73L && niveEnsei==2L) {
            libelle="ARAB";
        }
        else if (idMatier==29L && niveEnsei==2L) {
            libelle="MEMO";
        }
        else if (idMatier==35L && niveEnsei==2L) {
            libelle="SIRAH";
        }
        else if (idMatier==30L && niveEnsei==2L) {
            libelle="FIQ";
        }
        else if (idMatier==38L && niveEnsei==2L) {
            libelle="AKLQ";
        }
        else if (idMatier==37L && niveEnsei==2L) {
            libelle="AQD";
        }
        else {
            Matiere matiere = new Matiere();
            matiere = Matiere.findById(idMatier);
            libelle = matiere.getLibelle() ;
        }


        return  libelle ;
    }
    public  Double getMoyMatiere(String matricule,String libelleMatiere,String periode ,String libelleAnnee){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereLibelle=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getBilanMoyMatiere(String libelleMatiere,String periode ,String libelleAnnee ,Long classe ,Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select avg(d.moyenne)   from DetailBulletin  d join d.bulletin b  where  d.matiereCode=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.classeId =:classe and b.ecoleId=:idEcole ")
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("classe", classe)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double calculcoefFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.matricule=:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("matricule",matricule)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }
    public  Double calculMoycoefFran(Long classe, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select  SUM(d.coef*d.moyenne) from DetailBulletin d join d.bulletin b where b.classeId =:classe and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
                    .setParameter("classe",classe)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
            return  moyTfr ;
        } catch (NoResultException e){
            return 0D ;
        }
    }

    public  Integer getRang(String matricule,String periode ,String libelleAnnee){
        try {
            Integer   moyClasseF = (Integer) em.createQuery("select b.rang  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  Double getMoyennTrimestr(String matricule,String periode ,String libelleAnnee){
        try {
            Double   moyClasseF = (Double) em.createQuery("select b.moyGeneral  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
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

    public   Integer  getNumORDREMatiere(Long id ,Long idEcole ){
        try {
            TypedQuery<Integer> q = (TypedQuery<Integer>) em.createQuery( "SELECT  o.numOrdre from EcoleHasMatiere o where o.matiere.id=:id and o.ecole.id=:idEcole");
            Integer libelle = q.setParameter("id" ,id)
                    .setParameter("idEcole" ,idEcole)
                    .getSingleResult() ;

            return libelle;
        } catch (NoResultException e) {
            return 0 ;
        }

    }

    public Branche getLibelleMBranche(String classe,Long idEcole){
        try {
            TypedQuery<Branche> q = (TypedQuery<Branche>) em.createQuery( "SELECT  o.branche from Classe o   where o.libelle =:classe and o.ecole.id=:idEcole");
            Branche branche = q.setParameter("classe" ,classe)
                                .setParameter("idEcole" ,idEcole)
                              .getSingleResult() ;

            return branche;
        } catch (NoResultException e) {
            return null ;
        }
    }

    public Classe getClasse(Long classe,Long idEcole){
        try {
            TypedQuery<Classe> q = (TypedQuery<Classe>) em.createQuery( "SELECT  o from Classe o   where o.id =:classe and o.ecole.id=:idEcole");
            Classe classe1 = q.setParameter("classe" ,classe)
                    .setParameter("idEcole" ,idEcole)
                    .getSingleResult() ;

            return classe1;
        } catch (NoResultException e) {
            return null ;
        }
    }
    public  String getAppreciation(String matricule,String periode ,String libelleAnnee){
        try {
            String   moyClasseF = (String) em.createQuery("select b.appreciation  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public   EcoleHasMatiere  getLibelleEcoleHasMatiere(Long id ){
        try {
            TypedQuery<EcoleHasMatiere> q = (TypedQuery<EcoleHasMatiere>) em.createQuery( "SELECT  o from EcoleHasMatiere o where o.id=:id");
            EcoleHasMatiere ecoleHasMatiere = q.setParameter("id" ,id).getSingleResult() ;

            return ecoleHasMatiere;
        } catch (NoResultException e) {
            return null ;
        }

    }

}
