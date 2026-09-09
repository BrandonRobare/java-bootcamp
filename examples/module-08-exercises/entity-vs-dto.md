# Entity vs DTO (Exercise 3)

| Type | Fields | Why those fields |
| ---- | ------ | ---------------- |
| `CustomerRequest` | `name`, `email` | What a caller is allowed to send. `id` and `status` are ours to assign, so accepting them would let a client pick its own ID or activate itself. |
| `Customer` (entity) | `id`, `name`, `status` | Internal identity and state. `email` lives here too in a real system; the demo keeps it off to show the shapes are allowed to differ. |
| `CustomerResponse` | `id`, `name`, `status` | The safe outward view. It can omit anything the entity stores internally — audit columns, password hashes, a soft-delete flag. |

The three shapes differ on purpose. If the entity were used at the boundary, every
database column change would become an API-breaking change, and every field the DB
gains would leak to clients by default.

## Run

```bash
javac -d mini-out mini-src/com/northstar/crm/entity/Customer.java mini-src/com/northstar/crm/dto/CustomerRequest.java mini-src/com/northstar/crm/dto/CustomerResponse.java mini-src/com/northstar/crm/StructureDemo.java
java -cp mini-out com.northstar.crm.StructureDemo
```

Output: `CUS-1001 | Amina Khan | ACTIVE`

## Failure experiment

Changing `Customer.java` to `package com.northstar.crm.dto;` without moving the file:

```text
StructureDemo.java:5: error: package com.northstar.crm.entity does not exist
StructureDemo.java:12: error: cannot find symbol  class Customer
3 errors
```

The compiler resolves imports by package name, not by file location, so a declaration
that disagrees with its folder makes the class unreachable.

## Pass check

- Package tree matches declarations — Pass
- Compile and run output matches expected — Pass
- Entity vs request/response DTO explained — Pass
