package org.garden.egarden.workers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.workers.entities.WorkerEntity;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Integer>{
	WorkerEntity findByUserEntityId(String userId);
}
