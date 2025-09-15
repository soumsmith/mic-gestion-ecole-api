package com.vieecoles.services.souscription;

import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.entities.operations.acteur_messages;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.message_personnel;
import com.vieecoles.entities.operations.sous_attent_personn;
import com.vieecoles.projection.MessageSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MessageActeurService implements PanacheRepositoryBase<acteur_messages, Long> {
    @Inject
    EntityManager em;

    @Transactional
  public String  creeerMessage(message_personnelDto messageDto) {
        ecole myecole= new ecole() ;
        String messageRetour ;
        sous_attent_personn myperson= new sous_attent_personn() ;
        message_personnel message= new message_personnel() ;
      //  myperson = sous_attent_personn.findById(messageDto.getIdentifiant_personnel()) ;
        message.setMessage_personnel_message(messageDto.getMessage_personnel_message());
        message.setMessage_personnel_date(LocalDate.now());

        message.persist();
      messageRetour="MESSAGE ENVOYE AVEC SUCCES" ;
      return messageRetour ;
  }



    public List<MessageSelect> getMessageEnvoyes(Long idSouscrip){
        List<MessageSelect> mesPaniers  ;
        try {
            mesPaniers= (List<MessageSelect>) em.createQuery("SELECT new com.vieecoles.projection.MessageSelect(o.message_personnel_id,o.message_personnel_sujet, o.message_personnel_message,o.message_personnel_date,concat(p.sous_attent_personn_nom,' ',p.sous_attent_personn_prenom) ) from message_personnel o ,acteur_messages" +
                            " a , sous_attent_personn  p , ecole  e where  o.message_personnel_id = a.message_personnel_message_personnel_id and a.sous_attent_personn_sous_attent_personnid= p.sous_attent_personnid and p.sous_attent_personnid=:idSouscrip")
                    .setParameter("idSouscrip",idSouscrip)
                    .getResultList();
        } catch (Exception e) {
            mesPaniers=  null;
        }
        //System.out.println("mesPaniersxx"+ mesPaniers);
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
