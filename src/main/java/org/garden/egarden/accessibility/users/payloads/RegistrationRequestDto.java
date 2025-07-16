package org.garden.egarden.accessibility.users.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.garden.egarden.accessibility.users.entities.UserGender;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String username;
    private UserGender gender;
    private LocalDate birthDate;
}
