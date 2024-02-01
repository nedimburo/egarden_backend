package com.example.egardenrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsernameOrEmail(String username, String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
