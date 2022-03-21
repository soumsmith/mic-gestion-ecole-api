package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class categorie_information extends PanacheEntityBase {
 @Id @GeneratedValue
   private  Integer categorie_informationid ;
  private  String  categorie_informationcode ;
  private String  categorie_informationlibelle ;



    public categorie_information() {
    }

    public Integer getCategorie_informationid() {
        return categorie_informationid;
    }

    public void setCategorie_informationid(Integer categorie_informationid) {
        this.categorie_informationid = categorie_informationid;
    }

    public String getCategorie_informationcode() {
        return categorie_informationcode;
    }

    public void setCategorie_informationcode(String categorie_informationcode) {
        this.categorie_informationcode = categorie_informationcode;
    }

    public String getCategorie_informationlibelle() {
        return categorie_informationlibelle;
    }

    public void setCategorie_informationlibelle(String categorie_informationlibelle) {
        this.categorie_informationlibelle = categorie_informationlibelle;
    }
}
