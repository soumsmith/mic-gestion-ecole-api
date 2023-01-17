package com.vieecoles.projection;



public class IdentiteSpiderDto {

private String fon_nomPrenoms ;
private String  fon_fonction ;
private String  fon_adresse; 
private String fon_telephone ;
private String fon_cellulaire ;
private String fon_email ;
private String fon_code_etablissement;
private String etab_denomination ;
private String  etab_num_decision_ouverture ;
private String  etab_code_etablissement; 
private String etab_situation_geographique ;
private String etab_adresse ;
private String etab_telephone ;
private String etab_fax;
private String etab_email;
private String direct_adresse ;
private String  direct_telephone ;
private String  direct_cellulaire; 
private String direct_email ;
private String direct_numero_autorisation_enseigner ;
private String direct_nom_prenom ;
private String direct_code_etablissement;



public IdentiteSpiderDto(String fon_nomPrenoms, String fon_fonction, String fon_adresse, String fon_telephone,
        String fon_cellulaire, String fon_email, String fon_code_etablissement, String etab_denomination,
        String etab_num_decision_ouverture, String etab_code_etablissement, String etab_situation_geographique,
        String etab_adresse, String etab_telephone, String etab_fax, String etab_email, String direct_adresse,
        String direct_telephone, String direct_cellulaire, String direct_email,
        String direct_numero_autorisation_enseigner, String direct_nom_prenom, String direct_code_etablissement) {
    this.fon_nomPrenoms = fon_nomPrenoms;
    this.fon_fonction = fon_fonction;
    this.fon_adresse = fon_adresse;
    this.fon_telephone = fon_telephone;
    this.fon_cellulaire = fon_cellulaire;
    this.fon_email = fon_email;
    this.fon_code_etablissement = fon_code_etablissement;
    this.etab_denomination = etab_denomination;
    this.etab_num_decision_ouverture = etab_num_decision_ouverture;
    this.etab_code_etablissement = etab_code_etablissement;
    this.etab_situation_geographique = etab_situation_geographique;
    this.etab_adresse = etab_adresse;
    this.etab_telephone = etab_telephone;
    this.etab_fax = etab_fax;
    this.etab_email = etab_email;
    this.direct_adresse = direct_adresse;
    this.direct_telephone = direct_telephone;
    this.direct_cellulaire = direct_cellulaire;
    this.direct_email = direct_email;
    this.direct_numero_autorisation_enseigner = direct_numero_autorisation_enseigner;
    this.direct_nom_prenom = direct_nom_prenom;
    this.direct_code_etablissement = direct_code_etablissement;
}
@Override
public String toString() {
    return "IdentiteSpiderDto [fon_nomPrenoms=" + fon_nomPrenoms + ", fon_fonction=" + fon_fonction + ", fon_adresse="
            + fon_adresse + ", fon_telephone=" + fon_telephone + ", fon_cellulaire=" + fon_cellulaire + ", fon_email="
            + fon_email + ", fon_code_etablissement=" + fon_code_etablissement + ", etab_denomination="
            + etab_denomination + ", etab_num_decision_ouverture=" + etab_num_decision_ouverture
            + ", etab_code_etablissement=" + etab_code_etablissement + ", etab_situation_geographique="
            + etab_situation_geographique + ", etab_adresse=" + etab_adresse + ", etab_telephone=" + etab_telephone
            + ", etab_fax=" + etab_fax + ", etab_email=" + etab_email + ", direct_adresse=" + direct_adresse
            + ", direct_telephone=" + direct_telephone + ", direct_cellulaire=" + direct_cellulaire + ", direct_email="
            + direct_email + ", direct_numero_autorisation_enseigner=" + direct_numero_autorisation_enseigner
            + ", direct_nom_prenom=" + direct_nom_prenom + ", direct_code_etablissement=" + direct_code_etablissement
            + "]";
}
public IdentiteSpiderDto() {
}
public String getFon_nomPrenoms() {
    return fon_nomPrenoms;
}
public void setFon_nomPrenoms(String fon_nomPrenoms) {
    this.fon_nomPrenoms = fon_nomPrenoms;
}
public String getFon_fonction() {
    return fon_fonction;
}
public void setFon_fonction(String fon_fonction) {
    this.fon_fonction = fon_fonction;
}
public String getFon_adresse() {
    return fon_adresse;
}
public void setFon_adresse(String fon_adresse) {
    this.fon_adresse = fon_adresse;
}
public String getFon_telephone() {
    return fon_telephone;
}
public void setFon_telephone(String fon_telephone) {
    this.fon_telephone = fon_telephone;
}
public String getFon_cellulaire() {
    return fon_cellulaire;
}
public void setFon_cellulaire(String fon_cellulaire) {
    this.fon_cellulaire = fon_cellulaire;
}
public String getFon_email() {
    return fon_email;
}
public void setFon_email(String fon_email) {
    this.fon_email = fon_email;
}
public String getFon_code_etablissement() {
    return fon_code_etablissement;
}
public void setFon_code_etablissement(String fon_code_etablissement) {
    this.fon_code_etablissement = fon_code_etablissement;
}
public String getEtab_denomination() {
    return etab_denomination;
}
public void setEtab_denomination(String etab_denomination) {
    this.etab_denomination = etab_denomination;
}
public String getEtab_num_decision_ouverture() {
    return etab_num_decision_ouverture;
}
public void setEtab_num_decision_ouverture(String etab_num_decision_ouverture) {
    this.etab_num_decision_ouverture = etab_num_decision_ouverture;
}
public String getEtab_code_etablissement() {
    return etab_code_etablissement;
}
public void setEtab_code_etablissement(String etab_code_etablissement) {
    this.etab_code_etablissement = etab_code_etablissement;
}
public String getEtab_situation_geographique() {
    return etab_situation_geographique;
}
public void setEtab_situation_geographique(String etab_situation_geographique) {
    this.etab_situation_geographique = etab_situation_geographique;
}
public String getEtab_adresse() {
    return etab_adresse;
}
public void setEtab_adresse(String etab_adresse) {
    this.etab_adresse = etab_adresse;
}
public String getEtab_telephone() {
    return etab_telephone;
}
public void setEtab_telephone(String etab_telephone) {
    this.etab_telephone = etab_telephone;
}
public String getEtab_fax() {
    return etab_fax;
}
public void setEtab_fax(String etab_fax) {
    this.etab_fax = etab_fax;
}
public String getEtab_email() {
    return etab_email;
}
public void setEtab_email(String etab_email) {
    this.etab_email = etab_email;
}
public String getDirect_adresse() {
    return direct_adresse;
}
public void setDirect_adresse(String direct_adresse) {
    this.direct_adresse = direct_adresse;
}
public String getDirect_telephone() {
    return direct_telephone;
}
public void setDirect_telephone(String direct_telephone) {
    this.direct_telephone = direct_telephone;
}
public String getDirect_cellulaire() {
    return direct_cellulaire;
}
public void setDirect_cellulaire(String direct_cellulaire) {
    this.direct_cellulaire = direct_cellulaire;
}
public String getDirect_email() {
    return direct_email;
}
public void setDirect_email(String direct_email) {
    this.direct_email = direct_email;
}
public String getDirect_numero_autorisation_enseigner() {
    return direct_numero_autorisation_enseigner;
}
public void setDirect_numero_autorisation_enseigner(String direct_numero_autorisation_enseigner) {
    this.direct_numero_autorisation_enseigner = direct_numero_autorisation_enseigner;
}
public String getDirect_nom_prenom() {
    return direct_nom_prenom;
}
public void setDirect_nom_prenom(String direct_nom_prenom) {
    this.direct_nom_prenom = direct_nom_prenom;
}
public String getDirect_code_etablissement() {
    return direct_code_etablissement;
}
public void setDirect_code_etablissement(String direct_code_etablissement) {
    this.direct_code_etablissement = direct_code_etablissement;
}	








    

}
