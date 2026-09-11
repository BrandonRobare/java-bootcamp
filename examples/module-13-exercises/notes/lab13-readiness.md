# Lab 13 - Lab 13 Readiness Self-Check

## Mistake Sweep

Swept all five notes files on 2026-09-11 with grep, then re-read by hand.

| Check | Result |
| --- | --- |
| Template blanks left | none. Exercise 4 had a leftover duplicate `## Breaking Change` section with a blank; deleted. Exercise 2 had the worked example pasted verbatim; rewritten. |
| Verbs in paths | none in the design. The only verb paths (`/getCustomer`, `/getInteractions`) appear in the URI table as the bad examples being corrected. |
| Singular collection | none. `/api/customer/` appears once, as the bad URI in exercise 1. |
| Sub-resource nested | `interactions` and `status` both sit under `/api/customers/CUS-1001/`. |
| Create returns 200 | no. POST is 201 with `Location` in the status map and the OpenAPI sketch. |
| Bare array on list | no. `CustomerPage` envelope with `content`, `page`, `size`, `totalElements`, `totalPages`. |
| Error shape differs per endpoint | no. One `ErrorResponse` envelope with `code` and `correlationId`. |
| Schema types guessed | no. `customerId` is a string, `status` is an enum. |
| Version choice justified | yes, URI versioning with the reason and the rejected alternative. |

Predict: a verb left in a path surfaces in Lab 24 as the `@RequestMapping` value on the controller. Once the Angular client is generated from the OpenAPI file, that path is baked into the client, so renaming it later is a breaking change and needs `/api/v2`.

## Deliverable Recall

Recalled first, then checked against the guide. Lab 13 wants:

1. `openapi/northstar-crm-customers.yaml` with the Customer collection and item paths, every TODO filled
2. `docs/rest-design-notes.md`: the URI table (including PUT, DELETE, PATCH status rows), method/status map, error contract
3. Pagination, filter, and sort query design, both in the notes and reflected in the YAML
4. Amina and Ravi examples in the schemas matching the fixtures
5. Optional: a thin Spring Boot stub serving one GET
6. README run and cleanup steps
7. No secrets or `target/` committed

Missed on recall: item 6 (README) and item 7 (no `target/`). Both are housekeeping, not design, but they are on the checklist.

## Fixture Check

Every ID in the five files is `CUS-1001`, `CUS-1002`, `CUS-9999`, or `CUS-1003` (the hypothetical new ID in the POST `Location` example). Names are Amina Khan and Ravi Singh only; emails are `@example.com` only; correlation id is `lab-request-001`. No real customer data, no real email domains, no secrets.

## Pass Mark

Debug: a file that still contains a template blank is a Fail, not a Pass, even if the rest is complete.

Pass. All five files exist under `notes/`, contain no blanks, and meet their own pass criteria:

- `lab13-uri-table.md`
- `lab13-method-status-map.md`
- `lab13-error-contract.md`
- `lab13-collection-versioning.md`
- `lab13-openapi-skeleton.md`

## Scope

Pre-lab only: do not finish the full lab in this exercise.
