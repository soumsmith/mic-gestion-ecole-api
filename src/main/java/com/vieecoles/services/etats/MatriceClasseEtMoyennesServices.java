package com.vieecoles.services.etats;

import com.vieecoles.dto.MatiereTitreDto;
import com.vieecoles.dto.MatriculeMoyenneDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NiveauDto3;
import com.vieecoles.dto.RapportMatriceClasseDto;
import com.vieecoles.dto.matiereMoyenneBilanDto;
import com.vieecoles.dto.matiereMoyenneDto;
import com.vieecoles.dto.matriceClasseDto;
import com.vieecoles.dto.matriceClasseEtMoyenneDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Matiere;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class MatriceClasseEtMoyennesServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public List<matriceClasseEtMoyenneDto> getInfosMatriceClasse(Long idEcole , String libelleAnnee , String periode , Long anneeId , Long classe){

       /* Branche br = new Branche() ;
        Classe classe1= new Classe() ;
        classe1 = Classe.findById(classe);

        br= getLibelleMBranche(classe1.getLibelle(),idEcole) ;*/


        List<NiveauDto> matriculeList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b " +
            " where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.classeId =:classe order by b.nom ,b.prenoms " , NiveauDto.class);
        matriculeList = q.setParameter("idEcole", idEcole)
            .setParameter("annee", libelleAnnee)
            .setParameter("periode", periode)
            .setParameter("classe", classe)
            .getResultList() ;

        // System.out.println("matriculeList "+matriculeList.toString());
        int sizeMatricule  = matriculeList.size() ;


        List<NiveauDto3>  classeMatiereList = new ArrayList<>() ;

        System.out.println("pidEcole "+idEcole);
        System.out.println("plibelleAnnee "+libelleAnnee);
        System.out.println("pperiode "+periode);
        System.out.println("pclasse "+classe);
        TypedQuery<NiveauDto3> Q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauDto3(d.matiereId ,d.num_ordre) from Bulletin b, DetailBulletin d " +
                " where b.id= d.bulletin.id and b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.classeId =:classe order by d.num_ordre" , NiveauDto3.class);
        classeMatiereList = Q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", periode)
                .setParameter("classe", classe)
                .getResultList() ;


        int    sizeMatiereList = classeMatiereList.size() ;

        Long idEleve = null;
        String matricule = "", nom = null, prenoms = null;
        Integer rang = 0;
        Double moyenTrimes = 0.0;
        String appreciation = "";
        List<matiereMoyenneDto>  matiereMoyenneDto = new ArrayList<>() ;
        List<matriceClasseEtMoyenneDto> resultatsListElevesDto = new ArrayList<>() ;
        List<RapportMatriceClasseDto> rapportMatriceClasseDtoList = new ArrayList<>() ;
        List<matiereMoyenneBilanDto> matiereMoyenneBilanDtoList = new ArrayList<>() ;


            for (int k=0; k< sizeMatricule;k++) {
                Inscriptions myIns= new Inscriptions() ;
                myIns = inscriptionService.checkInscrit(idEcole,matriculeList.get(k).getNiveau() ,anneeId,null);


                if(myIns != null){
                    idEleve =  myIns.getEleve().getEleveid() ;
                    nom = myIns.getEleve().getElevenom() ;
                    prenoms = myIns.getEleve().getEleveprenom() ;

                }
                rang =  getRang(matriculeList.get(k).getNiveau() ,periode ,libelleAnnee,idEcole);
                appreciation = getAppreciation(matriculeList.get(k).getNiveau()  ,periode ,libelleAnnee,idEcole);
                moyenTrimes = getMoyennTrimestr(matriculeList.get(k).getNiveau() ,periode ,libelleAnnee,idEcole) ;

                for (int i=0; i< classeMatiereList.size();i++) {
                    matriceClasseEtMoyenneDto m =new matriceClasseEtMoyenneDto();

                long idMatiere = 0;
                long idMatiere1 = 0;
                Matiere myMatiere = new Matiere();
                String libelleMatiere ;
                String id = String.valueOf(classeMatiereList.get(i).getIdMatiere());
                //idMatiere = Long.parseLong(id);

                idMatiere = classeMatiereList.get(i).getIdMatiere();

                matricule= matriculeList.get(k).getNiveau() ;
                List<matiereMoyenneDto> matiMoy = new  ArrayList() ;
                myMatiere = Matiere.findById(idMatiere) ;

                libelleMatiere = getCodeLIbelleById(idMatiere);

                Double moyFr = calculMoycoefFran(matriculeList.get(k).getNiveau() ,libelleAnnee ,periode,idEcole ) ;
                Double coef = calculcoefFran(matriculeList.get(k).getNiveau() ,libelleAnnee ,periode,idEcole) ;
                Double moyMat=  getMoyMatiere(matriculeList.get(k).getNiveau()  , id,periode ,libelleAnnee,idEcole);
                Integer    numOrdreClasse  = getNiveauOrdreClasse(matriculeList.get(k).getNiveau() ,periode,libelleAnnee,idEcole) ;

                if(libelleMatiere.equals("FR") && numOrdreClasse<5){
                    m.setLibelleMatiere(libelleMatiere);
                    m.setMatricule(matriculeList.get(k).getNiveau() );
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    if(moyFr !=null)
                    {
                        moyMat= moyFr/coef;
                        m.setMoyMatiere(moyMat);
                    }

                    m.setNumOrdre(getNumORDREMatiere(idMatiere,idEcole));
                } else {
                    m.setLibelleMatiere(libelleMatiere);
                    m.setMatricule(matriculeList.get(k).getNiveau() );

                    m.setMoyMatiere(moyMat);
                    m.setNumOrdre(getNumORDREMatiere(idMatiere,idEcole));
                }
                    m.setMoyenTrimes(moyenTrimes);
                    m.setAppreciation(appreciation);
                    if(rang!= null)
                        m.setRang(rang);
                    m.setNom(nom);
                    m.setPrenoms(prenoms);
                    m.setIdEleve(idEleve);
                    // m.setClasse(classe1.getLibelle());
                    m.setMatricule(matricule);
                    m.setPeriode (periode);
                    m.setNumOrdre(k);

                    resultatsListElevesDto.add(m);
                }


            }
             ;




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



    public String getCodeLIbelleByIdold(Long idMatier){
        String libelle= null;

        Matiere matiere = new Matiere();
        matiere = Matiere.findById(idMatier);
        libelle = matiere.getLibelle();


        return  libelle ;
    }

    public String getCodeLIbelleById(Long idMatier ,Long idEcole){
        String libelle= null;
        Matiere matiere = new Matiere();
        matiere = Matiere.findById(idMatier);
        libelle = matiere.getLibelle() ;


        return  libelle ;
    }

    /*public String getCodeLIbelleById(Long idMatier ,Long idEcole){
        String libelle= null;
        Ecole ecole = new Ecole() ;
        ecole = Ecole.findById(idEcole) ;

        Long niveEnsei ;
       //niveEnsei = ecole.getNiveauEnseignement().getId();
        //System.out.println("niveEnsei "+niveEnsei);
        niveEnsei=6L ;
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
    }*/



    public  Double getMoyMatiere(String matricule,String libelleMatiere,String periode ,String libelleAnnee, Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select d.moyenne  from DetailBulletin  d join d.bulletin b  where b.matricule=:matricule and d.matiereCode=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
                    .setParameter("matricule",matricule)
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
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

    public  Integer getRang(String matricule,String periode ,String libelleAnnee, Long idEcole){
        try {
            //System.out.println ("Matricule>>> "+matricule);
            Integer   moyClasseF = (Integer) em.createQuery("select b.rang  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId =:idEcole ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){

            return 0 ;
        }

    }

    public  Integer getNiveauOrdreClasse(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            Integer   moyClasseF = (Integer) em.createQuery("select b.ordreNiveau  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0 ;
        }

    }

    public  Double getMoyennTrimestr(String matricule,String periode ,String libelleAnnee ,Long idEcole){
        try {
            Double   moyClasseF = (Double) em.createQuery("select b.moyGeneral  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
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

    public   Integer  getNumORDREMatiere(Long id ,Long idEcole){
        try {
            TypedQuery<Integer> q = (TypedQuery<Integer>) em.createQuery( "SELECT  o.numOrdre from EcoleHasMatiere o where o.matiere.id=:id and o.ecole.id=:idEcole ");
            Integer libelle = q.setParameter("id" ,id)
                    .setParameter("idEcole" ,idEcole)
                    .getSingleResult() ;

            return libelle;
        } catch (NoResultException e) {
            return 0 ;
        }

    }

    public Branche getLibelleMBranche(String classe,Long idEcole ){
        try {
            TypedQuery<Branche> q = (TypedQuery<Branche>) em.createQuery( "SELECT  o.branche from Classe o   where o.libelle =:classe and  o.ecole.id=:idEcole");
            Branche branche = q.setParameter("classe" ,classe)
                    .setParameter("idEcole" ,idEcole)
                    .getSingleResult() ;

            return branche;
        } catch (NoResultException e) {
            return null ;
        }

    }
    public  String getAppreciation(String matricule,String periode ,String libelleAnnee,Long idEcole){
        try {
            String   moyClasseF = (String) em.createQuery("select b.appreciation  from Bulletin b  where b.matricule=:matricule and  b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
                    .setParameter("matricule",matricule)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return null ;
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
            libelle = matiere.getLibelle() ;
        }


        return  libelle ;
    }
}
