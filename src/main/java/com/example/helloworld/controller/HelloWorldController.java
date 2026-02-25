package com.example.helloworld.controller;

import com.example.helloworld.service.HelloWorldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for the /hello-world endpoint.
 */
@RestController
public class HelloWorldController {

    private final HelloWorldService helloWorldService;

    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<Map<String, String>> helloWorld(
            @RequestParam(name = "name", required = false) String name) {

        String message = helloWorldService.greet(name);
        return ResponseEntity.ok(Map.of("message", message));
    }
}

