package com.example.egardenrestapi.requests.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.requests.entities.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer>{
	List<RequestEntity> findByUserEntityId(Integer userId);
}
