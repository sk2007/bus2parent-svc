package com.connect.bus2parent.service;

import com.connect.bus2parent.domain.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void test_getMessage() {
        Message message = helloService.getMessage("hello");
        assertEquals("hello", message.getMessage());

    }
}
