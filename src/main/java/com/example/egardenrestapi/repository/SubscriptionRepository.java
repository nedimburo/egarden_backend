package com.example.egardenrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{
	Subscription findByUserId(Integer userId);
	void deleteById(Integer id);
}
