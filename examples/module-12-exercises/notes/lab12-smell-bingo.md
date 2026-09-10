# Lab 12: Smell Bingo

Read against `lab12/starter/src/main/java/com/northstar/crm/service/CustomerService.java`.

## Step 1: Smell list

| Smell | Seen? | Where / fix idea |
| ----- | ----- | ---------------- |
| Long method | Yes | `doStuff` does seven jobs in ~35 lines. Split into `createCustomer` / `getCustomer` / `updateStatus`. |
| `==` on String ids | Yes | `get(id)` uses `==`, validation uses `a == ""`. Use `.equals()` and `isBlank()`. |
| Poor names | Yes | `doStuff`, `get`, `data`, params `a` to `e`. Rename to the exercise 1 API. |
| Duplicated logic | Yes | Status chain appears twice, id scan three times. Taking the enum deletes both chains. |
| Magic strings | Yes | `"ACTIVE"` / `"PROSPECT"` / `"SUSPENDED"` / `"CLOSED"` parsed when `CustomerStatus` exists. |
| Mixed I/O in domain | Yes | `System.out.println("bad" / "dup" / "ok " / "upd")`. No level, no correlation id. |
| Null as an error code | Yes | Same `null` for bad input and duplicate id. Throw instead. |
| Raw type plus casts | Yes | `List data = new ArrayList()` forces a cast at every read. Use `List<Customer>`. |
| `Object` return type | Yes | Both methods return `Object`. Return `Customer`. |
| Flag hidden in data | Yes | `if (b.contains("UPDATE"))` lets the customer's name trigger a status write. |
| Silent partial failure | Yes | The update branch handles only `ACTIVE` and `PROSPECT`. Others fall through silently. |
| Incomplete validation | Yes | `email` and `phone` are stored unchecked. |

## Step 2: Fixture tie-in

| Smell | Effect on CUS-1001 / CUS-1002 |
| ----- | ----------------------------- |
| `==` on String ids | Amina resolves only from the stored String instance. An id read at runtime misses and she reads as deleted. |
| Magic strings | Creating Amina with `"active"` falls through to `else` and stores `PROSPECT`. A typo becomes wrong data, no error. |
| Null as an error code | Creating Ravi twice returns `null`, the same value returned for a blank name. Two causes, one signal. |
| Flag hidden in data | Ravi cannot move `PROSPECT` to `ACTIVE` unless his name contains `UPDATE`. |
| Mixed I/O in domain | The log line is `ok CUS-1001`. Nothing ties it to `lab-request-001`. |
| Silent partial failure | Suspending Amina does nothing and reports nothing. She stays `ACTIVE`. |

## Step 3: Priority

1. **`==` on String ids.** The only live defect, one-line fix, provable with a test that passes `new String("CUS-1001")`.
2. **Long method and names.** The lab's main deliverable, and the duplicated chains, magic strings, and hidden `UPDATE` flag go with it.

## Predicted answer

Yes, though length is the symptom. The reason to split `doStuff` is that it changes for seven different reasons.

## Scope
Pre-lab only. Do not finish the full lab in this exercise.

## Fixtures
Amina `CUS-1001` / `ACTIVE`, Ravi `CUS-1002` / `PROSPECT`, correlation `lab-request-001`.
