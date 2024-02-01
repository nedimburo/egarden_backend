package com.example.egardenrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Integer>{
	List<Request> findByUserId(Integer userId);
}
