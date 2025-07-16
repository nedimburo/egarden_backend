package org.garden.egarden.certifications.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.certifications.repositories.CertificationRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository repository;
}
