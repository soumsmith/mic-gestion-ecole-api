package com.vieecoles.steph.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReaderFactory;

import com.vieecoles.steph.dto.LockResponseDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.LockedConcept;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LockService implements PanacheRepositoryBase<LockedConcept, String> {

	@Inject
	EntityManager em;

	// Vérifie si un concept est verrouillé
	public boolean isLocked(String conceptId, String conceptType) {
		LockedConcept lock = find("conceptId = ?1 and conceptType = ?2", conceptId, conceptType).firstResult();
		return lock != null && "VERROUILLE".equals(lock.status);
	}

	// Verrouille un concept
	@Transactional
	public LockResponseDto lock(String conceptId, String conceptType, String actor) {
		String existingStatus = null;
		try {
			LockedConcept lock = find("conceptId = ?1 and conceptType = ?2", conceptId, conceptType).firstResult();
			if (lock == null) {
				lock = new LockedConcept();
				lock.id = UUID.randomUUID().toString();
				lock.conceptId = conceptId;
				lock.conceptType = conceptType;
				existingStatus = Constants.UNLOCKED;
			} else {
				existingStatus = lock.status;
			}
			lock.status = Constants.LOCKED;
			lock.actionBy = actor;
			lock.actionAt = LocalDateTime.now();
			persist(lock);
			Boolean isLocked = Constants.LOCKED.equals(lock.status);

			return new LockResponseDto(lock.id, "Concept verrouillé avec succès", isLocked);
		} catch (Exception e) {
			boolean wasLocked = Constants.LOCKED.equals(existingStatus);
			return new LockResponseDto(null, "Erreur lors du verrouillage: " + e.getMessage(), wasLocked);
		}
	}

	// Déverrouille un concept
	@Transactional
	public LockResponseDto unlock(String conceptId, String conceptType, String actor) {
		String existingStatus = null;
		try {
			LockedConcept lock = find("conceptId = ?1 and conceptType = ?2", conceptId, conceptType).firstResult();
			if (lock != null) {
				lock.status = "DEVERROUILLE";
				lock.actionBy = actor;
				lock.actionAt = LocalDateTime.now();
				existingStatus = lock.status;
				persist(lock);

				return new LockResponseDto(lock.id, "Concept déverrouillé avec succès", false);
			} else {
				return new LockResponseDto(null, "Concept non trouvé", false);
			}
		} catch (Exception e) {
			boolean wasLocked = Constants.LOCKED.equals(existingStatus);
			return new LockResponseDto(null, "Erreur lors du déverrouillage: " + e.getMessage(), wasLocked);
		}
	}

	// Liste par statut
	public List<LockedConcept> listByStatus(String status) {
		return list("status", status);
	}

	// Liste de tous les concepts
	public List<LockedConcept> listAllCurrent() {
		return listAll();
	}

	// Historique via Envers
	public List<LockedConcept> getHistory(String conceptId) {
		var auditReader = AuditReaderFactory.get(em);
		var revisions = auditReader.getRevisions(LockedConcept.class, conceptId);

		return revisions.stream().map(rev -> auditReader.find(LockedConcept.class, conceptId, rev)).toList();
	}
}
