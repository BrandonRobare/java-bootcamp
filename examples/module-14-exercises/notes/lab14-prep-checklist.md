# Lab 14 prep checklist

## Earlier exercise files present?

Checked on 2026-09-11 with `ls` and a grep for leftover template blanks.

| File | Present? (yes/no) |
| ---- | ----------------- |
| notes/lab14-entity-vs-dto.md | yes |
| notes/lab14-mapper-no-leak.md | yes |
| notes/lab14-annotate-dto.md | yes |
| notes/lab14-invalid-cases.md | yes |
| notes/lab14-validatorfactory-todos.md | yes |

Debug: if one were missing, reopen that exercise before the OS guide. Order matters: E1 defines the boundary, E2 the mapper, E3 the annotations, E4 the cases, E5 the test scaffolding. A gap early on means the later notes were written without it.

## Fixtures (verify)

| ID | Name | Status | Email |
| -- | ---- | ------ | ----- |
| CUS-1001 | Amina Khan | ACTIVE | amina.khan@example.com |
| CUS-1002 | Ravi Singh | PROSPECT | ravi.singh@example.com |

`CUS-9999` is the unknown id. Correlation id `lab-request-001` lives in the header and logs, never in a DTO field.

## Scope statement

Pre-lab only: prepare for lab; do not complete full Lab 14 now.
Spring `@Valid`? No. Plain Maven, `jakarta.validation` with Hibernate Validator and Expressly, validation triggered by `ValidatorFactory` in the facade and in `CustomerRequestValidationTest`. Deep status transitions belong to Lab 15; the controller belongs to Lab 24.

## Boundary statement

The facade returns `CustomerResponseDTO`, never `Customer`. The mapper whitelists customerId, fullName, email, status, createdAt on the way out and leaves `phone` behind.

Predict: marking Pass while still planning to return `Customer` from the facade means E1 and E2 failed the gate. The file exists but the no-leak rule was not absorbed.

## Self-check (do not write this mark down)

Overall prep: checked myself against the five files above. No blanks remain, fixtures match, Spring deferred.
If Fail, revisit exercise(s): none.
