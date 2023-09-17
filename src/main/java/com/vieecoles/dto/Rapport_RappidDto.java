package com.vieecoles.dto;

import java.util.List;

public class Rapport_RappidDto {
    private Boolean perAdmi ;
    private Boolean conseilInter ;
    private Boolean conseilProfesseur ;
    private Boolean conseilProfesPrincip ;
    private Boolean conseilEnseigne ;
    private Boolean professClassExame ;
    private Boolean parentsEleve ;
    private Boolean perAdmichefClasse ;
    private Boolean unitePedagogi ;
    private  Boolean chefClasse ;
    private  Long stClasse ;
    private  Double pourAFF6 ;
    private  Double pourAFF5 ;
    private  Double pourAFF4 ;
    private  Double pourAFF3 ;
    private  Double pourAFF2A ;
    private  Double pourAFF2C ;
    private  Double pourAFF1A ;
    private  Double pourAFF1C ;
    private  Double pourAFF1D ;
    private  Double pourAFFTA;
    private  Double pourAFFTC ;
    private  Double pourAFFTD ;

    private  Double pourNAFF6 ;
    private  Double pourNAFF5 ;
    private  Double pourNAFF4 ;
    private  Double pourNAFF3 ;
    private  Double pourNAFF2A ;
    private  Double pourNAFF2C ;
    private  Double pourNAFF1A ;
    private  Double pourNAFF1C ;
    private  Double pourNAFF1D ;
    private  Double pourNAFFTA;
    private  Double pourNAFFTC ;
    private  Double pourNAFFTD ;
    private  Long stAttentAff ;
    private  Long stAttentNAff ;
    private  Long stpresentAff ;
    private  Long stpresentNAff ;
    private  Double pourAff ;
    private  Double pourNAff ;

    public Double getPourAff() {
        return pourAff;
    }

    public void setPourAff(Double pourAff) {
        this.pourAff = pourAff;
    }

    public Double getPourNAff() {
        return pourNAff;
    }

    public void setPourNAff(Double pourNAff) {
        this.pourNAff = pourNAff;
    }

    public Long getStAttentAff() {
        return stAttentAff;
    }

    public void setStAttentAff(Long stAttentAff) {
        this.stAttentAff = stAttentAff;
    }

    public Long getStAttentNAff() {
        return stAttentNAff;
    }

    public void setStAttentNAff(Long stAttentNAff) {
        this.stAttentNAff = stAttentNAff;
    }

    public Long getStpresentAff() {
        return stpresentAff;
    }

    public void setStpresentAff(Long stpresentAff) {
        this.stpresentAff = stpresentAff;
    }

    public Long getStpresentNAff() {
        return stpresentNAff;
    }

    public void setStpresentNAff(Long stpresentNAff) {
        this.stpresentNAff = stpresentNAff;
    }

    public Double getPourNAFF6() {
        return pourNAFF6;
    }

    public void setPourNAFF6(Double pourNAFF6) {
        this.pourNAFF6 = pourNAFF6;
    }

    public Double getPourNAFF5() {
        return pourNAFF5;
    }

    public void setPourNAFF5(Double pourNAFF5) {
        this.pourNAFF5 = pourNAFF5;
    }

    public Double getPourNAFF4() {
        return pourNAFF4;
    }

    public void setPourNAFF4(Double pourNAFF4) {
        this.pourNAFF4 = pourNAFF4;
    }

    public Double getPourNAFF3() {
        return pourNAFF3;
    }

    public void setPourNAFF3(Double pourNAFF3) {
        this.pourNAFF3 = pourNAFF3;
    }

    public Double getPourNAFF2A() {
        return pourNAFF2A;
    }

    public void setPourNAFF2A(Double pourNAFF2A) {
        this.pourNAFF2A = pourNAFF2A;
    }

    public Double getPourNAFF2C() {
        return pourNAFF2C;
    }

    public void setPourNAFF2C(Double pourNAFF2C) {
        this.pourNAFF2C = pourNAFF2C;
    }

    public Double getPourNAFF1A() {
        return pourNAFF1A;
    }

    public void setPourNAFF1A(Double pourNAFF1A) {
        this.pourNAFF1A = pourNAFF1A;
    }

    public Double getPourNAFF1C() {
        return pourNAFF1C;
    }

    public void setPourNAFF1C(Double pourNAFF1C) {
        this.pourNAFF1C = pourNAFF1C;
    }

    public Double getPourNAFF1D() {
        return pourNAFF1D;
    }

    public void setPourNAFF1D(Double pourNAFF1D) {
        this.pourNAFF1D = pourNAFF1D;
    }

    public Double getPourNAFFTA() {
        return pourNAFFTA;
    }

    public void setPourNAFFTA(Double pourNAFFTA) {
        this.pourNAFFTA = pourNAFFTA;
    }

    public Double getPourNAFFTC() {
        return pourNAFFTC;
    }

    public void setPourNAFFTC(Double pourNAFFTC) {
        this.pourNAFFTC = pourNAFFTC;
    }

    public Double getPourNAFFTD() {
        return pourNAFFTD;
    }

    public void setPourNAFFTD(Double pourNAFFTD) {
        this.pourNAFFTD = pourNAFFTD;
    }

    public Double getPourAFF6() {
        return pourAFF6;
    }

    public void setPourAFF6(Double pourAFF6) {
        this.pourAFF6 = pourAFF6;
    }

    public Double getPourAFF5() {
        return pourAFF5;
    }

    public void setPourAFF5(Double pourAFF5) {
        this.pourAFF5 = pourAFF5;
    }

    public Double getPourAFF4() {
        return pourAFF4;
    }

    public void setPourAFF4(Double pourAFF4) {
        this.pourAFF4 = pourAFF4;
    }

    public Double getPourAFF3() {
        return pourAFF3;
    }

    public void setPourAFF3(Double pourAFF3) {
        this.pourAFF3 = pourAFF3;
    }

    public Double getPourAFF2A() {
        return pourAFF2A;
    }

    public void setPourAFF2A(Double pourAFF2A) {
        this.pourAFF2A = pourAFF2A;
    }

    public Double getPourAFF2C() {
        return pourAFF2C;
    }

    public void setPourAFF2C(Double pourAFF2C) {
        this.pourAFF2C = pourAFF2C;
    }

    public Double getPourAFF1A() {
        return pourAFF1A;
    }

    public void setPourAFF1A(Double pourAFF1A) {
        this.pourAFF1A = pourAFF1A;
    }

    public Double getPourAFF1C() {
        return pourAFF1C;
    }

    public void setPourAFF1C(Double pourAFF1C) {
        this.pourAFF1C = pourAFF1C;
    }

    public Double getPourAFF1D() {
        return pourAFF1D;
    }

    public void setPourAFF1D(Double pourAFF1D) {
        this.pourAFF1D = pourAFF1D;
    }

    public Double getPourAFFTA() {
        return pourAFFTA;
    }

    public void setPourAFFTA(Double pourAFFTA) {
        this.pourAFFTA = pourAFFTA;
    }

    public Double getPourAFFTC() {
        return pourAFFTC;
    }

    public void setPourAFFTC(Double pourAFFTC) {
        this.pourAFFTC = pourAFFTC;
    }

    public Double getPourAFFTD() {
        return pourAFFTD;
    }

    public void setPourAFFTD(Double pourAFFTD) {
        this.pourAFFTD = pourAFFTD;
    }

    public Long getStClasse() {
        return stClasse;
    }

    public void setStClasse(Long stClasse) {
        this.stClasse = stClasse;
    }

    public Boolean getChefClasse() {
        return chefClasse;
    }

    public void setChefClasse(Boolean chefClasse) {
        this.chefClasse = chefClasse;
    }

    private List<ClasseParNiveauDto> classeParNiveauDto;
    private List<AffecteParNiveauDto> affecteParNiveauDto;
    private List<NonAffecteParNiveau> nonAffecteParNiveau;

    public Rapport_RappidDto() {
    }

    public Boolean getPerAdmi() {
        return perAdmi;
    }

    public void setPerAdmi(Boolean perAdmi) {
        this.perAdmi = perAdmi;
    }

    public Boolean getConseilInter() {
        return conseilInter;
    }

    public void setConseilInter(Boolean conseilInter) {
        this.conseilInter = conseilInter;
    }

    public Boolean getConseilProfesseur() {
        return conseilProfesseur;
    }

    public void setConseilProfesseur(Boolean conseilProfesseur) {
        this.conseilProfesseur = conseilProfesseur;
    }

    public Boolean getConseilProfesPrincip() {
        return conseilProfesPrincip;
    }

    public void setConseilProfesPrincip(Boolean conseilProfesPrincip) {
        this.conseilProfesPrincip = conseilProfesPrincip;
    }

    public Boolean getConseilEnseigne() {
        return conseilEnseigne;
    }

    public void setConseilEnseigne(Boolean conseilEnseigne) {
        this.conseilEnseigne = conseilEnseigne;
    }

    public Boolean getProfessClassExame() {
        return professClassExame;
    }

    public void setProfessClassExame(Boolean professClassExame) {
        this.professClassExame = professClassExame;
    }

    public Boolean getParentsEleve() {
        return parentsEleve;
    }

    public void setParentsEleve(Boolean parentsEleve) {
        this.parentsEleve = parentsEleve;
    }

    public Boolean getPerAdmichefClasse() {
        return perAdmichefClasse;
    }

    public void setPerAdmichefClasse(Boolean perAdmichefClasse) {
        this.perAdmichefClasse = perAdmichefClasse;
    }

    public Boolean getUnitePedagogi() {
        return unitePedagogi;
    }

    public void setUnitePedagogi(Boolean unitePedagogi) {
        this.unitePedagogi = unitePedagogi;
    }



    public List<ClasseParNiveauDto> getClasseParNiveauDto() {
        return classeParNiveauDto;
    }

    public void setClasseParNiveauDto(List<ClasseParNiveauDto> classeParNiveauDto) {
        this.classeParNiveauDto = classeParNiveauDto;
    }

    public List<AffecteParNiveauDto> getAffecteParNiveauDto() {
        return affecteParNiveauDto;
    }

    public void setAffecteParNiveauDto(List<AffecteParNiveauDto> affecteParNiveauDto) {
        this.affecteParNiveauDto = affecteParNiveauDto;
    }

    public List<NonAffecteParNiveau> getNonAffecteParNiveau() {
        return nonAffecteParNiveau;
    }

    public void setNonAffecteParNiveau(List<NonAffecteParNiveau> nonAffecteParNiveau) {
        this.nonAffecteParNiveau = nonAffecteParNiveau;
    }
}
