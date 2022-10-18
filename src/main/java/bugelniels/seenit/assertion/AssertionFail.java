package bugelniels.seenit.assertion;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Immutable class that represents the failure of a test case.
 */
public class AssertionFail {
    private final String methodName;
    private final String simpleClassName;
    private final String fullClassName;
    private final int lineNumber;
    private final ResultException resultException;

    /**
     * Creates a new assertion failure.
     *
     * @param method          The method that failed an assertion.
     * @param resultException The exception thrown as a result of the failed exception.
     */
    public AssertionFail(Method method, ResultException resultException) {
        methodName = method.getName();
        simpleClassName = method.getDeclaringClass().getSimpleName();
        fullClassName = method.getDeclaringClass().getName();
        if (resultException.getStackTrace().length <= 1) {
            this.lineNumber = 1;
        } else {
            this.lineNumber = resultException.getStackTrace()[1].getLineNumber();
        }
        this.resultException = resultException;
    }

    /**
     * Returns a detailed error message describing the assertion fail.
     * The message has the following format:
     * <p>
     * Assertion Failed: ..
     * Expected : ..
     * Actual   : ..
     * at ..
     * </p>
     *
     * @return Error message to be printed or logged.
     */
    public String getMessage() {
        return "\n" +
                resultException.getMessage() + "\n" +
                "\tExpected : " + resultException.getExpected() + "\n" +
                "\tActual   : " + resultException.getActual() + "\n\n" +
                "at " + fullClassName + "." + methodName + "(" + simpleClassName + ".java:" + lineNumber + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssertionFail that = (AssertionFail) o;
        return methodName.equals(that.methodName)
                && simpleClassName.equals(that.simpleClassName)
                && fullClassName.equals(that.fullClassName)
                && resultException.equals(that.resultException);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullClassName, lineNumber);
    }
}
