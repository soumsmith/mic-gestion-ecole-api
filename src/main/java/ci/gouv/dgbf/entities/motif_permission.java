package ci.gouv.dgbf.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class motif_permission extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  motif_permissionid ;
    private  String motif_permissioncode;
    private  String motif_permissionlibelle;

    public Long getMotif_permissionid() {
        return motif_permissionid;
    }

    public void setMotif_permissionid(Long motif_permissionid) {
        this.motif_permissionid = motif_permissionid;
    }

    public String getMotif_permissioncode() {
        return motif_permissioncode;
    }

    public void setMotif_permissioncode(String motif_permissioncode) {
        this.motif_permissioncode = motif_permissioncode;
    }

    public String getMotif_permissionlibelle() {
        return motif_permissionlibelle;
    }

    public void setMotif_permissionlibelle(String motif_permissionlibelle) {
        this.motif_permissionlibelle = motif_permissionlibelle;
    }
}
