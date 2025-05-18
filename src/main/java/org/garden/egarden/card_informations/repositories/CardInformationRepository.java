package org.garden.egarden.card_informations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.garden.egarden.card_informations.entities.CardInformationEntity;

public interface CardInformationRepository extends JpaRepository<CardInformationEntity, Integer>{
	CardInformationEntity findByUserEntityId(String userEntityId);
}
