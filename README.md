# Bug-EL

This framework, the Bug Elimination Library (Bug-EL) is a simplified, lightweight Java implementation of JUnit and is meant to demistify JUnit and demonstrate the usage of custom annotations and Reflection. Its purpose is not to be a fully-fledged testing framework, but simply to showcase how these things can be implemented (relatively) easily.

The project consists of two main parts:
- `bug-el`: contains the actual unit test library.
- `test-bugel`: contains a sample project that uses the `bug-el` library.

Annotations include:

- `@Test`
    Methods annotated with this are considered to be test methods. Only classes annoted with `@Test` will be executed during the test phase.
- `@TestClass`
    Classes annotated with this are considered to be test classes.
- `@BeforeAll`
    Methods annotated with this will run before the test suite has finished.
- `@BeforeEach`
    Methods annotated with this will run before each test method.
- `@AfterAll`
    Methods annotated with this will run after the test suite has finished.
- `@AfterEach`
    Methods annotated with this will run after each test method.

There is one simple assertion available:
- `assertEquals(expected, actual, message)`

# Running

First make sure to compile and register the `bug-el` library:

```shell
cd bug-el
bash addToMaven.sh
```

This will create a JAR of the `bug-el` library and install it as a Maven plugin.

It can be seen in action in the `test-bugel` project. To do this, first navigate to the root directory again. Then execute:

```shell
cd test-bugel
mvn test
```

The resulting output should be:

```shell
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running bugelniels.bugeltest.TestExample
Tests run: 2, Failures: 2, Errors: 0, Skipped: 0, Time elapsed: 0.003 sec <<< FAILURE!
bugelniels.bugeltest.TestExample.testPlus()  Time elapsed: 0.003 sec  <<< FAILURE!
bugelniels.bugel.assertion.ResultException: Assertion failed: 2 + 3 = 5
        at bugelniels.bugel.assertion.TestAssertions.assertEquals(TestAssertions.java:17)
        at bugelniels.bugeltest.TestExample.testPlus(TestExample.java:30)

bugelniels.bugeltest.TestExample.testMinus()  Time elapsed: 0 sec  <<< FAILURE!
bugelniels.bugel.assertion.ResultException: Assertion failed: 2 - 3 = -1
        at bugelniels.bugel.assertion.TestAssertions.assertEquals(TestAssertions.java:17)
        at bugelniels.bugeltest.TestExample.testMinus(TestExample.java:39)


Results :

Failed tests:   bugelniels.bugeltest.TestExample.testPlus(): Assertion failed: 2 + 3 = 5
  bugelniels.bugeltest.TestExample.testMinus(): Assertion failed: 2 - 3 = -1

Tests run: 2, Failures: 2, Errors: 0, Skipped: 0
```

# Using as Maven Dependency

To use the `bug-el` Maven plugin anywhere (after local install), simply add the following build plugin and dependency to your `pom.xml`:

```xml
<plugin>
    <groupId>bugelniels.bugel</groupId>
    <artifactId>bug-el-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <goals>
                <goal>unit-tester</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

```xml 
<dependency>
    <groupId>bugelniels.bugel</groupId>
    <artifactId>bug-el-maven-plugin</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

