package com.example.egardenrestapi.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.egardenrestapi.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.egardenrestapi.subscriptions.payloads.AddSubscriptionDto;
import com.example.egardenrestapi.workers.payloads.AddWorkerDto;
import com.example.egardenrestapi.cardInformations.payloads.CardInformationDto;
import com.example.egardenrestapi.requests.payloads.RequestDto;
import com.example.egardenrestapi.requests.payloads.RequestIdDto;
import com.example.egardenrestapi.requests.payloads.RequestResponseDto;
import com.example.egardenrestapi.users.payloads.UserProfileDto;
import com.example.egardenrestapi.requests.payloads.UsersRequestDto;
import com.example.egardenrestapi.workers.payloads.WorkerResponseDto;
import com.example.egardenrestapi.cardInformations.entities.CardInformationEntity;
import com.example.egardenrestapi.requests.entities.RequestEntity;
import com.example.egardenrestapi.subscriptions.entities.SubscriptionEntity;
import com.example.egardenrestapi.workers.entities.WorkerEntity;
import com.example.egardenrestapi.cardInformations.repositories.CardInformationRepository;
import com.example.egardenrestapi.requests.repositories.RequestRepository;
import com.example.egardenrestapi.subscriptions.repositories.SubscriptionRepository;
import com.example.egardenrestapi.users.repositories.UserRepository;
import com.example.egardenrestapi.workers.repositories.WorkerRepository;

@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CardInformationRepository cardInformationRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@PostMapping("/add-card-information")
	public ResponseEntity<?> addCardInformation(@RequestBody CardInformationDto cardInformationDto){
		try {
			if (cardInformationDto.getCardNumber()==null || cardInformationDto.getCardNumber().isEmpty()) {
				return new ResponseEntity<>("Card number cannot be null or empty.", HttpStatus.BAD_REQUEST);
			}
			if (cardInformationDto.getPinCode()==null || cardInformationDto.getPinCode().isEmpty()) {
				return new ResponseEntity<>("Cards pin code cannot be null or empty.", HttpStatus.BAD_REQUEST);
			}
			if (cardInformationDto.getThreeDigitNumber()==null || cardInformationDto.getThreeDigitNumber().isEmpty() || cardInformationDto.getThreeDigitNumber().length()!=3) {
				return new ResponseEntity<>("Three digit number cannot be null or empty and must only contain three digits.", HttpStatus.BAD_REQUEST);
			}
			if (cardInformationDto.getExpirationDate()==null) {
				return new ResponseEntity<>("Cards expiration date cannot be null.", HttpStatus.BAD_REQUEST);
			}
			UserEntity userEntity =userRepository.findByUsernameOrEmail(cardInformationDto.getUsername(), cardInformationDto.getUsername());
			CardInformationEntity existingCardInformationEntity =cardInformationRepository.findByUserEntityId(userEntity.getId());
			if(existingCardInformationEntity !=null) {
				existingCardInformationEntity.setCardNumber(cardInformationDto.getCardNumber());
				existingCardInformationEntity.setPinCode(cardInformationDto.getPinCode());
				existingCardInformationEntity.setThreeDigitNumber(cardInformationDto.getThreeDigitNumber());
				existingCardInformationEntity.setExpirationDate(cardInformationDto.getExpirationDate());
				cardInformationRepository.save(existingCardInformationEntity);
				return new ResponseEntity<>("Card Information has been updated.", HttpStatus.OK);
			}
			CardInformationEntity cardInformationEntity =new CardInformationEntity();
			cardInformationEntity.setCardNumber(cardInformationDto.getCardNumber());
			cardInformationEntity.setPinCode(cardInformationDto.getPinCode());
			cardInformationEntity.setThreeDigitNumber(cardInformationDto.getThreeDigitNumber());
			cardInformationEntity.setExpirationDate(cardInformationDto.getExpirationDate());
			cardInformationEntity.setUserEntity(userEntity);
			cardInformationRepository.save(cardInformationEntity);
			return new ResponseEntity<>("Credit card information is successfully added.", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Credit card information has not been saved.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/add-subscription")
	public ResponseEntity<?> addChoseSubscription(@RequestBody AddSubscriptionDto addSubscriptionDto){
		UserEntity userEntity =userRepository.findByUsernameOrEmail(addSubscriptionDto.getUsername(), addSubscriptionDto.getUsername());
		SubscriptionEntity existingSubscriptionEntity =subscriptionRepository.findByUserId(userEntity.getId());
		if (existingSubscriptionEntity !=null) {
			existingSubscriptionEntity.setSubscriptionName(addSubscriptionDto.getSubscriptionName());
			subscriptionRepository.save(existingSubscriptionEntity);
			return new ResponseEntity<>("Subscription successfully updated.", HttpStatus.OK);
		}
		SubscriptionEntity subscriptionEntity =new SubscriptionEntity();
		subscriptionEntity.setSubscriptionName(addSubscriptionDto.getSubscriptionName());
		subscriptionEntity.setUser(userEntity);
		subscriptionRepository.save(subscriptionEntity);
		return new ResponseEntity<>("Subscription successfully added.", HttpStatus.OK);
	}
	
	@PostMapping("/cancel-subscription")
	public ResponseEntity<?> cancelSubscription(@RequestBody UsernameRequestDto usernameRequestDto){
		UserEntity userEntity =userRepository.findByUsernameOrEmail(usernameRequestDto.getUsername(), usernameRequestDto.getUsername());
		SubscriptionEntity subscriptionEntity =subscriptionRepository.findByUserId(userEntity.getId());
		subscriptionRepository.deleteById(subscriptionEntity.getId());
		return new ResponseEntity<>("Subscription successfully canceled.", HttpStatus.OK);
	}
	
	@PostMapping("/create-request")
	public ResponseEntity<?> createRequest(@RequestBody RequestDto requestDto){
		if (requestDto.getAddress()==null || requestDto.getAddress().isEmpty()) {
			return new ResponseEntity<>("Address cannot be null.", HttpStatus.BAD_REQUEST);
		}
		if (requestDto.getCity()==null || requestDto.getAddress().isEmpty()) {
			return new ResponseEntity<>("City cannot be null.", HttpStatus.BAD_REQUEST);
		}
		if (requestDto.getCountry()==null || requestDto.getCountry().isEmpty()) {
			return new ResponseEntity<>("Country cannot be null.", HttpStatus.BAD_REQUEST);
		}
		UserEntity userEntity =userRepository.findByUsernameOrEmail(requestDto.getUsername(), requestDto.getUsername());
		RequestEntity requestEntity =new RequestEntity();
		requestEntity.setChosenMaintenance(requestDto.getChosenMaintenance());
		requestEntity.setChosenDecoration(requestDto.getChosenDecoration());
		requestEntity.setChosenLayout(requestDto.getChosenLayout());
		requestEntity.setPaymentMethod(requestDto.getPaymentMethod());
		requestEntity.setAllowAgency(requestDto.getAllowAgency());
		requestEntity.setPlannedBudget(requestDto.getPlannedBudget());
		requestEntity.setPrice(requestDto.getPrice());
		requestEntity.setStatus("PENDING");
		requestEntity.setCreationDate(LocalDate.now());
		requestEntity.setAddress(requestDto.getAddress());
		requestEntity.setCity(requestDto.getCity());
		requestEntity.setCountry(requestDto.getCountry());
		requestEntity.setUser(userEntity);
		requestRepository.save(requestEntity);
		return new ResponseEntity<>("Request has been successfully made.", HttpStatus.OK);
	}
	
	@GetMapping("/get-all-requests")
	public ResponseEntity<List<RequestResponseDto>> getAllRequestsForAdmin(){
		List<RequestEntity> allRequestEntities =requestRepository.findAll();
		List<RequestResponseDto> allRequestsResponse=new ArrayList<>();
		for (int i = 0; i< allRequestEntities.size(); i++) {
			if(allRequestEntities.get(i).getStatus().equals("PENDING")) {
				RequestResponseDto requestResponseDto=new RequestResponseDto();
				requestResponseDto.setId(allRequestEntities.get(i).getId());
				if (allRequestEntities.get(i).getChosenMaintenance()==null) {
					requestResponseDto.setChosenMaintenance("NOT SELECTED");
				}
				else {
					requestResponseDto.setChosenMaintenance(allRequestEntities.get(i).getChosenMaintenance());
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
				requestResponseDto.setPaymentMethod(allRequestEntities.get(i).getPaymentMethod());
				requestResponseDto.setAllowAgency(allRequestEntities.get(i).getAllowAgency());
				requestResponseDto.setPlannedBudget(allRequestEntities.get(i).getPlannedBudget());
				requestResponseDto.setPrice(allRequestEntities.get(i).getPrice());
				requestResponseDto.setStatus(allRequestEntities.get(i).getStatus());
				requestResponseDto.setCreationDate(allRequestEntities.get(i).getCreationDate());
				requestResponseDto.setUsername(allRequestEntities.get(i).getUser().getUsername());
				requestResponseDto.setEmail(allRequestEntities.get(i).getUser().getEmail());
				requestResponseDto.setFirstName(allRequestEntities.get(i).getUser().getFirstName());
				requestResponseDto.setLastName(allRequestEntities.get(i).getUser().getLastName());
				requestResponseDto.setAddress(allRequestEntities.get(i).getAddress());
				requestResponseDto.setCity(allRequestEntities.get(i).getCity());
				requestResponseDto.setCountry(allRequestEntities.get(i).getCountry());
				allRequestsResponse.add(requestResponseDto);
			}
		}
		return new ResponseEntity<>(allRequestsResponse, HttpStatus.OK);
	}
	
	@PostMapping("/approve-request")
	public ResponseEntity<?> approveRequest(@RequestBody RequestIdDto requestIdDto){
		RequestEntity requestEntity =requestRepository.findById(requestIdDto.getRequestId()).orElse(null);
		requestEntity.setStatus("APPROVED");
		requestRepository.save(requestEntity);
		return new ResponseEntity<>("Request has been successfully approved.", HttpStatus.OK);
	}
	
	@PostMapping("/deny-request")
	public ResponseEntity<?> denyRequest(@RequestBody RequestIdDto requestIdDto){
		RequestEntity requestEntity =requestRepository.findById(requestIdDto.getRequestId()).orElse(null);
		requestEntity.setStatus("DENIED");
		requestRepository.save(requestEntity);
		return new ResponseEntity<>("Request has been successfully denied.", HttpStatus.OK);
	}
	
	@PostMapping("/get-user-requests")
	public ResponseEntity<List<UsersRequestDto>> getUsersRequest(@RequestBody UsernameRequestDto usernameRequestDto){
		List<RequestEntity> allRequestEntities =requestRepository.findAll();
		List<UsersRequestDto> allUsersRequest=new ArrayList<>();
		for (int i = 0; i< allRequestEntities.size(); i++) {
			if (allRequestEntities.get(i).getUser().getUsername().equals(usernameRequestDto.getUsername())) {
				UsersRequestDto usersRequestDto=new UsersRequestDto();
				if (allRequestEntities.get(i).getChosenMaintenance()==null) {
					usersRequestDto.setChosenMaintenance("NOT SELECTED");
				}
				else {
					usersRequestDto.setChosenMaintenance(allRequestEntities.get(i).getChosenMaintenance());
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
				usersRequestDto.setPaymentMethod(allRequestEntities.get(i).getPaymentMethod());
				usersRequestDto.setAllowAgency(allRequestEntities.get(i).getAllowAgency());
				usersRequestDto.setPlannedBudget(allRequestEntities.get(i).getPlannedBudget());
				usersRequestDto.setPrice(allRequestEntities.get(i).getPrice());
				usersRequestDto.setStatus(allRequestEntities.get(i).getStatus());
				usersRequestDto.setCreationDate(allRequestEntities.get(i).getCreationDate());
				usersRequestDto.setAddress(allRequestEntities.get(i).getAddress());
				usersRequestDto.setCity(allRequestEntities.get(i).getCity());
				usersRequestDto.setCountry(allRequestEntities.get(i).getCountry());
				allUsersRequest.add(usersRequestDto);
			}
		}
		return new ResponseEntity<>(allUsersRequest, HttpStatus.OK);
	}
	
	@PostMapping("/sign-up-work")
	public ResponseEntity<?> signUpForWork(@RequestBody AddWorkerDto addWorkerDto){
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
			UserEntity userEntity =userRepository.findByUsernameOrEmail(addWorkerDto.getUsername(), addWorkerDto.getUsername());
			WorkerEntity existingWorkerEntity =workerRepository.findByUserId(userEntity.getId());
			if (existingWorkerEntity !=null) {
				existingWorkerEntity.setDescription(addWorkerDto.getDescription());
				existingWorkerEntity.setPhoneNumber(addWorkerDto.getPhoneNumber());
				existingWorkerEntity.setCity(addWorkerDto.getCity());
				existingWorkerEntity.setCountry(addWorkerDto.getCountry());
				workerRepository.save(existingWorkerEntity);
				return new ResponseEntity<>("Work details have been updated.", HttpStatus.OK);
			}
			WorkerEntity workerEntity =new WorkerEntity();
			workerEntity.setDescription(addWorkerDto.getDescription());
			workerEntity.setPhoneNumber(addWorkerDto.getPhoneNumber());
			workerEntity.setCity(addWorkerDto.getCity());
			workerEntity.setCountry(addWorkerDto.getCountry());
			workerEntity.setUser(userEntity);
			workerRepository.save(workerEntity);
			return new ResponseEntity<>("Work details have been successfully added.", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Work details have not been saved.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-all-workers")
	public ResponseEntity<List<WorkerResponseDto>> getAllWorkers(){
		List<WorkerEntity> allWorkerEntities =workerRepository.findAll();
		List<WorkerResponseDto> allWorkersResponse=new ArrayList<>();
		for (int i = 0; i< allWorkerEntities.size(); i++) {
			WorkerResponseDto workerResponseDto=new WorkerResponseDto();
			workerResponseDto.setFirstName(allWorkerEntities.get(i).getUser().getFirstName());
			workerResponseDto.setLastName(allWorkerEntities.get(i).getUser().getLastName());
			workerResponseDto.setEmail(allWorkerEntities.get(i).getUser().getEmail());
			workerResponseDto.setUsername(allWorkerEntities.get(i).getUser().getUsername());
			workerResponseDto.setDescription(allWorkerEntities.get(i).getDescription());
			workerResponseDto.setPhoneNumber(allWorkerEntities.get(i).getPhoneNumber());
			workerResponseDto.setCity(allWorkerEntities.get(i).getCity());
			workerResponseDto.setCountry(allWorkerEntities.get(i).getCountry());
			allWorkersResponse.add(workerResponseDto);
		}
		return new ResponseEntity<>(allWorkersResponse, HttpStatus.OK);
	}
}
