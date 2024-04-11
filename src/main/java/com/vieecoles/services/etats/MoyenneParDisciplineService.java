package com.vieecoles.services.etats;

import com.vieecoles.dto.MoenneParDisciplineDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MoyenneParDisciplineService {
    @Inject
    EntityManager em;

    public List<MoenneParDisciplineDto> getMoyenneParDiscipline(Long idEcole , String libelleAnnee , String libelleTrimestre){

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.niveau ,b.ordreNiveau order by b.ordreNiveau", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                              . getResultList() ;

  //System.out.println("classeNiveauDtoList "+classeNiveauDtoList.toString());
        System.out.println("Longueur Tableau" +classeNiveauDtoList.size());
      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10=0d,pourMoyInf999=0d,pourMoyInf85=0d, pourMoySup10F =0d ,pourMoySup10G =0d,pourMoyInf999F =0d,pourMoyInf999G =0d,pourMoyInf85G =0d,pourMoyInf85F =0d,
                moyClasseF =0d,moyClasseG =0d , moyClasse , moyClasseF_ET,moyClasse_ET,moyClasseG_ET ;
       Integer effectifClasse ,orderNiveau;

       Long pourInf10F, pourInf10G, classMathF, classMathG, nbreMoyInf10MathF, nbreMoyInf10MathG;
        Long classPCF, classPCG, nbreMoyInf10PCF, nbreMoyInf10PCG, classSVTF,classSVTG;
         Long nbreMoyInf10SVTF, nbreMoyInf10SVTG, classPHILOF, classPHILOG, nbreMoyInf10PHILOF, nbreMoyInf10PHILOG;
         Long classFrancaisF, classFrancaisG, nbreMoyInf10FrancaisF, nbreMoyInf10FrancaisG,classCompFrF, classCompFrG;
         Long nbreMoyInf10CompFrF, nbreMoyInf10CompFrG, classOrthGramF, classOrthGramG, nbreMoyInf10OrthGramF,nbreMoyInf10OrthGramG;
         Long classAngF, classAngG, nbreMoyInf10AngF, nbreMoyInf10AngG, classAllF, classAllG, nbreMoyInf10AllF;
        Long nbreMoyInf10AllG, classEspF, classEspG, nbreMoyInf10EspF, nbreMoyInf10EspG,classHgF, classHgG, nbreMoyInf10HgF, nbreMoyInf10HgG;

         Double pourMoyInf10SVTF= 0d, pourMoyInf10SVTG= 0d, pourMoyInf10PHILOF= 0d, pourMoyInf10PHILOG = 0d, pourMoyInf10FrancaisF= 0d, pourMoyInf10FrancaisG= 0d;
        Double pourMoyInf10CompFrF= 0d, pourMoyInf10CompFrG= 0d, pourMoyInf10OrthGramF= 0d, pourMoyInf10OrthGramG= 0d, pourMoyInf10AngF= 0d;
         Double pourMoyInf10AngG= 0d, pourMoyInf10AllF= 0d, pourMoyInf10AllG= 0d, pourMoyInf10EspF= 0d,pourMoyInf10EspG= 0d, pourMoyInf10HgF= 0d, pourMoyInf10HgG= 0d,pourMoyInf10MathF= 0d, pourMoyInf10MathG = 0d, pourMoyInf10PCF= 0d, pourMoyInf10PCG = 0d ;
        Double pourMoyInf10Math= 0d, pourMoyInf10PC= 0d, pourMoyInf10SVT= 0d, pourMoyInf10PHILO= 0d, pourMoyInf10Francais= 0d,pourMoyInf10CompFr= 0d, pourMoyInf10OrthGram= 0d, pourMoyInf10Ang= 0d;
         Double pourMoyInf10All = 0d, pourMoyInf10Esp = 0d, pourMoyInf10Hg = 0d;







        String cycle ;
        List<MoenneParDisciplineDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
       // System.out.println("resultatsListElevesDto Size "+ resultatsListElevesDto.size());
        for (int i=0; i< LongTableau;i++) {
            MoenneParDisciplineDto resultatsListEleves= new MoenneParDisciplineDto();
            orderNiveau = getOrderNiveau(classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);
            effectifClasse= getEffectifParClasse(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);
            System.out.println("effectifClasse "+effectifClasse);

            classMathF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,7L);
            System.out.println ("classMathF "+classMathF);
            classMathG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,7L);
            System.out.println ("classMathG "+classMathG);
            nbreMoyInf10MathG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,7L);
            System.out.println ("nbreMoyInf10MathG "+nbreMoyInf10MathG);
            nbreMoyInf10MathF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,7L);
            System.out.println ("nbreMoyInf10MathF "+nbreMoyInf10MathF);
            if(classMathG !=0)
                pourMoyInf10MathG = (double) ((nbreMoyInf10MathG*100d)/classMathG);
            System.out.println ("pourMoyInf10MathG "+pourMoyInf10MathG);
            if(classMathF !=0)
                pourMoyInf10MathF = (double) ((nbreMoyInf10MathF*100d)/classMathF);
            System.out.println ("pourMoyInf10MathF "+pourMoyInf10MathF);
            if(classMathG !=0||classMathF !=0)
                pourMoyInf10Math = (double)((nbreMoyInf10MathG + nbreMoyInf10MathF)*100d/(classMathF+classMathG)) ;

            classPCF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,8L);
            System.out.println ("classPCF "+classPCF);
            classPCG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,8L);
            System.out.println ("classPCG "+classPCG);
            nbreMoyInf10PCG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,8L);
            System.out.println ("nbreMoyInf10PCG "+nbreMoyInf10PCG);
            nbreMoyInf10PCF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,8L);
            System.out.println ("nbreMoyInf10PCF "+nbreMoyInf10PCF);
            if(classPCG !=0)
                pourMoyInf10PCG = (double) ((nbreMoyInf10PCG*100d)/classPCG);
            System.out.println ("pourMoyInf10PCG "+pourMoyInf10PCG);
            if(classPCF !=0)
                pourMoyInf10PCF = (double) ((nbreMoyInf10PCF*100d)/classPCF);
            System.out.println ("pourMoyInf10PCF "+pourMoyInf10PCF);

            if(classPCG !=0||classPCF !=0)
                pourMoyInf10PC = (double)((nbreMoyInf10PCG + nbreMoyInf10PCF)*100d/(classPCF+classPCG)) ;

            classSVTF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,9L);
            classSVTG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,9L);
            nbreMoyInf10SVTG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,9L);
            nbreMoyInf10SVTF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,9L);
            if(classSVTG !=0)
                pourMoyInf10SVTG = (double) ((nbreMoyInf10SVTG*100d)/classSVTG);
            System.out.println ("pourMoyInf10SVTG "+pourMoyInf10SVTG);
            if(classSVTF !=0)
                pourMoyInf10SVTF = (double) ((nbreMoyInf10SVTF*100d)/classSVTF);
            System.out.println ("pourMoyInf10SVTF "+pourMoyInf10SVTF);

            if(classSVTG !=0||classSVTF !=0)
                pourMoyInf10SVT = (double)((nbreMoyInf10SVTG + nbreMoyInf10SVTF)*100d/(classSVTF+classSVTG)) ;

            classPHILOF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,26L);
            classPHILOG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,26L);
            nbreMoyInf10PHILOG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,26L);
            nbreMoyInf10PHILOF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,26L);
            if(classPHILOG !=0)
                pourMoyInf10PHILOG = (double) ((nbreMoyInf10PHILOG*100d)/classPHILOG);
            if(classPHILOF !=0)
                pourMoyInf10PHILOF = (double) ((nbreMoyInf10PHILOF*100d)/classPHILOF);

            if(classPHILOG !=0||classPHILOF !=0)
                pourMoyInf10PHILO = (double)((nbreMoyInf10PHILOG + nbreMoyInf10PHILOF)*100d/(classPHILOF+classPHILOG)) ;

            classFrancaisF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,1L);
            classFrancaisG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,1L);
            nbreMoyInf10FrancaisG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,1L);
            nbreMoyInf10FrancaisF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,1L);
            if(classFrancaisG !=0)
                pourMoyInf10FrancaisG = (double) ((nbreMoyInf10FrancaisG*100d)/classFrancaisG);
            if(classFrancaisF !=0)
                pourMoyInf10FrancaisF = (double) ((nbreMoyInf10FrancaisF*100d)/classFrancaisF);

            if(classFrancaisG !=0||classFrancaisF !=0)
                pourMoyInf10Francais = (double)((nbreMoyInf10FrancaisG + nbreMoyInf10FrancaisF)*100d/(classFrancaisF+classFrancaisG)) ;

            classCompFrF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,2l);
            classCompFrG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,2l);
            nbreMoyInf10CompFrG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,2l);
            nbreMoyInf10CompFrF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,2l);
            if(classCompFrG !=0)
                    pourMoyInf10CompFrG = (double) ((nbreMoyInf10CompFrG*100d)/classCompFrG);
            System.out.println ("pourMoyInf10CompFrG "+pourMoyInf10CompFrG);
            if(classCompFrF !=0)
                pourMoyInf10CompFrF = (double) ((nbreMoyInf10CompFrF*100d)/classCompFrF);
            System.out.println ("pourMoyInf10CompFrF "+pourMoyInf10CompFrF);

            if(classCompFrG !=0||classCompFrF !=0)
                pourMoyInf10CompFr = (double)((nbreMoyInf10CompFrG + nbreMoyInf10CompFrF)*100d/(classCompFrF+classCompFrG)) ;


            classOrthGramF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,4l);
            classOrthGramG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,4l);
            nbreMoyInf10OrthGramG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,4l);
            nbreMoyInf10OrthGramF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,4l);
            if(classOrthGramG !=0)
                pourMoyInf10OrthGramG = (double) ((nbreMoyInf10OrthGramG*100d)/classOrthGramG);
            System.out.println ("pourMoyInf10OrthGramG "+pourMoyInf10OrthGramG);
            if(classOrthGramF !=0)
                pourMoyInf10OrthGramF = (double) ((nbreMoyInf10OrthGramF*100d)/classOrthGramF);
            System.out.println ("pourMoyInf10OrthGramF "+pourMoyInf10OrthGramF);
            if(classOrthGramG !=0||classOrthGramF !=0)
                pourMoyInf10OrthGram = (double)((nbreMoyInf10OrthGramG + nbreMoyInf10OrthGramF)*100d/(classOrthGramF+classOrthGramG)) ;



            classAngF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,5l);
            classAngG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,5l);
            nbreMoyInf10AngG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,5l);
            nbreMoyInf10AngF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,5l);
            if(classAngG !=0)
                pourMoyInf10AngG = (double) ((nbreMoyInf10AngG*100d)/classAngG);
            System.out.println ("pourMoyInf10AngG "+pourMoyInf10AngG);
            if(classAngF !=0)
                pourMoyInf10AngF = (double) ((nbreMoyInf10AngF*100d)/classAngF);
            System.out.println ("pourMoyInf10AngF "+pourMoyInf10AngF);
            if(classAngG !=0||classAngF !=0)
                pourMoyInf10Ang = (double)((nbreMoyInf10AngG + nbreMoyInf10AngF)*100d/(classAngF+classAngG)) ;

            classAllF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,25l);
            classAllG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,25l);
            nbreMoyInf10AllG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,25l);
            nbreMoyInf10AllF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,25l);
            if(classAllG !=0)
                pourMoyInf10AllG = (double) ((nbreMoyInf10AllG*100d)/classAllG);
            if(classAllF !=0)
                pourMoyInf10AllF = (double) ((nbreMoyInf10AllF*100d)/classAllF);
            if(classAllG !=0||classAllF !=0)
                pourMoyInf10All = (double)((nbreMoyInf10AllG + nbreMoyInf10AllF)*100d/(classAllF+classAllG)) ;

            classEspF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,21l);
            classEspG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,21l);
            nbreMoyInf10EspG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,21l);
            nbreMoyInf10EspF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,21l);
            if(classEspG !=0)
                pourMoyInf10EspG = (double) ((nbreMoyInf10EspG*100d)/classEspG);
            if(classEspF !=0)
                pourMoyInf10EspF = (double) ((nbreMoyInf10EspF*100d)/classEspF);

            if(classEspG !=0||classEspF !=0)
                pourMoyInf10Esp = (double)((nbreMoyInf10EspG + nbreMoyInf10EspF)*100d/(classEspF+classEspG)) ;

            classHgF = getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,6l);
            classHgG = getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,6l);
            nbreMoyInf10HgG= getnbreMoyInf10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,6l);
            nbreMoyInf10HgF= getnbreMoyInf10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre,6l);
            if(classHgG !=0)
                pourMoyInf10HgG = (double) ((nbreMoyInf10HgG*100d)/classHgG);
            System.out.println ("pourMoyInf10HgG "+pourMoyInf10HgG);
            if(classHgF !=0)
                pourMoyInf10HgF = (double) ((nbreMoyInf10HgF*100d)/classHgF);
            System.out.println ("pourMoyInf10HgF "+pourMoyInf10HgF);
            if(classHgG !=0||classHgF !=0)
                pourMoyInf10Hg = (double)((nbreMoyInf10HgG + nbreMoyInf10HgF)*100d/(classHgF+classHgG)) ;


            //calcul pourcentage
            if(orderNiveau<=4){
                cycle="1er";
            } else {
                cycle="2nd";
            }



            resultatsListEleves.setNiveau(classeNiveauDtoList.get(i).getNiveau());
            System.out.println("Niveau "+resultatsListEleves.getNiveau());
            resultatsListEleves.setClassMathF(classMathF);
            resultatsListEleves.setClassMathG(classMathG);
            resultatsListEleves.setNbreMoyInf10MathF(nbreMoyInf10MathF);
            resultatsListEleves.setNbreMoyInf10MathG(nbreMoyInf10MathG);
            resultatsListEleves.setPourMoyInf10MathF(pourMoyInf10MathF);
            resultatsListEleves.setPourMoyInf10MathG(pourMoyInf10MathG);
            resultatsListEleves.setPourMoyInf10Math(pourMoyInf10Math);

            resultatsListEleves.setClassPCF(classPCF);
            resultatsListEleves.setClassPCG(classPCG);
            resultatsListEleves.setNbreMoyInf10PCF(nbreMoyInf10PCF);
            resultatsListEleves.setNbreMoyInf10PCG(nbreMoyInf10PCG);
            resultatsListEleves.setPourMoyInf10PCF(pourMoyInf10PCF);
            resultatsListEleves.setPourMoyInf10PCG(pourMoyInf10PCG);
            resultatsListEleves.setPourMoyInf10PC(pourMoyInf10PC);

            resultatsListEleves.setClassSVTF(classSVTF);
            resultatsListEleves.setClassSVTG(classSVTG);
            resultatsListEleves.setNbreMoyInf10SVTF(nbreMoyInf10SVTF);
            resultatsListEleves.setNbreMoyInf10SVTG(nbreMoyInf10SVTG);
            resultatsListEleves.setPourMoyInf10SVTF(pourMoyInf10SVTF);
            resultatsListEleves.setPourMoyInf10SVTG(pourMoyInf10SVTG);
            resultatsListEleves.setPourMoyInf10SVT(pourMoyInf10SVT);

            resultatsListEleves.setClassPHILOF(classPHILOF);
            resultatsListEleves.setClassPHILOG(classPHILOG);
            resultatsListEleves.setNbreMoyInf10PHILOF(nbreMoyInf10PHILOF);
            resultatsListEleves.setNbreMoyInf10PHILOG(nbreMoyInf10PHILOG);
            resultatsListEleves.setPourMoyInf10PHILOF(pourMoyInf10PHILOF);
            resultatsListEleves.setPourMoyInf10PHILOG(pourMoyInf10PHILOG);
            resultatsListEleves.setPourMoyInf10PHILO(pourMoyInf10PHILO);

            resultatsListEleves.setClassFrancaisF(classFrancaisF);
            resultatsListEleves.setClassFrancaisG(classFrancaisG);
            resultatsListEleves.setNbreMoyInf10FrancaisF(nbreMoyInf10FrancaisF);
            resultatsListEleves.setNbreMoyInf10FrancaisG(nbreMoyInf10FrancaisG);
            resultatsListEleves.setPourMoyInf10FrancaisF(pourMoyInf10FrancaisF);
            resultatsListEleves.setPourMoyInf10FrancaisG(pourMoyInf10FrancaisG);
            resultatsListEleves.setPourMoyInf10Francais(pourMoyInf10Francais);

            resultatsListEleves.setClassCompFrF(classCompFrF);
            resultatsListEleves.setClassCompFrG(classCompFrG);
            resultatsListEleves.setNbreMoyInf10CompFrF(nbreMoyInf10CompFrF);
            resultatsListEleves.setNbreMoyInf10CompFrG(nbreMoyInf10CompFrG);
            resultatsListEleves.setPourMoyInf10CompFrF(pourMoyInf10CompFrF);
            resultatsListEleves.setPourMoyInf10CompFrG(pourMoyInf10CompFrG);
            resultatsListEleves.setPourMoyInf10CompFr(pourMoyInf10CompFr);

            resultatsListEleves.setClassOrthGramF(classOrthGramF);
            resultatsListEleves.setClassOrthGramG(classOrthGramG);
            resultatsListEleves.setNbreMoyInf10OrthGramF(nbreMoyInf10OrthGramF);
            resultatsListEleves.setNbreMoyInf10OrthGramG(nbreMoyInf10OrthGramG);
            resultatsListEleves.setPourMoyInf10OrthGramF(pourMoyInf10OrthGramF);
            resultatsListEleves.setPourMoyInf10OrthGramG(pourMoyInf10OrthGramG);
            resultatsListEleves.setPourMoyInf10OrthGram(pourMoyInf10OrthGram);

            resultatsListEleves.setClassAngF(classAngF);
            resultatsListEleves.setClassAngG(classAngG);
            resultatsListEleves.setNbreMoyInf10AngF(nbreMoyInf10AngF);
            resultatsListEleves.setNbreMoyInf10AngG(nbreMoyInf10AngG);
            resultatsListEleves.setPourMoyInf10AngF(pourMoyInf10AngF);
            resultatsListEleves.setPourMoyInf10AngG(pourMoyInf10AngG);
            resultatsListEleves.setPourMoyInf10Ang(pourMoyInf10Ang);

            resultatsListEleves.setClassAllF(classAllF);
            resultatsListEleves.setClassAllG(classAllG);
            resultatsListEleves.setNbreMoyInf10AllF(nbreMoyInf10AllF);
            resultatsListEleves.setNbreMoyInf10AllG(nbreMoyInf10AllG);
            resultatsListEleves.setPourMoyInf10AllF(pourMoyInf10AllF);
            resultatsListEleves.setPourMoyInf10AllG(pourMoyInf10AllG);
            resultatsListEleves.setPourMoyInf10All(pourMoyInf10All);

            resultatsListEleves.setClassEspF(classEspF);
            resultatsListEleves.setClassEspG(classEspG);
            resultatsListEleves.setNbreMoyInf10EspF(nbreMoyInf10EspF);
            resultatsListEleves.setNbreMoyInf10EspG(nbreMoyInf10EspG);
            resultatsListEleves.setPourMoyInf10EspF(pourMoyInf10EspF);
            resultatsListEleves.setPourMoyInf10EspG(pourMoyInf10EspG);
            resultatsListEleves.setPourMoyInf10Esp(pourMoyInf10Esp);

            resultatsListEleves.setClassHgF(classHgF);
            resultatsListEleves.setClassHgG(classHgG);
            resultatsListEleves.setNbreMoyInf10HgF(nbreMoyInf10HgF);
            resultatsListEleves.setNbreMoyInf10HgG(nbreMoyInf10HgG);
            resultatsListEleves.setPourMoyInf10HgF(pourMoyInf10HgF);
            resultatsListEleves.setPourMoyInf10HgG(pourMoyInf10HgG);
            resultatsListEleves.setPourMoyInf10Hg(pourMoyInf10Hg);
            resultatsListEleves.setCycle(cycle);
            resultatsListEleves.setOrdre_niveau(orderNiveau);
            resultatsListEleves.setPeriode (libelleTrimestre);
            resultatsListElevesDto.add(resultatsListEleves) ;



        }

        return  resultatsListElevesDto ;
    }
    public  Integer getOrderNiveau(String niveau ,String libelleAnnee , String libelleTrimestre){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct o.ordreNiveau from Bulletin o where  o.niveau=:niveau and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }
  public  Integer getEffectifParClasse(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
       Long effectifClasse;
      List<Integer> effecArray =new ArrayList<>();
      Integer sum = 0;
      try {
          effecArray = (List<Integer>) em.createQuery("select o.effectif from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau,o.effectif having  o.niveau=:niveau ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getResultList() ;

          for (Integer value : effecArray) {
              sum += value;
          }
      } catch (NoResultException e){
          return 0 ;
      }


  return  sum ;
  }
  public  Long geteffeF(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre,Long idMatiere){
      Long effeF ;
      try {
          return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o ,DetailBulletin d  where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                          "and o.id = d.bulletin.id and d.matiereId=:idMatiere  group by  o.niveau having  o.niveau=:niveau")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .setParameter("idMatiere", idMatiere)
                  .getSingleResult();
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long geteffeG(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre ,Long idMatiere){

      Long  effeG ;
      try {
          effeG= (Long) em.createQuery("select count(o.id) from Bulletin o ,DetailBulletin d where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee" +
                          " and o.id = d.bulletin.id and d.matiereId=:idMatiere  group by o.niveau having  o.niveau=:niveau")
                  .setParameter("sexe","MASCULIN")
                  .setParameter("idEcole",idEcole)
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .setParameter("idMatiere", idMatiere)
                  .getSingleResult();
          return  effeG ;
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long getclassF(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre ,Long idMatiere){
      Long classF;
      try {
          classF = (Long) em.createQuery("select count(o.id) from Bulletin o ,DetailBulletin d where  o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                          "and o.id = d.bulletin.id and d.matiereId=:idMatiere group by  o.niveau having o.niveau=:niveau")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("isClass","O")
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .setParameter("idMatiere", idMatiere)
                  .getSingleResult();

          return  classF ;
      } catch (NoResultException e){
          return 0L ;
      }


  }
    public Long getclassG(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre ,Long idMatiere){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o ,DetailBulletin d where  o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked =:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            "and o.id = d.bulletin.id and d.matiereId=:idMatiere group by o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("idMatiere", idMatiere)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getNonClasseF(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nonclassF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","N")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();

            return  nonclassF ;
        } catch (NoResultException e){
            return 0L ;
        }


    }

    public  Long getNonClasseG(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long    nonclassG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","N")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nonclassG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getnbreMoyInf10F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre ,Long idMatiere){
        try {
            Long   nbreMoySup10F = (Long) em.createQuery("select count(o.id) from Bulletin o ,DetailBulletin d where o.isClassed=:isClass and  o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne<:moy and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            "and o.id = d.bulletin.id and d.matiereId=:idMatiere group by  o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("idMatiere", idMatiere)
                    .getSingleResult();
            return  nbreMoySup10F;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf10G(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre ,Long idMatiere){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o,DetailBulletin d where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                            "and o.id = d.bulletin.id and d.matiereId=:idMatiere  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("idMatiere", idMatiere)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf999F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf999G(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nbreMoyInf999G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("niveau",niveau)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf85G(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long   nbreMoyInf85G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("niveau",niveau)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf85G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf85F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long  nbreMoyInf85F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("niveau",niveau)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf85F ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Double getmoyClasseF(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double  getmoyClasseG(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double getmoyClasse(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseGET(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                     .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseFET(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                     .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseET(Long idEcole ,String niveau  ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }


}
