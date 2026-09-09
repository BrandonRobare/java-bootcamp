# Dependency tree notes (Exercise 5)

Command used (must **not** use `-q`):

```text
mvn dependency:tree
```

`-q` suppresses `[INFO]` lines, and the tree *is* printed as `[INFO]` lines — so quiet mode leaves you staring at nothing but `BUILD SUCCESS`. Re-run without the flag.

## The real tree from `mini-maven/`

```text
com.northstar:build-demo:jar:0.1.0-SNAPSHOT
\- org.junit.jupiter:junit-jupiter:jar:5.11.4:test
   +- org.junit.jupiter:junit-jupiter-api:jar:5.11.4:test
   |  +- org.opentest4j:opentest4j:jar:1.3.0:test
   |  +- org.junit.platform:junit-platform-commons:jar:1.11.4:test
   |  \- org.apiguardian:apiguardian-api:jar:1.1.2:test
   +- org.junit.jupiter:junit-jupiter-params:jar:5.11.4:test
   \- org.junit.jupiter:junit-jupiter-engine:jar:5.11.4:test
      \- org.junit.platform:junit-platform-engine:jar:1.11.4:test
```

One declared dependency, nine artifacts resolved.

## Direct vs transitive

| Dependency | Direct or transitive? | Why |
| ---------- | --------------------- | --- |
| `org.junit.jupiter:junit-jupiter` | **Direct** | The only `<dependency>` in `pom.xml` |
| `junit-jupiter-api` | Transitive | Pulled by `junit-jupiter` — the annotations and assertions |
| `opentest4j` | Transitive (depth 3) | Pulled by the API — the shared assertion-failure exception type |
| `junit-platform-commons` | Transitive (depth 3) | Pulled by the API |
| `apiguardian-api` | Transitive (depth 3) | Pulled by the API — `@API` stability annotations |
| `junit-jupiter-params` | Transitive | Parameterized-test support |
| `junit-jupiter-engine` | Transitive | The engine that actually runs the tests |
| `junit-platform-engine` | Transitive (depth 3) | Pulled by the engine |

Everything shows `:test`, so none of it is on the production classpath or in `build-demo.jar` — this is `<scope>test</scope>` from Exercise 4 doing its job, visible.

## Reading the symbols

- `+-` — this node has more siblings after it at the same level.
- `\-` — last child at this level (backslash + hyphen), nothing else follows under that parent.
- `|` — a guide line, only there to connect a parent to children drawn further down.

From the tree above:

- `+- org.junit.jupiter:junit-jupiter-api:jar:5.11.4:test` — a `+-` line: `params` and `engine` still follow it under `junit-jupiter`.
- `\- org.junit.platform:junit-platform-engine:jar:1.11.4:test` — a `\-` line, and the deepest node in the tree: nothing follows it anywhere.

Read a row left → right: `groupId:artifactId:type:version:scope`.

## Surprising node

The platform artifacts resolve to **1.11.4** while the Jupiter artifacts are **5.11.4**. Not a conflict — JUnit 5 ships two version lines from one release train (`junit-jupiter` 5.x sits on `junit-platform` 1.x). Worth knowing before it gets "fixed" by someone forcing 5.11.4 onto a platform artifact that has no such version.

Also note `opentest4j` at 1.3.0 — a third-party artifact, not JUnit's own. A license or CVE review has to account for it even though nobody on the team ever typed its name.

## Why the tree matters

The POM lists what we asked for; the tree lists what we actually get. Security and license reviews start here, because eight of these nine artifacts arrived without anyone deciding on them.

## CI command habit

```text
-B = batch mode (CI-friendly)
verify = compile + test + package checks without casually installing to every laptop ~/.m2
CI habit for this bootcamp: mvn -B verify
```

## Pass check

- Direct vs transitive correct for the Jupiter rows — Pass
- `+-` vs `\-` explained in one line each — Pass
- Ran `mvn dependency:tree` without `-q` — Pass
- `mvn -B verify` recorded as the CI habit — Pass
