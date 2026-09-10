# AI test/refactor notes: Lab 11

## lab11-001: rejected false-confidence assertion

Asked for "one more test for CustomerServiceTest" with no constraints. Got this:

```java
@Test
void serviceIsNotNull() {
    assertNotNull(service);
}
```

Rejected it. `@BeforeEach` runs `service = new CustomerService()`, so there is no input
that makes this fail. It asserts that the JVM allocated an object. That is a test of the
test harness, not of CustomerService.

Replaced it with a test of real filtering behavior:

```java
@Test
void findByStatusReturnsOnlyMatchingCustomers()
```

Adds CUS-1001 (PROSPECT) and CUS-1002 (ACTIVE), then asserts `findByStatus(PROSPECT)`
returns exactly one customer and that it is CUS-1001. It fails if the stream filter drops
the predicate or compares the wrong field.

## lab11-002: code smell: duplicated blank-ID validation

Smell: duplicated guard. `addCustomer` had the null/blank customerId check written inline.
`updateStatus` had no check at all, so a blank ID fell through to a "Customer not found"
message that named the wrong problem.

Refactor applied: extracted `private void validateCustomerId(String)`. It is now the only
blank-ID check in the class, called from both `addCustomer` and `updateStatus`.
`addCustomer` keeps its own separate null-customer guard, since that is a different
precondition.

Tests proving behavior unchanged:

```bash
mvn -q test -Dtest=CustomerServiceTest,CustomerNotifierMockTest
```

6 tests green before the extract and 6 green after. `addCustomerStoresNewCustomer`,
`addCustomerRejectsDuplicateId`, `updateStatusChangesExistingCustomer` and
`updateStatusThrowsForUnknownCustomer` were not touched by the refactor and still pass,
which is the evidence that the extract preserved behavior.

## lab11-003: coverage gaps

| Class | Method | Covered? | By |
| --- | --- | --- | --- |
| `Customer` | `equals` | Yes | `equalsIsBasedOnCustomerIdOnly` |
| `Customer` | `toString` | Yes | `toStringIncludesCustomerId` |
| `Customer` | getters/setters | Indirect | every test |
| `Customer` | `hashCode` | No | |
| `CustomerService` | `addCustomer` (happy) | Yes | `addCustomerStoresNewCustomer` |
| `CustomerService` | `addCustomer` (duplicate) | Yes | `addCustomerRejectsDuplicateId` |
| `CustomerService` | `addCustomer` (null / blank id) | No | |
| `CustomerService` | `addCustomer` (createdAt / status defaulting) | No | |
| `CustomerService` | `findByCustomerId` | Indirect | add/update tests |
| `CustomerService` | `findByStatus` | Yes | `findByStatusReturnsOnlyMatchingCustomers` |
| `CustomerService` | `updateStatus` (happy) | Yes | `updateStatusChangesExistingCustomer` |
| `CustomerService` | `updateStatus` (unknown id) | Yes | `updateStatusThrowsForUnknownCustomer` |
| `CustomerService` | `updateStatus` calls notifier | Yes | `updateStatusInvokesNotifierWithOldAndNewStatus` |
| `CustomerService` | `listAll` | No | |

Gap decisions as of today:

- `listAll`: acceptable. It is a one-line `List.copyOf` delegation and there is nothing in
  it the compiler does not already catch. Worth a test once it grows a sort or a filter.
- `hashCode`: acceptable for now, but this one is a real risk. `equals` is ID-only, so a
  `hashCode` that includes other fields would break HashSet and HashMap lookups without
  any test going red. Nothing puts Customer in a hash collection yet. Add the test when
  something does.
- Blank/null customerId on `addCustomer` and `updateStatus`: not acceptable.
  `validateCustomerId` is a branch that throws and there is no test behind it. Carrying it
  into Lab 12 as the first test to write.
- `createdAt` / `status` defaulting in `addCustomer`: acceptable. Behavior came over from
  Lab 10 unchanged.
- `findByCustomerId`: indirect coverage is enough here. Every other test would fail if it
  broke.

## lab11-004: acceptance guidelines for AI-generated tests and refactors

1. Every assertion must be able to fail. If I can't describe an input that breaks it, it
   isn't a real test.
2. Every refactor must be backed by a passing test suite run before and after.
3. No accepted suggestion may introduce a dependency not already in `pom.xml`.
4. I can explain, without re-reading Copilot's explanation, why the code is correct.
5. Coverage gaps are documented, not silently ignored.

Full suite:

```bash
mvn -q clean test
```

`Tests run: 8, Failures: 0, Errors: 0`. That is CustomerTest 2 + CustomerServiceTest 5 +
CustomerNotifierMockTest 1, and BUILD SUCCESS. No Lab 9 PlaceholderTest in the tree.

## Failure experiments

| # | Experiment | Observed | Restored |
| - | ---------- | -------- | -------- |
| 1 | "One more test", no constraints | `assertNotNull(service)`, passes unconditionally | Rejected and replaced, see lab11-001 |
| 2 | Deleted `notifier.notifyStatusChange(...)` from `updateStatus` | CustomerNotifierMockTest fails with `Wanted but not invoked: notifier.notifyStatusChange("CUS-1002", PROSPECT, ACTIVE); Actually, there were zero interactions with this mock.` | Line put back, 8 green |
| 3 | Mockito test against `notifyCreated(Customer)` | Does not compile: `cannot find symbol: method notifyCreated(com.northstar.crm.entity.Customer)`. The method was never on the interface. | Scratch test deleted, stayed with the declared `notifyStatusChange` |
| 4 | `mvn test` twice with no changes | `Tests run: 8, Failures: 0, Errors: 0` both runs | Nothing to restore, no flake |

Experiment 2 is the one worth keeping in mind. Deleting the call under test turns the mock
test red, which is what shows the verify is doing work.
