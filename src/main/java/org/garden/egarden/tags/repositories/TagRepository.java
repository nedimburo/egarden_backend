package org.garden.egarden.tags.repositories;

import org.garden.egarden.tags.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {
}
