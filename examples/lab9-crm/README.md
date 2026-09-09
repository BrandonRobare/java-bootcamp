# Lab 9 starter - timed path (~45 minutes)

**Theme:** Maven build, dependencies, plugins, profiles

## Activity card

| | |
| --- | --- |
| **Objective** | Fill POM TODOs + lifecycle/tree evidence for lab9-crm |
| **Skills practiced** | Scopes, compiler/Surefire/jar plugins, verify, dependency tree |
| **Expected outcome** | `mvn -B clean verify` + runnable JAR smoke path |
| **Estimated time** | ~45 minutes |
| **Files** | `examples/lab9-crm/` copied from this starter |

**Boilerplate reduced:** Layer stubs from Lab 8 style are present - focus on Maven build TODOs and evidence docs.

Pacing: [`../../PACING.md`](../../PACING.md) · Full steps: [`../LAB-9-GUIDE.md`](../LAB-9-GUIDE.md)

**Flags:** Prefer full logs first; never `-q` with `dependency:tree`.

## Copy into your workspace

**Windows (PowerShell)** - from this lab folder:

```powershell
New-Item -ItemType Directory -Force -Path "$env:USERPROFILE\java-bootcamp\examples\lab9-crm" | Out-Null
Copy-Item -Recurse -Force ".\starter\*" "$env:USERPROFILE\java-bootcamp\examples\lab9-crm\"
cd $env:USERPROFILE\java-bootcamp\examples\lab9-crm
```

**macOS / Linux:**

```bash
mkdir -p ~/java-bootcamp/examples/lab9-crm
cp -R starter/. ~/java-bootcamp/examples/lab9-crm/
cd ~/java-bootcamp/examples/lab9-crm
```

Full GUIDE: [`../LAB-9-GUIDE.md`](../LAB-9-GUIDE.md)

## 45-minute checklist

- [x] Fill `pom.xml` TODOs (dependencies with scopes, compiler/Surefire/jar plugins)
- [x] Confirm `PlaceholderTest` runs under Surefire
- [x] Walk lifecycle phases; fill `docs/lifecycle-evidence.md`
- [x] Capture `docs/dependency-tree.txt`
- [x] Optional timed stretch: add `dev`/`prod` profiles (dev, test, prod)
- [ ] Run smoke test

## Smoke test

```bash
mvn -B clean verify
mvn package
java -jar target/customer-service.jar
```

## Timed-path Pass criteria

| Criterion | Pass / Fail |
| --------- | ----------- |
| `mvn -B verify` BUILD SUCCESS | Pass / Fail |
| JUnit is `test` scope; PlaceholderTest green | Pass / Fail |
| `target/customer-service.jar` runs Main | Pass / Fail |
| lifecycle-evidence.md has phase notes | Pass / Fail |

Continue remaining GUIDE steps as homework / full path if needed.

## CI note (preview - pipelines deepen in later modules)

Preferred verify command on agents:

    mvn -B verify

`-B` is batch mode (non-interactive). It also drops the download progress spinner, which
makes CI logs readable.

Prefer `verify` over `install` on CI unless the pipeline intentionally publishes to an
artifact repository. `install` writes to the agent's `~/.m2`, which is shared state
between jobs. Never deploy snapshots from a developer laptop without agreement.

Nothing is bound to `verify` in this POM yet, so it currently does the same work as
`package`. That is the point: it is the phase integration tests and quality checks will
bind to, and gating on it now means the gate does not move later.

    Artifact coordinates: com.northstar:customer-service:0.1.0-SNAPSHOT
    Sample customer IDs (docs only): CUS-1001, CUS-1002
    Correlation ID (logs later): lab-request-001

## Architecture - build-time NOW vs platform LATER

**NOW.** The POM is the whole subject. Source becomes `target/customer-service.jar`
through a lifecycle this project controls: coordinates name the artifact, scopes decide
which classpath each dependency lands on, plugins bind tools to phases, profiles vary
configuration without forking the file. The Java underneath is still Lab 8 layered stubs
that print to the console. `spring-context` is on the classpath as a learning placeholder
and no Spring code is written against it.

**LATER.** The same build grows a Spring Boot API, JPA against PostgreSQL, an Angular SPA
over HTTPS/JSON, and Kafka consumers for notification and audit. Every one of those
arrives as a dependency in this file.

Which is why the order matters. Adding Kafka to a project where nobody can say what
`test` scope means, or which phase runs the tests, means debugging a distributed system
and its build at the same time. Getting the build boring first is the point of Lab 9.

| Aspect | Lab 9 (NOW) | Later CRM labs |
| ------ | ----------- | -------------- |
| Focus | Build truth - POM, lifecycle, JAR | Runtime behavior - APIs, database, messaging |
| Spring | Placeholder dependency only | Spring Boot applications (Lab 22+) |
| Tests | `PlaceholderTest` | Real unit and integration tests |
| Config | `dev` / `test` / `prod` profiles | Secrets stores, environment config |
| Artifact | `target/` + local `~/.m2` | CI pipeline + artifact repository |
| Packaging | Plain JAR, no dependencies inside | Fat JAR / container image |

The last row is the one that changes most. A plain JAR holds only its own classes -
8.5 KB here, against 1.3 MB for `spring-context` alone. Running a Spring Boot service
from `java -jar` needs every dependency bundled in, which is a different packaging step
built on the same lifecycle.
