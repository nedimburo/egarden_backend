package org.garden.egarden.workers.entities;

import jakarta.persistence.*;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.certifications.entities.CertificationEntity;
import org.garden.egarden.common.entities.Auditable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.tags.entities.TagEntity;

import java.math.BigDecimal;
import java.util.List;
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

	@Column(name = "hourly_rate", nullable = false)
	private BigDecimal hourlyRate = BigDecimal.ZERO;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity userEntity;

	@ManyToMany
	@JoinTable(
			name = "worker_tags",
			joinColumns = @JoinColumn(name = "worker_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	private List<TagEntity> tags;

	@ManyToMany
	@JoinTable(
			name = "worker_certifications",
			joinColumns = @JoinColumn(name = "worker_id"),
			inverseJoinColumns = @JoinColumn(name = "certification_id")
	)
	private List<CertificationEntity> certifications;
}
