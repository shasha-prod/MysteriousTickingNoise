import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
        assertThrows(IllegalArgumentException.class,()->new PipeBomb(-20));
    }
    @Test
    void register_Valid() {
        assertDoesNotThrow(()->bomb.register(new StateMachineEntity("sharon",1, new State ("Start",1))));
    }
}