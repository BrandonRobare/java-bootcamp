# JDK 21 / Maven habit

- [x] `java -version` shows 21.x
- [x] `mvn -version` uses JDK 21
- PATH trap: if an older JDK appears before Temurin 21 on PATH, both `java` and `mvn` may silently use the wrong Java. Fix by setting `JAVA_HOME` to the JDK 21 install and ensuring the JDK 21 `bin` directory is first on PATH before starting the lab.
- Workspace: `examples/module-10-exercises/notes/`
- Boundary: do not run full Lab 10 Maven goals yet

## Evidence

- `java -version` -> OpenJDK 21.0.12.1
- `mvn -version` -> Java version: 21.0.12.1, vendor: Eclipse Adoptium
