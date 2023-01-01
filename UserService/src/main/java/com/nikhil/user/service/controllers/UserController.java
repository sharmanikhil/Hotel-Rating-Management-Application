package com.nikhil.user.service.controllers;

import com.nikhil.user.service.entities.User;
import com.nikhil.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(User.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    int retryCount=0;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    @Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        retryCount++;
        logger.info("Retry Count= {}",retryCount);
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
        logger.info("Fallback is executed because user service is down: " + ex.getMessage());
        User user = User.builder().
                email("dummy@gmail.com")
                .name("Dummy")
                .about("This is dummy user because servide is down")
                .userId("654123").
                build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
