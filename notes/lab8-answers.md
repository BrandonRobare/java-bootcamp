# Lab 8 - Reflection Answers

## 1. Which design decision most affected correctness of the skeleton?

Putting entity↔DTO mapping in the service. The service is the only layer holding
both a `CustomerRequest` and a `Customer`, so mapping anywhere else would force a
layer to import a type it has no business knowing — a controller building entities,
or a repository unpacking request objects. That one placement is what keeps
`CustomerRequest` out of the repository and SQL out of the controller.

## 2. What evidence proves the layered structure is real, not only aspirational?

The imports. `CustomerRepository` imports only `entity.Customer` — no DTO, no
controller type, no HTTP — and `Customer` itself imports nothing at all. Dependency
direction is checkable rather than claimed: every arrow points inward toward the
domain, and a violation would show up as an import that should not compile there.

The second piece of evidence is that `StructureDemo` runs the whole request path —
request → entity → response — with no framework present. If the boundaries only
existed on a diagram, plain Java could not walk them.

## 3. Which failure was hardest to diagnose (pathing, packages, POM)?

Pathing, and it was an IDE problem rather than a Java one. `practice/Module-8/src`
and `examples/module-08-exercises/mini-src` were both marked as source roots in the
same IntelliJ module, and both declare `com.northstar.crm.entity.Customer` — so the
IDE reported `duplicate class` plus a cascade of `cannot find symbol` errors in
files that were individually correct.

What made it hard is that the errors named the *wrong* files: `App.java` and
`InMemoryCustomerRepository` were flagged for calling `getCustomerId()` and
`setId()`, methods that exist perfectly well on the version of `Customer` those
files were written against. Nothing was wrong with the code. Fixed on 2026-09-09 by
excluding `mini-src` from the IntelliJ module — the exercise tree is compiled by
hand with `javac -d mini-out` and never needed to be a source root.

Lesson: when the error points at a file you did not change, check whether two
copies of the same fully-qualified name are on one classpath before debugging the
file itself.
