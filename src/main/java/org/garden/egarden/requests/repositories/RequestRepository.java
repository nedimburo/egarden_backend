package org.garden.egarden.requests.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.requests.entities.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer>{
	List<RequestEntity> findByUserEntityId(String userId);
}
