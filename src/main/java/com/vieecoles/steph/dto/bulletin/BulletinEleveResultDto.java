package com.vieecoles.steph.dto.bulletin;

import java.util.ArrayList;
import java.util.List;

/** Informations personnelles et résultats d'un élève */
public class BulletinEleveResultDto {
    private String matricule;
    private String nom;
    private String prenom;
    private String urlPhoto;
    private String sexe;
 
    /** Résultats périodiques */
    private double moyenneGenerale;
    private String appreciation;
    private int rang;
    private int absencesJustifiees;
    private int absencesNonJustifiees;
    private boolean estClasse;
 
    /** Résultats détaillés par matière */
    private List<MatiereResultDto> matieres = new ArrayList<>();
 
    /** Informations annuelles (renseignées uniquement si période finale) */
    private InfoAnnuelleEleveDto infoAnnuelle;
 
    // Getters / setters
    public String getMatricule()                                   { return matricule; }
    public void setMatricule(String v)                             { this.matricule = v; }
    public String getNom()                                         { return nom; }
    public void setNom(String v)                                   { this.nom = v; }
    public String getPrenom()                                      { return prenom; }
    public void setPrenom(String v)                                { this.prenom = v; }
    public String getUrlPhoto()                                    { return urlPhoto; }
    public void setUrlPhoto(String v)                              { this.urlPhoto = v; }
    public String getSexe()                                        { return sexe; }
    public void setSexe(String v)                                  { this.sexe = v; }
    public double getMoyenneGenerale()                             { return moyenneGenerale; }
    public void setMoyenneGenerale(double v)                       { this.moyenneGenerale = v; }
    public String getAppreciation()                                { return appreciation; }
    public void setAppreciation(String v)                          { this.appreciation = v; }
    public int getRang()                                           { return rang; }
    public void setRang(int v)                                     { this.rang = v; }
    public int getAbsencesJustifiees()                             { return absencesJustifiees; }
    public void setAbsencesJustifiees(int v)                       { this.absencesJustifiees = v; }
    public int getAbsencesNonJustifiees()                          { return absencesNonJustifiees; }
    public void setAbsencesNonJustifiees(int v)                    { this.absencesNonJustifiees = v; }
    public boolean isEstClasse()                                   { return estClasse; }
    public void setEstClasse(boolean v)                            { this.estClasse = v; }
    public List<MatiereResultDto> getMatieres()                    { return matieres; }
    public void setMatieres(List<MatiereResultDto> v)              { this.matieres = v; }
    public InfoAnnuelleEleveDto getInfoAnnuelle()                  { return infoAnnuelle; }
    public void setInfoAnnuelle(InfoAnnuelleEleveDto v)            { this.infoAnnuelle = v; }
}