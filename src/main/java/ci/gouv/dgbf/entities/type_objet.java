package ci.gouv.dgbf.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class type_objet extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  type_objetid ;
    private  String type_objetcode;
    private  String type_objetlibelle;

    public Long getType_objetid() {
        return type_objetid;
    }

    public void setType_objetid(Long type_objetid) {
        this.type_objetid = type_objetid;
    }

    public String getType_objetcode() {
        return type_objetcode;
    }

    public void setType_objetcode(String type_objetcode) {
        this.type_objetcode = type_objetcode;
    }

    public String getType_objetlibelle() {
        return type_objetlibelle;
    }

    public void setType_objetlibelle(String type_objetlibelle) {
        this.type_objetlibelle = type_objetlibelle;
    }
}
