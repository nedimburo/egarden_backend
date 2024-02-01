package com.example.egardenrestapi.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="requests")
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
