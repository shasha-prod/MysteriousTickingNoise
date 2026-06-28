package com.mysteryticking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StateMachineEntityTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream captured;

    @BeforeEach
    void redirectStdout() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);   // critical — see below
    }
    @Test
    void constructor_Invalid_emptyEntityName() {
        assertThrows(IllegalArgumentException.class,()->new StateMachineEntity("",1, new State ("Start",1)));
    }
    @Test
    void constructor_Invalid_IllegalTickCount() {
        assertThrows(IllegalArgumentException.class,()->new StateMachineEntity("scas",-21, new State ("Start",1)));
    }
    @Test
    void constructor_Invalid_EmptyState() {
        assertThrows(IllegalArgumentException.class,()->new StateMachineEntity("scas",21));
    }
    @Test
    void prints_name_on_entry_to_first_state() {
        StateMachineEntity snape = new StateMachineEntity("Snape", 1,
                new State("Snape", 2),
                new State("Snape", 2),
                new State("Severus Snape", 4));

        snape.onTick(1);
        assertEquals("Snape" + System.lineSeparator(), captured.toString());
    }

    @Test
    void testToString_Valid() {
        StateMachineEntity snape = new StateMachineEntity("Snape", 1,
                new State("Snape", 2),
                new State("Snape", 2),
                new State("Severus Snape", 4));
        assertEquals("Snape", snape.toString());
    }

    @Test
    void testToString_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> new StateMachineEntity("", 1,
                new State("empty", 2)));
    }

    @Test
    void silent_before_startTick() {
        // startTick=5, so ticks 1-4 must produce no output
        StateMachineEntity e = new StateMachineEntity("E", 5, new State("Go", 1));
        e.onTick(1);
        e.onTick(4);
        assertEquals("", captured.toString());
    }

    @Test
    void silent_mid_state() {
        // State cost=4: prints on tick 1 (entry), silent on ticks 2,3,4
        StateMachineEntity e = new StateMachineEntity("E", 1, new State("Go", 4));
        e.onTick(1); // entry → prints
        captured.reset();
        e.onTick(2); // mid-state → silent
        e.onTick(3);
        e.onTick(4);
        assertEquals("", captured.toString());
    }

    @Test
    void prints_second_state_on_correct_tick() {
        // States: cost=2, cost=2 → second state entered at posInCycle=2
        // startTick=1 → second state entry at tick=3
        StateMachineEntity e = new StateMachineEntity("E", 1,
                new State("First", 2),
                new State("Second", 2));
        e.onTick(3);
        assertEquals("Second" + System.lineSeparator(), captured.toString());
    }

    @Test
    void cycle_wraps_correctly() {
        // cycleLength=2 (one state, cost=2): entry at posInCycle=0
        // startTick=1 → entries at ticks 1,3,5,...
        StateMachineEntity e = new StateMachineEntity("E", 1, new State("Ping", 2));
        e.onTick(3); // posInCycle = (3-1)%2 = 0 → entry
        assertEquals("Ping" + System.lineSeparator(), captured.toString());
    }

    @Test
    void empty_name_state_is_silent() {
        // A state with name="" should produce no output
        StateMachineEntity e = new StateMachineEntity("E", 1, new State("", 2));
        e.onTick(1);
        assertEquals("", captured.toString());
    }

    @Test
    void startTick_zero_is_valid() {
        assertDoesNotThrow(() -> new StateMachineEntity("E", 0, new State("Go", 1)));
    }

    @Test
    void constructor_null_entityName_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new StateMachineEntity(null, 1, new State("Go", 1)));
    }
}