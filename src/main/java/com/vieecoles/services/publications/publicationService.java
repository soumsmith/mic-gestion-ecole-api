package com.vieecoles.services.publications;

import com.vieecoles.dto.publicationDto;
import com.vieecoles.entities.matiere;
import com.vieecoles.entities.niveau_etude_has_publication;
import com.vieecoles.entities.publication;
import com.vieecoles.projection.publicationCandidatSelectDto;
import com.vieecoles.projection.publicationSelectDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class publicationService implements PanacheRepositoryBase<publication, Long> {
    @Inject
    EntityManager em;
@Transactional
public List<publicationSelectDto> getListPublication(Long idEcole){
    List<publicationSelectDto> publicationList = new ArrayList<>() ;
    try {
        System.out.println("entree1");
        return  publicationList  = em.createQuery("SELECT new com.vieecoles.projection.publicationSelectDto(o.id,o.code, o.libelle,o.date_creation,o.date_modification,e.ecoleclibelle,t.libelle, niv.niveau_etude_libelle ,niv.niveau_etudeid,nip.id,o.experience,o.lieu, o.date_limite,o.fonction_fonctionid,f.fonctionlibelle,t.id,o.statut)  from publication o ,ecole e , type_offre t , niveau_etude_has_publication nip , niveau_etude niv, fonction f where o.ecole_ecoleid = e.ecoleid and o.type_offre_id= t.id and e.ecoleid=:idEcole and o.id= nip.publication_id and nip.niveau_etude_niveau_etudeid= niv.niveau_etudeid and o.fonction_fonctionid= f.fonctionid ", publicationSelectDto.class )
                .setParameter("idEcole",idEcole)
                .getResultList();
    } catch (Exception e){
        System.out.println("entree2");
        return  null ;
    }
}

    @Transactional
    public List<publicationSelectDto> rechercherPublication(Long ListNiveau,Long idEcole ,
                                                            Long typeOfre,Long typeFonction
                                                       , int periode){

        LocalDate aDate = LocalDate.now() ;
        LocalDate dateLimite = aDate.minusDays(periode);
        System.out.print("DateCalculer "+dateLimite);

        List<publicationSelectDto> publicationList = new ArrayList<>() ;
        System.out.print("typeFonction "+typeFonction);
        System.out.print("ListNiveau "+ListNiveau);
        System.out.print("typeOfre "+typeOfre);
        System.out.print("typeFonction "+typeFonction);
        System.out.print("dateLimite "+dateLimite);
        if(periode==0) {
            try {
                System.out.println("entree1");
                return  publicationList  = em.createQuery("SELECT new com.vieecoles.projection.publicationSelectDto(o.id,o.code, o.libelle,o.date_creation,o.date_modification,e.ecoleclibelle,t.libelle, niv.niveau_etude_libelle ,niv.niveau_etudeid,nip.id,o.experience,o.lieu, o.date_limite,o.fonction_fonctionid,f.fonctionlibelle,t.id,o.statut)  from publication o ,ecole e , type_offre t , niveau_etude_has_publication nip , niveau_etude niv, fonction f where o.ecole_ecoleid = e.ecoleid and o.type_offre_id= t.id  and o.id= nip.publication_id and nip.niveau_etude_niveau_etudeid= niv.niveau_etudeid and o.fonction_fonctionid= f.fonctionid " +
                                " and o.statut=:statut and o.date_creation = :dateLimite ", publicationSelectDto.class )
                                   .setParameter("dateLimite",dateLimite)
                        .setParameter("statut","OUVERTE")
                        .getResultList();
            } catch (Exception e){
                System.out.println("entree2");
                return  null ;
            }
        } else {
            try {
                System.out.println("entree1");
                return  publicationList  = em.createQuery("SELECT new com.vieecoles.projection.publicationSelectDto(o.id,o.code, o.libelle,o.date_creation,o.date_modification,e.ecoleclibelle,t.libelle, niv.niveau_etude_libelle ,niv.niveau_etudeid,nip.id,o.experience,o.lieu, o.date_limite,o.fonction_fonctionid,f.fonctionlibelle,t.id,o.statut)  from publication o ,ecole e , type_offre t , niveau_etude_has_publication nip , niveau_etude niv, fonction f where o.ecole_ecoleid = e.ecoleid and o.type_offre_id= t.id  and o.id= nip.publication_id and nip.niveau_etude_niveau_etudeid= niv.niveau_etudeid and o.fonction_fonctionid= f.fonctionid " +
                                " and o.statut=:statut and ( e.ecoleid=:idEcole or niv.niveau_etudeid =:ListNiveau or t.id =:typeOfre or f.fonctionid =:typeFonction or  o.date_creation  <= :dateLimite)  ", publicationSelectDto.class )
                        .setParameter("idEcole",idEcole)
                        .setParameter("ListNiveau",ListNiveau)
                        .setParameter("typeOfre",typeOfre)
                        .setParameter("typeFonction",typeFonction)
                        .setParameter("dateLimite",dateLimite)
                        .setParameter("statut","OUVERTE")
                        .getResultList();
            } catch (Exception e){
                System.out.println("entree2");
                return  null ;
            }

        }

    }

    @Transactional
    public List<publicationSelectDto> getPublicationCandidat(Long candidat){
        List<publicationSelectDto> publicationList = new ArrayList<>() ;
        try {
            System.out.println("entree1");
            return  publicationList  = em.createQuery("SELECT new com.vieecoles.projection.publicationSelectDto(o.id,o.code, o.libelle,o.date_creation,o.date_modification,e.ecoleclibelle,t.libelle, niv.niveau_etude_libelle ,niv.niveau_etudeid,nip.id,o.experience,o.lieu, o.date_limite,o.fonction_fonctionid,f.fonctionlibelle,t.id,o.statut)  from publication o ,ecole e , type_offre t , niveau_etude_has_publication nip , niveau_etude niv, fonction f ,postuler p where o.ecole_ecoleid = e.ecoleid and o.type_offre_id= t.id  and o.id= nip.publication_id and nip.niveau_etude_niveau_etudeid= niv.niveau_etudeid and o.fonction_fonctionid= f.fonctionid and o.id= p.publication_id and p.sous_attent_personn_sous_attent_personnid=:candidat ", publicationSelectDto.class )
                    .setParameter("candidat",candidat)
                    .getResultList();
        } catch (Exception e){
            System.out.println("entree2");
            return  null ;
        }
    }

    @Transactional
    public List<publicationCandidatSelectDto> getListCandidatPostulerParEcole(Long idEcole,int periode){
        List<publicationCandidatSelectDto> publicationList = new ArrayList<>() ;
        LocalDate aDate = LocalDate.now() ;
        LocalDate dateLimite = aDate.minusDays(periode);
        System.out.print("DateCalculer "+dateLimite);
        if(periode==0) {
            try {
                System.out.println("entree1");
                return  publicationList  = em.createQuery("SELECT new com.vieecoles.projection.publicationCandidatSelectDto(o.id,o.code, o.libelle,o.date_creation,o.date_modification,e.ecoleclibelle,t.libelle, niv.niveau_etude_libelle ,niv.niveau_etudeid,nip.id,o.experience,o.lieu, o.date_limite,o.fonction_fonctionid,f.fonctionlibelle,s.sous_attent_personn_nom,s.sous_attent_personn_prenom)  from publication o ,ecole e , type_offre t , niveau_etude_has_publication nip , niveau_etude niv, fonction f ,postuler p ,sous_attent_personn s where o.ecole_ecoleid = e.ecoleid and o.type_offre_id= t.id  and o.id= nip.publication_id and nip.niveau_etude_niveau_etudeid= niv.niveau_etudeid and o.fonction_fonctionid= f.fonctionid and o.id= p.publication_id  and p.sous_attent_personn_sous_attent_personnid =s.sous_attent_personnid and e.ecoleid=:idEcole and o.date_creation = :dateLimite ", publicationCandidatSelectDto.class )
                        .setParameter("idEcole",idEcole)
                        .setParameter("dateLimite",dateLimite)
                        .getResultList();
            } catch (Exception e){
                System.out.println("entree2");
                return  null ;
            }
        } else {
            try {
                System.out.println("entree1");
                return  publicationList  = em.createQuery("SELECT new com.vieecoles.projection.publicationCandidatSelectDto(o.id,o.code, o.libelle,o.date_creation,o.date_modification,e.ecoleclibelle,t.libelle, niv.niveau_etude_libelle ,niv.niveau_etudeid,nip.id,o.experience,o.lieu, o.date_limite,o.fonction_fonctionid,f.fonctionlibelle,s.sous_attent_personn_nom,s.sous_attent_personn_prenom)  from publication o ,ecole e , type_offre t , niveau_etude_has_publication nip , niveau_etude niv, fonction f ,postuler p ,sous_attent_personn s where o.ecole_ecoleid = e.ecoleid and o.type_offre_id= t.id  and o.id= nip.publication_id and nip.niveau_etude_niveau_etudeid= niv.niveau_etudeid and o.fonction_fonctionid= f.fonctionid and o.id= p.publication_id  and p.sous_attent_personn_sous_attent_personnid =s.sous_attent_personnid and e.ecoleid=:idEcole and o.date_creation <= :dateLimite ", publicationCandidatSelectDto.class )
                        .setParameter("idEcole",idEcole)
                        .setParameter("dateLimite",dateLimite)
                        .getResultList();
            } catch (Exception e){
                System.out.println("entree2");
                return  null ;
            }
        }

    }


@Transactional
    public void creerUnePublicationFin(publicationDto myPub){
        publication pub= new publication() ;
        pub.setCode(myPub.getCode());
        pub.setLibelle(myPub.getLibelle() );
        pub.setDate_creation(LocalDate.now());
        pub.setType_offre_id(myPub.getType_offre_id());
        pub.setEcole_ecoleid(myPub.getEcole_ecoleid());
        pub.setFonction_fonctionid(myPub.getFonction_fonctionid());
        pub.setDate_limite(myPub.getDate_limite());
        pub.setExperience(myPub.getExperience());
        pub.setLieu(myPub.getLieu());
        pub.setStatut("OUVERTE");
        Long pubId ;
        pubId= creerPub(pub) ;
        creerNiveauAndPub(pubId,myPub.getNiveau_id()) ;

     }
@Transactional
     public long creerPub(publication pub) {
      pub.persist();
      return  pub.getId() ;
     }
     @Transactional
    public void creerNiveauAndPub(Long pubId,Long niveauId) {
        niveau_etude_has_publication nivP = new niveau_etude_has_publication() ;
        nivP.setPublication_id(pubId);
        nivP.setNiveau_etude_niveau_etudeid(niveauId);

         nivP.persist(); ;
    }

    public Long  modifierUnePublication(publicationDto myPub ) {
        publication pub = new publication() ;
        pub= publication.findById(myPub.getId()) ;
        System.out.print("Pub à Modifier:"+pub.toString());
        pub.setCode(myPub.getCode());
        pub.setDate_creation(LocalDate.now());
        pub.setEcole_ecoleid(myPub.getEcole_ecoleid());
        pub.setType_offre_id(myPub.getType_offre_id());
        pub.setLibelle(myPub.getLibelle());
        pub.setFonction_fonctionid(myPub.getFonction_fonctionid());
        pub.setDate_limite(myPub.getDate_limite());
        pub.setExperience(myPub.getExperience());
        pub.setLieu(myPub.getLieu());
        pub.setStatut("OUVERTE");
        modifierNiveauAndPub(myPub.getIdNivPub(),myPub.getId(),myPub.getNiveau_id()) ;
       return  pub.getId() ;
    }

    public Long  fermerOfrre(Long myPub ) {
        publication pub = new publication() ;
        pub= publication.findById(myPub) ;
        System.out.print("Pub à Modifier:"+pub.toString());
        pub.setStatut("FERMEE");
        return  pub.getId() ;
    }


    @Transactional
    public niveau_etude_has_publication modifierNiveauAndPub(Long nivPub ,Long pubId, Long nivId) {
        niveau_etude_has_publication nivP = new niveau_etude_has_publication() ;
        nivP = niveau_etude_has_publication.findById(nivPub) ;
        nivP.setPublication_id(pubId);
        nivP.setNiveau_etude_niveau_etudeid(nivId);
      return  nivP ;
    }


    public void  supprimerUnePublication(Long idPub) {
        publication pub = new publication() ;
        pub= publication.findById(idPub) ;
        pub.delete();
    }




}
