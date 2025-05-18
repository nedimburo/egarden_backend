package org.garden.egarden.subscriptions.services;

import org.garden.egarden.subscriptions.Subscription;
import org.garden.egarden.subscriptions.entities.SubscriptionEntity;
import org.garden.egarden.subscriptions.entities.SubscriptionType;
import org.garden.egarden.subscriptions.payloads.AddSubscriptionDto;
import org.garden.egarden.subscriptions.repositories.SubscriptionRepository;
import org.garden.egarden.accessibility.users.entities.UserEntity;
import org.garden.egarden.accessibility.users.services.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class SubscriptionService implements Subscription {

    private final SubscriptionRepository repository;
    private final UserService userService;

    @Transactional
    public ResponseEntity<?> addChosenSubscription(AddSubscriptionDto addSubscriptionDto){
        UserEntity userEntity = userService.findByUsernameOrEmail(addSubscriptionDto.getUsername(), addSubscriptionDto.getUsername());
        SubscriptionEntity existingSubscriptionEntity = repository.findByUserEntityId(userEntity.getId());

        if (existingSubscriptionEntity !=null) {
            existingSubscriptionEntity.setSubscriptionType(SubscriptionType.valueOf(addSubscriptionDto.getSubscriptionType()));
            repository.save(existingSubscriptionEntity);
            return new ResponseEntity<>("Subscription successfully updated.", HttpStatus.OK);
        }

        SubscriptionEntity subscriptionEntity =new SubscriptionEntity();
        subscriptionEntity.setSubscriptionType(SubscriptionType.valueOf(addSubscriptionDto.getSubscriptionType()));
        subscriptionEntity.setUserEntity(userEntity);

        repository.save(subscriptionEntity);
        return new ResponseEntity<>("Subscription successfully added.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> cancelSubscription(String username){
        UserEntity userEntity = userService.findByUsernameOrEmail(username, username);
        SubscriptionEntity subscriptionEntity = repository.findByUserEntityId(userEntity.getId());
        repository.deleteById(subscriptionEntity.getId());
        return new ResponseEntity<>("Subscription successfully canceled.", HttpStatus.OK);
    }
}
