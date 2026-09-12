# DTO boundary notes - Lab 14

## 1. Why the entity must not be the public API contract

`Customer` is the program's internal shape. It has a `phone` field the API never asked
for, a `createdAt` the server owns, and equality by `customerId`. If it were returned
directly, every internal change (adding a JPA `@Id`, a password hash, an audit column)
would silently become a public API change, and every caller could set fields the
server should own.

`CustomerRequestDTO` and `CustomerResponseDTO` are the contract. They change only when
the API is meant to change. `CustomerMapper` is the single place the two worlds meet,
so the copying happens once and consistently instead of in every facade method.

## 2. Where validation runs (facade) vs business rules (service)

| Layer | Checks | Exception | Future HTTP |
| --- | --- | --- | --- |
| `CustomerApiFacade` | shape of the payload: blank, length, email format (Bean Validation annotations on the request DTO) | `IllegalArgumentException` | 400 |
| `CustomerService` | business state: duplicate `customerId`, default status | `IllegalStateException` | 409 |
| `CustomerMapper` | `CustomerStatus.valueOf` on the status string | `IllegalArgumentException` | 400 |

Annotations on the DTO are inert; nothing happens until `validator.validate(request)`
is called. The facade calls it first, before `service.createCustomer`, so invalid data
never reaches the service. Experiment 4 (below) shows what happens when that order is
broken.

In Spring Boot the facade becomes a `@RestController`, `@Valid` on the parameter
replaces the manual `validate()` call, and a `@ControllerAdvice` maps the exceptions to
status codes. The DTOs and mapper do not change.

## 3. Correlation `lab-request-001` on invalid payloads

The correlation ID is a per-request tag so one failure can be found in logs later. In
this lab it is a constant passed by `Main`; in production it is a UUID from an
`X-Correlation-ID` header, stored in MDC by a filter so every log line carries it.

The facade is the only layer that has both the request and the ID, so it is where the
ID is attached: logged to stderr and prefixed on the exception message.

```
[lab-request-001] validation failed: email: Email must be a valid email address
REJECTED: [lab-request-001] email: Email must be a valid email address
REJECTED: Customer not found: [lab-request-001] CUS-9999
```

## 4. What must never appear on response DTOs

- Validation annotations. Nothing validates outbound data; they are noise at best and
  `@NotBlank` on a `LocalDateTime` throws `UnexpectedTypeException` if ever run.
- Setters. The response is built once by `of(...)` and read; callers must not mutate it.
- Entity or persistence types (`Customer`, JPA ids, repository references).
- Fields the client did not ask for: internal flags, secrets, audit trails, `phone`
  until the contract says so.

## Failure experiments (2026-09-12)

| # | Experiment | Observed | Restore |
| - | --- | --- | --- |
| 1 | Removed `hibernate-validator` from `pom.xml`, ran `mvn clean test` | `NoProviderFoundException: Unable to create a Configuration, because no Jakarta Validation provider could be found` from `CustomerRequestValidationTest.setUp` | Dependency restored, 3/3 green |
| 2 | `create` with null `fullName`, bad email, blank status, null status | Four `IllegalArgumentException`s prefixed `[lab-request-001]`, each naming the field; `service.findByCustomerId` empty for all four, so nothing reached the service | none needed |
| 3 | `create` CUS-1001 twice | First OK; second `IllegalStateException: Duplicate customerId: CUS-1001`. Different type and no correlation prefix because it comes from the service, not Bean Validation | none needed |
| 4 | `validateOrThrow` lost its `throw` while adding the log line | Bad email was logged and then created anyway; `Main` showed the warning but no `REJECTED` line | `throw` re-added after the log; `Main` now shows both `REJECTED` lines |
| 5 | status `"Active"` (wrong case) | Passes Bean Validation (`@NotBlank` only), then `IllegalArgumentException: No enum constant CustomerStatus.Active` from `valueOf` in the facade | Require enum-aligned strings; a `@Pattern` on status would move this to a validation error |

Experiment 3 vs 5 is the useful contrast: duplicate is a business rule the DTO can
never know about, wrong-case status is a contract rule that could be enforced by the
DTO but currently is not.

Related: [[Lab 13 - REST design notes]]
