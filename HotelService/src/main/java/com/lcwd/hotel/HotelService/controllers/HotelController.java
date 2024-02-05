package com.lcwd.hotel.HotelService.controllers;

import com.lcwd.hotel.HotelService.entities.Hotel;
import com.lcwd.hotel.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody  Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

        @PreAuthorize("hasAuthority('SCOPE_internal')")
        @GetMapping("/{hotelId}")
        public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId) {
            return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
        }
        @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
        @GetMapping
        public ResponseEntity<List<Hotel>> getALL(){
        return ResponseEntity.ok(hotelService.getAll());

        }



}



