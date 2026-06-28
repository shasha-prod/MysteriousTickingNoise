package com.mysteryticking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PipeBombTest {
    PipeBomb bomb;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream captured;

    @BeforeEach
    void redirectStdout() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        bomb = new PipeBomb(5);
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }
    @Test
    void constructor_InvalidState() {
        assertThrows(IllegalArgumentException.class, () -> new PipeBomb(-20));
    }

    @Test
    void constructor_ZeroCountdown_throws() {
        assertThrows(IllegalArgumentException.class, () -> new PipeBomb(0));
    }

    @Test
    void register_Valid() {
        assertDoesNotThrow(() -> bomb.register(new StateMachineEntity("sharon", 1, new State("Start", 1))));
    }

    @Test
    void start_prints_BOOM_at_end() {
        bomb.start();
        assertTrue(captured.toString().endsWith("BOOM!" + System.lineSeparator()));
    }

    @Test
    void notifyListeners_calls_in_registration_order() {
        StringBuilder order = new StringBuilder();
        bomb.register(tick -> order.append("A"));
        bomb.register(tick -> order.append("B"));
        bomb.notifyListeners(1);
        assertEquals("AB", order.toString());
    }

    @Test
    void start_ticks_exactly_countdown_times() {
        int[] count = {0};
        bomb.register(tick -> count[0]++);
        bomb.start(); // bomb has countdown=5
        assertEquals(5, count[0]);
    }

    @Test
    void unregister_returns_true_for_registered_listener() {
        TickListener l = tick -> {};
        bomb.register(l);
        assertTrue(bomb.unregister(l));
    }

    @Test
    void unregister_returns_false_for_unknown_listener() {
        assertFalse(bomb.unregister(tick -> {}));
    }

    @Test
    void unregister_stops_listener_from_receiving_ticks() {
        int[] count = {0};
        TickListener l = tick -> count[0]++;
        bomb.register(l);
        bomb.unregister(l);
        bomb.notifyListeners(1);
        assertEquals(0, count[0]);
    }
}