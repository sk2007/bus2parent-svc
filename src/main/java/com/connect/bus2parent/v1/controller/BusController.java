package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Bus;
import com.connect.bus2parent.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/v1/buses/{BusNumber}")
    public ResponseEntity<Bus> getBus(@PathVariable("BusNumber") int BusNumber) {
        Bus bus = busService.getBus(BusNumber);
        return new ResponseEntity<>(bus, HttpStatus.OK);
    }

    @PostMapping("/v1/buses")
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) {
        busService.createBus(bus);
        return new ResponseEntity<>(bus, HttpStatus.CREATED);
    }

    @PostMapping("/v1/buses/batch")
    public ResponseEntity<List<Bus>> createBuses(@RequestBody List<Bus> buses) {
        busService.createBuses(buses);
        return new ResponseEntity<>(buses, HttpStatus.CREATED);
    }

    @PostMapping("/v1/buses/register")
    public ResponseEntity<Bus> registerBus(@RequestBody Bus bus) {
        busService.registerBus(bus);
        return new ResponseEntity<>(bus, HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/buses/{busNumber}")
    public ResponseEntity<Bus> removeBus(@PathVariable("busNumber") int busNumber) {
        Bus bus = busService.getBus(busNumber);
        busService.removeBus(busNumber);
        return new ResponseEntity<>(bus, HttpStatus.OK);
    }

}
