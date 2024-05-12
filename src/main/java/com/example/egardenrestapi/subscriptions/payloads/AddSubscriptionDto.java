package com.example.egardenrestapi.subscriptions.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddSubscriptionDto {

	private String username;
	private String subscriptionType;
}
