package com.vieecoles.parents.services;

import com.vieecoles.parents.entities.ParentConfiguration;
import com.vieecoles.parents.entities.ParentEnfant;
import com.vieecoles.parents.dto.ParentConfigurationRequest;
import com.vieecoles.parents.dto.EleveAssociationRequest;
import com.vieecoles.parents.repositories.ParentConfigurationRepository;
import com.vieecoles.parents.repositories.ParentEnfantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ParentConfigurationService {

    @Inject
    ParentConfigurationRepository parentConfigRepository;

    @Inject
    ParentEnfantRepository parentEnfantRepository;

    @Transactional
    public ParentConfiguration saveConfiguration(ParentConfigurationRequest request) {
        ParentConfiguration config = parentConfigRepository.findByParentTelephone(request.getParentTelephone());

        if (config == null) {
            config = new ParentConfiguration(request.getParentTelephone());
        }

        config.setSelectedEcoleId(request.getEcoleId());
        config.setSelectedAnneeId(request.getAnneeId());
        config.setSelectedClasseId(request.getClasseId());
        config.setIsConfigured(true);

        parentConfigRepository.persist(config);
        return config;
    }

    public ParentConfiguration getConfiguration(String parentTelephone) {
        return parentConfigRepository.findByParentTelephone(parentTelephone);
    }

    @Transactional
    public ParentEnfant associateEleve(EleveAssociationRequest request) {
        // Vérifier si l'association existe déjà
        if (parentEnfantRepository.existsByParentAndMatricule(request.getParentTelephone(), request.getEnfantMatricule())) {
            throw new RuntimeException("Cet élève est déjà associé à ce parent");
        }

        ParentEnfant association = new ParentEnfant();
        association.setParentTelephone(request.getParentTelephone());
        association.setEnfantMatricule(request.getEnfantMatricule());
        association.setEnfantNom(request.getEnfantNom());
        association.setEnfantPrenom(request.getEnfantPrenom());
        association.setEnfantAnneeId(request.getEnfantAnneeId());
        association.setEnfantClasseId(request.getEnfantClasseId());
        association.setEnfantEcoleId(request.getEnfantEcoleId());
        association.setRelationType(request.getRelationType());

        parentEnfantRepository.persist(association);
        return association;
    }

    public List<ParentEnfant> getEnfantsByParent(String parentTelephone) {
        return parentEnfantRepository.findByParentTelephone(parentTelephone);
    }

    @Transactional
    public boolean removeEleveAssociation(String parentTelephone, String matricule) {
        ParentEnfant association = parentEnfantRepository.findByParentAndMatricule(parentTelephone, matricule);
        if (association != null) {
            association.setIsActive(false);
            parentEnfantRepository.persist(association);
            return true;
        }
        return false;
    }
}
