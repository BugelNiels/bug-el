package bugelniels.bugel;

import bugelniels.bugel.invokers.TestInvoker;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a new runner for the Bug-EL framework. Executed during the TEST phase of the Maven lifecycle.
 */
@Mojo(name = "unit-tester", defaultPhase = LifecyclePhase.TEST)
public class BugElTestRunnerMojo extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(BugElTestRunnerMojo.class);

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Override
    public void execute() {
        String packageName = project.getGroupId();
        log.info("Running tests for: " + packageName);
        log.info("v1.0.0");
        new TestInvoker(packageName).run();
    }
}
