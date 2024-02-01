package com.example.egardenrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer>{
	Worker findByUserId(Integer userId);
}
