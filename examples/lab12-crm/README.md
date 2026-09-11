# Lab 12 starter — timed path (~45 minutes)

**Theme:** Coding standards — smell catalog + refactor messy service

## Activity card

| | |
| --- | --- |
| **Objective** | Freeze baseline, catalog smells, refactor to create/get/updateStatus |
| **Skills practiced** | Naming, equals/Map, evidence docs, green tests |
| **Expected outcome** | 8 tests green + `docs/smells.md` / before-after filled |
| **Estimated time** | ~45 minutes |
| **Files** | `examples/lab12-crm/` copied from this starter |

**Boilerplate reduced:** Messy baseline + test shells given — focus on smell catalog and target API refactor.

Pacing: [`../../PACING.md`](../../PACING.md) · Full steps: [`../LAB-12-GUIDE.md`](../LAB-12-GUIDE.md)

## Copy into your workspace

**Windows (PowerShell)** — from this lab folder:

```powershell
New-Item -ItemType Directory -Force -Path "$env:USERPROFILE\java-bootcamp\examples\lab12-crm" | Out-Null
Copy-Item -Recurse -Force ".\starter\*" "$env:USERPROFILE\java-bootcamp\examples\lab12-crm\"
cd $env:USERPROFILE\java-bootcamp\examples\lab12-crm
```

**macOS / Linux:**

```bash
mkdir -p ~/java-bootcamp/examples/lab12-crm
cp -R starter/. ~/java-bootcamp/examples/lab12-crm/
cd ~/java-bootcamp/examples/lab12-crm
```

Full GUIDE: [`../LAB-12-GUIDE.md`](../LAB-12-GUIDE.md)

## 45-minute checklist

- [ ] Confirm `CustomerService.before.java.txt` frozen
- [ ] Fill `docs/smells.md` (≥8 smells)
- [ ] Refactor to `createCustomer` / `getCustomer` / `updateStatus` (remove `doStuff`)
- [ ] Make `CustomerServiceTest` TODOs green
- [ ] Note before/after in `docs/before-after.md`
- [ ] Run smoke test

## Smoke test

```bash
mvn -B test
```

## Timed-path Pass criteria

| Criterion | Pass / Fail |
| --------- | ----------- |
| No `doStuff` remains; clean API present | Pass / Fail |
| Tests green for CUS-1001 / unknown / duplicate | Pass / Fail |
| smells.md has ≥8 items | Pass / Fail |

Continue remaining GUIDE steps as homework / full path if needed.

---

## My notes

### Run

```bash
mvn -B clean test
mvn -B verify
java -cp target/classes com.northstar.crm.Main
```

### Cleanup

```bash
mvn clean
git status
```

`CustomerService.before.java.txt` and `docs/` stay. `target/` is ignored.

### Evidence

- `docs/smells.md`: 8 smells plus 4 extras, each with a `CUS-1001` impact
- `docs/before-after.md`: smell-to-fix map, method list, test output, demo transcript, 5 failure experiments
- `docs/ai-review-notes.md`: manual review entries `lab12-001`, `lab12-002`
- `docs/CODING-STANDARDS-check.md`: 6-point checklist

### Architecture: NOW vs LATER

NOW: `Main` and tests call `CustomerService` directly. Storage is a `HashMap` in the
service. Errors are exceptions. The correlation id is a field the caller sets.

LATER: Angular calls a Spring Boot controller over HTTPS/JSON. The controller reads the
correlation header, puts it in MDC, maps the same exceptions to problem-detail responses,
and the service talks to PostgreSQL through JPA instead of the map. Kafka takes the
`updateStatus` event for notifications and audit. The three public method signatures are
what the controller will call, so they should not need to change.

### SOLID

Applied: single responsibility. Create, get and update are separate methods; validation
is in helpers that do one check each. The service no longer changes when the log format
changes, because `log` is one method.

Deferred: dependency inversion. The map and the logger are constructed inside the class.
An interface for storage would have one implementation until Lab 14 and would be an
abstraction with no second user. Open/closed on status transitions (which moves are
legal) is also deferred; `updateStatus` accepts any move today.

### Security

Untrusted inputs: every argument to `createCustomer` and `updateStatus`. Validation is in
the `require*` helpers; `email` and `phone` are not validated yet. No authn/authz anywhere,
which is a Lab 13+ concern at the controller. Sensitive values: `email` and `phone`, held in
memory only, never logged.

### Reflection

1. Keying the map by `customerId`. It made the `==` bug impossible rather than fixed, and
   it made duplicate detection a `containsKey` instead of a loop that could drift.
2. Six service tests that name the scenario in the method name, plus the `Main` transcript
   matching the guide's expected output line for line. Experiment 5 shows the limit: the
   suite would not catch the `UPDATE` branch coming back.
3. Null on lookup miss. Returning `null` for "not found" is common enough that it does not
   look wrong; the argument for throwing is that the caller has to handle it at the call
   site, with the correlation id attached, instead of at some later NPE.
