package com.example.egardenrestapi.cardInformations.controllers;

import com.example.egardenrestapi.cardInformations.payloads.CardInformationDto;
import com.example.egardenrestapi.cardInformations.services.CardInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("public/card-infomration")
@Tags(value = {@Tag(name = "Public | Card Information"), @Tag(name = "operationIdNamePublicCardInformation")})
public class CardInformationController {

    private final CardInformationService service;

    @PostMapping("/add-card-information")
    public ResponseEntity<?> addCardInformation(@RequestBody CardInformationDto cardInformationDto){
        return service.addCardInformation(cardInformationDto);
    }
}
