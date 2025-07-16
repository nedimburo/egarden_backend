package org.garden.egarden.accessibility.users.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.garden.egarden.accessibility.users.entities.UserGender;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDto {
	private String userId;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private RoleName role;
	private UserGender gender;
	private LocalDate birthDate;
	private String registrationDate;
}
