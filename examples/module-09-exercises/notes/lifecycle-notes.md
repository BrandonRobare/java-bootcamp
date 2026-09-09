# Lifecycle notes (Exercise 3)

| Phase | What it does | Local classroom? |
| ----- | ------------ | ---------------- |
| validate | Checks the POM/model is structurally OK before any compiling | Yes |
| compile | Compiles `src/main/java` to `target/classes` | Yes |
| test | Runs unit tests under Surefire | Yes |
| package | Produces the artifact — `target/build-demo.jar` | Yes |
| verify | Runs the extra checks/integration verification tied to the package | Yes |
| install | Copies the artifact into the local `~/.m2` repository | Only when another local project needs to resolve it |
| deploy | Publishes to a remote repository | Usually **no** — CI/release with credentials, not a laptop |

## Step 1 — Command for each intent

| Intent | Command |
| ------ | ------- |
| Confirm POM parses before coding further | `mvn validate` |
| Compile production Java only | `mvn compile` |
| Run unit tests | `mvn test` |
| Produce `target/customer-service.jar` | `mvn package` |
| Run package plus the checks CI cares about | `mvn verify` (usually `mvn -B verify`) |
| Put the JAR into the local Maven cache | `mvn install` |

## Step 3 — Order of the walk

`validate` → `compile` → `test` → `package` → `verify` → `install`

1. validate
2. compile
3. test
4. package
5. verify
6. install

`deploy` is deliberately excluded.

## Which phases does `mvn package` include?

All of them up to and including itself: validate, compile, test, package. Naming a phase runs every earlier phase in the same lifecycle first — that is why `mvn package` compiles sources you never ran `mvn compile` on, and why a failing unit test stops the JAR from ever being built.

## Why CI uses `mvn -B verify`

Continuous Integration usually runs `mvn -B verify` so the build is batch/non-interactive and stops after verification without casually installing or deploying from every laptop.

`-B` (batch mode) also drops the download progress spinner, which turns CI logs into something readable.

## Debug / design challenge

Someone running `deploy` to Central from a laptop should run `mvn install` instead — it puts the artifact in their own `~/.m2` where their other local projects can resolve it, and it reaches nobody else. Publishing outward is a credentialed release step owned by CI, and a version published to a remote repo cannot be taken back.

## Pass check

- Six intent → command rows match — Pass
- Lifecycle order correct without `deploy` — Pass
- Reason for `mvn -B verify` stated — Pass
