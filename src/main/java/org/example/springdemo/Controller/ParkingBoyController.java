package org.example.springdemo.Controller;

import org.example.springdemo.dao.entity.ParkingBoy;
import org.example.springdemo.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/parkingboys")
public class ParkingBoyController {
    @Autowired
   private ParkingBoyService parkingBoyService;

    @GetMapping("/")
    public ParkingBoy getParkingBoys(){
        return  parkingBoyService.getParkingBoy();
    }

    @PostMapping("/parked-cars")
    public String park(){
        return "";
    }



}
