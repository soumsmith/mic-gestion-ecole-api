package com.vieecoles.dto;

public class RecapResultatsElevesAffecteDto {
    private String niveau ;
    private String classe;
    private String sexe;
    private Integer effeF;
    private Integer effeG;
    private Integer classF;
    private Integer classG;
    private Integer nonclassF;
    private Integer nonclassG;
    private Integer nbreMoySup10F;
    private Integer nbreMoySup10G;
    private Double pourMoySup10F;
    private Double pourMoySup10G;
    private Integer nbreMoyInf999F;
    private Double pourMoyInf999F;
    private Integer nbreMoyInf999G;
    private Double pourMoyInf999G;
    private Integer nbreMoyInf85G;
    private Double pourMoyInf85G;
    private Integer nbreMoyInf85F;
    private Double pourMoyInf85F;
    private Double moyClasseF ;
    private Double moyClasseG ;

    public RecapResultatsElevesAffecteDto() {
    }

    public RecapResultatsElevesAffecteDto(String niveau,
                                          String classe,
                                          String sexe,
                                          Integer effeF,
                                          Integer effeG,
                                          Integer classF,
                                          Integer classG,
                                          Integer nonclassF,
                                          Integer nonclassG,
                                          Integer nbreMoySup10F,
                                          Integer nbreMoySup10G,
                                          Double pourMoySup10F,
                                          Double pourMoySup10G,
                                          Integer nbreMoyInf999F,
                                          Double pourMoyInf999F,
                                          Integer nbreMoyInf999G,
                                          Double pourMoyInf999G,
                                          Integer nbreMoyInf85G,
                                          Double pourMoyInf85G,
                                          Integer nbreMoyInf85F,
                                          Double pourMoyInf85F,
                                          Double moyClasseF,
                                          Double moyClasseG) {
        this.niveau = niveau;
        this.classe = classe;
        this.sexe = sexe;
        this.effeF = effeF;
        this.effeG = effeG;
        this.classF = classF;
        this.classG = classG;
        this.nonclassF = nonclassF;
        this.nonclassG = nonclassG;
        this.nbreMoySup10F = nbreMoySup10F;
        this.nbreMoySup10G = nbreMoySup10G;
        this.pourMoySup10F = pourMoySup10F;
        this.pourMoySup10G = pourMoySup10G;
        this.nbreMoyInf999F = nbreMoyInf999F;
        this.pourMoyInf999F = pourMoyInf999F;
        this.nbreMoyInf999G = nbreMoyInf999G;
        this.pourMoyInf999G = pourMoyInf999G;
        this.nbreMoyInf85G = nbreMoyInf85G;
        this.pourMoyInf85G = pourMoyInf85G;
        this.nbreMoyInf85F = nbreMoyInf85F;
        this.pourMoyInf85F = pourMoyInf85F;
        this.moyClasseF = moyClasseF;
        this.moyClasseG = moyClasseG;
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Integer getEffeF() {
        return effeF;
    }

    public void setEffeF(Integer effeF) {
        this.effeF = effeF;
    }

    public Integer getEffeG() {
        return effeG;
    }

    public void setEffeG(Integer effeG) {
        this.effeG = effeG;
    }

    public Integer getClassF() {
        return classF;
    }

    public void setClassF(Integer classF) {
        this.classF = classF;
    }

    public Integer getClassG() {
        return classG;
    }

    public void setClassG(Integer classG) {
        this.classG = classG;
    }

    public Integer getNonclassF() {
        return nonclassF;
    }

    public void setNonclassF(Integer nonclassF) {
        this.nonclassF = nonclassF;
    }

    public Integer getNonclassG() {
        return nonclassG;
    }

    public void setNonclassG(Integer nonclassG) {
        this.nonclassG = nonclassG;
    }

    public Integer getNbreMoySup10F() {
        return nbreMoySup10F;
    }

    public void setNbreMoySup10F(Integer nbreMoySup10F) {
        this.nbreMoySup10F = nbreMoySup10F;
    }

    public Integer getNbreMoySup10G() {
        return nbreMoySup10G;
    }

    public void setNbreMoySup10G(Integer nbreMoySup10G) {
        this.nbreMoySup10G = nbreMoySup10G;
    }

    public Double getPourMoySup10F() {
        return pourMoySup10F;
    }

    public void setPourMoySup10F(Double pourMoySup10F) {
        this.pourMoySup10F = pourMoySup10F;
    }

    public Double getPourMoySup10G() {
        return pourMoySup10G;
    }

    public void setPourMoySup10G(Double pourMoySup10G) {
        this.pourMoySup10G = pourMoySup10G;
    }

    public Integer getNbreMoyInf999F() {
        return nbreMoyInf999F;
    }

    public void setNbreMoyInf999F(Integer nbreMoyInf999F) {
        this.nbreMoyInf999F = nbreMoyInf999F;
    }

    public Double getPourMoyInf999F() {
        return pourMoyInf999F;
    }

    public void setPourMoyInf999F(Double pourMoyInf999F) {
        this.pourMoyInf999F = pourMoyInf999F;
    }

    public Integer getNbreMoyInf999G() {
        return nbreMoyInf999G;
    }

    public void setNbreMoyInf999G(Integer nbreMoyInf999G) {
        this.nbreMoyInf999G = nbreMoyInf999G;
    }

    public Double getPourMoyInf999G() {
        return pourMoyInf999G;
    }

    public void setPourMoyInf999G(Double pourMoyInf999G) {
        this.pourMoyInf999G = pourMoyInf999G;
    }

    public Integer getNbreMoyInf85G() {
        return nbreMoyInf85G;
    }

    public void setNbreMoyInf85G(Integer nbreMoyInf85G) {
        this.nbreMoyInf85G = nbreMoyInf85G;
    }

    public Double getPourMoyInf85G() {
        return pourMoyInf85G;
    }

    public void setPourMoyInf85G(Double pourMoyInf85G) {
        this.pourMoyInf85G = pourMoyInf85G;
    }

    public Integer getNbreMoyInf85F() {
        return nbreMoyInf85F;
    }

    public void setNbreMoyInf85F(Integer nbreMoyInf85F) {
        this.nbreMoyInf85F = nbreMoyInf85F;
    }

    public Double getPourMoyInf85F() {
        return pourMoyInf85F;
    }

    public void setPourMoyInf85F(Double pourMoyInf85F) {
        this.pourMoyInf85F = pourMoyInf85F;
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
