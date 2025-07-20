package org.garden.egarden.reviews.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.common.payloads.SuccessResponseDto;
import org.garden.egarden.reviews.payloads.ReviewRequestDto;
import org.garden.egarden.reviews.payloads.ReviewResponseDto;
import org.garden.egarden.reviews.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import static org.garden.egarden.common.config.Constants.OPERATION_ID_NAME;

@Slf4j
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("client/reviews")
@Tags(value = {@Tag(name = "Client | Reviews"), @Tag(name = OPERATION_ID_NAME + "ClientReviews")})
public class ClientReviewController {

    private final ReviewService service;

    @Operation(
            description = "Create review for the application or for a worker."
    )
    @PostMapping("/")
    public ReviewResponseDto createReview(
            @RequestBody ReviewRequestDto reviewRequestDto,
            @RequestParam(required = false) String workerId
    ) {
        return service.createReview(reviewRequestDto, workerId);
    }

    @Operation(
            description = "Update an existing review."
    )
    @PatchMapping("/")
    public ReviewResponseDto updateReview(
            @RequestBody ReviewRequestDto reviewRequestDto,
            @RequestParam String reviewId
    ) {
        return service.updateReview(reviewRequestDto, reviewId);
    }

    @Operation(
            description = "Delete an existing review."
    )
    @DeleteMapping("/")
    public SuccessResponseDto deleteReview(@RequestParam String reviewId) {
        return service.deleteReview(reviewId);
    }
}
