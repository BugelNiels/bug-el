package bugelniels.bugel.assertion;

import java.util.Objects;

/**
 * Exception thrown as a result of a failure in the assertEquals() call.
 */
public class ResultException extends RuntimeException {
    private final Object expected;
    private final Object actual;

    /**
     * Creates a new result exception.
     *
     * @param reason   Reason for the exception.
     * @param expected Expected value.
     * @param actual   Actual value.
     */
    public ResultException(String reason, Object expected, Object actual) {
        super(reason);
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultException that = (ResultException) o;
        return Objects.equals(expected, that.expected) && Objects.equals(actual, that.actual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expected, actual, getMessage());
    }

    /**
     * Retrieves the expected value.
     *
     * @return The expected value.
     */
    public Object getExpected() {
        return expected;
    }

    /**
     * Retrieves the actual value.
     *
     * @return The actual value.
     */
    public Object getActual() {
        return actual;
    }
}
