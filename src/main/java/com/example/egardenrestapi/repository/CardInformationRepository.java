package com.example.egardenrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.entity.CardInformation;

public interface CardInformationRepository extends JpaRepository<CardInformation, Integer>{
	CardInformation findByUserId(Integer userId);
}
