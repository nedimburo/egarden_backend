package com.example.egardenrestapi.cardInformations.services;

import com.example.egardenrestapi.cardInformations.CardInformation;
import com.example.egardenrestapi.cardInformations.entities.CardInformationEntity;
import com.example.egardenrestapi.cardInformations.repositories.CardInformationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CardInformationService implements CardInformation {

    private final CardInformationRepository repository;

    public CardInformationEntity findByUserEntityId(Integer userEntityId){
        return repository.findByUserEntityId(userEntityId);
    }
}
