package com.vieecoles.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.vieecoles.entities.operations.Inscriptions;

public class souscriptionEcoleDto {
    private Long  idSouscr ;
    private  String codeSouscr;
    private  String nomEcoleSouscr;
    private  String nomfondateurSouscr;
    private String  prenomfondateurSouscr;
    private String  telfondateurSouscrl;
    private String  telfondateurSouscrl2;
    private  String ecoleindicationSouscrl ;

    private  Long  villeidSouscrl ;
    private  Long zoneidSouscrl  ;
    private Long fonctionidSouscrl ;
    private  String villeLibelle ;
    private  String zoneLibelle ;
    private  String fonctionLibelle ;
    private  String lienAutorisationEnseign;
    private String niveauEnseignement ;
    private Long idcommune ;
    private String libelleCommune ;
    private  String sousc_atten_etabliss_lien_logo ;
    @Enumerated(EnumType.STRING)
    private Inscriptions.status statuts ;

    public String getSousc_atten_etabliss_lien_logo() {
        return sousc_atten_etabliss_lien_logo;
    }

    public void setSousc_atten_etabliss_lien_logo(String sousc_atten_etabliss_lien_logo) {
        this.sousc_atten_etabliss_lien_logo = sousc_atten_etabliss_lien_logo;
    }

    public Long getIdSouscr() {
        return idSouscr;
    }

    public void setIdSouscr(Long idSouscr) {
        this.idSouscr = idSouscr;
    }

    public String getCodeSouscr() {
        return codeSouscr;
    }

    public String getLienAutorisationEnseign() {
        return lienAutorisationEnseign;
    }

    public void setLienAutorisationEnseign(String lienAutorisationEnseign) {
        this.lienAutorisationEnseign = lienAutorisationEnseign;
    }

    public souscriptionEcoleDto() {
    }

    public Long getIdcommune() {
        return idcommune;
    }

    public souscriptionEcoleDto(Long idSouscr,
                                String codeSouscr ,
                                String nomEcoleSouscr,
                                String nomfondateurSouscr,
                                String prenomfondateurSouscr,
                                String telfondateurSouscrl,
                                String telfondateurSouscrl2,
                                String ecoleindicationSouscrl,
                                Long villeidSouscrl,
                                Long zoneidSouscrl,
                                Long fonctionidSouscrl,
                                String zoneLibelle,
                                String fonctionLibelle,
                                String lienAutorisationEnseign,
                                String niveauEnseignement,
                                Long idcommune,
                                String libelleCommune,
                                Inscriptions.status statuts) {
        this.idSouscr = idSouscr;
        this.codeSouscr = codeSouscr ;
        this.nomEcoleSouscr = nomEcoleSouscr;
        this.nomfondateurSouscr = nomfondateurSouscr;
        this.prenomfondateurSouscr = prenomfondateurSouscr;
        this.telfondateurSouscrl = telfondateurSouscrl;
        this.telfondateurSouscrl2 = telfondateurSouscrl2;
        this.ecoleindicationSouscrl = ecoleindicationSouscrl;
        this.villeidSouscrl = villeidSouscrl;
        this.zoneidSouscrl = zoneidSouscrl;
        this.fonctionidSouscrl = fonctionidSouscrl;
        this.zoneLibelle = zoneLibelle;
        this.fonctionLibelle = fonctionLibelle;
        this.lienAutorisationEnseign = lienAutorisationEnseign;
        this.niveauEnseignement = niveauEnseignement;
        this.idcommune = idcommune;
        this.libelleCommune = libelleCommune;
        this.statuts = statuts;
    }

    public void setIdcommune(Long idcommune) {
        this.idcommune = idcommune;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public Inscriptions.status getStatuts() {
        return statuts;
    }

    public void setStatuts(Inscriptions.status statuts) {
        this.statuts = statuts;
    }

    public String getVilleLibelle() {
        return villeLibelle;
    }

    public void setVilleLibelle(String villeLibelle) {
        this.villeLibelle = villeLibelle;
    }

    public String getZoneLibelle() {
        return zoneLibelle;
    }

    public void setZoneLibelle(String zoneLibelle) {
        this.zoneLibelle = zoneLibelle;
    }

    public String getFonctionLibelle() {
        return fonctionLibelle;
    }

    public void setFonctionLibelle(String fonctionLibelle) {
        this.fonctionLibelle = fonctionLibelle;
    }

    public void setCodeSouscr(String codeSouscr) {
        this.codeSouscr = codeSouscr;
    }

    public String getTelfondateurSouscrl2() {
        return telfondateurSouscrl2;
    }

    public void setTelfondateurSouscrl2(String telfondateurSouscrl2) {
        this.telfondateurSouscrl2 = telfondateurSouscrl2;
    }

    public String getNomEcoleSouscr() {
        return nomEcoleSouscr;
    }

    public void setNomEcoleSouscr(String nomEcoleSouscr) {
        this.nomEcoleSouscr = nomEcoleSouscr;
    }

    public String getNomfondateurSouscr() {
        return nomfondateurSouscr;
    }

    public void setNomfondateurSouscr(String nomfondateurSouscr) {
        this.nomfondateurSouscr = nomfondateurSouscr;
    }

    public String getPrenomfondateurSouscr() {
        return prenomfondateurSouscr;
    }

    public void setPrenomfondateurSouscr(String prenomfondateurSouscr) {
        this.prenomfondateurSouscr = prenomfondateurSouscr;
    }

    public String getTelfondateurSouscrl() {
        return telfondateurSouscrl;
    }

    public void setTelfondateurSouscrl(String telfondateurSouscrl) {
        this.telfondateurSouscrl = telfondateurSouscrl;
    }

    public String getEcoleindicationSouscrl() {
        return ecoleindicationSouscrl;
    }

    public void setEcoleindicationSouscrl(String ecoleindicationSouscrl) {
        this.ecoleindicationSouscrl = ecoleindicationSouscrl;
    }

    public Long getVilleidSouscrl() {
        return villeidSouscrl;
    }

    public void setVilleidSouscrl(Long villeidSouscrl) {
        this.villeidSouscrl = villeidSouscrl;
    }

    public Long getZoneidSouscrl() {
        return zoneidSouscrl;
    }

    public void setZoneidSouscrl(Long zoneidSouscrl) {
        this.zoneidSouscrl = zoneidSouscrl;
    }

    public Long getFonctionidSouscrl() {
        return fonctionidSouscrl;
    }

    public void setFonctionidSouscrl(Long fonctionidSouscrl) {
        this.fonctionidSouscrl = fonctionidSouscrl;
    }

    /**
     * @return String return the niveauEnseignement
     */
    public String getNiveauEnseignement() {
        return niveauEnseignement;
    }

    /**
     * @param niveauEnseignement the niveauEnseignement to set
     */
    public void setNiveauEnseignement(String niveauEnseignement) {
        this.niveauEnseignement = niveauEnseignement;
    }

}
