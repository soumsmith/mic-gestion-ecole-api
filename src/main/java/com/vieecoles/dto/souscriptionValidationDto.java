package com.vieecoles.dto;

public class souscriptionValidationDto {
    Long idsouscrip ;
    String statuts ;
    String messageRefus ;

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
}
