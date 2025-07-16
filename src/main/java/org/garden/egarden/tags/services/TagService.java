package org.garden.egarden.tags.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.tags.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;
}
