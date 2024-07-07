package com.connect.bus2parent.v1.controller;

import com.connect.bus2parent.domain.Message;
import com.connect.bus2parent.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/v1/hello")
    public ResponseEntity<Message> getHello() {
        Message message = helloService.getMessage("Hello Neha");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
