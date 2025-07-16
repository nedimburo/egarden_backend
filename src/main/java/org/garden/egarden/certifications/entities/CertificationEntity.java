package org.garden.egarden.certifications.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.common.entities.Auditable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cetifications")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class CertificationEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "acquired_from", nullable = false, length = 500)
    private String acquiredFrom;

    @Column(name = "acquired_at_ms", nullable = false)
    private Long acquiredAtMs;

    @Column(name = "valid_until_ms", nullable = false)
    private Long validUntilMs;
}
