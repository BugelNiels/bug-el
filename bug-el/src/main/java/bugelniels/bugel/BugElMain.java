package bugelniels.bugel;

import bugelniels.bugel.invokers.TestInvoker;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

/**
 * Main used to run all tests in the current package and context. Make sure to add this file to the root package of the
 * test-sources root.
 */
public class BugElMain {
    private static final Logger log = LoggerFactory.getLogger(BugElMain.class);

    /**
     * Runs all tests in the current package.
     *
     * @param args Commandline Arguments. Unused for now.
     */
    public static void main(String[] args) {
        URL testClassesURL = null;
        try {
            testClassesURL = Paths.get("target", "test-classes").toUri().toURL();
        } catch (MalformedURLException e) {
            log.error("Failed to construct proper path");
            return;
        }

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{testClassesURL},
                ClasspathHelper.staticClassLoader());
        String packageName = BugElMain.class.getPackageName();
        new TestInvoker(packageName, classLoader).run();
    }
}
