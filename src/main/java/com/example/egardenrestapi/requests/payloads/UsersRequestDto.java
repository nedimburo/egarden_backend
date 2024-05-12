package com.example.egardenrestapi.requests.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequestDto {
	private String chosenMaintenance;
	private String chosenDecoration;
	private String chosenLayout;
	private String paymentMethod;
	private String allowAgency;
	private float plannedBudget;
	private float price;
	private String status;
	private LocalDate creationDate;
	private String city;
	private String address;
	private String country;
}
