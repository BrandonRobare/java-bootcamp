# Mini Maven notes (Exercise 6)

| Command | Result |
| ------- | ------ |
| `mvn test` | `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0` → `BUILD SUCCESS` |
| `mvn package` | `Building jar: .../mini-maven/target/build-demo.jar` (2,609 bytes) → `BUILD SUCCESS` |
| `java -jar target/build-demo.jar` | `BuildDemo ready for Lab 9` |

Surefire line, verbatim:

```text
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.016 s -- in com.northstar.crm.BuildDemoTest
```

Run without `-q` the first time — `-q` hides the `T E S T S` block and the `Tests run:` count entirely.

## Coordinates

`com.northstar:build-demo:0.1.0-SNAPSHOT`, packaging `jar`.

The artifactId is `build-demo`, not `customer-service` from Exercise 1 — different project, and `<finalName>build-demo</finalName>` is why the output is `build-demo.jar` instead of the default `build-demo-0.1.0-SNAPSHOT.jar`.

## Plugin versions used

| Plugin | Version | Why it is here |
| ------ | ------- | -------------- |
| `maven-compiler-plugin` | 3.13.0 | `<release>21</release>` — targets Java 21 bytecode |
| `maven-surefire-plugin` | 3.5.2 | Runs the JUnit 5 tests; version pinned so the POM decides, not the Maven install |
| `maven-jar-plugin` | 3.4.2 | Writes `Main-Class` into the manifest |

Pinning all three is the point. Without an explicit `<version>`, the plugin version comes from whatever Maven's super-POM defaults to, so the same source builds differently on a teammate's machine.

## Manifest

```text
Manifest-Version: 1.0
Created-By: Maven JAR Plugin 3.4.2
Build-Jdk-Spec: 26
Main-Class: com.northstar.crm.BuildDemo
```

`Main-Class` is what makes `java -jar` work — the four-level nesting
(`configuration → archive → manifest → mainClass`) is the part that is easy to get wrong.

`Build-Jdk-Spec: 26` is this laptop's Maven JDK (Homebrew 26) while `java` on PATH is Temurin 21. The JAR still runs, because `<release>21</release>` makes the compiler emit class file version 65 regardless of which JDK is doing the compiling. That is the whole reason `release` is preferred over `source`/`target`.

## Debug / design challenge — JAR with no Main-Class

Remove the `<archive>` block and repackage: the manifest loses `Main-Class`, and

```text
java -jar target/build-demo.jar
no main manifest attribute, in target/build-demo.jar
```

The classes are still in the JAR — `java -cp target/build-demo.jar com.northstar.crm.BuildDemo` works fine. Only the "which class starts?" pointer is missing.

## Predict the Output / Behavior

After `mvn package`, the JAR is at `mini-maven/target/build-demo.jar`. `target/` is generated and is now in `.gitignore` — the POM and sources are the committed record, the JAR is not.

## Pass check

- `mvn test` succeeds, `Tests run: 1` — Pass
- `java -jar target/build-demo.jar` prints the banner — Pass
- JUnit is `test` scope, compiler release is 21 — Pass
