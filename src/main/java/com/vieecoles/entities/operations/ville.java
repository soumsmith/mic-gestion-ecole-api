package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class ville extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  villeid ;
    private  String villecode;
    private  String villelibelle;
    //private Long Direction_regionale_id ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Direction_regionale_id")
    private com.vieecoles.entities.Direction_regionale myDirection_regionale ;

    public ville() {
    }

    public Long getVilleid() {
        return villeid;
    }

    public void setVilleid(Long villeid) {
        this.villeid = villeid;
    }

    public String getVillecode() {
        return villecode;
    }

    public void setVillecode(String villecode) {
        this.villecode = villecode;
    }

    public String getVillelibelle() {
        return villelibelle;
    }

    public void setVillelibelle(String villelibelle) {
        this.villelibelle = villelibelle;
    }

   
    /**
     * @return Long return the Direction_regionale_id
     */
    


    /**
     * @return com.vieecoles.entities.Direction_regionale return the myDirection_regionale
     */
    public com.vieecoles.entities.Direction_regionale getMyDirection_regionale() {
        return myDirection_regionale;
    }

    /**
     * @param myDirection_regionale the myDirection_regionale to set
     */
    public void setMyDirection_regionale(com.vieecoles.entities.Direction_regionale myDirection_regionale) {
        this.myDirection_regionale = myDirection_regionale;
    }

}
