package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;

@NamedNativeQueries({
		@NamedNativeQuery(name = "Personnel.countbyEcoleAndGenre", query = "SELECT count(*) FROM personnel p "
				+ "LEFT JOIN sous_attent_personn ss on "
				+ "p.sous_attent_personn_sous_attent_personnid = ss.sous_attent_personnid "
				+ "WHERE p.ecole_ecoleid = :ecoleId and ss.sous_attent_personn_sexe = :sexeId"),
		@NamedNativeQuery(name = "Personnel.countbyEcoleAndGenreAndFonction", query = "SELECT count(*) FROM personnel p "
				+ "LEFT JOIN sous_attent_personn ss on "
				+ "p.sous_attent_personn_sous_attent_personnid = ss.sous_attent_personnid "
				+ "WHERE p.ecole_ecoleid = :ecoleId and ss.sous_attent_personn_sexe = :sexeId and p.fonction_fonctionid= :fonctionId"),

		@NamedNativeQuery(name = "Personnel.countbyEcoleAndGenreAndFonctionAndStatut", query = "SELECT count(*) FROM personnel p "
				+ " LEFT JOIN sous_attent_personn ss on "
				+ " p.sous_attent_personn_sous_attent_personnid = ss.sous_attent_personnid "
				+ " WHERE p.ecole_ecoleid = :ecoleId and ss.sous_attent_personn_sexe = :sexeId and p.fonction_fonctionid= :fonctionId "
				+ " and p.personnel_status_personnel_statusid = :statut") })
@Entity
@Table(name = "personnel")
@Data
public class Personnel extends PanacheEntityBase {

	@Id
	@Column(name = "personnelid")
	private long id;
	@Column(name = "personnelcode")
	private String code;
	@Column(name = "personnelnom")
	private String nom;
	@Column(name = "personnelprenom")
	private String prenom;
	@ManyToOne
	@JoinColumn(name = "fonction_fonctionid")
	private Fonction fonction;
	@ManyToOne
	@JoinColumn(name = "ecole_ecoleid")
	private Ecole ecole;
	@Column(name = "sous_attent_personn_sous_attent_personnid")
	private Long souscriptionAttenteId;
	@Column(name = "personnel_contact")
	private String contact;
	
	@Column(name = "personnel_status_personnel_statusid")
	private Integer statut;
	@Column(name = "personnel_sexe")
	private String sexe;
	@Column(name = "niveau_etude_niveau_etudeid")
	private Integer niveauEtude;
}
