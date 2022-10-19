package bugelniels.bugel;

import bugelniels.bugel.annotations.BeforeEach;
import bugelniels.bugel.annotations.Test;

import static bugelniels.bugel.assertion.TestAssertions.assertEquals;

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
