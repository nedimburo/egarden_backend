package org.garden.egarden.reviews.repositories;

import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.reviews.entities.ReviewEntity;
import org.garden.egarden.workers.entities.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    Optional<ReviewEntity> findByUserAndIsAppReviewTrue(UserEntity user);
    Optional<ReviewEntity> findByUserAndWorker(UserEntity user, WorkerEntity worker);
}
