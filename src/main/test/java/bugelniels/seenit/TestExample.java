package bugelniels.seenit;

import bugelniels.seenit.annotations.BeforeEach;
import bugelniels.seenit.annotations.Test;

import static bugelniels.seenit.assertion.TestAssertions.assertEquals;

public class TestExample {

    private int expected5;

    @BeforeEach
    public void setup() {
        expected5 = 5;
    }

    @Test
    public void testPlus() {
        assertEquals(expected5, 2 + 3, "2 + 3 = 5");
        assertEquals(1, -2 + 3, "-2 + 3 = 1");
    }

    @Test
    public void testMinus() {
        assertEquals(5, 2 - 3, "2 - 3 = -1");
        assertEquals(10, 15 - 5, "15 - 5 = 10");
    }
}
