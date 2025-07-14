package org.garden.egarden.workers.entities;

import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.common.entities.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "workers")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class WorkerEntity extends Auditable {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(name = "phone_number", unique = true)
	private String phoneNumber;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "hourly_rate")
	private BigDecimal hourlyRate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity userEntity;

}
