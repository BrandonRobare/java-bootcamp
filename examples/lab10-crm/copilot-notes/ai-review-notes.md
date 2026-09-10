# AI review notes — Lab 10

## lab10-001 — weak vs strong (entity)
- Date: 2026-09-09
- Weak prompt used: // customer class
- Output summary: Copilot suggested a JPA-style entity with Long id and @Entity annotations (rejected).
- Strong prompt used: // Java entity class Customer in package com.northstar.crm.entity representing a Northstar CRM customer. Fields: customerId (String, format "CUS-1001"), fullName (String), email (String), phone (String), status (CustomerStatus enum), createdAt (LocalDateTime). No-args constructor, all-args constructor, getters and setters, equals/hashCode based only on customerId, toString.
- Output summary: Generated a plain POJO matching the requested fields and methods. No persistence annotations accepted.
- Decision: accept
- Reason (1 sentence): Strong prompt prevented JPA hallucination and produced the required POJO shape.

## lab10-002 — weak vs strong (addCustomer)
- Date: 2026-09-09
- Decision: accept
- Reason: Implemented guard clauses for null/blank customerId and duplicate checks throwing appropriate exceptions.

## lab10-003 — mandatory human-review pass

Every suggestion accepted in Steps 4-5 (CustomerStatus, Customer, CustomerService) walked
through the Step 7 checklist. Marks kept as self-check only; evidence recorded here.

| # | Confirm | Evidence |
| - | ------- | -------- |
| 1 | Every import resolves against pom.xml deps actually present (no phantom JPA/Spring imports) | Only `java.time.LocalDateTime`, `java.util.Objects`, `java.util.ArrayList/List/Optional` and `com.northstar.crm.*`. pom.xml has one dependency, junit-jupiter (test scope). No jakarta/javax/springframework import anywhere in `src/`. |
| 2 | Business rules from the prompt appear in code, not only in comments | `addCustomer` throws IllegalArgumentException on null customer and null/blank customerId, IllegalStateException on duplicate id; `updateStatus` throws IllegalArgumentException via `orElseThrow` on unknown id. |
| 3 | equals / hashCode based on customerId only | `Customer.equals` compares `Objects.equals(customerId, ...)` and nothing else; `hashCode` is `Objects.hash(customerId)`. Other five fields deliberately excluded so a status change does not change identity. |
| 4 | I could explain every line with Copilot turned off | `deleteCustomer(String)` was written by hand with Copilot disabled (failure experiment 2) using `List.removeIf` — same idiom as the generated code, so the accepted hunks are within what I can write unaided. |
| 5 | No hardcoded secrets, real PII, or inappropriate test data | Fixtures only: CUS-1001 / CUS-1002, `@example.com` addresses, 555-01xx phone numbers. No credentials, tokens, or real customer data in sources or prompts. `target/` is gitignored and untracked. |

**Copilot mistake caught and corrected:** the weak prompt in lab10-001 produced a JPA-style
entity — `@Entity` / `@Id` / `@Column` from `jakarta.persistence` with a numeric `Long id`.
Rejected rather than "fixed" by adding JPA to the POM; identity stayed `String customerId`
in `CUS-1001` format. Accepting the `Long` would break Lab 11 tests with
`String cannot be converted to Long`.

**Scaffold note:** CustomerStatus has exactly the four lifecycle constants (PROSPECT,
ACTIVE, SUSPENDED, CLOSED); Customer is a plain POJO with no-args and all-args
constructors plus getters/setters for all six fields.

## lab10-004 — AI risk awareness
- Notes:
  1. Avoided typing real PII; used CUS-1001 / CUS-1002 and example emails.
  2. If Copilot suggests verbatim library code, inspect license/source and reimplement or cite before accepting.
  3. Team rule: do not merge AI-generated code until one human documents line-by-line what was accepted and why; unclear code must be rejected or rewritten.

## Failure Experiments

1. Experiment: Ask Chat to add a save method with no context
   - Action: did not run Copilot; simulated expected bad suggestion review.
   - Observe: typical hallucination is an invented DB layer or @Entity usage; would be rejected and removed.
   - Restore / conclude: do not accept; remove annotations and recompile.

2. Experiment: Disable Copilot and add deleteCustomer(String) by hand
   - Action: implemented deleteCustomer(String) manually in CustomerService.java.
   - Observe: compile succeeds without AI assistance.
   - Restore / conclude: confirmed the project can be completed without Copilot.

3. Experiment: Draft (do not send) a Chat prompt with a fake SSN/password as example
   - Action: drafted nothing that contains real PII; used CUS-1001/CUS-1002 only.
   - Observe: do not paste even fake sensitive values into Chat. Rewrote prompts to use safe IDs.
   - Restore / conclude: always sanitize example data.

4. Experiment: Ask Chat to build the entire CRM service layer in one shot
   - Action: did not accept oversized dump; used scoped prompts for each class/method.
   - Observe: large dumps are hard to review and often include unwanted framework imports.
   - Restore / conclude: prefer small, reviewed prompts per method/class.
