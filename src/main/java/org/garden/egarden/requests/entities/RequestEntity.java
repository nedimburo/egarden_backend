package org.garden.egarden.requests.entities;


import org.garden.egarden.common.entities.Auditable;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="requests")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class RequestEntity extends Auditable {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "chosen_maintenance")
	@Enumerated(EnumType.STRING)
	private MaintenanceType chosenMaintenance;

	@Column(name = "chosen_decoration")
	private String chosenDecoration;

	@Column(name = "chosen_layout")
	private String chosenLayout;

	@Column(name = "payment_method")
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Column(name = "allow_agency")
	@Enumerated(EnumType.STRING)
	private ConfirmationType allowAgency;

	@Column(name = "planned_budget")
	private float plannedBudget;

	@Column(name = "price")
	private float price;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusType status;

	@Column(name = "city")
	private String city;

	@Column(name = "address")
	private String address;

	@Column(name = "country")
	private String country;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity userEntity;

}
