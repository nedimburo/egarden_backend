package org.garden.egarden.worker_reviews.repositories;

import org.garden.egarden.worker_reviews.entities.WorkerReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkerReviewRepository extends JpaRepository<WorkerReviewEntity, UUID> {
}
