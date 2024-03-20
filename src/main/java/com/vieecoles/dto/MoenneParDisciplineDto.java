package com.vieecoles.dto;

import java.util.List;

public class MoenneParDisciplineDto {
    private String niveau ;
    private String classe;
    private String cycle;
    private Long pourInf10F;
    private Long pourInf10G;
    private Long classMathF;
    private Long classMathG;
    private Long nbreMoyInf10MathF;
    private Long nbreMoyInf10MathG;
    private Double pourMoyInf10MathF;
    private Double pourMoyInf10MathG;
    //-------------------------------------------
    private Long classPCF;
    private Long classPCG;
    private Long nbreMoyInf10PCF;
    private Long nbreMoyInf10PCG;
    private Double pourMoyInf10PCF;
    private Double pourMoyInf10PCG;

    private Long classSVTF;
    private Long classSVTG;
    private Long nbreMoyInf10SVTF;
    private Long nbreMoyInf10SVTG;
    private Double pourMoyInf10SVTF;
    private Double pourMoyInf10SVTG;

    private Long classPHILOF;
    private Long classPHILOG;
    private Long nbreMoyInf10PHILOF;
    private Long nbreMoyInf10PHILOG;
    private Double pourMoyInf10PHILOF;
    private Double pourMoyInf10PHILOG;

    private Long classFrancaisF;
    private Long classFrancaisG;
    private Long nbreMoyInf10FrancaisF;
    private Long nbreMoyInf10FrancaisG;
    private Double pourMoyInf10FrancaisF;
    private Double pourMoyInf10FrancaisG;
    private Long classCompFrF;
    private Long classCompFrG;
    private Long nbreMoyInf10CompFrF;
    private Long nbreMoyInf10CompFrG;
    private Double pourMoyInf10CompFrF;
    private Double pourMoyInf10CompFrG;

    private Long classOrthGramF;
    private Long classOrthGramG;
    private Long nbreMoyInf10OrthGramF;
    private Long nbreMoyInf10OrthGramG;
    private Double pourMoyInf10OrthGramF;
    private Double pourMoyInf10OrthGramG;

    private Long classAngF;
    private Long classAngG;
    private Long nbreMoyInf10AngF;
    private Long nbreMoyInf10AngG;
    private Double pourMoyInf10AngF;
    private Double pourMoyInf10AngG;

    private Long classAllF;
    private Long classAllG;
    private Long nbreMoyInf10AllF;
    private Long nbreMoyInf10AllG;
    private Double pourMoyInf10AllF;
    private Double pourMoyInf10AllG;

    private Long classEspF;
    private Long classEspG;
    private Long nbreMoyInf10EspF;
    private Long nbreMoyInf10EspG;
    private Double pourMoyInf10EspF;
    private Double pourMoyInf10EspG;
    private Long classHgF;
    private Long classHgG;
    private Long nbreMoyInf10HgF;
    private Long nbreMoyInf10HgG;
    private Double pourMoyInf10HgF;
    private Double pourMoyInf10HgG;
    private Double moyClasseF ;
    private Double moyClasseG ;
    Integer ordre_niveau ;
    private Double pourMoyInf10Math;
    private Double pourMoyInf10PC;
    private Double pourMoyInf10SVT;
    private Double pourMoyInf10PHILO;
    private Double pourMoyInf10Francais;
    private Double pourMoyInf10CompFr;
    private Double pourMoyInf10OrthGram;
    private Double pourMoyInf10Ang;
    private Double pourMoyInf10All;
    private Double pourMoyInf10Esp;
    private Double pourMoyInf10Hg;

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Double getPourMoyInf10Math() {
        return pourMoyInf10Math;
    }

    public void setPourMoyInf10Math(Double pourMoyInf10Math) {
        this.pourMoyInf10Math = pourMoyInf10Math;
    }

    public Double getPourMoyInf10PC() {
        return pourMoyInf10PC;
    }

    public void setPourMoyInf10PC(Double pourMoyInf10PC) {
        this.pourMoyInf10PC = pourMoyInf10PC;
    }

    public Double getPourMoyInf10SVT() {
        return pourMoyInf10SVT;
    }

    public void setPourMoyInf10SVT(Double pourMoyInf10SVT) {
        this.pourMoyInf10SVT = pourMoyInf10SVT;
    }

    public Double getPourMoyInf10PHILO() {
        return pourMoyInf10PHILO;
    }

    public void setPourMoyInf10PHILO(Double pourMoyInf10PHILO) {
        this.pourMoyInf10PHILO = pourMoyInf10PHILO;
    }

    public Double getPourMoyInf10Francais() {
        return pourMoyInf10Francais;
    }

    public void setPourMoyInf10Francais(Double pourMoyInf10Francais) {
        this.pourMoyInf10Francais = pourMoyInf10Francais;
    }

    public Double getPourMoyInf10CompFr() {
        return pourMoyInf10CompFr;
    }

    public void setPourMoyInf10CompFr(Double pourMoyInf10CompFr) {
        this.pourMoyInf10CompFr = pourMoyInf10CompFr;
    }

    public Double getPourMoyInf10OrthGram() {
        return pourMoyInf10OrthGram;
    }

    public void setPourMoyInf10OrthGram(Double pourMoyInf10OrthGram) {
        this.pourMoyInf10OrthGram = pourMoyInf10OrthGram;
    }

    public Double getPourMoyInf10Ang() {
        return pourMoyInf10Ang;
    }

    public void setPourMoyInf10Ang(Double pourMoyInf10Ang) {
        this.pourMoyInf10Ang = pourMoyInf10Ang;
    }

    public Double getPourMoyInf10All() {
        return pourMoyInf10All;
    }

    public void setPourMoyInf10All(Double pourMoyInf10All) {
        this.pourMoyInf10All = pourMoyInf10All;
    }

    public Double getPourMoyInf10Esp() {
        return pourMoyInf10Esp;
    }

    public void setPourMoyInf10Esp(Double pourMoyInf10Esp) {
        this.pourMoyInf10Esp = pourMoyInf10Esp;
    }

    public Double getPourMoyInf10Hg() {
        return pourMoyInf10Hg;
    }

    public void setPourMoyInf10Hg(Double pourMoyInf10Hg) {
        this.pourMoyInf10Hg = pourMoyInf10Hg;
    }

    public MoenneParDisciplineDto(List<MoenneParDisciplineDto> recapDesResultatsElevesNonAffecteDtos) {
    }



    public Integer getOrdre_niveau() {
        return ordre_niveau;
    }

    public void setOrdre_niveau(Integer ordre_niveau) {
        this.ordre_niveau = ordre_niveau;
    }



    public MoenneParDisciplineDto() {
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getPourInf10F() {
        return pourInf10F;
    }

    public void setPourInf10F(Long pourInf10F) {
        this.pourInf10F = pourInf10F;
    }

    public Long getPourInf10G() {
        return pourInf10G;
    }

    public void setPourInf10G(Long pourInf10G) {
        this.pourInf10G = pourInf10G;
    }

    public Long getClassMathF() {
        return classMathF;
    }

    public void setClassMathF(Long classMathF) {
        this.classMathF = classMathF;
    }

    public Long getClassMathG() {
        return classMathG;
    }

    public void setClassMathG(Long classMathG) {
        this.classMathG = classMathG;
    }

    public Long getNbreMoyInf10MathF() {
        return nbreMoyInf10MathF;
    }

    public void setNbreMoyInf10MathF(Long nbreMoyInf10MathF) {
        this.nbreMoyInf10MathF = nbreMoyInf10MathF;
    }

    public Long getNbreMoyInf10MathG() {
        return nbreMoyInf10MathG;
    }

    public void setNbreMoyInf10MathG(Long nbreMoyInf10MathG) {
        this.nbreMoyInf10MathG = nbreMoyInf10MathG;
    }

    public Double getPourMoyInf10MathF() {
        return pourMoyInf10MathF;
    }

    public MoenneParDisciplineDto(String niveau, String classe, Long pourInf10F,
                                  Long pourInf10G, Long classMathF,
                                  Long classMathG, Long nbreMoyInf10MathF,
                                  Long nbreMoyInf10MathG, Double pourMoyInf10MathF,
                                  Double pourMoyInf10MathG, Double moyClasseF,
                                  Double moyClasseG, Integer ordre_niveau,
                                  Double moyClasse, Double moyClasseF_ET,
                                  Double moyClasseG_ET, Double moyClasse_ET) {
        this.niveau = niveau;
        this.classe = classe;
        this.pourInf10F = pourInf10F;
        this.pourInf10G = pourInf10G;
        this.classMathF = classMathF;
        this.classMathG = classMathG;
        this.nbreMoyInf10MathF = nbreMoyInf10MathF;
        this.nbreMoyInf10MathG = nbreMoyInf10MathG;
        this.pourMoyInf10MathF = pourMoyInf10MathF;
        this.pourMoyInf10MathG = pourMoyInf10MathG;
        this.moyClasseF = moyClasseF;
        this.moyClasseG = moyClasseG;
        this.ordre_niveau = ordre_niveau;

    }

    public Long getClassPCF() {
        return classPCF;
    }

    public void setClassPCF(Long classPCF) {
        this.classPCF = classPCF;
    }

    public Long getClassPCG() {
        return classPCG;
    }

    public void setClassPCG(Long classPCG) {
        this.classPCG = classPCG;
    }

    public Long getNbreMoyInf10PCF() {
        return nbreMoyInf10PCF;
    }

    public void setNbreMoyInf10PCF(Long nbreMoyInf10PCF) {
        this.nbreMoyInf10PCF = nbreMoyInf10PCF;
    }

    public Long getNbreMoyInf10PCG() {
        return nbreMoyInf10PCG;
    }

    public void setNbreMoyInf10PCG(Long nbreMoyInf10PCG) {
        this.nbreMoyInf10PCG = nbreMoyInf10PCG;
    }

    public Double getPourMoyInf10PCF() {
        return pourMoyInf10PCF;
    }

    public void setPourMoyInf10PCF(Double pourMoyInf10PCF) {
        this.pourMoyInf10PCF = pourMoyInf10PCF;
    }

    public Double getPourMoyInf10PCG() {
        return pourMoyInf10PCG;
    }

    public void setPourMoyInf10PCG(Double pourMoyInf10PCG) {
        this.pourMoyInf10PCG = pourMoyInf10PCG;
    }

    public Long getClassSVTF() {
        return classSVTF;
    }

    public void setClassSVTF(Long classSVTF) {
        this.classSVTF = classSVTF;
    }

    public Long getClassSVTG() {
        return classSVTG;
    }

    public void setClassSVTG(Long classSVTG) {
        this.classSVTG = classSVTG;
    }

    public Long getNbreMoyInf10SVTF() {
        return nbreMoyInf10SVTF;
    }

    public void setNbreMoyInf10SVTF(Long nbreMoyInf10SVTF) {
        this.nbreMoyInf10SVTF = nbreMoyInf10SVTF;
    }

    public Long getNbreMoyInf10SVTG() {
        return nbreMoyInf10SVTG;
    }

    public void setNbreMoyInf10SVTG(Long nbreMoyInf10SVTG) {
        this.nbreMoyInf10SVTG = nbreMoyInf10SVTG;
    }

    public Double getPourMoyInf10SVTF() {
        return pourMoyInf10SVTF;
    }

    public void setPourMoyInf10SVTF(Double pourMoyInf10SVTF) {
        this.pourMoyInf10SVTF = pourMoyInf10SVTF;
    }

    public Double getPourMoyInf10SVTG() {
        return pourMoyInf10SVTG;
    }

    public void setPourMoyInf10SVTG(Double pourMoyInf10SVTG) {
        this.pourMoyInf10SVTG = pourMoyInf10SVTG;
    }

    public Long getClassPHILOF() {
        return classPHILOF;
    }

    public void setClassPHILOF(Long classPHILOF) {
        this.classPHILOF = classPHILOF;
    }

    public Long getClassPHILOG() {
        return classPHILOG;
    }

    public void setClassPHILOG(Long classPHILOG) {
        this.classPHILOG = classPHILOG;
    }

    public Long getNbreMoyInf10PHILOF() {
        return nbreMoyInf10PHILOF;
    }

    public void setNbreMoyInf10PHILOF(Long nbreMoyInf10PHILOF) {
        this.nbreMoyInf10PHILOF = nbreMoyInf10PHILOF;
    }

    public Long getNbreMoyInf10PHILOG() {
        return nbreMoyInf10PHILOG;
    }

    public void setNbreMoyInf10PHILOG(Long nbreMoyInf10PHILOG) {
        this.nbreMoyInf10PHILOG = nbreMoyInf10PHILOG;
    }

    public Double getPourMoyInf10PHILOF() {
        return pourMoyInf10PHILOF;
    }

    public void setPourMoyInf10PHILOF(Double pourMoyInf10PHILOF) {
        this.pourMoyInf10PHILOF = pourMoyInf10PHILOF;
    }

    public Double getPourMoyInf10PHILOG() {
        return pourMoyInf10PHILOG;
    }

    public void setPourMoyInf10PHILOG(Double pourMoyInf10PHILOG) {
        this.pourMoyInf10PHILOG = pourMoyInf10PHILOG;
    }

    public Long getClassFrancaisF() {
        return classFrancaisF;
    }

    public void setClassFrancaisF(Long classFrancaisF) {
        this.classFrancaisF = classFrancaisF;
    }

    public Long getClassFrancaisG() {
        return classFrancaisG;
    }

    public void setClassFrancaisG(Long classFrancaisG) {
        this.classFrancaisG = classFrancaisG;
    }

    public Long getNbreMoyInf10FrancaisF() {
        return nbreMoyInf10FrancaisF;
    }

    public void setNbreMoyInf10FrancaisF(Long nbreMoyInf10FrancaisF) {
        this.nbreMoyInf10FrancaisF = nbreMoyInf10FrancaisF;
    }

    public Long getNbreMoyInf10FrancaisG() {
        return nbreMoyInf10FrancaisG;
    }

    public void setNbreMoyInf10FrancaisG(Long nbreMoyInf10FrancaisG) {
        this.nbreMoyInf10FrancaisG = nbreMoyInf10FrancaisG;
    }

    public Double getPourMoyInf10FrancaisF() {
        return pourMoyInf10FrancaisF;
    }

    public void setPourMoyInf10FrancaisF(Double pourMoyInf10FrancaisF) {
        this.pourMoyInf10FrancaisF = pourMoyInf10FrancaisF;
    }

    public Double getPourMoyInf10FrancaisG() {
        return pourMoyInf10FrancaisG;
    }

    public void setPourMoyInf10FrancaisG(Double pourMoyInf10FrancaisG) {
        this.pourMoyInf10FrancaisG = pourMoyInf10FrancaisG;
    }

    public Long getClassCompFrF() {
        return classCompFrF;
    }

    public void setClassCompFrF(Long classCompFrF) {
        this.classCompFrF = classCompFrF;
    }

    public Long getClassCompFrG() {
        return classCompFrG;
    }

    public void setClassCompFrG(Long classCompFrG) {
        this.classCompFrG = classCompFrG;
    }

    public Long getNbreMoyInf10CompFrF() {
        return nbreMoyInf10CompFrF;
    }

    public void setNbreMoyInf10CompFrF(Long nbreMoyInf10CompFrF) {
        this.nbreMoyInf10CompFrF = nbreMoyInf10CompFrF;
    }

    public Long getNbreMoyInf10CompFrG() {
        return nbreMoyInf10CompFrG;
    }

    public void setNbreMoyInf10CompFrG(Long nbreMoyInf10CompFrG) {
        this.nbreMoyInf10CompFrG = nbreMoyInf10CompFrG;
    }

    public Double getPourMoyInf10CompFrF() {
        return pourMoyInf10CompFrF;
    }

    public void setPourMoyInf10CompFrF(Double pourMoyInf10CompFrF) {
        this.pourMoyInf10CompFrF = pourMoyInf10CompFrF;
    }

    public Double getPourMoyInf10CompFrG() {
        return pourMoyInf10CompFrG;
    }

    public void setPourMoyInf10CompFrG(Double pourMoyInf10CompFrG) {
        this.pourMoyInf10CompFrG = pourMoyInf10CompFrG;
    }

    public Long getClassOrthGramF() {
        return classOrthGramF;
    }

    public void setClassOrthGramF(Long classOrthGramF) {
        this.classOrthGramF = classOrthGramF;
    }

    public Long getClassOrthGramG() {
        return classOrthGramG;
    }

    public void setClassOrthGramG(Long classOrthGramG) {
        this.classOrthGramG = classOrthGramG;
    }

    public Long getNbreMoyInf10OrthGramF() {
        return nbreMoyInf10OrthGramF;
    }

    public void setNbreMoyInf10OrthGramF(Long nbreMoyInf10OrthGramF) {
        this.nbreMoyInf10OrthGramF = nbreMoyInf10OrthGramF;
    }

    public Long getNbreMoyInf10OrthGramG() {
        return nbreMoyInf10OrthGramG;
    }

    public void setNbreMoyInf10OrthGramG(Long nbreMoyInf10OrthGramG) {
        this.nbreMoyInf10OrthGramG = nbreMoyInf10OrthGramG;
    }

    public Double getPourMoyInf10OrthGramF() {
        return pourMoyInf10OrthGramF;
    }

    public void setPourMoyInf10OrthGramF(Double pourMoyInf10OrthGramF) {
        this.pourMoyInf10OrthGramF = pourMoyInf10OrthGramF;
    }

    public Double getPourMoyInf10OrthGramG() {
        return pourMoyInf10OrthGramG;
    }

    public void setPourMoyInf10OrthGramG(Double pourMoyInf10OrthGramG) {
        this.pourMoyInf10OrthGramG = pourMoyInf10OrthGramG;
    }

    public Long getClassAngF() {
        return classAngF;
    }

    public void setClassAngF(Long classAngF) {
        this.classAngF = classAngF;
    }

    public Long getClassAngG() {
        return classAngG;
    }

    public void setClassAngG(Long classAngG) {
        this.classAngG = classAngG;
    }

    public Long getNbreMoyInf10AngF() {
        return nbreMoyInf10AngF;
    }

    public void setNbreMoyInf10AngF(Long nbreMoyInf10AngF) {
        this.nbreMoyInf10AngF = nbreMoyInf10AngF;
    }

    public Long getNbreMoyInf10AngG() {
        return nbreMoyInf10AngG;
    }

    public void setNbreMoyInf10AngG(Long nbreMoyInf10AngG) {
        this.nbreMoyInf10AngG = nbreMoyInf10AngG;
    }

    public Double getPourMoyInf10AngF() {
        return pourMoyInf10AngF;
    }

    public void setPourMoyInf10AngF(Double pourMoyInf10AngF) {
        this.pourMoyInf10AngF = pourMoyInf10AngF;
    }

    public Double getPourMoyInf10AngG() {
        return pourMoyInf10AngG;
    }

    public void setPourMoyInf10AngG(Double pourMoyInf10AngG) {
        this.pourMoyInf10AngG = pourMoyInf10AngG;
    }

    public Long getClassAllF() {
        return classAllF;
    }

    public void setClassAllF(Long classAllF) {
        this.classAllF = classAllF;
    }

    public Long getClassAllG() {
        return classAllG;
    }

    public void setClassAllG(Long classAllG) {
        this.classAllG = classAllG;
    }

    public Long getNbreMoyInf10AllF() {
        return nbreMoyInf10AllF;
    }

    public void setNbreMoyInf10AllF(Long nbreMoyInf10AllF) {
        this.nbreMoyInf10AllF = nbreMoyInf10AllF;
    }

    public Long getNbreMoyInf10AllG() {
        return nbreMoyInf10AllG;
    }

    public void setNbreMoyInf10AllG(Long nbreMoyInf10AllG) {
        this.nbreMoyInf10AllG = nbreMoyInf10AllG;
    }

    public Double getPourMoyInf10AllF() {
        return pourMoyInf10AllF;
    }

    public void setPourMoyInf10AllF(Double pourMoyInf10AllF) {
        this.pourMoyInf10AllF = pourMoyInf10AllF;
    }

    public Double getPourMoyInf10AllG() {
        return pourMoyInf10AllG;
    }

    public void setPourMoyInf10AllG(Double pourMoyInf10AllG) {
        this.pourMoyInf10AllG = pourMoyInf10AllG;
    }

    public Long getClassEspF() {
        return classEspF;
    }

    public void setClassEspF(Long classEspF) {
        this.classEspF = classEspF;
    }

    public Long getClassEspG() {
        return classEspG;
    }

    public void setClassEspG(Long classEspG) {
        this.classEspG = classEspG;
    }

    public Long getNbreMoyInf10EspF() {
        return nbreMoyInf10EspF;
    }

    public void setNbreMoyInf10EspF(Long nbreMoyInf10EspF) {
        this.nbreMoyInf10EspF = nbreMoyInf10EspF;
    }

    public Long getNbreMoyInf10EspG() {
        return nbreMoyInf10EspG;
    }

    public void setNbreMoyInf10EspG(Long nbreMoyInf10EspG) {
        this.nbreMoyInf10EspG = nbreMoyInf10EspG;
    }

    public Double getPourMoyInf10EspF() {
        return pourMoyInf10EspF;
    }

    public void setPourMoyInf10EspF(Double pourMoyInf10EspF) {
        this.pourMoyInf10EspF = pourMoyInf10EspF;
    }

    public Double getPourMoyInf10EspG() {
        return pourMoyInf10EspG;
    }

    public void setPourMoyInf10EspG(Double pourMoyInf10EspG) {
        this.pourMoyInf10EspG = pourMoyInf10EspG;
    }

    public Long getClassHgF() {
        return classHgF;
    }

    public void setClassHgF(Long classHgF) {
        this.classHgF = classHgF;
    }

    public Long getClassHgG() {
        return classHgG;
    }

    public void setClassHgG(Long classHgG) {
        this.classHgG = classHgG;
    }

    public Long getNbreMoyInf10HgF() {
        return nbreMoyInf10HgF;
    }

    public void setNbreMoyInf10HgF(Long nbreMoyInf10HgF) {
        this.nbreMoyInf10HgF = nbreMoyInf10HgF;
    }

    public Long getNbreMoyInf10HgG() {
        return nbreMoyInf10HgG;
    }

    public void setNbreMoyInf10HgG(Long nbreMoyInf10HgG) {
        this.nbreMoyInf10HgG = nbreMoyInf10HgG;
    }

    public Double getPourMoyInf10HgF() {
        return pourMoyInf10HgF;
    }

    public void setPourMoyInf10HgF(Double pourMoyInf10HgF) {
        this.pourMoyInf10HgF = pourMoyInf10HgF;
    }

    public Double getPourMoyInf10HgG() {
        return pourMoyInf10HgG;
    }

    public void setPourMoyInf10HgG(Double pourMoyInf10HgG) {
        this.pourMoyInf10HgG = pourMoyInf10HgG;
    }

    public void setPourMoyInf10MathF(Double pourMoyInf10MathF) {
        this.pourMoyInf10MathF = pourMoyInf10MathF;
    }

    public Double getPourMoyInf10MathG() {
        return pourMoyInf10MathG;
    }

    public void setPourMoyInf10MathG(Double pourMoyInf10MathG) {
        this.pourMoyInf10MathG = pourMoyInf10MathG;
    }

    public Double getMoyClasseF() {
        return moyClasseF;
    }

    public void setMoyClasseF(Double moyClasseF) {
        this.moyClasseF = moyClasseF;
    }

    public Double getMoyClasseG() {
        return moyClasseG;
    }

    public void setMoyClasseG(Double moyClasseG) {
        this.moyClasseG = moyClasseG;
    }
}
