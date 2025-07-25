package org.garden.egarden.accessibility.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.garden.egarden.accessibility.users.payloads.RegistrationRequestDto;
import org.garden.egarden.accessibility.users.payloads.RegistrationResponseDto;
import org.garden.egarden.accessibility.users.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.garden.egarden.common.config.Constants.OPERATION_ID_NAME;

@Slf4j
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("public/users")
@Tags(value = {@Tag(name = "Public | Users"), @Tag(name = OPERATION_ID_NAME + "PublicUser")})
public class PublicUserController {

    private final UserService service;

    @Operation(
            summary = "Bearer token is required for this endpoint.",
            description = "Register new user by providing Firebase bearer token alongside the registration form data."
    )
    @PostMapping("/register")
    public RegistrationResponseDto register(HttpServletRequest request, @RequestBody RegistrationRequestDto registrationRequest) throws Exception {
        return service.register(request, registrationRequest);
    }
}
