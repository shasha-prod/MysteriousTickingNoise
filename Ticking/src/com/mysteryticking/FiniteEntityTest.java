package com.mysteryticking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FiniteEntityTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream captured;
    private PipeBomb bomb;

    @BeforeEach
    void setUp() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        bomb = new PipeBomb(10);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void fires_exactly_maxFires_times() {
        int[] count = {0};
        TickListener inner = tick -> count[0]++;
        bomb.register(new FiniteEntity(inner, bomb, 3));
        bomb.start();
        assertEquals(3, count[0]);
    }

    @Test
    void unregisters_itself_after_maxFires() {
        int[] count = {0};
        TickListener inner = tick -> count[0]++;
        FiniteEntity fe = new FiniteEntity(inner, bomb, 2);
        bomb.register(fe);

        bomb.notifyListeners(1); // fire 1
        bomb.notifyListeners(2); // fire 2 → unregisters
        int afterUnregister = count[0];
        bomb.notifyListeners(3); // should NOT fire
        assertEquals(2, afterUnregister);
        assertEquals(2, count[0]);
    }

    @Test
    void does_not_fire_if_maxFires_is_zero() {
        int[] count = {0};
        FiniteEntity fe = new FiniteEntity(tick -> count[0]++, bomb, 0);
        bomb.register(fe);
        bomb.notifyListeners(1);
        assertEquals(0, count[0]);
    }
}
