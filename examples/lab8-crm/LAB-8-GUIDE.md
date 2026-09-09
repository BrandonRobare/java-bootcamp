# Lab 8 — Northstar CRM skeleton

A plain-Java Maven skeleton for the Northstar Customer Management Platform: the standard
layout, seven layer packages, compile-ready stubs, and the standards the rest of the
bootcamp follows. **No Spring, no JPA, no HTTP** — structure only.

## Overview

Nothing here has behavior. Every method throws
`UnsupportedOperationException("Lab 8 stub — implement later")` on purpose, which lets the
*shape* of the application be reviewed before any logic exists to argue about.

```
com.northstar.crm
├── Main                    console banner, the only runnable thing
├── controller/             CustomerController        transport, delegates to service
├── service/                CustomerService           business rules (stub)
├── repository/             CustomerRepository        persistence boundary (stub)
├── entity/                 Customer                  domain model
├── dto/                    CustomerRequest           inbound contract
│                           CustomerResponse          outbound contract
├── config/                 AppConfig                 wiring placeholder
└── exception/              CustomerNotFoundException domain failure
```

Sample data used throughout: `CUS-1001` Amina Khan `ACTIVE` · `CUS-1002` Ravi Singh
`PROSPECT` · correlation ID `lab-request-001`.

## Compile and run

```bash
mvn clean compile
java -cp target/classes com.northstar.crm.Main
```

Expected:

```text
Northstar CRM skeleton — Lab 8
controller, service, repository, entity, dto, config, exception
Examples: CUS-1001 Amina Khan ACTIVE | CUS-1002 Ravi Singh PROSPECT
```

Any other entry point throws — that is correct for Lab 8.

## Docs

| File | What it is |
| ---- | ---------- |
| [`docs/CODING-STANDARDS.md`](docs/CODING-STANDARDS.md) | packages, layers, dependency direction, hard rules, naming. Read before adding a class |
| [`docs/layer-flow.md`](docs/layer-flow.md) | one create request traced through every layer, success and failure, with the type on each hop |
| [`docs/failure-experiments.md`](docs/failure-experiments.md) | five structural failures run deliberately, with the observed output |

## Design decisions

**Why layers.** Each package has one job, and dependencies point one way:
`controller → service → repository → entity`. The payoff is that a layer can be tested
against a fake of the layer below it, and swapping the database touches `repository` and
nothing else. The chain terminates at `entity`, which imports nothing above it — that is
what keeps the graph acyclic.

**Why stubs that throw, not stubs that return null.** A `null` placeholder fails somewhere
unrelated and sends the next person to the wrong layer. `UnsupportedOperationException`
names the exact unimplemented method in the stack trace — see
[experiment 2](docs/failure-experiments.md). It also makes "not done yet" impossible to
mistake for "done and returning nothing".

**Why DTOs are separate from the entity.** `CustomerRequest` carries `fullName` and
`email`. `Customer` carries `customerId`, `fullName`, `email`, `status`, `createdAt`.
`CustomerResponse` carries everything but `email`. Three different shapes on purpose: the
client does not choose IDs, and an internal field does not become part of the public
contract by accident. Collapse them into one class and the API and the database schema
become the same thing.

**Why constructor injection with a `final` field, before any framework.**
`CustomerService` holds `private final CustomerRepository repository` assigned in the
constructor, even though Lab 8 never calls it. `final` makes the dependency mandatory and
un-swappable after construction, and it is the exact shape Spring fills in later. Building
it by hand once is the only chance to see the wiring before annotations hide it.

**What the compiler does not check.** Adding
`import com.northstar.crm.controller.CustomerController;` to `CustomerRepository` compiles
green — verified in [experiment 4](docs/failure-experiments.md). The dependency rule is
enforced by `CODING-STANDARDS.md` and by review, nothing else. A later project can make it
a build failure with ArchUnit or Checkstyle import rules.

## Layout notes

Source root is `src/main/java`, not `src`. A `.java` file anywhere else is silently
ignored by Maven and missing from `target/classes` — [experiment 5](docs/failure-experiments.md).
`target/` is generated and gitignored; `mvn clean` deletes it and the next build recreates
it identically.
