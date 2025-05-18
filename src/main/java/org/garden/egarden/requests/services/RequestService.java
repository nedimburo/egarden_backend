package org.garden.egarden.requests.services;

import org.garden.egarden.requests.Request;
import org.garden.egarden.requests.entities.*;
import org.garden.egarden.requests.payloads.RequestDto;
import org.garden.egarden.requests.payloads.RequestResponseDto;
import org.garden.egarden.requests.payloads.UsersRequestDto;
import org.garden.egarden.requests.repositories.RequestRepository;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.services.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class RequestService implements Request {

    private final RequestRepository repository;
    private final UserService userService;

    @Transactional
    public ResponseEntity<?> createRequest(RequestDto requestDto){
        if (requestDto.getAddress()==null || requestDto.getAddress().isEmpty()) {
            return new ResponseEntity<>("Address cannot be null.", HttpStatus.BAD_REQUEST);
        }

        if (requestDto.getCity()==null || requestDto.getAddress().isEmpty()) {
            return new ResponseEntity<>("City cannot be null.", HttpStatus.BAD_REQUEST);
        }

        if (requestDto.getCountry()==null || requestDto.getCountry().isEmpty()) {
            return new ResponseEntity<>("Country cannot be null.", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userService.findByUsernameOrEmail(requestDto.getUsername(), requestDto.getUsername());
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setChosenMaintenance(MaintenanceType.valueOf(requestDto.getChosenMaintenance()));
        requestEntity.setChosenDecoration(requestDto.getChosenDecoration());
        requestEntity.setChosenLayout(requestDto.getChosenLayout());
        requestEntity.setPaymentMethod(PaymentMethod.valueOf(requestDto.getPaymentMethod()));
        requestEntity.setAllowAgency(ConfirmationType.valueOf(requestDto.getAllowAgency()));
        requestEntity.setPlannedBudget(requestDto.getPlannedBudget());
        requestEntity.setPrice(requestDto.getPrice());
        requestEntity.setStatus(StatusType.PENDING);
        requestEntity.setCreationDate(LocalDate.now());
        requestEntity.setAddress(requestDto.getAddress());
        requestEntity.setCity(requestDto.getCity());
        requestEntity.setCountry(requestDto.getCountry());
        requestEntity.setUserEntity(userEntity);

        repository.save(requestEntity);
        return new ResponseEntity<>("Request has been successfully made.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<RequestResponseDto>> getAllRequestsForAdmin(){
        List<RequestEntity> allRequestEntities = repository.findAll();
        List<RequestResponseDto> allRequestsResponse=new ArrayList<>();

        for (int i = 0; i< allRequestEntities.size(); i++) {
            if(allRequestEntities.get(i).getStatus().equals(StatusType.PENDING)) {
                RequestResponseDto requestResponseDto=new RequestResponseDto();
                requestResponseDto.setId(allRequestEntities.get(i).getId());
                if (allRequestEntities.get(i).getChosenMaintenance()==null) {
                    requestResponseDto.setChosenMaintenance("NOT SELECTED");
                }
                else {
                    requestResponseDto.setChosenMaintenance(String.valueOf(allRequestEntities.get(i).getChosenMaintenance()));
                }
                if (allRequestEntities.get(i).getChosenDecoration()==null) {
                    requestResponseDto.setChosenDecoration("NOT SELECTED");
                }
                else {
                    requestResponseDto.setChosenDecoration(allRequestEntities.get(i).getChosenDecoration());
                }
                if (allRequestEntities.get(i).getChosenLayout()==null) {
                    requestResponseDto.setChosenLayout("NOT SELECTED");
                }
                else {
                    requestResponseDto.setChosenLayout(allRequestEntities.get(i).getChosenLayout());
                }

                requestResponseDto.setPaymentMethod(String.valueOf(allRequestEntities.get(i).getPaymentMethod()));
                requestResponseDto.setAllowAgency(String.valueOf(allRequestEntities.get(i).getAllowAgency()));
                requestResponseDto.setPlannedBudget(allRequestEntities.get(i).getPlannedBudget());
                requestResponseDto.setPrice(allRequestEntities.get(i).getPrice());
                requestResponseDto.setStatus(String.valueOf(allRequestEntities.get(i).getStatus()));
                requestResponseDto.setCreationDate(allRequestEntities.get(i).getCreationDate());
                requestResponseDto.setUsername(allRequestEntities.get(i).getUserEntity().getUsername());
                requestResponseDto.setEmail(allRequestEntities.get(i).getUserEntity().getEmail());
                requestResponseDto.setFirstName(allRequestEntities.get(i).getUserEntity().getFirstName());
                requestResponseDto.setLastName(allRequestEntities.get(i).getUserEntity().getLastName());
                requestResponseDto.setAddress(allRequestEntities.get(i).getAddress());
                requestResponseDto.setCity(allRequestEntities.get(i).getCity());
                requestResponseDto.setCountry(allRequestEntities.get(i).getCountry());
                allRequestsResponse.add(requestResponseDto);
            }
        }
        return new ResponseEntity<>(allRequestsResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> approveRequest(Integer requestId){
        RequestEntity requestEntity = repository.findById(requestId).orElse(null);
        requestEntity.setStatus(StatusType.APPROVED);
        repository.save(requestEntity);
        return new ResponseEntity<>("Request has been successfully approved.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> denyRequest(Integer requestId){
        RequestEntity requestEntity = repository.findById(requestId).orElse(null);
        requestEntity.setStatus(StatusType.DENIED);
        repository.save(requestEntity);
        return new ResponseEntity<>("Request has been successfully denied.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<List<UsersRequestDto>> getUsersRequest(String username){
        List<RequestEntity> allRequestEntities = repository.findAll();
        List<UsersRequestDto> allUsersRequest=new ArrayList<>();

        for (int i = 0; i< allRequestEntities.size(); i++) {
            if (allRequestEntities.get(i).getUserEntity().getUsername().equals(username)) {
                UsersRequestDto usersRequestDto=new UsersRequestDto();
                if (allRequestEntities.get(i).getChosenMaintenance()==null) {
                    usersRequestDto.setChosenMaintenance("NOT SELECTED");
                }
                else {
                    usersRequestDto.setChosenMaintenance(String.valueOf(allRequestEntities.get(i).getChosenMaintenance()));
                }
                if (allRequestEntities.get(i).getChosenDecoration()==null) {
                    usersRequestDto.setChosenDecoration("NOT SELECTED");
                }
                else {
                    usersRequestDto.setChosenDecoration(allRequestEntities.get(i).getChosenDecoration());
                }
                if (allRequestEntities.get(i).getChosenLayout()==null) {
                    usersRequestDto.setChosenLayout("NOT SELECTED");
                }
                else {
                    usersRequestDto.setChosenLayout(allRequestEntities.get(i).getChosenLayout());
                }
                usersRequestDto.setPaymentMethod(String.valueOf(allRequestEntities.get(i).getPaymentMethod()));
                usersRequestDto.setAllowAgency(String.valueOf(allRequestEntities.get(i).getAllowAgency()));
                usersRequestDto.setPlannedBudget(allRequestEntities.get(i).getPlannedBudget());
                usersRequestDto.setPrice(allRequestEntities.get(i).getPrice());
                usersRequestDto.setStatus(String.valueOf(allRequestEntities.get(i).getStatus()));
                usersRequestDto.setCreationDate(allRequestEntities.get(i).getCreationDate());
                usersRequestDto.setAddress(allRequestEntities.get(i).getAddress());
                usersRequestDto.setCity(allRequestEntities.get(i).getCity());
                usersRequestDto.setCountry(allRequestEntities.get(i).getCountry());
                allUsersRequest.add(usersRequestDto);
            }
        }
        return new ResponseEntity<>(allUsersRequest, HttpStatus.OK);
    }
}
