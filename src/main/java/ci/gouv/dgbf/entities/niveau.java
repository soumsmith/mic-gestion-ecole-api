package ci.gouv.dgbf.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class niveau extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  niveauid ;
    private  String niveaucode;
    private  String niveaulibelle;

    public long getNiveauid() {
        return niveauid;
    }

    public void setNiveauid(long niveauid) {
        this.niveauid = niveauid;
    }

    public String getNiveaucode() {
        return niveaucode;
    }

    public void setNiveaucode(String niveaucode) {
        this.niveaucode = niveaucode;
    }

    public String getNiveaulibelle() {
        return niveaulibelle;
    }

    public void setNiveaulibelle(String niveaulibelle) {
        this.niveaulibelle = niveaulibelle;
    }
}
