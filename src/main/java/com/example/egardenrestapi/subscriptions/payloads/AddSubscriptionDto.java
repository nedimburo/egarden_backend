package com.example.egardenrestapi.subscriptions.payloads;

public class AddSubscriptionDto {
	private String username;
	private String subscriptionName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSubscriptionName() {
		return subscriptionName;
	}
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
	
}
