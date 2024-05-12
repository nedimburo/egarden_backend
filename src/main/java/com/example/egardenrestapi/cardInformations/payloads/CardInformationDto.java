package com.example.egardenrestapi.cardInformations.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardInformationDto {

	private String cardNumber;
	private String pinCode;
	private String threeDigitNumber;
	private LocalDate expirationDate;
	private String username;
}
