package org.garden.egarden.card_informations.entities;

import java.time.LocalDate;

import org.garden.egarden.card_informations.CardInformation;
import org.garden.egarden.accessibility.users.entities.UserEntity;
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
@Table(name="card_informations")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class CardInformationEntity implements CardInformation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "three_digit_number")
	private String threeDigitNumber;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private UserEntity userEntity;
	
}
