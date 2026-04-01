package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.BusLocation;
import com.connect.bus2parent.service.BusLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusLocationController {

    @Autowired
    private BusLocationService busLocationService;

    @GetMapping("/v1/locations")
    public ResponseEntity<List<BusLocation>> getAllLocations() {
        List<BusLocation> locations = busLocationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/v1/locations/{busNumber}")
    public ResponseEntity<BusLocation> getLocation(@PathVariable("busNumber") int busNumber) {
        BusLocation location = busLocationService.getLocation(busNumber);
        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping("/v1/locations")
    public ResponseEntity<BusLocation> updateLocation(@RequestBody BusLocation location) {
        busLocationService.updateLocation(location);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }
}
