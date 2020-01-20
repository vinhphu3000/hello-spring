package com.ciaotada.hellokafka;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloKafka {
    @RequestMapping("/hello")
    public void hello()
    {
        int x = 2 + 2;
    }
}
