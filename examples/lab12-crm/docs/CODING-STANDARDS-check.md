# Coding standards check: Lab 12

Checked against `CustomerService.java` after refactor.

| # | Confirm | Evidence |
| - | ------- | -------- |
| 1 | Meaningful type and method names | `createCustomer`, `getCustomer`, `updateStatus`, `customersById`, `requireNonBlank`, `requireUniqueId`, `requireExisting`. No single-letter parameters. |
| 2 | No raw types in new code | `Map<String, Customer>`, `Customer` return types. `grep -n 'List\b'` on the service returns nothing. |
| 3 | Validation in clear helpers | Three `require*` helpers; the public methods call them and do not repeat the checks. |
| 4 | Exceptions instead of null for errors | No `return null` in the file. Blank: `IllegalArgumentException`. Duplicate: `IllegalStateException`. Unknown: `CustomerNotFoundException`. |
| 5 | No production secrets / no PII beyond lab sample emails | Only `amina.khan@example.com` and `ravi.singh@example.com`, in tests and `Main`. Log lines carry `customerId` only. |
| 6 | Service still compiles without Spring/JPA/Kafka | `pom.xml` dependencies: `junit-jupiter` (test scope) only. Logging is `java.lang.System.Logger`. |

```
mvn -B verify
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
