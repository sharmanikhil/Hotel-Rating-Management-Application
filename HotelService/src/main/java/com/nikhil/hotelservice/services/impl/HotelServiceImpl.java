package com.nikhil.hotelservice.services.impl;

import com.nikhil.hotelservice.entities.Hotel;
import com.nikhil.hotelservice.exceptions.ResourceNotFoundException;
import com.nikhil.hotelservice.repository.HotelRepository;
import com.nikhil.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel){
        String randomId = UUID.randomUUID().toString();
        hotel.setId(randomId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource with given id is not found"));
    }
}
