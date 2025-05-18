package org.garden.egarden.workers.entities;

import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.workers.Worker;
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

@Getter
@Setter
@Entity
@Table(name="workers")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class WorkerEntity implements Worker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "description")
	private String description;

	@Column(name = "phone_number", unique = true)
	private String phoneNumber;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity userEntity;

}
