package com.vieecoles.dto;

public class ResultatsElevesNonAffecteDto {
    private String niveau ;
    private String classe;
    private Long effeF;
    private Long effeG;
    private Long classF;
    private Long classG;
    private Long nonclassF;
    private Long nonclassG;
    private Long nbreMoySup10F;
    private Long nbreMoySup10G;
    private Double pourMoySup10F;
    private Double pourMoySup10G;
    private Long nbreMoyInf999F;
    private Double pourMoyInf999F;
    private Long nbreMoyInf999G;
    private Double pourMoyInf999G;
    private Long nbreMoyInf85G;
    private Double pourMoyInf85G;
    private Long nbreMoyInf85F;
    private Double pourMoyInf85F;
    private Double moyClasseF ;
    private Double moyClasseG ;
    Integer ordre_niveau ;
    private  Double moyClasse ;
    private Double moyClasseFniv ;
    private Double moyClasseGniv ;
    private Double moyClasseniv ;

    public Double getMoyClasse() {
        return moyClasse;
    }

    public void setMoyClasse(Double moyClasse) {
        this.moyClasse = moyClasse;
    }

    public Double getMoyClasseFniv() {
        return moyClasseFniv;
    }

    public void setMoyClasseFniv(Double moyClasseFniv) {
        this.moyClasseFniv = moyClasseFniv;
    }

    public Double getMoyClasseGniv() {
        return moyClasseGniv;
    }

    public void setMoyClasseGniv(Double moyClasseGniv) {
        this.moyClasseGniv = moyClasseGniv;
    }

    public Double getMoyClasseniv() {
        return moyClasseniv;
    }

    public void setMoyClasseniv(Double moyClasseniv) {
        this.moyClasseniv = moyClasseniv;
    }

    public Integer getOrdre_niveau() {
        return ordre_niveau;
    }

    public void setOrdre_niveau(Integer ordre_niveau) {
        this.ordre_niveau = ordre_niveau;
    }

    public ResultatsElevesNonAffecteDto() {
    }

    public ResultatsElevesNonAffecteDto(String niveau,
                                        String classe,
                                        Long effeF,
                                        Long effeG,
                                        Long classF,
                                        Long classG,
                                        Long nonclassF,
                                        Long nonclassG,
                                        Long nbreMoySup10F,
                                        Long nbreMoySup10G,
                                        Double pourMoySup10F,
                                        Double pourMoySup10G,
                                        Long nbreMoyInf999F,
                                        Double pourMoyInf999F,
                                        Long nbreMoyInf999G,
                                        Double pourMoyInf999G,
                                        Long nbreMoyInf85G,
                                        Double pourMoyInf85G,
                                        Long nbreMoyInf85F,
                                        Double pourMoyInf85F,
                                        Double moyClasseF,
                                        Double moyClasseG,
                                      Integer  ordre_niveau,
                                        Double moyClasse,
                                        Double moyClasseFniv,
                                        Double moyClasseGniv,
                                        Double moyClasseniv
                                        ) {
        this.niveau = niveau;
        this.classe = classe;

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
        this.ordre_niveau = ordre_niveau ;
        this.moyClasse = moyClasse;
        this.moyClasseGniv =moyClasseGniv;
        this.moyClasseFniv =moyClasseFniv;
        this.moyClasseniv =moyClasseniv;
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





    public Long getEffeF() {
        return effeF;
    }

    public void setEffeF(Long effeF) {
        this.effeF = effeF;
    }

    public Long getEffeG() {
        return effeG;
    }

    public void setEffeG(Long effeG) {
        this.effeG = effeG;
    }

    public Long getClassF() {
        return classF;
    }

    public void setClassF(Long classF) {
        this.classF = classF;
    }

    public Long getClassG() {
        return classG;
    }

    public void setClassG(Long classG) {
        this.classG = classG;
    }

    public Long getNonclassF() {
        return nonclassF;
    }

    public void setNonclassF(Long nonclassF) {
        this.nonclassF = nonclassF;
    }

    public Long getNonclassG() {
        return nonclassG;
    }

    public void setNonclassG(Long nonclassG) {
        this.nonclassG = nonclassG;
    }

    public Long getNbreMoySup10F() {
        return nbreMoySup10F;
    }

    public void setNbreMoySup10F(Long nbreMoySup10F) {
        this.nbreMoySup10F = nbreMoySup10F;
    }

    public Long getNbreMoySup10G() {
        return nbreMoySup10G;
    }

    public void setNbreMoySup10G(Long nbreMoySup10G) {
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

    public Long getNbreMoyInf999F() {
        return nbreMoyInf999F;
    }

    public void setNbreMoyInf999F(Long nbreMoyInf999F) {
        this.nbreMoyInf999F = nbreMoyInf999F;
    }

    public Double getPourMoyInf999F() {
        return pourMoyInf999F;
    }

    public void setPourMoyInf999F(Double pourMoyInf999F) {
        this.pourMoyInf999F = pourMoyInf999F;
    }

    public Long getNbreMoyInf999G() {
        return nbreMoyInf999G;
    }

    public void setNbreMoyInf999G(Long nbreMoyInf999G) {
        this.nbreMoyInf999G = nbreMoyInf999G;
    }

    public Double getPourMoyInf999G() {
        return pourMoyInf999G;
    }

    public void setPourMoyInf999G(Double pourMoyInf999G) {
        this.pourMoyInf999G = pourMoyInf999G;
    }

    public Long getNbreMoyInf85G() {
        return nbreMoyInf85G;
    }

    public void setNbreMoyInf85G(Long nbreMoyInf85G) {
        this.nbreMoyInf85G = nbreMoyInf85G;
    }

    public Double getPourMoyInf85G() {
        return pourMoyInf85G;
    }

    public void setPourMoyInf85G(Double pourMoyInf85G) {
        this.pourMoyInf85G = pourMoyInf85G;
    }

    public Long getNbreMoyInf85F() {
        return nbreMoyInf85F;
    }

    public void setNbreMoyInf85F(Long nbreMoyInf85F) {
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

    @Override
    public String toString() {
        return "ResultatsElevesAffecteDto{" +
                "niveau='" + niveau + '\'' +
                ", classe='" + classe + '\'' +

                ", effeF=" + effeF +
                ", effeG=" + effeG +
                ", classF=" + classF +
                ", classG=" + classG +
                ", nonclassF=" + nonclassF +
                ", nonclassG=" + nonclassG +
                ", nbreMoySup10F=" + nbreMoySup10F +
                ", nbreMoySup10G=" + nbreMoySup10G +
                ", pourMoySup10F=" + pourMoySup10F +
                ", pourMoySup10G=" + pourMoySup10G +
                ", nbreMoyInf999F=" + nbreMoyInf999F +
                ", pourMoyInf999F=" + pourMoyInf999F +
                ", nbreMoyInf999G=" + nbreMoyInf999G +
                ", pourMoyInf999G=" + pourMoyInf999G +
                ", nbreMoyInf85G=" + nbreMoyInf85G +
                ", pourMoyInf85G=" + pourMoyInf85G +
                ", nbreMoyInf85F=" + nbreMoyInf85F +
                ", pourMoyInf85F=" + pourMoyInf85F +
                ", moyClasseF=" + moyClasseF +
                ", moyClasseG=" + moyClasseG +
                '}';
    }
}
