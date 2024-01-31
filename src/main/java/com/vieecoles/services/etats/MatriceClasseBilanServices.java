package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Matiere;

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
        System.out.println("Bilan>>>Class "+classe);
        classe1= Classe.findById(classe);

        br= getLibelleMBranche(classe1.getLibelle(),idEcole) ;
          String myBranch = null ;
        myBranch = String.valueOf(Classe.find("select distinct m.branche.libelle from Classe m where m.libelle = ?1 and m.ecole.id = ?2",classe1.getLibelle() ,idEcole).firstResult());

        System.out.println("myBranch "+myBranch);

        List<NiveauDto> matriculeList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b " +
                " where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.classeId =:classe " , NiveauDto.class);
        matriculeList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", periode)
                .setParameter("classe", classe)
                .getResultList() ;

        System.out.println("matriculeList "+matriculeList.toString());
        int sizeMatricule  = matriculeList.size() ;

        List<NiveauDto2>  classeMatiereList = new ArrayList<>() ;

        TypedQuery<NiveauDto2> Q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauDto2(d.matiereCode ,d.num_ordre) from Bulletin b, DetailBulletin d " +
                " where b.id= d.bulletin.id and b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.classeId =:classe order by d.num_ordre" , NiveauDto2.class);
        classeMatiereList = Q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", periode)
                .setParameter("classe", classe)
                .getResultList() ;




    //    classeMatiereList = ClasseMatiere.find("select distinct m.matiere.id from ClasseMatiere m  where m.matiere.ecole.id = ?1 and m.branche.libelle = ?2 ", idEcole,myBranch).list();





        //System.out.println("classeMatiereList "+classeMatiereList.toString());




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
            String id = String.valueOf(classeMatiereList.get(j).getNiveau());
            idMatiere = Long.parseLong(id);
            System.out.println("idMatiere "+idMatiere);
         Matiere myMatiere = new Matiere();
         myMatiere = Matiere.findById(idMatiere);
           // libelleMatiere= getLibelleMatiere(idMatiere) ;
           // libelleMatiere = myMatiere.getLibelle() ;
         libelleMatiere = getCodeLIbelleById(idMatiere);
         Integer    numOrdreClasse  = getNiveauOrdreClasse(classe,periode,libelleAnnee) ;

            System.out.println("libelleMatiere "+libelleMatiere);
         if(libelleMatiere.equals("FR") && numOrdreClasse<=2){
             System.out.println("SSSSSSS1");
             Double moyMat = null;
             Double moyFr = calculMoycoefFran(classe,libelleAnnee ,periode,idEcole ) ;
             if(moyFr!=null && moyFr!=0D)
              moyMat= moyFr/(3d*sizeMatricule) ;
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setEcoleId("1");
             l.setMoyMatiereBilan(moyMat);
             matiereMoyenneBilanDtoList.add(l) ;
         } else if(libelleMatiere.equals("FR") && numOrdreClasse>2&& numOrdreClasse<5)   {
             System.out.println("SSSSSSS2");
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

             System.out.println("libelleMatiere>>>>fRRRR "+libelleMatiere);
             Double moyMat=  getBilanMoyMatiere(id ,periode ,libelleAnnee ,classe ,idEcole);
             l.setLibelleMatiereBilan(libelleMatiere);
             l.setMoyMatiereBilan(moyMat);
             l.setEcoleId("1");
             matiereMoyenneBilanDtoList.add(l) ;
         }
         else   {
             System.out.println("SSSSSSS3");
             Matiere mat = new Matiere() ;
             mat= Matiere.findById(idMatiere);
             //libelleMatiere= getLibelleMatiere(idMatiere) ;

             //libelleMatiere = mat.getLibelle() ;
             System.out.println("libelleMatiere "+libelleMatiere);
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

    public String getCodeLIbelleById(Long idMatier){
        String libelle= null;

        if(idMatier==1L) {
            libelle="FR";
        } else if (idMatier==2L) {
            libelle="CF";
        }else if (idMatier==3L) {
            libelle="Ex O";
        } else if (idMatier==4L) {
            libelle="OG";
        } else if (idMatier==5L) {
            libelle="ANG";
        } else if (idMatier==6L) {
            libelle="HG";
        } else if (idMatier==7L) {
            libelle="Mathes";
        } else if (idMatier==8L) {
            libelle="PHYS";
        }else if (idMatier==9L) {
            libelle="SVT";
        } else if (idMatier==10L) {
            libelle="EPS";
        }
        else if (idMatier==11L) {
            libelle="EDHC";
        }

        else if (idMatier==12L) {
            libelle="COND";
        }

        else if (idMatier==13L) {
            libelle="INFO";
        }
        else if (idMatier==14L) {
            libelle="ENTREP";
        }
        else if (idMatier==19L) {
            libelle="ART-PLA";
        }
        else if (idMatier==21L) {
            libelle="ESP";
        }
        else if (idMatier==25L) {
            libelle="ALL";
        }
        else if (idMatier==26L) {
            libelle="PHILO";
        }
        else if (idMatier==27L) {
            libelle="TICE";
        }
        else if (idMatier==36L) {
            libelle="ART-VIS";
        }
        else if (idMatier==73L) {
            libelle="ARAB";
        }
        else if (idMatier==29L) {
            libelle="MEMO";
        }
        else if (idMatier==35L) {
            libelle="SIRAH";
        }
        else if (idMatier==30L) {
            libelle="FIQ";
        }
        else if (idMatier==38L) {
            libelle="AKLQ";
        }
        else if (idMatier==37L) {
            libelle="AQD";
        }
        else {
            Matiere matiere = new Matiere();
            matiere = Matiere.findById(idMatier);
            libelle = matiere.getLibelle().substring(0,4) ;
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

}
