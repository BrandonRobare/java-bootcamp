# Lab 12: Fill Correlation One-Liner TODOs

## Step 1: TODOs filled

| Blank | Value |
| ----- | ----- |
| Correlation id value | `lab-request-001` |
| Log on activate entry | `INFO updateStatus start correlationId=lab-request-001 customerId=CUS-1002` |
| Log on activate success for Ravi | `INFO updateStatus ok correlationId=lab-request-001 customerId=CUS-1002 from=PROSPECT to=ACTIVE` |
| Never log field | `email`, and `phone` with it. Log that a value was present, never the value. |
| Place correlation in | MDC |

## Step 2: One line per path

| Path | Line |
| ---- | ---- |
| Create | `INFO createCustomer ok correlationId=lab-request-001 customerId=CUS-1001` |
| Create rejected | `WARN createCustomer duplicate correlationId=lab-request-001 customerId=CUS-1001` |
| Get, unknown id | `WARN getCustomer miss correlationId=lab-request-001 customerId=CUS-9999` |
| Update | `INFO updateStatus ok correlationId=lab-request-001 customerId=CUS-1002 from=PROSPECT to=ACTIVE` |

Ids and status values only. `Customer.toString` already excludes `email` and `phone`, but it
does include `fullName`, so log the `customerId` rather than the object.

## Step 3: One-liner rule

*Every public service entry logs correlation once.*

Once at entry, not once per branch. Repeating it inside every `if` is how one request turns
into six log lines that all say the same thing.

## Step 4: Why MDC and not a header

There is no header to read. REST hosting is deferred to Labs 13+, so `CustomerService` is
called straight from `Main` and the id has to be set by the caller. MDC holds it for the
duration of the call without adding a parameter to every method signature. When a controller
arrives later it reads the header and populates the MDC, and the service code does not change.

## Debug challenge: blank correlationId in a failure path

Failure paths are the ones that need it most, since they are what support searches for. A
duplicate-id rejection or a missing-customer lookup that logs without `lab-request-001`
produces the one line nobody can trace back to a request. Set the id before the call, not
inside the success branch.

## Predicted answer: where correlationId appears in the Main demo

On every line. `Main` sets `lab-request-001` once at the start of the demo, and each service
call carries it, so `grep lab-request-001` returns the whole run in order. If some lines have
it and some do not, the id was set inside the service instead of by the caller.

## PII note

The boundary normalizes `email` by trimming and lowercasing it. That is a validation step,
not permission to log it. Store the normalized value, log the `customerId`.

## Scope
Pre-lab only. Do not finish the full lab in this exercise.

## Fixtures
Amina `CUS-1001` / `ACTIVE`, Ravi `CUS-1002` / `PROSPECT`, correlation `lab-request-001`.
