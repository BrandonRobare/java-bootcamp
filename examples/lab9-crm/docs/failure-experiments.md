# Failure experiments - Lab 9

Method: back up the file, break exactly one thing, run, capture, restore, confirm
`BUILD SUCCESS` before starting the next. One break at a time - two at once and the
failure cannot be attributed.

Restores were verified by md5, not by eye: `pom.xml` hashed
`bcd09f67e78636e5f315d1d7009fa9a4` before and after all three experiments.

---

## 1 - Unresolvable dependency version

**Break:** `<spring.version>6.2.3</spring.version>` → `9.9.9-NOPE`, then `mvn -B clean compile`.

```text
[INFO] BUILD FAILURE
[ERROR] Could not resolve dependencies for project com.northstar:customer-service:jar:0.1.0-SNAPSHOT
[ERROR] dependency: org.springframework:spring-context:jar:9.9.9-NOPE (compile)
[ERROR] Could not find artifact org.springframework:spring-context:jar:9.9.9-NOPE in central
```

**The finding:** not one goal executed. The log contains no `--- plugin:goal ---` lines at
all - not even `clean`. Maven resolves the entire dependency set while building the
project model, *before* running the build plan.

**The trap:** `target/` was left completely intact - stale `.class` files and a stale
`customer-service.jar` from the previous good build, timestamped five minutes earlier.
A `BUILD FAILURE` sitting next to a healthy-looking `target/` is not a partial success.
Nothing ran. Check timestamps, not existence.

**Restore:** real version back → `Compiling 9 source files` → `BUILD SUCCESS`.

---

## 2 - Failing test blocks packaging

**Break:** `assertTrue(true, …)` → `assertTrue(false, …)`, then `mvn -B clean package`.

```text
[ERROR] PlaceholderTest.projectCoordinatesAreMeaningful:10 Replace with real CRM tests in Labs 11/17 ==> expected: <true> but was: <false>
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

`ls target/*.jar` → no matches. **No JAR was produced.**

**The finding:** the cumulative lifecycle enforcing itself. `package` runs `test` first,
so a red test stops the build before `maven-jar-plugin` ever executes. There is no way to
ship an artifact whose tests fail without deliberately skipping them.

`mvn -B verify` fails identically - which is exactly why CI gates on `verify`.

**Second finding:** the failure message printed the assertion's own text. The two-arg
`assertTrue(condition, message)` explains *what was expected* on the line that failed;
the one-arg form gives only `expected: <true> but was: <false>`.

**Restore:** assertion back → `Tests run: 1, Failures: 0` → `Building jar:` → `BUILD SUCCESS`.

---

## 3 - Removing `<scope>test</scope>` from JUnit

**Break:** deleted the scope line, then `mvn dependency:tree` and repackaged.

```text
\- org.junit.jupiter:junit-jupiter:jar:5.11.4:compile
```

**What did NOT happen:** the JAR was unchanged - `jar tf` found zero `org/junit` entries
and the size stayed at ~8.5 KB. A plain JAR never contains its dependencies at all, so
scope cannot change what is inside it. The expected "test framework ships to customers"
outcome needs a fat/uber JAR (shade, assembly, Spring Boot repackage) to become real.

**What actually happened** - production code can now use JUnit. Added to `Main.java`:

```java
static final Class<?> LEAK = org.junit.jupiter.api.Test.class;
```

| Scope | Same `Main.java` |
| ----- | ---------------- |
| no scope (`compile`) | `Compiling 9 source files` → **BUILD SUCCESS** |
| `<scope>test</scope>` | `Main.java:[4,55] package org.junit.jupiter.api does not exist` → **BUILD FAILURE** |

**The finding:** scope is a compiler-enforced boundary, not documentation. `test` scope is
what makes it *impossible* for production code to depend on a test library - the build
fails at the moment someone tries. On `compile` scope nothing objects, and JUnit also
becomes a transitive dependency inherited by every consumer of this artifact.

**Restore:** scope line back, `Main.java` restored from backup, md5 verified.

---

## Final state

```text
mvn -B clean verify
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
Building jar: .../target/customer-service.jar
BUILD SUCCESS

java -jar target/customer-service.jar
Northstar CRM skeleton - Lab 8
Packages: controller, service, repository, entity, dto, config, exception
Examples: CUS-1001 Amina Khan ACTIVE | CUS-1002 Ravi Singh PROSPECT
```
