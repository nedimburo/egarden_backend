package org.garden.egarden.requests.controllers;

import org.garden.egarden.requests.payloads.RequestDto;
import org.garden.egarden.requests.payloads.RequestResponseDto;
import org.garden.egarden.requests.payloads.UsersRequestDto;
import org.garden.egarden.requests.services.RequestService;
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
@RequestMapping("public/request")
@Tags(value = @Tag(name = "Public | Request"))
public class RequestController {

    private final RequestService service;

    @PostMapping("/create-request")
    public ResponseEntity<?> createRequest(@RequestBody RequestDto requestDto){
        return service.createRequest(requestDto);
    }

    @GetMapping("/get-all-requests")
    public ResponseEntity<List<RequestResponseDto>> getAllRequestsForAdmin(){
        return service.getAllRequestsForAdmin();
    }

    @PatchMapping("/{requestId}/approve-request")
    public ResponseEntity<?> approveRequest(@PathVariable Integer requestId){
        return service.approveRequest(requestId);
    }

    @PatchMapping("/{requestId}/deny-request")
    public ResponseEntity<?> denyRequest(@PathVariable Integer requestId){
        return service.denyRequest(requestId);
    }

    @GetMapping("/{username}/get-user-requests")
    public ResponseEntity<List<UsersRequestDto>> getUsersRequest(@PathVariable String username){
        return service.getUsersRequest(username);
    }
}
