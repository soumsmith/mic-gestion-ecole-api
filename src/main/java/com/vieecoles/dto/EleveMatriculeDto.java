package com.vieecoles.dto;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
@Getter
@Setter
public class EleveMatriculeDto {
    private  String  nom ;
    private  String prenom ;
    private String dateNaissance ;
    private  String  lieuNaissance ;
    private  String numeroExtraitNaiss ;
    private  String  dateEtabliExtraitNaiss ;
    private  String  lieuEtablissEtraitNaissance ;
    private  String adresse ;
    private  String  cellulaire ;
    private  String  mail ;
    private  String nationalite ;
    private  String matriculeNational ;
    private  String sexe ;

}
