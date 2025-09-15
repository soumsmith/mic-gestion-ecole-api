package com.vieecoles.parents.repositories;

import com.vieecoles.parents.entities.ParentEnfant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ParentEnfantRepository implements PanacheRepository<ParentEnfant> {

    public List<ParentEnfant> findByParentTelephone(String parentTelephone) {
        return find("parentTelephone = ?1 and isActive = true", parentTelephone).list();
    }

    public ParentEnfant findByParentAndMatricule(String parentTelephone, String matricule) {
        return find("parentTelephone = ?1 and enfantMatricule = ?2 and isActive = true",
                   parentTelephone, matricule).firstResult();
    }

    public boolean existsByParentAndMatricule(String parentTelephone, String matricule) {
        return count("parentTelephone = ?1 and enfantMatricule = ?2 and isActive = true",
                    parentTelephone, matricule) > 0;
    }
}
