package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.service.BusParentService;
import com.connect.bus2parent.service.DuplicateSubscriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusParentController {

    @Autowired
    private BusParentService busParentService;

    @GetMapping("/v1/busparents")
    public ResponseEntity<List<BusParent>> getBusParents() {
        List<BusParent> busParentList = busParentService.getBusParents();
        return new ResponseEntity<>(busParentList, HttpStatus.OK);
    }

    @GetMapping("/v1/busparents/{busNumber}")
    public ResponseEntity<List<BusParent>> getBusParentsByBus(@PathVariable("busNumber") int busNumber) {
        List<BusParent> busParentList = busParentService.getBusParentsByBus(busNumber);
        return new ResponseEntity<>(busParentList, HttpStatus.OK);
    }

    @PostMapping("/v1/busparents")
    public ResponseEntity<BusParent> createBusParent(@RequestBody BusParent busParent) {
        try {
            busParentService.createBusParent(busParent);
            return new ResponseEntity<>(busParent, HttpStatus.CREATED);
        } catch (DuplicateSubscriptionException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/v1/busparents/{busNumber}/{parentEmail}")
    public ResponseEntity<BusParent> removeBusParent(
            @PathVariable("busNumber") int busNumber,
            @PathVariable("parentEmail") String parentEmail) {
        int rowsDeleted = busParentService.removeBusParent(busNumber, parentEmail);
        if (rowsDeleted == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new BusParent(busNumber, parentEmail), HttpStatus.OK);
    }
}
