package org.garden.egarden.reviews.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.services.UserService;
import org.garden.egarden.common.config.Auth;
import org.garden.egarden.common.exceptions.ResourceAlreadyExistsException;
import org.garden.egarden.common.exceptions.ResourceNotFoundException;
import org.garden.egarden.common.exceptions.UnauthorizedException;
import org.garden.egarden.reviews.entities.ReviewEntity;
import org.garden.egarden.reviews.mappers.ReviewMapper;
import org.garden.egarden.reviews.payloads.ReviewRequestDto;
import org.garden.egarden.reviews.payloads.ReviewResponseDto;
import org.garden.egarden.reviews.repositories.ReviewRepository;
import org.garden.egarden.workers.entities.WorkerEntity;
import org.garden.egarden.workers.services.WorkerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;
    private final WorkerService workerService;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequest, String workerId) {
        String uid;
        try {
            uid = Auth.getUserId();
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }

        UserEntity user;
        try {
            user = userService.getUser(uid);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User with ID: " + uid + " doesn't exist");
        }

        WorkerEntity worker = null;

        boolean isAppReview = (workerId == null);

        if (!isAppReview) {
            UUID workerUuid;
            try {
                workerUuid = UUID.fromString(workerId);
            } catch (Exception e) {
                throw new RuntimeException("Invalid worker ID format: " + workerId);
            }

            worker = workerService.getWorker(workerUuid);
            repository.findByUserAndWorker(user, worker).ifPresent(r -> {
                throw new ResourceAlreadyExistsException("You already reviewed the selected worker.");
            });
        } else {
            repository.findByUserAndAppReviewTrue(user).ifPresent(r -> {
                throw new ResourceAlreadyExistsException("You already reviewed the app.");
            });
        }

        try {
            ReviewEntity review = reviewMapper.toEntity(reviewRequest);
            review.setUser(user);
            review.setWorker(worker);
            review.setAppReview(isAppReview);
            ReviewEntity savedReview = repository.save(review);
            return reviewMapper.toResponseDto(savedReview);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating review: " + e.getMessage());
        }
    }

    public ReviewEntity getReview(UUID reviewId) {
        return repository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + reviewId));
    }
}
