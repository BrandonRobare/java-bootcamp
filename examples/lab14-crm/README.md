# Lab 14 starter — timed path (~45 minutes)

**Theme:** DTOs + Jakarta Bean Validation at the API boundary

## Activity card

| | |
| --- | --- |
| **Objective** | Complete DTO annotations, mapper, facade validation, and tests |
| **Skills practiced** | Bean Validation, no-leak mapping, correlation on failures |
| **Expected outcome** | `mvn -B clean test` green · response DTOs only |
| **Estimated time** | ~45 minutes |
| **Files** | `examples/lab14-crm/` copied from this starter |

**Boilerplate reduced:** Baseline CRM + DTO shells given — fill `// TODO`; do **not** add Spring Boot.

Pacing: [`../../PACING.md`](../../PACING.md) · Full steps: [`../LAB-14-GUIDE.md`](../LAB-14-GUIDE.md)

**Honesty:** No Spring `@Valid` controllers in Lab 14 — use `ValidatorFactory` in the facade.

## Copy into your workspace

**Windows (PowerShell)** — from this lab folder:

```powershell
New-Item -ItemType Directory -Force -Path "$env:USERPROFILE\java-bootcamp\examples\lab14-crm" | Out-Null
Copy-Item -Recurse -Force ".\starter\*" "$env:USERPROFILE\java-bootcamp\examples\lab14-crm\"
cd $env:USERPROFILE\java-bootcamp\examples\lab14-crm
```

**macOS / Linux:**

```bash
mkdir -p ~/java-bootcamp/examples/lab14-crm
cp -R starter/. ~/java-bootcamp/examples/lab14-crm/
cd ~/java-bootcamp/examples/lab14-crm
```

Full GUIDE: [`../LAB-14-GUIDE.md`](../LAB-14-GUIDE.md)

## 45-minute checklist

- [x] Add Bean Validation annotations on `CustomerRequestDTO`
- [x] Implement `CustomerMapper` + `CustomerApiFacade` TODOs
- [x] Complete validation tests (valid / bad email / blank name)
- [x] Main demo returns response DTOs only; invalid path shows `lab-request-001`
- [x] Fill `docs/dto-boundary-notes.md`
- [x] Run smoke test

## Smoke test

```bash
mvn -B clean test
```

## Timed-path Pass criteria

| Criterion | Pass / Fail |
| --------- | ----------- |
| Validation tests green | Pass (3/3, 2026-09-12) |
| Facade returns DTOs only (no entity leak) | Pass |
| Invalid payload rejected before service | Pass (experiment 2) |
| Correlation id visible on failure | Pass |

Continue remaining GUIDE steps as homework / full path if needed.

## Run

```bash
mvn -B clean test
mvn -q compile exec:java -Dexec.mainClass=com.northstar.crm.Main
```

## Validation rules (CustomerRequestDTO)

| Field | Constraints |
| ----- | ----------- |
| customerId | @NotBlank, @Size(max=32) |
| fullName | @NotBlank, @Size(2..100) |
| email | @NotBlank, @Email, @Size(max=254) |
| status | @NotBlank; must match `CustomerStatus` exactly (ACTIVE, PROSPECT, SUSPENDED, CLOSED) or `valueOf` fails in the mapper |

`phone` is not on the request DTO; the facade passes `null` to the service.

## Fixtures

| customerId | fullName | status | email |
| --- | --- | --- | --- |
| CUS-1001 | Amina Khan | ACTIVE | amina.khan@example.com |
| CUS-1002 | Ravi Singh | PROSPECT | ravi.singh@example.com |

Correlation ID: `lab-request-001`

## Main output (2026-09-12)

```
OK: CUS-1001 | Amina Khan | amina.khan@example.com | ACTIVE | 2026-09-12T13:43:43.122142
OK: CUS-1002 | Ravi Singh | ravi.singh@example.com | PROSPECT | 2026-09-12T13:43:43.122698
OK: CUS-1001 | Amina Khan | amina.khan@example.com | ACTIVE | 2026-09-12T13:43:43.122142
[lab-request-001] validation failed: email: Email must be a valid email address
REJECTED: [lab-request-001] email: Email must be a valid email address
REJECTED: Customer not found: [lab-request-001] CUS-9999
```

## Sample invalid (email)

`email=not-an-email` -> `IllegalArgumentException("[lab-request-001] email: Email must be a valid email address")`, thrown by the facade before `CustomerService.createCustomer` is called.

## Design decisions

- Validation is triggered in the facade with `validator.validate()`; the service never sees an invalid DTO. Spring `@Valid` replaces the trigger later, not the rules.
- Response DTO has no setters and no validation annotations; it is built by `of(...)` only.
- `get` uses `findByCustomerId(...).orElseThrow(CustomerNotFoundException)` so the not-found exception is a distinct type from validation failures (future 404 vs 400).
- Duplicate `customerId` is an `IllegalStateException` from the service, not a validation error; the DTO cannot know what already exists.
- Failure experiments and the entity-vs-DTO note: `docs/dto-boundary-notes.md`.
