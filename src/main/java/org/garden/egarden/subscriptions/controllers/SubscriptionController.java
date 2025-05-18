package org.garden.egarden.subscriptions.controllers;

import org.garden.egarden.subscriptions.payloads.AddSubscriptionDto;
import org.garden.egarden.subscriptions.services.SubscriptionService;
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
@RequestMapping("public/subscription")
@Tags(value = @Tag(name = "Public | Subscription"))
public class SubscriptionController {

    private final SubscriptionService service;

    @PostMapping("/add-subscription")
    public ResponseEntity<?> addChosenSubscription(@RequestBody AddSubscriptionDto addSubscriptionDto){
        return service.addChosenSubscription(addSubscriptionDto);
    }

    @DeleteMapping("/{username}/cancel-subscription")
    public ResponseEntity<?> cancelSubscription(@PathVariable String username){
        return service.cancelSubscription(username);
    }
}
