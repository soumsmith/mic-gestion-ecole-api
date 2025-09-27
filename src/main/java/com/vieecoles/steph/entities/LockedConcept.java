package com.vieecoles.steph.entities;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Audited
@Table(name = "locked_concept")
public class LockedConcept extends PanacheEntityBase {

    @Id
    @Column(length = 36)
    public String id;

    @Column(name = "concept_id", nullable = false)
    public String conceptId;

    @Column(name = "concept_type", nullable = false)
    public String conceptType;

    @Column(nullable = false)
    public String status; // VERROUILLE / DEVERROUILLE

    @Column(name = "action_by", nullable = false)
    public String actionBy;

    @Column(name = "action_at", nullable = false)
    public LocalDateTime actionAt;
}
