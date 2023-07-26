package bugelniels.bugel;

import bugelniels.bugel.invokers.TestInvoker;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a new runner for the Bug-EL framework. Executed during the TEST phase of the Maven lifecycle.
 */
@Mojo(name = "unit-tester",
        defaultPhase = LifecyclePhase.TEST,
        requiresDependencyResolution = ResolutionScope.TEST)
public class BugElTestRunnerMojo extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(BugElTestRunnerMojo.class);

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    private URLClassLoader getClassLoader() throws MojoExecutionException {
        try {
            List<String> classpathElements = project.getTestClasspathElements();
            List<URL> projectClasspathList = new ArrayList<>();
            for (String element : classpathElements) {
                try {
                    projectClasspathList.add(new File(element).toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new MojoExecutionException(element + " is an invalid classpath element", e);
                }
            }
            return new URLClassLoader(projectClasspathList.toArray(new URL[0]));
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("Dependency resolution failed", e);
        }
    }

    @Override
    public void execute() throws MojoExecutionException {
        String packageName = project.getGroupId();
        log.info("bug-el version: v1.0.0");
        log.info("Running tests for: " + packageName);
        new TestInvoker(packageName, getClassLoader()).run();
    }
}
