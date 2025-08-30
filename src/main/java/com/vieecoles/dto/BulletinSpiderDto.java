package com.vieecoles.dto;
import com.vieecoles.projection.BulletinSelectDto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;


public class BulletinSpiderDto {


  private List<BulletinSelectDto> details ;
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
    private String bulletin_id ;


}
