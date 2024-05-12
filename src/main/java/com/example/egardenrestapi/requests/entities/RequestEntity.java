package com.example.egardenrestapi.requests.entities;

import java.time.LocalDate;

import com.example.egardenrestapi.requests.Request;
import com.example.egardenrestapi.users.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="requests")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class RequestEntity implements Request {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

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

	@Column(name = "creation_date")
	private LocalDate creationDate;

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
