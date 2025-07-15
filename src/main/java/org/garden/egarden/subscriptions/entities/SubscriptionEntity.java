package org.garden.egarden.subscriptions.entities;

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
@Table(name="subscriptions")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class SubscriptionEntity extends Auditable {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "subscription_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptionType;

	@Column(name = "subscription_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private SubscriptionStatus subscriptionStatus;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity userEntity;

	@Column(name = "purchased_at_ms", nullable = false)
	private Long purchasedAtMs;

	@Column(name = "expiration_at_ms", nullable = false)
	private Long expirationAtMs;

	@Column(name = "cancellation_at_ms")
	private Long cancellationAtMs;
}
