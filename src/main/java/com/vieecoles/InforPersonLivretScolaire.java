package com.vieecoles;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class InforPersonLivretScolaire extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  id ;
    private String  idBulletin ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] photo_eleve ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] logo ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] amoirie ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] bg ;
    private Double TmoyFr ;
    private Double TcoefFr ;
    private Double TmoyCoefFr ;
    private Double TmoyCoefEMR ;
    private int TrangEMR ;
    private Double moy_1er_trim ;
    private Double moy_2eme_trim ;
    private Double moy_3eme_trim ;
    private Integer rang_1er_trim ;
    private Integer rang_2eme_trim ;
    private Integer rang_3eme_trim ;
    private int TrangFr ;
    private String  codeEcole ;
    private String  is_class_1er_trim ;
    private String  is_class_2e_trim ;
    private String  is_class_3e_trim ;
    private Double    TmoyFrPremier ;
    private Double    TmoyFrDeuxieme ;
    private Double    TmoyFrAnn ;

    private int    TrangFrAnnuel ;
    private Double    TmoyEMRANN ;
    private int       TrangFrPremier ;
    private int       TrangFrDeuxieme ;
    private int       TrangEMRPremier ;
    private int       TrangEMRDeuxieme ;
    private Double    TmoyCoefEMRPremier ;
    private Double    TmoyCoefEMRDeuxieme ;
    private  String   DateNaiss ;
    private String  matriculeEleve ;

    public String getMatriculeEleve() {
        return matriculeEleve;
    }

    public void setMatriculeEleve(String matriculeEleve) {
        this.matriculeEleve = matriculeEleve;
    }

    public Double getTmoyFrPremier() {
        return TmoyFrPremier;
    }

    public void setTmoyFrPremier(Double tmoyFrPremier) {
        TmoyFrPremier = tmoyFrPremier;
    }

    public Double getTmoyFrDeuxieme() {
        return TmoyFrDeuxieme;
    }

    public void setTmoyFrDeuxieme(Double tmoyFrDeuxieme) {
        TmoyFrDeuxieme = tmoyFrDeuxieme;
    }

    public Double getTmoyFrAnn() {
        return TmoyFrAnn;
    }

    public void setTmoyFrAnn(Double tmoyFrAnn) {
        TmoyFrAnn = tmoyFrAnn;
    }

    public int getTrangFrAnnuel() {
        return TrangFrAnnuel;
    }

    public void setTrangFrAnnuel(int trangFrAnnuel) {
        TrangFrAnnuel = trangFrAnnuel;
    }

    public Double getTmoyEMRANN() {
        return TmoyEMRANN;
    }

    public void setTmoyEMRANN(Double tmoyEMRANN) {
        TmoyEMRANN = tmoyEMRANN;
    }

    public int getTrangFrPremier() {
        return TrangFrPremier;
    }

    public void setTrangFrPremier(int trangFrPremier) {
        TrangFrPremier = trangFrPremier;
    }

    public int getTrangFrDeuxieme() {
        return TrangFrDeuxieme;
    }

    public void setTrangFrDeuxieme(int trangFrDeuxieme) {
        TrangFrDeuxieme = trangFrDeuxieme;
    }

    public int getTrangEMRPremier() {
        return TrangEMRPremier;
    }

    public void setTrangEMRPremier(int trangEMRPremier) {
        TrangEMRPremier = trangEMRPremier;
    }

    public int getTrangEMRDeuxieme() {
        return TrangEMRDeuxieme;
    }

    public void setTrangEMRDeuxieme(int trangEMRDeuxieme) {
        TrangEMRDeuxieme = trangEMRDeuxieme;
    }

    public Double getTmoyCoefEMRPremier() {
        return TmoyCoefEMRPremier;
    }

    public void setTmoyCoefEMRPremier(Double tmoyCoefEMRPremier) {
        TmoyCoefEMRPremier = tmoyCoefEMRPremier;
    }

    public Double getTmoyCoefEMRDeuxieme() {
        return TmoyCoefEMRDeuxieme;
    }

    public void setTmoyCoefEMRDeuxieme(Double tmoyCoefEMRDeuxieme) {
        TmoyCoefEMRDeuxieme = tmoyCoefEMRDeuxieme;
    }

    public String getDateNaiss() {
        return DateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        DateNaiss = dateNaiss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdBulletin() {
        return idBulletin;
    }

    public void setIdBulletin(String idBulletin) {
        this.idBulletin = idBulletin;
    }

    public byte[] getPhoto_eleve() {
        return photo_eleve;
    }

    public void setPhoto_eleve(byte[] photo_eleve) {
        this.photo_eleve = photo_eleve;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public byte[] getAmoirie() {
        return amoirie;
    }

    public byte[] getBg() {
        return bg;
    }

    public void setBg(byte[] bg) {
        this.bg = bg;
    }

    public void setAmoirie(byte[] amoirie) {
        this.amoirie = amoirie;
    }

    public Double getTmoyFr() {
        return TmoyFr;
    }

    public void setTmoyFr(Double tmoyFr) {
        TmoyFr = tmoyFr;
    }

    public Double getTcoefFr() {
        return TcoefFr;
    }

    public void setTcoefFr(Double tcoefFr) {
        TcoefFr = tcoefFr;
    }

    public Double getTmoyCoefFr() {
        return TmoyCoefFr;
    }

    public void setTmoyCoefFr(Double tmoyCoefFr) {
        TmoyCoefFr = tmoyCoefFr;
    }

    public Double getTmoyCoefEMR() {
        return TmoyCoefEMR;
    }

    public void setTmoyCoefEMR(Double tmoyCoefEMR) {
        TmoyCoefEMR = tmoyCoefEMR;
    }

    public int getTrangEMR() {
        return TrangEMR;
    }

    public void setTrangEMR(int trangEMR) {
        TrangEMR = trangEMR;
    }

    public Double getMoy_1er_trim() {
        return moy_1er_trim;
    }

    public void setMoy_1er_trim(Double moy_1er_trim) {
        this.moy_1er_trim = moy_1er_trim;
    }

    public Double getMoy_2eme_trim() {
        return moy_2eme_trim;
    }

    public void setMoy_2eme_trim(Double moy_2eme_trim) {
        this.moy_2eme_trim = moy_2eme_trim;
    }

    public Double getMoy_3eme_trim() {
        return moy_3eme_trim;
    }

    public void setMoy_3eme_trim(Double moy_3eme_trim) {
        this.moy_3eme_trim = moy_3eme_trim;
    }

    public Integer getRang_1er_trim() {
        return rang_1er_trim;
    }

    public void setRang_1er_trim(Integer rang_1er_trim) {
        this.rang_1er_trim = rang_1er_trim;
    }

    public Integer getRang_2eme_trim() {
        return rang_2eme_trim;
    }

    public void setRang_2eme_trim(Integer rang_2eme_trim) {
        this.rang_2eme_trim = rang_2eme_trim;
    }

    public Integer getRang_3eme_trim() {
        return rang_3eme_trim;
    }

    public void setRang_3eme_trim(Integer rang_3eme_trim) {
        this.rang_3eme_trim = rang_3eme_trim;
    }

    public int getTrangFr() {
        return TrangFr;
    }

    public void setTrangFr(int trangFr) {
        TrangFr = trangFr;
    }

    public String getCodeEcole() {
        return codeEcole;
    }

    public void setCodeEcole(String codeEcole) {
        this.codeEcole = codeEcole;
    }

    public String getIs_class_1er_trim() {
        return is_class_1er_trim;
    }

    public void setIs_class_1er_trim(String is_class_1er_trim) {
        this.is_class_1er_trim = is_class_1er_trim;
    }

    public String getIs_class_2e_trim() {
        return is_class_2e_trim;
    }

    public void setIs_class_2e_trim(String is_class_2e_trim) {
        this.is_class_2e_trim = is_class_2e_trim;
    }

    public String getIs_class_3e_trim() {
        return is_class_3e_trim;
    }

    public void setIs_class_3e_trim(String is_class_3e_trim) {
        this.is_class_3e_trim = is_class_3e_trim;
    }
}
