package com.vieecoles.Conduite.repository;

import static java.util.Collections.list;

import com.vieecoles.Conduite.Constants;
import com.vieecoles.Conduite.entites.Infraction;
import com.vieecoles.steph.entities.Eleve;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.time.LocalDate;
import java.util.List;

public class InfractionRepository implements PanacheRepository<Infraction> {
  public List<Infraction> findByEleve(Eleve eleve) {
    return list("eleve", eleve);
  }

  public List<Infraction> findByEleveAndCategorie(Eleve eleve, Constants.CategorieInfraction categorie) {
    return list("eleve = ?1 and categorie = ?2", eleve, categorie);
  }

  public List<Infraction> findByDateBetween(LocalDate debut, LocalDate fin) {
    return list("date >= ?1 and date <= ?2", debut, fin);
  }
}
