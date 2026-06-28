package com.mysteryticking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalEntityTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream captured;

    @BeforeEach
    void setUp() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void prints_when_predicate_is_true() {
        ConditionalEntity ce = new ConditionalEntity(t -> t == 5, "Hello");
        ce.onTick(5);
        assertEquals("Hello" + System.lineSeparator(), captured.toString());
    }

    @Test
    void silent_when_predicate_is_false() {
        ConditionalEntity ce = new ConditionalEntity(t -> t == 5, "Hello");
        ce.onTick(4);
        ce.onTick(6);
        assertEquals("", captured.toString());
    }

    @Test
    void fires_multiple_times_when_predicate_matches_repeatedly() {
        ConditionalEntity ce = new ConditionalEntity(t -> t % 3 == 0, "Ping");
        ce.onTick(3);
        ce.onTick(6);
        ce.onTick(9);
        String expected = ("Ping" + System.lineSeparator()).repeat(3);
        assertEquals(expected, captured.toString());
    }

    @Test
    void always_false_predicate_never_prints() {
        ConditionalEntity ce = new ConditionalEntity(t -> false, "Never");
        for (int i = 1; i <= 100; i++) ce.onTick(i);
        assertEquals("", captured.toString());
    }

    @Test
    void always_true_predicate_prints_every_tick() {
        ConditionalEntity ce = new ConditionalEntity(t -> true, "Always");
        ce.onTick(1);
        ce.onTick(2);
        String expected = ("Always" + System.lineSeparator()).repeat(2);
        assertEquals(expected, captured.toString());
    }
}
