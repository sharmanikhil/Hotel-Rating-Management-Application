package com.nikhil.hotelservice.services;

import com.nikhil.hotelservice.entities.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;


public interface HotelService {

    public Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String id);
}
