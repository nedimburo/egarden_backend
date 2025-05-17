package org.garden.egarden.workers.controllers;

import org.garden.egarden.workers.payloads.AddWorkerDto;
import org.garden.egarden.workers.payloads.WorkerResponseDto;
import org.garden.egarden.workers.services.WorkerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("public/worker")
@Tags(value = @Tag(name = "Public | Worker"))
public class WorkerController {

    private final WorkerService service;

    @PostMapping("/sign-up-work")
    public ResponseEntity<?> signUpForWork(@RequestBody AddWorkerDto addWorkerDto){
        return  service.signUpForWork(addWorkerDto);
    }

    @GetMapping("/get-all-workers")
    public ResponseEntity<List<WorkerResponseDto>> getAllWorkers(){
        return service.getAllWorkers();
    }
}
