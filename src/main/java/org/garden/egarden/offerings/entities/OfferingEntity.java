package org.garden.egarden.offerings.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.common.entities.Auditable;
import org.garden.egarden.images.entities.ImageEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "offerings")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class OfferingEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_per_square_meter", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerSquareMeter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @Column(name = "is active", nullable = false)
    private boolean isActive = true;

    @Column(name = "duration_estimate_minutes")
    private Integer durationEstimateMinutes;

    @Column(name = "category", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private OfferingCategory category;
}
