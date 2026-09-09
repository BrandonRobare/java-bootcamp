# Coding standards — Northstar CRM (Lab 8)

Read this before adding a class. If it takes more than five minutes, it is too long.

## Packages

- Root: `com.northstar.crm`
- Layers: `controller`, `service`, `repository`, `entity`, `dto`, `config`, `exception`

## Layers — one job each

| Package | Owns |
| ------- | ---- |
| `controller` | transport / API mapping only |
| `service` | business rules and orchestration |
| `repository` | persistence |
| `entity` | the domain model |
| `dto` | request / response contracts |
| `config` | wiring |
| `exception` | domain and API failures |

## Dependency direction (hard rule)

```text
controller -> service -> repository -> entity
controller -> dto
service    -> dto, entity, exception
repository -> entity
entity     -> (nothing in other CRM layers)
```

Read it off the import list, not off intent. A package must never import from a package
above it, and the chain must terminate at `entity` — anything that arrives back where it
started is a cycle.

Note this is the *target* direction, not a description of today's stubs: `service`
currently imports `dto` and `repository` only, because nothing throws
`CustomerNotFoundException` until the stubs get bodies.

## Hard rules

- Services must not depend on controllers.
- Entities must not carry HTTP or SOAP types.
- Repositories must not import controllers.
- Controllers must not construct SQL or touch files — delegate to the service.
- No production passwords, API keys, or tokens in source or in `application.properties`.
- Prefer `CUS-####` for stable customer identities in examples.
- `target/` is generated — never committed.

## Naming

- Classes: PascalCase (`CustomerService`)
- Methods: camelCase (`createCustomer`)
- Packages: lowercase, reverse-domain root (`com.northstar.crm.service`)
- Repositories: `<Entity>Repository` · Services: `<Entity>Service` · Exceptions: `<Condition>Exception`
- Customer IDs: `CUS-1001`, `CUS-1002`
- Correlation: `lab-request-001`

## What must NOT live where

| Package | Must NOT own |
| ------- | ------------ |
| controller | SQL, business rules |
| service | HTTP headers, JDBC details |
| repository | REST mapping |
| entity | Request JSON shapes |
| dto | Persistence annotations (later JPA stays on entity) |
| config | business logic — it assembles objects, it never decides |

## Stubs

Unimplemented methods throw `UnsupportedOperationException("Lab 8 stub — implement later")`.
Never return `null` as a placeholder — a stub that throws fails where the problem is; a
stub that returns `null` fails three layers away.

## Lab 8 ban

No Spring, JPA, or Kafka imports in stubs.
