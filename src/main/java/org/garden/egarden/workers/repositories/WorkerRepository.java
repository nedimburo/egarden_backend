package org.garden.egarden.workers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.workers.entities.WorkerEntity;

import java.util.UUID;

public interface WorkerRepository extends JpaRepository<WorkerEntity, UUID>{
	WorkerEntity findByUserEntityId(String userId);
}
