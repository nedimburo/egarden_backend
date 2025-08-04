package org.garden.egarden.offerings.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.offerings.repositories.OfferingRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class OfferingService {

    private final OfferingRepository repository;
}
