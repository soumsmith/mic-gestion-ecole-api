package com.vieecoles.parents.repositories;

import com.vieecoles.parents.entities.ParentConfiguration;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParentConfigurationRepository implements PanacheRepository<ParentConfiguration> {

    public ParentConfiguration findByParentTelephone(String parentTelephone) {
        return find("parentTelephone = ?1", parentTelephone).firstResult();
    }

    public boolean existsByParentTelephone(String parentTelephone) {
        return count("parentTelephone = ?1", parentTelephone) > 0;
    }
}
