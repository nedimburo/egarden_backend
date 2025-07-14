package org.garden.egarden.common.seeders;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.garden.egarden.accessibility.roles.entities.RoleEntity;
import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.garden.egarden.accessibility.roles.repositories.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {

        if (roleRepository.count() > 0) {
            return;
        }

        List<RoleName> roles = List.of(RoleName.ADMIN, RoleName.WORKER, RoleName.CLIENT);
        for (RoleName role : roles) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName(role);
            roleRepository.save(roleEntity);
        }
    }
}
