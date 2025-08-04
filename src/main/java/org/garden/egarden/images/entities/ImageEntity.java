package org.garden.egarden.images.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.common.entities.Auditable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "images")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class ImageEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "url", length = 500, nullable = false)
    private String url;
}
