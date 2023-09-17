package com.vieecoles.services.etats;

import com.vieecoles.dto.*;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.services.eleves.InscriptionService;
import com.vieecoles.services.souscription.SousceecoleService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CertificatScolariteServices {
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;
    @Inject
    SousceecoleService sousceecoleService ;

    public certificatScolariteDto getInfoCertificat(Long idEcole , String matricule , Long anneeId ,String periode){

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauDto(b.anneeLibelle) from Bulletin b  where b.matricule=:matricule "
                , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("matricule", matricule)
        .getResultList() ;

       int LongTableau = classeNiveauDtoList.size() ;

        Inscriptions myIns= new Inscriptions() ;
         String nomPrenomEleve = null ,  dateNaiss = null , lieuNaiss = null, matricules = null, nationalite = null ;
        String dateDebutFrequen  = null,dateFinFrequen = null ;


        myIns = inscriptionService.checkInscrit(idEcole,matricule,anneeId);
        if(myIns != null){
            nomPrenomEleve = myIns.getEleve().getElevenom() +" "+myIns.getEleve().getEleveprenom() ;
            dateNaiss = String.valueOf(myIns.getEleve().getElevedate_naissance());
            lieuNaiss =  myIns.getEleve().getElevelieu_naissance() ;
            matricules =  myIns.getEleve().getEleve_matricule() ;
            nationalite = myIns.getEleve().getEleve_nationalite() ;
            dateDebutFrequen = String.valueOf(myIns.getInscriptionsdate_creation()) ;

        }




       certificatScolariteDto resultatsListElevesDto = new certificatScolariteDto();
       List<parcourDto> parcourDtoList= new ArrayList<>() ;

        for (int i=0; i< LongTableau;i++) {
            parcourDto p = new parcourDto() ;
            p = getInfosParcours(idEcole ,matricule ,classeNiveauDtoList.get(i).getNiveau(),periode) ;
            parcourDtoList.add(p) ;
        }

        resultatsListElevesDto.setMatricule(matricules);
        resultatsListElevesDto.setNomPrenomEleve(nomPrenomEleve);
        resultatsListElevesDto.setNationalite(nationalite);
        resultatsListElevesDto.setDateNaiss(dateNaiss);
        resultatsListElevesDto.setLieuNaiss(lieuNaiss);
        resultatsListElevesDto.setDateDebutFrequen(dateDebutFrequen);
        resultatsListElevesDto.setParcourDto(parcourDtoList);

        return  resultatsListElevesDto ;
    }



     public  parcourDto getInfosParcours(Long idEcole , String matricule ,String libelleAnn ,String periode) {
         parcourDto p = new parcourDto() ;
         TypedQuery<parcourDto> q = em.createQuery( "SELECT new com.vieecoles.dto.parcourDto(b.anneeLibelle ,b.nomEcole,b.libelleClasse,b.moyAn,b.rangAn) from Bulletin b  where b.matricule=:matricule" +
                         " and b.ecoleId =:idEcole and b.anneeLibelle =:libelleAnn and b.libellePeriode=:periode "
                 , parcourDto.class);
         p = q.setParameter("matricule", matricule)
                .setParameter("idEcole", idEcole)
                 .setParameter("periode", periode)
                 .setParameter("libelleAnn", libelleAnn)
                 .getSingleResult() ;

         return p ;

     }





}
