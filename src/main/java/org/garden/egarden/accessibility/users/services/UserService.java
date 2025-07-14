package org.garden.egarden.accessibility.users.services;

import jakarta.persistence.EntityNotFoundException;
import org.garden.egarden.accessibility.roles.entities.RoleEntity;
import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.payloads.LoginDto;
import org.garden.egarden.accessibility.users.payloads.LoginResponseDto;
import org.garden.egarden.accessibility.users.payloads.RegisterDto;
import org.garden.egarden.accessibility.users.payloads.UserProfileDto;
import org.garden.egarden.accessibility.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    @Transactional
    public RoleName getUserRoleById(String id){
        UserEntity user = getUser(id);
        RoleEntity role = user.getRole();
        return role.getName();
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> authenticateUser(LoginDto loginDto){
        LoginResponseDto responseDto = new LoginResponseDto();
        try {
            UserEntity userEntity = repository.findByUsernameOrEmail(loginDto.getEmail(), loginDto.getEmail());
            responseDto.setMessage("User login successfully.");
            responseDto.setUsername(userEntity.getUsername());
            responseDto.setRole(String.valueOf(userEntity.getRole()));

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch(BadCredentialsException e) {
            responseDto.setMessage("Invalid email or password");
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public ResponseEntity<?> registerUser(RegisterDto registerDto){
        if (repository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is already in use.", HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is already in use.", HttpStatus.BAD_REQUEST);
        }

        String rawPassword = registerDto.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            return new ResponseEntity<>("Password cannot be null or empty.", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity =new UserEntity();
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setLastName(registerDto.getLastName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setUsername(registerDto.getUsername());
        userEntity.setGender(registerDto.getGender());
        userEntity.setBirthDate(registerDto.getBirthDate());

        repository.save(userEntity);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserProfileDto> getUsersProfile(String username){
        UserEntity userEntity = repository.findByUsernameOrEmail(username, username);

        UserProfileDto userProfileDto=new UserProfileDto();
        userProfileDto.setFirstName(userEntity.getFirstName());
        userProfileDto.setLastName(userEntity.getLastName());
        userProfileDto.setEmail(userEntity.getEmail());
        userProfileDto.setUsername(userEntity.getUsername());
        userProfileDto.setGender(userEntity.getGender());
        userProfileDto.setBirthDate(userEntity.getBirthDate());

        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }

    public UserEntity findByUsernameOrEmail(String username, String email){
        return repository.findByUsernameOrEmail(username, email);
    }

    public UserEntity getUser(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    public Boolean doesUserExist(String userId) {
        return repository.existsById(userId);
    }
}
