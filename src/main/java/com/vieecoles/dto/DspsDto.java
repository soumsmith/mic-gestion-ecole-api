package com.vieecoles.dto;

public class DspsDto {
    private String matricule ;
    private String nom ;
    private String prenoms;
       private String niveau ;
    private  Integer ordre_niveau ;
   private  Integer rang ;
    private  Double moyenTrim ;
    private Double moyenCompoFr;
    private  String is_clasCompoFr ;
    private Double moyenOrthoGram;
    private  String is_clasOrthoGram ;
    private Double moyenExpreOral;
    private  String is_clasExpreOral ;
    private Double moyenphiloso;
    private  String is_clasphiloso ;
    private Double moyenAng;
    private  String is_clasAng ;
    private Double moyenMath;
    private  String is_clasMath ;
    private Double moyenPhysiq;
    private  String is_clasPhysiq ;
    private Double moyenSVT;
    private  String is_clasSVT ;
    private Double moyenHg;
    private  String is_clasHg ;
    private Double moyenLv2;
    private  String is_clasLv2 ;
    private Double moyenEdhc;
    private  String is_clasEdhc ;
    private Double moyenArplat;
    private  String is_clasArplat ;
    private Double moyenTic;
    private  String is_clasTic ;
    private Double moyenConduite;
    private  String is_clasConduite ;
    private Double moyenEps;
    private  String is_clasEps ;

    private  String is_class_periode ;

    private  String nom_ecole  ;

    private Double moyen_fr;
    private  String is_class_fr ;

    public Double getMoyen_fr() {
        return moyen_fr;
    }

    public void setMoyen_fr(Double moyen_fr) {
        this.moyen_fr = moyen_fr;
    }

    public String getIs_class_fr() {
        return is_class_fr;
    }

    public void setIs_class_fr(String is_class_fr) {
        this.is_class_fr = is_class_fr;
    }

    public DspsDto() {
    }

    public String getNom_ecole() {
        return nom_ecole;
    }

    public void setNom_ecole(String nom_ecole) {
        this.nom_ecole = nom_ecole;
    }

    public DspsDto(String matricule, String nom, String prenoms, String niveau, Integer ordre_niveau,
                   Integer rang, Double moyenTrim, Double moyenCompoFr, String is_clasCompoFr,
                   Double moyenOrthoGram, String is_clasOrthoGram, Double moyenExpreOral,
                   String is_clasExpreOral, Double moyenphiloso, String is_clasphiloso, Double moyenAng,
                   String is_clasAng, Double moyenMath, String is_clasMath, Double moyenPhysiq,
                   String is_clasPhysiq, Double moyenSVT, String is_clasSVT, Double moyenHg,
                   String is_clasHg, Double moyenLv2, String is_clasLv2, Double moyenEdhc, String is_clasEdhc,
                   Double moyenArplat, String is_clasArplat, Double moyenTic, String is_clasTic, Double moyenConduite,
                   String is_clasConduite, Double moyenEps, String is_clasEps, String is_class_periode , String nom_ecole) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenoms = prenoms;
        this.niveau = niveau;
        this.ordre_niveau = ordre_niveau;

        this.rang = rang;
        this.moyenTrim = moyenTrim;
        this.moyenCompoFr = moyenCompoFr;
        this.is_clasCompoFr = is_clasCompoFr;
        this.moyenOrthoGram = moyenOrthoGram;
        this.is_clasOrthoGram = is_clasOrthoGram;
        this.moyenExpreOral = moyenExpreOral;
        this.is_clasExpreOral = is_clasExpreOral;
        this.moyenphiloso = moyenphiloso;
        this.is_clasphiloso = is_clasphiloso;
        this.moyenAng = moyenAng;
        this.is_clasAng = is_clasAng;
        this.moyenMath = moyenMath;
        this.is_clasMath = is_clasMath;
        this.moyenPhysiq = moyenPhysiq;
        this.is_clasPhysiq = is_clasPhysiq;
        this.moyenSVT = moyenSVT;
        this.is_clasSVT = is_clasSVT;
        this.moyenHg = moyenHg;
        this.is_clasHg = is_clasHg;
        this.moyenLv2 = moyenLv2;
        this.is_clasLv2 = is_clasLv2;
        this.moyenEdhc = moyenEdhc;
        this.is_clasEdhc = is_clasEdhc;
        this.moyenArplat = moyenArplat;
        this.is_clasArplat = is_clasArplat;
        this.moyenTic = moyenTic;
        this.is_clasTic = is_clasTic;
        this.moyenConduite = moyenConduite;
        this.is_clasConduite = is_clasConduite;
        this.moyenEps = moyenEps;
        this.is_clasEps = is_clasEps;
        this.is_class_periode = is_class_periode;
        this.nom_ecole = nom_ecole ;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }



    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Integer getOrdre_niveau() {
        return ordre_niveau;
    }

    public void setOrdre_niveau(Integer ordre_niveau) {
        this.ordre_niveau = ordre_niveau;
    }


    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public Double getMoyenTrim() {
        return moyenTrim;
    }

    public void setMoyenTrim(Double moyenTrim) {
        this.moyenTrim = moyenTrim;
    }

    public Double getMoyenCompoFr() {
        return moyenCompoFr;
    }

    public void setMoyenCompoFr(Double moyenCompoFr) {
        this.moyenCompoFr = moyenCompoFr;
    }

    public String getIs_clasCompoFr() {
        return is_clasCompoFr;
    }

    public void setIs_clasCompoFr(String is_clasCompoFr) {
        this.is_clasCompoFr = is_clasCompoFr;
    }

    public Double getMoyenOrthoGram() {
        return moyenOrthoGram;
    }

    public void setMoyenOrthoGram(Double moyenOrthoGram) {
        this.moyenOrthoGram = moyenOrthoGram;
    }

    public String getIs_clasOrthoGram() {
        return is_clasOrthoGram;
    }

    public void setIs_clasOrthoGram(String is_clasOrthoGram) {
        this.is_clasOrthoGram = is_clasOrthoGram;
    }

    public Double getMoyenExpreOral() {
        return moyenExpreOral;
    }

    public void setMoyenExpreOral(Double moyenExpreOral) {
        this.moyenExpreOral = moyenExpreOral;
    }

    public String getIs_clasExpreOral() {
        return is_clasExpreOral;
    }

    public void setIs_clasExpreOral(String is_clasExpreOral) {
        this.is_clasExpreOral = is_clasExpreOral;
    }

    public Double getMoyenphiloso() {
        return moyenphiloso;
    }

    public void setMoyenphiloso(Double moyenphiloso) {
        this.moyenphiloso = moyenphiloso;
    }

    public String getIs_clasphiloso() {
        return is_clasphiloso;
    }

    public void setIs_clasphiloso(String is_clasphiloso) {
        this.is_clasphiloso = is_clasphiloso;
    }

    public Double getMoyenAng() {
        return moyenAng;
    }

    public void setMoyenAng(Double moyenAng) {
        this.moyenAng = moyenAng;
    }

    public String getIs_clasAng() {
        return is_clasAng;
    }

    public void setIs_clasAng(String is_clasAng) {
        this.is_clasAng = is_clasAng;
    }

    public Double getMoyenMath() {
        return moyenMath;
    }

    public void setMoyenMath(Double moyenMath) {
        this.moyenMath = moyenMath;
    }

    public String getIs_clasMath() {
        return is_clasMath;
    }

    public void setIs_clasMath(String is_clasMath) {
        this.is_clasMath = is_clasMath;
    }

    public Double getMoyenPhysiq() {
        return moyenPhysiq;
    }

    public void setMoyenPhysiq(Double moyenPhysiq) {
        this.moyenPhysiq = moyenPhysiq;
    }

    public String getIs_clasPhysiq() {
        return is_clasPhysiq;
    }

    public void setIs_clasPhysiq(String is_clasPhysiq) {
        this.is_clasPhysiq = is_clasPhysiq;
    }

    public Double getMoyenSVT() {
        return moyenSVT;
    }

    public void setMoyenSVT(Double moyenSVT) {
        this.moyenSVT = moyenSVT;
    }

    public String getIs_clasSVT() {
        return is_clasSVT;
    }

    public void setIs_clasSVT(String is_clasSVT) {
        this.is_clasSVT = is_clasSVT;
    }

    public Double getMoyenHg() {
        return moyenHg;
    }

    public void setMoyenHg(Double moyenHg) {
        this.moyenHg = moyenHg;
    }

    public String getIs_clasHg() {
        return is_clasHg;
    }

    public void setIs_clasHg(String is_clasHg) {
        this.is_clasHg = is_clasHg;
    }

    public Double getMoyenLv2() {
        return moyenLv2;
    }

    public void setMoyenLv2(Double moyenLv2) {
        this.moyenLv2 = moyenLv2;
    }

    public String getIs_clasLv2() {
        return is_clasLv2;
    }

    public void setIs_clasLv2(String is_clasLv2) {
        this.is_clasLv2 = is_clasLv2;
    }

    public Double getMoyenEdhc() {
        return moyenEdhc;
    }

    public void setMoyenEdhc(Double moyenEdhc) {
        this.moyenEdhc = moyenEdhc;
    }

    public String getIs_clasEdhc() {
        return is_clasEdhc;
    }

    public void setIs_clasEdhc(String is_clasEdhc) {
        this.is_clasEdhc = is_clasEdhc;
    }

    public Double getMoyenArplat() {
        return moyenArplat;
    }

    public void setMoyenArplat(Double moyenArplat) {
        this.moyenArplat = moyenArplat;
    }

    public String getIs_clasArplat() {
        return is_clasArplat;
    }

    public void setIs_clasArplat(String is_clasArplat) {
        this.is_clasArplat = is_clasArplat;
    }

    public Double getMoyenTic() {
        return moyenTic;
    }

    public void setMoyenTic(Double moyenTic) {
        this.moyenTic = moyenTic;
    }

    public String getIs_clasTic() {
        return is_clasTic;
    }

    public void setIs_clasTic(String is_clasTic) {
        this.is_clasTic = is_clasTic;
    }

    public Double getMoyenConduite() {
        return moyenConduite;
    }

    public void setMoyenConduite(Double moyenConduite) {
        this.moyenConduite = moyenConduite;
    }

    public String getIs_clasConduite() {
        return is_clasConduite;
    }

    public void setIs_clasConduite(String is_clasConduite) {
        this.is_clasConduite = is_clasConduite;
    }

    public Double getMoyenEps() {
        return moyenEps;
    }

    public void setMoyenEps(Double moyenEps) {
        this.moyenEps = moyenEps;
    }

    public String getIs_clasEps() {
        return is_clasEps;
    }

    public void setIs_clasEps(String is_clasEps) {
        this.is_clasEps = is_clasEps;
    }

    public String getIs_class_periode() {
        return is_class_periode;
    }

    public void setIs_class_periode(String is_class_periode) {
        this.is_class_periode = is_class_periode;
    }
}
