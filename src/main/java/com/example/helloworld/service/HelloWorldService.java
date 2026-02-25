package com.example.helloworld.service;

import org.springframework.stereotype.Service;

/**
 * Service containing the business logic for the hello-world endpoint.
 */
@Service
public class HelloWorldService {

    /**
     * Validates the name and returns a greeting message.
     *
     * @param name the name query parameter
     * @return greeting string, e.g. "Hello Alice"
     * @throws IllegalArgumentException if name is null, empty, or starts with N-Z
     */
    public String greet(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is missing or empty");
        }

        char firstLetter = Character.toLowerCase(name.charAt(0));

        if (firstLetter < 'a' || firstLetter > 'z') {
            throw new IllegalArgumentException("Name must start with an alphabetic character");
        }

        if (firstLetter >= 'n') {
            throw new IllegalArgumentException("Name starts with a letter in the second half of the alphabet");
        }

        return "Hello " + capitalize(name);
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
