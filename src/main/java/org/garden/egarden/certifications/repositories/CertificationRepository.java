package org.garden.egarden.certifications.repositories;

import org.garden.egarden.certifications.entities.CertificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificationRepository extends JpaRepository<CertificationEntity, UUID> {
}
