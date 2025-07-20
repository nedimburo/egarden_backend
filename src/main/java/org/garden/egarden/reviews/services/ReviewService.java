package org.garden.egarden.reviews.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.services.UserService;
import org.garden.egarden.common.config.Auth;
import org.garden.egarden.common.exceptions.BadRequestException;
import org.garden.egarden.common.exceptions.ResourceAlreadyExistsException;
import org.garden.egarden.common.exceptions.ResourceNotFoundException;
import org.garden.egarden.common.exceptions.UnauthorizedException;
import org.garden.egarden.common.payloads.SuccessResponseDto;
import org.garden.egarden.reviews.entities.ReviewEntity;
import org.garden.egarden.reviews.mappers.ReviewMapper;
import org.garden.egarden.reviews.payloads.ReviewRequestDto;
import org.garden.egarden.reviews.payloads.ReviewResponseDto;
import org.garden.egarden.reviews.repositories.ReviewRepository;
import org.garden.egarden.workers.entities.WorkerEntity;
import org.garden.egarden.workers.services.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
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
        if (reviewRequest.getRating() > 5 || reviewRequest.getRating() < 1) {
            throw new BadRequestException("Rating should be between 1 and 5.");
        }

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

            try {
                worker = workerService.getWorker(workerUuid);
            } catch (EntityNotFoundException e) {
                throw new ResourceNotFoundException("Worker with ID: " + workerId + " doesn't exist");
            }
            repository.findByUserAndWorker(user, worker).ifPresent(r -> {
                throw new ResourceAlreadyExistsException("You already reviewed the selected worker.");
            });
        } else {
            repository.findByUserAndIsAppReviewTrue(user).ifPresent(r -> {
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

    @Transactional
    public ReviewResponseDto updateReview(ReviewRequestDto reviewRequest, String reviewId) {
        if (reviewRequest.getRating() > 5 || reviewRequest.getRating() < 1) {
            throw new BadRequestException("Rating should be between 1 and 5.");
        }

        UUID reviewUuid;
        try {
            reviewUuid = UUID.fromString(reviewId);
        } catch (Exception e) {
            throw new BadRequestException("Invalid review ID format: " + reviewId);
        }

        ReviewEntity review;
        try {
            review = getReview(reviewUuid);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Review with ID: " + reviewId + " doesn't exist");
        }

        try {
            if (reviewRequest.getRating() != null && !reviewRequest.getRating().equals(review.getRating())) {
                review.setRating(reviewRequest.getRating());
            }
            if (reviewRequest.getComment() != null && !reviewRequest.getComment().equals(review.getComment())) {
                review.setComment(review.getComment());
            }
            repository.save(review);

            return reviewMapper.toResponseDto(review);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating review: " + e.getMessage());
        }
    }

    @Transactional
    public SuccessResponseDto deleteReview(String reviewId) {
        UUID reviewUuid;
        try {
             reviewUuid = UUID.fromString(reviewId);
        } catch (Exception e) {
            throw new BadRequestException("Invalid review ID format: " + reviewId);
        }

        ReviewEntity review;
        try {
            review = getReview(reviewUuid);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Review with ID: " + reviewId + " doesn't exist");
        }

        try {
            repository.delete(review);
            return SuccessResponseDto.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.OK.value())
                    .message("Review has been successfully deleted")
                    .path(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting review: " + e.getMessage());
        }
    }

    public ReviewEntity getReview(UUID reviewId) {
        return repository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + reviewId));
    }
}
