package org.garden.egarden.accessibility.users.entities;

import java.time.LocalDate;

import org.garden.egarden.accessibility.roles.entities.RoleEntity;
import org.garden.egarden.common.entities.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="users")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class UserEntity extends Auditable {

	@Id
	@Column(name = "id", nullable = false, updatable = false)
	private String id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "gender")
	private String gender;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private RoleEntity role;
	
}
