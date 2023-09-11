package com.vieecoles.projection;

public class EnqueteSelectDto {
    private  String libelleBranche ;
    private Long idBranche ;
    private Integer nombreNAff ;
    private Integer nombreAff ;

    public EnqueteSelectDto(String libelleBranche, Long idBranche,Integer nombreAff , Integer nombreNAff) {
        this.libelleBranche = libelleBranche;
        this.idBranche = idBranche;
        this.nombreAff = nombreAff;
        this.nombreNAff = nombreNAff;

    }

    public String getLibelleBranche() {
        return libelleBranche;
    }

    public void setLibelleBranche(String libelleBranche) {
        this.libelleBranche = libelleBranche;
    }

    public Long getIdBranche() {
        return idBranche;
    }

    public void setIdBranche(Long idBranche) {
        this.idBranche = idBranche;
    }

    public Integer getNombreNAff() {
        return nombreNAff;
    }

    public void setNombreNAff(Integer nombreNAff) {
        this.nombreNAff = nombreNAff;
    }

    public Integer getNombreAff() {
        return nombreAff;
    }

    public void setNombreAff(Integer nombreAff) {
        this.nombreAff = nombreAff;
    }
}
