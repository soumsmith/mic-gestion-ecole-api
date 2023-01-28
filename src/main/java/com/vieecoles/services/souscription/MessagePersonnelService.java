package com.vieecoles.services.souscription;

import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.message_personnel;
import com.vieecoles.entities.operations.sous_attent_personn;
import com.vieecoles.projection.MessageSelect;
import com.vieecoles.projection.panier_personnelSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MessagePersonnelService implements PanacheRepositoryBase<message_personnel, Long> {
    @Inject
    EntityManager em;

    @Transactional
  public String  creeerMessage(message_personnelDto messageDto) {
        ecole myecole= new ecole() ;
        String messageRetour ;
        sous_attent_personn myperson= new sous_attent_personn() ;
        message_personnel message= new message_personnel() ;
        myperson = sous_attent_personn.findById(messageDto.getIdentifiant_personnel()) ;
        message.setMessage_personnel_message(messageDto.getMessage_personnel_message());
        message.setMessage_personnel_date(LocalDate.now());
        message.setSous_attent_personn(myperson);
        message.setMessage_personnel_sujet(messageDto.getMessage_personnel_sujet());
        message.setMessage_personnel_emetteur(messageDto.getMessage_personnel_emetteur());
        message.setEcole_ecoleid(messageDto.getEcole_ecoleid());
        message.setAdministrateur_gain_idadministrateur_gain(messageDto.getAdministrateur_gain_idadministrateur_gain());
        message.persist();
      messageRetour="MESSAGE ENVOYE AVEC SUCCES" ;
      return messageRetour ;
  }

    public  List<message_personnel> getAllMessageByEcole(Long id_ecole){
        try {
            return (List<message_personnel>) em.createQuery("select o from  message_personnel  o where o.ecole_ecoleid  =:id_ecole ")
                    .setParameter("id_ecole",id_ecole)
                    .getResultList() ;
        } catch (Exception e) {
            return  null;
        }
    }

    public List<MessageSelect> messageByEcole(Long idEcole){
        List<MessageSelect> mesPaniers  ;
        try {
            mesPaniers= (List<MessageSelect>) em.createQuery("SELECT new com.vieecoles.projection.MessageSelect(o.message_personnel_id,o.message_personnel_emetteur,o.message_personnel_sujet, o.message_personnel_message,o.message_personnel_date,p.sous_attent_personn_nom,p.sous_attent_personn_prenom,concat(p.sous_attent_personn_nom,' ',p.sous_attent_personn_prenom) ) from message_personnel o join" +
                            " o.sous_attent_personn p where o.ecole_ecoleid=:idEcole ")
                    .setParameter("idEcole",idEcole)
                    .getResultList();
        } catch (Exception e) {
            mesPaniers=  null;
        }
        System.out.println("mesPaniersxx"+ mesPaniers);
        return mesPaniers ;
    }




    public List<MessageSelect> messageByUser(Long idPersonnel){
        List<MessageSelect> mesPaniers  ;
        try {
            mesPaniers= (List<MessageSelect>) em.createQuery("SELECT new com.vieecoles.projection.MessageSelect(o.message_personnel_id,o.message_personnel_emetteur,o.message_personnel_sujet, o.message_personnel_message,o.message_personnel_date,p.sous_attent_personn_nom,p.sous_attent_personn_prenom ,concat(p.sous_attent_personn_nom,' ',p.sous_attent_personn_prenom)) from message_personnel o join" +
                            " o.sous_attent_personn p where p.sous_attent_personnid=:idPersonnel ")
                                  .setParameter("idPersonnel",idPersonnel)
                    .getResultList();
        } catch (Exception e) {
            mesPaniers=  null;
        }
        System.out.println("mesPaniersxx"+ mesPaniers);
        return mesPaniers ;
    }




    public  List<message_personnel> getAllMessageByPersonnel(Long idPersonnel){
        try {
            return (List<message_personnel>) em.createQuery("select o from  message_personnel  o where o.sous_attent_personn.sous_attent_personnid  =:idPersonnel")
                    .setParameter("idPersonnel",idPersonnel)
                    .getResultList() ;
        } catch (Exception e) {
            return  null;
        }
    }

    public  List<message_personnel> getAllMessageByAdministrateur(Long idAdministrate){
        try {
            return (List<message_personnel>) em.createQuery("select o from  message_personnel  o where o.administrateur_gain_idadministrateur_gain  =:idAdministrate ")
                    .setParameter("idAdministrate",idAdministrate)
                    .getResultList() ;
        } catch (Exception e) {
            return  null;
        }
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
