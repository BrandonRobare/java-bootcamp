# Code smells: Lab 12

Cataloged from the messy baseline (`doStuff`), frozen at
`src/main/java/com/northstar/crm/service/CustomerService.before.java.txt`.
Line numbers refer to that file.

| # | Smell | Location | Impact on CUS-1001 |
| - | ----- | -------- | ------------------ |
| 1 | Poor naming (`doStuff`, `data`) | `:11` `data`, `:13` `doStuff` and params `a` to `e`, `:52` `get` | A reviewer cannot tell that `doStuff` creates Amina and `get` retrieves her without reading both bodies. The parameter meanings survive only in a comment on `:14`, so a caller passing email and phone in the wrong order compiles and stores bad contact data. |
| 2 | Raw types | `:11` `List data = new ArrayList()`, casts at `:21`, `:41`, `:54` | Nothing stops a non-`Customer` from entering the list, and the failure surfaces as a `ClassCastException` at read time rather than at the write that caused it. |
| 3 | Long method / mixed responsibilities | `:13` to `:50` | Create and update share one body, so a change to Amina's creation rules risks altering how her status is updated. The method changes for seven different reasons. |
| 4 | Stringly-typed status | `:31` to `:35`, repeated at `:43` to `:44` | Creating Amina with `"active"` in lowercase falls through the chain to the `else` on `:35` and silently stores `PROSPECT`. A typo becomes wrong data with no error, and she is downgraded to Ravi's status. |
| 5 | Incorrect equality (`==`) | `:55` `getCustomerId() == id`, also `:15` `a == ""` | `getCustomer("CUS-1001")` resolves Amina only when the caller passes the same String instance that was stored. An id read from input or built at runtime misses, and support is told she does not exist. This is a live defect, not a style issue. |
| 6 | Null as control flow | `:17`, `:23`, `:59` | A blank name and a duplicate `CUS-1001` both return `null`, so the caller sees one failure with two possible causes. The lookup miss also returns `null`, which becomes an NPE in the caller rather than an error at the point of failure. |
| 7 | Side-effect logging | `:16`, `:22`, `:38`, `:45` | The record of creating Amina is `ok CUS-1001` on stdout. No level, no timestamp, and no `lab-request-001`, so two concurrent requests cannot be told apart and nothing is greppable by correlation id. |
| 8 | Magic `"UPDATE"` behavior | `:39` | A customer whose `fullName` contains `UPDATE` triggers a status rewrite nobody requested. Ravi at `CUS-1002` cannot be moved from `PROSPECT` to `ACTIVE` at all unless his name happens to contain that word. |

## Also present

| Smell | Location | Note |
| ----- | -------- | ---- |
| Duplicated logic | status chain at `:31` and `:43`, id scan at `:19`, `:40`, `:53` | Two copies of the same bug. Fixing `==` in one scan leaves the others broken. |
| `Object` return type | `:13`, `:52` | Callers must cast to `Customer` to use the result. |
| Silent partial failure | `:43` to `:44` | The update branch handles only `ACTIVE` and `PROSPECT`. Suspending Amina falls through and reports nothing, so she stays `ACTIVE` while the caller believes the change landed. |
| Incomplete validation | `:15` | Only `customerId` and `fullName` are checked. `email` and `phone` are stored unvalidated. |

## Fix order

1. `==` on ids (`:55`). The only live defect, one-line fix, provable with a test passing `new String("CUS-1001")`.
2. Split `doStuff` into `createCustomer`, `getCustomer`, and `updateStatus`. Smells 1, 3, 4, 8 and the duplicated logic all go with it.
