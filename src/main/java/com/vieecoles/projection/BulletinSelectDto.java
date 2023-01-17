package com.vieecoles.projection;
import java.util.Date;


public class BulletinSelectDto {

	
	
	public Long id_ecole;
	
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
	
	private String effectif_classe;
	
	private String total_coef;
	
	private String total_moy_coef;
	
	private String nom_prof_princ;
	
	private String heures_abs_just;
	
	private String heures_abs_non_just;
	
	private String moy_general;
	
	private String moy_max;
	
	private String moy_min;
	
	private String moy_avg;
	
	private String moy_annuelle;

	private String rang_annuelle;
	
	private String appreciation_conseil;

	private Date date_creation;
	
	private String code_qr;
	private String statut;
	private String libelle_matiere;
	private String moyenne;
	private String rang;
	private String coef;
	
	private String moy_coef;
	private String appreciation;
    private String  categorie;
    private Integer  num_ordre;
    private String  rangBulletin;
    private String nom_prenom_professeur ;

	

    public BulletinSelectDto() {
    }




    




    @Override
    public String toString() {
        return "BulletinSelectDto [id_ecole=" + id_ecole + ", nom_ecole=" + nom_ecole + ", statut_ecole=" + statut_ecole
                + ", url_logo=" + url_logo + ", adresse_ecole=" + adresse_ecole + ", tel_ecole=" + tel_ecole
                + ", annee_libelle=" + annee_libelle + ", libelle_periode=" + libelle_periode + ", matricule="
                + matricule + ", nom=" + nom + ", prenoms=" + prenoms + ", sexe=" + sexe + ", date_naissance="
                + date_naissance + ", lieu_naissance=" + lieu_naissance + ", nationalite=" + nationalite
                + ", redoublant=" + redoublant + ", boursier=" + boursier + ", affecte=" + affecte + ", libelle_classe="
                + libelle_classe + ", effectif_classe=" + effectif_classe + ", total_coef=" + total_coef
                + ", total_moy_coef=" + total_moy_coef + ", nom_prof_princ=" + nom_prof_princ + ", heures_abs_just="
                + heures_abs_just + ", heures_abs_non_just=" + heures_abs_non_just + ", moy_general=" + moy_general
                + ", moy_max=" + moy_max + ", moy_min=" + moy_min + ", moy_avg=" + moy_avg + ", moy_annuelle="
                + moy_annuelle + ", rang_annuelle=" + rang_annuelle + ", appreciation_conseil=" + appreciation_conseil
                + ", date_creation=" + date_creation + ", code_qr=" + code_qr + ", statut=" + statut
                + ", libelle_matiere=" + libelle_matiere + ", moyenne=" + moyenne + ", rang=" + rang + ", coef=" + coef
                + ", moy_coef=" + moy_coef + ", appreciation=" + appreciation + ", categorie=" + categorie
                + ", num_ordre=" + num_ordre + ", RangBulletin=" + rangBulletin + ", nom_prenom_professeur="
                + nom_prenom_professeur + "]";
    }









    








    public BulletinSelectDto(Long id_ecole, String nom_ecole, String statut_ecole, String url_logo,
            String adresse_ecole, String tel_ecole, String annee_libelle, String libelle_periode, String matricule,
            String nom, String prenoms, String sexe, String date_naissance, String lieu_naissance, String nationalite,
            String redoublant, String boursier, String affecte, String libelle_classe, String effectif_classe,
            String total_coef, String total_moy_coef, String nom_prof_princ, String heures_abs_just,
            String heures_abs_non_just, String moy_general, String moy_max, String moy_min, String moy_avg,
            String moy_annuelle, String rang_annuelle, String appreciation_conseil, Date date_creation, String code_qr,
            String statut, String libelle_matiere, String moyenne, String rang, String coef, String moy_coef,
            String appreciation, String categorie, Integer num_ordre, String rangBulletin, String nom_prenom_professeur) {
        this.id_ecole = id_ecole;
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
        this.total_coef = total_coef;
        this.total_moy_coef = total_moy_coef;
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
        this.code_qr = code_qr;
        this.statut = statut;
        this.libelle_matiere = libelle_matiere;
        this.moyenne = moyenne;
        this.rang = rang;
        this.coef = coef;
        this.moy_coef = moy_coef;
        this.appreciation = appreciation;
        this.categorie = categorie;
        this.num_ordre = num_ordre;
        this.rangBulletin = rangBulletin;
        this.nom_prenom_professeur = nom_prenom_professeur;
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
    public String getEffectif_classe() {
        return effectif_classe;
    }

    /**
     * @param effectif_classe the effectif_classe to set
     */
    public void setEffectif_classe(String effectif_classe) {
        this.effectif_classe = effectif_classe;
    }

    /**
     * @return String return the total_coef
     */
    public String getTotal_coef() {
        return total_coef;
    }

    /**
     * @param total_coef the total_coef to set
     */
    public void setTotal_coef(String total_coef) {
        this.total_coef = total_coef;
    }

    /**
     * @return String return the total_moy_coef
     */
    public String getTotal_moy_coef() {
        return total_moy_coef;
    }

    /**
     * @param total_moy_coef the total_moy_coef to set
     */
    public void setTotal_moy_coef(String total_moy_coef) {
        this.total_moy_coef = total_moy_coef;
    }

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
    public String getMoy_general() {
        return moy_general;
    }

    /**
     * @param moy_general the moy_general to set
     */
    public void setMoy_general(String moy_general) {
        this.moy_general = moy_general;
    }

    /**
     * @return String return the moy_max
     */
    public String getMoy_max() {
        return moy_max;
    }

    /**
     * @param moy_max the moy_max to set
     */
    public void setMoy_max(String moy_max) {
        this.moy_max = moy_max;
    }

    /**
     * @return String return the moy_min
     */
    public String getMoy_min() {
        return moy_min;
    }

    /**
     * @param moy_min the moy_min to set
     */
    public void setMoy_min(String moy_min) {
        this.moy_min = moy_min;
    }

    /**
     * @return String return the moy_avg
     */
    public String getMoy_avg() {
        return moy_avg;
    }

    /**
     * @param moy_avg the moy_avg to set
     */
    public void setMoy_avg(String moy_avg) {
        this.moy_avg = moy_avg;
    }

    /**
     * @return String return the moy_annuelle
     */
    public String getMoy_annuelle() {
        return moy_annuelle;
    }

    /**
     * @param moy_annuelle the moy_annuelle to set
     */
    public void setMoy_annuelle(String moy_annuelle) {
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
    public String getMoyenne() {
        return moyenne;
    }

    /**
     * @param moyenne the moyenne to set
     */
    public void setMoyenne(String moyenne) {
        this.moyenne = moyenne;
    }

    /**
     * @return String return the rang
     */
    public String getRang() {
        return rang;
    }

    /**
     * @param rang the rang to set
     */
    public void setRang(String rang) {
        this.rang = rang;
    }

    /**
     * @return String return the coef
     */
    public String getCoef() {
        return coef;
    }

    /**
     * @param coef the coef to set
     */
    public void setCoef(String coef) {
        this.coef = coef;
    }

    /**
     * @return String return the moy_coef
     */
    public String getMoy_coef() {
        return moy_coef;
    }

    /**
     * @param moy_coef the moy_coef to set
     */
    public void setMoy_coef(String moy_coef) {
        this.moy_coef = moy_coef;
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
     * @return String return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
     * @return String return the code_qr
     */
    public String getCode_qr() {
        return code_qr;
    }

    /**
     * @param code_qr the code_qr to set
     */
    public void setCode_qr(String code_qr) {
        this.code_qr = code_qr;
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
