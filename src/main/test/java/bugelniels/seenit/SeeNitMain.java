package bugelniels.seenit;

import bugelniels.seenit.invokers.TestInvoker;

/**
 * Main used to run all tests in the current package and context. Make sure to add this file to the root package of the
 * test-sources root.
 */
public class SeeNitMain {

    /**
     * Runs all tests in the current package.
     *
     * @param args Commandline Arguments. Unused for now.
     */
    public static void main(String[] args) {
        String packageName = SeeNitMain.class.getPackageName();
        new TestInvoker(packageName).run();
    }
}
