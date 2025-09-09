package org.example.springdemo.service;

import org.example.springdemo.dao.entity.ParkingBoy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {

    public ParkingBoy getParkingBoy(){
        ParkingBoy parkingBoy = new ParkingBoy();
        return  parkingBoy;
    }
}
