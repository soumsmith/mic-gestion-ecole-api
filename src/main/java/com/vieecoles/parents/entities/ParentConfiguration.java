package com.vieecoles.parents.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parent_configurations")
public class ParentConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_telephone", nullable = false, length = 20, unique = true)
    private String parentTelephone;

    @Column(name = "selected_ecole_id")
    private Long selectedEcoleId;

    @Column(name = "selected_annee_id")
    private Long selectedAnneeId;

    @Column(name = "selected_classe_id")
    private Long selectedClasseId;

    @Column(name = "is_configured")
    private Boolean isConfigured = false;

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
    public ParentConfiguration() {}

    public ParentConfiguration(String parentTelephone) {
        this.parentTelephone = parentTelephone;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getParentTelephone() { return parentTelephone; }
    public void setParentTelephone(String parentTelephone) { this.parentTelephone = parentTelephone; }

    public Long getSelectedEcoleId() { return selectedEcoleId; }
    public void setSelectedEcoleId(Long selectedEcoleId) { this.selectedEcoleId = selectedEcoleId; }

    public Long getSelectedAnneeId() { return selectedAnneeId; }
    public void setSelectedAnneeId(Long selectedAnneeId) { this.selectedAnneeId = selectedAnneeId; }

    public Long getSelectedClasseId() { return selectedClasseId; }
    public void setSelectedClasseId(Long selectedClasseId) { this.selectedClasseId = selectedClasseId; }

    public Boolean getIsConfigured() { return isConfigured; }
    public void setIsConfigured(Boolean isConfigured) { this.isConfigured = isConfigured; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
