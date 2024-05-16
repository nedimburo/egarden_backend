package com.example.egardenrestapi.workers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.workers.entities.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer>{
	WorkerEntity findByUserEntityId(Integer userId);
}
