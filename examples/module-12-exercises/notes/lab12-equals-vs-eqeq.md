# Lab 12: Equals vs ==

## Reference

| Check | Use | Why |
| ----- | --- | --- |
| status is `ACTIVE`? | `==` on the enum | Enum constants are singletons, so one instance per value. |
| same `Customer` instance? | `==` | Reference identity is the actual question. |
| id is `CUS-1001`? | `equals` | Value equality. Same characters, possibly different objects. |
| either side might be null | `Objects.equals(a, b)` | Null-safe both ways. `status.equals("ACTIVE")` throws on null. |
| id as a lookup key | `Map<String, Customer>` | `HashMap` is defined on `hashCode` and `equals`. |

## Step 2: Bad snippet

```java
if (status == "ACTIVE") { }   // Fail
```

Passes when `status` came from a literal, because both sides are the same pooled object. Fails when it came from input, concatenation, or a database read. Behavior depends on data origin, so it goes green in tests and fails in production.

## Step 3: Good snippet

```java
if (customer.getStatus() == CustomerStatus.ACTIVE) { }        // enum, safe

if (Objects.equals(status, "ACTIVE")) { }                     // String fallback, null-safe
```

Amina is `CUS-1001` / `ACTIVE`. One instance of the constant exists, so `==` compares the right thing regardless of where the value came from.

## Step 4: JDK note

On JDK 21, prefer the enum when the value set is closed. `CustomerStatus` has four compile-time values, so a typo like `ACTVE` becomes a compile error instead of a silent false, `switch` can be checked for exhaustiveness, and `==` becomes correct rather than a trap. Keep `Objects.equals` for open values like ids and names.

## Debug challenge: why `getCustomer(new String("CUS-1001"))` fails with `==`

`new String(...)` allocates outside the string pool, so the argument and the stored id hold the same characters at different addresses. `==` compares addresses, gets false, and reports Amina as missing. The literal form passes only by accident, which is why a test for this fix has to use `new String(...)`.

## Predicted answer: List scan with `==`, or Map plus `equals`?

`Map<String, Customer>` plus `equals`.

- Value equality becomes the default instead of something the caller remembers.
- Lookup drops from O(n) to O(1).
- The duplicate-id check folds into `containsKey`, removing a second scan.

A `List` scan with `equals` is correct but still linear. A `List` scan with `==` is the defect above.

## Scope
Pre-lab only. Do not finish the full lab in this exercise.

## Fixtures
Amina `CUS-1001` / `ACTIVE`, Ravi `CUS-1002` / `PROSPECT`, correlation `lab-request-001`.
