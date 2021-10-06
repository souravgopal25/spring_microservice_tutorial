package com.sourav.spring_security.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResources {
    @RequestMapping("/")
    public String home() {
        return "<H1>Hello World!<H1>";
    }
}
