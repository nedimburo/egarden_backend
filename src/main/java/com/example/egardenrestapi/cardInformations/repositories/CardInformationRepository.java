package com.example.egardenrestapi.cardInformations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.egardenrestapi.cardInformations.entities.CardInformationEntity;

public interface CardInformationRepository extends JpaRepository<CardInformationEntity, Integer>{
	CardInformationEntity findByUserEntityId(Integer userEntityId);
}
