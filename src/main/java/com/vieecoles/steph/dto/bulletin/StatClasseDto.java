package com.vieecoles.steph.dto.bulletin;
/** Statistiques globales sur la classe pour la période */
public class StatClasseDto {
    private double moyenneClasse;
    private double moyenneMin;
    private double moyenneMax;
    private int nombreEleves;
    private int nombreElevesClasses;
 
    // Getters / setters
    public double getMoyenneClasse()                               { return moyenneClasse; }
    public void setMoyenneClasse(double v)                         { this.moyenneClasse = v; }
    public double getMoyenneMin()                                  { return moyenneMin; }
    public void setMoyenneMin(double v)                            { this.moyenneMin = v; }
    public double getMoyenneMax()                                  { return moyenneMax; }
    public void setMoyenneMax(double v)                            { this.moyenneMax = v; }
    public int getNombreEleves()                                   { return nombreEleves; }
    public void setNombreEleves(int v)                             { this.nombreEleves = v; }
    public int getNombreElevesClasses()                            { return nombreElevesClasses; }
    public void setNombreElevesClasses(int v)                      { this.nombreElevesClasses = v; }
}