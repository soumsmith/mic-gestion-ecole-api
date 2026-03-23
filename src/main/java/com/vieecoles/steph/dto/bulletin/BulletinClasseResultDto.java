package com.vieecoles.steph.dto.bulletin;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO principal retourné par la méthode de calcul.
 * Contient les résultats périodiques de toute une classe.
 */
public class BulletinClasseResultDto {
    private ClasseInfoDto classe;
    private EcoleInfoDto ecole;
    private String anneeLibelle;
    private String periodeLibelle;
    private boolean estPeriodeFinale;
 
    /** Résultats individuels de chaque élève, triés par rang croissant */
    private List<BulletinEleveResultDto> eleves = new ArrayList<>();
 
    /** Informations statistiques sur la classe entière */
    private StatClasseDto statsClasse;
 
    // Getters / setters
    public ClasseInfoDto getClasse()                               { return classe; }
    public void setClasse(ClasseInfoDto c)                         { this.classe = c; }
    public EcoleInfoDto getEcole()                                 { return ecole; }
    public void setEcole(EcoleInfoDto e)                           { this.ecole = e; }
    public String getAnneeLibelle()                                { return anneeLibelle; }
    public void setAnneeLibelle(String v)                          { this.anneeLibelle = v; }
    public String getPeriodeLibelle()                              { return periodeLibelle; }
    public void setPeriodeLibelle(String v)                        { this.periodeLibelle = v; }
    public boolean isEstPeriodeFinale()                            { return estPeriodeFinale; }
    public void setEstPeriodeFinale(boolean v)                     { this.estPeriodeFinale = v; }
    public List<BulletinEleveResultDto> getEleves()                { return eleves; }
    public void setEleves(List<BulletinEleveResultDto> v)          { this.eleves = v; }
    public StatClasseDto getStatsClasse()                          { return statsClasse; }
    public void setStatsClasse(StatClasseDto v)                    { this.statsClasse = v; }
}