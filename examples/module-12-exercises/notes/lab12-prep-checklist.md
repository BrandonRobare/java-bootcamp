# Lab 12 Prep Checklist

## Earlier exercise files present?

| File | Exercise | Present? |
| ---- | -------- | -------- |
| `notes/lab12-target-api-sketch.md` | 1 | yes |
| `notes/lab12-solid-scope.md` | 2 | yes |
| `notes/lab12-smell-bingo.md` | 3 | yes |
| `notes/lab12-equals-vs-eqeq.md` | 4 | yes |
| `notes/lab12-correlation-todos.md` | 5 | yes |

All five are complete, with every template blank filled in.

## Fixtures (verify)

| ID | Name | Status |
| -- | ---- | ------ |
| CUS-1001 | Amina Khan | ACTIVE |
| CUS-1002 | Ravi Singh | PROSPECT |

Correlation id: `lab-request-001`.

## Ready for the lab

| Item | State |
| ---- | ----- |
| Freeze name | `CustomerService.before.java.txt` |
| Docs paths | `docs/smells.md`, `docs/before-after.md` |
| Target API names ready | `createCustomer`, `getCustomer`, `updateStatus`. No `doStuff`. |
| No Spring Boot or REST hosting | Confirmed. Deferred in the exercise 2 scope note. |
| `examples/lab12-crm/` | Not created yet. Lab step 1 copies it from `lab11-crm`. |

## Debug challenge: why the freeze file cannot stay `.java`

Maven compiles everything under `src/main/java`, so a saved copy of the messy baseline as
`CustomerService.before.java` is a second class with the same name in the same package and
the build fails. Renaming it to `CustomerService.before.java.txt` takes it out of the
compile path while keeping the before state in git as evidence.

## Predicted answer: Surefire count after Lab 12

`Tests run: 8`. The starter ships 8 method shells: 6 in `CustomerServiceTest` and 2 in
`CustomerTest`. The service ones include `createRaviProspectThenActivate`,
`blankCustomerIdThrows`, and `updateUnknownThrowsWithCorrelation`, so the refactor has to
land exceptions and correlation, not just renames.

## Scope statement

Pre-lab only. Prepare for the lab; do not complete full Lab 12 now.

## If Fail, revisit

Nothing outstanding. All five prerequisite notes exist and the fixtures match.
