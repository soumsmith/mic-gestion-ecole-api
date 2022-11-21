package com.vieecoles.dao.entities.operations;


import com.vieecoles.dao.entities.*;
import com.vieecoles.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class personnel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  personnelid ;
   private  String personnelcode;
    private  String personnelnom;
    private  String personnelprenom;
    private LocalDate personneldatenaissance;
    private  String personnel_lieunaissance;

    private  String personnel_emprunte ;
    private  String  personnel_contact ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private com.vieecoles.dao.entities.tenant tenant ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_personnel_type_personnelid")
    private com.vieecoles.dao.entities.type_personnel type_personnel;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_status_personnel_statusid")
    private com.vieecoles.dao.entities.personnel_status personnel_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fonction_fonctionid")
    private com.vieecoles.dao.entities.fonction fonction;
    @Transient
    @Enumerated(EnumType.STRING)
    private Civilite civilite;
    public com.vieecoles.dao.entities.fonction getFonction() {
        return fonction;
    }

    public void setFonction(com.vieecoles.dao.entities.fonction fonction) {
        this.fonction = fonction;
    }

    public Long getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(Long personnelid) {
        this.personnelid = personnelid;
    }

    public String getPersonnelcode() {
        return personnelcode;
    }

    public void setPersonnelcode(String personnelcode) {
        this.personnelcode = personnelcode;
    }

    public String getPersonnelnom() {
        return personnelnom;
    }

    public void setPersonnelnom(String personnelnom) {
        this.personnelnom = personnelnom;
    }

    public String getPersonnelprenom() {
        return personnelprenom;
    }

    public void setPersonnelprenom(String personnelprenom) {
        this.personnelprenom = personnelprenom;
    }

    public LocalDate getPersonneldatenaissance() {
        return personneldatenaissance;
    }

    public void setPersonneldatenaissance(LocalDate personneldatenaissance) {
        this.personneldatenaissance = personneldatenaissance;
    }

    public String getPersonnel_lieunaissance() {
        return personnel_lieunaissance;
    }

    public void setPersonnel_lieunaissance(String personnel_lieunaissance) {
        this.personnel_lieunaissance = personnel_lieunaissance;
    }

    public com.vieecoles.dao.entities.type_personnel getType_personnel() {
        return type_personnel;
    }

    public void setType_personnel(com.vieecoles.dao.entities.type_personnel type_personnel) {
        this.type_personnel = type_personnel;
    }

    public com.vieecoles.dao.entities.personnel_status getPersonnel_status() {
        return personnel_status;
    }

    public void setPersonnel_status(com.vieecoles.dao.entities.personnel_status personnel_status) {
        this.personnel_status = personnel_status;
    }

    public com.vieecoles.dao.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.dao.entities.tenant tenant) {
        this.tenant = tenant;
    }

    public String getPersonnel_emprunte() {
        return personnel_emprunte;
    }

    public void setPersonnel_emprunte(String personnel_emprunte) {
        this.personnel_emprunte = personnel_emprunte;
    }

    public String getPersonnel_contact() {
        return personnel_contact;
    }

    public void setPersonnel_contact(String personnel_contact) {
        this.personnel_contact = personnel_contact;
    }
}
