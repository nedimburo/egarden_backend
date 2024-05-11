package com.example.egardenrestapi.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.users.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	UserEntity findByUsernameOrEmail(String username, String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
