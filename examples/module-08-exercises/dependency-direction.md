# Dependency direction (Exercise 6)

"Depends on" means **imports**. The rule is read off the import list, not the intent.

| From → To | Legal? | Why |
| --------- | ------ | --- |
| controller → service | **Yes** | the normal chain — the controller's whole job is delegating |
| service → repository | **Yes** | the service asks storage for what it needs |
| repository → controller | **No** | persistence depending on UI — the repository would need HTTP to compile |
| service → controller | **No** | backwards; the service must not know a controller exists |
| controller → repository (skipping service) | **Compiles, but wrong** | not a direction violation — a *layer-skipping* one. Bypasses every business rule |
| entity → controller | **No** | the domain depending on transport — `Customer` would break if the API changed |
| service → dto | **Yes** | the service accepts and returns DTOs; it is the conversion point |

Two of these need a sentence more:

- **controller → repository** points the right way, so nothing stops it compiling. It is
  still wrong: validation, duplicate checks and ID assignment all live in the service, and
  the controller has just skipped them. This is how god controllers start.
- **service → dto** is legal but worth watching. Transport types leaking *further down*
  — a repository importing `CustomerRequest` — is the failure mode. DTOs stop at the
  service.

## The cycle, repaired

**Broken:**

```
controller → service → repository → controller
     ▲                                   │
     └───────────────────────────────────┘
```

None of the three can be compiled, tested, or understood alone. Change one and all three
are in the blast radius — three names for one lump.

**Repaired — the chain has to end somewhere:**

```
controller → service → repository → entity
                                      │
                                   imports nothing above it
```

`entity` is the terminator. It has no import pointing back up, so the graph is acyclic
and each layer above can be compiled and tested against the ones below it.

## The architecture rule

<!-- DONE: one written rule -->

> Dependencies flow `controller → service → repository → entity`. A package must never
> import from a package above it, and `entity` and `repository` must never import
> `controller`. Every call goes through the next layer down — never skip one.

Checkable without reading any logic: open a file under `entity/` or `repository/` and
look at its imports. If `com.northstar.crm.controller` appears, the rule is broken.

## Why it earns its keep

- **Testability** — the service can be tested with a fake repository, because it does not
  know what a controller is. In a cycle, testing the service drags in HTTP.
- **Replaceability** — swap the database and only `repository` changes; nothing below it
  depends on anything above it, so nothing above notices.
- **Blast radius** — a change stops at the layer that owns it instead of rippling both
  directions.

## Pass check

- Seven dependencies classified — Pass
- Cycle identified and repaired so the chain ends at `entity` — Pass
- One written architecture rule — Pass
