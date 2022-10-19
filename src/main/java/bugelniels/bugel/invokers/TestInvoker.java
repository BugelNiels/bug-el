package bugelniels.bugel.invokers;

import bugelniels.bugel.annotations.AfterAll;
import bugelniels.bugel.annotations.BeforeAll;
import bugelniels.bugel.annotations.Test;
import bugelniels.bugel.assertion.AssertionFail;
import bugelniels.bugel.assertion.ResultException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Class responsible for invoking all tests in a particular package.
 */
public class TestInvoker {

    private static final Logger log = LoggerFactory.getLogger(TestInvoker.class);

    private final String packageName;

    /**
     * Creates a new test invoker that will be able to invoke any methods annotated with @Test in the provided package.
     * Note that this should be called from the correct context.
     * If this class is instantiated in e.g. the sources root,
     * then it will only find all classes in that particular directory.
     * As such, if instantiated in the test-sources root, it will find all classes in the package of the
     * test-sources root.
     *
     * @param packageName The name of the package containing all classes to test.
     */
    public TestInvoker(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Runs the test invoker. Will invoke every method annotated with @Test.
     * In other words, it will execute the entire test suite.
     */
    public void run() {
        Set<Class<?>> classes = findClassesInPackage(packageName);
        classes.forEach(clazz -> {
            List<AssertionFail> errors = invokeTestsOfClass(clazz);
            errors.forEach(e -> System.err.println(e.getMessage()));
        });
    }

    /**
     * Retrieves every class in the provided package.
     *
     * @param packageName Name of the package to find all the classes in.
     * @return Collection of all classes found within the provided package.
     */
    private Set<Class<?>> findClassesInPackage(String packageName) {
        // TODO: use non-deprecated version
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }

    /**
     * Invokes all test related methods of a particular cast.
     *
     * @param clazz The class to test.
     * @return A collection of assertion fail summaries. Empty if all tests passed.
     */
    private List<AssertionFail> invokeTestsOfClass(Class<?> clazz) {
        MethodInvoker methodInvoker = new MethodInvoker(clazz);
        methodInvoker.executeStaticMethodsWithAnnotation(BeforeAll.class);
        List<AssertionFail> assertFails = new ArrayList<>();
        for (Method m : clazz.getMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                log.info("Testing " + clazz.getName() + "." + m.getName());
                try {
                    boolean success = methodInvoker.executeMethodInIsolation(m);
                    if (!success) {
                        return List.of();
                    }
                } catch (ResultException e) {
                    assertFails.add(new AssertionFail(m, e));
                }
            }
        }
        methodInvoker.executeStaticMethodsWithAnnotation(AfterAll.class);
        return Collections.unmodifiableList(assertFails);
    }

}
