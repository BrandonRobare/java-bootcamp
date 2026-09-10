# Lab 12: SOLID Apply vs Defer

| Principle | Apply now? | Why |
| --------- | ---------- | --- |
| S (SRP) | Apply | `doStuff` validates, checks for duplicates, parses a status string, builds the `Customer`, stores it, logs, and then updates status behind the `name.contains("UPDATE")` branch. Split it into `createCustomer`, `getCustomer`, and `updateStatus`, with a private `validateRequired` helper. |
| O (OCP) | Defer | The one closed-for-modification spot is the `if/else if` chain over the four status strings, and passing `CustomerStatus` as a parameter removes that chain. A strategy class per status would add structure with nothing behind it. |
| L (LSP) | Defer | There are no subclasses in the starter. One `CustomerService`, one `Customer`, nothing to substitute. |
| I (ISP) | Defer | There is no interface yet, and `Main` is the only caller. Wait until REST resources give it more than one client in Labs 13+. |
| D (DIP) | Defer | `List data = new ArrayList()` is hard-wired storage. DIP means a `CustomerRepository` interface plus injection, and `AppConfig` is an empty class today. Labs 13+ own the wiring. |

## Step 1: Apply now

SRP only. Move the required-field check (`customerId` and `fullName` non-null and
non-blank) out of the create path, and move the status change out of create into
`updateStatus`. It is the one letter Lab 12 can show, since the before/after diff on
`CustomerService` is the evidence.

## Step 2: Defer

- DIP: no `CustomerRepository` interface, no constructor injection, no Spring. Keep the
  in-memory `List<Customer>` as a private field.
- ISP: no `CustomerApi` / `CustomerReader` / `CustomerWriter` split. One class, one caller.

LSP and OCP are deferred too, but for a different reason. The starter has nothing for them
to fix. DIP and ISP are the two I could do and am choosing not to.

## Step 3: Why defer

Modules 10-12 come before REST hosting, so a port and adapter layer would be shaped by
guesses about a controller that does not exist yet. Lab 12 is judged on names, method
boundaries, `equals` over `==`, exceptions, and correlation logging, and a repository
interface does not help with any of those.

## Predicted answer

No. A DI framework is not required for Module 12. Nothing is wired at runtime, and `Main`
constructs `CustomerService` directly.

## Scope
Pre-lab only. Do not finish the full lab in this exercise.

## Fixtures
Amina `CUS-1001` / `ACTIVE`, Ravi `CUS-1002` / `PROSPECT`, correlation `lab-request-001`.
