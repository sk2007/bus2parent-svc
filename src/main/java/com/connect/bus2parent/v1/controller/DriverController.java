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
        List<Driver> drivers = driverService.getDrivers();
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/v1/drivers/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable("id") int id) {
        Driver driver = driverService.getDriver(id);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping("/v1/drivers")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        driverService.createDriver(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @PostMapping("/v1/drivers/batch")
    public ResponseEntity<List<Driver>> createDrivers(@RequestBody List<Driver> drivers) {
        driverService.createDrivers(drivers);
        return new ResponseEntity<>(drivers, HttpStatus.CREATED);
    }

    @PostMapping("/v1/drivers/register")
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {
        driverService.registerDriver(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/drivers/{id}")
    public ResponseEntity<Driver> removeDriver(@PathVariable("id") int id) {
        Driver driver = driverService.getDriver(id);
        driverService.removeDriver(id);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PutMapping ("/v1/drivers/{id}")
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver, @PathVariable("id") int id) {
        driverService.updateDriver(id, driver);
        return new ResponseEntity<>(driverService.getDriver(id), HttpStatus.ACCEPTED);
    }
}
