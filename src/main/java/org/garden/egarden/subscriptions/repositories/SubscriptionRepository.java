package org.garden.egarden.subscriptions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.subscriptions.entities.SubscriptionEntity;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID>{
	SubscriptionEntity findByUserEntityId(String userId);
}
