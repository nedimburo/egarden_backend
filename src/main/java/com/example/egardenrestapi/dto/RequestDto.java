package com.example.egardenrestapi.dto;

public class RequestDto {
	private String chosenMaintenance;
	private String chosenDecoration;
	private String chosenLayout;
	private String paymentMethod;
	private String allowAgency;
	private float plannedBudget;
	private float price;
	private String city;
	private String address;
	private String country;
	private String username;
	public RequestDto() {
		super();
	}
	public String getChosenMaintenance() {
		return chosenMaintenance;
	}
	public void setChosenMaintenance(String chosenMaintenance) {
		this.chosenMaintenance = chosenMaintenance;
	}
	public String getChosenDecoration() {
		return chosenDecoration;
	}
	public void setChosenDecoration(String chosenDecoration) {
		this.chosenDecoration = chosenDecoration;
	}
	public String getChosenLayout() {
		return chosenLayout;
	}
	public void setChosenLayout(String chosenLayout) {
		this.chosenLayout = chosenLayout;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getAllowAgency() {
		return allowAgency;
	}
	public void setAllowAgency(String allowAgency) {
		this.allowAgency = allowAgency;
	}
	public float getPlannedBudget() {
		return plannedBudget;
	}
	public void setPlannedBudget(float plannedBudget) {
		this.plannedBudget = plannedBudget;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
