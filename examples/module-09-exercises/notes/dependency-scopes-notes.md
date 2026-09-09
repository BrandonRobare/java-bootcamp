# Dependency scopes notes (Exercise 4)

| Library / situation | Scope | Why |
| ------------------- | ----- | --- |
| junit-jupiter | `test` | Only used from `src/test/java`. `test` keeps it off the production classpath and out of the shipped JAR. |
| App library used in main code (Spring Context placeholder) | `compile` (default) | Production sources `import` it, so it must be on both the compile and the runtime classpath. |
| Container-provided API | `provided` | Needed to compile against, but the app server or JDK supplies it at runtime — packaging it too risks a duplicate-class conflict. |
| JDBC driver only at runtime | `runtime` | Never imported in Java source (code talks to the `java.sql` interfaces), but the JVM must find the driver to open a connection. |

## Bad idea

```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.11.4</version>
  <!-- no scope — defaults to compile -->
</dependency>
```

JUnit becomes a production dependency: it is packaged/resolved for the main app, pollutes the runtime classpath, and signals the wrong intent to teammates and CI.

Concretely — production code can now `import org.junit.jupiter.api.Assertions` and still compile, the assertion library ships to customers, the JAR and every downstream consumer grows for nothing, and the test library becomes a transitive dependency of anyone who depends on this artifact.

## Team rule

Test libraries always use `<scope>test</scope>`.
Do not leave JUnit on the default `compile` scope.

## Scope map for reference

| Scope | Compile classpath | Runtime classpath | Packaged |
| ----- | ----------------- | ----------------- | -------- |
| `compile` | Yes | Yes | Yes |
| `test` | Tests only | Tests only | No |
| `runtime` | No | Yes | Yes |
| `provided` | Yes | No | No |

## Pass check

- Four scope assignments match the reference — Pass
- JUnit-without-scope mistake explained — Pass
- Team rule written — Pass
