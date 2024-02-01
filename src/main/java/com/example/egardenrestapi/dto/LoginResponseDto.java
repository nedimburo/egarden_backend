package com.example.egardenrestapi.dto;

public class LoginResponseDto {
	private String message;
	private String username;
	private String role;
	private boolean hasCard;
	public LoginResponseDto() {
		super();
	}
	public LoginResponseDto(String message, String username, String role, boolean hasCard) {
		super();
		this.message = message;
		this.username = username;
		this.role = role;
		this.hasCard = hasCard;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isHasCard() {
		return hasCard;
	}
	public void setHasCard(boolean hasCard) {
		this.hasCard = hasCard;
	}
	
	
}
