package com.vieecoles.services.souscription;

import java.time.LocalDate;
import java.util.List;

import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.entities.operations.message_personnel;
import com.vieecoles.projection.MessageSelect;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MessagePersonnelService implements PanacheRepositoryBase<message_personnel, Long> {
    @Inject
    EntityManager em;






@Transactional
  public  String buildMessage( message_personnelDto messageDto){
      String messageRetour ;
      message_personnel message= new message_personnel() ;
      message.setMessage_personnel_message(messageDto.getMessage_personnel_message());
      message.setMessage_personnel_date(LocalDate.now());
      message.setMessage_personnel_sujet(messageDto.getMessage_personnel_sujet());
      message.setIdemetteur(messageDto.getIdEmetteur());
      System.out.println("messageDto.getIdEmetteur() "+messageDto.getIdEmetteur());
      message.setIdrecepteur(messageDto.getIdDestinataire());
      System.out.println("messageDto.getIdDestinataire() "+messageDto.getIdDestinataire());
      message.persist();
      messageRetour="MESSAGE ENVOYE AVEC SUCCES" ;
      return  messageRetour ;
  }
public long getGlobalIdUser(Long idPanier){
    Long idUser;
    Long idPersonnel;
    idPersonnel=getIdPersonnelByPanier(idPanier);
//    System.out.println("idPersonnel "+idPersonnel);

    idUser= getIdUtilisateurByPersonnel(idPersonnel);
//    System.out.println("idUser "+idUser);
    return idUser ;
}
    @Transactional
    public long  getIdUtilisateurByPersonnel(Long idPersonnel){
        Long IdUtilisateur = null;
        try {
            IdUtilisateur= (Long) em.createQuery("select o.utilisateurid from utilisateur  o  where  o.sous_attent_personn_sous_attent_personnid =:idPersonnel ")
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
            System.out.println("MessagePersonnelService.getIdUtilisateurByPersonnel()");
            System.out.println("IdUtilisateur "+IdUtilisateur);
        } catch (Exception e) {
            IdUtilisateur = 0L;
        }
        return IdUtilisateur ;
    }
@Transactional
    public long  getIdPersonnelByPanier(Long idPanier){
        Long idPersonnel = null;
        try {
            idPersonnel= (Long) em.createQuery("select o.sous_attent_personn.sous_attent_personnid from panier_personnel  o  where  o.idpanier_personnel_id =:idPanier ")
                    .setParameter("idPanier",idPanier)
                    .getSingleResult();
        } catch (Exception e) {
            idPersonnel = 0L;
        }
        return idPersonnel ;
    }



 public List<MessageSelect> messageEmetteur(Long idEmetteur){
        List<MessageSelect> mesPaniers  ;
        try {
            mesPaniers= (List<MessageSelect>) em.createQuery("SELECT new com.vieecoles.projection.MessageSelect(o.message_personnel_id,o.message_personnel_sujet, o.message_personnel_message,o.message_personnel_date,concat(p.sous_attent_personn_nom,' ',p.sous_attent_personn_prenom)) from message_personnel o , utilisateur u , sous_attent_personn  p " +
                                    "where  o.idrecepteur=u.utilisateurid and u.sous_attent_personn_sous_attent_personnid = p.sous_attent_personnid and o.idemetteur=:idEmetteur  ")
                    .setParameter("idEmetteur",idEmetteur)
                    .getResultList();
        } catch (Exception e) {
            mesPaniers=  null;
        }
        System.out.println("mesPaniersxx"+ mesPaniers);
        return mesPaniers ;
    }


    public List<MessageSelect> messageRecu(Long idrecu){
        List<MessageSelect> mesPaniers  ;
        try {
            mesPaniers= (List<MessageSelect>) em.createQuery("SELECT new com.vieecoles.projection.MessageSelect(o.message_personnel_id,o.message_personnel_sujet, o.message_personnel_message,o.message_personnel_date,concat(p.sous_attent_personn_nom,' ',p.sous_attent_personn_prenom) ) from message_personnel o , utilisateur u , sous_attent_personn  p " +
                            "where  o.idemetteur=u.utilisateurid and u.sous_attent_personn_sous_attent_personnid = p.sous_attent_personnid and o.idrecepteur=:idrecu  ")
                    .setParameter("idrecu",idrecu)
                    .getResultList();
        } catch (Exception e) {
            mesPaniers=  null;
        }
//        System.out.println("mesPaniersxx"+ mesPaniers);
        return mesPaniers ;
    }









    @Transactional
    public void    deleteMessage(Long identifiant) {
        message_personnel message = new message_personnel() ;
        message = message_personnel.findById(identifiant) ;

        try{
            message.delete();
        }catch (Exception e) {

        }

    }




}
