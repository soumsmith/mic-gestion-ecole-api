package com.vieecoles.services.adom;

import com.vieecoles.dto.Adom.AnnonceDto;
import com.vieecoles.dto.Adom.AnnonceParentDto;
import com.vieecoles.entities.adom.Annonce;
import com.vieecoles.entities.adom.AnnonceParent;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SinterresserAnnonceService implements PanacheRepositoryBase<Annonce, Long> {

  public void createInterresser(AnnonceParentDto annonceParentDto) {
    AnnonceParent ann = new AnnonceParent();
//    ann.setCode(annonce.getCode());
//    ann.setNumero(annonce.getNumero());
//    ann.setTitre(annonce.getTitre());
//    ann.setInformationCours(annonce.getInformationCours());
//    ann.setCommentaireSurVous(annonce.getCommentaireSurVous());
//    ann.setTarif(annonce.getTarif());
    ann.persist();
  }

  public void modifier(AnnonceDto annonce, Long id) {
    Annonce ann = new Annonce();
    ann = Annonce.findById(id);
    ann.setCode(annonce.getCode());
    ann.setNumero(annonce.getNumero());
    ann.setTitre(annonce.getTitre());
    ann.setInformationCours(annonce.getInformationCours());
    ann.setCommentaireSurVous(annonce.getCommentaireSurVous());
    ann.setTarif(annonce.getTarif());
  }
  public void delete(Long id) {
    Annonce ann = new Annonce();
    ann = Annonce.findById(id);
    ann.delete();
  }

}
