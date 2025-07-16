package org.garden.egarden.tags.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.common.entities.Auditable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tags")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class TagEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;
}
