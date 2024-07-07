package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Driver;
import com.connect.bus2parent.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/v1/drivers")
    public ResponseEntity<List<Driver>> getDriver() {
        List<Driver> myDrivers = driverService.getDrivers();
        return new ResponseEntity<>(myDrivers, HttpStatus.OK);
    }

    @GetMapping("/v1/drivers/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable("id") String id) {
        System.out.println("id from path: " + id);
        Driver driver = driverService.getDriver(id);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping("/v1/drivers")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        System.out.println("Driver payload:" +  driver);
        driverService.createDriver(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @PostMapping("/v1/drivers/batch")
    public ResponseEntity<List<Driver>> createDrivers(@RequestBody List<Driver> drivers) {
        System.out.println("Drivers payload:" +  drivers);
        driverService.createDrivers(drivers);
        return new ResponseEntity<>(drivers, HttpStatus.CREATED);
    }
}
