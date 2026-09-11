# Before / after: Lab 12

Baseline frozen at `src/main/java/com/northstar/crm/service/CustomerService.before.java.txt`
(64 lines). Refactored `CustomerService.java` is 80 lines. Longer, because every failure
now throws with a message and a correlation id instead of printing one word and returning
`null`.

## Smell to fix

Numbers match `smells.md`. Line numbers here refer to the refactored file.

| # | Smell | Fix | Where |
| - | ----- | --- | ----- |
| 1 | Poor naming | `doStuff(a..e)` is `createCustomer(customerId, fullName, email, phone, status)`; `get` is `getCustomer`; `data` is `customersById` | `:15`, `:22`, `:35` |
| 2 | Raw types | `Map<String, Customer>`; no casts remain | `:15` |
| 3 | Long method | Three public methods, one job each; validation pulled into `requireNonBlank`, `requireUniqueId`, `requireExisting` | `:51` to `:71` |
| 4 | Stringly-typed status | `CustomerStatus` parameter on both `createCustomer` and `updateStatus`; the `if/else` chain is gone | `:22`, `:39` |
| 5 | `==` on ids | `Map.get` and `containsKey` use `equals`/`hashCode`; the test passes `new String("CUS-1001")` to prove it | `:58`, `:65` |
| 6 | Null as control flow | Blank field: `IllegalArgumentException`. Duplicate: `IllegalStateException`. Unknown: `CustomerNotFoundException` | `:53`, `:60`, `:68` |
| 7 | Side-effect logging | `System.Logger` with level, one line per public entry, `correlationId=` on every line | `:13`, `:77` |
| 8 | Magic `"UPDATE"` | Removed. Status changes only through `updateStatus` | `:39` |
| - | Duplicated logic | One id scan (`requireExisting`), one status path | `:64` |
| - | `Object` return | Both return `Customer` | `:22`, `:35`, `:39` |
| - | Silent partial update | `updateStatus` accepts any `CustomerStatus`; a `null` status throws | `:41` |

Not fixed: `email` and `phone` are still stored unvalidated. Out of scope for the target
API; noted for Lab 13 where the request boundary lives.

## Methods

| Before | After |
| ------ | ----- |
| `Object doStuff(String a, String b, String c, String d, String e)` | `Customer createCustomer(String customerId, String fullName, String email, String phone, CustomerStatus status)` |
| `Object get(String id)` | `Customer getCustomer(String customerId)` |
| (hidden inside `doStuff` behind `"UPDATE"`) | `Customer updateStatus(String customerId, CustomerStatus newStatus)` |
| | `void setCorrelationId(String correlationId)` |
| | `private void requireNonBlank(String value, String fieldName)` |
| | `private void requireUniqueId(String customerId)` |
| | `private Customer requireExisting(String customerId)` |

## Test output

Before the refactor, all six `CustomerServiceTest` shells threw `UnsupportedOperationException`
and the class did not compile against `setCorrelationId`. After:

```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0 -- in com.northstar.crm.entity.CustomerTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0 -- in com.northstar.crm.service.CustomerServiceTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

`mvn -B verify` gives the same 8 and BUILD SUCCESS.

## Demo transcript

`java -cp target/classes com.northstar.crm.Main`, stdout only:

```
create Customer{customerId='CUS-1001', fullName='Amina Khan', status=ACTIVE}
create Customer{customerId='CUS-1002', fullName='Ravi Singh', status=PROSPECT}
get CUS-1001 -> Amina Khan
updateStatus CUS-1002 -> ACTIVE
duplicate CUS-1001 -> IllegalStateException: Duplicate customerId: CUS-1001 correlationId=lab-request-001
unknown CUS-9999 -> CustomerNotFoundException: Customer not found: CUS-9999 correlationId=lab-request-001
```

Log lines on stderr for the same run:

```
INFO: createCustomer ok customerId=CUS-1001 correlationId=lab-request-001
INFO: createCustomer ok customerId=CUS-1002 correlationId=lab-request-001
INFO: updateStatus ok customerId=CUS-1002 from=PROSPECT to=ACTIVE correlationId=lab-request-001
WARNING: createCustomer duplicate customerId=CUS-1001 correlationId=lab-request-001
WARNING: getCustomer miss customerId=CUS-9999 correlationId=lab-request-001
```

`get CUS-1001` in `Main` passes `new String("CUS-1001")`. Under the old `==` that line would
have returned `null`.

## Failure experiments

| # | Experiment | Observed | Restored |
| - | ---------- | -------- | -------- |
| 1 | Import renamed to `Customerr` | `mvn compile`: `CustomerService.java:[3,32] cannot find symbol` and again at `[15,31]` | Import fixed, compile clean |
| 2 | `createCustomer(" ", ...)` | `IllegalArgumentException: customerId is required correlationId=lab-request-001` from `requireNonBlank` (`blankCustomerIdThrows`) | Helper kept |
| 3 | Create `CUS-1001` twice | Second call: `IllegalStateException: Duplicate customerId: CUS-1001 ...`; Amina's name unchanged afterwards (`duplicateIdThrows`) | Kept |
| 4 | `getCustomer(new String("CUS-1001"))` | Returns the same instance that `createCustomer` returned (`assertSame`). `HashMap` keys compare with `equals` | Kept |
| 5 | Added `if (fullName.contains("UPDATE")) customer.setStatus(ACTIVE);` after the `put` | All 8 tests still green. Nothing in the suite names a customer containing `UPDATE`, so the magic branch is invisible to the tests. That is the risk: it is undocumented and untested behavior | Line removed; `grep UPDATE` on the service returns 0 |
