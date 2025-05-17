package org.garden.egarden.accessibility.roles.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.garden.egarden.accessibility.roles.entities.RoleEntity;
import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.garden.egarden.accessibility.roles.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public RoleEntity findByName(RoleName role) { return repository.findByName(role); }
}
