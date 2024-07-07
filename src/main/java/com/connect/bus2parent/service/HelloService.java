package com.connect.bus2parent.service;

import com.connect.bus2parent.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public Message getMessage(String message) {
        Message message1 = new Message();
        message1.setMessage(message);
        return message1;
    }
}
