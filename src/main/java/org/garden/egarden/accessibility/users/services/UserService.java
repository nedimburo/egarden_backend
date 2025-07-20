package org.garden.egarden.accessibility.users.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.garden.egarden.accessibility.roles.entities.RoleEntity;
import org.garden.egarden.accessibility.roles.entities.RoleName;
import org.garden.egarden.accessibility.roles.services.RoleService;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.mappers.UserMapper;
import org.garden.egarden.accessibility.users.payloads.*;
import org.garden.egarden.accessibility.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.common.config.Auth;
import org.garden.egarden.common.exceptions.BadRequestException;
import org.garden.egarden.common.exceptions.ResourceAlreadyExistsException;
import org.garden.egarden.common.exceptions.ResourceNotFoundException;
import org.garden.egarden.common.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import static org.garden.egarden.accessibility.roles.entities.RoleName.CLIENT;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Transactional
    public RoleName getUserRoleById(String id){
        UserEntity user = getUser(id);
        RoleEntity role = user.getRole();
        return role.getName();
    }

    @Transactional
    public RegistrationResponseDto register(HttpServletRequest request, RegistrationRequestDto registrationRequest) throws Exception {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Token header missing or not properly formatted.");
        }

        String token = tokenHeader.substring(7);
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String userId = decodedToken.getUid();

            if (repository.existsById(userId)) {
                throw new ResourceAlreadyExistsException("User profile with ID: " + userId + " already exists.");
            }

            UserEntity newUser = new UserEntity();
            newUser.setId(userId);
            newUser.setEmail(decodedToken.getEmail());
            newUser.setUsername(registrationRequest.getUsername());
            newUser.setFirstName(registrationRequest.getFirstName());
            newUser.setLastName(registrationRequest.getLastName());
            newUser.setGender(registrationRequest.getGender());
            newUser.setBirthDate(registrationRequest.getBirthDate());
            newUser.setRole(roleService.findByName(CLIENT));
            UserEntity savedUser = repository.save(newUser);

            RegistrationResponseDto response = userMapper.toRegistrationDto(savedUser);
            response.setMessage("User has been successfully registered.");
            return response;
        } catch (FirebaseAuthException e) {
            throw new Exception("Invalid or expired token: " + e.getMessage());
        }
    }

    @Transactional
    public UserProfileResponseDto getUsersProfile() {
        String uid;
        try {
            uid = Auth.getUserId();
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }

        UserEntity user;
        try {
            user = getUser(uid);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User with ID: " + uid + " doesn't exist");
        }

        try {
            return userMapper.toUserProfileDto(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user profile data: " + e.getMessage());
        }
    }

    public UserEntity findByUsernameOrEmail(String username, String email){
        return repository.findByUsernameOrEmail(username, email);
    }

    public UserEntity getUser(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

}
