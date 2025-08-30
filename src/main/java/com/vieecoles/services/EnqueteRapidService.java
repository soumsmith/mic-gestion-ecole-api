package com.vieecoles.services;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.Rapide_rapideDto;
import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.entities.domaine;
import com.vieecoles.entities.operations.Rapide_rapide;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.projection.EnqueteSelectDto;
import com.vieecoles.steph.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EnqueteRapidService implements PanacheRepositoryBase<Rapide_rapide, Long> {
    @Inject
    EntityManager em;
   public List<Rapide_rapide> getListdomaine(){
       return  domaine.listAll();
   }

   public  String insertorModifEnquete(Rapide_rapideDto r ) {
       Rapide_rapide ra = new Rapide_rapide() ;
       ra = checkExistEnquete(r.getIdEcole(),r.getIdAnn() , r.getIdBranche()) ;
       String mess = null ;
       if (ra ==null){
           Rapide_rapide ra2 = new Rapide_rapide() ;
           Annee_Scolaire an = new Annee_Scolaire() ;
           ecole  e = new ecole() ;
           Branche b = new Branche() ;
           b = Branche.findById(r.getIdBranche());
           an = Annee_Scolaire.findById(r.getIdAnn()) ;
           e = ecole.findById(r.getIdEcole()) ;

           ra2.setAnnee_scolaire(an);
           ra2.setEcole(e);
           ra2.setBranche(b);
           ra2.setNombreAff(r.getNombreAff());
           ra2.setNombreNAff(r.getNombreNAff());

           ra2.persist();
           return  mess= "Enquête bien enregistrée!" ;

       } else {
           Annee_Scolaire an = new Annee_Scolaire() ;
           ecole  e = new ecole() ;
           an = Annee_Scolaire.findById(r.getIdAnn()) ;
           Branche b = new Branche() ;
           b = Branche.findById(r.getIdBranche());
           e = ecole.findById(r.getIdEcole()) ;
           ra.setAnnee_scolaire(an);
           ra.setEcole(e);
           ra.setBranche(b);
           ra.setNombreAff(r.getNombreAff());
           ra.setNombreNAff(r.getNombreNAff());
           return  mess= "Enquête bien modifiée!" ;

       }

   }



    public Rapide_rapide checkExistEnquete(Long idEcole ,Long AnneeId , Long idBranche){
        try {
            return (Rapide_rapide) em.createQuery("select o from Rapide_rapide o where o.annee_scolaire.annee_scolaireid =:AnneeId and " +
                            " o.branche.id=:idBranche  " +
                            " and o.ecole.ecoleid=:idEcole  ")
                    .setParameter("AnneeId",AnneeId)
                    .setParameter("idEcole",idEcole)
                    .setParameter("idBranche",idBranche)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }
    }

    public List<EnqueteSelectDto> getList(Long idEcole , Long AnneeId ){
        List<EnqueteSelectDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<EnqueteSelectDto> q = em.createQuery( "SELECT new com.vieecoles.projection.EnqueteSelectDto(b.libelle ,b.id ,o.nombreAff,o.nombreNAff) from Rapide_rapide o  join o.branche b  where o.ecole.ecoleid=:idEcole " +
                        " and o.annee_scolaire.annee_scolaireid =:AnneeId "
                , EnqueteSelectDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("idEcole", idEcole)
                .setParameter("AnneeId", AnneeId)
                .getResultList() ;
        return  classeNiveauDtoList ;
    }




}
