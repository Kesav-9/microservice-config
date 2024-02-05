package com.lcwd.hotel.HotelService.services;

import com.lcwd.hotel.HotelService.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel create(Hotel hotel);
    List<Hotel> getAll();
    Hotel get(String id);
}
