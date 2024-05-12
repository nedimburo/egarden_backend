package com.example.egardenrestapi.users.services;

import com.example.egardenrestapi.cardInformations.entities.CardInformationEntity;
import com.example.egardenrestapi.cardInformations.services.CardInformationService;
import com.example.egardenrestapi.users.User;
import com.example.egardenrestapi.users.entities.RoleType;
import com.example.egardenrestapi.users.entities.UserEntity;
import com.example.egardenrestapi.users.payloads.LoginDto;
import com.example.egardenrestapi.users.payloads.LoginResponseDto;
import com.example.egardenrestapi.users.payloads.RegisterDto;
import com.example.egardenrestapi.users.payloads.UserProfileDto;
import com.example.egardenrestapi.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class UserService implements User {

    private final UserRepository repository;
    private final CardInformationService cardInformationService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<LoginResponseDto> authenticateUser(LoginDto loginDto){
        LoginResponseDto responseDto = new LoginResponseDto();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserEntity userEntity = repository.findByUsernameOrEmail(loginDto.getEmail(), loginDto.getEmail());
            CardInformationEntity cardInformationEntity = cardInformationService.findByUserEntityId(userEntity.getId());
            responseDto.setMessage("User login successfully.");
            responseDto.setUsername(userEntity.getUsername());
            responseDto.setRole(String.valueOf(userEntity.getRole()));

            if(cardInformationEntity !=null) {
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
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userEntity.setRole(RoleType.USER);

        repository.save(userEntity);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserProfileDto> getUsersProfile(String username){
        UserEntity userEntity = repository.findByUsernameOrEmail(username, username);
        CardInformationEntity cardInformationEntity = cardInformationService.findByUserEntityId(userEntity.getId());

        UserProfileDto userProfileDto=new UserProfileDto();
        userProfileDto.setFirstName(userEntity.getFirstName());
        userProfileDto.setLastName(userEntity.getLastName());
        userProfileDto.setEmail(userEntity.getEmail());
        userProfileDto.setUsername(userEntity.getUsername());
        userProfileDto.setGender(userEntity.getGender());
        userProfileDto.setBirthDate(userEntity.getBirthDate());

        if (cardInformationEntity !=null) {
            userProfileDto.setHasCard(true);
        }
        else {
            userProfileDto.setHasCard(false);
        }

        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }
}
