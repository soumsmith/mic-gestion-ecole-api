package com.vieecoles.dto;

import java.time.LocalDate;

public class souscriptionValidationFondatDto {
    private Long idsouscrip ;
    private String statuts ;
    private String messageRefus ;
   private Long personnelid ;
   private LocalDate Datefin;
   private Long idEcole;
   private Long ProfilId ;


    public String getStatuts() {
        return statuts;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }

    public String getMessageRefus() {
        return messageRefus;
    }

    public void setMessageRefus(String messageRefus) {
        this.messageRefus = messageRefus;
    }

    public Long getIdsouscrip() {
        return idsouscrip;
    }

    public void setIdsouscrip(Long idsouscrip) {
        this.idsouscrip = idsouscrip;
    } 




    /**
     * @return Long return the personnelid
     */
    public Long getPersonnelid() {
        return personnelid;
    }

    /**
     * @param personnelid the personnelid to set
     */
    public void setPersonnelid(Long personnelid) {
        this.personnelid = personnelid;
    }

    /**
     * @return LocalDate return the Datefin
     */
    public LocalDate getDatefin() {
        return Datefin;
    }

    /**
     * @param Datefin the Datefin to set
     */
    public void setDatefin(LocalDate Datefin) {
        this.Datefin = Datefin;
    }

    /**
     * @return Long return the idEcole
     */
    public Long getIdEcole() {
        return idEcole;
    }

    /**
     * @param idEcole the idEcole to set
     */
    public void setIdEcole(Long idEcole) {
        this.idEcole = idEcole;
    }

    /**
     * @return Long return the ProfilId
     */
    public Long getProfilId() {
        return ProfilId;
    }

    /**
     * @param ProfilId the ProfilId to set
     */
    public void setProfilId(Long ProfilId) {
        this.ProfilId = ProfilId;
    }

}
