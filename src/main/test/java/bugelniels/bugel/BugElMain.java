package bugelniels.bugel;

import bugelniels.bugel.invokers.TestInvoker;

/**
 * Main used to run all tests in the current package and context. Make sure to add this file to the root package of the
 * test-sources root.
 */
public class BugElMain {

    /**
     * Runs all tests in the current package.
     *
     * @param args Commandline Arguments. Unused for now.
     */
    public static void main(String[] args) {
        String packageName = BugElMain.class.getPackageName();
        new TestInvoker(packageName).run();
    }
}
