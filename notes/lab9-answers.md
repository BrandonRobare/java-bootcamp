# Lab 9 answers — Maven build and dependencies

## 1. Which design decision most affected build correctness?

Putting JUnit on `<scope>test</scope>`. I proved it by deleting the line and adding a
JUnit reference to `Main.java` — production code compiled fine with the scope gone, and
failed with `package org.junit.jupiter.api does not exist` once it was restored. Scope is
enforced by the compiler, so it is the one decision that makes a whole class of mistakes
impossible instead of merely discouraged.

## 2. What evidence proves the lifecycle walk was real (not only `package` once)?

Each phase left something different behind, recorded in `docs/lifecycle-evidence.md`:
`validate` created no `target/` at all, `compile` created `target/classes/`, `test` added
`surefire-reports/`, `package` produced `customer-service.jar`, `verify` added nothing
because no plugin is bound to it here, and `install` wrote outside the project entirely to
`~/.m2/repository/com/northstar/customer-service/0.1.0-SNAPSHOT/`.

The install path is the strongest evidence, because it can only exist if the phases before
it ran, and the JAR there is named `customer-service-0.1.0-SNAPSHOT.jar` from the
coordinates rather than `customer-service.jar` from `<finalName>` — two different files
that a single `mvn package` could not have produced.

## 3. Which failure was hardest to diagnose?

The bad `spring.version`, because the evidence lied. The build printed `BUILD FAILURE`
while `target/` still held a full set of `.class` files and a runnable JAR, which looked
like a partial success. They were stale, five minutes old, from the previous good build.

Maven resolves every dependency while building the project model, before running the build
plan, so a version that does not exist kills the run before a single goal executes —
`clean` included. Nothing was deleted because nothing ran. Checking file timestamps rather
than file existence is what settled it.
