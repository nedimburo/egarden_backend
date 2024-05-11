package com.example.egardenrestapi.subscriptions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.subscriptions.entities.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer>{
	SubscriptionEntity findByUserId(Integer userId);
	void deleteById(Integer id);
}
