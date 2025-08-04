package org.garden.egarden.offerings.repositories;

import org.garden.egarden.offerings.entities.OfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfferingRepository extends JpaRepository<OfferingEntity, UUID> {
}
