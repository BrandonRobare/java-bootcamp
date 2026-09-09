# Lifecycle evidence - Lab 9

Run each phase separately and paste a short excerpt.

| Phase | Command | BUILD SUCCESS? | Notes |
| ----- | ------- | -------------- | ----- |
| validate | `mvn validate` | YES | No `target/` created - validate only checks the POM/model |
| compile | `mvn compile` | YES | Creates `target/classes/` - 9 source files, release 21 |
| test | `mvn test` | YES | `Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`; adds `target/test-classes/` + `surefire-reports/` |
| package | `mvn package` | YES | `target/customer-service.jar` |
| verify | `mvn verify` | YES | Added **nothing** new to `target/` - no Failsafe or check plugins are bound to this phase yet. The `jar:3.4.2:jar (default-jar)` line in the log is `package` re-running, not verify's own work |
| install | `mvn install` | YES | `~/.m2/repository/com/northstar/customer-service/0.1.0-SNAPSHOT/` |

## What `install` wrote

```text
~/.m2/repository/com/northstar/customer-service/0.1.0-SNAPSHOT/
├── customer-service-0.1.0-SNAPSHOT.jar
├── customer-service-0.1.0-SNAPSHOT.pom
└── maven-metadata-local.xml
```

The path is the GAV turned into folders: `com/northstar` + `customer-service` + `0.1.0-SNAPSHOT`. Coordinates are the address.

**JAR naming differs by location:**

| Location | Filename |
| -------- | -------- |
| `target/` | `customer-service.jar` (`<finalName>`) |
| `~/.m2/…` | `customer-service-0.1.0-SNAPSHOT.jar` (coordinates) |

`<finalName>` only renames the local build output. The repository ignores it and uses the coordinates - it has to, or two versions of the same artifact would collide in one folder.

The `.pom` is installed next to the JAR. That is how a consumer learns the transitive dependencies without seeing the source.

## Phases are cumulative

Each phase ran every earlier phase first. `mvn package` compiled and tested before building the JAR - a failing test would have meant no JAR at all.

`install` is the first phase that writes **outside** the project directory. That is the line between "my build" and "my machine".

## Dependency tree

Captured to `docs/dependency-tree.txt` - one declared Spring dependency pulls seven transitive compile-scope artifacts; JUnit is `test` scope and ships nothing.

## The default lifecycle

```text
validate → compile → test → package → verify → install → deploy
   │         │        │       │         │        │          │
   │         │        │       │         │        │          └─ remote repo (CI/release only)
   │         │        │       │         │        └─ ~/.m2 - first write outside the project
   │         │        │       │         └─ integration checks (nothing bound here yet)
   │         │        │       └─ target/customer-service.jar
   │         │        └─ Surefire runs *Test classes
   │         └─ target/classes/
   └─ POM parses; no output produced
```

Naming a phase runs every phase to its left. There is no way to reach `package`
without passing through `test`, which is why a failing test means no JAR.

| Phase | Bound plugin here | What it leaves behind |
| ----- | ----------------- | --------------------- |
| validate | - | nothing |
| compile | `maven-compiler-plugin:3.13.0` | `target/classes/` |
| test | `maven-surefire-plugin:3.5.2` | `target/test-classes/`, `target/surefire-reports/` |
| package | `maven-jar-plugin:3.4.2` | `target/customer-service.jar` |
| verify | *(none)* | nothing - the phase exists, this project binds nothing to it |
| install | `maven-install-plugin` | `~/.m2/repository/com/northstar/customer-service/0.1.0-SNAPSHOT/` |
| deploy | `maven-deploy-plugin` | not run - remote publishing is a credentialed CI step |

The empty `verify` row is the point of `mvn -B verify` as a CI gate: it is where
integration tests and quality checks *will* bind, and it stops short of writing to
anyone's `~/.m2`.

## Profiles

`dev` / `test` / `prod`, each setting `app.environment`. `dev` is `activeByDefault`.

```text
$ mvn help:active-profiles
 - dev (source: com.northstar:customer-service:0.1.0-SNAPSHOT)

$ mvn -Ptest help:active-profiles
 - test (source: com.northstar:customer-service:0.1.0-SNAPSHOT)

$ mvn -Pprod help:active-profiles
 - prod (source: com.northstar:customer-service:0.1.0-SNAPSHOT)
```

Resolved values, via `mvn help:evaluate -Dexpression=app.environment -DforceStdout -q`:

| Command | `app.environment` |
| ------- | ----------------- |
| `mvn …` | `dev` |
| `mvn -Ptest …` | `test` |
| `mvn -Pprod …` | `prod` |

**`activeByDefault` is not "always on".** Runs 2 and 3 list `test` / `prod` **alone** -
`dev` is gone, not stacked underneath. Any `-P` that names a profile deactivates the
default set. A build that relies on `dev` properties silently loses them the moment
someone activates an unrelated profile.

### Flag note

`mvn -q help:active-profiles` prints **nothing** - the help plugin reports through
`[INFO]`, which `-q` suppresses. Same trap as `-q` with `dependency:tree`. But
`-q` *is* correct with `help:evaluate -DforceStdout`, which bypasses the logger.
`-q` hides log output, not program output.

## No secrets

`app.environment` is an environment label, not a credential. No passwords, tokens, or
endpoints live in these profiles or in `application-dev.properties` - those come from
environment variables or a secrets store at runtime.
