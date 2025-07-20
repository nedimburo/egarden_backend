package org.garden.egarden.workers.services;

import jakarta.persistence.EntityNotFoundException;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.services.UserService;
import org.garden.egarden.workers.entities.WorkerEntity;
import org.garden.egarden.workers.payloads.AddWorkerDto;
import org.garden.egarden.workers.payloads.WorkerResponseDto;
import org.garden.egarden.workers.repositories.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository repository;
    private final UserService userService;

    @Transactional
    public ResponseEntity<?> signUpForWork(AddWorkerDto addWorkerDto){
        try {
            if(addWorkerDto.getDescription()==null || addWorkerDto.getDescription().isEmpty()) {
                return new ResponseEntity<>("Description cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            if(addWorkerDto.getPhoneNumber()==null || addWorkerDto.getPhoneNumber().isEmpty()) {
                return new ResponseEntity<>("Phone number cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            if(addWorkerDto.getCity()==null || addWorkerDto.getCity().isEmpty()) {
                return new ResponseEntity<>("City cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            if(addWorkerDto.getCountry()==null || addWorkerDto.getCountry().isEmpty()) {
                return new ResponseEntity<>("Country cannot be null or empty.", HttpStatus.BAD_REQUEST);
            }

            UserEntity userEntity = userService.findByUsernameOrEmail(addWorkerDto.getUsername(), addWorkerDto.getUsername());
            WorkerEntity existingWorkerEntity = repository.findByUserEntityId(userEntity.getId());

            if (existingWorkerEntity !=null) {
                existingWorkerEntity.setDescription(addWorkerDto.getDescription());
                existingWorkerEntity.setPhoneNumber(addWorkerDto.getPhoneNumber());
                existingWorkerEntity.setCity(addWorkerDto.getCity());
                existingWorkerEntity.setCountry(addWorkerDto.getCountry());
                repository.save(existingWorkerEntity);
                return new ResponseEntity<>("Work details have been updated.", HttpStatus.OK);
            }
            WorkerEntity workerEntity =new WorkerEntity();
            workerEntity.setDescription(addWorkerDto.getDescription());
            workerEntity.setPhoneNumber(addWorkerDto.getPhoneNumber());
            workerEntity.setCity(addWorkerDto.getCity());
            workerEntity.setCountry(addWorkerDto.getCountry());
            workerEntity.setUserEntity(userEntity);

            repository.save(workerEntity);
            return new ResponseEntity<>("Work details have been successfully added.", HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>("Work details have not been saved.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<List<WorkerResponseDto>> getAllWorkers(){
        List<WorkerEntity> allWorkerEntities = repository.findAll();
        List<WorkerResponseDto> allWorkersResponse = new ArrayList<>();

        for (int i = 0; i< allWorkerEntities.size(); i++) {
            WorkerResponseDto workerResponseDto=new WorkerResponseDto();
            workerResponseDto.setFirstName(allWorkerEntities.get(i).getUserEntity().getFirstName());
            workerResponseDto.setLastName(allWorkerEntities.get(i).getUserEntity().getLastName());
            workerResponseDto.setEmail(allWorkerEntities.get(i).getUserEntity().getEmail());
            workerResponseDto.setUsername(allWorkerEntities.get(i).getUserEntity().getUsername());
            workerResponseDto.setDescription(allWorkerEntities.get(i).getDescription());
            workerResponseDto.setPhoneNumber(allWorkerEntities.get(i).getPhoneNumber());
            workerResponseDto.setCity(allWorkerEntities.get(i).getCity());
            workerResponseDto.setCountry(allWorkerEntities.get(i).getCountry());
            allWorkersResponse.add(workerResponseDto);
        }

        return new ResponseEntity<>(allWorkersResponse, HttpStatus.OK);
    }

    public WorkerEntity getWorker(UUID workerId) {
        return repository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("Worker not found with ID " + workerId));
    }
}
