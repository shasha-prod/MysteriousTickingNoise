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
        assertEquals("Snape",snape.toString());
    }
    @Test
    void testToString_Invalid() {
        StateMachineEntity snape = new StateMachineEntity("", 1,
                new State("empty", 2));
        assertNotEquals("",snape.toString());
    }
}