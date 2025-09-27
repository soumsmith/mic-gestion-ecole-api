package com.vieecoles.parents.entities;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parent_enfants")
public class ParentEnfant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_telephone", nullable = false, length = 20)
    private String parentTelephone;

    @Column(name = "enfant_matricule", nullable = false, length = 50)
    private String enfantMatricule;

    @Column(name = "enfant_nom", nullable = false, length = 100)
    private String enfantNom;

    @Column(name = "enfant_prenom", nullable = false, length = 100)
    private String enfantPrenom;

    @Column(name = "enfant_annee_id")
    private Long enfantAnneeId;

    @Column(name = "enfant_classe_id")
    private Long enfantClasseId;

    @Column(name = "enfant_ecole_id")
    private Long enfantEcoleId;

    @Column(name = "relation_type", length = 50)
    private String relationType = "parent";

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructeurs
    public ParentEnfant() {}

    public ParentEnfant(String parentTelephone, String enfantMatricule,
                       String enfantNom, String enfantPrenom) {
        this.parentTelephone = parentTelephone;
        this.enfantMatricule = enfantMatricule;
        this.enfantNom = enfantNom;
        this.enfantPrenom = enfantPrenom;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getParentTelephone() { return parentTelephone; }
    public void setParentTelephone(String parentTelephone) { this.parentTelephone = parentTelephone; }

    public String getEnfantMatricule() { return enfantMatricule; }
    public void setEnfantMatricule(String enfantMatricule) { this.enfantMatricule = enfantMatricule; }

    public String getEnfantNom() { return enfantNom; }
    public void setEnfantNom(String enfantNom) { this.enfantNom = enfantNom; }

    public String getEnfantPrenom() { return enfantPrenom; }
    public void setEnfantPrenom(String enfantPrenom) { this.enfantPrenom = enfantPrenom; }

    public Long getEnfantAnneeId() { return enfantAnneeId; }
    public void setEnfantAnneeId(Long enfantAnneeId) { this.enfantAnneeId = enfantAnneeId; }

    public Long getEnfantClasseId() { return enfantClasseId; }
    public void setEnfantClasseId(Long enfantClasseId) { this.enfantClasseId = enfantClasseId; }



    public Long getEnfantEcoleId() { return enfantEcoleId; }
    public void setEnfantEcoleId(Long enfantEcoleId) { this.enfantEcoleId = enfantEcoleId; }

    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
