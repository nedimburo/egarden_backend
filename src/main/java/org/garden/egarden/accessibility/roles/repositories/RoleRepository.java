package org.garden.egarden.accessibility.roles.repositories;

import org.garden.egarden.accessibility.roles.entities.RoleEntity;
import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(RoleName name);
}
