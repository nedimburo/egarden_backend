package org.garden.egarden.worker_reviews.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.worker_reviews.repositories.WorkerReviewRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class WorkerReviewService {

    private final WorkerReviewRepository repository;
}
