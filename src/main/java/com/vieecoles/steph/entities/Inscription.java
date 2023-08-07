package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;


@NamedNativeQueries({
    @NamedNativeQuery(
            name = "Inscription.getNewEleveCount",
            query = "select count(c.nbreOcc) from ( "
            		+ "SELECT COUNT(ins.eleve_eleveid) as nbreOcc, ins.eleve_eleveid  FROM inscriptions ins"
            		+ "  where ins.ecole_ecoleid = :ecoleId "
            		+ "GROUP BY ins.eleve_eleveid, ins.eleve_eleveid) c "
            		+ "where c.nbreOcc = 1"
    ),@NamedNativeQuery(
            name = "Inscription.getNewEleveCountBySexe",
            query = "select count(c.nbreOcc) from ( "
            		+ "SELECT COUNT(ins.eleve_eleveid) as nbreOcc, ins.eleve_eleveid  FROM inscriptions ins left join eleve elv on ins.eleve_eleveid = elv.eleveid "
            		+ "  where ins.ecole_ecoleid = :ecoleId and  elv.eleve_sexe = :sexe"
            		+ "GROUP BY ins.eleve_eleveid, ins.eleve_eleveid) c "
            		+ "where c.nbreOcc = 1)"
    ),@NamedNativeQuery(
            name = "Inscription.getNewEleveCountBySexeAndAffecte",
            query = "select count(c.nbreOcc) from ( "
            		+ " SELECT COUNT(ins.eleve_eleveid) as nbreOcc, ins.eleve_eleveid  FROM inscriptions ins left join eleve elv on ins.eleve_eleveid = elv.eleveid "
            		+ " where ins.ecole_ecoleid = :ecoleId and  elv.eleve_sexe = :sexe AND ins.inscriptions_statut_eleve = :statutAffecte"
            		+ " GROUP BY ins.eleve_eleveid, ins.eleve_eleveid) c "
            		+ " where c.nbreOcc = 1)"
    ),@NamedNativeQuery(
            name = "Inscription.getOldEleveCount",
            query = "select count(c.nbreOcc) from ( "
            		+ "SELECT COUNT(ins.eleve_eleveid) as nbreOcc, ins.eleve_eleveid  FROM inscriptions ins"
            		+ "  where ins.ecole_ecoleid = :ecoleId "
            		+ "GROUP BY ins.eleve_eleveid, ins.eleve_eleveid) c "
            		+ "where c.nbreOcc > 1)"
    ),@NamedNativeQuery(
            name = "Inscription.getOldEleveCountBySexe",
            query = "select count(c.nbreOcc) from ( "
            		+ "SELECT COUNT(ins.eleve_eleveid) as nbreOcc, ins.eleve_eleveid  FROM inscriptions ins left join eleve elv on ins.eleve_eleveid = elv.eleveid "
            		+ "  where ins.ecole_ecoleid = :ecoleId and  elv.eleve_sexe = :sexe"
            		+ "GROUP BY ins.eleve_eleveid, ins.eleve_eleveid) c "
            		+ "where c.nbreOcc > 1)"
    ),@NamedNativeQuery(
            name = "Inscription.getOldEleveCountBySexeAndAffecte",
            query = "select count(c.nbreOcc) from ( "
            		+ " SELECT COUNT(ins.eleve_eleveid) as nbreOcc, ins.eleve_eleveid  FROM inscriptions ins left join eleve elv on ins.eleve_eleveid = elv.eleveid "
            		+ " where ins.ecole_ecoleid = :ecoleId and  elv.eleve_sexe = :sexe AND ins.inscriptions_statut_eleve = :statutAffecte"
            		+ " GROUP BY ins.eleve_eleveid, ins.eleve_eleveid) c "
            		+ " where c.nbreOcc > 1)"
    ),@NamedNativeQuery(
            name = "Inscription.getAvgEffectifbyClasse",
            query = "select AVG(c.nbreEleve) FROM "
            		+ "( SELECT count(*) as nbreEleve, classe_classeid FROM inscriptions_has_classe icl "
            		+ "left join inscriptions ins on icl.inscriptions_inscriptionsid = ins.inscriptionsid "
            		+ "where ins.ecole_ecoleid = :ecoleId and ins.annee_scolaire_annee_scolaireid = :anneeId "
            		+ "group by icl.classe_classeid "
            		+ ") c"
    )
})
@Entity
@Table(name = "inscriptions")
@Data
@EqualsAndHashCode(callSuper = false)
public class Inscription extends PanacheEntityBase{

	@Id
	@Column(name = "inscriptionsid")
	private long id;
	@Column(name = "inscriptionscode")
	private String code;
	@Column(name = "num_decision_affecte")
	private String numDecisionAffecte;
	@Column(name = "inscriptions_langue_vivante")
	private String lv2;
	@ManyToOne
	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "eleve_eleveid")
	private Eleve eleve;
	@ManyToOne
	@JoinColumn(name = "Branche_id")
	private Branche branche;
	@Column(name = "inscriptions_status")
	private String statut;
	@Column(name="inscriptions_boursier")
	private String boursier;
	@Column(name="inscriptions_statut_eleve")
	private String afecte;
	@Column(name="inscriptions_redoublant")
	private String redoublant;
	@Column(name = "inscriptions_ecole_origine")
	private String ecoleOrigine; 
	@Column(name = "transfert")
	private String transfert;
	@ManyToOne
	@JoinColumn(name = "ecole_ecoleid")
	private Ecole ecole;

}
