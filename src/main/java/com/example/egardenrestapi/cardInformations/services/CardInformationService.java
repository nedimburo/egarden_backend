package com.example.egardenrestapi.cardInformations.services;

import com.example.egardenrestapi.cardInformations.CardInformation;
import com.example.egardenrestapi.cardInformations.entities.CardInformationEntity;
import com.example.egardenrestapi.cardInformations.payloads.CardInformationDto;
import com.example.egardenrestapi.cardInformations.repositories.CardInformationRepository;
import com.example.egardenrestapi.users.entities.UserEntity;
import com.example.egardenrestapi.users.services.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CardInformationService implements CardInformation {

    private final CardInformationRepository repository;
    private final UserService userService;

    public CardInformationEntity findByUserEntityId(Integer userEntityId){
        return repository.findByUserEntityId(userEntityId);
    }

    @Transactional
    public ResponseEntity<?> addCardInformation(CardInformationDto cardInformationDto){
        try {
            if (cardInformationDto.getCardNumber()==null || cardInformationDto.getCardNumber().isEmpty()) {
                return new ResponseEntity<>("Card number cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            if (cardInformationDto.getPinCode()==null || cardInformationDto.getPinCode().isEmpty()) {
                return new ResponseEntity<>("Cards pin code cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            if (cardInformationDto.getThreeDigitNumber()==null || cardInformationDto.getThreeDigitNumber().isEmpty() || cardInformationDto.getThreeDigitNumber().length()!=3) {
                return new ResponseEntity<>("Three digit number cannot be null or empty and must only contain three digits.", HttpStatus.BAD_REQUEST);
            }

            if (cardInformationDto.getExpirationDate()==null) {
                return new ResponseEntity<>("Cards expiration date cannot be null.", HttpStatus.BAD_REQUEST);
            }

            UserEntity userEntity = userService.findByUsernameOrEmail(cardInformationDto.getUsername(), cardInformationDto.getUsername());
            CardInformationEntity existingCardInformationEntity = repository.findByUserEntityId(userEntity.getId());
            if(existingCardInformationEntity !=null) {
                existingCardInformationEntity.setCardNumber(cardInformationDto.getCardNumber());
                existingCardInformationEntity.setPinCode(cardInformationDto.getPinCode());
                existingCardInformationEntity.setThreeDigitNumber(cardInformationDto.getThreeDigitNumber());
                existingCardInformationEntity.setExpirationDate(cardInformationDto.getExpirationDate());
                repository.save(existingCardInformationEntity);
                return new ResponseEntity<>("Card Information has been updated.", HttpStatus.OK);
            }
            CardInformationEntity cardInformationEntity =new CardInformationEntity();
            cardInformationEntity.setCardNumber(cardInformationDto.getCardNumber());
            cardInformationEntity.setPinCode(cardInformationDto.getPinCode());
            cardInformationEntity.setThreeDigitNumber(cardInformationDto.getThreeDigitNumber());
            cardInformationEntity.setExpirationDate(cardInformationDto.getExpirationDate());
            cardInformationEntity.setUserEntity(userEntity);
            repository.save(cardInformationEntity);
            return new ResponseEntity<>("Credit card information is successfully added.", HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>("Credit card information has not been saved.", HttpStatus.BAD_REQUEST);
        }
    }
}
