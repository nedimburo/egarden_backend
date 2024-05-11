package com.example.egardenrestapi.cardInformations.payloads;

import java.time.LocalDate;

public class CardInformationDto {
	private String cardNumber;
	private String pinCode;
	private String threeDigitNumber;
	private LocalDate expirationDate;
	private String username;
	public CardInformationDto() {
		super();
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getThreeDigitNumber() {
		return threeDigitNumber;
	}
	public void setThreeDigitNumber(String threeDigitNumber) {
		this.threeDigitNumber = threeDigitNumber;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
