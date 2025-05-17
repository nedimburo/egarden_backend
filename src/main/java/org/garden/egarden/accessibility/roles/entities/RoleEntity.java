package org.garden.egarden.accessibility.roles.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.common.entities.Auditable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "roles")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class RoleEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
