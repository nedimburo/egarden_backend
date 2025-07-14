package org.garden.egarden.subscriptions.entities;

import org.garden.egarden.common.entities.Auditable;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="subscriptions")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class SubscriptionEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "subscription_type")
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptionType;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity userEntity;

}
