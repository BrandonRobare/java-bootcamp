# Lab 12: Target API Sketch

## Step 1: Methods

Replacing `doStuff(a,b,c,d,e)` and `get(id)` with three named methods:

```java
Customer createCustomer(String customerId, String fullName, String email, String phone, CustomerStatus status);
Customer getCustomer(String customerId);                       // throws CustomerNotFoundException
Customer updateStatus(String customerId, CustomerStatus status);
```

| Method | Purpose |
| ------ | ------- |
| `createCustomer` | Validate required fields, reject a duplicate id, store one new `Customer` with `createdAt`. Creating only. No status update hidden inside it. |
| `getCustomer` | Look up by id using `equals`, not `==`. A missing id throws `CustomerNotFoundException` instead of returning `null`. |
| `updateStatus` | The only method that changes `CustomerStatus`. Takes the enum directly, so there is no status string to parse. |

Supporting changes this implies: `List<Customer>` instead of raw `List`, no casts,
`CustomerStatus` at the boundary instead of status-as-String, logging instead of
`System.out.println("ok " + a)`.

Names I left out: `findById`, because it does the same job as `getCustomer` and two
names for one job is worse than one. `validateStatus`, because the enum parameter
removes the string that needed validating.

## Step 2: Ravi path

Ravi is `CUS-1002` / `PROSPECT`. `updateStatus("CUS-1002", CustomerStatus.ACTIVE)` moves
him PROSPECT to ACTIVE. The status change belongs there, not in `createCustomer`. The
starter does it inside create, behind the `name.contains("UPDATE")` branch.

## Step 3: Keep out

Out of scope for this sketch: REST endpoints, `@RestController`, Spring wiring of any
kind, persistence and repositories. `CustomerService` stays a plain Java class called
from `Main`. Also out: the `CustomerRequest` and `CustomerResponse` DTOs. They are empty
shells in the starter, and filling them is a hosting concern rather than a naming cleanup.

## Step 4: Prep boundary

*Do not complete full Lab 12 refactor in pre-lab.* This file is the contract only. The
method bodies get written in Lab 12.

## Scope
Pre-lab only. Do not finish the full lab in this exercise.

## Fixtures
Amina `CUS-1001` / `ACTIVE`, Ravi `CUS-1002` / `PROSPECT`, correlation `lab-request-001`.
