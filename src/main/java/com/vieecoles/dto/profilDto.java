package com.vieecoles.dto;



public class profilDto  {
  
 private  Long   profilid ;
  private  String  profilcode ;
 private  String   profil_libelle ;


     public profilDto() {
    } 

    public Long getProfilid() {
        return profilid;
    }

    public void setProfilid(Long profilid) {
        this.profilid = profilid;
    }

    public String getProfilcode() {
        return profilcode;
    }

    public void setProfilcode(String profilcode) {
        this.profilcode = profilcode;
    }

    public String getProfil_libelle() {
        return profil_libelle;
    }

    public void setProfil_libelle(String profil_libelle) {
        this.profil_libelle = profil_libelle;
    }

    @Override
    public String toString() {
        return "profil [profilid=" + profilid + ", profilcode=" + profilcode + ", profil_libelle=" + profil_libelle
                + "]";
    }

  


}
