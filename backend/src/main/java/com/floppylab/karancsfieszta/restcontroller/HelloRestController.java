package com.floppylab.karancsfieszta.restcontroller;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/api")
public class HelloRestController {

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECRET_TEXT = "Shhh! this is secret!";

    @GetMapping(path = "/hello")
    public String sayHello() {
        return HELLO_TEXT;
    }

    @GetMapping(path = "/secret")
    public String saySecret() {
        return SECRET_TEXT;
    }

}
