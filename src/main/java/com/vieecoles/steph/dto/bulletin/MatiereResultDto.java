package com.vieecoles.steph.dto.bulletin;

import java.util.ArrayList;
import java.util.List;

/** Résultat d'un élève dans une matière */
public class MatiereResultDto {
    private Long matiereId;
    private String matiereCode;
    private String matiereLibelle;
    private String professeurNom;
    private String professeurPrenom;
    private double coef;
    private double moyenne;
    private String appreciation;
    private String rang;
    private boolean estAjuste; // repêchage appliqué
 
    /** Détail de chaque note saisie */
    private List<NoteDetailDto> notes = new ArrayList<>();
 
    // Getters / setters
    public Long getMatiereId()                                     { return matiereId; }
    public void setMatiereId(Long v)                               { this.matiereId = v; }
    public String getMatiereCode()                                  { return matiereCode; }
    public void setMatiereCode(String v)                           { this.matiereCode = v; }
    public String getMatiereLibelle()                               { return matiereLibelle; }
    public void setMatiereLibelle(String v)                        { this.matiereLibelle = v; }
    public String getProfesseurNom()                               { return professeurNom; }
    public void setProfesseurNom(String v)                         { this.professeurNom = v; }
    public String getProfesseurPrenom()                            { return professeurPrenom; }
    public void setProfesseurPrenom(String v)                      { this.professeurPrenom = v; }
    public double getCoef()                                        { return coef; }
    public void setCoef(double v)                                  { this.coef = v; }
    public double getMoyenne()                                     { return moyenne; }
    public void setMoyenne(double v)                               { this.moyenne = v; }
    public String getAppreciation()                                { return appreciation; }
    public void setAppreciation(String v)                          { this.appreciation = v; }
    public String getRang()                                        { return rang; }
    public void setRang(String v)                                  { this.rang = v; }
    public boolean isEstAjuste()                                   { return estAjuste; }
    public void setEstAjuste(boolean v)                            { this.estAjuste = v; }
    public List<NoteDetailDto> getNotes()                          { return notes; }
    public void setNotes(List<NoteDetailDto> v)                    { this.notes = v; }
}