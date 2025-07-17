package org.garden.egarden.reviews.mappers;

import org.garden.egarden.reviews.entities.ReviewEntity;
import org.garden.egarden.reviews.payloads.ReviewRequestDto;
import org.garden.egarden.reviews.payloads.ReviewResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "reviewId", source = "id")
    @Mapping(target = "createdAt", expression = "java(entity.getCreated().toString())")
    @Mapping(target = "updatedAt", expression = "java(entity.getUpdated().toString())")
    @Mapping(target = "reviewerUsername", source = "user.username")
    @Mapping(target = "reviewerFullName", expression = "java(entity.getUser().getFirstName() + \" \" + entity.getUser().getLastName())")
    ReviewResponseDto toResponseDto(ReviewEntity entity);

    ReviewEntity toEntity(ReviewRequestDto request);
}
