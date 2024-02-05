package com.lcwd.hotel.HotelService.repositories;
import com.lcwd.hotel.HotelService.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel,String> {

}
