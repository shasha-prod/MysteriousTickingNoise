import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void nullNameError() {
        assertThrows(IllegalArgumentException.class, () -> new State("", 3));
    }

    @Test
    void negCostError() {
        assertThrows(IllegalArgumentException.class, () -> new State("Danielle", -4));
    }
}