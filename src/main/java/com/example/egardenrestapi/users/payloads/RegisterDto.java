package com.example.egardenrestapi.users.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String gender;
	private LocalDate birthDate;
}
