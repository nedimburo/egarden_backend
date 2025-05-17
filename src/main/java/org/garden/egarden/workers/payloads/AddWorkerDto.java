package org.garden.egarden.workers.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddWorkerDto {

	private String description;
	private String phoneNumber;
	private String city;
	private String country;
	private String username;
}
