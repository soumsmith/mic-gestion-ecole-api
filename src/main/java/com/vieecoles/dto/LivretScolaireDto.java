package com.vieecoles.dto;
import java.awt.image.BufferedImage;
import java.util.Date;


public class LivretScolaireDto {
	public Long id_ecole;
    private  String pec ;
	public String nom_ecole;
	public String statut_ecole;

	public String url_logo;

	public String adresse_ecole;

	public String tel_ecole;

	public String annee_libelle;

	private String libelle_periode;

	public String matricule;
	public String nom;
	public String prenoms;
	public String sexe;
	private String date_naissance;
	private String lieu_naissance;
	private String nationalite;
	private String redoublant;
	private String boursier;
	private String affecte;

	private String libelle_classe;

	private Integer effectif_classe;

	private String nom_prof_princ;

	private String heures_abs_just;

	private String heures_abs_non_just;

	private Double moy_general;

	private Double moy_max;

	private Double moy_min;

	private Double moy_avg;

	private Double moy_annuelle;

	private String rang_annuelle;

	private String appreciation_conseil;

	private Date date_creation;

	private String statut;
	private String libelle_matiere;
	private Double moyenne;
	private Integer rang;
    private Double moy_An ;
    private Integer rang_An ;

	private String appreciation;

    private int  num_ordre;
    private String  rangBulletin;
    private String nom_prenom_professeur ;
    private String libelle_categorie ;

    private String signataire ;
    private  String bonus ;

    private  String parent_matiere ;
    private String is_classed_mat ;
    private String is_classed_periode ;
    private Integer effectif_non_classe ;

    public Double getMoy_An() {
        return moy_An;
    }

    public void setMoy_An(Double moy_An) {
        this.moy_An = moy_An;
    }

    public Integer getRang_An() {
        return rang_An;
    }

    public void setRang_An(Integer rang_An) {
        this.rang_An = rang_An;
    }

    public Integer getEffectif_non_classe() {
        return effectif_non_classe;
    }

    public void setEffectif_non_classe(Integer effectif_non_classe) {
        this.effectif_non_classe = effectif_non_classe;
    }

    public String getIs_classed_mat() {
        return is_classed_mat;
    }

    public void setIs_classed_mat(String is_classed_mat) {
        this.is_classed_mat = is_classed_mat;
    }

    public String getIs_classed_periode() {
        return is_classed_periode;
    }

    public void setIs_classed_periode(String is_classed_periode) {
        this.is_classed_periode = is_classed_periode;
    }

    public String getParent_matiere() {
        return parent_matiere;
    }

    public void setParent_matiere(String parent_matiere) {
        this.parent_matiere = parent_matiere;

    }

    public LivretScolaireDto(Long id_ecole, String pec, String nom_ecole,
                             String statut_ecole, String url_logo, String adresse_ecole,
                             String tel_ecole, String annee_libelle, String libelle_periode,
                             String matricule, String nom, String prenoms, String sexe,
                             String date_naissance, String lieu_naissance, String nationalite,
                             String redoublant, String boursier, String affecte, String libelle_classe,
                             Integer effectif_classe, String nom_prof_princ, String heures_abs_just,
                             String heures_abs_non_just, Double moy_general,
                             Double moy_max, Double moy_min, Double moy_avg,
                             Double moy_annuelle, String rang_annuelle,
                             String appreciation_conseil, Date date_creation,
                             String statut, String libelle_matiere, Double moyenne,
                             Integer rang, Double moy_An, Integer rang_An,
                             String appreciation, int num_ordre, String rangBulletin,
                             String nom_prenom_professeur, String libelle_categorie,
                             String signataire, String bonus, String parent_matiere,
                             String is_classed_mat, String is_classed_periode,
                             Integer effectif_non_classe) {
        this.id_ecole = id_ecole;
        this.pec = pec;
        this.nom_ecole = nom_ecole;
        this.statut_ecole = statut_ecole;
        this.url_logo = url_logo;
        this.adresse_ecole = adresse_ecole;
        this.tel_ecole = tel_ecole;
        this.annee_libelle = annee_libelle;
        this.libelle_periode = libelle_periode;
        this.matricule = matricule;
        this.nom = nom;
        this.prenoms = prenoms;
        this.sexe = sexe;
        this.date_naissance = date_naissance;
        this.lieu_naissance = lieu_naissance;
        this.nationalite = nationalite;
        this.redoublant = redoublant;
        this.boursier = boursier;
        this.affecte = affecte;
        this.libelle_classe = libelle_classe;
        this.effectif_classe = effectif_classe;
        this.nom_prof_princ = nom_prof_princ;
        this.heures_abs_just = heures_abs_just;
        this.heures_abs_non_just = heures_abs_non_just;
        this.moy_general = moy_general;
        this.moy_max = moy_max;
        this.moy_min = moy_min;
        this.moy_avg = moy_avg;
        this.moy_annuelle = moy_annuelle;
        this.rang_annuelle = rang_annuelle;
        this.appreciation_conseil = appreciation_conseil;
        this.date_creation = date_creation;
        this.statut = statut;
        this.libelle_matiere = libelle_matiere;
        this.moyenne = moyenne;
        this.rang = rang;
        this.moy_An = moy_An;
        this.rang_An = rang_An;
        this.appreciation = appreciation;
        this.num_ordre = num_ordre;
        this.rangBulletin = rangBulletin;
        this.nom_prenom_professeur = nom_prenom_professeur;
        this.libelle_categorie = libelle_categorie;
        this.signataire = signataire;
        this.bonus = bonus;
        this.parent_matiere = parent_matiere;
        this.is_classed_mat = is_classed_mat;
        this.is_classed_periode = is_classed_periode;
        this.effectif_non_classe = effectif_non_classe;
    }

    public String getSignataire() {
        return signataire;
    }

    public void setSignataire(String signataire) {
        this.signataire = signataire;
    }

    public LivretScolaireDto() {
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public void setNum_ordre(int num_ordre) {
        this.num_ordre = num_ordre;
    }



    /**
     * @return Long return the id_ecole
     */
    public Long getId_ecole() {
        return id_ecole;
    }

    /**
     * @param id_ecole the id_ecole to set
     */
    public void setId_ecole(Long id_ecole) {
        this.id_ecole = id_ecole;
    }

    /**
     * @return String return the nom_ecole
     */
    public String getNom_ecole() {
        return nom_ecole;
    }

    /**
     * @param nom_ecole the nom_ecole to set
     */
    public void setNom_ecole(String nom_ecole) {
        this.nom_ecole = nom_ecole;
    }

    /**
     * @return String return the statut_ecole
     */
    public String getStatut_ecole() {
        return statut_ecole;
    }

    /**
     * @param statut_ecole the statut_ecole to set
     */
    public void setStatut_ecole(String statut_ecole) {
        this.statut_ecole = statut_ecole;
    }

    /**
     * @return String return the url_logo
     */
    public String getUrl_logo() {
        return url_logo;
    }

    /**
     * @param url_logo the url_logo to set
     */
    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    /**
     * @return String return the adresse_ecole
     */
    public String getAdresse_ecole() {
        return adresse_ecole;
    }

    /**
     * @param adresse_ecole the adresse_ecole to set
     */
    public void setAdresse_ecole(String adresse_ecole) {
        this.adresse_ecole = adresse_ecole;
    }

    /**
     * @return String return the tel_ecole
     */
    public String getTel_ecole() {
        return tel_ecole;
    }

    public String getLibelle_categorie() {
        return libelle_categorie;
    }

    public void setLibelle_categorie(String libelle_categorie) {
        this.libelle_categorie = libelle_categorie;
    }

    /**
     * @param tel_ecole the tel_ecole to set
     */
    public void setTel_ecole(String tel_ecole) {
        this.tel_ecole = tel_ecole;
    }

    /**
     * @return String return the annee_libelle
     */
    public String getAnnee_libelle() {
        return annee_libelle;
    }

    /**
     * @param annee_libelle the annee_libelle to set
     */
    public void setAnnee_libelle(String annee_libelle) {
        this.annee_libelle = annee_libelle;
    }

    /**
     * @return String return the libelle_periode
     */
    public String getLibelle_periode() {
        return libelle_periode;
    }

    /**
     * @param libelle_periode the libelle_periode to set
     */
    public void setLibelle_periode(String libelle_periode) {
        this.libelle_periode = libelle_periode;
    }

    /**
     * @return String return the matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * @param matricule the matricule to set
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * @return String return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return String return the prenoms
     */
    public String getPrenoms() {
        return prenoms;
    }

    /**
     * @param prenoms the prenoms to set
     */
    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    /**
     * @return String return the sexe
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * @return String return the date_naissance
     */
    public String getDate_naissance() {
        return date_naissance;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    /**
     * @param date_naissance the date_naissance to set
     */
    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }



    /**
     * @return String return the lieu_naissance
     */
    public String getLieu_naissance() {
        return lieu_naissance;
    }

    /**
     * @param lieu_naissance the lieu_naissance to set
     */
    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    /**
     * @return String return the nationalite
     */
    public String getNationalite() {
        return nationalite;
    }

    /**
     * @param nationalite the nationalite to set
     */
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    /**
     * @return String return the redoublant
     */
    public String getRedoublant() {
        return redoublant;
    }

    /**
     * @param redoublant the redoublant to set
     */
    public void setRedoublant(String redoublant) {
        this.redoublant = redoublant;
    }

    /**
     * @return String return the boursier
     */
    public String getBoursier() {
        return boursier;
    }

    /**
     * @param boursier the boursier to set
     */
    public void setBoursier(String boursier) {
        this.boursier = boursier;
    }

    /**
     * @return String return the affecte
     */
    public String getAffecte() {
        return affecte;
    }

    /**
     * @param affecte the affecte to set
     */
    public void setAffecte(String affecte) {
        this.affecte = affecte;
    }

    /**
     * @return String return the libelle_classe
     */
    public String getLibelle_classe() {
        return libelle_classe;
    }

    /**
     * @param libelle_classe the libelle_classe to set
     */
    public void setLibelle_classe(String libelle_classe) {
        this.libelle_classe = libelle_classe;
    }

    /**
     * @return String return the effectif_classe
     */
    public Integer getEffectif_classe() {
        return effectif_classe;
    }

    /**
     * @param effectif_classe the effectif_classe to set
     */
    public void setEffectif_classe(Integer effectif_classe) {
        this.effectif_classe = effectif_classe;
    }

    /**
     * @return String return the total_coef


    /**
     * @return String return the nom_prof_princ
     */
    public String getNom_prof_princ() {
        return nom_prof_princ;
    }

    /**
     * @param nom_prof_princ the nom_prof_princ to set
     */
    public void setNom_prof_princ(String nom_prof_princ) {
        this.nom_prof_princ = nom_prof_princ;
    }

    /**
     * @return String return the heures_abs_just
     */
    public String getHeures_abs_just() {
        return heures_abs_just;
    }

    /**
     * @param heures_abs_just the heures_abs_just to set
     */
    public void setHeures_abs_just(String heures_abs_just) {
        this.heures_abs_just = heures_abs_just;
    }

    /**
     * @return String return the heures_abs_non_just
     */
    public String getHeures_abs_non_just() {
        return heures_abs_non_just;
    }

    /**
     * @param heures_abs_non_just the heures_abs_non_just to set
     */
    public void setHeures_abs_non_just(String heures_abs_non_just) {
        this.heures_abs_non_just = heures_abs_non_just;
    }

    /**
     * @return String return the moy_general
     */
    public Double getMoy_general() {
        return moy_general;
    }

    /**
     * @param moy_general the moy_general to set
     */
    public void setMoy_general(Double moy_general) {
        this.moy_general = moy_general;
    }

    /**
     * @return String return the moy_max
     */
    public Double getMoy_max() {
        return moy_max;
    }

    /**
     * @param moy_max the moy_max to set
     */
    public void setMoy_max(Double moy_max) {
        this.moy_max = moy_max;
    }

    /**
     * @return String return the moy_min
     */
    public Double getMoy_min() {
        return moy_min;
    }

    /**
     * @param moy_min the moy_min to set
     */
    public void setMoy_min(Double moy_min) {
        this.moy_min = moy_min;
    }

    /**
     * @return String return the moy_avg
     */
    public Double getMoy_avg() {
        return moy_avg;
    }

    /**
     * @param moy_avg the moy_avg to set
     */
    public void setMoy_avg(Double moy_avg) {
        this.moy_avg = moy_avg;
    }

    /**
     * @return String return the moy_annuelle
     */
    public Double getMoy_annuelle() {
        return moy_annuelle;
    }

    /**
     * @param moy_annuelle the moy_annuelle to set
     */
    public void setMoy_annuelle(Double moy_annuelle) {
        this.moy_annuelle = moy_annuelle;
    }

    /**
     * @return String return the rang_annuelle
     */
    public String getRang_annuelle() {
        return rang_annuelle;
    }

    /**
     * @param rang_annuelle the rang_annuelle to set
     */
    public void setRang_annuelle(String rang_annuelle) {
        this.rang_annuelle = rang_annuelle;
    }

    /**
     * @return String return the appreciation_conseil
     */
    public String getAppreciation_conseil() {
        return appreciation_conseil;
    }

    /**
     * @param appreciation_conseil the appreciation_conseil to set
     */
    public void setAppreciation_conseil(String appreciation_conseil) {
        this.appreciation_conseil = appreciation_conseil;
    }

    /**
     * @return Date return the date_creation
     */
    public Date getDate_creation() {
        return date_creation;
    }

    /**
     * @param date_creation the date_creation to set
     */
    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    /**
     * @return String return the codeQr
     */


    /**
     * @return String return the statut
     */
    public String getStatut() {
        return statut;
    }

    /**
     * @param statut the statut to set
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }

    /**
     * @return String return the matiereLibelle
     */


    /**
     * @return String return the moyenne
     */
    public Double getMoyenne() {
        return moyenne;
    }

    /**
     * @param moyenne the moyenne to set
     */
    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    /**
     * @return String return the rang
     */
    public Integer getRang() {
        return rang;
    }

    /**
     * @param rang the rang to set
     */
    public void setRang(Integer rang) {
        this.rang = rang;
    }


    /**
     * @return String return the appreciation
     */
    public String getAppreciation() {
        return appreciation;
    }

    /**
     * @param appreciation the appreciation to set
     */
    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }


















    /**
     * @return String return the num_ordre
     */
    public Integer getNum_ordre() {
        return num_ordre;
    }

    /**
     * @param num_ordre the num_ordre to set
     */
    public void setNum_ordre(Integer num_ordre) {
        this.num_ordre = num_ordre;
    }




    /**
     * @return String return the libelle_matiere
     */
    public String getLibelle_matiere() {
        return libelle_matiere;
    }

    /**
     * @param libelle_matiere the libelle_matiere to set
     */
    public void setLibelle_matiere(String libelle_matiere) {
        this.libelle_matiere = libelle_matiere;
    }


    /**
     * @return String return the RangBulletin
     */



    /**
     * @return String return the nom_prenom_professeur
     */
    public String getNom_prenom_professeur() {
        return nom_prenom_professeur;
    }

    /**
     * @param nom_prenom_professeur the nom_prenom_professeur to set
     */
    public void setNom_prenom_professeur(String nom_prenom_professeur) {
        this.nom_prenom_professeur = nom_prenom_professeur;
    }


    /**
     * @return String return the rangBulletin
     */
    public String getRangBulletin() {
        return rangBulletin;
    }

    /**
     * @param rangBulletin the rangBulletin to set
     */
    public void setRangBulletin(String rangBulletin) {
        this.rangBulletin = rangBulletin;
    }

}
