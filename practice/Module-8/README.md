# Module 8 — Controller to Service to Repository to Database

A create-customer request, traced through every layer. Plain Java, no Spring, no JPA,
no database server — Module 8 is structure only.

## Run it

```bash
cd ~/java-bootcamp/practice/Module-8
javac -d out $(find src -name "*.java")
java -cp out com.northstar.crm.App
```

```
--- create a customer ---
      [db]   INSERT INTO customers -> id 1
      [out]  CustomerResponse{id=1, name=Amina Khan, status=ACTIVE}
--- fetch it again ---
      [db]   SELECT * FROM customers WHERE id = 1
      [out]  CustomerResponse{id=1, name=Amina Khan, status=ACTIVE}
--- create with a blank name ---
      [out]  rejected: Name is required
```

---

## 1. The layers, and what changes shape between them

```
   +------------------------------------------+
   |  Client      App.java                    |   the browser / Postman / Angular
   +------------------------------------------+
                    |          ^
    CustomerRequest |          |  CustomerResponse
                    v          |
   +------------------------------------------+
   |  Controller  controller/                 |   front door - delegate, nothing else
   +------------------------------------------+
                    |          ^
    CustomerRequest |          |  CustomerResponse
                    v          |
   +------------------------------------------+
   |  Service     service/                    |   business rules + all the mapping
   +------------------------------------------+
                    |          ^
           Customer |          |  Customer (id = 1)
                    v          |
   +------------------------------------------+
   |  Repository  repository/                 |   the only door to storage
   +------------------------------------------+
                    |          ^
             INSERT |          |  the saved row
                    v          |
   +------------------------------------------+
   |  Database    a HashMap                   |   swap for PostgreSQL, nothing above changes
   +------------------------------------------+
```

The type changes twice on the way down and twice on the way back. Both changes happen
in the **service** — that is what a service is for.

---

## 2. The same trip, step by step

```
Client           Controller           Service            Repository  Database
   |                  |                  |                    |          |
   | CustomerRequest  |                  |                    |          |
   |------------------>                  |                    |          |
   |                  | createCustomer() |                    |          |
   |                  |------------------>                    |          |
   |                  |                  |     validate name  |          |
   |                  |                  |--.                 |          |
   |                  |                  |<-'                 |          |
   |                  |                  |     map to entity  |          |
   |                  |                  |--.                 |          |
   |                  |                  |<-'                 |          |
   |                  |                  | save(customer)     |          |
   |                  |                  |-------------------->          |
   |                  |                  |                    | INSERT   |
   |                  |                  |                    |---------->
   |                  |                  |                    | id = 1   |
   |                  |                  |                    <----------|
   |                  |                  | Customer id=1      |          |
   |                  |                  <--------------------|          |
   |                  |                  |     map to DTO     |          |
   |                  |                  |--.                 |          |
   |                  |                  |<-'                 |          |
   |                  | CustomerResponse |                    |          |
   |                  <------------------|                    |          |
   | CustomerResponse |                  |                    |          |
   <------------------|                  |                    |          |
   |                  |                  |                    |          |
```

Read it as three jobs:

- **Controller** — one line. Hand it to the service, hand the answer back.
- **Service** — decides things (validate, set status) and translates (DTO to entity, entity to DTO).
- **Repository** — talks to the database. Knows nothing about customers being valid or invalid.

---

## 3. When it fails, it never reaches the database

```
 Client           Controller           Service            Repository  Database
   |                  |                  |                    |          |
   | name = ""        |                  |                    |          |
   |------------------>                  |                    |          |
   |                  | createCustomer() |                    |          |
   |                  |------------------>                    |          |
   |                  |                  |     validate name  |          |
   |                  |                  |--.                 |          |
   |                  |                  |<-'  FAILS          |          |
   |                  |                  |                    |          |
   |                  | IllegalArgumentException              |          |
   |                  <------------------|                    |          |
   | "Name is required"                  |                    |          |
   <------------------|                  |                    |          |
   |                  |                  |                    |          |
   |                  |                  |                    X          X
                                                        never reached
```

Bad data stops at the highest layer that can recognize it. Nothing gets written, so
nothing has to be cleaned up.

---

## 4. Dependencies point one way

Allowed — arrows only ever point right:

```
   +------------+     +---------+     +------------+     +--------+
   | controller | --> | service | --> | repository | --> | entity |
   +------------+     +---------+     +------------+     +--------+
```

Forbidden — every one of these:

```
   +------------+     +---------+     +------------+
   | controller | <-X | service | <-X | repository |
   +------------+     +---------+     +------------+
```

`dto` is the shared exception: controller and service both import it. The repository
does not — it deals in entities, because that is what the database stores.

Prove it on your own code:

```bash
grep -rn "^import com.northstar" src/com/northstar/crm/repository
```

Only `entity` should come back. If `service` or `controller` shows up there, the layers
have leaked into each other and you can no longer test or replace either one alone.

---

## 5. Where the Spring annotations go later

Same files, same method bodies. Annotations are the only thing added.

```
   +--------------------------------------+---------------------------------------+
   |  now (Module 8)                      |  later (Spring modules)               |
   +--------------------------------------+---------------------------------------+
   |  class CustomerController            |  @RestController                      |
   |                                      |  @RequestMapping("/api/customers")    |
   |                                      |                                       |
   |  CustomerResponse create(req)        |  @PostMapping                         |
   |                                      |  ResponseEntity<CustomerResponse>     |
   |                                      |     create(@Valid @RequestBody req)   |
   +--------------------------------------+---------------------------------------+
   |  class CustomerService               |  @Service  (+ @Transactional)         |
   |  if (name.isBlank()) throw ...       |  @NotBlank on the request DTO         |
   +--------------------------------------+---------------------------------------+
   |  interface CustomerRepository        |  extends JpaRepository<Customer, Long>|
   |  class InMemoryCustomerRepository    |  deleted - Spring writes it for you   |
   +--------------------------------------+---------------------------------------+
   |  class Customer                      |  @Entity @Table(name = "customers")   |
   |                                      |  @Id @GeneratedValue                  |
   +--------------------------------------+---------------------------------------+
   |  new Repo(); new Service(repo); ...  |  constructor injection - Spring wires |
   |  (hand-wired in App.java)            |  it from the annotations              |
   +--------------------------------------+---------------------------------------+
   |  try/catch in App.java               |  @RestControllerAdvice, one class,    |
   |                                      |  every endpoint                       |
   +--------------------------------------+---------------------------------------+
```

---

## Files

| File | Its one job |
| ---- | ----------- |
| `App.java` | plays the client; hand-wires the layers |
| `controller/CustomerController.java` | front door — delegate |
| `service/CustomerService.java` | business rules + DTO/entity mapping |
| `repository/CustomerRepository.java` | interface — the door to storage |
| `repository/InMemoryCustomerRepository.java` | the "database" (a HashMap is the table) |
| `entity/Customer.java` | one row |
| `dto/CustomerRequest.java` | what comes in — no id, no status |
| `dto/CustomerResponse.java` | what goes out — no email, on purpose |
