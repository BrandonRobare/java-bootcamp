# Failure experiments — Lab 8

Five structural failure modes, run deliberately and restored. Working state verified
after each. Environment: JDK 21.0.12 (Temurin), Maven 3.9.16, macOS.

---

## 1 — Remove the POM

**Do:** `mv pom.xml pom.xml.bak` then `mvn compile`

**Observed:**

```text
[ERROR] The goal you specified requires a project to execute but there is no POM in
this directory (/Users/brandon/java-bootcamp/examples/lab8-crm). Please verify you
invoked Maven from the correct directory.
[INFO] BUILD FAILURE
```

**What it shows:** `pom.xml` is not optional metadata — it is the project. Without it
Maven has no coordinates, no Java release, no source layout, and refuses to run at all.
The same error appears when you run `mvn` from the wrong directory, which is the more
common real-world cause.

**Restore:** `mv pom.xml.bak pom.xml` → `mvn clean compile` → `BUILD SUCCESS`

---

## 2 — Call a stub

**Do:** temporary `Throwaway.main` calling `new CustomerRepository().findById("CUS-1001")`

**Observed:**

```text
Exception in thread "main" java.lang.UnsupportedOperationException: Lab 8 stub — implement later
	at com.northstar.crm.repository.CustomerRepository.findById(CustomerRepository.java:16)
	at com.northstar.crm.Throwaway.main(Throwaway.java:7)
```

**What it shows:** the stub fails *loudly, at the exact line that isn't implemented*.
The stack trace names `CustomerRepository.findById` — no guessing. A stub that returned
`null` instead would have handed the caller a null, blown up somewhere unrelated, and
sent the next person hunting in the wrong layer. This is why Lab 8 stubs throw.

**Restore:** deleted `Throwaway.java`; stubs unchanged.

---

## 3 — Compile twice

**Do:** `mvn clean compile` back to back

**Observed:**

```text
run1: [INFO] BUILD SUCCESS
run2: [INFO] BUILD SUCCESS
```

**What it shows:** the build is reproducible and `target/` is genuinely disposable —
`clean` deletes it and the second run rebuilds it identically from source. Nothing in
`target/` is a source of truth, which is exactly why it is `.gitignore`d. A build that
only works the first time is depending on state nobody can see.

**Restore:** nothing to restore.

---

## 4 — Upward import (the important one)

**Do:** add `import com.northstar.crm.controller.CustomerController;` to
`CustomerRepository`, then `mvn compile`

**Observed:**

```text
3:import com.northstar.crm.entity.Customer;
4:import com.northstar.crm.controller.CustomerController;
5:import java.util.Optional;
  [INFO] BUILD SUCCESS
```

**What it shows — the point of the whole lab.** The architecture rule is **not
compiler-enforced.** Persistence now depends on the web layer, the dependency graph is
one step from a cycle, and the build is green. No error, no warning, nothing red in the
IDE.

Why a reviewer rejects it anyway:

- `CustomerRepository` can no longer be compiled or tested without the controller
- swapping REST for a batch job or a Kafka consumer now means touching persistence
- the next import in that direction closes the cycle, and then none of the three layers
  can be understood alone

The only things standing between this codebase and that outcome are `CODING-STANDARDS.md`
and someone reading the import list. Tooling that *can* enforce it — ArchUnit tests,
Checkstyle import rules, Maven module boundaries — exists, and this is the argument for
adding it.

**Restore:** import removed immediately; verified no `import com.northstar.crm.controller`
remains under `repository/`.

---

## 5 — Source file outside `src/main/java`

**Do:** create `src/java/com/northstar/crm/Stray.java`, then `mvn clean compile`

**Observed:**

```text
  [INFO] BUILD SUCCESS

  compiled classes in target/classes:
    com/northstar/crm/Main.class
    com/northstar/crm/dto/CustomerResponse.class
    com/northstar/crm/dto/CustomerRequest.class
    com/northstar/crm/repository/CustomerRepository.class
    com/northstar/crm/config/AppConfig.class
    com/northstar/crm/entity/Customer.class
    com/northstar/crm/controller/CustomerController.class
    com/northstar/crm/service/CustomerService.class
    com/northstar/crm/exception/CustomerNotFoundException.class

  Error: Could not find or load main class com.northstar.crm.Stray
  Caused by: java.lang.ClassNotFoundException: com.northstar.crm.Stray
```

**What it shows:** `Stray.java` is missing from the nine compiled classes. Maven did not
error and did not warn — it simply never looked at `src/java`, because the source root is
`src/main/java` by convention. The failure surfaces later and elsewhere, as
`ClassNotFoundException` at runtime.

Convention over configuration cuts both ways: follow the layout and everything is
automatic; miss it by one directory and your code silently does not exist.

**Restore:** `rm -rf src/java`; `src/` contains `main` and `test` only.

---

## Final state

```text
no upward import in repository/
src/main
src/test
[INFO] BUILD SUCCESS
Northstar CRM skeleton — Lab 8
controller, service, repository, entity, dto, config, exception
Examples: CUS-1001 Amina Khan ACTIVE | CUS-1002 Ravi Singh PROSPECT
```

All five experiments restored. Nine classes compile, `Main` runs, no layer violation
left in committed code.
