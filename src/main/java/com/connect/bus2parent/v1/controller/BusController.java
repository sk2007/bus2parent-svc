package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Bus;
import com.connect.bus2parent.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping("/v1/buses")
    public ResponseEntity<List<Bus>> getBus() {
        List<Bus> myBuses = busService.getBuses();
        return new ResponseEntity<>(myBuses, HttpStatus.OK);
    }

}
