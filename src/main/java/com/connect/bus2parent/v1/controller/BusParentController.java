package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Bus;
import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.domain.Parent;
import com.connect.bus2parent.service.BusParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusParentController {

    @Autowired private BusParentService busParentService;

    @GetMapping ("/v1/busparents")
    public ResponseEntity<List<BusParent>> getBusParents() {
        List<BusParent> busParentList = busParentService.getBusParents();
        return new ResponseEntity<>(busParentList, HttpStatus.OK);
    }
}
