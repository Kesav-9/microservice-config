package com.lcwd.user.service.UserService.controllers;

import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoggersEndpoint loggersEndpoint;
    @Autowired
    private UserController user;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
            User user1=userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod ="ratingHotelFallback")
    public ResponseEntity<User> getSingleUser( @PathVariable String userId){
        User user=userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
       User user= User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is Created Because some user is down")
                .userId("1234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser=userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
