# Request flow — create customer (Exercise 5)

Traced for one request: **Amina Khan · amina@example.test · requested status ACTIVE ·
correlation ID `lab-request-001`**.

## Success flow

1. Client sends create payload — `{ "name": "Amina Khan", "email": "amina@example.test" }`
2. **Controller** — `CustomerController.create(...)` receives the body as a
   `CustomerRequest`, checks the *shape* only (name present, email well-formed), and
   delegates. It never inspects the customer list and never builds an ID.
3. **Service** — `CustomerService.create(request)` runs the business rules: name not
   blank, name not already taken. It assigns `id = CUS-1001` and `status = ACTIVE`, then
   builds the entity: `new Customer("CUS-1001", request.getName(), "ACTIVE")`.
4. **Repository** — `CustomerRepository.save(entity)` writes it. Storage only; no rule
   runs here.
5. **Response DTO back** — the service maps the saved entity to
   `new CustomerResponse("CUS-1001", "Amina Khan", "ACTIVE")` and returns it to the
   controller, which sends it out. `summary()` → `CUS-1001 | Amina Khan | ACTIVE`.

```
Client ──► Controller ──► Service ──► Repository ──► (store)
                             │
Client ◄── Controller ◄──────┘  CustomerResponse
```

## Failure flow — blank name

```
Client ──► Controller ──► Service  ✗ validation fails
                             │
Client ◄── Controller ◄──────┘  error

                          Repository NEVER CALLED
```

1. Client sends `{ "name": "", "email": "amina@example.test" }`
2. **Controller** accepts it — an empty string is a structurally valid request body
3. **Service** rejects it — a blank name is a *business* rule, so this is where it dies.
   No ID is assigned, no entity is built
4. **Repository is never called.** Nothing reaches storage, so there is no partial
   write to clean up
5. The failure travels back out as an error response, not as a saved customer

The layer that stops the request is the layer that owns the rule. Because validation
lives in the service, the database is never touched — put the same check in the
repository and a bad row would already be halfway in.

## Transformations at each hop

| Hop | Type in flight |
| --- | -------------- |
| client → controller | JSON |
| controller → service | `CustomerRequest` (name, email) |
| service → repository | `Customer` (id, name, status) |
| repository → service | `Customer` |
| service → controller | `CustomerResponse` (id, name, status) |
| controller → client | JSON |

`email` arrives on the request and does not appear on the entity; `id` and `status` are
assigned by the service and do not appear on the request. The two shapes are
deliberately different — that is the point of separating DTO from entity.

**Entities never leave the service. DTOs never reach storage.** The service is the
conversion point in both directions.

## Now vs later

<!-- DONE: truthful now-vs-later table -->

| | Now (Module 8, plain Java) | Later (Spring modules) |
| --- | --- | --- |
| Entry point | `StructureDemo.main()` calls the classes directly | `@RestController` + `@PostMapping` on a real HTTP endpoint |
| Request binding | `new CustomerRequest("Amina Khan", "amina@example.test")` in code | `@RequestBody` deserializes JSON |
| Shape validation | a manual `isBlank()` check | `@NotBlank` / `@Email` on the DTO, triggered by `@Valid` |
| Persistence | `CustomerRepository` stub throwing `UnsupportedOperationException` | `JpaRepository<Customer, Long>`, implemented by Spring Data |
| ID assignment | hardcoded `CUS-1001` | `@Id @GeneratedValue` from the database |
| Failure response | a returned string or a thrown exception | `@RestControllerAdvice` mapping it to an HTTP status |
| Wiring | constructor called by hand | constructor injection by the container |

What is real today: the package names, the layer responsibilities, the DTO ↔ entity
split, and the documented flow. What is not yet real: HTTP, annotations, a database, and
any actual persistence.

## Notes

<!-- DONE: seams for validation / exceptions later -->

- **Validation seam** — the shape check in the controller becomes `@Valid` on the DTO;
  the blank-name and duplicate-name rules stay in the service, since neither is
  expressible as an annotation on a request body.
- **Exception seam** — the service should throw `CustomerNotFoundException` and a
  duplicate/validation exception rather than returning error strings. Once
  `@RestControllerAdvice` exists, each maps to a status code with no change to the
  service.
- **Correlation seam** — `lab-request-001` is passed by hand today. It becomes a request
  header logged at every layer, so one failure can be traced end to end (Module 7,
  contextual logging).

## Pass check

- Success flow documented, Client → Controller → Service → Repository → Controller — Pass
- Failure flow documented, blank name stops at the service, repository never called — Pass
- DTO/entity transformations listed per hop — Pass
- Truthful now-vs-later table — Pass
