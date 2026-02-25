package com.example.helloworld.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link HelloWorldService}.
 */
class HelloWorldServiceTest {

    private HelloWorldService service;

    @BeforeEach
    void setUp() {
        service = new HelloWorldService();
    }

    // --- Valid inputs (A-M) ---

    @Test
    void greet_withNameStartingWithA_returnsGreeting() {
        assertEquals("Hello Alice", service.greet("alice"));
    }

    @Test
    void greet_withNameStartingWithM_returnsGreeting() {
        assertEquals("Hello Mary", service.greet("Mary"));
    }

    @Test
    void greet_withUppercaseA_returnsGreeting() {
        assertEquals("Hello Alice", service.greet("ALICE"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"alice", "Bob", "Charlie", "david", "Eve", "frank", "Grace",
            "henry", "Irene", "jack", "Kate", "liam", "Mike"})
    void greet_withNamesStartingAtoM_returnsGreeting(String name) {
        String result = service.greet(name);
        assertTrue(result.startsWith("Hello "));
    }

    // --- Invalid inputs (N-Z) ---

    @Test
    void greet_withNameStartingWithN_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("Nancy"));
    }

    @Test
    void greet_withNameStartingWithZ_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("Zara"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nancy", "Oscar", "peter", "Quinn", "rachel", "Sam",
            "tom", "Uma", "victor", "Wendy", "xavier", "Yara", "zack"})
    void greet_withNamesStartingNtoZ_throwsException(String name) {
        assertThrows(IllegalArgumentException.class, () -> service.greet(name));
    }

    // --- Null and empty ---

    @Test
    void greet_withNull_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet(null));
    }

    @Test
    void greet_withEmptyString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet(""));
    }

    @Test
    void greet_withBlankString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("   "));
    }

    // --- Edge cases ---

    @Test
    void greet_withSingleCharacterA_returnsGreeting() {
        assertEquals("Hello A", service.greet("a"));
    }

    @Test
    void greet_withSingleCharacterM_returnsGreeting() {
        assertEquals("Hello M", service.greet("M"));
    }

    @Test
    void greet_withSingleCharacterN_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("n"));
    }

    @Test
    void greet_withNonAlphabeticFirstChar_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("123"));
    }

    @Test
    void greet_withSpecialCharacterFirst_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("@alice"));
    }

    @Test
    void greet_capitalizesNameCorrectly() {
        assertEquals("Hello Alice", service.greet("aLICE"));
        assertEquals("Hello Bob", service.greet("BOB"));
        assertEquals("Hello Charlie", service.greet("charlie"));
    }
}

