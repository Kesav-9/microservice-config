package com.lcwd.user.service.UserService.services.impl;
import com.lcwd.user.service.UserService.entities.Hotel;
import com.lcwd.user.service.UserService.entities.Rating;
import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import com.lcwd.user.service.UserService.external.services.HotelService;
import com.lcwd.user.service.UserService.repositories.UserRespository;
import com.lcwd.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRespository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRespository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRespository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with give id is not found :" + userId));
        Rating[] ratingsofUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        logger.info("{} ", ratingsofUser);
        List<Rating> ratings= Arrays.stream(ratingsofUser).toList();

        List<Rating> ratingList = ratings .stream().map(rating -> {

           // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/" + rating.getHotelId(), Hotel.class);
            Hotel hotel= hotelService.getHotel(rating.getHotelId());
           // logger.info("response status code:{}", forEntity.getStatusCode());
            rating.setHotel(hotel);

            return rating;
        }).collect(Collectors.toList());

        user.setRatings( ratingList);
        return user;
    }
}