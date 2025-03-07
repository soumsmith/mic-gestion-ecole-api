package com.vieecoles.processors.bouake.services;

import com.vieecoles.dto.EffectifElevLangueVivante2Dto;
import com.vieecoles.dto.LangVivanteDto;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class EffectifParLangueServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public LangVivanteDto getLangueVivante(Long idEcole ,String anneLibelle, String trimestre,Long anneeId){
        Long effe5G=0L; Long effe6G=0L;Long effeALL4G=0L;Long effeESP4G=0L;Long effeALL3G=0L;Long effeESP3G=0L;
        Long effeALL2AG=0L;Long effeESP2AG=0L;Long effeALL2CG=0L;Long effeESP2CG=0L;Long effeALL1AG=0L;
        Long effeESP1AG=0L;Long effeALL1CG=0L; Long effeESP1CG=0L; Long effeALL1DG=0L;Long effeESP1DG=0L;
        Long effeALLTAG=0L; Long effeESPTAG=0L;Long effeALLTCG=0L;Long effeESPTCG=0L;Long effeALLTDG=0L;
        Long effeESPTDG=0L;Long effe5F=0L;Long effe6F=0L;Long effeALL4F=0L;Long effeESP4F=0L;
        Long effeALL3F=0L;Long effeESP3F=0L;Long effeALL2AF=0L;Long effeESP2AF=0L;Long effeALL2CF=0L;
        Long effeESP2CF=0L;Long effeALL1AF=0L;Long effeESP1AF=0L; Long effeALL1CF=0L;
        Long effeESP1CF=0L;Long effeALL1DF=0L;Long effeESP1DF=0L;Long effeALLTAF=0L;
        Long effeESPTAF=0L;Long effeALLTCF=0L;Long effeESPTCF=0L;Long effeALLTDF=0L;
        Long effeESPTDF=0L;Long effe5Affecte=0L;Long effe6Affecte=0L;Long effeALL4Affecte=0L;
        Long effeESP4Affecte=0L;Long effeALL3Affecte=0L;Long effeESP3Affecte=0L; Long effeALL2AAffecte=0L;
        Long effeESP2AAffecte=0L;Long effeALL2CAffecte=0L;
        Long effeESP2CAffecte=0L;Long effeALL1AAffecte=0L;Long effeESP1AAffecte=0L; Long effeALL1CAffecte=0L;
        Long effeESP1CAffecte=0L;Long effeALL1DAffecte=0L; Long effeESP1DAffecte=0L;Long effeALLTAAffecte=0L;
        Long effeESPTAAffecte=0L;Long effeALLTCAffecte=0L;Long effeESPTCAffecte=0L;Long effeALLTDAffecte=0L;
        Long effeESPTDAffecte=0L;Long effe5NonAffecte=0L; Long effe6NonAffecte=0L;Long effeALL4NonAffecte=0L;
        Long effeESP4NonAffecte=0L;Long effeALL3NonAffecte=0L;Long effeESP3NonAffecte=0L;Long effeALL2ANonAffecte=0L;
        Long effeESP2ANonAffecte=0L;Long effeALL2CNonAffecte=0L;Long effeESP2CNonAffecte=0L;
        Long effeALL1ANonAffecte=0L;Long effeESP1ANonAffecte=0L; Long effeALL1CNonAffecte=0L;
        Long effeESP1CNonAffecte=0L; Long effeALL1DNonAffecte=0L; Long effeESP1DNonAffecte=0L;
        Long effeALLTANonAffecte=0L;Long effeESPTANonAffecte=0L;Long effeALLTCNonAffecte=0L;
        Long effeESPTCNonAffecte=0L;Long effeALLTDNonAffecte=0L; Long effeESPTDNonAffecte=0L;

      Long effe5Classe=0L;Long effe6Classe=0L; Long effeALL4Classe=0L;Long effeESP4Classe=0L;Long effeALL3Classe=0L; Long effeESP3Classe=0L;
      Long effeALL2AClasse=0L;Long effeESP2AClasse=0L;Long effeALL2CClasse=0L; Long effeESP2CClasse=0L;Long effeALL1AClasse=0L;
      Long effeESP1AClasse=0L;Long effeALL1CClasse=0L; Long effeESP1CClasse=0L;Long effeALL1DClasse=0L;Long effeESP1DClasse=0L;
      Long effeALLTAClasse=0L;Long effeESPTAClasse=0L;Long effeALLTCClasse=0L;Long effeESPTCClasse=0L;Long effeALLTDClasse=0L;
      Long effeESPTDClasse=0L;


        LangVivanteDto m = new LangVivanteDto();
        effeALL4F = geteffeAllF(idEcole,3,anneLibelle,trimestre);
        effeESP4F = geteffeEspF(idEcole,3,anneLibelle,trimestre);
        m.setEffeALL4F(effeALL4F);
        m.setEffeESP4F(effeESP4F);

        effeALL3F = geteffeAllF(idEcole,4,anneLibelle,trimestre);
        effeESP3F = geteffeEspF(idEcole,4,anneLibelle,trimestre);

        m.setEffeALL3F(effeALL3F);
        m.setEffeESP3F(effeESP3F);

        effeALL2AF = geteffeAllF(idEcole,5,anneLibelle,trimestre);
        effeESP2AF = geteffeEspF(idEcole,5,anneLibelle,trimestre);
        m.setEffeALL2AF(effeALL2AF);
        m.setEffeESP2AF(effeESP2AF);

        effeALL2CF = geteffeAllF(idEcole,6,anneLibelle,trimestre);
        effeESP2CF = geteffeEspF(idEcole,6,anneLibelle,trimestre);
        m.setEffeALL2CF(effeALL2CF);
        m.setEffeESP2CF(effeESP2CF);

        effeALL1AF = geteffeAllF(idEcole,7,anneLibelle,trimestre);
        effeESP1AF = geteffeEspF(idEcole,7,anneLibelle,trimestre);

        m.setEffeALL1AF(effeALL1AF);
        m.setEffeESP1AF(effeESP1AF);

        effeALL1CF = geteffeAllF(idEcole,8,anneLibelle,trimestre);
        effeESP1CF = geteffeEspF(idEcole,8,anneLibelle,trimestre);
        m.setEffeALL1CF(effeALL1CF);
        m.setEffeESP1CF(effeESP1CF);

        effeALL1DF = geteffeAllF(idEcole,9,anneLibelle,trimestre);
        effeESP1DF = geteffeEspF(idEcole,9,anneLibelle,trimestre);

        m.setEffeALL1DF(effeALL1DF);
        m.setEffeESP1DF(effeESP1DF);

        effeALLTAF = geteffeAllF(idEcole,10,anneLibelle,trimestre);
        effeESPTAF = geteffeEspF(idEcole,10,anneLibelle,trimestre);


       Long effeALLTA1F = geteffeAllF(idEcole,11,anneLibelle,trimestre);
        Long effeESPTA1F = geteffeEspF(idEcole,11,anneLibelle,trimestre);

        Long effeALLTA2F = geteffeAllF(idEcole,12,anneLibelle,trimestre);
        Long effeESPTA2F = geteffeEspF(idEcole,12,anneLibelle,trimestre);

        effeALLTAF=effeALLTAF+effeALLTA1F+effeALLTA2F;
        effeESPTAF=effeESPTAF+effeESPTA1F+effeESPTA2F;
        m.setEffeALLTAF(effeALLTAF);
        m.setEffeESPTAF(effeESPTAF);

        effeALLTCF = geteffeAllF(idEcole,13,anneLibelle,trimestre);
        effeESPTCF = geteffeEspF(idEcole,13,anneLibelle,trimestre);
        m.setEffeALLTCF(effeALLTCF);
        m.setEffeESPTCF(effeESPTCF);

        effeALLTDF = geteffeAllF(idEcole,14,anneLibelle,trimestre);
        effeESPTDF = geteffeEspF(idEcole,14,anneLibelle,trimestre);

        m.setEffeALLTDF(effeALLTDF);
        m.setEffeESPTDF(effeESPTDF);
        //Garcon
        effeALL4G = geteffeAllG(idEcole,3,anneLibelle,trimestre);
        effeESP4G = geteffeEspG(idEcole,3,anneLibelle,trimestre);
        m.setEffeALL4G(effeALL4G);
        m.setEffeESP4G(effeESP4G);

        effeALL3G = geteffeAllG(idEcole,4,anneLibelle,trimestre);
        effeESP3G = geteffeEspG(idEcole,4,anneLibelle,trimestre);

        m.setEffeALL3G(effeALL3G);
        m.setEffeESP3G(effeESP3G);

        effeALL2AG = geteffeAllG(idEcole,5,anneLibelle,trimestre);
        effeESP2AG= geteffeEspG(idEcole,5,anneLibelle,trimestre);
        m.setEffeALL2AG(effeALL2AG);
        m.setEffeESP2AG(effeESP2AG);

        effeALL2CG = geteffeAllG(idEcole,6,anneLibelle,trimestre);
        effeESP2CG = geteffeEspG(idEcole,6,anneLibelle,trimestre);
        m.setEffeALL2CG(effeALL2CG);
        m.setEffeESP2CG(effeESP2CG);

        effeALL1AG = geteffeAllG(idEcole,7,anneLibelle,trimestre);
        effeESP1AG = geteffeEspG(idEcole,7,anneLibelle,trimestre);

        m.setEffeALL1AG(effeALL1AG);
        m.setEffeESP1AG(effeESP1AG);

        effeALL1CG = geteffeAllG(idEcole,8,anneLibelle,trimestre);
        effeESP1CG = geteffeEspG(idEcole,8,anneLibelle,trimestre);
        m.setEffeALL1CG(effeALL1CG);
        m.setEffeESP1CG(effeESP1CG);

        effeALL1DG = geteffeAllG(idEcole,9,anneLibelle,trimestre);
        effeESP1DG = geteffeEspG(idEcole,9,anneLibelle,trimestre);

        m.setEffeALL1DG(effeALL1DG);
        m.setEffeESP1DG(effeESP1DG);

        effeALLTAG = geteffeAllG(idEcole,10,anneLibelle,trimestre);
        effeESPTAG = geteffeEspG(idEcole,10,anneLibelle,trimestre);


        Long effeALLTA1G = geteffeAllG(idEcole,11,anneLibelle,trimestre);
        Long effeESPTA1G = geteffeEspG(idEcole,11,anneLibelle,trimestre);

        Long effeALLTA2G = geteffeAllG(idEcole,12,anneLibelle,trimestre);
        Long effeESPTA2G = geteffeEspG(idEcole,12,anneLibelle,trimestre);

        effeALLTAG=effeALLTAG+effeALLTA1G+effeALLTA2G;
        effeESPTAG=effeESPTAG+effeESPTA1G+effeESPTA2G;
        m.setEffeALLTAG(effeALLTAG);
        m.setEffeESPTAG(effeESPTAG);

        effeALLTCG = geteffeAllG(idEcole,13,anneLibelle,trimestre);
        effeESPTCG = geteffeEspG(idEcole,13,anneLibelle,trimestre);
        m.setEffeALLTCG(effeALLTCG);
        m.setEffeESPTCG(effeESPTCG);

        effeALLTDG = geteffeAllG(idEcole,14,anneLibelle,trimestre);
        effeESPTDG = geteffeEspG(idEcole,14,anneLibelle,trimestre);

        m.setEffeALLTDG(effeALLTDG);
        m.setEffeESPTDG(effeESPTDG);

        effeESP4Affecte= geteffeEspAFF(idEcole,3,anneLibelle,trimestre);
        effeALL4Affecte= geteffeAllAFF(idEcole,3,anneLibelle,trimestre);
        m.setEffeALL4Affecte(effeALL4Affecte);
        m.setEffeESP4Affecte(effeESP4Affecte);

        effeESP3Affecte= geteffeEspAFF(idEcole,4,anneLibelle,trimestre);
        effeALL3Affecte= geteffeAllAFF(idEcole,4,anneLibelle,trimestre);
        m.setEffeALL3Affecte(effeALL3Affecte);
        m.setEffeESP3Affecte(effeESP3Affecte);

        effeESP2AAffecte= geteffeEspAFF(idEcole,5,anneLibelle,trimestre);
        effeALL2AAffecte= geteffeAllAFF(idEcole,5,anneLibelle,trimestre);

        m.setEffeALL2AAffecte(effeALL2AAffecte);
        m.setEffeESP2AAffecte(effeESP2AAffecte);

        effeESP2CAffecte= geteffeEspAFF(idEcole,6,anneLibelle,trimestre);
        effeALL2CAffecte= geteffeAllAFF(idEcole,6,anneLibelle,trimestre);
        m.setEffeALL2CAffecte(effeALL2CAffecte);
        m.setEffeESP2CAffecte(effeESP2CAffecte);

        effeESP1AAffecte= geteffeEspAFF(idEcole,7,anneLibelle,trimestre);
        effeALL1AAffecte= geteffeAllAFF(idEcole,7,anneLibelle,trimestre);

        m.setEffeALL1AAffecte(effeALL1AAffecte);
        m.setEffeESP1AAffecte(effeESP1AAffecte);

        effeESP1CAffecte= geteffeEspAFF(idEcole,8,anneLibelle,trimestre);
        effeALL1CAffecte= geteffeAllAFF(idEcole,8,anneLibelle,trimestre);
        m.setEffeALL1CAffecte(effeALL1CAffecte);
        m.setEffeESP1CAffecte(effeESP1CAffecte);

        effeESP1DAffecte= geteffeEspAFF(idEcole,9,anneLibelle,trimestre);
        effeALL1DAffecte= geteffeAllAFF(idEcole,9,anneLibelle,trimestre);
        m.setEffeALL1DAffecte(effeALL1DAffecte);
        m.setEffeESP1DAffecte(effeESP1DAffecte);

        effeESPTAAffecte= geteffeEspAFF(idEcole,10,anneLibelle,trimestre);
        effeALLTAAffecte= geteffeAllAFF(idEcole,10,anneLibelle,trimestre);


        Long effeESPTA1Affecte= geteffeEspAFF(idEcole,11,anneLibelle,trimestre);
       Long  effeALLTA1Affecte= geteffeAllAFF(idEcole,11,anneLibelle,trimestre);

        Long effeESPTA2Affecte= geteffeEspAFF(idEcole,12,anneLibelle,trimestre);
        Long  effeALLTA2Affecte= geteffeAllAFF(idEcole,12,anneLibelle,trimestre);

        effeESPTAAffecte=effeESPTAAffecte+effeESPTA1Affecte+effeESPTA2Affecte;
        effeALLTAAffecte=effeALLTAAffecte+effeALLTA1Affecte+effeALLTA2Affecte;

        m.setEffeALLTAAffecte(effeALLTAAffecte);
        m.setEffeESPTAAffecte(effeESPTAAffecte);

        effeESPTCAffecte= geteffeEspAFF(idEcole,13,anneLibelle,trimestre);
         effeALLTCAffecte= geteffeAllAFF(idEcole,13,anneLibelle,trimestre);

        m.setEffeALLTCAffecte(effeALLTCAffecte);
        m.setEffeESPTCAffecte(effeESPTCAffecte);

        effeESPTDAffecte= geteffeEspAFF(idEcole,14,anneLibelle,trimestre);
        effeALLTDAffecte= geteffeAllAFF(idEcole,14,anneLibelle,trimestre);

        m.setEffeALLTDAffecte(effeALLTDAffecte);
        m.setEffeESPTDAffecte(effeESPTDAffecte);

        //Non affectes

        effeESP4NonAffecte= geteffeEspNonAFF(idEcole,3,anneLibelle,trimestre);
        effeALL4NonAffecte= geteffeAllNonAFF(idEcole,3,anneLibelle,trimestre);
        m.setEffeALL4NonAffecte(effeALL4NonAffecte);
        m.setEffeESP4NonAffecte(effeESP4NonAffecte);

        effeESP3NonAffecte= geteffeEspNonAFF(idEcole,4,anneLibelle,trimestre);
        effeALL3NonAffecte= geteffeAllNonAFF(idEcole,4,anneLibelle,trimestre);
        m.setEffeALL3NonAffecte(effeALL3NonAffecte);
        m.setEffeESP3NonAffecte(effeESP3NonAffecte);

        effeESP2ANonAffecte= geteffeEspNonAFF(idEcole,5,anneLibelle,trimestre);
        effeALL2ANonAffecte= geteffeAllNonAFF(idEcole,5,anneLibelle,trimestre);

        m.setEffeALL2ANonAffecte(effeALL2ANonAffecte);
        m.setEffeESP2ANonAffecte(effeESP2ANonAffecte);

        effeESP2CNonAffecte= geteffeEspNonAFF(idEcole,6,anneLibelle,trimestre);
        effeALL2CNonAffecte= geteffeAllNonAFF(idEcole,6,anneLibelle,trimestre);
        m.setEffeALL2CNonAffecte(effeALL2CNonAffecte);
        m.setEffeESP2CNonAffecte(effeESP2CNonAffecte);

        effeESP1ANonAffecte= geteffeEspNonAFF(idEcole,7,anneLibelle,trimestre);
        effeALL1ANonAffecte= geteffeAllNonAFF(idEcole,7,anneLibelle,trimestre);

        m.setEffeALL1ANonAffecte(effeALL1ANonAffecte);
        m.setEffeESP1ANonAffecte(effeESP1ANonAffecte);

        effeESP1CNonAffecte= geteffeEspNonAFF(idEcole,8,anneLibelle,trimestre);
        effeALL1CNonAffecte= geteffeAllNonAFF(idEcole,8,anneLibelle,trimestre);
        m.setEffeALL1CNonAffecte(effeALL1CNonAffecte);
        m.setEffeESP1CNonAffecte(effeESP1CNonAffecte);

        effeESP1DNonAffecte= geteffeEspNonAFF(idEcole,9,anneLibelle,trimestre);
        effeALL1DNonAffecte= geteffeAllNonAFF(idEcole,9,anneLibelle,trimestre);
        m.setEffeALL1DNonAffecte(effeALL1DNonAffecte);
        m.setEffeESP1DNonAffecte(effeESP1DNonAffecte);

        effeESPTANonAffecte= geteffeEspNonAFF(idEcole,10,anneLibelle,trimestre);
        effeALLTANonAffecte= geteffeAllNonAFF(idEcole,10,anneLibelle,trimestre);


        Long effeESPTA1NonAffecte= geteffeEspNonAFF(idEcole,11,anneLibelle,trimestre);
        Long  effeALLTA1NonAffecte= geteffeAllNonAFF(idEcole,11,anneLibelle,trimestre);

        Long effeESPTA2NonAffecte= geteffeEspNonAFF(idEcole,12,anneLibelle,trimestre);
        Long  effeALLTA2NonAffecte= geteffeAllNonAFF(idEcole,12,anneLibelle,trimestre);

        effeESPTANonAffecte=effeESPTANonAffecte+effeESPTA1NonAffecte+effeESPTA2NonAffecte;
        effeALLTANonAffecte=effeALLTANonAffecte+effeALLTA1NonAffecte+effeALLTA2NonAffecte;

        m.setEffeALLTANonAffecte(effeALLTANonAffecte);
        m.setEffeESPTANonAffecte(effeESPTANonAffecte);

        effeESPTCNonAffecte= geteffeEspNonAFF(idEcole,13,anneLibelle,trimestre);
        effeALLTCNonAffecte= geteffeAllNonAFF(idEcole,13,anneLibelle,trimestre);

        m.setEffeALLTCNonAffecte(effeALLTCNonAffecte);
        m.setEffeESPTCNonAffecte(effeESPTCNonAffecte);

        effeESPTDNonAffecte= geteffeEspNonAFF(idEcole,14,anneLibelle,trimestre);
        effeALLTDNonAffecte= geteffeAllNonAFF(idEcole,14,anneLibelle,trimestre);

        m.setEffeALLTDNonAffecte(effeALLTDNonAffecte);
        m.setEffeESPTDNonAffecte(effeESPTDNonAffecte);

        //Classe pedagogique


      effeESP4Classe= getClasseEsp(idEcole,3,anneLibelle,trimestre);
      effeALL4Classe= getClasseAll(idEcole,3,anneLibelle,trimestre);
      m.setEffeALL4Classe(effeALL4Classe);
      m.setEffeESP4Classe(effeESP4Classe);

      effeESP3Classe= getClasseEsp(idEcole,4,anneLibelle,trimestre);
      effeALL3Classe= getClasseAll(idEcole,4,anneLibelle,trimestre);
      m.setEffeALL3Classe(effeALL3Classe);
      m.setEffeESP3Classe(effeESP3Classe);

      effeESP2AClasse= getClasseEsp(idEcole,5,anneLibelle,trimestre);
      effeALL2AClasse= getClasseAll(idEcole,5,anneLibelle,trimestre);

      m.setEffeALL2AClasse(effeALL2AClasse);
      m.setEffeESP2AClasse(effeESP2AClasse);

      effeESP2CClasse= getClasseEsp(idEcole,6,anneLibelle,trimestre);
      effeALL2CClasse= getClasseAll(idEcole,6,anneLibelle,trimestre);
      m.setEffeALL2CClasse(effeALL2CClasse);
      m.setEffeESP2CClasse(effeESP2CClasse);

      effeESP1AClasse= getClasseEsp(idEcole,7,anneLibelle,trimestre);
      effeALL1AClasse= getClasseAll(idEcole,7,anneLibelle,trimestre);

      m.setEffeALL1AClasse(effeALL1AClasse);
      m.setEffeESP1AClasse(effeESP1AClasse);

      effeESP1CClasse= getClasseEsp(idEcole,8,anneLibelle,trimestre);
      effeALL1CClasse= getClasseAll(idEcole,8,anneLibelle,trimestre);
      m.setEffeALL1CClasse(effeALL1CClasse);
      m.setEffeESP1CClasse(effeESP1CClasse);

      effeESP1DClasse= getClasseEsp(idEcole,9,anneLibelle,trimestre);
      effeALL1DClasse= getClasseAll(idEcole,9,anneLibelle,trimestre);
      m.setEffeALL1DClasse(effeALL1DClasse);
      m.setEffeESP1DClasse(effeESP1DClasse);

      effeESPTAClasse= getClasseEsp(idEcole,10,anneLibelle,trimestre);
      effeALLTAClasse= getClasseAll(idEcole,10,anneLibelle,trimestre);


      Long effeESPTA1Classe= getClasseEsp(idEcole,11,anneLibelle,trimestre);
      Long  effeALLTA1Classe= getClasseAll(idEcole,11,anneLibelle,trimestre);

      Long effeESPTA2Classe= getClasseEsp(idEcole,12,anneLibelle,trimestre);
      Long  effeALLTA2Classe= getClasseAll(idEcole,12,anneLibelle,trimestre);

      effeESPTAClasse=effeESPTAClasse+effeESPTA1Classe+effeESPTA2Classe;
      effeALLTAClasse=effeALLTAClasse+effeALLTA1Classe+effeALLTA2Classe;

      m.setEffeALLTAClasse(effeALLTAClasse);
      m.setEffeESPTAClasse(effeESPTAClasse);

      effeESPTCClasse= getClasseEsp(idEcole,13,anneLibelle,trimestre);
      effeALLTCClasse= getClasseAll(idEcole,13,anneLibelle,trimestre);

      m.setEffeALLTCClasse(effeALLTCClasse);
      m.setEffeESPTCClasse(effeESPTCClasse);

      effeESPTDClasse= getClasseEsp(idEcole,14,anneLibelle,trimestre);
      effeALLTDClasse= getClasseAll(idEcole,14,anneLibelle,trimestre);

      m.setEffeALLTDClasse(effeALLTDClasse);
      m.setEffeESPTDClasse(effeESPTDClasse);

        return  m ;
    }



       Long getCountNomBreParLangueClasse(Long idEcole , Long idAnneId ,Integer niveauId ,String langueid) {
           try {
               TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from Bulletin o   where o.ecoleId =:idEcole   and o.ordreNiveau=:niveauId and o.lv2=:langueid ");
               Long size = q.setParameter("idEcole" ,idEcole).
                                  setParameter("niveauId" ,niveauId).
                                     setParameter("langueid" ,langueid).
                              getSingleResult() ;

               return size;
           } catch (NoResultException e) {
               return 0L ;
           }
       }

    public  Long geteffeEspF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ESP")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long geteffeEspAFF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.affecte=:affecte and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("affecte","AFFECTE")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ESP")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long geteffeAllAFF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.affecte=:affecte and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("affecte","AFFECTE")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ALL")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long geteffeEspNonAFF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.affecte=:affecte and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("affecte","NON_AFFECTE")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ESP")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long geteffeAllNonAFF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.affecte=:affecte and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("affecte","NON_AFFECTE")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ALL")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long geteffeEspG(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ESP")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long geteffeAllF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2  and  o.ordreNiveau=:niveau  order by o.ordreNiveau desc ")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ALL")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long geteffeAllG(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
                    " and o.lv2=:lv2 and  o.ordreNiveau=:niveau order by o.ordreNiveau desc ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("niveau",niveau)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("lv2", "ALL")
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }
    }


  public  Long getClasseAll(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select distinct count(o.classeId) from Bulletin o where   o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
              " and o.lv2=:lv2 and  o.ordreNiveau=:niveau order by o.ordreNiveau desc ")
          .setParameter("idEcole",idEcole)
          .setParameter("niveau",niveau)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("lv2", "ALL")
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }

  public  Long getClasseEsp(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select distinct count(o.classeId) from Bulletin o where   o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee " +
              " and o.lv2=:lv2 and  o.ordreNiveau=:niveau order by o.ordreNiveau desc ")
          .setParameter("idEcole",idEcole)
          .setParameter("niveau",niveau)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("lv2", "ESP")
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }


}
