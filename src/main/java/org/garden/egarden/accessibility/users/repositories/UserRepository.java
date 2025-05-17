package org.garden.egarden.accessibility.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.accessibility.users.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
	UserEntity findByUsernameOrEmail(String username, String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
