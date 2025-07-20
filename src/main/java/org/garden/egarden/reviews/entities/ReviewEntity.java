package org.garden.egarden.reviews.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.common.entities.Auditable;
import org.garden.egarden.workers.entities.WorkerEntity;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "worker_id"}),
                @UniqueConstraint(columnNames = {"user_id", "is_app_review"})
        }
)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class ReviewEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private WorkerEntity worker;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 1500, nullable = false)
    private String comment;

    @Column(name = "is_app_review", nullable = false)
    private boolean isAppReview;
}
