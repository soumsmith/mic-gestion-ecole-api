package com.vieecoles.steph.dto.bulletin;
/** Une note individuelle d'évaluation */
public class NoteDetailDto {
    private String typeEvaluation;
    private Long numero;
    private double note;
    private String noteSur;
    private boolean estTestLourd;
 
    // Getters / setters
    public String getTypeEvaluation()                              { return typeEvaluation; }
    public void setTypeEvaluation(String v)                        { this.typeEvaluation = v; }
    public Long getNumero()                                         { return numero; }
    public void setNumero(Long v)                                   { this.numero = v; }
    public double getNote()                                        { return note; }
    public void setNote(double v)                                  { this.note = v; }
    public String getNoteSur()                                     { return noteSur; }
    public void setNoteSur(String v)                               { this.noteSur = v; }
    public boolean isEstTestLourd()                                { return estTestLourd; }
    public void setEstTestLourd(boolean v)                         { this.estTestLourd = v; }
}