package com.vieecoles.Conduite.entites;

import com.vieecoles.Conduite.Constants;
import com.vieecoles.steph.entities.Eleve;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Infraction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Constants.CategorieInfraction categorie;

  @Enumerated(EnumType.STRING)
  private Constants.TypeInfraction type;

  private String description;
  private LocalDate date;
  private float pointsRetires;

  @ManyToOne
  @JoinColumn(name = "eleveid")
  private Eleve eleve;

  // Constructeurs, getters et setters
  public Infraction() {}

  public Infraction(Constants.CategorieInfraction categorie, Constants.TypeInfraction type, String description, float pointsRetires) {
    this.categorie = categorie;
    this.type = type;
    this.description = description;
    this.date = LocalDate.now();
    this.pointsRetires = pointsRetires;
  }
}
