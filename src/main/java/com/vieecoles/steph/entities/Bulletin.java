package com.vieecoles.steph.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "Bulletins")
public class Bulletin extends PanacheEntityBase{

	@Id
	private String id;
	@Column(name = "id_ecole")
	private Long ecoleId;
	@Column(name = "nom_ecole")
	private String nomEcole;
	@Column(name = "statut_ecole")
	private String statutEcole;
	@Column(name = "url_logo")
	private String urlLogo;
	@Column(name = "adresse_ecole")
	private String adresseEcole;
	@Column(name = "tel_ecole")
	private String telEcole;
	@Column(name = "annee_id")
	private Long anneeId;
	@Column(name = "annee_libelle")
	private String anneeLibelle;
	@Column(name = "id_periode")
	private Long periodeId;
	@Column(name = "libelle_periode")
	private String libellePeriode;
	@Column(name = "matricule")
	private String matricule;
	private String nom;
	private String prenoms;
	private String sexe;
	@Column(name = "date_naissance")
	private String dateNaissance;
	@Column(name = "lieu_naissance")
	private String lieuNaissance;
	@Column(name = "nationalite")
	private String nationalite;
	private String redoublant;
	private String boursier;
	private String affecte;
	@Column(name = "id_classe")
	private Long classeId;
	@Column(name = "libelle_classe")
	private String libelleClasse;
	@Column(name = "effectif_classe")
	private Integer effectif;
	@Column(name = "total_coef")
	private Double totalCoef;
	@Column(name = "total_moy_coef")
	private Double totalMoyCoef;
	@Column(name = "code_prof_princ")
	private String codeProfPrincipal;
	@Column(name = "nom_prof_princ")
	private String nomPrenomProfPrincipal;
	@Column(name = "code_educateur")
	private String codeEducateur;
	@Column(name = "nom_educateur")
	private String nomPrenomEducateur;
	@Column(name = "heures_abs_just")
	private String heuresAbsJustifiees;
	@Column(name = "heures_abs_non_just")
	private String heuresAbsNonJustifiees;
	@Column(name = "moy_general")
	private Double moyGeneral;
	@Column(name = "moy_max")
	private Double moyMax;
	@Column(name = "moy_min")
	private Double moyMin;
	@Column(name = "moy_avg")
	private Double moyAvg;
	@Column(name = "moy_annuelle")
	private Double moyAn;
	@Column(name = "rang_annuelle")
	private Integer rangAn;
	@Column(name = "appreciation_conseil")
	private String appreciation;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	@Column(name = "num_decision_affecte")
	private String numDecisionAffecte;
	private String lv2;
	private String nature;
	@Column(name="is_classed")
	private String isClassed;
	@Column(name = "code_qr")
	private String codeQr;
	private String statut;
	private Integer rang;
	private String niveau ;
	@Column(name = "ecole_origine")
	private String ecoleOrigine;
	@Column(name = "nom_signataire")
	private String nomSignataire;
	private String transfert;


	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length=100000)
	private byte[] photo_eleve ;

	@Column(name = "niveau_libelle")
	private String niveauLibelle;

	@Column(name = "effectif_non_classe")
	private String effectifNonClasse;

}
