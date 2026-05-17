import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @org.junit.jupiter.api.Test
    void nullNameError() {
        assertThrows(IllegalArgumentException.class, () -> new State("", 3));
    }

    @org.junit.jupiter.api.Test
    void negCostError() {
        assertThrows(IllegalArgumentException.class, () -> new State("Danielle", -4));

    }
}