package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EffectifParLangueServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public EffectifElevLangueVivante2Dto getLangueVivante(Long idEcole ,Long anneeId){

        EffectifElevLangueVivante2Dto  m = new EffectifElevLangueVivante2Dto() ;
        Long nbreClasseALL4eme =null , nbreClasseESP4eme =null, nbreClasseALL3eme =null , nbreClasseESP3eme =null;
         Long nbreClasseALL2ndA =null , nbreClasseESP2ndA =null, nbreClasseALL2ndC =null, nbreClasseESP2ndC =null;
         Long nbreClasseALL1erA =null, nbreClasseESP1erA =null , nbreClasseALLTLA =null, nbreClasseESPTLA =null ;
         Long nbreEleveALL4eme =null , nbreEleveESP4eme =null, nbreEleveALL3eme =null, nbreEleveESP3eme =null ;
         Long nbreEleveALL2ndA =null, nbreEleveESP2ndA =null, nbreEleveALL2ndC =null, nbreEleveESP2ndC =null;
         Long nbreEleveALL1erA =null, nbreEleveESP1erA =null,nbreEleveALLTLA =null , nbreEleveESPTLA =null;

        nbreClasseALL4eme=getCountNomBreParLangueClasse(idEcole,anneeId,3,1L);
        nbreClasseESP4eme=getCountNomBreParLangueClasse(idEcole,anneeId,3,2L);

        nbreClasseALL3eme=getCountNomBreParLangueClasse(idEcole,anneeId,4,1L);
        nbreClasseESP3eme=getCountNomBreParLangueClasse(idEcole,anneeId,4,2L);



        nbreClasseALL2ndA=getCountNomBreParLangueClasse(idEcole,anneeId,5,1L);
        nbreClasseESP2ndA=getCountNomBreParLangueClasse(idEcole,anneeId,5,2L);

        nbreClasseALL1erA =getCountNomBreParLangueClasse(idEcole,anneeId,6,1L);
        nbreClasseESP1erA =getCountNomBreParLangueClasse(idEcole,anneeId,6,2L);

        nbreClasseALL2ndC =getCountNomBreParLangueClasse(idEcole,anneeId,7,1L);
        nbreClasseESP2ndC =getCountNomBreParLangueClasse(idEcole,anneeId,7,2L);

        nbreClasseALLTLA =getCountNomBreParLangueClasse(idEcole,anneeId,8,1L);
        nbreClasseESPTLA =getCountNomBreParLangueClasse(idEcole,anneeId,8,2L);

        /////////////////////////////

        nbreEleveALL4eme=getEffectparLangueAfflanClasse(anneeId,3,1L ,idEcole);
        nbreEleveESP4eme=getEffectparLangueAfflanClasse(anneeId,3,2L,idEcole);

        nbreEleveALL3eme=getEffectparLangueAfflanClasse(anneeId,4,1L,idEcole);
        nbreEleveESP3eme=getEffectparLangueAfflanClasse(anneeId,4,2L,idEcole);

        nbreEleveALL2ndA=getEffectparLangueAfflanClasse(anneeId,5,1L,idEcole);
        nbreEleveESP2ndA=getEffectparLangueAfflanClasse(anneeId,5,2L ,idEcole);

        nbreEleveALL1erA =getEffectparLangueAfflanClasse(anneeId,6,1L ,idEcole);
        nbreEleveESP1erA =getEffectparLangueAfflanClasse(anneeId,6,2L ,idEcole);

        nbreEleveALL2ndC =getEffectparLangueAfflanClasse(anneeId,7,1L ,idEcole);
        nbreEleveESP2ndC =getEffectparLangueAfflanClasse(anneeId,7,2L ,idEcole);

        nbreEleveALLTLA =getEffectparLangueAfflanClasse(anneeId,8,1L ,idEcole);
        nbreEleveESPTLA =getEffectparLangueAfflanClasse(anneeId,8,2L ,idEcole);

        m.setNbreClasseESP4eme(nbreClasseESP4eme);
        m.setNbreClasseALL4eme(nbreClasseALL4eme);
        m.setNbreEleveALL4eme(nbreEleveALL4eme);
        m.setNbreEleveESP4eme(nbreEleveESP4eme);

        m.setNbreClasseESP3eme(nbreClasseESP3eme);
        m.setNbreClasseALL3eme(nbreClasseALL3eme);
        m.setNbreEleveALL3eme(nbreEleveALL3eme);
        m.setNbreEleveESP3eme(nbreEleveESP3eme);

        m.setNbreClasseESP2ndA(nbreClasseESP2ndA);
        m.setNbreClasseALL2ndA(nbreClasseALL2ndA);
        m.setNbreEleveALL2ndA(nbreEleveALL2ndA);
        m.setNbreEleveESP2ndA(nbreEleveESP2ndA);

        m.setNbreClasseESP1erA(nbreClasseESP1erA);
        m.setNbreClasseALL1erA(nbreClasseALL1erA);
        m.setNbreEleveALL1erA(nbreEleveALL1erA);
        m.setNbreEleveESP1erA(nbreEleveESP1erA);

        m.setNbreClasseESP2ndC(nbreClasseESP2ndC);
        m.setNbreClasseALL2ndC(nbreClasseALL2ndC);
        m.setNbreEleveALL2ndC(nbreEleveALL2ndC);
        m.setNbreEleveESP2ndC(nbreEleveESP2ndC);

        m.setNbreClasseESPTLA(nbreClasseESPTLA);
        m.setNbreClasseALLTLA(nbreClasseALLTLA);
        m.setNbreEleveALLTLA(nbreEleveALLTLA);
        m.setNbreEleveESPTLA(nbreEleveESPTLA);

        return  m ;
    }



       Long getCountNomBreParLangueClasse(Long idEcole , Long idAnneId ,Integer niveauId ,Long langueid) {
           try {
               TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from Classe o   where o.ecole.id =:idEcole   and o.branche.niveau.id=:niveauId and o.langueVivante.id=:langueid ");
               Long size = q.setParameter("idEcole" ,idEcole).
                                  setParameter("niveauId" ,niveauId).
                                     setParameter("langueid" ,langueid).
                              getSingleResult() ;

               return size;
           } catch (NoResultException e) {
               return 0L ;
           }
       }





    Long getEffectparLangueAfflanClasse(Long idAnneId ,Integer niveauId ,Long langueId ,Long idEcole  ) {
        //  Inscriptions.statusEleve aff = Inscriptions.statusEleve.valueOf(aff1);
        try {
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  count(o.id) from ClasseEleve o  where o.classe.branche.niveau.id =: niveauId  and o.inscription.annee.id=:idAnn " +
                    " and o.classe.langueVivante.id =: langueId and o.classe.ecole.id=:idEcole ");
            Long size = q.setParameter("niveauId" ,niveauId).
                    setParameter("idAnn" ,idAnneId).
                    setParameter("langueId" ,langueId).
                    setParameter("idEcole" ,idEcole).
                    getSingleResult() ;

            return size;
        } catch (NoResultException e) {
            return 0L ;
        }
    }


}
