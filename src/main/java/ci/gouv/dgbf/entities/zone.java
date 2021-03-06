package ci.gouv.dgbf.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class zone extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  zoneid ;
    private  String zonecode;
    private  String zonelibelle;


    public Long getZoneid() {
        return zoneid;
    }

    public void setZoneid(Long zoneid) {
        this.zoneid = zoneid;
    }

    public String getZonecode() {
        return zonecode;
    }

    public void setZonecode(String zonecode) {
        this.zonecode = zonecode;
    }

    public String getZonelibelle() {
        return zonelibelle;
    }

    public void setZonelibelle(String zonelibelle) {
        this.zonelibelle = zonelibelle;
    }
}
