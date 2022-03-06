package ci.gouv.dgbf.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class matiere extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  matiereid ;
    private  String matierecode;
    private  String matierelibelle;
    private Long  matierecoefficien ;


    public Long getMatiereid() {
        return matiereid;
    }

    public void setMatiereid(Long matiereid) {
        this.matiereid = matiereid;
    }

    public String getMatierecode() {
        return matierecode;
    }

    public void setMatierecode(String matierecode) {
        this.matierecode = matierecode;
    }

    public String getMatierelibelle() {
        return matierelibelle;
    }

    public void setMatierelibelle(String matierelibelle) {
        this.matierelibelle = matierelibelle;
    }

    public Long getMatierecoefficien() {
        return matierecoefficien;
    }

    public void setMatierecoefficien(Long matierecoefficien) {
        this.matierecoefficien = matierecoefficien;
    }
}
