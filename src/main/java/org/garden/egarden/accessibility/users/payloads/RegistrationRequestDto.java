package org.garden.egarden.accessibility.users.payloads;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "First name can't be blank")
    private String firstName;
    @NotBlank(message = "Last name can't be blank")
    private String lastName;
    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "Gender can't be blank")
    private UserGender gender;
    @NotBlank(message = "Birth date can't be blank")
    private LocalDate birthDate;
}
