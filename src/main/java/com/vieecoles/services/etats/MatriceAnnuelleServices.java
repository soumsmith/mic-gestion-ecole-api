package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MatriceAnnuelleServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<matriceAnnuelleDto> getInfosMatriceAnnuelleClasse(Long idEcole , String libelleAnnee , String periode ,Long anneeId ,String classe){

        Branche br = new Branche() ;
        br= getLibelleMBranche(classe) ;
          String myBranch = null ;
        myBranch = String.valueOf(Classe.find("select distinct m.branche.libelle from Classe m where m.libelle = ?1 and m.ecole.id = ?2",classe ,idEcole).firstResult());

        System.out.println("myBranch "+myBranch);

        List<NiveauDto> matriculeList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b " +
                " where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.libelleClasse=:classe " , NiveauDto.class);
        matriculeList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", periode)
                .setParameter("classe", classe)
                .getResultList() ;

        //System.out.println("matriculeList "+matriculeList.toString());
        int sizeMatricule  = matriculeList.size() ;

        List<ClasseMatiere>  classeMatiereList = new ArrayList<>() ;
        classeMatiereList = ClasseMatiere.find("select distinct m.matiere.id from ClasseMatiere m  where m.matiere.ecole.id = ?1 and m.branche.libelle = ?2 ", idEcole,myBranch).list();

        //System.out.println("classeMatiereList "+classeMatiereList.toString());

     int    sizeMatiereList = classeMatiereList.size() ;

            Long idEleve = null;
         String matricule , nom = null, prenoms = null;
           int rang ;
           Double moyenTrimes ;


          List<matriceAnnuelleDto> resultatsListElevesDto = new ArrayList<>() ;


         String nomEleve = null,prenomEleve = null ,libelleClasse = null,appreciation;
         Double moy1 ,moy2,moy3,moyAnn;
           int rang1 ,rang2, rang3,rangAn;


        for (int i=0; i< sizeMatricule;i++) {
            System.out.println("Matricule"+matriculeList.get(i).getNiveau());
            matricule= matriculeList.get(i).getNiveau() ;
            List<matiereMoyenneDto> matiMoy = new  ArrayList() ;
            matriceAnnuelleDto m =new matriceAnnuelleDto();
            Inscriptions myIns= new Inscriptions() ;
            myIns = inscriptionService.checkInscrit(idEcole,matriculeList.get(i).getNiveau(),anneeId,null);


            if(myIns != null){
                idEleve =  myIns.getEleve().getEleveid() ;
                nomEleve = myIns.getEleve().getElevenom() ;
                prenomEleve = myIns.getEleve().getEleveprenom() ;
                libelleClasse = classe ;

            }
            moy1 = getMoyennTrimestr(matriculeList.get(i).getNiveau() ,"Premier Trimestre" ,libelleAnnee) ;
            moy2 = getMoyennTrimestr(matriculeList.get(i).getNiveau() ,"Deuxième Trimestre" ,libelleAnnee) ;
            moy3 = getMoyennTrimestr(matriculeList.get(i).getNiveau() ,"Troisième Trimestre",libelleAnnee) ;
            moyAnn = getMoyennAnnuelle(matriculeList.get(i).getNiveau() ,"Troisième Trimestre" ,libelleAnnee) ;

            rang1 =  getRang(matriculeList.get(i).getNiveau() ,"Premier Trimestre"  ,libelleAnnee);
            rang2 =  getRang(matriculeList.get(i).getNiveau() ,"Deuxième Trimestre" ,libelleAnnee);
            rang3 =  getRang(matriculeList.get(i).getNiveau() ,"Troisième Trimestre" ,libelleAnnee);
            rangAn = Integer.parseInt(getRangAnnuell(matriculeList.get(i).getNiveau() ,"Troisième Trimestre" ,libelleAnnee));
            appreciation = getAppreciation(matriculeList.get(i).getNiveau() ,"Troisième Trimestre" ,libelleAnnee);
              m.setAppreciation(appreciation);
              m.setLibelleClasse(libelleClasse);
              m.setMoy1(moy1);
              m.setMoy2(moy2);
              m.setMoy3(moy3);
              m.setMoyAnn(moyAnn);
              m.setRang1(rang1);
              m.setRang2(rang2);
              m.setRang3(rang3);
              m.setRangAn(rangAn);
              m.setNomEleve(nomEleve);
              m.setPrenomEleve(prenomEleve);
            resultatsListElevesDto.add(m) ;

        }


/*
        for (int j=0; j< sizeMatiereList;j++) {
            matiereMoyenneBilanDto l = new matiereMoyenneBilanDto() ;

            long idMatiere = 0;
            String libelleMatiere ;
            String id = String.valueOf(classeMatiereList.get(j));
            idMatiere = Long.parseLong(id);
            System.out.println("idMatiere "+idMatiere);
            libelleMatiere= getLibelleMatiere(idMatiere) ;

            Integer    numOrdreClasse  = getNiveauOrdreClasse(classe,periode,libelleAnnee) ;

            System.out.println("libelleMatiere "+libelleMatiere);
            if(libelleMatiere.equals("FRANCAIS") && numOrdreClasse<=2){
                System.out.println("SSSSSSS1");
                Double moyFr = calculMoycoefFran(classe,libelleAnnee ,periode,idEcole ) ;
                Double moyMat= moyFr/(3d*sizeMatricule) ;
                l.setLibelleMatiereBilan(libelleMatiere.substring(0, 3));
                l.setEcoleId("1");
                l.setMoyMatiereBilan(moyMat);
                matiereMoyenneBilanDtoList.add(l) ;
            } else if(libelleMatiere.equals("FRANCAIS") && (numOrdreClasse>2||numOrdreClasse<=5))   {
                System.out.println("SSSSSSS2");
                Double moyFr = calculMoycoefFran(classe,libelleAnnee ,periode,idEcole ) ;
                Double moyMat= moyFr/(4d*sizeMatricule) ;
                l.setLibelleMatiereBilan(libelleMatiere.substring(0, 3));
                l.setEcoleId("1");
                l.setMoyMatiereBilan(moyMat);
                matiereMoyenneBilanDtoList.add(l) ;
            } else if (!libelleMatiere.equals("FRANCAIS"))   {
                System.out.println("SSSSSSS3");
                libelleMatiere= getLibelleMatiere(idMatiere) ;
                System.out.println("libelleMatiere "+libelleMatiere.substring(0, 3));
                Double moyMat=  getBilanMoyMatiere(libelleMatiere ,periode ,libelleAnnee ,classe ,idEcole);
                l.setLibelleMatiereBilan(libelleMatiere.substring(0, 3));
                l.setMoyMatiereBilan(moyMat);
                l.setEcoleId("1");
                matiereMoyenneBilanDtoList.add(l) ;
            }

            }

        RapportMatriceClasseDto rapport = new RapportMatriceClasseDto();
        rapport.setMatriceClasseDto(resultatsListElevesDto);
        rapport.setMatiereMoyenneBilanDto(matiereMoyenneBilanDtoList);
        rapportMatriceClasseDtoList.add(rapport) ;*/

        return  resultatsListElevesDto ;
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
    public  Double getBilanMoyMatiere(String libelleMatiere,String periode ,String libelleAnnee ,String classe ,Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select avg(d.moyenne)   from DetailBulletin  d join d.bulletin b  where  d.matiereLibelle=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.libelleClasse=:classe and b.ecoleId=:idEcole ")
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
    public  Double calculMoycoefFran(String matricule, String annee,String periode,Long idEcole){
        try {
            Double  moyTfr = (Double) em.createQuery("select  SUM(d.coef*d.moyenne) from DetailBulletin d join d.bulletin b where b.matricule =:matricule and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
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

    public  String getRangAnnuell(String matricule,String periode ,String libelleAnnee){
        try {
            String   moyClasseF = (String) em.createQuery("select b.rangAn  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
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

    public  Integer getNiveauOrdreClasse(String matricule,String periode ,String libelleAnnee){
        try {
            Integer   moyClasseF = (Integer) em.createQuery("select b.ordreNiveau  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
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

    public  Double getMoyennAnnuelle(String matricule,String periode ,String libelleAnnee){
        try {
            Double   moyClasseF = (Double) em.createQuery("select b.moyAn  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
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

    public   Integer  getNumORDREMatiere(Long id ){
        try {
            TypedQuery<Integer> q = (TypedQuery<Integer>) em.createQuery( "SELECT  o.numOrdre from EcoleHasMatiere o where o.id=:id");
            Integer libelle = q.setParameter("id" ,id).getSingleResult() ;

            return libelle;
        } catch (NoResultException e) {
            return 0 ;
        }

    }

    public Branche getLibelleMBranche(String classe){
        try {
            TypedQuery<Branche> q = (TypedQuery<Branche>) em.createQuery( "SELECT  o.branche from Classe o   where o.libelle =:classe");
            Branche branche = q.setParameter("classe" ,classe).getSingleResult() ;

            return branche;
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
