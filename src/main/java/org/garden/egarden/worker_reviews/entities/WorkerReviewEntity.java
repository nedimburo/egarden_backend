package org.garden.egarden.worker_reviews.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.garden.egarden.common.entities.Auditable;
import org.garden.egarden.workers.entities.WorkerEntity;
import org.garden.egarden.workers.entities.WorkerStatus;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "worker_reviews")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
public class WorkerReviewEntity extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerEntity worker;

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkerStatus result;

    @Column(name = "reviewed_at_ms", nullable = false)
    private Long reviewedAtMs;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "reviewd_by_admin_id", nullable = false)
    private String reviewedByAdminId;
}
