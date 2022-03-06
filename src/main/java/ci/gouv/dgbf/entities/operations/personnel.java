package ci.gouv.dgbf.entities.operations;

import ci.gouv.dgbf.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class personnel extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  personnelid ;
    private  String personnelcode;
    private  String personnelnom;
    private  String personnelprenom;
    private  String personneldatenaissance;
    private  String personnel_lieunaissance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_personnel_type_personnelid")
    private type_personnel type_personnel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_status_personnel_statusid")
    private personnel_status personnel_status;


    @ManyToMany
    @JoinTable( name = "personnel_classe",
            joinColumns = @JoinColumn( name = "personnel_personnelid" ),
            inverseJoinColumns = @JoinColumn( name = "classe_classeid" ) )
    private List<classe> classe = new ArrayList<>();



    @ManyToMany
    @JoinTable( name = "personnel_fonction",
            joinColumns = @JoinColumn( name = "personnel_personnelid" ),
            inverseJoinColumns = @JoinColumn( name = "fonction_fonctionid" ) )
    private List<ci.gouv.dgbf.entities.fonction> fonction = new ArrayList<>();


    @ManyToMany
    @JoinTable( name = "personnel_objectif_personnel",
            joinColumns = @JoinColumn( name = "personnel_personnelid" ),
            inverseJoinColumns = @JoinColumn( name = "objectif_personnel_objectif_personnelid" ) )
    private List<objectif_personnel> objectif_personnels = new ArrayList<>();


    @ManyToMany
    @JoinTable( name = "personnel_objet",
            joinColumns = @JoinColumn( name = "personnel_personnelid" ),
            inverseJoinColumns = @JoinColumn( name = "objet_objetid" ) )
    private List<objet> objet = new ArrayList<>();



}
