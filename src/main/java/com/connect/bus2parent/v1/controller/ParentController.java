package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Parent;
import com.connect.bus2parent.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParentController {

    @Autowired
    private ParentService parentService;

    @GetMapping("/v1/parents")
    public ResponseEntity<List<Parent>> getParent() {
        List<Parent> parents = parentService.getParents();
        return new ResponseEntity<>(parents, HttpStatus.OK);
    }

    @GetMapping("/v1/parents/{emailAddress}")
    public ResponseEntity<Parent> getParent(@PathVariable("emailAddress") String emailAddress) {
        Parent parent = parentService.getParent(emailAddress);
        return new ResponseEntity<>(parent, HttpStatus.OK);
    }

    @PostMapping("/v1/parents")
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
        parentService.createParent(parent);
        return new ResponseEntity<>(parent, HttpStatus.CREATED);
    }

    @PostMapping("/v1/parents/batch")
    public ResponseEntity<List<Parent>> createParents(@RequestBody List<Parent> parents) {
        parentService.createParents(parents);
        return new ResponseEntity<>(parents, HttpStatus.CREATED);
    }
}
