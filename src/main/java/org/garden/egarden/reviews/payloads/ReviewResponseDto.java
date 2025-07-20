package org.garden.egarden.reviews.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {
    private String reviewId;
    private Integer rating;
    private String comment;
    private String reviewerUsername;
    private String reviewerFullName;
    private String createdAt;
    private String updatedAt;
}
