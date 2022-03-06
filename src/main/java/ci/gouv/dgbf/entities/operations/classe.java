package ci.gouv.dgbf.entities.operations;

import ci.gouv.dgbf.entities.niveau;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class classe extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  classeid ;
    private  String classecode;
    private  String classelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_niveauid")
    private ci.gouv.dgbf.entities.niveau niveau ;

    public classe() {
    }

    public Long getClasseid() {
        return classeid;
    }

    public void setClasseid(Long classeid) {
        this.classeid = classeid;
    }

    public String getClassecode() {
        return classecode;
    }

    public void setClassecode(String classecode) {
        this.classecode = classecode;
    }

    public String getClasselibelle() {
        return classelibelle;
    }

    public void setClasselibelle(String classelibelle) {
        this.classelibelle = classelibelle;
    }

    public ci.gouv.dgbf.entities.niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(ci.gouv.dgbf.entities.niveau niveau) {
        this.niveau = niveau;
    }
}
