import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PipeBombTest {

    @BeforeEach
    void setUp() {
    }
    @Test
    void testFlow() {
        List<Integer> seen = new ArrayList<>();
        PipeBomb bomb = new PipeBomb(5);
        bomb.register(t -> seen.add(t));
        bomb.start();
        assertEquals(List.of(1, 2, 3, 4, 5), seen);
    }

    @Test
    void register() {
    }

    @Test
    void notifyListeners() {
    }

    @Test
    void start() {
    }
}