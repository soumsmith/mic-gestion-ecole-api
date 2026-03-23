package com.vieecoles.steph.dto.bulletin;
/** Infos annuelles d'un élève (période finale uniquement) */
public class InfoAnnuelleEleveDto {
    private Double moyenneAnnuelle;
    private String appreciationAnnuelle;
    private String rangAnnuel;
    private Double moyenneAnnuelleParMatiere; // voir MatiereResultDto pour le détail
    // Primaire
    private Double moyenneInterne;
    private Double moyenneIEPP;
    private Double moyennePassage;
    // Secondaire
    private Double moyenneFrancaisAnnuelle;
 
    // Getters / setters
    public Double getMoyenneAnnuelle()                             { return moyenneAnnuelle; }
    public void setMoyenneAnnuelle(Double v)                       { this.moyenneAnnuelle = v; }
    public String getAppreciationAnnuelle()                        { return appreciationAnnuelle; }
    public void setAppreciationAnnuelle(String v)                  { this.appreciationAnnuelle = v; }
    public String getRangAnnuel()                                  { return rangAnnuel; }
    public void setRangAnnuel(String v)                            { this.rangAnnuel = v; }
    public Double getMoyenneInterne()                              { return moyenneInterne; }
    public void setMoyenneInterne(Double v)                        { this.moyenneInterne = v; }
    public Double getMoyenneIEPP()                                 { return moyenneIEPP; }
    public void setMoyenneIEPP(Double v)                           { this.moyenneIEPP = v; }
    public Double getMoyennePassage()                              { return moyennePassage; }
    public void setMoyennePassage(Double v)                        { this.moyennePassage = v; }
    public Double getMoyenneFrancaisAnnuelle()                     { return moyenneFrancaisAnnuelle; }
    public void setMoyenneFrancaisAnnuelle(Double v)               { this.moyenneFrancaisAnnuelle = v; }
}