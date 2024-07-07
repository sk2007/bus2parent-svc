package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Parent;
import com.connect.bus2parent.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParentController {

    @Autowired
    private ParentService parentService;

    @GetMapping("/v1/parents")
    public ResponseEntity<List<Parent>> getParent() {
        List<Parent> myParents = parentService.getParents();
        return new ResponseEntity<>(myParents, HttpStatus.OK);
    }
}
