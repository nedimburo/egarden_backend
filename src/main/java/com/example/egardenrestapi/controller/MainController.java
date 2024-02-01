package com.example.egardenrestapi.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.egardenrestapi.dto.AddSubscriptionDto;
import com.example.egardenrestapi.dto.AddWorkerDto;
import com.example.egardenrestapi.dto.CardInformationDto;
import com.example.egardenrestapi.dto.LoginDto;
import com.example.egardenrestapi.dto.LoginResponseDto;
import com.example.egardenrestapi.dto.RegisterDto;
import com.example.egardenrestapi.dto.RequestDto;
import com.example.egardenrestapi.dto.RequestIdDto;
import com.example.egardenrestapi.dto.RequestResponseDto;
import com.example.egardenrestapi.dto.UserProfileDto;
import com.example.egardenrestapi.dto.UsernameRequestDto;
import com.example.egardenrestapi.dto.UsersRequestDto;
import com.example.egardenrestapi.dto.WorkerResponseDto;
import com.example.egardenrestapi.entity.CardInformation;
import com.example.egardenrestapi.entity.Request;
import com.example.egardenrestapi.entity.Subscription;
import com.example.egardenrestapi.entity.User;
import com.example.egardenrestapi.entity.Worker;
import com.example.egardenrestapi.repository.CardInformationRepository;
import com.example.egardenrestapi.repository.RequestRepository;
import com.example.egardenrestapi.repository.SubscriptionRepository;
import com.example.egardenrestapi.repository.UserRepository;
import com.example.egardenrestapi.repository.WorkerRepository;

@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginDto){
		LoginResponseDto responseDto=new LoginResponseDto();
		try {
			Authentication authentication=authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			//
			User user=userRepository.findByUsernameOrEmail(loginDto.getEmail(), loginDto.getEmail());
			CardInformation cardInformation=cardInformationRepository.findByUserId(user.getId());
			responseDto.setMessage("User login successfully.");
			responseDto.setUsername(user.getUsername());
			responseDto.setRole(user.getRole());
			if(cardInformation!=null) {
				responseDto.setHasCard(true);
			}
			else {
				responseDto.setHasCard(false);
			}
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}catch(BadCredentialsException e) {
			responseDto.setMessage("Invalid email or password");
			return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			return new ResponseEntity<>("Username is already in use.", HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			return new ResponseEntity<>("Email is already in use.", HttpStatus.BAD_REQUEST);
		}
		String rawPassword = registerDto.getPassword();
	    if (rawPassword == null || rawPassword.isEmpty()) {
	        return new ResponseEntity<>("Password cannot be null or empty.", HttpStatus.BAD_REQUEST);
	    }
		User user=new User();
		user.setFirstName(registerDto.getFirstName());
		user.setLastName(registerDto.getLastName());
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setGender(registerDto.getGender());
		user.setBirthDate(registerDto.getBirthDate());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
	}
	
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
			User user=userRepository.findByUsernameOrEmail(cardInformationDto.getUsername(), cardInformationDto.getUsername());
			CardInformation existingCardInformation=cardInformationRepository.findByUserId(user.getId());
			if(existingCardInformation!=null) {
				existingCardInformation.setCardNumber(cardInformationDto.getCardNumber());
				existingCardInformation.setPinCode(cardInformationDto.getPinCode());
				existingCardInformation.setThreeDigitNumber(cardInformationDto.getThreeDigitNumber());
				existingCardInformation.setExpirationDate(cardInformationDto.getExpirationDate());
				cardInformationRepository.save(existingCardInformation);
				return new ResponseEntity<>("Card Information has been updated.", HttpStatus.OK);
			}
			CardInformation cardInformation=new CardInformation();
			cardInformation.setCardNumber(cardInformationDto.getCardNumber());
			cardInformation.setPinCode(cardInformationDto.getPinCode());
			cardInformation.setThreeDigitNumber(cardInformationDto.getThreeDigitNumber());
			cardInformation.setExpirationDate(cardInformationDto.getExpirationDate());
			cardInformation.setUser(user);
			cardInformationRepository.save(cardInformation);
			return new ResponseEntity<>("Credit card information is successfully added.", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Credit card information has not been saved.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/user-profile")
    public ResponseEntity<UserProfileDto> handleUsernameRequest(@RequestBody UsernameRequestDto usernameRequestDto) {
        String username = usernameRequestDto.getUsername();
        User user=userRepository.findByUsernameOrEmail(username, username);
        CardInformation cardInformation=cardInformationRepository.findByUserId(user.getId());
        UserProfileDto userProfileDto=new UserProfileDto();
        userProfileDto.setFirstName(user.getFirstName());
        userProfileDto.setLastName(user.getLastName());
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setGender(user.getGender());
        userProfileDto.setBirthDate(user.getBirthDate());
        if (cardInformation!=null) {
        	userProfileDto.setHasCard(true);
        }
        else {
        	userProfileDto.setHasCard(false);
        }
        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }
	
	@PostMapping("/add-subscription")
	public ResponseEntity<?> addChoseSubscription(@RequestBody AddSubscriptionDto addSubscriptionDto){
		User user=userRepository.findByUsernameOrEmail(addSubscriptionDto.getUsername(), addSubscriptionDto.getUsername());
		Subscription existingSubscription=subscriptionRepository.findByUserId(user.getId());
		if (existingSubscription!=null) {
			existingSubscription.setSubscriptionName(addSubscriptionDto.getSubscriptionName());
			subscriptionRepository.save(existingSubscription);
			return new ResponseEntity<>("Subscription successfully updated.", HttpStatus.OK);
		}
		Subscription subscription=new Subscription();
		subscription.setSubscriptionName(addSubscriptionDto.getSubscriptionName());
		subscription.setUser(user);
		subscriptionRepository.save(subscription);
		return new ResponseEntity<>("Subscription successfully added.", HttpStatus.OK);
	}
	
	@PostMapping("/cancel-subscription")
	public ResponseEntity<?> cancelSubscription(@RequestBody UsernameRequestDto usernameRequestDto){
		User user=userRepository.findByUsernameOrEmail(usernameRequestDto.getUsername(), usernameRequestDto.getUsername());
		Subscription subscription=subscriptionRepository.findByUserId(user.getId());
		subscriptionRepository.deleteById(subscription.getId());
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
		User user=userRepository.findByUsernameOrEmail(requestDto.getUsername(), requestDto.getUsername());
		Request request=new Request();
		request.setChosenMaintenance(requestDto.getChosenMaintenance());
		request.setChosenDecoration(requestDto.getChosenDecoration());
		request.setChosenLayout(requestDto.getChosenLayout());
		request.setPaymentMethod(requestDto.getPaymentMethod());
		request.setAllowAgency(requestDto.getAllowAgency());
		request.setPlannedBudget(requestDto.getPlannedBudget());
		request.setPrice(requestDto.getPrice());
		request.setStatus("PENDING");
		request.setCreationDate(LocalDate.now());
		request.setAddress(requestDto.getAddress());
		request.setCity(requestDto.getCity());
		request.setCountry(requestDto.getCountry());
		request.setUser(user);
		requestRepository.save(request);
		return new ResponseEntity<>("Request has been successfully made.", HttpStatus.OK);
	}
	
	@GetMapping("/get-all-requests")
	public ResponseEntity<List<RequestResponseDto>> getAllRequestsForAdmin(){
		List<Request> allRequests=requestRepository.findAll();
		List<RequestResponseDto> allRequestsResponse=new ArrayList<>();
		for (int i=0; i<allRequests.size(); i++) {
			if(allRequests.get(i).getStatus().equals("PENDING")) {
				RequestResponseDto requestResponseDto=new RequestResponseDto();
				requestResponseDto.setId(allRequests.get(i).getId());
				if (allRequests.get(i).getChosenMaintenance()==null) {
					requestResponseDto.setChosenMaintenance("NOT SELECTED");
				}
				else {
					requestResponseDto.setChosenMaintenance(allRequests.get(i).getChosenMaintenance());
				}
				if (allRequests.get(i).getChosenDecoration()==null) {
					requestResponseDto.setChosenDecoration("NOT SELECTED");
				}
				else {
					requestResponseDto.setChosenDecoration(allRequests.get(i).getChosenDecoration());
				}
				if (allRequests.get(i).getChosenLayout()==null) {
					requestResponseDto.setChosenLayout("NOT SELECTED");
				}
				else {
					requestResponseDto.setChosenLayout(allRequests.get(i).getChosenLayout());
				}
				requestResponseDto.setPaymentMethod(allRequests.get(i).getPaymentMethod());
				requestResponseDto.setAllowAgency(allRequests.get(i).getAllowAgency());
				requestResponseDto.setPlannedBudget(allRequests.get(i).getPlannedBudget());
				requestResponseDto.setPrice(allRequests.get(i).getPrice());
				requestResponseDto.setStatus(allRequests.get(i).getStatus());
				requestResponseDto.setCreationDate(allRequests.get(i).getCreationDate());
				requestResponseDto.setUsername(allRequests.get(i).getUser().getUsername());		
				requestResponseDto.setEmail(allRequests.get(i).getUser().getEmail());
				requestResponseDto.setFirstName(allRequests.get(i).getUser().getFirstName());
				requestResponseDto.setLastName(allRequests.get(i).getUser().getLastName());
				requestResponseDto.setAddress(allRequests.get(i).getAddress());
				requestResponseDto.setCity(allRequests.get(i).getCity());
				requestResponseDto.setCountry(allRequests.get(i).getCountry());
				allRequestsResponse.add(requestResponseDto);
			}
		}
		return new ResponseEntity<>(allRequestsResponse, HttpStatus.OK);
	}
	
	@PostMapping("/approve-request")
	public ResponseEntity<?> approveRequest(@RequestBody RequestIdDto requestIdDto){
		Request request=requestRepository.findById(requestIdDto.getRequestId()).orElse(null);
		request.setStatus("APPROVED");
		requestRepository.save(request);
		return new ResponseEntity<>("Request has been successfully approved.", HttpStatus.OK);
	}
	
	@PostMapping("/deny-request")
	public ResponseEntity<?> denyRequest(@RequestBody RequestIdDto requestIdDto){
		Request request=requestRepository.findById(requestIdDto.getRequestId()).orElse(null);
		request.setStatus("DENIED");
		requestRepository.save(request);
		return new ResponseEntity<>("Request has been successfully denied.", HttpStatus.OK);
	}
	
	@PostMapping("/get-user-requests")
	public ResponseEntity<List<UsersRequestDto>> getUsersRequest(@RequestBody UsernameRequestDto usernameRequestDto){
		List<Request> allRequests=requestRepository.findAll();
		List<UsersRequestDto> allUsersRequest=new ArrayList<>();
		for (int i=0; i<allRequests.size(); i++) {
			if (allRequests.get(i).getUser().getUsername().equals(usernameRequestDto.getUsername())) {
				UsersRequestDto usersRequestDto=new UsersRequestDto();
				if (allRequests.get(i).getChosenMaintenance()==null) {
					usersRequestDto.setChosenMaintenance("NOT SELECTED");
				}
				else {
					usersRequestDto.setChosenMaintenance(allRequests.get(i).getChosenMaintenance());
				}
				if (allRequests.get(i).getChosenDecoration()==null) {
					usersRequestDto.setChosenDecoration("NOT SELECTED");
				}
				else {
					usersRequestDto.setChosenDecoration(allRequests.get(i).getChosenDecoration());
				}
				if (allRequests.get(i).getChosenLayout()==null) {
					usersRequestDto.setChosenLayout("NOT SELECTED");
				}
				else {
					usersRequestDto.setChosenLayout(allRequests.get(i).getChosenLayout());
				}
				usersRequestDto.setPaymentMethod(allRequests.get(i).getPaymentMethod());
				usersRequestDto.setAllowAgency(allRequests.get(i).getAllowAgency());
				usersRequestDto.setPlannedBudget(allRequests.get(i).getPlannedBudget());
				usersRequestDto.setPrice(allRequests.get(i).getPrice());
				usersRequestDto.setStatus(allRequests.get(i).getStatus());
				usersRequestDto.setCreationDate(allRequests.get(i).getCreationDate());
				usersRequestDto.setAddress(allRequests.get(i).getAddress());
				usersRequestDto.setCity(allRequests.get(i).getCity());
				usersRequestDto.setCountry(allRequests.get(i).getCountry());
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
			User user=userRepository.findByUsernameOrEmail(addWorkerDto.getUsername(), addWorkerDto.getUsername());
			Worker existingWorker=workerRepository.findByUserId(user.getId());
			if (existingWorker!=null) {
				existingWorker.setDescription(addWorkerDto.getDescription());
				existingWorker.setPhoneNumber(addWorkerDto.getPhoneNumber());
				existingWorker.setCity(addWorkerDto.getCity());
				existingWorker.setCountry(addWorkerDto.getCountry());
				workerRepository.save(existingWorker);
				return new ResponseEntity<>("Work details have been updated.", HttpStatus.OK);
			}
			Worker worker=new Worker();
			worker.setDescription(addWorkerDto.getDescription());
			worker.setPhoneNumber(addWorkerDto.getPhoneNumber());
			worker.setCity(addWorkerDto.getCity());
			worker.setCountry(addWorkerDto.getCountry());
			worker.setUser(user);
			workerRepository.save(worker);
			return new ResponseEntity<>("Work details have been successfully added.", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Work details have not been saved.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get-all-workers")
	public ResponseEntity<List<WorkerResponseDto>> getAllWorkers(){
		List<Worker> allWorkers=workerRepository.findAll();
		List<WorkerResponseDto> allWorkersResponse=new ArrayList<>();
		for (int i=0; i<allWorkers.size(); i++) {
			WorkerResponseDto workerResponseDto=new WorkerResponseDto();
			workerResponseDto.setFirstName(allWorkers.get(i).getUser().getFirstName());
			workerResponseDto.setLastName(allWorkers.get(i).getUser().getLastName());
			workerResponseDto.setEmail(allWorkers.get(i).getUser().getEmail());
			workerResponseDto.setUsername(allWorkers.get(i).getUser().getUsername());
			workerResponseDto.setDescription(allWorkers.get(i).getDescription());
			workerResponseDto.setPhoneNumber(allWorkers.get(i).getPhoneNumber());
			workerResponseDto.setCity(allWorkers.get(i).getCity());
			workerResponseDto.setCountry(allWorkers.get(i).getCountry());
			allWorkersResponse.add(workerResponseDto);
		}
		return new ResponseEntity<>(allWorkersResponse, HttpStatus.OK);
	}
}
