package bugelniels.bugel.invokers;

import bugelniels.bugel.annotations.AfterEach;
import bugelniels.bugel.annotations.BeforeEach;
import bugelniels.bugel.assertion.ResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class responsible for invoking methods of a single class.
 */
public class MethodInvoker {

    private static final Logger log = LoggerFactory.getLogger(MethodInvoker.class);
    private final Class<?> clazz;

    /**
     * Creates a new invoker for the provided class.
     *
     * @param clazz The class type that this invoker should be able to invoke methods on.
     */
    public MethodInvoker(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Executes a method by first creating a new instance of the class and then invoking the method in question.
     *
     * @param method The method to invoke.
     * @return True if the invocation is successful; false otherwise.
     * @throws ResultException Thrown when the method in question throws a ResultException.
     */
    public boolean executeMethodInIsolation(Method method) throws ResultException {
        try {
            Constructor<?> cons = clazz.getConstructor();
            Object o = cons.newInstance();
            executeMethodsWithAnnotation(o, BeforeEach.class);
            method.invoke(o);
            executeMethodsWithAnnotation(o, AfterEach.class);
            return true;
        } catch (NoSuchMethodException e) {
            methodInvocationFailure(method, "Method " + method.getName() + " does not exist.");
        } catch (InstantiationException e) {
            log.error("Cannot instantiate " + clazz.getName() + " - " + e.getMessage());
        } catch (IllegalAccessException e) {
            methodInvocationFailure(method, "Cannot access method " + method.getName() +
                    ". Make sure the method is public.");
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof ResultException) {
                throw (ResultException) e.getTargetException();
            } else {
                methodInvocationFailure(method, e.getMessage());
            }
        }
        return false;
    }

    /**
     * Logs a method invocation error.
     *
     * @param method  The method that failed to execute.
     * @param message Message to log in addition to the invocation failure message.
     */
    private void methodInvocationFailure(Method method, String message) {
        log.error("Failed to execute method: " + clazz.getName() + "." + method.getName() + "()\n" +
                "\t" + message);
    }

    /**
     * Attempts to execute all methods annotated with the provided annotation of the provided object.
     * Methods with the provided annotation are assumed to have no parameters.
     *
     * @param object     The object to execute the methods on.
     * @param annotation The annotation the methods to execute should have.
     */
    public void executeMethodsWithAnnotation(Object object, Class<? extends Annotation> annotation) {
        for (Method m : clazz.getMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                try {
                    m.invoke(object);
                } catch (IllegalAccessException e) {
                    methodInvocationFailure(m, "Cannot access method: " + e.getMessage());
                } catch (InvocationTargetException e) {
                    methodInvocationFailure(m, "Cannot invoke method: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    methodInvocationFailure(m,
                            "Methods annotated with @" + annotation.getSimpleName() +
                                    " must not take any parameters.");
                }
            }
        }
    }

    /**
     * Attempts to execute all methods annotated with the provided annotation of the class this invoker is
     * responsible for. Methods with the provided annotation are assumed to have no parameters.
     * The methods must also be static.
     *
     * @param annotation The annotation the static methods to be executed should have.
     */
    public void executeStaticMethodsWithAnnotation(Class<? extends Annotation> annotation) {
        for (Method m : clazz.getMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                if (!Modifier.isStatic(m.getModifiers())) {

                    methodInvocationFailure(m,
                            "The method is annotated with @" + annotation.getSimpleName() +
                                    ", but is not declared static");
                    continue;
                }
                try {
                    m.invoke(null);
                } catch (IllegalAccessException e) {
                    methodInvocationFailure(m, "Cannot access method: " + e.getMessage());
                } catch (InvocationTargetException e) {
                    methodInvocationFailure(m, "Cannot invoke method: " + e.getMessage());
                }
            }
        }
    }
}
