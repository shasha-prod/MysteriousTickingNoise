package com.mysteryticking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void nullNameError() {
        assertThrows(IllegalArgumentException.class, () -> new State(null, 3));
    }

    @Test
    void emptyNameIsLegal() {
        assertDoesNotThrow(() -> new State("", 3));
    }

    @Test
    void negCostError() {
        assertThrows(IllegalArgumentException.class, () -> new State("Danielle", -4));
    }
}