package org.garden.egarden.accessibility.users.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String gender;
	private LocalDate birthDate;
}
