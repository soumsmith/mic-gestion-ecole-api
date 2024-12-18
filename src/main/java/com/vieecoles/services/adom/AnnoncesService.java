package com.vieecoles.services.adom;

import com.vieecoles.dto.Adom.AnnonceDto;
import com.vieecoles.entities.adom.Annonce;
import com.vieecoles.entities.domaine;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.net.URI;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class AnnoncesService implements PanacheRepositoryBase<Annonce, Long> {

  public void create(AnnonceDto annonce) {
    Annonce ann = new Annonce();
    ann.setCode(annonce.getCode());
    ann.setNumero(annonce.getNumero());
    ann.setTitre(annonce.getTitre());
    ann.setInformationCours(annonce.getInformationCours());
    ann.setCommentaireSurVous(annonce.getCommentaireSurVous());
    ann.setTarif(annonce.getTarif());
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
