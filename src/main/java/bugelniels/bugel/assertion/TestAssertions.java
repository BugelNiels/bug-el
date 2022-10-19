package bugelniels.bugel.assertion;

/**
 * Contains the assertions to be used in any test cases.
 */
public class TestAssertions {

    /**
     * Asserts that two objects are equal. If not, a ResultException is thrown.
     *
     * @param expected Expected value.
     * @param actual   Actual value.
     * @param message  Message to be added to the exception on failure.
     */
    public static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new ResultException("Assertion failed: " + message, expected, actual);
        }
    }

    /**
     * Asserts that two objects are equal. If not, a ResultException is thrown.
     *
     * @param expected Expected value.
     * @param actual   Actual value.
     */
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new ResultException("Assertion failed.", expected, actual);
        }
    }

}
