# Layer responsibilities (Exercise 4)

| Responsibility | Controller | Service | Repository |
| -------------- | ---------- | ------- | ---------- |
| Parse/adapt input | **owns** | — | — |
| Business rules | — | **owns** | — |
| Persist/load | — | — | **owns** |
| Map entity ↔ DTO (typical) | — | **owns** | — |

Controller adapts HTTP to a DTO and back. Service decides. Repository stores. The
service is the only layer holding both a `CustomerRequest` and a `Customer`, which is
why the mapping lives there and not at either end.

## Seven CRM tasks, assigned

| Task | Layer | Why |
| ---- | ----- | --- |
| Accept the incoming create-customer payload | `controller` | HTTP is the controller's only concern |
| Reject a blank name before anything is stored | `service` | a business rule, not a transport rule |
| Look up a customer by ID | `repository` | data access, nothing else |
| Hold `id` / `name` / `status` for a real customer | `entity` | the domain model — what a customer *is* |
| Define the shape of the request body | `dto` | `CustomerRequest` is the API contract, not the domain |
| Signal that a customer was not found | `exception` | `CustomerNotFoundException`, thrown by the service |
| Decide which repository implementation gets wired in | `config` | assembly, no business decisions |

## Wrong ownership examples

<!-- DONE: SQL in controller; HTTP details in repository -->

- **SQL in the controller** — `SELECT * FROM customers WHERE ...` built in a
  `@PostMapping` method. Two layers skipped at once: no business rule can run, and the
  query cannot be reused or tested without standing up HTTP.
- **HTTP details in the repository** — a repository returning `ResponseEntity` or
  reading a request header. Persistence now depends on the web layer, so swapping REST
  for a batch job or a Kafka consumer means rewriting the repository.
- **Business rules in the entity** — a `Customer` that calls a pricing service. The
  domain object now needs infrastructure to exist, so it cannot be constructed in a test.
- **DTOs reaching the repository** — saving a `CustomerRequest` directly. The API
  contract becomes the database schema, and changing one breaks the other.

## God controller — repaired

**Before** — one method doing three layers' work:

```java
@PostMapping
public String create(CustomerRequest req) {
    if (req.getName().isBlank()) return "error";               // business rule
    if (customerList.stream().anyMatch(                        // querying storage
            c -> c.getName().equals(req.getName()))) return "duplicate";
    customerList.add(new Customer("CUS-" + counter++,          // storage + ID assignment
            req.getName(), "ACTIVE"));
    return "created";
}
```

**After** — each step where it belongs:

```
Controller   receives CustomerRequest, delegates
     ↓
Service      rejects a blank name
             asks the repository whether the name already exists
             assigns CUS-1001 and status ACTIVE
             builds the Customer entity
     ↓
Repository   save(entity)
     ↑
Service      maps the saved entity → CustomerResponse
     ↑
Controller   returns the response
```

Flow: `Controller → Service → Repository → Service → Controller`. The controller now
holds no rule, no query, and no list.

## Benefits of the repair

1. **Testable without HTTP.** The duplicate-name rule can be tested by calling the
   service with a fake repository — no server, no request object, milliseconds instead
   of seconds.
2. **The storage choice becomes replaceable.** `customerList` can become a database
   behind the same `CustomerRepository` interface, and neither the controller nor the
   service changes.
3. **The rule stops being copy-pasted.** A second entry point — a bulk import, an admin
   screen — calls the same service instead of duplicating the validation and drifting
   out of sync.

## Pass check

- Seven tasks assigned to a layer — Pass
- God-controller flow repaired, ends `Controller → Service → Repository → Service → Controller` — Pass
- Two or more benefits explained — Pass (three)
