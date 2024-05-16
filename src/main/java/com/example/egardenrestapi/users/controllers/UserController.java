package com.example.egardenrestapi.users.controllers;

import com.example.egardenrestapi.users.payloads.LoginDto;
import com.example.egardenrestapi.users.payloads.LoginResponseDto;
import com.example.egardenrestapi.users.payloads.RegisterDto;
import com.example.egardenrestapi.users.payloads.UserProfileDto;
import com.example.egardenrestapi.users.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("public/user")
@Tags(value = @Tag(name = "Public | User"))
public class UserController {

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginDto){
        return service.authenticateUser(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        return service.registerUser(registerDto);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfileDto> getUsersProfile(@PathVariable String username){
        return service.getUsersProfile(username);
    }
}
