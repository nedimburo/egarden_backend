package org.garden.egarden.subscriptions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.subscriptions.entities.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer>{
	SubscriptionEntity findByUserEntityId(String userId);
	void deleteById(Integer id);
}
