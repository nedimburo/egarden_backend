package org.garden.egarden.workers.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkerResponseDto {

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String description;
	private String phoneNumber;
	private String city;
	private String country;
}
