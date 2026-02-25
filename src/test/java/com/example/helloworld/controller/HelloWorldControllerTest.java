package com.example.helloworld.controller;

import com.example.helloworld.exception.GlobalExceptionHandler;
import com.example.helloworld.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests using MockMvc standalone setup — no Spring context required.
 * Works with any Spring Boot version without relying on @WebMvcTest autoconfigure.
 */
@ExtendWith(MockitoExtension.class)
class HelloWorldControllerTest {

    @Mock
    private HelloWorldService helloWorldService;

    @InjectMocks
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Standalone setup: spins up only the controller + exception handler, no full Spring context
        mockMvc = MockMvcBuilders
                .standaloneSetup(helloWorldController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // --- Valid name (A-M) ---

    @Test
    void helloWorld_withValidName_returns200AndMessage() throws Exception {
        when(helloWorldService.greet("alice")).thenReturn("Hello Alice");

        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @Test
    void helloWorld_withNameStartingWithM_returns200() throws Exception {
        when(helloWorldService.greet("Mary")).thenReturn("Hello Mary");

        mockMvc.perform(get("/hello-world").param("name", "Mary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Mary"));
    }

    @Test
    void helloWorld_withNameStartingWithA_uppercase_returns200() throws Exception {
        when(helloWorldService.greet("ALICE")).thenReturn("Hello Alice");

        mockMvc.perform(get("/hello-world").param("name", "ALICE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    // --- Invalid name (N-Z) ---

    @Test
    void helloWorld_withNameStartingWithN_returns400() throws Exception {
        when(helloWorldService.greet("Nancy"))
                .thenThrow(new IllegalArgumentException("Invalid"));

        mockMvc.perform(get("/hello-world").param("name", "Nancy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void helloWorld_withNameStartingWithZ_returns400() throws Exception {
        when(helloWorldService.greet("Zara"))
                .thenThrow(new IllegalArgumentException("Invalid"));

        mockMvc.perform(get("/hello-world").param("name", "Zara"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void helloWorld_withNameStartingWithNonAlpha_returns400() throws Exception {
        when(helloWorldService.greet("123abc"))
                .thenThrow(new IllegalArgumentException("Non-alpha"));

        mockMvc.perform(get("/hello-world").param("name", "123abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    // --- Missing or empty name ---

    @Test
    void helloWorld_withMissingName_returns400() throws Exception {
        when(helloWorldService.greet(null))
                .thenThrow(new IllegalArgumentException("Missing"));

        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void helloWorld_withEmptyName_returns400() throws Exception {
        when(helloWorldService.greet(""))
                .thenThrow(new IllegalArgumentException("Empty"));

        mockMvc.perform(get("/hello-world").param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void helloWorld_withBlankName_returns400() throws Exception {
        when(helloWorldService.greet("   "))
                .thenThrow(new IllegalArgumentException("Blank"));

        mockMvc.perform(get("/hello-world").param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }
}
