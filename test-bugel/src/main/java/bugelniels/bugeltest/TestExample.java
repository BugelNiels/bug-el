package bugelniels.bugeltest;

import bugelniels.bugel.annotations.BeforeEach;
import bugelniels.bugel.annotations.Test;
import bugelniels.bugel.annotations.TestClass;

import static bugelniels.bugel.assertion.TestAssertions.assertEquals;

/**
 * Example class.
 */
@TestClass
public class TestExample {

    private int expected5;

    /**
     * Setup.
     */
    @BeforeEach
    public void setup() {
        expected5 = 6;
    }

    /**
     * Tests the + operator.
     */
    @Test
    public void testPlus() {
        assertEquals(expected5, 2 + 3, "2 + 3 = 5");
        assertEquals(1, -2 + 3, "-2 + 3 = 1");
    }

    /**
     * Tests the - operator. First assert should fail.
     */
    @Test
    public void testMinus() {
        assertEquals(5, 2 - 3, "2 - 3 = -1");
        assertEquals(10, 15 - 5, "15 - 5 = 10");
    }
}
