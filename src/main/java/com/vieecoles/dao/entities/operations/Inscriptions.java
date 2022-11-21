package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.Annee_scolaire;
import com.vieecoles.dao.entities.Branche;
import com.vieecoles.dao.entities.Libellehandicap;
import com.vieecoles.dao.entities.Niveau;
import com.vieecoles.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inscriptions")
public class Inscriptions extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   inscriptionsid ;
   private String  inscriptionscode ;
   private String inscriptions_ecole_origine ;
   private  String   inscriptions_classe_precedente ;
   private  String    inscriptions_derniereclasse_religieuse ;
   private  String   inscriptions_classe_actuelle ;
    private  String   inscriptions_redoublant ;
    private  String   inscriptions_boursier ;
    @Enumerated(EnumType.STRING)
    private  statusEleve   inscriptions_statut_eleve ;
    @Enumerated(EnumType.STRING)
    private  typeOperation   inscriptions_type ;
   private  int    inscriptions_delete ;
    @Enumerated(EnumType.STRING)
   private status inscriptions_status ;
   private LocalDate inscriptionsdate_creation ;
   private LocalDate inscriptionsdate_modification ;
   private  String tenant_tenantid ;
    @Enumerated(EnumType.STRING)
   private processus inscriptions_processus ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleve_eleveid")
    private eleve eleve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_annee_scolaireid")
    private Annee_scolaire annee_scolaire ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_niveauid")
    private Niveau myNiveau ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecole_ecoleid")
    private ecole ecole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Branche_id")
    private Branche branche;
    @ManyToMany
    @JoinTable( name = "libellehandicap_inscriptions",
            joinColumns = @JoinColumn( name = "inscriptions_inscriptionsid" ),
            inverseJoinColumns = @JoinColumn( name = "libelleHandicap_libelleHandicapid" ) )
    private List<Libellehandicap> libellehandicap = new ArrayList<>();


    public enum processus{
        EN_COURS, TERMINE
    }
    public enum status{
        VALIDEE, EN_ATTENTE
    }
    public enum statusEleve{
        AFFECTE, NON_AFFECTE
    }
    public enum typeOperation{
        INSCRIPTION, PREINSCRIPTION
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public String getTenant_tenantid() {
        return tenant_tenantid;
    }

    public void setTenant_tenantid(String tenant_tenantid) {
        this.tenant_tenantid = tenant_tenantid;
    }

    public Niveau getMyNiveau() {
        return myNiveau;
    }

    public void setMyNiveau(Niveau myNiveau) {
        this.myNiveau = myNiveau;
    }



    public Annee_scolaire getAnnee_scolaire() {
        return annee_scolaire;
    }

    public void setAnnee_scolaire(Annee_scolaire annee_scolaire) {
        this.annee_scolaire = annee_scolaire;
    }

    public String getInscriptions_redoublant() {
        return inscriptions_redoublant;
    }

    public void setInscriptions_redoublant(String inscriptions_redoublant) {
        this.inscriptions_redoublant = inscriptions_redoublant;
    }

    public String getInscriptions_boursier() {
        return inscriptions_boursier;
    }

    public void setInscriptions_boursier(String inscriptions_boursier) {
        this.inscriptions_boursier = inscriptions_boursier;
    }

    public processus getInscriptions_processus() {
        return inscriptions_processus;
    }

    public void setInscriptions_processus(processus inscriptions_processus) {
        this.inscriptions_processus = inscriptions_processus;
    }

    public statusEleve getInscriptions_statut_eleve() {
        return inscriptions_statut_eleve;
    }

    public void setInscriptions_statut_eleve(statusEleve inscriptions_statut_eleve) {
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
    }

    public typeOperation getInscriptions_type() {
        return inscriptions_type;
    }

    public void setInscriptions_type(typeOperation inscriptions_type) {
        this.inscriptions_type = inscriptions_type;
    }

    public Inscriptions() {
    }

    public int getInscriptions_delete() {
        return inscriptions_delete;
    }

    public void setInscriptions_delete(int inscriptions_delete) {
        this.inscriptions_delete = inscriptions_delete;
    }

    public com.vieecoles.dao.entities.operations.ecole getEcole() {
        return ecole;
    }

    public void setEcole(com.vieecoles.dao.entities.operations.ecole ecole) {
        this.ecole = ecole;
    }

    public List<Libellehandicap> getLibellehandicap() {
        return libellehandicap;
    }

    public void setLibellehandicap(List<Libellehandicap> libellehandicap) {
        this.libellehandicap = libellehandicap;
    }

    public Long getInscriptionsid() {
        return inscriptionsid;
    }



    public String getInscriptionscode() {
        return inscriptionscode;
    }

    public void setInscriptionscode(String inscriptionscode) {
        this.inscriptionscode = inscriptionscode;
    }

    public String getInscriptions_ecole_origine() {
        return inscriptions_ecole_origine;
    }

    public void setInscriptions_ecole_origine(String inscriptions_ecole_origine) {
        this.inscriptions_ecole_origine = inscriptions_ecole_origine;
    }

    public String getInscriptions_classe_precedente() {
        return inscriptions_classe_precedente;
    }

    public void setInscriptions_classe_precedente(String inscriptions_classe_precedente) {
        this.inscriptions_classe_precedente = inscriptions_classe_precedente;
    }

    public String getInscriptions_derniereclasse_religieuse() {
        return inscriptions_derniereclasse_religieuse;
    }

    public void setInscriptions_derniereclasse_religieuse(String inscriptions_derniereclasse_religieuse) {
        this.inscriptions_derniereclasse_religieuse = inscriptions_derniereclasse_religieuse;
    }

    public String getInscriptions_classe_actuelle() {
        return inscriptions_classe_actuelle;
    }

    public void setInscriptions_classe_actuelle(String inscriptions_classe_actuelle) {
        this.inscriptions_classe_actuelle = inscriptions_classe_actuelle;
    }

    public void setInscriptionsid(Long inscriptionsid) {
        this.inscriptionsid = inscriptionsid;
    }

    public status getInscriptions_status() {
        return inscriptions_status;
    }

    public void setInscriptions_status(status inscriptions_status) {
        this.inscriptions_status = inscriptions_status;
    }

    public LocalDate getInscriptionsdate_creation() {
        return inscriptionsdate_creation;
    }

    public void setInscriptionsdate_creation(LocalDate inscriptionsdate_creation) {
        this.inscriptionsdate_creation = inscriptionsdate_creation;
    }

    public LocalDate getInscriptionsdate_modification() {
        return inscriptionsdate_modification;
    }

    public void setInscriptionsdate_modification(LocalDate inscriptionsdate_modification) {
        this.inscriptionsdate_modification = inscriptionsdate_modification;
    }

    public com.vieecoles.dao.entities.operations.eleve getEleve() {
        return eleve;
    }

    public void setEleve(com.vieecoles.dao.entities.operations.eleve eleve) {
        this.eleve = eleve;
    }


    @Override
    public String toString() {
        return "Inscriptions{" +
                "inscriptionsid=" + inscriptionsid +
                ", inscriptionscode='" + inscriptionscode + '\'' +
                ", inscriptions_ecole_origine='" + inscriptions_ecole_origine + '\'' +
                ", inscriptions_classe_precedente='" + inscriptions_classe_precedente + '\'' +
                ", inscriptions_derniereclasse_religieuse='" + inscriptions_derniereclasse_religieuse + '\'' +
                ", inscriptions_classe_actuelle='" + inscriptions_classe_actuelle + '\'' +
                ", inscriptions_delete=" + inscriptions_delete +
                ", inscriptions_status=" + inscriptions_status +
                ", inscriptionsdate_creation=" + inscriptionsdate_creation +
                ", inscriptionsdate_modification=" + inscriptionsdate_modification +
                ", eleve=" + eleve +
                ", ecole=" + ecole +
                ", libellehandicap=" + libellehandicap +
                '}';
    }


}
